(ns darkmoor.core
  (:require [clojure.java.io :as io]))

;OPENING SEQUENCE__________________________________________________________

(defn open-title []
  (print (str (char 27) "[2J")) ;clear screen using ANSI escape
  (with-open [rdr (io/reader "resources/title.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  ;(Thread/sleep 1500)
  (print (str (char 27) "[2J"))) ;clear screen using ANSI escape

(defn open-intro []
  (with-open [rdr (io/reader "resources/intro.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  ;(Thread/sleep 2000)
  (print (str (char 27) "[2J"))) ;clear screen using ANSI escape

(defn open-main-menu []
  (with-open [rdr (io/reader "resources/main-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  ;(Thread/sleep 2000)
  (print (str (char 27) "[2J"))) ;clear screen using ANSI escape

;LEVEL BUILDING___________________________________________________________
;;1-temple 2-inn 3-general store 4-mansion 5-house 6-ruined building1 7-object in road

(def loc-1-0
  {:desc "You're inside the temple." :exit-start-coords {:row 0 :col 3}})
(def loc-2-0
  {:desc "You're inside the tavern." :exit-start-coords {:row 2 :col 0}})
(def loc-2-1
  {:desc "You moved a bit."})
(def loc-3-0
  {:desc "You're inside the store." :exit-start-coords {:row 3 :col 1}})
(def loc-4-0
  {:desc "You're inside the mansion." :exit-start-coords {:row 1 :col 1}})
(def loc-5-0
  {:desc "You're inside the house." :exit-start-coords {:row 3 :col 2}})
(def loc-6-0
  {:desc "You're inside the ruins." :exit-start-coords {:row 1 :col 4}})

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
  {:desc "You're in the mud. People are staring at you. " :obj "" :enemy "" :exit false})

(def loc-1
  {:desc "You're at a temple. " :obj "There's a murky fountain in front of you. You hope it's not supposed to be holy water. " :enemy "A cultist attacks you! " :enter {:map l1m-indoors-1 :start-coords {:row 0 :col 0}}})

(def loc-2
  {:desc "You're at the tavern. It smells like stale beer. " :obj "A forgotten mug, broken by time, lies by the door. " :enemy "A drunk guy starts hurling insults at you. " :enter {:map l1m-indoors-2 :start-coords {:row 0 :col 0}}})

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
  {:row 0 :col 0})   

;it's a list
(def init-map-stack
  (list level-1-map))

(defn get-pc-loc [pc-loc map-stack]
  (get-in (first map-stack) [(:row pc-loc) (:col pc-loc)]))

(defn print-loc-desc [pc-loc map-stack]
  (println (:desc (get-pc-loc pc-loc map-stack))))

(defn print-loc-obj [pc-loc map-stack]
  (if (:obj (get-pc-loc pc-loc map-stack))
    (println (:obj (get-pc-loc pc-loc map-stack)))))

(defn print-loc-enemy [pc-loc map-stack]
  (if (:enemy (get-pc-loc pc-loc map-stack))
    (println (:enemy (get-pc-loc pc-loc map-stack)))))

;MENU AND USER OPTIONS________________________________________________________________________________________

;moving normally________________________________________
(defn parse-user-input [pc-loc map-stack]
  (let [input (read-line)]
    (cond
      (= input "n") (do (println "You move North.") (Thread/sleep 1000) (assoc pc-loc :row (dec (:row pc-loc))))
      (= input "s") (do (println "You move South.") (Thread/sleep 1000) (assoc pc-loc :row (inc (:row pc-loc))))
      (= input "e") (do (println "You move East.") (Thread/sleep 1000) (assoc pc-loc :col (inc (:col pc-loc)))) 
      (= input "w") (do (println "You move West.") (Thread/sleep 1000) (assoc pc-loc :col (dec (:col pc-loc)))) 
      (= input "o") (do (print-loc-obj pc-loc map-stack) (Thread/sleep 2000) pc-loc)
      (= input "m") (do (open-main-menu) (Thread/sleep 1000) pc-loc) 
      (= input "l") (do (println "There are no enemies yet, so there is no loot.") (Thread/sleep 1000) pc-loc) 
      (= input "i") (do (println "You do not have an inventory yet.") (Thread/sleep 1000) pc-loc)
      (= input "q") (System/exit 0) 
      :else (do (println "That's not a valid choice.") (Thread/sleep 1000) pc-loc))))

(defn print-user-menu []
  (with-open [rdr (io/reader "resources/user-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn check-move [pc-loc map-stack]
  (print-user-menu)
  (let [new-loc (parse-user-input pc-loc map-stack)]
    (if (:desc (get-pc-loc new-loc map-stack))
      [new-loc map-stack]
      (do
        (if (not= (first map-stack) level-1-map)
          (println "There is a wall in that direction. You move back to where you were.")
          (println "You find yourself at the edge of the village and turn back to where you started."))
        (Thread/sleep 2000)
        [pc-loc map-stack]))))

;entering______________________________________________
(defn push-map [pc-loc map-stack]
  (let [input (read-line)]
      (cond
        ;new map is pushed on, so change is "true"
        (= input "y") [
                       (:start-coords (:enter (get-pc-loc pc-loc map-stack)))
                       (conj map-stack
                             (:map (:enter (get-pc-loc pc-loc map-stack))))
                       true]
        ;old map is returned, so change is "false"
        (= input "x") [pc-loc map-stack false]
        :else (do (println "That was not a valid choice.") (Thread/sleep 1000) [pc-loc map-stack false]))))

(defn print-enter [pc-loc map-stack]
  (print "
            ________________________
          =(____   ___      __     _)=
            |                      |
            | You can enter a new  |
            |    area from here.   | 
            |                      |
            |   Type y to enter    |
            |  or x to stay here.  |
            |__    ___   __    ___ |
          =(________________________)=")
  (println))

(defn check-push-map [pc-loc map-stack]
  (do
    (print-enter pc-loc map-stack)
    (push-map pc-loc map-stack)))

(defn push-control [pc-loc map-stack]
  (let [[new-loc new-map-stack true-false] (check-push-map pc-loc map-stack)]
    ;will be true if a new map was pushed on 
    (if true-false
      ;load new map
      [new-loc new-map-stack]
      ;move normally
      (check-move pc-loc map-stack))))

;exiting_____________________________________________
(defn pop-map [pc-loc map-stack]
  (let [input (read-line)]
    (cond
      ;change is made (popping off a map), so "true"
      (= input "y") [(:exit-start-coords (get-pc-loc pc-loc map-stack)) (rest map-stack) true]
      ;no change is made to map, so "false"
      (= input "x") [pc-loc map-stack false]
      :else (do (println "That was not a valid choice. What else would you like to do at this location?") (Thread/sleep 2000) [pc-loc map-stack false]))))

(defn print-exit []
  (print  " 
            ________________________
          =(____   ___      __     _)=
            |                      |
            |  You can exit this   |
            | this area from here. |
            |                      |
            |    Type y to exit    |
            |    or x to stay.     |
            |__    ___   __    ___ |
          =(________________________)=")
  (println))

(defn check-pop-map [pc-loc map-stack]
  (print-exit)
  (pop-map pc-loc map-stack))

(defn pop-control [pc-loc map-stack]
  (let [[new-loc new-map-stack true-false] (check-pop-map pc-loc map-stack)]
    ;will be true if a map was popped off
    (if true-false
      ;load new map
      [new-loc new-map-stack]
      ;move normally
      (check-move pc-loc map-stack)))) 

;getting and parsing user input_____________________________
(defn display-menu [pc-loc map-stack]
  (cond
    (:enter (get-pc-loc pc-loc map-stack)) (push-control pc-loc map-stack)
    (:exit-start-coords (get-pc-loc pc-loc map-stack)) (pop-control pc-loc map-stack)
    :else (check-move pc-loc map-stack)))

;MAIN_____________________________________________________________________

(defn -main []
  (open-title)
  (open-intro)
  (open-main-menu)
  (loop [pc-loc init-pc-loc map-stack init-map-stack]
      (do (print (str (char 27) "[2J")) ;clear screen
          (print-loc-desc pc-loc map-stack)
          (print-loc-enemy pc-loc map-stack) 
          (let [[new-loc map-stack] (display-menu pc-loc map-stack)]
            (recur new-loc map-stack)))))
