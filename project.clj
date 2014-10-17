(defproject bonjoure "0.1.0-SNAPSHOT"
  :description "Fetches the 100 latest tweets about 6Wunderkinder"
  :url "http://github.com/dschneider/bonjoure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/clojure "1.5.1"]
    [twitter-api "0.7.7"]
    [ring "1.3.1"]
    [org.clojure/data.json "0.2.5"]
  ]
  :main ^:skip-aot bonjoure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
