<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="CurrentDay" select="iwrecordset/transaction/datamap[@name='getsftime']/data/row/col[@name='currentday']"/>
		<xsl:variable name="CurrentDay3" select="iwrecordset/transaction/datamap[@name='getsftime3']/data/row/col[@name='currentday3']"/>
		<xsl:variable name="CurrentDay6" select="iwrecordset/transaction/datamap[@name='getsftime6']/data/row/col[@name='currentday6']"/>
		<xsl:variable name="DaysPrevMonth" select="iwrecordset/transaction/datamap[@name='getmonthdays']/data/row/col[@name='monthday']"/>
		<xsl:variable name="Q0">
			<xsl:if test="$CurrentDay='01'">
				<xsl:choose>
					<xsl:when test="$DaysPrevMonth=28">or Card_Bills_On_2__c like '29%' or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=29"> or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=30"> or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="Q1">
			<xsl:if test="$CurrentDay='04'">
				<xsl:choose>
					<xsl:when test="$DaysPrevMonth=28">or Card_Bills_On_2__c like '29%' or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=29"> or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=30"> or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="Q2">
			<xsl:if test="$CurrentDay='07'">
				<xsl:choose>
					<xsl:when test="$DaysPrevMonth=28">or Card_Bills_On_2__c like '29%' or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=29"> or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=30"> or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="SessionId" select="iwrecordset/transaction[@name='SFLogin_CM']/datamap[@name='login']/data/row/col[@name='sessionId']"/>
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
					<queryString>Select Id, Name, Gross_Monthly_Charge__c, Credit_Card_Client__c, CC_Exp_Date_00_00__c, FirstName, LastName, Number_of_Declines__c, BillingPostalCode, Total_Balance__c from Account where Last_Authorized_Payment_Date__c!=TODAY and PersonHasOptedOutOfEmail=false and ACH__c=false and Sent_To_Mont__c=false and (Client_Status__c='Active' or Client_Status__c='Cancel No Pay') and Special_Billing_2__c=false and (Card_Bills_On_2__c like '<xsl:value-of select="$CurrentDay"/>%'<xsl:value-of select="$Q0"/> or (Number_of_Declines__c=1 and (Card_Bills_On_2__c like '<xsl:value-of select="$CurrentDay3"/>%'<xsl:value-of select="$Q1"/>)) or (Number_of_Declines__c=2 and (Card_Bills_On_2__c like '<xsl:value-of select="$CurrentDay6"/>%'<xsl:value-of select="$Q2"/>))) and Paid_By_Check__pc=false
						</queryString>
				</query>
			</soap:Body>
		</soap:Envelope>
	</xsl:template>
</xsl:stylesheet>
