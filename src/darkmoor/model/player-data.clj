(in-ns 'darkmoor.core)

(load "model/objects-data")

(def init-player
  {:health [10 10] ; [current, total]
   :damage 8
   :eq {:head nil ; slot : id of item
        :neck nil
        :shoulders nil
        :body 1
        :gloves nil
        :fingers nil
        :boots nil
        :hand [2 nil]
        :potion nil} 
   :row (rand-int 4) ; player's positon
   :col (rand-int 3)
   :moved? true
   :inv {1 1 ; item id : count
         2 1} 
   :hp 3 ; count of health potions
   })
