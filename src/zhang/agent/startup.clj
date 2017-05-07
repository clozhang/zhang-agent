(ns zhang.agent.startup
  "Zhang agent startup."
  (:require [clojang.agent.const :as const]
            [clojang.agent.startup :as startup]
            [clojure.tools.logging :as log]
            [clojusc.twig :as logger]
            [dire.core :refer [with-handler!]]
            [trifl.net :as net]
            [zhang.agent.processes :as processes])
  (:import [java.lang.instrument]
           [java.awt HeadlessException SplashScreen])
  (:gen-class
    :methods [^:static [premain [String java.lang.instrument.Instrumentation] void]]))

(def process-table (atom nil))

(def get-node-name
  "Get the node name."
  startup/get-node-name)

(def perform-gui-tasks
  "Close the custom splash screen."
  startup/perform-gui-tasks)

(def headless?
  "Check to see if this JVM is declared as being headless."
  startup/headless?)

(defn perform-node-tasks
  "This is the function that sets up a running node for a given JVM."
  []
  (logger/set-level! '[clojang zhang] :info)
  (let [default-node-name (get-node-name)]
    (log/infof "Bringing up Zhang node on %s ..." default-node-name)
    ;; XXX We'll address the commented out lines onces we have a node-
    ;; implementation-agnostic Zhang ready to test ...
    ; (let [default-node (nodes/default-node default-node-name)
    ;       default-mbox (messaging/default-mbox
    ;                      default-node const/default-mbox-name)]
    ;   (log/info "Registered nodes with message boxes:"
    ;             (into [] (nodes/get-names default-node))))
    (reset! process-table (processes/create-process-table))
  ))

(defn -premain
  [args instrument]
  (perform-node-tasks)
  (if-not (headless?)
    (perform-gui-tasks)))

(with-handler! #'perform-gui-tasks
  HeadlessException
  (fn [e & args]
    (log/warn (.getMessage e))))
