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

(defmacro ASCOM-GET [path args & v]
  `(GET ~(str "/api/v1/camera/:id" path) ~(into '[id  ClientID ClientTransactionID] args) ~@v))

(defmacro ASCOM-PUT [path args & v]
  `(PUT ~(str "/api/v1/camera/:id" path) ~(into '[id  ClientID ClientTransactionID] args) ~@v))

(defmacro success
  ([& v] `(ascom-response ~@'[ClientTransactionID 200] ~@v)))

(defmacro not-supported[v]
  `(ascom-response ~@'[ClientTransactionID 500] ~v))

(defmacro bad-args[v]
  `(ascom-response ~@'[ClientTransactionID 400] ~v))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (ASCOM-GET "/interfaceversion" [] (success 2))
  (ASCOM-GET "/connected" [] (success true))
  (ASCOM-PUT "/connected" [Connected]
             (println "Mock setting connected to" Connected)
             (success))
  
  (ASCOM-GET "/sensorname" [] (success ""))
  (ASCOM-GET "/canasymmetricbin" [] (success false))
  (ASCOM-GET "/binx" [] (success 1))
  (ASCOM-GET "/biny" [] (success 1))
  (ASCOM-PUT "/binx" [ClientID] (success))
  (ASCOM-PUT "/biny" [ClientID] (success))
  (ASCOM-GET "/maxbinx" [] (success 1))
  (ASCOM-GET "/maxbiny" [] (success 1))
  (ASCOM-GET "/cameraxsize" [] (success 6004))
  (ASCOM-GET "/cameraysize" [] (success 4004))
  (ASCOM-GET "/sensortype" [] (success 0))
  (ASCOM-GET "/maxadu" [] (success 65535))
  (ASCOM-GET "/startx" [] (success 0))
  (ASCOM-GET "/starty" [] (success 0))
  (ASCOM-PUT "/startx" [] (success))
  (ASCOM-PUT "/starty" [] (success))
  (ASCOM-GET "/gainmin" [] (success 100))
  (ASCOM-GET "/gainmax" [] (success 6400))
  (ASCOM-GET "/gain" [] (success 3)) ;; index into gains array
  (ASCOM-PUT "/gain" [Gain] (success Gain)) ;; index into gains array
  (ASCOM-GET "/driverinfo" [] (success "DCC - Clojure ASCOM bridge"))
  (ASCOM-GET "/exposuremin" [] (success 1))
  (ASCOM-GET "/exposuremax" [] (success 3600))
  (ASCOM-GET "/cooleron" [] (success false))
  (ASCOM-GET "/cansetccdtemperature" [] (success false))
  (ASCOM-GET "/ccdtemperature" [] (not-supported "Not supported (Thanks Nikon!)"))
  (ASCOM-GET "/setccdtemperature" [] (not-supported "Not supported (Thanks Nikon!)"))
  (ASCOM-GET "/heatsinktemperature" [] (not-supported "Not supported (Thanks Nikon!)"))
  (ASCOM-GET "/cangetcoolerpower" [] (success false))
  (ASCOM-GET "/numx" [] (success 6004))
  (ASCOM-GET "/numy" [] (success 4004))
  (ASCOM-PUT "/numx" [] (success))
  (ASCOM-PUT "/numy" [] (success))
  (ASCOM-GET "/canabortexposure" [] (success true))
  (ASCOM-GET "/imageready" [] (success false));;;; TODO:
  (ASCOM-GET "/camerastate" []
             (println "CAMERA STATE " (dcc/get-camera-state @d))
             (success (get dcc/camera-states (dcc/get-camera-state @d))));; TODO: Complete 

  (ASCOM-PUT "/startexposure" [Duration Light]
      (try
         (Integer/parseInt Duration)
         (try
           (let [_ (swap! d dcc/start-bulb)
                 _ (future
                     (Thread/sleep (* 1000 (Integer/parseInt Duration)))
                     (swap! d dcc/stop-bulb))] (success))

           (catch Exception e
             {:status 500 :body (str e)}))

         (catch Exception e
           {:status 400 :body "Duration parameter not numeric"})))
  
  (ASCOM-PUT "/abortexposure" []
            (swap! d dcc/stop-bulb)
             (success))

  (ASCOM-PUT "/stopexposure" []
            (swap! d dcc/stop-bulb)
             (success))

  (route/not-found "Not Found"))


(def app
  (-> app-routes
      logger/wrap-log-response
      wrap-json-response
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))



