segment-tree
============
[![Build Status][]][Build Results]
[![Codecov Status][]][Codecov Results]

A Clojure library designed to manipulate [segment tree][].

Segment tree is useful to quickly search a specific element in the given
interval.

## Installation

This library is released on [Clojars][].

Clojure CLI/deps.edn:

```clojure
segment-tree {:mvn/version "0.1.0"}
```

Leiningen/Boot:

```clojure
[segment-tree "0.1.0"]
```

## Usage

Make a segment tree.

```clojure
(def s
  (let [z 2147483647] ; 2^31 - 1
    (make 4 z)))
```

```
index:
       |-----------------------0-----------------------|
       |-----------1-----------|-----------2-----------|
       |-----3-----|-----4-----|-----5-----|-----6-----|
       |--7--|--8--|--9--|--10-|--11-|--12-|--13-|--14-|
       |15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|

value:
       |-----------------------z-----------------------|
       |-----------z-----------|-----------z-----------|
       |-----z-----|-----z-----|-----z-----|-----z-----|
       |--z--|--z--|--z--|--z--|--z--|--z--|--z--|--z--|
       | z| z| z| z| z| z| z| z| z| z| z| z| z| z| z| z|
```

Update the segment tree.

```clojure
(doseq [[i x] (map-indexed vector [5 3 7 9 1 4 6 2])]
  (segment-tree/update! s i x))
```

```
value:
       |-----------------------1-----------------------|
       |-----------1-----------|-----------z-----------|
       |-----3-----|-----1-----|-----z-----|-----z-----|
       |--3--|--7--|--1--|--2--|--z--|--z--|--z--|--z--|
       | 5| 3| 7| 9| 1| 4| 6| 2| z| z| z| z| z| z| z| z|
```

Find a minimum element in the given interval (0-based half-close-half-open).

```clojure
(segment-tree/find-min s 0 1) ; 5
(segment-tree/find-min s 0 2) ; 3
(segment-tree/find-min s 0 3) ; 3
(segment-tree/find-min s 0 4) ; 3
(segment-tree/find-min s 0 8) ; 1
(segment-tree/find-min s 0 16) ; 1
(segment-tree/find-min s 5 7) ; 4
```

```
value:
       |-----------------------1-----------------------|
       |-----------1-----------|-----------z-----------|
       |-----3-----|-----1-----|-----z-----|-----z-----|
       |--3--|--7--|--1--|--2--|--z--|--z--|--z--|--z--|
       | 5| 3| 7| 9| 1| 4| 6| 2| z| z| z| z| z| z| z| z|

interval:
[0,1)  |--| 5
[0,2)  |-----| 3
[0,3)  |--------| 3
[0,4)  |-----------| 3
[0,8)  |-----------------------| 1
[0,16) |-----------------------------------------------| 1
[5,7)                 |-----| 4
```

Sum the minimum element in the given interval.

```clojure
(segment-tree/sum s 0 1) ; 5
(segment-tree/sum s 0 2) ; 3
(segment-tree/sum s 0 3) ; 10
(segment-tree/sum s 0 4) ; 3
(segment-tree/sum s 0 8) ; 1
(segment-tree/sum s 0 16) ; 1
(segment-tree/sum s 5 7) ; 10
```

```
value:
       |-----------------------1-----------------------|
       |-----------1-----------|-----------z-----------|
       |-----3-----|-----1-----|-----z-----|-----z-----|
       |--3--|--7--|--1--|--2--|--z--|--z--|--z--|--z--|
       | 5| 3| 7| 9| 1| 4| 6| 2| z| z| z| z| z| z| z| z|

interval:
[0,1)  |--| 5
[0,2)  |-----| 3
[0,3)  |---------| 10
[0,4)  |-----------| 3
[0,8)  |-----------------------| 1
[0,16) |-----------------------------------------------| 1
[5,7)                 |-----| 10
```

Refer to the docstrings for more documentation, and to the tests for more examples.

## License

Copyright © 2019 r6eve

[Build Status]: https://github.com/r6eve/segment-tree/workflows/main/badge.svg
[Build Results]: https://github.com/r6eve/segment-tree/actions
[Codecov Status]: https://codecov.io/github/r6eve/segment-tree/coverage.svg?branch=master
[Codecov Results]: https://codecov.io/github/r6eve/segment-tree?branch=master
[segment tree]: https://www.slideshare.net/iwiwi/ss-3578491/33
[Clojars]: https://clojars.org/segment-tree
