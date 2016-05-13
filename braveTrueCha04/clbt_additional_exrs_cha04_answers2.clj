;; Implement map using `first`, `rest`, and `cons` in Clojure.
;; Basic idea: (cons (f first) (map f rest))
(defn my-map
  [f seqn]
  (if (nil? (first seqn))
    '()
    (cons (f (first seqn)) (my-map f (rest seqn)))))

;; Suppose you get 25% cash back on a purchase of $75, 15% cash back on a 
;; purchase of $50, 11% cash back on a purchase of $45, 10% cash back on a 
;; purchase of $111, 8% cash back on a purchase of $213, and 5% cash back on
;; a purchase of $2,109.99. How much total cash back did you receive?
(def rebates [0.25 0.15 0.11 0.10 0.08 0.05])
(def expenses [75 50 45 111 213 2109.99])
(println (reduce + (map * rebates expenses)))

;; Pass a set of functions a single collection with map. For example, make
;; three functions, `my-min`, `my-mean`, and `my-max` and put them into a
;; group with `def` (try a `vector` and then a `list`) and another function
;; called `stats` that maps those three functions over a vector of numbers
;; passed to it as an arguemnt.
(defn my-min
  [nums]
  (apply min nums))

(defn my-max
  [nums]
  (apply max nums))

(defn my-mean
  [nums]
  (let [sum (reduce + nums) n (count nums)]
    (/ sum n)))

(def stat-funcs [my-min my-max my-mean])

(defn stats
  [nums]
  (map #(% nums) stat-funcs))

;; Map a key over a list or vector of hash-maps to get all the values.
(def my-test-maps '({:a 1 :b 2 :c 3} {:a 11 :b 12 :c 13} {:a 21 :b 22 :c 23}))
(map :a my-test-maps)

;; Use `assoc` in a function.
(def my-path {:a :b})
(defn path-extender
  [path start-node end-node]
  (assoc path start-node end-node))
(path-extender my-path :b :c)

;; Implement `map` using `reduce`.

;; Implement `filter` using `reduce`.

;; Implement `some` using `reduce`.

;; Implement `take`, `drop`, `take-while`, and `drop-while`.
(defn my-take
  [n seqn]
  (loop [ret [] procd seqn]
    (if (>= (count ret) n)
      (seq ret)
      (recur (conj ret (first procd)) (rest procd)))))

(defn my-drop-recur
  [n seqn]
  (if (= 0 n)
    seqn
    (my-drop (- n 1) (rest seqn))))

(defn my-drop
  [n seqn]
  (loop [ret seqn cntr 0]
    (if (>= cntr n)
      ret
      (recur (rest ret) (inc cntr)))))

(defn my-take-while
  [pred seqn]
  (loop [ret [] procd seqn]
    (if (nil? (first procd))
      (seq ret)
      (if (pred (first procd))
        (recur (conj ret (first procd)) (rest procd))
        (seq ret)))))

(defn my-drop-while
  [pred seqn]
  (loop [ret seqn]
    (if (nil? (first ret))
      '()
      (if (pred (first ret))
        (recur (rest ret))
        ret))))

;; sort [6 3 2 4 5 1] both ascedning and descending. hint: `(doc compare)`
(sort [6 3 2 4 5 1])                    ;; ascending
(sort #(compare %1 %2) [6 3 2 4 5 1])   ;; ascending
(sort #(compare %2 %1) [6 3 2 4 5 1])   ;; descending

;; Use `some` in a function. Try using `some` to see if a list matches some
;; conditions, and then try to `map` that function over a list of lists to see
;; which of them match the condition, etc.
(some #(> % 3) '(1 2))
(some #(> % 3) '(1 2 3 4 5))
(map #(> % 3) '(1 2 3 4 5))

;; Turn `(concat (take 8 (repeat "na")) '("Batman!"))` into one string. Hint:
;; use `apply` or `reduce`.
(concat (take 8 (repeat "na ")) '("Batman!"))
(apply str (concat (take 8 (repeat "na ")) '("Batman!")))
(reduce str (concat (take 8 (repeat "na ")) '("Batman!")))

;; Use `partial` in a function - define your own version of `inc`.
(def my-inc (partial + 1))
(map my-inc (range 1 10))

;; Use `into` in a function.

;; Use `conj` in a function.

;; Use `complement` in a function.

;; Make a lazy seq of Fibonnaci numbers that is 1,000,000 elements long. Get the
;; fourth element.

(defn fibon
  "a non-lazy fibo sequence generator that gives the sequence in reverse order"
  [n]
  (loop [seqn '(1 1) cntr 2]
    (if (>= cntr n)
      seqn
      (recur (cons (+ (second seqn) (first seqn)) seqn) (inc cntr)))))

;; Make a lazy seq of Fibonnaci numbers that is inifinitely long. Get the fourth 
;; element.

(defn fib
  "a lzy fibo sequence generator that gives the seq in the right order"
  [n1 n2]
  (cons n1 (lazy-seq (fib n2 (+ n1 n2)))))

(def my-fibs (fib 1 1))
(last (take 4 my-fibos))


  
