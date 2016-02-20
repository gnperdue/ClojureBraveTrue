;; Test away!

;; user> (let [[color [size] :as original] ["blue" ["small"]]]
;;         {:color color :size size :original original})
;; {:color "blue", :size "small", :original ["blue" ["small"]]}
;; user> (let [{flower1 :flower1 flower2 :flower2}
;;             {:flower1 "red" :flower2 "blue"}]
;;         (str "the flowers are " flower1 " and " flower2))
;; "the flowers are red and blue"

;; Need to set this here explicitly? - if we launch the REPL from here, 
;; apparently yes...
(in-ns 'user)

;; I have trouble running this out of the REPL if I `cider-jack-in` from this
;; file. I end up in one of the ones we fiddle with below (at least for some
;; forms of this file...).
;; (ns-name *ns*)       
                 
;; This is always "safe"
(clojure.core/ns-name clojure.core/*ns*)

;; We `intern` this var...
(def great-books ["East of Eden" "The Glass Bead Game"])

;; See all interned vars...
(ns-interns *ns*)

(def my-favorite-things ["Raindrops on roses" "Whiskers on kittens"])

(ns-interns *ns*)

;; #'user/great-books
(get (ns-interns *ns*) 'great-books)

;; `nil`
(get (ns-interns *ns*) 'great-borks)

;; ["East of Eden" "The Glass Bead Game"]
(deref #'user/great-books)

;; Exception
;; (deref #'user/great-borks)

;; name collision
(def great-books ["The Power of Bees" "Journey to Upstairs"])

;; make, but don't change to
(create-ns 'cheese.taxonomy)

;; Get the name of the thing we made...
(ns-name (create-ns 'cheese.taxonomy))

;; still `user`
(ns-name *ns*)

;; make and change-to - running this "here" as opposed to explicitly in the
;; REPL "by-hand" does not seem to actually move us into the new ns (while
;; running directly in the REPL does)
(in-ns 'cheese.taxonomy)

;; now `cheese.taxnomy` - note we need to use ns spec on funcs, etc.
;; note - it is just `cheese.taxonomy` in the scratchpad here, but not the repl
;; unless we run the command "by hand" there...
(clojure.core/ns-name clojure.core/*ns*)

;; creating these "here", in `cheese.taxonomy` means they are not visible in the
;; REPL unless I say, for example:
;; user> cheese.taxonomy/cheddars
;; ["mild" "medium" "strong" "sharp" "extra sharp"]
(def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])

(def bries ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"])

(in-ns 'cheese.analysis)

;; we're in the wrong namespace - this will throw an exception
;; (clojure.core/println cheddars)

;; now we can print this - the values show up in the REPL
(clojure.core/println #'cheese.taxonomy/cheddars)  ;; get the `#'...`
(clojure.core/println cheese.taxonomy/cheddars)    ;; get the vect

;; switch back
(in-ns 'cheese.taxonomy)
(clojure.core/println cheddars)


