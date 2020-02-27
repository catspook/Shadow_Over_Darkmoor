(in-ns 'darkmoor.core)
(load "enemies")

;LEVEL BUILDING___________________________________________________________

(def level-1-map
  [[:stables      :mansion :archive       :armory :stream]
   [:grave        :temple  :square        :market :docks]
   [:wizard-tower :ruins   :haunted-house :tavern :cages]
   [:alch-garden  :cave    :shrine        :field  :orchard]])

(def temple-cellar-map 
  [[:temple-cellar-0 :temple-cellar-1]
   [:temple-cellar-2 :temple-cellar-3]]) 

(def temple-map 
  [[:nil-cell :temple-0 :temple-1 :nil-cell]
   [:temple-2 :temple-3 :temple-4 :temple-5]
   [:nil-cell :temple-6 :temple-7 :nil-cell]
   [:nil-cell :temple-8 :temple-9 :nil-cell]])

(def crypt-map 
  [[:nil-cell   :crypt-0 :nil-cell  ]
   [:nil-cell   :crypt-1 :nil-cell  ]
   [:crypt-2 :crypt-3 :crypt-4]
   [:crypt-5 :crypt-6 :crypt-7]])

(def graveyard-map
  [[:grave-6 :grave-7 :grave-8]
   [:grave-3 :grave-4 :grave-5]
   [:grave-0 :grave-1 :grave-2]])

(def armory-map
  [[:armory-0 :armory-1 :armory-2]
   [:armory-3 :armory-4 :armory-5]
   [:armory-6 :armory-7 :armory-8]])

(def wizard-tower-map
  [[:nil-cell :wizard-tower-0 :wizard-tower-1]
   [:wizard-tower-2 :wizard-tower-3 :wizard-tower-4]
   [:wizard-tower-5 :wizard-tower-6 :wizard-tower-7]
   [:nil-cell :wizard-tower-8 :wizard-tower-9]])

(def haunted-house-map
  [[:haunted-house-0 :haunted-house-1 :haunted-house-2 :nil-cell]
   [:haunted-house-3 :haunted-house-4 :haunted-house-5 :haunted-house-6]])

(def archive-map
  [[:archive-0 :archive-1 :archive-2]
   [:archive-3 :archive-4 :archive-5]
   [:archive-6 :archive-7 :archive-8]])

(def cave-map
  [[:nil-cell  :nil-cell  :nil-cell  :cave-9 :cave-10 :cave-11 :cave-12 :cave-13]
   [:nil-cell  :nil-cell  :cave-6 :cave-7 :cave-8  :nil-cell   :cave-14 :cave-15]
   [:nil-cell  :cave-2 :cave-3 :cave-4 :cave-5  :nil-cell   :nil-cell   :nil-cell ]
   [:cave-0 :cave-1 :nil-cell  :nil-cell  :nil-cell   :nil-cell   :nil-cell   :nil-cell]])

(def tavern-map
  [[:tavern-0 :tavern-1 :tavern-2]
   [:tavern-3 :tavern-4 :tavern-5]
   [:tavern-6 :tavern-7 :tavern-8]
   [:tavern-9 :tavern-10 :tavern-11]])

(def init-map-stack
  (list level-1-map))

(def init-loc-info
  {:nil-cell {} ;nil location (can't be loaded)

   ;temple basement (from temple)
   :temple-cellar-0 {:desc "temple basement 0-enter temple" ;cell description
                     :loot {} ;list of items at this cell (initially empty)
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :temple-loot ;pull items from this master list of items
                     :enemy [cultist]  ;list of possible enemies at this cell
                     :exit {:row 1 :col 0 :old-map-name "THE ABBEY OF THE DAWN "}} ;optional -- if player can go back a map at this cell, list what coords they should go back to
                  ;  :enter                               optional -- if player can go to a new map at this cell, list new map name and what coords they should start at
   :temple-cellar-1 {:desc "temple basement 1"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :temple-loot
                     :enemy [cultist]}
   :temple-cellar-2 {:desc "temple basement 2"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :temple-loot
                     :enemy [cultist]}
   :temple-cellar-3 {:desc "temple basement 3"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :temple-loot
                     :enemy [cultist]}

   ;temple
   :temple-0 {:desc "temple 0-exit" 
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :enemy [cultist ghost]
              :exit {:row 1 :col 2 :old-map-name "DARKMOOR VILLAGE"}}
   :temple-1 {:desc "temple 1"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :enemy [cultist ghost]}
   :temple-2 {:desc "temple 2-enter temple basement"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :enemy [cultist ghost]
              :enter {:goto temple-cellar-map
                      :new-map-name "THE ABBEY MAUSOLEUM"
                      :start-coords {:row 0 :col 0}}}
   :temple-3 {:desc "temple 3"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :enemy [cultist ghost]}
   :temple-4 {:desc "temple 4"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :enemy [cultist ghost]}
   :temple-5 {:desc "temple 5"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :enemy [cultist ghost]}
   :temple-6 {:desc "temple 6"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :enemy [cultist ghost]}
   :temple-7 {:desc "temple 7"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :enemy [cultist ghost]}
   :temple-8 {:desc "temple 8"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :enemy [cultist ghost]}
   :temple-9 {:desc "temple 9"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :enemy [townsfolk ghost]}
  
   ;crypt (from graveyard)
   :crypt-0 {:desc "crypt 0-exit" 
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele ghost]
             :exit {:row 0 :col 1 :old-map-name "DARKMOOR'S GRAVEYARD"}}
   :crypt-1 {:desc "crypt 1"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele ghost]}
   :crypt-2 {:desc "crypt 2"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele ghost]}
   :crypt-3 {:desc "crypt 3"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele ghost]}
   :crypt-4 {:desc "crypt 4"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele ghost]}
   :crypt-5 {:desc "crypt 5"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele ghost]}
   :crypt-6 {:desc "crypt 6"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele ghost]}
   :crypt-7 {:desc "crypt 7"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele ghost]}

   ;graveyard
   :grave-0 {:desc "graveyard 0"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele wizard werewolf]}
   :grave-1 {:desc "graveyard 1-exit"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele cultist werewolf]
             :exit {:row 1 :col 1 :old-map-name "DARKMOOR VILLAGE"}}
   :grave-2 {:desc "graveyard 2"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele wizard werewolf]}
   :grave-3 {:desc "graveyard 3"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele cultist werewolf]}
   :grave-4 {:desc "graveyard 4"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele wizard werewolf]}
   :grave-5 {:desc "graveyard 5"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele cultist werewolf]}
   :grave-6 {:desc "graveyard 6"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele wizard werewolf]}
   :grave-7 {:desc "graveyard 7-enter crypt"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele cultist werewolf]
             :enter {:goto crypt-map 
                     :new-map-name "THE CRYPT OF LADY IOETTA"
                     :start-coords {:row 0 :col 1}}}
   :grave-8 {:desc "graveyard 8"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele wizard werewolf]}

   ;armory   
   :armory-0 {:desc "armory 0"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :armory-loot
                  :enemy [cultist townsfolk]}
   :armory-1 {:desc "armory 1"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :armory-loot
                  :enemy [townsfolk]}
   :armory-2 {:desc "armory 2"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :armory-loot
                  :enemy [cultist townsfolk]}
   :armory-3 {:desc "armory 3"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :armory-loot
                  :enemy [townsfolk]}
   :armory-4 {:desc "armory 4"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :armory-loot
                  :enemy [cultist townsfolk]}
   :armory-5 {:desc "armory 5"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :armory-loot
                  :enemy [townsfolk]}
   :armory-6 {:desc "armory 6"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :armory-loot
                  :enemy [cultist townsfolk]}
   :armory-7 {:desc "armory 7-exit" 
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :armory-loot
                  :enemy [townsfolk]
                  :exit {:row 0 :col 4 :old-map-name "DARKMOOR VILLAGE"}}
   :armory-8 {:desc "armory 8"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :armory-loot
                  :enemy [cultist townsfolk]}
   
   ;wizard tower
   :wizard-tower-0 {:desc "wizard-tower 0"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :enemy [wizard]}
   :wizard-tower-1 {:desc "wizard-tower 1"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :enemy [wizard]}
   :wizard-tower-2 {:desc "wizard-tower 2"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :enemy [wizard]}
   :wizard-tower-3 {:desc "wizard-tower 3"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :enemy [wizard]}
   :wizard-tower-4 {:desc "wizard-tower 4"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :enemy [wizard]}
   :wizard-tower-5 {:desc "wizard-tower 5-exit"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :enemy [wizard]
                    :exit {:row 2 :col 2 :old-map-name "DARKMOOR VILLAGE"}}
   :wizard-tower-6 {:desc "wizard-tower 6"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :enemy [wizard]}
   :wizard-tower-7 {:desc "wizard-tower 7"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :enemy [wizard]}
   :wizard-tower-8 {:desc "wizard-tower 8"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :enemy [wizard]}
   :wizard-tower-9 {:desc "wizard-tower 9"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :enemy [wizard]}
   
   ;haunted-house
   :haunted-house-0 {:desc "haunted-house 0"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :haunted-house-loot
             :enemy [ghost]}
   :haunted-house-1 {:desc "haunted-house 1"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :haunted-house-loot
             :enemy [townsfolk]}
   :haunted-house-2 {:desc "haunted-house 2"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :haunted-house-loot
             :enemy [ghost]}
   :haunted-house-3 {:desc "haunted-house 3"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :haunted-house-loot
             :enemy [townsfolk]}
   :haunted-house-4 {:desc "haunted-house 4"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :haunted-house-loot
             :enemy [ghost]}
   :haunted-house-5 {:desc "haunted-house 5"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :haunted-house-loot
             :enemy [townsfolk]}
   :haunted-house-6 {:desc "haunted-house 6-exit"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :haunted-house-loot
             :enemy [ghost]
             :exit {:row 3 :col 2 :old-map-name "DARKMOOR VILLAGE"}}

   ;archive
   :archive-0 {:desc "archive 0"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :archive-loot
             :enemy [werewolf ghost]}
   :archive-1 {:desc "archive 1"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :archive-loot
             :enemy [werewolf ghost]}
   :archive-2 {:desc "archive 2"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :archive-loot
             :enemy [werewolf ghost]}
   :archive-3 {:desc "archive 3-exit" 
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :archive-loot
             :enemy [werewolf ghost]
             :exit {:row 3 :col 4 :old-map-name "DARKMOOR VILLAGE"}}
   :archive-4 {:desc "archive 4"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :archive-loot
             :enemy [werewolf ghost]}
   :archive-5 {:desc "archive 5"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :archive-loot
             :enemy [werewolf ghost]}
   :archive-6 {:desc "archive 6"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :archive-loot
             :enemy [werewolf ghost]}
   :archive-7 {:desc "archive 7"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :archive-loot
             :enemy [werewolf ghost]}
   :archive-8 {:desc "archive 8"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :archive-loot
             :enemy [werewolf ghost]}
   
   ;cave
   :cave-0 {:desc "cave 0-exit" 
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]
            :exit {:row 3 :col 0 :old-map-name "DARKMOOR VILLAGE"}}
   :cave-1 {:desc "cave 1"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-2 {:desc "cave 2"
            :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-3 {:desc "cave 3"
            :loot {}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-4 {:desc "cave 4"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-5 {:desc "cave 5"
            :loot {}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-6 {:desc "cave 6"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-7 {:desc "cave 7"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-8 {:desc "cave 8"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-9 {:desc "cave 9"
            :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-10 {:desc "cave 10"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-11 {:desc "cave 11"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-12 {:desc "cave 12"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-13 {:desc "cave 13"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-14 {:desc "cave 14"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   :cave-15 {:desc "cave 15"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :enemy [werewolf skele ghost cultist]}
   
   ;tavern
   :tavern-0 {:desc "tavern 0"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :enemy [skele townsfolk]}
   :tavern-1 {:desc "tavern 1"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :enemy [skele townsfolk]}
   :tavern-2 {:desc "tavern 2"
              :loot {}
              :get-loot-from :tavern-loot
              :enemy [wizard cultist]}
   :tavern-3 {:desc "tavern 3"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :enemy [skele townsfolk]}
   :tavern-4 {:desc "tavern 4"
              :loot {}
              :get-loot-from :tavern-loot
              :enemy [skele townsfolk]}
   :tavern-5 {:desc "tavern 5"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :enemy [cultist wizard]}
   :tavern-6 {:desc "tavern 6"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :enemy [skele townsfolk]}
   :tavern-7 {:desc "tavern 7"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :enemy [skele townsfolk]}
   :tavern-8 {:desc "tavern 8"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :enemy [cultist wizard]}
   :tavern-9 {:desc "tavern 9"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :enemy [skele townsfolk]}
   :tavern-10 {:desc "tavern 10-exit"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :tavern-loot
               :enemy [skele townsfolk] 
               :exit {:row 1 :col 4 :old-map-name "DARKMOOR VILLAGE"}}
   :tavern-11 {:desc "tavern 11"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :tavern-loot
               :enemy [cultist wizard]}
   
   ;main map
   :stables    {:desc "The Decrepit Stables"
               :loot {}
               :loot-desc {:yes "Yes loot" 
                           :no "no loot"}
               :get-loot-from :gen-loot
               :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :temple  {:desc "The Derelict Temple of the Dawn" 
             :loot {}
             :loot-desc {:yes "It appears the cultists dropped some relics during thier looting." 
                         :no "You sift through a pile of crumbled facade, but find nothing but stone."}
             :get-loot-from :temple-loot
             :enemy [cultist]
             :enter {:goto temple-map
                     :new-map-name "THE ABBEY OF THE DAWN"
                     :start-coords {:row 0 :col 1}}}
   :grave   {:desc "The Graveyard of Dawn's Ending" 
             :loot {}
             :loot-desc {:yes "A trail of skeletal footprints and grave-goods lead out of the graveyard." 
                         :no "You notice some fresh skeletal footprints, but see no more dropped items."}
             :get-loot-from :graveyard-loot
             :enemy [skele]
             :enter {:goto graveyard-map
                     :new-map-name "DARKMOOR'S GRAVEYARD"
                     :start-coords {:row 2 :col 1}}}
   :armory   {:desc "The Darkmoor Armory" 
                  :loot {}
                  :loot-desc {:yes "Weapons and armor lie scattered about the splintered Armory door." 
                              :no "You search the entrance again, but only find a town guard's cloak."}
                  :get-loot-from :armory-loot
                  :enemy [townsfolk]
                  :enter {:goto armory-map
                          :new-map-name "DARKMOOR'S GUARDHOUSE"
                          :start-coords {:row 2 :col 1}}}
   :wizard-tower {:desc "The Tower of Archmage Cerberus" 
                  :loot {}
                  :loot-desc {:yes "Gargoyles above the door glare as you steal their Master's property." 
                              :no "The gargoyles are gone, along with the remaining trinkets."}
                  :get-loot-from :wizard-tower-loot
                  :enemy [wizard]
                  :enter {:goto wizard-tower-map 
                          :new-map-name "THE ARCHMAGE'S TOWER"
                          :start-coords {:row 2 :col 0}}} 
   :haunted-house   {:desc "A Hut Wreathed in Shadows" 
                     :loot {}
                     :loot-desc {:yes "A few trinkets untangle themselves from a tower of furnature and sail towards you." 
                                 :no "The impossible furnature tower offers you no more gifts."}
                     :get-loot-from :haunted-house-loot
                     :enemy [werewolf townsfolk]
                     :enter {:goto haunted-house-map
                             :new-map-name "A HAUNTED HOUSE"
                             :start-coords {:row 1 :col 3}}}
   :ruins   {:desc "The Burnt-Out Shell of a Cottage"
             :loot {}
             :loot-desc {:yes "There are still unburnt goods within the wreckage." 
                         :no "A piece of leather armor crumbles as you pick it up."}
             :get-loot-from :gen-loot
             :enemy [werewolf ghost]}
   :square    {:desc "Darkmoor's Town Square"
               :loot {}
               :loot-desc {:yes "An abandoned wagon still holds some market goods."
                           :no "The market square lies empty and deserted."}
               :get-loot-from :gen-loot
               :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :docks    {:desc "The Rickety Darkmoor Docks"
              :loot {}
              :loot-desc {:yes "Some gear, encrusted with fish guts, lies on a half-rotted board." 
                          :no "You see old fishing rods and moldy rope."}
              :get-loot-from :gen-loot
              :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :cages   {:desc "A Pile of Chains and Cages"
             :loot {}
             :loot-desc {:yes "There's some abandoned gear inside a human-sized cage." 
                         :no "The cages lie empty, except for manacles and scattered bones."}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :alch-garden    {:desc "A Tiny Alchemical Garden"
                    :loot {}
                    :loot-desc {:yes "There are a few offerings inside a circle of red toadstools." 
                                :no "The circle of toadstools lies empty."}
                    :get-loot-from :gen-loot
                    :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :market   {:desc "The Abandoned Market Stalls"
              :loot {}
              :loot-desc {:yes "Rain-soaked goods still lie inside the faded stalls." 
                          :no "The rain has wrecked all the remaining goods you can find."}
              :get-loot-from :gen-loot
              :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :mansion   {:desc "The Magistrate's Looted Mansion"
               :loot {}
               :loot-desc {:yes "You pull some items from the pile of rubble blocking the door."
                           :no "You find a severed arm inside the rubble pile, and step away."}
               :get-loot-from :gen-loot
               :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :cave    {:desc "A Yawning Cave Mouth"
             :loot {}
             :loot-desc {:yes "The body of a warrior lies at the cave mouth, their gear intact." 
                         :no "You've already stripped the warrior's corpse of any useful gear."}
             :get-loot-from :cave-loot
             :enemy [cultist skele werewolf ghost]
             :enter {:goto cave-map
                     :new-map-name "A DARK CAVE"
                     :start-coords {:row 3 :col 0}}}
   :shrine  {:desc "A small, blackened shrine"
             :loot {}
             :loot-desc {:yes "A malevolent presence bears down upon you as you take the offerings off of the shrine." 
                         :no "Only offerings of congealed blood and carved bone remain."}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :archive {:desc "The Mouldering Darkmoor Archive"
             :loot {}
             :loot-desc {:yes "You find something interesting hidden behind a thorny rosebush." 
                         :no "There is nothing here but torn pages drifting outside the Archive's entrance."}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]
             :enter {:goto archive-map
                     :new-map-name "THE DARKMOOR ARCHIVE"
                     :start-coords {:row 1 :col 0}}}
   :stream  {:desc "The Stony Bank of a Murmuring Brook"
             :loot {}
             :loot-desc {:yes "You see something glinting from the silty streambed." 
                         :no "The brook only holds sickly minnows and bits of gnawed bone."}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :orchard  {:desc "An Overgrown Orchard"
             :loot {}
             :loot-desc {:yes "You feel something inside the knot of an old, gnarled tree." 
                         :no "You find only rotten apples under the gnardled trees."}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :tavern  {:desc "The Run-Down 'Hungry Hag Tavern'" 
             :loot {}
             :loot-desc {:yes "You see an unopened barrel hidden under the tavern's fallen sign."
                         :no "You have already picked the last un-smashed barrel clean."}
             :get-loot-from :tavern-loot
             :enemy [townsfolk skele]
             :enter {:goto tavern-map
                     :new-map-name "THE HUNGRY HAG TAVERN"
                     :start-coords {:row 3 :col 1}}}
   :field   {:desc "A Field of Blighted Grain"
             :loot {} 
             :loot-desc {:yes "You tear open the scarecrow and find a stash of loot hidden inside." 
                         :no "The torn scarecrow holds only brittle stalks of dry wheat."}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}})
