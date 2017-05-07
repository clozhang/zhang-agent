(ns zhang.agent.processes
  "Process-tracking for the Zhang agent."
  (:require [clojure.core.async :as async]))

(def process-table (atom nil))

(defrecord ZhangProcessTable [chan data])

(defn create-process-table
  "Create a new process table.

  It is intended that there will only be one process table per agent/node."
  []
  (->ZhangProcessTable (async/chan)
                       {}))
