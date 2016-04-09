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

;; Write a function called "my-second-thing" that gets the second item from a
;; list or vector (or string).

;; Use an anonymous function `(fn [args] body)` to add one to each of [1 2 3 4 5].

;; Use an anonymous function `#( %)` to multiply each element of the vector
;; [1 2 3 4 5] by 4.

;; Use an anonymous function `#( %)` to transform the list of names '(Einstein
;; Crick Pauling) such that each name starts with "Dr. " and ends with ", PhD".

;; Write a function called `all-true?` that accepts a list or a vector of bools
;; and returns `true` if they are all `true` and `false` if any one the entries
;; is `false`. *NOTE:* here and throughout - it is okay to reference library
;; documentation and use existing functions, but consider trying to implement
;; the function on your own too. The best thing to do is try to write the
;; function yourself first, and then look for existing library functions that
;; do the "same thing" and see how similar their behavior is.

;; Write a function that accepts a string from the user. `if` the string starts
;; with a vowel, print "Vowel", otherwise, print "Consonant". Use `let` to define
;; the set of vowels (and the set of consonants if you want).

;; Write a function called `check-vowel?` that accepts no arguments and tests
;; whether your `vowel?` function always returns `true` when given `\a`, `\e`,
;; `\i`, `\o`, or `\u`. Hint: `map` your function over `"aeiou"`.

;; Use `when` in a function.

;; Write comparisons that check for equality between `nil` and `false` and 
;; `true`.

;; Write a function called `my-first` that returns the first element of
;; a list or vector, and a function called `my-rest` that returns only
;; the elements after the first element of a list or vector.

;; `(first [1 2 3])` is `1` and `(rest [1 2 3])` is `(2 3)`. Given that, 
;; what is `(if [] "truthy" "falsey")`? `(if (rest []) "truthy" "falsey")`?
;; `(if (first []) "truthy" "falsey")`?

;; The book referenced Project Euler. The first problem in Project Euler asks
;; you to find the sum of all the numbers between 1 and 1000 that are evenly
;; divisble by 3 or 5. Use `loop` and `or` to solve this problem. What if
;; you instead require the number to be evenly divisble by three `and` five?

;; Use `into` to add an element to a list, a vector, a set, and a map. Can you
;; use `into` to add a map element to a set, a list, or a vector? Can you use
;; `into` to add a list or a vector to a map? How about sets? Write some code
;; in the REPL to experiment.

;; Use `reduce` to compute the factorial of 5.

;; Write a function called `my-length` that computes the length of a vector or list.
;; Does the function also work on maps? How about sets?

;; Write a function, `my-make-list` that takes a number and makes a list with 
;; elements ranging from 0 to the number (inclusive).

;; Improve `my-make-list` so it may optionally take two numbers - if two are
;; provided the function makes a list from the first to the second (inclusive).

;; Improve `my-make-list` again so it may optionally take three numbers. It
;; should have the same existing functionality, but if a third number is provided,
;; it serves as the _step size_. 

;; Finally, tidy up `my-make-list` so the lower-arity options call the higher
;; arity implementation code (assuming you haven't already written it that
;; way).

;; Write a function called `my-multiply-elems` that multiplies all the elements
;; of a vector or list together, so `(my-multiply-elems [1 2 3])` should return 6.
;; DON'T use `reduce` (we'll do that in a bit).

;; Write  a function called `my-factorial` that calls `my-make-list` and
;; `my-multiply-elems` to compute a factorial.

;; Write a function called `simpler-factorial` that computes a factorial using
;; `reduce`.

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
