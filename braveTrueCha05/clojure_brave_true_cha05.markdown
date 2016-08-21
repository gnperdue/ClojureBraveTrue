# Functional Programming

## Pure Functions: What and Why

Pure functions:

* always return the same result for the same arguments (referential transparency)
* can't cause side effects

### Pure Functions are Referentially Transparent

    user> (defn wisdom
            [words]
            (str words ", Daniel-san"))
    #'user/wisdom
    user> (wisdom "Always bathe on Fridays")
    "Always bathe on Fridays, Daniel-san"

    user> (analyze-file "testfns.clj")
    "Character count: 257"

### Pure Functions Have No Side Effects

To perform a side effect is to change the association between a name its value
within a given scope.

We can demonstrate in JavaScript:

    braveTrueCha05$ js side-effect.js
    Carefree!
    So emo :'(

Of course useful programs should have some side effects - write to disk, print to
screen, etc. (change the association between a filename and a collection of disk
sectors, change the RGB values of the monitor pixels, etc.).

All of Clojure's core data structures are immutable.

## Living with Immutable Data Structures

### Recursion Instead of for/while

Clojure has no assignment operator. We can't associate a new value with a name
without creating a new scope.

    (def great-baby-name "Rosanthony")
    
    (let [great-baby-name "Bloodthunders"]
      great-baby-name)

We can get around this with recursion.

    user> (my-sum [1 2 3 4 5])
    15

Like all recursive colutions, this function checks its arguments against a base
condition. If we haven't reached the base condition, we keep working through the
cases. Each recursive call creates a new scope where the symbols are bound to
different values.

Note that you should generally use `recur` when doing recursion for performance
reasons.

Immutable data structures in Clojure are implemented using _structural sharing_.
See: http://hypirion.com/musings/understanding-persistent-vector-pt-1

### Function Composition Instead of Attribute Mutation

    user> (clean "My boa constrictor is so sassy lol!   ")
    "My boa constrictor is so sassy LOL!"

Combining functions so the return value of one function is passed as an argument
to another is called _function composition_.

## Cool Things to Do with Pure Functions

We may derive new functions from existing functions in the same way we derive new
data from existing data. `partial` is an example of a function that creates new
functions. Two other useful examples are `comp` and `memoize`.

### `comp`

`comp` creates new functions from the composition of any number of functions, e.g.

    user> ((comp inc *)  2 3)
    7
    user> (inc (* 2 3))
    7

One detail to note here is that the first function applied can take any number of
arguments while the second may only take one. What do we do if the once of the
functions we want to compose needs more than one argument? We wrap it with an
anonymous function.

`comp` may compose any number of functions.

    user> ((comp * *) 3 3 3)
    27
    user> ((comp * * *) 3 3 3 3)
    81

### `memoize`

We may memoize pure functions so Clojure remembers the result of a pure function
call. This works because of referential transparency. All of the following are
equivalent and we may swap one for the other:

    user> (+ 3 (+ 5 8))
    16
    user> (+ 3 13)
    16
    user> 16
    16

Consider:

    user> (sleepy-identify "mr x")
    "mr x"
    user> (time (sleepy-identify "mr x"))
    "Elapsed time: 1001.365 msecs"
    "mr x"
    user> (time (sleepy-identify "mr x"))
    "Elapsed time: 1000.158 msecs"
    "mr x"

And:

    user> (time (memo-sleep-identify "mr y"))
    "Elapsed time: 1000.803 msecs"
    "mr y"
    user> (time (memo-sleep-identify "mr y"))
    "Elapsed time: 0.074 msecs"
    "mr y"
    user> (time (memo-sleep-identify "mr y"))
    "Elapsed time: 0.084 msecs"
    "mr y"

This works because the "memoize"d function remembers what to return for a specific
input.

    user> (time (memo-sleep-identify "mr z"))
    "Elapsed time: 1005.2 msecs"
    "mr z"
    user> (time (memo-sleep-identify "mr a"))
    "Elapsed time: 1000.588 msecs"
    "mr a"
    user> (time (memo-sleep-identify "mr a"))
    "Elapsed time: 0.06 msecs"
    "mr a"
    user> (time (memo-sleep-identify "mr z"))
    "Elapsed time: 0.06 msecs"
    "mr z"
    user> (time (memo-sleep-identify "mr b"))
    "Elapsed time: 1005.09 msecs"
    "mr b"
    user> (time (memo-sleep-identify "mr b"))
    "Elapsed time: 0.073 msecs"
    "mr b"

