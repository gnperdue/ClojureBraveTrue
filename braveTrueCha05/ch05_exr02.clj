;; implement the `comp` function

;; first, implement a `comp2` that composes two functions
(defn comp2
  [f g]
  (fn [& args]
    (f (apply g args))))

;; now, try three
(defn comp3
  [f g h]
  (fn [& args]
    (f (g (apply h args)))))

;; user> (cons :a (cons :b '(:c)))
;; (:a :b :c)

;; user> (drop-last [1 2 3])
;; (1 2)


(defn compn
  [fna & other-fns]
  (let [fns (flatten (list fna other-fns))]
    (fn [& args]
      (let [inner-fn (last fns)
            outer-fns (drop-last fns)
            first-result (apply inner-fn args)]
        (loop [rem-fns outer-fns result first-result]
          (if (empty? rem-fns)
            result
            (recur (drop-last rem-fns) ((last rem-fns) result))))
        ))))

;; user> ((compn inc inc +) 1 1)
;; 4
;; user> ((compn inc inc inc +) 1 1)
;; 5
;; user> ((comp3 inc inc +) 1 1)
;; 4
