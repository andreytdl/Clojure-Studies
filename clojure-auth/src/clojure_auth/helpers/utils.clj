(ns clojure-auth.helpers.utils
  (:require 
   [clojure.pprint]
   [clojure.string :as str]
   ))
  

(defn printer [something]
  
  (clojure.pprint/pprint something)
  
  something)
  
  


(defn validate-request-params
  "Checks if the given map of parameters is not: empty, nil nor contains only 
   whitespace.
   
  Args:
    params: map of parameters.
    
  Returns:
    bool: true if all parameters are valid.
   
  Throws:
    ExceptionInfo: When any of the parameters are not valid, including a list of
    the missing fields."
  [params]
  (let [missing-fields (->> (into {} (filter (comp #(if (string? %)
                                                      (str/blank? %)
                                                      (nil? %)) second) params))
                            keys (map name))]
    (when (not-empty missing-fields)
      (throw (ex-info "Missing required fields"
                      {:cause (str "Missing fields: "
                                   (str/join ", " missing-fields))})))
    true))