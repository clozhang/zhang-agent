(ns zhang.agent.process-table.impl
  "Implementation of the process-tracking table for the Zhang agent."
  (:require [clojure.core.async :as async]
            [clojure.pprint :as pprint]))

(defn table-printer
  ""
  [data]
  (pprint/print-table ["ID" "Function" "Channel"] data))

(defprotocol IProcessTable
  "The process table interface for Zhang."
  (add-process
    [process]
    [this process]
    "Add a process to the process table.")
  (lookup-process
    [id]
    [this id]
    "Lookup a process by id.")
  (list-processes
    [this]
    "List all processes.")
  (remove-process
    [id]
    [this id]
    "Remove the process with the given id from the process table."))

(defrecord ZhangProcessTable [chan data])

(defn list-p
  [this]
  (table-printer (vals this)))

(defn add-p
  [this process]
  (assoc this (:id process) process))

(defn lookup-p
  [this id]
  (get this id))

(defn remove-p
  [this id]
  (dissoc this id))

(def process-table-behaviour
  {:add-process add-p
   :lookup-process lookup-p
   :list-processes list-p
   :remove-process remove-p})

(extend ZhangProcessTable
        IProcessTable
        process-table-behaviour)

(defn new-table
  ([]
   (new-table {}))
  ([data]
   (new-table (async/chan) data))
  ([chan data]
   (->ZhangProcessTable chan data)))
