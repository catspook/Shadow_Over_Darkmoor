(in-ns 'darkmoor.core)

(load "model/enemy-data") (load "model/objects-data")
(load "model/location-data")

;GENERAL: helper functions

(defn get-player-loc [player map-stack]
  "Get name of player's current location, based on cell coordinates"
  (get-in (first map-stack) [(:row player) (:col player)]))

(defn get-loc-map [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :map]))

(defn get-loc-desc [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :desc]))

(defn get-loc-loot [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :loot]))

(defn get-loc-loot-desc [player map-stack loc-info]
  "Prints differently if there is loot at a location or if it's empty."
  (get-in loc-info [(get-player-loc player map-stack) :loot-desc]))

(defn get-loc-enemy [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :enemy]))

(defn get-loc-enter [player map-stack loc-info]
  "Prints if player can move to a new map from current location."
  (get-in loc-info [(get-player-loc player map-stack) :enter]))

(defn get-loc-exit [player map-stack loc-info]
  "Prints if player can return to an old map from current location."
  (get-in loc-info [(get-player-loc player map-stack) :exit]))

(defn inv-not-empty? [player] 
  (not-every? true? (for [v (vals (:inv player))] (zero? v))))

(defn drink-hp [player]
  "Increases player health by 25% of total and decreases health-potion count."
  (let [current-health (first (:health player))
        max-health (last (:health player))
        health-gain (int (* 0.25 (last (:health player))))
        hp-count (:hp player)]
    (let [new-health (+ health-gain current-health)
          new-hp-player (assoc player :hp (dec hp-count) :moved? false)]
      (if (>= new-health max-health)
        (assoc new-hp-player :health [max-health max-health])
        (assoc new-hp-player :health [(+ health-gain current-health) max-health])))))

;INVENTORY

(defn get-item [id]
  "Gets item information based on item id"
  (get id-obj id))

; l-hand and r-hand functions are specified due to
; extra checks that need to happen regarding weapons
(defn get-r-hand [player]
  "Gets item information of item equipped in r-hand slot"
  (get-item (:r-hand (:eq player))))

(defn set-r-hand [player value]
  "For equipping: sets item information in r-hand slot"
  (assoc-in player [:eq :r-hand] value))

(defn get-l-hand [player]
  "Gets item information of item equipped in l-hand slot"
  (get-item (:l-hand (:eq player))))

(defn set-l-hand [player value]
  "For equipping: sets item information in l-hand slot"
  (assoc-in player [:eq :l-hand] value))

(defn item-eq-in-r-hand? [player id]
  "Is the specified item the same as the item 
   currently equipped in the r-hand slot?"
  (= (get-r-hand player) (get-item id)))
 
(defn item-eq-in-l-hand? [player id]
  "Is the specified item the same as the item 
   currently equipped in the l-hand slot?"
  (= (get-l-hand player) (get-item id)))

(defn get-item-slot [id]
  "Get the slot this item should be equipped in."
  (:slot (get-item id)))

(defn get-eq-slot [player slot]
  "Get the item that is currently equipped in this slot."
  (get-item (slot (:eq player))))

(defn is-item-eq? [player slot id]
  "Is the specified item the same as the item 
   currently equipped in this slot?"
  (= (get-eq-slot player slot) (get-item id)))

(defn set-eq-slot [player slot value] 
  "For equipping: sets item information in specified slot"
  (assoc-in player [:eq slot] value))

(defn get-l-hand-id [player]
  "Get the id of the item currently equipped in l-hand"
  (:l-hand (:eq player)))
  
(defn get-inv-item-count [player id]
  "How many items with this id are in the player's inventory?
   (Can be zero, but never negative)"
  (get (:inv player) id))

(defn item-already-in-hand [player id]
  "If the specified item is already equipped in a hand,
   and there's only 1 of that item in inventory, don't let player
   equip that item in the other hand as well."
  (if (or (item-eq-in-r-hand? player id)
          (item-eq-in-l-hand? player id))
    (if (< (get-inv-item-count player id) 2)
      true
      false)
    false))

(defn set-inv-item [player id value]
  "Sets count of the selected item in player's inventory to a particular value."
  (assoc-in player [:inv id] value))

(defn is-item-in-inv? [player id]
  "Does player have the specified item in their inventory?"
  (some true? (for [k (keys (:inv player))] (= k id))))

(defn get-loot-item-count [player map-stack loc-info id]
  "Gets the count of the selected item at the current player location."
  (get (get-loc-loot player map-stack loc-info) id))

(defn set-loot-item-count [player map-stack loc-info id value]
  "Sets the count of the selected item at the current player 
   location to a particular value. Adds kv pair if item
   has not been in location before."
  (assoc-in loc-info [(get-player-loc player map-stack) :loot id] value))

