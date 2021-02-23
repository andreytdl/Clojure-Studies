(ns clojure-component.protocols.storage-client
  (:require [schema.core :as s]))

(defn ->arg-map [query & args]
  {:query query
   :args  args})

(defprotocol StorageClientMemory
  ;; FIXME: There should be a way to generalize query for many args
  ;;   Protocol doesn't accept many varargs, so one can't use (& arg)
  (q
    [conn data]
    [conn data arg]
    [conn data arg1 arg2 arg3]
    "Return the contents of storage based on datalog")
  (exec! [storage data]   "Mutate the storage with the provided function")
  (transact [storage data] "It transacts something"))

(def IStorageClient (s/protocol StorageClientMemory))
