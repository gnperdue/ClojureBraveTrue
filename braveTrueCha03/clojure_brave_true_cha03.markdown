# Do Things: A Clojure Crash Course

## Syntax

### Forms

Clojure recognizes two kinds of structures:

* Literal representations of data structures
* Operations

Clojure _evaluates_ every form to produce a value.

    user> (+ 1 2 3)
    6
    user> (str "It was the panda " "in the library " "with a dust buster")
    "It was the panda in the library with a dust buster"

### Control Flow

#### `if`

    (if boolean-form
        then-form
        optional-else-form)

e.g.

    user> (if true
            "By Zeus's Hammer!"
            "By Aquaman's Trident!")
    "By Zeus's Hammer!"

Note: use `C-j` to move to the next line and indent (you can't get rid of trailing
parens to go to the next line with my new Emacs config).

    user> (if false
            "By Zeus's Hammer!"
            "By Aquaman's Trident!")
    "By Aquaman's Trident!"

    user> (if false
            "By Odin's elbow!")
    nil

And,

    user> (if (println "success")
            "true"
            "false")
    success
    "false"
    user> (println "success")
    success
    nil

    user> (if (println "success")
            "printing success is truthy"
            "printing success is falsey")
    success
    "printing success is falsey"

#### `do`

`do` lets you wrap up multiple forms in parentheses (it is `progn`?) and run
each of them:

    user> (if true
            (do (println "Success!")
                "By Zeus's hammer!")
            (do (println "Failure")
                "By Aquaman's trident!"))
    Success!
    "By Zeus's hammer!"

    user> (if false
            (do (println "Success!")
                "By Zeus's hammer!")
            (do (println "Failure")
                "By Aquaman's trident!"))
    Failure
    "By Aquaman's trident!"

    user> (do (println "yo")
              (println "dawg")
              (println "i heard you like parens"))
    yo
    dawg
    i heard you like parens
    nil

    user> (= nil (println "chewbacca"))
    chewbacca
    true

#### `when`

`when` is like a combination of `if` and `do`.

    user> (when true
            (println "Success!")
            "abra cadabra")
    Success!
    "abra cadabra"
    user> (when false
            (println "Success!")
            "abra cadabra")
    nil

    user> (when true
            (println "Success!")
            (println "abra cadabra!")
            "lol wut")
    Success!
    abra cadabra!
    "lol wut"

Clojure automatically `progn`s?...

    user> (when true
            (println "yo")
            (println "dawg")
            (println "i heard you like parens"))
    yo
    dawg
    i heard you like parens
    nil
    user> (when false
            (println "yo")
            (println "dawg")
            (println "i heard you like parens"))
    nil
    user> (when
            (println "yo")
            (println "dawg")
            (println "i heard you like parens"))
    yo
    nil

    user> (if true
            (println "yo")
            (println "dawg")
            (println "you fast"))
    CompilerException java.lang.RuntimeException: Too many arguments to if, ...
    user> (when true
            (println "yo")
            (println "dawg")
            (println "you fast"))
    yo
    dawg
    you fast
    nil

Use `when` if we want to do multiple things when a condition is true and return
`nil` when the condition is false.

#### `nil`, `true`, `false`, Truthiness, Equality, and Boolean Expressions

Clojure has `true` and `false`. `nil` is used to indicate _no value_. We can check
for nullity with the `nil?` function:

    user> (nil? 1)
    false
    user> (nil? nil)
    true
    user> (nil? false)
    false
    user> (true? nil)
    false
    user> (false? nil)
    false
    user> (nil? (println "success"))
    success
    true
    user> (= false (println "chewbacca"))
    chewbacca
    false

Both `nil` and `false` are used to represent logical falsity, while all other
values are logically true.

Clojure's equality operator is `=`:

    user> (= 1 1)
    true
    user> (= nil nil)
    true
    user> (= 1 2)
    false
    user> (= nil false)
    false
    user> (= true false)
    false
    user> (= 1.0 1.0)
    true
    user> (= 1 1.0)
    false
    user> (= true (println "success"))
    success
    false
    user> (= nil (println "success"))
    success
    true

We don't need to worry about type when checking for equality in Clojure.

Clojure also has `or` and `and`:

    user> (or false nil :large_I_mean_venti :why_cant_I_just_say_large)
    :large_I_mean_venti
    user> (or false true :large_I_mean_venti :why_cant_I_just_say_large)
    true
    user> (or true false :large :small)
    true
    user> (or nil false :small true)
    :small
    user> (or nil false true :small)
    true
    user> (or (= 0 1) (= "yes" "no"))
    false
    user> (or nil)
    nil

