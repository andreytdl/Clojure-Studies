(ns ecommerce.aula6
  (:require
   [ecommerce.db :as db]
   [datomic.api :as d]
   [ecommerce.model :as model]
   [clojure.pprint :as pprint]))

;; Use REPL
;; 
;; Requires: 
;; 
;; (require '[ecommerce.db])
;; (require '[ecommerce.model])
;; (require '[ecommerce.db :as db])
;; (require '[datomic.api :as d])
;; (require '[ecommerce.model :as model])
;; (require '[clojure.pprint :as pprint])
;; 
;;;;;;;;;;; Questions and Answers ;;;;;;;;;;;;;;;;
;;
;;1) What if i transact 4 items at the same time and the 4º fail?
;; R) When transacting 4 products at the same time, if it occurs 
;; some error at last, the whole transaction is canceled. This characteristic 
;; to do "or all or nothing" is called Atomicity: 
;; if a portion of the transaction fails, the whole transaction fails and no 
;; change is made in the BD.
;;
;;;;;;;;;;;;;;;;;;; TRANSACTING AND RETRIEVING ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; (def conn (db/open-connection))
;;
;; (db/create-schema conn)
;;
;; (let [computer (model/new-product "New computer", "/new_computer", 2500.10M)
;;       smartphone (model/new-product "Expensive smartphone", "/smartphone", 888888.10M)
;;       calc {:product/name "4 operation calc"}
;;       smartphone-barato (model/new-product "smartphone Barato", "/smartphone-barato", 0.1M)]
;;   (d/transact conn [computer, smartphone, calc, smartphone-barato]))
;;
;; ; It should retrieve 2
;;
;; (db/get-all-products-by-price-greater-than (d/db conn) 1000)))
;;
;; ; It should retrieve 1
;;
;; (db/get-all-products-by-price-greater-than (d/db conn) 5000)))
;;
;; (db/delete-database)
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;; CARDINALITY ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; ;; To handle many-to-many attributes, we use :db/add to add and :db/retract
;; ;; to remove the items, since it is an array attribute
;;
;; ;; Adding an item to :product/keyword attribute
;; (d/transact conn [[:db/add 17592186045418 
;;                    :product/keyword "black and white screen"]])
;; (d/transact conn [[:db/add 17592186045418 
;;                    :product/keyword "Acer"]])
;;
;; (pprint (db/todos-os-produtos (d/db conn)))
;;
;; ;; Removing an item from :product/keyword attribute
;; (d/transact conn [[:db/retract 17592186045418 
;;                    :product/keyword "black and white screen"]])
;;
;;(db/delete-database)
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



