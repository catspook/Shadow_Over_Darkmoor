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
  (clear-screen)
  (with-open [rdr (io/reader "resources/title.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause-screen3)
  (clear-screen))

(defn open-help []
  (clear-screen)
  (with-open [rdr (io/reader "resources/help.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause)
  (clear-screen))

(defn open-intro []
  "opens intro paragraph, pauses screen, then clears screen"
  (with-open [rdr (io/reader "resources/intro.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause)
  (open-help))

(defn open-inv-menu []
  (with-open [rdr (io/reader "resources/inv-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-inv-menu-end []
  (with-open [rdr (io/reader "resources/inv-menu-end.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-loot-menu []
  (with-open [rdr (io/reader "resources/loot-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-loot-menu-end []
  (with-open [rdr (io/reader "resources/loot-menu-end.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

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

;helper
(defn get-item [id]
  (get id-obj id))

(defn get-r-hand [player]
  (get-item (:r (:hand (:eq player)))))

(defn set-r-hand [player value]
  (assoc-in player [:eq :hand :r] value))

(defn get-l-hand [player]
  (get-item (:l (:hand (:eq player)))))

(defn set-l-hand [player value]
  (assoc-in player [:eq :hand :l] value))

(defn item-eq-in-r-hand? [player id]
  (= (get-r-hand player) (get-item id)))
 
(defn item-eq-in-l-hand? [player id]
  (= (get-l-hand player) (get-item id)))

(defn get-item-slot [id]
  (:slot (get-item id)))

(defn get-eq-slot [player slot]
  (get-item (slot (:eq player))))

(defn item-eq-in-non-hand? [player slot id]
  (= (get-eq-slot player slot) (get-item id)))

(defn set-eq-slot [player slot value] 
  (assoc-in player [:eq slot] value))

(defn get-l-hand-id [player]
  (:l (:hand (:eq player))))
  
(defn get-inv-item-count [player id]
  (get (:inv player) id))

(defn item-already-in-hand [player id]
  ;if item is already equipped and < 2 are in inv, don't equip   
  (if (or (item-eq-in-r-hand? player id)
          (item-eq-in-l-hand? player id))
    (if (< (get-inv-item-count player id) 2)
      true
      false)
    false))

(defn set-inv-item [player id value]
  (assoc-in player [:inv id] value))

(defn is-item-in-inv? [player id-num]
  (some true? (for [k (keys (:inv player))] (= k id-num))))

(defn check-inv-id-num [player id]
  (let [id-num (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is at that loc 
    (if (is-item-in-inv? player id-num)
      ;check if item is "in stock" 
      (if (> (get-inv-item-count player id-num) 0)
        id-num
        nil)
      nil)))

;loot helper funcs
(defn get-loot-item-count [player map-stack loc-info id]
  (get (get-loc-loot player map-stack loc-info) id))

(defn set-loot-item-count [player map-stack loc-info id value]
  (assoc-in loc-info [(get-player-loc player map-stack) :loot id] value))

(defn add-to-loc [player map-stack loc-info id]
  (if (get-loot-item-count player map-stack loc-info id)
    (set-loot-item-count player map-stack loc-info id (inc (get-loot-item-count player map-stack loc-info id)))
    (set-loot-item-count player map-stack loc-info id 1)))

;controller inv functions

(defn add-hp-dmg [player id]
  (let [old-health (:health player)
        old-damage (:damage player)
        item (get-item id)]
  (assoc player :health [(+ (first old-health) (:health item))
                         (+ (last old-health) (:health item))]
                :damage (+ old-damage (:damage item)))))

(defn sub-hp-dmg [player id]
  (let [old-health (:health player)
        old-damage (:damage player)
        item (get-item id)]
  (assoc player :health [(- (first old-health) (:health item))
                         (- (last old-health) (:health item))]
                :damage (- old-damage (:damage item)))))

(defn unequip-item-hand [player id]
  (if (item-eq-in-r-hand? player id)
    (let [uneq-player (sub-hp-dmg player id)]
      (set-r-hand uneq-player nil))
    (if (item-eq-in-l-hand? player id)
      (let [uneq-player (sub-hp-dmg player id)]
        (set-l-hand uneq-player nil))
      player)))

(defn unequip-item [player id]
  (let [item-slot (get-item-slot id)]
    ;because :hand is an array, we need special function
    (if (= item-slot :hand)
      (unequip-item-hand player id)
      (if (item-eq-in-non-hand? player item-slot id)
        (let [uneq-player (sub-hp-dmg player id)]
          (set-eq-slot uneq-player item-slot nil))
        player))))

(defn unequip-eq-hand [player]
  (let [uneq-player (sub-hp-dmg player (get-l-hand-id player))]
    (set-l-hand uneq-player nil)))

(defn unequip-eq [player id]
  (let [old-id ((get-item-slot id) (:eq player))]
    (let [uneq-player (sub-hp-dmg player old-id)]
      (set-eq-slot uneq-player (get-item-slot id) nil))))

(defn equip-item-hand [player id]
  ;if item is already equipped and < 2 are in inv, don't equip   
  (if (item-already-in-hand player id)
    player
    ;new stats for player (used if unequip not needed)
    (let [new-stats-player (add-hp-dmg player id)]
      (if (get-r-hand player)
        (if (get-l-hand player)
          ;both hands are full, so unequip 2nd hand
          (let [uneq-player (unequip-eq-hand player)]
            ;then add item's health to player
            (let [new-player (add-hp-dmg uneq-player id)]
              ;and eq new item
              (set-l-hand new-player id)))
          (set-l-hand new-stats-player id))
        (set-r-hand new-stats-player id)))))

(defn equip-item [player id]
  (let [item-slot (get-item-slot id)]
    ;because :hand is an array, we need special function
    (if (= item-slot :hand)
      (equip-item-hand player id)
      (if (item-slot (:eq player))
        ;unequip item if slot is taken
        (let [uneq-player (unequip-eq player id)]
          ;add new stats to player
          (let [new-player (add-hp-dmg uneq-player id)]
            (set-eq-slot new-player item-slot id)))
        (let [new-player (add-hp-dmg player id)]
          (set-eq-slot new-player item-slot id))))))

(defn drop-item-hand [player map-stack loc-info id]
  ;if item is equipped and there is only 1 in inventory, unequip then drop
  (if (and (or (item-eq-in-r-hand? player id) 
               (item-eq-in-l-hand? player id))
           (= 1 (get-inv-item-count player id)))
    [(let [uneq-player (unequip-item player id)]
       (set-inv-item uneq-player id (dec (get-inv-item-count player id))))
     (add-to-loc player map-stack loc-info id)]
    [(set-inv-item player id (dec (get-inv-item-count player id)))
     (add-to-loc player map-stack loc-info id)]))

(defn drop-item [player map-stack loc-info id]
  (let [item-slot (get-item-slot id)]
    ;because :hand is an array, we need special function
    (if (= item-slot :hand)
      (drop-item-hand player map-stack loc-info id)
      ;is item equipped, and is there only 1 in inv?
      (if (and (item-eq-in-non-hand? player item-slot id) 
               (= 1 (get-inv-item-count player id)))
        ;if so, unequip item before dropping
        [(let [uneq-player (unequip-item player id)]
           (set-inv-item uneq-player id (dec (get-inv-item-count player id))))
         (add-to-loc player map-stack loc-info id)]
        [(set-inv-item player id (dec (get-inv-item-count player id)))
         (add-to-loc player map-stack loc-info id)]))))
    
;view io functions
(defn print-item [player k]
  (let [item (get-item k)]
    ;only print if item count is > 0
    (if (> (get-inv-item-count player k) 0)
      (do

        ;is it equipped?
       (if (= (:slot item) :hand)
          (if (or (item-eq-in-r-hand? player k)
                  (item-eq-in-l-hand? player k))
            (print " ********   ")
            (print "            "))
          ;get the id of the eq slot that's the
          (if (item-eq-in-non-hand? player (get-item-slot k) k)
            (print " ********   ")
            (print "            ")))

        ;print item id
        (print (str (:id item)))

        ;print " " if id < 10
        (if (< (:id item) 10)
          (print "    ")
          (print "   "))

        ;print item's name
        (print (:name item))

        ;print item quantity if > 1 
        (if (> (get-inv-item-count player k) 1)
          (print (str " (" (get-inv-item-count player k) ")"))
          (print "    "))
        ;print extra spaces so things line up
        (doseq [space (repeat (- 28 (count (:name item))) " ")] (print space))

        ;print item health
        (if (> (:health item) -1)
          (do
            (print (str "+" (:health item)))
            (if (< (:health item) 10) (print " ")))
          (do
            (print (str "-" (:health item)))
            (if (> (:health item) -10) (print " "))))

        (print "      ")

        ;print item damage
        (if (> (:damage item) -1)
          (do
            (print (str "+" (:damage item)))
            (if (< (:damage item) 10) (print " ")))
          (do
            (print (str "-" (:damage item)))
            (if (> (:damage item) -10) (print " "))))

        ;print item damage type
        (println (str "      " (:d-type item)))))))

(defn print-inv-menu [player]
  (open-inv-menu)
  (print (str "                 "
              "| \u2665 " 
              (first (:health player)) 
              "/" 
              (last (:health player))))
  (if (> (last (:health player)) 9)
    (if (> (first (:health player)) 9)
      (println "                                 |")
      (println "                                  |"))
    (println "                                   |"))

  (print (str "                 "
              "| \u2718 " 
              (:damage player)))
  (if (> (:damage player) 9)
    (println "                                    |")
    (println "                                     |"))

  (open-inv-menu-end)
  (println "\n\n EQUIPPED . ID . ITEM NAME                     . HEALTH . DAMAGE . DAMAGE TYPE")
  (println " -----------------------------------------------------------------------------")
  (doseq [k (keys (:inv player))] (print-item player k))
  (println))
   
(defn inv-menu [player map-stack loc-info]
  (clear-screen)
  (print-inv-menu player)
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
      (= (str (first input)) "r") (let [id-num? (check-inv-id-num player (cstr/join (rest input)))]
                                      ;if id-num is a number and matches an item id that's in the inventory and above 0, call drop-item
                                      (if id-num?
                                        (let [[new-player new-loc-info] (drop-item player map-stack loc-info id-num?)]
                                          (inv-menu new-player map-stack new-loc-info))
                                        (inv-menu player map-stack loc-info)))
      (= input "x") [player loc-info]
      (= input "h") (do (open-help) (inv-menu player map-stack loc-info))
      (= input "q") (System/exit 0)
      :else (inv-menu player map-stack loc-info))))

;LOOT FUNCTIONS-----------------

;helper
(defn is-item-at-loc [player map-stack loc-info id-num]
  (some true? (for [k (keys (get-loc-loot player map-stack loc-info))] (= k id-num))))

(defn check-loc-id-num [player map-stack loc-info id]
  (let [id-num (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is at that loc
    (if (is-item-at-loc player map-stack loc-info id-num)
      ;check if item is at "in stock"
      (if (> (get-loot-item-count player map-stack loc-info id-num) 0)
        id-num
        nil)
      nil)))

;controller
(defn add-item [player map-stack loc-info id]
  (if (get-inv-item-count player id)
    [(set-inv-item player id (inc (get-inv-item-count player id)))
     (set-loot-item-count player map-stack loc-info id (dec (get-loot-item-count player map-stack loc-info id)))]
    [(set-inv-item player id 1)
     (set-loot-item-count player map-stack loc-info id (dec (get-loot-item-count player map-stack loc-info id)))]))

(defn add-to-equip-item [player map-stack loc-info id]
  (let [[player-added-item loc-remove-item] (add-item player map-stack loc-info id)]
    [(equip-item player-added-item id)
    ;remove from loc
    loc-remove-item]))

;view io
(defn print-loot [player map-stack loc-info k]
  (let [item (get-item k)]
    ;only print if item count is > 0
    (if (> (get-loot-item-count player map-stack loc-info k) 0)
      (do

        ;print item id
        (print (str " " (:id item)))

        ;print " " if id < 10
        (if (< (:id item) 10)
          (print "    ")
          (print "   "))

        ;print item's name
        (print (:name item))

        ;print item quantity if > 1 
        (if (> (get-loot-item-count player map-stack loc-info k) 1)
          (print (str " (" (get-loot-item-count player map-stack loc-info k) ")"))
          (print "    "))
        ;print extra spaces so things line up
        (doseq [space (repeat (- 28 (count (:name item))) " ")] (print space))

        ;print item health
        (if (> (:health item) -1)
          (do
            (print (str "+" (:health item)))
            (if (< (:health item) 10) (print " ")))
          (do
            (print (str "-" (:health item)))
            (if (> (:health item) -10) (print " "))))

        (print "      ")

        ;print item damage
        (if (> (:damage item) -1)
          (do
            (print (str "+" (:damage item)))
            (if (< (:damage item) 10) (print " ")))
          (do
            (print (str "-" (:damage item)))
            (if (> (:damage item) -10) (print " "))))

        ;print item damage type
        (println (str "      " (:d-type item)))))))

(defn print-loot-menu [player map-stack loc-info]
  (open-loot-menu)
  (print (str "             "
              "| \u2665 " 
              (first (:health player)) 
              "/" 
              (last (:health player)))) 
  (if (> (last (:health player)) 9)
    (if (> (first (:health player)) 9)
      (println "                              |")
      (println "                               |"))
    (println "                                |"))

  (print (str "             "
              "| \u2718 " 
              (:damage player)))
  (if (> (:damage player) 9)
    (println "                                 |")
    (println "                                  |"))

  (open-loot-menu-end)
  (println "\n\n ID . ITEM NAME                     . HEALTH . DAMAGE . DAMAGE TYPE")
  (println " ------------------------------------------------------------------")
  (doseq [k (keys (get-loc-loot player map-stack loc-info))] (print-loot player map-stack loc-info k))
  (println))
    
(defn loot-menu [player map-stack loc-info]
  (clear-screen)
  (print-loot-menu player map-stack loc-info)
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
      (= input "x") [player loc-info]
      (= input "h") (do (open-help) (loot-menu player map-stack loc-info))
      (= input "q") (System/exit 0)
      :else (loot-menu player map-stack loc-info))))
   
;MOVEMENT AND MENU----------------

;controller
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

      (= input "h") (do (open-help) (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w))
      (= input "q") (System/exit 0)
      :else (get-user-input player map-stack loc-info enter? exit? loot? inv? n s e w))))

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

;helper funcs
(defn can-enter? [player map-stack loc-info]
  (:new-map-name (get-loc-enter player map-stack loc-info)))

(defn can-exit? [player map-stack loc-info]
  (:old-map-name (get-loc-exit player map-stack loc-info)))

(defn get-new-loc-desc [loc-info map-stack dir row col]
  (if dir
    (get-in loc-info [(nth (nth (first map-stack) row) col) :desc])
    false))

(defn is-there-loot? [player map-stack loc-info]
  ;are thee non-zero values in loot?
  (if (some true? 
            (for [v (vals (get-loc-loot player map-stack loc-info))]
              (if (not= 0 v) 
                true 
                false)))
    [true (:yes (get-loc-loot-desc player map-stack loc-info))]
    [false (:no (get-loc-loot-desc player map-stack loc-info))]))

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

;view io funcs
(defn main-menu [player map-stack loc-info]
  (let [enter? (can-enter? player map-stack loc-info)
        exit? (can-exit? player map-stack loc-info)
        loot? (is-there-loot? player map-stack loc-info)
        nsew? (valid-directions player map-stack loc-info)
        inv? (inv-not-empty? player)]
    (println (str " " (get-loc-desc player map-stack loc-info)))
    (println)
    (if enter?
      (println (str " (x) You can enter " enter? " from here.\n")))
    (if exit?
      (println (str " (x) You can exit to " exit? " from here.\n")))
    ; want to make sure that there's a non-0 value
    (if (first loot?) 
      (println (str " (l) " (last loot?)))
      (println (str " " (last loot?))))
    (println)
      
    ;can't invoke side effects in list comprehensions, so this is the best way
    ; to print directions right now :(
    (let [n (first nsew?)
          s (nth nsew? 1)
          e (nth nsew? 2)
          w (last nsew?)]
      (if (first n)
        (println (str " " (nth n 1) " To the " (nth n 2) ", you see " (first n))))
      (if (first s)
        (println (str " " (nth s 1) " To the " (nth s 2) ", you see " (first s))))
      (if (first e)
        (println (str " " (nth e 1) " To the " (nth e 2) ", you see " (first e))))
      (if (first w)
        (println (str " " (nth w 1) " To the " (nth w 2) ", you see " (first w))))
      (let [cant-move
            (remove nil? 
                    (flatten 
                      (for [dir nsew?] 
                         (if (false? (first dir))
                           (nth dir 2)))))]
        (if (not (empty? cant-move))
            (println (str " You cannot move " (apply str (interpose ", " cant-move)))))
        (println)
        (println (str " Your health is: " (first (:health player)) "/" (last (:health player))))
        (println (str " Your damage is: " (:damage player)))
        (println)
        (if (> (:hp player) 0)
          (println (str " (p) Drink a Health Potion (" (:hp player) " remaining)"))
          (println (str " You are out of Health Potions.")))
        (if inv?
          (println " (i) Open your inventory")
          (println " Your inventory is empty."))
        (println "\n (h) Help!")
        (println " (q) Quit the game")
        (println)
        (get-user-input player map-stack loc-info enter? exit? (first loot?) inv? n s e w)))))

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
