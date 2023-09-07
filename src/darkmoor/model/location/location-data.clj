(in-ns 'darkmoor.core)

(load "model/enemy-data")
(load "model/location/level-1-locations/level-1")
(load "model/location/level-1-locations/temple-cellar")
(load "model/location/level-1-locations/temple")
(load "model/location/level-1-locations/crypt")
(load "model/location/level-1-locations/graveyard")
(load "model/location/level-1-locations/armory")
(load "model/location/level-1-locations/wizard-tower")
(load "model/location/level-1-locations/haunted-house")
(load "model/location/level-1-locations/archive")
(load "model/location/level-1-locations/cave")
(load "model/location/level-1-locations/tavern")

(def init-map-stack
  (list level-1-map))

(def init-loc-info
  (merge
    init-temple-cellar
    init-temple
    init-crypt
    init-graveyard
    init-armory
    init-wizard-tower
    init-haunted-house
    init-archive
    init-cave
    init-tavern
    init-level-1))
 