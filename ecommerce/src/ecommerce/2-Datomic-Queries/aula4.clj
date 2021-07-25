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
(def chess (model/novo-produto "chessboard" "/chessboard"
                               30M))
(clojure.pprint/pprint @(d/transact conn [computer smartphone calculator
                                          cheap-smartphone chess]))

(db/set-category-to-products! conn [computer, smartphone, cheap-smartphone] eletronics)

(clojure.pprint/pprint (db/get-product-by-id (d/db conn) (:product/id computer)))

(db/set-category-to-products! conn [chess] sport)

(clojure.pprint/pprint (db/get-all-products-name-and-its-category-name conn))

(clojure.pprint/pprint (db/get-products-by-category conn "eletronics"))

(clojure.pprint/pprint (db/get-all-products (d/db conn)))

(db/delete-database!)