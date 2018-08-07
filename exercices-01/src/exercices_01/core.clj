(ns exercices-01.core
  (:gen-class))

; Exercises
; 1. Use the str, vector, list, hash-map, and hash-set functions.

(defn use-str
  [first second]
  (str first " and " second))

(println (use-str "tchau" "bença"))


(defn use-vector
  [first second]
  (vector first second))

(println (use-vector 1 2))


(defn use-list
  [first second]
  (list first second))

(println (use-list 3 4))


(defn use-hash-map
  [key value]
  (hash-map key value))

(println (use-hash-map :key "value"))


(defn use-hash-set
  [first second third]
  (hash-set first second third))

(println (use-hash-set 1 1 2))




; 2. Write a function that takes a number and adds 100 to it.

(defn add-100
  [number]
  (+ 100 number))

(println (add-100 0))
(println (add-100 10))




; 3. Write a function, dec-maker, that works exactly like the function inc-maker
; except with subtraction:

(defn dec-maker
  [factor]
  #(- % factor))

(def dec9 (dec-maker 9))
(println (dec9 10))
(println (dec9 9))
(println (dec9 0))




; 4. Write a function, mapset, that works like map except the return value is a set:

(defn mapset
  [f coll]
  (set (map f coll)))

(println (mapset inc [1 1 2 2]))


(defn mapset2
  [f coll]
  (reduce (fn [mySet elem] (conj mySet (f elem)))
          #{}
          coll))

(println (mapset2 inc [1 1 2 2]))
(println (mapset2 inc [1 1 2 3]))
(println (mapset2 dec9 [9 0 10 9]))




; 5. Create a function that’s similar to symmetrize-body-parts except that it
; has to work with weird space aliens with radial symmetry.
; Instead of two eyes, arms, legs, and so on, they have five.

(def asym-alien-body-parts [{:name "head" :size 3}
                            {:name "first-eye" :size 1}
                            {:name "first-ear" :size 1}
                            {:name "mouth" :size 1}
                            {:name "nose" :size 1}
                            {:name "first-upper-arm" :size 3}
                            {:name "chest" :size 10}
                            {:name "back" :size 10}
                            {:name "first-forearm" :size 3}
                            {:name "abdomen" :size 6}
                            {:name "first-hand" :size 2}
                            {:name "first-lower-leg" :size 3}
                            {:name "first-foot" :size 2}])

(defn symmetrize-part
  [part]
  [
   {:name (clojure.string/replace (:name part) #"^first-" "second-") :size (:size part)}
   {:name (clojure.string/replace (:name part) #"^first-" "third-") :size (:size part)}
   {:name (clojure.string/replace (:name part) #"^first-" "fourth-") :size (:size part)}
   {:name (clojure.string/replace (:name part) #"^first-" "fith-") :size (:size part)}])


(defn symmetrize-body-parts
      [asym-body-parts]
      (reduce (fn [parts part]
                  (into parts (set (conj (symmetrize-part part) part))))
              []
              asym-body-parts))

(println (symmetrize-body-parts asym-alien-body-parts))




; 6. Create a function that generalizes symmetrize-body-parts
;     and the function you created in Exercise 5.
;    The new function should take a collection of body parts
;     and the number of matching body parts to add.

; (defn general-symmetry
;   [asym-body-parts num-parts]
;   body)

(def asym-n-body-parts [{:name "head" :size 3}
                        {:name "0-eye" :size 1}
                        {:name "0-ear" :size 1}
                        {:name "mouth" :size 1}
                        {:name "nose" :size 1}
                        {:name "0-upper-arm" :size 3}
                        {:name "chest" :size 10}
                        {:name "back" :size 10}
                        {:name "0-forearm" :size 3}
                        {:name "abdomen" :size 6}
                        {:name "0-hand" :size 2}
                        {:name "0-lower-leg" :size 3}
                        {:name "0-foot" :size 2}])

(defn part-with-num
  [part num]
  {:name (clojure.string/replace (:name part) #"^0-" (str num "-"))
   :size (:size part)})

(defn symmetrize-part-n-times
  [part num]
  (let [seq (take num (range))]
       (into [] (map #(part-with-num part %) seq))))

(defn symmetrize-n-body
  [asym-body-parts num]
  (reduce (fn [parts part]
              (into parts (set (conj (symmetrize-part-n-times part num) part))))
          []
          asym-body-parts))

(println (symmetrize-n-body asym-n-body-parts 3))
