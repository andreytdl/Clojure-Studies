(ns clojure-component.db.user
  (:require [clojure-component.protocols.storage-client :as storage-client]
            [clojure-component.logic.datomic.logic :as db-logic]))
;;;;;;;;;;;;;;
; User logic ;
;;;;;;;;;;;;;;

(defn- user-entry->datomic [user-entry]
  (db-logic/add-namespace "user" user-entry))


(defn transact! [storage data]
  (->> (if (sequential? data) data [data])
       (mapv user-entry->datomic)
       (storage-client/exec! storage)
       (db-logic/assert-transact-ok)))

;; --------- GETTING

(def ^:private pull-all
  '[(pull ?e [*])])

(def ^:private query-all
  {:find  pull-all
   :where '[[?e :user/name ?name]]})

;; (def query-by-name
;;   '{:find '[?e ?name ?message]
;;     :in '[$ ?name]
;;     :where '[[?e :user/name ?name]]})

(def ^:private query-by-name
  (merge '{:in [$ ?name]}
         query-all))

(defn query-update [storage data]

  (transact! storage data))


(def query-all-accounts
  {:find '[?e ?name ?message]
   :where '[[?e :user/name ?name]
            [?e :user/message ?message]]})

(defn get-all [storage]
  (storage-client/q storage (storage-client/->arg-map query-all-accounts)))


(defn get-by-name [name storage]
  (storage-client/q storage query-by-name name))