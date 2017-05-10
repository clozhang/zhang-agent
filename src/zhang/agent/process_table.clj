(ns zhang.agent.process-table
  "Process-tracking for the Zhang agent."
  (:require [zhang.agent.process-table.impl :as process-table])
  (:refer-clojure :exclude [get-all remove]))

(def ^:dynamic *process-table* (atom nil))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Process Table Wrappers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn create
  "Create a new process table.

  It is intended that there will only be one process table per agent/node."
  []
  (reset! *process-table*
          (process-table/new-table)))

(defn get-all
  ""
  []
  (process-table/get-processes @*process-table*))

(defn ls
  ""
  []
  (process-table/list-processes @*process-table*))

(defn add
  ""
  [process]
  (reset! *process-table*
          (process-table/add-process @*process-table* process))
  process)

(defn lookup
  ""
  [id]
  (process-table/lookup-process @*process-table* id))

(defn remove
  ""
  [id]
  (reset! *process-table*
          (process-table/remove-process @*process-table* id)))
