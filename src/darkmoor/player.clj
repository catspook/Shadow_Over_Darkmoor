(in-ns 'darkmoor.core)
(load "objects")

(def init-player
  {:health [8 10] ; [current health remaining, total health pool]
   :damage 8   ; amount of total damage done per attack 
   :eq {:head nil  ; name of item equipped in this slot
        :neck nil
        :shoulders nil
        :body starting-clothes
        :gloves nil
        :fingers nil
        :boots nil
        :hand [starting-sword nil]
        :hands nil
        :potion nil}
   ;FIXME put in monad
   :row (rand-int 4) ; coordinates of player's position
   :col (rand-int 3)
   :inv {1 1 ;key: object id, value: count of how many are in inventory
         2 1} 
   :hp 3 ; how many health potions does player have on them
   })
