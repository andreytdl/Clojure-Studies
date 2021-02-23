(ns clojure-auth.controllers.users
  (:require [compojure.core :refer [defroutes GET POST]]
            [clojure-auth.services.auth :as auth]))

(defroutes routes
  (POST "/" {{login :login password :password} :body} (auth/login {:login login :password password}))
  (GET "/" [] {:status 200 :body "ping: pong"} :headers {"content-type" "text/plain"}))