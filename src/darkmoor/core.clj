(ns darkmoor.core
  (:require [clojure.java.io :as io]))

;OPENING SEQUENCE__________________________________________________________

(defn clear-screen []
  "clears screen using ANSI escape sequence"
  (print (str (char 27) "[2J"))) 

(defn pause-screen5 []
  "pauses thread for 5 seconds"
  (Thread/sleep 5000))

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

(defn open-intro []
  "opens intro paragraph, pauses screen, then clears screen"
  (with-open [rdr (io/reader "resources/intro.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause-screen1-5)
  (clear-screen))

;FIXME may be redundant?
(defn open-main-menu []
  "opens menu. Eventually will list all commands"
  (with-open [rdr (io/reader "resources/main-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause-screen5)
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

(def init-obj-map
  {:l1m {:l0 (place-obj gen-vec 2) :l1 (place-obj gen-vec 3) :l2 (place-obj gen-vec 2) :l3 (place-obj gen-vec 1) :l4 (place-obj gen-vec 2) :l5 (place-obj gen-vec 3) :l6 (place-obj gen-vec 2) :l7 (place-obj gen-vec 1)} :l1m-1 {:l1-0 (place-obj gen-vec 2)} :l1m-2 {:l2-0 (place-obj gen-vec 3) :l2-1 (place-obj gen-vec 2)} :l1m-3 {:l3-0 (place-obj gen-vec 1)} :l1m-4 {:l4-0 (place-obj gen-vec 2)} :l1m-5 {:l5-0 (place-obj gen-vec 3)} :l1m-6 {:l6-0 (place-obj gen-vec 2)}})

(def init-pc-inv
  [])

;LEVEL BUILDING___________________________________________________________
;1-temple 2-inn 3-general store 4-mansion 5-house 6-ruined building1 7-object in road

;interior location data maps
;exit-start-coords maps to what location the player should be off after popping of the map corresponding to each location
(def loc-1-0
  {:obj :l1-0 :desc "You're inside the temple." :exit-start-coords {:row 0 :col 3}})
(def loc-2-0
  {:obj :l2-0 :desc "You're inside the tavern." :exit-start-coords {:row 2 :col 0}})
(def loc-2-1
  {:obj :l2-1 :desc "You moved a bit."})
(def loc-3-0
  {:obj :l3-0 :desc "You're inside the store." :exit-start-coords {:row 3 :col 1}})
(def loc-4-0
  {:obj :l4-0 :desc "You're inside the mansion." :exit-start-coords {:row 1 :col 1}})
(def loc-5-0
  {:obj :l5-0 :desc "You're inside the house." :exit-start-coords {:row 3 :col 2}})
(def loc-6-0
  {:obj :l6-0 :desc "You're inside the ruins." :exit-start-coords {:row 1 :col 4}})

;interior matrixes. Will be bigger one day.
(def l1m-indoors-1
  {:obj :l1m-1 :map [[loc-1-0]]})

(def l1m-indoors-2
  {:obj :l1m-2 :map [[loc-2-0 loc-2-1]]})

(def l1m-indoors-3
  {:obj :l1m-3 :map [[loc-3-0]]})

(def l1m-indoors-4
  {:obj :l1m-4 :map [[loc-4-0]]})

(def l1m-indoors-5
  {:obj :l1m-5 :map [[loc-5-0]]})

(def l1m-indoors-6
  {:obj :l1m-6 :map [[loc-6-0]]})

;maps of location data, accessed by keywords
;obj and enemy are place holders right now
;:enter means there is a matrix map that can be loaded from that location.
;:map is the matrix that should be loaded (pushed onto the stack) 
;:start-coords is the location the player should start at in the new map
;FIXME it looks like we might need locs for every cell if we want unique objects.
(def loc-0 
  {:obj :l0 :desc "You're in the mud. People are staring at you. " :enemy ""})

(def loc-1
  {:obj :l1 :desc "You're at a temple. " :enemy "A cultist attacks you! " :enter {:goto l1m-indoors-1 :start-coords {:row 0 :col 0}}})

(def loc-2
  {:obj :l2 :desc "You're at the tavern. It smells like stale beer. " :enemy "A drunk guy starts hurling insults at you. " :enter {:goto l1m-indoors-2 :start-coords {:row 0 :col 0}}})

(def loc-3
  {:obj :l3 :desc "You're at the decrepit general store. " :enemy "" :enter {:goto l1m-indoors-3 :start-coords {:row 0 :col 0}}})

(def loc-4
  {:obj :l4 :desc "You're at a fancy mansion. " :enemy "A guard attacks! " :enter {:goto l1m-indoors-4 :start-coords {:row 0 :col 0}}})

(def loc-5
  {:obj :l5 :desc "This house is plain. " :enemy "A dog bites your ankle. Ow. " :enter {:goto l1m-indoors-5 :start-coords {:row 0 :col 0}}})

(def loc-6 
  {:obj :l6 :desc "This building looks like it burned down long ago. You wonder why it hasn't been restored. " :enemy "A rat bites you! " :enter {:goto l1m-indoors-6 :start-coords {:row 0 :col 0}}})

(def loc-7
  {:obj :l7 :desc "You're in the street. " :enemy "" })

;level map is a matrix, each cell corresponds to a location data map
(def level-1-map
  {:obj :l1m :map [[loc-0 loc-0 loc-0 loc-1 loc-0]
                   [loc-0 loc-4 loc-0 loc-0 loc-6]
                   [loc-2 loc-0 loc-0 loc-7 loc-0]
                   [loc-0 loc-3 loc-5 loc-0 loc-0]]})

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
  (get-in (get (first map-stack) :map) [(:row pc-loc) (:col pc-loc)]))

(defn print-loc-desc [pc-loc map-stack]
  "prints location description, using get-pc-loc"
  (println (:desc (get-pc-loc pc-loc map-stack))))

;FIXME placeholder
(defn print-loc-enemy [pc-loc map-stack]
  "prints enemy descriptions"
  (if (:enemy (get-pc-loc pc-loc map-stack))
    (println (:enemy (get-pc-loc pc-loc map-stack)))))

;OBJECT OPTIONS______________________________________________________________________________

(defn get-obj [pc-loc map-stack obj-map]
  (get-in obj-map [(:obj (first map-stack)) (:obj (get-pc-loc pc-loc map-stack))]))

(defn print-loc-obj [pc-loc map-stack obj-map]
  "prints object descriptions that are in current location"
  (print "printing objects in location....")
  ;(println (get-obj pc-loc map-stack obj-map))
  ;(println obj-map)
  (if (= [] (get-obj pc-loc map-stack obj-map))
    (println "You look around, but nothing catches your eye."))
  (doseq [item (map :desc (get-obj pc-loc map-stack obj-map))]
    (println item)))

(defn print-pc-inv [pc-inv]
  (print "printing inv...")
  ;(println pc-inv)
  (if (= pc-inv [])
    (println "Your inventory is currently empty."))
  (doseq [item (get pc-inv :desc)]
    (do
      (print "printing inv!!!!!!!!")
      (println item))))

(defn print-add-item []
  (print "
            _________________________________
          =(____   ___      __     ______   _)=
            |                               |
            |       Which item do you       |
            |         want to add?          |
            |                               |
            |    Type 0 to add nothing.     |
            |  Type 1 for the first item,   |
            |   Type 2 for the 2nd item,    |
            |             etc.              |
            |__    ______    _______  ___   |
          =(_________________________________)=")
  (println))

(defn add-obj-to-inv [pc-loc map-stack obj-map pc-inv int-input]
  ;(conj pc-inv (nth (get-obj pc-loc map-stack obj-map) (- 1 int-input))))
  ;(print "pre and post inv: ")
  ;(print pc-inv)
  (println "That item has been added to your inventory.")
  (let [new-pc-inv (conj pc-inv (nth (get-obj pc-loc map-stack obj-map) (- 1 int-input)))]
    ;(print new-pc-inv)
    ;(println "")
    ;(print-pc-inv new-pc-inv)
    new-pc-inv))

;FIXME print-loc-obj is returning nil
(defn remove-obj-from-loc [pc-loc map-stack obj-map pc-inv int-input]
  (let [pre-obj (subvec (get-obj pc-loc map-stack obj-map) 0 (dec int-input))
        post-obj  (subvec (get-obj pc-loc map-stack obj-map) int-input)]
;    (print "Pre and post obj lists")
;    (print pre-obj)
;    (print post-obj)
    (println "")
    ;(print-loc-obj pc-loc map-stack (concat pre-obj post-obj))
    ;(print "Associng into top level key " (:obj (first map-stack)))
    ;(print "Associng into localtion key " (:obj (get-pc-loc pc-loc map-stack))
    ;      "list: " (concat pre-obj post-obj) 
    ;       "into " (get-obj pc-loc map-stack obj-map))
    (assoc-in obj-map [(:obj (first map-stack)) (:obj (get-pc-loc pc-loc map-stack))] (vec (concat pre-obj post-obj)))))

;FIXME
(defn add-to-inv [pc-loc map-stack obj-map pc-inv]
  (print-add-item)
  (print-loc-obj pc-loc map-stack obj-map)
  (let [input (read-line)]
    ;(println (type input))
    (let [int-input (Integer/parseInt input)]
      ;(println (type int-input))
      (if (= int-input 0)
        (do
          [obj-map pc-inv])
        (let [new-pc-inv (add-obj-to-inv pc-loc map-stack obj-map pc-inv int-input)
              new-obj-map (remove-obj-from-loc pc-loc map-stack obj-map pc-inv int-input)]
            (print "New object map" new-obj-map)
            ;(print-loc-obj pc-loc map-stack new-obj-map)
            ;(print-pc-inv new-pc-inv)
            (pause-screen2)
            [new-obj-map new-pc-inv])))))

;MENU AND USER OPTIONS________________________________________________________________________________________

;moving normally________________________________________
(defn parse-user-input [pc-loc map-stack obj-map pc-inv]
  "called from check-move. Parses user input.
  movement: dec/inc returns a new value that is one greater than the value passed in.
    assoc returns a new map of pc coordinates that is updated according to inc/dec
  objects: objects, loot, and inventory are placeholders
 'q' quits program (system call)"
  (let [input (read-line)]
    (cond
      (= input "n") (do (println "You move North.") (pause-screen1) [(assoc pc-loc :row (dec (:row pc-loc))) obj-map pc-inv])
      (= input "s") (do (println "You move South.") (pause-screen1) [(assoc pc-loc :row (inc (:row pc-loc))) obj-map pc-inv])
      (= input "e") (do (println "You move East.") (pause-screen1) [(assoc pc-loc :col (inc (:col pc-loc))) obj-map pc-inv]) 
      (= input "w") (do (println "You move West.") (pause-screen1) [(assoc pc-loc :col (dec (:col pc-loc))) obj-map pc-inv]) 
      (= input "o") (do (print-loc-obj pc-loc map-stack obj-map) (pause-screen2) [pc-loc obj-map pc-inv])
      (= input "m") (do (open-main-menu) (pause-screen2) [pc-loc obj-map pc-inv]) 
      (= input "l") (do (println "There are no enemies yet, so there is no loot.") (pause-screen1-5) [pc-loc obj-map pc-inv]) 
      (= input "a") (let [[new-obj-map new-pc-inv] (add-to-inv pc-loc map-stack obj-map pc-inv)] 
                      [pc-loc new-obj-map new-pc-inv])
      (= input "i") (do (print-pc-inv pc-inv) (pause-screen1-5) [pc-loc obj-map pc-inv])
      (= input "q") (System/exit 0) 
      :else (do (println "That's not a valid choice.") (pause-screen1-5) [pc-inv obj-map pc-inv]))))

(defn print-user-menu []
  (with-open [rdr (io/reader "resources/user-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn check-move [pc-loc map-stack obj-map pc-inv]
  "takes pc-loc player coordinates and the map stack
  prints a menu of options for the user to see
  then calls parse-user-input
    parse-user-input returns an updated set of PC coordinates
  If a description can be pulled from the new pc-loc (from location data stored in map)
    the new move is valid and the new pc-loc is returned (along with the unchanged map stack). 
  If not, it's not a valid move, and the old pc-loc is returned (along with the unchanged map stack)"
  (print-user-menu)
  (let [[new-loc new-obj-map new-pc-inv] (parse-user-input pc-loc map-stack obj-map pc-inv)]
    (if (:desc (get-pc-loc new-loc map-stack))
      [new-loc map-stack new-obj-map new-pc-inv]
      (do
        (if (not= (get (first map-stack) :map) (get level-1-map :map))
          (println "There is a wall in that direction. You move back to where you were.")
          (println "You find yourself at the edge of the village and turn back to where you started."))
        (pause-screen2)
        [pc-loc map-stack new-obj-map new-pc-inv]))))

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
        :else (do (println "That was not a valid choice.") (pause-screen1) [pc-loc map-stack false]))))

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

(defn push-control [pc-loc map-stack obj-map pc-inv]
  "called from display-menu, takes in pc-loc and map-stack
  calls check-push-map, which returns an updated pc-coordinate location and map stack, and either true or false
  if true, new map has been loaded and the new pc-coordinates and map stack are returned to loop recur
  if false, calls noromal movement functions (check-move)"
  (print-enter pc-loc map-stack)
  (let [[new-loc new-map-stack true-false] (check-push-map pc-loc map-stack)]
    ;will be true if a new map was pushed on 
    (if true-false
      ;load new map
      [new-loc new-map-stack obj-map pc-inv]
      ;move normally
      (check-move pc-loc map-stack obj-map pc-inv))))

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
      :else (do (println "That was not a valid choice. What else would you like to do at this location?") (pause-screen1-5) [pc-loc map-stack false]))))

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

(defn pop-control [pc-loc map-stack obj-map pc-inv]
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
      [new-loc new-map-stack obj-map pc-inv]
      ;move normally
      (check-move pc-loc map-stack obj-map pc-inv)))) 

;getting and parsing user input_____________________________
(defn display-menu [pc-loc map-stack obj-map pc-inv]
  "checks to see if a place can be entered or exited from current player location.
  if yes, gives player the option to enter/exit
  if no, just displays normal movement functions (check-move)"
  (cond
    (:enter (get-pc-loc pc-loc map-stack)) (push-control pc-loc map-stack obj-map pc-inv)
    (:exit-start-coords (get-pc-loc pc-loc map-stack)) (pop-control pc-loc map-stack obj-map pc-inv)
    :else (check-move pc-loc map-stack obj-map pc-inv)))

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
  (loop [pc-loc init-pc-loc map-stack init-map-stack obj-map init-obj-map pc-inv init-pc-inv]
      (do (clear-screen)
          ;(function arguments)
          (print-loc-desc pc-loc map-stack)
          (print-loc-enemy pc-loc map-stack) 
          (let [[new-loc map-stack obj-map pc-inv] (display-menu pc-loc map-stack obj-map pc-inv)]
            (recur new-loc map-stack obj-map pc-inv)))))
