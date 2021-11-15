(ns ae.rashomon-test
  (:require [clojure.test :refer :all]
            [ae.rashomon :refer :all]
            [ae.rashomon.event :as event]))

(deftest persps->fn-test
  (testing "persps->fn produces a function that can be used by reduce."
    (let [f (fn [_ e] (* 2 (:value e)))
          event {::event/type :foo :value 45}
          persps {:foo f}
          expected 90
          actual (reduce (persps->fn persps) nil [event])]
      (is (= expected actual)))))

(deftest apply-event-test
  (testing "A single event can be applied."
    (let [testimony 40
          event {::event/type :foo :value 30}
          f (fn [t e] (+ t (:value e)))
          persps {:foo f}
          expected 70
          actual (apply-event testimony persps event)]
      (is (= expected actual))))

  (testing "A single event can be applied with a custom type key."
    (let [testimony 40
          event {:foo.event-type :foo :value 30}
          f (fn [t e] (+ t (:value e)))
          persps {:foo f}
          expected 70
          actual (apply-event testimony persps event :foo.event-type)]
      (is (= expected actual)))))

(deftest apply-events-test
  (testing "Several event can be applied."
    (let [testimony 40
          e1 {::event/type :foo :value 30}
          e2 {::event/type :foo :value 20}
          f (fn [t e] (+ t (:value e)))
          persps {:foo f}
          expected 90
          actual (apply-events testimony persps [e1 e2])]
      (is (= expected actual)))))

(deftest build-test
  (testing "Testimonies can be built from nil."
    (let [e1 {::event/type :foo :value 30}
          e2 {::event/type :foo :value 20}
          f (fn [t e] (+ (or t 0) (:value e)))
          persps {:foo f}
          expected 50
          actual (build persps [e1 e2])]
      (is (= expected actual))))

  (testing "Testimonies can be built from nil whilst specifying a custom event-type key."
    (let [e1 {:foo.event-type :foo :value 30}
          e2 {:foo.event-type :foo :value 20}
          f (fn [t e] (+ (or t 0) (:value e)))
          persps {:foo f}
          expected 50
          actual (build persps [e1 e2] :foo.event-type)]
      (is (= expected actual)))))
