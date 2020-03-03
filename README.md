# The Shadow Over Darkmoor

![Welcome.](/Shadow_Over_Darkmoor/master/pink-welcome.png?raw=true)
Over the last few weeks, travel to and from the town of Darkmoor has trickled to a halt, save for a few refugees who
whisper of necromancy and other horrors. These murmerings have reached the Queen's ears, and she has sent you
to find the truth and rescue the town of Darkmoor from its fate.

You arrive in Darkmoor as darkness falls. Your mission is simple: survive.

The Shadow Over Darkmoor is a text-based roguelike adventure game set in the fictonal village of
Darkmoor, and features 30 unique enemies, nearly 40 unique items, and over 100 unique locations to explore. 
Darkmoor runs off of the terminal and has a very gentle learning curve--gameplay is explained as the 
player progresses through the storyline.

Darkmoor is also customizable! To add more enemies, locations, or items to the game, alter the data files in 
src/darkmoor/model. 

## 03/2020 Update
CODE:
* Complete rewrite--much cleaner, more effecient
* Game data structures easier to keep track of
* Data structures contain much more information

ENEMIES and FIGHTING:
* Enemies take extra damage from their damage weaknesses
* Enemies no longer hard-coded to locations, instead have % chance of appearing
* No longer option to run away from combat
* Enemy stats scale with player

LOOT and INVENTORY:
* Damage-dealing items now have a 'damage type' as well
* Loot description has replaced location description
* Loot and inventory inaccessable if empty
* Armor can't be double equipped anymore
* Health potions are dropped by enemies, and heal % of health

UI:
* New UI for moving, looting, inventory, and fighting
* Much smoother experience
* Can "see" nearby locations before moving there
* No longer have option to open empty inventory or loot menu
* Game won't let you move past playable area anymore

## Build Tool

Darkmoor uses [Leiningen](https://leiningen.org/) to handle the builds/dependencies.

Source code can be run by running ```$ lein run``` anywhere in Darkmoor's file system.

To build a standalone jar run ```$ lein compile; lein uberjar```
This jar can then be ran with the command: ```$ java -jar target/uberjar/darkmoor-0.2.0-SNAPSHOT-standalone.jar```

## Acknowledgements

Thanks to my wife, [Thea Leake](https://github.com/thea-leake) who originally helped me get this off the ground.

ASCII art taken from: [asciiart.eu](https://www.asciiart.eu/mythology/skeletons), [Text To Ascii Art Generator](https://patorjk.com/software/taag)

## License

Copyright © 2019-2020 CM Rutz 

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
