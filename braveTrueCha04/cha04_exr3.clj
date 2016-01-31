;; Write a function named `validate` that checks that `:name` and `:glitter-index`
;; are present when we `append` to our suspects list. `validate` should accept two
;; arguments - a map of keywords to validating functions and the record to be
;; validated.

;; fwpd.core> (#(get % :name) {:name "The Dude" :glitter-index 0})
;; "The Dude"
;; fwpd.core> (#(get % :name) {:alias "The Dude" :glitter-index 0})
;; nil

