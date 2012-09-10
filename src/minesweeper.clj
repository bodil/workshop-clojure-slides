(ns minesweeper
  (:use clojure.test))

(def board [[nil   :mine nil   nil  ]
            [:mine nil   nil   nil  ]
            [nil   nil   :mine nil  ]
            [:mine nil   nil   :mine]])

(defn y [c] (c 0))
(defn x [c] (c 1))

(with-test
    (defn add
      "Add two coordinate pairs together."
      [c1 c2]
      [(+ (y c1) (y c2))
       (+ (x c1) (x c2))])

  (is (= [4 4] (add [1 -3] [3 7])))
  (is (= [2 -4] (add [-1 3] [3 -7]))))

(with-test
    (defn mine?
      "Returns true if cell c contains a mine, false otherwise."
      [board c]
        (and 
          (>= (first c) 0)
          (>= (second c) 0)
          (< (first c) (count board))
          (< (second c) (count (first board)))
           (= :mine ((board (y c)) (x c)))
        ))
  
  (is (true? (mine? board [0 1])))
  (is (false? (mine? board [0 2])))
  (is (false? (mine? board [-1 -1])))
  (is (false? (mine? board [13 37]))))

(with-test
    (defn neighbours
      "For a cell c, return a list of the cell's neighbours."
      [c]
      (map (fn [offset] (add c offset))
           [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]]))

  (is (= [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]]
         (neighbours [0 0])))
  (is (= [[1 2] [1 3] [1 4] [2 2] [2 4] [3 2] [3 3] [3 4]]
         (neighbours [2 3]))))

(with-test
    (defn mines
      "For a cell c, return the number of mines adjacent to it."
      [board c]
      (count (filter (fn [c] (mine? board c)) (neighbours c))))

  (is (= 3 (mines board [1 1])))
  (is (= 0 (mines board [0 3])))
  (is (= 2 (mines board [3 2]))))