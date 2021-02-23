(ns clojure-auth.core
  (:require [compojure.route :as route]
            [clojure-auth.controllers.users :as users]
            [camel-snake-kebab.core :as csk]
            [compojure.core :refer (defroutes)]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.json :as json-middleware])
  (:gen-class))

(defroutes routes
  users/routes
  (route/resources "/"))

(def application (-> routes (json-middleware/wrap-json-body {:keywords? csk/->kebab-case-keyword})
                     (json-middleware/wrap-json-response {:key-fn csk/->camelCaseString})))

(defn start [port]
  (jetty/run-jetty application {:port port
                          :join? false}))

(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (start port)))