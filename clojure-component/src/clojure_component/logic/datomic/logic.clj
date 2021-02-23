(ns clojure-component.logic.datomic.logic
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure-component.protocols.storage-client :as d])
  (:import (clojure.lang ExceptionInfo)
           (java.util.concurrent ExecutionException)))

(defn load-schema
  "Reads a schema with <<name>>.edn resource"
  [name]
  (-> (str "datomic/" name ".edn")
      (io/resource)
      (slurp)
      (edn/read-string)))

(defn resolve-datomic-ref [ref-key m]
  (get m ref-key m))

(defn resolve-map [record ns references-map]
  (reduce (fn [final-map [key value]]
            (if (= (namespace key) ns)
              (let [stripped-key (keyword (name key))]
                (assoc final-map stripped-key (if (contains? references-map stripped-key)
                                                (resolve-datomic-ref (get references-map stripped-key) value)
                                                value)))
              final-map))
          {}
          record))

(defn assert-transact-ok [{status :status :as result}]
  (when (= status :failed)
    (throw (ex-info "result status failed" result)))
  result)

(defn tx-data [data]
  {:tx-data data})

(defn filter-enum [obj key expected-value]
  (= expected-value
     (get-in obj [key :db/ident])))

(defn transact! [datomic-connection data]
  (->> data
       (tx-data)
       (d/transact datomic-connection)
       (assert-transact-ok)))

(defmacro retry?
  ([retries tt & body]
   `(if (> ~retries 0)
      ~@body
      (throw (IllegalStateException. "Max Retries Exceeded" ~tt)))))

(defn try-transact!
  ([datomic-connection f]
   (try-transact! datomic-connection f 5))

  ([datomic-connection f retries]
   (try
     (transact! datomic-connection (f))

     ; datomic cloud
     (catch ExceptionInfo t
       (if (= (:cognitect.anomalies/category (ex-data t))
              :cognitect.anomalies/conflict)
         (retry? retries t
                 (try-transact! datomic-connection f (dec retries)))
         (throw t)))

     ; datomic on-prem
     (catch IllegalStateException ise
       (retry? retries ise
               (try-transact! datomic-connection f (dec retries))))

     ; datomic memory
     (catch ExecutionException ee
       (retry? retries ee
               (try-transact! datomic-connection f (dec retries)))))))

(defn ident-name [datomic eid]
  (->> (d/q datomic
            (d/->arg-map '[:find ?v
                           :in $ ?eid
                           :where [?eid :db/ident ?v]]
                         eid))
       (ffirst)))

(defn ident-id [datomic ident]
  (->> (d/q datomic
            (d/->arg-map '[:find ?eid
                           :in $ ?v
                           :where [?eid :db/ident ?v]]
                         ident))
       (ffirst)))

(defn pull-eid [datomic eid]
  (->> (d/q datomic
            (d/->arg-map '[:find (pull ?eid [*])
                           :in $ ?eid]
                         eid))
       (ffirst)))

(defn add-namespace [ns payload]
  (reduce (fn [namespaced [key value]]
            (assoc namespaced (keyword ns (name key)) value))
          {}
          payload))