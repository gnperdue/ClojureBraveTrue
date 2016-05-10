;; Implement map using `first`, `rest`, and `cons` in Clojure.
(defn my-map
  [func sq]
  (if (empty? sq)
    '()
    (cons (func (first sq)) (my-map func (rest sq)))))

;; Pass a set of functions a single collection with map. For example, make
;; three functions, `my-min`, `my-mean`, and `my-max` and put them into a
;; group with `def` (try a `vector` and then a `list`) and another function
;; called `stats` that maps those three functions over a vector of numbers
;; passed to it as an arguemnt.
(defn my-mean
  [nums]
  (/ (reduce + nums) (count nums)))

(defn my-max
  [nums]
  (apply max nums))

(defn my-min
  [nums]
  (apply min nums))

(def stat-funcs
  "For some reason, we must create this as `(list ...)` and not `'(...)`"
  (list my-min my-mean my-max))

(defn stats
  [nums]
  (map #(% nums) stat-funcs))

(defn test-stats
  []
  (let [test-nums [1 2 3 4 5 6 7 8 9 10]]
    (stats test-nums)))

;; Map a key over a list or vector of hash-maps to get all the values.
(def test-map-vect
  [{:a 1.0 :b 2.0 :c 3.0 :d 4.0 :e 5.0}
   {:a 1.1 :b 2.1 :c 3.1 :d 4.1 :e 5.1}
   {:a 1.2 :b 2.2 :c 3.2 :d 4.2 :e 5.2}])

(defn test-test-map-vect
  []
  (map :a test-map-vect))  

;; Use `assoc` in a function.
(defn add-key-and-val-to-test-map
  [tmap ky vl]
  (assoc tmap ky vl))

;; Implement `map` using `reduce`.

;; Implement `filter` using `reduce`.

;; Implement `some` using `reduce`.

;; Use `some` in a function. Try using `some` to see if a list matches some
;; conditions, and then try to `map` that function over a list of lists to see
;; which of them match the condition, etc.
(def test-list-vect
  ['(0 1 2 3 4 5 6 7 8 9)
   '(1 3 4 1 2 4 5 6 8)
   '(0 0 0 0 0 1 0 1)])

(defn test-some
  []
  (some #(> % 2.0) '(0 1 0 1 0 1 2 1 0 3 1)))

(defn test-some-more
  []
  (let [get-some
        (fn [lst] (some #(> % 2.0) lst))]
    (map get-some test-list-vect)))

;; Turn `(concat (take 8 (repeat "na")) '("Batman!"))` into one string. Hint:
;; use `apply`.
(def raw-na-bat
  (concat (take 8 (repeat "na ")) '("Batman!")))

(def polished-na-bat
  (apply str raw-na-bat))

;; Use `partial` in a function - define your own version of `inc`.
(def my-inc
  (partial + 1))

;; Use `into` in a function.

;; Use `conj` in a function.

;; Use `complement` in a function.
