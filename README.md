## Objective
Implement a function that authorizes a transaction for a specific account, following some predefined rules.

## Input
You'll receive as input: the account data (card status and current available limit), latest approved transactions and the current transaction to be approved.

## Output
The output should consist of whether the purchase was authorized or not, the updated available limit and, when relevant, all of the reasons why the transaction was denied.

## Rules
You should choose a few rules to implement a fully working initial version, then you can expand the solution with other rules.

1. The transaction amount should not be above limit
2. No transaction should be approved when the card is blocked
3. The first transaction shouldn't be above 90% of the limit
4. There should not be more than 10 transactions on the same merchant
5. Merchant Blacklist
6. There should not be more than 3 purchases on a 2 minutes interval
7. There should not be more than 2 similar purchases (same amount and merchant) in a 2 minutes interval
8. Account whitelist (should only check available limit and card status - 1 and 2)

## Schema
```clojure
(def Purchase {:merchant s/Str :amount s/Num :time s/Inst})

(s/defn authorize! :- {:approved s/Bool :new-limit s/Num :denied-reasons [s/Keyword]}
   [account :- {:card-status (s/enum :active :blocked) :limit s/Num :blacklist [s/Str] :whitelisted? s/Bool :last-purchases [Purchase]}
   purchase :- Purchase]
  ...)
```
