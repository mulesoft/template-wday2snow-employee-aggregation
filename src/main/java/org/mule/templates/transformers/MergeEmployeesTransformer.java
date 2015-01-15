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
public class MergeEmployeesTransformer extends AbstractMessageTransformer {

	private static final String QUERY_COMPANY_A = "employeesFromOrgA";
	private static final String QUERY_COMPANY_B = "employeesFromOrgB";

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		List<Map<String, String>> mergedEmployeeList = mergeList(getEmployeessList(message, QUERY_COMPANY_A), getEmployeessList(message, QUERY_COMPANY_B));

		return mergedEmployeeList;
	}

	private List<Map<String, String>> getEmployeessList(MuleMessage message, String propertyName) {
		return message.<List<Map<String, String>>>getInvocationProperty(propertyName);
	}

	/**
	 * The method will merge the accounts from the two lists creating a new one.
	 * 
	 * @param employeesFromOrgA
	 *            employees from organization A
	 * @param employeesFromOrgB
	 *            employees from organization B
	 * @return a list with the merged content of the to input lists
	 */
	private List<Map<String, String>> mergeList(List<Map<String, String>> employeesFromOrgA, List<Map<String, String>> employeesFromOrgB) {
		try{
			List<Map<String, String>> mergedEmployeesList = new ArrayList<Map<String, String>>();
	
			// Put all employees from A in the merged mergedEmployeeList
			for (Map<String, String> employeeFromA : employeesFromOrgA) {
				Map<String, String> mergedEmployee = createMergedEmployee(employeeFromA);
				mergedEmployee.put("IDInA", employeeFromA.get("Id"));
				mergedEmployeesList.add(mergedEmployee);
			}
	
			// Add the new employees from B and update the exiting ones
			for (Map<String, String> employeeFromB : employeesFromOrgB) {
				Map<String, String> employeeFromA = findEmployeeInList(employeeFromB.get("Email"), mergedEmployeesList);
				if (employeeFromA != null) {
					employeeFromA.put("IDInB", employeeFromB.get("Id"));
				} else {
					Map<String, String> mergedEmployee = createMergedEmployee(employeeFromB);
					mergedEmployee.put("IDInB", employeeFromB.get("Id"));
					mergedEmployeesList.add(mergedEmployee);
				}
	
			}
			return mergedEmployeesList;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private Map<String, String> createMergedEmployee(Map<String, String> employee) {
		Map<String, String> mergedEmployee = new HashMap<String, String>();
		mergedEmployee.put("Name", employee.get("Name"));
		mergedEmployee.put("Email", employee.get("Email"));
		mergedEmployee.put("IDInA", "");
		mergedEmployee.put("IDInB", "");
		return mergedEmployee;
	}

	private Map<String, String> findEmployeeInList(String employeeName, List<Map<String, String>> orgList) {
		for (Map<String, String> emp : orgList) {
			if (emp.get("Email") != null && emp.get("Email")
						.equals(employeeName)) {
				return emp;
			}
		}
		return null;
	}
}

