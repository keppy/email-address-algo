(ns chefsteps.core
  (:require [faker.internet :as fake]))

(def first-emails (take 50000 (fake/emails)))

(def emails (let [s1 first-emails
                  s2 first-emails]
              (concat s1 s2)))

(deftype OrderedSet [v m s]
  clojure.lang.IObj
    (withMeta [this md] (OrderedSet. v (with-meta m md) s))
    (meta [this] (meta m))
  clojure.lang.IPersistentSet
    (cons [this object]
      (if (contains? m object)
        this
        (OrderedSet.
          (conj v object)
          (assoc m object (count v))
          s)))
    (count [this] (count m))
    (empty [this] (OrderedSet. [] {} (Object.)))
    (equiv [this other] (= (seq this) other))
    (seq [this] (seq (remove #{s} v)))
    (get [this object] (v (m object)))
    (contains [this object]
      (contains? m object))  
    (disjoin [this object]
      (if-let [idx (m object)]
        (OrderedSet.
          (assoc v idx s)
          (dissoc m object)
          s)
        this))
    clojure.lang.IFn
      (invoke [this object]
        (get this object))
      (invoke [this object not-found]
        (get this object not-found)))

(defn make-ordered-set [& elements]
  (reduce conj (OrderedSet. [] {} (Object.)) elements))

(defn make-sorted-set [elements]
  "Use a PersistentTreeSet to sort the elements."
  (clojure.lang.PersistentTreeSet/create elements))

(defn ordered-emails
  "Use apply to order the elements"
  [a]
  (apply make-ordered-set a))

(defn time-operation
  "how long does it take to do this operation?"
  []
  (time (ordered-emails emails)))
