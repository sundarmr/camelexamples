package com.camel.sql.examples;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AbstractListAggregationStrategy;


public class ListAggregationStrategy extends AbstractListAggregationStrategy {

	@Override
	public Object getValue(Exchange arg0) {
		// TODO Auto-generated method stub
		return arg0.getIn().getBody(Map.class);
	}

}
