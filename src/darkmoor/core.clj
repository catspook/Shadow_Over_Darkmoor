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
  (println " Hit ENTER to continue.")
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

(defn open-you-died []
  (with-open [rdr (io/reader "resources/you-died.txt")]
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

(defn drink-hp [player]
  (let [current-health (first (:health player))
        max-health (last (:health player))
        health-gain (int (* 0.25 (last (:health player))))
        hp-count (:hp player)]
    (let [new-health (+ health-gain current-health)
          new-hp-player (assoc player :hp (dec hp-count) :moved? false)]
      (if (>= new-health max-health)
        (assoc new-hp-player :health [max-health max-health])
        (assoc new-hp-player :health [(+ health-gain current-health) max-health])))))

;INVENTORY FUNCTIONS---------------

;helper
(defn get-item [id]
  (get id-obj id))

(defn get-r-hand [player]
  (get-item (:r-hand (:eq player))))

(defn set-r-hand [player value]
  (assoc-in player [:eq :r-hand] value))

(defn get-l-hand [player]
  (get-item (:l-hand (:eq player))))

(defn set-l-hand [player value]
  (assoc-in player [:eq :l-hand] value))

(defn item-eq-in-r-hand? [player id]
  (= (get-r-hand player) (get-item id)))
 
(defn item-eq-in-l-hand? [player id]
  (= (get-l-hand player) (get-item id)))

(defn get-item-slot [id]
  (:slot (get-item id)))

(defn get-eq-slot [player slot]
  (get-item (slot (:eq player))))

(defn is-item-eq? [player slot id]
  (= (get-eq-slot player slot) (get-item id)))

(defn set-eq-slot [player slot value] 
  (assoc-in player [:eq slot] value))

(defn get-l-hand-id [player]
  (:l-hand (:eq player)))
  
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

(defn check-inv-item-id [player input]
  (let [id-num (try (edn/read-string input)
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
      (if (is-item-eq? player item-slot id)
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
      (if (and (is-item-eq? player item-slot id) 
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
          (if (is-item-eq? player (get-item-slot k) k)
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
   
(defn check-inv-item-id [player id]
  (let [item-id? (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is at that loc 
    (if (is-item-in-inv? player item-id?)
      ;check if item is "in stock" 
      (if (> (get-inv-item-count player item-id?) 0)
        (do
        (print item-id?)
        item-id?)
        nil)
      nil)))

(defn parse-inv-input [player]
  (let [input (read-line)]
    (cond 
      (= input "x") [:exit nil]
      (= input "q") (System/exit 0) 
      (= input "h") [:help nil]
      :else (let [command (str (first input))
                  item-id (check-inv-item-id player (cstr/join (rest input)))]
              (cond
                (and item-id (= command "e")) [:equip item-id]
                (and item-id (= command "u")) [:unequip item-id]
                (and item-id (= command "r")) [:remove item-id]
                :else (parse-inv-input player))))))

(defn inv-menu [player map-stack loc-info]
  (clear-screen)
  (print-inv-menu player)
  (let [[command item-id] (parse-inv-input player)]
    (cond
      (= command :exit)    [(assoc player :moved? false) loc-info]
      (= command :help)    (do (open-help) (inv-menu player map-stack loc-info))
      (= command :equip)   (inv-menu (equip-item player item-id) map-stack loc-info)
      (= command :unequip) (inv-menu (unequip-item player item-id) map-stack loc-info)
      (= command :remove)  (let [[new-player new-loc-info] (drop-item player map-stack loc-info item-id)]
                             (inv-menu new-player map-stack new-loc-info)))))

;LOOT FUNCTIONS-----------------

;helper
(defn is-item-at-loc [player map-stack loc-info id-num]
  (some true? (for [k (keys (get-loc-loot player map-stack loc-info))] (= k id-num))))

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
   
(defn check-loc-item-id [player map-stack loc-info input]
  (let [item-id? (try (edn/read-string input)
                    (catch Exception e -1))]
    ;check if input is at that loc
    (if (is-item-at-loc player map-stack loc-info item-id?)
      ;check if item is at "in stock"
      (if (> (get-loot-item-count player map-stack loc-info item-id?) 0)
        item-id?
        nil)
      nil)))

(defn parse-loot-input [player map-stack loc-info]
  (let [input (read-line)]
    (cond 
      (= input "x") [:exit nil]
      (= input "q") (System/exit 0) 
      (= input "h") [:help nil]
      :else (let [command (str (first input))
                  item-id (check-loc-item-id player map-stack loc-info (cstr/join (rest input)))]
              (cond
                (and item-id (= command "e")) [:equip item-id]
                (and item-id (= command "a")) [:add item-id]
                :else (parse-loot-input player map-stack loc-info))))))

(defn loot-menu [player map-stack loc-info]
  (clear-screen)
  (print-loot-menu player map-stack loc-info)
  (let [[command item-id] (parse-loot-input player map-stack loc-info)]
    (cond
      (= command :exit)  [(assoc player :moved? false) loc-info]
      (= command :help)  (do (open-help) (loot-menu player map-stack loc-info))
      (= command :equip) (let [[new-player new-loc-info] (add-to-equip-item player map-stack loc-info item-id)]
                           (loot-menu new-player map-stack new-loc-info))
      (= command :add)   (let [[new-player new-loc-info] (add-item player map-stack loc-info item-id)]
                                          (loot-menu new-player map-stack new-loc-info)))))
 
;FIGHT FUNCTIONS-------------------

(defn get-player-dmg-types [player]
  (for [v (vals (:eq player))] (:d-type (get-item v))))

(defn count-dmg-matches [player en-weak]
   (reduce (fn [cnt val] (if val (inc cnt) cnt)) 
          0
          (for [pl (get-player-dmg-types player) en en-weak] (= pl en))))

(defn get-player-extra-dmg [player en-weak]
  (let [extra-dmg-percent (int (/ (* 10 (count-dmg-matches player en-weak)) 100))]
    (let [extra-dmg (* (:damage player) extra-dmg-percent)]
      (if (< extra-dmg 1)
        (+ 1 (:damage player))
        (+ extra-dmg (:damage player))))))

(defn print-enemy-player-stats [player enemy en-h en-dmg en-weak]
  (clear-screen)
  (print (str " " (first enemy) "\n \u2665 " en-h "\n \u2718 " en-dmg))
  (println (str "\n Weak to " (apply str (interpose ", " en-weak)) "."))
  (let [pl-dmg (get-player-extra-dmg player en-weak)]
    (print (str "\n\n THE HERO OF DARKMOOR"  "\n \u2665 " (first (:health player)) "/" (second (:health player)) "\n \u2718 " pl-dmg))
    (let [pl-dmg-types (filter #(and (not (nil? %)) (not= "" %)) (get-player-dmg-types player))]
      (println (str "\n Damage type: " (apply str (interpose ", " pl-dmg-types))))
      (println "\n <<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>\n"))))

(defn get-new-player-health [player en-dmg hit-chance]
  (cond 
    (= hit-chance 0) player
    (= hit-chance 9) (assoc player :health [(- (first (:health player)) (int (* 1.5 en-dmg))) (last (:health player))])
    :else (assoc player :health [(- (first (:health player)) en-dmg) (last (:health player))])))

(defn print-enemy-attack [player enemy en-dmg hit-chance]
  (pause-screen1)
  (cond
    (= hit-chance 0) (println (str " Miss! You take 0 " (last enemy) " damage.\n"))
    (= hit-chance 9) (println (str " Critical hit! You take " (int (* 1.5 en-dmg)) " " (last enemy) " damage.\n"))
    :else (println (str " Hit! You take " en-dmg " " (last enemy) " damage.\n")))
  (pause-screen1)
  (pause))

(defn enemy-attack [player enemy en-dmg]
  (println (str " " (second enemy)))
  (let [hit-chance (rand-int 10)]
    (print-enemy-attack player enemy en-dmg hit-chance)
    (get-new-player-health player en-dmg hit-chance)))

(defn inv-has-weapons? [player]
  (some true? (for [k (keys (:inv player))] (and (not= (get (:inv player) k) 0) 
                                                 (= :hand (get-item-slot k))))))

(defn print-fight-item [player k]
  (let [item (get-item k)]
    ;only print if item count is > 0 and it's a weapon
    (if (and (> (get-inv-item-count player k) 0)
             (= :hand (get-item-slot k)))
      (do

        ;is it equipped?
        (if (or (item-eq-in-r-hand? player k)
                (item-eq-in-l-hand? player k))
          (print " ********   ")
          (print "            "))

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

(defn print-fight-inv-menu [player enemy en-weak]
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
  (println (str "\n\n " (first enemy) " is weak to: " (apply str (interpose ", " en-weak)) "."))
  (println "\n\n EQUIPPED . ID . ITEM NAME                     . HEALTH . DAMAGE . DAMAGE TYPE")
  (println " -----------------------------------------------------------------------------")
  (doseq [k (keys (:inv player))] (print-fight-item player k))
  (println))
   
(defn fight-check-inv-item-id [player id]
  (let [item-id? (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is at that loc  and that it's a weapon
    (if (and (is-item-in-inv? player item-id?)
             (= :hand (get-item-slot item-id?)))
      ;check if item is "in stock" 
      (if (> (get-inv-item-count player item-id?) 0)
        (do
        (print item-id?)
        item-id?)
        nil)
      nil)))

(defn parse-fight-inv-input [player]
  (let [input (read-line)]
    (cond 
      (= input "x") [:exit nil]
      (= input "q") (System/exit 0) 
      (= input "h") [:help nil]
      :else (let [command (str (first input))
                  item-id (fight-check-inv-item-id player (cstr/join (rest input)))]
              (cond
                (and item-id (= command "e")) [:equip item-id]
                (and item-id (= command "u")) [:unequip item-id]
                :else (parse-fight-inv-input player))))))

(defn fight-inv-menu [player map-stack loc-info enemy en-weak]
  (clear-screen)
  (print-fight-inv-menu player enemy en-weak)
  (let [[command item-id] (parse-fight-inv-input player)]
    (cond
      (= command :exit)    player 
      (= command :help)    (do (open-help) (fight-inv-menu player map-stack loc-info enemy en-weak))
      (= command :equip)   (fight-inv-menu (equip-item-hand player item-id) map-stack loc-info enemy en-weak)
      (= command :unequip) (fight-inv-menu (unequip-item-hand player item-id) map-stack loc-info enemy en-weak))))

(defn player-attack-drink-potion [player enemy en-h en-dmg en-weak]
  (println " You drink the potion, and immediately feel a little better.\n\n")
  (pause)
  (clear-screen) 
  (print-enemy-player-stats (drink-hp player) enemy en-h en-dmg en-weak)
  (drink-hp player))

(defn get-new-enemy-health [en-h pl-dmg hit-chance]
  (cond
    (= hit-chance 0) en-h
    (= hit-chance 9) (- en-h (int (* 1.5 pl-dmg)))
    :else (- en-h pl-dmg)))

(defn bonus-weapon-dmg [player enemy pl-dmg]
  (print (str " " (first enemy) " takes "))
  (if (> pl-dmg (:damage player))
    (print (str (int (* 1.5 (- pl-dmg (:damage player))))))
    (print (str (- pl-dmg (:damage player)))))
  (print " bonus weapon damage!\n"))

(defn player-attack [player enemy en-h pl-dmg]
  ;(pause-screen1) 
  (println " You attack!") 
  (let [hit-chance (rand-int 10)] 
    (pause-screen1)
    (cond
      (= 0 hit-chance) (println " Miss! Your hit goes wide and you do 0 damage.")
      (= 9 hit-chance) (println (str " Critical hit! You do " (int (* 1.5 pl-dmg)) " damage."))
      :else (println (str " Hit! You do " pl-dmg " damage.")))
    (if (> pl-dmg (:damage player))
      (bonus-weapon-dmg player enemy pl-dmg))
    (pause-screen1)
    (pause)
    [player (get-new-enemy-health en-h pl-dmg hit-chance)]))

(defn print-player-attack-menu [player]
  (println "\n a Attack!")
  (if (inv-has-weapons? player)
    (println " w Switch out a weapon")
    (println " You have no other weapons to switch to."))
  (if (> (:hp player) 0)
    (println (str " p Drink a health potion (" (:hp player) " remaining)"))
    (println " You are out of health potions."))
  (println " h Help!\n q Quit the game\n\n"))

(defn parse-player-attack-input [player]
  (let [input (read-line)]
    (cond
      (= input "a") :attack
      (= input "w") (if (inv-has-weapons? player)
                      :inv
                      (parse-player-attack-input player))
      (= input "p") (if (> (:hp player) 0) 
                      :hp
                      (parse-player-attack-input player))
      (= input "h") :help
      (= input "q") (System/exit 0)
      :else (parse-player-attack-input player))))

(defn player-attack-menu [player map-stack loc-info enemy en-h en-dmg en-weak pl-dmg]
  (print-player-attack-menu player)
  (let [command (parse-player-attack-input player)]
    (cond
      (= command :attack) (player-attack player enemy en-h pl-dmg)
      (= command :inv) [(fight-inv-menu player map-stack loc-info enemy en-weak) en-h]
      (= command :hp) (player-attack-menu (player-attack-drink-potion player enemy en-h en-dmg en-weak) map-stack loc-info enemy en-h en-dmg en-weak pl-dmg)
      (= command :help) (do 
                          (open-help) 
                          (clear-screen) 
                          (print-enemy-player-stats player enemy en-h en-dmg en-weak) 
                          (player-attack-menu player map-stack loc-info enemy en-h en-dmg en-weak pl-dmg)))))

(defn fight-loop [player map-stack loc-info enemy en-h en-dmg en-weak en-goes-first?]
  (let [pl-dmg (get-player-extra-dmg player en-weak)]
    (clear-screen)
    (print-enemy-player-stats player enemy en-h en-dmg en-weak)
    (if en-goes-first? ;switch between player and enemy as fight goes on
      (let [new-player (enemy-attack player enemy en-dmg)]
        (if (<= (first (:health new-player)) 0)
          [new-player enemy]
          (fight-loop new-player map-stack loc-info enemy en-h en-dmg en-weak false)))
      (let [[new-player new-en-h] (player-attack-menu player map-stack loc-info enemy en-h en-dmg en-weak pl-dmg)]
        (pause-screen1)
        (if (<= new-en-h 0)
          [new-player enemy]
          (fight-loop new-player map-stack loc-info enemy new-en-h en-dmg en-weak true))))))

(defn print-fight-start [player map-stack loc-info enemy]
  (clear-screen)
  (println (str " You arrive at " (get-loc-desc player map-stack loc-info) ".\n"))
  (pause-screen1)
  (println (str " " (first enemy) " jumps out and attacks you!\n"))
  (pause-screen1)
  (println " FIGHT!\n")
  (pause-screen1)
  (pause))

(defn did-player-win? [player enemy]
  (if (<= (first (:health player)) 0)
    (do
      (clear-screen)
      (open-you-died)
      (System/exit 0))
    (do 
      (clear-screen)
      (println (str " You killed " (first enemy) "!"))
      (println " You gained a health potion!\n")
      (pause-screen1)
      (pause)
      (clear-screen)
      (assoc player :hp (inc (:hp player)) :moved? false))))

(defn get-enemy-info [player map-stack loc-info]
    (let [rand-enemy-index (rand-int (count (get-loc-enemy player map-stack loc-info)))]
      (let [enemy-type (nth (get-loc-enemy player map-stack loc-info) rand-enemy-index)
            en-h (int (* 0.4 (last (:health player))))
            en-dmg (int (* 0.4 (:damage player)))]
        (let [rand-desc-index (rand-int (count (:desc enemy-type)))]
          (let [enemy (nth (:desc enemy-type) rand-desc-index)
                en-weak (:weak enemy-type)
                en-goes-first? (even? (count (vals (:inv player))))]
            ;{:desc (first enemy) :attack-desc (second enemy) :dmg-type (last enemy) :health en-h :dmg en-dmg :first? en-goes-first?}
            [enemy en-h en-dmg en-weak en-goes-first?])))))

(defn fight-menu [player map-stack loc-info]
  ;are we fighting?
  (if (and (:moved? player) 
           (< (rand-int 10) 4))
    (let [[enemy en-h en-dmg en-weak en-goes-first?] (get-enemy-info player map-stack loc-info)]
      (print-fight-start player map-stack loc-info enemy)
      (let [[new-player enemy] (fight-loop player map-stack loc-info enemy en-h en-dmg en-weak en-goes-first?)]
        (did-player-win? new-player enemy)))
    player))

;MOVEMENT AND MENU----------------

(defn enter-loc [player map-stack loc-info]
  (let [new-map-stack (cons (:goto (get-loc-enter player map-stack loc-info)) map-stack)
        new-player (assoc player :row (get-in (get-loc-enter player map-stack loc-info) [:start-coords :row])
                                 :col (get-in (get-loc-enter player map-stack loc-info) [:start-coords :col])
                                 :moved? true)]
      [new-player new-map-stack]))

(defn exit-loc [player map-stack loc-info]
  (let [new-map-stack (rest map-stack)
        new-player (assoc player :row (:row (get-loc-exit player map-stack loc-info))
                                 :col (:col (get-loc-exit player map-stack loc-info))
                                 :moved? true)]
      [new-player new-map-stack]))

;main menu parsing and printing
(defn print-main-menu [player map-stack loc-info enter? exit? loot? n s e w cant-move inv? hp?]
  (println (str " You are at " (get-loc-desc player map-stack loc-info)))
  (println)
  (if enter?
    (println (str " x  You can enter " enter? " from here.\n")))
  (if exit?
    (println (str " x  You can exit to " exit? " from here.\n")))
  ; want to make sure that there's a non-0 value
  (if (first loot?) 
    (println (str " l  " (last loot?)))
    (println (str " " (last loot?))))
  (println "\n <<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>\n")
  
  (if (first n)
    (println (str " " (second n) "  To the " (last n) ", you see " (first n))))
  (if (first s)
    (println (str " " (second s) "  To the " (last s) ", you see " (first s))))
  (if (first e)
    (println (str " " (second e) "  To the " (last e) ", you see " (first e))))
  (if (first w)
  (println (str " " (second w) "  To the " (last w) ", you see " (first w))))
  (if (not (empty? cant-move))
      (println (str " You cannot move " (apply str (interpose ", " cant-move)) ".")))
  (println "\n <<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>\n")

  (println (str "    \u2665 " (first (:health player)) "/" (last (:health player))))
  (if hp?
    (println (str " p  Drink a Health Potion (" (:hp player) " remaining)"))
    (println (str " You are out of Health Potions.")))
  (println)
  (println (str "    \u2718 " (:damage player)))
  (if inv?
    (println " i  Open your inventory")
    (println " Your inventory is empty."))

  (println "\n <<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>\n")
  (println " h  Help!")
  (println " q  Quit the game")
  (println))

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
      [[n? "n" "North"]
       [s? "s" "South"]
       [e? "e" "East"]
       [w? "w" "West"]])))

(defn parse-main-input [player map-stack loc-info]
  (clear-screen)
  (let [enter? (can-enter? player map-stack loc-info)
        exit? (can-exit? player map-stack loc-info)
        loot? (is-there-loot? player map-stack loc-info)
        [n s e w] (valid-directions player map-stack loc-info)
        inv? (inv-not-empty? player)
        hp? (> (:hp player) 0)]
    (let [cant-move (remove nil? (flatten (for [dir (list n s e w)] (if (false? (first dir)) (second dir)))))]
      (print-main-menu player map-stack loc-info enter? exit? loot? n s e w cant-move inv? hp?)
      (let [input (read-line)]
        (cond 
          (and enter? 
               (= input "x")) :enter
          (and exit? 
               (= input "x")) :exit
          (and loot? 
               (= input "l")) :loot
          (and (first n) 
               (= input "n")) :n
          (and (first s) 
               (= input "s")) :s
          (and (first e) 
               (= input "e")) :e
          (and (first w) 
               (= input "w")) :w
          (and inv? 
               (= input "i")) :inv
          (and hp? 
               (= input "p")) :hp
          (= input "h") :help 
          (= input "q") (System/exit 0)
          :else (parse-main-input player map-stack loc-info))))))

(defn main-menu [player map-stack loc-info]
  (let [new-player (fight-menu player map-stack loc-info)]
    (let [command (parse-main-input new-player map-stack loc-info)]
      (cond
        (= command :enter) (let [[enter-player enter-map-stack] (enter-loc new-player map-stack loc-info)]
                             [enter-player enter-map-stack loc-info])
        (= command :exit)  (let [[exit-player exit-map-stack] (exit-loc new-player map-stack loc-info)]
                             [exit-player exit-map-stack loc-info])
        (= command :loot)  (let [[loot-player loot-loc-info] (loot-menu new-player map-stack loc-info)]
                             [loot-player map-stack loot-loc-info])
  
        (= command :n) [(assoc new-player :moved? true :row (dec (:row new-player))) map-stack loc-info]
        (= command :s) [(assoc new-player :moved? true :row (inc (:row new-player))) map-stack loc-info]
        (= command :e) [(assoc new-player :moved? true :col (inc (:col new-player))) map-stack loc-info]
        (= command :w) [(assoc new-player :moved? true :col (dec (:col new-player))) map-stack loc-info]
  
        (= command :inv)  (let [[inv-player inv-loc-info] (inv-menu new-player map-stack loc-info)]
                            [inv-player map-stack inv-loc-info])
  
        (= command :hp) [(drink-hp new-player) map-stack loc-info]
        (= command :help)  (do (open-help) (main-menu (assoc new-player :moved? false) map-stack loc-info))))))

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
