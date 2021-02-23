(ns clojure-component.fixtures.logic
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [datomic.client.api :as d]
            [clojure-component.components.components :as components]
            [com.stuartsierra.component :as component]
            [clojure-component.components.system-utils :as system-utils]
            [compute.datomic-client-memdb.core :as memdb]))

;; Add your schemas
(def ^:private schemas ["user"])

(defn- load-schemas
  "Load all schemas within a list, to transact only once"
  []
  (mapcat #(-> (str "datomic/" % ".edn")
                            (io/resource)
                            (slurp)
                            (edn/read-string))
                       schemas))

(def system-map (atom nil))


(defn- load-fixtures
  "Load all fixtures within a list, to transact only once"
  []
  (mapcat #(-> (str "resources/" % ".edn")
                            (io/resource)
                            (slurp)
                            (edn/read-string))
                       schemas))

(defn- init-db [datomic-connection]
  (->> {:tx-data (load-schemas)}
       (d/transact datomic-connection))
  (->> {:tx-data (load-fixtures)}))

(defn clear-db!
  "Clear memdb database for tests purposes"
  []
  (memdb/close (memdb/client {})))

(defn test-fixture [f]

  (components/create-and-start-system! :test-system)

  (let [storage (system-utils/get-component! :storage)]

    (init-db (:connection storage)))

  (f)

  (clear-db!)

  (system-utils/stop-system!))
