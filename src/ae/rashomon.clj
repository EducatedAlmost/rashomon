(ns ae.rashomon)

(create-ns 'ae.rashomon.event)
(alias 'event 'ae.rashomon.event)

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn persps->fn
  "Convert the map of perspectives to a fn that can be used with reduce."
  ([persps] (persps->fn persps nil))
  ([persps type-key]
   (fn [testimony event]
     (let [type-key (or type-key ::event/type)
           type (get event type-key)
           persp (get persps type)]
       (if (nil? persp)
         testimony ; noop if there is no persp for ::event/type
         (persp testimony event))))))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn apply-event
  "Update a testimony by applying a single event."
  ([testimony persps event] ((persps->fn persps) testimony event))
  ([testimony persps event type-key]
   ((persps->fn persps type-key) testimony event)))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn apply-events
  "Update a testimony by applying several events."
  ([testimony persps events] (apply-events testimony persps events nil))
  ([testimony persps events type-key]
   (reduce (persps->fn persps type-key) testimony events)))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn build
  "Use perspectives to build a testimony."
  ([persps events] (build persps events nil))
  ([persps events type-key]
   (apply-events nil persps events type-key)))
