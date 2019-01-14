(ns darkmoor.core
  (:require [clojure.java.io :as io]))

;OPENING SEQUENCE__________________________________________________________

(defn open-title []
  (with-open [rdr (io/reader "resources/title.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (Thread/sleep 1500))

(defn open-intro []
  (with-open [rdr (io/reader "resources/intro.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (Thread/sleep 2000))

(defn open-menu []
  (with-open [rdr (io/reader "resources/menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

;LEVEL BUILDING___________________________________________________________
;;haha oh god what am i doing
;;1-temple 2-inn 3-general store 4-mansion 5-house 6-ruined building1 7-object in road

;make a func out of these that take a vector set (level maps) and return their mappings
(def loc-1-0
  {:desc "You're inside the temple." :exit {:map loc-1 :start-coords {:row 0 :col 3}}})
(def loc-2-0
  {:desc "You're inside the inn." :exit {:map loc-2 :start-coords {:row 2 :col 0}}})
(def loc-2-1
  {:desc "You moved a bit."})
(def loc-3-0
  {:desc "You're inside the store." :exit {:map loc-3 :start-coords {:row 3 :col 1}}})
(def loc-4-0
  {:desc "You're inside the mansion." :exit {:map loc-4 :start-coords {:row 1 :col 1}}})
(def loc-5-0
  {:desc "You're inside the house." :exit {:map loc-5 :start-coords {:row 3 :col 2}}})
(def loc-6-0
  {:desc "You're inside the ruins." :exit {:map loc-6 :start-coords {:row 2 :col 4}}})

(def l1m-indoors-1
  [[loc-1-0]])

(def l1m-indoors-2
  [[loc-2-0 loc-2-1]])

(def l1m-indoors-3
  [[loc-3-0]])

(def l1m-indoors-4
  [[loc-4-0]])

(def l1m-indoors-5
  [[loc-5-0]])

(def l1m-indoors-6
  [[loc-6-0]])

(def loc-0 
  {:desc "You're in the mud. People are staring at you. " :obj "" :enemy "" })

(def loc-1
  {:desc "You're at a temple. " :obj "There's a murky fountain in front of you. You hope it's not supposed to be holy water. " :enemy "A cultist attacks you! " :enter {:map l1m-indoors-1 :start-coords {:row 0 :col 0}}})

(def loc-2
  {:desc "You're at the inn. It smells like stale beer. " :obj "A forgotten mug, broken by time, lies by the door. " :enemy "A drunk guy starts hurling insults at you. " :enter {:map l1m-indoors-2 :start-coords {:row 0 :col 0}}})

(def loc-3
  {:desc "You're at the decrepit general store. " :obj "Barrels of decaying grains have been shoved against the wall. " :enemy "" :enter {:map l1m-indoors-3 :start-coords {:row 0 :col 0}}})

(def loc-4
  {:desc "You're at a fancy mansion. " :obj "You can see a torn letter lying in the nearby bushes. " :enemy "A guard attacks! " :enter {:map l1m-indoors-4 :start-coords {:row 0 :col 0}}})

(def loc-5
  {:desc "This house is plain. " :obj "" :enemy "A dog bites your ankle. Ow. " :enter {:map l1m-indoors-5 :start-coords {:row 0 :col 0}}})

(def loc-6 
  {:desc "This building looks like it burned down long ago. You wonder why it hasn't been restored. " :obj "You find a blackened axe in the rubble. " :enemy "A rat bites you! " :enter {:map l1m-indoors-6 :start-coords {:row 0 :col 0}}})

(def loc-7
  {:desc "You're in the street. " :obj "A strange coin is pressed into the mud. " :enemy "" })

(def level-1-map
  [[loc-0 loc-0 loc-0 loc-1 loc-0]
   [loc-0 loc-4 loc-0 loc-0 loc-6]
   [loc-2 loc-0 loc-0 loc-7 loc-0]
   [loc-0 loc-3 loc-5 loc-0 loc-0]])

;PC LOCATION______________________________________________________________________

(def init-pc-loc
  {:row 2 :col 0})   

(def init-map
  level-1-map)

(defn get-pc-loc [pc-loc current-map]
  (get-in current-map [(:row pc-loc) (:col pc-loc)]))

(defn print-loc-desc [pc-loc current-map]
  (println (:desc (get-pc-loc pc-loc current-map))))

(defn print-loc-obj [pc-loc current-map]
  (if (:obj (get-pc-loc pc-loc current-map))
  (println (:obj (get-pc-loc pc-loc current-map)))))

(defn print-loc-enemy [pc-loc current-map]
  (if (:enemy (get-pc-loc pc-loc current-map))
  (println (:enemy (get-pc-loc pc-loc current-map)))))

;USER MOVEMENT COMMANDS______________________________________________________

;parses user input for movement
(defn attempt-get-user-input [pc-loc]
  (let [input (read-line)]
    (cond 
      (= input "n") (do (println "") (assoc pc-loc :row (dec (:row pc-loc))))  
      (= input "s") (do (println "") (assoc pc-loc :row (inc (:row pc-loc))))
      (= input "e") (do (println "") (assoc pc-loc :col (inc (:col pc-loc))))
      (= input "w") (do (println "") (assoc pc-loc :col (dec (:col pc-loc))))
      (= input "q") (System/exit 0)
      (= input "m") (do (open-menu) pc-loc)
      :else (do (println "That was not a valid choice.") pc-loc))))

;parses user input to enter new map
(defn attempt-enter-building [pc-loc current-map]
  (let [input (read-line)]
      (cond
        (= input "y") (do 
                        ;(println "Is this printing?")
                        ;(print pc-loc)
                        ;(print current-map)
                        ;(print (get-pc-loc pc-loc current-map))
                        (println "Entering building.") 
                        ;(println (:start-coords (:enter (get-pc-loc pc-loc current-map))))
                        [(:start-coords (:enter (get-pc-loc pc-loc current-map))) (:map (:enter (get-pc-loc pc-loc current-map)))])
        (= input "x") (do (println "Not entering.") [pc-loc current-map])
        :else (do (println "That was not a valid choice.") [pc-loc current-map]))))

(defn print-move-options []
  (print "
             ________________________
           =(____   ___      __     _)=
             |                      |
             | Type n to move North |
             | Type s to move South |
             | Type e to move East  |
             | Type w to move West  |
             | Type m to open Menu  |
             |__    ___   __    ___ |
           =(________________________)=")
  (println))

;looks up where player can move based on map they are in
(defn get-user-input [pc-loc current-map]
  (print-move-options)
  (let [new-loc (attempt-get-user-input pc-loc)]
    ;(println new-loc)
    ;(println (:desc (get-pc-loc new-loc current-map)))
    (if (:desc (get-pc-loc new-loc current-map))
      new-loc
    (do
      (println "You cannot go that way.")
      ;(println (:desc (get-pc-loc new-loc current-map)))
      pc-loc))))

(defn print-enter-building []
  (print "
            ________________________
          =(____   ___      __     _)=
            |                      |
            | Would you like to    |
            | enter this building? |
            |                      |
            | Type y to Enter      |
            | Type x to move away  |
            |__    ___   __    ___ |
          =(________________________)=")
  (println))

;this is the main move func, calls get-user-input now
(defn change-map [pc-loc current-map]
  (if (:enter (get-pc-loc pc-loc current-map))
    (do
      (print-enter-building)
      (let [[new-loc new-map] (attempt-enter-building pc-loc current-map)]
        (if (= new-map current-map)
          [(get-user-input pc-loc current-map) current-map]
          [new-loc new-map])))
    [(get-user-input pc-loc current-map) current-map]))

;MAIN_____________________________________________________________________

(defn -main []
  (open-title)
  (open-intro)
  (open-menu)
  (loop [pc-loc init-pc-loc current-map init-map]
    ;new-loc is bound to first item in vector returned from change-map
      (do (print-loc-desc pc-loc current-map)
          (print-loc-obj pc-loc current-map)
          (print-loc-enemy pc-loc current-map)
          (let [[new-loc current-map]
                (change-map pc-loc current-map)]
            (recur new-loc current-map)))))
