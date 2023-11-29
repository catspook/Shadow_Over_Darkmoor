(in-ns 'darkmoor.core)

;OBJECT DATA____________________________________________________________________________
; objects for player to find at locations or on enemies

(def poison-ring
  {
    :id 44
    :name "Assassin's Ring"
    :slot :finger
    :health 2
    :damage 2
    :d-type "POISON"})

(def fire-wand
  {
    :id 43
    :name "Wand of Fire"
    :slot :hand
    :health 0
    :damage 7
    :d-type "FIRE"})

(def holy-robe
  {
    :id 42
    :name "Blessed Robe"
    :slot :body
    :health 10
    :damage 2
    :d-type "RADIANT"})

(def cult-armor
  {
    :id 41
    :name "Cultist's Eldritch Armor"
    :slot :body
    :health 20
    :damage 2
    :d-type "NECROTIC"})

(def poison-dagger
  {
    :id 40
    :name "Poisoned dagger"
    :slot :hand
    :health 0
    :damage 5
    :d-type "POISON"})

(def poison-bow
  {
    :id 39
    :name "Bow and Poisoned Arrows"
    :slot :hand
    :health 3
    :damage 7
    :d-type "POISON"})

(def torch
  {
    :id 38
    :name "Lit Torch"
    :slot :hand
    :health 1
    :damage 5
    :d-type "FIRE"})

(def cult-robe
  {
    :id 37
    :name "Cultist's Blood-Soaked Robe"
    :slot :body
    :health 10
    :damage 3
    :d-type "NECROTIC"})

(def wood-axe 
  {
   :id 36
   :name "Woodcutter's Axe"
   :slot :hand 
   :health 0 
   :damage 5
   :d-type "SLASHING"})

(def leather-armor
  {
   :id  35
   :name "Cracked Leather Armor" 
   :slot :body 
   :health 10
   :damage 0
   :d-type ""})

(def cult-dagger 
  {
   :id 34
   :name "Cultist's Cursed Dagger"
   :slot :hand
   :health 0 
   :damage 5 
   :d-type "NECROTIC"})

(def flame-shield 
  {
   :id 33
   :name "Flaming Shield"
   :slot :hand 
   :health 3
   :damage 1 
   :d-type "FIRE"})

(def butcher-gloves 
  {
   :id 32
   :name "Butcher's Chain Gloves"
   :slot :gloves 
   :health 3 
   :damage 0
   :d-type ""})

(def femur 
  {
   :id 31
   :name "Femur"
   :slot :hand 
   :health 0 
   :damage 5
   :d-type "BLUDGEONING"})

(def half-plate 
  {
   :id 30
   :name "Dirty Half-Plate Armor"
   :slot :body 
   :health 15 
   :damage 0
   :d-type ""})

(def speed-boots
  {
   :id 29
   :name "Boots of Speed"
   :slot :boots
   :health 5 
   :damage 0
   :d-type ""})

(def scythe 
  {
   :id 28
   :name "Sharpened Sythe"
   :slot :hand
   :health 0 
   :damage 5
   :d-type "SLASHING"})

(def flail 
  {
   :id 27
   :name "Spiked Flail"
   :slot :hand
   :health 0 
   :damage 7 
   :d-type "PIERCING"})

(def pole-axe 
  {
   :id 26
   :name "Pole Axe"
   :slot :hand
   :health 0
   :damage 5 
   :d-type "SLASHING"})

(def guard-chainmail 
  {
   :id 25
   :name "Town Guard's Chainmail"
   :slot :body 
   :health 20 
   :damage 0
   :d-type ""})

(def wizard-robe 
  {
   :id 24
   :name "Wizard's Enchanted Robe"
   :slot :body 
   :health 10 
   :damage 3 
   :d-type "ARCANE"})

(def silver-sword 
  {
   :id 23
   :name "Fine Silver Sword"
   :slot :hand
   :health 0
   :damage 6 
   :d-type "ARCANE"})

(def severed-arm 
  {
   :id 22
   :name "Severed Arm"
   :slot :hand
   :health 0
   :damage 5 
   :d-type "BLUDGEONING"})

(def warhammer
  {
   :id 21
   :name "Carved Warhammer"
   :slot :hand
   :health 0
   :damage 7
   :d-type "BLUDGEONING"})

(def scepter 
  {
   :id 20
   :name "Archmage's Scepter"
   :slot :hand
   :health 0 
   :damage 10 
   :d-type "ARCANE"})

(def great-axe 
  {
   :id 19
   :name "Great Axe"
   :slot :hand
   :health 0
   :damage 6 
   :d-type "SLASHING"})

(def spike-shield
  {
   :id 18
   :name "Spiked Shield"
   :slot :hand
   :health 4 
   :damage 1 
   :d-type "PIERCING"})

(def spike-gloves
  {
   :id 17
   :name "Spiked Gauntlets"
   :slot :gloves
   :health 3 
   :damage 1 
   :d-type "PIERCING"})

(def spike-shoulders 
  {
   :id 16
   :name "Spiked Pauldrons"
   :slot :shoulders 
   :health 3 
   :damage 0
   :d-type ""})

(def holy-mace 
  {
   :id 15
   :name "Mace of the Shining Sun"
   :slot :hand
   :health 0 
   :damage 5 
   :d-type "RADIANT"})

(def chair-leg
  {
   :id 14
   :name "Broken Chair Leg"
   :slot :hand
   :health 0
   :damage 4
   :d-type "BLUDGEONING"})

(def broken-bottle
  {
   :id 13
   :name "Broken Wine Bottle"
   :slot :hand
   :health 0
   :damage 5 
   :d-type "SLASHING"})

(def rum
  {
   :id 12
   :name "Bottle of Rum"
   :slot :potion
   :health 1
   :damage -1
   :d-type ""})

(def moonshine 
  {
   :id 11
   :name "Jug of Moonshine"
   :slot :potion
   :health -1
   :damage 1
   :d-type "BLUDGEONING"})

(def holy-shield
  {
   :id 10
   :name "Shield of the Dawn"
   :slot :hand
   :health 3
   :damage 2 
   :d-type "RADIANT"})

(def holy-armor 
  {
   :id 9
   :name "Rising Sun Plate"
   :slot :body 
   :health 22
   :damage 0
   :d-type ""})

(def holy-ring 
  {
   :id 8
   :name "Ring of the Dawn"
   :slot :finger 
   :health 2
   :damage 2 
   :d-type "RADIANT"})

(def arcane-dagger
  {
   :id 7
   :name "Arcane Dagger"
   :slot :hand
   :health 0
   :damage 7
   :d-type "ARCANE"})

(def hunt-knife
  {
   :id 6
   :name "Hunting Knife"
   :slot :hand
   :health 0 
   :damage 5 
   :d-type "SLASHING"})

(def hunt-bow 
  {
   :id 5
   :name "Fine Hunting Bow"
   :slot :hand
   :health 2
   :damage 6 
   :d-type "PIERCING"})

(def necro-wand 
  {
   :id 4
   :name "Wand of Shadows"
   :slot :hand
   :health 0
   :damage 2 
   :d-type "NECROTIC"})

(def necro-amulet 
  {
   :id 3
   :name "Vampiric Amulet"
   :slot :neck 
   :health 2
   :damage 2 
   :d-type "NECROTIC"})

(def hp
  {:name "HEALTH POTION"
   :hp true})

(def starting-sword
  {
   :id 2
   :name "Hand-Me-Down Sword"
   :slot :hand
   :health 0
   :damage 3 
   :d-type "SLASHING"})

(def starting-clothes
  {
   :id 1
   :name "Traveling Clothes"
   :slot :body 
   :health 5
   :damage 0
   :d-type ""})

(def id-obj 
  {
   1 starting-clothes
   2 starting-sword
   3 necro-amulet
   4 necro-wand 
   5 hunt-bow
   6 hunt-knife
   7 arcane-dagger
   8 holy-ring
   9 holy-armor 
   10 holy-shield 
   11 moonshine
   12 rum
   13 broken-bottle 
   14 chair-leg 
   15 holy-mace 
   16 spike-shoulders 
   17 spike-gloves 
   18 spike-shield 
   19 great-axe 
   20 scepter 
   21 warhammer 
   22 severed-arm 
   23 silver-sword 
   24 wizard-robe 
   25 guard-chainmail 
   26 pole-axe 
   27 flail 
   28 scythe 
   29 speed-boots 
   30 half-plate 
   31 femur 
   32 butcher-gloves 
   33 flame-shield 
   34 cult-dagger 
   35 leather-armor 
   36 wood-axe
   37 cult-robe
   38 torch
   39 poison-bow
   40 poison-dagger
   41 cult-armor
   42 holy-robe
   43 fire-wand
   44 poison-ring})

(def loot-types
  {:temple-loot [holy-shield holy-armor holy-ring holy-mace holy-robe]
                 ; holy
   :wizard-tower-loot [flame-shield scepter arcane-dagger speed-boots wizard-robe fire-wand]
                       ; arcane
   :tavern-loot [chair-leg broken-bottle rum moonshine wood-axe butcher-gloves torch]
   :alchemy-loot [poison-bow poison-dagger poison-ring]
                  ; poison
   :haunted-house-loot [femur severed-arm silver-sword poison-dagger poison-bow scythe poison-ring necro-amulet torch necro-wand flame-shield]
                        ; grave + poison + cult
   :cave-loot [necro-wand necro-amulet severed-arm cult-robe cult-dagger cult-armor torch leather-armor half-plate spike-gloves spike-shoulders]
               ; grave + cult
   :graveyard-loot [wood-axe cult-dagger femur half-plate leather-armor cult-robe severed-arm torch]
                    ; grave
   :armory-loot [half-plate flail pole-axe guard-chainmail speed-boots silver-sword warhammer great-axe spike-shield spike-gloves spike-shoulders]
                 ; fighter 
   :archive-loot [wizard-robe speed-boots arcane-dagger guard-chainmail warhammer silver-sword]
                  ; fighter + arcane
   :gen-loot [wood-axe leather-armor butcher-gloves hunt-knife hunt-bow scythe torch]})
