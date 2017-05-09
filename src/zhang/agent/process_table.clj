(ns zhang.agent.process-table
  "Process-tracking for the Zhang agent."
  (:require [clojure.core.async :as async]))

(def ^:dynamic *process-table* (atom nil))

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
  (remove-process
    [id]
    [this id]
    "Remove the process with the given id from the process table."))

(defrecord ZhangProcessTable [chan data])

(defn add-process
  ([process]
    (add-process @*process-table* process))
  ([this process]
    (reset! this
            (assoc this (:id process) process))
    process))

(defn lookup-process
  ([id]
    (lookup-process @*process-table* id))
  ([this id]
    (get this id)))

(defn remove-process
  ([id]
    (remove-process @*process-table* id))
  ([this id]
    (reset! this
            (dissoc this id))))

(def process-table-behaviour
  {:add-process add-process
   :lookup-process lookup-process
   :remove-process remove-process})

(extend ZhangProcessTable
        IProcessTable
        process-table-behaviour)

(defn create
  "Create a new process table.

  It is intended that there will only be one process table per agent/node."
  []
  (reset! *process-table*
          (->ZhangProcessTable (async/chan)
                               {})))

