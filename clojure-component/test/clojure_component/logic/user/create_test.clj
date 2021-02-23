
(ns clojure-component.logic.user.create-test
  (:require
   [clojure-component.logic.user.create :as logic-user-create]
   [clojure.test :refer :all]
   [clojure-component.fixtures.logic :as fixtures-logic]
   [clojure-component.components.system-utils :as system-utils]))

(use-fixtures :each fixtures-logic/test-fixture)

(def ^:private create-user-attributes
  {:components [:storage]})

(deftest controller-test
  (testing "Should be able to create"
    (let [storage (system-utils/get-component! :storage)

          components {:storage storage}
          
          body {:name "Andrey"
                :message "Hello World!"}
          
          attrs {:components (select-keys components  (:components create-user-attributes))
                 :body  body}
          
          expected_response :created

          response (logic-user-create/operation attrs)]

      (is (= expected_response (:result response))))))