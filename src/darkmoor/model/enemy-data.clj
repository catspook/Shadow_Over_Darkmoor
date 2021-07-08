(in-ns 'darkmoor.core)

;ENEMY DESCRIPTIONS
; includes: :weak -- damage types the creature is weak to
;           :desc -- list of descriptions for each enemy of that type (name, fighing description, and damage type)

(def wizard 
  {:weak ["POISON"]
   :desc [["A witch playing the banjo" "She hits you with some out-of-tune chords. " "pierce"]
          ["A hag sacrificing a goat" "She stabs at you with a jagged spear of ice. " "frost"]
          ["A wizard absorbed in a book" "He tries to give you a paper cut. " "slash"]
          ["A mousy novice enchanting some brooms" "They command the brooms to beat you over the head. " "blunt"]
          ["A warlock committing magical arson" "They practice throwing a fireball at your face. " "flame"]]})

(def townsfolk 
  {:weak ["NECROTIC" "POISON"]
   :desc [["An entranced farmer dragging a pitchfork" "He jabs the pitchfolk vaguely in your direction. " "pierce"]
          ["A traitor town guard" "He yells 'Better you than me!' and stabs you with a pike. " "pierce"]
          ["The town drunk" "She asks you for whiskey, pounds it down, then punches you." "blunt"]
          ["A creepy child holding a cursed doll" "The doll flies through the air, aiming for your eyes. " "pierce"]
          ["A reluctantly violent seamstress" "She quietly apologizes as she tries to stick you with a pin. " "pierce"]]})

(def cultist 
  {:weak ["FLAME" "FROST"]
   :desc [["A creepily cheerful cultist" "He sings a song about sacrificing you to The Old Gods. " "necrotic"]
          ["A cultist questioning their life choices" "They debate about whether or not they should curse you. " "necrotic"]
          ["A cultist recruiting for the Eldritch Gods" "They hand you a pamphlet titled 'Survive The End of the World! Convert Today!' " "necrotic"]
          ["A cultist training vampire rabbits" "Bunnicula attempts to suck the juice out of your remaining food." "necrotic"]
          ["A crazed cultist" "She shrieks wildly and swings an evil-looking dagger. " "necrotic"]]})

(def werewolf 
  {:weak ["FLAME" "FROST" "NECROTIC"]
   :desc [["A werewolf dumpster-diving for food" "She flings a half-chewed chicken bone at you. " "poison"]
          ["A werewolf with a Chinese menu in its hand" "He decides to make you into chow mein instead. " "slash"]
          ["A very hairy werewolf" "She tries to rip your lungs out. " "slash"]
          ["A werewolf enjoying a pina colada" "They throw their drink at you. " "poison"]
          ["The best-dressed werewolf you've ever seen" "You ask who his tailor is, but he insults your taste instead. " "blunt"]]})

(def ghost
  {:weak ["RADIANT"]
   :desc [["A mournful banshee" "As her voice begins to rise, cold fear fills your chest. " "frost"]
          ["A ghost suffering from ennui" "It begins a long monologue about how nothing really matters anyhow. " "frost"]
          ["Nearly-Headless Rick" "He tilts his head off his shoulders and shows you his neck stump. " "blunt"]
          ["The Ghost of Yuletide Past" "It offers you a present, then takes it away at the last minute. " "frost"]
          ["A ghost wrapped in chains" "It flings a chain around your neck. " "blunt"]]})

(def skele 
  {:weak ["RADIANT"]
   :desc [["A dreamily dancing skeleton" "It dreamily whirls you into a firepit. " "flame"]
          ["A combatively drunk skeleton" "It stumbles into a wall, waving a broken liquor bottle at you. " "slash"]
          ["A skeleton stuck in a coffin" "It pulls the coffin behind it as it grabs at your ankles. " "blunt"]
          ["A skeletal dog with a knife in its mouth" "It jumps up for pets, the knife dangerously close. " "slash"]
          ["A skeleton wearing brass knuckles" "It cracks its knuckles, then tries to crack your head. " "blunt"]]})
