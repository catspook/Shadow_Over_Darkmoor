(in-ns 'darkmoor.core)

;ENEMIES________________________________________________________________________________
;lists of enemy types, hard-coded into each location below

(def rat
  {:rat true
   :health 5
   :damage 3})
(def dagger-cult
  {:cultist true
   :health 24 
   :damage 4})
(def magic-cult
  {:cultist true 
   :health 20
   :damage 5})
(def bow-skele
  {:skeleton true
   :health 15
   :damage 3})
(def axe-skele
  {:skeleton true
   :health 18
   :damage 4})
(def zombie 
  {:zombie true
   :health 20
   :damage 4})
(def ghost
  {:ghost true
   :health 15
   :damage 4})
(def boss
  {:boss true
   :health 35
   :damage 9})
(def minion
  {:minion true
   :health 30
   :damage 7})

;places w/ a monster you've already killed get added here
(def init-hit-list
  #{})

