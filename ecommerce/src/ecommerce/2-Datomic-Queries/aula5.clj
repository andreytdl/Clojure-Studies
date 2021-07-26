(ns ecommerce.2-Datomic-Queries.aula3
  (:require [clojure.pprint]
            [ecommerce.model :as model]
            [ecommerce.db :as db]
            [datomic.api :as d]))

(def conn (db/open-connection))

(db/create-schema! conn)

;; Creating categories
(def eletronics (model/new-category "eletronics"))
(def sport (model/new-category "sport"))

(clojure.pprint/pprint @(d/transact conn [eletronics sport]))

;; Getting the categories
(def categories (db/get-all-categories (d/db conn)))
(clojure.pprint/pprint categories)

;; Creating the products
(def computer (model/new-product (model/uuid) "New computer" "/new-computer" 2500.10M))
(def smartphone (model/new-product (model/uuid) "Expensive smartphone" "/smartphone" 888888.10M))
(def calculator {:product/name "4 operations calculator"})
(def cheap-smartphone (model/new-product "Cheap smartphone" "/cheap-smartphone" 0.1M))
(def chess (model/novo-product "chessboard" "/chessboard"
                               30M))
(clojure.pprint/pprint @(d/transact conn [computer smartphone calculator
                                          cheap-smartphone chess]))
(db/set-category-to-products! conn [computer, smartphone, cheap-smartphone] eletronics)

;; Starting the class

;; With nested maps we're creating a new product and a new category simultaneously 
(clojure.pprint/pprint
 @(db/add-products!
   conn
   [{:product/name     "Shirt"
     :product/slug     "/shirt"
     :product/price     30M
     :product/id        (model/uuid)
     :product/category  {:category/name "Clothes"
                         :category/id  (model/uuid)}}]))

(def sport-id (:category/id sport))

;; If the category already exists, with nested maps we can create a new product 
;; and add the existent category simultaneously using lookup refs.
(clojure.pprint/pprint
 @(db/add-products!
   conn
   [{:product/name       "Soccer"
     :product/slug       "/soccer"
     :product/price      30M
     :product/id         (model/uuid)
     :product/category   [:category/id sport-id]}]))

(clojure.pprint/pprint (db/todos-os-products (d/db conn)))

(db/delete-database!)