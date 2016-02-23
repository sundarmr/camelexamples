Combine ErrorHandler and CircuitBreaker
==========================================
This Example combines the CircuitBreaker of LoadBalancer with the default error handler.

If a Route throws an exception the retry handler tries to redeliver the message x times before it fails ,The x number of failures will now count as one for threshold calculation of CircuitBreaker.The entire route is  encapsulated in the CircuitBreaker with a threshold of 2 , so the behavior expected is as below.

The message gets routed via  the same route 6 times ( 2 times with a retyr of 3 for each pass ) before circuitbreaker opens and from this time onwards , we expect that the message will be sent once ( which will ofcourse be retried 3 times ) before the circuitbreaker closes after the arrival of a correct response (for eas the example does not send a correct response).

