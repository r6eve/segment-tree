;;;;
;;;;           Copyright r6eve 2019 -
;;;;  Distributed under the Boost Software License, Version 1.0.
;;;;     (See accompanying file LICENSE_1_0.txt or copy at
;;;;           https://www.boost.org/LICENSE_1_0.txt)
;;;;

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
