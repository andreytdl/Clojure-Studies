(defproject shouter "0.0.2"
  :description "User app"
  :url ""
  :min-lein-version "2.0.0"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [compojure "1.6.1"]
                 [cheshire "5.10.0"]
                 [camel-snake-kebab "0.4.2"]
                 [clj-http "3.10.0"]
                 [ring/ring-core "1.8.0"]
                 [ring/ring-json "0.5.0"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-jetty-adapter "1.8.0"]
                 [org.eclipse.jetty/jetty-server "9.4.28.v20200408"]
])
