(ns exercises-02.core
  (:gen-class))

; From fwpd exercise
(def filename "suspects.csv")


(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})
(defn convert
  [vamp-key value]
  ((vamp-key conversions) value))

(defn parse
  "Convert CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  [rows]
  (map #(hash-map :name (first %)
                  :glitter-index (convert :glitter-index (second %)))
       rows))

(defn glitter-filter
  [min-glitter records]
  (filter #(>= (:glitter-index %) min-glitter) records))

(def suspects (mapify (parse (slurp filename))))

(def vampires (glitter-filter 3 suspects))


; Exercises 02

; 1. Turn the result of your glitter filter into a list of names.
(defn glitter-filter-names
  [vampires]
  (map :name vampires))

(def vampire-names (glitter-filter-names vampires))


; 2. Write a function, append,
; which will append a new suspect to your list of suspects.
(defn append
  [suspects new-suspect]
  (concat suspects [new-suspect]))


; 3. Write a function, validate, which will check that
; :name and :glitter-index are present when you append.
; The validate function should accept two arguments: a map of keywords to validating functions, similar to conversions, and the record to be validated.
(def validations {:name some?
                  :glitter-index some?})

(defn validate
  [validations-map record]
  (let [keywords (keys validations-map)]
    (reduce #(and %1 ((%2 validations-map) (%2 record)))
            true
            keywords)))

(defn validated-append
  [suspects new-suspect]
  (if (validate validations new-suspect)
    (append suspects new-suspect)))


; 4. Write a function that will take your list of maps and
; convert it back to a CSV string
; You’ll need to use the clojure.string/join function.
(defn map-to-csv
  [suspect-map]
  (clojure.string/join "," (vals suspect-map)))

(defn suspects-to-csv
  [suspects]
  (clojure.string/join "\n" (map map-to-csv suspects)))

(spit "vampires.csv" (suspects-to-csv vampires))
