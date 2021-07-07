(ns ecommerce.2-Datomic-Queries.aula3
  (:require [clojure.pprint]
            [ecommerce.model :as model]
            [ecommerce.db :as db]
            [datomic.api :as d]))

(def conn (db/open-connection))

(db/create-schema conn)

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
(def xadrez (model/novo-produto "Tabuleiro de xadrez" "/tabuleiro-de-xadrez"
                                30M))
(clojure.pprint/pprint @(d/transact conn [computer smartphone calculator
                                          cheap-smartphone xadrez]))

;; Getting the products
(def products (db/get-all-products (d/db conn)))
(clojure.pprint/pprint products)

;; Setting the computer's category as eletronics using "lookup refs"
;; As i told you, lookup refs can be used due the unicity of the product or
;; category "/id" attribute
(clojure.pprint/pprint
 (d/transact conn [[:db/add
                    [:product/id (:product/id computer)]
                    :product/category
                    [:category/id (:category/id eletronics)]]]))

;; Result 
(clojure.pprint/pprint (db/get-product-by-id
                        (d/db conn (:produto/id computer))))

;; Result above:
;; :product/name "New computer"
;; ...data
;; :product/category #db:{:id 1231413531232314}

(db/delete-database)