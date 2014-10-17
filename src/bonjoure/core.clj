(ns bonjoure.core
 (:gen-class)
 (:require [clojure.data.json :as json])
 (:use
   [twitter.oauth]
   [twitter.callbacks]
   [twitter.callbacks.handlers]
   [twitter.api.restful]
   [ring.adapter.jetty])
  (:import
   (twitter.callbacks.protocols SyncSingleCallback)))

(def creds-conf (json/read-str (slurp "creds.json") :key-fn keyword))

(def creds (make-oauth-creds
                (:consumer_key creds-conf)
                (:consumer_secret creds-conf)
                (:access_token creds-conf)
                (:access_token_secret creds-conf)))

(defn twitter-posts [query tweet-count]
  (let [tweets (search-tweets :oauth-creds creds :params {:q query :count tweet-count})
        statuses (:statuses (:body tweets))]
    (map #(str "<tr class=\"odd-row\"><td class=\"first\">" (:text %) "</td></tr>" ) statuses)))

(defn html [tweets]
  (let [string-tweets (apply str tweets)]
    (str "<!DOCTYPE html><html><head><title>6Wunderkinder Tweets</title>"
         "<script src=\"http://www.modernizr.com/downloads/modernizr-latest.js\"></script>"
         "<link href=\"http://dennis-schneider.com/downloads/layout.css\" rel=\"stylesheet\" type=\"text/css\"></head>"
     "<body><table><tr><td class=\"first\"><strong>Message</strong></td></tr>" string-tweets "</table></body>")))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html (twitter-posts "6Wunderkinder" 100))})

(defn -main
  [& args]
  (run-jetty handler {:port 8080}))
