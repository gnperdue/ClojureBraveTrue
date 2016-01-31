# Core Functions in Depth

_Programming to abstractions_ and _sequence_ and _collection_ abstractions.
_Lazy sequences_.

## Programmng to Abstractions

Emacs lisp (elisp) needs `mapcar` and `maphash` to map a function over a list and
a hash map respectively. But we can do both with just `map` in Clojure. The
reason is that Clojure defines `map` and `reduce` (and other) functions in terms
of the _sequence abstraction_, not in terms of specific data structures.

Abstractions are like named collections of operations. If we can perform all
of them on an object (e.g., `first`, `rest`, `cons`), then we say the object is
an instance of that abstraction.

### Treating Lists, Vectors, Sets, and Maps as Sequences

A _sequence_ is a collection of elements organized in linear order. Clojurists
usually refer to _seq_ instead of sequence, and _seq functions_ and the
_seq library_.

If the core sequence functions `first`, `rest`, and `cons` work on a data
structure, we can say the data structure _implements_ the sequence abstraction.

    user> (defn titlesize
            [topic]
            (str topic " for the Brave and True"))
    #'user/titlesize
    user> (map titlesize ["Hamsters" "Ragnorok"])
    ("Hamsters for the Brave and True" "Ragnorok for the Brave and True")
    user> (map titlesize '("Empathy" "Decorating"))
    ("Empathy for the Brave and True" "Decorating for the Brave and True")
    user> (map titlesize #{"Elbows" "Soap Carving"})
    ("Elbows for the Brave and True" "Soap Carving for the Brave and True")
    user> (map #(titlesize (second %)) {:uncomfortable-thing "Winking"})
    ("Winking for the Brave and True")

### `first`, `rest`, and `cons`

It doesn't matter how a particular data structure is implemented: when it comes
to using seq functions, all Clojure asks is "can I `first`, `rest`, and `cons`
it?" If so, we may use the seq library with that data structure.

We drop into JavaScript to fiddle with this a bit...

    braveTrueCha04$ js -f map.js
    first
    middle
    last
    new first
    first
    first mapped!
    middle mapped!
    Transylvania mapped!,Forks, WA mapped!,

### Abstraction Through Indirection

_Indirection_ is a generic term for the mechanisms a language employes so that
one name may have multiple, related meanings. The name `first` has multiple,
data-structure-specific meanings; indirection is what makes this possible.

_Polymorphism_ is one way Clojure provides indirection. Basically, polymorphic
functions dispatch to different function bodies based on the type of argument
supplied.

Clojure also creates indirection for sequences by doing a sort of type conversion,
producing a data structure that works with an abstraction's functions. Whenever
Clojure expects a sequence, e.g., when we call `map`, `first`, or `cons`, it calls
the `seq` function to obtain a data structure that allows for `first`, `rest`, and
`cons`.

    user> (seq '(1 2 3))
    (1 2 3)
    user> (seq [1 2 3])
    (1 2 3)
    user> (seq #{1 2 3})
    (1 3 2)
    user> (seq {:name "Bill Compton" :occupation "Dopey money guy"})
    ([:name "Bill Compton"] [:occupation "Dopey money guy"])

Note that `seq` always returns a value that looks and acts like a list. Also note
what is going on with the `map`...

    user> (first (seq {:name "Bill Compton" :occupation "Dopey money guy"}))
    [:name "Bill Compton"]
    user> (first (first (seq {:name "Bill Compton" :occupation "Dopey money guy"})))
    :name

We can conver the seq back into a `map` with `into`:

    user> (into {} (seq {:name "Bill Compton" :occupation "Dopey money guy"}))
    {:name "Bill Compton", :occupation "Dopey money guy"}
    user> (get
           (into {}
                 (seq {:name "Bill Compton"
                       :occupation "Dopey money guy"}))
           :name)
    "Bill Compton"

The takeaway is we should focus on what we can _do_ with data structures and not
on how they are implemented.

    user> (doc cons)
    -------------------------
    clojure.core/cons
    ([x seq])
      Returns a new seq where x is the first element and seq is
        the rest.
    nil
    user> (cons 1 '(2 3))
    (1 2 3)
    user> (conj '(2 3) 1)
    (1 2 3)

## `seq` Function Examples

Surprising _and_ delightful...

### `map`

    user> (map inc [1 2 3])
    (2 3 4)
    user> (map str ["a" "b" "c"] ["A" "B" "C"])
    ("aA" "bB" "cC")
    user> (map str ["a" "b" "c"] ["A" "B" "C"] ["1" "2" "3"])
    ("aA1" "bB2" "cC3")

When we pass `map` multiple collections, the elements of the first collection
will be passed as the first argument of the mapping function, the elements of
the second collection will be passsed as the second, etc.

    user> (map unify-diet-data human-consumption critter-consumption)
    ({:human 8.1, :critter 0.0} {:human 7.3, :critter 0.2} {:human 6.6, :critter 0.3} {:human 5.0, :critter 1.1})

We can even pass groups of functions a single collection!

    user> (stats [3 4 10])
    (17 3 17/3)
    user> (stats [80 1 44 13 6])
    (144 5 144/5)

We can even map keys to get the associated values from `map`s:

    user> (map :real identities)
    ("Bruce Wayne" "Peter Parker" "Your Mom" "Your Dad")

### `reduce`

    user> (reduce (fn [new-map [key val]]
                    (assoc new-map key (inc val)))
                  {}
                  {:max 30 :min 10})
    {:max 31, :min 11}

How does `assoc` work?

    user> (assoc {:max 10} :min 5)
    {:max 10, :min 5}
    user> (assoc {} :min 5)
    {:min 5}
    user> (assoc {} :max 10 :min 5)
    {:max 10, :min 5}

In our example, `reduce` treats the argument like a sequence of vectors, e.g.,
`'([:max 30] [:min 10])`. Then it starts with an empty map and builds it up using
an anonymous function.

Here, the magic of `reduce` is it lets us treat the map like a sequence of two-
element vectors, so getting the key and value is as simple as just destructuring
the vector.

We can also filter...

    user> (reduce (fn [new-map [key val]]
                    (if (> val 4)
                      (assoc new-map key val)
                      new-map))
                  {}
                  {:human 4.1 :critter 3.9})
    {:human 4.1}

Whenever we want to derive a new value from a seqable data structure, `reduce`
will usually be able to do what we need.

### `take`, `drop`, `take-while`, and `drop-while`

    user> (take 3 [1 2 3 4 5 6])
    (1 2 3)
    user> (drop 3 [1 2 3 4 5 6])
    (4 5 6)

`take-while` and `drop-while` take _predicate functions_...

    user> (take-while #(< % 3) '(1 2 3 4 5 6))
    (1 2)
    user> (drop-while #(< % 3) '(1 2 3 4 5 6))
    (3 4 5 6)

And...

    user> (take-while #(< (:month %) 3) food-journal)
    ({:month 1, :day 1, :human 5.3, :critter 2.3} {:month 1, :day 2, :human 5.1, :critter 2.0} {:month 2, :day 1, :human 4.9, :critter 2.1} {:month 2, :day 2, :human 5.0, :critter 2.5})
    user> (drop-while #(< (:month %) 3) food-journal)
    ({:month 3, :day 1, :human 4.2, :critter 3.3} {:month 3, :day 2, :human 4.0, :critter 3.8} {:month 4, :day 1, :human 3.7, :critter 3.9} {:month 4, :day 2, :human 3.7, :critter 3.6})
    user> (take-while #(< (:month %) 4)
                      (drop-while #(< (:month %) 2) food-journal))
    ({:month 2, :day 1, :human 4.9, :critter 2.1} {:month 2, :day 2, :human 5.0, :critter 2.5} {:month 3, :day 1, :human 4.2, :critter 3.3} {:month 3, :day 2, :human 4.0, :critter 3.8})

### `filter` and `some`

    user> (filter #(< (:human %) 3.8) food-journal)
    ({:month 4, :day 1, :human 3.7, :critter 3.9} {:month 4, :day 2, :human 3.7, :critter 3.6})
    user> (filter #(< (:month %) 3) food-journal)
    ({:month 1, :day 1, :human 5.3, :critter 2.3} {:month 1, :day 2, :human 5.1, :critter 2.0} {:month 2, :day 1, :human 4.9, :critter 2.1} {:month 2, :day 2, :human 5.0, :critter 2.5})

We can often use `filter` in place of `take-while`, but sometimes `take-while` is
more efficient (for example, if we know something about the ordering of the data).

Often, we want to know if a collection contains _any_ values that test true for
a given predicate. `some` does that, returning the first truthy value for a
predicate:

    user> (some #(> (:critter %) 5) food-journal)
    nil
    user> (some #(> (:critter %) 3) food-journal)
    true

We could get the entry if our predicate returned that:

    user> (some #(and (> (:critter %) 3) %) food-journal)
    {:month 3, :day 1, :human 4.2, :critter 3.3}

### `sort` and `sort-by`

`sort` will sort elements in ascending order:

    user> (sort [3 1 2])
    (1 2 3)

It defaults to sort of reasonable things:

    user> (sort [[3 1 2] [5 6 4 7]])
    ([3 1 2] [5 6 4 7])
    user> (sort [[3 1 2 0 -1] [5 6 4 7]])
    ([5 6 4 7] [3 1 2 0 -1])

If we need to do something a bit fancier, we have `sort-by`, which accepts a
_key function_.

    user> (sort-by count ["aaa" "c" "bb"])
    ("c" "bb" "aaa")
    user> (sort ["aaa" "c" "bb"])
    ("aaa" "bb" "c")

### `concat`

Put sequences together:

    user> (concat [1 2] [3 4 5])
    (1 2 3 4 5)
    user> (concat [1 2] [3 4 5] [6 7])
    (1 2 3 4 5 6 7)
    user> (concat [1 2] [3 4 5] '(6 7))
    (1 2 3 4 5 6 7)

We can do more than two and mix types a bit.

## Lazy Seqs

Many functions, including `map` and `filter` return a _lazy seq_. A lazy seq is
a seq whose members are not computed until we try to access them. Computing the
seq's members is called _realizing_ them.

### Demonstrating Lazy Seq Efficiency

    user> (time (vampire-related-details 0))
    "Elapsed time: 1003.48 msecs"
    {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}

    user> (time (identify-vampire [0 1 2 3]))
    "Elapsed time: 4008.384 msecs"
    {:makes-blood-puns? true, :has-pulse? false, :name "Damon Salvatore"}
    user> (time (identify-vampire [0 1 2 3 4 5 6]))
    "Elapsed time: 7022.378 msecs"
    {:makes-blood-puns? true, :has-pulse? false, :name "Damon Salvatore"}

Hmmm...

    user> (time (def mapped-details (map vampire-related-details (range 0 1000000))))
    "Elapsed time: 0.094 msecs"
    #'user/mapped-details
    user> (time (first mapped-details))
    "Elapsed time: 32065.484 msecs"
    {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}

Here, Clojure takes a while to get the first value because it usually _chunks_
lazy seqs (in this case, it got 32 entries).

But, lazy seq elements only need to be realized once.

    user> (time (first mapped-details))
    "Elapsed time: 0.05 msecs"
    {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}

So, we can search for the vampire...

    user> (time (identify-vampire (range 0 1000000)))
    "Elapsed time: 32087.003 msecs"
    {:makes-blood-puns? true, :has-pulse? false, :name "Damon Salvatore"}

Let's make the database more than 32 elements (use 36):

    user> (time (identify-vampire (range 0 1000000)))
    "Elapsed time: 32082.029 msecs"
    {:makes-blood-puns? true, :has-pulse? false, :name "Damon Salvatore"}

### Infinite Sequences

Clojure comes with a few functions to create infinite sequences. One way is to
use `repeat`:

    user> (concat (take 8 (repeat "na")) '("Batman!"))
    ("na" "na" "na" "na" "na" "na" "na" "na" "Batman!")
    user> (str (concat (take 8 (repeat "na ")) '("Batman!")))
    "clojure.lang.LazySeq@34e2521b"
    user> (println (str (concat (take 8 (repeat "na ")) '("Batman!"))))
    clojure.lang.LazySeq@34e2521b
    nil

The right way is to use `reduce`...

    user> (reduce str (concat (take 8 (repeat "na ")) '("Batman!")))
    "na na na na na na na na Batman!"

We can also use `repeatedly`, which will call a provided function to generate
an element of the sequence:

    user> (take 3 (repeatedly (fn [] (rand-int 10))))
    (3 4 2)
    user> (take 10 (repeatedly (fn [] (rand-int 10))))
    (4 4 3 8 3 3 0 3 4 5)

We can even use `lazy-seq` to create our own infinite sequences.

    user> (take 10 (even-numbers))
    (0 2 4 6 8 10 12 14 16 18)

This work because of the way `cons` works with recursion.

    user> (cons 0 '(2 4 6))
    (0 2 4 6)

Lispers call using `cons` "consing".

## The Collection Abstraction

The collection abstraction is related to the sequence abstration - all of Clojure's
core data structures (vectors, maps, lists, and sets) take part in both
abstractions.

The sequence abstraction is about operating on members individually, while the
collection abstraction is about the data structure as a whole. For example,
`empty?` and `every?` are not about elements, they're about the whole:

    user> (empty? [])
    true
    user> (empty? ["no!"])
    false
    user> (every? #(> (count %) 0) ['() '(1) '(1 2)])
    false
    user> (every? #(>= (count %) 0) ['() '(1) '(1 2)])
    true
    user> (every? empty? ['() '(1) '(1 2)])
    false
    user> (every? empty? ['() '() '()])
    true

### `into`

We use `into` to convert the output of seq's back into their original data
structures.

    user> (map identity {:sunlight-reaction "Glitter!"})
    ([:sunlight-reaction "Glitter!"])
    user> (into {} (map identity {:sunlight-reaction "Glitter!"}))
    {:sunlight-reaction "Glitter!"}

And...

    user> (map identity [:a :b :c])
    (:a :b :c)
    user> (into [] (map identity [:a :b :c]))
    [:a :b :c]

We can also convert:

    user> (map identity [:garlic-clove :garlic-clove])
    (:garlic-clove :garlic-clove)
    user> (into #{} (map identity [:garlic-clove :garlic-clove]))
    #{:garlic-clove}

The first argument of `into` doesn't have to be empty:

    user> (into {:favorit-emotion "gloomy"} [[:sunlight-reaction "glitter!"]])
    {:favorit-emotion "gloomy", :sunlight-reaction "glitter!"}
    user> (into ["cherry"] '("pine" "spruce"))
    ["cherry" "pine" "spruce"]

Of course, both arguments can be the same type:

user> (into {:a 1} {:b 2 :c 3})
{:a 1, :b 2, :c 3}

`into` is good at taking two collections and adding all the elements from the
second collection into the first.

### `conj`

`conj` also adds elements to a collection, but be careful:

    user> (conj [0] [1])
    [0 [1]]
    user> (conj [0] 1)
    [0 1]
    user> (flatten (conj [0] [1]))
    (0 1)
    user> (into [] (flatten (conj [0] [1])))
    [0 1]

Compare with `into`:

    user> (into [0] [1])
    [0 1]

`conj` can take multiple elements:

    user> (conj [0] 1 2 3 4)
    [0 1 2 3 4]

We can absorb different data structures:

    user> (conj {:time "midnight"} [:place "the graveyard"])
    {:time "midnight", :place "the graveyard"}

`conj` and `into` are so similar that we can define `conj` in terms of `into`:

    user> (my-conj [0 1] 2 3)
    [0 1 2 3]

This basically works because the `tail` in a destructured set of arguments
automatically goes into a seq:

    user> (defn my-tail-test
            [head & tail]
            tail)
    #'user/my-tail-test
    user> (my-tail-test [0] 1 2)
    (1 2)

## Function Functions

`apply` and `partial` both accept _and_ return functions.

### `apply`

`apply` _explodes_ a seqable data structure so it may be passed to a function that
expects a rest parameter. For example:

    user> (max 0 1 2)
    2
    user> (max [0 1 2])
    [0 1 2]
    user> (apply max [0 1 2])
    2

This also allows us to define `into` in terms of `conj`.

    user> (my-into [0] [1 2 3])
    [0 1 2 3]
    user> (apply conj [0] [1 2 3])
    [0 1 2 3]

### `partial`

`partial` takes a function and any number of arguments and returns a new function.

    user> (def add10 (partial + 10))
    #'user/add10
    user> (add10 3)
    13

And

    user> (add-missing-elements "unobtainium" "adamantium")
    ["water" "earth" "air" "unobtainium" "adamantium"]

In general, we want to use partials when we find we're repeating the same
combination of function and arguments in many different contexts.

### `complement`

It is so common to want the _complement_ (negation) of a Boolean function, that
there's a function, `complement` for just that.

    (def not-vampire? (complement vampire?))

    user> (neg? -1)
    true
    user> (def my-pos? (complement neg?))
    #'user/my-pos?
    user> (my-pos? 5)
    true
    user> (my-pos? -5)
    false

## A Vampire Data Analysis Program for the FWPD

We want to parse a CSV "database" and analyze it for potential vampires.

    braveTrueCha04$ lein new app fwpd
    Generating a project called fwpd based on the 'app' template.
    braveTrueCha04$ cd fwpd/
    fwpd$ vim suspects.csv
    fwpd$ more suspects.csv
    Edward Cullen,10
    Bella Swan,0
    Charlie Swan,0
    Jacob Black,3
    Carlisle Cullen,6

`M-x cider-restard` crashed for me... possibly too far out on the bleeding edge.

Go to the `fwpd` directory and open the log markdown file and `src/fwpd/core.clj`
file and start a repl. Get a new prompt...

    fwpd.core>

Now start editing `core.clj`...

    fwpd.core> (slurp filename)
    "Edward Cullen,10\nBella Swan,0\nCharlie Swan,0\nJacob Black,3\nCarlisle Cullen,6\n"

The `split` syntax looks a bit funny, but `#<blah>` is a regular expression, not
a function.

    fwpd.core> (clojure.string/split "He said, she said" #", ")
    ["He said" "she said"]

    fwpd.core> (parse (slurp filename))
    (["Edward Cullen,10"] ["Bella Swan,0"] ["Charlie Swan,0"] ["Jacob Black,3"] ["Carlisle Cullen,6"])

The hard function to understand is `mapify`... Recall:

    fwpd.core> (assoc {:a 1} :b 2)
    {:a 1, :b 2}
    fwpd.core> (assoc {:a 1} :b 2 :c 3)
    {:a 1, :b 2, :c 3}

We also have:

    fwpd.core> vamp-keys
    [:name :glitter-index]
    fwpd.core> (map vector vamp-keys ["Name" "10"])
    ([:name "Name"] [:glitter-index "10"])

`map`ping `vector` over seqs basically interweaves them:

    fwpd.core> (map vector ["a" "b" "c"] [1 2 3] ["x" "y" "z"])
    (["a" 1 "x"] ["b" 2 "y"] ["c" 3 "z"])

If we `map` `#(map vector vamp-keys %)` over all the rows, we almost get what we
want, except the numbers are all still strings.

    fwpd.core> (map #(map vector vamp-keys %) (parse (slurp filename)))
    (([:name "Edward Cullen"] [:glitter-index "10"]) ([:name "Bella Swan"] [:glitter-index "0"]) ([:name "Charlie Swan"] [:glitter-index "0"]) ([:name "Jacob Black"] [:glitter-index "3"]) ([:name "Carlisle Cullen"] [:glitter-index "6"]))

The last part maps the string->int conversion over the glitter indices and then
recollects it all into a `map`.
