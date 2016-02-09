;; Implement `update-in`

;; user> (def q {:name "James" :age 26})
;; #'user/q
;; user> (update q :age inc)
;; {:name "James", :age 27}
;; user> (update q :name #(str "Look out " %))
;; {:name "Look out James", :age 26}

;; user> (def dude {:name "The Dude" :age 50 :level 0})
;; #'user/dude
;; user> dude
;; {:name "The Dude", :age 50, :level 0}
;; user> (update-in dude [:age :level] inc)
;; NullPointerException   clojure.lang.Numbers.ops (Numbers.java:1013)
;; user> (update-in dude [:age] inc)
;; {:name "The Dude", :age 51, :level 0}
;; user> (update-in dude [:level] inc)
;; {:name "The Dude", :age 50, :level 1}
;; user> (def dudette {:name "The Dudette" :age 30 :level {:primary 10 :secondary 11}})
;; #'user/dudette
;; user> (update-in dudette [:level :primary] inc)
;; {:name "The Dudette", :age 30, :level {:primary 11, :secondary 11}}

;; We may dig into multiple levels.
(def player1 {:name "Player 1" :attribs {:str 10 :int 11 :wis 9}})

(update-in player1 [:attribs :str] inc)
;; {:name "Player 1", :attribs {:str 11, :int 11, :wis 9}}

(update-in player1 [:attribs :str] * 2)
;; {:name "Player 1", :attribs {:str 20, :int 11, :wis 9}}

(def player0 {:name "Player 0" :str 10})

;; user> (update player0 :str inc)
;; {:name "Player 0", :str 11}

(def player2 {:name "Player 2" :attribs {:str 10 :int 11 :wis 12}})

;; user> (update player2 :attribs #(update % :str inc))
;; {:name "Player 2", :attribs {:str 11, :int 11, :wis 12}}

;; well...
