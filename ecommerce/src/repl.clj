(ns repl)

;; Creating a database
(require '[datomic.api :as d])
(def db-uri "datomic:dev://localhost:4334/hello")
(d/create-database db-uri)