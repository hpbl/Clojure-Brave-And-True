(ns fwpd.core)
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

(println (glitter-filter 3 (mapify (parse (slurp filename)))))
