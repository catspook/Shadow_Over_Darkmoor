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
  (let [item-id? (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is at that loc 
    (if (is-item-in-inv? player item-id?)
      ;check if item is "in stock" 
      (if (> (get-inv-item-count player item-id?) 0)
        item-id?
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

;LOOT MENU CONTROLLER FUNCTIONS_____________________________
   
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
 
;FIGHT MENU CONTROLLER FUNCTIONS___________________________
  
(defn fight-check-inv-item-id [player id]
  (let [item-id? (try (edn/read-string id)
                    (catch Exception e -1))]
    ;check if input is at that loc  and that it's a weapon
    (if (and (is-item-in-inv? player item-id?)
             (= :hand (get-item-slot item-id?)))
      ;check if item is "in stock" 
      (if (> (get-inv-item-count player item-id?) 0)
        item-id?
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

(defn fight-inv-menu [player map-stack loc-info enemy]
  (clear-screen)
  (print-fight-inv-menu player enemy)
  (let [[command item-id] (parse-fight-inv-input player)]
    (cond
      (= command :exit)    player 
      (= command :help)    (do (open-help) (fight-inv-menu player map-stack loc-info enemy))
      (= command :equip)   (fight-inv-menu (equip-item-hand player item-id) map-stack loc-info enemy)
      (= command :unequip) (fight-inv-menu (unequip-item-hand player item-id) map-stack loc-info enemy))))

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

(defn player-attack-menu [player map-stack loc-info enemy pl-dmg]
  (print-player-attack-menu player)
  (let [command (parse-player-attack-input player)]
    (cond
      (= command :attack) (player-attack player enemy pl-dmg)
      (= command :inv) [(fight-inv-menu player map-stack loc-info enemy ) enemy]
      (= command :hp) (player-attack-menu (player-attack-drink-potion player enemy) map-stack loc-info enemy pl-dmg)
      (= command :help) (do 
                          (open-help) 
                          (clear-screen) 
                          (print-enemy-player-stats player enemy) 
                          (player-attack-menu player map-stack loc-info enemy pl-dmg)))))

(defn fight-loop [player map-stack loc-info enemy]
  (let [pl-dmg (get-player-extra-dmg player (:weak enemy))]
    (clear-screen)
    (print-enemy-player-stats player enemy)
    (if (:first? enemy) ;switch between player and enemy as fight goes on
      (let [new-player (enemy-attack player enemy)]
        (if (<= (first (:health new-player)) 0)
          [new-player enemy]
          (fight-loop new-player map-stack loc-info (assoc enemy :first? false))))
      (let [[new-player new-enemy] (player-attack-menu player map-stack loc-info enemy pl-dmg)]
        (pause-screen1)
        (if (<= (:health new-enemy) 0)
          [new-player new-enemy]
          (fight-loop new-player map-stack loc-info (assoc new-enemy :first? true)))))))

(defn fight-menu [player map-stack loc-info]
  ;are we fighting?
  (if (and (:moved? player) 
           (< (rand-int 10) 4))
    (let [enemy (get-enemy-info player map-stack loc-info)]
      (print-fight-start player map-stack loc-info enemy)
      (let [[new-player enemy] (fight-loop player map-stack loc-info enemy)]
        (did-player-win? new-player enemy)))
    player))

;MOVEMENT AND MAIN MENU CONTROLLERS________________________

(defn parse-main-input [player map-stack loc-info]
  (clear-screen)
  (let [enter? (can-enter? player map-stack loc-info)
        exit? (can-exit? player map-stack loc-info)
        loot? (is-there-loot? player map-stack loc-info)
        [n s e w] (valid-directions player map-stack loc-info)
        inv? (inv-not-empty? player)
        hp? (> (:hp player) 0)]
    (let [cant-move (remove nil? (flatten (for [dir (list n s e w)] (if (false? (first dir)) (last dir)))))]
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
