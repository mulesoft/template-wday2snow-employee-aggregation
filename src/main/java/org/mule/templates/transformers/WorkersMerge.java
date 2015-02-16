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

/**
 * This class will take two lists as input and create a third one that
 * will be the merge of the previous two. The identity of an element of the list is
 * defined by its email.
 * 
 * @author cesar.garcia
 */
public class WorkersMerge {

	/**
	 * The method will merge the workers from the two lists creating a new one.
	 * 
	 * @param workersFromOrgA
	 *            workers from organization A
	 * @param workersFromOrgB
	 *            workers from organization B
	 * @return a list with the merged content of the to input lists
	 */
	public List<Map<String, String>> mergeList(List<Map<String, String>> workersFromOrgA, List<Map<String, String>> workersFromOrgB) {
		try{
			List<Map<String, String>> mergedWorkersList = new ArrayList<Map<String, String>>();
	
			// Put all workers from A in the merged mergedWorkersList
			for (Map<String, String> workerFromA : workersFromOrgA) {
				Map<String, String> mergedWorker = createMergedWorker(workerFromA);
				mergedWorker.put("IDInWorkday", workerFromA.get("Id"));
				mergedWorker.put("UsernameInWorkday", workerFromA.get("Username"));
				mergedWorkersList.add(mergedWorker);
			}
	
			// Add the new workers from B and update the exiting ones
			for (Map<String, String> workerFromB : workersFromOrgB) {
				Map<String, String> workersFromA = findWorkerInList(workerFromB.get("Email"), mergedWorkersList);
				if (workersFromA != null) {
					workersFromA.put("IDInServiceNow", workerFromB.get("Id"));
					workersFromA.put("UsernameInServiceNow", workerFromB.get("Username"));
				} else {
					Map<String, String> mergedWorker = createMergedWorker(workerFromB);
					mergedWorker.put("IDInServiceNow", workerFromB.get("Id"));
					mergedWorker.put("UsernameInServiceNow", workerFromB.get("Username"));
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
		mergedWorker.put("IDInWorkday", "");
		mergedWorker.put("IDInServiceNow", "");
		mergedWorker.put("UsernameInWorkday", "");
		mergedWorker.put("UsernameInServiceNow", "");
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

