(in-ns 'darkmoor.core)

;OBJECT DATA____________________________________________________________________________
;a mix of general objects and specialized objects for player to find at locations
; the function place-obj is called from each location below

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
