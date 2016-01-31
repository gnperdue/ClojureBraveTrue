;; Write a function `mapset` that works like `map` except the return value is a
;; set.
;;         (mapset inc [1 1 2 2])
;;         ; => #{2 3}

(defn mapset0
  [func collection]
  (set (map func collection))
  )

;; user> (mapset0 inc [1 1 2 2])
;; #{3 2}

;; How to re-implement map in Clojure? How to preserve collection type?
;; This re-definition is objectively absolutely terrible.
(defn my-map
  [func collection]
  (loop [newcoll '() oldcoll collection]
    (if (empty? oldcoll)
      (reverse newcoll)
      (recur (conj newcoll (func (first oldcoll))) (rest oldcoll)))))

;; even worse...
(defn my-map2
  [func collection]
  (loop [newcoll '() oldcoll collection]
    (if (empty? oldcoll)
      (reverse (flatten newcoll))
      (recur (list (func (first oldcoll)) newcoll) (rest oldcoll)))))
