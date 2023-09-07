(in-ns 'darkmoor.core)

(load "model/enemy-data")

(def tavern-map
  [[:tavern-0 :tavern-1 :tavern-2]
   [:tavern-3 :tavern-4 :tavern-5]
   [:tavern-6 :tavern-7 :tavern-8]
   [:tavern-9 :tavern-10 :tavern-11]])

(def init-tavern
  {:tavern-0 {:desc "tavern 0"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :map "resources/map-tavern.txt"
              :enemy [skele townsfolk]}
   :tavern-1 {:desc "tavern 1"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :map "resources/map-tavern.txt"
              :enemy [skele townsfolk]}
   :tavern-2 {:desc "tavern 2"
              :loot {}
              :get-loot-from :tavern-loot
              :map "resources/map-tavern.txt"
              :enemy [wizard cultist]}
   :tavern-3 {:desc "tavern 3"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :map "resources/map-tavern.txt"
              :enemy [skele townsfolk]}
   :tavern-4 {:desc "tavern 4"
              :loot {}
              :get-loot-from :tavern-loot
              :map "resources/map-tavern.txt"
              :enemy [skele townsfolk]}
   :tavern-5 {:desc "tavern 5"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :map "resources/map-tavern.txt"
              :enemy [cultist wizard]}
   :tavern-6 {:desc "tavern 6"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :map "resources/map-tavern.txt"
              :enemy [skele townsfolk]}
   :tavern-7 {:desc "tavern 7"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :map "resources/map-tavern.txt"
              :enemy [skele townsfolk]}
   :tavern-8 {:desc "tavern 8"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :map "resources/map-tavern.txt"
              :enemy [cultist wizard]}
   :tavern-9 {:desc "tavern 9"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :tavern-loot
              :map "resources/map-tavern.txt"
              :enemy [skele townsfolk]}
   :tavern-10 {:desc "tavern 10-exit"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :tavern-loot
               :map "resources/map-tavern.txt"
               :enemy [skele townsfolk] 
               :exit {:row 2 :col 3 :old-map-name "DARKMOOR VILLAGE"}}
   :tavern-11 {:desc "tavern 11"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :tavern-loot
               :map "resources/map-tavern.txt"
               :enemy [cultist wizard]}})
   