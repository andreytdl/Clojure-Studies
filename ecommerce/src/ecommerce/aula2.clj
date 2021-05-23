(ns ecommerce.aula2
  (:require
   [ecommerce.db :as db]
   [datomic.api :as d]
   [ecommerce.model :as model]))

;; Use REPL
;; 
;; Requires: 
;; 
; (require '[ecommerce.db])
; (require '[ecommerce.model])
; (require '[ecommerce.db :as db])
; (require '[datomic.api :as d])
; (require '[ecommerce.model :as model])
;; 
;;
;;;;;;;;;;;;       DOCS       ;;;;;;;;;;;;;;;
;;
;;
;;;;;;;;;; QUESTIONS AND ANSWERS ;;;;;;;;;;;;
;;
;;
;;
;;;;;;;; TRANSACTING THE ITEM, BUT WITH JUST ONE ATTRIBUTE FILLED. ;;;;;;;;;;
;;              (No problem, Datomic allows that)
;;
;; (let [calculadora {:produto/nome "Calculadora com 4 operações"}]
;;   (d/transact conn [calculadora]))
;;
;; ;; UPDATING THE ITEM
;;(let [celular-barato (model/novo-produto "Celular Barato", "/celular-barato", 8888.10M
;; resultado @(d/transact conn [celular-barato]) ;; Using deref
;; id-entidade (first (vals (:tempids resultado)))] ;; Without deref vals should be nil
;; (pprint resultado)
;; (d/transact conn [[:db/add id-entidade :produto/preco 0.1M]])
;; (pprint))
;;
;; ;; REMOVING AN ATTRIBUTE
;; (pprint @(d/transact conn [[:db/retract id-entidade :produto/slug "/celular-barato"]])
;; 
;;;;;;;;;;;; TRANSACT MANY ITEMS ;;;;;;;;;;;;   
;
; (def conn (db/open-connection))
; (d/transact conn db/schema)
; (def computer1 (model/new-product "New Computer1", "/new_computer1", 2500.10M))
; (def computer2 (model/new-product "New Computer2", "/new_computer2", 2500.20M))
; (d/transact conn [computer1 computer2])
; 
;;;;;;;;;;;;   CRIANDO O SCHEMA  ;;;;;;;;;;;;
;; ;; The schema will not be created twice, because datomic save the datoms
;; ;; and knows that it don't need to be created once again
;; 
;; (db/create-schema conn)
;; 
;;;;;;;;;;;;       QUERIES       ;;;;;;;;;;;;
;;
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;








