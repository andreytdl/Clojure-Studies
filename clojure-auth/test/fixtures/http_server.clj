(ns fixtures.http-server
  (:require [clojure.test :refer :all]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [camel-snake-kebab.core :as csk]
            [ring.middleware.json :as json-middleware]))

(defn test-server [routes]
  (-> routes
      (wrap-defaults api-defaults)
      (json-middleware/wrap-json-body {:keywords? csk/->kebab-case-keyword})
      (json-middleware/wrap-json-response {:key-fn csk/->camelCaseString})))

(def server-addr "http://127.0.0.1:17171")

(defn server-fixture [routes reset]
  (fn [f]
    (let [srv (jetty/run-jetty (test-server routes) {:host  "127.0.0.1" :port 17171
                                                     :join? false :daemon? true})]
      (f)
      (.stop srv)
      (when reset (reset)))))
