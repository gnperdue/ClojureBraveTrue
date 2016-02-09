;; Write a function `atr` that we can call like `(attr :intelligence)` and get
;; that attribute out of a character record.

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
;; These could be, e.g. `(fn [c] (:strength (:attributes c)))`
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(defn attr
  "take a key, e.g. `:strength` to get an attribute value"
  [kv]
  (partial (comp kv :attributes)))

;; user> ((attr :strength) character)
;; 4
