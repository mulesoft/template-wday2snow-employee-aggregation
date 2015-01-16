/**
 * Mule Anypoint Template
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

/**
 * This transformer will take to list as input and create a third one that will be the merge of the previous two. The identity of an element of the list is
 * defined by its email.
 * 
 * @author cesar.garcia
 */
public class MergeWorkersTransformer extends AbstractMessageTransformer {

	private static final String QUERY_COMPANY_A = "workersFromOrgA";
	private static final String QUERY_COMPANY_B = "workersFromOrgB";

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		List<Map<String, String>> mergedWorkerList = mergeList(getWorkerList(message, QUERY_COMPANY_A), getWorkerList(message, QUERY_COMPANY_B));

		return mergedWorkerList;
	}

	private List<Map<String, String>> getWorkerList(MuleMessage message, String propertyName) {
		return message.<List<Map<String, String>>>getInvocationProperty(propertyName);
	}

	/**
	 * The method will merge the accounts from the two lists creating a new one.
	 * 
	 * @param workersFromOrgA
	 *            workers from organization A
	 * @param workersFromOrgB
	 *            workers from organization B
	 * @return a list with the merged content of the to input lists
	 */
	private List<Map<String, String>> mergeList(List<Map<String, String>> workersFromOrgA, List<Map<String, String>> workersFromOrgB) {
		try{
			List<Map<String, String>> mergedWorkersList = new ArrayList<Map<String, String>>();
	
			// Put all workers from A in the merged mergedWorkersList
			for (Map<String, String> workerFromA : workersFromOrgA) {
				Map<String, String> mergedWorker = createMergedWorker(workerFromA);
				mergedWorker.put("IDInA", workerFromA.get("Id"));
				mergedWorkersList.add(mergedWorker);
			}
	
			// Add the new workers from B and update the exiting ones
			for (Map<String, String> workerFromB : workersFromOrgB) {
				Map<String, String> workersFromA = findWorkerInList(workerFromB.get("Email"), mergedWorkersList);
				if (workersFromA != null) {
					workersFromA.put("IDInB", workerFromB.get("Id"));
				} else {
					Map<String, String> mergedWorker = createMergedWorker(workerFromB);
					mergedWorker.put("IDInB", workerFromB.get("Id"));
					mergedWorkersList.add(mergedWorker);
				}
	
			}
			return mergedWorkersList;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private Map<String, String> createMergedWorker(Map<String, String> worker) {
		Map<String, String> mergedWorker = new HashMap<String, String>();
		mergedWorker.put("Name", worker.get("Name"));
		mergedWorker.put("Email", worker.get("Email"));
		mergedWorker.put("IDInA", "");
		mergedWorker.put("IDInB", "");
		return mergedWorker;
	}

	private Map<String, String> findWorkerInList(String name, List<Map<String, String>> orgList) {
		for (Map<String, String> obj : orgList) {
			if (obj.get("Email") != null && obj.get("Email")
						.equals(name)) {
				return obj;
			}
		}
		return null;
	}
}

