; The Shadow Over Darkmoor
; copywright (c) CMR 2019-2020 

(ns darkmoor.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as cstr])
  (:require [clojure.edn :as edn])
  (:gen-class))

(load "enemies")
(load "objects")
(load "locations")
(load "player")
;
;;FIXME
;(def debug
;  false)
;
;;FIXME
;(def debug-hit-list
;  false)
;
;;FIXME
;(defn print-debug [thing]
;  "debug statement for objects, inventory, equipped, and unequipped lists"
;  (if debug
;  (println thing)))
;
;;FIXME
;(defn print-debug-hit-list [func, hit-list]
;  "debug statement for hit-list (list of places with killed enemies)"
;  (if debug-hit-list
;    (do
;      (println)
;      (print func)
;      (print " ")
;      (print hit-list)
;      (println))))
;
;;OPENING SEQUENCE__________________________________________________________
;
;;FIXME this should ALL be in view section
(defn clear-screen []
  "clears screen using ANSI escape sequence"
  (print (str (char 27) "[2J"))) 

(defn pause []
  "pauses by asking for user input"
  (println) 
  (println "Press ENTER to continue.")
  (read-line))

(defn pause-screen3 []
  "pauses thread for 3 seconds"
  (Thread/sleep 3000))
;
;(defn pause-screen2 []
;  "pauses thread for 2 seconds"
;  (Thread/sleep 2000))
;
;(defn pause-screen1-5 []
;  "pauses thread for 1.5 seconds"
;  (Thread/sleep 1500))
;
;(defn pause-screen1 []
;  "pauses thread for 1 second"
;  (Thread/sleep 1000))

