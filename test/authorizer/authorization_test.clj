(ns authorizer.authorization-test
  (:require [midje.sweet :refer :all]
            [schema.core :as s]
            [authorizer.authorization :refer :all]))

(def account
  {:card-status    :active
   :limit          20.02
   :blacklist      ["Oxxo"]
   :whitelisted?   false
   :last-purchases []})

(def purchase
  {:merchant "Oxxo"
   :amount   2.2
   :time     #inst "2018-09-09T12:00:00"})

(s/with-fn-validation
  (fact "we authorize purchases when there is available limit"
    (authorize! account purchase) => (contains {:approved true}))

  (fact "we don't authorize when the amount is greater than the available limit"
    (authorize! account (assoc purchase :amount 50.0))
    => (contains {:approved false, :denied-reasons [:overlimit :large-first-purchase]}))

  (fact "we don't authorize when the card is blocked"
    (authorize! (assoc account :card-status :blocked) purchase)
    => (contains {:approved false, :denied-reasons [:blocked]}))

  (fact "we don't authorize when card is blocked and there is no available limit"
    (authorize! (assoc account :card-status :blocked)
                (assoc purchase :amount 50.0))
    => (contains {:approved false, :denied-reasons [:blocked :overlimit :large-first-purchase]})))
