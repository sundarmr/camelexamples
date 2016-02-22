package com.camel.examples.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class MultipleFromRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("timer:foo?repeatCount=1&delay=1000")
		.routeId("firstroute")
		.setBody(simple("sundar"))
		.to("direct:a")
		.end();

		from("timer:foo1?repeatCount=1&delay=1000")
		.routeId("secondRoute")
		.setBody(simple("sundar1"))
		.to("direct:a")
		.end();
		
		from("timer:foo2?repeatCount=1&delay=1000")
		.routeId("thirdRoute")
		.setBody(simple("sundar2"))
		.to("direct:a")
		.end();

		from("direct:a")
				.aggregate(new AggregationStrategy() {

					@Override
					public Exchange aggregate(Exchange arg0, Exchange arg1) {
						Exchange argReturn = null;
						if (arg0 == null) {
							argReturn= arg1;
						}
						if (arg1 == null) {
							argReturn= arg0;
						}
						if (arg1 != null && arg0 != null) {
							try {
								String arg1Str = arg1.getIn()
										.getMandatoryBody().toString();
								String arg2Str = arg0.getIn()
										.getMandatoryBody().toString();
								System.out.println(arg2Str+arg1Str );
								arg1.getIn().setBody(arg2Str+arg1Str);
							} catch (Exception e) {
								e.printStackTrace();
							}
							argReturn= arg1;
						}
						return argReturn;
					}
				}).constant(true).completionSize(3)
				.to("direct:b").end();

		from("direct:b")
		.to("log:sundarLog?showAll=true&multiline=true")
		.end();
	}

}
