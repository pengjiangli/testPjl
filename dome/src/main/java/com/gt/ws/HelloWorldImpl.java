package com.gt.ws;

import javax.jws.WebService;

@WebService(endpointInterface="com.gt.ws.HelloWorld",serviceName="helloWorld") 
public class HelloWorldImpl implements HelloWorld {

	@Override
	public String sayHello(String username) {
		 System.out.println("sayHello() is called");  
	        return username +" helloWorld";
	}

}
