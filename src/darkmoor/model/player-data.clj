(in-ns 'darkmoor.core)

(load "model/objects-data")

(def init-player
  {:health [10 10] ; [current health remaining, total health pool]
   :damage 8   ; amount of total damage done per attack 
   :eq {:head nil  ; id of item equipped in this slot
        :neck nil
        :shoulders nil
        :body 1
        :gloves nil
        :fingers nil
        :boots nil
        :r-hand 2
        :l-hand nil
        :potion nil}
   :row (rand-int 4) ; coordinates of player's position
   :col (rand-int 3)
   :moved? true
   :inv {1 1 ;key: object id, value: count of how many are in inventory
         2 1} 
   :hp 3 ; how many health potions does player have on them
   })
