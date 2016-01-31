;; Crete a function like `symmetrize-body-parts` except it must operate on aliens
;; with radial symmetry. Instead of two arms, etc., they have five.

(def asym-alien-body-parts [
                            {:name "head" :size 3}
                            {:name "first-eye" :size 1}
                            {:name "firts-ear" :size 1}
                            {:name "mouth" :size 1}
                            {:name "nose" :size 1}
                            {:name "neck" :size 2}
                            {:name "first-shoulder" :size 3}
                            {:name "first-upper-arm" :size 3}
                            {:name "chest" :size 10}
                            {:name "back" :size 10}
                            {:name "first-forearm" :size 3}
                            {:name "abdomen" :size 6}
                            {:name "first-kidney" :size 1}
                            {:name "first-hand" :size 2}
                            {:name "first-knee" :size 2}
                            {:name "first-thigh" :size 4}
                            {:name "first-lower-leg" :size 3}
                            {:name "first-achilles" :size 1}
                            {:name "first-foot" :size 2}
                            ])

(defn matching-part
  "numberstr should be stuff like 'second-', 'third-', etc."
  [part numberstr]
  {:name (clojure.string/replace (:name part) #"^first-" numberstr)
   :size (:size part)})

(def symmetry-vect ["second-" "third-" "fourth-" "fifth-"])

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts sym-part]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part sym-part)])))
          []
          asym-body-parts))

(defn symmetrize-alien-parts
  [sym-part]
  (symmetrize-body-parts asym-alien-body-parts sym-part))

(defn symmtrize-all-body-parts
  []
  (map symmetrize-alien-parts symmetry-vect))
