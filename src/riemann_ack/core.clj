(ns riemann-ack.core
	(:require
		[taoensso.carmine :as car :refer (wcar)]
		[riemann.streams       :refer [smap call-rescue]]
	)
)

(def server1-conn {:pool {} :spec {:host "redis"}}) ; See `wcar` docstring for opts
(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))


(defn acked
  [{:keys [host service tags] :as event}]
	(def check (str 'ack- host '- service))
	(def ack (wcar* (car/get check)))
  (if (= ack "1")
    (update-in event [:tags] #(-> % (conj "acked") set))
		event
	)
)

(defn alert-stream [& children]
	(fn [e] (let [new-event (acked e)]
		(call-rescue new-event children)
	))
)
