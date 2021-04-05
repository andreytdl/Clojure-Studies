(defproject clojure-component "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.stuartsierra/component "1.0.0"]
                 [com.walmartlabs/dyn-edn "0.2.0"]
                 [ring/ring-jetty-adapter "1.7.0-RC1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring-cors "0.1.13"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-core "1.7.0-RC1"]
                 [org.eclipse.jetty/jetty-server "9.4.28.v20200408"]
                 [org.eclipse.jetty/jetty-client "9.4.28.v20200408"]
                 [datomic-client-memdb "1.0.1"
                  :exclusions [org.slf4j/slf4j-nop
                               org.slf4j/slf4j-log4j12]]
                 [camel-snake-kebab "0.4.2"]
                 [compojure "1.6.1"]
                 [prismatic/schema "1.1.12"]
                 [clj-http "3.10.0"]
                 ]
  :repl-options {:init-ns clojure-component.server}
  :main ^{:skip-aot true} clojure-component.server)
