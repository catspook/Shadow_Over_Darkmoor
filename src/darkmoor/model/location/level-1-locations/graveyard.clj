(in-ns 'darkmoor.core)

(load "model/enemy-data")

(def graveyard-map
  [[:grave-6 :grave-7 :grave-8]
   [:grave-3 :grave-4 :grave-5]
   [:grave-0 :grave-1 :grave-2]])

(def init-graveyard
  {:grave-0 {:desc "graveyard 0"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-graveyard.txt"
             :enemy [skele wizard werewolf]}
   :grave-1 {:desc "graveyard 1-exit"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-graveyard.txt"
             :enemy [skele cultist werewolf]
             :exit {:row 1 :col 0 :old-map-name "DARKMOOR VILLAGE"}}
   :grave-2 {:desc "graveyard 2"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-graveyard.txt"
             :enemy [skele wizard werewolf]}
   :grave-3 {:desc "graveyard 3"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-graveyard.txt"
             :enemy [skele cultist werewolf]}
   :grave-4 {:desc "graveyard 4"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-graveyard.txt"
             :enemy [skele wizard werewolf]}
   :grave-5 {:desc "graveyard 5"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-graveyard.txt"
             :enemy [skele cultist werewolf]}
   :grave-6 {:desc "graveyard 6"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-graveyard.txt"
             :enemy [skele wizard werewolf]}
   :grave-7 {:desc "graveyard 7-enter crypt"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-graveyard.txt"
             :enemy [skele cultist werewolf]
             :enter {:goto crypt-map 
                     :new-map-name "THE CRYPT OF LADY IOETTA"
                     :start-coords {:row 0 :col 1}}}
   :grave-8 {:desc "graveyard 8"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-graveyard.txt"
             :enemy [skele wizard werewolf]}})
