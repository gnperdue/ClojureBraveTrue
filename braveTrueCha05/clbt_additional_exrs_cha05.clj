;; Use `comp` in a function.

;; Write a function called `my-comp` that can compose any number of functions.

;; Use `memoize` in a function.

;; Use `lazy-seq` to make a lazy sequence of Fibboncci numbers.
(defn fibo*
  ([] (fibo* 1 1))
  ([a b]
   (let [sum (+ a b)]
     (cons sum (lazy-seq (fibo* b sum))))))

(defn get-fibos
  [len]
  (let [fibos (take (- len 2) (fibo*))]
    (cons 1 (cons 1 fibos))))

;; Use `reduce` to perform a series of transformations to a string.

;; Use `doseq` in a function.

;; Use `take` in a function.

;; Use `apply str` in a function. Is it the same as `clojure.string/join`?

