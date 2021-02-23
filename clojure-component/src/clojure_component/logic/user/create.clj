(ns clojure-component.logic.user.create
  
 (:require [clojure-component.db.user :as db-user])
 (:import (clojure.lang ExceptionInfo)))



(defn- persist-db [attrs]
  (let [{:keys [components body]} attrs
        {:keys [storage]} components
        db-model (select-keys body [:name :message])]
    
    (db-user/transact! storage db-model)
    (dissoc attrs :components)))

(defn operation
  [attrs]

  (try
    (let [{:keys [body]} attrs
          new-user (assoc body :status :user.status/CREATED)]
      (-> (assoc attrs :result :created :new-user new-user)
          ;; (verify-user-exists) Maybe
          (persist-db)))

    (catch ExceptionInfo ex
      {:status 400 :body (ex-data ex)})))

