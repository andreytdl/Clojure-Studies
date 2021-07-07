(ns ecommerce.2-Datomic-Queries.aula2
  (:require [clojure.pprint]
            [ecommerce.model :as model]
            [ecommerce.db :as db]
            [datomic.api :as d]))

;; What if we transact a data with the same id than other?
;; R) The datomic will update old values.
(def conn (db/open-connection))

(db/create-schema conn)

(def computer (model/new-product (model/uuid) "New computer", "/new-computer", 2500.10M))
(def smartphone (model/new-product (model/uuid) "Expensive smartphone", "/smartphone", 888888.10M))
(def calculator {:product/name "4 operations calculator"})
(def cheap-smartphone (model/new-product "Cheap smartphone", "/cheap-smartphone", 0.1M))

(clojure.pprint/pprint @(d/transact conn [computer, smartphone, calculator, cheap-smartphone]))

;; We already have a product with this :product/id so the query will update/overwrite the data
(def cheap-smartphone-2 (model/new-product (:product/id cheap-smartphone) "smartphone cheapppp!!!", "cheap-smartphone", 0.0001M))

(clojure.pprint/pprint @(d/transact conn [cheap-smartphone-2]))

(def products (db/get-all-products (d/db conn)))
(clojure.pprint/pprint products)