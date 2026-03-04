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
					<queryString>Select Id, Name, Gross_Monthly_Charge__c, Credit_Card_Client__c, CC_Exp_Date_00_00__c, First_Name__c, Last_Name__c, Number_of_Declines__c, ACH__c, Billing_Postal_Code__c, Billing_Company_Street__c, Billing_City__c, Billing_State__c, Billing_Country__c, Billing_Email__c, CVM_Value__c, Arrival_Date__c, Departure_Date__c, Guest_Name__r.Name, Guest_Name__r.BillingCity, Guest_Name__r.BillingCountry, Guest_Name__r.BillingPostalCode, Guest_Name__r.BillingState, Guest_Name__r.BillingStreet, Guest_Name__r.Phone, Client_Acct__c, Routing__c, Account_Type__c, Total_Balance__c, Bank_Name__c, Echeck_Type__c, Bank_Check_Number__c from Reservation__c where Last_Authorized_Payment_Date__c!=TODAY and Id=?
						</queryString>
				</query>
			</soap:Body>
		</soap:Envelope>
	</xsl:template>
</xsl:stylesheet>