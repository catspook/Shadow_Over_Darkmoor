; The Shadow Over Darkmoor
; copywright (c) CMR 2019-2020 

(ns darkmoor.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as cstr])
  (:require [clojure.edn :as edn])
  (:gen-class))

(load "model/enemy-data")
(load "model/objects-data")
(load "model/location-data")
(load "model/player-data")
(load "model/model")
(load "view")
(load "controller")

;MAIN

(defn -main []
  (open-title)
  (open-intro)
  (loop [player init-player map-stack init-map-stack loc-info init-loc-info]
    (let [updated-loc-info (put-loot-in-new-locs player map-stack loc-info)]
      (do 
        (clear-screen)
        (let [[new-player new-map-stack new-loc-info] (main-menu player map-stack updated-loc-info)]
          (recur new-player new-map-stack new-loc-info))))))  
