(ns zhang.agent.startup
  "Zhang agent startup."
  (:require ;[clojang.agent.const :as const]
            ;[clojang.agent.startup :as startup]
            [clojure.tools.logging :as log]
            ;[trifl.net :as net]
            [zhang.agent.process-table :as process-table]))

(defn perform-management-tasks
  ""
  []
  (process-table/create))

(defn perform-node-tasks
  "This is the function that sets up a running node for a given JVM."
  [node-name]
  (log/infof "Bringing up OTP node on %s ..." node-name)
  ;; XXX We'll address the commented out lines onces we have a node-
  ;; implementation-agnostic Zhang ready to test ...
  ; (let [default-node (nodes/default-node node-name)
  ;       default-mbox (messaging/default-mbox
  ;                      default-node const/default-mbox-name)]
  ;   (log/info "Registered nodes with message boxes:"
  ;             (into [] (nodes/get-names default-node))))
  )
