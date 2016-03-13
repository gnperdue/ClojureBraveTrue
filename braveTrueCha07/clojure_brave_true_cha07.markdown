# Clojure Alchemy: Reading, Evaluation, and Macros

Macros allow us to transform arbitrary expressions into Clojure code and therefore
extend the language to suit our needs.

Consider:

    user> (backwards (" backwards" " am" "I" str))
    "I am backwards"

But, of course,

    user> (" backwards" " am" "I" str)
    ClassCastException java.lang.String cannot be cast to clojure.lang.IFn

Clojure's evaluation model is built around the _reader_, the _evaluator_, and
the _macro expander_.

## An Overview of Clojure's Evaluation Model

Clojure has a two-phase system where it reads textual source code producing
Clojure data structures, then those structures are evaluated. Languages with
this relationship between source code, data, and evaluation are called
_homoiconic_. Homoiconic languages allow you to reason about your code as a
set of data structures that you can manipulate programatically. Another way
to think about the evaluation model is that we are programming natively inside
the abstract syntax tree that is the data structure that holds the code.

    user> (def addition-list (list + 1 2))
    #'user/addition-list
    user> addition-list
    (#function[clojure.core/+] 1 2)
    user> (eval addition-list)
    3

We may use the full power of Clojure and all the code we've written to construct
data structures for evaluation:

    user> (eval (concat addition-list [10]))
    13
    user> (eval (list 'def 'lucky-number (concat addition-list [10])))
    #'user/lucky-number
    user> lucky-number
    13

Our program may "speak" directly with its own evaluator.

## The Reader

The reader converts the text source code in a file or the REPL into Clojure data
structures.

### Reading

    user> (str "If you don't understand recursion, "
               "go to the beginning of this sentence.")
    "If you don't understand recursion, go to the beginning of this sentence."

Textual representation of code is called the _reader form_. Once we hit `<Enter>`,
it goes to the reader.

Reading and evaluation are distinct processes that may be performed independently.

    user> (read-string "(+ 1 2)")
    (+ 1 2)
    user> (list? (read-string "(+ 1 2)"))
    true
    user> (conj (read-string "(+ 1 2)") :zagglewag)
    (:zagglewag + 1 2)

We may read without evaluation, and pass the results to other functions.

    user> (eval (read-string "(+ 1 2)"))
    3

In all these examples, there's been a 1-to-1 relationship between the reader
form and the corresponding data structures.

* `()` a list reader form
* `str` a symbol reader form
* `[1 2]` a vector reader form
* `{:sound "hoot"}` a map reader form

But, they may be more complex...

    user> (#(+ 1 %) 3)
    4
    user> (read-string "#(+ 1 %)")
    (fn* [p1__9596#] (+ 1 p1__9596#))

This is not the 1-to-1 mapping we're used to.

### Reader Macros

The reader used a _reader macro_ to transform `#(+ 1 %)`. Reader macros are sets
of rules for transforming text into data structures. They're designated by the
_macro characters_, e.g. `'`, `#`, and `@`. They're also different from the macros
we'll deal with later.

    user> (read-string "'(a b c)")
    (quote (a b c))

The `deref` macro is similar for the `@` character:

    user> (read-string "@var")
    (clojure.core/deref var)

Reader macros can also do things like cause text to be ignored:

    user> (read-string "; ignore!\n(+ 1 2)")
    (+ 1 2)

## The Evaluator

Clojure's evaluator can be thought of as a function that takes a data structure,
processes it using rules corresponding to its type, and returns a result.

* symbols: look up what they refer to
* list: look at the first element and call a function, macro, or special form
* others: evaluate to themselves

### These Things Evaluate to Themselves

Whenever Clojure evaluates data structures that are not lists or symbols, they
evaluate to themselves (includes empty lists):

    user> true
    true
    user> {}
    {}
    user> :huzzah
    :huzzah
    user> 1
    1
    user> ()
    ()
    user> '()
    ()

### Symbols

Clojure uses _symbols_ to name functions, macros, data, etc. and evaluates them
by _resolving them_ - Clojure traverses any create bindings and looks up the
symbol's entry in a namespace mapping. Ultimately, symbols evaluate to _values_
or _special forms_.

1. check if it is a special form
2. check if it corresponds to a local binding
3. try to find it in namespace mapping
4. if all else fails, throw an exception

        user> (if true :a :b)
        :a
        user> if
        CompilerException java.lang.RuntimeException: Unable to resolve symbol: if

A local binding is any association between a symbol and value that was not created
with `def`, so, e.g., `let`:

    user> (let [x 5]
            (+ x 3))
    8

The local binding takes precedence:

    user> (def x 15)
    #'user/x
    user> (+ x 3)
    18
    user> (let [x 5]
            (+ x 3))
    8

If we nest bindings, the most recent takes precedence. Functions also create local
bindings.

    user> (defn exclaim
            [exclamation]
            (str exclamation "!"))
    #'user/exclaim
    user> (exclaim "Hadoken")
    "Hadoken!"

Note that function symbols refer to functions, but they should not be confused
with the functions themselves. They are data structures we may interact with
separately:

    user> (read-string "+")
    +
    user> (type (read-string "+"))
    clojure.lang.Symbol
    user> (list (read-string "+") 1 2)
    (+ 1 2)
    user> (eval (list (read-string "+") 1 2))
    3

Here "`+`" is being interacted with more as a data structure than as a function,
until we `evaluate` it.

On their own, symbols and their referents don't do anything - Clojure performs
woek by evaluating lists.

### Lists

If a data structure is an empty list, it evaluates to an empty list:

    user> (eval (read-string "()"))
    ()

Otherwise it is evaluated as a _call_ to the first element in the list.

#### Function Calls

We may nest function calls.

#### Special Forms

Special forms are "special" because they implement behavior we can't implement with
functions. Consider:

    user> (if true 1 2)
    1
    user> (if true 1 (/ 1 0))
    1
    user> (/ 1 0)
    ArithmeticException Divide by zero

Special forms don't follow the same evaluation rules as normal functions. When we
call a function, each operand gets evaluated. But with `if` we don't want every
operand evaluated.

    user> (if false (/ 1 0) 1)
    1

The `'` is another important special form.

    user> '(a b c)
    (a b c)

This invokes a macro that produces:

    user> (quote (a b c))
    (a b c)

Normally, Clojure would try to resolve the `a` symbol and call it because it is the
first element in the list. But `quote` instead tells Clojure to just return the
data structure as is.

Other special forms:

* `def`
* `let`
* `loop`
* `fn`
* `do`
* `recur`

These are all not evaluated in the same way as functions. For example, instead of
resolving symbols, `def` and `let` actually create associations between symbols
and values.

We may also place _macros_ at the beginning of a list instead of a function or a
special form.

### Macros

We may use Clojure to manipulate the data structures we use to write Clojure
programs.

Suppose we want to use infix evaluation:

    user> (read-string "(1 + 1)")
    (1 + 1)
    user> (eval (read-string "(1 + 1)"))
    ClassCastException java.lang.Long cannot be cast to clojure.lang.IFn

But, `read-string` returns a list, so we can use Clojure to re-organize:

    user> (let [infix (read-string "(1 + 1)")]
            (list (second infix) (first infix) (last infix)))
    (+ 1 1)
    user> (eval (let [infix (read-string "(1 + 1)")]
                  (list (second infix) (first infix) (last infix))))
    2

This is cool, but awkward. This is where macros come in. They give us a convenient
way to manipulate lists before Clojure evaluates them. What makes macros
interesting is that they are executed in between the reader and the evaluator.
They can manipulate data structures produced by the reader before they are passed
to the evaluator.

    user> (defmacro ignore-last-operand
            [function-call]
            (butlast function-call))
    #'user/ignore-last-operand
    user> (ignore-last-operand (+ 1 2 3 10))
    6
    user> (ignore-last-operand (+ 1 2 3 (println "look at me!")))
    6

The macro receives `(+ 1 2 3 10)` as its argument, not 16. This is different
than a function call - functions always evaluate the arguments passed to them.
When you call a macro, the operands are _not_ evaluated.

Another difference is that a data structure returned by a function is _not_
evaluated, while a data structure returned by a macro _is_. The process of
determining the return value of a macro is called _macro expansion_, and we may
use the function `macroexpand` to see the data structure a macro returns before
it is evaluated.

    user> (macroexpand '(ignore-last-operand (+ 1 2 3 10)))
    (+ 1 2 3)
    user> (macroexpand (ignore-last-operand (+ 1 2 3 (println "look at me!"))))
    6   ;; <- must be quoted!
    user> (macroexpand '(ignore-last-operand (+ 1 2 3 (println "look at me!"))))
    (+ 1 2 3)

For fun:

    user> (defmacro infix
            [infixed]
            (list (second infixed)
                  (first infixed)
                  (last infixed)))
    #'user/infix
    user> (infix (1 + 2))
    3

Essentially, there is a phase between reading and evaluation - that of macro
expansion. This means we may use Clojure to extend itself - macros enable syntactic
abstraction.

### Syntactic Abstraction and the `->` Macro

Clojure often contains nested function calls, e.g.

    user> (defn read-resource
            "read a resource into a string"
            [path]
            (read-string (slurp (clojure.java.io/resource path))))
    #'user/read-resource

We can re-write this with the _threading macro_:

    user> (defn read-sentence
            [path]
            (-> path
                clojure.java.io/resource
                slurp
                read-string))
    #'user/read-sentence

This is like a pipeline that goes from top to bottom instead of from inner to
outer.
