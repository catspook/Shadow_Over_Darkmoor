(in-ns 'darkmoor.core)

(load "model/objects-data")

(def init-player
  {:health nil
   :damage nil
   :eq nil
   :row (rand-int 4) ; coordinates of player's position
   :col (rand-int 3)
   :moved? true
   :inv nil
   :hp 3 ; how many health potions does player have on them
   :class nil
   :ability nil
   })

(def paladin
  {:name "Paladin"
   :init-health [10 10] ; [current health, total health]
   :init-damage 8
   :init-eq {:head nil  ; id of item equipped in this slot
        :neck nil
        :shoulders nil
        :body 40
        :gloves nil
        :fingers nil
        :boots nil
        :r-hand 2
        :l-hand 10
        :potion nil}
   :init-inv {40 1 ; key: object id, value: how many in inventory
              2 1
              10 1}
   :ability nil})

(def sorcerer 
  {:name "Sorcerer"
   :init-health [8 8]
   :init-damage 10
   :init-eq {:head nil
        :neck nil
        :shoulders nil
        :body 38
        :gloves nil
        :fingers nil
        :boots nil
        :r-hand 37
        :l-hand nil
        :potion nil}
   :init-inv {38 1
              37 1}
   :ability nil})

(def hunter 
  {:name "Hunter"
   :init-health [8 8]
   :init-damage 8
   :init-eq {:head nil
        :neck nil
        :shoulders nil
        :body 39
        :gloves nil
        :fingers nil
        :boots nil
        :r-hand 6
        :l-hand nil
        :potion nil}
   :init-inv {39 1
              6 1}
   :ability nil})

(def necromancer 
  {:name "Necromancer"
   :init-health [8 8]
   :init-damage 8
   :init-eq {:head nil
        :neck nil
        :shoulders nil
        :body 38
        :gloves nil
        :fingers nil
        :boots nil
        :r-hand 34
        :l-hand nil
        :potion nil}
   :init-inv {38 1
              34 1}
   :ability nil})
