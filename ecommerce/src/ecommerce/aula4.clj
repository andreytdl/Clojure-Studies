(ns ecommerce.aula4
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
;;;;;;;;;;;; TRANSACT MANY ITEMS ;;;;;;;;;;;;   
;;
;; (def conn (db/open-connection))
;; (d/transact conn db/schema)
;; (def computer1 (model/new-product "New Computer1", "/new_computer1", 2500.10M))
;; (def computer2 (model/new-product "New Computer2", "/new_computer2", 2500.20M))
;; (d/transact conn [computer1 computer2])
;;
;;;;;;;;;;;;;;;; DIFERENCIATING PULL AND FIND ;;;;;;;;;;;;;;;;;;;;;
;;
;; ;; Find
;; (defn todos-os-produtos-por-preco [db]
;;   (d/q '[:find ?nome, ?preco
;;          :where [?produto :produto/preco ?preco]
;;          [?produto :produto/nome ?nome]] db))
;;
;; ;; Response
;; #{["New Computer1" 2500.10M] ["New Computer2" 2500.20M]}
;;
;; ;; Find with keys
;; (defn todos-os-produtos-por-preco [db]
;;   (d/q '[:find ?nome, ?preco
;;          :keys nome, preco
;;          :where [?produto :produto/preco ?preco]
;;          [?produto :produto/nome ?nome]] db))
;;
;; ;; Response
;; [{:name "New Computer1", :price 2500.10M}
;;  {:name "New Computer2", :price 2500.20M}]
;;
;;
;; ;; Find with aninhed keys
;; (defn todos-os-produtos-por-preco [db]
;;   (d/q '[:find ?nome, ?preco
;;          :keys produto/nome, produto/preco
;;          :where [?produto :produto/preco ?preco]
;;          [?produto :produto/nome ?nome]] db))
;;
;; ;; Response
;; [#:product {:name "New Computer1", :price 2500.10M}
;;  #:product {:name "New Computer2", :price 2500.20M}]
;;
;; ;; Pull selected attributes
;; (defn todos-os-produtos [db]
;;     (d/q '[:find (pull ?etidade [:produto/nome :produto/preco :produto/slug])
;;         :where ?entidade :produto/nome] db))]
;;
;; It continues retrieving the products even it don't own "slug" attribute
;; It brings what it can bring 
;; ;; Response
;; [#:product {:name "New Computer1", :price 2500.10M}
;;  #:product {:name "New Computer2", :price 2500.20M}]
;;
;; ;; Pull All attributes ;;Lazier and not performatic since it retrieves all 
;;    attributes
;; (defn todos-os-produtos [db]
;; (d/q '[:find (pull ?etidade [:produto/nome :produto/preco :produto/slug])
;;     :where ?entidade :produto/nome]] db))
;;
;; ;; Response
;; [#:product {:name "New Computer1", :price 2500.10M}
;;  #:product {:name "New Computer2", :price 2500.20M}]
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;
;;