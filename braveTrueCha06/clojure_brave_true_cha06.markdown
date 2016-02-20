# Organizing Your Project: A Librarian's Tale

* `def`
* namespaces and how to use them
* namespaces and the filesystem
* `refer`, `alias`, `require`, `use`, `ns`
* organize Clojure projects with the filesystem

## Your Project as a Library

We can think of Clojure as storing objects (e.g., data structures and functions) in
a vast set of numbered shelves. We can't keep track of the address, so instead we
give Clojure an identifier to retrieve the object.

Clojure maintains the associations betwen identifiers and "shelf addresses" using
_namespaces_. Namespaces contain maps between _symbols_ and referecnes to objects,
known as _vars_.

Namespaces are objects of type `clojure.lang.Namespace` and we may interact with
them just like other data structures.

    user> (ns-name *ns*)
    user

Clojure allows us to create as many namespaces as we like.

Symbols are data types that we can use to get objects.

    user> inc
    #function[clojure.core/inc]
    user> map
    #function[clojure.core/map]
    user> (map inc [1 2 3])
    (2 3 4)
    user> '(map inc [1 2 3])
    (map inc [1 2 3])

Note that we can use quotes (`'`) to keep objects from being "evaluated."

## Storing Objects with `def`

    user> (def great-books ["East of Eden" "The Glass Bead Game"])
    #'user/great-books
    user> great-books
    ["East of Eden" "The Glass Bead Game"]

Clojure

1. updates the current namespace's map with the association between `great-books`
and the var
2. finds free storage "on the shelf"
3. stores the strings there
4. writes the address of the "shelf" on the var
5. returns the var, (here `#'user/great-books`)

