;; Use `str`, `vector`, `list`, `hash-map`, and `hash-set` functions.

(defn use-str
  [usr-str]
  (str "You used a string like '" usr-str "'! Heresy!"))

(defn head
  "first element of a vector, list, or string"
  [[first & rest]]
  first)

(def my-ring
  {:a {:b 1 :e 1} :b {:a 1 :c 1} :c {:b 1 :d 1} :d {:c 1 :e 1} :e {:d 1 :a 1}})

;; user> (:a my-ring)
;; {:b 1, :e 1}
;; user> (get my-ring [:a :b])
;; nil
;; user> (get my-ring :a)
;; {:b 1, :e 1}
;; user> (get (get my-ring :a) :b)
;; 1
;; user> (get-in my-ring [:b :a])
;; 1

(def my-list
  (list 0 1 2 3 4 5 6 7 8 9))

(def my-hash-map
  (hash-map :a 0 :b 1 :c 2))

(def my-hash-set
  (hash-set :a :a :b :b :c :c :d :d :d :d :e))
