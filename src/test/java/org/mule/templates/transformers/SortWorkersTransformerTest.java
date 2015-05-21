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
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class SortWorkersTransformerTest {
	
	private static final Logger log = LogManager.getLogger(SortWorkersTransformerTest.class);
	
	@Mock
	private MuleContext muleContext;

	@Test
	public void testSort() throws TransformerException {

		MuleMessage message = new DefaultMuleMessage(WorkersMergeTest.createExpectedList(), muleContext);

		SortWorkersTransformer transformer = new SortWorkersTransformer();
		List<Map<String, String>> sortedList = (List<Map<String, String>>) transformer.transform(message, "UTF-8");

		log.info(sortedList);
		Assert.assertEquals("The merged list obtained is not as expected", createOriginalList(), sortedList);

	}

	private List<Map<String, String>> createOriginalList() {
		Map<String, String> worker0 = new HashMap<String, String>();
		worker0.put("IDInWorkday", "0");
		worker0.put("IDInServiceNow", "");
		worker0.put("Email", "some.email.0@fakemail.com");
		worker0.put("Name", "SomeName_0");
		worker0.put("UsernameInWorkday", "username_0_A");
		worker0.put("UsernameInServiceNow", "");		

		Map<String, String> worker2 = new HashMap<String, String>();
		worker2.put("IDInWorkday", "");
		worker2.put("IDInServiceNow", "2");
		worker2.put("Email", "some.email.2@fakemail.com");
		worker2.put("Name", "SomeName_2");
		worker2.put("UsernameInWorkday", "");
		worker2.put("UsernameInServiceNow", "username_2_B");

		Map<String, String> worker1 = new HashMap<String, String>();
		worker1.put("IDInWorkday", "1");
		worker1.put("IDInServiceNow", "1");
		worker1.put("Email", "some.email.1@fakemail.com");
		worker1.put("Name", "SomeName_1");
		worker1.put("UsernameInWorkday", "username_1_A");
		worker1.put("UsernameInServiceNow", "username_1_B");
		
		List<Map<String, String>> workerList = new ArrayList<Map<String, String>>();
		workerList.add(worker0);
		workerList.add(worker2);
		workerList.add(worker1);

		return workerList;

	}

}
