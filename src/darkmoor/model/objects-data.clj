(in-ns 'darkmoor.core)

;OBJECT DATA____________________________________________________________________________
;a mix of general objects and specialized objects for player to find at locations
; the function place-obj is called from each location below

(def butcher-knife
  {
   :id 57
   :name "Butcher's Knife"
   :slot :hand
   :2-hand false 
   :health 0
   :damage 6
   :d-type "SLASH"})

(def scimitar
  {
   :id 56
   :name "Jeweled Scimitar"
   :slot :hand
   :2-hand false
   :health 0
   :damage 6
   :d-type "SLASH"})

(def embalming-knife
  {
   :id 55
   :name "Embalmer's Knife"
   :slot :hand
   :2-hand false 
   :health 0
   :damage 5
   :d-type "SLASH"})

(def archive-book
  {
   :id 54
   :name "Archive Reference Book"
   :slot :hand
   :2-hand true
   :health 0
   :damage 8
   :d-type "BLUNT"})

(def brass-knuckles
  {
   :id 53
   :name "Brass Knuckles"
   :slot :hand
   :2-hand false
   :health 0
   :damage 5
   :d-type "BLUNT"})

(def walking-stick
  {
   :id 52
   :name "Oak Walking Stick"
   :slot :hand
   :2-hand true
   :health 0
   :damage 8
   :d-type "BLUNT"})

(def rusted-mace
  {
   :id 51
   :name "Rusted Mace"
   :slot :hand
   :2-hand false 
   :health 0
   :damage 5
   :d-type "BLUNT"})

(def rapier
  {
   :id 50
   :name "Duelist's Rapier"
   :slot :hand
   :2-hand true
   :health 0
   :damage 10
   :d-type "PIERCING"})

(def stiletto
  {
   :id 49
   :name "Sharpened Letter Opener"
   :slot :hand
   :2-hand false 
   :health 0
   :damage 6
   :d-type "PIERCE"})

(def spear
  {
   :id 48
   :name "Town Guard's Spear"
   :slot :hand
   :2-hand false
   :health 0
   :damage 7
   :d-type "PIERCE"})

(def poison-necklace
  {
   :id 46
   :name "Lady Ioletta's Choker"
   :slot :neck
   :light? true
   :health 1
   :damage 1
   :d-type "POISON"})

(def poison-bow
  {
   :id 45
   :name "Bow of the Viper"
   :slot :hand
   :2hand? true 
   :health 2
   :damage 8
   :d-type "POISON"})

(def poison-dagger
  {
   :id 44
   :name "Scorpion's Stinger"
   :slot :hand
   :2hand? false
   :health 0
   :damage 7
   :d-type "POISON"})

(def ice-knife  
  {
   :id 43
   :name "Ice Knife"
   :slot :hand
   :2hand? false
   :health 0
   :damage 6
   :d-type "FROST"})

(def duelist-armor
  {
   :id 42
   :name "Duelist's Padded Armor"
   :slot :body
   :light? true
   :health 15
   :damage 0 
   :d-type ""})

(def scale-mail
  {
   :id 40
   :name "Standard Issue Mail"
   :slot :body
   :light? false
   :health 5
   :damage 0
   :d-type ""})

(def ragged-leathers 
  {
   :id 39
   :name "Ragged Leathers"
   :slot :body
   :light? true
   :health 5
   :damage 0
   :d-type ""})

(def acolyte-robe
  {
   :id 38
   :name "Acolyte's Robe"
   :slot :body
   :light? true
   :health 5
   :damage 0
   :d-type ""})

(def frost-staff 
  {
   :id 37
   :name "Staff of Frostbite"
   :slot :hand 
   :2hand? true
   :health 0 
   :damage 7
   :d-type "FROST"})

(def leather-armor
  {
   :id  35
   :name "Studded Leather Armor" 
   :slot :body 
   :light? false
   :health 10
   :damage 0
   :d-type ""})

(def cult-dagger 
  {
   :id 34
   :name "Eldrich Dagger"
   :slot :hand
   :2hand? false
   :health 0 
   :damage 5 
   :d-type "NECROTIC"})

(def flame-shield 
  {
   :id 33
   :name "Flaming Shield"
   :slot :hand 
   :2hand? false
   :health 3
   :damage 1 
   :d-type "FLAME"})

(def butcher-gloves 
  {
   :id 32
   :name "Butcher's Chain Gloves"
   :slot :gloves 
   :light? false
   :health 3 
   :damage 0
   :d-type ""})

(def femur 
  {
   :id 31
   :name "Femur"
   :slot :hand 
   :2hand? false
   :health 0 
   :damage 5
   :d-type "BLUNT"})

(def half-plate 
  {
   :id 30
   :name "Dirty Half-Plate Armor"
   :slot :body 
   :light? false
   :health 15 
   :damage 0
   :d-type ""})

(def speed-boots
  {
   :id 29
   :name "Boots of Speed"
   :slot :boots
   :light? true
   :health 5 
   :damage 0
   :d-type ""})

(def scythe 
  {
   :id 28
   :name "Sharpened Sythe"
   :slot :hand
   :2hand? false
   :health 0 
   :damage 5
   :d-type "SLASH"})

(def flail 
  {
   :id 27
   :name "Bloodstained Flail"
   :slot :hand
   :2hand? false
   :health 0 
   :damage 7 
   :d-type "PIERCING"})

(def guard-chainmail 
  {
   :id 25
   :name "Town Guard's Chainmail"
   :slot :body 
   :light? false
   :health 20 
   :damage 0
   :d-type ""})

(def cult-robe 
  {
   :id 24
   :name "Smoldering Robe"
   :slot :body 
   :light? true
   :health 10 
   :damage 2
   :d-type "FLAME"})

(def severed-arm 
  {
   :id 22
   :name "Severed Arm"
   :slot :hand
   :2hand? false
   :health 0
   :damage 5 
   :d-type "BLUNT"})

(def warhammer
  {
   :id 21
   :name "Carved Warhammer"
   :slot :hand
   :2hand? true
   :health 0
   :damage 8
   :d-type "BLUNT"})

(def scepter 
  {
   :id 20
   :name "Frozen Scepter of Cerebus"
   :slot :hand
   :2hand? true
   :health 0 
   :damage 10 
   :d-type "FROST"})

(def great-axe 
  {
   :id 19
   :name "Great Axe"
   :slot :hand
   :2hand? true
   :health 0
   :damage 7
   :d-type "SLASH"})

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
   :name "Edgy Spiked Gloves"
   :slot :gloves
   :light? true
   :health 3 
   :damage 1 
   :d-type "PIERCING"})

