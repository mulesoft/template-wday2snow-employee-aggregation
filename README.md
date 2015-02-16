
# Anypoint Template: Workday to Service Now Worker Aggregation

+ [License Agreement](#licenseagreement)
+ [Use Case](#usecase)
+ [Considerations](#considerations)
	* [ServiceNow Considerations](#servicenowconsiderations)
	* [Workday Considerations](#workdayconsiderations)
+ [Run it!](#runit)
	* [Running on premise](#runonopremise)
	* [Running on Studio](#runonstudio)
	* [Running on Mule ESB stand alone](#runonmuleesbstandalone)
	* [Running on CloudHub](#runoncloudhub)
	* [Deploying your Anypoint Template on CloudHub](#deployingyouranypointtemplateoncloudhub)
	* [Properties to be configured (With examples)](#propertiestobeconfigured)
+ [API Calls](#apicalls)
+ [Customize It!](#customizeit)
	* [config.xml](#configxml)
	* [businessLogic.xml](#businesslogicxml)
	* [endpoints.xml](#endpointsxml)
	* [errorHandling.xml](#errorhandlingxml)


# License Agreement <a name="licenseagreement"/>
Note that using this template is subject to the conditions of this [License Agreement](AnypointTemplateLicense.pdf).
Please review the terms of the license before downloading and using this template. In short, you are allowed to use the template for free with Mule ESB Enterprise Edition, CloudHub, or as a trial in Anypoint Studio.

# Use Case <a name="usecase"/>
I want to aggregate workers from Workday and ServiceNow Instances and compare them to see which workers can only be found in one of the two and which workers are in both instances. 

For practical purposes this Template will generate the result in the format of a CSV Report sent by email.

This Template should serve as a foundation for extracting data from two systems, aggregating data, comparing values of fields for the objects, and generating a report on the differences. 

As implemented, it gets workers from one instance of Workday and one instance of ServiceNow, compares by the email address of the workers, and generates a CSV file which shows workers in A (WorkDay), workers in B (ServiceNow), and workers in A and B. The report is then e-mailed to the configured e-mail address.

# Considerations <a name="considerations"/>

To make this Anypoint Template run, there are certain preconditions that must be considered. All of them deal with the preparations in both, that must be made in order for all to run smoothly. **Failling to do so could lead to unexpected behavior of the template.**





## ServiceNow Considerations <a name="servicenowconsiderations"/>

There may be a few things that you need to know regarding ServiceNow, in order for this template to work.


### As destination of data

There are no particular considerations for this Anypoint Template regarding ServiceNow as data destination.
## Workday Considerations <a name="workdayconsiderations"/>

### As source of data

There are no particular considerations for this Anypoint Template regarding Workday as data origin.


# Run it! <a name="runit"/>
Simple steps to get Workday to Service Now Worker Aggregation running.


## Running on premise <a name="runonopremise"/>
Complete all properties in one of the property files, for example in [mule.prod.properties] (../blob/master/src/main/resources/mule.prod.properties) and run your app with the corresponding environment variable to use it. To follow the example, this will be `mule.env=prod`.

After this, to trigger the use case you just need to hit the local http endpoint with the port you configured in your file. If this is, for instance, `9090` then you should hit: `http://localhost:9090/generatereport` and this will create a CSV report and send it to the mail set.


### Where to Download Mule Studio and Mule ESB
First thing to know if you are a newcomer to Mule is where to get the tools.

+ You can download Mule Studio from this [Location](http://www.mulesoft.com/platform/mule-studio)
+ You can download Mule ESB from this [Location](http://www.mulesoft.com/platform/soa/mule-esb-open-source-esb)


### Importing an Anypoint Template into Studio
Mule Studio offers several ways to import a project into the workspace, for instance: 

+ Anypoint Studio generated Deployable Archive (.zip)
+ Anypoint Studio Project from External Location
+ Maven-based Mule Project from pom.xml
+ Mule ESB Configuration XML from External Location

You can find a detailed description on how to do so in this [Documentation Page](http://www.mulesoft.org/documentation/display/current/Importing+and+Exporting+in+Studio).


### Running on Studio <a name="runonstudio"/>
Once you have imported you Anypoint Template into Anypoint Studio you need to follow these steps to run it:

+ Locate the properties file `mule.dev.properties`, in src/main/resources
+ Complete all the properties required as per the examples in the section [Properties to be configured](#propertiestobeconfigured)
+ Once that is done, right click on you Anypoint Template project folder 
+ Hover you mouse over `"Run as"`
+ Click on  `"Mule Application"`


### Running on Mule ESB stand alone <a name="runonmuleesbstandalone"/>
Complete all properties in one of the property files, for example in [mule.prod.properties] (../master/src/main/resources/mule.prod.properties) and run your app with the corresponding environment variable to use it. To follow the example, this will be `mule.env=prod`. 


## Running on CloudHub <a name="runoncloudhub"/>
While [creating your application on CloudHub](http://www.mulesoft.org/documentation/display/current/Hello+World+on+CloudHub) (Or you can do it later as a next step), you need to go to Deployment > Advanced to set all environment variables detailed in **Properties to be configured** as well as the **mule.env**.
Once your app is all set and started, supposing you choose as domain name `wday2snowworkeraggregation` to trigger the use case you just need to hit `http://wday2snowworkeraggregation.cloudhub.io/generatereport` and the report will be sent to the e-mails configured.

### Deploying your Anypoint Template on CloudHub <a name="deployingyouranypointtemplateoncloudhub"/>
Mule Studio provides you with really easy way to deploy your Template directly to CloudHub, for the specific steps to do so please check this [link](http://www.mulesoft.org/documentation/display/current/Deploying+Mule+Applications#DeployingMuleApplications-DeploytoCloudHub)


## Properties to be configured (With examples) <a name="propertiestobeconfigured"/>
In order to use this Mule Anypoint Template you need to configure properties (Credentials, configurations, etc.) either in properties file or in CloudHub as Environment Variables. Detail list with examples:
### Application configuration
+ http.port `9090` 
+ wday.page.size `100`

#### WorkDay Connector configuration
+ wday.user `user@mulesoft`
+ wday.password `secret`
+ wday.endpoint `https://impl-cc.workday.com/ccx/service/mulesoft/Human_Resources/v21.1`

#### ServiceNow Connector configuration
+ snow.user `mule.snow`
+ snow.password `secret`
+ snow.endpoint `https://dev107.service-now.com`

#### SMPT Services configuration
+ smtp.host `smtp.gmail.com`
+ smtp.port `587`
+ smtp.user `exampleuser@gmail.com`
+ smtp.password `ExamplePassword456`

#### Mail details
+ mail.from `exampleuser@gmail.com`
+ mail.to `woody.guthrie@gmail.com`
+ mail.subject `Worker aggregation Report`
+ mail.body `Worker aggregation report`
+ attachment.name `WorkerReport.csv`

# API Calls <a name="apicalls"/>
There are no special considerations regarding API calls.


# Customize It!<a name="customizeit"/>
This brief guide intends to give a high level idea of how this Anypoint Template is built and how you can change it according to your needs.
As mule applications are based on XML files, this page will be organized by describing all the XML that conform the Anypoint Template.
Of course more files will be found such as Test Classes and [Mule Application Files](http://www.mulesoft.org/documentation/display/current/Application+Format), but to keep it simple we will focus on the XMLs.

Here is a list of the main XML files you'll find in this application:

* [config.xml](#configxml)
* [endpoints.xml](#endpointsxml)
* [businessLogic.xml](#businesslogicxml)
* [errorHandling.xml](#errorhandlingxml)


## config.xml<a name="configxml"/>
Configuration for Connectors and [Properties Place Holders](http://www.mulesoft.org/documentation/display/current/Configuring+Properties) are set in this file. **Even you can change the configuration here, all parameters that can be modified here are in properties file, and this is the recommended place to do it so.** Of course if you want to do core changes to the logic you will probably need to modify this file.

In the visual editor they can be found on the *Global Element* tab.


## businessLogic.xml<a name="businesslogicxml"/>
Functional aspect of the Template is implemented on this XML, directed by one flow responsible of conducting the aggregation of data, comparing records and finally formating the output, in this case being a report.
The *mainFlow* organises the job in three different steps and finally invokes the *outboundFlow* that will deliver the report to the corresponding outbound endpoint.
This flow has Exception Strategy that basically consists on invoking the *defaultChoiseExceptionStrategy* defined in *errorHandling.xml* file.


### Gather Data Flow
Mainly consisting of two calls (Queries) to Workday and Service Now and aggregating the results.

[Scatter Gather](http://www.mulesoft.org/documentation/display/current/Scatter-Gather) is responsible for aggregating the results from the two collections of Workers.
Criteria and format applied:

+ Scatter Gather component implements an aggregation strategy that results in List of Maps with keys: **Name**, **Email**, **IDInWorkday**, **IDInServiceNow**, **UsernameInWorkday** and **UsernameInServiceNow**. 
+ Workers will be matched by Email, that is to say, a record in both systems with the same Email is considered the same worker.

### workdayRetrievalMapperFlow
Workers are loaded by [Workday connector](http://www.mulesoft.org/documentation/display/current/Workday+Connector) to List of Maps with keys: **Id**, **Name**, **Email** and **Username**.

### snowRetrievalMapperFlow
Users are loaded by [ServiceNow connector](http://www.mulesoft.org/documentation/display/current/ServiceNow+Connector) to List of Maps with keys: **Id**, **Name**, **Email** and **Username**.

### Format Output Flow
[Java Transformer](http://www.mulesoft.org/documentation/display/current/Java+Transformer+Reference) is responsible for sorting the list of workers in the following order:

1. Workers only in Workday
2. Workers only in Service Now
3. Workers in both systems

All records ordered alphabetically by IDs within each category.
If you want to change this order then the *compare* method should be modified.

+ CSV Report [DataMapper](http://www.mulesoft.org/documentation/display/current/Datamapper+User+Guide+and+Reference) transforming the List of Maps in CSV with headers **Name**, **Email**, **IDInWorkday**, **IDInServiceNow**, **UsernameInWorkday** and **UsernameInServiceNow**. 
+ An [Object to string transformer](http://www.mulesoft.org/documentation/display/current/Transformers) is used to set the payload as an String. 

### Outbound Flow
**SMTP Outbound Endpoint** - Send email

+ Both SMTP Server configuration and the actual email to be sent are defined in this endpoint.
+ This flow is going to be invoked from the flow that does all the functional work: *mainFlow*, the same that is invoked from the Inbound Flow upon triggering of the HTTP Endpoint.



## endpoints.xml<a name="endpointsxml"/>
This is the file where you will find the inbound and outbound sides of your integration app.
This Template has an [HTTP Listener Connector](http://www.mulesoft.org/documentation/display/current/HTTP+Listener+Connector) as the way to trigger the use case and an [SMTP Transport](http://www.mulesoft.org/documentation/display/current/SMTP+Transport+Reference) as the outbound way to send the report.

### Trigger Flow
**HTTP Listener Connector** - Start Report Generation

+ `${http.port}` is set as a property to be defined either on a property file or in CloudHub environment variables.
+ The path configured by default is `generatereport` and you are free to change for the one you prefer.
+ The host name for all endpoints in your CloudHub configuration should be defined as `localhost`. CloudHub will then route requests from your application domain URL to the endpoint.



## errorHandling.xml<a name="errorhandlingxml"/>
This is the right place to handle how your integration will react depending on the different exceptions. 
This file holds a [Choice Exception Strategy](http://www.mulesoft.org/documentation/display/current/Choice+Exception+Strategy) that is referenced by the main flow in the business logic.



