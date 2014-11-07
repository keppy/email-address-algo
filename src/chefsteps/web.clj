(ns chefsteps.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [chefsteps.core :as cs]))

(def time-elapsed
  (time (cs/make-ordered-set cs/emails)))

(defn splash []
  {:status 200
    :headers {"Content-Type" "text/plain"}
    :body (pr-str ["Hello" :from 'Keppy
                   "it took: " time-elapsed " to filter out duplicate emails from a list of 100,000, with 50% duplicates."])})

(defroutes app
  (GET "/" []
    (splash)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
