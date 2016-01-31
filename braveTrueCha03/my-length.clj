(defn my-length
  "Comput the length of a vector, list, map, or set."
  [vect]
  (if (first vect)
    (+ 1 (my-length (rest vect)))
    0))
