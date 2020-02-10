(in-ns 'darkmoor.core)

;OBJECT DATA____________________________________________________________________________
;a mix of general objects and specialized objects for player to find at locations
; the function place-obj is called from each location below

(def wood-axe 
  {
   :name "Woodcutter's Axe"
   :slot :hands 
   :health 0 
   :damage [5, :slash]})

(def leather-armor
  {
   :name "Cracked Leather Armor" 
   :slot :body 
   :health 10
   :damage 0})

(def cult-dagger 
  {
   :name "Cultist's Eldrich Dagger"
   :slot :hand
   :health 0 
   :damage [5, :necro]})

(def flame-shield 
  {
   :name "Flaming Shield"
   :slot :hand 
   :health 3
   :damage [1, :arcane]})

(def butcher-gloves 
  {
   :name "Butcher's Chain Gloves"
   :slot :gloves 
   :health 3 
   :damage 0})

(def femur 
  {
   :name "Femur"
   :slot :hands 
   :health 0 
   :damage [5, "blunt"]})

(def half-plate 
  {
   :name "Dirty Half-Plate Armor"
   :slot :body 
   :health 15 
   :damage 0})

(def speed-boots
  {
   :name "Boots of Speed"
   :slot :boots
   :health 5 
   :damage 0})

(def sythe 
  {
   :name "Sharpened Sythe"
   :slot :hand
   :health 0 
   :damage [5, :slash]})

(def flail 
  {
   :name "Spiked Flail"
   :slot :hand
   :health 0 
   :damage [7, :pierce]})

(def pole-axe 
  {
   :name "Pole Axe"
   :slot :hands
   :health 0
   :damage [5, :slash]})

(def guard-chainmail 
  {
   :name "Town Guard's Chainmail"
   :slot :body 
   :health 20 
   :damage 0})

(def cult-robe 
  {
   :name "Cultist's Enchanted Robe"
   :slot :body 
   :health 10 
   :damage [3, :arcane]})

(def silver-sword 
  {
   :name "Fine Silver Sword"
   :slot :hand
   :health 0
   :damage [6, :slash]})

(def severed-arm 
  {
   :name "Severed Arm"
   :slot :hand
   :health 0
   :damage [5, :necro]})

(def warhammer
  {
   :name "Carved Warhammer"
   :slot :hands
   :health 0
   :damage [7, "blunt"]})

(def scepter 
  {
   :name "Archmage's Scepter"
   :slot :hands
   :health 0 
   :damage [10, :arcane]})

(def great-axe 
  {
   :name "Great Axe"
   :slot :hands
   :health 0
   :damage [6, :slash]})

(def spike-shield
  {
   :name "Spiked Shield"
   :slot :hand
   :health 4 
   :damage [1, :pierce]})

(def spike-gloves
  {
   :name "Spiked Gauntlets"
   :slot :gloves
   :health 3 
   :damage [1, :pierce]})

(def spike-shoulders 
  {
   :name "Spiked Pauldrons"
   :slot :shoulders 
   :health 3 
   :damage 0})

(def holy-mace 
  {
   :name "Mace of the Shining Sun"
   :slot :hand
   :health 0 
   :damage [5, :radiant]})

(def chair-leg
  {
   :name "Broken Chair Leg"
   :slot :hand
   :health 0
   :damage [4, "blunt"]})

(def broken-bottle
  {
   :name "Broken Wine Bottle"
   :slot :hand
   :health 0
   :damage [5, :slash]})

(def rum
  {:name "Bottle of Rum"
   :slot :potion
   :health 1
   :damage -1})

(def moonshine 
  {:name "Jug of Moonshine"
   :slot :potion
   :health -1
   :damage [1, "blunt"]})

(def holy-shield
  {
   :name "Shield of the Dawn"
   :slot :hand
   :health 3
   :damage [2, :radiant]})

(def holy-armor 
  {
   :name "Rising Sun Plate"
   :slot :body 
   :health 22
   :damage 0})

(def holy-ring 
  {
   :name "Ring of the Dawn"
   :slot :finger 
   :health 2
   :damage [2, :radiant]})

(def arcane-dagger
  {
   :name "Arcane Dagger"
   :slot :hand
   :health 0
   :damage [10, :arcane]})

(def hunt-knife
  {
   :name "Hunting Knife"
   :slot :hand
   :health 0 
   :damage [5 :slash]})

(def hunt-bow 
  {
   :name "Fine Hunting Bow"
   :slot :hands
   :health 2
   :damage [6 :pierce]})

(def necro-wand 
  {
   :name "Wand of Shadows"
   :slot :hand
   :health 0
   :damage [2 :necro]})

(def necro-amulet 
  {:name "Vampiric Amulet"
   :slot :neck 
   :health 2
   :damage [2 :necro]})

(def hp
  {:name "HEALTH POTION"
   :hp true})
   ;(int (* 0.25 (last (get player :health)))) ; 25% of player's total health

(def starting-sword
  {:name "HAND-ME-DOWN SWORD"
   :slot :hand
   :health 0
   :damage [3 :slash]})

(def starting-clothes
  {:name "TRAVELING CLOTHES"
   :slot :body 
   :health 5
   :damage 0})

(def loot-types
  {:temple-loot [holy-shield holy-armor holy-ring holy-mace arcane-dagger cult-robe]
   :graveyard-loot [wood-axe cult-dagger femur half-plate leather-armor cult-robe severed-arm]
   :guardhouse-loot [half-plate flail pole-axe guard-chainmail speed-boots silver-sword warhammer great-axe spike-shield spike-gloves spike-shoulders]
   :wizard-tower-loot [flame-shield scepter arcane-dagger speed-boots necro-wand necro-amulet]
   :house-loot [wood-axe butcher-gloves silver-sword hunt-knife hunt-bow leather-armor great-axe warhammer sythe]
   :ruins-loot [hunt-knife hunt-bow cult-dagger half-plate cult-robe chair-leg femur]
   :cave-loot [necro-wand necro-amulet severed-arm silver-sword cult-robe half-plate femur cult-dagger leather-armor]
   :tavern-loot [chair-leg broken-bottle rum moonshine wood-axe]
   :gen-loot [wood-axe leather-armor flame-shield femur severed-arm cult-dagger butcher-gloves 
              flail pole-axe guard-chainmail warhammer great-axe hunt-knife hunt-bow broken-bottle 
              holy-ring necro-amulet necro-wand spike-gloves spike-shoulders spike-shield silver-sword cult-robe sythe speed-boots]})
