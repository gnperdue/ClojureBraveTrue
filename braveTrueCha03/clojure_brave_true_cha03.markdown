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

#### `when`

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

Use `when` if we want to do multiple things when a condition is true and return
`nil` when the condition is false.

#### `nil`, `true`, `false`, Truthiness, Equality, and Boolean Expressions

Clojure has `true` and `false`. `nil` is used to indicate _no value_. We can check
for nullity with the `nil?` function:

    user> (nil? 1)
    false
    user> (nil? nil)
    true

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

We don't need to worry about type when checking for equality in Clojure.

Clojure also has `or` and `and`:

    user> (or false nil :large_I_mean_venti :why_cant_I_just_say_large)
    :large_I_mean_venti
    user> (or false true :large_I_mean_venti :why_cant_I_just_say_large)
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

We can also look up values in maps by treating the map like a function with the
key value as an argument:

    user> ({:name "The Human Coffeepot"} :name)
    "The Human Coffeepot"

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

We can also use `get` to retrieve by index:

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

Note that `nth` is much slower (O(n)) than `get` (O("a few hops")). We may also
make lists with the `list` function:

    user> (list 1 "two" {3 4})
    (1 "two" {3 4})

We can add elements to a list with `conj`, but they're added to the _beginning_ of
the list.

    user> (conj '(1 2 3) 4)
    (4 1 2 3)

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

We may also create sets from vectors or lists:

    user> (set [1 2 2 3 3 3 4 4 4 4])
    #{1 4 3 2}
    user> (set '(1 2 2 3 3 3 4 4 4 4))
    #{1 4 3 2}

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

### Defining Functions

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

Clojure also supports variable arity via a _rest parameter_, indicated by an `&`.

    user> (codger "Billy" "Bob" "Thornpike")
    ("Get off my lawn, Billy!!!" "Get off my lawn, Bob!!!" "Get off my lawn, Thornpike!!!")

When we provide arguments to variable arity functions, the arguments are treated
like a list. When mixing rest parameters with normal parameters, the rest parameters
must come last.

    user> (favorite-things "Maria" "whiskers" "sleigh-bells")
    "Hi, Maria, here are my favorite things: whiskers, sleigh-bells"

#### Destructing

Destructuring lets us concisely bind names within a collection.

    user> (my-first ["oven" "bike" "war-axe"])
    "oven"

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

We can retain access to the original map argument using the `:as` keyword.

    (defn receive-treasure-location
      [{:keys [lat lng] :as treasure-location}]
      (println (str "Treasure lat: " lat))
      (println (str "Treasure lng: " lng)))

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

    user> (def my-speical-multiplier (fn [x] (* x 3)))
    #'user/my-speical-multiplier
    user> (my-speical-multiplier 12)
    36

There is another, more compact notation: `#( %)`

    user> (#(* % 3) 8)
    24
    user> (#(* 3 %) 8)
    24
    user> (map #(str "Hi, " %) ["Darth" "Magoo"])
    ("Hi, Darth" "Hi, Magoo")

This strange-looking style is made possible by _reader macros_. We can indicate
multiple arguments with `%1`, `%2`, `%3`, etc.

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

### Symmetrizer

### Better Symmetrizer with `reduce`

    user> (reduce + [1 2 3 4])
    10
    user> (reduce + 15 [1 2 3 4])
    25

But,

    user> (reduce + [1 2 3] [4 5])
    ClassCastException clojure.lang.PersistentVector cannot be cast to java.lang.Number  clojure.lang.Numbers.add (Numbers.java:128)

We can give `reduce` an optional initial argument (otherwise the first item in the
sequence is the initial value).

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
