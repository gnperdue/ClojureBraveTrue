;; Write a function, `dec-maker` that works like `inc-maker`, but with subtraction.
;;         (def dec9 (dec-maker 9))
;;         (dec9 10)
;;         ; => 1
;;
;; Anonymous function syntax 1:
;;         (fn [param-list]
;;           function body)

(defn dec-maker
  [numbr]
  (fn [x]
    (- x numbr)))

;; user> (def dec9 (dec-maker 9))
;; #'user/dec9
;; user> (dec9 10)
;; 1
;; user> (dec9 100)
;; 91
;; user> (dec9 0)
;; -9

;; Anonymous function syntax 2:
;;         #( %)

(defn dec-maker2
  [numbr]
  #(- % numbr))

;; user> (def dec7 (dec-maker2 7))
;; #'user/dec7
;; user> (dec7 10)
;; 3
;; user> (dec7 7)
;; 0
;; user> (dec7 7000)
;; 6993
