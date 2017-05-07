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
* XXX
* Additionally, it closes the splash image that Clojang displays


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
