<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
			<soap:Header>
				<QueryOptions xmlns="urn:partner.soap.sforce.com">
					<batchSize>2000</batchSize>
				</QueryOptions>
				<SessionHeader xmlns="urn:partner.soap.sforce.com">
					<sessionId>
						<xsl:value-of select="$SessionId"/>
					</sessionId>
				</SessionHeader>
				<CallOptions xmlns="urn:partner.soap.sforce.com">
					<client>IntegrationTechnologies/Interweave/</client>
				</CallOptions>
			</soap:Header>
			<soap:Body>
				<query xmlns="urn:partner.soap.sforce.com">
					<queryString>Select Id, Name, Gross_Monthly_Charge__c, Credit_Card_Client__c, CC_Exp_Date_00_00__c, FirstName, LastName, Number_of_Declines__c, ACH__c, BillingPostalCode, BillingStreet, BillingCity, BillingState, BillingCountry, Phone, PersonEmail, Client_Acct__c, Routing__c, Account_Type__c, Total_Balance__c from Account where Name=? and PersonHasOptedOutOfEmail=false and ACH__c=true and (Client_Status__c='Active' or Client_Status__c='Cancel No Pay') and Special_Billing_2__c=false and ((Source_Location__c='ORLANDO') or ((Source_Location__c='PGA') and  (Gross_Monthly_Charge__c &gt; 500))) and Paid_By_Check__pc=false 
						</queryString>
				</query>
			</soap:Body>
		</soap:Envelope>
	</xsl:template>
</xsl:stylesheet>