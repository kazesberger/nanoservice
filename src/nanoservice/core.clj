(ns nanoservice.core
  (:require [ring.adapter.jetty :as jetty]
            [cheshire.core :as cheshire]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]))

def db (atom {})

(defn resp-handler
  (response
    (cheshire/generate-string {:name       "klaus"
                               :profession "HROO! HROO! HROO!"
                               :foo        ["bar" "baz"]})))

(defn handler [request]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (cheshire/generate-string {:name       "klaus"
                                       :profession "HROO! HROO! HROO!"
                                       :foo        ["bar" "baz"]})})


;(jetty/run-jetty handler {:port 3000
;                          :async? true
;                          :join? false
;                          :async true})