(defn open-title []
  "opens title sequence, pauses screen for time to read it, then clears screen"
  (print (str (char 27) "[2J")) ;clear screen using ANSI escape
  (with-open [rdr (io/reader "resources/title.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause-screen3)
  (clear-screen))

;(defn open-main-menu []
;  "opens menu. Eventually will list all commands"
;  (with-open [rdr (io/reader "resources/main-menu.txt")]
;    (doseq [line (line-seq rdr)]
;      (println line))))
;
(defn open-intro []
  "opens intro paragraph, pauses screen, then clears screen"
  (with-open [rdr (io/reader "resources/intro.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause)
  (clear-screen)
  (with-open [rdr (io/reader "resources/intro-cont.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause)
  (clear-screen))
;
;;PC LOCATION_________________________________________________________________________________
;
;(defn get-player-loc [player map-stack]
;  (get-in (first map-stack) [(:row player) (:col player)]))
;
;;FIXME move to print section
;(defn print-loc-desc [pc-loc map-stack]
;  (println (:desc (get-player-loc player map-stack))))
;
;;PC HEALTH AND DAMAGE________________________________________________________________________
;
;;player's initial max health
;(def init-max-health
;  15)
;
;;player's actual health--subtracted during combat
;(def init-pc-health
;  15)
;
;(defn print-pc-health [pc-health max-health]
;  (print "             Your current health is ") 
;  (print pc-health)
;  (print " out of ")
;  (print max-health))
;
;;player's initial damage--added to by weapons
;(def init-pc-damage
;  5)
;
;(defn print-pc-damage [pc-damage]
;  (println) 
;  (print "             Your current damage output is ") 
;  (print pc-damage) 
;  (println) 
;  (println))
;
;(defn add-damage [pc-inv pc-damage maybe-int]
;  "called when player equippes new weapon.
;   weapon damage is added to player's damage"
;  (+ pc-damage (:damage (nth pc-inv (dec maybe-int)))))
;
;(defn add-health [pc-inv health maybe-int]
;  "called when player equippes new armor.
;   armor health is added to player's health"
;  (+ health (:health (nth pc-inv (dec maybe-int)))))
;
;(defn sub-damage [pc-eq pc-damage maybe-int]
;  "called when player unequippes a weapon.
;   weapon damage is subtracted from player's damage"
;  (- pc-damage (:damage (nth pc-eq (dec maybe-int)))))
;
;(defn sub-health [pc-eq health maybe-int]
;  "called when player unequippes armor.
;   armor health is subtracted from player's damage"
;  (- health (:health (nth pc-eq (dec maybe-int)))))
;
;(defn potion-add-health [pc-inv pc-health maybe-int max-health]
;  "called when player drinks a health potion. makes sure player health
;   never goes over max."
;  (let [new-pc-health (+ pc-health (:health (nth pc-inv (dec maybe-int))))]
;    (if (> new-pc-health max-health)
;      max-health
;      new-pc-health)))
;
;;OBJECT OPTIONS______________________________________________________________________________
;
;(defn get-obj [pc-loc map-stack]
;  "used for getting the object in the location"
;  (deref 
;    (get (get-player-loc player map-stack) :obj)))
;
;(defn get-obj-ref [pc-loc map-stack]
;  "used for changing the actual list of objs in location"
;  (get (get-player-loc player map-stack) :obj))
;
;;adding items to inventory____________________________________________________
;
;(defn add-obj-to-inv [pc-loc map-stack pc-inv maybe-int]
;  "takes number of obj user indicated they wanted.
;   that object is gotten from the location obj list and added to inventory list"
;  (println)
;  (print "That item has been added to your inventory.")
;  (println)
;  (print-debug pc-inv)
;  (vec (conj pc-inv (nth (get-obj pc-loc map-stack) (dec maybe-int)))))
;
;(defn remove-obj-from-loc [pc-loc map-stack maybe-int]
;  "takes number of obj user indicated to pick up. that item is removed
;   from location after being added to player's inventory. removal happens
;   by splitting the vector to two lists (neither one including the obj
;   that was indicated) and then joins them back together."
;  (let [pre-obj (subvec (get-obj pc-loc map-stack) 0 (dec maybe-int))
;        post-obj  (subvec (get-obj pc-loc map-stack) maybe-int)]
;    (dosync
;      (ref-set (get-obj-ref pc-loc map-stack) (vec (concat pre-obj post-obj))))))
;
;(defn nothing-to-add []
;  (println) 
;  (println "There is nothing here to add.") 
;  (println) 
;  (pause)) 
;
;(defn is-int? [input]
;  "try/catch block for user input sanitization. if user didn't input an int,
;   will catch the error instead of killing the program."
;  (try 
;    (Integer/parseInt input)
;    (catch NumberFormatException e (println "That's not a number."))))
;
;(defn something-to-add [pc-loc map-stack pc-inv]
;  "gets user input for item number they want to add,
;   passes to try-catch block for input sanitization.
;   If user input is correct, calls func to add obj to inventory,
;   and calls func to remove obj from location."
;  (println) 
;  (println "What would you like to add? enter '1' for the first item listed, '2' for the second item listed, and so on.") 
;  (println)
;  (let [input (read-line)]
;    (if-let [maybe-int (is-int? input)]
;      (if (or (> maybe-int (count (get-obj pc-loc map-stack))) (<= maybe-int 0))
;        (do
;          (println "There's not that many items here.")
;          (something-to-add pc-loc map-stack pc-inv))
;        (let [new-pc-inv (add-obj-to-inv pc-loc map-stack pc-inv maybe-int)]
;          (print-debug new-pc-inv)
;          (remove-obj-from-loc pc-loc map-stack maybe-int)
;          (print-debug new-pc-inv)
;          new-pc-inv))
;      (something-to-add pc-loc map-stack pc-inv))))
;
;(defn add-to-inv [pc-loc map-stack pc-inv]
;  "prevents error if there are no objects to add at that location"
;  (if (= [] (get-obj pc-loc map-stack))
;    (do 
;      (nothing-to-add)
;      (print-debug pc-inv) 
;      pc-inv)
;    (something-to-add pc-loc map-stack pc-inv)))
;
;;print object options_____________________________________________________
;
;(defn print-obj-item [item]
;  "prints item desc"
;  (print "                    ")
;  (print item)
;  (println))
;
;(defn print-obj [pc-loc map-stack]
;  "prints individual items at that loc one by one"
;  (if (= [] (get-obj pc-loc map-stack))
;    (println "                    You look around, but nothing catches your eye.")
;    (doseq [item (map :desc (get-obj pc-loc map-stack))]
;      (print-obj-item item))))
;
;(defn open-potions []
;  (with-open [rdr (io/reader "resources/potions.txt")]
;    (doseq [line (line-seq rdr)]
;      (println line))))
;
;(defn open-objects []
;  (with-open [rdr (io/reader "resources/objects.txt")]
;    (doseq [line (line-seq rdr)]
;      (println line))))
;
;(defn print-obj-commands [pc-loc map-stack]
;  "for user to see options"
;  (clear-screen)
;  (open-objects)
;  (print-obj pc-loc map-stack)
;  (open-potions))
;
;(defn obj-control [pc-loc map-stack pc-inv]
;  "displays player options, and gets user input.
;   calls function that controls object adding functionality.
;   then prints object descriptions that are in current location"
;  (print-obj-commands pc-loc map-stack)
;  (print-debug pc-inv)
;  (let [input (read-line)]
;    (cond
;      (= input "x") pc-inv
;      (= input "a") (let [new-pc-inv (add-to-inv pc-loc map-stack pc-inv)]
;                     (obj-control pc-loc map-stack new-pc-inv)) 
;      :else (do 
;              (print-debug pc-inv) 
;              (obj-control pc-loc map-stack pc-inv)))))
;
;;inventory________________________________________________________________________
;
;;removing items from inventory_______________________________________________
;
;(defn add-item-to-loc [pc-loc map-stack pc-inv maybe-int]
;  "copies item from inventory to object list at location."
;  (print-debug pc-inv)
;  (dosync
;    (alter (get-obj-ref pc-loc map-stack) conj (nth pc-inv (dec maybe-int)))))
;
;(defn nothing-to-drop [pc-inv]
;  (println "You have nothing to drop.")
;  (pause)
;  pc-inv)
;
;(defn something-to-drop [pc-loc map-stack pc-inv]
;  "gets user input, makes sure it's an int, then adds obj from inv to location
;   and removes obj from inv (splits inv in two around obj, then joins segments together)"
;  (println)
;  (println "What item do you want to drop? Enter '1' for the first item listed, '2' for the second item listed, and so on.")
;  (let [input (read-line)]
;    (if-let [maybe-int (is-int? input)]
;      (if (or (> maybe-int (count pc-inv)) (<= maybe-int 0))
;        (do
;          (println "You don't have that many items in your inventory.")
;          (something-to-drop pc-loc map-stack pc-inv))
;        (do
;          (print-debug pc-inv)
;          (add-item-to-loc pc-loc map-stack pc-inv maybe-int)
;          (let [pre-inv (subvec pc-inv 0 (dec maybe-int))
;                post-inv (subvec pc-inv maybe-int)]
;            (print-debug pre-inv)
;            (print-debug post-inv)
;            (vec (concat pre-inv post-inv)))))
;      (something-to-drop pc-loc map-stack pc-inv))))
;
;(defn remove-item-from-inv [pc-loc map-stack pc-inv]
;  "Making sure program won't crash if there's nothing in inv to drop"
;  (if (= pc-inv [])
;    (nothing-to-drop pc-inv)
;    (something-to-drop pc-loc map-stack pc-inv)))
;
;;equip and unequip___________________________________________________
;
;(defn print-eq [item]
;  "prints item desc"
;  (print "                    ")
;  (print (:desc item))
;  (println))
;
;(defn print-pc-eq [pc-eq]
;  "gets individual items to print descriptions"
;  (if (= pc-eq [])
;    (println "             You have no items equipped.")
;    (doseq [item pc-eq]
;      (print-eq item))))
;
;(defn nothing-to-equip [pc-eq pc-health pc-damage max-health]
;  (println "There is nothing in your inventory.")
;  (pause)
;  [pc-eq
;   pc-health
;   pc-damage
;   max-health])
;
;(defn something-to-equip [pc-inv pc-eq pc-health pc-damage max-health]
;  "gets user input (makes sure it's an int), check if it's a potion
;   (can't be equipped), then adds that item to end of equipped list
;   and adds the item's health and damage bonuses to player's."
;  (println "What item do you want to equip? Enter '1' for the first item listed, '2' for the second item listed, and so in.")
;  (println) 
;  (let [input (read-line)]
;    (if-let [maybe-int (is-int? input)]
;      (if (or (> maybe-int (count pc-inv)) (<= maybe-int 0))
;        (do
;          (println "You don't have that many items in your inventory.")
;          (something-to-equip pc-inv pc-eq pc-health pc-damage max-health))
;        (if (:potion (nth pc-inv (dec maybe-int)))
;          (do
;            (println "You can't equip that.")
;            (pause)
;            [pc-eq pc-health pc-damage max-health])
;          [(vec (conj pc-eq (nth pc-inv (dec maybe-int)))) 
;           (add-health pc-inv pc-health maybe-int) 
;           (add-damage pc-inv pc-damage maybe-int)
;           (add-health pc-inv max-health maybe-int)]))
;      (something-to-equip pc-inv pc-eq pc-health pc-damage max-health))))
;
;(defn equip-item [pc-inv pc-eq pc-health pc-damage max-health]
;  "to make sure program doesn't die if there's nothing to equip"
;  (println)
;  (if (= [] pc-inv)
;    (nothing-to-equip pc-eq pc-health pc-damage max-health) 
;    (something-to-equip pc-inv pc-eq pc-health pc-damage max-health)))
;
;(defn something-to-unequip [pc-eq pc-health pc-damage max-health]
;  "gets user input (makes sure it's an int), then remove it from equipped list
;   by splitting equipped list around that object and then joining it together again.
;   subtracts that item's health and damge from player's."
;  (println)
;  (println "What item do you want to unequip? Enter'1' for the first item listed, '2' for the second item listed, and so on.")
;  (println)
;  (let [input (read-line)]
;    (if-let [maybe-int (is-int? input)]
;      (if (or (> maybe-int (count pc-eq)) (<= maybe-int 0))
;        (do
;          (println "You don't have that many items equipped.")
;          (something-to-unequip pc-eq pc-health pc-damage max-health))
;        (let [pre-eq (subvec pc-eq 0 (dec maybe-int))
;              post-eq (subvec pc-eq maybe-int)]
;          [(vec (concat pre-eq post-eq))
;           (sub-health pc-eq pc-health maybe-int) 
;           (sub-damage pc-eq pc-damage maybe-int)
;           (sub-health pc-eq max-health maybe-int)]))
;      (something-to-unequip pc-eq pc-health pc-damage max-health))))
;
;(defn unequip-item [pc-eq pc-health pc-damage max-health]
;  "makes sure program doesn't die if there's nothing equipped"
;  (if (= [] pc-eq)
;    (nothing-to-equip pc-eq pc-health pc-damage max-health) 
;    (something-to-unequip pc-eq pc-health pc-damage max-health)))
;
;(defn not-drinkable [pc-inv pc-health]
;  "non-potions are persistant."
;  (println)
;  (println "You don't think you can drink that.")
;  (println)
;  [pc-inv pc-health]) 
;
;(defn drinkable [pc-loc map-stack pc-inv pc-health maybe-int max-health]
;  "adds potion's health bonus to user, and subtracts it from the inventory."
;  (println)
;  (println "You drink the potion, and immediately feel a little bit better.")
;  (print-debug pc-inv)
;  (let [pre-inv (subvec pc-inv 0 (dec maybe-int))
;        post-inv (subvec pc-inv maybe-int)]
;    (print-debug pre-inv)
;    (print-debug post-inv)
;    [(vec (concat pre-inv post-inv)) 
;     (potion-add-health pc-inv pc-health maybe-int max-health)]))
;
;(defn drink-hp [pc-loc map-stack pc-inv pc-health max-health]
;  "gets user input, makes sure it's an int, makes sure it's a potion,
;   then calls function to add potion bonus to user and remove from inventory."
;  (println)
;  (println "What item do you want to drink? Enter '1' for the first item listed, '2' for the second item listed, and so on.")
;  (let [input (read-line)]
;    (if-let [maybe-int (is-int? input)]
;      (if (> maybe-int (count pc-inv))
;        (if (<= maybe-int 0)
;          (do
;            (println "That's not an item you can drink.")
;            [pc-inv pc-health])
;          (if (not (:potion (nth pc-inv (dec maybe-int))))
;            (not-drinkable pc-inv pc-health)
;            (drinkable pc-loc map-stack pc-inv pc-health maybe-int max-health)))
;        (do
;          (println "You don't have that many items in your inventory.")
;          (drink-hp pc-loc map-stack pc-inv pc-health max-health)))
;      (drink-hp pc-loc map-stack pc-inv pc-health max-health))))
;
;;print inventory____________________________________________________
;
;(defn print-item [item]
;  "prints individual item description"
;  (print "                    ")
;  (print (:desc item))
;  (println))
;
;(defn print-pc-inv [pc-inv]
;  "gets individual item to print"
;  (if (= pc-inv [])
;    (println "             Your inventory is currently empty.")
;    (do
;      (print-debug pc-inv)
;      (doseq [item  pc-inv]
;      (print-item item)))))
;
;(defn open-inv-menu []
;  "displays inv menu"
;  (with-open [rdr (io/reader "resources/inv-menu.txt")]
;    (doseq [line (line-seq rdr)]
;      (println line))))
;
;(defn open-sword []
;  "displays sword"
;  (with-open [rdr (io/reader "resources/sword.txt")]
;    (doseq [line (line-seq rdr)]
;      (println line))))
;
;(defn print-inv-commands [pc-inv pc-eq pc-health pc-damage max-health]
;  "nice printing for inventory"
;  (clear-screen)
;  (open-inv-menu)
;  (open-sword)
;  (print-pc-health pc-health max-health)
;  (print-pc-damage pc-damage)
;  (println "             Your inventory contains the following items:")
;  (print-pc-inv pc-inv)
;  (println)
;  (println "             You have equipped the following items:")
;  (print-pc-eq pc-eq)
;  (println))
;
;(defn inv-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health]
;  "for user inventory: displays user menu and gets input. user can:
;   exit the inv menu, remove an item from inventory, equip something, 
;   unequip something, and drink potion."
;  (print-inv-commands pc-inv pc-eq pc-health pc-damage max-health)
;  (let [input (read-line)]
;    (cond
;      (= input "x") [pc-inv 
;                     pc-eq 
;                     pc-health 
;                     pc-damage
;                     max-health]
;      (= input "r") (let [new-pc-inv (remove-item-from-inv pc-loc map-stack pc-inv)]
;                     (inv-control pc-loc map-stack new-pc-inv pc-eq pc-health pc-damage max-health))
;      (= input "e") (let [[new-pc-eq new-pc-health new-pc-damage new-max-health] 
;                          (equip-item pc-inv pc-eq pc-health pc-damage max-health)]
;                      (inv-control pc-loc map-stack pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health))
;      (= input "u") (let [[new-pc-eq new-pc-health new-pc-damage new-max-health] 
;                          (unequip-item pc-eq pc-health pc-damage max-health)]
;                      (inv-control pc-loc map-stack pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health))
;      (= input "d") (let [[new-pc-inv new-pc-health] 
;                          (drink-hp pc-loc map-stack pc-inv pc-health max-health)]
;                      (pause)
;                      (inv-control pc-loc map-stack new-pc-inv pc-eq new-pc-health pc-damage max-health))
;      :else (inv-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health))))
;
;;combat_______________________________________________________________________________________
;
;(defn print-rat []
;  (println "A large rat scuttles out of the shadows, open sores oozing on its back. It bares its yellow teeth at you. "))
; 
;(defn print-cultist []
;  (if (= 1 (rand-int 2))
;    (println "A young man in swirling black blood-soaked robes glances around, spotting you immediately. He shouts, drawing a curved dagger dripping with poison.")
;    (println "You turn a corner and see a woman with long, grey hair concentrate for a moment before a tiny blue flame appears in her hand. In a second a roaring fire is contained in her palm.")))
;
;(defn print-skele []
;  (if (= 1 (rand-int 2))
;    (println "A skeleton comes rushing towards you, jaw clacking. It raises a cracked bow and fits a rusted arrow to it.")
;    (println "This skeleton looks between you and the chipped axe in its bony hand, and a whisper of a sad sigh rattles in its ribcage.")))
;
;(defn print-zombie []
;  (println "As you carefully pick your way forward, a corpse groans and rises from the ground, stumbling towards you."))
;
;(defn print-ghost []
;  (println "The air freezes and the light around you dims. A gleam of ethereal blue light coalesces into a spirit, lost in the torment of its last moments. 'No! I won't let you hurt them!' "))
;
;(defn print-boss []
;  (println "A old woman, back bent with age, looks up from the alter, a wicked gleam in her eye. The wooden staff in her hand crackles with energy and the crystal at the top glows a deep red." ))
;
;(defn print-minion []
;  (println " 'You will not interrupt the Master!' A knight blocks your path, a heavy mace held aloft. The holy symbol that once adorned the breastplate lies to the side, cracked in half."))
;
;(defn print-tavern-skele []
;  (let [which-one? (rand-int 5)]
;    (cond
;      (= which-one? 0) (println "A skeleton stumbles towards you drunkenly, waving an empty bottle of whiskey.")
;      (= which-one? 1) (println "A skeleton with a tablecloth tied around its head as a bandana breaks a chair over its bony knee, picking up the remnants in both hands.")
;      (= which-one? 2) (println "A skeleton pours a bottle behind the bar through its open jaw, rum splashing through its body. It smashes the empty bottle and jumps over the counter towards you.")
;      (= which-one? 3) (println "Two skeletons are locked in a brawl in front of you. One uses a candlestick to bash the other's skull, and it collapses. The victor looks around for another victim.")
;      (= which-one? 4) (println "A skeleton dances drunkenly to silent music. You try to squeeze past it, but you bump its hip and it angrily lashes out at you.")
;      :else (println "this should never print: print-tavern-skele"))))
;
;(defn print-enemy [pc-loc map-stack]
;  "prints different enemy descriptions"
;  (if (= tavern-18 (first map-stack))
;    (print-tavern-skele)
;    (do
;      (cond
;        (:rat (:enemy (get-player-loc player map-stack))) (print-rat)
;        (:cultist (:enemy (get-player-loc player map-stack))) (print-cultist)
;        (:skeleton (:enemy (get-player-loc player map-stack))) (print-skele)
;        (:zombie (:enemy (get-player-loc player map-stack))) (print-zombie)
;        (:ghost (:enemy (get-player-loc player map-stack))) (print-ghost)
;        (:boss (:enemy (get-player-loc player map-stack))) (print-boss)
;        (:minion (:enemy (get-player-loc player map-stack))) (print-minion)
;        :else (do 
;                (println "this should never print: print-enemy")
;                (println (:enemy (get-player-loc player map-stack))))))))
;
;(defn print-enemy-damage-done [pc-health max-health new-pc-health]
;  "nice display for user"
;  (print "             The enemy attacks and deals ")
;  (print (- pc-health new-pc-health))
;  (print " damage.")
;  (println)
;  (print-pc-health new-pc-health max-health)
;  (println)
;  (println))
;
;(defn pc-dead []
;  "clears screen, displays 'you died' screen, and quits program"
;  (clear-screen)
;  (println)
;  (with-open [rdr (io/reader "resources/you-died.txt")]
;    (doseq [line (line-seq rdr)]
;      (println line)))
;      (System/exit 0))
;
;(defn pc-wins []
;  "clears screen, displays 'you won' screen, and quits program"
;  (clear-screen)
;  (println)
;  (with-open [rdr (io/reader "resources/you-win.txt")]
;    (doseq [line (line-seq rdr)]
;      (println line)))
;      (System/exit 0))
;
;(defn print-pc-damage-done [enemy-health new-enemy-health]
;  "nice display for user"
;  (print "             You swing your weapon as hard as you can, hitting your opponent for ") 
;  (print (- enemy-health new-enemy-health))
;  (print " damage!")
;  (println)
;  (print "             Enemy health: ")
;  (print new-enemy-health)
;  (println)
;  (println))
;
;(defn enemy-first [pc-loc map-stack pc-health pc-damage max-health enemy-health] 
;  "enemy hits user first. pc's health is lowered by damage taken.
;   then user hits enemy, and enemy's health is lowered by damage taken.
;   if player dies, calls func to end game. if enemy dies, ends combat."
;  (let [new-pc-health 
;        (- pc-health (- (get-in (get-player-loc player map-stack) [:enemy :damage]) (rand-int 3)))]
;    (print-enemy-damage-done pc-health max-health new-pc-health)
;    (if (<= new-pc-health 0)
;      (pc-dead)
;      (let [new-enemy-health 
;            (- enemy-health (- pc-damage (rand-int 3)))]
;        (print-pc-damage-done enemy-health new-enemy-health)
;        [new-pc-health new-enemy-health]))))
;
;(defn pc-first [pc-loc map-stack pc-health pc-damage max-health enemy-health]
;  "player hits enemy first. enemy's health is lowered by damage taken.
;   then enemy hits user, and player's health is lowered by damage taken.
;   if player dies, calls func to end game. if enemy dies, ends combat."
;  (let [new-enemy-health 
;        (- enemy-health (- pc-damage (rand-int 3)))]
;    (print-pc-damage-done enemy-health new-enemy-health)
;    (if (<= new-enemy-health 0)
;      [pc-health new-enemy-health]
;      (let [new-pc-health (- pc-health 
;                             (- (get-in (get-player-loc player map-stack) [:enemy :damage]) (rand-int 3)))]
;        (print-enemy-damage-done pc-health max-health new-pc-health)
;        (if (<= new-pc-health 0)
;          (pc-dead)
;          [new-pc-health new-enemy-health])))))
;
;(defn combat-round [pc-loc map-stack pc-health pc-damage max-health enemy-health]
;  "Generates random 0 or 1: if 1, user goes first. if 0, enemy goes first."
;  (if (= 1 (rand-int 2))
;    (do
;      (println "             You get the jump on it.")
;      (let [[new-pc-health new-enemy-health] 
;            (pc-first pc-loc map-stack pc-health pc-damage max-health enemy-health)]
;        [new-pc-health new-enemy-health]))
;    (do
;      (println "             It gets the jump on you.")
;      (let [[new-pc-health new-enemy-health] 
;            (enemy-first pc-loc map-stack pc-health pc-damage max-health enemy-health)]
;        [new-pc-health new-enemy-health]))))
;
;(defn first-map [map-stack]
;  "for run-away: pops back to original map to make sure player gets to safe place"
;  (if (not= (first map-stack) level-1-map)
;    (first-map (rest map-stack))
;    map-stack))
;
;(defn run-away [pc-loc map-stack pc-health max-health]
;  "get one hit by enemy, then move player's location to loc-8."
;  (println "             As your vision grows grey, you blindly turn and run to a safe place. Your attacker takes a swing at your back, but does not persue you.")
;  (let [new-pc-health 
;        (- pc-health (- (get-in (get-player-loc player map-stack) [:enemy :damage]) (rand-int 3)))]
;    (if (<= new-pc-health 0)
;      (pc-dead)
;      (do
;        (print "             You take ")
;        (print (- pc-health new-pc-health))
;        (print " damage.")
;        (println)
;        (print-pc-health new-pc-health max-health)
;        (println)
;        (let [new-map-stack (first-map map-stack)]
;          (let [new-row-pc-loc (assoc pc-loc :row 0)] 
;            (let [new-pc-loc (assoc new-row-pc-loc :col 1)]
;              (pause)
;              [new-pc-loc new-map-stack new-pc-health])))))))
;
;(defn open-combat []
;  (with-open [rdr (io/reader "resources/combat.txt")]
;    (doseq [line (line-seq rdr)]
;      (println line))))
;
;(defn open-sword2 []
;  (with-open [rdr (io/reader "resources/sword2.txt")]
;    (doseq [line (line-seq rdr)]
;      (println line))))
;    
;(defn print-fight-start [pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health]
;  "displays combat screen, enemy health, and your inventory."
;  (open-combat)
;  (print-enemy pc-loc map-stack)
;  (open-sword2)
;  (print-pc-health pc-health max-health)
;  (println)
;  (print "             Enemy health: ")
;  (print enemy-health)
;  (println)
;  (println)
;  (println "             Your inventory contains the following items:")
;  (print-pc-inv pc-inv)
;  (println))
;
;(defn continue [pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health]
;  "gets user input: either fight or drink a potion"
;  (println "What else would you like to do?")
;  (let [new-input (read-line)]
;    (clear-screen)
;    (print-fight-start pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health)
;    new-input))
;
;(defn im-not-dead-yet [pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health]
;  "if enemy is not dead, get input for next round of combat"
;  (let [input (read-line)]
;    (clear-screen)
;    (print-fight-start pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health)
;    input))
;
;(defn fought-boss? [pc-loc map-stack pc-inv pc-health]
;  "checks if user fought and killed boss. If they did, prints this message, then calls pc-wins function."
;  (if (= boss (get (get-player-loc player map-stack) :enemy))
;    (do
;      (println "             Gathering all your remaining strength, you let out a yell and swing your weapon as hard as you can towards the old woman's head.
;             The Master begins to pull her staff up to block you and chant a spell, but her age betrays her and her strength faulters.
;             As she falls to the floor, you run over to the alter and smash your weapon on the strange spellbook again and again until the magical smoke fades.")
;      (pause)
;      (pc-wins))
;    (do 
;      (println "             With one last groan, your enemy falls dead at your feet. You win the fight!") 
;      (pause)
;      [pc-loc map-stack pc-inv pc-health false])))
;
;(defn fight [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health enemy-health input]
;  "interprets user input, to either run away, attack, or drink a potion. calls fucntions to make that happen."
;  (cond
;    (= input "r") (let [[new-pc-loc new-map-stack new-pc-health] (run-away pc-loc map-stack pc-health max-health)]
;                    [new-pc-loc new-map-stack pc-inv new-pc-health true])
;    (= input "d") (let [[new-pc-inv new-pc-health] (drink-hp pc-loc map-stack pc-inv pc-health max-health)]
;                    (let [new-input (continue pc-loc map-stack new-pc-inv pc-eq new-pc-health max-health enemy-health)]
;                      (fight pc-loc map-stack new-pc-inv pc-eq new-pc-health pc-damage max-health enemy-health new-input)))
;    (= input "a") (let [[new-pc-health new-enemy-health] (combat-round pc-loc map-stack pc-health pc-damage max-health enemy-health)]
;                    (if (<= new-enemy-health 0)
;                      (fought-boss? pc-loc map-stack pc-inv new-pc-health)
;                      (let [new-input (im-not-dead-yet pc-loc map-stack pc-inv pc-eq new-pc-health max-health new-enemy-health)]
;                        (fight pc-loc map-stack pc-inv pc-eq new-pc-health pc-damage max-health new-enemy-health new-input))))
;    :else (do
;            (println "That's not valid input. What do you want to do?")
;            (let [new-input (read-line)]
;              (fight pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health enemy-health new-input)))))
;
;(defn start-combat [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list]
;  "gets enemy health, calls combat function (fight). Adds location to hit-list if enemy has been killed,
;   doesn't if player ran away."
;  (print-debug-hit-list "start-combat" hit-list)
;  (let [enemy-health 
;        (:health (:enemy (get-player-loc player map-stack)))]
;  (print-fight-start pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health) 
;    (let [input (read-line)]
;      (let [[new-pc-loc new-map-stack new-pc-inv new-pc-health alive?] 
;            (fight pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health enemy-health input)]
;        (if-not alive?
;          ;enemy is dead, add to hit-list
;          [new-pc-loc new-map-stack new-pc-inv new-pc-health (conj hit-list (get-player-loc new-pc-loc new-map-stack))]
;          ;enemy is not dead, leave hit-list the way it was
;          [new-pc-loc new-map-stack new-pc-inv new-pc-health hit-list])))))
;
;;menu and user options________________________________________________________________________________________
;
;;moving normally________________________________________
;
;(defn parse-user-input [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health]
;  "called from check-move. parses user input.
;  movement: dec/inc returns a new value that is one greater than the value passed in.
;    assoc returns a new map of pc coordinates that is updated according to inc/dec
;  objects: objects, loot, and inventory are placeholders
; 'q' quits program (System call)"
;  (let [input (read-line)]
;    (cond
;      (= input "n") [(assoc pc-loc :row (dec (:row pc-loc))) 
;                       pc-inv 
;                       pc-eq 
;                       pc-health 
;                       pc-damage
;                       max-health
;                       "n"]
;      (= input "s")  [(assoc pc-loc :row (inc (:row pc-loc))) 
;                       pc-inv 
;                       pc-eq 
;                       pc-health 
;                       pc-damage
;                       max-health
;                       "s"]
;      (= input "e")  [(assoc pc-loc :col (inc (:col pc-loc))) 
;                       pc-inv 
;                       pc-eq 
;                       pc-health 
;                       pc-damage
;                       max-health
;                       "e"] 
;      (= input "w")  [(assoc pc-loc :col (dec (:col pc-loc))) 
;                       pc-inv 
;                       pc-eq 
;                       pc-health 
;                       pc-damage
;                       max-health
;                       "w"] 
;      (= input "o") (let [new-pc-inv (obj-control pc-loc map-stack pc-inv)]
;                      [pc-loc 
;                       new-pc-inv 
;                       pc-eq 
;                       pc-health 
;                       pc-damage 
;                       max-health
;                       "o"])
;      (= input "i") (let [[new-pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health] 
;                          (inv-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health)]
;                      [pc-loc 
;                       new-pc-inv 
;                       new-pc-eq 
;                       new-pc-health 
;                       new-pc-damage
;                       new-max-health
;                       "i"])
;      (= input "x") (System/exit 0) 
;      :else (do 
;              (println "That's not a valid choice.") 
;              (pause) 
;              [pc-loc 
;               pc-inv 
;               pc-eq 
;               pc-health 
;               pc-damage 
;               max-health
;               "parse-user-input"]))))
;
;(defn open-user-menu []
;  (with-open [rdr (io/reader "resources/user-menu.txt")]
;    (doseq [line (line-seq rdr)]
;      (println line))))
;
;(defn check-move [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list wall-message-printed old-user-input]
;  (let [[new-loc new-pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health new-user-input] 
;        (parse-user-input pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health)]
;    (print-debug new-pc-inv)
;    (print-debug-hit-list "check-move" hit-list)
;    (if (:desc (get-player-loc new-loc map-stack))
;      (do
;        [new-loc 
;         map-stack 
;         new-pc-inv 
;         new-pc-eq 
;         new-pc-health 
;         new-pc-damage
;         new-max-health
;         hit-list])
;      (do
;        (if (or (= wall-message-printed false) (not= new-user-input old-user-input))
;          (if (not= (first map-stack) level-1-map)
;            (println "There is a wall in that direction. You move back to where you were. What else would you like to do?")
;            (println "You find yourself at the edge of the village and turn back to where you started. What else would you like to do?")))
;        (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list true new-user-input)))))
;
;(defn display-main-menu [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list]
;  "takes pc-loc player coordinates and the map stack
;  prints a menu of options for the user to see
;  then calls parse-user-input
;    parse-user-input returns an updated set of pc coordinates
;  if a description can be pulled from the new pc-loc (from location data stored in map)
;    the new move is valid and the new pc-loc is returned (along with the unchanged map stack). 
;  if not, it's not a valid move, and the old pc-loc is returned (along with the unchanged map stack)"
;  (open-main-menu)
;  (print-debug-hit-list "display-main-menu" hit-list)
;  (if (< pc-health max-health)
;    (do
;      (println)
;      (print "             You're injured! Your current health is ")
;      (print pc-health)
;      (print " out of ")
;      (print max-health)
;      (print ".")
;      (println)))
;  (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list false "display-main-menu"))
;
;;entering______________________________________________
;
;(defn check-push-map [pc-loc map-stack]
;  "called from push-control, takes in pc-loc and map-stack
;  y: player wants to enter new location
;     the new player coordinates are the start coordinates stored in the old locations's :enter keyword
;     the new map (in the old locations's :enter keyword) is pushed onto the map stack
;     true is returned (because map change is true)
;  n/else: old player location, old map, and "false" (because map change is false) is returned"
;  (let [input (read-line)]
;      (cond
;        ;new map is pushed on, so change is "true"
;        (= input "y") [(:start-coords (:enter (get-player-loc player map-stack)))
;                       (conj map-stack (:goto (:enter (get-player-loc player map-stack))))
;                       true]
;        ;old map is returned, so change is "false"
;        (= input "x") [pc-loc 
;                       map-stack 
;                       false]
;        :else (do 
;                (println "That was not a valid choice.") 
;                (pause) 
;                [pc-loc 
;                 map-stack 
;                 false]))))
;
;(defn print-enter [pc-loc map-stack]
;  (print "
;            ________________________
;          =(____   ___      __     _)=
;            |                      |
;            | You can enter a new  |
;            |    area from here.   | 
;            |                      |
;            |     y.....enter      |
;            |   x......stay here   |
;            |__    ___   __    ___ |
;          =(________________________)=")
;  (println)(println)(println)(println)(println))
;
;(defn push-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list]
;  "called from display-menu, takes in pc-loc and map-stack
;  calls check-push-map, which returns an updated pc-coordinate location and map stack, and either true or false
;  if true, new map has been loaded and the new pc-coordinates and map stack are returned to loop recur
;  if false, calls noromal movement functions (display-main-menu)"
;  (print-debug-hit-list "push-control" hit-list)
;  (print-enter pc-loc map-stack)
;  (let [[new-loc new-map-stack true-false] 
;        (check-push-map pc-loc map-stack)]
;    ;will be true if a new map was pushed on 
;    (print-debug-hit-list "push-control 2" hit-list)
;    (if true-false
;      ;load new map
;      [new-loc 
;       new-map-stack 
;       pc-inv 
;       pc-eq 
;       pc-health 
;       pc-damage
;       max-health
;       hit-list]
;      ;move normally
;      (display-main-menu pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list))))
;
;;exiting_____________________________________________
;
;(defn check-pop-map [pc-loc map-stack]
;  "called from pop-control, takes pc-loc and map-stack
;  if y: returns coordinates player should be at in old map, and pops off a map from the stack 
;        (as newest map is the first in the stack, returns stack-first element)
;        returns true as a map change is "true"
;  if n: returns old pc-location, old map-stack, and "false""
;  (let [input (read-line)]
;    (cond
;      ;change is made (popping off a map), so "true"
;      (= input "y") [(:exit-start-coords (get-player-loc player map-stack)) 
;                     (rest map-stack) 
;                     true]
;      ;no change is made to map, so "false"
;      (= input "x") [pc-loc 
;                     map-stack 
;                     false]
;      :else (do 
;              (println "That was not a valid choice. What else would you like to do at this location?") 
;              (pause) 
;              [pc-loc 
;               map-stack 
;               false]))))
;
;(defn print-exit []
;  (print  " 
;            ________________________
;          =(____   ___      __     _)=
;            |                      |
;            |  You can exit this   |
;            |   area from here.    |
;            |                      |
;            |      y.....exit      |
;            |   x.....stay here    |
;            |__    ___   __    ___ |
;          =(________________________)=")
;  (println)(println)(println)(println)(println))
;
;(defn pop-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list]
;  "called from display-menu, takes in pc-loc and map-stack
;  called from check-pop-map, which returns updated pc-loc, map stack, and either true/false
;  if true, returns updated pc-loc and map-stack to loop-recur
;  if false, calls normal movement functions (display-main-menu)"
;  (print-debug-hit-list "pop-control" hit-list)
;  (print-exit)
;  (let [[new-loc new-map-stack true-false] 
;        (check-pop-map pc-loc map-stack)]
;    (print-debug-hit-list "pop-control 2" hit-list)
;    ;will be true if a map was popped off
;    (if true-false
;      ;load new map
;      [new-loc 
;       new-map-stack 
;       pc-inv 
;       pc-eq 
;       pc-health 
;       pc-damage
;       max-health
;       hit-list]
;      ;move normally
;      (display-main-menu pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)))) 
;
;;getting and parsing user input_____________________________
;(defn display-menu [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list]
;  "checks to see if a place can be entered or exited from current player location.
;  if yes, gives player the option to enter/exit
;  if no, just displays normal movement functions (display-main-menu)"
;  (print-debug-hit-list "display-menu" hit-list)
;  (if (:enemy (get-player-loc player map-stack))
;    (if (not (contains? hit-list (get-player-loc player map-stack)))
;      (let [[new-pc-loc new-map-stack new-pc-inv new-pc-health new-hit-list] 
;            (start-combat pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)]
;        (print-debug-hit-list "display-menu 2" hit-list)
;        (clear-screen)
;        (print-loc-desc new-pc-loc new-map-stack)
;        (cond
;          (:enter (get-player-loc new-pc-loc new-map-stack)) (push-control new-pc-loc new-map-stack new-pc-inv pc-eq new-pc-health pc-damage max-health new-hit-list)
;          (:exit-start-coords (get-player-loc new-pc-loc new-map-stack)) (pop-control new-pc-loc new-map-stack new-pc-inv pc-eq new-pc-health pc-damage max-health new-hit-list)
;          :else (display-main-menu new-pc-loc new-map-stack new-pc-inv pc-eq new-pc-health pc-damage max-health new-hit-list)))
;      (cond
;        (:enter (get-player-loc player map-stack)) (push-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)
;        (:exit-start-coords (get-player-loc player map-stack)) (pop-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)
;        :else (display-main-menu pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)))
;    (cond
;      (:enter (get-player-loc player map-stack)) (push-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)
;      (:exit-start-coords (get-player-loc player map-stack)) (pop-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)
;      :else (display-main-menu pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list))))
;

;------- new ---------

;helper functions -----------
(defn get-player-loc [player map-stack]
  (get-in (first map-stack) [(:row player) (:col player)]))

(defn get-loc-desc [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :desc]))

(defn get-loc-loot [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :loot]))

(defn get-loc-loot-desc [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :loot-desc]))

(defn get-loc-enemy [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :enemy]))

(defn get-loc-enter [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :enter]))

(defn get-loc-exit [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :exit]))

;FIXME write help function
(defn help []
  (println "HELP ME!"))

; inventory functions-------------

(defn unequip-item-head [player id]
  ;is item equipped in first hand?
  (if (= (first (:hand (:eq player))) (get id-obj id))
    ;if yes--let first hand be nil
    (assoc-in player [:eq :hand] [nil (last (:hand (:eq player)))])
    ;if no--is item equipped in second hand?
    (if (= (last (:hand (:eq player))) (get id-obj id))
      ;if no--return unchanged
      player
      ;if yes--let second hand be nil
      (assoc-in player [:eq :hand] [(first (:hand (:eq player))) nil]))))

(defn unequip-item [player id]
  (let [item-slot (:slot (get id-obj id))]
    ;because :hand is an array, we need special function
    (println (str "item-slot: " item-slot))
    (println (item-slot (:eq player)))
    (println (get id-obj id))
    (println (= (item-slot (:eq player)) (get id-obj id)))
    (if (= item-slot :hand)
      (unequip-item-head player id)
      ;if not, is item equipped?
      (if (= (item-slot (:eq player)) (get id-obj id))
        ;if it is, let that space just be nil
        (assoc-in player [:eq item-slot] nil)
        player))))

(defn equip-item-head [player id item-slot]
  ;is an item equiped in first hand?
  (if (first (:hand (:eq player)))
    ;if yes--is there an item equipped in 2nd hand?
    (if (last (:hand (:eq player)))
      ;if yes--unequip 2nd item and equip 
      (assoc-in (unequip-item player id) [:eq :hand] [(first (:hand (:eq player))) id])
      ;if no--equip in empty 2nd space
      (assoc-in player [:eq :hand] [(first (item-slot (:eq player))) id]))
    ;if no--equip in empty 1st space
    (assoc-in player [:eq :hand] [id (last (item-slot (:eq player)))])))

(defn equip-item [player id]
  (let [item-slot (:slot (get id-obj id))]
    ;because :hand is an array, we need special function
    (if (= item-slot :hand)
      (equip-item-head player id item-slot)
      ;if something is equipped there...
      (if (item-slot (:eq player))
        ;unequip item and then equip
        (assoc-in (unequip-item player id) [:eq item-slot] id)
        (assoc-in player [:eq item-slot] id)))))

(defn add-to-loc [player map-stack loc-info id]
  (if (get (get-loc-loot player map-stack loc-info) id)
    (assoc-in loc-info [(get-player-loc player map-stack) :loot id] (+ (get-loc-loot player map-stack loc-info) 1))
    (assoc-in loc-info [(get-player-loc player map-stack) :loot id] 1)))

(defn drop-item-hand [player map-stack loc-info id]
  ;if item is equipped and there is only 1 in inventory, unequip then drop
  (if (and (some true? (for [v (:hand (:eq player))] (= v (get id-obj id)))) (= 1 (get (:inv player) id)))
    [(assoc-in (unequip-item player id) [:inv id] (- (get (:inv player) id) 1))
     (add-to-loc player map-stack loc-info id)]
    [(assoc-in player [:inv id] (- (get (:inv player) id) 1))
     (add-to-loc player map-stack loc-info id)]))

(defn drop-item [player map-stack loc-info id]
  (let [item-slot (:slot (get id-obj id))]
    ;because :hand is an array, we need special function
    (if (= item-slot :hand)
      (drop-item-hand player map-stack loc-info id)
      ;is item equipped, and is here only 1 equipped?
      (if (and (item-slot (:eq player)) (= 1 (get (:inv player) id)))
        ;if so, unequip item before dropping
        [(assoc-in (unequip-item player id) [:inv id] (- (get (:inv player) id) 1))
         (add-to-loc player map-stack loc-info id)]
        [(assoc-in player [:inv id] (- (get (:inv player) id) 1))
         (add-to-loc player map-stack loc-info id)]))))

(defn check-inv-id-num [player id]
  (let [id-num (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is an object id
    (if (get id-obj id-num)
      ;check if more than 0 of that item is at that loc
      (if (> (get (:inv player) id-num) 0)
        id-num
        nil)
      nil)))

(defn inv-menu [player map-stack loc-info]
  (clear-screen)
  ;FIXME -- have in library 
  (println "INV!")
  (println (str "player inv: " (:inv player)))
  (println (str "player eq: " (:eq player)))
  ;FIXME -- have in library 
  (let [input (read-line)]
    ;input may contain just letter, or letter and item id
    (cond
      (= (str (first input)) "e") (let [id-num? (check-inv-id-num player (cstr/join (rest input)))]
                                      ;if id-num is a number and matches an item id that's in the inventory and above 0, call equip-item
                                      (if id-num?
                                        (inv-menu (equip-item player id-num?) map-stack loc-info)
                                        (inv-menu player map-stack loc-info)))
      (= (str (first input)) "u") (let [id-num? (check-inv-id-num player (cstr/join (rest input)))]
                                      ;if id-num is a number and matches an item id that's in the inventory and above 0, call unequip-item
                                      (if id-num?
                                        (inv-menu (unequip-item player id-num?) map-stack loc-info)
                                        (inv-menu player map-stack loc-info)))
      (= (str (first input)) "d") (let [id-num? (check-inv-id-num player (cstr/join (rest input)))]
                                      ;if id-num is a number and matches an item id that's in the inventory and above 0, call drop-item
                                      (if id-num?
                                        (let [[new-player new-loc-info] (drop-item player map-stack loc-info id-num?)]
                                          (inv-menu new-player map-stack new-loc-info))
                                        (inv-menu player map-stack loc-info)))
      (= input "r") [player loc-info]
      (= input "h") (do (help) (inv-menu player map-stack loc-info))
      (= input "q") (System/exit 0)
      :else (inv-menu player map-stack loc-info))))
    
; loot functions-------------

(defn dec-loc-loot-item [player map-stack loc-info id]
  (assoc-in loc-info [(get-player-loc player map-stack) :loot id] (- (get (get-loc-loot player map-stack loc-info) id) 1)))

(defn add-item [player map-stack loc-info id]
  (if (get (:inv player) id)
    [(assoc-in player [:inv id] (+ (get-in player [:inv id]) 1))
     (dec-loc-loot-item player map-stack loc-info id)]
    [(assoc-in player [:inv id] 1)
     (dec-loc-loot-item player map-stack loc-info id)]))

(defn add-to-equip-item [player map-stack loc-info id]
  (let [player-added-item (add-item player map-stack loc-info id)]
    [(equip-item player-added-item id)
    ;remove from loc
    (dec-loc-loot-item player map-stack loc-info id)]))

(defn check-loc-id-num [player map-stack loc-info id]
  (println "check-loc-id-num")
  (let [id-num (try (edn/read-string id)
                    (catch Exception e -1))]
    (println (str "id-num: " id-num))
    ;check if input is an object id
    (if (get id-obj id-num)
      ;check if more than one of item is at that loc
      (if (> (get (get-loc-loot player map-stack loc-info) id-num) 0)
        id-num
        nil)
      nil)))

(defn loot-menu [player map-stack loc-info]
  (clear-screen)
  ;FIXME put in library 
  (println "LOOT!")
  (println (str "loc loot: " (get-loc-loot player map-stack loc-info)))
  (println (str "player eq: " (:eq player)))
  ;FIXME put in library 
  (let [input (read-line)]
    ;input may contain just letter, or letter and item id
    (cond
      (= (str (first input)) "a") (let [id-num? (check-loc-id-num player map-stack loc-info (cstr/join (rest input)))]
                                      ;if id-num is a number and matches a loot id that's in the location and above 0, call add-item
                                      (if id-num?
                                        (let [[new-player new-loc-info] (add-item player map-stack loc-info id-num?)]
                                          (loot-menu new-player map-stack new-loc-info))
                                        (loot-menu player map-stack loc-info)))
      (= (str (first input)) "e") (let [id-num? (check-loc-id-num player map-stack loc-info (cstr/join (rest input)))]
                                      ;if id-num is a number and matches a loot id that's in the location and above 0, call add-to-equip-item
                                      (if id-num?
                                        (let [[new-player new-loc-info] (add-to-equip-item player map-stack loc-info id-num?)]
                                          (loot-menu new-player map-stack new-loc-info))
                                        (loot-menu player map-stack loc-info)))
      (= input "r") [player loc-info]
      (= input "h") (do (help) (loot-menu player map-stack loc-info))
      (= input "q") (System/exit 0)
      :else (loot-menu player map-stack loc-info))))
   
;movement and menu----------------

(defn enter-loc [player map-stack loc-info]
  (let [new-map-stack (cons (:goto (get-loc-enter player map-stack loc-info)) map-stack)
        half-updated-player (assoc player :row (get-in (get-loc-enter player map-stack loc-info) [:start-coords :row]))]
    (let [new-player (assoc half-updated-player :col (get-in (get-loc-enter player map-stack loc-info) [:start-coords :col]))]
      [new-player new-map-stack])))

(defn exit-loc [player map-stack loc-info]
  (let [new-map-stack (rest map-stack)
        half-updated-player (assoc player :row (:row (get-loc-exit player map-stack loc-info)))]
    (let [new-player (assoc half-updated-player :col (:col (get-loc-exit player map-stack loc-info)))]
      [new-player new-map-stack])))

(defn drink-hp [player]
  (let [current-health (first (:health player))
        max-health (last (:health player))
        health-gain (int (* 0.25 (last (:health player))))
        hp-count (:hp player)]
    (let [new-health (+ health-gain current-health)
          new-hp-count-player (assoc player :hp (dec hp-count))]
      (if (>= new-health max-health)
        (assoc new-hp-count-player :health [max-health max-health])
        (assoc new-hp-count-player :health [(+ health-gain current-health) max-health])))))

(defn get-user-input [player map-stack loc-info enter? exit? loot? n s e w]
  ;FIXME put in library
  (let [input (read-line)]
    (cond
      (= input "n") (if (first n)
                      [(assoc player :row (dec (:row player))) map-stack loc-info]
                      (get-user-input player map-stack loc-info enter? exit? loot? n s e w))
      (= input "s") (if (first s)
                      [(assoc player :row (inc (:row player))) map-stack loc-info]
                      (get-user-input player map-stack loc-info enter? exit? loot? n s e w))
      (= input "e") (if (first e)
                      [(assoc player :col (inc (:col player))) map-stack loc-info]
                      (get-user-input player map-stack loc-info enter? exit? loot? n s e w))
      (= input "w") (if (first w)
                      [(assoc player :col (dec (:col player))) map-stack loc-info]
                      (get-user-input player map-stack loc-info enter? exit? loot? n s e w))

      (= input "l") (if loot?
                      (let [[new-player new-loc-info] (loot-menu player map-stack loc-info)]
                        [new-player map-stack new-loc-info])
                      (get-user-input player map-stack loc-info enter? exit? loot? n s e w))

      (= input "x") (if enter?
                      (let [[new-player new-map-stack] (enter-loc player map-stack loc-info)]
                        [new-player new-map-stack loc-info])
                      (if exit?
                        (let [[new-player new-map-stack] (exit-loc player map-stack loc-info)]
                          [new-player new-map-stack loc-info])
                        (get-user-input player map-stack loc-info enter? exit? loot? n s e w)))

      (= input "i") (let [[new-player new-loc-info] (inv-menu player map-stack loc-info)]
                      [new-player map-stack new-loc-info])

      (= input "p") (if (> (:hp player) 0)
                      [(drink-hp player) map-stack loc-info]
                      (get-user-input player map-stack loc-info enter? exit? loot? n s e w))

      (= input "q") (System/exit 0)
      :else (get-user-input player map-stack loc-info enter? exit? loot? n s e w))))

;main menu functions -----------

(defn can-enter? [player map-stack loc-info]
  (:new-map-name (get-loc-enter player map-stack loc-info)))

(defn can-exit? [player map-stack loc-info]
  (:old-map-name (get-loc-exit player map-stack loc-info)))

(defn is-there-loot? [player map-stack loc-info]
  ;are there non-zero values in loot?
  (println (str "loc loot: " (get-loc-loot player map-stack loc-info)))
  (println (str "loc loot: " (vals (get-loc-loot player map-stack loc-info))))
  (if (some true? 
            (for [v (vals (get-loc-loot player map-stack loc-info))]
              (if (not= 0 v) 
                true 
                false)))
    [true (:yes (get-loc-loot-desc player map-stack loc-info))]
    [false (:no (get-loc-loot-desc player map-stack loc-info))]))

(defn get-new-loc-desc [loc-info map-stack dir row col]
  (if dir
    (get-in loc-info [(nth (nth (first map-stack) row) col) :desc])
    false))

(defn valid-directions [player map-stack loc-info]
  (let [dec-row (dec (:row player)) 
        inc-row (inc (:row player))
        dec-col (dec (:col player)) 
        inc-col (inc (:col player))]
    (let [n? (get-new-loc-desc loc-info map-stack (>= dec-row 0) dec-row (:col player))
          s? (get-new-loc-desc loc-info map-stack (< inc-row (count (first map-stack))) inc-row (:col player))
          e? (get-new-loc-desc loc-info map-stack (< inc-col (count (ffirst map-stack))) (:row player) inc-col)
          w? (get-new-loc-desc loc-info map-stack (>= dec-col 0) (:row player) dec-col)]
      [[n? "(n)" "North"]
       [s? "(s)" "South"]
       [e? "(e)" "East"]
       [w? "(w)" "West"]])))

;FIXME -- put in lib
(defn main-menu [player map-stack loc-info]
  (let [enter? (can-enter? player map-stack loc-info)
        exit? (can-exit? player map-stack loc-info)
        loot? (is-there-loot? player map-stack loc-info)
        nsew? (valid-directions player map-stack loc-info)]
    (println (str "You are at the: " (get-loc-desc player map-stack loc-info)))
    (println)
    (if enter?
      (println (str "(x) You can enter " enter? " from here.\n")))
    (if exit?
      (println (str "(x) You can exit to " exit? " from here.\n")))
    ; want to make sure that there's a non-0 value
    (if (first loot?) 
      (println (str "(l) " (last loot?)))
      (println (str "    " (last loot?))))
    (println)
      
    ;can't invoke side effects in list comprehensions, so this is the best way
    ; to print directions right now :(
    (let [n (first nsew?)
          s (nth nsew? 1)
          e (nth nsew? 2)
          w (last nsew?)]
      (if (first n)
        (println (str (nth n 1) " To the " (nth n 2) ", you see " (first n))))
      (if (first s)
        (println (str (nth s 1) " To the " (nth s 2) ", you see " (first s))))
      (if (first e)
        (println (str (nth e 1) " To the " (nth e 2) ", you see " (first e))))
      (if (first w)
        (println (str (nth w 1) " To the " (nth w 2) ", you see " (first w))))
      (let [cant-move
            (remove nil? 
                    (flatten 
                      (for [dir nsew?] 
                         (if (false? (first dir))
                           (nth dir 2)))))]
        (if (not (empty? cant-move))
            (println (str "You cannot move " (apply str (interpose ", " cant-move)))))
        (println)
        (println (str "Your health is: " (first (:health player)) "/" (last (:health player))))
        (println (str "Your damage is: " (:damage player)))
        (println)
        (println (str "(p) Drink a Health Potion to restore 25% of your health (" (:hp player) " remaining)"))
        (println "(i) Open your inventory")
        (println)
        (get-user-input player map-stack loc-info enter? exit? (first loot?) n s e w)))))

;check to see if loot needs to go in locations
(defn put-loot-in-new-locs [player map-stack loc-info]
  (let [player-loc-name (get-player-loc player map-stack)]
    (let [get-loot-from (get-in loc-info [player-loc-name :get-loot-from])]
      (if (nil? get-loot-from)
        loc-info ;already been at this location, don't need to add loot
        ;FIXME - shuffle needs to be in a library 
        (let [loot-list (take 2 (shuffle (get-loot-from loot-types)))]
           ;loc loot is stored key: id, value: count
          (let [half-updated-loc-info (assoc-in loc-info [player-loc-name :loot] {(:id (first loot-list)) 1 
                                                                                  (:id (last loot-list)) 1})] 
            (let [new-loc-info (assoc-in half-updated-loc-info [get-loot-from :get-loot-from] nil)]
              new-loc-info)))))))

;MAIN_____________________________________________________________________

(defn -main []
  (open-title)
  (open-intro)
  (loop [player init-player map-stack init-map-stack loc-info init-loc-info]
    (let [updated-loc-info (put-loot-in-new-locs player map-stack loc-info)]
      (do 
        (clear-screen)
        (println (str "LOC: " (get-player-loc player map-stack)))
        (println (str "LOOT: " (apply str (get-loc-loot player map-stack updated-loc-info))))
        (let [[new-player new-map-stack new-loc-info] (main-menu player map-stack updated-loc-info)]
          (recur new-player new-map-stack new-loc-info))))))  
