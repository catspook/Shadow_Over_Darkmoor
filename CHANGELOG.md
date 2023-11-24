# Change Log

TODO:
- damage bonus for eq weapons with matching weaknesses: maybe add flat bonus, or bonus to weapon damage instead of total dmg? 
- make player damage a range instead of flat value
- make enemy damage and health a range instead of flat value 
- randomize how much loot can be at locations
- make slot visible on inv menu?
- only list damage types you have when fighting once
- reduce enemy and items with weaknesses & special dmg types
- remove pauses after fighting

Longer TODO:
- add items that grant resistances to dmg types
- add two-handed weapons

## [0.3.2] - 24-11-2023
Removed "-Hand" functions from Model

## [0.3.1] - 10-11 2023
Refactoring Model: 
- Moved location data out of main model file and into sub-folders instead. Each level gets its own folder now.
- Moved helper functions in model.clj to their own file
- Removed unncessary comments or made comments less verbose
- Refactored functions in model.clj to be  more concise

## [0.2.1] - 2020-08
### Changed
- Changed menu to include a "map" option, that displays the layout of the current area
- Added a text file for every area map

## [0.2.0] - 2020-03
### Changed
CODE:
* Rewritten code to be cleaner, more effecient
* Rewritten game data structures to be more containerized and easier to keep track of
* Data structures contain much more information

ENEMIES and FIGHTING:
* Enemies have damage weaknesses and take extra damage from their weakness
* Enemies no longer hard-coded to locations, instead have % chance of appearing
* Combat includes a 'second wind' option instead of 'run away'
* Enemy stats scale with player

LOOT and INVENTORY: 
* Loot description has replaced location description
* Armor can't be double equipped anymore
* Health potions much more rare, and heal % of health

UI: 
* New UI for moving, looting, inventory, and fighting
* Much smoother experience
* Can "see" nearby locations before moving there
* no longer have option to open empty inventory or loot menu
* Game won't let you move past playable area anymore 

## [0.1.1] - 2019-06
### Changed
Small bug fixes

## [0.1.0] - 2019-01 to 2019-04
### Changed 
Original game development

[Unreleased]: https://github.com/catspook/darkmoor/compare/0.1.1...HEAD
[0.1.1]: https://github.com/catspook/darkmoor/compare/0.1.0...0.1.1
