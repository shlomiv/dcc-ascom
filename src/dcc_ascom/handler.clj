(ns dcc-ascom.handler
  (:require [compojure.core :refer :all]
            [dcc-ascom.dcc :as dcc]
            [compojure.route :as route]
            [ring.logger :as logger]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def transaction-id (atom 0))

(def d (atom (dcc/init-DCC-sim)))

(defn next-transaction[]
  (swap! transaction-id inc))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/api/v1/camera/:id/interfaceversion" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 2}})
  (GET "/api/v1/camera/:id/connected" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" true}})
  (PUT "/api/v1/camera/:id/connected" [id Connected ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""}})
  (GET "/api/v1/camera/:id/sensorname" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" ""}})
  (GET "/api/v1/camera/:id/canasymmetricbin" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" false}})
  (GET "/api/v1/camera/:id/binx" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 1}})
  (GET "/api/v1/camera/:id/biny" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 1}})
  (PUT "/api/v1/camera/:id/binx" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""}})
  (PUT "/api/v1/camera/:id/biny" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""}})
  (GET "/api/v1/camera/:id/maxbinx" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 1}})
  (GET "/api/v1/camera/:id/maxbiny" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 1}})
  (GET "/api/v1/camera/:id/cameraxsize" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 6004}})
  (GET "/api/v1/camera/:id/cameraysize" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 4004}})
  (GET "/api/v1/camera/:id/sensortype" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 0}})
  (GET "/api/v1/camera/:id/maxadu" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 65535}})
  (GET "/api/v1/camera/:id/startx" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 0}})
  (GET "/api/v1/camera/:id/starty" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 0}})
  (PUT "/api/v1/camera/:id/startx" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""}})
  (PUT "/api/v1/camera/:id/starty" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""}})
  (GET "/api/v1/camera/:id/gainmin" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 100}})
  (GET "/api/v1/camera/:id/gainmax" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 6400}})
  (GET "/api/v1/camera/:id/driverinfo" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" "DCC - Clojure ASCOM bridge"}})
  (GET "/api/v1/camera/:id/exposuremin" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 1}})
  (GET "/api/v1/camera/:id/exposuremax" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 3600}})
  (GET "/api/v1/camera/:id/cooleron" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" false}})
  
  (GET "/api/v1/camera/:id/cansetccdtemperature" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" false}})
  (GET "/api/v1/camera/:id/ccdtemperature" [id ClientID ClientTransactionID]
       {:status 500 :body "Not supported (Thanks Nikon!)"})
  (GET "/api/v1/camera/:id/setccdtemperature" [id ClientID ClientTransactionID]
       {:status 500 :body "Not supported (Thanks Nikon!)"})
  (GET "/api/v1/camera/:id/heatsinktemperature" [id ClientID ClientTransactionID]
       {:status 500 :body "Not supported (Thanks Nikon!)"})
  (GET "/api/v1/camera/:id/cangetcoolerpower" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" false}})
  (GET "/api/v1/camera/:id/numx" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 6004}})
  (GET "/api/v1/camera/:id/numy" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" 4004}})
  (PUT "/api/v1/camera/:id/numx" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""}})
  (PUT "/api/v1/camera/:id/numy" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""}})
  (GET "/api/v1/camera/:id/canabortexposure" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" true}})
  (GET "/api/v1/camera/:id/imageready" [id ClientID ClientTransactionID]
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" false}}) ;;;; TODO:
  (GET "/api/v1/camera/:id/camerastate" [id ClientID ClientTransactionID]
       (println "CAMERA STATE " (dcc/get-camera-state @d))
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""
               "Value" (get dcc/camera-states (dcc/get-camera-state @d))}}) ;; TODO: Complete 
  
  (PUT "/api/v1/camera/:id/startexposure" [id Duration Light ClientID ClientTransactionID]
       (try
         (Integer/parseInt Duration)
         (try
           (let [_ (swap! d dcc/start-bulb)
                 _ (future
                     (println "SLEEPING")
                     (Thread/sleep (* 1000 (Integer/parseInt Duration)))
                     (println "DONE SLEEPING")
                     (swap! d dcc/stop-bulb))]
             {:status 200
              :body {"ClientTransactionID" ClientTransactionID
                     "ServerTransactionID" (next-transaction)
                     "ErrorNumber" 0
                     "ErrorMessage" ""}})
           (catch Exception e
             {:status 500
              :body (str e)}))
         (catch Exception e
           {:status 400
            :body "Duration parameter not numeric"}))
       )
  (PUT "/api/v1/camera/:id/abortexposure" [id ClientID ClientTransactionID]
       (swap! d dcc/stop-bulb)
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""}})
  (PUT "/api/v1/camera/:id/stopexposure" [id ClientID ClientTransactionID]
       (swap! d dcc/stop-bulb)
       {:status 200
        :body {"ClientTransactionID" ClientTransactionID
               "ServerTransactionID" (next-transaction)
               "ErrorNumber" 0
               "ErrorMessage" ""}})
  (route/not-found "Not Found"))

(def app
  (wrap-defaults (wrap-json-response (logger/wrap-log-response app-routes))
                 (assoc-in site-defaults [:security :anti-forgery] false)
                 ))
