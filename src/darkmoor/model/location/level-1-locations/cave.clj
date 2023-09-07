(in-ns 'darkmoor.core)

(load "model/enemy-data")

(def cave-map
  [[:nil-cell  :nil-cell  :nil-cell  :cave-9 :cave-10 :cave-11 :cave-12 :cave-13]
   [:nil-cell  :nil-cell  :cave-6 :cave-7 :cave-8  :nil-cell   :cave-14 :cave-15]
   [:nil-cell  :cave-2 :cave-3 :cave-4 :cave-5  :nil-cell   :nil-cell   :nil-cell ]
   [:cave-0 :cave-1 :nil-cell  :nil-cell  :nil-cell   :nil-cell   :nil-cell   :nil-cell]])

(def init-cave
  {:nil-cell {}
   :cave-0 {:desc "cave 0-exit" 
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]
            :exit {:row 3 :col 1 :old-map-name "DARKMOOR VILLAGE"}}
   :cave-1 {:desc "cave 1"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-2 {:desc "cave 2"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-3 {:desc "cave 3"
            :loot {}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-4 {:desc "cave 4"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-5 {:desc "cave 5"
            :loot {}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-6 {:desc "cave 6"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-7 {:desc "cave 7"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-8 {:desc "cave 8"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-9 {:desc "cave 9"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-10 {:desc "cave 10"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-11 {:desc "cave 11"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-12 {:desc "cave 12"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-13 {:desc "cave 13"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-14 {:desc "cave 14"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}
   :cave-15 {:desc "cave 15"
            :loot {}
            :loot-desc {:yes "Yes loot" :no "no loot"}
            :get-loot-from :cave-loot
            :map "resources/map-cave.txt"
            :enemy [werewolf skele ghost cultist]}})
   