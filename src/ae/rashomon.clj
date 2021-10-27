(ns ae.rashomon)

(create-ns 'ae.rashomon.event)
(alias 'event 'ae.rashomon.event)

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn persps->fn
  "Convert the map of perspectives to a fn that can be used with reduce."
  [persps]
  (fn [testimony {:keys [::event/type] :as event}]
    (let [persp (get persps type)]
      (if (nil? persp)
        testimony                       ; noop if there is no persp for ::event/type
        (persp testimony event)))))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn apply-event
  "Update a testimony by applying a single event."
  [testimony persps event]
  ((persps->fn persps) testimony event))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn apply-events
  "Update a testimony by applying several events."
  [testimony persps events]
  (reduce (persps->fn persps) testimony events))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn build
  "Use perspective to build a testimony."
  [persps events]
  (apply-events nil persps events))
