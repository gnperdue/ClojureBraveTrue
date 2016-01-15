# Building, Running, and the REPL

## First Things First

* JVM processes execute Java bytecode.
* Usually, the Java Compiler produces Java bytecode from Java source code.
* JAR files are collections of Java bytecode.
* Java programs are usually distributed as JAR files.
* The Java program _clojure.jar_ reads Clojure source code and produces Java
bytecode.
* That Java bytecode is then executed by the same JVM process already running
_clojure.jar_.

## Leiningen

To install Clojure you should install Leiningen:

    brew install leiningen

and then follow the tutorial:
  https://github.com/technomancy/leiningen/blob/stable/doc/TUTORIAL.md

    $ brew upgrade leiningen
    ==> Upgrading 1 outdated package, with result:
    leiningen 2.5.3
    ==> Upgrading leiningen
    ==> Downloading https://homebrew.bintray.com/bottles/leiningen-2.5.3.yosemite.bottle.
    ######################################################################## 100.0%
    ==> Pouring leiningen-2.5.3.yosemite.bottle.tar.gz
    ==> Caveats
    Dependencies will be installed to:
    $HOME/.m2/repository
    To play around with Clojure run `lein repl` or `lein help`.
    
    Bash completion has been installed to:
    /usr/local/etc/bash_completion.d
    
    zsh completion has been installed to:
    /usr/local/share/zsh/site-functions
    ==> Summary
    🍺  /usr/local/Cellar/leiningen/2.5.3: 8 files, 15M
    $ lein repl
    Retrieving org/clojure/tools.nrepl/0.2.10/tools.nrepl-0.2.10.pom from central
    Retrieving org/clojure/clojure/1.7.0/clojure-1.7.0.pom from central
    Retrieving org/clojure/tools.nrepl/0.2.10/tools.nrepl-0.2.10.jar from central
    Retrieving org/clojure/clojure/1.7.0/clojure-1.7.0.jar from central
    nREPL server started on port 51087 on host 127.0.0.1 - nrepl://127.0.0.1:51087
    REPL-y 0.3.7, nREPL 0.2.10
    Clojure 1.7.0
    Java HotSpot(TM) 64-Bit Server VM 1.7.0_17-b02
    Docs: (doc function-name-here)
    (find-doc "part-of-name-here")
    Source: (source function-name-here)
    Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
    Results: Stored in vars *1, *2, *3, an exception in *e
    
    user=> (+ 1 2)
    3
    user=> Bye for now!

Then,

    braveTrueCha01$ ls
    braveTrueCha01$ lein new app clojure-noob
    Generating a project called clojure-noob based on the 'app' template.
    braveTrueCha01$ tree
    .
    └── clojure-noob
        ├── CHANGELOG.md
        ├── LICENSE
        ├── README.md
        ├── doc
        │   └── intro.md
        ├── project.clj
        ├── resources
        ├── src
        │   └── clojure_noob
        │       └── core.clj
        └── test
            └── clojure_noob
                └── core_test.clj
    
    7 directories, 7 files

### Running the Clojure Project

    clojure-noob$ emacs src/clojure_noob/core.clj
    clojure-noob$ ls
    CHANGELOG.md  README.md     project.clj   src/
    LICENSE       doc/          resources/    test/
    clojure-noob$ lein run
    Mellow, whirled!

### Building the Clojure Project

    clojure-noob$ lein uberjar
    Compiling clojure-noob.core
    Created /Users/gnperdue/Dropbox/Programming/Programming/Clojure/ClojureBraveTrue/braveTrueCha01/clojure-noob/target/uberjar+uberjar/clojure-noob-0.1.0-SNAPSHOT.jar
    Created /Users/gnperdue/Dropbox/Programming/Programming/Clojure/ClojureBraveTrue/braveTrueCha01/clojure-noob/target/uberjar/clojure-noob-0.1.0-SNAPSHOT-standalone.jar
    clojure-noob$ ls
    CHANGELOG.md   README.md      doc/           resources/     target/
    LICENSE        dev-resources/ project.clj    src/           test/
    clojure-noob$ ls target/uberjar/
    clojure-noob-0.1.0-SNAPSHOT-standalone.jar
    stale/
    clojure-noob$ ls target/uberjar/clojure-noob-0.1.0-SNAPSHOT-standalone.jar
    target/uberjar/clojure-noob-0.1.0-SNAPSHOT-standalone.jar
    clojure-noob$ java -jar target/uberjar/clojure-noob-0.1.0-SNAPSHOT-standalone.jar
    Mellow, whirled!

### Using the REPL

    clojure-noob$ lein repl
    nREPL server started on port 51204 on host 127.0.0.1 - nrepl://127.0.0.1:51204
    REPL-y 0.3.7, nREPL 0.2.10
    Clojure 1.7.0
    Java HotSpot(TM) 64-Bit Server VM 1.7.0_17-b02
    Docs: (doc function-name-here)
    (find-doc "part-of-name-here")
    Source: (source function-name-here)
    Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
    Results: Stored in vars *1, *2, *3, an exception in *e
    
    clojure-noob.core=>
    clojure-noob.core=> (-main)
    Mellow, whirled!
    nil
    clojure-noob.core=> (+ 1 2 3 4 5 6 7 8 9 10)
    55
    clojure-noob.core=> (* 1 2 3 4 5 6 7 8 9 10)
    3628800
    clojure-noob.core=> (first [1 2 3 4 5 6 7 8 9 10])
    1
    clojure-noob.core=> (do (println "no prompt here!")
                   #_=> (+ 1 3))
    no prompt here!
    4

### Clojure Editors

Emacs duh.

