;; create an infix function that takes a list like `(1 + 3 * 4 - 5)` and
;; transforms it into the lists Clojure needs to evaluate is. is this a general
;; infix transofrmer? - ugh, maybe just write this special case...

;; this is terrible and embarassing...

(defmacro clojurize
  [infixed]
  (list (sixth infixed)
        (list (second infixed)
              (list (fourth infixed)
                    (third infixed)
                    (fifth infixed))
              (first infixed))
        (last infixed)))
