(ns segment-tree.core-bench
  (:require [criterium.core :as criterium]
            [segment-tree.core :as segment-tree]))

(let [n 10 s (segment-tree/make n 2147483647)]
  (criterium/with-progress-reporting
    (criterium/quick-bench
     (do
       (doseq [[i x] (map-indexed vector (range 50))]
         (segment-tree/update! s i x))
       nil)
     :verbose))
  (criterium/with-progress-reporting
    (criterium/quick-bench
     (do
       (segment-tree/find-min s 0 (dec n))
       nil)
     :verbose)))
