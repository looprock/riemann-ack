(defproject riemann-ack "0.0.1"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [com.taoensso/carmine "2.15.1"]
    [riemann "0.2.14" :exclusions [com.fasterxml.jackson.core/jackson-annotations com.fasterxml.jackson.core/jackson-databind joda-time org.clojure/tools.reader org.jsoup/jsoup org.slf4j/slf4j-api org.slf4j/slf4j-log4j12]]
  ]
  ; :main ^:skip-aot test-redis.core
  ; :main test-redis.core
  ; :target-path "target/%s"
  ; :profiles {:uberjar {:aot :all}}
)
