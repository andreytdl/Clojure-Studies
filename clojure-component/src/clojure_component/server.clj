(ns clojure-component.server
  (:gen-class)
  (:require [clojure-component.components.components :as components]
            [clojure-component.components.system-utils :as system-utils]
            [clojure.core.async :as async]))

(defn startup [system-name]
  (println "@SYSTEM init")
  (components/create-and-start-system! system-name)
  (println "@SYSTEM init-ok"))

(defn shutdown []
  (println "@SYSTEM shutdown")
  (system-utils/stop-system!)
  (println "@SYSTEM shutdown-ok"))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& _]
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. shutdown))
  (println "\nCreating your [DEV] server...")
  (startup :test-system))

(defn -main
  "The entry-point for 'lein run'"
  [& _]
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. shutdown))
  (println "\nCreating your server...")
  (startup :base-system)
  (async/<!! (async/chan)))
