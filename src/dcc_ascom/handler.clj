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

(defmacro ASCOM-GET [path args f]
  `(GET ~(str "/api/v1/camera/:id" path) ~(into '[id ClientID ClientTransactionID] args) ~f))

(defmacro ASCOM-GET-success [path args f]
  `(GET ~(str "/api/v1/camera/:id" path) ~(into '[id ClientID ClientTransactionID] args) (ascom-response ClientTransactionID 200 ~f)))

(defmacro ASCOM-PUT [path args f]
  `(GET ~(str "/api/v1/camera/:id" path) ~(into '[id ClientID ClientTransactionID] args) ~f))

(defmacro ascom-success [val]
  `(ascom-response ClientTransactionID 200 val))

(macroexpand '(ASCOM-GET "/interfaceversion" []
                         (ascom-success 2)))


(macroexpand (macroexpand '(ASCOM-GET "/interfaceversion" []
                         (ascom-success 2))))(compojure.core/make-route :get #clout.core.CompiledRoute{:source "/api/v1/camera/:id/interfaceversion", :re #"/api/v1/camera/([^/,;?]+)/interfaceversion", :keys [:id], :absolute? false} (clojure.core/fn [request__3834__auto__] (compojure.core/let-request [[id ClientID ClientTransactionID] request__3834__auto__] (ascom-success 2))))


(macroexpand '(ASCOM-GET-success "/interfaceversion" [] 3))

;;(defroutes app-routes)
(defroutes app-routes
  (GET "/" [] "Hello ")
  
  (ASCOM-GET-success "/interfaceversion" []
                    )

  (ASCOM-GET-success "/connected" []
                    true)

  (PUT "/api/v1/camera/:id/connected" [id Connected ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (ASCOM-GET-success "/sensorname" []
                    "")

  (ASCOM-GET-success "/canasymmetricbin" []
                    false)

  (ASCOM-GET-success "/binx" []
                    1)

  (ASCOM-GET-success "/biny" []
                    1)

  (PUT "/api/v1/camera/:id/binx" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (PUT "/api/v1/camera/:id/biny" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (ASCOM-GET-success "/maxbinx" []
                    1)

  (ASCOM-GET-success "/maxbiny" []
                    1)

  (ASCOM-GET-success "/cameraxsize" []
                    6004)

  (ASCOM-GET-success "/cameraysize" []
                    4004)

  (ASCOM-GET-success "/sensortype" []
                    0)

  (ASCOM-GET-success "/maxadu" []
                    65535)

  (ASCOM-GET-success "/startx" []
                    0)

  (ASCOM-GET-success "/starty" []
                    0)

  (PUT "/api/v1/camera/:id/startx" [id ClientID ClientTransactionID]
       (as))

  (PUT "/api/v1/camera/:id/starty" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (ASCOM-GET-success "/gainmin" []
                    100)

  (ASCOM-GET-success "/gainmax" []
                    6400)

  (ASCOM-GET-success "/gain" []
                    3) ;; index into gains array

  (PUT "/api/v1/camera/:id/gain" [id Gain ClientID ClientTransactionID]
       (ascom-success 3 Gain)) ;; index into gains array

  (ASCOM-GET-success "/driverinfo" []
                    "DCC - Clojure ASCOM bridge")

  (ASCOM-GET-success "/exposuremin" []
                    1)

  (ASCOM-GET-success "/exposuremax" []
                    3600)

  (ASCOM-GET-success "/cooleron" []
                    false)

  (ASCOM-GET-success "/cansetccdtemperature" []
                    false)

  (ASCOM-GET-success "/ccdtemperature" []
                    })
  (ASCOM-GET-success "/setccdtemperature" []
                    })
  (ASCOM-GET-success "/heatsinktemperature" []
                    })
  (ASCOM-GET-success "/cangetcoolerpower" []
                    false)

  (ASCOM-GET-success "/numx" []
                    6004)

  (ASCOM-GET-success "/numy" []
                    4004)

  (PUT "/api/v1/camera/:id/numx" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (PUT "/api/v1/camera/:id/numy" [id ClientID ClientTransactionID]
       (ascom-response ClientTransactionID 200))

  (ASCOM-GET-success "/canabortexposure" []
                    true)

  (ASCOM-GET-success "/imageready" []
                    false);;;; TODO:
  
  (ASCOM-GET-success "/camerastate" []
                    (println " (dcc/get-camera-state @d))
       (ascom-success (get dcc/camera-states (dcc/get-camera-state @d))));; TODO: Complete 
  
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



