(in-ns 'darkmoor.core)

(load "model/objects-data")

(def init-player
  {:health [10 10] ; [current health remaining, total health pool]
   :damage 8   ; amount of total damage done per attack 
   :eq nil
   :row (rand-int 4) ; coordinates of player's position
   :col (rand-int 3)
   :moved? true
   :inv nil
   :hp 3 ; how many health potions does player have on them
   :class nil ; what class is the player
   })

(def paladin
  {:name "Paladin"
   :dmg-bonus "RADIANT"
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
   :dmg-bonus "ARCANE"
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
   :dmg-bonus "PIERCING"
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
   :dmg-bonus "NECROTIC"
   :init-eq {:head nil
        :neck nil
        :shoulders nil
        :body 38
        :gloves nil
        :fingers nil
        :boots nil
        :r-hand 28
        :l-hand nil
        :potion nil}
   :init-inv {38 1
              28 1}
   :ability nil})

(def brawler 
  {:name "Brawler"
   :dmg-bonus "BLUDGEONING"
   :init-eq {:head nil
        :neck nil
        :shoulders nil
        :body 39
        :gloves nil
        :fingers nil
        :boots nil
        :r-hand 41
        :l-hand nil
        :potion 11}
   :init-inv {39 1
              41 1
              11 1}
   :ability nil})
