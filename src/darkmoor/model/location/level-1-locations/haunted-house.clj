(in-ns 'darkmoor.core)

(load "model/enemy-data")

(def haunted-house-map
  [[:haunted-house-0 :haunted-house-1 :haunted-house-2 :nil-cell]
   [:haunted-house-3 :haunted-house-4 :haunted-house-5 :haunted-house-6]])

(def init-haunted-house
  {:nil-cell {}
   :haunted-house-0 {:desc "haunted-house 0"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :haunted-house-loot
                     :map "resources/map-haunted-house.txt" 
                     :enemy [ghost]}
   :haunted-house-1 {:desc "haunted-house 1"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :haunted-house-loot
                     :map "resources/map-haunted-house.txt" 
                     :enemy [townsfolk]}
   :haunted-house-2 {:desc "haunted-house 2"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :haunted-house-loot
                     :map "resources/map-haunted-house.txt" 
                     :enemy [ghost]}
   :haunted-house-3 {:desc "haunted-house 3"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :haunted-house-loot
                     :map "resources/map-haunted-house.txt" 
                     :enemy [townsfolk]}
   :haunted-house-4 {:desc "haunted-house 4"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :haunted-house-loot
                     :map "resources/map-haunted-house.txt" 
                     :enemy [ghost]}
   :haunted-house-5 {:desc "haunted-house 5"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :haunted-house-loot
                     :map "resources/map-haunted-house.txt" 
                     :enemy [townsfolk]}
   :haunted-house-6 {:desc "haunted-house 6-exit"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :haunted-house-loot
                     :map "resources/map-haunted-house.txt" 
                     :enemy [ghost]
                     :exit {:row 2 :col 2 :old-map-name "DARKMOOR VILLAGE"}}})
