/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mule.api.MuleContext;
import org.mule.api.transformer.TransformerException;

@RunWith(MockitoJUnitRunner.class)
public class WorkersMergeTest {
	
	private static final Logger LOGGER = LogManager.getLogger(WorkersMergeTest.class);

	@Mock
	private MuleContext muleContext;

	@Test
	public void testMerge() throws TransformerException {
		List<Map<String, String>> workersA = createWorkerLists("A", 0, 1);
		List<Map<String, String>> workersB = createWorkerLists("B", 1, 2);
		
		WorkersMerge workersMerge = new WorkersMerge();
		List<Map<String, String>> mergedList = workersMerge.mergeList(workersA, workersB);

		LOGGER.info(mergedList);
		Assert.assertEquals("The merged list obtained is not as expected", createExpectedList(), mergedList);

	}

	static List<Map<String, String>> createExpectedList() {
		Map<String, String> worker0 = new HashMap<String, String>();
		worker0.put("IDInWorkday", "0");
		worker0.put("IDInServiceNow", "");
		worker0.put("Email", "some.email.0@fakemail.com");
		worker0.put("Name", "SomeName_0");
		worker0.put("UsernameInWorkday", "username_0_A");
		worker0.put("UsernameInServiceNow", "");

		Map<String, String> worker1 = new HashMap<String, String>();
		worker1.put("IDInWorkday", "1");
		worker1.put("IDInServiceNow", "1");
		worker1.put("Email", "some.email.1@fakemail.com");
		worker1.put("Name", "SomeName_1");
		worker1.put("UsernameInWorkday", "username_1_A");
		worker1.put("UsernameInServiceNow", "username_1_B");

		Map<String, String> worker2 = new HashMap<String, String>();
		worker2.put("IDInWorkday", "");
		worker2.put("IDInServiceNow", "2");
		worker2.put("Email", "some.email.2@fakemail.com");
		worker2.put("Name", "SomeName_2");
		worker2.put("UsernameInWorkday", "");
		worker2.put("UsernameInServiceNow", "username_2_B");

		List<Map<String, String>> workerList = new ArrayList<Map<String, String>>();
		workerList.add(worker0);
		workerList.add(worker1);
		workerList.add(worker2);

		return workerList;

	}

	static List<Map<String, String>> createWorkerLists(String orgId, int start, int end) {
		List<Map<String, String>> workerList = new ArrayList<Map<String, String>>();
		for (int i = start; i <= end; i++) {
			workerList.add(createWorker(orgId, i));
		}
		return workerList;
	}

	static Map<String, String> createWorker(String orgId, int sequence) {
		Map<String, String> worker = new HashMap<String, String>();

		worker.put("Id", new Integer(sequence).toString());
		worker.put("Username", "username_" + sequence + "_" + orgId);
		worker.put("Name", "SomeName_" + sequence);
		worker.put("FirstName", "SomeName_" + sequence);
		worker.put("Email", "some.email." + sequence + "@fakemail.com");

		return worker;
	}
}
