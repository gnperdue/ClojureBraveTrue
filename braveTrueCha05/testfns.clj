;; Test away.

(defn analysis
  "a pure function - always do the same thing for the same input"
  [text]
  (str "Character count: " (count text)))

(defn analyze-file
  "not a pure function - the file can change"
  [filename]
  (analysis (slurp filename)))

(def great-baby-name "Rosanthony")

(let [great-baby-name "Bloodthunders"]
  great-baby-name)

(defn my-sum
  ([numbrs] (my-sum numbrs 0))
  ([numbrs ttl]
   (if (empty? numbrs)
     ttl
     (my-sum (rest numbrs) (+ (first numbrs) ttl)))))

(defn my-sum-recur
  "generally it is better to use recur for recursion"
  ([numbrs]
   (my-sum-recur numbrs 0))
  ([numbrs acc-total]
   (if (empty? numbrs)
     acc-total
     (recur (rest numbrs) (+ (first numbrs) acc-total)))))

(require '[clojure.string :as s])
(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
;; These could be, e.g. `(fn [c] (:strength (:attributes c)))`
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(defn spell-slots
  [character]
  (int (inc (/ (c-int character) 2))))
(def spell-slots-comp
  (comp int inc #(/ % 2) c-int))

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

(defn sleepy-identify
  "returns the val after 1 second"
  [x]
  (Thread/sleep 1000)
  x)

(def memo-sleep-identify (memoize sleepy-identify))

(defn clean-reduced
  [text]
  (reduce (fn [string string-fn] (string-fn srtring))
          text
          [s/trim #(s/replace % #"lol" "LOL")]))

(def my-test-map-1
  {"RDU" {"ATL" 1 "PHL" 1}})

(def my-test-map-1-conn-RDU-ATL
  (get-in my-test-map-1 ["RDU" "ATL"]))
