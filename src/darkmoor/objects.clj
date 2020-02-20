(in-ns 'darkmoor.core)

;OBJECT DATA____________________________________________________________________________
;a mix of general objects and specialized objects for player to find at locations
; the function place-obj is called from each location below

(def wood-axe 
  {
   :id 36
   :name "Woodcutter's Axe"
   :slot :hands 
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
   :name "Cultist's Eldrich Dagger"
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
   :d-type "ARCANE"})

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
   :slot :hands 
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
   :slot :hands
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

(def cult-robe 
  {
   :id 24
   :name "Cultist's Enchanted Robe"
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
   :d-type "SLASHING"})

(def severed-arm 
  {
   :id 22
   :name "Severed Arm"
   :slot :hand
   :health 0
   :damage 5 
   :d-type "NECROTIC"})

(def warhammer
  {
   :id 21
   :name "Carved Warhammer"
   :slot :hands
   :health 0
   :damage 7
   :d-type "BLUDGEONING"})

(def scepter 
  {
   :id 20
   :name "Archmage's Scepter"
   :slot :hands
   :health 0 
   :damage 10 
   :d-type "ARCANE"})

(def great-axe 
  {
   :id 19
   :name "Great Axe"
   :slot :hands
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
   :damage 10 
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
   :slot :hands
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
   :name "HAND-ME-DOWN SWORD"
   :slot :hand
   :health 0
   :damage 3 
   :d-type "SLASHING"})

(def starting-clothes
  {
   :id 1
   :name "TRAVELING CLOTHES"
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
   24 cult-robe 
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
   36 wood-axe})

(def loot-types
  {:temple-loot [holy-shield holy-armor holy-ring holy-mace arcane-dagger cult-robe]
   :graveyard-loot [wood-axe cult-dagger femur half-plate leather-armor cult-robe severed-arm]
   :guardhouse-loot [half-plate flail pole-axe guard-chainmail speed-boots silver-sword warhammer great-axe spike-shield spike-gloves spike-shoulders]
   :wizard-tower-loot [flame-shield scepter arcane-dagger speed-boots necro-wand necro-amulet]
   :house-loot [wood-axe butcher-gloves silver-sword hunt-knife hunt-bow leather-armor great-axe warhammer scythe]
   :ruins-loot [hunt-knife hunt-bow cult-dagger half-plate cult-robe chair-leg femur]
   :cave-loot [necro-wand necro-amulet severed-arm silver-sword cult-robe half-plate femur cult-dagger leather-armor]
   :tavern-loot [chair-leg broken-bottle rum moonshine wood-axe]
   :gen-loot [wood-axe leather-armor flame-shield femur severed-arm cult-dagger butcher-gloves 
              flail pole-axe guard-chainmail warhammer great-axe hunt-knife hunt-bow broken-bottle 
              holy-ring necro-amulet necro-wand spike-gloves spike-shoulders spike-shield silver-sword cult-robe scythe speed-boots]})
