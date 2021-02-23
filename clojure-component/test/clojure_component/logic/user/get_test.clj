
(ns clojure-component.logic.user.get-test
  (:require
   [clojure-component.logic.user.get :as logic-user-get]
   [clojure.test :refer :all]
   [clojure-component.fixtures.logic :as fixtures-logic]
   [clojure-component.components.system-utils :as system-utils]))

(use-fixtures :each fixtures-logic/test-fixture)

(def ^:private get-user-attributes
  {:components [:storage]})

(deftest controller-test
  (testing "Should be able to get"
    (let [
          storage (system-utils/get-component! :storage)
          components {:storage storage}
          body {}
          
          attrs {:components (select-keys components  (:components get-user-attributes))
                 :body  body}
          
          expected_response :retrieved

          response (logic-user-get/operation attrs)
          ]

      (is (= expected_response (:result response))))))