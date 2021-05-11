(ns ecommerce.core
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
;; WHEN CONNECTING TO A DB ------------------
;; (def conn (db/open-connection))
;; (d/transact conn db/schema)
;; 
;; Response:
;; #datom[13194139534324 50 #inst "2021-05-01T20:23:07.052-00:00" 13194139534324 true]
;;
;; Means:
;; At the instant "2021-05-01T20:23:07.052-00:00" the model 50 were transacted, creating
;; the snapshot 13194139534324. This is true because it is created. If it were false it
;; is removed
;;
;; DATONS -----------------------------------
;; #datom [id-da-entidade atributo valor id-da-tx added?]
;; #datom [72 10 :produto/nome 13194139534312 true]
;; #datom [72 40 23 13194139534312 true]
;; #datom [72 41 35 13194139534312 true]
;; #datom [72 62 "O nome de um produto" 13194139534312 true]
;; #datom [73 10 :produto/slug 13194139534312 true]
;; #datom [73 40 23 13194139534312 true]
;; #datom [73 41 35 13194139534312 true] 
;; #datom [73 62 "O caminho para acessar esse produto via http 13194139534312 true]
;; 
;;;;;;;
;;
;;
;;
;;
;;
;;;;;;;;;;;; TRANSACT AN ITEM ;;;;;;;;;;;;;;;   
;;
; (def conn (db/open-connection))
; (d/transact conn db/schema)
; (def computer (model/new-product "New Computer", "/new_computer", 2500.10M))
; (d/transact conn [computer])
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
;; ;;Criando o banco de leitura
;; (def db (d/db conn))
;; ;; Buscando uma entidade no banco que possua o atributo :produto/nome
;; ;; Nesse caso, todos os produtos serão devolvidos, pois todos possuem
;; ;; :produto/nome
;; (d/q '[:find ?entidade
;;        :where [?entidade :produto/nome]] db)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;