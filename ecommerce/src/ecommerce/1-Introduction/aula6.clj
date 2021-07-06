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
;;;;;;;;;;;;;;;;;;; TRANSACTING AND RETRIEVING ;;;;;;;;;;;;;;;;;;;;;;
;;
; (def conn (db/open-connection))

; (db/create-schema conn)

; (let [computer (model/new-product "New computer", "/new_computer", 2500.10M)
;       smartphone (model/new-product "Expensive smartphone", "/smartphone", 888888.10M)
;       calculator {:product/name "4 operation calculator"}
;       cheap-smartphone (model/new-product "Cheap smartphone", "/cheap-smartphone", 0.1M)]
;   (d/transact conn [computer, smartphone, calculator, cheap-smartphone]))
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
;; ;; Adding two items to :product/keyword attribute that is with cardinality 
;; ;; many
;;
;; (d/transact conn [[:db/add 17592186045418 
;;                    :product/keyword "black and white screen"]])
;; (d/transact conn [[:db/add 17592186045418 
;;                    :product/keyword "Acer"]])
;;
;; ;; 17592186045418 is the computer entity datomic id
;;
;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; (pprint (db/get-all-products (d/db conn)))
;;
;; ;; Removing an item from :product/keyword attribute
;; (d/transact conn [[:db/retract 17592186045418 
;;                    :product/keyword "black and white screen"]])
;;
;;(db/delete-database)
;;
