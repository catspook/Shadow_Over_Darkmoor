(ns darkmoor.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s]))

(def debug
  true)

(defn print-debug [thing]
  (if debug
  (println thing)))

;OPENING SEQUENCE__________________________________________________________

(defn clear-screen []
  "clears screen using ANSI escape sequence"
  (print (str (char 27) "[2J"))) 

(defn pause []
  "pauses by asking for user input"
  (println "Press any key to continue.")
  (read-line))

(defn pause-screen2 []
  "pauses thread for 2 seconds"
  (Thread/sleep 2000))

(defn pause-screen1-5 []
  "pauses thread for 1.5 seconds"
  (Thread/sleep 1500))

(defn pause-screen1 []
  "pauses thread for 1 second"
  (Thread/sleep 1000))

(defn open-title []
  "opens title sequence, pauses screen for time to read it, then clears screen"
  (print (str (char 27) "[2J")) ;clear screen using ANSI escape
  (with-open [rdr (io/reader "resources/title.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause-screen2)
  (clear-screen))

(defn open-main-menu []
  "opens menu. Eventually will list all commands"
  (with-open [rdr (io/reader "resources/main-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-intro []
  "opens intro paragraph, pauses screen, then clears screen"
  (with-open [rdr (io/reader "resources/intro.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (open-main-menu)
  (pause)
  (clear-screen))

;OBJECT DATA____________________________________________________________________________

;general obejcts
(def o1-gen
  {:desc "Thing2"})
(def o2-gen
  {:desc "Thing2"})
(def o3-gen
  {:desc "Thing3"})
(def o4-gen
  {:desc "Thing4"})
(def o5-gen
  {:desc "Thing5"})
(def o6-gen
  {:desc "Thing6"})
(def o7-gen
  {:desc "Thing7"})
(def o8-gen
  {:desc "Thing8"})
(def o9-gen
  {:desc "Thing9"})
(def o10-gen
  {:desc "Thing10"})

;for place-obj
(def gen-vec
  [o1-gen o2-gen o3-gen o4-gen o5-gen o6-gen o7-gen o8-gen o9-gen o10-gen])

(defn place-obj [obj-list amt]
  (vec (take amt (repeatedly #(nth obj-list (rand-int (count obj-list)))))))

(def init-pc-inv
  [])

(def init-pc-eq
  [])

;LEVEL BUILDING___________________________________________________________
;1-temple 2-inn 3-general store 4-mansion 5-house 6-ruined building1 7-object in road

;interior location data maps
;exit-start-coords maps to what location the player should be off after popping of the map corresponding to each location
(def loc-1-0
  {:obj (ref (place-obj gen-vec 6)) :desc "You're inside the temple." :exit-start-coords {:row 0 :col 3}})
(def loc-2-0
  {:obj (ref (place-obj gen-vec 10)) :desc "You're inside the tavern." :exit-start-coords {:row 2 :col 0}})
(def loc-2-1
  {:obj (ref (place-obj gen-vec 3)) :desc "You moved a bit."})
(def loc-3-0
  {:obj (ref (place-obj gen-vec 5)) :desc "You're inside the store." :exit-start-coords {:row 3 :col 1}})
(def loc-4-0
  {:obj (ref (place-obj gen-vec 1)) :desc "You're inside the mansion." :exit-start-coords {:row 1 :col 1}})
(def loc-5-0
  {:obj (ref (place-obj gen-vec 1)) :desc "You're inside the house." :exit-start-coords {:row 3 :col 2}})
(def loc-6-0
  {:obj (ref (place-obj gen-vec 2)) :desc "You're inside the ruins." :exit-start-coords {:row 1 :col 4}})

;interior matrixes. Will be bigger one day.
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

;maps of location data, accessed by keywords
;obj and enemy are place holders right now
;:enter means there is a matrix map that can be loaded from that location.
;:map is the matrix that should be loaded (pushed onto the stack) 
;:start-coords is the location the player should start at in the new map
;FIXME it looks like we might need locs for every cell if we want unique objects.
(def loc-0 
  {:obj (ref (place-obj gen-vec 3)) :desc "You're in the mud. People are staring at you. " :enemy ""})

(def loc-1
  {:obj (ref (place-obj gen-vec 2)) :desc "You're at a temple. " :enemy "A cultist attacks you! " :enter {:goto l1m-indoors-1 :start-coords {:row 0 :col 0}}})

(def loc-2
  {:obj (ref (place-obj gen-vec 1)) :desc "You're at the tavern. It smells like stale beer. " :enemy "A drunk guy starts hurling insults at you. " :enter {:goto l1m-indoors-2 :start-coords {:row 0 :col 0}}})

(def loc-3
  {:obj (ref (place-obj gen-vec 1)) :desc "You're at the decrepit general store. " :enemy "" :enter {:goto l1m-indoors-3 :start-coords {:row 0 :col 0}}})

(def loc-4
  {:obj (ref (place-obj gen-vec 2)) :desc "You're at a fancy mansion. " :enemy "A guard attacks! " :enter {:goto l1m-indoors-4 :start-coords {:row 0 :col 0}}})

(def loc-5
  {:obj (ref (place-obj gen-vec 3)) :desc "This house is plain. " :enemy "A dog bites your ankle. Ow. " :enter {:goto l1m-indoors-5 :start-coords {:row 0 :col 0}}})

(def loc-6 
  {:obj (ref (place-obj gen-vec 3)) :desc "This building looks like it burned down long ago. You wonder why it hasn't been restored. " :enemy "A rat bites you! " :enter {:goto l1m-indoors-6 :start-coords {:row 0 :col 0}}})

(def loc-7
  {:obj (ref (place-obj gen-vec 2)) :desc "You're in the street. " :enemy "" })

;level map is a matrix, each cell corresponds to a location data map
(def level-1-map
  [[loc-0 loc-0 loc-0 loc-1 loc-0]
   [loc-0 loc-4 loc-0 loc-0 loc-6]
   [loc-2 loc-0 loc-0 loc-7 loc-0]
   [loc-0 loc-3 loc-5 loc-0 loc-0]])

;PC LOCATION_________________________________________________________________________________

;initial player location in the first map, needed for loop recur
(def init-pc-loc
  {:row (rand-int 4) :col (rand-int 3)})   

;it's a list, acts as a stack of maps
;init needed for loop recur
(def init-map-stack
  (list level-1-map))

(defn get-pc-loc [pc-loc map-stack]
  "pulls location data using first map (current) in stack
  using the row and column of player's location"
  (get-in (first map-stack) [(:row pc-loc) (:col pc-loc)]))

(defn print-loc-desc [pc-loc map-stack]
  "prints location description, using get-pc-loc"
  (println (:desc (get-pc-loc pc-loc map-stack))))

;FIXME placeholder
(defn print-loc-enemy [pc-loc map-stack]
  "prints enemy descriptions"
  (if (:enemy (get-pc-loc pc-loc map-stack))
    (println (:enemy (get-pc-loc pc-loc map-stack)))))

;PC HEALTH AND DAMAGE________________________________________________________________________

(def init-pc-health
  10)

(defn print-pc-health [pc-health]
  (print "             Your max health is ") (print pc-health))

(def init-pc-damage
  10)

(defn print-pc-damage [pc-damage]
  (println) (print "             Your max damage is ") (print pc-damage) (println) (println))

;OBJECT OPTIONS______________________________________________________________________________

(defn get-obj [pc-loc map-stack]
  (deref (get (get-pc-loc pc-loc map-stack) :obj)))

(defn get-obj-ref [pc-loc map-stack]
  (get (get-pc-loc pc-loc map-stack) :obj))

;adding items to inventory____________________________________________________

(defn add-obj-to-inv [pc-loc map-stack pc-inv int-input]
  (println)
  (print "That item has been added to your inventory.")
  (println)
  (print-debug pc-inv)
  (vec (conj pc-inv (nth (get-obj pc-loc map-stack) (dec int-input)))))

(defn remove-obj-from-loc [pc-loc map-stack int-input]
  (let [pre-obj (subvec (get-obj pc-loc map-stack) 0 (dec int-input))
        post-obj  (subvec (get-obj pc-loc map-stack) int-input)]
    (dosync
      (ref-set (get-obj-ref pc-loc map-stack) (vec (concat pre-obj post-obj))))))

(defn add-to-inv [pc-loc map-stack pc-inv]
  (if (= [] (get-obj pc-loc map-stack))
    (do (println) (println "There is nothing here to add.") (println) (pause) (print-debug pc-inv) pc-inv)
    (do
      (println) (println "What would you like to add? Enter the object number.") (println)
      (let [input (read-line)]
        (let [int-input (Integer/parseInt input)]
          (let [new-pc-inv (add-obj-to-inv pc-loc map-stack pc-inv int-input)]
            (print-debug new-pc-inv)
            (remove-obj-from-loc pc-loc map-stack int-input)
            (print-debug new-pc-inv)
            (pause)
            new-pc-inv))))))

;print object options_____________________________________________________

(defn print-obj-item [item]
  (print "                    ")
  (print item)
  (println))

(defn print-obj [pc-loc map-stack]
  (if (= [] (get-obj pc-loc map-stack))
    (println "                    You look around, but nothing catches your eye.")
    (doseq [item (map :desc (get-obj pc-loc map-stack))]
      (print-obj-item item))))

(defn open-potions []
  (with-open [rdr (io/reader "resources/potions.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-objects []
  (with-open [rdr (io/reader "resources/objects.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn print-obj-commands [pc-loc map-stack]
  (clear-screen)
  (open-objects)
  (print-obj pc-loc map-stack)
  (open-potions))

(defn obj-control [pc-loc map-stack pc-inv]
  "prints object descriptions that are in current location"
  (print-obj-commands pc-loc map-stack)
  (print-debug pc-inv)
  (let [input (read-line)]
    (cond
      ;FIXME here???
      (= input "x") pc-inv
      (= input "a") (add-to-inv pc-loc map-stack pc-inv)
      :else (do (println "That's not a valid choice.") (pause) (print-debug pc-inv) pc-inv))))

;INVENTORY________________________________________________________________________

;removing items from inventory_______________________________________________

;FIXME not working, pc-inv and pc-eq would also need to be ref'd
(defn add-item-to-loc [pc-loc map-stack pc-inv int-input]
  (println "The problem is here?")
  (print-debug pc-inv)
  (dosync
    (alter (get-obj-ref pc-loc map-stack) conj  (nth pc-inv (dec int-input)))))

(defn remove-item-from-inv [pc-loc map-stack pc-inv]
  "removes a user-defined item from inventory"
  (println)
  (println "What item do you want to drop? Enter the item number.")
  (let [input (read-line)]
    (let [int-input (Integer/parseInt input)]
      (do
        (print-debug pc-inv)
        (add-item-to-loc pc-loc map-stack pc-inv int-input)
        (let [pre-inv (subvec pc-inv 0 (dec int-input))
              post-inv (subvec pc-inv int-input)]
          (print-debug pre-inv)
          (print-debug post-inv)
          (vec (concat pre-inv post-inv)))))))


;equip and unequip___________________________________________________

(defn print-eq [item]
  (print "                    ")
  (print (:desc item))
  (println))

(defn print-pc-eq [pc-eq]
  (if (= pc-eq [])
    (println "             You have no items equipped.")
    (doseq [item pc-eq]
      (print-eq item))))

(defn equip-item [pc-inv pc-eq pc-health pc-damage]
  (println)
  (if (= [] pc-inv)
    (do (println "There is nothing to equip.") pc-eq)
    (do
      (println "What item do you want to equip? Enter the item number.")
      (println) 
      (let [input (read-line)]
        (let [int-input (Integer/parseInt input)]
          ;FIXME needs to return a vector of this, health, and damage
          [(vec (conj pc-eq (nth pc-inv (dec int-input)))) pc-health pc-damage])))))

(defn unequip-item [pc-eq pc-health pc-damage]
  "removes a user-defined item from inventory"
  (if (= [] pc-eq)
    (do (println "There is nothing to unequip.") pc-eq)
    (do
      (println)
      (println "What item do you want to unequip? Enter the item number.")
      (println)
      (let [input (read-line)]
        (let [int-input (Integer/parseInt input)]
          (let [pre-eq (subvec pc-eq 0 (dec int-input))
                post-eq (subvec pc-eq int-input)]
            ;FIXME needs to return a vector of this, health, and damage
            [(vec (concat pre-eq post-eq)) pc-health pc-damage]))))))

;print inventory____________________________________________________

;FIXME doesn't fucking work
(defn in? [coll elem]  
  "true if coll contains elem"
  "in this case, coll is pc-eq and elem is pc-inv item"
  (some #(= elem %) coll))

;FIXME doens't fucking work
(defn list-contains? [coll value]
  (let [s (seq coll)]
    (if s
      (if (= (first s) value) true (recur (rest s) value))
      false)))

;FIXME doesn't fucking work
(defn is-it-eq [pc-eq item]
  "checks if an item is in pc-eq
  if yes, prints ' ** ' otherwise ' -- ' "
  ;(if (in? pc-eq item) 
  (if (list-contains? pc-eq item)
    (print " ** ")
    (print "    ")))

;FIXME doens't fucking work now, returns nothing but -1 
(defn get-index-of [coll elem]
  "returns the index of an elem"
  "in this case, coll is pc-inv and elem is pc-inv item"
  (print (.indexOf coll elem)))

(defn print-item [item]
  (print "                    ")
  (print (:desc item))
  (println))

(defn print-pc-inv [pc-inv pc-eq]
  (if (= pc-inv [])
    (println "             Your inventory is currently empty.")
    (do
      (print-debug pc-inv)
      (doseq [item  pc-inv]
      (print-item item)))))

(defn open-inv-menu []
  "opens inv menu"
  (with-open [rdr (io/reader "resources/inv-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-sword []
  "opens sword"
  (with-open [rdr (io/reader "resources/sword.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn print-inv-commands [pc-inv pc-eq pc-health pc-damage]
  (clear-screen)
  (open-inv-menu)
  (open-sword)
  ;FIXME
  (print-pc-health pc-health)
  (print-pc-damage pc-damage)
  (println "             Your inventory contains the following items:")
  (print-pc-inv pc-inv pc-eq)
  (println)
  (println "             You have equipped the following items:")
  (print-pc-eq pc-eq)
  (println))

(defn inv-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage]
  (print-inv-commands pc-inv pc-eq pc-health pc-damage)
  (let [input (read-line)]
    (cond
      (= input "x") [pc-inv pc-eq pc-health pc-damage]
      (= input "r") (let [new-pc-inv (remove-item-from-inv pc-loc map-stack pc-inv)]
                     [new-pc-inv pc-eq pc-health pc-damage])
      (= input "e") (let [[new-pc-eq new-pc-health new-pc-damage] (equip-item pc-inv pc-eq pc-health pc-damage)]
                      [pc-inv new-pc-eq new-pc-health new-pc-damage])
      (= input "u") (let [[new-pc-eq new-pc-health new-pc-damage] (unequip-item pc-eq pc-health pc-damage)]
                      [pc-inv new-pc-eq new-pc-health new-pc-damage])
      :else (do (println "That's not a valid choice.") (pause) [pc-inv pc-eq pc-health pc-damage]))))

;MENU AND USER OPTIONS________________________________________________________________________________________

;moving normally________________________________________

(defn parse-user-input [pc-loc map-stack pc-inv pc-eq pc-health pc-damage]
  "called from check-move. Parses user input.
  movement: dec/inc returns a new value that is one greater than the value passed in.
    assoc returns a new map of pc coordinates that is updated according to inc/dec
  objects: objects, loot, and inventory are placeholders
 'q' quits program (system call)"
  (let [input (read-line)]
    (cond
      (= input "n") (do (println "You move North.") (pause) [(assoc pc-loc :row (dec (:row pc-loc))) pc-inv pc-eq pc-health pc-damage])
      (= input "s") (do (println "You move South.") (pause) [(assoc pc-loc :row (inc (:row pc-loc))) pc-inv pc-eq pc-health pc-damage])
      (= input "e") (do (println "You move East.") (pause) [(assoc pc-loc :col (inc (:col pc-loc))) pc-inv pc-eq pc-health pc-damage]) 
      (= input "w") (do (println "You move West.") (pause) [(assoc pc-loc :col (dec (:col pc-loc))) pc-inv pc-eq pc-health pc-damage]) 
      (= input "m") (do (open-main-menu) (println) (pause) [pc-loc pc-inv pc-eq pc-health pc-damage]) 
      (= input "l") (do (println "There are no enemies yet, so there is no loot.") (println) (pause) [pc-loc pc-inv pc-eq pc-health pc-damage]) 
      (= input "o") (let [new-pc-inv (obj-control pc-loc map-stack pc-inv)]
                      [pc-loc new-pc-inv pc-eq pc-health pc-damage])
      (= input "i") (let [[new-pc-inv new-pc-eq new-pc-health new-pc-damage] (inv-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage)]
                      [pc-loc new-pc-inv new-pc-eq new-pc-health new-pc-damage])
      (= input "q") (System/exit 0) 
      :else (do (println "That's not a valid choice.") (pause) [pc-loc pc-inv pc-eq pc-health pc-damage]))))

(defn print-user-menu []
  (with-open [rdr (io/reader "resources/user-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn check-move [pc-loc map-stack pc-inv pc-eq pc-health pc-damage]
  "takes pc-loc player coordinates and the map stack
  prints a menu of options for the user to see
  then calls parse-user-input
    parse-user-input returns an updated set of PC coordinates
  If a description can be pulled from the new pc-loc (from location data stored in map)
    the new move is valid and the new pc-loc is returned (along with the unchanged map stack). 
  If not, it's not a valid move, and the old pc-loc is returned (along with the unchanged map stack)"
  (print-user-menu)
  (let [[new-loc new-pc-inv new-pc-eq new-pc-health new-pc-damage] (parse-user-input pc-loc map-stack pc-inv pc-eq pc-health pc-damage)]
    ;(println new-pc-inv)
    (if (:desc (get-pc-loc new-loc map-stack))
      [new-loc map-stack new-pc-inv new-pc-eq new-pc-health new-pc-damage]
      (do
        (if (not= (first map-stack) (get level-1-map :map))
          (println "There is a wall in that direction. You move back to where you were.")
          (println "You find yourself at the edge of the village and turn back to where you started."))
        (pause)
        [pc-loc map-stack new-pc-inv new-pc-eq new-pc-health new-pc-damage]))))

;entering______________________________________________

(defn check-push-map [pc-loc map-stack]
  "called from push-control, takes in pc-loc and map-stack
  y: player wants to enter new location
     the new player coordinates are the start coordinates stored in the old locations's :enter keyword
     the new map (in the old locations's :enter keyword) is pushed onto the map stack
     true is returned (because map change is true)
  n/else: old player location, old map, and "false" (because map change is false) is returned"
  (let [input (read-line)]
      (cond
        ;new map is pushed on, so change is "true"
        (= input "y") [(:start-coords (:enter (get-pc-loc pc-loc map-stack)))
                       (conj map-stack (:goto (:enter (get-pc-loc pc-loc map-stack))))
                       true]
        ;old map is returned, so change is "false"
        (= input "x") [pc-loc map-stack false]
        :else (do (println "That was not a valid choice.") (pause) [pc-loc map-stack false]))))

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

(defn push-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage]
  "called from display-menu, takes in pc-loc and map-stack
  calls check-push-map, which returns an updated pc-coordinate location and map stack, and either true or false
  if true, new map has been loaded and the new pc-coordinates and map stack are returned to loop recur
  if false, calls noromal movement functions (check-move)"
  (print-enter pc-loc map-stack)
  (let [[new-loc new-map-stack true-false] (check-push-map pc-loc map-stack)]
    ;will be true if a new map was pushed on 
    (if true-false
      ;load new map
      [new-loc new-map-stack pc-inv pc-eq pc-health pc-damage]
      ;move normally
      (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage))))

;exiting_____________________________________________

(defn check-pop-map [pc-loc map-stack]
  "called from pop-control, takes pc-loc and map-stack
  if y: returns coordinates player should be at in old map, and pops off a map from the stack 
        (as newest map is the first in the stack, returns stack-first element)
        returns true as a map change is "true"
  if n: returns old pc-location, old map-stack, and "false""
  (let [input (read-line)]
    (cond
      ;change is made (popping off a map), so "true"
      (= input "y") [(:exit-start-coords (get-pc-loc pc-loc map-stack)) (rest map-stack) true]
      ;no change is made to map, so "false"
      (= input "x") [pc-loc map-stack false]
      :else (do (println "That was not a valid choice. What else would you like to do at this location?") (pause) [pc-loc map-stack false]))))

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

(defn pop-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage]
  "called from display-menu, takes in pc-loc and map-stack
  called from check-pop-map, which returns updated pc-loc, map stack, and either true/false
  if true, returns updated pc-loc and map-stack to loop-recur
  if false, calls normal movement functions (check-move)"
  (print-exit)
  ;let [things returned from function] (function and arguements) 
  ;    enables new values to be compared to old values
  ;    need to do it this way because pc-loc itself is not a variable and cannot be changed like one
  (let [[new-loc new-map-stack true-false] (check-pop-map pc-loc map-stack)]
    ;will be true if a map was popped off
    (if true-false
      ;load new map
      [new-loc new-map-stack pc-inv pc-eq pc-health pc-damage]
      ;move normally
      (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage)))) 

;getting and parsing user input_____________________________
(defn display-menu [pc-loc map-stack pc-inv pc-eq pc-health pc-damage]
  "checks to see if a place can be entered or exited from current player location.
  if yes, gives player the option to enter/exit
  if no, just displays normal movement functions (check-move)"
  (cond
    (:enter (get-pc-loc pc-loc map-stack)) (push-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage)
    (:exit-start-coords (get-pc-loc pc-loc map-stack)) (pop-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage)
    :else (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage)))

;MAIN_____________________________________________________________________

(defn -main []
  "open- functions are all for start of game, pretty things for user
  loop-recur: takes in an initial pc coordinate set and an inital map stack
  displays description of location
  enemy function is a placeholder
  display-menu returns an updated pc-loc and map-stack
  recur takes those values and passes those to the top of the loop
  loop-recur acts like a recursive function: 
        takes in inital values, calls other functions that send updated pc-loc and map-stack to registers
        and then recur calls itself again, using what is in registers"
  ;these are function calls
  (open-title)
  (open-intro)
  (open-main-menu)
  (loop [pc-loc init-pc-loc map-stack init-map-stack pc-inv init-pc-inv pc-eq init-pc-eq pc-health init-pc-health pc-damage init-pc-damage]
      (do (clear-screen)
          ;(function arguments)
          (print-loc-desc pc-loc map-stack)
          (print-loc-enemy pc-loc map-stack) 
          (let [[new-loc map-stack new-pc-inv new-pc-eq new-pc-health new-pc-damage] (display-menu pc-loc map-stack pc-inv pc-eq pc-health pc-damage)]
            (recur new-loc map-stack new-pc-inv new-pc-eq new-pc-health new-pc-damage)))))
