(ns chefsteps.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [chefsteps.core :as cs]
            [criterium.core :as crit]
            [hiccup.page :as hiccup-page]
            [faker.internet :as fake]))

(def first-emails (take 50000 (fake/emails)))

(def emails (let [s1 first-emails
                  s2 first-emails]
              (concat s1 s2)))

(def sorted-emails
  (cs/make-sorted-set
    (seq
      (cs/seq-to-ordered-set emails))))

(def page
  (hiccup-page/html5
    [:head
     [:title "ordered, sorted emails."]]
    [:body
      [:h1 (str "This list of 100,000 email addresses was randomly generated and sorted in place in well under a second:")]
      [:ul
       (for [x sorted-emails]
         [:li x])]]))

(defroutes app
  (GET "/" []
    page))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
