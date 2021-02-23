(ns datomic-github-tutorial.core
  (:require [datomic.api :as d]))

;;Usar todos esses comandos no REPL
(comment
  ;; Criando o schema
  (def schema
    [{:db/doc "A users email."
      :db/ident :user/email
      :db/valueType :db.type/string
      :db/cardinality :db.cardinality/one
      :db.install/_attribute :db.part/db}])

  ;;Gerando a url e criando o banco
  (def db-url "datomic:mem://omn-dev")
  (d/create-database db-url)

  ;;Fazendo a transact do schema
  (d/transact (d/connect db-url) schema)

  ;;Também pode ser feita assim
  (-> db-url
      d/connect
      (d/transact schema))

  ;;Adicionando dados no arquivo
  (def test-data
    [{:user/email "fred.jones@gmail.com"}])

  ;;TAmbém pode ser feito assim 
  (-> db-url
      d/connect
      (d/transact test-data))

  ;;Blowing away the database and recreating it
  (d/delete-database db-url)
  (d/create-database db-url)


  ;;Using a better schema
  (def schema
    [{:db/doc "A users email."
      :db/ident :user/email
      :db/valueType :db.type/string
      :db/cardinality :db.cardinality/one
      :db.install/_attribute :db.part/db}

     {:db/doc "A users age."
      :db/ident :user/age
      :db/valueType :db.type/long
      :db/cardinality :db.cardinality/one
      :db.install/_attribute :db.part/db}])

  ;;Adding the new schema
  (d/transact (d/connect db-url) schema)


  ;;Creating the two datas
  (def test-data
    [{:user/email "sally.jones@gmail.com"
      :user/age 34}

     {:user/email "franklin.rosevelt@gmail.com"
      :user/age 14}])

  ;;Adding the two datas
  (d/transact (d/connect db-url) test-data)

  ;;Find the people
  ;; ?e = each -> it stores all founded items into variable "?e"
  (def users '[:find ?e
               :where [?e :user/email]])
  



  )