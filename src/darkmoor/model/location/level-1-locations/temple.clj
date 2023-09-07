(in-ns 'darkmoor.core)

(load "model/enemy-data")

(def temple-map 
  [[:nil-cell :temple-0 :temple-1 :nil-cell]
   [:temple-2 :temple-3 :temple-4 :temple-5]
   [:nil-cell :temple-6 :temple-7 :nil-cell]
   [:nil-cell :temple-8 :temple-9 :nil-cell]])

(def init-temple
  {:nil-cell {}
   :temple-0 {:desc "temple 0-exit" 
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :map "resources/map-temple.txt"
              :enemy [cultist ghost]
              :exit {:row 1 :col 1 :old-map-name "DARKMOOR VILLAGE"}}
   :temple-1 {:desc "temple 1"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :map "resources/map-temple.txt"
              :enemy [cultist ghost]}
   :temple-2 {:desc "temple 2-enter temple basement"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :map "resources/map-temple.txt"
              :enemy [cultist ghost]
              :enter {:goto temple-cellar-map
                      :new-map-name "THE ABBEY MAUSOLEUM"
                      :start-coords {:row 0 :col 0}}}
   :temple-3 {:desc "temple 3"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :map "resources/map-temple.txt"
              :enemy [cultist ghost]}
   :temple-4 {:desc "temple 4"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :map "resources/map-temple.txt"
              :enemy [cultist ghost]}
   :temple-5 {:desc "temple 5"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :map "resources/map-temple.txt"
              :enemy [cultist ghost]}
   :temple-6 {:desc "temple 6"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :map "resources/map-temple.txt"
              :enemy [cultist ghost]}
   :temple-7 {:desc "temple 7"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :map "resources/map-temple.txt"
              :enemy [cultist ghost]}
   :temple-8 {:desc "temple 8"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :map "resources/map-temple.txt"
              :enemy [cultist ghost]}
   :temple-9 {:desc "temple 9"
              :loot {}
              :loot-desc {:yes "Yes loot" :no "no loot"}
              :get-loot-from :temple-loot
              :map "resources/map-temple.txt"
              :enemy [townsfolk ghost]}})
