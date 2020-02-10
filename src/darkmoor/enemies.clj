(in-ns 'darkmoor.core)

;ENEMY DESCRIPTIONS
; includes: :weak -- damage types the creature is weak to
;           :desc -- list of descriptions for each enemy of that type (name, fighing description, and damage type)

(def wizard 
  {:weak [:blunt :pierce :necro]
   :desc [["A WITCH PLAYING THE BANJO" "She hits you with some out-of-tune chords. " "sound"]
           ["A WARLOCK PRACTING THEIR SPELLS" "They practice throwing a fireball at your face. " "fire"]]})

(def townsfolk 
  {:weak [:blunt :necro :pierce]
   :desc [["AN ENTRANCED FARMER DRAGGING A PITCHFORK" "He jabs the pitchfolk vaguely in your direction. " "piercing"]
           ["A RELUCTANTLY VIOLENT SEAMSTRESS" "She quietly apologizes as she tries to stick you with a pin. " "piercing"]]})

(def cultist 
  {:weak [:necro :pierce :radiant]
   :desc [["A CREEPILY CHEERFUL CULTIST" "He sings a cheerful song about sacrificing you to The Old Gods. " "spooky"]
           ["A CRAZED CLULTIST" "She shrieks wildly and swings an evil-looking dagger. " "piercing"]]})

(def werewolf 
  {:weak [:necro :arcane :radiant]
   :desc [["A WEREWOLF BEGGING FOR SCRAPS" "It flings a half-chewed chicken bone at you. " "blunt"]
           ["A WEREWOLF WITH A CHINESE MENU IN ITS HAND" "He decides to make you into chow mein instead. " "slashing"]
           ["A VERY HAIRY WEREWOLF" "She tries to rip your lungs out. " "slashing"]
           ["THE BEST-DRESSED WEREWOLF YOU'VE EVER SEEN" "You ask who his tailor is, but he bites you instead." "piercing"]]})

(def ghost
  {:weak [:radiant :arcane]
   :desc [["A MOURNFUL BANSHEE" "As her voice begins to rise, ice fills your chest. " "necrotic"]
           ["A GHOST DRAGGING CHAINS BEHIND IT" "It flings a chain around your neck. " "blunt"]]})

(def skele 
  {:weak [:radiant :arcane :blunt]
   :desc [["A DREAMILY DANCING SKELETON" "It dreamily whirls you into a firepit. " "fire"]
           ["A SKELETON WEARING BRASS KNUCKLES" "It cracks its knuckles, then tries to crack your head. " "blunt"]]})
