(ns ecommerce.2-Datomic-Queries.aula1
  (:require [clojure.pprint]
            [ecommerce.model :as model]
            [ecommerce.db :as db]
            [datomic.api :as d]))

(def conn (db/open-connection))

(db/create-schema! conn)

(let [computer (model/new-product "New computer", "/new-computer", 2500.10M)
      expensive-smartphone (model/new-product "Expensive Smartphone", "/expensive-smartphone", 888888.10M)
      calculator {:product/name "4 operations calculator"}
      cheap-smartphone (model/new-product "Cheap smartphone", "/cheap-smartphone", 0.1M)]

  (clojure.pprint/pprint @(d/transact conn [computer, expensive-smartphone, calculator, cheap-smartphone])))

;; Getting all products
(def products (db/get-all-products (d/db conn)))

;; Getting the product's db-id
(def first-product-id (-> products
                          first
                          first
                          :db/id))
(println "The first product's id is: " first-product-id)

;; retrieving this product from the database
(print (db/get-product-by-db-id (d/db conn) first-product-id))