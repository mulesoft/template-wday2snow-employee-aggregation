package org.mule.templates;

import java.math.BigDecimal;

import com.workday.hr.GetWorkersRequestType;
import com.workday.hr.ResponseFilterType;
import com.workday.hr.WorkerRequestCriteriaType;

public class WorkerRequest {

	public static GetWorkersRequestType createRequest(long pageSize){
		GetWorkersRequestType get = new GetWorkersRequestType();
		WorkerRequestCriteriaType reqCriteria = new WorkerRequestCriteriaType();
		reqCriteria.setExcludeInactiveWorkers(true);
		get.setRequestCriteria(reqCriteria);
		
		ResponseFilterType filter = new ResponseFilterType();
		filter.setCount(BigDecimal.valueOf(pageSize));		
		get.setResponseFilter(filter );
		return get ;
	}
}
