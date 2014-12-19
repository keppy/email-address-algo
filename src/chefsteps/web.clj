(ns chefsteps.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [chefsteps.core :as cs]
            [criterium.core :as crit]
            [hiccup.core :as hiccup]))

(def sorted-emails
  (cs/make-sorted-set
    (cs/order-emails cs/emails)))

(def page
  (hiccup/html
    [:h1 "This list of 100,000 email addresses was randomly generated and sorted in place in under 1 second:"]
    [:ul
     (for [x sorted-emails]
       [:li x])]))

(defn splash []
  {:status 200
    :headers {"Content-Type" "text/plain"}
    :body page})

(defroutes app
  (GET "/" []
    (splash)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
