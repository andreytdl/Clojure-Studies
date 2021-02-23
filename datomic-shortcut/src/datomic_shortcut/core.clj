(ns datomic-shortcut.core
  (:require [datomic.api :as d]))

;;Rodar todos esses comandos separadamente no REPL

;;Create the database and the connection.
(def db-uri "datomic:mem://foo")

(comment
  (d/create-database db-uri)
  (def conn (d/connect db-uri)))

;;Add the movie schema to the database.
(def movie-schema [{:db/ident :movie/title
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "The title of the movie"}

                   {:db/ident :movie/genre
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "The genre of the movie"}

                   {:db/ident :movie/release-year
                    :db/valueType :db.type/long
                    :db/cardinality :db.cardinality/one
                    :db/doc "The year the movie was released in theaters"}])

(comment
  @(d/transact conn movie-schema)
  ;; => {:db-before ...,
  ;;     ...}
  )

;;Add some movies
(def first-movies [{:movie/title "The Goonies"
                    :movie/genre "action/adventure"
                    :movie/release-year 1985}
                   {:movie/title "Commando"
                    :movie/genre "thriller/action"
                    :movie/release-year 1985}
                   {:movie/title "Repo Man"
                    :movie/genre "punk dystopia"
                    :movie/release-year 1984}])

(comment
  @(d/transact conn first-movies)
  ;; => {:db-before ...,
  ;;     ...}
  )

;;Query the titles back out
(def all-titles-q '[:find ?movie-title
                    :where
                    [_ :movie/title ?movie-title]])

;;Pull all
(def pull-all-q '[:find pull-all
                    :where
                    [_ :movie/title ?movie-title]])



(comment
  (d/q all-titles-q (d/db conn))
  ;; => #{["Commando"] ["The Goonies"] ["Repo Man"]}
  )