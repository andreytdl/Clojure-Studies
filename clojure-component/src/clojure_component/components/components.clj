(ns clojure-component.components.components
  (:refer-clojure :exclude [test])
  (:require [clojure-component.components.system-utils :as system-utils]
            [clojure-component.config :as config]
            [clojure-component.components.datomic-memory :as dm]
            [com.stuartsierra.component :as component]
            [clojure-component.components.webserver :as webserver]))

;; Sistemas -----------------
(defn base []
  (let [config (config/get-config)]
    (component/system-map
     :storage (dm/new-datomic-memory)
     :jetty   (component/using
               (webserver/new-web-server config)
               [:storage]))))

(defn test-system
  ([] (test-system (config/get-config)))
  ([config]
   (component/system-map
    :storage               (dm/new-datomic-memory)
    :jetty                 (component/using
                            (webserver/new-web-server config)
                            [:storage]))))

;; Junção dos sistemas --------

(def systems-map
  {:test-system  test-system
   :base-system  base})

;; Inicializando o sistema -----
(defn create-and-start-system!
  ([] (create-and-start-system! :base-system))
  ([env]
   (system-utils/bootstrap! systems-map env)))

;; Certificando que o sistema está de pé
(defn ensure-system-up! [env]
  (or (deref system-utils/system)
      (create-and-start-system! env)))

;; Parando o sistema
(defn stop-system! [] (system-utils/stop-components!))