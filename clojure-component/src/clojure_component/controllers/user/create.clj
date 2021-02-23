(ns clojure-component.controllers.user.create
  (:require
   [clojure-component.logic.user.create :as logic-user-create]))

(def ^:private create-user-attributes
  {:components [:storage]})


(defn operation
  [body components]
  (let [attrs {:components (select-keys components  (:components create-user-attributes))
               :body  body}
        result (logic-user-create/operation attrs)] result))
