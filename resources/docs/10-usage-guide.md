# Usage Guide

To use the agent, update your ``project.clj`` (either top-level or one of your profiles) to include the following:

```clj
  ...
  :dependencies [
    [clojang/zhang "0.1.0-SNAPSHOT"]
    [clojang/zhang-agent "0.2.0-SNAPSHOT"]
    ...]
  :jvm-opts ["-Dnode.sname=zhang"]
  :java-agents [[clojang/zhang-agent "0.2.0-SNAPSHOT"]]
  :aot [zhang.agent.startup]
  ...
```

In your project's REPL, you can then do something like the following:

```clj
(require '[zhang.process :as process]
         '[zhang.process.table :as process-table])

(defn counter
  [cnt msg]
  (case (:type msg)
    :inc (partial counter (inc cnt))
    :get (do (process/! (:to msg) cnt)
             (partial counter cnt))))

(defn printer
  [msg]
  (println "Got:" msg)
  printer)

(def printer-process (process/spawn printer))

(def counter-process (process/spawn (partial counter 0)))
```

Now that you've spawned some processes, list them:
```clj
zhang.dev=> (process-table/ls)

total 2

|                 :id |                    :fun |                                              :chan |
|---------------------+-------------------------+----------------------------------------------------|
| <hostname:123:1234> |   zhang.dev$printer$ptr | clojure.core.async.impl.channels.ManyToManyChannel |
| <hostname:234:2345> | clojure.core$partial$fn | clojure.core.async.impl.channels.ManyToManyChannel |
:ok
```

There is also a `process-table/get-all` function that will return the process
table data structure. Alternatively, to get the data for a single process:

```clj
(process-table/lookup "<hostname:123:1234>")
```

And, of course, you can use your functions by sending them messages:

```clj
zhang.dev=> (process/! counter-process {:type :get :to printer-process})
true
Got:
0
zhang.dev=> (process/! counter-process {:type :inc})
true
zhang.dev=> (process/! counter-process {:type :get :to printer-process})
Got: 1
true
zhang.dev=> (process/! counter-process {:type :inc})
true
zhang.dev=> (process/! counter-process {:type :inc})
true
zhang.dev=> (process/! counter-process {:type :inc})
true
zhang.dev=> (process/! counter-process {:type :get :to printer-process})
Got: 4
true
```

Finally, termination:

```clj
zhang.dev=> (process/terminate counter-process)
:terminated
zhang.dev=> (process-table/ls)

total 1

|                 :id |                    :fun |                                              :chan |
|---------------------+-------------------------+----------------------------------------------------|
| <hostname:123:1234> |   zhang.dev$printer$ptr | clojure.core.async.impl.channels.ManyToManyChannel |
:ok
zhang.dev=> (process/terminate printer-process)
:terminated
zhang.dev=> (process-table/ls)

total 0
:ok
```

If you are sure you want to remove all processes from the table, you can use
this method (useful for resetting state during development):

```clj
zhang.dev=> (process-table/remove-all)
```
