(in-ns 'darkmoor.core)

(load "model/enemy-data") 
(load "model/objects-data")
(load "model/location/location-data")

; LOCATION helper functions

(defn get-player-loc [player map-stack]
  (get-in (first map-stack) [(:row player) (:col player)]))

(defn get-loc-map [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :map]))

(defn get-loc-desc [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :desc]))

(defn get-loc-loot [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :loot]))

(defn get-loc-loot-desc [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :loot-desc]))

(defn get-loc-enemy [player map-stack loc-info]
  (get-in loc-info [(get-player-loc player map-stack) :enemy]))

(defn get-loc-enter [player map-stack loc-info]
  ; gives info of new map, if one can be entered from current location
  (get-in loc-info [(get-player-loc player map-stack) :enter]))

(defn get-loc-exit [player map-stack loc-info]
  ; gives info of old map, if one can be returned to from current location
  (get-in loc-info [(get-player-loc player map-stack) :exit]))

; HEALTH helper functions

(defn get-new-health [current-health max-health]
  (let [new-health (+ current-health (int (* 0.25 max-health)))]
    (if (< new-health max-health)
      new-health
      max-health)))

(defn drink-hp [player]
  ; inc player health by 25% and dec health-potion count
  (let [max-health (last (:health player))
        new-health (get-new-health (first (:health player)) max-health)]
    (assoc player :hp (dec (:hp player)) :moved? false :health [new-health max-health])))

;INVENTORY helper functions

(defn inv-not-empty? [player] 
  (not-every? true? (for [v (vals (:inv player))] (zero? v))))

(defn get-item [id]
  ; gets item info based on id
  (get id-obj id))

(defn get-hands [player]
  (:hand (:eq player)))

