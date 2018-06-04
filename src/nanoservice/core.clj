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

;(defn upload-handler)

(def db2 (atom {}))


; https://stackoverflow.com/questions/32742744/how-to-download-a-file-and-unzip-it-from-memory-in-clojure
; https://www.eclipse.org/jetty/javadoc/current/org/eclipse/jetty/server/HttpInput.html
; from debug: {:ssl-client-cert nil, :protocol "HTTP/1.1", :cookies {}, :remote-addr "127.0.0.1", :params {}, :flash nil, :route-params {}, :headers {"user-agent" "curl/7.47.0", "host" "localhost:3000", "accept" "*/*", "content-length" "276696", "expect" "100-continue"}, :server-port 3000, :content-length 276696, :form-params {}, :compojure/route [:put "/serenity"], :session/key nil, :query-params {}, :content-type nil, :character-encoding nil, :uri "/serenity", :server-name "localhost", :query-string nil, :body #object[org.eclipse.jetty.server.HttpInputOverHTTP 0x15f925d4 "HttpInputOverHTTP@15f925d4"], :multipart-params {}, :scheme :http, :request-method :put, :session {}}

(comp/defroutes main-routes
  (comp/GET "/inc" [] (handler inc))
  (comp/GET "/dec" [] (handler dec))
  (comp/GET "/serenity" [] (handler dec))
  (comp/PUT "/serenity" request (reset! db2 request))
  (comp/GET "/debug" [] (str @db2))
  (route/resources "/")
  (route/not-found "Page not found"))

;(jetty/run-jetty resp-handler {:port 3000
;                               :join? false})

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))