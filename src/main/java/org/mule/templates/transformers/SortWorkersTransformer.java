/**
 * Mule Anypoint Template
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 */

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
public class SortWorkersTransformer extends AbstractMessageTransformer {

	public static Comparator<Map<String, String>> recordComparator = new Comparator<Map<String, String>>() {

		public int compare(Map<String, String> worker1, Map<String, String> worker2) {

			String key1 = buildKey(worker1);
			String key2 = buildKey(worker2);

			return key1.compareTo(key2);

		}

		private String buildKey(Map<String, String> worker) {
			StringBuilder key = new StringBuilder();

			if (StringUtils.isNotBlank(worker.get("IDInWorkday")) && StringUtils.isNotBlank(worker.get("IDInServiceNow"))) {
				key.append("~~");
				key.append(worker.get("IDInWorkday"));
				key.append(worker.get("IDInServiceNow"));
				key.append(worker.get("Email"));
			}

			if (StringUtils.isNotBlank(worker.get("IDInWorkday")) && StringUtils.isBlank(worker.get("IDInServiceNow"))) {
				key.append(worker.get("IDInWorkday"));
				key.append("~");
				key.append(worker.get("Email"));
			}

			if (StringUtils.isBlank(worker.get("IDInWorkday")) && StringUtils.isNotBlank(worker.get("IDInServiceNow"))) {
				key.append("~");
				key.append(worker.get("IDInServiceNow"));
				key.append(worker.get("Email"));
			}

			return key.toString();
		}

	};

	@SuppressWarnings("unchecked")
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		List<Map<String, String>> sortedList = (List<Map<String, String>>) message.getPayload();

		Collections.sort(sortedList, recordComparator);

		return sortedList;

	}

}
