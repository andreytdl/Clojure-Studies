(ns ecommerce.db
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/hello")
(defn open-connection []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn delete-database! []
  (d/delete-database db-uri))

(def schema [{:db/doc              "Product's identifier"
              :db/ident            :product/id
              :db/valueType        :db.type/uuid
              :db/cardinality      :db.cardinality/one
              :db/unique           :db.unique/identity}
             {:db/doc              "Product's name"
              :db/ident            :product/name
              :db/valueType        :db.type/string
              :db/cardinality      :db.cardinality/one}
             {:db/doc              "Product's url"
              :db/ident            :product/slug
              :db/valueType        :db.type/string
              :db/cardinality      :db.cardinality/one}
             {:db/doc              "Product's price"
              :db/ident            :product/price
              :db/valueType        :db.type/bigdec
              :db/cardinality      :db.cardinality/one}
             {:db/doc              "Product's keyword"
              :db/ident            :product/keyword
              :db/valueType        :db.type/string
              :db/cardinality      :db.cardinality/many}
             {:db/doc              "Product's category"
              :db/ident            :product/category
              :db/valueType        :db.type/ref
              :db/cardinality      :db.cardinality/one}

             ;;category
             {:db/ident         :category/name
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one}
             {:db/ident         :category/id
              :db/valueType     :db.type/uuid
              :db/cardinality   :db.cardinality/one
              :db/unique        :db.unique/identity}])

(defn create-schema! [conn]
  (d/transact conn schema))

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

(defn get-all-products-by-price-greater-than
  "What is happening?
   args: 
    db: Database's snapshot
    minimum-price-requested: price that will be comparated with others
   
   content: 
     :find ?name, ?price -> Retrieve the name and price
     :in -> Received parameters (db and slug)
        $ -> Symbol default for Database's snapshot
        ?minimum-price -> Minimun price (received as parameter)
     :where -> Conditions
       [?product :product/price ?price] -> Where the entity have the attribute
         :product/price (?price is binding this attribute)
       [(> ?price ?minimum-price)] -> Where the ?price (binded before) is 
        greater than the ?minimum-price (passend in :in clause)
       [?product :product/name ?name] -> Where the product have the attribute 
        product/name (?name is binding this attribute)
   Note: 
     The datomic :where clause is executed sequencially, so we put as first the
     restrictions that will eliminate more datoms as possible for performance 
     reasons"
  [db minimum-price-requested]
  (d/q '[:find ?name, ?price
         :in $, ?minimum-price
         :keys product/name, product/price
         :where [?product :product/price ?price]
         [(> ?price ?minimum-price)]
         [?product :product/name ?name]]
       db, minimum-price-requested))

