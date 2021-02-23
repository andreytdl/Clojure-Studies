(ns clojure-component.components.webserver
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.json :as json-middleware]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [camel-snake-kebab.core :as csk]
            [compojure.core :refer (defroutes PUT POST GET OPTIONS)]
            [compojure.route :as route]
            [clojure-component.http.users :as http-user])
  (:import (org.eclipse.jetty.server Server)))

(defn wrap-services-config [f services config]
  (fn [req]
    (f (assoc req :services services :config config))))

(defroutes app-routes
  ;; users/routes
  (PUT "/users" {services :services, :as request} (http-user/update-user request services))
  (POST "/users" {services :services, :as request} (http-user/create-user request services))
  (GET "/users" {services :services, :as request} {:status 200 :body (http-user/get-user request services) })
  
  (GET "/" [] {:status 200 :body {:message "ping: pong"} :headers {"content-type" "text/plain"}})
  (GET "/test" [] {:status 200 :body {:message "Aqui funciona"} :headers {"content-type" "text/plain"}})

  (OPTIONS "*" [] {:status  204
                   :headers {"access-control-allow-origin"      "*"
                             "access-control-allow-methods"     "GET, POST, OPTIONS"
                             "access-control-allow-headers"     "DNT,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range,X-Session-Id"
                             "access-control-expose-headers"    "Content-Length,Content-Range,X-Session-Id"
                             "access-control-allow-credentials" "true"}})
  (route/not-found "Not Found"))

(defn app [config services]
  (-> app-routes
      (wrap-services-config services config)
      (wrap-defaults api-defaults)
      (wrap-params)
      (wrap-cookies)
      (wrap-multipart-params)
      (json-middleware/wrap-json-body {:keywords? csk/->kebab-case-keyword})
      (json-middleware/wrap-json-response {:key-fn csk/->camelCaseString})))

(defrecord WebServer [;; static config - system
                      config
                      ;; dependencies
                      storage
                      ;; runtime objects
                      jetty]
  component/Lifecycle
  (start [this]
    (if jetty
      this
      (assoc this :jetty
             (jetty/run-jetty (app config {:storage storage})
                              (:server config)))))

  (stop [this]
    (when jetty
      (.stop ^Server jetty))
    (assoc this :jetty nil)))

(defn new-web-server [config]
  (map->WebServer {:config config}))
