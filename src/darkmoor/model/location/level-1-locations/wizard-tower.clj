(in-ns 'darkmoor.core)

(load "model/enemy-data")

(def wizard-tower-map
  [[:nil-cell :wizard-tower-0 :wizard-tower-1]
   [:wizard-tower-2 :wizard-tower-3 :wizard-tower-4]
   [:wizard-tower-5 :wizard-tower-6 :wizard-tower-7]
   [:nil-cell :wizard-tower-8 :wizard-tower-9]])

(def init-wizard-tower
  {:nil-cell {}
   :wizard-tower-0 {:desc "wizard-tower 0"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :map "resources/map-wizard-tower.txt"
                    :enemy [wizard]}
   :wizard-tower-1 {:desc "wizard-tower 1"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :map "resources/map-wizard-tower.txt"
                    :enemy [wizard]}
   :wizard-tower-2 {:desc "wizard-tower 2"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :map "resources/map-wizard-tower.txt"
                    :enemy [wizard]}
   :wizard-tower-3 {:desc "wizard-tower 3"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :map "resources/map-wizard-tower.txt"
                    :enemy [wizard]}
   :wizard-tower-4 {:desc "wizard-tower 4"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :map "resources/map-wizard-tower.txt"
                    :enemy [wizard]}
   :wizard-tower-5 {:desc "wizard-tower 5-exit"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :map "resources/map-wizard-tower.txt"
                    :enemy [wizard]
                    :exit {:row 2 :col 0 :old-map-name "DARKMOOR VILLAGE"}}
   :wizard-tower-6 {:desc "wizard-tower 6"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :map "resources/map-wizard-tower.txt"
                    :enemy [wizard]}
   :wizard-tower-7 {:desc "wizard-tower 7"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :map "resources/map-wizard-tower.txt"
                    :enemy [wizard]}
   :wizard-tower-8 {:desc "wizard-tower 8"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :map "resources/map-wizard-tower.txt"
                    :enemy [wizard]}
   :wizard-tower-9 {:desc "wizard-tower 9"
                    :loot {}
                    :loot-desc {:yes "Yes loot" :no "no loot"}
                    :get-loot-from :wizard-tower-loot
                    :map "resources/map-wizard-tower.txt"
                    :enemy [wizard]}})
