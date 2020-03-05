(in-ns 'darkmoor.core)

(load "model/enemy-data")
(load "model/objects-data")
(load "model/location-data")
(load "model/model")

;PAUSE__________________________________________________________

(defn pause []
  "pauses by asking for user input"
  (println "\n Hit ENTER to continue.")
  (read-line))

(defn pause-screen5 []
  "pauses game for 5 seconds"
  (Thread/sleep 5000))

(defn pause-screen3 []
  "pauses game for 3 seconds"
  (Thread/sleep 3000))

(defn pause-screen2 []
  "pauses game for 2 seconds"
  (Thread/sleep 2000))

(defn pause-screen1-5 []
  "pauses game for 1.5 seconds"
  (Thread/sleep 1500))

(defn pause-screen1 []
  "pauses game for 1 second"
  (Thread/sleep 1000))

;DISPLAY RESOURCE FILES__________________________________

(defn clear-screen []
  "clears screen using ANSI escape sequence"
  (print (str (char 27) "[2J"))) 

(defn open-title []
  "opens title sequence, pauses screen for time to read it, then clears screen"
  (clear-screen)
  (with-open [rdr (io/reader "resources/title.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause-screen3)
  (clear-screen))

(defn open-help []
  (clear-screen)
  (with-open [rdr (io/reader "resources/help.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause)
  (clear-screen))

(defn open-intro []
  "opens intro paragraph, pauses screen, then clears screen"
  (with-open [rdr (io/reader "resources/intro.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause)
  (open-help))

(defn open-inv-menu []
  (with-open [rdr (io/reader "resources/inv-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-inv-menu-end []
  (with-open [rdr (io/reader "resources/inv-menu-end.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-loot-menu []
  (with-open [rdr (io/reader "resources/loot-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-loot-menu-end []
  (with-open [rdr (io/reader "resources/loot-menu-end.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-you-died []
  (with-open [rdr (io/reader "resources/you-died.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-fight []
  (with-open [rdr (io/reader "resources/fight.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-you-win []
  (with-open [rdr (io/reader "resources/you-win.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-sword2 []
  (with-open [rdr (io/reader "resources/sword2.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

;PRINT INVENTORY MENU______________________

(defn print-item [player k]
  (let [item (get-item k)]
    ;only print if item count is > 0
    (if (pos? (get-inv-item-count player k))
      (do

        ;is it equipped?
       (if (= (:slot item) :hand)
          (if (or (item-eq-in-r-hand? player k)
                  (item-eq-in-l-hand? player k))
            (print " ********   ")
            (print "            "))
          ;get the id of the eq slot that's the
          (if (is-item-eq? player (get-item-slot k) k)
            (print " ********   ")
            (print "            ")))

        ;print item id
        (print (str (:id item)))

        ;print " " if id < 10
        (if (< (:id item) 10)
          (print "    ")
          (print "   "))

        ;print item's name
        (print (:name item))

        ;print item quantity if > 1 
        (if (> (get-inv-item-count player k) 1)
          (print (str " (" (get-inv-item-count player k) ")"))
          (print "    "))
        ;print extra spaces so things line up
        (doseq [space (repeat (- 28 (count (:name item))) " ")] (print space))

        ;print item health
        (if (> (:health item) -1)
          (do
            (print (str "+" (:health item)))
            (if (< (:health item) 10) (print " ")))
          (do
            (print (str "-" (:health item)))
            (if (> (:health item) -10) (print " "))))

        (print "      ")

        ;print item damage
        (if (> (:damage item) -1)
          (do
            (print (str "+" (:damage item)))
            (if (< (:damage item) 10) (print " ")))
          (do
            (print (str "-" (:damage item)))
            (if (> (:damage item) -10) (print " "))))

        ;print item damage type
        (println (str "      " (:d-type item)))))))

(defn print-inv-menu [player]
  (open-inv-menu)
  (print (str "                 "
              "| \u2665 " 
              (first (:health player)) 
              "/" 
              (last (:health player))))
  (if (> (last (:health player)) 9)
    (if (> (first (:health player)) 9)
      (println "                                 |")
      (println "                                  |"))
    (println "                                   |"))

  (print (str "                 "
              "| \u2718 " 
              (:damage player)))
  (if (> (:damage player) 9)
    (println "                                    |")
    (println "                                     |"))

  (open-inv-menu-end)
  (println "\n\n EQUIPPED . ID . ITEM NAME                     . HEALTH . DAMAGE . DAMAGE TYPE")
  (println " -----------------------------------------------------------------------------")
  (doseq [k (keys (:inv player))] (print-item player k))
  (println))

;PRINT LOOT MENU____________________________________ 

(defn print-loot [player map-stack loc-info k]
  (let [item (get-item k)]
    ;only print if item count is > 0
    (if (pos? (get-loot-item-count player map-stack loc-info k))
      (do

        ;print item id
        (print (str " " (:id item)))

        ;print " " if id < 10
        (if (< (:id item) 10)
          (print "    ")
          (print "   "))

        ;print item's name
        (print (:name item))

        ;print item quantity if > 1 
        (if (> (get-loot-item-count player map-stack loc-info k) 1)
          (print (str " (" (get-loot-item-count player map-stack loc-info k) ")"))
          (print "    "))
        ;print extra spaces so things line up
        (doseq [space (repeat (- 28 (count (:name item))) " ")] (print space))

        ;print item health
        (if (> (:health item) -1)
          (do
            (print (str "+" (:health item)))
            (if (< (:health item) 10) (print " ")))
          (do
            (print (str (:health item)))
            (if (> (:health item) -10) (print " "))))

        (print "      ")

        ;print item damage
        (if (> (:damage item) -1)
          (do
            (print (str "+" (:damage item)))
            (if (< (:damage item) 10) (print " ")))
          (do
            (print (str (:damage item)))
            (if (> (:damage item) -10) (print " "))))

        ;print item damage type
        (println (str "      " (:d-type item)))))))

(defn print-loot-menu [player map-stack loc-info]
  (open-loot-menu)
  (print (str "             "
              "| \u2665 " 
              (first (:health player)) 
              "/" 
              (last (:health player)))) 
  (if (> (last (:health player)) 9)
    (if (> (first (:health player)) 9)
      (println "                              |")
      (println "                               |"))
    (println "                                |"))

  (print (str "             "
              "| \u2718 " 
              (:damage player)))
  (if (> (:damage player) 9)
    (println "                                 |")
    (println "                                  |"))

  (open-loot-menu-end)
  (println "\n\n ID . ITEM NAME                     . HEALTH . DAMAGE . DAMAGE TYPE")
  (println " ------------------------------------------------------------------")
  (doseq [k (keys (get-loc-loot player map-stack loc-info))] (print-loot player map-stack loc-info k))
  (println))

;PRINT FIGHT MENU and DISPLAY___________________________

(defn print-enemy-player-stats [player enemy]
  (clear-screen)
  (print (str " " (:desc enemy) "\n \u2665 " (:health enemy) "\n \u2718 " (:damage enemy)))
  (println (str "\n Weak to " (apply str (interpose ", " (:weak enemy))) "."))
  (let [pl-dmg (get-player-extra-dmg player (:weak enemy))]
    (print (str "\n\n The Hero of Darkmoor"  "\n \u2665 " (first (:health player)) "/" (second (:health player)) "\n \u2718 " pl-dmg))
    (let [pl-dmg-types (filter #(and (not (nil? %)) (not= "" %)) (get-player-dmg-types player))]
      (println (str "\n Damage type: " (apply str (interpose ", " pl-dmg-types)) "\n"))
      (open-sword2)
      (println))))

(defn print-enemy-attack [player enemy hit-chance]
  (cond
    (= hit-chance 0) (println (str " Miss! You take 0 " (:dmg-type enemy) " damage.\n"))
    (= hit-chance 9) (println (str " Critical hit! You take " (int (* 1.5 (:damage enemy))) " " (:dmg-type enemy) " damage.\n"))
    :else (println (str " Hit! You take " (:damage enemy) " " (:dmg-type enemy) " damage.\n")))
  (pause))

(defn enemy-attack [player enemy]
  (println (str " " (:attack-desc enemy)))
  (let [hit-chance (rand-int 10)]
    (print-enemy-attack player enemy hit-chance)
    (get-new-player-health player enemy hit-chance)))

(defn print-fight-item [player k]
  (let [item (get-item k)]
    ;only print if item count is > 0 and it's a weapon
    (if (and (pos? (get-inv-item-count player k))
             (= :hand (get-item-slot k)))
      (do

        ;is it equipped?
        (if (or (item-eq-in-r-hand? player k)
                (item-eq-in-l-hand? player k))
          (print " ********   ")
          (print "            "))

        ;print item id
        (print (str (:id item)))

        ;print " " if id < 10
        (if (< (:id item) 10)
          (print "    ")
          (print "   "))

        ;print item's name
        (print (:name item))

        ;print item quantity if > 1 
        (if (> (get-inv-item-count player k) 1)
          (print (str " (" (get-inv-item-count player k) ")"))
          (print "    "))
        ;print extra spaces so things line up
        (doseq [space (repeat (- 28 (count (:name item))) " ")] (print space))

        ;print item health
        (if (> (:health item) -1)
          (do
            (print (str "+" (:health item)))
            (if (< (:health item) 10) (print " ")))
          (do
            (print (str "-" (:health item)))
            (if (> (:health item) -10) (print " "))))

        (print "      ")

        ;print item damage
        (if (> (:damage item) -1)
          (do
            (print (str "+" (:damage item)))
            (if (< (:damage item) 10) (print " ")))
          (do
            (print (str "-" (:damage item)))
            (if (> (:damage item) -10) (print " "))))

        ;print item damage type
        (println (str "      " (:d-type item)))))))

(defn print-fight-inv-menu [player enemy]
  (open-inv-menu)
  (print (str "                 "
              "| \u2665 " 
              (first (:health player)) 
              "/" 
              (last (:health player))))
  (if (> (last (:health player)) 9)
    (if (> (first (:health player)) 9)
      (println "                                 |")
      (println "                                  |"))
    (println "                                   |"))

  (print (str "                 "
              "| \u2718 " 
              (:damage player)))
  (if (> (:damage player) 9)
    (println "                                    |")
    (println "                                     |"))

  (open-inv-menu-end)
  (println (str "\n\n " (:desc enemy) " is weak to: " (apply str (interpose ", " (:weak enemy))) "."))
  (println "\n\n EQUIPPED . ID . ITEM NAME                     . HEALTH . DAMAGE . DAMAGE TYPE")
  (println " -----------------------------------------------------------------------------")
  (doseq [k (keys (:inv player))] (print-fight-item player k))
  (println))

(defn player-attack-drink-potion [player enemy]
  (println "\n You drink the potion, and immediately feel a little better. \n")
  (pause-screen2)
  (clear-screen) 
  (print-enemy-player-stats (drink-hp player) enemy)
  (drink-hp player))

(defn enemy-attack [player enemy]
  (println (str " " (:attack-desc enemy)))
  (let [hit-chance (rand-int 10)]
    (print-enemy-attack player enemy hit-chance)
    (get-new-player-health player enemy hit-chance)))

(defn bonus-weapon-dmg [player enemy pl-dmg]
  (print (str " " (:desc enemy) " takes "))
  (if (> pl-dmg (:damage player))
    (print (str (int (* 1.5 (- pl-dmg (:damage player))))))
    (print (str (- pl-dmg (:damage player)))))
  (print " bonus weapon damage!\n"))

(defn player-attack [player enemy pl-dmg]
  (println " You attack!") 
  (let [hit-chance (rand-int 10)] 
    (pause-screen1)
    (cond
      (= hit-chance 0) (println " Miss! Your hit goes wide and you do 0 damage.")
      (= hit-chance 9) (println (str " Critical hit! You do " (int (* 1.5 pl-dmg)) " damage."))
      :else (println (str " Hit! You do " pl-dmg " damage.")))
    (if (and (not= 0 hit-chance) 
             (> pl-dmg (:damage player)))
      (bonus-weapon-dmg player enemy pl-dmg))
    (pause-screen2)
    [player (get-new-enemy-health enemy pl-dmg hit-chance)]))

(defn print-player-attack-menu [player]
  (println "\n a Attack!")
  (if (inv-has-weapons? player)
    (println " w Switch out a weapon")
    (println " You have no other weapons to switch to."))
  (if (pos? (:hp player))
    (println (str " p Drink a health potion (" (:hp player) " remaining)"))
    (println " You are out of health potions."))
  (println " h Help!\n q Quit the game\n\n"))

(defn print-fight-start [player map-stack loc-info enemy]
  (clear-screen)
  (open-fight)
  (println (str "\n You arrive at " (get-loc-desc player map-stack loc-info) ".\n"))
  (println (str " " (:desc enemy) " jumps out and attacks you!\n"))
  (pause-screen5))

(defn did-player-win? [player enemy]
  (if (not (pos? (first (:health player))))
    (do
      (clear-screen)
      (open-you-died)
      (System/exit 0))
    (do 
      (clear-screen)
      (open-you-win)
      (println (str "\n You killed " (:desc enemy) "!"))
      (println " You gained a health potion!")
      (pause-screen5)
      (clear-screen)
      (assoc player :hp (inc (:hp player)) :moved? false))))

;PRINT MAIN MENU__________________________________________________________

(defn print-main-menu [player map-stack loc-info enter? exit? loot? n s e w cant-move inv? hp?]
  (println (str " You are at " (get-loc-desc player map-stack loc-info)))
  (println)
  (if enter?
    (println (str " x  You can enter " enter? " from here.\n")))
  (if exit?
    (println (str " x  You can exit to " exit? " from here.\n")))
  ; want to make sure that there's a non-0 value
  (if (first loot?) 
    (println (str " l  " (last loot?)))
    (println (str " " (last loot?))))
  (println "\n <<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>\n")
  
  (if (first n)
    (println (str " " (second n) "  To the " (last n) ", you see " (first n))))
  (if (first s)
    (println (str " " (second s) "  To the " (last s) ", you see " (first s))))
  (if (first e)
    (println (str " " (second e) "  To the " (last e) ", you see " (first e))))
  (if (first w)
  (println (str " " (second w) "  To the " (last w) ", you see " (first w))))
  (if (not (empty? cant-move))
      (println (str " You cannot move " (apply str (interpose ", " cant-move)) ".")))
  (println "\n <<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>\n")

  (println (str "    \u2665 " (first (:health player)) "/" (last (:health player))))
  (if hp?
    (println (str " p  Drink a Health Potion (" (:hp player) " remaining)"))
    (println (str " You are out of Health Potions.")))
  (println)
  (println (str "    \u2718 " (:damage player)))
  (if inv?
    (println " i  Open your inventory")
    (println " Your inventory is empty."))

  (println "\n <<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>\n")
  (println " h  Help!")
  (println " q  Quit the game")
  (println))
