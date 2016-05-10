(defn thomas-the-train
  "this is a docstring"
  []
  (println "Chugga-choo"))

(def my-map {:first-name "Charlie"
             :last-name "McFishwich"})

(def my-fn-map {"string-key" +})

(def my-nested-map {:name {:first "John" :middle "Jacob" :last "Jingle"}})

(defn too-enthusiastic
  "Return a cheer that might be a bit too enthusiastic."
  [name]
  (str "OH. MY. GOD!" name " YOU ARE THE BEST!"))

(defn no-params
  []
  "I take no parameters!")

(defn one-param
  [x]
  (str "I take one parameter: " x))

(defn two-params
  [x y]
  (str "Two parameters. I will smoosh them "
       "together to spite you! " x y))

(defn multi-arity
  ([arg1 arg2 arg3]
   (str "Three args: " arg1 " " arg2 " " arg3))
  ([arg1 arg2]
   (str "Two args: " arg1 " " arg2))
  ([arg1]
   (str "One arg: " arg1))
  ([]
   "No args"))

(defn x-chop
  "Describe the kind of chop you're inflicting on someone."
  ([name chop-type]
   (str "I " chop-type " chop " name "! Take that!"))
  ([name]
   (x-chop name "karate")))

(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper "!!!"))

(defn codger
  [& whippersnappers]
  (map codger-communication whippersnappers))

(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join ", " things)))

;; return the first element of a collection
(defn my-first
  [[first-thing]]   ;; notice `first-thing` is within a vector
  first-thing)

(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring your other choices. "
                "Here they are in case you need them: "
                (clojure.string/join ", " unimportant-choices))))

(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(defn announce-trasure-loc-keys
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(defn receive-treasure-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(defn illustrative-function
  []
  (+ 1 304)
  30
  "joe")

(defn number-comment
  [x]
  (if (> x 6)
    "OMG that is a big number."
    "That number is okay, I guess."))

(defn inc-maker
  "Create an incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(def failed-protagonist-names
  ["Larry Potter", "Doreen the Explorer", "The Incredible Bulk"])

(defn error-message
  [severity]
  (str "OH NOES! IT'S A DISASTER! WE'RE "
       (if (= severity :mild)
         "MILDLY INCONVENIENCED!"
         "DOOOOOOOOOOOOMED!")))

;; This won't work because '() and [] are truthy...
;; (defn my-length
;;   [vect]
;;   (if vect
;;     (+ 1 (my-length (rest vect)))
;;     0))
;;
;; user> (if []
;;         "hello"
;;         "goodbye")
;; "hello"
;;
;; But...
;; user> (if (first [])
;;         "hello"
;;         "goodbye")
;; "goodbye"

(defn my-length
  "Comput the length of a vector, list, map, or set."
  [vect]
  (if (first vect)
    (+ 1 (my-length (rest vect)))
    0))

(defn my-multiply-elems
  [vect]
  (if (first vect)
    (* (first vect) (my-multiply-elems (rest vect)))
    1))

(defn my-multiply-elems2
  [vect]
  (if (empty? vect)
    1
    (* (first vect) (my-multiply-elems2 (rest vect)))))

;; this doesn't work, the decrement never occurs
(defn my-test-2
  [x]
  (loop [countr x]
    (if (> countr 0)
      (do (println countr)
          (recur (- countr 1)))
      (println countr)))
  )

(defn my-test-1
  [x]
  (loop [countr x]
    (if (> countr 0)
      (do (println countr)
          (recur (dec countr)))
      (println countr)))
  )

(defn my-make-list
  ([stop]
   (my-make-list 0 stop 1))
  ([start stop]
   (my-make-list start stop 1))
  ([start stop step]
   (loop [lst '() countr start]
     (if (<= countr stop)
       (do
         (println countr)
         (recur (conj lst countr) (+ countr step)))
       (reverse lst)))))

(defn my-make-vec
  [x]
  (loop [vectr [] countr x]
    (if (>= countr 0)
      (do
        (println countr)
        (recur (conj vectr countr) (dec countr)))
      vectr)))

(defn my-factorial
  [n]
  (my-multiply-elems (my-make-list 1 n)))

(defn simpler-factorial
  [n]
  (reduce * (my-make-list 1 n)))


(defn my-reduce
  ([f initial coll]
   (loop [result initial
          remaining coll]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
   (my-reduce f head tail)))

(defn euler1
  [maxn]
  (loop [numbr 0
         tsum 0]
    (do
      (println (str "number = " numbr ", sum = " tsum))
      (if (> numbr maxn)
        tsum
        (recur (+ 1 numbr) (if (or (= (mod numbr 3) 0)
                                   (= (mod numbr 5) 0))
                             (+ numbr tsum)
                             tsum))))))

(defn list-maker
  [x]
  (loop [countr 0 lst '()]
    (if (>= countr x)
      lst
      (recur (inc countr) (conj lst countr)))))
