(ns yathzee (:use clojure.test))

(with-test 
  (defn simple [dices digit]
    (reduce + (filter #(= % digit) dices))
    )
  (is (= 0 (simple [2 2 3 4 5] 1)) "No matches")
  (is (= 4 (simple [2 2 3 4 5] 2)) "Two twos")
)

(with-test
  (defn several-equals [dices num-equals]
    (let [pairs (filter (fn [x] (>= (second x) num-equals)) (frequencies dices))]
    (if (empty? pairs) 0 (* num-equals (reduce max (map #(first %) pairs))))
    )) 
    (is (= 0 (several-equals [6 2 3 4 5] 2)) "No pairs")
    (is (= 4 (several-equals [2 2 3 4 5] 2)) "One pair")
    (is (= 12 (several-equals [2 2 6 6 5] 2)) "Highest pair")
    (is (= 12 (several-equals [2 6 6 6 5] 2)) "Three equals is also a pair")
    (is (= 24 (several-equals [2 6 6 6 6] 4)) "Four equals")
    (is (= 0 (several-equals [2 6 6 6 5] 4)) "Not four equal")
)

(with-test 
  (defn chance [dices]
    (reduce + dices)
    )
  (is (= 20 (chance [2 3 6 6 3])))
)

(with-test
  (defn house [dices]
    (if (= (set (vals (dice-map dices))) #{2 3}) (reduce + dices) 0)
    )
  (is (= 0 (house [1 2 4 4 5])))
  (is (= 16 (house [2 2 2 5 5])))
  )

(with-test
  (defn low-straight [dices]
    (if (= (set dices) #{1 2 3 4 5}) 15 0))
  (is (= 0 (low-straight [3 4 5 3 1])))
  (is (= 15 (low-straight [5 4 2 3 1])))
  )

(with-test
  (defn high-straight [dices]
    (if (= (set dices) #{2 3 4 5 6}) 20 0))
  (is (= 0 (high-straight [3 4 5 3 1])))
  (is (= 20 (high-straight [5 4 2 3 6])))
  )

(with-test
  (defn two-pairs [dices]
    (let [pairs (filter #(>= (second %) 2) (dice-map dices))]
       (if (= (count pairs) 2) (* 2 (reduce + (map #(first %) pairs))) 0)
    ))
  (is (= 0 (two-pairs [3 3 4 5 2])))
  (is (= 14 (two-pairs [3 3 4 4 2])))
  (is (= 14 (two-pairs [3 3 4 4 4])))
  )