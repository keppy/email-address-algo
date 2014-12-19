(ns chefsteps.core-test
  (:require [clojure.test :refer :all]
            [chefsteps.core :as cs]))

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


