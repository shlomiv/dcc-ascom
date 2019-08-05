(ns dcc-ascom.dcc)

(defn now []
  (quot (System/currentTimeMillis) 1000))

(def camera-states
  {:idle 0 :waiting 1 :exposing 2 :reading 3 :downloading 4 :error 5})

(defprotocol DCC-interace
  (list-iso [this])
  (set-iso [this iso])
  (set-output [this path])
  (get-output [this])
  (get-camera-state [this])
  (start-bulb [this])
  (stop-bulb [this])
  (get-last-raw [this]))

(defrecord DCC-sim [isos camera-state]
  DCC-interace
  (list-iso [this]
    isos)
  (get-camera-state [this]
    (:camera-state this))
  (set-iso [this iso]
    (assoc this :iso iso))
  (set-output [this path]
    (assoc this :output path))
  (get-output [this]
    (:output this))
  (start-bulb [this]
    (println "STARTING")
    (assoc this
           :bulb-started (now)
           :camera-state :exposing))
  (stop-bulb [this]
    (let [elapsed (- (now) (:bulb-started this))]
      (println "STOPPING")
      (assoc
       (dissoc this :bulb-started)
       :elapsed-exposure elapsed
       :camera-state :idle)))
  (get-last-raw [this]
    (byte-array [(byte 0x43) 
                 (byte 0x6c)
                 (byte 0x6f)
                 (byte 0x6a)
                 (byte 0x75)
                 (byte 0x72)
                 (byte 0x65)
                 (byte 0x21)])))

(defn init-DCC-sim []
  (->DCC-sim [100 200 400 800 1600 3200 6400] :idle))

