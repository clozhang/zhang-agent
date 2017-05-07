(ns zhang.agent.processes
  (:require [clojure.core.async :as async]))

(defrecord ZhangProcessTable [chan data])

(defn create-process-table
  "Create a new process table.

  It is intended that there will only be one process table per agent/node."
  []
  (->ZhangProcessTable (async/chan)
                       (atom {})))
