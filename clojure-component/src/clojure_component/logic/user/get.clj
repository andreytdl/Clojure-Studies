(ns clojure-component.logic.user.get

  (:require [clojure-component.db.user :as db-user])
  )

;; Get user
(defn operation [attrs]
  ;(log/info {::type :start :attrs attrs})
  
  (try
    (let [{:keys [components]} attrs
          {:keys [storage]} components
          users (db-user/get-all storage)]

      (assoc attrs
             :result :retrieved
             :users users))


    (catch clojure.lang.ExceptionInfo err
      ;(log/error {::type :finish :error err})
      (assoc (ex-data err) :error (ex-message err)))

    (catch Exception err
      ;(log/error {::type :finish :error err})
      (assoc {} :error (ex-message err) :result :internal-server-error))))