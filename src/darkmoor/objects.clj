(in-ns 'darkmoor.core)

;OBJECT DATA____________________________________________________________________________
;a mix of general objects and specialized objects for player to find at locations
; the function place-obj is called from each location below

(def o1
  {:potion false
   :desc "WOODCUTTER'S AXE: Blunted and notched. +3 SLASHING DAMAGE" 
   :name "Woodcutter's Axe"
   :slot [:hand 1]
   :weapon true 
   :tag [:o1 "wa"]
   :health 0 
   :damage 3})
(def o2
  {:potion false
   :desc "OLD LEATHER ARMOR: Better than fighting in your traveling clothes. +10 HEALTH" 
   :name "Old Leather Armor" 
   :slot [:body 1]
   :armor true
   :tag [:o2 "la"]
   :health 10
   :damage 0})
(def o3
  {:potion false
   :desc "CULTIST'S DAGGER: A strange symbol carved on the blade glows with an unearthly purple light. +5 NECROTIC DAMAGE" 
   :name "Cultist's Eldrich Dagger"
   :slot [:hand 1]
   :weapon true
   :tag [:o3 "cd"]
   :health 0 
   :damage 5})
(def o4
  {:potion false
   :desc "FIRE SHIELD: Flames lick around the edges of this shield, but thankfully the handle is not red-hot. +3 HEALTH, +1 FIRE DAMAGE" 
   :name "Flaming Shield"
   :slot [:hand 1]
   :weapon true 
   :tag [:o4 "fs"] 
   :health 3
   :damage 1})
(def o5
  {:potion false
   :desc "BUTCHER'S GLOVES: These stink, but your fingers have never felt so protected. +3 HEALTH" 
   :name "Butcher's Chain Gloves"
   :slot [:arms 1]
   :armor true
   :tag [:o5 "bg"] 
   :health 3 
   :damage 0})
(def o6
  {:potion false
   :desc "FEMUR: Makes a good club, but takes both of your hands to carry. +5 BLUNT DAMAGE" 
   :name "Femur"
   :slot [:hand 2]
   :weapon true
   :tag [:o6 "f"] 
   :health 0 
   :damage 5})
(def o7
  {:potion false
   :desc "HALF-PLATE ARMOR: Caked in dirt. You're pretty sure that no one living has worn this recently. +15 HEALTH" 
   :name "Dirty Half-Plate Armor"
   :slot [:body 1]
   :armor true
   :tag [:o7 "hpa"]
   :health 15 
   :damage 0})
(def o8
  {:potion false
   :desc "ETHERIAL BOOTS: You feel just a bit less present while wearing these. +5 HEALTH" 
   :name "Etherial Boots"
   :slot [:feet 1]
   :armor true
   :tag [:o8 "eb"]
   :health 5 
   :damage 0})
(def o9
  {:potion false
   :desc "SHARPENED SYTHE: More of a farmer's weapon than Death's. +3 SLASHING DAMAGE" 
   :name "Sharpened Sythe"
   :slot [:hand 1]
   :weapon true
   :tag [:o9 "ss"]
   :health 0 
   :damage 3})
(def o10
  {:potion false
   :desc "SPIKED FLAIL: Screaming faces are engraved on the handle, and the spikes are coated in gore. +7 PIERCING DAMAGE" 
   :name "Spiked Flail"
   :slot [:hand 2]
   :weapon true
   :tag [:o10 "sf"]
   :health 0 
   :damage 7})
(def o11
  {:potion false
   :desc "POLE-AXE: Literally a hand axe tied to a long stick. +4 SLASHING DAMAGE"
   :name "Pole Axe"
   :slot [:hand 1]
   :weapon true
   :tag [:o11 "pa"]
   :health 0
   :damage 4})
(def o12
  {:potion false
   :desc "FINE CHAINMAIL: Finely-made armor consisting of thousands of tiny, silver rings. +20 HEALTH"
   :name "Fine Chainmail"
   :slot [:body 1]
   :armor true
   :tag [:o12 "fc"]
   :health 20 
   :damage 0})
(def o13
  {:potion false
   :desc "CULTIST'S ROBE: A thick, wool robe embroidered with enchanted runes that do damage to enemies while protecting the wearer. +10 HEALTH, +3 NECROTIC DAMAGE"
   :name "Cultist's Enchanted Robe"
   :slot [:body 1]
   :armor true
   :tag [:o13 "cer"]
   :health 10 
   :damage 3})
(def o14
  {:potion false
   :desc "FINE SILVER SWORD: A thin blade with a decorative eagle carved around the handle. +6 PIERCING DAMAGE"
   :name "Fine Silver Sword"
   :slot [:hand 1]
   :weapon true
   :tag [:o14 "fss"]
   :health 0
   :damage 6})
(def o15
  {:potion false
   :desc "SEVERED ARM: It's just like a club. A squishy, stinking club. +4 NECROTIC DAMAGE"
   :name "Severed Arm"
   :slot [:hand 1]
   :weapon true
   :tag [:o15 "saw"]
   :health 0
   :damage 4})
(def o16
  {:potion false
   :desc "WARHAMMER: A large, heavy stone hammer carved with intricate geometric designs. +7 BLUNT DAMAGE"
   :name "Carved Warhammer"
   :slot [:hand 2]
   :weapon true
   :tag [:o16 "wh"]
   :health 0
   :damage 7})
(def o17
  {:potion false
   :desc "RUSTED BOW AND ARROW: A half-rotted bow and a handful of rusted arrows. This weapon is ranged, but requires both hands to use. +2 HEALTH, 5 PIERCING DAMAGE"
   :name "Rusted Bow And Arrow"
   :slot [:hand 2]
   :weapon true
   :tag [:o17 "rba"]
   :health 2
   :damage 5})
(def o18
  {:potion false
   :desc "GREAT AXE: Passed down from generation to generation, this once was a prized family heirloom. It's yours now. +6 SLASHING DAMAGE"
   :name "Great Axe"
   :slot [:hand 2]
   :weapon true
   :tag [:o18 "ga"]
   :health 0
   :damage 6})
(def o19
  {:potion false
   :desc "SPIKED SHIELD: Long spikes protrude from the center of this shield. +4 HEALTH, +1 PIERCING DAMAGE"
   :name "Spiked Shield"
   :slot [:hand 1]
   :weapon true
   :tag [:o19 "ssh"]
   :health 4 
   :damage 1})
(def o20
  {:potion false
   :desc "SPIKED GAUNTLETS: Finely made from a previous age, if a bit dusty. +3 HEALTH, +1 PIERCING DAMAGE"
   :name "Spiked Gauntlets"
   :slot [:arms 1]
   :armor true
   :tag [:o20 "sg"]
   :health 3 
   :damage 1})
(def o21
  {:potion false
   :desc "SPIKED PAULDRONS: Your neck will be somewhat more protected by these large spikes. Just don't bend your head too far. +3 HEALTH"
   :name "Spiked Pauldrons"
   :slot [:shoulders 1]
   :armor true
   :tag [:o21 "sp"]
   :health 3 
   :damage 0})
(def o22
  {:potion false
   :desc "TORN LETTER: This blood-spattered letter contains instructions to a cultist from the 'MASTER', who is headquartered in the basement of the Temple." 
   :name "Spiked Pauldrons"
   :other true
   :tag [:o22 "tl"]
   :health 0
   :damage 0})
(def o23
  {:potion false
   :desc "BARREL OF DEAD FISH: You don't think you could get within a foot of this without throwing up."
   :name "Barrel Of Dead Fish"
   :other true
   :tag [:o23 "bdf"]
   :health 0
   :damage 0})
(def o24
  {:potion false
   :desc "HEMP ROPE: It's so rotten it snaps as soon as you pick it up."
   :name "Help Rope"
   :other true
   :tag [:o24 "hr"]
   :health 0
   :damage 0})
(def o25
  {:potion false
   :desc "BARREL OF ROTTEN GRAIN: Not quite beer yet."
   :name "Barrel of Rotten Grain"
   :other true
   :tag [:o25 "brg"]
   :health 0
   :damage 0})
(def o26
  {:potion false
   :desc "RUSTED HORSESHOE: You're not a centaur, so you're not sure what you'd do with this." 
   :name "Rusted Horseshoe"
   :other true
   :tag [:o26 "rhs"]
   :health 0
   :damage 0})
(def o27
  {:potion false
   :desc "BROKEN BAR STOOL: A little unweildy, but heavy. Getting hit with this would hurt. +6 BLUNT DAMAGE, -2 HEALTH"
   :name "Broken Bar Stool"
   :slot [:hand 2]
   :weapon true
   :tag [:o27 "bbs"]
   :health -2
   :damage 6})
(def o28
  {:potion false
   :desc "CHAIR LEG: Made of sturdy oak. +4 BLUNT DAMAGE"
   :name "Broken Chair Leg"
   :slot [:hand 1]
   :weapon true
   :tag [:o28 "bcl"]
   :health 0
   :damage 4})
(def o29
  {:potion false
   :desc "BROKEN WINE BOTTLE: Sharp as glass. +4 PIERCING DAMAGE"
   :name "Broken Wine Bottle"
   :slot [:hand 1]
   :weapon true
   :tag [:o29 "bwb"]
   :health 0
   :damage 4})
(def o30
  {:desc "RUM: Gives the drinker the Sea Legs effect while EQUIPPED, allowing them to evade more attacks (+1 HEALTH), but reducing their aim (-1 DAMAGE)." 
   :name "Bottle of Rum"
   :potion true
   :tag [:o30 "rum"]
   :health 1
   :damage -1})
(def o31
  {:desc "MOONSHINE: Gives the drinker the Drunken Rage effect while EQUIPPED, allowing them to do an extra +1 DAMAGE at the cost of -1 HEALTH."
   :name "Jug of Moonshine"
   :potion true
   :tag [:o31 "jom"]
   :health -1
   :damage 1})
(def o32
  {:potion false
   :desc "TEMPLAR SHIELD: A shield painted with a yellow rising sun. +3 HEALTH, +2 HOLY DAMAGE"
   :name "Templar Shield"
   :slot [:hand 1]
   :weapon true
   :tag [:o32 "tsh"]
   :health 3
   :damage 2})
(def o33
  {:potion false
   :desc "TEMPLAR ARMOR: Plate mail emblazoned with a gold rising sun on the breastplate. It has been blessed to provide a bit of extra protection. +22 HEALTH"
   :name "Templar Armor"
   :slot [:body 1]
   :armor true
   :tag [:o33 "ta"]
   :health 22
   :damage 0})
(def o34
  {:potion false
   :desc "RING OF THE MOON: An onyx ring set with a large moonstone. +2 ICE DAMAGE"
   :name "Ring of the Moon"
   :slot [:finger 1]
   :armor true
   :tag [:o34 "rom"]
   :health 0
   :damage 2})
(def o35
  {:potion false
   :desc "ARCANE DAGGER: Made of a opalescent crystaline material and as cold as ice. +10 ARCANE DAMAGE"
   :name "Arcane Dagger"
   :slot [:hand 1]
   :weapon true
   :tag [:o35 "ad"]
   :health 0
   :damage 10})
(def o36
  {:potion false
   :desc "HUNTING BOW: Meant for hunting game, but works for people too. +1 HEALTH, +5 PIERCING DAMAGE"
   :name "Hunting Bow"
   :slot [:hand 2]
   :weapon true
   :tag [:o36 "hb"]
   :health 1
   :damage 5})
(def o37
  {:potion false
   :desc "HUNTING KNIFE: A long knife meant for mercifully ending an animal's life. +5 SLASHING DAMAGE"
   :name "Hunting Knife"
   :slot [:hand 1]
   :weapon true
   :tag [:o37 "hkn"]
   :health 0 
   :damage 5})
(def o38
  {:potion false
   :desc "FINE HUNTING BOW: The better version that the nobility can afford. You can stand farther back from enemies with this bow than with other bows. +2 HEALTH, +6 DAMAGE"
   :name "Fine Hunting Bow"
   :slot [:hand 2]
   :weapon true
   :tag [:o38 "fhb"]
   :health 2
   :damage 6})
(def o39
  {:potion false
   :desc "WAND OF SHADOWS: This twisted piece of wood somehow blasts its opponent with black, necrotic energy. You're not too sure how it works. +6 NECROTIC DAMAGE"
   :name "Wand of Shadows"
   :slot [:hand 1]
   :weapon true
   :tag [:o39 "wos"]
   :health 0
   :damage 6})
(def o40
  {:potion false
   :desc "VAMPIRIC AMULET: A blood-red gem sits in the center of this tarnished silver neclace. During combat, this amulet syphons health from your opponent to you. +2 HEALTH, +2 NECROTIC DAMAGE"
   :name "Vampiric Amulet"
   :slot [:neck 1]
   :armor true
   :tag [:o40 "va"]
   :health 2
   :damage 2})
(def h1
  {:desc "LESSER HEALTH POTION: This potion glimmers with a healthy green glow. After drinking this, you will regain 10 health." 
   :name "Lesser Health Potion"
   :potion true
   :tag [:h1 "hp1"] 
   :health 10 
   :damage 0})
(def h2
  {:potion true 
   :desc "COMMON HEALTH POTION: This potion shines a bright, sunshine yellow. After drinking this, you will regain 15 health." 
   :name "Common Health Potion"
   :tag [:h2 "hp2"]
   :health 15 
   :damage 0})
(def h3
  {:potion true 
   :desc "GREATER HEALTH POTION: This potion ruby red sparkles cheerfully in the gloom. After drinking this, you will regain 20 health." 
   :name "Greater Health Potion"
   :tag [:h3 "hp3"]
   :health 20 
   :damage 0})
(def init1
  {:potion false
   :desc "STANDARD ISSUE SWORD: +2 PIERCING DAMAGE"
   :name "Standard Issue Sword"
   :slot [:hand 1]
   :weapon true
   :tag [:init1 "sis"]
   :health 0
   :damage 2})
(def init2
  {:potion false
   :desc "TRAVELING CLOTHES: +5 HEALTH"
   :name "Traveling Clothes"
   :slot [:body 1]
   :armor 1
   :tag [:init2 "tc"]
   :health 5
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
  [init1 init2])

;initial equiped list
(def init-pc-eq
  [init1 init2])
