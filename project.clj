(defproject segment-tree "0.1.1-SNAPSHOT"
  :description "Library designed to manipulate segment tree."
  :url "https://github.com/r6eve/segment-tree"
  :license {:name "Boost Software License - Version 1.0"
            :url "https://www.boost.org/users/license.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :profiles {; OPTIMIZE: Keep it generalized.
             :dev {:global-vars {*warn-on-reflection* true
                                 ; *unchecked-math* :warn-on-boxed
                                 }}
             :bench {:dependencies [[criterium "0.4.5"]]
                     :plugins [[lein-exec "0.3.7"]]}
             :coverage {:plugins [[lein-cloverage "1.1.1"]]}
             :clj-async-profiler {:dependencies [[com.clojure-goes-fast/clj-async-profiler "0.4.0"]]
                                  :jvm-opts ["-Djdk.attach.allowAttachSelf"]}}
  :repl-options {:init-ns segment-tree.core}
  :repositories [["release" {:url "https://clojars.org/repo"
                             :creds :gpg}]])
