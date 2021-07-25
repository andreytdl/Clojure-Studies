(ns ecommerce.aula2
  (:require
   [ecommerce.db :as db]
   [datomic.api :as d]
   [ecommerce.model :as model]))

;; Use REPL
;; 
;; Requires: 
;; 
;; (require '[ecommerce.db])
;; (require '[ecommerce.model])
;; (require '[ecommerce.db :as db])
;; (require '[datomic.api :as d])
;; (require '[ecommerce.model :as model])
;; 
;;;;;;;; TRANSACTING THE ITEM, BUT WITH JUST ONE ATTRIBUTE FILLED. ;;;;;;;;;;
;;              (No problem, Datomic allows that)
;;
;; (let [calculator {:product/name "4 operations calculator"}]
;;   (d/transact conn [calculator]))
;;
;; ;; UPDATING THE ITEM
;; (let [cheap-smartphone (model/new-product "Cheap Smartphone", "/cheap-smartphone", 8888.10M
;; result @(d/transact conn [cheap-smartphone]) ;; Using deref
;; entity-id (first (vals (:tempids result)))] ;; Without deref vals should be nil
;; (pprint result)
;; (d/transact conn [[:db/add entity-id :product/price 0.1M]])
;; (pprint))
;;
;; ;; REMOVING AN ATTRIBUTE
;; (pprint @(d/transact conn [[:db/retract entity-id :product/slug "/cheap-smartphone"]])
;; 
;;;;;;;;;;;; TRANSACT MANY ITEMS ;;;;;;;;;;;;   
;
; (def conn (db/open-connection))
; (d/transact conn db/schema)
; (def computer1 (model/new-product "New Computer1", "/new_computer1", 2500.10M))
; (def computer2 (model/new-product "New Computer2", "/new_computer2", 2500.20M))
; (d/transact conn [computer1 computer2])
; 
;;;;;;;;;;;;   CREATING THE SCHEMA  ;;;;;;;;;;;;
;; ;; The schema will not be created twice, because datomic save the datoms
;; ;; and knows that it don't need to be created once again
;; 
;; (db/create-schema! conn)
;; 
