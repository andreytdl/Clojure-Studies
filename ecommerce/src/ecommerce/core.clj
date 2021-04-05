(ns ecommerce.core
  (:require
   [ecommerce.db :as db]
   [datomic.api :as d]
   [ecommerce.model :as model]))

;; Use REPL
;; 
;;;;;;;;;;;; TRANSACT AN ITEM ;;;;;;;;;;;;;;;   
;;
;;  (def conn (db/open-connection))
;;  (d/transact conn db/schema)
;;  (def computer (model/new-product "New Computer", "/new_computer", 2500.10M))
;;  (d/transact conn [computer]);;     
;; 
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  
;; 
;; 
;;        
