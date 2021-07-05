; The Shadow Over Darkmoor
; copywright (c) Casper Rutz 2019-2021

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

; this should be abstracted out of this module

(defn print-class-choice []
  (print "p, s, h, n, b?"))

(defn parse-class-input [init-player]
  (clear-screen)
  (print-class-choice)
  (let [input (read-line)]
    (cond
      (= input "p") (assoc init-player :class paladin :eq (:init-eq paladin) :inv (:init-inv paladin))
      (= input "s") (assoc init-player :class sorcerer :eq (:init-eq sorcerer) :inv (:init-inv sorcerer))
      (= input "h") (assoc init-player :class hunter :eq (:init-eq hunter) :inv (:init-inv hunter))
      (= input "n") (assoc init-player :class necromancer :eq (:init-eq necromancer) :inv (:init-inv necromancer))
      (= input "b") (assoc init-player :class brawler :eq (:init-eq brawler) :inv (:init-inv brawler))
      :else (parse-class-input init-player))))

(defn -main []
  (open-title)
  (open-intro)

  (let [init-player-with-class (parse-class-input init-player)]

    (loop [player init-player-with-class map-stack init-map-stack loc-info init-loc-info]
      (let [updated-loc-info (put-loot-in-new-locs player map-stack loc-info)]
        (do 
          (print (:class player))
          (clear-screen)
          (let [[new-player new-map-stack new-loc-info] (main-menu player map-stack updated-loc-info)]
            (recur new-player new-map-stack new-loc-info)))))))
