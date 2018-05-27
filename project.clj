(defproject nanoservice "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.7.0-RC1"]
                 [cheshire "5.8.0"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-devel "1.6.3"]
                 [compojure "1.6.1"]]

  :ring {:handler nanoservice.core/app}
  :plugins [[lein-ring "0.12.1"]])