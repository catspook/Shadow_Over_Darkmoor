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

;OPENING SEQUENCE__________________________________________________________

;FIXME this should ALL be in view section
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
  (pause-screen3)
  (clear-screen))

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

;HELPER FUNCTIONS---------------

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

(defn inv-not-empty? [player] 
  (not-every? true? (for [v (vals (:inv player))] (= v 0))))

;INVENTORY FUNCTIONS---------------

(defn sub-player-hp-damage [player id]
  (let [old-health (:health player)
        old-damage (:damage player)
        item (get id-obj id)]
  (assoc player :health [(- (first old-health) (:health item))
                         (- (last old-health) (:health item))]
                :damage (- old-damage (:damage item)))))
 
(defn unequip-item-hand [player id]
  ;is item equipped in first hand?
  (if (= (get id-obj (first (:hand (:eq player)))) (get id-obj id))
    (assoc-in (sub-player-hp-damage player id) [:eq :hand] [nil (last (:hand (:eq player)))])
    ;is item in second hand?
    (if (= (get id-obj (last (:hand (:eq player)))) id)
      (assoc-in (sub-player-hp-damage player id) [:eq :hand] [(first (:hand (:eq player))) nil])
      player)))

(defn unequip-item [player id]
  (let [item-slot (:slot (get id-obj id))]
    ;because :hand is an array, we need special function
    (if (= item-slot :hand)
      (unequip-item-hand player id)
      ;if not, is item equipped?
      (if (= (get id-obj (item-slot (:eq player))) (get id-obj id))
        ;if it is, let that space just be nil
        (assoc-in (sub-player-hp-damage player id) [:eq item-slot] nil)
        player))))

(defn item-already-in-hand [player id]
  ;if item is already equipped and < 2 are in inv, don't equip   
  (if (some true? (for [k (:hand (:eq player))] (= k id)))    
    (if (< (get (:inv player) id) 2)
      true
      false)
    false))

(defn equip-item-hand [player id]
  ;if item is already equipped and < 2 are in inv, don't equip   
  (if (item-already-in-hand player id)
    player
    ;is an item equiped in first hand?
    (if (first (:hand (:eq player)))
      ;if yes--is there an item equipped in 2nd hand?
      (if (last (:hand (:eq player)))
        ;if yes--unequip 2nd item and equip 
        (assoc-in (unequip-item player id) [:eq :hand] [(first (:hand (:eq player))) id])
        ;if no--equip in empty 2nd space
        (assoc-in player [:eq :hand] [(first (:hand (:eq player))) id]))
      ;if no--equip in empty 1st space
      (assoc-in player [:eq :hand] [id (last (:hand (:eq player)))]))))

(defn add-player-hp-damage [player id]
  (let [old-health (:health player)
        old-damage (:damage player)
        item (get id-obj id)]
  (assoc player :health [(+ (first old-health) (:health item))
                         (+ (last old-health) (:health item))]
                :damage (+ old-damage (:damage item)))))
 
(defn equip-item [player id]
  (let [item-slot (:slot (get id-obj id))]
    ;add health and damage
    (let [updated-player (add-player-hp-damage player id)]
      ;because :hand is an array, we need special function
      (if (= item-slot :hand)
        (equip-item-hand updated-player id)
        ;if slot is taken, unequip it first
        (if (item-slot (:eq updated-player))
          (assoc-in (unequip-item updated-player id) [:eq item-slot] id)
          (assoc-in updated-player [:eq item-slot] id))))))

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

(defn is-item-in-inv [player id-num]
  (some true? (for [k (keys (:inv player))] (= k id-num))))

