# zhang-agent

[![Build Status][travis-badge]][travis]
[![Dependencies Status][deps-badge]][deps]
[![Clojars Project][clojars-badge]][clojars]

*Zhang Node and REPL Start-up*

[![Clojang logo][logo]][logo-large]


#### Contents

* [About](#about-)
* [Usage](#usage-)
* [License](#license-)


## About [&#x219F;](#contents)

The Zhang Agent is intended to perform several tasks for Zhang nodes:

* Start up a JInterface node (in the same way that an Erlang shell or an LFE REPL when the BEAM is started in distributed mode, e.g., ``-sname mynode``)
* Create a process-tracking table
* Provide functions for maintaining processes in the process table, including
  querying the table
* Additionally, it closes the splash image that Clojang displays (if running in
  GUI mode)


## Usage [&#x219F;](#contents)

To use the agent, update your ``project.clj`` (either top-level or one of your profiles) to include the following:

```clj
  ...
  :dependencies [
    [clojang/zhang "0.1.0-SNAPSHOT"]
    [clojang/zhang-agent "0.1.0-SNAPSHOT"]
    ...]
  :jvm-opts ["-Dnode.sname=zhang"]
  :java-agents [[clojang/zhang-agent "0.1.0-SNAPSHOT"]]
  :aot [zhang.agent.startup]
  ...
```

In your project's REPL, you can then do something like the following:

```clj
(defn counter
  [cnt msg]
  (case (:type msg)
    :inc (partial counter (inc cnt))
    :get (do (process/! (:to msg) cnt)
             (partial counter cnt))))

(defn printer []
  (process/spawn (fn ptr [msg]
           (println "Got:" msg)
           ptr)))

(def printer-process (printer))

(def counter-process (process/spawn (partial counter 0)))
```
```clj
zhang.dev=> (process-table/ls)

|                              :id |                                   :fun |                                                       :chan |
|----------------------------------+----------------------------------------+-------------------------------------------------------------|
| <hostname:123:1234> |   zhang.dev$printer$ptr | clojure.core.async.impl.channels.ManyToManyChannel |
| <hostname:234:2345> | clojure.core$partial$fn | clojure.core.async.impl.channels.ManyToManyChannel |
:ok
```

And, of course, you can use your functions :-)

```clj
zhang.dev=> (process/! counter-process {:type :get :to printer-process})
trueGot:
0
zhang.dev=> (process/! counter-process {:type :inc})
true
zhang.dev=> (process/! counter-process {:type :get :to printer-process})
Got: true1

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


## License [&#x219F;](#contents)

```
Copyright © 2016-2017 Duncan McGreggor

Distributed under the Apache License Version 2.0.
```


<!-- Named page links below: /-->

[travis]: https://travis-ci.org/clojang/zhang-agent
[travis-badge]: https://travis-ci.org/clojang/zhang-agent.png?branch=master
[deps]: http://jarkeeper.com/clojang/zhang-agent
[deps-badge]: http://jarkeeper.com/clojang/zhang-agent/status.svg
[clojars]: https://clojars.org/clojang/zhang-agent
[clojars-badge]: https://img.shields.io/clojars/v/clojang/zhang-agent.svg
[logo]: https://github.com/clojang/resources/blob/master/images/logo-5-250x.png
[logo-large]: https://github.com/clojang/resources/blob/master/images/logo-5-1000x.png
