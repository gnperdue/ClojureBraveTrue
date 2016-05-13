;; Test away

(def human-consumption [8.1 7.3 6.6 5.0])

(def critter-consumption [0.0 0.2 0.3 1.1])

(defn unify-diet-data
  "expect two args, each a number"
  [human critter]
  {:human human
   :critter critter})

;; (map unify-diet-data human-consumption critter-consumption)

(def sum #(reduce + %))

(def avg #(/ (sum %) (count %)))

(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spiderman" :real "Peter Parker"}
   {:alias "Santa" :real "Your Mom"}
   {:alias "Easter Bunny" :real "Your Dad"}
   ])

(defn reduce-test1
  []
  (reduce (fn [new-map [key val]]
            (assoc new-map key (inc val)))
          {}
          {:max 30 :min 10 :mid 15}))

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}
   ])

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true :name "McMackson"}
   2 {:makes-blood-puns? true, :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true, :has-pulse? true :name "Mickey Mouse"}
   4 {:makes-blood-puns? false, :has-pulse? false :name "Rob Zombie"}
   5 {:makes-blood-puns? true, :has-pulse? true :name "Castlevania Guy"}
   6 {:makes-blood-puns? false, :has-pulse? true :name "McMansion"}
   7 {:makes-blood-puns? false, :has-pulse? true :name "McMansion1"}
   8 {:makes-blood-puns? false, :has-pulse? true :name "McMansion2"}
   9 {:makes-blood-puns? false, :has-pulse? true :name "McMansion3"}
   10 {:makes-blood-puns? false, :has-pulse? true :name "McMansion4"}
   11 {:makes-blood-puns? false, :has-pulse? true :name "McMansion5"}
   12 {:makes-blood-puns? false, :has-pulse? true :name "McMansion6"}
   13 {:makes-blood-puns? false, :has-pulse? true :name "McMansion7"}
   14 {:makes-blood-puns? false, :has-pulse? true :name "McMansion8"}
   15 {:makes-blood-puns? false, :has-pulse? true :name "McMansion9"}
   16 {:makes-blood-puns? false, :has-pulse? true :name "McMansion10"}
   17 {:makes-blood-puns? false, :has-pulse? true :name "McMansion11"}
   18 {:makes-blood-puns? false, :has-pulse? true :name "McMansion12"}
   19 {:makes-blood-puns? false, :has-pulse? true :name "McMansion13"}
   20 {:makes-blood-puns? false, :has-pulse? true :name "McMansion14"}
   21 {:makes-blood-puns? false, :has-pulse? true :name "McMansion15"}
   22 {:makes-blood-puns? false, :has-pulse? true :name "McMansion16"}
   23 {:makes-blood-puns? false, :has-pulse? true :name "McMansion17"}
   24 {:makes-blood-puns? false, :has-pulse? true :name "McMansion18"}
   25 {:makes-blood-puns? false, :has-pulse? true :name "McMansion19"}
   26 {:makes-blood-puns? false, :has-pulse? true :name "McMansion20"}
   27 {:makes-blood-puns? false, :has-pulse? true :name "McMansion21"}
   28 {:makes-blood-puns? false, :has-pulse? true :name "McMansion22"}
   29 {:makes-blood-puns? false, :has-pulse? true :name "McMansion23"}
   30 {:makes-blood-puns? false, :has-pulse? true :name "McMansion24"}
   31 {:makes-blood-puns? false, :has-pulse? true :name "McMansion25"}
   32 {:makes-blood-puns? false, :has-pulse? true :name "McMansion26"}
   33 {:makes-blood-puns? false, :has-pulse? true :name "McMansion27"}
   34 {:makes-blood-puns? false, :has-pulse? true :name "McMansion28"}
   35 {:makes-blood-puns? false, :has-pulse? true :name "McMansion29"}
   36 {:makes-blood-puns? false, :has-pulse? true :name "McMansion30"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(defn my-conj
  [target & additions]
  (into target additions))

(defn my-into
  [target additions]
  (apply conj target additions))

(def add10 (partial + 10))

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))

(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))

(defn identify-humans
  [social-security-numbers]
  (filter #(not (vampire? %))
          (map vampire-related-details social-security-numbers)))

(def not-vampire? (complement vampire?))

(defn identify-humans
  [social-security-numbers]
  (filter not-vampire?
          (map vampire-related-details social-security-numbers)))

(defn my-complement
  [fun]
  (fn [& args]
    (not (apply fun args))))