(defn check-inv-id-num [player id]
  (let [id-num (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is at that loc 
    (if (is-item-in-inv player id-num)
      ;check if item is "in stock" 
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
    
;LOOT FUNCTIONS-----------------

(defn dec-loc-loot-item [player map-stack loc-info id]
  (assoc-in loc-info [(get-player-loc player map-stack) :loot id] (- (get (get-loc-loot player map-stack loc-info) id) 1)))

(defn add-item [player map-stack loc-info id]
  (if (get (:inv player) id)
    [(assoc-in player [:inv id] (+ (get (:inv player) id) 1))
     (dec-loc-loot-item player map-stack loc-info id)]
    [(assoc-in player [:inv id] 1)
     (dec-loc-loot-item player map-stack loc-info id)]))

(defn add-to-equip-item [player map-stack loc-info id]
  (let [[player-added-item loc-remove-item] (add-item player map-stack loc-info id)]
    [(equip-item player-added-item id)
    ;remove from loc
    loc-remove-item]))

(defn is-item-at-loc [player map-stack loc-info id-num]
  (some true? (for [k (keys (get-loc-loot player map-stack loc-info))] (= k id-num))))

(defn check-loc-id-num [player map-stack loc-info id]
  (let [id-num (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is at that loc
    (if (is-item-at-loc player map-stack loc-info id-num)
      ;check if item is at "in stock"
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
   
;MOVEMENT AND MENU----------------

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

(defn get-user-input [player map-stack loc-info enter? exit? loot? inv? n s e w]
  ;FIXME put in library
  (let [input (read-line)]
    (cond
      (= input "n") (if (first n)
                      [(assoc player :row (dec (:row player))) map-stack loc-info]
                      (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w))
      (= input "s") (if (first s)
                      [(assoc player :row (inc (:row player))) map-stack loc-info]
                      (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w))
      (= input "e") (if (first e)
                      [(assoc player :col (inc (:col player))) map-stack loc-info]
                      (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w))
      (= input "w") (if (first w)
                      [(assoc player :col (dec (:col player))) map-stack loc-info]
                      (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w))

      (= input "l") (if loot?
                      (let [[new-player new-loc-info] (loot-menu player map-stack loc-info)]
                        [new-player map-stack new-loc-info])
                      (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w))

      (= input "x") (if enter?
                      (let [[new-player new-map-stack] (enter-loc player map-stack loc-info)]
                        [new-player new-map-stack loc-info])
                      (if exit?
                        (let [[new-player new-map-stack] (exit-loc player map-stack loc-info)]
                          [new-player new-map-stack loc-info])
                        (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w)))

      (= input "i") (if (inv-not-empty? player)
                      (let [[new-player new-loc-info] (inv-menu player map-stack loc-info)]
                        [new-player map-stack new-loc-info])
                      (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w))
      (= input "p") (if (> (:hp player) 0)
                      [(drink-hp player) map-stack loc-info]
                      (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w))

      (= input "q") (System/exit 0)
      :else (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w))))

;MAIN MENU FUNCTIONS--------------

(defn can-enter? [player map-stack loc-info]
  (:new-map-name (get-loc-enter player map-stack loc-info)))

(defn can-exit? [player map-stack loc-info]
  (:old-map-name (get-loc-exit player map-stack loc-info)))

(defn is-there-loot? [player map-stack loc-info]
  ;are there non-zero values in loot?
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
        nsew? (valid-directions player map-stack loc-info)
        inv? (inv-not-empty? player)]
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
        (if inv?
          (println "(i) Open your inventory")
          (println "    Your inventory is empty."))
        (println)
        (get-user-input player map-stack loc-info enter? exit? (first loot?) inv? n s e w)))))

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
            (assoc-in half-updated-loc-info [player-loc-name :get-loot-from] nil)))))))

;MAIN_____________________________________________________________________

(defn -main []
  (open-title)
  (open-intro)
  (loop [player init-player map-stack init-map-stack loc-info init-loc-info]
    (let [updated-loc-info (put-loot-in-new-locs player map-stack loc-info)]
      (do 
        (clear-screen)
        (let [[new-player new-map-stack new-loc-info] (main-menu player map-stack updated-loc-info)]
          (recur new-player new-map-stack new-loc-info))))))  