Here, the first truthy value is returned by `or`.

    user> (and :free_wifi :hot_coffee)
    :hot_coffee
    user> (and :feelin_super_cool nil false)
    nil

`and` returns the last truthy value if all the values are truthy.

### Naming Values with `def`

We use `def` to _bind_ a name to a value in Clojure:

    user> (def failed-protagonist-names
            ["Larry Potter", "Doreen the Explorer", "The Incredible Bulk"])
    #'user/failed-protagonist-names
    user> failed-protagonist-names
    ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"]

    user> (defn error-message
            [severity]
            (str "OH NOES! IT'S A DISASTER! WE'RE "
                 (if (= severity :mild)
                   "MILDLY INCONVENIENCED!"
                   "DOOOOOOOOOOOOMED!")))
    #'user/error-message
    user> (error-message :mil)
    "OH NOES! IT'S A DISASTER! WE'RE DOOOOOOOOOOOOMED!"
    user> (error-message :mild)
    "OH NOES! IT'S A DISASTER! WE'RE MILDLY INCONVENIENCED!"

## Data Structures

All of Clojure's data structures are immutable.

### Numbers

    user> 1/5
    1/5
    user> 0.2
    0.2
    user> 1
    1
    user> (= 1/5 0.2)
    false
    user> (= 1/5 2/10)
    true
    user> (= (/ 1 5) 1/5)
    true
    user> (= (/ 2 10) 1/5)
    true
    user> (= (/ 2.0 10.0) 1/5)
    false
    user> (= (/ 2.0 10.0) 0.2)
    true

### Strings

    user> "Lord Voldemort"
    "Lord Voldemort"
    user> "\"He who must not be named\""
    "\"He who must not be named\""
    user> "\"Great cow of Moscow!\" - Hermes Conrad"
    "\"Great cow of Moscow!\" - Hermes Conrad"

Clojure uses `"` to delimit strings and it doesn't have string interpolation. You
must use `str`:

    user> (def name "Chewbacca")
    WARNING: name already refers to: #'clojure.core/name in namespace: user, being replaced by: #'user/name
    #'user/name
    user> name
    "Chewbacca"
    user> #'user/name
    #'user/name
    user> (str "\"Ugglglglg\" - " name)
    "\"Ugglglglg\" - Chewbacca"
    user> (println (str "\"Ugglglglg\" - " name))
    "Ugglglglg" - Chewbacca
    nil

    user> (def string1 "\"He who shall not be named\"")
    #'user/string1
    user> string1
    "\"He who shall not be named\""
    user> (println string1)
    "He who shall not be named"
    nil
    user> (def string2 "Lord Voldemort")
    #'user/string2
    user> (println string2)
    Lord Voldemort
    nil

### Maps

Maps are similar to dictionaries or hashes in other languages. The two kinds of
maps in Clojure are hash maps and sorted maps. The empty map is `{}`.

    (def my-map {:first-name "Charlie"
                 :last-name "McFishwich"})
    
    (def my-fn-map {"string-key" +})
    
    (def my-nested-map {:name {:first "John" :middle "Jacob" :last "Jingle"}})
    
    user> my-map
    {:first-name "Charlie", :last-name "McFishwich"}
    user> my-nested-map
    {:name {:first "John", :middle "Jacob", :last "Jingle"}}

We can also use the `hash-map` function to create a map:

    user> (hash-map :a 1 :b 2)
    {:b 2, :a 1}

We can look up values with `get`:

    user> (get {:a 0 :b 1} :b)
    1
    user> (get {:a 0 :b 1} :c)
    nil
    user> (get {:a 0 :b {:c "ho hum"}} :b)
    {:c "ho hum"}

We can also provide a default if `get` fails:

    user> (get {:a 0 :b 1} :c "unicorns?")
    "unicorns?"
    user> (get {:a 0 :b 1} :c :my-symbol)
    :my-symbol

`get-in` lets us look up values in nested maps:

    user> (get-in {:a 0 :b {:c "ho hum"}} [:b :c])
    "ho hum"
    user> (get-in {:a 0 :b {:c {:d "ho hum"}}} [:b :c])
    {:d "ho hum"}
    user> (get-in {:a 0 :b {:c {:d "ho hum"}}} [:b :c :d])
    "ho hum"

