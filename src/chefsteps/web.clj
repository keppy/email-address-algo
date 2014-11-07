(ns chefsteps.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [chefsteps.core :as cs]
            [criterium.core :as crit]))

(defn splash []
  {:status 200
    :headers {"Content-Type" "text/plain"}
    :body (pr-str ["Hello" :from 'Keppy
                   "these addresses were filterd from a list of 100,000 email addresses with at least 50% duplicates (in under a second!): " (cs/ordered-emails cs/emails) ])})

(defroutes app
  (GET "/" []
    (splash)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
