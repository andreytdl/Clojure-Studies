(ns clojure-component.http.users
  (:require
   [clojure-component.controllers.user.update :as ctrl-user-update]
   [clojure-component.controllers.user.get :as ctrl-user-get]
   [clojure-component.controllers.user.create :as ctrl-user-create]))

(defn update-user [request services]
  (let [{:keys [body]} request
        response (ctrl-user-update/operation body services)]
    response))

(defn create-user [request services]
  (println "Opa, tamo ai")
  (println request)
  (println services)
  
  (let [{:keys [body]} request
        response (ctrl-user-create/operation body services)]
    response))

(defn get-user [request services]
  (let [{:keys [body]} request
        response (ctrl-user-get/operation body services)]
    response))