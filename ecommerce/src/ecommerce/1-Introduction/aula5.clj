(ns ecommerce.aula5
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
;;;;;;;;;;;;  CREATING THE SCHEMA  ;;;;;;;;;;;;
;; ;; The schema will not be created twice, because datomic save the datoms
;; ;; and knows that it don't need to be created once again
;; 
;; (db/create-schema conn)]
;;
;;;;;;;;;;;;;; RETRIEVE OLD DATABASE'S SNAPSHOT ;;;;;;;;;;;;;;;   
;;
;; Setup -----------
;;
;; (def conn (db/open-connection))
;; (db/create-schema conn)
;;
;; (let [computer (model/new-product "New computer", "/new-computer", 2500)
;;       smartphone (model/new-product "Expensive smartphone", "/smatphone", 8888888.10M)]
;;   (d/transact conn [computer, smartphone]))
;;
;;   (def past-snapshot (d/db conn))
;;
;; (let [calculator {product/name "4 operations calculator"}
;;       cheap-smartphone (model/new-product "Cheap Smartphone",0.1M)] "/cheap-smartphone"
;;      (d/transact conn [calculator, cheap-smartphone])
;;
;; Retrieving by database's snapshot -----
;;
;; Database's snapshot when d/db = 4
;; (pprint (count (db/get-all-products (d/db conn)))
;;
;; Database's snapshot when d/db = 2
;; (pprint (count (db/get-all-products past-snapshot)))
;;
;; Retrieving by instant -----------------
;;
;;  ;; before
;;  (pprint/pprint (count (db/get-all-products (d/as-of (d/db conn) #inst "2019-09-18T17:34:34.200"))))
;;  Results: 0
;;
;;  ;; after the first transactions and before the last
;;  (pprint/pprint (count (db/get-all-products (d/as-of (d/db conn) #inst "2019-09-18T17:35:34.200"))))
;;  Results: 2
;;
;;  ;; after all
;;  (pprint/pprint (count (db/get-all-products (d/as-of (d/db conn) #inst "2019-09-18T17:36:34.200"))))
;;  Results 4
