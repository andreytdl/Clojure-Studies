
(ns clojure-component.controllers.user.create-test
  (:require
   [clojure-component.controllers.user.create :as ctrl-user-create]
   [clojure.test :refer :all]
   [clojure-component.fixtures.logic :as fixtures-logic]
   [clojure-component.components.system-utils :as system-utils]))

(use-fixtures :each fixtures-logic/test-fixture)

(deftest controller-test
  (testing "Should be able to create"
    (let [body {:name "Andrey"
                :message "Hello World!"}

          storage (system-utils/get-component! :storage)

          services {:storage storage}

          expected_response :created
          
          response (ctrl-user-create/operation body services)]
      
      (is (= expected_response (:result response))))))