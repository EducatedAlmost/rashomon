(ns ae.rashomon.specs-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [ae.rashomon.specs :as sut]
            [ae.rashomon :as r]
            [ae.rashomon.event :as r.event]))

(deftest event-type-test
  (testing "::r.event/type spec works."
    (is (s/valid? ::r.event/type :foo))))

(deftest event-test
  (testing "::r/event spec works."
    (is (s/valid? ::r/event {::r.event/type :foo}))))

(deftest testimonial-test
  (testing "::r/testimonial spec works."
    (is (s/valid? ::r/testimonial [3 4 5]))
    (is (s/valid? ::r/testimonial {:foo :bar}))))

(deftest persp-test
  (testing "::r/persp fspec works."
    (is (s/valid? `sut/persp (fn [_ _] :foo)))
    (is (not (s/valid? `sut/persp (fn [_] :bar))))))

(deftest persps-test
  (testing "::r/persps spec works."
    (is (s/valid? ::r/persps {:foo (fn [_ _] :foo)
                              :bar (fn [_ _] :bar)}))
    #_(is (not (s/valid? ::r/persps {:foo (fn [_] :qux)})))))
