(ns ae.rashomon.specs
  (:require
   [clojure.spec.alpha :as s]
   [ae.rashomon :as r]
   [ae.rashomon.event :as r.event]))

(s/def ::r.event/type keyword?)
(s/def ::r/event (s/keys :req [::r.event/type]))
(s/def ::r/testimonial any?)
(s/fdef persp
  :args (s/cat :testimonial ::r/testimonial
               :event ::r/event)
  :ret ::r/testimonial)
(s/def ::r/persps map?)
