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
					<queryString>Select Id, Name, Monthly_Charge__c, Credit_Card_Client__c, CVV__c, CC_EXP_Date_00_00__c, FirstName, LastName, Reason_s_For_Denial__c, Authorization_Attempts__c, PostalCode, Street, City, State, Country, Phone, Email, ACCOUNT__c, Routing__c, Account_Type__c, Birthdate__c from Lead where Id=?
						</queryString>
				</query>
			</soap:Body>
		</soap:Envelope>
	</xsl:template>
</xsl:stylesheet>