## Peg Thing

See: https://www.nostarch.com/clojure

### Playing

    braveTrueCha05$ git clone https://github.com/flyingmachine/pegthing.git
    Cloning into 'pegthing'...
    remote: Counting objects: 170, done.
    remote: Total 170 (delta 0), reused 0 (delta 0), pack-reused 170
    Receiving objects: 100% (170/170), 24.06 KiB | 0 bytes/s, done.
    Resolving deltas: 100% (62/62), done.
    Checking connectivity... done.
    braveTrueCha05$ ls
    clbt_additional_exrs05.clj         side-effect.js
    clojure_brave_true_cha05.markdown  testfns.clj
    pegthing/
    braveTrueCha05$ cd pegthing/
    pegthing$ ls
    LICENSE      README.md    doc/         project.clj  src/         test/
    pegthing$ lein run
    Retrieving org/clojure/clojure/1.5.1/clojure-1.5.1.pom from central
    Retrieving org/clojure/clojure/1.5.1/clojure-1.5.1.jar from central
    Get ready to play peg thing!
    How many rows? [5]
    5
    Here's your board:
          a0
         b0 c0
       d0 e0 f0
      g0 h0 i0 j0
    k0 l0 m0 n0 o0
    Remove which peg? [e]
    e
    
    Here's your board:
          a0
         b0 c0
       d0 e- f0
      g0 h0 i0 j0
    k0 l0 m0 n0 o0
    
    Move from where to where? Enter two letters:
    ne
    
    Here's your board:
          a0
         b0 c0
       d0 e0 f0
      g0 h0 i- j0
    k0 l0 m0 n- o0
    Move from where to where? Enter two letters:

etc.

    braveTrueCha05$ lein new app pegjump
    Generating a project called pegjump based on the 'app' template.
    braveTrueCha05$ cd pegjump/
    pegjump$ ls
    CHANGELOG.md  README.md     project.clj   src/
    LICENSE       doc/          resources/    test/
    pegjump$ emacs src/pegjump/core.clj

Then, `M-x cider-jack-in`.

### Code Organization

1. create a new board
2. return a board with the results of a player's move
3. represent the baord textually
4. handle user interaction

The code is organized into a _pure_ layer at the bottom and a user-interaction layer
at the top (where all the side-effecting happens).

### Creating the Board

Here, we will represent the board using a map with numerical keys corresponding to
each position and values containing information about that position's connections.
It will also contain a `:rows` key to store the total number of rows. (We'll use
letters for display, but numbers to track internally.)

For each position, we'll record

    {:pegged true, :connections {6 3, 4 2}}

where `:pegged` tells us if there is a peg at the location and `:connections` tells
us the list of valid jumping over points keyed by the resulting destinations  (so
`6 3` means jump over `3` to get to `6`). We'll use nested recursive functions to
build the board.

    pegjump.core> (take 2 [1 2 3])
    (1 2)
    pegjump.core> (take 2 (tri*))
    (1 3)
    pegjump.core> (take 5 (tri*))
    (1 3 6 10 15)
    pegjump.core> (take 10 (tri*))
    (1 3 6 10 15 21 28 36 45 55)

    pegjump.core> (assoc-in {} [:cookie :monster :vocals] "Finntroll")
    {:cookie {:monster {:vocals "Finntroll"}}}
    pegjump.core> (get-in (assoc-in {} [:cookie :monster :vocals] "Finntroll")
                          [:cookie :monster])
    {:vocals "Finntroll"}
    pegjump.core> (assoc-in {} [1 :connections 4] 2)
    {1 {:connections {4 2}}}

    pegjump.core> (assoc-in {1 {:connections {3 2}}} [1 :connections 4] 2)
    {1 {:connections {3 2, 4 2}}}

Remember, `reduce` works like this:

    pegjump.core> (reduce + 0 [1 2 3])
    6
    pegjump.core> (reduce + 5 [1 2 3])
    11

