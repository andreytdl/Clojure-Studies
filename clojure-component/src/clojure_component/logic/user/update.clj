(ns clojure-component.logic.user.update
  (:require [clojure-component.db.user :as db-user]))

(defn- persist-db [attrs]
  (let [{:keys [name message], {:keys [storage]} :components} attrs
        user   (db-user/get-by-name name storage)
        id     (-> user (ffirst) :db/id)
        updated-user {:name name
                      :message message
                      :uid (str id)}]
    (db-user/transact! storage updated-user)
    (-> (assoc attrs :updated-user updated-user)
        (dissoc :components))))


;; Update user
(defn operation [attrs]
  (try
    (-> (assoc attrs :result :updated)
        ;;(verify-user-exists) Maybe
        (persist-db))

    (catch clojure.lang.ExceptionInfo err
      ;(log/error {::type :finish :error err})
      (println err)
      (assoc (ex-data err) :error (ex-message err)))

    (catch Exception err
      ;(log/error {::type :finish :error err})
      (println err)
      (assoc {} :error (ex-message err) :result :internal-server-error))))