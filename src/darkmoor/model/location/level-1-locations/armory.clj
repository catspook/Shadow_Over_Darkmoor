(in-ns 'darkmoor.core)

(load "model/enemy-data")

(def armory-map
  [[:armory-0 :armory-1 :armory-2]
   [:armory-3 :armory-4 :armory-5]
   [:armory-6 :armory-7 :armory-8]])

(def init-armory
  {:armory-0 {:desc "armory 0"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :armory-loot
              :map "resources/map-armory.txt"
              :enemy [cultist townsfolk]}
   :armory-1 {:desc "armory 1"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :armory-loot
              :map "resources/map-armory.txt"
              :enemy [townsfolk]}
   :armory-2 {:desc "armory 2"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :armory-loot
              :map "resources/map-armory.txt"
              :enemy [cultist townsfolk]}
   :armory-3 {:desc "armory 3"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :armory-loot
              :map "resources/map-armory.txt"
              :enemy [townsfolk]}
   :armory-4 {:desc "armory 4"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :armory-loot
              :map "resources/map-armory.txt"
              :enemy [cultist townsfolk]}
   :armory-5 {:desc "armory 5"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :armory-loot
              :map "resources/map-armory.txt"
              :enemy [townsfolk]}
   :armory-6 {:desc "armory 6"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :armory-loot
              :map "resources/map-armory.txt"
              :enemy [cultist townsfolk]}
   :armory-7 {:desc "armory 7-exit" 
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :armory-loot
              :enemy [townsfolk]
              :map "resources/map-armory.txt"
              :exit {:row 0 :col 3 :old-map-name "DARKMOOR VILLAGE"}}
   :armory-8 {:desc "armory 8"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :armory-loot
              :map "resources/map-armory.txt"
              :enemy [cultist townsfolk]}})
