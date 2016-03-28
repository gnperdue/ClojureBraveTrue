
(defmacro code-critic
  "phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  (list 'do
        (list 'println
              "Great squid of Madrid, this is bad code:"
              (list 'quote bad))
        (list 'println
              "Swwet gorilla of Manila, this is good code:"
              (list 'quote good))))
