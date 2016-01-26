# How to use Emacs

## Installation

## Configuration

## Emacs Escape Hatch

## Emacs Buffers

`C-x b` to open a buffer, `C-x k` to kill it.

## Working with Files

## Key Bindings and Modes

### Emacs is a Lisp Interpreter

### Modes

An emacs _mode_ is a collection of key bindings and functions that are packaged
together to help you be productive when editing different types of files. Modes
also control things like syntax highlighting. There are _major_ modes and
_minor_ modes. Only one major mode is active at a time.

### Installing Packages

`M-x package-list-packages` will show almost every package available. Be sure to
`M-x package-refresh-contents` first to get the latest list. Install packages
with `M-x package-install`.

## Core Editing Terminology and Key Bindings

### Point

### Movement

### Selection with Regions

### Killing and the Kill Ring

### Editing and Help

## Using Emacs with Clojure

Update my `~/.lein/profiles.clj`

    {:user {:plugins [[cider/cider-nrepl "0.10.0-SNAPSHOT"]]}}
    {:repl {:dependencies [[org.clojure/tools.nrepl "0.2.12"]]}}

### Fire Up Your REPL!

    braveTrueCha02$ lein new app mellow
    Retrieving cider/cider-nrepl/0.8.1/cider-nrepl-0.8.1.pom from clojars
    Retrieving compliment/compliment/0.2.0/compliment-0.2.0.pom from clojars
    Retrieving cljs-tooling/cljs-tooling/0.1.3/cljs-tooling-0.1.3.pom from clojars
    Retrieving org/tcrawley/dynapath/0.2.3/dynapath-0.2.3.pom from central
    Retrieving org/clojure/java.classpath/0.2.0/java.classpath-0.2.0.pom from central
    Retrieving org/clojure/tools.namespace/0.2.5/tools.namespace-0.2.5.pom from central
    Retrieving org/clojure/tools.trace/0.7.8/tools.trace-0.7.8.pom from central
    Retrieving org/clojure/java.classpath/0.2.0/java.classpath-0.2.0.jar from central
    Retrieving org/tcrawley/dynapath/0.2.3/dynapath-0.2.3.jar from central
    Retrieving org/clojure/tools.namespace/0.2.5/tools.namespace-0.2.5.jar from central
    Retrieving org/clojure/tools.trace/0.7.8/tools.trace-0.7.8.jar from central
    Retrieving cljs-tooling/cljs-tooling/0.1.3/cljs-tooling-0.1.3.jar from clojars
    Retrieving compliment/compliment/0.2.0/compliment-0.2.0.jar from clojars
    Retrieving cider/cider-nrepl/0.8.1/cider-nrepl-0.8.1.jar from clojars
    Generating a project called mellow based on the 'app' template.

Emacs `mellow/src/clojure_mellow/core.clj`, start Cider with `M-x cider-jack-in`.

Modernized my emacs and fought with it a bit, willing to live with some CIDER
warnings for now.

### Interlude: Emacs Windows and Frames

In Emacs, frames contain windows.

### A Cornucopia of Useful Key Bindings

* `C-x C-e` at the end of a line - eval the line in the repl.
* `C-u C-x C-e` at the end of a line - print the evaluation
* `C-c M-n` set the namespace to the namespace at the top of the current file.
* `C-c C-k` to compile the current file.
* `C-c C-c` to compile the current function.
* `C-<up arrow>` or `M-p` to cycle back through history
* `C-<down arrow>` or `M-n` to cycle forward through history
* `C-c C-d C-d` for documentation for the symbol under the point (`q` to quit)
* `C-c C-d C-a` appropos across documentation (`q` to quit)
* `M-.` and `M-,` navigate source for symbol under point

### How to Handle Errors

* `q` to exit the stack trace
* `C-x b *cider-error*` to view the error again (`q` to exit)

### Paredit

`paredit-mode` is a minor mode that is useful for handling paren's in Lisps. Use
`M-x paredit-mode` to toggle it on and off.

#### Wrapping and Slurping

Use `M-(` for `paredit-wrap-around` and `C-<right arrow>` and `C-<left arrow>` in
inner parens to "slurp" and "barf".

#### Barfing

#### Navigation

* `C-M-f` to move to the closing parens.
* `C-M-b` to move to the opening parens.

## Continue Learning

Many nice references...
