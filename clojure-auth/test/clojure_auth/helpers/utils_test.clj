(ns clojure-auth.helpers.utils-test
  (:require [clojure.test :refer :all]
            [clojure-auth.core :refer :all]
            [clojure-auth.helpers.utils :as utils]))

(deftest utils
  (testing "Case we are sending email and password"
    (let [login "andrey.torres@dieselbank.com.br"
          password "123456"
          expected_response true]

      (is (= (utils/validate-request-params {:login login :password password}) expected_response))))

  (testing "Case we are not sending email and password"
    (let [login "andrey.torres@dieselbank.com.br"
          expected_response #"Missing required fields"]

      (is (thrown-with-msg? Exception expected_response (utils/validate-request-params {:login login :password nil}))))))