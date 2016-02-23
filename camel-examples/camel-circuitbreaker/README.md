Combine ErrorHandler and CircuitBreaker
==========================================
This Example combines the CircuitBreaker of LoadBalancer with the default error handler , the use cases is as below

The use case is simple demonstration 

If a Route throws an exception the retry handler tries to redeliver the message three times before it fails , this entire route is now encapsulated in the CircuitBreaker with a threshold of 2 , so the behavior expected is that 

The message gets routed via  the same route 6 times before circuitbreaker opens and from this time onwards , we expect that the message will be sent once ( which will ofcourse be retried 3 times ) before the circuitbreaker closes after the arrival of a correct response (for eas the example does not send a correct response).

