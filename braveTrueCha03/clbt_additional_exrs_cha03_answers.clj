;; NOTE: write docstrings for all of your functions!

;; Are 1/5 and 2.0 equal in Clojure?
(defn math-check1
  []
  (= 1/5 2.0))

;; Are 1/5 and 2/10 equal in Clojure?
(defn math-check2
  []
  (= 1/5 2/10))

;; Are 2/10 and 3/15 equal in Clojure?
(defn math-check3
  []
  (= 3/15 2/10))

;; Is 1.0 divided by 5.0 equal to 1/5 in Clojure?
(defn math-check4
  []
  (= (/ 1.0 5.0) 1/5))

;; What is 1 divided by 3 divided by 3 divided by 3? How many times do you need
;; to type `/` to solve this?
(defn math-check5
  []
  (/ 1 3 3 3))

;; What is 1 divided by the quantity 3 times 3 times 3?
(defn math-check6
  []
  (/ 1 (* 3 3 3))
  )

;; Is 1 divided by 3 divided by 3 divided by 3 equal to 1 divided by the
;; quantity 3 times 3 times 3? 
(defn math-check7
  []
  (= (/ 1 (* 3 3 3))
     (/ 1 3 3 3)))

;; Which are legal maps in Clojure? {a 1}, {"a" 1}, {:a 1}, {'a 1} ?
(defn map-test
  []
  (hash-map {"a" 1} {:a 1}))

;; Use `def` to bind an anonymous function to a named vaiable.
(def my-anon-fun
  (fn [string] (str "You said: " string)))

;; Use `def` to define a list of strings (of your favorite programming languages).
(def my-favorite-programming-languages
  '("Python" "C++" "Clojure" "Lisp" "Perl" "Haskell" "R" "shell-script" "JavaScript"))

;; What do the following return? Are they all legal?
;; (a' {'a 1 'b 2}), (:a {'a 1 'b 2}), (:a {:a 1 :b 2})
(defn map-getter1
  []
  (= 1 (:a {:a 1 :b 2})))

(defn map-getter2
  []
  (nil? (:a {'a 1 'b 2})))

;; What do the following return? Are they all legal?
;; (get {'a 1 'b 2} a'), (get {:a 1 :b 2} :a), (get {"a" 1 "b" 2} "a")
(defn map-get-check1
  []
  (get {:a 1 :b 2} :a))

(defn map-get-check2
  []
  (get {"a" 1 "b" 2} "a"))

;; What do we use `get-in` for? Write a function that uses it.
(defn get-deep-map-check
  []
  (get-in {:a {:x 1 :y 2} :b {:x 3 :y 4}} [:a :x]))

;; How does `conj` operate differently on vectors and lists? What is the
;; difference between these?
;; (conj (conj '() 0) 1)
;; (conj (conj '[] 0) 1)
(defn conj-list-test
  "should give you (1 0)"
  []
  (conj (conj '() 0) 1))

(defn conj-vect-test
  "should give you [0 1]"
  []
  (conj (conj [] 0) 1))

;; Write a function that implements your own version of `nth`, the function
;; that gets the "nth" element of a list.
(defn my-nth
  [lst idx]
  (if (< idx 0)
    nil
    (loop [pos 0 remaining lst]
      (if (>= pos idx)
        (first remaining)
        (recur (inc pos) (rest remaining))))))

;; Write a function called "my-second-thing" that gets the second item from a
;; list or vector (or string).
(defn my-second-thing
  "get the second thing from a list or vector"
  [[fst snd & tail]]
  snd)

;; Use an anonymous function `(fn [args] body)` to add one to each of [1 2 3 4 5].
(defn my-anon-demo-inc
  []
  (map (fn [x] (+ x 1)) [1 2 3 4 5]))

;; Use an anonymous function `#( %)` to multiply each element of the vector
;; [1 2 3 4 5] by 4.
(defn my-anon-demo-inc-by-4
  []
  (map #(+ 4 %) [1 2 3 4 5]))

;; Use an anonymous function `#( %)` to transform the list of names '(Einstein
;; Crick Pauling) such that each name starts with "Dr. " and ends with ", PhD".
(defn doct-ify
  "give a list of names and have them truned into dr phds!"
  [lst]
  (map #(str "Dr. " % ", PhD") lst))

;; Write a function called `all-true?` that accepts a list or a vector of bools
;; and returns `true` if they are all `true` and `false` if any one the entries
;; is `false`. *NOTE:* here and throughout - it is okay to reference library
;; documentation and use existing functions, but consider trying to implement
;; the function on your own too. The best thing to do is try to write the
;; function yourself first, and then look for existing library functions that
;; do the "same thing" and see how similar their behavior is.
(defn all-true?
  "check to see if all the elements of a list or vector are true"
  [[head & tail]]
  (if (nil? head)
    true
    (if (not head)
      false
      (all-true? tail))))

;; Write a function that accepts a string from the user. `if` the string starts
;; with a vowel, print "Vowel", otherwise, print "Consonant". Use `let` to define
;; the set of vowels (and the set of consonants if you want).
(defn vowel?
  "check to see if a given letter character is one of 'aeiou'"
  [letter]
  (let [vowels (set "aeiou")]
    (if (contains? vowels letter)
      true
      false)))

;; Write a function called `check-vowel?` that accepts no arguments and tests
;; whether your `vowel?` function always returns `true` when given `\a`, `\e`,
;; `\i`, `\o`, or `\u`. Hint: `map` your function over `"aeiou"`.
(defn check-vowel?
  "test the `vowel?` function"
  []
  (and (= true (all-true? (map vowel? "aeiou")))
       (= false (all-true? (map vowel? "abeioux")))))

;; Use `when` in a function.
(defn when-test1
  "use `when` to print a message if the argument is truthy"
  [bool-check]
  (when bool-check
    (println "when test 1 passes!")
    ))

;; Write comparisons that check for equality between `nil` and `false` and 
;; `true`.
(defn nil-false-check
  "print a message based on the equality of `nil` and `false`"
  []
  (if (= nil false)
    (println "nil and false are equal")
    (println "nil and false are not equal")))

;; Write a function called `my-first` that returns the first element of
;; a list or vector, and a function called `my-rest` that returns only
;; the elements after the first element of a list or vector.
(defn my-first
  "return the head of a list/vector"
  [[head & tail]]
  head)
(defn my-rest
  "return the tail of a list/vector"
  [[head & tail]]
  tail)

;; `(first [1 2 3])` is `1` and `(rest [1 2 3])` is `(2 3)`. Given that, 
;; what is `(if [] "truthy" "falsey")`? `(if (rest []) "truthy" "falsey")`?
;; `(if (first []) "truthy" "falsey")`?
(defn check-truthy-falsey1
  []
  (if [] "empty vects are truthy" "empty vects are falsey"))

(defn check-truthy-falsey2
  []
  (if (rest []) "the rest of empty is truthy" "the rest of empty is falsey"))

(defn check-truthy-falsey3
  []
  (if (first []) "the first of empty is truthy" "the first of empty is falsey"))

;; The book referenced Project Euler. The first problem in Project Euler asks
;; you to find the sum of all the numbers between 1 and 1000 that are evenly
;; divisble by 3 or 5. Use `loop` and `or` to solve this problem. What if
;; you instead require the number to be evenly divisble by three `and` five?
(defn euler1
  [numbr]
  (let [divisible-by-3-or-5?
        (fn [n] (or (= 0 (rem n 3)) (= 0 (rem n 5))))]
    (loop [countr 0 totaln 0]
      (if (<= countr numbr)
        (if (divisible-by-3-or-5? countr)
          (recur (+ countr 1) (+ totaln countr))
          (recur (+ countr 1) totaln))
        totaln))))

(defn divis-by-3-or-5?
  [n]
  (or (= 0 (rem n 3)) (= 0 (rem n 5))))

(defn divis-by-3-and-5?
  [n]
  (and (= 0 (rem n 3)) (= 0 (rem n 5))))

(defn euler1-general
  [numbr func]
  (loop [countr 0 totaln 0]
    (if (<= countr numbr)
      (if (func countr)
        (recur (inc countr) (+ totaln countr))
        (recur (inc countr) totaln))
      totaln)))

;; Use `into` to add an element to a list, a vector, a set, and a map. Can you
;; use `into` to add a map element to a set, a list, or a vector? Can you use
;; `into` to add a list or a vector to a map? How about sets? Write some code
;; in the REPL to experiment.
(defn into-test-1
  [collection]
  (into collection "element"))

(defn into-test-three-way
  []
  (map into-test-1 '('(0 1 2) [0 1 2] (set "mewllow whirled"))))

(defn into-map
  [mp]
  (into mp {:a 1}))

(defn into-map-test
  []
  (map into-map '({:b 2} {:c 3})))

;; Use `reduce` to compute the factorial of 5.
(defn simple-factorial
  []
  (reduce * [1 2 3 4 5]))

;; Write a function called `my-length` that computes the length of a vector or list.
;; Does the function also work on maps? How about sets?
(defn my-length
  [collection]
  (loop [len 0 remaining collection]
    (if (nil? (first remaining))
      len
      (recur (inc len) (rest remaining)))))

;; Write a function, `my-make-list` that takes a number and makes a list with 
;; elements ranging from 0 to the number (inclusive).
(defn my-make-list
  "make a list from 0 to numbr"
  [numbr]
   (loop [lst '() countr 0]
     (if (> countr numbr)
       (reverse lst)
       (recur (conj lst countr) (inc countr)))))

;; Improve `my-make-list` so it may optionally take two numbers - if two are
;; provided the function makes a list from the first to the second (inclusive).
(defn my-make-list
  "make a list from 0 to numbr"
  ([start stop]
   (loop [lst '() countr start]
     (if (> countr stop)
       (reverse lst)
       (recur (conj lst countr) (inc countr)))))
  ([stop]
   (my-make-list 0 stop)))

;; Improve `my-make-list` again so it may optionally take three numbers. It
;; should have the same existing functionality, but if a third number is provided,
;; it serves as the _step size_. 
(defn my-make-list
  "make a list from 0 to numbr"
  ([start stop stride]
   (loop [lst '() countr start]
     (if (> countr stop)
       (reverse lst)
       (recur (conj lst countr) (+ countr stride)))))
  ([start stop]
   (my-make-list start stop 1))
  ([stop]
   (my-make-list 0 stop 1)))

;; Finally, tidy up `my-make-list` so the lower-arity options call the higher
;; arity implementation code (assuming you haven't already written it that
;; way).

;; Write a function called `my-multiply-elems` that multiplies all the elements
;; of a vector or list together, so `(my-multiply-elems [1 2 3])` should return 6.
;; DON'T use `reduce` (we'll do that in a bit).
(defn my-multiply-elems
  "multiply all the elements of a vector or list together to produce a scalar"
  [collection]
  (loop [prod (first collection) remaining (rest collection)]
    (if (empty? remaining)
      prod
      (recur (* prod (first remaining)) (rest remaining)))))

;; Write  a function called `my-factorial` that calls `my-make-list` and
;; `my-multiply-elems` to compute a factorial.
(defn my-factorial
  "compute a factorial"
  [n]
  (my-multiply-elems (my-make-list 1 n)))

;; Write a function called `simpler-factorial` that computes a factorial using
;; `reduce`.
(defn simpler-factorial
  "compute a factorial"
  [n]
  (reduce * (my-make-list 1 n)))

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
(def my-test-set1 (set '(nil 0 1 2 3 4 5)))

(def my-test-set2 (set '(0 1 2 3 4 5)))

(defn my-set-test1
  [myset]
  (and (= 5 (get myset 5)) (= nil (get myset nil))))

(defn my-set-test2
  [myset]
  (and (= true (contains? myset 5)) (= true (contains? myset nil))))

(defn my-set-test3
  [myset]
  (and (= true (contains? myset nil)) (= true (get myset nil))))

(defn my-set-test4
  [myset]
  (= true (contains? myset nil)))

(defn my-set-test5
  [myset]
  (= true (get myset nil)))

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
(def my-ring-graph
  {:a {:b 1 :e 1}
   :b {:a 1 :c 1}
   :c {:b 1 :d 1}
   :d {:c 1 :e 1}
   :e {:d 1 :a 1}})

(def my-nodes '(:a :b))

(defn get-inner-connections
  [mymap node-list]
  (let [fst (first node-list) snd (second node-list)]
    (if (= (get-in mymap [fst snd]) (get-in mymap [snd fst]))
      (get-in mymap [fst snd])
      "improperly constructed graph!")))
