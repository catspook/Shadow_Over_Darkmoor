(in-ns 'darkmoor.core)

(load "model/enemy-data")

(def archive-map
  [[:archive-0 :archive-1 :archive-2]
   [:archive-3 :archive-4 :archive-5]
   [:archive-6 :archive-7 :archive-8]])

(def init-archive
  {:archive-0 {:desc "archive 0"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :archive-loot
               :map "resources/map-archive.txt"
               :enemy [werewolf ghost]}
   :archive-1 {:desc "archive 1"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :archive-loot
               :map "resources/map-archive.txt"
               :enemy [werewolf ghost]}
   :archive-2 {:desc "archive 2"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :archive-loot
               :map "resources/map-archive.txt"
               :enemy [werewolf ghost]}
   :archive-3 {:desc "archive 3-exit" 
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :archive-loot
               :map "resources/map-archive.txt"
               :enemy [werewolf ghost]
               :exit {:row 0 :col 2 :old-map-name "DARKMOOR VILLAGE"}}
   :archive-4 {:desc "archive 4"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :archive-loot
               :map "resources/map-archive.txt"
               :enemy [werewolf ghost]}
   :archive-5 {:desc "archive 5"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :archive-loot
               :map "resources/map-archive.txt"
               :enemy [werewolf ghost]}
   :archive-6 {:desc "archive 6"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :archive-loot
               :map "resources/map-archive.txt"
               :enemy [werewolf ghost]}
   :archive-7 {:desc "archive 7"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :archive-loot
               :map "resources/map-archive.txt"
               :enemy [werewolf ghost]}
   :archive-8 {:desc "archive 8"
               :loot {}
               :loot-desc {:yes "Yes loot" :no "no loot"}
               :get-loot-from :archive-loot
               :map "resources/map-archive.txt"
               :enemy [werewolf ghost]}})
   