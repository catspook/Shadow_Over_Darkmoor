(ns darkmoor.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s]))

(def debug
  false)

(defn print-debug [thing]
  (if debug
  (println thing)))

;OPENING SEQUENCE__________________________________________________________

(defn clear-screen []
  "clears screen using ANSI escape sequence"
  (print (str (char 27) "[2J"))) 

(defn pause []
  "pauses by asking for user input"
  (println) 
  (println "Press any key to continue.")
  (read-line))

(defn pause-screen2 []
  "pauses thread for 2 seconds"
  (Thread/sleep 2000))

(defn pause-screen1-5 []
  "pauses thread for 1.5 seconds"
  (Thread/sleep 1500))

(defn pause-screen1 []
  "pauses thread for 1 second"
  (Thread/sleep 1000))

(defn open-title []
  "opens title sequence, pauses screen for time to read it, then clears screen"
  (print (str (char 27) "[2J")) ;clear screen using ANSI escape
  (with-open [rdr (io/reader "resources/title.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause-screen2)
  (clear-screen))

(defn open-main-menu []
  "opens menu. Eventually will list all commands"
  (with-open [rdr (io/reader "resources/main-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-intro []
  "opens intro paragraph, pauses screen, then clears screen"
  (with-open [rdr (io/reader "resources/intro.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (open-main-menu)
  (pause)
  (clear-screen))

;OBJECT DATA____________________________________________________________________________

;general obejcts
(def o1
  {:potion false 
   :desc "WOODCUTTER'S AXE: Blunted and notched. +2 SLASHING DAMAGE" 
   :health 0 
   :damage 2})
(def o2
  {:potion false 
   :desc "OLD LEATHER ARMOR: Better than fighting in your traveling clothes. +4 HEALTH" 
   :health 4 
   :damage 0})
(def o3
  {:potion false 
   :desc "CULTIST'S DAGGER: A strange symbol carved on the blade glows with an unearthly purple light. +5 NECROTIC DAMAGE" 
   :health 0 
   :damage 5})
(def o4
  {:potion false 
   :desc "FIRE SHIELD: Flames lick around the edges of this shield, but thankfully the handle is not red-hot. +2 HEALTH, +1 FIRE DAMAGE" 
   :health 2 
   :damage 1})
(def o5
  {:potion false 
   :desc "BUTCHER'S GLOVES: These stink, but your fingers have never felt so protected. +2 HEALTH" 
   :health 2 
   :damage 0})
(def o6
  {:potion false 
   :desc "FEMUR: Makes a good club, but takes both of your hands to carry. +5 BLUNT DAMAGE" 
   :health 0 
   :damage 8})
(def o7
  {:potion false 
   :desc "HALF-PLATE ARMOR: Caked in dirt. You're pretty sure that no one living has worn this recently. +7 HEALTH" 
   :health 7 
   :damage 0})
(def o8
  {:potion false 
   :desc "ETHERIAL BOOTS: You feel just a bit less present while wearing these. +3 HEALTH" 
   :health 3 
   :damage 0})
(def o9
  {:potion false 
   :desc "SHARPENED SYTHE: More of a farmer's weapon than Death's. +3 SLASHING DAMAGE" 
   :health 0 
   :damage 3})
(def o10
  {:potion false 
   :desc "SPIKED FLAIL: Screaming faces are engraved on the handle, and the spikes are coated in gore. +6 PIERCING DAMAGE" 
   :health 0 
   :damage 6})
(def o11
  {:potion false
   :desc "POLE-AXE: Literally a hand axe tied to a long stick. +4 SLASHING DAMAGE"
   :health 0
   :damage 4})
(def o12
  {:potion false
   :desc "FINE CHAINMAIL: Finely-made armor consisting of thousands of tiny, silver rings. +6 HEALTH"
   :health 9
   :damage 0})
(def o13
  {:potion false
   :desc "CULTIST'S ROBE: A thick, wool robe embroidered with enchanted runes that do damage to enemies while protecting the wearer. +4 HEALTH, +3 NECROTIC DAMAGE"
   :health 4
   :damage 3})
(def o14
  {:potion false
   :desc "FINE SILVER SWORD: A thin, curved blade with a decorative eagle carved around the handle. +6 PIERCING DAMAGE"
   :health 0
   :damage 6})
(def o15
  {:potion false
   :desc "SEVERED ARM: It's just like a club. A squishy, stinking club. +3 NECROTIC DAMAGE"
   :health 0
   :damage 3})
(def o16
  {:potion false
   :desc "WARHAMMER: A large, heavy stone hammer carved with intricate geometric designs. +7 BLUNT DAMAGE"
   :health 0
   :damage 7})
(def o17
  {:potion false
   :desc "RUSTED BOW AND ARROW: A half-rotted bow and a handful of rusted arrows. This weapon is ranged, but requires both hands to use. +2 HEALTH, 4 PIERCING DAMAGE"
   :health 0
   :damage 5})
(def o18
  {:potion false
   :desc "GREAT AXE: Passed down from generation to generation, this once was a prized family heirloom. It's yours now. +7 SLASHING DAMAGE"
   :health 0
   :damage 7})
(def o19
  {:potion false
   :desc "SPIKED SHIELD: Long spikes protrude from the center of this shield. +3 HEALTH, +1 PIERCING DAMAGE"
   :health 3
   :damage 1})
(def o20
  {:potion false
   :desc "SPIKED GAUNTLETS: Finely made from a previous age, if a bit dusty. +2 HEALTH, +1 PIERCING DAMAGE"
   :health 2
   :damage 1})
(def o21
  {:potion false
   :desc "SPIKED PAULDRONS: Your neck will be somewhat more protected by these large spikes. Just don't bend your head too far. +1 HEALTH"
   :health 1
   :damage 0})
;(def o22
;  {:potion false
;   :key true
;   :desc "TEMPLE KEY: A large brass key, marked with the symbol of the Templar Order. This probably unlocks something in the temple."
;   :health 0
;   :damage 0})
(def o23
  {:potion false
   :desc "BARREL OF DEAD FISH: You don't think you could get within a foot of this without throwing up."
   :health 0
   :damage -2})
(def o24
  {:potion false
   :desc "HEMP ROPE: It's so rotten it snaps as soon as you pick it up."
   :health 0
   :damage 0})
(def o25
  {:potion false
   :desc "BARREL OF ROTTEN GRAIN: Not quite beer yet."
   :health 0
   :damage 0})
(def o26
  {:potion false
   :desc "RUSTED HORSESHOE: You're not a centaur, so you're not sure what you'd do with this." 
   :health 0
   :damage 0})
(def o27
  {:potion false
   :desc "BROKEN BAR STOOL: A little unweildy, but heavy. Getting hit with this will hurt. +5 BLUNT DAMAGE, -2 HEALTH"
   :health -2
   :damage 5})
(def o28
  {:potion false
   :desc "CHAIR LEG: Made of sturdy oak. +3 BLUNT DAMAGE"
   :health 0
   :damage 3})
(def o29
  {:potion false
   :desc "BROKEN WINE BOTTLE: Sharp as glass. +4 PIERCING DAMAGE"
   :health 0
   :damage 4})
(def o30
  {:potion false 
   :desc "RUM: Gives the drinker the Sea Legs effect while EQUIPPED, allowing them to evade more attacks (+1 HEALTH), but reducing their aim (-1 DAMAGE)." 
   :health 1
   :damage -1})
(def o31
  {:potion false
   :desc "MOONSHINE: Gives the drinker the Drunken Rage effect while EQUIPPED, allowing them to do an extra +1 DAMAGE at the cost of -1 HEALTH."
   :health -1
   :damage 1})
(def o32
  {:potion false
   :desc "HOLY SHIELD: A shield painted with a yellow rising sun. +3 HEALTH, +1 HOLY DAMAGE"
   :health 3
   :damage 1})
(def o33
  {:potion false
   :desc "TEMPLAR ARMOR: Plate mail emblazoned with a gold rising sun on the breastplate. +8 HEALTH"
   :health 8
   :damage 0})
(def o34
  {:potion false
   :desc "RING OF THE MOON: An onyx ring set with a large moonstone. +1 ICE DAMAGE"
   :health 0
   :damage 1})
(def o35
  {:potion false
   :desc "ARCANE DAGGER: Made of a sheer crystaline material and as cold as ice. +5 ICE DAMAGE"
   :health 0
   :damage 5})
(def h1
  {:potion true 
   :desc "LESSER HEALTH POTION: This potion glimmers with a healthy green glow. After drinking this, you will regain 5 health." 
   :health 5 
   :damage 0})
(def h2
  {:potion true 
   :desc "COMMON HEALTH POTION: This potion shines a bright, sunshine yellow. After drinking this, you will regain 10 health." 
   :health 10
   :damage 0})
(def h3
  {:potion true 
   :desc "GREATER HEALTH POTION: This potion ruby red sparkles cheerfully in the gloom. After drinking this, you will regain 15 health." 
   :health 15 
   :damage 0})

;for place-obj
(def health
  [h1 h2 h3])
(def skele
  [o2 o6 o7 o11 o16 o17])
(def rich
  [o12 o14 o16 o18 h3 h2])
(def cultists
  [o3 o8 o10 o13 h2 h3])
(def forge
  [o4 o9 o12 o14 o16])
(def gen-skele
  [o1 o2 o5 o6 o7 o9 o11 o15 o17 h1 h2])
(def cult-skele
  [o3 o6 o7 o9 o13 o15 o17 h1 h2])
(def gen-cult
  [o1 o2 o3 o5 o9 o11 o13 h2 h3])
(def crypt
  [o19 o20 o21 h2 h3])
(def junk-gen-forge
  [o1 o2 o4 o5 o9 o11 o12 o14 o16 o23 o24 o25 o26 h1 h2])
(def junk
  [o23 o24 o25 o26])
(def tavern 
  [o27 o28 o29 o30 o31])
(def temple
  [o32 o33 o34 o35 h1 h2 h3])
(def gen
  [o1 o2 o5 o9 o11 o24 o26 h1])
;(def cave-key
;  [o22])

(defn place-obj [obj-list amt]
  (vec (take amt (repeatedly #(nth obj-list (rand-int (count obj-list)))))))

(def init-pc-inv
  [])

(def init-pc-eq
  [])

;LEVEL BUILDING___________________________________________________________
;exit-start-coords maps to what location the player should be off after popping of the map corresponding to each location

;this loc is null. has no description and therefore can't be loaded.
(def loc-0-0
  {})

;temple basement
;exiting basement
(def loc-1-3-0
  {:obj (ref (place-obj health 1)) 
   :desc "temple basement"
   :exit-start-coords {:row 1 :col 0}})
(def loc-1-3-1
  {:obj (ref (place-obj cultists 2)) 
   :desc "basement1"})
(def loc-1-3-2
  {:obj (ref (place-obj cult-skele 3)) 
   :desc "basement2"})
(def loc-1-3-3
  {:obj (ref (place-obj temple 1)) 
   :desc "basement5"})

(def basement-1-3 
  [[loc-1-3-0 loc-1-3-1]
   [loc-1-3-2 loc-1-3-3]])

;temple
;exiting temple
(def loc-1-0
  {:obj (ref (place-obj temple 1)) 
   :desc "You're inside the temple." 
   :exit-start-coords {:row 1 :col 2}})
(def loc-1-1
  {:obj (ref (place-obj cultists 1)) 
   :desc "temple 1"})
(def loc-1-2
  {:obj (ref (place-obj health 1)) 
   :desc "temple 2"})
;enter temple basement
(def loc-1-3
  {:obj (ref (place-obj health 0)) 
   :desc "enter basement"
   :enter {:goto basement-1-3
           :start-coords {:row 0 :col 0}}})
(def loc-1-4
  {:obj (ref (place-obj cultists 1)) 
   :desc "temple 4"})
(def loc-1-5
  {:obj (ref (place-obj temple 2)) 
   :desc "temple 5"})
(def loc-1-6
  {:obj (ref (place-obj temple 1)) 
   :desc "temple 6"})
(def loc-1-7
  {:obj (ref (place-obj health 2)) 
   :desc "temple 7"})
(def loc-1-8
  {:obj (ref (place-obj cultists 0)) 
   :desc "temple 8"})
(def loc-1-9
  {:obj (ref (place-obj cultists 0)) 
   :desc "temple 9"})
(def loc-1-10
  {:obj (ref (place-obj temple 1)) 
   :desc "temple 10"})
(def loc-1-11
  {:obj (ref (place-obj cultists 1)) 
   :desc "temple 11"})

(def temple-1 
  [[loc-0-0 loc-1-0  loc-1-1  loc-0-0]
   [loc-1-3 loc-1-4  loc-1-5  loc-1-6]
   [loc-0-0 loc-1-8  loc-1-9  loc-0-0]
   [loc-0-0 loc-1-10 loc-1-11 loc-0-0]])

;mausoleum
;exiting mausoleum
(def loc-2-7-0
  {:obj (ref (place-obj crypt 1)) 
   :desc "in Mausoleum" 
   :exit-start-coords {:row 0 :col 1}})
(def loc-2-7-1
  {:obj (ref (place-obj skele 0)) 
   :desc "mausoleum 1"})
(def loc-2-7-2
  {:obj (ref (place-obj skele 0)) 
   :desc "mausoleum 2"})
(def loc-2-7-3
  {:obj (ref (place-obj health 1)) 
   :desc "mausoleum 3"})
(def loc-2-7-4
  {:obj (ref (place-obj crypt 2)) 
   :desc "mausoleum 4"})
(def loc-2-7-5
  {:obj (ref (place-obj health 1)) 
   :desc "mausoleum 5"})
(def loc-2-7-6
  {:obj (ref (place-obj crypt 2)) 
   :desc "mausoleum 6"})
(def loc-2-7-7
  {:obj (ref (place-obj crypt 1)) 
   :desc "mausoleum 7"})

;mausoleum
(def mausoleum-2-7 
  [[loc-0-0   loc-2-7-0 loc-0-0  ]
   [loc-0-0   loc-2-7-1 loc-0-0  ]
   [loc-2-7-2 loc-2-7-3 loc-2-7-4]
   [loc-2-7-5 loc-2-7-6 loc-2-7-7]])

;graveyard
(def loc-2-0
  {:obj (ref (place-obj skele 2)) 
   :desc "graveyard 0"})
;exit graveyard
(def loc-2-1
  {:obj (ref (place-obj skele 2)) 
   :desc "You stand inside the graveyard." 
   :exit-start-coords {:row 1 :col 1}})
(def loc-2-2
  {:obj (ref (place-obj skele 1)) 
   :desc "graveyard 2"})
(def loc-2-3
  {:obj (ref (place-obj skele 0)) 
   :desc "graveyard 3"})
(def loc-2-4
  {:obj (ref (place-obj skele 2)) 
   :desc "graveyard 4"})
(def loc-2-5
  {:obj (ref (place-obj skele 1)) 
   :desc "graveyard 5"})
(def loc-2-6
  {:obj (ref (place-obj skele 0)) 
   :desc "graveyard 6"})
;entering mausoleum
(def loc-2-7
  {:obj (ref (place-obj skele 0)) 
   :desc "enter mausoleum"
   :enter {:goto mausoleum-2-7 
           :start-coords {:row 0 :col 1}}})
(def loc-2-8
  {:obj (ref (place-obj skele 1)) 
   :desc "graveyard 8"})

(def graveyard-2 
  [[loc-2-6 loc-2-7 loc-2-8]
   [loc-2-3 loc-2-4 loc-2-5]
   [loc-2-0 loc-2-1 loc-2-2]])

;store
(def loc-3-0
  {:obj (ref (place-obj gen 2)) 
   :desc "store 0"})
(def loc-3-1
  {:obj (ref (place-obj gen-cult 3)) 
   :desc "store 1"})
(def loc-3-2
  {:obj (ref (place-obj gen-cult 1)) 
   :desc "store 2"})
(def loc-3-3
  {:obj (ref (place-obj gen-cult 2)) 
   :desc "store 3"})
(def loc-3-4
  {:obj (ref (place-obj junk 1)) 
   :desc "store 4"})
(def loc-3-5
  {:obj (ref (place-obj junk-gen-forge 3)) 
   :desc "store 5"})
(def loc-3-6
  {:obj (ref (place-obj gen 0)) 
   :desc "store 6"})
;enter store
(def loc-3-7
  {:obj (ref (place-obj gen 5)) 
   :desc "You're inside the store." 
   :exit-start-coords {:row 0 :col 4}})
(def loc-3-8
  {:obj (ref (place-obj gen-cult 3)) 
   :desc "store 8"})

(def store-3 
  [[loc-3-0 loc-3-1 loc-3-2]
   [loc-3-3 loc-3-4 loc-3-5]
   [loc-3-6 loc-3-7 loc-3-8]])

;mansion
(def loc-4-0
  {:obj (ref (place-obj gen 0))
   :desc "mansion 0"})
(def loc-4-1
  {:obj (ref (place-obj rich 1))
   :desc "mansion 1"})
(def loc-4-2
  {:obj (ref (place-obj health 3))
   :desc "mansion 2"})
(def loc-4-3
  {:obj (ref (place-obj rich 0))
   :desc "mansion 3"})
(def loc-4-4
  {:obj (ref (place-obj rich 2))
   :desc "mansion 4"})
;enter mansion
(def loc-4-5
  {:obj (ref (place-obj health 1))
   :desc "you're in the mansion"
   :exit-start-coords {:row 2 :col 2}})
(def loc-4-6
  {:obj (ref (place-obj gen 0))
   :desc "mansion 6"})
(def loc-4-7
  {:obj (ref (place-obj health 2))
   :desc "mansion 7"})
(def loc-4-8
  {:obj (ref (place-obj gen 0))
   :desc "mansion 8"})
(def loc-4-9
  {:obj (ref (place-obj rich 3))
   :desc "mansion 9"})

(def mansion-4 
  [[loc-0-0 loc-4-0 loc-4-1]
   [loc-4-2 loc-4-3 loc-4-4]
   [loc-4-5 loc-4-6 loc-4-7]
   [loc-0-0 loc-4-8 loc-4-9]])

;house
(def loc-5-0
  {:obj (ref (place-obj junk-gen-forge 2))
   :desc "house 0"})
(def loc-5-1
  {:obj (ref (place-obj gen 2))
   :desc "house 1"})
(def loc-5-2
  {:obj (ref (place-obj rich 0))
   :desc "house 2"})
(def loc-5-3
  {:obj (ref (place-obj health 1))
   :desc "house 3"})
(def loc-5-4
  {:obj (ref (place-obj gen 0))
   :desc "house 4"})
(def loc-5-5
  {:obj (ref (place-obj rich 1))
   :desc "house 5"})
(def loc-5-6
  {:obj (ref (place-obj health 1))
   :desc "you're in the house"
   :exit-start-coords {:row 3 :col 2}})

(def house-5 
  [[loc-5-0 loc-5-1 loc-5-2 loc-0-0]
   [loc-5-3 loc-5-4 loc-5-5 loc-5-6]])

;ruins
(def loc-6-0
  {:obj (ref (place-obj junk-gen-forge 2)) 
   :desc "ruins 0"})
(def loc-6-1
  {:obj (ref (place-obj junk-gen-forge 3)) 
   :desc "ruins 1"})
(def loc-6-2
  {:obj (ref (place-obj junk-gen-forge 1)) 
   :desc "ruins 2"})
;enter ruins
(def loc-6-3
  {:obj (ref (place-obj junk-gen-forge 2)) 
   :desc "You're inside the ruins." 
   :exit-start-coords {:row 3 :col 4}})
(def loc-6-4
  {:obj (ref (place-obj junk-gen-forge 0)) 
   :desc "ruins 4"})
(def loc-6-5
  {:obj (ref (place-obj junk-gen-forge 0)) 
   :desc "ruins 5"})
(def loc-6-6
  {:obj (ref (place-obj junk-gen-forge 4)) 
   :desc "ruins 6"})
(def loc-6-7
  {:obj (ref (place-obj junk-gen-forge 2)) 
   :desc "ruins 7"})
(def loc-6-8
  {:obj (ref (place-obj junk-gen-forge 3)) 
   :desc "ruins 8"})

(def ruins-6 
  [[loc-6-0 loc-6-1 loc-6-2]
   [loc-6-3 loc-6-4 loc-6-5]
   [loc-6-6 loc-6-7 loc-6-8]])

;cave
(def loc-13-0
  {:obj (ref (place-obj skele 1)) 
   :desc "You're inside the cave." 
   :exit-start-coords {:row 3 :col 0}})
(def loc-13-1
  {:obj (ref (place-obj cult-skele 2)) 
   :desc "cave 1"})
(def loc-13-2
  {:obj (ref (place-obj skele 1)) 
   :desc "cave 2"})
(def loc-13-3
  {:obj (ref (place-obj cult-skele 2)) 
   :desc "cave 3"})
(def loc-13-4
  {:obj (ref (place-obj skele 0)) 
   :desc "cave 4"})
(def loc-13-5
  {:obj (ref (place-obj cult-skele 0)) 
   :desc "cave 5"})
(def loc-13-6
  {:obj (ref (place-obj cult-skele 0)) 
   :desc "cave 6"})
(def loc-13-7
  {:obj (ref (place-obj cult-skele 2)) 
   :desc "cave 7"})
(def loc-13-8
  {:obj (ref (place-obj skele 1)) 
   :desc "cave 8"})
(def loc-13-9
  {:obj (ref (place-obj cult-skele 0)) 
   :desc "cave 9"})
(def loc-13-10
  {:obj (ref (place-obj cult-skele 0)) 
   :desc "cave 10"})
(def loc-13-11
  {:obj (ref (place-obj cult-skele 3)) 
   :desc "cave 11"})
(def loc-13-12
  {:obj (ref (place-obj cult-skele 2)) 
   :desc "cave 12"})
(def loc-13-13
  {:obj (ref (place-obj skele 1)) 
   :desc "cave 13"})
(def loc-13-14
  {:obj (ref (place-obj cult-skele 0)) 
   :desc "cave 14"})
(def loc-13-15
  {:obj (ref (place-obj cult-skele 0)) 
   :desc "cave 15"})
(def loc-13-16
  {:obj (ref (place-obj cult-skele 3)) 
   :desc "cave 16"})

(def cave-13 
  [[loc-0-0  loc-0-0  loc-13-9 loc-13-10 loc-13-11 loc-13-12 loc-13-13 loc-13-14]
   [loc-0-0  loc-0-0  loc-13-6 loc-13-7  loc-13-8  loc-0-0   loc-13-15 loc-13-16]
   [loc-0-0  loc-13-2 loc-13-3 loc-13-4  loc-13-5  loc-0-0   loc-0-0   loc-0-0  ]
   [loc-13-0 loc-13-1 loc-0-0  loc-0-0   loc-0-0   loc-0-0   loc-0-0   loc-0-0  ]])

;tavern
(def loc-18-0
  {:obj (ref (place-obj tavern 2)) 
   :desc "tavern 0"})
(def loc-18-1
  {:obj (ref (place-obj gen 1)) 
   :desc "tavern 1"})
(def loc-18-2
  {:obj (ref (place-obj junk 2)) 
   :desc "tavern 2"})
(def loc-18-3
  {:obj (ref (place-obj gen 0)) 
   :desc "tavern 3"})
(def loc-18-4
  {:obj (ref (place-obj gen 0)) 
   :desc "tavern 4"})
(def loc-18-5
  {:obj (ref (place-obj tavern 1)) 
   :desc "tavern 5"})
(def loc-18-6
  {:obj (ref (place-obj gen 2)) 
   :desc "tavern 6"})
(def loc-18-7
  {:obj (ref (place-obj tavern 2)) 
   :desc "tavern 7"})
(def loc-18-8
  {:obj (ref (place-obj gen 0)) 
   :desc "tavern 8"})
(def loc-18-9
  {:obj (ref (place-obj junk-gen-forge 3)) 
   :desc "tavern 9"})
(def loc-18-10
  {:obj (ref (place-obj tavern 1)) 
   :desc "you are in the tavern"
   :exit-start-coords {:row 1 :col 4}})
(def loc-18-11
  {:obj (ref (place-obj tavern 4)) 
   :desc "tavern 10"})

(def tavern-18 
  [[loc-18-0 loc-18-1 loc-18-2]
   [loc-18-3 loc-18-4 loc-18-5]
   [loc-18-6 loc-18-7 loc-18-8]
   [loc-18-9 loc-18-10 loc-18-11]])

;maps of location data, accessed by keywords
;:enter means there is a matrix map that can be loaded from that location.
;:map is the matrix that should be loaded (pushed onto the stack) 
;:start-coords is the location the player should start at in the new map

(def loc-0 
  {:obj (ref (place-obj junk 2)) 
   :desc "A cart with a broken wheel lies abandoned on the side of the road." 
   :enemy ""})

(def loc-1
  {:obj (ref (place-obj health 1)) 
   :desc "An unfamiliar temple adorned with snarling gargoyles rises above you. " 
   :enemy "" 
   :enter {:goto temple-1 
           :start-coords {:row 0 :col 1}}})

(def loc-2
  {:obj (ref (place-obj skele 0)) 
   :desc "Rickety gates surround an old graveyard. The air smells of freshly-dug earth." 
   :enemy "" 
   :enter {:goto graveyard-2
           :start-coords {:row 2 :col 1}}})

(def loc-3
  {:obj (ref (place-obj junk 2)) 
   :desc "A decrepit general store stands before you, barrels of rotting goods scattered about the area." 
   :enemy "" 
   :enter {:goto store-3 
           :start-coords {:row 2 :col 1}}})

(def loc-4
  {:obj (ref (place-obj health 1)) 
   :desc "A grand, old mansion sits in what was once a garden. A single candle flickers behind a stained-glass window." 
   :enemy ""
   :enter {:goto mansion-4 
           :start-coords {:row 2 :col 0}}}) 

(def loc-5
  {:obj (ref (place-obj gen 2)) 
   :desc "This cottage is exceptionally well built, and in better shape than most of the surrounding buildings. There still might be something useful inside." 
   :enemy ""
   :enter {:goto house-5 
           :start-coords {:row 1 :col 3}}})

(def loc-6 
  {:obj (ref (place-obj gen 0)) 
   :desc "A ruined building lies in an overgrown field. It looks like it burned down long ago, but a glint of metal catches your eye." 
   :enemy "" 
   :enter {:goto ruins-6 
           :start-coords {:row 1 :col 0}}})

(def loc-7
  {:obj (ref (place-obj gen 1)) 
   :desc "You squeeze into a narrow alleyway between two buildings. A rat scurries away from you." 
   :enemy "" })

(def loc-8 
  {:obj (ref (place-obj health 2)) 
   :desc "You hide among a small copse of gnarled pine trees, the hanging moss shielding you from view." 
   :enemy ""})

(def loc-9
  {:obj (ref (place-obj junk-gen-forge 1)) 
   :desc "Broken, cracked cobblestones and smashed stalls form a picture of a town square bustling with ghosts." 
   :enemy ""})

(def loc-10
  {:obj (ref (place-obj gen 0)) 
   :desc "A half-rotted well lies in the middle of the market square. You drop a small pebble and hear a small 'plink' of it hitting water several seconds later." 
   :enemy ""})

(def loc-11
  {:obj (ref (place-obj junk-gen-forge 2)) 
   :desc "What was once brightly-colored cloth and lanterns lie smashed around a market stall." 
   :enemy ""})

(def loc-12
  {:obj (ref (place-obj forge 2)) 
   :desc "You find yourself in front of a forge, long cold." 
   :enemy ""})

(def loc-13
  {:obj (ref (place-obj cult-skele 0)) 
   :desc "Mossy rocks jut out of the marsh. Cold air whistles past you, coming from a small opening in the rock just big enough to crawl through." 
   :enemy ""
   :enter {:goto cave-13 
           :start-coords {:row 3 :col 0}}})

(def loc-14
  {:obj (ref (place-obj cult-skele 2)) 
   :desc "A small shrine of carved, blackened bones has been constructed on top of a pile of smooth stones." 
   :enemy ""})

(def loc-15
  {:obj (ref (place-obj cultists 2)) 
   :desc "Low chanting drifts over on the wind. In the distance, a small camp surrounds a fire of unearthly purple flames. You crouch lower in the weeds, hoping to not be seen." 
   :enemy "" })

(def loc-16
  {:obj (ref (place-obj skele 2)) 
   :desc "A sticky smear of blood leads to a pile of burnt corpses that once were the people who lived here. You feel your stomach turn over and look away."
   :enemy ""})

(def loc-17
  {:obj (ref (place-obj gen 2)) 
   :desc "This grain store has been painted with a swirling symbol. You stare at it, trying to make sense of the squiggling lines, and feel your mind bending." 
   :enemy ""})

(def loc-18
  {:obj (ref (place-obj gen 1)) 
   :desc "You stand in front of a tavern, its welcoming sign hanging askew. Casks of beer lie smashed on the road." 
   :enemy ""
   :enter {:goto tavern-18 
           :start-coords {:row 3 :col 1}}})

(def loc-19
  {:obj (ref (place-obj gen-skele 4)) 
   :desc "A lone chicken picks for worms in a field littered with traveling packs and household goods, all torn open and looted."
   :enemy ""})

;level map is a matrix, each cell corresponds to a location data map
(def level-1-map
  [[loc-15 loc-8  loc-0 loc-11 loc-3]
   [loc-14 loc-2  loc-1 loc-9  loc-18]
   [loc-16 loc-19 loc-4 loc-10 loc-12]
   [loc-13 loc-17 loc-5 loc-7  loc-6]])

;PC LOCATION_________________________________________________________________________________

;initial player location in the first map, needed for loop recur
(def init-pc-loc
  {:row (rand-int 4) :col (rand-int 3)})   

;it's a list, acts as a stack of maps
;init needed for loop recur
(def init-map-stack
  (list level-1-map))

(defn get-pc-loc [pc-loc map-stack]
  "pulls location data using first map (current) in stack
  using the row and column of player's location"
  (get-in (first map-stack) [(:row pc-loc) (:col pc-loc)]))

(defn print-loc-desc [pc-loc map-stack]
  "prints location description, using get-pc-loc"
  (println (:desc (get-pc-loc pc-loc map-stack))))

;FIXME placeholder
(defn print-loc-enemy [pc-loc map-stack]
  "prints enemy descriptions"
  (if (:enemy (get-pc-loc pc-loc map-stack))
    (println (:enemy (get-pc-loc pc-loc map-stack)))))

;PC HEALTH AND DAMAGE________________________________________________________________________

(def init-max-health
  20)

(def init-pc-health
  20)

(defn print-pc-health [pc-health max-health]
  (print "             Your current health is ") 
  (print pc-health)
  (print " out of ")
  (print max-health))

(def init-pc-damage
  5)

(defn print-pc-damage [pc-damage]
  (println) 
  (print "             Your current damage output is ") 
  (print pc-damage) 
  (println) 
  (println))

;OBJECT OPTIONS______________________________________________________________________________

(defn get-obj [pc-loc map-stack]
  (deref 
    (get (get-pc-loc pc-loc map-stack) :obj)))

(defn get-obj-ref [pc-loc map-stack]
  (get (get-pc-loc pc-loc map-stack) :obj))

;adding items to inventory____________________________________________________

(defn add-obj-to-inv [pc-loc map-stack pc-inv int-input]
  (println)
  (print "That item has been added to your inventory.")
  (println)
  (print-debug pc-inv)
  (vec (conj pc-inv (nth (get-obj pc-loc map-stack) (dec int-input)))))

(defn remove-obj-from-loc [pc-loc map-stack int-input]
  (let [pre-obj (subvec (get-obj pc-loc map-stack) 0 (dec int-input))
        post-obj  (subvec (get-obj pc-loc map-stack) int-input)]
    (dosync
      (ref-set (get-obj-ref pc-loc map-stack) (vec (concat pre-obj post-obj))))))

(defn add-to-inv [pc-loc map-stack pc-inv]
  (if (= [] (get-obj pc-loc map-stack))
    (do 
      (println) 
      (println "There is nothing here to add.") 
      (println) 
      (pause) 
      (print-debug pc-inv) 
      pc-inv)
    (do
      (println) 
      (println "What would you like to add? Enter '1' for the first item listed, '2' for the second item listed, and so on.") 
      (println)
      (let [input (read-line)]
        (let [int-input (Integer/parseInt input)]
          (let [new-pc-inv (add-obj-to-inv pc-loc map-stack pc-inv int-input)]
            (print-debug new-pc-inv)
            (remove-obj-from-loc pc-loc map-stack int-input)
            (print-debug new-pc-inv)
            new-pc-inv))))))

;print object options_____________________________________________________

(defn print-obj-item [item]
  (print "                    ")
  (print item)
  (println))

(defn print-obj [pc-loc map-stack]
  (if (= [] (get-obj pc-loc map-stack))
    (println "                    You look around, but nothing catches your eye.")
    (doseq [item (map :desc (get-obj pc-loc map-stack))]
      (print-obj-item item))))

(defn open-potions []
  (with-open [rdr (io/reader "resources/potions.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-objects []
  (with-open [rdr (io/reader "resources/objects.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn print-obj-commands [pc-loc map-stack]
  (clear-screen)
  (open-objects)
  (print-obj pc-loc map-stack)
  (open-potions))

(defn obj-control [pc-loc map-stack pc-inv]
  "prints object descriptions that are in current location"
  (print-obj-commands pc-loc map-stack)
  (print-debug pc-inv)
  (let [input (read-line)]
    (cond
      (= input "x") pc-inv
      (= input "a") (let [new-pc-inv (add-to-inv pc-loc map-stack pc-inv)]
                     (obj-control pc-loc map-stack new-pc-inv)) 
      :else (do 
              (print-debug pc-inv) 
              (obj-control pc-loc map-stack pc-inv)))))

;INVENTORY________________________________________________________________________

;removing items from inventory_______________________________________________

(defn add-item-to-loc [pc-loc map-stack pc-inv int-input]
  (print-debug pc-inv)
  (dosync
    (alter (get-obj-ref pc-loc map-stack) conj (nth pc-inv (dec int-input)))))

(defn remove-item-from-inv [pc-loc map-stack pc-inv]
  "removes a user-defined item from inventory"
  (if (= pc-inv [])
    (do
      (println "You have nothing to drop.")
      (pause)
      pc-inv)
    (do
      (println)
      (println "What item do you want to drop? Enter '1' for the first item listed, '2' for the second item listed, and so on.")
      (let [input (read-line)]
        (let [int-input (Integer/parseInt input)]
          (do
            (print-debug pc-inv)
            (add-item-to-loc pc-loc map-stack pc-inv int-input)
            (let [pre-inv (subvec pc-inv 0 (dec int-input))
                  post-inv (subvec pc-inv int-input)]
              (print-debug pre-inv)
              (print-debug post-inv)
              (vec (concat pre-inv post-inv)))))))))

;equip and unequip___________________________________________________

(defn print-eq [item]
  (print "                    ")
  (print (:desc item))
  (println))

(defn print-pc-eq [pc-eq]
  (if (= pc-eq [])
    (println "             You have no items equipped.")
    (doseq [item pc-eq]
      (print-eq item))))

(defn add-damage [pc-inv pc-damage int-input]
  (+ pc-damage (:damage (nth pc-inv (dec int-input)))))

(defn add-health [pc-inv health int-input]
  (+ health (:health (nth pc-inv (dec int-input)))))

(defn equip-item [pc-inv pc-eq pc-health pc-damage max-health]
  (println)
  (if (= [] pc-inv)
    (do 
      (println "There is nothing to equip.") 
      (pause)
      [pc-eq
       pc-health
       pc-damage
       max-health])
    (do
      (println "What item do you want to equip? Enter '1' for the first item listed, '2' for the second item listed, and so in.")
      (println) 
      (let [input (read-line)]
        (let [int-input (Integer/parseInt input)]
          [(vec (conj pc-eq (nth pc-inv (dec int-input)))) 
           (add-health pc-inv pc-health int-input) 
           (add-damage pc-inv pc-damage int-input)
           (add-health pc-inv max-health int-input)])))))

(defn sub-damage [pc-eq pc-damage int-input]
  (- pc-damage (:damage (nth pc-eq (dec int-input)))))

(defn sub-health [pc-eq health int-input]
  (- health (:health (nth pc-eq (dec int-input)))))

(defn unequip-item [pc-eq pc-health pc-damage max-health]
  "removes a user-defined item from inventory"
  (if (= [] pc-eq)
    (do 
      (println "There is nothing to unequip.") 
      (pause)
      [pc-eq
       pc-health
       pc-damage
       max-health])
    (do
      (println)
      (println "What item do you want to unequip? Enter'1' for the first item listed, '2' for the second item listed, and so on.")
      (println)
      (let [input (read-line)]
        (let [int-input (Integer/parseInt input)]
          (let [pre-eq (subvec pc-eq 0 (dec int-input))
                post-eq (subvec pc-eq int-input)]
            [(vec (concat pre-eq post-eq))
             (sub-health pc-eq pc-health int-input) 
             (sub-damage pc-eq pc-damage int-input)
             (sub-health pc-eq max-health int-input)]))))))

(defn potion-add-health [pc-inv pc-health int-input max-health]
  (let [new-pc-health (+ pc-health (:health (nth pc-inv (dec int-input))))]
    (if (> new-pc-health max-health)
      max-health
      new-pc-health)))

(defn drink-hp [pc-loc map-stack pc-inv pc-health max-health]
  (println)
  (println "What item do you want to drink? Enter '1' for the first item listed, '2' for the second item listed, and so on.")
  (let [input (read-line)]
    (let [int-input (Integer/parseInt input)]
      (if (not (:potion (nth pc-inv (dec int-input))))
        (do
          (println)
          (println "You don't think you can drink that.")
          (println)
          (pause)
          [pc-inv 
           pc-health])
      (do
        (println)
        (println "You drink the potion, and immediately feel a little bit better.")
        (pause)
        (print-debug pc-inv)
        (let [pre-inv (subvec pc-inv 0 (dec int-input))
              post-inv (subvec pc-inv int-input)]
          (print-debug pre-inv)
          (print-debug post-inv)
          [(vec (concat pre-inv post-inv)) 
           (potion-add-health pc-inv pc-health int-input max-health)]))))))

;print inventory____________________________________________________

(defn print-item [item]
  (print "                    ")
  (print (:desc item))
  (println))

(defn print-pc-inv [pc-inv pc-eq]
  (if (= pc-inv [])
    (println "             Your inventory is currently empty.")
    (do
      (print-debug pc-inv)
      (doseq [item  pc-inv]
      (print-item item)))))

(defn open-inv-menu []
  "opens inv menu"
  (with-open [rdr (io/reader "resources/inv-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-sword []
  "opens sword"
  (with-open [rdr (io/reader "resources/sword.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn print-inv-commands [pc-inv pc-eq pc-health pc-damage max-health]
  (clear-screen)
  (open-inv-menu)
  (open-sword)
  (print-pc-health pc-health max-health)
  (print-pc-damage pc-damage)
  (println "             Your inventory contains the following items:")
  (print-pc-inv pc-inv pc-eq)
  (println)
  (println "             You have equipped the following items:")
  (print-pc-eq pc-eq)
  (println))

(defn inv-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health]
  (print-inv-commands pc-inv pc-eq pc-health pc-damage max-health)
  (let [input (read-line)]
    (cond
      (= input "x") [pc-inv 
                     pc-eq 
                     pc-health 
                     pc-damage
                     max-health]
      (= input "r") (let [new-pc-inv (remove-item-from-inv pc-loc map-stack pc-inv)]
                     (inv-control pc-loc map-stack new-pc-inv pc-eq pc-health pc-damage max-health))
      (= input "e") (let [[new-pc-eq new-pc-health new-pc-damage new-max-health] 
                          (equip-item pc-inv pc-eq pc-health pc-damage max-health)]
                      (inv-control pc-loc map-stack pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health))
      (= input "u") (let [[new-pc-eq new-pc-health new-pc-damage new-max-health] 
                          (unequip-item pc-eq pc-health pc-damage max-health)]
                      (inv-control pc-loc map-stack pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health))
      (= input "d") (let [[new-pc-inv new-pc-health] 
                          (drink-hp pc-loc map-stack pc-inv pc-health max-health)]
                      [new-pc-inv
                       pc-eq
                       new-pc-health
                       pc-damage 
                       max-health])
      :else (inv-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health))))

;MENU AND USER OPTIONS________________________________________________________________________________________

;moving normally________________________________________

(defn parse-user-input [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health]
  "called from check-move. Parses user input.
  movement: dec/inc returns a new value that is one greater than the value passed in.
    assoc returns a new map of pc coordinates that is updated according to inc/dec
  objects: objects, loot, and inventory are placeholders
 'q' quits program (system call)"
  (let [input (read-line)]
    (cond
      (= input "n") [(assoc pc-loc :row (dec (:row pc-loc))) 
                       pc-inv 
                       pc-eq 
                       pc-health 
                       pc-damage
                       max-health]
      (= input "s")  [(assoc pc-loc :row (inc (:row pc-loc))) 
                       pc-inv 
                       pc-eq 
                       pc-health 
                       pc-damage
                       max-health]
      (= input "e")  [(assoc pc-loc :col (inc (:col pc-loc))) 
                       pc-inv 
                       pc-eq 
                       pc-health 
                       pc-damage
                       max-health] 
      (= input "w")  [(assoc pc-loc :col (dec (:col pc-loc))) 
                       pc-inv 
                       pc-eq 
                       pc-health 
                       pc-damage
                       max-health] 
      (= input "m") (do 
                      (open-main-menu) 
                      (println) 
                      (pause) 
                      [pc-loc 
                       pc-inv 
                       pc-eq 
                       pc-health 
                       pc-damage
                       max-health]) 
      (= input "l") (do 
                      (println "There are no enemies yet, so there is no loot.") 
                      (println) 
                      (pause) 
                      [pc-loc 
                       pc-inv 
                       pc-eq 
                       pc-health 
                       pc-damage
                       max-health]) 
      (= input "o") (let [new-pc-inv (obj-control pc-loc map-stack pc-inv)]
                      [pc-loc 
                       new-pc-inv 
                       pc-eq 
                       pc-health 
                       pc-damage 
                       max-health])
      (= input "i") (let [[new-pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health] 
                          (inv-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health)]
                      [pc-loc 
                       new-pc-inv 
                       new-pc-eq 
                       new-pc-health 
                       new-pc-damage
                       new-max-health])
      (= input "q") (System/exit 0) 
      :else (do 
              (println "That's not a valid choice.") 
              (pause) 
              [pc-loc 
               pc-inv 
               pc-eq 
               pc-health 
               pc-damage max-health]))))

(defn print-user-menu []
  (with-open [rdr (io/reader "resources/user-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn check-move [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health]
  "takes pc-loc player coordinates and the map stack
  prints a menu of options for the user to see
  then calls parse-user-input
    parse-user-input returns an updated set of PC coordinates
  If a description can be pulled from the new pc-loc (from location data stored in map)
    the new move is valid and the new pc-loc is returned (along with the unchanged map stack). 
  If not, it's not a valid move, and the old pc-loc is returned (along with the unchanged map stack)"
  (print-user-menu)
  (if (< pc-health max-health)
    (do
     (println)
      (print "             You're injured! Your current health is ")
      (print pc-health)
      (print " out of ")
      (print max-health)
      (print ".")
      (println)))
  (let [[new-loc new-pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health] 
        (parse-user-input pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health)]
    (print-debug new-pc-inv)
    (if (:desc (get-pc-loc new-loc map-stack))
      (do
        [new-loc 
         map-stack 
         new-pc-inv 
         new-pc-eq 
         new-pc-health 
         new-pc-damage
         new-max-health])
      (do
        (if (not= (first map-stack) level-1-map)
          (println "There is a wall in that direction. You move back to where you were.")
          (println "You find yourself at the edge of the village and turn back to where you started."))
        [pc-loc 
         map-stack 
         new-pc-inv 
         new-pc-eq 
         new-pc-health 
         new-pc-damage
         new-max-health]))))

;entering______________________________________________

(defn check-push-map [pc-loc map-stack]
  "called from push-control, takes in pc-loc and map-stack
  y: player wants to enter new location
     the new player coordinates are the start coordinates stored in the old locations's :enter keyword
     the new map (in the old locations's :enter keyword) is pushed onto the map stack
     true is returned (because map change is true)
  n/else: old player location, old map, and "false" (because map change is false) is returned"
  (let [input (read-line)]
      (cond
        ;new map is pushed on, so change is "true"
        (= input "y") [(:start-coords (:enter (get-pc-loc pc-loc map-stack)))
                       (conj map-stack (:goto (:enter (get-pc-loc pc-loc map-stack))))
                       true]
        ;old map is returned, so change is "false"
        (= input "x") [pc-loc 
                       map-stack 
                       false]
        :else (do 
                (println "That was not a valid choice.") 
                (pause) 
                [pc-loc 
                 map-stack 
                 false]))))

(defn print-enter [pc-loc map-stack]
  (print "
            ________________________
          =(____   ___      __     _)=
            |                      |
            | You can enter a new  |
            |    area from here.   | 
            |                      |
            |   Type y to enter    |
            |  or x to stay here.  |
            |__    ___   __    ___ |
          =(________________________)=")
  (println))

(defn push-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health]
  "called from display-menu, takes in pc-loc and map-stack
  calls check-push-map, which returns an updated pc-coordinate location and map stack, and either true or false
  if true, new map has been loaded and the new pc-coordinates and map stack are returned to loop recur
  if false, calls noromal movement functions (check-move)"
  (print-enter pc-loc map-stack)
  (let [[new-loc new-map-stack true-false] 
        (check-push-map pc-loc map-stack)]
    ;will be true if a new map was pushed on 
    (if true-false
      ;load new map
      [new-loc 
       new-map-stack 
       pc-inv 
       pc-eq 
       pc-health 
       pc-damage
       max-health]
      ;move normally
      (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health))))

;exiting_____________________________________________

(defn check-pop-map [pc-loc map-stack]
  "called from pop-control, takes pc-loc and map-stack
  if y: returns coordinates player should be at in old map, and pops off a map from the stack 
        (as newest map is the first in the stack, returns stack-first element)
        returns true as a map change is "true"
  if n: returns old pc-location, old map-stack, and "false""
  (let [input (read-line)]
    (cond
      ;change is made (popping off a map), so "true"
      (= input "y") [(:exit-start-coords (get-pc-loc pc-loc map-stack)) 
                     (rest map-stack) 
                     true]
      ;no change is made to map, so "false"
      (= input "x") [pc-loc 
                     map-stack 
                     false]
      :else (do 
              (println "That was not a valid choice. What else would you like to do at this location?") 
              (pause) 
              [pc-loc 
               map-stack 
               false]))))

(defn print-exit []
  (print  " 
            ________________________
          =(____   ___      __     _)=
            |                      |
            |  You can exit this   |
            | this area from here. |
            |                      |
            |    Type y to exit    |
            |    or x to stay.     |
            |__    ___   __    ___ |
          =(________________________)=")
  (println))

(defn pop-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health]
  "called from display-menu, takes in pc-loc and map-stack
  called from check-pop-map, which returns updated pc-loc, map stack, and either true/false
  if true, returns updated pc-loc and map-stack to loop-recur
  if false, calls normal movement functions (check-move)"
  (print-exit)
  (let [[new-loc new-map-stack true-false] 
        (check-pop-map pc-loc map-stack)]
    ;will be true if a map was popped off
    (if true-false
      ;load new map
      [new-loc 
       new-map-stack 
       pc-inv 
       pc-eq 
       pc-health 
       pc-damage
       max-health]
      ;move normally
      (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health)))) 

;getting and parsing user input_____________________________
(defn display-menu [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health]
  "checks to see if a place can be entered or exited from current player location.
  if yes, gives player the option to enter/exit
  if no, just displays normal movement functions (check-move)"
  (cond
    (:enter (get-pc-loc pc-loc map-stack)) (push-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health)
    (:exit-start-coords (get-pc-loc pc-loc map-stack)) (pop-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health)
    :else (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health)))

;MAIN_____________________________________________________________________

(defn -main []
  ;these are function calls
  (open-title)
  (open-intro)
  (open-main-menu)
  (loop [pc-loc init-pc-loc map-stack init-map-stack pc-inv init-pc-inv pc-eq init-pc-eq pc-health init-pc-health pc-damage init-pc-damage max-health init-max-health]
      (do (clear-screen)
          ;(function arguments)
          (print-loc-desc pc-loc map-stack)
          (print-loc-enemy pc-loc map-stack) 
          (let [[new-loc map-stack new-pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health] 
                (display-menu pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health)]
            (recur 
              new-loc map-stack new-pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health)))))
