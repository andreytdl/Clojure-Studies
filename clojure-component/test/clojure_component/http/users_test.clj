(ns clojure-component.http.users-test
  (:require [clojure.test :refer :all]
            [clojure-component.fixtures.logic :as fixtures-logic]
            [clj-http.client :as client]))

(def state (atom {}))

(defn user-handler [headers body]
  (swap! state #(assoc %
                       :num-of-reqs (+ 1 (get % :num-of-reqs 0)) ; keep the number of requests made
                       :user {:headers headers :body body}))    ; save the name information used
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    {:access-token  "access-token"
             :refresh-token "refresh-token"
             :expires-in    1440}})

(defn reset-state [] (reset! state {}))

(use-fixtures :each fixtures-logic/test-fixture)

(deftest ping-pong
  (testing "It just check if the server is on"
    (let [response (client/get "http://127.0.0.1:8080/"
                               {:headers {}})]
      (is (= 200 (:status response))))))

(deftest get-users
  (testing "It should get a user"
    (let [response (client/get "http://127.0.0.1:8080/users"
                               {:headers {}})]
      (is (= 200 (:status response))))))

(deftest update-user
  (testing "Should successfully update a user"
    (let [response (client/put "http://127.0.0.1:8080/users"
                               {:form-params {:name "Andrey" :message "Hello World!"}
                                :content-type :json} {})]
      (is (= 200 (:status response))))))

(deftest create-user
  (testing "Should successfully create a user"
    (let [response (client/post "http://127.0.0.1:8080/users"
                                {:form-params {:name "Andre" :message "Hello Worlda"}
                                 :content-type :json} {})
          _ (println response)]
      (is (= 200 (:status response))))))


