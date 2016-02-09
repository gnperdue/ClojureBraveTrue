;; look-up and use `update-in`

;; user> (def p {:name "James" :age 26})
;; #'user/p
;; user> p
;; {:name "James", :age 26}
;; user> (update-in p [:age] inc)
;; {:name "James", :age 27}
;; user> p
;; {:name "James", :age 26}
;; user> (update-in p [:age] + 10)
;; {:name "James", :age 36}
;; user> p
;; {:name "James", :age 26}
;; user> (update-in p [:age] - 10)
;; {:name "James", :age 16}

(def users [{:name "James" :age 26} {:name "John" :age 43}])

(println (update-in users [1 :age] inc))
