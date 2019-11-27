(ns segment-tree.core
  "Library designed to manipulate segment tree.")

(deftype SegmentTree [^long size init seg])

(defn- parent ^long [^long i]
  (quot (dec i) 2))

(defn- left ^long [^long i]
  (+ 1 (* 2 i)))

(defn- right ^long [^long i]
  (+ 2 (* 2 i)))

(defn- pow
  [^long x ^long n]
  (loop [x x n n acc 1]
    (if (zero? n)
      acc
      (recur (* x x)
             (quot n 2)
             (long (cond-> acc (= 1 (mod n 2)) (* x)))))))

(defn make
  "Returns an instance that stands for segment tree. This function takes two
  arguments: `height` of tree and `init` with which elements initialized."
  [height init]
  (let [size (long (pow 2 height))
        seg (to-array (repeat (dec (* 2 size)) init))]
    (->SegmentTree size init seg)))

(defn update!
  "Replace `i` elements by `x`."
  ([^SegmentTree s i x] (update! s i x min))
  ([^SegmentTree s i x min']
   (let [bottom-i (dec (+ i (.size s)))]
     (aset (.seg s) bottom-i x)
     (loop [i bottom-i]
       (if (<= i 0)
         s
         (let [p (parent i)
               l (aget (.seg s) (left p))
               r (aget (.seg s) (right p))]
           (aset (.seg s) p (min' l r))
           (recur p)))))))

(defn add!
  "Modifies `i` elements by the value that adds `i` elements and `x`."
  ([^SegmentTree s i x] (add! s i x +))
  ([^SegmentTree s i x add]
   (let [bottom-i (dec (+ i (.size s)))]
     (aset (.seg s) bottom-i (+ x (aget (.seg s) bottom-i)))
     (loop [i bottom-i]
       (if (<= i 0)
         s
         (let [p (parent i)
               l (aget (.seg s) (left p))
               r (aget (.seg s) (right p))]
           (aset (.seg s) p (add l r))
           (recur p)))))))

(defn- cross? [a b r l]
  (or (<= r a) (<= b l)))

(defn- include? [a b r l]
  (and (<= a l) (<= r b)))

(defn walk
  "Traverse the segment tree `s` with range [`a`,`b`) (0-based,
  half-close-half-open) and identity `ident` while applying `f`."
  [^SegmentTree s a b ident f]
  (letfn [(doit [l r k]
            (if (cross? a b r l)
              ident
              (if (include? a b r l)
                (aget (.seg s) k)
                (let [m (quot (+ l r) 2)]
                  (f (doit l m (left k)) (doit m r (right k)))))))]
    (doit 0 (.size s) 0)))

(defn find-min
  "Returns a minimum element in the range [`a`,`b`)."
  ([^SegmentTree s a b] (find-min s a b min))
  ([^SegmentTree s a b min']
   (walk s a b (.init s) min')))

(defn sum
  "Sums the minimum elements in the range [`a`,`b`)."
  ([^SegmentTree s a b] (sum s a b +))
  ([^SegmentTree s a b add]
   (walk s a b 0 add)))
