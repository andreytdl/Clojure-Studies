(ns clojure-tests.core-test
  (:require [clojure.test :refer :all]
            [clojure-tests.core :refer :all]
            [clojure-tests.andrey :as andrey]
            [fixtures.http-server :as http-server]
            [clj-http.client :as client]
            [compojure.core :refer [defroutes POST]]))


(deftest a-test
  (testing "i don't fail anymore"
    (is (not= 0 1)))

  (testing "I am okay"
    (is (= 1 1))))

;; Testing my own function ------------------

(deftest helloTest
  (testing "I test the hello function"

    (let [hello (andrey/hello)]


      (is (= hello "Hello, World!"))))


  (testing "I test the hello function and i don't fail anymore"

    (let [hello (andrey/hello)]


      (is (not= hello "Hello, Worlda!"))))


  (testing "I test the intusiastic function"

    (let [too-enthusiastic (andrey/too-enthusiastic "Andrey")
          expected_result "OH. MY. GOD! Andrey YOU ARE MOST DEFINITELY LIKE THE BEST MAN EVER I LOVE YOU AND WE SHOULD RUN AWAY SOMEWHERE"]
      (is (= too-enthusiastic expected_result))))




;; Error Cases --------------------
  (testing "I test the hello function. I fail but that is my purpose"

    (is (thrown? ArithmeticException (/ 1 0)))))


;; Http Cases ----------------------
(def state (atom {}))

(defn login-handler [headers body]
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
  (POST "/authentication/Login" {headers :headers body :body} (login-handler headers body)))

(use-fixtures :each (http-server/server-fixture test-routes reset-state))

(deftest login-success
  (testing "Should successfully authenticate user and return both user and 
            cookies"
    (let [response (client/post "http://127.0.0.1:17171/authentication/Login"
                                {} {})
          _ (println response)]
      (is (= 200 (:status response))))))













