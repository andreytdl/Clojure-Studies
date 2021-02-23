(ns app-noob.core
  (:gen-class)
  (require [app-noob.core-andrey :as andrey]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn function
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello!")
  (andrey/function))