(defn add-to-loc [player map-stack loc-info id]
  "When player drops an item: if that item has been at this location before,
   increment item's count at location. If not, sets its count to 1." 
  (if (get-loot-item-count player map-stack loc-info id)
    (set-loot-item-count player map-stack loc-info id (inc (get-loot-item-count player map-stack loc-info id)))
    (set-loot-item-count player map-stack loc-info id 1)))

; NOTE: -hand functions below are needed because items equipped in r-hand or l-hand  
;       need to be tagged with :hand instead of :r-hand or :l-hand, and so process 
;       to look up which slot to equip in and unequip is different then with other slots.

(defn add-hp-dmg [player id]
  "Adds item's health and damage stats to player's"
  (let [old-health (:health player)
        old-damage (:damage player)
        item (get-item id)]
  (assoc player :health [(+ (first old-health) (:health item))
                         (+ (last old-health) (:health item))]
                :damage (+ old-damage (:damage item)))))

(defn sub-hp-dmg [player id]
  "Subtracts item's health and damage stats from player's"
  (let [old-health (:health player)
        old-damage (:damage player)
        item (get-item id)]
  (assoc player :health [(- (first old-health) (:health item))
                         (- (last old-health) (:health item))]
                :damage (- old-damage (:damage item)))))

(defn unequip-item-hand [player id]
  "For unequipping a weapon at player's command:
   sets slot item was equipped in to nil, and subtracts
   item's stats from player's."
  (if (item-eq-in-r-hand? player id)
    (let [uneq-player (sub-hp-dmg player id)]
      (set-r-hand uneq-player nil))
    (if (item-eq-in-l-hand? player id)
      (let [uneq-player (sub-hp-dmg player id)]
        (set-l-hand uneq-player nil))
      player)))

(defn unequip-item [player id]
  "For unequipping an item at player's command: 
   sets slot item was equipped in to nil, and subtracts item's
   stats from player's."
  (let [item-slot (get-item-slot id)]
    (if (= item-slot :hand)
      (unequip-item-hand player id)
      (if (is-item-eq? player item-slot id)
        (let [uneq-player (sub-hp-dmg player id)]
          (set-eq-slot uneq-player item-slot nil))
        player))))

(defn unequip-eq-hand [player]
  "For unequipping item in l-hand or r-hand before equipping
   some other item in that slot."
  (let [uneq-player (sub-hp-dmg player (get-l-hand-id player))]
    (set-l-hand uneq-player nil)))

(defn unequip-eq [player id]
  "For unequipping items in non-hand slots before equipping
   some other item in that slot."
  (let [old-id ((get-item-slot id) (:eq player))]
    (let [uneq-player (sub-hp-dmg player old-id)]
      (set-eq-slot uneq-player (get-item-slot id) nil))))

(defn equip-item-hand [player id]
  "Adds an item id to an item slot in player's equipped list
   and updates player stats. If slot is not open, unequips 
   item in slot before adding item stats to player. Does nothing
   if item is already equipped in a hand and only 1 of item is
   in inventory."
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
  "Adds an item id to an item slot in player's equipped list
   and updates player stats. If slot is not open, unequips 
   item in slot before adding item stats to player."
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
  "Removes an item from player's inventory, and 
   adds to location. If item was equipped and only 1 was 
   in inventory, unequips first."
  (if (and (or (item-eq-in-r-hand? player id) 
               (item-eq-in-l-hand? player id))
           (= 1 (get-inv-item-count player id)))
    [(let [uneq-player (unequip-item player id)]
       (set-inv-item uneq-player id (dec (get-inv-item-count player id))))
     (add-to-loc player map-stack loc-info id)]
    [(set-inv-item player id (dec (get-inv-item-count player id)))
     (add-to-loc player map-stack loc-info id)]))

(defn drop-item [player map-stack loc-info id]
  "Removes an item from player's inventory, and 
   adds to location. If item was equipped and only 1 was 
   in inventory, unequips first."
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
    
;LOOT

(defn is-item-at-loc [player map-stack loc-info id-num]
  "Returns true if item has been at location before (id will be present, but count
   may be zero)"
  (some true? (for [k (keys (get-loc-loot player map-stack loc-info))] (= k id-num))))

