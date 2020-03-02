(in-ns 'darkmoor.core)

;ENEMY DESCRIPTIONS
; includes: :weak -- damage types the creature is weak to
;           :desc -- list of descriptions for each enemy of that type (name, fighing description, and damage type)

(def wizard 
  {:weak ["BLUDGEONING" "PIERCING" "NECROTIC"]
   :desc [["A WITCH PLAYING THE BANJO" "She hits you with some out-of-tune chords. " "sound"]
           ["A WARLOCK PRACTING THEIR SPELLS" "They practice throwing a fireball at your face. " "ARCANE"]]})

(def townsfolk 
  {:weak ["BLUDGEONING" "NECROTIC" "PIERCING"]
   :desc [["AN ENTRANCED FARMER DRAGGING A PITCHFORK" "He jabs the pitchfolk vaguely in your direction. " "piercing"]
           ["A RELUCTANTLY VIOLENT SEAMSTRESS" "She quietly apologizes as she tries to stick you with a pin. " "piercing"]]})

(def cultist 
  {:weak ["NECROTIC" "PIERCING" "RADIANT"]
   :desc [["A CREEPILY CHEERFUL CULTIST" "He sings a cheerful song about sacrificing you to The Old Gods. " "spooky"]
           ["A CRAZED CLULTIST" "She shrieks wildly and swings an evil-looking dagger. " "piercing"]]})

(def werewolf 
  {:weak ["NECROTIC" "ARCANE" "RADIANT"]
   :desc [["A WEREWOLF DUMPSTER-DIVING FOR FOOD" "She flings a half-chewed chicken bone at you. " "bludgeoning"]
           ["A WEREWOLF WITH A CHINESE MENU IN ITS HAND" "He decides to make you into chow mein instead. " "slashing"]
           ["A VERY HAIRY WEREWOLF" "She tries to rip your lungs out. " "slashing"]
           ["THE BEST-DRESSED WEREWOLF YOU'VE EVER SEEN" "You ask who his tailor is, but he bites you instead. " "piercing"]]})

(def ghost
  {:weak ["RADIANT" "ARCANE"]
   :desc [["A MOURNFUL BANSHEE" "As her voice begins to rise, ice fills your chest. " "necrotic"]
           ["A GHOST DRAGGING CHAINS BEHIND IT" "It flings a chain around your neck. " "bludgeoning"]]})

(def skele 
  {:weak ["RADIANT" "ARCANE" "BLUDGEONING"]
   :desc [["A DREAMILY DANCING SKELETON" "It dreamily whirls you into a firepit. " "ARCANE"]
           ["A SKELETON WEARING BRASS KNUCKLES" "It cracks its knuckles, then tries to crack your head. " "bludgeoning"]]})
