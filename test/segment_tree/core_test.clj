;;;;
;;;;           Copyright r6eve 2019 -
;;;;  Distributed under the Boost Software License, Version 1.0.
;;;;     (See accompanying file LICENSE_1_0.txt or copy at
;;;;           https://www.boost.org/LICENSE_1_0.txt)
;;;;

(ns segment-tree.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [segment-tree.core :as segment-tree])
  (:import [segment_tree.core SegmentTree]))

(deftest make-test
  (let [s ^SegmentTree (segment-tree/make 4 10)]
    (is (= 10 (.init s)))
    (is (= 16 (.size s)))
    (is (= (repeat 31 10) (vec (.seg s))))))

(deftest update!-test
  (let [z 2147483647 ; 2^31 - 1
        s (segment-tree/make 4 z)]
    (doseq [[i x] (map-indexed vector [5 3 7 9 1 4 6 2])]
      (segment-tree/update! s i x))
    (is (= [1 1 z 3 1 z z 3 7 1 2 z z z z 5 3 7 9 1 4 6 2 z z z z z z z z]
           (vec (.seg ^SegmentTree s))))))

;; index:
;;        |-----------------------0-----------------------|
;;        |-----------1-----------|-----------2-----------|
;;        |-----3-----|-----4-----|-----5-----|-----6-----|
;;        |--7--|--8--|--9--|--10-|--11-|--12-|--13-|--14-|
;;        |15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|
;;
;; value:
;;        |-----------------------1-----------------------|
;;        |-----------1-----------|-----------z-----------|
;;        |-----3-----|-----1-----|-----z-----|-----z-----|
;;        |--3--|--7--|--1--|--2--|--z--|--z--|--z--|--z--|
;;        | 5| 3| 7| 9| 1| 4| 6| 2| z| z| z| z| z| z| z| z|
;;
;; interval:
;; [0,1)  |--|
;; [0,2)  |-----|
;; [0,3)  |--------|
;; [0,4)  |-----------|
;; [0,8)  |-----------------------|
;; [0,16) |-----------------------------------------------|
;; [1,2)     |--|
;; [1,3)     |-----|
;; [5,7)                 |-----|
;; [8,10)                         |-----|

(deftest find-min-test
  (let [z 2147483647
        s (segment-tree/make 4 z)]
    (doseq [[i x] (map-indexed vector [5 3 7 9 1 4 6 2])]
      (segment-tree/update! s i x))
    (is (= 5 (segment-tree/find-min s 0 1)))
    (is (= 3 (segment-tree/find-min s 0 2)))
    (is (= 3 (segment-tree/find-min s 0 3)))
    (is (= 3 (segment-tree/find-min s 0 4)))
    (is (= 1 (segment-tree/find-min s 0 8)))
    (is (= 1 (segment-tree/find-min s 0 16)))
    (is (= 3 (segment-tree/find-min s 1 2)))
    (is (= 3 (segment-tree/find-min s 1 3)))
    (is (= 4 (segment-tree/find-min s 5 7)))
    (is (= z (segment-tree/find-min s 8 10)))))

(deftest sum-test
  (let [z 2147483647
        s (segment-tree/make 4 z)]
    (doseq [[i x] (map-indexed vector [5 3 7 9 1 4 6 2])]
      (segment-tree/update! s i x))
    (is (= 5 (segment-tree/sum s 0 1)))
    (is (= 3 (segment-tree/sum s 0 2)))
    (is (= 10 (segment-tree/sum s 0 3)))
    (is (= 3 (segment-tree/sum s 0 4)))
    (is (= 1 (segment-tree/sum s 0 8)))
    (is (= 1 (segment-tree/sum s 0 16)))
    (is (= 3 (segment-tree/sum s 1 2)))
    (is (= 10 (segment-tree/sum s 1 3)))
    (is (= 10 (segment-tree/sum s 5 7)))
    (is (= z (segment-tree/sum s 8 10)))))

;; value:
;;        |-----------6-----------|
;;        |-----6-----|-----z-----|
;;        |--3--|--3--|--z--|--z--|
;;        | 1| 2| 3| z| z| z| z| z|
;;
;; interval:
;; [0,2)  |-----|
;; [1,2)     |--|
;; [1,3)     |-----|
;; [3,5)           |----|

(deftest add!-test
  (let [z 0
        s (segment-tree/make 3 z)]
    (doseq [[i x] (map-indexed vector [1 2 3])]
      (segment-tree/add! s i x))
    (is (= [6 6 z 3 3 z z 1 2 3 z z z z z]
           (vec (.seg ^SegmentTree s))))
    (is (= 3 (segment-tree/sum s 0 2)))
    (is (= 2 (segment-tree/sum s 1 2)))
    (is (= 5 (segment-tree/sum s 1 3)))
    (is (= z (segment-tree/sum s 3 5)))))

(deftest no-elements-test
  (testing "Returns the initial value when no elements in trees."
    (let [z 2147483647
          s (segment-tree/make 1 z)]
      (is (= z (segment-tree/find-min s 0 1)))
      (segment-tree/update! s 0 5)
      (is (= 5 (segment-tree/find-min s 0 1))))))
