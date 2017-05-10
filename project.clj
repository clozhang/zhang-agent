(defproject clojang/zhang-agent "0.2.0-SNAPSHOT"
  :description "Zhang Node, REPL Start-up, and Zhang Process Table"
  :url "https://github.com/clojang/zhang-agent"
  :scm {
    :name "git"
    :url  "https://github.com/clojang/zhang-agent"}
  :license {
    :name "Apache License, Version 2.0"
    :url  "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [clojang/agent "0.4.0-SNAPSHOT"]
    [clojusc/trifl "0.1.0-SNAPSHOT"]
    [clojusc/twig "0.3.1" :exclusions [org.clojure/tools.reader]]
    [dire "0.5.4"]
    [org.clojure/core.async "0.3.442"]]
  :manifest {"Premain-Class" "zhang.agent"}
  :codox {:output-path "docs/current"
          :doc-paths ["resources/docs"]
          :project {:name "zhang-agent"}
          :themes [:rdash]
          :namespaces [#"^zhang\.(?!test)"]
          :metadata {:doc/format :markdown}}
  :profiles {
    :uberjar {:aot :all}
    :docs {
      :dependencies [[codox-theme-rdash "0.1.2"]]
      :plugins [
        [lein-codox "0.10.3"]
        [lein-simpleton "1.3.0"]]}
    :testing {
       :plugins [
         [lein-ancient "0.6.10"]
         [jonase/eastwood "0.2.3" :exclusions [org.clojure/clojure]]
         [lein-bikeshed "0.4.1"]
         [lein-kibit "0.1.3" :exclusions [org.clojure/clojure]]
         [venantius/yagni "0.1.4"]]}
    :dev {
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"]]
      :source-paths ["dev-resources/src"]
      :repl-options {:init-ns zhang.agent.dev}}})
