(ns fizzbuzz
  (:use clojure.test))

(defn fizz-buzz [x]
  (cond (zero? (rem x 15)) "FizzzBuzz"
        (zero? (rem x 5)) "Buzz"
        (zero? (rem x 3)) "Fizz"
        :else x
  ))
