(ns chefsteps.core-test
  (:require [clojure.test :refer :all]
            [chefsteps.core :as cs]))

;; Test the full ordered->sorted set flow using email addresses:
(def test-emails
  ["wookie@gmail.com"
   "arnie@gmail.com"
   "becky@smash.com"
   "rocky"
   "zzz"]
)

(def expected-emails-output
  "This is how we expect the sorted output to look (as a set):"
  #{"arnie@gmail.com"
   "becky@smash.com"
   "rocky"
   "wookie@gmail.com"
   "zzz"}
)

(deftest sort-test-emails
  (testing "That the test emails are reordered correctly"
    (let[ordered-emails
         (cs/make-sorted-set 
           (seq (cs/ordered-emails test-emails)))]
      (is (= ordered-emails expected-emails-output)))))

;; Test the full ordered->sorted set flow using numbers:
(def test-numbers
  [1 6 9 3 4 5 5 6 0])

(def expeted-numbers-output
  #{0 1 3 4 5 6 9})

(deftest sort-test-numbers
  (testing "That the numbers are ordered & sorted."
    (let [ordered-numbers
      (cs/make-sorted-set
        (seq (cs/ordered-emails test-numbers)))]
      (is (ordered-numbers expected-numbers-output)))))
