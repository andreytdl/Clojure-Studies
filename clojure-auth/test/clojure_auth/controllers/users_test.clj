(ns clojure-auth.controllers.users-test
  (:require [clojure.test :refer :all]
            [clojure-auth.core :refer :all]
            [clojure-auth.controllers.users :as users]
            [clojure-auth.services.auth :as auth]
            [compojure.core :refer [defroutes POST GET]]
            [fixtures.http-server :as http-server]
            [clj-http.client :as client]))

(def state (atom {}))

(defn user-handler [headers body]
  (swap! state #(assoc %
                       :num-of-reqs (+ 1 (get % :num-of-reqs 0)) ; keep the number of requests made
                       :login {:headers headers :body body}))    ; save the login information used
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    {:access-token  "access-token"
             :refresh-token "refresh-token"
             :expires-in    1440}})

(defn reset-state [] (reset! state {}))

(defroutes
  test-routes
  (POST "/" {{login :login password :password} :body} (auth/login {:login login :password password}))
  (GET "/" [] {:status 200 :body "ping: pong"} :headers {"content-type" "text/plain"}))
(use-fixtures :each (http-server/server-fixture test-routes reset-state))

(deftest login-success
  (testing "Should successfully authenticate user"
    (let [response (client/post "http://127.0.0.1:17171/"
                                {:form-params {:login "andrey.torres@dieselbank.com.br" :password "123456"}
                                 :content-type :json} {})
          _ (println response)]
      (is (= 200 (:status response))))))

(deftest ping-pong
  (testing "It just check if the server is on"
    (let [response (client/get "http://127.0.0.1:17171/"
                                {:headers {}})
          _ (println response)]
      (is (= 200 (:status response))))))


