;; NOTE: write docstrings for all of your functions!
;; Also note - `c-x c-e` at the end of a line will evaluate the line
;; `c-x c-e` over a symbol, e.g. `3/15` will evaluate the symbol (to 1/5 here)

;; Are 1/5 and 2.0 equal in Clojure?
(= 1/5 2.0)
;; false

;; Are 1/5 and 2/10 equal in Clojure?
(= 1/5 2/10)
;; true

;; Are 2/10 and 3/15 equal in Clojure?
(= 2/10 3/15)
;; true 

;; Is 1.0 divided by 5.0 equal to 1/5 in Clojure?
;; (= 1.0/5.0 1/5) - you can't do this
(= (/ 1.0 5.0) 1/5)
;; false
(= (/ 1.0 5.0) (/ 2.0 10.0))
;; true

;; What is 1 divided by 3 divided by 3 divided by 3? How many times do you need
;; to type `/` to solve this?
;; just once...
(/ 1 3 3 3)
;; 1/27

;; What is 1 divided by the quantity 3 times 3 times 3?
(/ 1 (* 3 3 3))
;; 1/27

;; Is 1 divided by 3 divided by 3 divided by 3 equal to 1 divided by the
;; quantity 3 times 3 times 3? 
(= (/ 1 3 3 3) (/ 1 (* 3 3 3)))
;; true

