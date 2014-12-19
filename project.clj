(defproject chefsteps "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [faker "0.2.2"]
                 [compojure "1.2.1"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [environ "1.0.0"]
                 [criterium "0.4.3"]
                 [hiccup "1.0.5"]]
  :min-lein-version "2.0.0"
  :plugins [[lein-environ "1.0.0"]]
  :uberjar-name "chefsteps.jar"
  :profiles {:production {:env {:production true}}})
