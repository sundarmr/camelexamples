package com.camel.sql.examples;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.model.order.Order;

public class ConvertToMap implements Processor{

	@Override
	public void process(Exchange arg0) throws Exception {
		Order body = arg0.getIn().getBody(Order.class);
		Map<String,Object> obj= new HashMap<String,Object>();
		obj.put("orderid", body.getOrderid());
		obj.put("productid",body.getProductid());
		obj.put("lastName",body.getLastName());
		obj.put("firstName", body.getFirstName());
		obj.put("productDescription", body.getProductDescription());
		obj.put("customerId", body.getCustomerId());
		obj.put("productName", body.getProductName());
		arg0.getOut().setBody(obj);
		
	}

}
