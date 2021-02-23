
(ns clojure-component.logic.user.update-test
  (:require
   [clojure-component.logic.user.update :as logic-user-update]
   [clojure.test :refer :all]
   [clojure-component.fixtures.logic :as fixtures-logic]
   [clojure-component.components.system-utils :as system-utils]))

(use-fixtures :each fixtures-logic/test-fixture)

(def ^:private update-user-attributes
  {:components [:storage]})

(deftest controller-test
  (testing "Should be able to update"
    (let [body {:name "Andrey"
                :message "Hello World!"}

          storage (system-utils/get-component! :storage)

          components {:storage storage}
          
          attrs {:components (select-keys components  (:components update-user-attributes))
                 :name (:name body)
                 :message (:message body)}

          expected_response :updated

          ;;Updating the user
          response (logic-user-update/operation attrs)]

      (is (= expected_response (:result response))))))