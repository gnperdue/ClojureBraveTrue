(defmacro backwards
  "break the rule that the operand must be first"
  [form]
  (reverse form))
