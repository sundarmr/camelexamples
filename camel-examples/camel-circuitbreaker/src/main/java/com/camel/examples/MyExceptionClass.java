package com.camel.examples;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyExceptionClass implements Processor {
	Logger Logger = LoggerFactory.getLogger(MyExceptionClass.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		throw new MyException("Other Exceptions");
	}
}
