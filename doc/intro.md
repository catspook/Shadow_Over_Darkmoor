# Introduction to The Shadow Over Darkmoor

See README for more information.

After hearing rumors of necromancers ransacking towns, our protagonist returns home to find their
village already annhilated. Vowing revenge, the protagonist makes their way through the village to find the person
or people responsible for the attack and destroy them. Along the way, the protagonist will find plenty of weapons,
armor, and clues, as well as opponents--both living and dead--to defeat.

The Shadow Over Darkmoor is a text-based roguelike adventure game set in the fictonal village of
Darkmoor, and features over 100 unique areas, over 250 interactable items to discover, and over 50 enemies
to fight. Darkmoor runs off of the terminal and has a very gentle learning curve--gameplay is explained as the
player progresses through the storyline.

# Build Tool

Darkmoor uses [Leiningen](https://leiningen.org/) to handle the builds/dependencies.

## Code

The code is located in 'src/darkmoor/core.clj', and requires text documents found in
the 'resources' file.

The source code can be run without compilation by running ```$ lein run```
anywhere in Darkmoor's file system. This won't be as fast as running it off of a
standalone jar.

To build a standalone jar run ```$ lein compile; lein uberjar```. This jar can then be ran
with the command: ```$ java -jar target/uberjar/darkmoor-0.1.0-SNAPSHOT-standalone.jar```
