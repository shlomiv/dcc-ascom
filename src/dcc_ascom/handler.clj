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

(defn ascom-response
  ([client-transaction-id err-code] (ascom-response client-transaction-id err-code nil))
  ([client-transaction-id err-code response]
   (cond 
     (#{200} err-code) {:status 200
                         :body (let [r {"ClientTransactionID" client-transaction-id
                                        "ServerTransactionID" (next-transaction)
                                        "ErrorNumber" 0
                                        "ErrorMessage" ""}
                                     resp (if (nil? response) r (assoc r "Value" response) )]
                                 (println "Responding: "resp)
                                 resp)}
     (#{400 500} err-code) {:status 400 :body response}
     :else {:status 500 :body (str "Dont know err-code (" err-code "): " response)})))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/api/v1/camera/:id/interfaceversion" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 2))

  (GET "/api/v1/camera/:id/connected" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 true))

  (PUT "/api/v1/camera/:id/connected" [id Connected ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (GET "/api/v1/camera/:id/sensorname" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 ""))

  (GET "/api/v1/camera/:id/canasymmetricbin" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 false))

  (GET "/api/v1/camera/:id/binx" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 1))

  (GET "/api/v1/camera/:id/biny" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 1))

  (PUT "/api/v1/camera/:id/binx" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (PUT "/api/v1/camera/:id/biny" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (GET "/api/v1/camera/:id/maxbinx" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 1))

  (GET "/api/v1/camera/:id/maxbiny" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 1))

  (GET "/api/v1/camera/:id/cameraxsize" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 6004))

  (GET "/api/v1/camera/:id/cameraysize" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 4004))

  (GET "/api/v1/camera/:id/sensortype" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 0))

  (GET "/api/v1/camera/:id/maxadu" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 65535))

  (GET "/api/v1/camera/:id/startx" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 0))

  (GET "/api/v1/camera/:id/starty" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 0))

  (PUT "/api/v1/camera/:id/startx" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (PUT "/api/v1/camera/:id/starty" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (GET "/api/v1/camera/:id/gainmin" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 100))

  (GET "/api/v1/camera/:id/gainmax" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 6400))

  (GET "/api/v1/camera/:id/gain" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 3)) ;; index into gains array

  (PUT "/api/v1/camera/:id/gain" [id Gain ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 3 Gain)) ;; index into gains array

  (GET "/api/v1/camera/:id/driverinfo" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 "DCC - Clojure ASCOM bridge"))

  (GET "/api/v1/camera/:id/exposuremin" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 1))

  (GET "/api/v1/camera/:id/exposuremax" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 3600))

  (GET "/api/v1/camera/:id/cooleron" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 false))

  (GET "/api/v1/camera/:id/cansetccdtemperature" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 false))

  (GET "/api/v1/camera/:id/ccdtemperature" [id ClientID ClientTransactionID]
       {:status 500 :body "Not supported (Thanks Nikon!)"})
  (GET "/api/v1/camera/:id/setccdtemperature" [id ClientID ClientTransactionID]
       {:status 500 :body "Not supported (Thanks Nikon!)"})
  (GET "/api/v1/camera/:id/heatsinktemperature" [id ClientID ClientTransactionID]
       {:status 500 :body "Not supported (Thanks Nikon!)"})
  (GET "/api/v1/camera/:id/cangetcoolerpower" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 false))

  (GET "/api/v1/camera/:id/numx" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 6004))

  (GET "/api/v1/camera/:id/numy" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 4004))

  (PUT "/api/v1/camera/:id/numx" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (PUT "/api/v1/camera/:id/numy" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (GET "/api/v1/camera/:id/canabortexposure" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 true))

  (GET "/api/v1/camera/:id/imageready" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200 false));;;; TODO:
  
  (GET "/api/v1/camera/:id/camerastate" [id ClientID ClientTransactionID]
       (println "CAMERA STATE " (dcc/get-camera-state @d))
       (ascom-response ClientTransactionID 200 (get dcc/camera-states (dcc/get-camera-state @d))));; TODO: Complete 
  
  (PUT "/api/v1/camera/:id/startexposure" [id Duration Light ClientID ClientTransactionID]
       (try
         (Integer/parseInt Duration)
         (try
           (let [_ (swap! d dcc/start-bulb)
                 _ (future
                     (Thread/sleep (* 1000 (Integer/parseInt Duration)))
                     (swap! d dcc/stop-bulb))]
             (ascom-response ClientTransactionID 200))

           (catch Exception e
             {:status 500 :body (str e)}))

         (catch Exception e
           {:status 400 :body "Duration parameter not numeric"})))
  
  (PUT "/api/v1/camera/:id/abortexposure" [id ClientID ClientTransactionID]
       (swap! d dcc/stop-bulb)
       (ascom-response ClientTransactionID 200))

  (PUT "/api/v1/camera/:id/stopexposure" [id ClientID ClientTransactionID]
       (swap! d dcc/stop-bulb)
       (ascom-response ClientTransactionID 200))

  (route/not-found "Not Found"))


(def app
  (-> app-routes
      logger/wrap-log-response
      wrap-json-response
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))



