(ns ecommerce.aula1
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
;; #datom [entity-id attribute value                    transaction-id added?]
;; #datom [72        10        :product/name            13194139534312 true  ]
;; #datom [72        40        23                       13194139534312 true  ]
;; #datom [72        41        35                       13194139534312 true  ]
;; #datom [72        62        "Product's name"         13194139534312 true  ]
;; #datom [73        10        :product/slug            13194139534312 true  ]
;; #datom [73        40        23                       13194139534312 true  ]
;; #datom [73        41        35                       13194139534312 true  ] 
;; #datom [73        62        "Product's http address" 13194139534312 true  ]
;; 
;;;;;;;;;; QUESTIONS AND ANSWERS ;;;;;;;;;;;;
;;1) There is a way to transact an item but with only part of its attributes?
;;R) Yes, you can transact if the schema has no restrictions
;;
;;2) Datomic accepts nil values as attribute? For example: Name = nil
;;R) No, if you wants to set any attribute as nil, you can just don't set the
;;   attribute (like the question 1) 
;;
;;3) Are the datomic's queries async?
;;R) Yes, if you want your code to wait them to end, you can add an @ or deref,
;;   for example: 
;; (let [calculator {:product/name "calculator com 4 operações"}]
;;   @(d/transact conn [calculator]))
;;  or
;; (let [calculator {:product/name "calculator com 4 operações"}]
;;   (deref(d/transact conn [calculator])))
;;
;; The deref or @ expression is derreference the result, so it is waiting 
;; the process to end to get its value.
;;
;;4) Is there a way for remove attributes from a item
;;R) Yes, search in this file for :db/retract for examples
;;
;;5) Is there a way for update an item's attribute?
;;R) Yes, search in this file for :db/add for examples
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
;;;;;;;;;;;;   CREATING THE SCHEMA  ;;;;;;;;;;;;
;; ;; The schema will not be created twice, because datomic save the datoms
;; ;; and knows that it don't need to be created once again
;; 
;; (db/create-schema conn)
;; 
;;;;;;;;;;;;       QUERIES       ;;;;;;;;;;;;
;; ;; CREATING A READONLY DATABASE
;; (def db (d/db conn))
; (def db (d/db conn))
;; ;; Searching on database an entity that has the attribute :product/name
;; ;; For this case, all itens will be retrieved since all of them have the 
;; ;; :product/name
; (d/q '[:find ?entidade
;        :where [?entidade :product/name]] db)
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;