(defn get-product-by-db-id
  "What is happening? 
   
   args:
    db: Database's snapshot
    product-id: product's db-id
   
   content:
   (d/pull): datomic's method used to retrieve some attributes from a determined
   unique attribute
   '[*]: All attributes
   product-id: the target datom
   
   "
  [db product-id]
  (d/pull db '[*] product-id))

(defn get-product-by-id
  "What is happening? 
   
   args:
    db: Database's snapshot
    product-id: product's id
   
   content:
   (d/pull): datomic's method used to retrieve some attributes from a determined
   unique attribute
   '[*]: All attributes
   [:product/id product-id]: the product with the property id equals to 
    product-id received by parameter
   
   Note: The pull method uses to retrieve items by db-id because it is unique.
    So you can use the :product/id property, because it is also unique.
    This Datomic's property is called 'lookup refs'
   
   "
  [db product-id]
  (d/pull db '[*] [:product/id product-id]))

(defn get-product-price-and-slug-by-id
  "What is happening? 
   
   args:
    db: Database's snapshot
    product-id: product's id
   
   content:
   (d/pull): datomic's method used to retrieve some attributes from a determined
     unique attribute
   [:product/price :product/slug]: product's price and slug
   [:product/id product-id]: the product with the property id equals to 
     product-id received by parameter
   
   Note: The pull method uses to retrieve items by db-id because it is unique.
    So you can use the :product/id property, because it is also unique.
    This Datomic's property is called 'lookup refs'
   
   "
  [db product-id]
  (d/pull db '[:product/price :product/slug] [:product/id product-id]))

(defn get-all-categories
  "What is happening?
   args: 
    db: Database's snapshot
   
   content: 
     :find (pull ?entity [*]) -> Retrieve all the attributes from the entity 
      id '?entity'
     :where -> Conditions
       ?entity :category/id -> Where the entity have the attribute
       :category/id"
  [db]
  (d/q '[:find (pull ?entity [*])
         :where [?entity :category/id]]
       db))

(defn set-category-to-products!
  "
   (Not tested)
   Transacts the given category as an attribute in all the given products
   Args:
     conn: datomic's connection
     products: a list of products
     category: a category
   "
  [conn products category]
  (let [itens
        (reduce (fn [db-adds product] (conj db-adds [:db/add
                                                     [:product/id (:product/id product)]
                                                     :product/category
                                                     [:category/id (:category/id category)]]))
                []
                [products])]
    (d/transact conn itens)))

(defn add-products!
  "
   Given a list of products, transacts all of them
    Note:
       This method is identical to the 'add-categories!'. they are not a generic
       'add' due the contracts that will be added soon!
    Args: 
       conn: Datomic's connection
       products: List of products
   "
  [conn products]
  (d/transact conn products))

(defn add-categories!
  "
   Given a list of categories, transacts all of them
    Note:
       This method is identical to the 'add-products!'. they are not a generic
       'add' due the contracts that will be added soon!
    Args: 
       conn: Datomic's connection
       categories: List of categories
   "
  [conn categories]
  (d/transact conn categories))

(defn get-all-products-name-and-its-category-name
  "Retrieves the product's name and its category for all products
    Args:
      conn: Datomic connection
    Note:
      this is a kind of SQL implicit Join.
   "
  [conn]
  (d/q (quote [:find ?product ?category-name
               :keys product category
               :where [?product product/name ?name]
               [?product :product/category ?category]
               [?category :category/name ?category-name]]) conn))

(defn get-products-by-category
  "
   Retrieves all products that owns the given category using forward navigation
   technique.
   Content: 
   (pull ?product [:product/name :product/slug {:product/category [*]}])
      - pull the ?product's attributes: :product/name, :product/slug and 
        :product/category. From this attribute called :product/category, 
        pull all attributes. It is called forward navigation, because 
        first the product is found and then its category is retrieved.
        In this case, 3 queries are performed, so, in this example,it is not 
        performatic.
   Returns:
     product's name, slug and all its category attributes.
   "
  [db category-name]
  (d/q '[:find (pull ?product [:product/name :product/slug {:product/category [*]}])
         :in $ ?name
         :where [?category :category/name ?name]
         [?product   :product/category ?category]]
       db category-name))

(defn get-products-by-category-backward-navigation
  "
   Retrieves all products that owns the given category using backward navigation
   technique.
   Content: 
   (pull ?category [:category/name {:product/_category
                                                [:product/name :product/slug]}])
      - pull the ?product's attributes: :product/name, :product/slug and 
        :product/_category. From this category, retrieves its name. It is called
        backward navigation, because first the category is found and then the 
        database tries to find the products that owns it. In this case, one 
        query is performed. For this purpose (Find all products that owns the 
        given category), it is performatic. 
   Returns:
     product's name, slug and its category name.
   Note:
     The underscore (:product/_category) means that we need the product that 
     owns that category. That is the backward navigation.
   "
  [db category-name]
  (d/q '[:find (pull ?category [:category/name {:product/_category
                                                [:product/name :product/slug]}])
         :in $ ?name
         :where [?category :category/name ?name]]
       db category-name))

(defn get-min-and-max-products-price
  "
   Retrieves the min and the max product's price. This is a aggregation query 
   example.
   "
  [db]
  (d/q '[:find (min ?price) (max ?price)
         :where [_ :product/price ?price]]
       db))

(defn get-products-data-resume
  "
   Retrieves the min, max and the product's amount. This is a aggregation 
   query example.
   
   WARNING:
     :with clause - The datomic always see the query result as a set of data. So
     if the products' price are R$2, R$5 and R$2 it will returns [2, 5] as 
     prices due the 2 is being retrieved repeatedly, thus the count in the :find
     clause will return 2. To solve this issue, the :with clause specifies that
     the set must contain one other field with the price. In the example below, 
     the Datomic is retrieving the results [product-eid, price], so the final 
     result will be [[product-1-id, 2], [product-2-id, 5], [product-3-id, 2]] 
     and the final count will be 3.
   "
  [db]
  (d/q '[:find (min ?price) (max ?price) (count ?price)
         :keys min max total
         :with ?product
         :where [?product :product/price ?price]]
       db))

(defn get-products-data-resume-group-by-category
  "
   Retrieves the min, max, product's amount, the total product's price and group
   by category.
   
   How the 'group by' works?
   - To group by anything is just add another paramether in the :find clause 
   that is not an aggregate method. In this case, we're grouping by the 
   category's name. 
  "
  [db]
  (d/q '[:find ?name (min ?price) (max ?price) (count ?price) (sum ?price)
         :keys category min max total price-total
         :with ?product
         :where [?product :product/price ?price]
         [?product :product/category ?category]
         [?category :category/name ?name]]
       db))