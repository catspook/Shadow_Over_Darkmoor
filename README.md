# The Shadow Over Darkmoor

After hearing rumors of necromancers ransacking towns, our protagonist returns home to find their 
village already annhilated. Vowing revenge, the protagonist makes their way through the village to find the person
or people responsible for the attack and destroy them. Along the way, the protagonist will find plenty of weapons,
armor, and clues, as well as opponents--both living and dead--to defeat. 

The Shadow Over Darkmoor is a text-based roguelike adventure game set in the fictonal village of
Darkmoor, and features over 100 unique areas, over 250 interactable items to discover, and over 50 enemies
to fight. Darkmoor runs off of the terminal and has a very gentle learning curve--gameplay is explained as the 
player progresses through the storyline.

Darkmoor is also customizable! To add more enemies, locations, or items to the game, 
copy and alter the code structure used at lines: 90-129 and 1356-1371 (enemies), 131-339 (items), 
380-426 ('indoor' locations), and 789-884 ('outdoor' locations). 

## Build Tool

Darkmoor uses [Leiningen](https://leiningen.org/) to handle the builds/dependencies.

## Code

The code is located in 'src/darkmoor/core.clj', and requires text documents found in
the 'resources' file.

Source code can be run without compilation by running ```$ lein run```
anywhere in Darkmoor's file system. This won't be as fast as running it off of a 
standalone jar.

To build a standalone jar run ```$ lein compile; lein uberjar```. This jar can then be ran
with the command: ```$ java -jar target/uberjar/darkmoor-0.1.0-SNAPSHOT-standalone.jar```

## Acknowledgements

Thanks to my wife, without whose advice I never would have been able to complete this.

All ASCII art taken from: 

patorjk.com/software/taag

www.asciiart.eu/

ascii.co.uk/

## License

Copyright © 2019 CM Rutz 

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