(defn add-item [player map-stack loc-info id]
  "Returns vector of player (after adding item to inventory) and 
   location (after decrementing item count in location's loot)"
  (if (get-inv-item-count player id)
    [(set-inv-item player id (inc (get-inv-item-count player id)))
     (set-loot-item-count player map-stack loc-info id (dec (get-loot-item-count player map-stack loc-info id)))]
    [(set-inv-item player id 1)
     (set-loot-item-count player map-stack loc-info id (dec (get-loot-item-count player map-stack loc-info id)))]))

(defn add-to-equip-item [player map-stack loc-info id]
  "Returns vector of player after adding item to player's inventory 
   and equipping item, and location information after removing item from location."
  (let [[player-added-item loc-remove-item] (add-item player map-stack loc-info id)]
    [(equip-item player-added-item id)
    loc-remove-item]))

;FIGHT

(defn get-player-dmg-types [player]
  "Returns a list of the damage types belonging to the player's
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
   matches an enemy weakness. Returns updated player damage."
  (let [extra-dmg-percent (/ (count-dmg-matches player en-weak) 10)]
    (if (pos? extra-dmg-percent)
      (int (+ (:damage player) 
              (* extra-dmg-percent (:damage player))))
      (:damage player))))

(defn get-new-player-health [player enemy hit-chance]
  "Returns new player health, based on enemy missing (0 dmg), hitting 
   (normal enemy damage), or critting (150% enemy damage)"
  (cond
    (= hit-chance 0) player
    (= hit-chance 9) (assoc player :health [(- (first (:health player)) 
                                               (int (* 1.5 (:damage enemy)))) 
                                            (last (:health player))])
    :else (assoc player :health [(- (first (:health player)) 
                                    (:damage enemy)) 
                                 (last (:health player))])))

(defn inv-has-weapons? [player]
  "Returns true if player's inventory as at least 1 item tagged with :hand slot
   whose count is above 0."
  (some true? (for [k (keys (:inv player))] (and (not= (get (:inv player) k) 0) 
                                                 (= :hand (get-item-slot k))))))

(defn get-new-enemy-health [enemy pl-dmg hit-chance]
  "Returns new enemy health, based on player missing (0 dmg), hitting
   (normal player dmg), or critting (150% player dmg)"
  (cond
    (= hit-chance 0) enemy
    (= hit-chance 9) (assoc enemy :health (- (:health enemy) 
                                             (int (* 1.5 pl-dmg))))
    :else (assoc enemy :health (- (:health enemy) 
                                  pl-dmg))))

(defn get-enemy-info [player map-stack loc-info]
  "Returns map of enemy name, attacking description, damage type, weaknesses,
   health, damage, and if enemy attacks before player."
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

; MOVEMENT

(defn enter-loc [player map-stack loc-info]
  "Adds new map to map-stack, and sets player coordinates to appropriate start positions in new map."
  (let [new-map-stack (cons (:goto (get-loc-enter player map-stack loc-info)) map-stack)
        new-player (assoc player :row (get-in (get-loc-enter player map-stack loc-info) [:start-coords :row])
                                 :col (get-in (get-loc-enter player map-stack loc-info) [:start-coords :col])
                                 :moved? true)]
      [new-player new-map-stack]))

(defn exit-loc [player map-stack loc-info]
  "Pops top map off of map stack (effectively returning to old map), and sets player 
   coordinates in old map based on which map they returned from."
  (let [new-map-stack (rest map-stack)
        new-player (assoc player :row (:row (get-loc-exit player map-stack loc-info))
                                 :col (:col (get-loc-exit player map-stack loc-info))
                                 :moved? true)]
      [new-player new-map-stack]))

(defn can-enter? [player map-stack loc-info]
  "Returns either the name of a map the player can enter to from their current location, or nil."
  (:new-map-name (get-loc-enter player map-stack loc-info)))

(defn can-exit? [player map-stack loc-info]
  "Returns either the name of a map the player can exit to from their current location, or nil."
  (:old-map-name (get-loc-exit player map-stack loc-info)))

(defn get-new-loc-desc [loc-info map-stack dir row col]
  "If dir represents a valid cell in this map's vector, returns description of 
   location specified by the inputted row and column values, otherwise false."
  (if dir
    (get-in loc-info [(nth (nth (first map-stack) row) col) :desc])
    false))

(defn valid-directions [player map-stack loc-info]
  "Returns a vector containing the description of the cell directly to the row above, row below, 
   column to the right, and column to the left, as well as strings to be used in printing."
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

(defn is-there-loot? [player map-stack loc-info]
  "Returns true if there is at least 1 item in location's loot
   whose count is above zero, as well as appropriate loot description."
  (if (some true? 
            (for [v (vals (get-loc-loot player map-stack loc-info))]
                 (if (not= 0 v) 
                   true 
                   false)))
    [true (:yes (get-loc-loot-desc player map-stack loc-info))]
    [false (:no (get-loc-loot-desc player map-stack loc-info))]))

; SET LOCATION LOOT

(defn put-loot-in-new-locs [player map-stack loc-info]
  "Generates two items to set in player's current location, if 
   this is first time player is visiting this location."
  (let [player-loc-name (get-player-loc player map-stack)]
    (let [get-loot-from (get-in loc-info [player-loc-name :get-loot-from])]
      (if (nil? get-loot-from)
        loc-info ;already been at this location, don't need to add loot
        (let [loot-list (take 2 (shuffle (get-loot-from loot-types)))]
           ;loc loot is stored key: id, value: count
          (let [half-updated-loc-info (assoc-in loc-info [player-loc-name :loot] {(:id (first loot-list)) 1 
                                                                                  (:id (last loot-list)) 1})] 
            (assoc-in half-updated-loc-info [player-loc-name :get-loot-from] nil)))))))
