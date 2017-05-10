(ns zhang.agent.process-table
  "Process-tracking for the Zhang agent."
  (:require [zhang.agent.process-table.impl :as process-table])
  (:refer-clojure :exclude [count empty? remove]))

(def ^:dynamic *process-table* (atom nil))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Process Table Wrappers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn create
  "Create a new process table.

  It is intended that there will only be one process table per agent/node."
  []
  (reset! *process-table*
          (process-table/create)))

(defn count
  "Return the number of processes."
  []
  (process-table/count @*process-table*))

(defn empty?
  "Perform a boolean check on the process table; returns `true` if there are
  no processes in the table."
  []
  (process-table/empty? @*process-table*))

(defn get-all
  "Get all processes from the process table."
  []
  (process-table/get-processes @*process-table*))

(defn ps
  "Display all the processes in an ASCII-formatted table."
  []
  (let [table @*process-table*]
    (println "\ntotal" (process-table/count table))
    (when-not (process-table/empty? table)
      (process-table/list-processes table))
    :ok))

(defn add
  "Add a process to the process table."
  [process]
  (reset! *process-table*
          (process-table/add-process @*process-table* process))
  process)

(defn lookup
  "Lookup a process by id."
  [id]
  (process-table/lookup-process @*process-table* id))

(defn remove
  "Remove the process with the given id from the process table. If a list of
  ids is passed instead, they will be removed from the process table."
  [id]
  (if (coll? id)
    (reset! *process-table*
            (process-table/remove-processes @*process-table* id))
    (reset! *process-table*
            (process-table/remove-process @*process-table* id)))
  :ok)

(defn remove-all
  "Remove the given process ids from the process table. If not ids are given,
  all processes will be removed."
  []
  (reset! *process-table*
          (process-table/remove-processes @*process-table*))
  :ok)