We can also look up values in maps by treating the map like a function with the
key value as an argument:

    user> ({:name "The Human Coffeepot"} :name)
    "The Human Coffeepot"
    user> ({:a 1 :b 2} :c)
    nil
    user> ({:a 1 :b 2} :b)
    2
    user> (:b {:a 0 :b 1})
    1

We can also use keywords as functions to look up their values.

### Keywords

Keywords are primarily used as keys in maps.

    user> :a
    :a
    user> :rumplestiltsken
    :rumplestiltsken
    user> :34
    :34
    user> :_?
    :_?

Keywords may be used as functions that look up the corresponding value in a data
structure.

    user> (:a {:a 1 :b 2 :c 3})
    1
    user> (get {:a 1 :b 2 :c 3} :a)    ;; equivalent
    1
    user> ({:a 1 :b 2} :b)
    2
    user> (:b {:a 1 :b 2})
    2
    user> (:name {:name "tigre" :occupation "bad-dude"})
    "tigre"
    user> ({:name "tigre" :occupation "bad-dude"} :name)
    "tigre"

We may also provide default values:

    user> (:d {:a 1 :b 2 :c 3} "No gnome knows homes like Noah knows")
    "No gnome knows homes like Noah knows"

### Vectors

A vector is a 0-indexed collection.

    user> [3 2 1]
    [3 2 1]
    user> (get [3 2 1] 0)
    3
    user> (get [3 2 1] -1)
    nil
    user> (get [3 2 1] 3)
    nil

We use `get` to retrieve by index:

    user> (get ["a" {:name "Pugsly W."} "c"] 1)
    {:name "Pugsly W."}

Vector elements may be of any type and we may mix types. We can also create
them with the `vector` function:

    user> (vector "creepy" "full" "moon")
    ["creepy" "full" "moon"]
    user> (get (vector "creepy" "full" "moon") 0)
    "creepy"

We may use `conj` to add elements to the _end_ of a vector:

    user> (conj [1 2 3] 4)
    [1 2 3 4]

### Lists

Lists are linear collections of values. We can't use `get` though. We create
literals like so:

    user> '(1 2 3 4)
    (1 2 3 4)

