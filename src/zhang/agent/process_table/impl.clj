(ns zhang.agent.process-table.impl
  "Implementation of the process-tracking table for the Zhang agent."
  (:require [clojure.core.async :as async]
            [clojure.pprint :as pprint])
  (:refer-clojure :exclude [count empty?]))

(defn table-printer
  "A convenience function for displying process data in an ASCII table."
  [data]
  (pprint/print-table [:id :fun :chan] data))

(defprotocol IProcessTable
  "The process table interface for Zhang."
  (count
    [this]
    "Return the number of processes.")
  (empty?
    [this]
    "Perform a boolean check on the process table; returns `true` if there are
    no processes in the table.")
  (get-processes
    [this]
    "Get all processes from the process table.")
  (list-processes
    [this]
    "Display all the processes in an ASCII-formatted table.")
  (add-process
    [this process]
    "Add a process to the process table.")
  (lookup-process
    [this id]
    "Lookup a process by id.")
  (remove-process
    [this id]
    "Remove the process with the given id from the process table.")
  (remove-processes
    [this]
    [this ids]
    "Remove the given process ids from the process table. If no ids are given,
    all processes will be removed."))

(defrecord ZhangProcessTable [chan data])

(defn count-ps
  [this]
  (clojure.core/count (:data this)))

(defn empty-ps?
  [this]
  (clojure.core/empty? (:data this)))

(defn get-ps
  [this]
  (vals (:data this)))

(defn list-ps
  [this]
  (table-printer (get-ps this)))

(defn add-p
  [this process]
  (assoc-in this [:data (:id process)] process))

(defn lookup-p
  [this id]
  (get-in this [:data id]))

(defn remove-p
  [this id]
  (update-in this [:data] dissoc id))

(defn remove-ps
  ([this]
   (assoc this :data {}))
  ([this ids]
   (reduce remove-p this ids)))

(def process-table-behaviour
  {:count count-ps
   :empty? empty-ps?
   :get-processes get-ps
   :list-processes list-ps
   :add-process add-p
   :lookup-process lookup-p
   :remove-process remove-p
   :remove-processes remove-ps})

(extend ZhangProcessTable
        IProcessTable
        process-table-behaviour)

(defn create
  "Process table constructor."
  ([]
   (create {}))
  ([data]
   (create (async/chan) data))
  ([chan data]
   (->ZhangProcessTable chan data)))
