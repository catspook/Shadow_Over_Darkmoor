(in-ns 'darkmoor.core)

(load "model/enemy-data")
(load "model/location/level-1-locations/temple-cellar")
(load "model/location/level-1-locations/temple")
(load "model/location/level-1-locations/crypt")
(load "model/location/level-1-locations/graveyard")
(load "model/location/level-1-locations/armory")
(load "model/location/level-1-locations/wizard-tower")
(load "model/location/level-1-locations/haunted-house")
(load "model/location/level-1-locations/archive")
(load "model/location/level-1-locations/cave")
(load "model/location/level-1-locations/tavern")

(def level-1-map
  [[:stables      :mansion :archive       :armory :stream]
   [:grave        :temple  :square        :market :docks]
   [:wizard-tower :ruins   :haunted-house :tavern :cages]
   [:alch-garden  :cave    :shrine        :field  :orchard]])

(def init-level-1
  {:stables {:desc "The Decrepit Stables"
             :loot {}
             :loot-desc {:yes "You find some hunting supplies among the piles of manure and tack." 
                         :no "You'd rather not search the manure piles for loot."}
             :get-loot-from :gen-loot
             :map "resources/map-darkmoor.txt"
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :temple {:desc "The Derelict Temple of the Dawn" 
            :loot {}
            :loot-desc {:yes "It appears the cultists dropped some relics during thier looting." 
                        :no "You sift through a pile of crumbled facade, but find nothing but stone."}
            :get-loot-from :temple-loot
            :map "resources/map-darkmoor.txt"
            :enemy [cultist]
            :enter {:goto temple-map
                    :new-map-name "THE ABBEY OF THE DAWN"
                    :start-coords {:row 0 :col 1}}}
   :grave {:desc "The Graveyard of Dawn's Ending" 
           :loot {}
           :loot-desc {:yes "A trail of skeletal footprints and grave-goods lead out of the graveyard." 
                       :no "You notice some fresh skeletal footprints, but see no more dropped items."}
           :get-loot-from :graveyard-loot
           :map "resources/map-darkmoor.txt"
           :enemy [skele]
           :enter {:goto graveyard-map
                   :new-map-name "DARKMOOR'S GRAVEYARD"
                   :start-coords {:row 2 :col 1}}}
   :armory {:desc "The Darkmoor Armory" 
            :loot {}
            :loot-desc {:yes "Weapons and armor lie scattered about the splintered Armory door." 
                        :no "You search the entrance again, but only find a town guard's cloak."}
            :get-loot-from :armory-loot
            :map "resources/map-darkmoor.txt"
            :enemy [townsfolk]
            :enter {:goto armory-map
                    :new-map-name "DARKMOOR'S GUARDHOUSE"
                    :start-coords {:row 2 :col 1}}}
   :wizard-tower {:desc "The Tower of Archmage Cerberus" 
                  :loot {}
                  :loot-desc {:yes "Gargoyles above the door glare as you steal their Master's property." 
                              :no "The gargoyles are gone, along with the remaining trinkets."}
                  :get-loot-from :wizard-tower-loot
                  :map "resources/map-darkmoor.txt"
                  :enemy [wizard]
                  :enter {:goto wizard-tower-map 
                          :new-map-name "THE ARCHMAGE'S TOWER"
                          :start-coords {:row 2 :col 0}}} 
   :haunted-house {:desc "A Hut Wreathed in Shadows" 
                   :loot {}
                   :loot-desc {:yes "A few trinkets untangle themselves from a tower of furnature and sail towards you." 
                               :no "The impossible furnature tower offers you no more gifts."}
                   :get-loot-from :haunted-house-loot
                   :map "resources/map-darkmoor.txt"
                   :enemy [werewolf townsfolk]
                   :enter {:goto haunted-house-map
                           :new-map-name "A HAUNTED HOUSE"
                           :start-coords {:row 1 :col 3}}}
   :ruins {:desc "The Burnt-Out Shell of a Cottage"
           :loot {}
           :loot-desc {:yes "There are still unburnt goods within the wreckage." 
                       :no "A piece of leather armor crumbles as you pick it up."}
           :get-loot-from :gen-loot
           :map "resources/map-darkmoor.txt"
           :enemy [werewolf ghost]}
   :square {:desc "Darkmoor's Town Square"
            :loot {}
            :loot-desc {:yes "An abandoned wagon still holds some market goods."
                        :no "The market square lies empty and deserted."}
            :get-loot-from :gen-loot
            :map "resources/map-darkmoor.txt"
            :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :docks {:desc "The Rickety Darkmoor Docks"
           :loot {}
           :loot-desc {:yes "Some gear, encrusted with fish guts, lies on a half-rotted board." 
                       :no "You see old fishing rods and moldy rope."}
           :get-loot-from :gen-loot
           :map "resources/map-darkmoor.txt"
           :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :cages {:desc "A Pile of Chains and Cages"
           :loot {}
           :loot-desc {:yes "There's some abandoned gear inside a human-sized cage." 
                       :no "The cages lie empty, except for manacles and scattered bones."}
           :get-loot-from :gen-loot
           :map "resources/map-darkmoor.txt"
           :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :alch-garden {:desc "A Tiny Alchemical Garden"
                 :loot {}
                 :loot-desc {:yes "There are a few offerings inside a circle of red toadstools." 
                             :no "The circle of toadstools lies empty."}
                 :get-loot-from :gen-loot
                 :map "resources/map-darkmoor.txt"
                 :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :market {:desc "The Abandoned Market Stalls"
            :loot {}
            :loot-desc {:yes "Rain-soaked goods still lie inside the faded stalls." 
                        :no "The rain has wrecked all the remaining goods you can find."}
            :get-loot-from :gen-loot
            :map "resources/map-darkmoor.txt"
            :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :mansion {:desc "The Magistrate's Looted Mansion"
             :loot {}
             :loot-desc {:yes "You pull some items from the pile of rubble blocking the door."
                         :no "You find a severed arm inside the rubble pile, and step away."}
             :get-loot-from :gen-loot
             :map "resources/map-darkmoor.txt"
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :cave {:desc "A Yawning Cave Mouth"
          :loot {}
          :loot-desc {:yes "The body of a warrior lies at the cave mouth, their gear intact." 
                      :no "You've already stripped the warrior's corpse of any useful gear."}
          :get-loot-from :cave-loot
          :map "resources/map-darkmoor.txt"
          :enemy [cultist skele werewolf ghost]
          :enter {:goto cave-map
                  :new-map-name "A DARK CAVE"
                  :start-coords {:row 3 :col 0}}}
   :shrine {:desc "A small, blackened shrine"
            :loot {}
            :loot-desc {:yes "A malevolent presence bears down upon you as you take the offerings off of the shrine." 
                        :no "Only offerings of congealed blood and carved bone remain."}
            :get-loot-from :gen-loot
            :map "resources/map-darkmoor.txt"
            :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :archive {:desc "The Mouldering Darkmoor Archive"
            :loot {}
            :loot-desc {:yes "You find something interesting hidden behind a thorny rosebush." 
                        :no "There is nothing here but torn pages drifting outside the Archive's entrance."}
            :get-loot-from :gen-loot
            :map "resources/map-darkmoor.txt"
            :enemy [townsfolk cultist wizard werewolf ghost skele]
            :enter {:goto archive-map
                    :new-map-name "THE DARKMOOR ARCHIVE"
                    :start-coords {:row 1 :col 0}}}
   :stream {:desc "The Stony Bank of a Murmuring Brook"
            :loot {}
            :loot-desc {:yes "You see something glinting from the silty streambed." 
                        :no "The brook only holds sickly minnows and bits of gnawed bone."}
            :get-loot-from :gen-loot
            :map "resources/map-darkmoor.txt"
            :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :orchard {:desc "An Overgrown Orchard"
             :loot {}
             :loot-desc {:yes "You feel something inside the knot of an old, gnarled tree." 
                         :no "You find only rotten apples under the gnardled trees."}
             :get-loot-from :gen-loot
             :map "resources/map-darkmoor.txt"
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :tavern {:desc "The Run-Down 'Hungry Hag Tavern'" 
            :loot {}
            :loot-desc {:yes "You see an unopened barrel hidden under the tavern's fallen sign."
                        :no "You have already picked the last un-smashed barrel clean."}
            :get-loot-from :tavern-loot
            :map "resources/map-darkmoor.txt"
            :enemy [townsfolk skele]
            :enter {:goto tavern-map
                    :new-map-name "THE HUNGRY HAG TAVERN"
                    :start-coords {:row 3 :col 1}}}
   :field {:desc "A Field of Blighted Grain"
           :loot {} 
           :loot-desc {:yes "You tear open the scarecrow and find a stash of loot hidden inside." 
                       :no "The torn scarecrow holds only brittle stalks of dry wheat."}
           :get-loot-from :gen-loot
           :map "resources/map-darkmoor.txt"
           :enemy [townsfolk cultist wizard werewolf ghost skele]}})
