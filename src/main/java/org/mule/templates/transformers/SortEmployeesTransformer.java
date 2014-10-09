package org.mule.templates.transformers;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

/**
 * This transformer will take to list as input and create a third one that will be the merge of the previous two. The identity of an element of the list is
 * defined by its email.
 * 
 * @author
 */
public class SortEmployeesTransformer extends AbstractMessageTransformer {

	public static Comparator<Map<String, String>> recordComparator = new Comparator<Map<String, String>>() {

		public int compare(Map<String, String> employee1, Map<String, String> employee2) {

			String key1 = buildKey(employee1);
			String key2 = buildKey(employee2);

			return key1.compareTo(key2);

		}

		private String buildKey(Map<String, String> employee) {
			StringBuilder key = new StringBuilder();

			if (StringUtils.isNotBlank(employee.get("IDInA")) && StringUtils.isNotBlank(employee.get("IDInB"))) {
				key.append("~~");
				key.append(employee.get("IDInA"));
				key.append(employee.get("IDInB"));
				key.append(employee.get("Email"));
			}

			if (StringUtils.isNotBlank(employee.get("IDInA")) && StringUtils.isBlank(employee.get("IDInB"))) {
				key.append(employee.get("IDInA"));
				key.append("~");
				key.append(employee.get("Email"));
			}

			if (StringUtils.isBlank(employee.get("IDInA")) && StringUtils.isNotBlank(employee.get("IDInB"))) {
				key.append("~");
				key.append(employee.get("IDInB"));
				key.append(employee.get("Email"));
			}

			return key.toString();
		}

	};

	@SuppressWarnings("unchecked")
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		List<Map<String, String>> sortedemployeesList = (List<Map<String, String>>) message.getPayload();

		Collections.sort(sortedemployeesList, recordComparator);

		return sortedemployeesList;

	}

}
