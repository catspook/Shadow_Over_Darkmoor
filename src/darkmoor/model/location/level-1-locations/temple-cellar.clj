(in-ns 'darkmoor.core)

(load "model/enemy-data")

(def temple-cellar-map 
  [[:temple-cellar-0 :temple-cellar-1]
   [:temple-cellar-2 :temple-cellar-3]]) 

(def init-temple-cellar
   {:temple-cellar-0 {:desc "temple basement 0-enter temple" ;cell description
                     :loot {} ;list of items at this cell (initially empty)
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :temple-loot ;pull items from this master list of items
                     :map "resources/map-temple-cellar.txt"
                     :enemy [cultist]  ;list of possible enemies at this cell
                     :exit {:row 1 :col 1 :old-map-name "THE ABBEY OF THE DAWN "}} ;optional -- if player can go back a map at this cell, list what coords they should go back to
                  ;  :enter                               optional -- if player can go to a new map at this cell, list new map name and what coords they should start at
   :temple-cellar-1 {:desc "temple basement 1"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :temple-loot
                     :map "resources/map-temple-cellar.txt"
                     :enemy [cultist]}
   :temple-cellar-2 {:desc "temple basement 2"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :temple-loot
                     :map "resources/map-temple-cellar.txt"
                     :enemy [cultist]}
   :temple-cellar-3 {:desc "temple basement 3"
                     :loot {}
                     :loot-desc {:yes "Yes loot" :no "no loot"}
                     :get-loot-from :temple-loot
                     :map "resources/map-temple-cellar.txt"
                     :enemy [cultist]}})
