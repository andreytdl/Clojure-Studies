(ns ecommerce.2-Datomic-Queries.aula2
  (:require [clojure.pprint]
            [ecommerce.model :as model]
            [ecommerce.db :as db]
            [datomic.api :as d]))

(def conn (db/open-connection))

(db/create-schema conn)

(let [computer (model/new-product "New computer", "/new-computer", 2500.10M)
      expensive-smartphone (model/new-product "Expensive Smartphone", "/expensive-smarthphone", 888888.10M)
      calculator {:product/name "4 operations calculator"}
      cheap-smarthphone (model/new-product "Cheap smartphone", "/cheap-smarthphone", 0.1M)]

  (clojure.pprint/pprint @(d/transact conn [computer, expensive-smartphone, calculator, cheap-smarthphone])))

;; Getting all products
(def products (db/get-all-products (d/db conn)))

;; Getting the product's db-id
(def first-product-id (-> products
                          first
                          first
                          :product/id))
(println "The first product's id is: " first-product-id)

;; retrieving this product from the database
(println (db/get-product-by-id (d/db conn) first-product-id))