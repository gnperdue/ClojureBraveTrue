
(defn criticize-code
  [criticism code]
  `(println ~criticism (quote ~code)))

(defmacro code-critic
  [bad good]
  `(do ~(criticize-code "Cursed bacteria of Liberia, this is bad code: " bad)
       ~(criticize-code "Sweet sacred boa of Samoa, this is good code:" good)))
