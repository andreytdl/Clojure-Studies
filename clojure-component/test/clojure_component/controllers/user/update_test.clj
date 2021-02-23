
(ns clojure-component.controllers.user.update-test
  (:require
   [clojure-component.controllers.user.update :as ctrl-user-update]
   [clojure.test :refer :all]
   [clojure-component.fixtures.logic :as fixtures-logic]
   [clojure-component.components.system-utils :as system-utils]))

(use-fixtures :each fixtures-logic/test-fixture)

(deftest controller-test
  (testing "Should be able to update"
    (let [body {:name "Andrey"
                :message "Hello World!"}

          storage (system-utils/get-component! :storage)

          services {:storage storage}

          expected_response "Andrey"

          ;;Updating the user
          response (ctrl-user-update/operation body services)]



      (is (= expected_response (:name (:body response)))))))