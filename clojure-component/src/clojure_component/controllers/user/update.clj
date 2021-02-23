(ns clojure-component.controllers.user.update
  (:require
   [clojure-component.logic.user.update :as logic-user-update]))

(def ^:private update-user-attributes
  {:components [:storage]})


(defn operation
  [body components]
  (let [attrs {:components (select-keys components  (:components update-user-attributes))
               :name (:name body)
               :message (:message body)}
        result (logic-user-update/operation attrs)]
    {:body (:updated-user result)
     :status 200}))