# The Shadow Over Darkmoor

The Shadow Over Darkmoor is a text-based roguelike adventure game set in the fictonal village of
Darkmoor. It runs off of the terminal (untested on Windows) and has a very gentle learning curve.

![pink-welcome](https://raw.githubusercontent.com/catspook/Shadow_Over_Darkmoor/master/doc/pink-welcome.png)

Over the last few weeks, travel to and from the town of Darkmoor has trickled to a halt, save for a few refugees who
whisper of necromancy and other horrors. These murmerings have reached the Queen's ears, and she has sent you
to find the truth and rescue the town of Darkmoor from its fate.

You arrive in Darkmoor as darkness falls. Your mission is simple: survive.

### Travel to over 100 unique locations!
What's inside A House Wreathed in Shadows or The Tower of Archmage Cerberus? Find out!
Now featuring maps, so you always know where you are! 
(Interior cell descriptions are still WIP)
![pink-main](https://raw.githubusercontent.com/catspook/Shadow_Over_Darkmoor/master/doc/pink-main.png)

### Loot cool new weapons and armor!
Customize your character and do extra damage to certain enemies with over 40 unique items!
![pink-inv](https://raw.githubusercontent.com/catspook/Shadow_Over_Darkmoor/master/doc/pink-inv.png)

### Fight 30 unique enemies!
Punch A Creepy Child Holding a Cursed Doll and A Werewolf Drinking a Pina Colada in the face!
![pink-enemy](https://raw.githubusercontent.com/catspook/Shadow_Over_Darkmoor/master/doc/pink-enemy.png)

Darkmoor is also customizable! To add more enemies, locations, or items to the game, alter the data files in 
src/darkmoor/model. 

## Build Tool

Darkmoor uses [Leiningen](https://leiningen.org/) to handle the builds/dependencies.

Source code can be run by running ```$ lein run``` anywhere in Darkmoor's file system.

To build a standalone jar run ```$ lein compile; lein uberjar```

This jar can then be ran with the command: ```$ java -jar target/uberjar/darkmoor-0.2.0-SNAPSHOT-standalone.jar```

## Acknowledgements

Thanks to my parter, [Thea Leake](https://github.com/thea-leake) who originally helped me get this off the ground way back when I was just learning to code. Check out her [Halloween Banqui game](https://github.com/thea-leake/graveyard), featuring pixel art by me!

ASCII art taken from: [asciiart.eu](https://www.asciiart.eu/mythology/skeletons), [Text To Ascii Art Generator](https://patorjk.com/software/taag)

## Updates 
See `CHANGELOG` for details.

## License

© 2019-2023 Casper Rutz 

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
