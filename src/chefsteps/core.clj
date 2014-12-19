(ns chefsteps.core)

;; The OrderedSet data type ensures the order will be the same
;; as the element order of the input data. This is then followed up later
;; with another transformation that turns the ordered set into a sorted set.
;; Both of these states are potentially useful.

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
  "Reduction for conjing elements into the set"
  (reduce conj (OrderedSet. [] {} (Object.)) elements))

(defn make-sorted-set [elements]
  "Use a PersistentTreeSet to sort the elements."
  (clojure.lang.PersistentTreeSet/create elements))

(defn seq-to-ordered-set
  "Use apply to order the seq of elements"
  [a]
  (apply make-ordered-set a))
