
(ns clojure-component.controllers.user.get-test
  (:require
   [clojure-component.controllers.user.get :as ctrl-user-get]
   [clojure.test :refer :all]
   [clojure-component.fixtures.logic :as fixtures-logic]
   [clojure-component.components.system-utils :as system-utils]))

(use-fixtures :each fixtures-logic/test-fixture)

(deftest controller-test
  (testing "Should be able to get"
    (let [body {}

          storage (system-utils/get-component! :storage)

          services {:storage storage}

          response (ctrl-user-get/operation body services)]

      (is (= 200 (:status response))))))