;; Which are legal maps in Clojure? {a 1}, {"a" 1}, {:a 1}, {'a 1} ?
;; exception: (def my-test-map1 {a 1})
(def my-test-map1 {"a" 1}) ;; ok
(def my-test-map2 {:a 1})  ;; ok
(def my-test-map3 {'a 1})  ;; ok

;; Use `def` to bind an anonymous function to a named vaiable.
(def my-adder1 (fn [x] (+ x 1)))

(def my-adder2 #(+ % 2))

;; this doesn't work quite right (def my-adder-rest #(+ %& 1))

;; Use `def` to define a list of strings (of your favorite programming languages).
(def my-favorite-strings '("Python" "C++" "bash" "Perl" "Clojure"))

;; What do the following return? Are they all legal?
;; (a' {'a 1 'b 2}), (:a {'a 1 'b 2}), (:a {:a 1 :b 2})
;; illegal: (a' {'a 1 'b 2}) 
(:a {'a 1 'b 2})      ;; nil
(:a {:a 1 :b 2})      ;; 1

;; What do the following return? Are they all legal?
;; (get {'a 1 'b 2} 'a), (get {:a 1 :b 2} :a), (get {"a" 1 "b" 2} "a")
(get {'a 1 'b 2} 'a)     ;; 1
(get {:a 1 :b 2} :a)     ;; 1
(get {"a" 1 "b" 2} "a")  ;; 1

;; What do we use `get-in` for? Write a function that uses it.
;; we use `get-in` to dig into nested maps...
(get-in {:a {:b 2 :c 4} :b {:a 1 :c 3}} [:b :a])   ;; 1
(get-in {:a {:b 2 :c 4} :b {:a 1 :c 3}} [:a :b])   ;; 2

;; How does `conj` operate differently on vectors and lists? What is the
;; difference between these?
;; (conj (conj '() 0) 1)
;; (conj (conj '[] 0) 1)
(conj (conj '() 0) 1)   ;; (1 0)
(conj (conj '[] 0) 1)   ;; [0 1]

;; Write a function that implements your own version of `nth`, the function
;; that gets the "nth" element of a list.
(defn my-nth
  "get the nth elem of a list or vector"
  [n collection]
  (first (drop n collection)))

;; Write a function called "my-second-thing" that gets the second item from a
;; list or vector (or string).
(defn my-second-thing
  [[first-arg second-arg & other-args]]
  second-arg)

;; Use an anonymous function `(fn [args] body)` to add one to each of [1 2 3 4 5].
(map (fn [num] (+ 1 num))
     [1 2 3 4 5])

;; Use an anonymous function `#( %)` to multiply each element of the vector
;; [1 2 3 4 5] by 4.
(map #(* % 4)
     [1 2 3 4 5])

;; Use an anonymous function `#( %)` to transform the list of names '(Einstein
;; Crick Pauling) such that each name starts with "Dr. " and ends with ", PhD".
(map #(str "Dr. " % ", PhD")
     ["Einstein" "Crick" "Pauli"])

;; Write a function called `all-true?` that accepts a list or a vector of bools
;; and returns `true` if they are all `true` and `false` if any one the entries
;; is `false`. *NOTE:* here and throughout - it is okay to reference library
;; documentation and use existing functions, but consider trying to implement
;; the function on your own too. The best thing to do is try to write the
;; function yourself first, and then look for existing library functions that
;; do the "same thing" and see how similar their behavior is.
(defn all-true?
  "cheat a bit and use some library functions..."
  [collection]
  (every? identity collection))

;; Write a function that accepts a string from the user. `if` the string starts
;; with a vowel, print "Vowel", otherwise, print "Consonant". Use `let` to define
;; the set of vowels (and the set of consonants if you want).
(defn any-true?
  "are any elems true"
  [collection]
  (some identity collection))
(defn starts-with-vowel?
  "check to see if a string starts with a voewl"
  [string]
  (let [vs "aeiou"]
    (if (any-true? (map #(= % (first string)) vs))
      (println "starts with a vowel")
      (println "starts with a consonant"))))
;; my first take was much better (although incomplete...
(defn begins-with-vowel?
  [string]
  (let [vs (set "aeiuo")]
    (if (contains? vs (first string))
      true
      false)))

;; Write a function called `check-vowel?` that accepts no arguments and tests
;; whether your `vowel?` function always returns `true` when given `\a`, `\e`,
;; `\i`, `\o`, or `\u`. Hint: `map` your function over `"aeiou"`.
(defn check-vowel?
  "use map list seq to turn a string into a list of lists each holding a char"
  []
  (map begins-with-vowel? (map list (seq "aeiou"))))

;; Use `when` in a function.
(defn up-front-vowel?
  "yeah, yeah"
  [string]
  (when (begins-with-vowel? string)
    (println "it started with a vowel")))

;; Write comparisons that check for equality between `nil` and `false` and 
;; `true`.
(= nil false)
(= nil true)
(= true false)
(nil? nil)
(nil? false)
(nil? true)

;; Write a function called `my-first` that returns the first element of
;; a list or vector, and a function called `my-rest` that returns only
;; the elements after the first element of a list or vector.
(defn my-first
  "get the first elem of a colleciton"
  [[head & tail]]
  head)
(defn my-rest
  "get the first elem of a colleciton"
  [[head & tail]]
  tail)

;; `(first [1 2 3])` is `1` and `(rest [1 2 3])` is `(2 3)`. Given that, 
;; what is `(if [] "truthy" "falsey")`? `(if (rest []) "truthy" "falsey")`?
;; `(if (first []) "truthy" "falsey")`?
(if [] "truthy" "falsey")           ;; truthy
(if (rest []) "truthy" "falsey")    ;; (rest []) is (), so truthy
(if (first []) "truthy" "falsey")   ;; (first []) is nil, so falsey

;; The book referenced Project Euler. The first problem in Project Euler asks
;; you to find the sum of all the numbers between 1 and 1000 that are evenly
;; divisble by 3 or 5. Use `loop` and `or` to solve this problem. What if
;; you instead require the number to be evenly divisble by three `and` five?
(defn my-range
  "get a range of numbers"
  ([end]
   (my-range 0 end))
  ([start end]
   (loop [coll [] counter start]
     (if (> counter end)
       coll
       (recur (conj coll counter) (inc counter))))))

(defn div-by-3-or-5?
  [num]
  (or (= 0 (rem num 5)) (= 0 (rem num 3))))

(defn div-by-3-and-5?
  [num]
  (and (= 0 (rem num 5)) (= 0 (rem num 3))))

;; cheat and just use filter instead of re-implementing it

(def euler-seq (my-range 3 1000))

(def euler-3-or-5 (reduce + (filter div-by-3-or-5? euler-seq)))

(def euler-3-and-5 (reduce + (filter div-by-3-and-5? euler-seq)))

;; Use `into` to add an element to a list, a vector, a set, and a map. Can you
;; use `into` to add a map element to a set, a list, or a vector? Can you use
;; `into` to add a list or a vector to a map? How about sets? Write some code
;; in the REPL to experiment.

(into '(0) '(1 2 3))
(into {:a 1} {:b 2 :c 3})
(into #{1 2} #{3 4 5 6})

;; be careful mixing collectoin types in `into`; we can insert into a list or
;; a vector or a set, but can't arbitrarily `into` a dict

;; Use `reduce` to compute the factorial of 5.
(reduce * (my-range 1 5))

;; Write a function called `my-length` that computes the length of a vector or list.
;; Does the function also work on maps? How about sets?

(defn my-length
  [collection]
  (loop [length 0 coll collection]
    (if (= nil (first coll))
      length
      (recur (inc length) (rest coll)))))

;; works on sets and dicts, etc.

;; Write a function, `my-make-list` that takes a number and makes a list with 
;; elements ranging from 0 to the number (inclusive).

;; did this above...

;; Improve `my-make-list` so it may optionally take two numbers - if two are
;; provided the function makes a list from the first to the second (inclusive).

;; also did tis above...

;; Improve `my-make-list` again so it may optionally take three numbers. It
;; should have the same existing functionality, but if a third number is provided,
;; it serves as the _step size_. 

(defn my-range
  "get a range of numbers"
  ([end]
   (my-range 0 end 1))
  ([start end]
   (my-range start end 1))
  ([start end step]
   (loop [coll [] counter start]
     (if (> counter end)
       coll
       (recur (conj coll counter) (+ counter step))))))

;; Finally, tidy up `my-make-list` so the lower-arity options call the higher
;; arity implementation code (assuming you haven't already written it that
;; way).

;; did this above...

;; Write a function called `my-multiply-elems` that multiplies all the elements
;; of a vector or list together, so `(my-multiply-elems [1 2 3])` should return 6.
;; DON'T use `reduce` (we'll do that in a bit).

(defn my-multiply-elems
  [vectr]
  (loop [start 1 numbers vectr]
    (if (= nil (first numbers))
      start
      (recur (* start (first numbers)) (rest numbers)))))

;; Write  a function called `my-factorial` that calls `my-make-list` and
;; `my-multiply-elems` to compute a factorial.

(defn my-factorial
  [num]
  (my-multiply-elems (my-range 1 num)))

;; Write a function called `simpler-factorial` that computes a factorial using
;; `reduce`.

(defn my-simpler-factorial
  [num]
  (reduce * (my-range 1 num)))

;; Write a function called `my-vect-slicer` that can accept one, two, or three
;; parameters. If the user supplies one parameter, the function should return
;; all the elements of the vector up to that index. If they provide two
;; parameters, the function should return the elements of the vector between
;; those indices. If they supply three parameters, they should act as a start,
;; stop, and stride. Feel free to decide your own fencepost conventions, but
;; there are good arguments for the slice options `a b` to mean `[a:b)` 
;; inclusive of `a`, but stopping before `b`.

;; Make a set that contains `nil`, and the numbers 0 to 5. Can you use `get` to
;; retrieve any element of this set? What about `contains?`? Test your answers
;; in the REPL. What happens when you try a set that contains 0 to 5? Can you
;; `get` or `contains?` the `nil`?

(def my-set #{nil 0 1 2 3 4 5})

(get my-set nil)    ;; nil
(get my-set 0)      ;; 0
(get my-set 6)      ;; nil
(contains? my-set nil)   ;; true
(contains? my-set 0)     ;; true
(contains? my-set 7)     ;; false

(def my-set2 #{0 1 2 3 4 5})

(get my-set2 nil)    ;; nil
(get my-set2 0)      ;; 0
(get my-set2 6)      ;; nil
(contains? my-set2 nil)   ;; false
(contains? my-set2 0)     ;; true
(contains? my-set2 7)     ;; false

;; Recall the several ways we were able to destructure maps and consider the
;; map below - here we are defining a _graph_ as a map of maps, where the
;; "inner maps" label other nodes and the number connections between the outer
;; node and the inner nodes:
;; (def my-ring-graph
;;   {:a {:b 1 :e 1}
;;    :b {:a 1 :c 1}
;;    :c {:b 1 :d 1}
;;    :d {:c 1 :e 1}
;;    :e {:d 1 :a 1}})
;; Write a function that takes a list of nodes, e.g. `'(:a :b)` and returns
;; the number of connections between the nodes.
