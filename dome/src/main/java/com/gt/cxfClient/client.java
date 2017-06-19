package com.gt.cxfClient;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class client {
	public static void main(String arg[]){
		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();

		Client client = clientFactory.createClient("http://localhost:8080/erp/webservice/HelloWorld?wsdl");

		Object[] result = null;
		try {
			result = client.invoke("sayHello", "KEVIN","");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(result[0]);
	}

}
