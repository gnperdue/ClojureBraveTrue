;; implement `assoc-in`- hint: use the `assoc` function and define its parameters
;; as [m [k & ks] v]

;; user> (assoc {} :key1 "val1" :key2 "val2")
;; {:key1 "val1", :key2 "val2"}
;; user> (def my-tmp-map {:key0 "val0"})
;; #'user/my-tmp-map
;; user> (assoc my-tmp-map :key1 "val1" :key2 "val2")
;; {:key0 "val0", :key1 "val1", :key2 "val2"}

;; user> (assoc-in {} [:a :b :c] true)
;; {:a {:b {:c true}}}

(defn my-assoc-1
  [m k v]
  (assoc m k v)
  )

(defn my-assoc-2
  [m k1 k2 v]
  (assoc m k1 (assoc {}  k2 v))
  )

(defn my-assoc-3
  [m k1 k2 k3 v]
  (assoc m k1 (assoc {} k2 (assoc {}  k3 v)))
  )

;; user> (my-assoc-3 {} :key1 :key2 :key3 "value")
;; {:key1 {:key2 {:key3 "value"}}}
;; user> (assoc-in {}  [:key1 :key2 :key3] "value")
;; {:key1 {:key2 {:key3 "value"}}}

(defn my-assoc-in
  [m [k & ks] v]
  (if (empty? ks)
    (assoc m k v)
    (let [new-v (assoc {}  (last ks) v)]
      (my-assoc-in m (apply vector (cons k (drop-last ks))) new-v))))

