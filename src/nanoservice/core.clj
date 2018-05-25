(ns nanoservice.core
  (:require [ring.adapter.jetty :as jetty]
            [cheshire.core :as cheshire]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response content-type]]))

(def db (atom {}))

(defn resp-handler [request]
  (content-type
    (response
      (cheshire/generate-string {:name       "klaus"
                                 :profession "HROO! HROO! HROO!"
                                 :foo        ["bar" "baz"]}))
    "application/json"))

(response "Hello World")

(defn handler [request]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "foo"})


(jetty/run-jetty resp-handler {:port 3000
                               :join? false})