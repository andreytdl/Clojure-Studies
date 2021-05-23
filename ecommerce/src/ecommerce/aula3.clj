(ns ecommerce.aula2
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
;; (pprint/pprint (db/get-all-products (d/db conn)))
;;
;; (db/delete-database)
;;
;;;;;;;;;;;;;;;;;;;;;;;;; GET ALL SLUGS ;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; It is being explained on ecommerce.db file
;;
;;               (db/get-all-slugs (d/db conn))
;;
;;;;;;;;;;;;;;;;;; GET PRODUCTS WITH PRICE ;;;;;;;;;;;;;;;;;;;;;;;;
;; It is being explained on ecommerce.db file
;;
;;          (db/get-all-products-with-price (d/db conn))
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;






