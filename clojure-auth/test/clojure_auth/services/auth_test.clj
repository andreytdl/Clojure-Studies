(ns clojure-auth.services.auth-test
  (:require [clojure.test :refer :all]
            [clojure-auth.core :refer :all]
            [clojure-auth.services.auth :as auth]))

(deftest auth
  (testing "Case we are sending email and password"
    (let [login "andrey.torres@dieselbank.com.br"
          password "123456"
          expected_response {:status 200 :body "Tudo certo"}]

      (is (= (auth/login {:login login :password password}) expected_response))))

  (testing "Case we are not sending email and password"
    (let [login "andrey.torres@dieselbank.com.br"
          expected_response {:status 400 :body {:cause "Missing fields: password"}}]

      (is (= (auth/login {:login login}) expected_response)))))