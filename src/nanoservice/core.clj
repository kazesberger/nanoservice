(ns nanoservice.core
  (:use     [hiccup.middleware :only (wrap-base-url)])
  (:require [ring.adapter.jetty :as jetty]
            [cheshire.core :as cheshire]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response content-type]]
            [compojure.core :as comp]
            [compojure.route :as route]
            [compojure.handler :as handler]))

(def db (atom 0))

(defn resp-handler [request]
  (content-type
    (response
      (cheshire/generate-string {:name       "klaus"
                                 :profession "HROO! HROO! HROO!"
                                 :foo        ["bar" "baz"]}))
    "application/json"))

(defn state []
  (str "Counter is " @db))

(defn handler [f]
  (swap! db f)
  (state))

(comp/defroutes main-routes
  (comp/GET "/inc" [] (handler inc))
  (comp/GET "/dec" [] (handler dec))
  (route/resources "/")
  (route/not-found "Page not found"))

;(jetty/run-jetty resp-handler {:port 3000
;                               :join? false})

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))