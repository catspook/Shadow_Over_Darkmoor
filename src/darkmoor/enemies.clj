(in-ns 'darkmoor.core)

;ENEMY DESCRIPTIONS
; includes: :weak -- damage types the creature is weak to
;           :desc -- list of descriptions for each enemy of that type (name, fighing description, and damage type)

(def wizard 
  {:weak ["BLUDGEONING" "PIERCING" "NECROTIC"]
   :desc [["A witch playing the banjo" "She hits you with some out-of-tune chords. " "sound"]
          ["A hag sacrificing a goat" "She stabs at you with a jagged ritual dagger. " "piercing"]
          ["A wizard absorbed in a book" "He tries to give you a paper cut. " "slashing"]
          ["A mousy novice enchanting some brooms" "They command the brooms to beat you over the head. " "bludgeoning"]
          ["A warlock committing magical arson" "They practice throwing a fireball at your face. " "arcane"]]})

(def townsfolk 
  {:weak ["BLUDGEONING" "NECROTIC" "PIERCING"]
   :desc [["An entranced farmer dragging a pitchfork" "He jabs the pitchfolk vaguely in your direction. " "piercing"]
          ["A traitor town guard" "He yells 'Better you than me!' and stabs you with a pike. " "piercing"]
          ["The town drunk" "She asks you for whiskey, pounds it down, then punches you." "bludgeoning"]
          ["A creepy child holding a cursed doll" "The doll flies through the air, aiming for your eyes. " "piercing"]
          ["A reluctantly violent seamstress" "She quietly apologizes as she tries to stick you with a pin. " "piercing"]]})

(def cultist 
  {:weak ["NECROTIC" "PIERCING" "RADIANT"]
   :desc [["A creepily cheerful cultist" "He sings a cheerful song about sacrificing you to The Old Gods. " "spooky"]
          ["A cultist questioning their life choices" "They debate about whether or not they should curse you. " "necrotic"]
          ["A cultist recruiting for the Eldritch Gods" "They hand you a pamphlet titled 'Survive The End of the World! Convert Today!' " "necrotic"]
          ["A cultist training vampire rabbits" "Bunnicula attempts to suck the juice out of your remaining food." "starvation"]
          ["A crazed cultist" "She shrieks wildly and swings an evil-looking dagger. " "piercing"]]})

(def werewolf 
  {:weak ["NECROTIC" "ARCANE" "RADIANT"]
   :desc [["A werewolf dumpster-diving for food" "She flings a half-chewed chicken bone at you. " "bludgeoning"]
          ["A werewolf with a Chinese menu in its hand" "He decides to make you into chow mein instead. " "slashing"]
          ["A very hairy werewolf" "She tries to rip your lungs out. " "slashing"]
          ["A werewolf enjoying a pina colada" "They throw their drink at you. " "splash"]
          ["The best-dressed werewolf you've ever seen" "You ask who his tailor is, but he bites you instead. " "piercing"]]})

(def ghost
  {:weak ["RADIANT" "ARCANE"]
   :desc [["A mournful banshee" "As her voice begins to rise, cold fear fills your chest. " "necrotic"]
          ["A ghost suffering from ennui" "It begins a long monologue about how nothing really matters anyhow. " "depression"]
          ["Nearly-Headless Rick" "He tilts his head off his shoulders and shows you his neck stump. " "disgust"]
          ["The Ghost of Yuletide Past" "It offers you a present, then takes it away at the last minute. " "disappointment"]
          ["A ghost wrapped in chains" "It flings a chain around your neck. " "bludgeoning"]]})

(def skele 
  {:weak ["RADIANT" "ARCANE" "BLUDGEONING"]
   :desc [["A dreamily dancing skeleton" "It dreamily whirls you into a firepit. " "arcane"]
          ["A combatively drunk skeleton" "It stumbles into a wall, waving a broken liquor bottle at you. " "slashing"]
          ["A skeleton stuck in a coffin" "It pulls the coffin behind it as it grabs at your ankles. " "bludgeoning"]
          ["A skeletal dog with a knife in its mouth" "It jumps up for pets, the knife dangerously close. " "slashing"]
          ["A skeleton wearing brass knuckles" "It cracks its knuckles, then tries to crack your head. " "bludgeoning"]]})
