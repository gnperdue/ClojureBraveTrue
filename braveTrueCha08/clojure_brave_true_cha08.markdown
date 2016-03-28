# Writing Macros

## Macros Are Essential

`when` is actually a macro, not a special form.

    user> (macroexpand '(when boolean-expression
                          expression-1
                          expression-2
                          expression-3))
    (if boolean-expression (do expression-1 expression-2 expression-3))

## Anatomy of a Macro

Macro definitions look much like function definitions. They have a name, an
optional docstring, an argument list, and body. The body usually returns a
list. We may use any special form, function, or macro within a macro body,
and we call macros just like special forms or functions.

    user> (defmacro infix
            "use this when we pine for the notation of childhood"
            [infixed]
            (list (second infixed) (first infixed) (last infixed)))
    #'user/infix
    user> (infix (1 + 1))
    2
    user> (macroexpand '(infix (1 + 1)))
    (+ 1 1)

We may also use argument destructuring in macro definitions:

    user> (defmacro infix-2
            [[operand1 op operand2]]
            (list op operand1 operand2))
    #'user/infix-2
    user> (infix-2 (1 - 1))
    0
    user> (macroexpand '(infix-2 (1 - 1)))
    (- 1 1)

We may also create multiple-arity macros - `and` and `or` are defined this way:

    user> (defmacro my-and
            ([] true)
            ([x] x)
            ([x & next]
             `(let [and# ~x]
                (if and# (and ~@next) and#))))
    #'user/my-and

There is some unfamiliar notation here, but there are also three macro bodies.

## Building Lists for Evaluation

### Distinguishing Symbols and Values

Suppose we want a macro that takes an expression and both prints and returns
its value (by contrast, `println` returns `nil`):

    (let [result expresion]
      (println result)
      result)

A first version of the macro might look like:

    user> (defmacro my-print-whoopsie
            [expression]
            (list let [result expression]
                  (list println result)
                  result))
    CompilerException java.lang.RuntimeException: Can't take value of a macro

Hmmm... the problem is that the macro body attempts to get the _value_ of the
_symbol_ `let` refers to, but what we actually want to do is return the `let`
symbol itself. There are other problems: we're trying to get the value of
`result`, which is unbound; and we're trying to get the value of `println`
instead of returning its symbol.

Let's try again:

    user> (defmacro my-print
            [expression]
            (list 'let ['result expression]
                  (list 'println 'result)
                  'result))
    #'user/my-print
    user> (my-print "hello")
    hello
    "hello"
    user> (macroexpand '(my-print "hello"))
    (let* [result "hello"] (println result) result)

What is `let*`?

    user> (let [result "hello"]
            (println result)
            result)
    hello
    "hello"
    user> (let* [result "hello"]
            (println result)
            result)
    hello
    "hello"

Here we quote each symbol by prefixing them with `'` - this tells Clojure not
to evaluate whatever follows, so we won't resolve the symbols instead of justr
returning them.

### Simple Quoting

We use quoting in macros to obtain unevaluated symbols.

    user> (+ 1 2)
    3
    user> (quote (+ 1 2))
    (+ 1 2)
    user> '(+ 1 2)
    (+ 1 2)
    user> +
    #function[clojure.core/+]
    user> (quote +)
    +

Evaluating an unbound symbol raises an exception:

    user> sweating-to-the-oldies
    CompilerException java.lang.RuntimeException: Unable to resolve symbol...
    user> 'sweating-to-the-oldies
    sweating-to-the-oldies

The single quote character `'` is a reader macro for `(quote x)`. Note that
the quotes are removed when we macro-expand:

    user> (macroexpand '(when (the-cows-come :home)
                          (call me :pappy)
                          (slap me :silly)))
    (if (the-cows-come :home) (do (call me :pappy) (slap me :silly)))

### Syntax Quoting

Syntax quoting returns unevaluated data structures, similar to normal quoting.
However, there are two differences - syntax quoting returns _fully qualified_
symbols (i.e., with namespace included), and syntax quoting allows us to
_unquote_ forms using a `~`.

    user> '+
    +
    user> 'clojure.core/+
    clojure.core/+
    user> `+
    clojure.core/+
    user> '(+ 1 2)
    (+ 1 2)
    user> `(+ 1 2)
    (clojure.core/+ 1 2)

The reason syntax quotes include the namespace is to help us avoid collisions.
Unquoting "cancels" the macro's ability to return unevaluated forms:

    user> `(+ 1 ~(inc 1))
    (clojure.core/+ 1 2)

This allows us to write more concise code:

    user> (list '+ 1 (inc 1))
    (+ 1 2)
    user> `(+ 1 ~(inc 1))
    (clojure.core/+ 1 2)

## Using Syntax Quoting in a Macro

Here is a "naive" version of the `code-critic` macro:

    (defmacro code-critic
      "phrases are courtesy Hermes Conrad from Futurama"
      [bad good]
      (list 'do
            (list 'println
                  "Great squid of Madrid, this is bad code:"
                  (list 'quote bad))
            (list 'println
                  "Swwet gorilla of Manila, this is good code:"
                  (list 'quote good))))


    user> (code-critic (1 + 1) (1 + 1))
    Great squid of Madrid, this is bad code: (1 + 1)
    Swwet gorilla of Manila, this is good code: (1 + 1)
    nil

But, using syntax quoting, we can make this neater:

    (defmacro code-critic
      "phrases are courtesy Hermes Conrad from Futurama"
      [bad good]
      `(do (println "Great squid of Madrid, this is bad code:"
                    (quote ~bad))
           (println "Sweet gorilla of Manila, this is good code:"
                    (quote ~good))))
    
    user> (code-critic (1 + 1) (1 + 1))
    Great squid of Madrid, this is bad code: (1 + 1)
    Sweet gorilla of Manila, this is good code: (1 + 1)
    nil

Here we quote everything but the symbols `good` and `bad`. Rather than quoting
each piece individually and explicitly placing a list just to prevent the two
symbols from being quoted, with synatx quoting, we can just wrap everything in
a `do` and unquote only the symbols we wish to evaluate.

## Refactoring a Macro and Unquote Splicing

Our `code-critic` could still use some work - there is too much duplication.

    (defn criticize-code
      [criticism code]
      `(println ~criticism (quote ~code)))
    
    (defmacro code-critic
      [bad good]
      `(do ~(criticize-code "Cursed bacteria of Liberia, this is bad code: " bad)
           ~(criticize-code "Sweet sacred boa of Samoa, this is good code:" good)))
    
    user> (code-critic (1 + 1) (+ 1 1))
    Cursed bacteria of Liberia, this is bad code:  (1 + 1)
    Sweet sacred boa of Samoa, this is good code: (+ 1 1)
    nil

Note how the `criticize-code` function returns a syntax-quoted list - this is
how we build up the list our macro will return.

We may do even better though - rather than repeating function calls, we can use
a seq function like `map`:

    (defn criticize-code
      [criticism code]
      `(println ~criticism (quote ~code)))
    
    (defmacro code-critic
      [bad good]
      `(do ~(map #(apply criticize-code %)
                 [["Great squid of Madrid, this is bad code:" bad]
                  ["Sweet gorilla of Manila, this is good code:" good]])))
    
    user> (code-critic (1 + 1) (+ 1 1))
    Great squid of Madrid, this is bad code: (1 + 1)
    Sweet gorilla of Manila, this is good code: (+ 1 1)
    NullPointerException   user/eval9677 (form-init4345144152208272108.clj:1)

Why does this generate an exception?

The problem is that `map` returns a list, and in this case it returned a list
of `println` expressions. We just want the result of each `println` call, but
this code sticks both results in a list and then tries to evaluate that list.

Something called "unquote splicing" was invented to handle this situation.

    user> `(+ ~(list 1 2 3))
    (clojure.core/+ (1 2 3))
    user> `(+ ~@(list 1 2 3))
    (clojure.core/+ 1 2 3)

Unquote splicing unwraps a seq-able data structure, placing its contents within
the enclosing syntax-quoted data structure.

    (defn criticize-code
      [criticism code]
      `(println ~criticism (quote ~code)))
    
    (defmacro code-critic
      [bad good]
      `(do ~@(map #(apply criticize-code %)
                  [["Great squid of Madrid, this is bad code:" bad]
                   ["Sweet gorilla of Manila, this is good code:" good]])))
    
    user> (code-critic (1 + 1) (+ 1 1))
    Great squid of Madrid, this is bad code: (1 + 1)
    Sweet gorilla of Manila, this is good code: (+ 1 1)
    nil

## Things to Watch Out For

### Variable Capture

_Variable capture_ is when a macro introduces a binding that, unknown to the
macros' user, eclipses an existing binding.

    (def message "Good job!")
    
    (defmacro with-mischief
      [& stuff-to-do]
      (concat (list 'let ['message "Oh, big deal!"])
              stuff-to-do))
    
    user> (with-mischief
            (println "here's how i feel about that thing you did: " message))
    here's how i feel about that thing you did:  Oh, big deal!
    nil

Note that this macro did not use syntax quoting - this would have resulted in an
exception. This is intentional - syntax quoting is designed to prevent us from
accidentally capturing variables within macros.

If we want to use `let` bindings in our macro, we should use a _gensym_:

    user> (gensym)
    G__9667
    user> (gensym)
    G__9670
    user> (gensym)
    G__9673

`gensym` produces _unique_ symbols on each successive function call. We may also
pass a symbol prefix:

    user> (gensym 'message)
    message9676
    user> (gensym 'message)
    message9679
    user> (gensym 'message)
    message9682

Loading the code from `var-cap-examp-fixed.clj`:

    user> (without-mischief
           (println "Here's how I feel about that thing you did: " message))
    Here's how I feel about that thing you did:  Good job!
    I still need to say:  Oh, big deal!
    nil

Because this is such a common pattern, we may use an _auto-gensym_:

    user> `(blarg# blarg#)
    (blarg__9706__auto__ blarg__9706__auto__)
    user> `(let [name# "Larry Potter"] name#)
    (clojure.core/let [name__9710__auto__ "Larry Potter"] name__9710__auto__)

We create the auto-gensym by appending a hash mark. Clojure ensures that each
instance of `x#` resolves to the same symbol within the same syntax quoted
list, and that each instance of `y#` resolves similarly, etc.

### Double Evaluation

Double evaluation occurs when a form passed to a macro as an argument gets
evaluated more than once.

Evaluating `double_eval_examp1.clj`:

    user> (report (do (Thread/sleep 1000) (+ 1 1)))
    (do (Thread/sleep 1000) (+ 1 1)) was successful: 2
    nil


