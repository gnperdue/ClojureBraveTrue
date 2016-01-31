(ns fwpd.core)                    ;; establish the namesapce
(def filename "suspects.csv")     ;; (slurp filename)

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns."
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(> (:glitter-index %) minimum-glitter) records))

(def list-of-suspects
  (mapify (parse (slurp filename))))

(def list-of-vampires
  (glitter-filter 3 (mapify (parse (slurp filename)))))

(def list-of-vampire-names
  "Chapter 4 Exercise 1"
  (map :name list-of-vampires))

(defn append-new-suspect
  [new-suspect suspect-list]
  (conj suspect-list new-suspect))

;; (def validations {:name #(= :name %)
;;                   :glitter-index #(= :glitter-index %)})

(def validations {:name #(get % :name)
                  :glitter-index #(get % :glitter-index)})

(def test-rec-g
  {:name "The Dude" :glitter-index 0})

(def test-rec-b
  {:alias "The Dude" :glitter-index 0})

(defn validate-simple
  [rec]
  (and (not (nil? (get rec :name)))
       (not (nil? (get rec :glitter-index)))))

(defn all-true?
  "check to see if all the elements of a list or vector are true"
  [[head & tail]]
  (if (nil? head)
    true
    (if (not head)
      false
      (all-true? tail))))

(defn validate-with-keyset
  [rec keyset]
  (all-true? (map #(not (nil? (get rec %))) keyset)))

(defn stringify
  [record]
  (str (:name record) "," (:glitter-index record)))

(defn makecsv
  [records]
  (clojure.string/join "\n" (map stringify records)))

(defn showcsv
  []
  (println (makecsv list-of-suspects)))
