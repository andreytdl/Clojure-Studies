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
;;;;;;;;;;;;   CRIANDO O SCHEMA  ;;;;;;;;;;;;
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
;; (db/cria-schema conn)
;;
;; (let [computador (model/novo-produto "Computador Novo", "/computador-novo", 2500)
;;       celular (model/novo-produto "Celular Caro", "/celular", 8888888.10M)]
;;   (d/transact conn [computador, celular]))
;;
;;   (def fotografia-no-passado (d/db conn))
;;
;; (let [calculadora {produto/nome "Calculadora com 4 operações"}
;;       celular-barato (model/novo-produto "Celular Barato",0.1M)] "/celular-barato"
;;      (d/transact conn [calculadora, celular-barato])
;;
;; Retriving by database's snapshot -----
;;
;; Database's snapshot when d/db = 4
;; (pprint (count (db/todos-os-produtos (d/db conn)))
;;
;; Database's snapshot when d/db = 2
;;         pprint (count (db/todos-os-produtos fotografia-no-passado)))
;;
;; Retrieving by instant -----------------
;;
;;  ;; before
;;  (pprint/pprint (count (db/todos-os-produtos (db/as-of) #inst "2019-09-18T17:34:34.200")))
;;  Results: 0
;;
;;  ;; after the first transactions and before the last
;;  (pprint/pprint (count (db/todos-os-produtos (db/as-of) #inst "2019-09-18T17:35:34.200")))
;;  Results: 2
;;
;;  ;; after all
;;  (pprint/pprint (count (db/todos-os-produtos (db/as-of) #inst "2019-09-18T17:36:34.200"))))
;;  Results 4
;;
;;
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;