To get an element, use the `nth` function:

    user> (nth '(1 2 3 4) 1)
    2
    user> (nth '(:a :b :c) 1)
    :b
    user> (nth '(0 1 2 3) 0)
    0
    user> (nth '(0 1 2 3) 3)
    3
    user> (nth '(0 1 2 3) 4)
    IndexOutOfBoundsException   clojure.lang.RT.nthFrom (RT.java:885)
    user> (nth '(0 1 2 3) -1)
    IndexOutOfBoundsException   clojure.lang.RT.nthFrom (RT.java:885)

Note that `nth` is much slower (O(n)) than `get` (O("a few hops")). We may also
make lists with the `list` function:

    user> (list 1 "two" {3 4})
    (1 "two" {3 4})

We can add elements to a list with `conj`, but they're added to the _beginning_ of
the list.

    user> (conj '(1 2 3) 4)
    (4 1 2 3)

Also,

    user> (list [0 1 2])
    ([0 1 2])
    user> (apply list [0 1 2])
    (0 1 2)

### Sets

Sets are collections of unique values. Clojure has hash sets and sorted sets. Hash
sets are more common. We can create them like so:

    user> #{"kurt vonnegut" 20 :icicle}
    #{20 :icicle "kurt vonnegut"}
    user> (hash-set 1 1 2 2)
    #{1 2}

If we try to add an element to a set that already exists, nothing will happen:

    user> (conj #{:a :b} :b)
    #{:b :a}
    user> (conj #{:a :b} :c)
    #{:c :b :a}

Interestingly:

    user> #{:a :b :c :d :a}
    IllegalArgumentException Duplicate key: :a  clojure.lang.PersistentHashSet.createWithCheck (PersistentHashSet.java:68)
    user> (hash-set :a :b :c :d :a)
    #{:c :b :d :a}
    user> (conj #{:a :b :c} :a)
    #{:c :b :a}
    user> (conj #{:a :b :c} :d)
    #{:c :b :d :a}

We may also create sets from vectors or lists:

    user> (set [1 2 2 3 3 3 4 4 4 4])
    #{1 4 3 2}
    user> (set '(1 2 2 3 3 3 4 4 4 4))
    #{1 4 3 2}

We can make `sorted-set`s...

    user> (hash-set 1 2 2 3 3 3)
    #{1 3 2}
    user> (sorted-set 1 2 2 3 3 3)
    #{1 2 3}
    user> (sorted-set "a" "b" "b" "c" "c" "c")
    #{"a" "b" "c"}

We can check for membership with the `contains?` function:

    user> (contains? #{:a :b} :a)
    true
    user> (contains? #{:a :b} 3)
    false
    user> (contains? #{nil} nil)
    true

We can use a keyword also:

    user> (:a #{:a :b})
    :a
    user> (:c #{:a :b})
    nil
    user> (3 #{:a :b})
    ClassCastException java.lang.Long cannot be cast to clojure.lang.IFn  user/eval7942 (form-init7620152324657781441.clj:1)
    user> (#{:a :b} :a)
    :a

    user> (nil #{:a :b :c nil})
    CompilerException java.lang.IllegalArgumentException...
    user> (#{:a :b :c nil} nil)
    nil
    user> (#{:a :b :c} :C)
    nil
    user> (#{:a :b :c} :c)
    :c
    user> (:c #{:a :b :c})
    :c

We can also use `get`:

    user> (get #{:a :b} :a)
    :a
    user> (get #{:a :b} :c)
    nil
    user> (get #{:a :b} 3)
    nil

Note that using `get` to check if a set contains `nil` will always return `nil`,
so `contains?` may be a better choice:

    user> (get #{:a :b nil} nil)
    nil
    user> (get #{:a :b} nil)
    nil
    user> (contains? #{:a :b} nil)
    false
    user> (contains? #{:a :b nil} nil)
    true

### Simplicity

Clojure philosophy: it is better to have 100 functions operate on one data
structure than 10 functions operate on 10 data structures.

## Functions

### Calling Functions

All Clojure functions have the same Lispy syntax.

    user> (or + -)
    #object[clojure.core$_PLUS_ 0x25e9df6d "clojure.core$_PLUS_@25e9df6d"]
    user> (and + -)
    #object[clojure.core$_ 0x44bfe13d "clojure.core$_@44bfe13d"]
    user> ((and + -) 1 2 3)
    -4
    user> ((or + -) 1 2 3)
    6
    user> ((or - +) 1 2 3)
    -4

Remember, `and` returns the last truthy value if everything is truthy:

    user> ((and + -) 1 2 3 4)
    -8
    user> (- 1 2 3 4)
    -8
    user> ((and + nil) 1 2)
    NullPointerException   user/eval9788 (form-init4069719896812134405.clj:1)
    user> ((or + nil) 1 2)
    3

If anything is falsey, `and` returns the first falsey value:

    user> (and false true)
    false
    user> (and + true)
    true

Some more functions that return 6:

    user> ((and (= 1 1) +) 1 2 3)
    6
    user> ((first [+ 0]) 1 2 3)
    6

Syntactically, functions can take any expression as an argument, including other
functions. Functions that can take functions as arguments or return functions
are called _higher-order functions_.

    user> (inc 1.1)
    2.1
    user> (map inc [0 1 2 3])
    (1 2 3 4)
    user> (map inc '(1 2 3))
    (2 3 4)
    user> (map inc #{1 2})
    (2 3)

    user> (map inc [1 2])
    (2 3)
    user> (apply inc [1 2])
    ArityException Wrong number of args (2) passed to: core/inc...
    user> (apply list [1 2])
    (1 2)

Note that `map` doesn't return a vector (or set) when handed one. Note also that
Clojure evaluates all function arguments recursively before passing them to
the function.

    user> (+ (inc 199) (/ 100 (- 7 2)))
    220

### Function Calls, Macro Calls, and Special Forms

Macros and special forms are special because they don't always evaluate all of
their operands. For example

    (if good-mood
       (tweet walking-on-sunshine-lyrics)
       (tweet mopey-country-song-lyrics))

We only want Clojure to evaluate one of the two branches, not both. Another
distinguishing feature that differentiates special forms is that you can't use them
as arguments to functions. In general, special forms implement core Clojure
functionality that can't be implemented with functions. Similarly, macros can't
be passed as arguments to functions.

    user> (if true
            (* 1 0)
            (/ 1 0))
    0
    user> (if false
            (/ 1 0)
            (* 1 0))
    0

### Defining Functions

There are five main parts:

1. `defn` (or, later `def` and `fn`, etc.)
2. Function name
3. A docstring
4. Parameters in brackets
5. Function body

e.g.,

    (defn too-enthusiastic
      "Return a cheer that might be a bit too enthusiastic."
      [name]
      (str "OH. MY. GOD!" name " YOU ARE THE BEST!"))

#### The Docstring

The _docstring_ is a useful way to describe and document code. We can view it
in the REPL with `(doc fn-name)`:

    user> (too-enthusiastic "Gabe")
    "OH. MY. GOD!Gabe YOU ARE THE BEST!"
    user> (doc too-enthusiastic)
    -------------------------
    user/too-enthusiastic
    ([name])
      Return a cheer that might be a bit too enthusiastic.
    nil

#### Parameters and Arity

Clojure functions may be defined with zero or more parameters. Functions also
support arity overloading. This means we can define a function so that a different
function body runs depending on the arity.

Note that each arity definition is enclosed in `()` and has an argument list in
`[]`:

    user> (defn blarg
            ([arg1 arg2]
             (str arg1 " and " arg2))
            ([arg]
             (str arg)))
    #'user/blarg
    user> (blarg :a)
    ":a"
    user> (blarg :a :b)
    ":a and :b"

    user> (multi-arity)
    "No args"
    user> (multi-arity "hellow")
    "One arg: hellow"
    user> (multi-arity "hello" "world")
    "Two args: hello world"
    user> (multi-arity "goodbye" "cruel" "world")
    "Three args: goodbye cruel world"

Each arity is enclosed in parentheses and has an argument list. Arity overloading
is one way to provide default arguments to functions.

    user> (x-chop "Bill")
    "I karate chop Bill! Take that!"
    user> (x-chop "Bill" "Tae-kwon-do")
    "I Tae-kwon-do chop Bill! Take that!"

We can, also, of couse make each arity do completely different things.

    user> (defn weird
            ([] "yo dawg")
            ([number]
             (inc number)))
    #'user/weird
    user> (weird)
    "yo dawg"
    user> (weird 1)
    2

Clojure also supports variable arity via a _rest parameter_, indicated by an `&`.

    user> (codger "Billy" "Bob" "Thornpike")
    ("Get off my lawn, Billy!!!" "Get off my lawn, Bob!!!" "Get off my lawn, Thornpike!!!")
    user> (codger :billy)
    ("Get off my lawn, :billy!!!")
    user> (codger :billy :ray)
    ("Get off my lawn, :billy!!!" "Get off my lawn, :ray!!!")
    user> (codger [:billy :ray])
    ("Get off my lawn, [:billy :ray]!!!")

When we provide arguments to variable arity functions, the arguments are treated
like a list. When mixing rest parameters with normal parameters, the rest parameters
must come last.

    user> (favorite-things "Maria" "whiskers" "sleigh-bells")
    "Hi, Maria, here are my favorite things: whiskers, sleigh-bells"

#### Destructuring

Destructuring lets us concisely bind names within a collection.

    user> (my-first ["oven" "bike" "war-axe"])
    "oven"
    user> (my-first :A)
    UnsupportedOperationException nth not supported on this type: Keyword  clojure.lang.RT.nthFrom (RT.java:933)
    user> (my-first '(:a :b :c))
    :a

The code looks like this:

    ;; return the first element of a collection
    (defn my-first
      [[first-thing]]   ;; notice `first-thing` is within a vector
      first-thing)

The vector is a "sign" that the function is going to receive a list or vector as
an argument and it asks Clojure to take the argument's structure apart and
associate meaningful names with different parts of the argument.

    user> (chooser ["Orange" "Yellow" "Green" "Red" "Blue"])
    Your first choice is: Orange
    Your second choice is: Yellow
    We're ignoring your other choices. Here they are in case you need them: Green, Red, Blue
    nil
    user> (chooser ["Orange" "Yellow"])
    Your first choice is: Orange
    Your second choice is: Yellow
    We're ignoring your other choices. Here they are in case you need them: 
    nil
    user> (chooser ["Orange"])
    Your first choice is: Orange
    Your second choice is: 
    We're ignoring your other choices. Here they are in case you need them: 
    nil

We could have also given a list to `chooser`.

    user> (defn my-second-thing
            [[first-thing second-thing & other-things]]
            second-thing)
    #'user/my-second-thing
    user> (my-second-thing '(1 2 3 4 5 6))
    2

We can also destructure maps.

    (defn announce-treasure-location
      [{lat :lat lng :lng}]
      (println (str "Treasure lat: " lat))
      (println (str "Treasure lng: " lng)))

    user> (announce-treasure-location {:lat 28 :lng 17})
    Treasure lat: 28
    Treasure lng: 17
    nil

Note, you can't do this:

    user> (defn announce-treasure-location
            [{:lat lat :lng lng}]
            (println (str "Treasure lat: " lat))
            (println (str "Treasure lng: " lng)))
    
    CompilerException java.lang.RuntimeException: Unable to resolve symbol: lat in this context, compiling:(*cider-repl localhost*:57:6)

We often want to just break out the keywords, and there is a shorter syntax for
that.

    (defn announce-trasure-loc-keys
      [{:keys [lat lng]}]
      (println (str "Treasure lat: " lat))
      (println (str "Treasure lng: " lng)))

    user> (announce-trasure-loc-keys {:lat 28 :lng 18})
    Treasure lat: 28
    Treasure lng: 18
    nil

Note, you can't do this:

    user> (defn announce-trasure-loc-keys
            [{:lat :lng}]
            (println (str "Treasure lat: " lat))
            (println (str "Treasure lng: " lng)))
    
    CompilerException java.lang.RuntimeException: Unable to resolve symbol: lng in this context, compiling:(*cider-repl localhost*:73:18)

Also, note the names you give to give to `:keys` are the names you have to use:

    user> (defn announce-treasure-loc-keys
            [{:keys [lt lg]}]
            (println (str "Treasure lat: " lt))
            (println (str "Treasure lng: " lg)))
    #'user/announce-treasure-loc-keys
    user> (announce-treasure-loc-keys {:lat 56 :lng 50})
    Treasure lat: 
    Treasure lng: 
    nil
    user> (announce-treasure-loc-keys {:lt 56 :lg 50})
    Treasure lat: 56
    Treasure lng: 50
    nil

We can retain access to the original map argument using the `:as` keyword.

    (defn receive-treasure-location
      [{:keys [lat lng] :as treasure-location}]
      (println (str "Treasure lat: " lat))
      (println (str "Treasure lng: " lng))
      (println (str "New location: " treasure-location)))

In action...

    user> (defn receive-treasure-location
            [{:keys [lat lng] :as treasure-location}]
            (println (str "Treasure lat: " lat))
            (println (str "Treasure lng: " lng))
            (println (str "New location: " treasure-location)))
    
    #'user/receive-treasure-location
    user> (receive-treasure-location {:lat 56 :lng 40})
    Treasure lat: 56
    Treasure lng: 40
    New location: {:lat 56, :lng 40}
    nil

#### Function Body

The body can contain forms of any kind and Clojure will automatically return the
last form evaluated.

#### All Functions Are Created Equal

Clojure has no privileged functions.

### Anonymous Functions

Functions don't need to have names - we will use _anonymous functions_ all the
time. There are two ways:

    (fn [param-list]
       function body)

This looks like `defn`.

    user> (map (fn [name] (str "Hi, " name))
               ["Darth Vader" "Mr. Magoo"])
    ("Hi, Darth Vader" "Hi, Mr. Magoo")
    user> ((fn [x] (* x 3)) 8)
    24

We may treat `fn` almost the same way we treat `defn`. Parameter lists and function
bodies work the same way, we may use argument destructuring, rest parameters, and
so on. We may even associate anonymous functions with a name:

    user> ((fn [x] (* x 3)) 5)
    15
    user> (def my-speical-multiplier (fn [x] (* x 3)))
    #'user/my-speical-multiplier
    user> (my-speical-multiplier 12)
    36

Indeed, if we `(doc defn)`, we see it is a macro that wraps this functionality up
for us.

There is another, more compact notation: `#( %)`

    user> (#(* % 3) 8)
    24
    user> (#(* 3 %) 8)
    24
    user> (map #(str "Hi, " %) ["Darth" "Magoo"])
    ("Hi, Darth" "Hi, Magoo")

This strange-looking style is made possible by _reader macros_. We can indicate
multiple arguments with `%1`, `%2`, `%3`, etc. (note - not `%0`):

    user> (#(str %1 " and " %2) "cornbread" "beans")
    "cornbread and beans"

We can include the "rest" with `%&`:

    user> (#(identity %&) 1 "blarg" :yip)
    (1 "blarg" :yip)

### Returning Functions

Functions can return other functions. The returned functions are _closures_, which
means they may access the variables that were in scope when the function was
created.

    (defn inc-maker
      "Create an incrementor"
      [inc-by]
      #(+ % inc-by))
    
    (def inc3 (inc-maker 3))
    
    user> (inc3 7)
    10
    user> (inc3 1)
    4

## Pulling it All Together

Okay, hit hobbits...

### The Shire's Next Top Model

### `let`

`(let ...)` binds names to values. It is short for "let it be...".

    user> (let [:vowels "aeiou"] :vowels)
    Exception Unsupported binding key: :vowels  clojure.core/destructure (core.clj:4298)
    user> (let [vowels "aeiou"] vowels)
    "aeiou"

    user> (let [x 3]
            x)
    3
    user> (def dalmation-list
            ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])
    #'user/dalmation-list
    user> (let [dalmatians (take 2 dalmation-list)]
            dalmatians)
    ("Pongo" "Perdita")

`let` introduces a _new scope_:

    user> (def x 0)
    #'user/x
    user> (let [x 1] x)
    1

We may reference existing bindings in our binding:

    user> x
    0
    user> (let [x (inc x)] x)
    1
    user> x
    0

We may also use "rest" parameters in `let`, just as in functions:

    user> dalmation-list
    ["Pongo" "Perdita" "Puppy 1" "Puppy 2"]
    user> (let [[pongo & dalmatians] dalmation-list]
            [pongo dalmatians])
    ["Pongo" ("Perdita" "Puppy 1" "Puppy 2")]

The value of a `let` form is the last form in its body that is evaluated. `let`
forms follow all the destructuring rules introduced in "calling functions."

`let` forms have two main uses. They provide clarity by allowing us to name things.
They also allow us to evaluate an expression once and use the result multiple
times.

The code

    (into final-body-parts
      (set [part (matching-part part)]))

first creates a set made from `part` and the result of calling `matching-part` on
`part`. We make a set to be sure there are no duplicates.

    user> (into [] (set [:a :a :a]))
    [:a]

We can see `into` moves data into a structure.

    user> (into [0 1 2 4] [3])
    [0 1 2 4 3]
    user> (into (set '(:a :b)) '(:c))
    #{:c :b :a}
    user> (into '(0 1 2 3 4) '(5))
    (5 0 1 2 3 4)

### `loop`

    user> (loop [iteration 0]
            (println (str "Iteration " iteration))
            (if (> iteration 3)
              (println "Goodbye!")
              (recur (inc iteration))))
    Iteration 0
    Iteration 1
    Iteration 2
    Iteration 3
    Iteration 4
    Goodbye!
    nil

Here, `loop [iteration 0]` begins the loop and introduces a binding with an
initial value.

### Regular Expressions

The syntax is roughly `#"regular-expression"`.

    user> (re-find #"^left-" "left-eye")
    "left-"
    user> (re-find #"^left-" "cleft-chin")
    nil
    user> (re-find #"^left-" "wongleft")
    nil
    user> (re-find #"^left-" "leftwong")
    nil
    user> (re-find #"^left-" "left-wongle")
    "left-"

    user> (clojure.string/replace "wonglebart" #"^wongle" "woongle")
    "woonglebart"
    user> (clojure.string/replace "wonglebart" #"^wyngle" "woongle")
    "wonglebart"

Note that if the match-expression in `clojure.string/replace` fails, then the
function just returns the original string we give it.

### Symmetrizer

`into` is useful for building up collections:

    user> (into '() '(1 2 3))
    (3 2 1)
    user> (into [] '(1 2 3))
    [1 2 3]
    user> (into #{} '(1 2 1 3))
    #{1 3 2}
    user> (into #{1 2 3} '(1 2 1 3 4 5 5 6))
    #{1 4 6 3 2 5}
    user> (into #{1 2 3} (set '(1 2 1 3 4 5 5 6)))
    #{1 4 6 3 2 5}

### Better Symmetrizer with `reduce`

The pattern of "process each element in a sequence and build up a result" is so
common it has its own built-in function - `reduce`:

    user> (reduce + [1 2 3 4])
    10
    user> (reduce + 15 [1 2 3 4])
    25

    user> (reduce * [1 2 3 4 5])
    120
    user> (reduce - [1 2 3 4 5])
    -13
    user> (reduce + [1 2 3 4 5])
    15
    user> (reduce / [1 2 3 4 5])
    1/120

But,

    user> (reduce + [1 2 3] [4 5])
    ClassCastException clojure.lang.PersistentVector cannot be cast to java.lang.Number  clojure.lang.Numbers.add (Numbers.java:128)

We can give `reduce` an optional initial argument (otherwise the first item in the
sequence is the initial value).

    user> (reduce / 10 [1 2 3 4 5])
    1/12

### Hobbit Violence

    user> (hit asym-hobbit-body-parts)
    {:name "left-lower-leg", :size 3}
    user> (hit asym-hobbit-body-parts)
    {:name "left-upper-arm", :size 3}
    user> (hit asym-hobbit-body-parts)
    {:name "right-kidney", :size 1}
    user> (hit asym-hobbit-body-parts)
    {:name "left-foot", :size 2}
    user> (hit asym-hobbit-body-parts)
    {:name "chest", :size 10}
    user> (hit asym-hobbit-body-parts)
    {:name "neck", :size 2}

## Summary

* http://clojure.org/cheatsheet
* http://www.projecteuler.net
* http://www.4clojure.com/problems

## Exercises

1. Use `str`, `vector`, `list`, `hash-map`, and `hash-set` functions.

2. Write a function that takes a number and adds 100 to it.

3. Write a function, `dec-maker` that works like `inc-maker`, but with subtraction.

        (def dec9 (dec-maker 9))
        (dec9 10)
        ; => 1

4. Write a function `mapset` that works like `map` except the return value is a
set.

        (mapset inc [1 1 2 2])
        ; => #{2 3}

5. Crete a function like `symmetrize-body-parts` except it must operate on aliens
with radial symmetry. Instead of two arms, etc., they have five.

6. Create a function that generalizes `symmetrize-body-parts` and the function
from exercise 5. The new function should take a collection of body parts and
the number of matching parts to add. [We sort of did this in part 5...]

## Additional exercises

Standalone `.clj` files _plus_, a leiningen project for tests...

    braveTrueCha03$ lein new btexrs03
    Generating a project called btexrs03 based on the 'default' template.
    The default template is intended for library projects, not applications.
    To see other templates (app, plugin, etc), try `lein help new`.
    braveTrueCha03$ cd btexrs03/
    btexrs03$ ls
    CHANGELOG.md  README.md     project.clj   src/
    LICENSE       doc/          resources/    test/
    btexrs03$ mv src/btexrs03/core.clj src/btexrs03/exrs.clj
    btexrs03$ mv test/btexrs03/core_test.clj test/btexrs03/exrs_test.clj

Next, we edit `exrs.clj` and `exrs_test.clj` to reflect the changed file
names in the namespaces - basically swap 'exrs' for 'core' in `ns` statements
at the top of both files (everywhere it appears in them!).

Next, add `lein-test-refresh` to our `project.clj` file. Before:

    btexrs03$ more project.clj
    (defproject btexrs03 "0.1.0-SNAPSHOT"
      :description "FIXME: write description"
      :url "http://example.com/FIXME"
      :license {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}
      :dependencies [[org.clojure/clojure "1.8.0"]])

After:

    btexrs03$ more project.clj
    (defproject btexrs03 "0.1.0-SNAPSHOT"
      :description "FIXME: write description"
      :url "http://example.com/FIXME"
      :license {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}
      :dependencies [[org.clojure/clojure "1.8.0"]]
      :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.7.0"]]}})

Next, run test-refresh in project area:

    btexrs03$ lein test-refresh
    *********************************************
    *************** Running tests ***************
    :reloading (btexrs03.exrs btexrs03.exrs-test)
    
    Testing btexrs03.exrs-test
    
    FAIL in (a-test) (exrs_test.clj:7)
    FIXME, I fail.
    expected: (= 0 1)
      actual: (not (= 0 1))
    
    Ran 1 tests containing 1 assertions.
    1 failures, 0 errors.
    
    Failed 1 of 1 assertions
    Finished at 17:30:54.181 (run time: 0.102s)

Now, begin adding tests and code to `src/btexrs03/exrs.clj` and
`test/btexrs03/exrs_test.clj`.

For regular work

    btexrs03$ emacs src/btexrs03/exrs.clj \
      test/btexrs03/exrs_test.clj \
      ../clojure_brave_true_cha03.markdown \
      ../clbt_additional_exrs_cha03.clj