(defn get-id-in-hand [player id]
  (some #{id} (get-hands player)))

(defn get-item-slot [id]
  ; gets slot this item should be equipped in
  (:slot (get-item id)))

(defn get-eq-slot [player slot]
  ; get item that's currently equipped in this slot
  (get-item (slot (:eq player))))

(defn is-item-eq? [player slot id]
  (if (= slot :hand)
    (and (not (nil? id)) (= (get-id-in-hand player id) id))
    (and (not (nil? id)) (= (get-eq-slot player slot) (get-item id)))))

(defn set-eq-slot [player slot old-id new-id] 
  ; sets item info in specified equipment slot
  (if (= slot :hand)
    ; on hands, need to remove old id from vector first
    (let [[no-matches one-match] (split-with (partial not= old-id) (get-hands player))]
      (assoc-in player [:eq :hand] (conj (concat no-matches (rest one-match)) new-id)))
    (assoc-in player [:eq slot] new-id)))

(defn get-inv-item-count [player id]
  ; how many of this sepcific item are in the player's inventory
  (get (:inv player) id))

(defn set-inv-item [player id value]
  ; sets inventory count of specified item to specific value
  (assoc-in player [:inv id] value))

(defn is-item-in-inv? [player id]
  (some true? (for [k (keys (:inv player))] (= k id))))

(defn get-loot-item-count [player map-stack loc-info id]
  ; gets count of specific item at current player location
  (get (get-loc-loot player map-stack loc-info) id))

(defn set-loot-item-count [player map-stack loc-info id value]
  ; sets count of specific item at current player location
  (assoc-in loc-info [(get-player-loc player map-stack) :loot id] value))

(defn add-to-loc [player map-stack loc-info id]
  (if-let [current-count (get-loot-item-count player map-stack loc-info id)]
    (set-loot-item-count player map-stack loc-info id (inc current-count))
    (set-loot-item-count player map-stack loc-info id 1)))

(defn player-add-hp-dmg [player id]
  (let [item (get-item id)]
  (assoc player :health (map + (:health player) (repeat 2 (:health item)))
                :damage (+ (:damage player) (:damage item)))))

(defn player-sub-hp-dmg [player id]
  (let [item (get-item id)]
  (assoc player :health (map - (:health player) (repeat 2 (:health item)))
                :damage (- (:damage player) (:damage item)))))

(defn unequip-item [player id]
  (let [slot (get-item-slot id)]
    (if (is-item-eq? player slot id)
      (set-eq-slot (player-sub-hp-dmg player id) slot id nil)
      player)))

(defn get-id [id]
 ; id might be a vector or an int
  (if (int? id)
    id
    (if (some nil? id)
      nil
      (second id))))

(defn get-eq-item-id [player slot]
  (let [maybe-id-vector (slot (:eq player))]
    (get-id maybe-id-vector)))

(defn equip-item [player new-id]
  (let [slot (get-item-slot new-id)
        old-id (get-eq-item-id player slot)]
    (if (is-item-eq? player slot old-id)
      ;unequip item if slot is taken
      (set-eq-slot (player-add-hp-dmg (unequip-item player old-id) new-id) slot nil new-id)
      (set-eq-slot (player-add-hp-dmg player new-id) slot nil new-id))))

(defn drop-item [player map-stack loc-info id]
  (let [slot (get-item-slot id)
        inv-item-count (get-inv-item-count player id)
        loc-with-item (add-to-loc player map-stack loc-info id)
        dec-inv-item-count (dec inv-item-count)]
    (if (and (is-item-eq? player slot id) 
             (= 1 inv-item-count))
      [(set-inv-item (unequip-item player id) id dec-inv-item-count)
       loc-with-item]
      [(set-inv-item player id dec-inv-item-count)
       loc-with-item])))
    
;LOOT

(defn is-item-at-loc [player map-stack loc-info id-num]
  ; returns true if item has been at loc before (current count might be 0)
  (some true? (for [k (keys (get-loc-loot player map-stack loc-info))] (= k id-num))))

(defn add-item [player map-stack loc-info id]
  ; returns [player + item, location - item]
  (let [new-loc (set-loot-item-count player map-stack loc-info id (dec (get-loot-item-count player map-stack loc-info id)))]
    (if-let [inv-count (get-inv-item-count player id)]
      [(set-inv-item player id (inc inv-count))
       new-loc]
      [(set-inv-item player id 1)
       new-loc])))

(defn add-to-equip-item [player map-stack loc-info id]
  ; returns [player + item (equipped), location - item]
  (let [[new-player new-loc] (add-item player map-stack loc-info id)]
    [(equip-item new-player id)
    new-loc]))

;FIGHT

(defn get-player-dmg-types [player]
  (for [eq-item (vals (:eq player))] (:d-type (get-item eq-item))))

(defn count-dmg-matches [player en-weak]
  ; returns number of equipped items that have a damage type the current enemy is weak to
   (reduce (fn [matches val] (if val (inc matches) matches)) 
          0
          (for [pl-dmg-type (get-player-dmg-types player) en en-weak] (= pl-dmg-type en))))

(defn get-player-extra-dmg [player en-weak]
  ; adds 5% dmg bonus to player for every item equipped that has a damage type the current enemy is weak to
  (let [extra-dmg-percent (/ (* (count-dmg-matches player en-weak) 5) 100)]
    (int (* (:damage player) 
       (+ extra-dmg-percent 1)))))

(defn get-new-player-health [player enemy hit-chance]
  (let [current-health (first (:health player))
        max-health (last (:health player))]
    (cond
      ; miss
      (= hit-chance 0) player
      ; critical hit
      (= hit-chance 9) (assoc player :health [(- current-health (int (* 1.25 (:damage enemy)))) 
                                              max-health])
      ; normal hit                                  
      :else (assoc player :health [(- current-health (:damage enemy)) 
                                   max-health]))))

(defn inv-has-weapons? [player]
  ; returns true if player inventory currently holds 1 or more items tagged with :hand slot
  (some true? (for [id (keys (:inv player))] (and (= :hand (get-item-slot id)) 
                                                  (> (get (:inv player) id) 0)))))

(defn get-new-enemy-health [enemy pl-dmg hit-chance]
  (cond
    ; miss
    (= hit-chance 0) enemy
    ; critical hit
    (= hit-chance 9) (assoc enemy :health (- (:health enemy) (int (* 1.25 pl-dmg))))
    ; normal hit
    :else (assoc enemy :health (- (:health enemy) pl-dmg))))

(defn get-enemy-info [player map-stack loc-info]
  (let [enemy (first (shuffle (:desc (first (shuffle (get-loc-enemy player map-stack loc-info))))))
        en-h (int (* 1.5 (:damage player)))
        en-dmg (int (* 0.3 (:damage player)))
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

(defn maybe-get-new-loc-desc [loc-info map-stack dir row col]
  ; first, check if desired direction of travel ("dir") is a valid cell in this map's vector
  (if dir
    ; if yes, return description of that location
    (get-in loc-info [(nth (nth (first map-stack) row) col) :desc])
    false))

(defn valid-directions [player map-stack loc-info]
  (let [player-col (:col player)
        player-row (:row player)
        dec-row (dec player-row) 
        inc-row (inc player-row)
        dec-col (dec player-col) 
        inc-col (inc player-col)
        n? (maybe-get-new-loc-desc loc-info map-stack (>= dec-row 0) dec-row player-col)
        s? (maybe-get-new-loc-desc loc-info map-stack (< inc-row (count (first map-stack))) inc-row player-col)
        e? (maybe-get-new-loc-desc loc-info map-stack (< inc-col (count (ffirst map-stack))) player-row inc-col)
        w? (maybe-get-new-loc-desc loc-info map-stack (>= dec-col 0) player-row dec-col)]
      [[n? "n" "North"]
       [s? "s" "South"]
       [e? "e" "East"]
       [w? "w" "West"]]))

(defn is-there-loot? [player map-stack loc-info]
  ; returns true if location has at least one item whose count is above 0
  (if (some true? 
            (for [v (vals (get-loc-loot player map-stack loc-info))]
                 (> v 0)))
    [true (:yes (get-loc-loot-desc player map-stack loc-info))]
    [false (:no (get-loc-loot-desc player map-stack loc-info))]))

; SET LOCATION LOOT

(defn put-loot-in-new-locs [player map-stack loc-info]
  (let [player-loc (get-player-loc player map-stack)
        get-loot-from (get-in loc-info [player-loc :get-loot-from])]
    ; get-loot-from is nil if loc already has loot
    (if get-loot-from
      (let [loot-locs (take 2 (shuffle (get-loot-from loot-types)))
            loot-ids (map last (map first loot-locs))
            loc-with-loot (assoc-in loc-info [player-loc :loot] (zipmap loot-ids (repeat 2 1)))]
        (assoc-in loc-with-loot [player-loc :get-loot-from] nil))
      loc-info)))