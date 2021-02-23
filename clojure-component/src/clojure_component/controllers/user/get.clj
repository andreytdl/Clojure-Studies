(ns clojure-component.controllers.user.get
  (:require
   [clojure-component.logic.user.get :as logic-user-get]))

(def ^:private get-user-attributes
  {:components [:storage]})

(defn operation
  [body components]
  (let [attrs {:components (select-keys components  (:components get-user-attributes))
               :body  body}
        result (logic-user-get/operation attrs)] result

       (cond
         (= :retrieved (:result result))
         (let [{:keys [users]}  result]
           {:status 200
            :body users})
         
         :else
         {:status 500})))