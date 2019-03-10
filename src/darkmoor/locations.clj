(in-ns 'darkmoor.core)

;LEVEL BUILDING___________________________________________________________
; :exit-start-coords maps to what location the player should be off after popping of the map corresponding to each location
; :enter maps to what new map should be loaded, as well as what coordinates that player should start at once they are there.

;nil loc. has no description and therefore can't be loaded.
(def loc-0-0
  {})

;temple basement
;exiting basement
(def loc-1-3-0
  {:obj (ref (place-obj temple 2)) 
   :desc "The Templar used this underground chamber for holy rituals. A small wooden table lies to the side of the rope ladder leading upwards, covered in blood-spattered candles."
   :exit-start-coords {:row 1 :col 0}})
(def loc-1-3-1
  {:obj (ref (place-obj health 1)) 
   :desc "A beautifully carved marble monk stares silently forward, unaware of the defilement of its temple."
   :enemy minion})
(def loc-1-3-2
  {:obj (ref (place-obj health 2)) 
   :desc "The High Priest of the Templar Order lies before you, dead. A sword is still clutched in his feeble hand." 
   :enemy minion})
(def loc-1-3-3
  {:obj (ref (place-obj temple 0)) 
   :desc "A marble alter takes up one-quarter of the underground chamber. It has been defaced with runes painted in blood. A spellbook lies open on top, emitting unearthly black smoke."
   :enemy boss})

(def basement-1-3 
  [[loc-1-3-0 loc-1-3-1]
   [loc-1-3-2 loc-1-3-3]])

;temple
;exiting temple
(def loc-1-0
  {:obj (ref (place-obj letter 1)) 
   :desc "The air inside in the Temple smells dusty and stagnant, and feels wrong coming from a place you've always known as bright and full of life." 
   :exit-start-coords {:row 1 :col 2}})
(def loc-1-1
  {:obj (ref (place-obj temple 1)) 
   :desc "Heavy oaken pews lie stacked haphazardly in front of the Temple's Eastern doors. You will have to move West to exit."})
;enter temple basement
(def loc-1-2
  {:obj (ref (place-obj health 1)) 
   :desc "A wooden bed lies upturned, the plain woollen blanket soaking up the last of the blood trail. A heavy wooden trapdoor lies open, eerie chanting echoing up from the depths."
   :enemy magic-cult
   :enter {:goto basement-1-3
           :start-coords {:row 0 :col 0}}})
(def loc-1-3
  {:obj (ref (place-obj cultists 2)) 
   :desc "The thick velvet curtain seperating the Priest's quarters has been ripped violently to the side, part of it dyed black from fresh blood. The blood trail continues West."
   :enemy dagger-cult})
(def loc-1-4
  {:obj (ref (place-obj cultists 1)) 
   :desc "A basin of Holy Water has been replaced with a brackish, foul-smelling, and cloudy fluid. You dare not touch it."})
(def loc-1-5
  {:obj (ref (place-obj temple 3)) 
   :desc "A carved monk stands in silent supplication, his marble hands reaching beseechingly upward."
   :enemy dagger-cult})
(def loc-1-6
  {:obj (ref (place-obj cultists 1)) 
   :desc "Smashed wooden pews and ripped pieces of tapistry litter the floor, a trail of fresh blood leading North through the wreckage."})
(def loc-1-7
  {:obj (ref (place-obj temple 4)) 
   :desc "Soft orange light filters through the stained glass, illuminating a red sun setting over a golden field."
   :enemy magic-cult})
(def loc-1-8
  {:obj (ref (place-obj temple 2)) 
   :desc "Bloody handprints mar the stained-glass windows at the back of the Temple. The blood drips into a small pool at the base of the wall and trails Northwest."
   :enemy dagger-cult})
(def loc-1-9
  {:obj (ref (place-obj health 3)) 
   :desc "An intricately carved alter sits untouched on a small dias. You're unsure of why the Cultists have left it alone--perhaps there's something else in here they wanted more."})

(def temple-1 
  [[loc-0-0 loc-1-0 loc-1-1  loc-0-0]
   [loc-1-2 loc-1-3 loc-1-4  loc-1-5]
   [loc-0-0 loc-1-6 loc-1-7  loc-0-0]
   [loc-0-0 loc-1-8 loc-1-9 loc-0-0]])

;mausoleum
;exiting mausoleum
(def loc-2-7-0
  {:obj (ref (place-obj letter 1)) 
   :desc "The heavy crypt doors sit ajar behind you. Strangely worn steps continue down into the darkness--who is trodding them, in this house of the dead?" 
   :exit-start-coords {:row 0 :col 1}})
(def loc-2-7-1
  {:obj (ref (place-obj skele 1)) 
   :desc "The worn steps continue downwards, and you begin to make out recesses in the room beyond, skulls gleaming white in the darkness."})
(def loc-2-7-2
  {:obj (ref (place-obj health 1)) 
   :desc "The rectangular recess in this wall is stacked with bones, skulls carefully placed in a pyramid atop a circle of long bones."
   :enemy bow-skele})
(def loc-2-7-3
  {:obj (ref (place-obj temple 1)) 
   :desc "Carefully edging forwards, you see the carved stone body of a Templar Knight from days gone by lying on top of an untouched sarcophagus."})
(def loc-2-7-4
  {:obj (ref (place-obj crypt 2)) 
   :desc "You push a sarcophagus lid aside and find a skeleton clutching a leather-bound journal protectively. As soon as you touch the journal, it crumbles into dust."
   :enemy bow-skele})
(def loc-2-7-5
  {:obj (ref (place-obj crypt 3)) 
   :desc "Several coffins are stacked on top of each other in the corner of the crypt. You gather that whoever was in these either wasn't well liked or wasn't very important."
   :enemy axe-skele})
(def loc-2-7-6
  {:obj (ref (place-obj cultists 1)) 
   :desc "You narrowly avoid stepping in the center of a chalk ring. Spectral runes glow a faint purple inside the circle, but slowly fade as you scrub your toe through the chalk line."
   :enemy axe-skele})
(def loc-2-7-7
  {:obj (ref (place-obj health 1))
   :desc "The slight smoky smell of insense hangs in the air above a smouldering censer."
   :enemy bow-skele})

;mausoleum
(def mausoleum-2-7 
  [[loc-0-0   loc-2-7-0 loc-0-0  ]
   [loc-0-0   loc-2-7-1 loc-0-0  ]
   [loc-2-7-2 loc-2-7-3 loc-2-7-4]
   [loc-2-7-5 loc-2-7-6 loc-2-7-7]])

;graveyard
(def loc-2-0
  {:obj (ref (place-obj health 1)) 
   :desc " A tiny fence marks the boundary of a family plot. Marigolds have been planted along the inside edge."})
;exit graveyard
(def loc-2-1
  {:obj (ref (place-obj skele 3)) 
   :desc "You stand just inside the twisting iron gate of the graveyard." 
   :enemy bow-skele
   :exit-start-coords {:row 1 :col 1}})
(def loc-2-2
  {:obj (ref (place-obj skele 2)) 
   :desc " A small wooden cross mark's a pauper's grave, the name too weathered to read."
   :enemy axe-skele})
(def loc-2-3
  {:obj (ref (place-obj rich 1)) 
   :desc " 'R. I. P. Commander Agustus Naro, an Inspiration to All' "
   :enemy axe-skele})
(def loc-2-4
  {:obj (ref (place-obj skele 0)) 
   :desc "This gravestone is half-hidden amongst a bunch of bright yellow sunflowers."})
(def loc-2-5
  {:obj (ref (place-obj skele 2)) 
   :desc " 'Here lies Stefan, a Goode Boy until the Ende.' You can just make out the unskilled carving of a Terrier underneith the epitaph. "
   :enemy bow-skele})
(def loc-2-6
  {:obj (ref (place-obj skele 1)) 
   :desc "A tall stone monument tilts to the side, the epitaph hidden under the ivy wrapping its way up the pillar."
   :enemy axe-skele})
;entering mausoleum
(def loc-2-7
  {:obj (ref (place-obj skele 1)) 
   :desc "Nestled among carefully-pruned bushes sits the Templar Mausoleum, the heavy stone jar cracked slightly open."
   :enemy bow-skele
   :enter {:goto mausoleum-2-7 
           :start-coords {:row 0 :col 1}}})
(def loc-2-8
  {:obj (ref (place-obj health 1)) 
   :desc "You crouch under an ancient and tall weeping willow, its branches trailing over several tombstones."})

(def graveyard-2 
  [[loc-2-6 loc-2-7 loc-2-8]
   [loc-2-3 loc-2-4 loc-2-5]
   [loc-2-0 loc-2-1 loc-2-2]])

;store
(def loc-3-0
  {:obj (ref (place-obj gen 5)) 
   :desc "Bunches of unused candles tied with twine are neatly stacked in this corner."})
(def loc-3-1
  {:obj (ref (place-obj skele 1)) 
   :desc "The floor here is littered with hard sugar candy, spilled from their container."
   :enemy bow-skele})
(def loc-3-2
  {:obj (ref (place-obj home 2)) 
   :desc "The entire corner of the shop is stacked with wooden crates, some marked with an expiration date of several days prior to today's."
   :enemy axe-skele})
(def loc-3-3
  {:obj (ref (place-obj health 2)) 
   :desc "A curtain, embroidered with tiny cherries, cheerfully hides the gray light filtering through the grimy window."})
(def loc-3-4
  {:obj (ref (place-obj gen 6)) 
   :desc "A hand-tied rug dominates the center of the shop, its original colors faded under years of heavy use."})
(def loc-3-5
  {:obj (ref (place-obj home 4)) 
   :desc "You examine the main counter piled high with buttons and spools of thread. The store's money tin has been pried open and emptied."
   :enemy bow-skele})
(def loc-3-6
  {:obj (ref (place-obj gen 3)) 
   :desc "The ground around a keg of unskilfully tapped cider is still saturated with liquid."})
;enter store
(def loc-3-7
  {:obj (ref (place-obj home 1)) 
   :desc "Your head brushes against a small brass bell attached to the door and a litle chime rings out." 
   :enemy bow-skele
   :exit-start-coords {:row 0 :col 4}})
(def loc-3-8
  {:obj (ref (place-obj skele 1)) 
   :desc "An oversized shelf stocked high with home goods is squeezed into the corner of the shop."
   :enemy axe-skele})

(def store-3 
  [[loc-3-0 loc-3-1 loc-3-2]
   [loc-3-3 loc-3-4 loc-3-5]
   [loc-3-6 loc-3-7 loc-3-8]])

;mansion
(def loc-4-0
  {:obj (ref (place-obj home 3))
   :desc "You stand in a grand dining room. The silver cutlery sparkles in the afternoon sun."
   :enemy ghost})
(def loc-4-1
  {:obj (ref (place-obj gen 2))
   :desc "You walk towards a cabinet in the corner of the dinking room and find several empty wine bottles inside."})
(def loc-4-2
  {:obj (ref (place-obj health 2))
   :desc "Three walls of this room are full-length windows, and clusters of potted plans sit every few feet. A small alchemical workbench is pressed agianst one wall."
   :enemy ghost})
(def loc-4-3
  {:obj (ref (place-obj home 0))
   :desc "An unobtrustive door leads to a washroom, a rare sight in this town. You take some time to slash your face with fresh, cold water from the basin."})
(def loc-4-4
  {:obj (ref (place-obj rich 3))
   :desc "The door the master suite hangs from one hinge. You can just make out the mayor's bloodied body lying behind the four-poster bed dominating the room."
   :enemy ghost})
;enter mansion
(def loc-4-5
  {:obj (ref (place-obj health 1))
   :desc "The foyer of this mansion is decorated with an oil painting of its original owner--the mayor's grandfather, the founder of this town."
   :exit-start-coords {:row 2 :col 2}})
(def loc-4-6
  {:obj (ref (place-obj cultists 2))
   :desc "You rifle through the mayor's study and find a crumpled note in the fire. It is from the captain of the town's guard, and mentions suspicious figures have been seen lurking outside town."})
(def loc-4-7
  {:obj (ref (place-obj rich 7))
   :desc "Two crossed swords hang on the wall above the entrance to the mayor's pursonal armory. You pick through her collection and spot a few pieces you like."})
(def loc-4-8
  {:obj (ref (place-obj gen 3))
   :desc "A dark celler lies behind a battered door. A few beets spill out of a sack in the corner."
   :enemy ghost})
(def loc-4-9
  {:obj (ref (place-obj rich 2))
   :desc "You're sure this was a comfortable sitting room at one time, but it's hard to ignore the body slumped forward in an armchair off to the side of the cold fireplace."
   :enemy ghost})

(def mansion-4 
  [[loc-0-0 loc-4-0 loc-4-1]
   [loc-4-2 loc-4-3 loc-4-4]
   [loc-4-5 loc-4-6 loc-4-7]
   [loc-0-0 loc-4-8 loc-4-9]])

;house
(def loc-5-0
  {:obj (ref (place-obj gen 3))
   :desc "You lean out the back door and see a small vegetable garden. Empty and half-full sacks of vegetables are stacked by the door."
   :enemy ghost})
(def loc-5-1
  {:obj (ref (place-obj home 3))
   :desc "A large, rough-hewn shelf sits opposite the wooden table. Jars of herbs and spices are intermixed with sacks of flour and a mortar and pestle."
   :enemy ghost})
(def loc-5-2
  {:obj (ref (place-obj home 2))
   :desc "A faded lace tablecloth covers a modest wooden table. Dried flowers decorate the window sill."})
(def loc-5-3
  {:obj (ref (place-obj home 4))
   :desc "Muddy boots and some small farming equipment are tossed haphazardly by the back door."})
(def loc-5-4
  {:obj (ref (place-obj health 1))
   :desc "The kitchen table is piled high with loves of baked bread, some tinged slightly green from herbs. You take a loaf hungrily--though stale, it still tastes very good."})
(def loc-5-5
  {:obj (ref (place-obj rich 2))
   :desc "You enter the main room of the house. An old brass key tied to a faded pink ribbon hangs from the wall. Underneith is a large wooden iron-studded chest."
   :enemy ghost})
;enter house
(def loc-5-6
  {:obj (ref (place-obj health 2))
   :desc "A woven basket sits by the front door. A linen cloth has been carefully wrapped around a bundle of herbs and potions."
   :exit-start-coords {:row 3 :col 2}})

(def house-5 
  [[loc-5-0 loc-5-1 loc-5-2 loc-0-0]
   [loc-5-3 loc-5-4 loc-5-5 loc-5-6]])

;ruins
(def loc-6-0
  {:obj (ref (place-obj gen 4)) 
   :desc "A side of the house has collapsed completely, leaving the surrounding area exposed to the elements. Pockets of moss are beginning to grow on one rain-soaked log."
   :enemy ghost})
(def loc-6-1
  {:obj (ref (place-obj health 1)) 
   :desc "You cling to a fallen beam as you cover a patch of cracked and broken flooring."})
(def loc-6-2
  {:obj (ref (place-obj gen 0)) 
   :desc "Whatever was originally in this pantry has completely burnt to a crisp."})
;enter ruins
(def loc-6-3
  {:obj (ref (place-obj home 2)) 
   :desc "You duck under a beam that fell in front of the front door. Blackened wreakage lays around you." 
   :exit-start-coords {:row 3 :col 4}})
(def loc-6-4
  {:obj (ref (place-obj health 1)) 
   :desc "You climb on top of a fallen staircase to get a better look around."
   :enemy ghost})
(def loc-6-5
  {:obj (ref (place-obj home 1)) 
   :desc "You spot a trapdoor behind the ruined staircase. The surrounding earth has sunken into it, filling the basement completely."})
(def loc-6-6
  {:obj (ref (place-obj home 5)) 
   :desc "You shift a few large wooden beams and find a pocket of goods untouched by the fire."
   :enemy ghost})
(def loc-6-7
  {:obj (ref (place-obj health 0)) 
   :desc "The only thing left standing in this house is the soot-covered fireplace."})
(def loc-6-8
  {:obj (ref (place-obj gen 3)) 
   :desc "The ground here is sunken and shifted in a small landslide."
   :enemy ghost})

(def ruins-6 
  [[loc-6-0 loc-6-1 loc-6-2]
   [loc-6-3 loc-6-4 loc-6-5]
   [loc-6-6 loc-6-7 loc-6-8]])

;cave
(def loc-13-0
  {:obj (ref (place-obj letter 1)) 
   :desc "The light from the cave entrance does not pierce far into the darkness. Every so often a small 'plink' of water hitting stone echos out of the depths." 
   :exit-start-coords {:row 3 :col 0}})
(def loc-13-1
  {:obj (ref (place-obj cultists 3)) 
   :desc "A small trickle of water feeds a large concentration of dark blue mushrooms. You have no idea if they're edible, and you'd rather not check."
   :enemy magic-cult})
(def loc-13-2
  {:obj (ref (place-obj skele 0)) 
   :desc "Bats fly around your head as you round a corner. You curse as you pull your left boot out of a squishy pile of guano."})
(def loc-13-3
  {:obj (ref (place-obj skele 3)) 
   :desc "A crumbling deer skull glints in the gloom. Moss is beginning to cover the antlers in a soft green blanket."
   :enemy axe-skele})
(def loc-13-4
  {:obj (ref (place-obj gen 4)) 
   :desc "There is a half-rotted wooden dock on the rocky bank, covered in moss. One post is still tied with a rotted rope, but the boat it led to has long since floated away."
   :enemy rat})
(def loc-13-5
  {:obj (ref (place-obj gen 0)) 
   :desc "Pillars of connected stalagmites and stactites block your path to the West. To the South you hear the dull roar of an unground river."})
(def loc-13-6
  {:obj (ref (place-obj health 2)) 
   :desc "A small spring bubbles in a rocky alcove. Moss and ferns grow happily in the dampness, illuminated by the soft light of glowing mushrooms."})
(def loc-13-7
  {:obj (ref (place-obj health 1)) 
   :desc "An old traveler's pack has been torn open on the ground, ripped apart by animal teeth from what you can tell."
   :enemy rat})
(def loc-13-8
  {:obj (ref (place-obj skele 3)) 
   :desc "A jagged line of stalagmites mark the edge of a rockslide leading to a river far below. There is a wooden bridge spanning the chasm to your North."
   :enemy bow-skele})
(def loc-13-9
  {:obj (ref (place-obj cultists 2)) 
   :desc "The glowing mushrooms to the South West barely illuminate the remains of a campsite. You poke around a bit, but the tracks of animals indicate that you're not the first to do so."
   :enemy dagger-cult})
(def loc-13-10
  {:obj (ref (place-obj home 1)) 
   :desc "You find a skeletal body propped against the Northern wall of the cave, a few arrows still sticking out of its shield. This poor fellow has been here a while."})
(def loc-13-11
  {:obj (ref (place-obj cultists 3)) 
   :desc "A rickety wooden bridge spans the length of a dark chasm in the rocky earth."
   :enemy magic-cult})
(def loc-13-12
  {:obj (ref (place-obj gen 1)) 
   :desc "You glare warily at the creeking wooden bridge, very glad to be on solid ground for the moment. An eerie green glow illuminates the path to the East."
   :enemy rat})
(def loc-13-13
  {:obj (ref (place-obj health 1)) 
   :desc "Phosphorescent mushrooms cast an unearthly greenish glow on the far side of the cave. You catch the gleam of armor to the South."})
(def loc-13-14
  {:obj (ref (place-obj skele 2)) 
   :desc "You nearly tumble off the edge of an underground cliff. You sit on a nearby boulder and catch your breath, listening to the rumble of the underground river below."})
(def loc-13-15
  {:obj (ref (place-obj temple 3)) 
   :desc "The body of a knight lies face down in the rocky earth. You're pretty sure you need her gear more than she does at the moment."
   :enemy bow-skele})

(def cave-13 
  [[loc-0-0  loc-0-0  loc-0-0  loc-13-9 loc-13-10 loc-13-11 loc-13-12 loc-13-13]
   [loc-0-0  loc-0-0  loc-13-6 loc-13-7 loc-13-8  loc-0-0   loc-13-14 loc-13-15]
   [loc-0-0  loc-13-2 loc-13-3 loc-13-4 loc-13-5  loc-0-0   loc-0-0   loc-0-0  ]
   [loc-13-0 loc-13-1 loc-0-0  loc-0-0  loc-0-0   loc-0-0   loc-0-0   loc-0-0  ]])

;tavern
(def loc-18-0
  {:obj (ref (place-obj tavern 2)) 
   :desc "You peek into the kitchen, and nearly gag at the smell of days-old dirty plates and mugs soaking in a sink full of foul, rancid water."})
(def loc-18-1
  {:obj (ref (place-obj gen 4)) 
   :desc "Kegs of beer and barrels of dried meat are stacked against the wall of the storeroom."
   :enemy bow-skele})
(def loc-18-2
  {:obj (ref (place-obj gen 7)) 
   :desc "You enter the back room of the tavern, your boots sticking slightly to the floor as you try to avoid piles of spilled food and dried drink."})
(def loc-18-3
  {:obj (ref (place-obj tavern 2)) 
   :desc "You crawl along the edge of the counter, trying not to be seen."
   :enemy bow-skele})
(def loc-18-4
  {:obj (ref (place-obj gen 0)) 
   :desc "You squeeze yourself between two heavy armchairs in front of the fireplace."
   :enemy axe-skele})
(def loc-18-5
  {:obj (ref (place-obj tavern 3)) 
   :desc "This fireplace is still lit, fed by chair and table legs and smashed bottles of alcohol."
   :enemy axe-skele})
(def loc-18-6
  {:obj (ref (place-obj tavern 4)) 
   :desc "You duck behind the counter and find a few bottles of untouched beer and liquor."})
(def loc-18-7
  {:obj (ref (place-obj tavern 5)) 
   :desc "Someone--or some skeleton--has taken the time to carefully construct a card castle on the edge of the dancefloor. Bottles stand around it like guardtowers."
   :enemy axe-skele})
(def loc-18-8
  {:obj (ref (place-obj tavern 3)) 
   :desc "A few skeletons lie still on the tavern's makeshift dance floor, thier hands still clutching broken bottles and clubs."})
(def loc-18-9
  {:obj (ref (place-obj gen 2)) 
   :desc "Tables and chairs have been pushed in a circle to form a make-shift fighting circle."})
;enter tavern
(def loc-18-10
  {:obj (ref (place-obj tavern 1)) 
   :desc "The front doors of the tavern creak open slowly in the breeze."
   :exit-start-coords {:row 1 :col 4}})
(def loc-18-11
  {:obj (ref (place-obj tavern 4)) 
   :desc "A discarded lute lies in the corner next to several overturned chairs. You can just see the edge of a drum peeking out from the nearest table."
   :enemy bow-skele})

(def tavern-18 
  [[loc-18-0 loc-18-1 loc-18-2]
   [loc-18-3 loc-18-4 loc-18-5]
   [loc-18-6 loc-18-7 loc-18-8]
   [loc-18-9 loc-18-10 loc-18-11]])

;maps of location data, accessed by keywords
;:enter means there is a matrix map that can be loaded from that location.
;:map is the matrix that should be loaded (pushed onto the stack) 
;:start-coords is the location the player should start at in the new map

(def loc-0 
  {:obj (ref (place-obj gen 5)) 
   :desc "A cart with a broken wheel lies abandoned on the side of the road."})
(def loc-1
  {:obj (ref (place-obj letter 1)) 
   :desc "The Templar Temple rises above you, colored light shining through stained glass windows onto the cobblestones below. A carving of a holy knight slaying a demon sits above the door." 
   :enter {:goto temple-1 
           :start-coords {:row 0 :col 1}}})
(def loc-2
  {:obj (ref (place-obj skele 2)) 
   :desc "Rickety gates surround an old graveyard. The air smells of freshly-dug earth." 
   :enter {:goto graveyard-2
           :start-coords {:row 2 :col 1}}})
(def loc-3
  {:obj (ref (place-obj gen 3)) 
   :desc "A decrepit general store stands before you, barrels of rotting goods scattered about the area." 
   :enter {:goto store-3 
           :start-coords {:row 2 :col 1}}})
(def loc-4
  {:obj (ref (place-obj health 1)) 
   :desc "A grand, old mansion sits in a dying garden. A single candle flickers behind a stained-glass window." 
   :enter {:goto mansion-4 
           :start-coords {:row 2 :col 0}}}) 
(def loc-5
  {:obj (ref (place-obj home 2)) 
   :desc "This cottage is exceptionally well built, and in better shape than most of the surrounding buildings. There still might be something useful inside." 
   :enter {:goto house-5 
           :start-coords {:row 1 :col 3}}})
(def loc-6 
  {:obj (ref (place-obj gen 3)) 
   :desc "A ruined building lies in an overgrown field. It looks like it burned down not long ago, but a glint of metal catches your eye." 
   :enter {:goto ruins-6 
           :start-coords {:row 1 :col 0}}})
(def loc-7
  {:obj (ref (place-obj gen 2)) 
   :desc "You squeeze into a narrow alleyway between two buildings. A rat scurries away from you." 
   :enemy rat})
(def loc-8 
  {:obj (ref (place-obj health-loc-8 8)) 
   :desc "You hide among a small copse of gnarled pine trees, the hanging moss shielding you from view."})
(def loc-9
  {:obj (ref (place-obj home 4)) 
   :desc "Broken, cracked cobblestones and smashed stalls form a picture of a town square bustling with ghosts."
   :enemy axe-skele})
(def loc-10
  {:obj (ref (place-obj health 3)) 
   :desc "A half-rotted well lies in the middle of the market square. You drop a small pebble and hear a small 'plink' several seconds later." 
   :enemy zombie})
(def loc-11
  {:obj (ref (place-obj forge 2)) 
   :desc "Brightly-colored cloth and lanterns lie smashed around a market stall."
   :enemy rat})
(def loc-12
  {:obj (ref (place-obj forge 6)) 
   :desc "You find yourself in front of a forge, long cold."})
(def loc-13
  {:obj (ref (place-obj letter 1)) 
   :desc "Mossy rocks jut out of the marsh. Cold air whistles past you, coming from a small opening in the rock just big enough to crawl through." 
   :enter {:goto cave-13 
           :start-coords {:row 3 :col 0}}})
(def loc-14
  {:obj (ref (place-obj cultists 3)) 
   :desc "A small shrine of carved, blackened bones has been constructed on top of a pile of smooth stones." 
   :enemy magic-cult})
(def loc-15
  {:obj (ref (place-obj cultists 6)) 
   :desc "Low chanting drifts over on the wind. In the distance, a small camp surrounds a fire of unearthly purple flames. You crouch lower in the weeds, hoping to not be seen." 
   :enemy dagger-cult})
(def loc-16
  {:obj (ref (place-obj cultists 3)) 
   :desc "A sticky smear of blood leads to a pile of burnt corpses that once were the people who lived here. You feel your stomach turn over and look away."
   :enemy zombie})
(def loc-17
  {:obj (ref (place-obj gen 2)) 
   :desc "This grain store has been painted with a swirling symbol. You stare at it, trying to make sense of the squiggling lines, and feel your mind bending."})
(def loc-18
  {:obj (ref (place-obj tavern 2)) 
   :desc "You stand in front of a tavern, its welcoming sign hanging askew. Casks of beer lie smashed on the road." 
   :enter {:goto tavern-18 
           :start-coords {:row 3 :col 1}}})
(def loc-19
  {:obj (ref (place-obj home 3)) 
   :desc "A lone chicken picks for worms in a field littered with blood and household goods."
   :enemy bow-skele})

;level map is a matrix, each cell corresponds to a location data map
(def level-1-map
  [[loc-15 loc-8  loc-0 loc-11 loc-3]
   [loc-14 loc-2  loc-1 loc-9  loc-18]
   [loc-16 loc-19 loc-4 loc-10 loc-12]
   [loc-13 loc-17 loc-5 loc-7  loc-6]])