### Moving Pegs

    pegjump.core> (def my-board (assoc-in (new-board 5) [4 :pegged] false))
    #'pegjump.core/my-board
    pegjump.core> (valid-moves my-board 1)
    {4 2}
    pegjump.core> (valid-moves my-board 2)
    {}
    pegjump.core> (valid-moves my-board 3)
    {}
    pegjump.core> (valid-moves my-board 6)
    {4 5}
    pegjump.core> (map #(valid-moves my-board %) (range 1 16))
    ({4 2} {} {} {} {} {4 5} {} {} {} {} {4 7} {} {4 8} {} {})

    pegjump.core> (valid-move? my-board 8 4)
    nil
    pegjump.core> (valid-move? my-board 1 4)
    2

`if-let` is a way to say "if an expression evaluates to a truthy value, then bind
that value to a name the same way I would in a `let` expression; otherwise, if I've
provided an `else` clause, do that; otherwise return `nil`".

Hmmm... `some` and `empty?` are a bit counter-intuitive...

    pegjump.core> (some empty? '('() '(1 2) '(1 2 3)))
    nil
    pegjump.core> (some empty? [[] [1] [1 2]])
    true

    pegjump.core> (some empty? '([] [1] [1 2]))
    true
    pegjump.core> (empty? '())
    true
    pegjump.core> (map empty? '('() '(1) '(1 2)))
    (false false false)
    pegjump.core> (map empty? [[] [1] [1 2]])
    (true false false)

The answer to this conundrum is the funny way `quote` works:

    pegjump.core> (map empty? '(() (1) (1 2)))
    (true false false)
    pegjump.core> (some empty? '(() (1) (1 2)))
    true

### Rendering and Printing the Board

    pegjump.core> (char 97)
    \a
    pegjump.core> (str (char 97))
    "a"
    pegjump.core> (comp str char 97)
    #function[clojure.core/comp$fn--4495]
    pegjump.core> ((comp str char) 97)
    "a"

    pegjump.core> (map row-num (range 1 5))
    (1 2 2 3)
    pegjump.core> (doseq [row-num (range 1 5)] (println identity))
    #function[clojure.core/identity]
    #function[clojure.core/identity]
    #function[clojure.core/identity]
    #function[clojure.core/identity]
    nil
    pegjump.core> (doseq [row-num (range 1 5)] (println row-num))
    1
    2
    3
    4
    nil
    pegjump.core> (doseq [my-vect (range 1 5)] (println my-vect))
    1
    2
    3
    4
    nil
    pegjump.core> (map println (range 1 5))
    1
    2
    3
    4
    (nil nil nil nil)

    pegjump.core> (take 5 (repeat " "))
    (" " " " " " " " " ")
    pegjump.core> (concat (take 5 (repeat " ")))
    (" " " " " " " " " ")
    pegjump.core> (clojure.string/join (take 5 (repeat " ")))
    "     "
    pegjump.core> (apply str (take 5 (repeat " ")))
    "     "
    pegjump.core> (str (take 5 (repeat " ")))
    "clojure.lang.LazySeq@386d0bf"

### Player Interaction

    pegjump.core> (letter->pos "a")
    1
    pegjump.core> (letter->pos "z")
    26
    pegjump.core> (first "a")
    \a
    pegjump.core> (int (first "a"))
    97
    pegjump.core> (- (int (first "a")) 97)
    0
    pegjump.core> (inc (- (int (first "a")) 97))
    1

Write my own `characters-as-strings` before looking at the book's:

    pegjump.core> (first "a  bc")
    \a
    pegjump.core> (rest "a  bc")
    (\space \space \b \c)
    pegjump.core> (map int "a  bc")
    (97 32 32 98 99)
    pegjump.core> (filter #(and (> % 97) (< % 126)) (map int "a  bc"))
    (98 99)
    pegjump.core> (map char (filter #(and (> % 97) (< % 126)) (map int "a  bc")))
    (\b \c)
    pegjump.core> (map (comp str char) (filter #(and (> % 97) (< % 126)) (map int "a  bc")))
    ("b" "c")
    pegjump.core> (map (comp str char) (filter #(and (>= % 97) (< % 126)) (map int "a  bc")))
    ("a" "b" "c")
    pegjump.core> (map (comp str char) (filter #(and (>= % 97) (<= % 123)) (map int "a  bc x y z")))
    ("a" "b" "c" "x" "y" "z")

The book's answer is, of course, pretty cool and leverages fancy internal functions.

## Summary

Pure functions are referentially transparent and side-effect free, which makes them
awesome.