(def spike-shoulders 
  {
   :id 16
   :name "Edgy Spiked Collar"
   :slot :neck
   :light? true
   :health 1
   :damage 1
   :d-type "PIERCING"})

(def holy-mace 
  {
   :id 15
   :name "Mace of the Shining Sun"
   :slot :hand
   :2hand? false
   :health 0 
   :damage 6
   :d-type "RADIANT"})

(def chair-leg
  {
   :id 14
   :name "Broken Chair Leg"
   :slot :hand
   :2hand? false
   :health 0
   :damage 5
   :d-type "BLUNT"})

(def broken-bottle
  {
   :id 13
   :name "Broken Wine Bottle"
   :slot :hand
   :2hand? false
   :health 0
   :damage 7
   :d-type "SLASH"})

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
   :d-type "BLUNT"})

(def holy-shield
  {
   :id 10
   :name "Shield of the Dawn"
   :slot :hand
   :health 2
   :damage 2 
   :d-type "RADIANT"})

(def holy-armor 
  {
   :id 9
   :name "Rising Sun Plate Mail"
   :slot :body 
   :light? false
   :health 20
   :damage 0
   :d-type ""})

(def holy-ring 
  {
   :id 8
   :name "Ring of the Dawn"
   :slot :finger 
   :light? true
   :health 1
   :damage 1
   :d-type "RADIANT"})

(def flame-staff
  {
   :id 7
   :name "Wand of Fireballs"
   :slot :hand
   :2hand? true 
   :health 0
   :damage 8
   :d-type "FLAME"})

(def hunt-knife
  {
   :id 6
   :name "Hunting Knife"
   :slot :hand
   :2hand? false
   :health 0 
   :damage 5 
   :d-type "SLASH"})

(def hunt-bow 
  {
   :id 5
   :name "Fine Hunting Bow"
   :slot :hand
   :2hand? true
   :health 2
   :damage 7 
   :d-type "PIERCING"})

(def necro-wand 
  {
   :id 4
   :name "Wand of Shadows"
   :slot :hand
   :2hand? true
   :health 0
   :damage 5 
   :d-type "NECROTIC"})

(def necro-amulet 
  {
   :id 3
   :name "Vampiric Amulet"
   :slot :neck 
   :light? true
   :health 1
   :damage 1
   :d-type "NECROTIC"})

(def starting-sword
  {
   :id 2
   :name "Blunted Sword"
   :slot :hand
   :2hand? false
   :health 0
   :damage 3 
   :d-type "SLASH"})

(def id-obj 
  {
   2 starting-sword
   3 necro-amulet
   4 necro-wand 
   5 hunt-bow
   6 hunt-knife
   7 flame-staff
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
   24 cult-robe 
   25 guard-chainmail 
   27 flail 
   28 scythe 
   29 speed-boots 
   30 half-plate 
   31 femur 
   32 butcher-gloves 
   33 flame-shield 
   34 cult-dagger 
   35 leather-armor 
   37 frost-staff
   38 acolyte-robe
   39 ragged-leathers
   40 scale-mail
   42 duelist-armor
   43 ice-knife
   44 poison-dagger
   45 poison-bow
   46 poison-necklace
   48 spear
   49 stiletto
   50 rapier
   51 rusted-mace
   52 walking-stick
   53 brass-knuckles
   54 archive-book
   55 butcher-knife
   56 scimitar
   57 embalming-knife
   })

; non-magic damage is twice as common, magic damage appears at locations that make sense
(def loot-types
  {
   :temple-loot [holy-ring holy-armor holy-shield acolyte-robe holy-mace flame-shield]
   :graveyard-loot [necrotic-amulet necrotic-wand cult-dagger embalming-knife femur severed-arm]
   :armory-loot [spear starting-sword great-axe warhammer guard-chainmail scale-mail flail spiked-shield]
   :wizard-tower-loot [scepter flame-staff frost-staff cult-robe ice-knife]
   :haunted-house-loot [spiked-shoulders spiked-goves necrotic-amulet]
   :archive-loot [archive-book stiletto]
   :mansion-loot [scimitar rapier duelist-armor ]
   :stables-loot [leather-armor hunter-bow hunter-knife]
   :store-loot [rum broken-bottle butcher-knife butcher-gloves speed-boots]
   :tavern-loot [moonshine rum broken-bottle chair-leg]
   :garden-loot [poison-dagger poison-bow poison-necklace]
   :cult-loot [femur severed-arm embalming-knife ]
   :outside-loot [walking-stick scythe rusted-mace scale-mail ragged-leathers]
   })
