/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.routing.AggregationContext;
import org.mule.templates.integration.AbstractTemplateTestCase;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class WorkersMergeAggregationStrategyTest extends AbstractTemplateTestCase {
	
	@Mock
	private MuleContext muleContext;
  
	
	@Test
	public void testAggregate() throws Exception {
		List<Map<String, String>> workersA = WorkersMergeTest.createWorkerLists("A", 0, 1);
		List<Map<String, String>> workersB = WorkersMergeTest.createWorkerLists("B", 1, 2);
		
		MuleEvent testEventA = getTestEvent("");
		MuleEvent testEventB = getTestEvent("");
		
		testEventA.getMessage().setPayload(workersA);
		testEventB.getMessage().setPayload(workersB);
		
		List<MuleEvent> testEvents = new ArrayList<MuleEvent>();
		testEvents.add(testEventA);
		testEvents.add(testEventB);
		
		AggregationContext aggregationContext = new AggregationContext(getTestEvent(""), testEvents);
		
		WorkersMergeAggregationStrategy workersMergeAggregationStrategy = new WorkersMergeAggregationStrategy();
		List<Map<String, String>> mergedList = (List<Map<String, String>>) workersMergeAggregationStrategy.aggregate(aggregationContext).getMessage().getPayload();
		
		Assert.assertEquals("The merged list obtained is not as expected", WorkersMergeTest.createExpectedList(), mergedList);

	}
	
}
