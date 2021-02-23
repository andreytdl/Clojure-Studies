(ns clojure-auth.services.auth
  
 (:require [clojure-auth.helpers.utils :as utils])
 (:import (clojure.lang ExceptionInfo)))


(defn login
  "Authenticates credentials and returns both the respective user and its
   http-cookies.
   
  Args:
    body: the HTTP request's body.
    services: the service map of components.
    
  Returns:
    map: A HTTP response with the authenticated user and its associated cookie,
     in case of success, or an appropriate status code and error.
   
     200: Authenticated, {:cookie {:access-token <token>}, body: {:user <user>}}
   
     400: Bad-Request, body:
   
       {:error 'Missing required fields: <fields> |
                The request is invalid and/or malformed |
                Invalid credentials |
                The user is currently in an action that has prevented the login|
                User has expired|The user is locked and cannot login'}
   
     500: Internal server error, body:
       {:error 'Missing API key |
                Something went wrong. Check the FusionAuth log files'}"
  [{login  :login
    password :password}]
  (try
    (when (utils/validate-request-params {:login login
                                          :password password})
      {:status 200 :body "Tudo certo"})

    (catch ExceptionInfo ex
      {:status 400 :body (ex-data ex)})))