This process is called _interning_ a var. We may interact with a namespace's map
of symbols-to-interned-vars with `ns-interns`:

    user> (ns-interns *ns*)
    {great-books #'user/great-books}
    
    user> (ns-map *ns*)
    ... large map ...

We can `get` a specific var (`ns`'s are like maps):

    user> (get (ns-interns *ns*) 'great-books)
    #'user/great-books
    user> (get (ns-interns *ns*) 'great-bookers)
    nil

We call `#'user/great-books` the _reader form_ of a var. We use `#'` to get a hold
of the var corresponding to the following symbol.

    user> #'great-books
    #'user/great-books
    user> #'user/great-books
    #'user/great-books

We may also `deref` vars to get the objects they point to:

    user> (deref #'user/great-books)
    ["East of Eden" "The Glass Bead Game"]

Normally we just use the symbol.

_Name collision_ can result in us losing data...

    user> (def great-books ["The Power of Bees" "Journey to Upstairs"])
    #'user/great-books
    user> great-books
    ["The Power of Bees" "Journey to Upstairs"]

Clojure lets us create namespaces so we may avoid these collisions.

## Creating and Switching to Namespaces

* function `create-ns`
* function `in-ns`
* macro `ns`

`create-ns` makes a namespace and returns it if it didn't exist. It does not move
to it.

    user> (create-ns 'cheese.taxonomy)
    #namespace[cheese.taxonomy]
    user> (ns-name (create-ns 'cheese.taxonomy))
    cheese.taxonomy
    user> (ns-name *ns*)
    user

`in-ns` creates a namespace (if it doesn't exist) and moves into it.

    user> (in-ns 'cheese.taxonomy)
    #namespace[cheese.taxonomy]
    cheese.taxonomy> (ns-name *ns*)
    CompilerException java.lang.RuntimeException: Unable to resolve symbol:...

    cheese.taxonomy> (def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])
    #'cheese.taxonomy/cheddars
    cheese.taxonomy> (in-ns 'cheese.analysis)
    #namespace[cheese.analysis]
    cheese.analysis> cheddars
    CompilerException java.lang.RuntimeException: Unable to resolve symbol
    cheese.analysis> #'cheese.taxonomy/cheddars
    #'cheese.taxonomy/cheddars
    cheese.analysis> (in-ns 'cheese.taxonomy)
    #namespace[cheese.taxonomy]
    cheese.taxonomy> cheddars
    ["mild" "medium" "strong" "sharp" "extra sharp"]

And,

    cheese.taxonomy> (in-ns 'cheese.analysis)
    #namespace[cheese.analysis]
    cheese.analysis> cheese.taxonomy/cheddars
    ["mild" "medium" "strong" "sharp" "extra sharp"]

### `refer`

`refer` provides fine-grained control over how we refer to objects in other
namespaces.

    cheese.analysis> (in-ns 'cheese.taxonomy)
    #namespace[cheese.taxonomy]
    cheese.taxonomy> (def bries ["Wisconsin" "Somerset" "Brie de Meaux"
                                 "Brie de Melun"])
    #'cheese.taxonomy/bries

And,

    cheese.taxonomy> (in-ns 'cheese.analysis)
    #namespace[cheese.analysis]
    cheese.analysis> (clojure.core/refer 'cheese.taxonomy)
    nil
    cheese.analysis> bries
    ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]
    cheese.analysis> cheddars
    ["mild" "medium" "strong" "sharp" "extra sharp"]

Calling `refer` with a namespace symbol lets us refer to the corresponding
namespace's objects without having to use the fully-qualified symbols. We can
see new entries like:

    cheese.analysis> (clojure.core/get
                      (clojure.core/ns-map clojure.core/*ns*) 'bries)
    #'cheese.taxonomy/bries
    cheese.analysis> (clojure.core/get
                      (clojure.core/ns-map clojure.core/*ns*) 'cheddars)
    #'cheese.taxonomy/cheddars

It's as if Clojure...

1. calls `ns-interns` on the `cheese.taxonomy` namespace
2. merges that with the `ns-map` of the current namespace
3. makes the result of the new `ns-map` of the current namespace

When we call `refer`, we may also pass it the filters `:only`, `:exclude`, and
`:rename`. `:only` and `:exclude` restrict which symbol/var mappings get merged
into the current namespace's `ns-map` and `:rename` lets us use different symbols
for the vars being merged in.

    cheese.analysis> (clojure.core/refer 'cheese.taxonomy :only ['bries])
    nil
    cheese.analysis> bries
    ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]

Here is `:exclude`:

    cheese.analysis> (clojure.core/refer 'cheese.taxonomy :exclude ['bries])
    nil
    cheese.analysis> cheddars
    ["mild" "medium" "strong" "sharp" "extra sharp"]

Note, if we already made `bries` visible, it appears to stay visible:

    cheese.analysis> bries
    ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]

And lastly, `:rename`:

    cheese.analysis> (clojure.core/refer 'cheese.taxonomy :rename
                                         {'bries 'yummy-bries})
    nil
    cheese.analysis> yummy-bries
    ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]

Note that we must use the fully-qualified names of the objects in `clojure.core`
in these examples. We can bring the core objects in by evaluating

    (clojure.core/refer-clojure)

when we create a new namespace.

    cheese.analysis> (clojure.core/refer-clojure)
    nil
    cheese.analysis> (ns-name *ns*)
    cheese.analysis

We can define functions such that they are only available to other functions
within the namespace - also known as _private_ functions.

    cheese.analysis> (defn- private-function
                       "just an example that does nothing"
                       [])
    #'cheese.analysis/private-function

If we call this funciton from another namespace or refer to it, we will get an
exception.

    cheese.analysis> (private-function)
    nil
    cheese.analysis> (in-ns 'cheese.taxonomy)
    #namespace[cheese.taxonomy]
    cheese.taxonomy> (private-function)
    CompilerException java.lang.RuntimeException: Unable to resolve symbol...
    cheese.taxonomy> (cheese.analysis/private-function)
    CompilerException java.lang.IllegalStateException: var: #'cheese.analysis/private-function is not public, ...

Even if we explicitly refer to the function, we can't use it in a different
namespace.

### `alias`

All `alias` does is let us shorten a namespace for using fully-qualified symbols.

    cheese.analysis> (clojure.core/alias 'taxonomy 'cheese.taxonomy)
    nil
    cheese.analysis> taxonomy/bries
    ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]

## Real Project Organization

### The Relationship Between File Paths and Namespace Names

    braveTrueCha06$ lein new app the-divine-cheese-code
    Generating a project called the-divine-cheese-code based on the 'app' template.

`ns` is the primary way to create and manage namespaces within Clojure. In Clojure,
there is a 1-to-1 mapping between a namespace name and the path of the file where
the namespace is declared, with some conventions:

* when creating a directory with `lein`, the source code's root is `src` by default
* dashes in namespace names correpsond to underscrores in the file-system
* the component preceeding a period (`.`) in a namespace corresponds to a directory
* the final component of a namespace corresponds to a file with `.clj`, so `core`
is mapped to `core.clj`

The project gets one more namespace

    the-divine-cheese-code$ mkdir src/the_divine_cheese_code/visualization
    the-divine-cheese-code$ touch src/the_divine_cheese_code/visualization/svg.clj

### Requiring and Using Namespaces

`require` takes a symbol designating a namespace and ensures that the namespace
exists and is ready to be used. This means Clojure will evaluate the
corresponding file - note that it won't evaluate the file automatically just
because it is in a project. We have to tell Clojure to evaluate it.

After `require`ing, we may `refer` to a namespace so we don't need to use fully
qualified names to reference the functions.

    the-divine-cheese-code$ lein run
    6.97,50.95 8.55,47.37 5.37,43.3 8.55,47.37 12.45,41.9

Also, we can start emacs in `...ue/braveTrueCha06/the-divine-cheese-code/` and
then `M-x cider-jack-in` while viewing the `core.clj` file to load the right
namespace and:

    the-divine-cheese-code.core> (-main)
    6.97,50.95 8.55,47.37 5.37,43.3 8.55,47.37 12.45,41.9
    nil
    the-divine-cheese-code.core>

If you squint at `require`, it does the following:

1. do nothing until we call `require` with the symbol
2. otherwise, find the file according to usual conventions, then evaluate the
contents.

`require` also lets us alias namespaces. So

    (require '[the-divine-cheese-code.visualization.svg :as svg])

is equivalent to

    (require 'the-divine-cheese-code.visualization.svg)
    (alias 'svg 'the-divine-cheese-code.visualization.svg)

Then, we can use the aliased namespace:

    (svg/points heists)

`use` lets us both `require` and `refer` in one step. So

    (require 'the-divine-cheese-code.visualization.svg)
    (refer 'the-divine-cheese-code.visualization.svg)

is equivalent to

    (use 'the-divine-cheese-code.visualization.svg)

We may also alias in `use`:

    (use '[the-divine-cheese-code.visualization.svg :as svg])

In general, `use` takes the same options as `refer`: `:only`, `:exclude`, `:as`,
and `:rename`. So aliasing a namespace after we `use` it lets us refer to symbols
we've excluded, etc.

### The `ns` Macro

Generally we use the tools we've covered so far in the REPL but `ns` in source
code. By default, `ns` `refer`s `clojure.core`. We can control what gets `refer`ed
with `:refer-clojure`:

    (ns the-divine-cheese.core
      (:refer-clojure :exclude [println]))

Here, `:refer-clojure` is a _reference_. There are six possible references within
`ns`:

* `:refer-clojure`
* `:require`
* `:use`
* `:import`
* `:load`
* `:gen-class`

`:require` works a lot like `require`:

    (ns the-divine-cheese-code.core
      (:require the-divine-cheese-code.visualization.svg))

is equivalent to

    (in-ns 'the-divine-cheese-code.core)
    (require 'the-divine-cheese-code.visualization.svg)

Note that we don't need to quote symbols inside `ns`.

We may also alias libraries:

    (ns the-divine-cheese-code.core
      (:require [the-divine-cheese-code.visualization.svg :as svg]))

We may `:require` multiple libraries:

    (ns the-divine-cheese-code.core
      (:require [the-divine-cheese-code.visualization.svg :as svg]
                [clojure.java.browse :as browse]))

`:require` also allows us to `refer` names:

    (ns the-divine-cheese-code.core
      (:require [the-divine-cheese-code.visualization.svg :refer [points]]))

We can also `:refer` to all symbols:

    (ns the-divine-cheese-code.core
      (:require [the-divine-cheese-code.visualization.svg :refer :all]))

There is also `:use`, which if we call on a vector, takes the first symbols as
the _base_ and then calls `use` with each symbol that follows.

## To Catch a Burglar

    the-divine-cheese-code.core> (min [{:lat 50 :lng 5} {:lat 40 :lng 10}])
    {:lat 40, :lng 5}
    the-divine-cheese-code.core> (max [{:lat 50 :lng 5} {:lat 40 :lng 10}])
    {:lat 50, :lng 10}

    the-divine-cheese-code.core> (map :lat [{:lat 0} {:lat 1} {:lat 2}])
    (0 1 2)
    the-divine-cheese-code.core> (clojure.core/max '(0 1 2))
    (0 1 2)
    the-divine-cheese-code.core> (apply clojure.core/max '(0 1 2))
    2
    the-divine-cheese-code.core> (zipmap [:a :b] [{:a 1} {:b 2}])
    {:a {:a 1}, :b {:b 2}}
    the-divine-cheese-code.core> (zipmap [:a :b] [1 2])
    {:a 1, :b 2}

    the-divine-cheese-code.core> (map (fn [k] (+ k 1)) [0 1 2])
    (1 2 3)

    the-divine-cheese-code.core> (merge-with - {:lat 50 :lng 10} {:lat 5 :lng 5})
    {:lat 45, :lng 5}
    the-divine-cheese-code.core> (merge-with + {:lat 50 :lng 10} {:lat 5 :lng 5})
    {:lat 55, :lng 15}
    the-divine-cheese-code.core> (merge-with + {:lat 50 :lng 10} {:east 5 :west 5})
    {:lat 50, :lng 10, :east 5, :west 5}

## Summary

Namespaces organize maps between symbols and vars, and vars are references to
Clojure objects. `def` sotres an object and updates the current namespace with a
map betwwn a symbol and a var pointing to that object. `defn-` lets us create
private functions.

Generally in files we'll use the `ns` macro, although in the REPL we will sometimes
use `in-ns` and more rarely, `create-ns`. There is a 1-to-1 relationship between a
namespace and its path on the filesystem.

We may use fully-qualified names to refer to objects in other namespaces. `refer`
allows us to use names from other namespaces without requiring full qualification.
`alias` lets us use a shorter name for namespace when writing a fully-qualified
name.

`require` and `use` ensure a namespace exists and is ready to be used. We may
optionally `refer` and `alias` the corresponding namespaces.

https://gist.github.com/ghoseb/287710/ is a great `ns` cheatsheet.

