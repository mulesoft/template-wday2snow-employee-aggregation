package org.mule.kicks;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.workday.hr.EffectiveAndUpdatedDateTimeDataType;
import com.workday.hr.GetWorkersRequestType;
import com.workday.hr.TransactionLogCriteriaType;
import com.workday.hr.WorkerRequestCriteriaType;

public class WorkersRequest {

	public static GetWorkersRequestType create(/*Date startDate*/) throws ParseException, DatatypeConfigurationException {
//		Date startDate = new Date(1000000);
//
//		/*
//		 * Set data range for events
//		 */
//		EffectiveAndUpdatedDateTimeDataType dateRangeData = new EffectiveAndUpdatedDateTimeDataType();
//		dateRangeData.setUpdatedFrom(xmlDate(startDate));
//		dateRangeData.setUpdatedThrough(xmlDate(new Date()));
//
//		/*
//		 * Set event type criteria filter
//		 */
//
//		TransactionLogCriteriaType transactionLogCriteria = new TransactionLogCriteriaType();
//		transactionLogCriteria.setTransactionDateRangeData(dateRangeData);
//
//		WorkerRequestCriteriaType workerRequestCriteria = new WorkerRequestCriteriaType();
//		workerRequestCriteria.getTransactionLogCriteriaData().add(transactionLogCriteria);
//
//		GetWorkersRequestType getWorkersType = new GetWorkersRequestType();
//		//getWorkersType.setRequestCriteria(workerRequestCriteria);
//
//		return getWorkersType;
		
		
		
		
		
		
		com.workday.hr.GetWorkersRequestType getWorkersType = new com.workday.hr.GetWorkersRequestType();
		//OPTIONAL instantiate a responsefilter object to set page number
//		com.workday.hr.ResponseFilterType responseFilterType = new com.workday.hr.ResponseFilterType();
		//Set the page number
//		responseFilterType.setPage(new BigDecimal(1));
		//set the response filter in the worker object
//		getWorkersType.setResponseFilter(responseFilterType);
		//return the getworkerrequest object as the message payload
		return getWorkersType; 
		
	}

	private static XMLGregorianCalendar xmlDate(Date date) throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		gregorianCalendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	}

}
