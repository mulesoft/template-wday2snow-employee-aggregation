/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.integration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleEvent;
import org.mule.processor.chain.SubflowInterceptingChainLifecycleWrapper;
import org.mule.tck.junit4.rule.DynamicPort;

/**
 * The objective of this class is to validate the correct behavior of the flows for this Mule Template that
 * make calls to external systems.
 *
 * @author aurel.medvegy
 */
public class BusinessLogicIT extends AbstractTemplateTestCase {

	private static final String EMPLOYEES_FROM_ORG_A = "employeesFromOrgA";
	private static final String EMPLOYESS_FROM_ORG_B = "employeesFromOrgB";
	
	protected static final String TEMPLATE_NAME = "employee-aggregation";
	
	@Rule
	public DynamicPort port = new DynamicPort("http.port");
	
	@Test
	public void testGatherDataFlow() throws Exception {
		SubflowInterceptingChainLifecycleWrapper flow = getSubFlow("gatherDataFlow");
		flow.setMuleContext(muleContext);
		flow.initialise();
		flow.start();
		MuleEvent event = flow.process(getTestEvent("", MessageExchangePattern.REQUEST_RESPONSE));
		
		Assert.assertTrue("There should be workers from Workday.", ((ArrayList<Map<String, String>>)event.getFlowVariable(EMPLOYEES_FROM_ORG_A)).size() != 0);
		Assert.assertTrue("There should be users from ServiceNow.", ((ArrayList<Map<String, String>>)event.getFlowVariable(EMPLOYESS_FROM_ORG_B)).size() != 0);

	}

	@Test
	@SuppressWarnings("rawtypes")
	public void testAggregationFlow() throws Exception {
		MuleEvent testEvent = prepareTestEvent();

		SubflowInterceptingChainLifecycleWrapper flow = getSubFlow("aggregationFlow");
		flow.initialise();
		MuleEvent event = flow.process(testEvent);

		Assert.assertTrue("The payload should not be null.", event.getMessage().getPayload() != null);
		Assert.assertFalse("The lead list should not be empty.", ((List) event.getMessage().getPayload()).isEmpty());
	}
	
	@Test
	public void testFormatOutputFlow() throws Exception {		
		MuleEvent testEvent = prepareTestEvent();
		SubflowInterceptingChainLifecycleWrapper flow = getSubFlow("aggregationFlow");
		flow.initialise();
		MuleEvent event = flow.process(testEvent);

		flow = getSubFlow("formatOutputFlow");
		flow.initialise();
		event = flow.process(event);

		Assert.assertTrue("The payload should not be null.", event.getMessage().getPayload() != null);
	}	

	private MuleEvent prepareTestEvent() throws Exception {
		List<Map<String, String>> listA = new ArrayList<Map<String,String>>();
		listA.add(createUser());
		List<Map<String, String>> listB = new ArrayList<Map<String,String>>();
		listB.add(createUser());
		MuleEvent event = getTestEvent("");
		event.getMessage().setInvocationProperty(EMPLOYEES_FROM_ORG_A, listA);
		event.getMessage().setInvocationProperty(EMPLOYESS_FROM_ORG_B, listB);
		return event;
	}

	private Map<String, String> createUser() {
		Map<String, String> user = new HashMap<String, String>();
		long id = System.currentTimeMillis();
		user.put("Name", buildUniqueName(TEMPLATE_NAME, "test" + id));
		user.put("Email", buildUniqueEmail("test" + id));
		user.put("Id", String.valueOf(id));
		return user;
	}	

	private void deleteTestObjectFromSandBox(List<Map<String, Object>> createdEmployees, String deleteFlow) throws Exception {
		List<String> idList = new ArrayList<String>();

		SubflowInterceptingChainLifecycleWrapper flow = getSubFlow(deleteFlow);
		flow.initialise();
		for (Map<String, Object> c : createdEmployees) {
			idList.add((String) c.get("Id"));
		}
		flow.process(getTestEvent(idList, MessageExchangePattern.REQUEST_RESPONSE));
		idList.clear();
	}
	
	private String buildUniqueName(String templateName, String name) {
		String timeStamp = new Long(new Date().getTime()).toString();

		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append(templateName);
		builder.append(timeStamp);

		return builder.toString();
	}
	
	private String buildUniqueEmail(String user) {
		String server = "fakemail";

		StringBuilder builder = new StringBuilder();
		builder.append(buildUniqueName(TEMPLATE_NAME, user));
		builder.append("@");
		builder.append(server);
		builder.append(".com");

		return builder.toString();
	}

}
