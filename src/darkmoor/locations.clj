(in-ns 'darkmoor.core)
(load "enemies")

;LEVEL BUILDING___________________________________________________________

(def level-1-map
  [[:camp    :docks  :cart         :stall  :guardhouse]
   [:shrine  :grave  :temple       :forge  :tavern]
   [:bodies  :field  :wizard-tower :park   :arena]
   [:cave    :grain  :house        :well   :ruins]])

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

(def guardhouse-map
  [[:guardhouse-0 :guardhouse-1 :guardhouse-2]
   [:guardhouse-3 :guardhouse-4 :guardhouse-5]
   [:guardhouse-6 :guardhouse-7 :guardhouse-8]])

(def wizard-tower-map
  [[:nil-cell :wizard-tower-0 :wizard-tower-1]
   [:wizard-tower-2 :wizard-tower-3 :wizard-tower-4]
   [:wizard-tower-5 :wizard-tower-6 :wizard-tower-7]
   [:nil-cell :wizard-tower-8 :wizard-tower-9]])

(def house-map
  [[:house-0 :house-1 :house-2 :nil-cell]
   [:house-3 :house-4 :house-5 :house-6]])

(def ruins-map
  [[:ruins-0 :ruins-1 :ruins-2]
   [:ruins-3 :ruins-4 :ruins-5]
   [:ruins-6 :ruins-7 :ruins-8]])

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
             :exit {:row 0 :col 1 :old-map-name "DARKMOOR VILLAGE"}}
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

   ;guardhouse   
   :guardhouse-0 {:desc "guardhouse 0"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :guardhouse-loot
                  :enemy [cultist townsfolk]}
   :guardhouse-1 {:desc "guardhouse 1"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :guardhouse-loot
                  :enemy [townsfolk]}
   :guardhouse-2 {:desc "guardhouse 2"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :guardhouse-loot
                  :enemy [cultist townsfolk]}
   :guardhouse-3 {:desc "guardhouse 3"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :guardhouse-loot
                  :enemy [townsfolk]}
   :guardhouse-4 {:desc "guardhouse 4"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :guardhouse-loot
                  :enemy [cultist townsfolk]}
   :guardhouse-5 {:desc "guardhouse 5"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :guardhouse-loot
                  :enemy [townsfolk]}
   :guardhouse-6 {:desc "guardhouse 6"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :guardhouse-loot
                  :enemy [cultist townsfolk]}
   :guardhouse-7 {:desc "guardhouse 7-exit" 
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :guardhouse-loot
                  :enemy [townsfolk]
                  :exit {:row 0 :col 4 :old-map-name "DARKMOOR VILLAGE"}}
   :guardhouse-8 {:desc "guardhouse 8"
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :guardhouse-loot
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
   
   ;house
   :house-0 {:desc "house 0"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :house-loot
             :enemy [ghost]}
   :house-1 {:desc "house 1"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :house-loot
             :enemy [townsfolk]}
   :house-2 {:desc "house 2"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :house-loot
             :enemy [ghost]}
   :house-3 {:desc "house 3"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :house-loot
             :enemy [townsfolk]}
   :house-4 {:desc "house 4"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :house-loot
             :enemy [ghost]}
   :house-5 {:desc "house 5"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :house-loot
             :enemy [townsfolk]}
   :house-6 {:desc "house 6-exit"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :house-loot
             :enemy [ghost]
             :exit {:row 3 :col 2 :old-map-name "DARKMOOR VILLAGE"}}

   ;ruins
   :ruins-0 {:desc "ruins 0"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :ruins-loot
             :enemy [werewolf ghost]}
   :ruins-1 {:desc "ruins 1"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :ruins-loot
             :enemy [werewolf ghost]}
   :ruins-2 {:desc "ruins 2"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :ruins-loot
             :enemy [werewolf ghost]}
   :ruins-3 {:desc "ruins 3-exit" 
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :ruins-loot
             :enemy [werewolf ghost]
             :exit {:row 3 :col 4 :old-map-name "DARKMOOR VILLAGE"}}
   :ruins-4 {:desc "ruins 4"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :ruins-loot
             :enemy [werewolf ghost]}
   :ruins-5 {:desc "ruins 5"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :ruins-loot
             :enemy [werewolf ghost]}
   :ruins-6 {:desc "ruins 6"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :ruins-loot
             :enemy [werewolf ghost]}
   :ruins-7 {:desc "ruins 7"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :ruins-loot
             :enemy [werewolf ghost]}
   :ruins-8 {:desc "ruins 8"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :ruins-loot
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
   :cart    {:desc "cart"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :temple  {:desc "temple" 
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :temple-loot
             :enemy [cultist]
             :enter {:goto temple-map
                     :new-map-name "THE ABBEY OF THE DAWN"
                     :start-coords {:row 0 :col 1}}}
   :grave   {:desc "grave" 
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :enemy [skele]
             :enter {:goto graveyard-map
                     :new-map-name "DARKMOOR'S GRAVEYARD"
                     :start-coords {:row 2 :col 1}}}
   :guardhouse   {:desc "guardhouse" 
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :guardhouse-loot
                  :enemy [townsfolk]
                  :enter {:goto guardhouse-map
                          :new-map-name "DARKMOOR'S GUARDHOUSE"
                          :start-coords {:row 2 :col 1}}}
   :wizard-tower {:desc "wizard tower" 
                  :loot {}
                  :loot-desc {:yes "Yes loot" :no "no loot"}
                  :get-loot-from :wizard-tower-loot
                  :enemy [wizard]
                  :enter {:goto wizard-tower-map 
                          :new-map-name "CERBERUS COLLEGE OF MAGIC"
                          :start-coords {:row 2 :col 0}}} 
   :house   {:desc "house" 
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :house-loot
             :enemy [werewolf townsfolk]
             :enter {:goto house-map
                     :new-map-name "A MOULDERING COTTAGE"
                     :start-coords {:row 1 :col 3}}}
   :ruins   {:desc "ruins"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :ruins-loot
             :enemy [werewolf ghost]
             :enter {:goto ruins-map
                     :new-map-name "THE SMOULDERING RUINS"
                     :start-coords {:row 1 :col 0}}}
   :well    {:desc "well"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :docks    {:desc "docks"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :forge   {:desc "forge"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :park    {:desc "park"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :stall   {:desc "stall"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :arena   {:desc "arena"
             :loot {}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :cave    {:desc "cave"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :cave-loot
             :enemy [cultist skele werewolf ghost]
             :enter {:goto cave-map
                     :new-map-name "A DARK CAVE"
                     :start-coords {:row 3 :col 0}}}
   :shrine  {:desc "shrine"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :camp    {:desc "camp"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :bodies  {:desc "bodies"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :grain   {:desc "grain"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}
   :tavern  {:desc "tavern" 
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :tavern-loot
             :enemy [townsfolk skele]
             :enter {:goto tavern-map
                     :new-map-name "THE HUNGRY HAG TAVERN"
                     :start-coords {:row 3 :col 1}}}
   :field   {:desc "field"
             :loot {} 
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :gen-loot
             :enemy [townsfolk cultist wizard werewolf ghost skele]}})
