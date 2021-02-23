(ns clojure-component.config
  (:require [com.walmartlabs.dyn-edn :refer [env-readers]]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn read-from-file [file]
  (->> file
       slurp
       (edn/read-string {:readers (env-readers)})))

(defn get-system-config
  "Reads a config.edn file if it exists at the same location as the running process,
  or load the config from the defaults using the environment."
  []
  (if (.exists (io/file "config.edn"))
    (->> "config.edn"
         read-from-file)
    (->> "config-defaults.edn"
         io/resource
         read-from-file)))

(def get-config (memoize get-system-config))
