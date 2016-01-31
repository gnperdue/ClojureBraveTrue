;; Write a function that takes a number and adds 100 to it.

(defn century-plus
  "Take a number and add a century to it!"
  [numbr]
  (+ 100 numbr))

;; user> (century-plus 1)
;; 101
;; user> (century-plus 2)
;; 102
;; user> (map century-plus '(0 1 2 3 4 5 6 7 8 9))
;; (100 101 102 103 104 105 106 107 108 109)

