package com.camel.failover.db.policy;

import java.util.HashSet;
import java.util.Set;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.spi.RoutePolicy;
import org.apache.camel.util.ServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.camel.failover.db.MyAppDbLocker;

public class TimerPolicy implements RoutePolicy{
	
	protected MyAppDbLocker dbLocker;
	public MyAppDbLocker getDbLocker() {
		return dbLocker;
	}

	public void setDbLocker(MyAppDbLocker dbLocker) {
		this.dbLocker = dbLocker;
	}

	Logger LOG = LoggerFactory.getLogger(TimerPolicy.class);

	@Override
	public void onInit(Route route) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemove(Route route) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(Route route) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStop(Route route) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuspend(Route route) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResume(Route route) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExchangeBegin(Route route, Exchange exchange) {
		try{
			LOG.info("Checking for lease lock : if it is still with us .."+dbLocker.isLeaseOwner());
		if(dbLocker.isLeaseOwner()){
			Consumer consumer = route.getRouteContext().getCamelContext().getRoute("appRoute").getConsumer();
			LOG.info("Application Route Consumer "+consumer);
			if(consumer!=null){
				LOG.debug("Starting Route Consumer "+consumer);
				ServiceHelper.startService(consumer);
			}
					
		}else{
			Consumer consumer = route.getRouteContext().getCamelContext().getRoute("appRoute").getConsumer();
			Set<Consumer> consumers = new HashSet<Consumer>();
			if(consumer!=null){
				LOG.info("Stopping Route Consumer "+consumer);
				consumers.add(consumer);
				
				boolean suspendService = ServiceHelper.suspendService(consumer);
				LOG.info("Consumer is Stopped ? "+suspendService);
				
			}else{
				LOG.info("No consumer available to stop");
			}
		}
		}catch(Exception e){
			LOG.error("the error message is :"+e.getMessage());
			Consumer consumer = route.getRouteContext().getCamelContext().getRoute("appRoute").getConsumer();
			Set<Consumer> consumers = new HashSet<Consumer>();
			if(consumer!=null){
				LOG.error("Stopping Route Consumer "+consumer);
				consumers.add(consumer);
				try {
					ServiceHelper.suspendService(consumer);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onExchangeDone(Route route, Exchange exchange) {
		// TODO Auto-generated method stub
		
	}

}
