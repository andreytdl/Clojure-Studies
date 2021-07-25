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
;;;;;;;;;;;;   CREATING THE SCHEMA  ;;;;;;;;;;;;
;; ;; The schema will not be created twice, because datomic save the datoms
;; ;; and knows that it don't need to be created once again
;; 
;; (db/create-schema! conn)]
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
;; Find: Must have all attribute bound in the where clause
;; Pull: Don't need to have all attribute pulled. It will retrieve even if it is
;; incomplete
;;
;; ;; Find
;; (defn get-all-products-by-price [db]
;;   (d/q '[:find ?name, ?price
;;          :where [?product :product/price ?price]
;;          [?product :product/name ?name]] db))
;;
;; ;; Response
;; #{["New Computer1" 2500.10M] ["New Computer2" 2500.20M]}
;;
;; ;; Find with keys
;; (defn get-all-products-by-price [db]
;;   (d/q '[:find ?name, ?price
;;          :keys name, price
;;          :where [?product :product/price ?price]
;;          [?product :product/name ?name]] db))
;;
;; ;; Response
;; [{:name "New Computer1", :price 2500.10M}
;;  {:name "New Computer2", :price 2500.20M}]
;;
;;
;; ;; Find with aninhed keys
;; (defn get-all-products-by-price [db]
;;   (d/q '[:find ?name, ?price
;;          :keys product/name, product/price
;;          :where [?product :product/price ?price]
;;          [?product :product/name ?name]] db))
;;
;; ;; Response
;; [#:product {:name "New Computer1", :price 2500.10M}
;;  #:product {:name "New Computer2", :price 2500.20M}]
;;
;; ;; Pull selected attributes
;; (defn get-all-products [db]
;;     (d/q '[:find (pull ?entity [:product/name :product/price :product/slug])
;;         :where ?entidade :product/name] db))
;;
;; It continues retrieving the products even it don't own "slug" attribute
;; It brings what it can bring 
;; ;; Response
;; [#:product {:name "New Computer1", :price 2500.10M}
;;  #:product {:name "New Computer2", :price 2500.20M}]
;;
;; ;; Pull All attributes ;;Lazier and not performatic since it retrieves all 
;;    attributes
;; (defn get-all-products [db]
;; (d/q '[:find (pull ?entity [*])
;;     :where ?entidade :product/name]] db))
;;
;; ;; Response
;; [#:product {:name "New Computer1", :price 2500.10M}
;;  #:product {:name "New Computer2", :price 2500.20M}]
;;
