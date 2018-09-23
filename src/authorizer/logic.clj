(ns authorizer.logic
  (:require [schema.core :as s]
            [authorizer.model :as model]))

(s/defn over-limit :- model/MaybeDenyReason
  "The transaction amount should not be above limit"
  [amount :- s/Num, account :- model/Account]
  (when (> amount (:limit account))
    :overlimit))

(s/defn blocked :- model/MaybeDenyReason
  "No transaction should be approved when the card is blocked"
  [{status :card-status} :- model/Account]
  (when (= status :blocked)
    :blocked))

(s/defn large-first-purchase :- model/MaybeDenyReason
  "The first transaction shouldn't be above 90% of the limit"
  [{limit :limit last-purchases :last-purchases} :- model/Account
   {amount :amount} :- model/Purchase]
  (when (and (empty? last-purchases) (> amount (* limit 0.9)))
    :large-first-purchase
  ))
