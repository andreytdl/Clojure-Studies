(ns clojure-component.components.datomic-memory
  (:require
   [com.stuartsierra.component :as component]
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [datomic.client.api :as d]
   [clojure-component.protocols.storage-client :as storage-prot]))

(def db-name "clojure-component-dev")

(def schemas ["user"])

(defn tx-data [data]
  {:tx-data data})

(defn load-schema
  "Reads a schema with <<name>>.edn resource"
  [name]
  (-> (str "datomic/" name ".edn")
      (io/resource)
      (slurp)
      (edn/read-string)))


(defn new-client []
  (require 'compute.datomic-client-memdb.core)
  (if-let [client (resolve 'compute.datomic-client-memdb.core/client)]
    (@client {})
    (throw (ex-info "compute.datomic-client-memdb.core is not on the classpath." {}))))

(defn new-conn []
  (d/connect (new-client)
             {:db-name db-name}))

(defn create-db []
  (let [client (new-client)
        _      (d/create-database client {:db-name db-name})
        conn   (d/connect client {:db-name db-name})]
    (doseq [schema schemas]
      (->> schema
           (load-schema)
           (tx-data)
           (d/transact conn)))))

(defrecord DatomicMemory [; runtime objects
                          connection]
  component/Lifecycle
  (start [this]
    (if connection
      this
      (do (create-db)
          (assoc this :connection (new-conn)))))

  (stop [this]
    (if connection
      (assoc this :connection nil)
      this))
  
  ;;Implements
  storage-prot/StorageClientMemory
  (q [_ {query :query args :args}]
    (d/q {:query query
          :args  (concat [(d/db connection)] args)}))
  ;; (q [_this data] (d/q data (d/db connection)))
  (q [_this data arg] (d/q data (d/db connection) arg))
  (q [_this data arg1 arg2 arg3] (d/q data (d/db connection) arg1 arg2 arg3))
  (exec! [_this data] (d/transact connection {:tx-data data}))
  (transact [_ tx-data]
    (d/transact connection tx-data)))

(defn new-datomic-memory []
  (map->DatomicMemory {}))
