(ns zhang.agent.process-table.impl
  "Implementation of the process-tracking table for the Zhang agent."
  (:require [clojure.core.async :as async]
            [clojure.pprint :as pprint]))

(defn table-printer
  ""
  [data]
  (pprint/print-table [:id :fun :chan] data))

(defprotocol IProcessTable
  "The process table interface for Zhang."
  (get-processes
    [this]
    "Get all processes.")
  (list-processes
    [this]
    "List all processes.")
  (add-process
    [process]
    [this process]
    "Add a process to the process table.")
  (lookup-process
    [id]
    [this id]
    "Lookup a process by id.")
  (remove-process
    [id]
    [this id]
    "Remove the process with the given id from the process table."))

(defrecord ZhangProcessTable [chan data])

(defn get-p
  [this]
  (vals (:data this)))

(defn list-p
  [this]
  (table-printer (get-p this)))

(defn add-p
  [this process]
  (assoc-in this [:data (:id process)] process))

(defn lookup-p
  [this id]
  (get-in this [:data id]))

(defn remove-p
  [this id]
  (update-in this [:data] dissoc id))

(def process-table-behaviour
  {:get-processes get-p
   :list-processes list-p
   :add-process add-p
   :lookup-process lookup-p
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
