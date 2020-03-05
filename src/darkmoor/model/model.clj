(in-ns 'darkmoor.core)

(load "model/enemy-data")
(load "model/objects-data")
(load "model/location-data")

;GENERAL: helper functions

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

(defn inv-not-empty? [player] 
  (not-every? true? (for [v (vals (:inv player))] (zero? v))))

;set player health

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

;INV: helpers, getters and setters

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
      (if (pos? (get-inv-item-count player id-num))
        id-num
        nil)
      nil)))

;INV: loot-specific helper functions needed for inventory
(defn get-loot-item-count [player map-stack loc-info id]
  (get (get-loc-loot player map-stack loc-info) id))

(defn set-loot-item-count [player map-stack loc-info id value]
  (assoc-in loc-info [(get-player-loc player map-stack) :loot id] value))

(defn add-to-loc [player map-stack loc-info id]
  (if (get-loot-item-count player map-stack loc-info id)
    (set-loot-item-count player map-stack loc-info id (inc (get-loot-item-count player map-stack loc-info id)))
    (set-loot-item-count player map-stack loc-info id 1)))

;INV: add, unequip, and equip functions

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
    ;because :hand is another map, we need special function
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
    ;because :hand is another map, we need special function
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
    ;because :hand is another map, we need special function
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
    
;LOOT: loot getters and setters 

(defn is-item-at-loc [player map-stack loc-info id-num]
  (some true? (for [k (keys (get-loc-loot player map-stack loc-info))] (= k id-num))))

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

;FIGHT: get player damage and damage types

(defn get-player-dmg-types [player]
  "returns a list of the damage types belonging to the player's
   equipped items."
  (for [eq-item (vals (:eq player))] (:d-type (get-item eq-item))))

(defn count-dmg-matches [player en-weak]
   "Returns a count of the number of items the player has equipped whose
    damage type matches an enemy weakness."
   (reduce (fn [matches val] (if val (inc matches) matches)) 
          0
          (for [pl-dmg-type (get-player-dmg-types player) en en-weak] (= pl-dmg-type en))))

(defn get-player-extra-dmg [player en-weak]
  "Adds a 10% damage bonus to the player for every item equipped whose damage type
   matches an enemy weakness."
  (let [extra-dmg-percent (/ (count-dmg-matches player en-weak) 10)]
    (if (pos? extra-dmg-percent)
      (int (+ (:damage player) 
              (* extra-dmg-percent (:damage player))))
      (:damage player))))

;FIGHT: set player health

(defn get-new-player-health [player enemy hit-chance]
  (cond
    (= hit-chance 0) player
    (= hit-chance 9) (assoc player :health [(- (first (:health player)) 
                                               (int (* 1.5 (:damage enemy)))) 
                                            (last (:health player))])
    :else (assoc player :health [(- (first (:health player)) 
                                    (:damage enemy)) 
                                 (last (:health player))])))

;FIGHT: get inv weapons, for fighting inventory

(defn inv-has-weapons? [player]
  (some true? (for [k (keys (:inv player))] (and (not= (get (:inv player) k) 0) 
                                                 (= :hand (get-item-slot k))))))

;FIGHT: set enemy info

(defn get-new-enemy-health [enemy pl-dmg hit-chance]
  (cond
    (= hit-chance 0) enemy
    (= hit-chance 9) (assoc enemy :health (- (:health enemy) 
                                             (int (* 1.5 pl-dmg))))
    :else (assoc enemy :health (- (:health enemy) 
                                  pl-dmg))))

(defn get-enemy-info [player map-stack loc-info]
  (let [enemy (first (shuffle (:desc (first (shuffle (get-loc-enemy player map-stack loc-info))))))
        en-h (int (* 0.4 (last (:health player))))
        en-dmg (int (* 0.4 (:damage player)))
        en-weak (:weak (first (shuffle (get-loc-enemy player map-stack loc-info))))
        en-goes-first? (even? (count (vals (:inv player))))]
    {:desc (first enemy) 
     :attack-desc (second enemy) 
     :dmg-type (last enemy) 
     :weak en-weak 
     :health en-h 
     :damage en-dmg 
     :first? en-goes-first?}))

;movement getters and setters

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

(defn can-enter? [player map-stack loc-info]
  (:new-map-name (get-loc-enter player map-stack loc-info)))

(defn can-exit? [player map-stack loc-info]
  (:old-map-name (get-loc-exit player map-stack loc-info)))

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
      [[n? "n" "North"]
       [s? "s" "South"]
       [e? "e" "East"]
       [w? "w" "West"]])))

;get location info for main menu

(defn is-there-loot? [player map-stack loc-info]
  ;are thee non-zero values in loot?
  (if (some true? 
            (for [v (vals (get-loc-loot player map-stack loc-info))]
                 (if (not= 0 v) 
                   true 
                   false)))
    [true (:yes (get-loc-loot-desc player map-stack loc-info))]
    [false (:no (get-loc-loot-desc player map-stack loc-info))]))

;set loot in locations

(defn put-loot-in-new-locs [player map-stack loc-info]
  (let [player-loc-name (get-player-loc player map-stack)]
    (let [get-loot-from (get-in loc-info [player-loc-name :get-loot-from])]
      (if (nil? get-loot-from)
        loc-info ;already been at this location, don't need to add loot
        (let [loot-list (take 2 (shuffle (get-loot-from loot-types)))]
           ;loc loot is stored key: id, value: count
          (let [half-updated-loc-info (assoc-in loc-info [player-loc-name :loot] {(:id (first loot-list)) 1 
                                                                                  (:id (last loot-list)) 1})] 
            (assoc-in half-updated-loc-info [player-loc-name :get-loot-from] nil)))))))
