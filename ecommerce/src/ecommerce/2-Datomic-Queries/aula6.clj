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
(def computer (model/new-product (model/uuid) "New computer" "/new-computer"
                                 2500.10M))
(def smartphone (model/new-product (model/uuid) "Expensive smartphone"
                                   "/smartphone" 888888.10M))
(def calculator {:product/name "4 operations calculator"})
(def cheap-smartphone (model/new-product "Cheap smartphone" "/cheap-smartphone"
                                         0.1M))
(def chess (model/novo-product "chessboard" "/chessboard"
                               30M))
(clojure.pprint/pprint @(d/transact conn [computer smartphone calculator
                                          cheap-smartphone chess]))
(db/set-category-to-products! conn [computer, smartphone, cheap-smartphone]
                              eletronics)

;; Starting the class - Nested Queries (Subqueries)

(defn get-the-most-expensive-products
  "
   Getting the most expensive products by the wrong way. It is the wrong way 
   because the Datomic is performing two queries separately
   "
  [db]
  (let [max-price (ffirst (d/q '[:find (max ?price)
                                 :where [_ :product/price ?price]]
                               db))]
    (d/q '[:find (pull ?product [*])
           :in $ ?price
           :where [?product :product/price ?price]]
         db max-price)))

;; The right way (Using subquery)
(db/get-the-most-expensive-product conn)

;; Datomic's transaction data --------------------------------------------------

;; Did you know that is possible to use the db/add and even query over the 
;; datomic's transaction?
;; Every time that the Datomic transacts something the transaction receives an
;; datomic-id like the entities.
;; For example (step-by-step):

;; Datomic adds the :product/name field and sets its id to 72
#datom[72 :db/ident :product/name]
;; Datomic adds the :product/slug field and sets its id to 73
#datom[73 :db/ident :product/slug]
;; Datomic adds the :product/price field and sets its id to 74
#datom[74 :db/ident :product/price]

;; Datomic performs a new query at the time "2021-07-27T00:04:12.922-00:00" and
;; sets its id to 31431413514313 
#datom[31431413514313 50 #inst "2021-07-27T00:04:12.922-00:00" 314314135143123
       true]

;; To the entity 13141321314531, datomic is adding to the attribute 72 the value
;; "Shirt". this operation was performed in the query 31431413514313 (the query
;; above)
#datom[13141321314531 72 "Shirt" 31431413514313 true]

;; To the entity 13141321314531, datomic is adding to the attribute 73 the value
;; "/shirt". this operation was performed also in the query 31431413514313 (the 
;; query above)
#datom[13141321314531 73 "/shirt" 31431413514313 true]

;; To the entity 13141321314531, datomic is adding to the attribute 74 the value
;; 30M. this operation was performed also in the query 31431413514313 (the query
;; above)
#datom[13141321314531 74 30M 31431413514313 true]
;; -----------------------------------------------------------------------------

;; So it is possible to add any attributes to the query, for example, the query
;; operator, the IP address of the who performed the query, etc.

(db/add-products! conn [computer smartphone calculator cheap-smartphone
                        chess] "192.168.0.1")

(db/get-all-products-by-ip-address conn "192.168.0.1")

(clojure.pprint/pprint (db/todos-os-products (d/db conn)))

(db/delete-database!)