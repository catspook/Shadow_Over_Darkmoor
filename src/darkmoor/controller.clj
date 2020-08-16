; The Shadow Over Darkmoor
; copywright (c) CMR 2019-2020 

(in-ns 'darkmoor.core)

(load "model/enemy-data")
(load "model/objects-data")
(load "model/location-data")
(load "model/model")
(load "view")

;INVENTORY MENU CONTROLLER FUNCTIONS__________________________
  
(defn check-inv-item-id [player id]
  "Returns user input as an integer item id.
   Checks for valid id and count above 0."
  (let [item-id? (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is at that loc 
    (if (is-item-in-inv? player item-id?)
      ;check if item is "in stock" 
      (if (pos? (get-inv-item-count player item-id?))
        item-id?
        nil)
      nil)))

(defn parse-inv-input [player]
  (let [input (read-line)]
    (case input
      "x" [:exit nil]
      "q" (System/exit 0) 
      "h" [:help nil]
      (let [command (str (first input))
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
    (case command
      :exit    [(assoc player :moved? false) loc-info]
      :help    (do (open-help) (inv-menu player map-stack loc-info))
      :equip   (inv-menu (equip-item player item-id) map-stack loc-info)
      :unequip (inv-menu (unequip-item player item-id) map-stack loc-info)
      :remove  (let [[new-player new-loc-info] (drop-item player map-stack loc-info item-id)]
                 (inv-menu new-player map-stack new-loc-info)))))

;LOOT MENU CONTROLLER FUNCTIONS_____________________________
   
(defn check-loc-item-id [player map-stack loc-info input]
  "Returns user input as an integer item id.
   Checks for valid id and count above 0."
  (let [item-id? (try (edn/read-string input)
                    (catch Exception e -1))]
    ;check if input is at that loc
    (if (is-item-at-loc player map-stack loc-info item-id?)
      ;check if item is at "in stock"
      (if (pos? (get-loot-item-count player map-stack loc-info item-id?))
        item-id?
        nil)
      nil)))

(defn parse-loot-input [player map-stack loc-info]
  (let [input (read-line)]
    (case input
      "x" [:exit nil]
      "q" (System/exit 0) 
      "h" [:help nil]
      (let [command (str (first input))
                  item-id (check-loc-item-id player map-stack loc-info (cstr/join (rest input)))]
              (cond
                (and item-id (= command "e")) [:equip item-id]
                (and item-id (= command "a")) [:add item-id]
                :else (parse-loot-input player map-stack loc-info))))))

(defn loot-menu [player map-stack loc-info]
  (clear-screen)
  (print-loot-menu player map-stack loc-info)
  (let [[command item-id] (parse-loot-input player map-stack loc-info)]
    (case command
      :exit  [(assoc player :moved? false) loc-info]
      :help  (do (open-help) (loot-menu player map-stack loc-info))
      :equip (let [[new-player new-loc-info] (add-to-equip-item player map-stack loc-info item-id)]
               (loot-menu new-player map-stack new-loc-info))
      :add   (let [[new-player new-loc-info] (add-item player map-stack loc-info item-id)]
               (loot-menu new-player map-stack new-loc-info)))))
 
;FIGHT MENU CONTROLLER FUNCTIONS___________________________
  
(defn fight-check-inv-item-id [player id]
  "Returns user input as an integer item id.
   Checks for valid weapon id and count above 0."
  (let [item-id? (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is at that loc  and that it's a weapon
    (if (and (is-item-in-inv? player item-id?)
             (= :hand (get-item-slot item-id?)))
      ;check if item is "in stock" 
      (if (pos? (get-inv-item-count player item-id?))
        item-id?
        nil)
      nil)))

(defn parse-fight-inv-input [player]
  "Getting and parsing user input for fight-inv-menu."
  (let [input (read-line)]
    (case input
      "x" [:exit nil]
      "q" (System/exit 0) 
      "h" [:help nil]
      (let [command (str (first input))
                  item-id (fight-check-inv-item-id player (cstr/join (rest input)))]
              (cond
                (and item-id (= command "e")) [:equip item-id]
                (and item-id (= command "u")) [:unequip item-id]
                :else (parse-fight-inv-input player))))))

(defn fight-inv-menu [player map-stack loc-info enemy]
  "Called from player-attack-menu if player selects inventory.
   Only supports un/equipping weapons, using normal functions."
  (clear-screen)
  (print-fight-inv-menu player enemy)
  (let [[command item-id] (parse-fight-inv-input player)]
    (case command
      :exit    player 
      :help    (do (open-help) (fight-inv-menu player map-stack loc-info enemy))
      :equip   (fight-inv-menu (equip-item-hand player item-id) map-stack loc-info enemy)
      :unequip (fight-inv-menu (unequip-item-hand player item-id) map-stack loc-info enemy))))

(defn parse-player-attack-input [player]
  "Getting and parsing user input for player-attack-menu"
  (let [input (read-line)]
    (case input
      "a" :attack
      "w" (if (inv-has-weapons? player)
            :inv
            (parse-player-attack-input player))
      "p" (if (pos? (:hp player)) 
            :hp
            (parse-player-attack-input player))
      "h" :help
      "q" (System/exit 0)
      (parse-player-attack-input player))))

(defn player-attack-menu [player map-stack loc-info enemy pl-dmg]
  (print-player-attack-menu player)
  (let [command (parse-player-attack-input player)]
    (case command
      :attack (player-attack player enemy pl-dmg)
      :inv    [(fight-inv-menu player map-stack loc-info enemy ) enemy]
      :hp     (player-attack-menu (player-attack-drink-potion player enemy) map-stack loc-info enemy pl-dmg)
      :help   (do 
               (open-help) 
                (clear-screen) 
                (print-enemy-player-stats player enemy) 
                (player-attack-menu player map-stack loc-info enemy pl-dmg)))))

(defn enemy-attack [player enemy]
  (println (str " " (:attack-desc enemy)))
  (let [hit-chance (rand-int 10)]
    (print-enemy-attack player enemy hit-chance)
    (get-new-player-health player enemy hit-chance)))

(defn fight-loop [player map-stack loc-info enemy]
  "Controls combat logic by calling enemy-attack or player-attack-menu, and then recursing 
   to call the opposite function. Will continue until enemy kills player or player kills enemy."
  (let [pl-dmg (get-player-extra-dmg player (:weak enemy))]
    (clear-screen)
    (print-enemy-player-stats player enemy)
    (if (:first? enemy) ;switch between player and enemy as fight goes on
      (let [new-player (enemy-attack player enemy)]
        (if (not (pos? (first (:health new-player))))
          [new-player enemy]
          (fight-loop new-player map-stack loc-info (assoc enemy :first? false))))
      (let [[new-player new-enemy] (player-attack-menu player map-stack loc-info enemy pl-dmg)]
        (pause-screen1)
        (if (not (pos? (:health new-enemy)))
          [new-player new-enemy]
          (fight-loop new-player map-stack loc-info (assoc new-enemy :first? true)))))))

(defn fight-menu [player map-stack loc-info]
  "Decides if combat will happen, creates enemy info, and calls fight-loop to 
   control combat logic."
  (if (and (:moved? player) 
           (< (rand-int 10) 5))
    (let [enemy (get-enemy-info player map-stack loc-info)]
      (print-fight-start player map-stack loc-info enemy)
      (let [[new-player enemy] (fight-loop player map-stack loc-info enemy)]
        (did-player-win? new-player enemy)))
    player))

;MOVEMENT AND MAIN MENU CONTROLLERS________________________

(defn parse-main-input [player map-stack loc-info]
  "Gathers conditions for valid commands, gets user input, and 
   either returns it or recurses for new input."
  (clear-screen)
  (let [enter? (can-enter? player map-stack loc-info)
        exit? (can-exit? player map-stack loc-info)
        loot? (is-there-loot? player map-stack loc-info)
        [n s e w] (valid-directions player map-stack loc-info)
        inv? (inv-not-empty? player)
        hp? (pos? (:hp player))]
    (let [cant-move (remove nil? (flatten (for [dir (list n s e w)] (if (false? (first dir)) (last dir)))))]
      (print-main-menu player map-stack loc-info enter? exit? loot? n s e w cant-move inv? hp?)
      (let [input (read-line)]
        (cond 
          (= input "m") :map
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
  "Calls fight menu, then calls parse-main-input to get user input, and calls appropriate
   function based on parse-main-input's return value."
  (let [new-player (fight-menu player map-stack loc-info)]
    (let [command (parse-main-input new-player map-stack loc-info)]
      (case command
        :map (do (open-map player map-stack loc-info) (main-menu (assoc new-player :moved? false) map-stack loc-info))
        :enter (let [[enter-player enter-map-stack] (enter-loc new-player map-stack loc-info)]
                 [enter-player enter-map-stack loc-info])
        :exit (let [[exit-player exit-map-stack] (exit-loc new-player map-stack loc-info)]
                [exit-player exit-map-stack loc-info])
        :loot (let [[loot-player loot-loc-info] (loot-menu new-player map-stack loc-info)]
                [loot-player map-stack loot-loc-info])
  
        :n [(assoc new-player :moved? true :row (dec (:row new-player))) map-stack loc-info]
        :s [(assoc new-player :moved? true :row (inc (:row new-player))) map-stack loc-info]
        :e [(assoc new-player :moved? true :col (inc (:col new-player))) map-stack loc-info]
        :w [(assoc new-player :moved? true :col (dec (:col new-player))) map-stack loc-info]
  
        :inv (let [[inv-player inv-loc-info] (inv-menu new-player map-stack loc-info)]
               [inv-player map-stack inv-loc-info])
  
        :hp [(drink-hp new-player) map-stack loc-info]
        :help (do (open-help) (main-menu (assoc new-player :moved? false) map-stack loc-info))))))
