(in-ns 'darkmoor.core)

(load "model/enemy-data")

(def crypt-map 
  [[:nil-cell   :crypt-0 :nil-cell  ]
   [:nil-cell   :crypt-1 :nil-cell  ]
   [:crypt-2 :crypt-3 :crypt-4]
   [:crypt-5 :crypt-6 :crypt-7]])

(def init-crypt
  {:nil-cell {}
   :crypt-0 {:desc "crypt 0-exit" 
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-crypt.txt"
             :enemy [skele ghost]
             :exit {:row 0 :col 1 :old-map-name "DARKMOOR'S GRAVEYARD"}}
   :crypt-1 {:desc "crypt 1"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-crypt.txt"
             :enemy [skele ghost]}
   :crypt-2 {:desc "crypt 2"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-crypt.txt"
             :enemy [skele ghost]}
   :crypt-3 {:desc "crypt 3"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-crypt.txt"
             :enemy [skele ghost]}
   :crypt-4 {:desc "crypt 4"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-crypt.txt"
             :enemy [skele ghost]}
   :crypt-5 {:desc "crypt 5"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-crypt.txt"
             :enemy [skele ghost]}
   :crypt-6 {:desc "crypt 6"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-crypt.txt"
             :enemy [skele ghost]}
   :crypt-7 {:desc "crypt 7"
             :loot {}
             :loot-desc {:yes "Yes loot" :no "no loot"}
             :get-loot-from :graveyard-loot
             :map "resources/map-crypt.txt"
             :enemy [skele ghost]}})
