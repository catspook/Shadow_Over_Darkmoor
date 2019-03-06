; The Shadow Over Darkmoor
;   a text-based game for the terminal
; copywright (c) CMR, Jan 01 2019

(ns darkmoor.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s]))

(def debug
  false)

(def debug-hit-list
  false)

(defn print-debug [thing]
  "debug statement for objects, inventory, equipped, and unequipped lists"
  (if debug
  (println thing)))

(defn print-debug-hit-list [func, hit-list]
  "debug statement for hit-list (list of places with killed enemies)"
  (if debug-hit-list
    (do
      (println)
      (print func)
      (print " ")
      (print hit-list)
      (println))))

;OPENING SEQUENCE__________________________________________________________

(defn clear-screen []
  "clears screen using ANSI escape sequence"
  (print (str (char 27) "[2J"))) 

(defn pause []
  "pauses by asking for user input"
  (println) 
  (println "Press any key to continue.")
  (read-line))

(defn pause-screen3 []
  "pauses thread for 3 seconds"
  (Thread/sleep 3000))

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
  (pause-screen3)
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
  (pause)
  (clear-screen)
  (with-open [rdr (io/reader "resources/intro-cont.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
  (pause)
  (clear-screen))

;ENEMIES________________________________________________________________________________
;lists of enemy types, coded into locations later on

(def rat
  {:rat true
   :health 5
   :damage 3})
(def dagger-cult
  {:cultist true
   :health 24 
   :damage 4})
(def magic-cult
  {:cultist true 
   :health 20
   :damage 5})
(def bow-skele
  {:skeleton true
   :health 15
   :damage 3})
(def axe-skele
  {:skeleton true
   :health 18
   :damage 4})
(def zombie 
  {:zombie true
   :health 20
   :damage 4})
(def ghost
  {:ghost true
   :health 15
   :damage 4})
(def boss
  {:boss true
   :health 35
   :damage 9})
(def minion
  {:minion true
   :health 30
   :damage 7})

;places w/ a monster you've already killed get added here
(def init-hit-list
  #{})

;OBJECT DATA____________________________________________________________________________
;a mix of general objects and specialized objects for player to find at locations

(def o1
  {:desc "WOODCUTTER'S AXE: Blunted and notched. +3 SLASHING DAMAGE" 
   :health 0 
   :damage 3})
(def o2
  {:desc "OLD LEATHER ARMOR: Better than fighting in your traveling clothes. +10 HEALTH" 
   :health 10
   :damage 0})
(def o3
  {:desc "CULTIST'S DAGGER: A strange symbol carved on the blade glows with an unearthly purple light. +5 NECROTIC DAMAGE" 
   :health 0 
   :damage 5})
(def o4
  {:desc "FIRE SHIELD: Flames lick around the edges of this shield, but thankfully the handle is not red-hot. +3 HEALTH, +1 FIRE DAMAGE" 
   :health 3
   :damage 1})
(def o5
  {:desc "BUTCHER'S GLOVES: These stink, but your fingers have never felt so protected. +3 HEALTH" 
   :health 3 
   :damage 0})
(def o6
  {:desc "FEMUR: Makes a good club, but takes both of your hands to carry. +5 BLUNT DAMAGE" 
   :health 0 
   :damage 5})
(def o7
  {:desc "HALF-PLATE ARMOR: Caked in dirt. You're pretty sure that no one living has worn this recently. +15 HEALTH" 
   :health 15 
   :damage 0})
(def o8
  {:desc "ETHERIAL BOOTS: You feel just a bit less present while wearing these. +5 HEALTH" 
   :health 5 
   :damage 0})
(def o9
  {:desc "SHARPENED SYTHE: More of a farmer's weapon than Death's. +3 SLASHING DAMAGE" 
   :health 0 
   :damage 3})
(def o10
  {:desc "SPIKED FLAIL: Screaming faces are engraved on the handle, and the spikes are coated in gore. +7 PIERCING DAMAGE" 
   :health 0 
   :damage 7})
(def o11
  {:desc "POLE-AXE: Literally a hand axe tied to a long stick. +4 SLASHING DAMAGE"
   :health 0
   :damage 4})
(def o12
  {:desc "FINE CHAINMAIL: Finely-made armor consisting of thousands of tiny, silver rings. +20 HEALTH"
   :health 20 
   :damage 0})
(def o13
  {:desc "CULTIST'S ROBE: A thick, wool robe embroidered with enchanted runes that do damage to enemies while protecting the wearer. +10 HEALTH, +3 NECROTIC DAMAGE"
   :health 10 
   :damage 3})
(def o14
  {:desc "FINE SILVER SWORD: A thin blade with a decorative eagle carved around the handle. +6 PIERCING DAMAGE"
   :health 0
   :damage 6})
(def o15
  {:desc "SEVERED ARM: It's just like a club. A squishy, stinking club. +4 NECROTIC DAMAGE"
   :health 0
   :damage 4})
(def o16
  {:desc "WARHAMMER: A large, heavy stone hammer carved with intricate geometric designs. +7 BLUNT DAMAGE"
   :health 0
   :damage 7})
(def o17
  {:desc "RUSTED BOW AND ARROW: A half-rotted bow and a handful of rusted arrows. This weapon is ranged, but requires both hands to use. +2 HEALTH, 5 PIERCING DAMAGE"
   :health 2
   :damage 5})
(def o18
  {:desc "GREAT AXE: Passed down from generation to generation, this once was a prized family heirloom. It's yours now. +6 SLASHING DAMAGE"
   :health 0
   :damage 6})
(def o19
  {:desc "SPIKED SHIELD: Long spikes protrude from the center of this shield. +4 HEALTH, +1 PIERCING DAMAGE"
   :health 4 
   :damage 1})
(def o20
  {:desc "SPIKED GAUNTLETS: Finely made from a previous age, if a bit dusty. +3 HEALTH, +1 PIERCING DAMAGE"
   :health 3 
   :damage 1})
(def o21
  {:desc "SPIKED PAULDRONS: Your neck will be somewhat more protected by these large spikes. Just don't bend your head too far. +3 HEALTH"
   :health 3 
   :damage 0})
(def o22
  {:desc "TORN LETTER: This blood-spattered letter contains instructions to a cultist from the 'MASTER', who is headquartered in the basement of the Temple." 
   :health 0
   :damage 0})
(def o23
  {:desc "BARREL OF DEAD FISH: You don't think you could get within a foot of this without throwing up."
   :health 0
   :damage 0})
(def o24
  {:desc "HEMP ROPE: It's so rotten it snaps as soon as you pick it up."
   :health 0
   :damage 0})
(def o25
  {:desc "BARREL OF ROTTEN GRAIN: Not quite beer yet."
   :health 0
   :damage 0})
(def o26
  {:desc "RUSTED HORSESHOE: You're not a centaur, so you're not sure what you'd do with this." 
   :health 0
   :damage 0})
(def o27
  {:desc "BROKEN BAR STOOL: A little unweildy, but heavy. Getting hit with this would hurt. +6 BLUNT DAMAGE, -2 HEALTH"
   :health -2
   :damage 6})
(def o28
  {:desc "CHAIR LEG: Made of sturdy oak. +4 BLUNT DAMAGE"
   :health 0
   :damage 4})
(def o29
  {:desc "BROKEN WINE BOTTLE: Sharp as glass. +4 PIERCING DAMAGE"
   :health 0
   :damage 4})
(def o30
  {:desc "RUM: Gives the drinker the Sea Legs effect while EQUIPPED, allowing them to evade more attacks (+1 HEALTH), but reducing their aim (-1 DAMAGE)." 
   :health 1
   :damage -1})
(def o31
  {:desc "MOONSHINE: Gives the drinker the Drunken Rage effect while EQUIPPED, allowing them to do an extra +1 DAMAGE at the cost of -1 HEALTH."
   :health -1
   :damage 1})
(def o32
  {:desc "TEMPLAR SHIELD: A shield painted with a yellow rising sun. +3 HEALTH, +2 HOLY DAMAGE"
   :health 3
   :damage 2})
(def o33
  {:desc "TEMPLAR ARMOR: Plate mail emblazoned with a gold rising sun on the breastplate. It has been blessed to provide a bit of extra protection. +22 HEALTH"
   :health 22
   :damage 0})
(def o34
  {:desc "RING OF THE MOON: An onyx ring set with a large moonstone. +2 ICE DAMAGE"
   :health 0
   :damage 2})
(def o35
  {:desc "ARCANE DAGGER: Made of a opalescent crystaline material and as cold as ice. +10 ARCANE DAMAGE"
   :health 0
   :damage 10})
(def o36
  {:desc "HUNTING BOW: Meant for hunting game, but works for people too. +1 HEALTH, +5 PIERCING DAMAGE"
   :health 1
   :damage 5})
(def o37
  {:desc "HUNTING KNIFE: A long knife meant for mercifully ending an animal's life. +5 SLASHING DAMAGE"
   :health 0 
   :damage 5})
(def o38
  {:desc "FINE HUNTING BOW: The better version that the nobility can afford. You can stand farther back from enemies with this bow than with other bows. +2 HEALTH, +6 DAMAGE"
   :health 2
   :damage 6})
(def o39
  {:desc "WAND OF SHADOWS: This twisted piece of wood somehow blasts its opponent with black, necrotic energy. You're not too sure how it works. +6 NECROTIC DAMAGE"
   :health 0
   :damage 6})
(def o40
  {:desc "VAMPIRIC AMULET: A blood-red gem sits in the center of this tarnished silver neclace. During combat, this amulet syphons health from your opponent to you. +2 HEALTH, +2 NECROTIC DAMAGE"
   :health 2
   :damage 2})
(def h1
  {:potion true 
   :desc "LESSER HEALTH POTION: This potion glimmers with a healthy green glow. After drinking this, you will regain 10 health." 
   :health 10 
   :damage 0})
(def h2
  {:potion true 
   :desc "COMMON HEALTH POTION: This potion shines a bright, sunshine yellow. After drinking this, you will regain 15 health." 
   :health 15 
   :damage 0})
(def h3
  {:potion true 
   :desc "GREATER HEALTH POTION: This potion ruby red sparkles cheerfully in the gloom. After drinking this, you will regain 20 health." 
   :health 20 
   :damage 0})

;pre-defined sets for place-obj to pull from
(def health
  [h1 h2 h3])
(def health-loc-8
  [h1 h2 h3 o2 o36 o37])
(def skele
  [o2 o6 o7 o11 o15 o17])
(def rich
  [o4 o12 o14 o16 o18 o34 o35 o38])
(def home
  [o1 o2 o5 o9 o18 o36 o37])
(def cultists
  [o3 o8 o10 o13 o22 o39 o40])
(def forge
  [o4 o12 o14 o16 o18])
(def crypt
  [o19 o20 o21])
(def tavern 
  [o1 o5 o27 o28 o29 o30 o31])
(def temple
  [o14 o18 o32 o33 o34 o35])
(def gen
  [o1 o5 o9 o23 o24 o25 o26])
(def letter
  [o22])

(defn place-obj [obj-list amt]
  "function that pulls certtain number (amt) of a random assorment of objects
  piped in through obj-list, and saves them to a location"
  (vec (take amt (repeatedly #(nth obj-list (rand-int (count obj-list)))))))

;initial inventory list
(def init-pc-inv
  [])

;initial equiped list
(def init-pc-eq
  [])

;LEVEL BUILDING___________________________________________________________
;exit-start-coords maps to what location the player should be off after popping of the map corresponding to each location

;nil loc. has no description and therefore can't be loaded.
(def loc-0-0
  {})

;temple basement
;exiting basement
(def loc-1-3-0
  {:obj (ref (place-obj temple 2)) 
   :desc "The Templar used this underground chamber for holy rituals. A small wooden table lies to the side of the rope ladder leading upwards, covered in blood-spattered candles."
   :exit-start-coords {:row 1 :col 0}})
(def loc-1-3-1
  {:obj (ref (place-obj health 1)) 
   :desc "A beautifully carved marble monk stares silently forward, unaware of the defilement of its temple."
   :enemy minion})
(def loc-1-3-2
  {:obj (ref (place-obj health 2)) 
   :desc "The High Priest of the Templar Order lies before you, dead. A sword is still clutched in his feeble hand." 
   :enemy minion})
(def loc-1-3-3
  {:obj (ref (place-obj temple 0)) 
   :desc "A marble alter takes up one-quarter of the underground chamber. It has been defaced with runes painted in blood. A spellbook lies open on top, emitting unearthly black smoke."
   :enemy boss})

(def basement-1-3 
  [[loc-1-3-0 loc-1-3-1]
   [loc-1-3-2 loc-1-3-3]])

;temple
;exiting temple
(def loc-1-0
  {:obj (ref (place-obj letter 1)) 
   :desc "The air inside in the Temple smells dusty and stagnant, and feels wrong coming from a place you've always known as bright and full of life." 
   :exit-start-coords {:row 1 :col 2}})
(def loc-1-1
  {:obj (ref (place-obj temple 1)) 
   :desc "Heavy oaken pews lie stacked haphazardly in front of the Temple's Eastern doors. You will have to move West to exit."})
;enter temple basement
(def loc-1-2
  {:obj (ref (place-obj health 1)) 
   :desc "A wooden bed lies upturned, the plain woollen blanket soaking up the last of the blood trail. A heavy wooden trapdoor lies open, eerie chanting echoing up from the depths."
   :enemy magic-cult
   :enter {:goto basement-1-3
           :start-coords {:row 0 :col 0}}})
(def loc-1-3
  {:obj (ref (place-obj cultists 2)) 
   :desc "The thick velvet curtain seperating the Priest's quarters has been ripped violently to the side, part of it dyed black from fresh blood. The blood trail continues West."
   :enemy dagger-cult})
(def loc-1-4
  {:obj (ref (place-obj cultists 1)) 
   :desc "A basin of Holy Water has been replaced with a brackish, foul-smelling, and cloudy fluid. You dare not touch it."})
(def loc-1-5
  {:obj (ref (place-obj temple 3)) 
   :desc "A carved monk stands in silent supplication, his marble hands reaching beseechingly upward."
   :enemy dagger-cult})
(def loc-1-6
  {:obj (ref (place-obj cultists 1)) 
   :desc "Smashed wooden pews and ripped pieces of tapistry litter the floor, a trail of fresh blood leading North through the wreckage."})
(def loc-1-7
  {:obj (ref (place-obj temple 4)) 
   :desc "Soft orange light filters through the stained glass, illuminating a red sun setting over a golden field."
   :enemy magic-cult})
(def loc-1-8
  {:obj (ref (place-obj temple 2)) 
   :desc "Bloody handprints mar the stained-glass windows at the back of the Temple. The blood drips into a small pool at the base of the wall and trails Northwest."
   :enemy dagger-cult})
(def loc-1-9
  {:obj (ref (place-obj health 3)) 
   :desc "An intricately carved alter sits untouched on a small dias. You're unsure of why the Cultists have left it alone--perhaps there's something else in here they wanted more."})

(def temple-1 
  [[loc-0-0 loc-1-0 loc-1-1  loc-0-0]
   [loc-1-2 loc-1-3 loc-1-4  loc-1-5]
   [loc-0-0 loc-1-6 loc-1-7  loc-0-0]
   [loc-0-0 loc-1-8 loc-1-9 loc-0-0]])

;mausoleum
;exiting mausoleum
(def loc-2-7-0
  {:obj (ref (place-obj letter 1)) 
   :desc "The heavy crypt doors sit ajar behind you. Strangely worn steps continue down into the darkness--who is trodding them, in this house of the dead?" 
   :exit-start-coords {:row 0 :col 1}})
(def loc-2-7-1
  {:obj (ref (place-obj skele 1)) 
   :desc "The worn steps continue downwards, and you begin to make out recesses in the room beyond, skulls gleaming white in the darkness."})
(def loc-2-7-2
  {:obj (ref (place-obj health 1)) 
   :desc "The rectangular recess in this wall is stacked with bones, skulls carefully placed in a pyramid atop a circle of long bones."
   :enemy bow-skele})
(def loc-2-7-3
  {:obj (ref (place-obj temple 1)) 
   :desc "Carefully edging forwards, you see the carved stone body of a Templar Knight from days gone by lying on top of an untouched sarcophagus."})
(def loc-2-7-4
  {:obj (ref (place-obj crypt 2)) 
   :desc "You push a sarcophagus lid aside and find a skeleton clutching a leather-bound journal protectively. As soon as you touch the journal, it crumbles into dust."
   :enemy bow-skele})
(def loc-2-7-5
  {:obj (ref (place-obj crypt 3)) 
   :desc "Several coffins are stacked on top of each other in the corner of the crypt. You gather that whoever was in these either wasn't well liked or wasn't very important."
   :enemy axe-skele})
(def loc-2-7-6
  {:obj (ref (place-obj cultists 1)) 
   :desc "You narrowly avoid stepping in the center of a chalk ring. Spectral runes glow a faint purple inside the circle, but slowly fade as you scrub your toe through the chalk line."
   :enemy axe-skele})
(def loc-2-7-7
  {:obj (ref (place-obj health 1))
   :desc "The slight smoky smell of insense hangs in the air above a smouldering censer."
   :enemy bow-skele})

;mausoleum
(def mausoleum-2-7 
  [[loc-0-0   loc-2-7-0 loc-0-0  ]
   [loc-0-0   loc-2-7-1 loc-0-0  ]
   [loc-2-7-2 loc-2-7-3 loc-2-7-4]
   [loc-2-7-5 loc-2-7-6 loc-2-7-7]])

;graveyard
(def loc-2-0
  {:obj (ref (place-obj health 1)) 
   :desc " A tiny fence marks the boundary of a family plot. Marigolds have been planted along the inside edge."})
;exit graveyard
(def loc-2-1
  {:obj (ref (place-obj skele 3)) 
   :desc "You stand just inside the twisting iron gate of the graveyard." 
   :enemy bow-skele
   :exit-start-coords {:row 1 :col 1}})
(def loc-2-2
  {:obj (ref (place-obj skele 2)) 
   :desc " A small wooden cross mark's a pauper's grave, the name too weathered to read."
   :enemy axe-skele})
(def loc-2-3
  {:obj (ref (place-obj rich 1)) 
   :desc " 'R. I. P. Commander Agustus Naro, an Inspiration to All' "
   :enemy axe-skele})
(def loc-2-4
  {:obj (ref (place-obj skele 0)) 
   :desc "This gravestone is half-hidden amongst a bunch of bright yellow sunflowers."})
(def loc-2-5
  {:obj (ref (place-obj skele 2)) 
   :desc " 'Here lies Stefan, a Goode Boy until the Ende.' You can just make out the unskilled carving of a Terrier underneith the epitaph. "
   :enemy bow-skele})
(def loc-2-6
  {:obj (ref (place-obj skele 1)) 
   :desc "A tall stone monument tilts to the side, the epitaph hidden under the ivy wrapping its way up the pillar."
   :enemy axe-skele})
;entering mausoleum
(def loc-2-7
  {:obj (ref (place-obj skele 1)) 
   :desc "Nestled among carefully-pruned bushes sits the Templar Mausoleum, the heavy stone jar cracked slightly open."
   :enemy bow-skele
   :enter {:goto mausoleum-2-7 
           :start-coords {:row 0 :col 1}}})
(def loc-2-8
  {:obj (ref (place-obj health 1)) 
   :desc "You crouch under an ancient and tall weeping willow, its branches trailing over several tombstones."})

(def graveyard-2 
  [[loc-2-6 loc-2-7 loc-2-8]
   [loc-2-3 loc-2-4 loc-2-5]
   [loc-2-0 loc-2-1 loc-2-2]])

;store
(def loc-3-0
  {:obj (ref (place-obj gen 5)) 
   :desc "Bunches of unused candles tied with twine are neatly stacked in this corner."})
(def loc-3-1
  {:obj (ref (place-obj skele 1)) 
   :desc "The floor here is littered with hard sugar candy, spilled from their container."
   :enemy bow-skele})
(def loc-3-2
  {:obj (ref (place-obj home 2)) 
   :desc "The entire corner of the shop is stacked with wooden crates, some marked with an expiration date of several days prior to today's."
   :enemy axe-skele})
(def loc-3-3
  {:obj (ref (place-obj health 2)) 
   :desc "A curtain, embroidered with tiny cherries, cheerfully hides the gray light filtering through the grimy window."})
(def loc-3-4
  {:obj (ref (place-obj gen 6)) 
   :desc "A hand-tied rug dominates the center of the shop, its original colors faded under years of heavy use."})
(def loc-3-5
  {:obj (ref (place-obj home 4)) 
   :desc "You examine the main counter piled high with buttons and spools of thread. The store's money tin has been pried open and emptied."
   :enemy bow-skele})
(def loc-3-6
  {:obj (ref (place-obj gen 3)) 
   :desc "The ground around a keg of unskilfully tapped cider is still saturated with liquid."})
;enter store
(def loc-3-7
  {:obj (ref (place-obj home 1)) 
   :desc "Your head brushes against a small brass bell attached to the door and a litle chime rings out." 
   :enemy bow-skele
   :exit-start-coords {:row 0 :col 4}})
(def loc-3-8
  {:obj (ref (place-obj skele 1)) 
   :desc "An oversized shelf stocked high with home goods is squeezed into the corner of the shop."
   :enemy axe-skele})

(def store-3 
  [[loc-3-0 loc-3-1 loc-3-2]
   [loc-3-3 loc-3-4 loc-3-5]
   [loc-3-6 loc-3-7 loc-3-8]])

;mansion
(def loc-4-0
  {:obj (ref (place-obj home 3))
   :desc "You stand in a grand dining room. The silver cutlery sparkles in the afternoon sun."
   :enemy ghost})
(def loc-4-1
  {:obj (ref (place-obj gen 2))
   :desc "You walk towards a cabinet in the corner of the dinking room and find several empty wine bottles inside."})
(def loc-4-2
  {:obj (ref (place-obj health 2))
   :desc "Three walls of this room are full-length windows, and clusters of potted plans sit every few feet. A small alchemical workbench is pressed agianst one wall."
   :enemy ghost})
(def loc-4-3
  {:obj (ref (place-obj home 0))
   :desc "An unobtrustive door leads to a washroom, a rare sight in this town. You take some time to slash your face with fresh, cold water from the basin."})
(def loc-4-4
  {:obj (ref (place-obj rich 3))
   :desc "The door the master suite hangs from one hinge. You can just make out the mayor's bloodied body lying behind the four-poster bed dominating the room."
   :enemy ghost})
;enter mansion
(def loc-4-5
  {:obj (ref (place-obj health 1))
   :desc "The foyer of this mansion is decorated with an oil painting of its original owner--the mayor's grandfather, the founder of this town."
   :exit-start-coords {:row 2 :col 2}})
(def loc-4-6
  {:obj (ref (place-obj cultists 2))
   :desc "You rifle through the mayor's study and find a crumpled note in the fire. It is from the captain of the town's guard, and mentions suspicious figures have been seen lurking outside town."})
(def loc-4-7
  {:obj (ref (place-obj rich 7))
   :desc "Two crossed swords hang on the wall above the entrance to the mayor's pursonal armory. You pick through her collection and spot a few pieces you like."})
(def loc-4-8
  {:obj (ref (place-obj gen 3))
   :desc "A dark celler lies behind a battered door. A few beets spill out of a sack in the corner."
   :enemy ghost})
(def loc-4-9
  {:obj (ref (place-obj rich 2))
   :desc "You're sure this was a comfortable sitting room at one time, but it's hard to ignore the body slumped forward in an armchair off to the side of the cold fireplace."
   :enemy ghost})

(def mansion-4 
  [[loc-0-0 loc-4-0 loc-4-1]
   [loc-4-2 loc-4-3 loc-4-4]
   [loc-4-5 loc-4-6 loc-4-7]
   [loc-0-0 loc-4-8 loc-4-9]])

;house
(def loc-5-0
  {:obj (ref (place-obj gen 3))
   :desc "You lean out the back door and see a small vegetable garden. Empty and half-full sacks of vegetables are stacked by the door."
   :enemy ghost})
(def loc-5-1
  {:obj (ref (place-obj home 3))
   :desc "A large, rough-hewn shelf sits opposite the wooden table. Jars of herbs and spices are intermixed with sacks of flour and a mortar and pestle."
   :enemy ghost})
(def loc-5-2
  {:obj (ref (place-obj home 2))
   :desc "A faded lace tablecloth covers a modest wooden table. Dried flowers decorate the window sill."})
(def loc-5-3
  {:obj (ref (place-obj home 4))
   :desc "Muddy boots and some small farming equipment are tossed haphazardly by the back door."})
(def loc-5-4
  {:obj (ref (place-obj health 1))
   :desc "The kitchen table is piled high with loves of baked bread, some tinged slightly green from herbs. You take a loaf hungrily--though stale, it still tastes very good."})
(def loc-5-5
  {:obj (ref (place-obj rich 2))
   :desc "You enter the main room of the house. An old brass key tied to a faded pink ribbon hangs from the wall. Underneith is a large wooden iron-studded chest."
   :enemy ghost})
;enter house
(def loc-5-6
  {:obj (ref (place-obj health 2))
   :desc "A woven basket sits by the front door. A linen cloth has been carefully wrapped around a bundle of herbs and potions."
   :exit-start-coords {:row 3 :col 2}})

(def house-5 
  [[loc-5-0 loc-5-1 loc-5-2 loc-0-0]
   [loc-5-3 loc-5-4 loc-5-5 loc-5-6]])

;ruins
(def loc-6-0
  {:obj (ref (place-obj gen 4)) 
   :desc "A side of the house has collapsed completely, leaving the surrounding area exposed to the elements. Pockets of moss are beginning to grow on one rain-soaked log."
   :enemy ghost})
(def loc-6-1
  {:obj (ref (place-obj health 1)) 
   :desc "You cling to a fallen beam as you cover a patch of cracked and broken flooring."})
(def loc-6-2
  {:obj (ref (place-obj gen 0)) 
   :desc "Whatever was originally in this pantry has completely burnt to a crisp."})
;enter ruins
(def loc-6-3
  {:obj (ref (place-obj home 2)) 
   :desc "You duck under a beam that fell in front of the front door. Blackened wreakage lays around you." 
   :exit-start-coords {:row 3 :col 4}})
(def loc-6-4
  {:obj (ref (place-obj health 1)) 
   :desc "You climb on top of a fallen staircase to get a better look around."
   :enemy ghost})
(def loc-6-5
  {:obj (ref (place-obj home 1)) 
   :desc "You spot a trapdoor behind the ruined staircase. The surrounding earth has sunken into it, filling the basement completely."})
(def loc-6-6
  {:obj (ref (place-obj home 5)) 
   :desc "You shift a few large wooden beams and find a pocket of goods untouched by the fire."
   :enemy ghost})
(def loc-6-7
  {:obj (ref (place-obj health 0)) 
   :desc "The only thing left standing in this house is the soot-covered fireplace."})
(def loc-6-8
  {:obj (ref (place-obj gen 3)) 
   :desc "The ground here is sunken and shifted in a small landslide."
   :enemy ghost})

(def ruins-6 
  [[loc-6-0 loc-6-1 loc-6-2]
   [loc-6-3 loc-6-4 loc-6-5]
   [loc-6-6 loc-6-7 loc-6-8]])

;cave
(def loc-13-0
  {:obj (ref (place-obj letter 1)) 
   :desc "The light from the cave entrance does not pierce far into the darkness. Every so often a small 'plink' of water hitting stone echos out of the depths." 
   :exit-start-coords {:row 3 :col 0}})
(def loc-13-1
  {:obj (ref (place-obj cultists 3)) 
   :desc "A small trickle of water feeds a large concentration of dark blue mushrooms. You have no idea if they're edible, and you'd rather not check."
   :enemy magic-cult})
(def loc-13-2
  {:obj (ref (place-obj skele 0)) 
   :desc "Bats fly around your head as you round a corner. You curse as you pull your left boot out of a squishy pile of guano."})
(def loc-13-3
  {:obj (ref (place-obj skele 3)) 
   :desc "A crumbling deer skull glints in the gloom. Moss is beginning to cover the antlers in a soft green blanket."
   :enemy axe-skele})
(def loc-13-4
  {:obj (ref (place-obj gen 4)) 
   :desc "There is a half-rotted wooden dock on the rocky bank, covered in moss. One post is still tied with a rotted rope, but the boat it led to has long since floated away."
   :enemy rat})
(def loc-13-5
  {:obj (ref (place-obj gen 0)) 
   :desc "Pillars of connected stalagmites and stactites block your path to the West. To the South you hear the dull roar of an unground river."})
(def loc-13-6
  {:obj (ref (place-obj health 2)) 
   :desc "A small spring bubbles in a rocky alcove. Moss and ferns grow happily in the dampness, illuminated by the soft light of glowing mushrooms."})
(def loc-13-7
  {:obj (ref (place-obj health 1)) 
   :desc "An old traveler's pack has been torn open on the ground, ripped apart by animal teeth from what you can tell."
   :enemy rat})
(def loc-13-8
  {:obj (ref (place-obj skele 3)) 
   :desc "A jagged line of stalagmites mark the edge of a rockslide leading to a river far below. There is a wooden bridge spanning the chasm to your North."
   :enemy bow-skele})
(def loc-13-9
  {:obj (ref (place-obj cultists 2)) 
   :desc "The glowing mushrooms to the South West barely illuminate the remains of a campsite. You poke around a bit, but the tracks of animals indicate that you're not the first to do so."
   :enemy dagger-cult})
(def loc-13-10
  {:obj (ref (place-obj home 1)) 
   :desc "You find a skeletal body propped against the Northern wall of the cave, a few arrows still sticking out of its shield. This poor fellow has been here a while."})
(def loc-13-11
  {:obj (ref (place-obj cultists 3)) 
   :desc "A rickety wooden bridge spans the length of a dark chasm in the rocky earth."
   :enemy magic-cult})
(def loc-13-12
  {:obj (ref (place-obj gen 1)) 
   :desc "You glare warily at the creeking wooden bridge, very glad to be on solid ground for the moment. An eerie green glow illuminates the path to the East."
   :enemy rat})
(def loc-13-13
  {:obj (ref (place-obj health 1)) 
   :desc "Phosphorescent mushrooms cast an unearthly greenish glow on the far side of the cave. You catch the gleam of armor to the South."})
(def loc-13-14
  {:obj (ref (place-obj skele 2)) 
   :desc "You nearly tumble off the edge of an underground cliff. You sit on a nearby boulder and catch your breath, listening to the rumble of the underground river below."})
(def loc-13-15
  {:obj (ref (place-obj temple 3)) 
   :desc "The body of a knight lies face down in the rocky earth. You're pretty sure you need her gear more than she does at the moment."
   :enemy bow-skele})

(def cave-13 
  [[loc-0-0  loc-0-0  loc-0-0  loc-13-9 loc-13-10 loc-13-11 loc-13-12 loc-13-13]
   [loc-0-0  loc-0-0  loc-13-6 loc-13-7 loc-13-8  loc-0-0   loc-13-14 loc-13-15]
   [loc-0-0  loc-13-2 loc-13-3 loc-13-4 loc-13-5  loc-0-0   loc-0-0   loc-0-0  ]
   [loc-13-0 loc-13-1 loc-0-0  loc-0-0  loc-0-0   loc-0-0   loc-0-0   loc-0-0  ]])

;tavern
(def loc-18-0
  {:obj (ref (place-obj tavern 2)) 
   :desc "You peek into the kitchen, and nearly gag at the smell of days-old dirty plates and mugs soaking in a sink full of foul, rancid water."})
(def loc-18-1
  {:obj (ref (place-obj gen 4)) 
   :desc "Kegs of beer and barrels of dried meat are stacked against the wall of the storeroom."
   :enemy bow-skele})
(def loc-18-2
  {:obj (ref (place-obj gen 7)) 
   :desc "You enter the back room of the tavern, your boots sticking slightly to the floor as you try to avoid piles of spilled food and dried drink."})
(def loc-18-3
  {:obj (ref (place-obj tavern 2)) 
   :desc "You crawl along the edge of the counter, trying not to be seen."
   :enemy bow-skele})
(def loc-18-4
  {:obj (ref (place-obj gen 0)) 
   :desc "You squeeze yourself between two heavy armchairs in front of the fireplace."
   :enemy axe-skele})
(def loc-18-5
  {:obj (ref (place-obj tavern 3)) 
   :desc "This fireplace is still lit, fed by chair and table legs and smashed bottles of alcohol."
   :enemy axe-skele})
(def loc-18-6
  {:obj (ref (place-obj tavern 4)) 
   :desc "You duck behind the counter and find a few bottles of untouched beer and liquor."})
(def loc-18-7
  {:obj (ref (place-obj tavern 5)) 
   :desc "Someone--or some skeleton--has taken the time to carefully construct a card castle on the edge of the dancefloor. Bottles stand around it like guardtowers."
   :enemy axe-skele})
(def loc-18-8
  {:obj (ref (place-obj tavern 3)) 
   :desc "A few skeletons lie still on the tavern's makeshift dance floor, thier hands still clutching broken bottles and clubs."})
(def loc-18-9
  {:obj (ref (place-obj gen 2)) 
   :desc "Tables and chairs have been pushed in a circle to form a make-shift fighting circle."})
;enter tavern
(def loc-18-10
  {:obj (ref (place-obj tavern 1)) 
   :desc "The front doors of the tavern creak open slowly in the breeze."
   :exit-start-coords {:row 1 :col 4}})
(def loc-18-11
  {:obj (ref (place-obj tavern 4)) 
   :desc "A discarded lute lies in the corner next to several overturned chairs. You can just see the edge of a drum peeking out from the nearest table."
   :enemy bow-skele})

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
  {:obj (ref (place-obj gen 5)) 
   :desc "A cart with a broken wheel lies abandoned on the side of the road."})
(def loc-1
  {:obj (ref (place-obj letter 1)) 
   :desc "The Templar Temple rises above you, colored light shining through stained glass windows onto the cobblestones below. A carving of a holy knight slaying a demon sits above the door." 
   :enter {:goto temple-1 
           :start-coords {:row 0 :col 1}}})
(def loc-2
  {:obj (ref (place-obj skele 2)) 
   :desc "Rickety gates surround an old graveyard. The air smells of freshly-dug earth." 
   :enter {:goto graveyard-2
           :start-coords {:row 2 :col 1}}})
(def loc-3
  {:obj (ref (place-obj gen 3)) 
   :desc "A decrepit general store stands before you, barrels of rotting goods scattered about the area." 
   :enter {:goto store-3 
           :start-coords {:row 2 :col 1}}})
(def loc-4
  {:obj (ref (place-obj health 1)) 
   :desc "A grand, old mansion sits in a dying garden. A single candle flickers behind a stained-glass window." 
   :enter {:goto mansion-4 
           :start-coords {:row 2 :col 0}}}) 
(def loc-5
  {:obj (ref (place-obj home 2)) 
   :desc "This cottage is exceptionally well built, and in better shape than most of the surrounding buildings. There still might be something useful inside." 
   :enter {:goto house-5 
           :start-coords {:row 1 :col 3}}})
(def loc-6 
  {:obj (ref (place-obj gen 3)) 
   :desc "A ruined building lies in an overgrown field. It looks like it burned down not long ago, but a glint of metal catches your eye." 
   :enter {:goto ruins-6 
           :start-coords {:row 1 :col 0}}})
(def loc-7
  {:obj (ref (place-obj gen 2)) 
   :desc "You squeeze into a narrow alleyway between two buildings. A rat scurries away from you." 
   :enemy rat})
(def loc-8 
  {:obj (ref (place-obj health-loc-8 8)) 
   :desc "You hide among a small copse of gnarled pine trees, the hanging moss shielding you from view."})
(def loc-9
  {:obj (ref (place-obj home 4)) 
   :desc "Broken, cracked cobblestones and smashed stalls form a picture of a town square bustling with ghosts."
   :enemy axe-skele})
(def loc-10
  {:obj (ref (place-obj health 3)) 
   :desc "A half-rotted well lies in the middle of the market square. You drop a small pebble and hear a small 'plink' several seconds later." 
   :enemy zombie})
(def loc-11
  {:obj (ref (place-obj forge 2)) 
   :desc "Brightly-colored cloth and lanterns lie smashed around a market stall."
   :enemy rat})
(def loc-12
  {:obj (ref (place-obj forge 6)) 
   :desc "You find yourself in front of a forge, long cold."})
(def loc-13
  {:obj (ref (place-obj letter 1)) 
   :desc "Mossy rocks jut out of the marsh. Cold air whistles past you, coming from a small opening in the rock just big enough to crawl through." 
   :enter {:goto cave-13 
           :start-coords {:row 3 :col 0}}})
(def loc-14
  {:obj (ref (place-obj cultists 3)) 
   :desc "A small shrine of carved, blackened bones has been constructed on top of a pile of smooth stones." 
   :enemy magic-cult})
(def loc-15
  {:obj (ref (place-obj cultists 6)) 
   :desc "Low chanting drifts over on the wind. In the distance, a small camp surrounds a fire of unearthly purple flames. You crouch lower in the weeds, hoping to not be seen." 
   :enemy dagger-cult})
(def loc-16
  {:obj (ref (place-obj cultists 3)) 
   :desc "A sticky smear of blood leads to a pile of burnt corpses that once were the people who lived here. You feel your stomach turn over and look away."
   :enemy zombie})
(def loc-17
  {:obj (ref (place-obj gen 2)) 
   :desc "This grain store has been painted with a swirling symbol. You stare at it, trying to make sense of the squiggling lines, and feel your mind bending."})
(def loc-18
  {:obj (ref (place-obj tavern 2)) 
   :desc "You stand in front of a tavern, its welcoming sign hanging askew. Casks of beer lie smashed on the road." 
   :enter {:goto tavern-18 
           :start-coords {:row 3 :col 1}}})
(def loc-19
  {:obj (ref (place-obj home 3)) 
   :desc "A lone chicken picks for worms in a field littered with blood and household goods."
   :enemy bow-skele})

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

;PC HEALTH AND DAMAGE________________________________________________________________________

;player's initial max health
(def init-max-health
  20)

;player's actual health--subtracted during combat
(def init-pc-health
  20)

(defn print-pc-health [pc-health max-health]
  (print "             Your current health is ") 
  (print pc-health)
  (print " out of ")
  (print max-health))

;player's initial damage--added to by weapons
(def init-pc-damage
  5)

(defn print-pc-damage [pc-damage]
  (println) 
  (print "             Your current damage output is ") 
  (print pc-damage) 
  (println) 
  (println))

(defn add-damage [pc-inv pc-damage maybe-int]
  "called when player equippes new weapon.
   weapon damage is added to player's damage"
  (+ pc-damage (:damage (nth pc-inv (dec maybe-int)))))

(defn add-health [pc-inv health maybe-int]
  "called when player equippes new armor.
   armor health is added to player's health"
  (+ health (:health (nth pc-inv (dec maybe-int)))))

(defn sub-damage [pc-eq pc-damage maybe-int]
  "called when player unequippes a weapon.
   weapon damage is subtracted from player's damage"
  (- pc-damage (:damage (nth pc-eq (dec maybe-int)))))

(defn sub-health [pc-eq health maybe-int]
  "called when player unequippes armor.
   armor health is subtracted from player's damage"
  (- health (:health (nth pc-eq (dec maybe-int)))))

(defn potion-add-health [pc-inv pc-health maybe-int max-health]
  "called when player drinks a health potion.
   makes sure that player's health never goes over
   theoretical max."
  (let [new-pc-health (+ pc-health (:health (nth pc-inv (dec maybe-int))))]
    (if (> new-pc-health max-health)
      max-health
      new-pc-health)))

;OBJECT OPTIONS______________________________________________________________________________

(defn get-obj [pc-loc map-stack]
  "used for getting the object in the location"
  (deref 
    (get (get-pc-loc pc-loc map-stack) :obj)))

(defn get-obj-ref [pc-loc map-stack]
  "used for changing the actual list of objs in location"
  (get (get-pc-loc pc-loc map-stack) :obj))

;adding items to inventory____________________________________________________

(defn add-obj-to-inv [pc-loc map-stack pc-inv maybe-int]
  "takes number of obj user indicated they wanted.
   that object is gotten from the location obj list and added to inventory list"
  (println)
  (print "That item has been added to your inventory.")
  (println)
  (print-debug pc-inv)
  (vec (conj pc-inv (nth (get-obj pc-loc map-stack) (dec maybe-int)))))

(defn remove-obj-from-loc [pc-loc map-stack maybe-int]
  "takes number of obj user indicated to pick up. that item is removed
   from location after being added to player's inventory. removal happens
   by splitting the vector to two lists (neither one including the obj
   that was indicated) and then joins them back together."
  (let [pre-obj (subvec (get-obj pc-loc map-stack) 0 (dec maybe-int))
        post-obj  (subvec (get-obj pc-loc map-stack) maybe-int)]
    (dosync
      (ref-set (get-obj-ref pc-loc map-stack) (vec (concat pre-obj post-obj))))))

(defn nothing-to-add []
  (println) 
  (println "There is nothing here to add.") 
  (println) 
  (pause)) 

(defn is-int? [input]
  "try/catch block for user input sanitization. if user didn't input an int,
   will catch the error instead of killing the program."
  (try 
    (Integer/parseInt input)
    (catch NumberFormatException e (println "That's not a number."))))

(defn something-to-add [pc-loc map-stack pc-inv]
  "gets user input for item number they want to add,
   passes to try-catch block for input sanitization.
   If user input is correct, calls func to add obj to inventory,
   and calls func to remove obj from location."
  (println) 
  (println "What would you like to add? enter '1' for the first item listed, '2' for the second item listed, and so on.") 
  (println)
  (let [input (read-line)]
    (let [maybe-int (is-int? input)]
      (if maybe-int
        (if (or (<= maybe-int (count (get-obj pc-loc map-stack))) (<= maybe-int 0))
          (let [new-pc-inv (add-obj-to-inv pc-loc map-stack pc-inv maybe-int)]
            (print-debug new-pc-inv)
            (remove-obj-from-loc pc-loc map-stack maybe-int)
            (print-debug new-pc-inv)
            new-pc-inv)
          (do
            (println "There's not that many items here.")
            (something-to-add pc-loc map-stack pc-inv)))
        (something-to-add pc-loc map-stack pc-inv)))))

(defn add-to-inv [pc-loc map-stack pc-inv]
  "prevents error if there are no objects to add at that location"
  (if (= [] (get-obj pc-loc map-stack))
    (do 
      (nothing-to-add)
      (print-debug pc-inv) 
      pc-inv)
    (something-to-add pc-loc map-stack pc-inv)))

;print object options_____________________________________________________

(defn print-obj-item [item]
  "prints item desc"
  (print "                    ")
  (print item)
  (println))

(defn print-obj [pc-loc map-stack]
  "prints individual items at that loc one by one"
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
  "for user to see options"
  (clear-screen)
  (open-objects)
  (print-obj pc-loc map-stack)
  (open-potions))

(defn obj-control [pc-loc map-stack pc-inv]
  "displays player options, and gets user input.
   calls function that controls object adding functionality.
   then prints object descriptions that are in current location"
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

;inventory________________________________________________________________________

;removing items from inventory_______________________________________________

(defn add-item-to-loc [pc-loc map-stack pc-inv maybe-int]
  "copies item from inventory to object list at location."
  (print-debug pc-inv)
  (dosync
    (alter (get-obj-ref pc-loc map-stack) conj (nth pc-inv (dec maybe-int)))))

(defn nothing-to-drop [pc-inv]
  (println "You have nothing to drop.")
  (pause)
  pc-inv)

(defn something-to-drop [pc-loc map-stack pc-inv]
  "gets user input, makes sure it's an int, then adds obj from inv to location
   and removes obj from inv (splits inv in two around obj, then joins segments together)"
  (println)
  (println "What item do you want to drop? Enter '1' for the first item listed, '2' for the second item listed, and so on.")
  (let [input (read-line)]
    (let [maybe-int (is-int? input)]
      (if maybe-int
        (if (or (<= maybe-int (count pc-inv)) (<= maybe-int 0))
          (do
            (print-debug pc-inv)
            (add-item-to-loc pc-loc map-stack pc-inv maybe-int)
            (let [pre-inv (subvec pc-inv 0 (dec maybe-int))
                  post-inv (subvec pc-inv maybe-int)]
              (print-debug pre-inv)
              (print-debug post-inv)
              (vec (concat pre-inv post-inv))))
          (do
            (println "You don't have that many items in your inventory.")
            (something-to-drop pc-loc map-stack pc-inv)))
        (something-to-drop pc-loc map-stack pc-inv)))))

(defn remove-item-from-inv [pc-loc map-stack pc-inv]
  "Making sure program won't crash if there's nothing in inv to drop"
  (if (= pc-inv [])
    (nothing-to-drop pc-inv)
    (something-to-drop pc-loc map-stack pc-inv)))

;equip and unequip___________________________________________________

(defn print-eq [item]
  "prints item desc"
  (print "                    ")
  (print (:desc item))
  (println))

(defn print-pc-eq [pc-eq]
  "gets individual items to print descriptions"
  (if (= pc-eq [])
    (println "             You have no items equipped.")
    (doseq [item pc-eq]
      (print-eq item))))

(defn nothing-to-equip [pc-eq pc-health pc-damage max-health]
  (println "There is nothing in your inventory.")
  (pause)
  [pc-eq
   pc-health
   pc-damage
   max-health])

(defn something-to-equip [pc-inv pc-eq pc-health pc-damage max-health]
  "gets user input (makes sure it's an int), check if it's a potion
   (can't be equipped), then adds that item to end of equipped list
   and adds the item's health and damage bonuses to player's."
  (println "What item do you want to equip? Enter '1' for the first item listed, '2' for the second item listed, and so in.")
  (println) 
  (let [input (read-line)]
    (let [maybe-int (is-int? input)]
      (if maybe-int
        (if (or (<= maybe-int (count pc-inv)) (<= maybe-int 0))
          (if (:potion (nth pc-inv (dec maybe-int)))
            (do
              (println "You can't equip that.")
              (pause)
              [pc-eq pc-health pc-damage max-health])
            [(vec (conj pc-eq (nth pc-inv (dec maybe-int)))) 
             (add-health pc-inv pc-health maybe-int) 
             (add-damage pc-inv pc-damage maybe-int)
             (add-health pc-inv max-health maybe-int)])
          (do
            (println "You don't have that many items in your inventory.")
            (something-to-equip pc-inv pc-eq pc-health pc-damage max-health)))
        (something-to-drop pc-inv pc-eq pc-health pc-damage max-health)))))

(defn equip-item [pc-inv pc-eq pc-health pc-damage max-health]
  "to make sure program doesn't die if there's nothing to equip"
  (println)
  (if (= [] pc-inv)
    (nothing-to-equip pc-eq pc-health pc-damage max-health) 
    (something-to-equip pc-inv pc-eq pc-health pc-damage max-health)))

(defn something-to-unequip [pc-eq pc-health pc-damage max-health]
  "gets user input (makes sure it's an int), then remove it from equipped list
   by splitting equipped list around that object and then joining it together again.
   subtracts that item's health and damge from player's."
  (println)
  (println "What item do you want to unequip? Enter'1' for the first item listed, '2' for the second item listed, and so on.")
  (println)
  (let [input (read-line)]
    (let [maybe-int (is-int? input)]
      (if maybe-int
        (if (or (<= maybe-int (count pc-eq)) (<= maybe-int 0))
          (let [pre-eq (subvec pc-eq 0 (dec maybe-int))
                post-eq (subvec pc-eq maybe-int)]
            [(vec (concat pre-eq post-eq))
             (sub-health pc-eq pc-health maybe-int) 
             (sub-damage pc-eq pc-damage maybe-int)
             (sub-health pc-eq max-health maybe-int)])
          (do
            (println "You don't have that many items equipped.")
            (something-to-unequip pc-eq pc-health pc-damage max-health)))
        (something-to-unequip pc-eq pc-health pc-damage max-health)))))

(defn unequip-item [pc-eq pc-health pc-damage max-health]
  "makes sure program doesn't die if there's nothing equipped"
  (if (= [] pc-eq)
    (nothing-to-equip pc-eq pc-health pc-damage max-health) 
    (something-to-unequip pc-eq pc-health pc-damage max-health)))

(defn not-drinkable [pc-inv pc-health]
  "non-potions are persistant."
  (println)
  (println "You don't think you can drink that.")
  (println)
  [pc-inv pc-health]) 

(defn drinkable [pc-loc map-stack pc-inv pc-health maybe-int max-health]
  "adds potion's health bonus to user, and subtracts it from the inventory."
  (println)
  (println "You drink the potion, and immediately feel a little bit better.")
  (print-debug pc-inv)
  (let [pre-inv (subvec pc-inv 0 (dec maybe-int))
        post-inv (subvec pc-inv maybe-int)]
    (print-debug pre-inv)
    (print-debug post-inv)
    [(vec (concat pre-inv post-inv)) 
     (potion-add-health pc-inv pc-health maybe-int max-health)]))

(defn drink-hp [pc-loc map-stack pc-inv pc-health max-health]
  "gets user input, makes sure it's an int, makes sure it's a potion,
   then calls function to add potion bonus to user and remove from inventory."
  (println)
  (println "What item do you want to drink? Enter '1' for the first item listed, '2' for the second item listed, and so on.")
  (let [input (read-line)]
    (let [maybe-int (is-int? input)]
      (if maybe-int
        (if (<= maybe-int (count pc-inv))
          (if (<= maybe-int 0)
            (do
              (println "That's not an item you can drink.")
              [pc-inv pc-health])
            (if (not (:potion (nth pc-inv (dec maybe-int))))
              (not-drinkable pc-inv pc-health)
              (drinkable pc-loc map-stack pc-inv pc-health maybe-int max-health)))
          (do
            (println "You don't have that many items in your inventory.")
            (drink-hp pc-loc map-stack pc-inv pc-health max-health)))
        (drink-hp pc-loc map-stack pc-inv pc-health max-health)))))

;print inventory____________________________________________________

(defn print-item [item]
  "prints individual item description"
  (print "                    ")
  (print (:desc item))
  (println))

(defn print-pc-inv [pc-inv]
  "gets individual item to print"
  (if (= pc-inv [])
    (println "             Your inventory is currently empty.")
    (do
      (print-debug pc-inv)
      (doseq [item  pc-inv]
      (print-item item)))))

(defn open-inv-menu []
  "displays inv menu"
  (with-open [rdr (io/reader "resources/inv-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-sword []
  "displays sword"
  (with-open [rdr (io/reader "resources/sword.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn print-inv-commands [pc-inv pc-eq pc-health pc-damage max-health]
  "nice printing for inventory"
  (clear-screen)
  (open-inv-menu)
  (open-sword)
  (print-pc-health pc-health max-health)
  (print-pc-damage pc-damage)
  (println "             Your inventory contains the following items:")
  (print-pc-inv pc-inv)
  (println)
  (println "             You have equipped the following items:")
  (print-pc-eq pc-eq)
  (println))

(defn inv-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health]
  "for user inventory: displays user menu and gets input. user can:
   exit the inv menu, remove an item from inventory, equip something, 
   unequip something, and drink potion."
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
                      (pause)
                      (inv-control pc-loc map-stack new-pc-inv pc-eq new-pc-health pc-damage max-health))
      :else (inv-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health))))

;combat_______________________________________________________________________________________

(defn print-rat []
  (println "A large rat scuttles out of the shadows, open sores oozing on its back. It bares its yellow teeth at you. "))
 
(defn print-cultist []
  (if (= 1 (rand 2))
    (println "A young man in swirling black blood-soaked robes glances around, spotting you immediately. He shouts, drawing a curved dagger dripping with poison.")
    (println "You turn a corner and see a woman with long, grey hair concentrate for a moment before a tiny blue flame appears in her hand. In a second a roaring fire is contained in her palm.")))

(defn print-skele []
  (if (= 1 (rand 2))
    (println "A skeleton comes rushing towards you, jaw clacking. It raises a cracked bow and fits a rusted arrow to it.")
    (println "This skeleton looks between you and the chipped axe in its bony hand, and a whisper of a sad sigh rattles in its ribcage.")))

(defn print-zombie []
  (println "As you carefully pick your way forward, a corpse groans and rises from the ground, stumbling towards you."))

(defn print-ghost []
  (println "The air freezes and the light around you dims. A gleam of ethereal blue light coalesces into a spirit, lost in the torment of its last moments. 'No! I won't let you hurt them!' "))

(defn print-boss []
  (println "A old woman, back bent with age, looks up from the alter, a wicked gleam in her eye. The wooden staff in her hand crackles with energy and the crystal at the top glows a deep red." ))

(defn print-minion []
  (println " 'You will not interrupt the Master!' A knight blocks your path, a heavy mace held aloft. The holy symbol that once adorned the breastplate lies to the side, cracked in half."))

(defn print-tavern-skele []
  (let [which-one? (rand-int 5)]
    (cond
      (= which-one? 0) (println "A skeleton stumbles towards you drunkenly, waving an empty bottle of whiskey.")
      (= which-one? 1) (println "A skeleton with a tablecloth tied around its head as a bandana breaks a chair over its bony knee, picking up the remnants in both hands.")
      (= which-one? 2) (println "A skeleton pours a bottle behind the bar through its open jaw, rum splashing through its body. It smashes the empty bottle and jumps over the counter towards you.")
      (= which-one? 3) (println "Two skeletons are locked in a brawl in front of you. One uses a candlestick to bash the other's skull, and it collapses. The victor looks around for another victim.")
      (= which-one? 4) (println "A skeleton dances drunkenly to silent music. You try to squeeze past it, but you bump its hip and it angrily lashes out at you.")
      :else (println "this should never print: print-tavern-skele"))))

(defn print-enemy [pc-loc map-stack]
  "prints different enemy descriptions"
  (if (= tavern-18 (first map-stack))
    (print-tavern-skele)
    (do
      (cond
        (:rat (:enemy (get-pc-loc pc-loc map-stack))) (print-rat)
        (:cultist (:enemy (get-pc-loc pc-loc map-stack))) (print-cultist)
        (:skeleton (:enemy (get-pc-loc pc-loc map-stack))) (print-skele)
        (:zombie (:enemy (get-pc-loc pc-loc map-stack))) (print-zombie)
        (:ghost (:enemy (get-pc-loc pc-loc map-stack))) (print-ghost)
        (:boss (:enemy (get-pc-loc pc-loc map-stack))) (print-boss)
        (:minion (:enemy (get-pc-loc pc-loc map-stack))) (print-minion)
        :else (do 
                (println "this should never print: print-enemy")
                (println (:enemy (get-pc-loc pc-loc map-stack))))))))

(defn print-enemy-damage-done [pc-health max-health new-pc-health]
  "nice display for user"
  (print "             The enemy attacks and deals ")
  (print (- pc-health new-pc-health))
  (print " damage.")
  (println)
  (print-pc-health new-pc-health max-health)
  (println)
  (println))

(defn pc-dead []
  "clears screen, displays 'you died' screen, and quits program"
  (clear-screen)
  (println)
  (with-open [rdr (io/reader "resources/you-died.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
      (System/exit 0))

(defn pc-wins []
  "clears screen, displays 'you won' screen, and quits program"
  (clear-screen)
  (println)
  (with-open [rdr (io/reader "resources/you-win.txt")]
    (doseq [line (line-seq rdr)]
      (println line)))
      (System/exit 0))

(defn print-pc-damage-done [enemy-health new-enemy-health]
  "nice display for user"
  (print "             You swing your weapon as hard as you can, hitting your opponent for ") 
  (print (- enemy-health new-enemy-health))
  (print " damage!")
  (println)
  (print "             Enemy health: ")
  (print new-enemy-health)
  (println)
  (println))

(defn enemy-first [pc-loc map-stack pc-health pc-damage max-health enemy-health] 
  "enemy hits user first. pc's health is lowered by damage taken.
   then user hits enemy, and enemy's health is lowered by damage taken.
   if player dies, calls func to end game. if enemy dies, ends combat."
  (let [new-pc-health 
        (- pc-health (- (get-in (get-pc-loc pc-loc map-stack) [:enemy :damage]) (rand-int 3)))]
    (print-enemy-damage-done pc-health max-health new-pc-health)
    (if (<= new-pc-health 0)
      (pc-dead)
      (let [new-enemy-health 
            (- enemy-health (- pc-damage (rand-int 3)))]
        (print-pc-damage-done enemy-health new-enemy-health)
        [new-pc-health new-enemy-health]))))

(defn pc-first [pc-loc map-stack pc-health pc-damage max-health enemy-health]
  "player hits enemy first. enemy's health is lowered by damage taken.
   then enemy hits user, and player's health is lowered by damage taken.
   if player dies, calls func to end game. if enemy dies, ends combat."
  (let [new-enemy-health 
        (- enemy-health (- pc-damage (rand-int 3)))]
    (print-pc-damage-done enemy-health new-enemy-health)
    (if (<= new-enemy-health 0)
      [pc-health new-enemy-health]
      (let [new-pc-health (- pc-health 
                             (- (get-in (get-pc-loc pc-loc map-stack) [:enemy :damage]) (rand-int 3)))]
        (print-enemy-damage-done pc-health max-health new-pc-health)
        (if (<= new-pc-health 0)
          (pc-dead)
          [new-pc-health new-enemy-health])))))

(defn combat-round [pc-loc map-stack pc-health pc-damage max-health enemy-health]
  "Generates random 0 or 1: if 1, user goes first. if 0, enemy goes first."
  (if (= 1 (rand-int 2))
    (do
      (println "             You get the jump on it.")
      (let [[new-pc-health new-enemy-health] 
            (pc-first pc-loc map-stack pc-health pc-damage max-health enemy-health)]
        [new-pc-health new-enemy-health]))
    (do
      (println "             It gets the jump on you.")
      (let [[new-pc-health new-enemy-health] 
            (enemy-first pc-loc map-stack pc-health pc-damage max-health enemy-health)]
        [new-pc-health new-enemy-health]))))

(defn first-map [map-stack]
  "for run-away: pops back to original map to make sure player gets to safe place"
  (if (not= (first map-stack) level-1-map)
    (first-map (rest map-stack))
    map-stack))

(defn run-away [pc-loc map-stack pc-health max-health]
  "get one hit by enemy, then move player's location to loc-8."
  (println "             As your vision grows grey, you blindly turn and run to a safe place. Your attacker takes a swing at your back, but does not persue you.")
  (let [new-pc-health 
        (- pc-health (- (get-in (get-pc-loc pc-loc map-stack) [:enemy :damage]) (rand-int 3)))]
    (if (<= new-pc-health 0)
      (pc-dead)
      (do
        (print "             You take ")
        (print (- pc-health new-pc-health))
        (print " damage.")
        (println)
        (print-pc-health pc-health max-health)
        (println)
        (let [new-map-stack (first-map map-stack)]
          (let [new-row-pc-loc (assoc pc-loc :row 0)] 
            (let [new-pc-loc (assoc new-row-pc-loc :col 1)]
              (pause)
              [new-pc-loc new-map-stack new-pc-health])))))))

(defn open-combat []
  (with-open [rdr (io/reader "resources/combat.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn open-sword2 []
  (with-open [rdr (io/reader "resources/sword2.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))
    
(defn print-fight-start [pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health]
  "displays combat screen, enemy health, and your inventory."
  (open-combat)
  (print-enemy pc-loc map-stack)
  (open-sword2)
  (print-pc-health pc-health max-health)
  (println)
  (print "             Enemy health: ")
  (print enemy-health)
  (println)
  (println)
  (println "             Your inventory contains the following items:")
  (print-pc-inv pc-inv)
  (println))

(defn continue [pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health]
  "gets user input: either fight or drink a potion"
  (println "What else would you like to do?")
  (let [new-input (read-line)]
    (clear-screen)
    (print-fight-start pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health)
    new-input))

(defn im-not-dead-yet [pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health]
  "if enemy is not dead, get input for next round of combat"
  (let [input (read-line)]
    (clear-screen)
    (print-fight-start pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health)
    input))

(defn fought-boss? [pc-loc map-stack pc-inv pc-health]
  "checks if user fought and killed boss. If they did, prints this message, then calls pc-wins function."
  (if (= boss (get (get-pc-loc pc-loc map-stack) :enemy))
    (do
      (println "             Gathering all your remaining strength, you let out a yell and swing your weapon as hard as you can towards the old woman's head.
             The Master begins to pull her staff up to block you and chant a spell, but her age betrays her and her strength faulters.
             As she falls to the floor, you run over to the alter and smash your weapon on the strange spellbook again and again until the magical smoke fades.")
      (pause)
      (pc-wins))
    (do 
      (println "             With one last groan, your enemy falls dead at your feet. You win the fight!") 
      (pause)
      [pc-loc map-stack pc-inv pc-health false])))

(defn fight [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health enemy-health input]
  "interprets user input, to either run away, attack, or drink a potion. calls fucntions to make that happen."
  (cond
    (= input "r") (let [[new-pc-loc new-map-stack new-pc-health] (run-away pc-loc map-stack pc-health max-health)]
                    [new-pc-loc new-map-stack pc-inv new-pc-health true])
    (= input "d") (let [[new-pc-inv new-pc-health] (drink-hp pc-loc map-stack pc-inv pc-health max-health)]
                    (let [new-input (continue pc-loc map-stack new-pc-inv pc-eq new-pc-health max-health enemy-health)]
                      (fight pc-loc map-stack new-pc-inv pc-eq new-pc-health pc-damage max-health enemy-health new-input)))
    (= input "a") (let [[new-pc-health new-enemy-health] (combat-round pc-loc map-stack pc-health pc-damage max-health enemy-health)]
                    (if (<= new-enemy-health 0)
                      (fought-boss? pc-loc map-stack pc-inv new-pc-health)
                      (let [new-input (im-not-dead-yet pc-loc map-stack pc-inv pc-eq new-pc-health max-health new-enemy-health)]
                        (fight pc-loc map-stack pc-inv pc-eq new-pc-health pc-damage max-health new-enemy-health new-input))))
    :else (do
            (println "That's not valid input. What do you want to do?")
            (let [new-input (read-line)]
              (fight pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health enemy-health new-input)))))

(defn start-combat [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list]
  "gets enemy health, calls combat function (fight). Adds location to hit-list if enemy has been killed,
   doesn't if player ran away."
  (print-debug-hit-list "start-combat" hit-list)
  (let [enemy-health 
        (:health (:enemy (get-pc-loc pc-loc map-stack)))]
  (print-fight-start pc-loc map-stack pc-inv pc-eq pc-health max-health enemy-health) 
    (let [input (read-line)]
      (let [[new-pc-loc new-map-stack new-pc-inv new-pc-health alive?] 
            (fight pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health enemy-health input)]
        (if (= alive? false)
          ;enemy is dead, add to hit-list
          [new-pc-loc new-map-stack new-pc-inv new-pc-health (conj hit-list (get-pc-loc new-pc-loc new-map-stack))]
          ;enemy is not dead, leave hit-list the way it was
          [new-pc-loc new-map-stack new-pc-inv new-pc-health hit-list])))))

;menu and user options________________________________________________________________________________________

;moving normally________________________________________

(defn parse-user-input [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health]
  "called from check-move. parses user input.
  movement: dec/inc returns a new value that is one greater than the value passed in.
    assoc returns a new map of pc coordinates that is updated according to inc/dec
  objects: objects, loot, and inventory are placeholders
 'q' quits program (System call)"
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
      (= input "x") (System/exit 0) 
      :else (do 
              (println "That's not a valid choice.") 
              (pause) 
              [pc-loc 
               pc-inv 
               pc-eq 
               pc-health 
               pc-damage 
               max-health]))))

(defn open-user-menu []
  (with-open [rdr (io/reader "resources/user-menu.txt")]
    (doseq [line (line-seq rdr)]
      (println line))))

(defn check-move [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list]
  "takes pc-loc player coordinates and the map stack
  prints a menu of options for the user to see
  then calls parse-user-input
    parse-user-input returns an updated set of pc coordinates
  if a description can be pulled from the new pc-loc (from location data stored in map)
    the new move is valid and the new pc-loc is returned (along with the unchanged map stack). 
  if not, it's not a valid move, and the old pc-loc is returned (along with the unchanged map stack)"
  (open-main-menu)
  (print-debug-hit-list "check-move" hit-list)
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
    (print-debug-hit-list "check-move 2" hit-list)
    (if (:desc (get-pc-loc new-loc map-stack))
      (do
        [new-loc 
         map-stack 
         new-pc-inv 
         new-pc-eq 
         new-pc-health 
         new-pc-damage
         new-max-health
         hit-list])
      (do
        (if (not= (first map-stack) level-1-map)
          (println "There is a wall in that direction. You move back to where you were.")
          (println "You find yourself at the edge of the village and turn back to where you started."))
        (pause)
        (print-debug-hit-list "check-move 3" hit-list)
        [pc-loc 
         map-stack 
         new-pc-inv 
         new-pc-eq 
         new-pc-health 
         new-pc-damage
         new-max-health
         hit-list]))))

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
            |     y.....enter      |
            |   x......stay here   |
            |__    ___   __    ___ |
          =(________________________)=")
  (println)(println)(println)(println)(println))

(defn push-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list]
  "called from display-menu, takes in pc-loc and map-stack
  calls check-push-map, which returns an updated pc-coordinate location and map stack, and either true or false
  if true, new map has been loaded and the new pc-coordinates and map stack are returned to loop recur
  if false, calls noromal movement functions (check-move)"
  (print-debug-hit-list "push-control" hit-list)
  (print-enter pc-loc map-stack)
  (let [[new-loc new-map-stack true-false] 
        (check-push-map pc-loc map-stack)]
    ;will be true if a new map was pushed on 
    (print-debug-hit-list "push-control 2" hit-list)
    (if true-false
      ;load new map
      [new-loc 
       new-map-stack 
       pc-inv 
       pc-eq 
       pc-health 
       pc-damage
       max-health
       hit-list]
      ;move normally
      (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list))))

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
            |   area from here.    |
            |                      |
            |      y.....exit      |
            |   x.....stay here    |
            |__    ___   __    ___ |
          =(________________________)=")
  (println)(println)(println)(println)(println))

(defn pop-control [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list]
  "called from display-menu, takes in pc-loc and map-stack
  called from check-pop-map, which returns updated pc-loc, map stack, and either true/false
  if true, returns updated pc-loc and map-stack to loop-recur
  if false, calls normal movement functions (check-move)"
  (print-debug-hit-list "pop-control" hit-list)
  (print-exit)
  (let [[new-loc new-map-stack true-false] 
        (check-pop-map pc-loc map-stack)]
    (print-debug-hit-list "pop-control 2" hit-list)
    ;will be true if a map was popped off
    (if true-false
      ;load new map
      [new-loc 
       new-map-stack 
       pc-inv 
       pc-eq 
       pc-health 
       pc-damage
       max-health
       hit-list]
      ;move normally
      (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)))) 

;getting and parsing user input_____________________________
(defn display-menu [pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list]
  "checks to see if a place can be entered or exited from current player location.
  if yes, gives player the option to enter/exit
  if no, just displays normal movement functions (check-move)"
  (print-debug-hit-list "display-menu" hit-list)
  (if (:enemy (get-pc-loc pc-loc map-stack))
    (if (not (contains? hit-list (get-pc-loc pc-loc map-stack)))
      (let [[new-pc-loc new-map-stack new-pc-inv new-pc-health new-hit-list] 
            (start-combat pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)]
        (print-debug-hit-list "display-menu 2" hit-list)
        (clear-screen)
        (print-loc-desc new-pc-loc new-map-stack)
        (cond
          (:enter (get-pc-loc new-pc-loc new-map-stack)) (push-control new-pc-loc new-map-stack new-pc-inv pc-eq new-pc-health pc-damage max-health new-hit-list)
          (:exit-start-coords (get-pc-loc new-pc-loc new-map-stack)) (pop-control new-pc-loc new-map-stack new-pc-inv pc-eq new-pc-health pc-damage max-health new-hit-list)
          :else (check-move new-pc-loc new-map-stack new-pc-inv pc-eq new-pc-health pc-damage max-health new-hit-list)))
      (cond
        (:enter (get-pc-loc pc-loc map-stack)) (push-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)
        (:exit-start-coords (get-pc-loc pc-loc map-stack)) (pop-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)
        :else (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)))
    (cond
      (:enter (get-pc-loc pc-loc map-stack)) (push-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)
      (:exit-start-coords (get-pc-loc pc-loc map-stack)) (pop-control pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)
      :else (check-move pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list))))

;MAIN_____________________________________________________________________

(defn -main []
  ;these are function calls
  (open-title)
  (open-intro)
  (loop [pc-loc init-pc-loc map-stack init-map-stack pc-inv init-pc-inv pc-eq init-pc-eq pc-health init-pc-health pc-damage init-pc-damage max-health init-max-health hit-list init-hit-list]
      (do (clear-screen)
          (print-debug-hit-list "main" hit-list)
          (print-loc-desc pc-loc map-stack)
          (let [[new-loc map-stack new-pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health new-hit-list] 
                (display-menu pc-loc map-stack pc-inv pc-eq pc-health pc-damage max-health hit-list)]
            (print-debug-hit-list "main 2" new-hit-list)
            (recur 
              new-loc map-stack new-pc-inv new-pc-eq new-pc-health new-pc-damage new-max-health new-hit-list)))))
