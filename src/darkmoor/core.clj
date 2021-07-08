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

(defn integrate-class-info [init-player class-choice]
  (pause)
  (clear-screen)
  (assoc init-player :class class-choice 
                     :health (:init-health class-choice)
                     :damage (:init-damage class-choice)
                     :eq (:init-eq class-choice) 
                     :inv (:init-inv class-choice)))

(defn print-class-choice []
  (println "CHOOSE YOUR CLASS")
  (println)
  (println "p, s, h, n, b?"))

(defn parse-class-input [init-player]
  (clear-screen)
  (print-class-choice)
  (let [input (read-line)]
    (cond
      (= input "p") (integrate-class-info init-player paladin)
      (= input "s") (integrate-class-info init-player sorcerer)
      (= input "h") (integrate-class-info init-player hunter)
      (= input "n") (integrate-class-info init-player necromancer)
      (= input "b") (integrate-class-info init-player brawler)
      :else (parse-class-input init-player))))

(defn -main []
  (open-title)
  (open-intro)

  (let [init-player-with-class (parse-class-input init-player)]

    (loop [player init-player-with-class map-stack init-map-stack loc-info init-loc-info]
      (let [updated-loc-info (put-loot-in-new-locs player map-stack loc-info)]
        (do 
          (clear-screen)
          (let [[new-player new-map-stack new-loc-info] (main-menu player map-stack updated-loc-info)]
            (recur new-player new-map-stack new-loc-info)))))))
