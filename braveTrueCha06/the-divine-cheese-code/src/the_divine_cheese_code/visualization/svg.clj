(ns the-divine-cheese-code.visualization.svg
  (:require [clojure.string :as s])
  (:refer-clojure :exclude [min max]))

(defn latlng->point
  "Convert lat/lng map to comma-separated string"
  [latlng]
  (str (:lng latlng) "," (:lat latlng)))

(defn points
  "Given a set of lat/lng maps, return string of points joined by space"
  [locations]
  (clojure.string/join " " (map latlng->point locations)))

(defn line
  [points]
  (str "<polyline points=\"" points "\" />"))

(defn comparator-over-maps
  "returns a function that compares the values for the keys provided"
  [comparison-fn ks]
  (fn [maps]
    (zipmap ks
            (map (fn [k] (apply comparison-fn (map k maps)))
                 ks)))) 

(def min (comparator-over-maps clojure.core/min [:lat :lng]))
(def max (comparator-over-maps clojure.core/max [:lat :lng]))

(defn translate-to-00
  "Find the `min` of our locations and subtract that value from each locaton.
  `locations` is a vector of dictionaries (maps)."
  [locations]
  (let [minicoords (min locations)]
    (map #(merge-with - % minicoords) locations)))

(defn scale
  "`width` and `height` are numbers that scale the `:lng` and `:lat` of the
  `locations` vector of maps."
  [width height locations]
  (let [maxcoords (max locations)
        ratio {:lat (/ height (:lat maxcoords))
               :lng (/ width (:lng maxcoords))}]
    (map #(merge-with * % ratio) locations)))

(defn transform
  "just chains other functions"
  [width height locations]
  (->> locations
       translate-to-00
       (scale width height)))

(defn xml
  "svg 'template' that also flips the coordinate system"
  [width height locations]
  (str "<svg height=\"" height "\" width=\"" width "\">"
       "<g transform=\"translate(0, " height ")\">"
       "<g transform=\"scale(1, -1)\">"
       (-> (transform width height locations)
           points
           line)
       "</g></g>"
       "</svg>"))
