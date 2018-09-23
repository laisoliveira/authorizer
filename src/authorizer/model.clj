(ns authorizer.model
  (:require [schema.core :as s]))

(s/defschema DenyReason
  (s/enum :blocked :overlimit :large-first-purchase))

(s/defschema MaybeDenyReason
  (s/maybe DenyReason))

(s/defschema Purchase
  {:merchant s/Str
   :amount   s/Num
   :time     s/Inst})

(s/defschema AuthorizationResult
  {:approved       s/Bool
   :new-limit      s/Num
   :denied-reasons [DenyReason]})

(s/defschema Account
  {:card-status    (s/enum :active :blocked)
   :limit          s/Num
   :blacklist      [s/Str]
   :whitelisted?   s/Bool
   :last-purchases [Purchase]})
