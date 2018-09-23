(ns authorizer.authorization
  (:require [schema.core :as s]
            [authorizer.model :as model]
            [authorizer.logic :as logic]))

(s/defn authorize! :- model/AuthorizationResult
  [account :- model/Account
   purchase :- model/Purchase]
  (let [deny-reasons (remove nil? [(logic/blocked account)
                                   (logic/over-limit (:amount purchase) account)
                                   (logic/large-first-purchase account purchase)])]
    (if (not (empty? deny-reasons))
      {:approved       false
       :new-limit      18.02
       :denied-reasons deny-reasons}
      {:approved       true
       :new-limit      18.02
       :denied-reasons []})))
