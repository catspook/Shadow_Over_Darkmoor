(defproject darkmoor "0.2.0-SNAPSHOT"
  :description "The Shadow Over Darkmoor, a text-based roguelike adventure game"
  :url "github.com/catspook/Shadow_Over_Darkmoor"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot darkmoor.core
  :repl-options {:init-ns darkmoor.core}
  :profiles {:uberjar {:aot :all}})
