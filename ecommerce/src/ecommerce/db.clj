(ns ecommerce.db
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/hello")
(defn open-connection []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn delete-database []
  (d/delete-database db-uri))

(def schema [{:db/doc              "O nome de um produto"
              :db/ident            :product/name
              :db/valueType        :db.type/string
              :db/cardinality      :db.cardinality/one}
             {:db/doc              "O caminho para acessar esse produto via http"
              :db/ident            :product/slug
              :db/valueType        :db.type/string
              :db/cardinality      :db.cardinality/one}
             {:db/doc              "O preco de um produto com precisão monetária"
              :db/ident            :product/price
              :db/valueType        :db.type/bigdec
              :db/cardinality      :db.cardinality/one}])

(defn create-schema [conn]
  (d/transact conn schema))

;; db -> Database's snapshot
(defn get-all-products
  "What is happening?
   args: 
    db: Database's snapshot
   
   content: 
     :find ?e -> Retrieve the entity id
     :where -> Conditions
       ?entity :product/name -> Where the entity have the attribute
       :product/name"
  [db]
  (d/q '[:find ?e
         :where [?e :product/name]] db))

(defn get-all-products-by-slug
  "What is happening?
   args: 
    db: Database's snapshot
    slug: Slug that i want to search
   
   content: 
     :find ?entity -> Retrieve the entity id
     :in -> Received parameters (db and slug)
        $ -> Symbol default for Database's snapshot
        ?target-slug -> Slug received as parameter (Also can be hardcoded)
     :where -> Conditions
       ?entity :product/slug ?target-slug -> Where the entity have the attribute
       :product/slug equals to ?target-slug
   "
  [db slug]
  (d/q '[:find ?entity
         :in $ ?target-slug
         :where [?entity :product/slug ?target-slug]] db slug))

(defn get-all-slugs
  "What is happening?
   args: 
    db: Database's snapshot
   
   content: 
     :find ?slug -> Retrieve the slugs
     :where -> Conditions
       ?entity :product/slug ?slug -> Where the entity have the attribute
       :product/slug (?slug is binding this attribute)
   Note: 
     We are not using ?entity, so it can be _ (undercore)
   "
  [db]
  (d/q '[:find ?slug
         :where [?entity :product/slug ?slug]] db))

(defn get-all-products-with-price
  "What is happening?
   args: 
    db: Database's snapshot
   
   content: 
     :find ?slug -> Retrieve the slugs
     :where -> Conditions
       ?entity :product/slug ?slug -> Where the entity have the attribute
       :product/slug (?slug is binding this attribute)
           and
       ?entity :product/slug ?slug -> Where that same entity also have the 
       attribute :product/slug (?slug is binding this attribute)
   WARNING:
     Here even the query isn't returning the product id on :find clause, we 
   can't replace ?product with underscore.
   If it happens, datomic's query will search sequencially and get any product 
  with any name. So, receiving the products (x, y, z) and the prices (1, 2, 3)
   respectively
  it will return ([x, 1] [x, 2], [x, 3], [y, 1] ... [z, 3]) 
   To solve this issue, we use ?product, so given a ?product inside both :where 
   clause, it will return all ?product that contains :product/price and then, 
   return from this ?product all that have :product/name. The final list will 
   be  ([x, 1], [y, 2], [z, 3])
"
  [db]
  (d/q '[:find ?name, ?price
         :where [?product :product/price ?price]
         [?product :product/name ?name]] db))
