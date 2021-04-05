(ns ecommerce.db
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/hello")
(defn open-connection []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn delete-database []
  (d/delete-database db-uri))

(def schema [{:db/doc              "O nome de um produto"
              :db/ident            :product/name
              :db/valueType        :db.type/string
              :db/cardinality      :db.cardinality/one}
             {:db/doc              "O caminho para acessar esse produto via http"
              :db/ident            :product/slug
              :db/valueType        :db.type/string
              :db/cardinality      :db.cardinality/one}
             {:db/doc              "O preco de um produto com precisão monetária"
              :db/ident            :product/price
              :db/valueType        :db.type/bigdec
              :db/cardinality      :db.cardinality/one}])