<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/row">
			<xsl:variable name="SFId0" select="col[@name='ReferenceNumber']"/>
			<xsl:variable name="SFId">
				<xsl:choose>
					<xsl:when test="$SFId0!=''">
						<xsl:value-of select="$SFId0"/>
					</xsl:when>
					<xsl:otherwise>%P(SFId)%</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="R1" select="col[@name='Success']"/>
			<xsl:variable name="R11">ECHECK</xsl:variable>
			<xsl:variable name="R70" select="col[@name='TransID']"/>
			<xsl:variable name="R7">
				<xsl:choose>
					<xsl:when test="$R70!=''">
						<xsl:value-of select="$R70"/>
					</xsl:when>
					<xsl:otherwise>%P(TransactionId)%</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="AppStatus">
				<xsl:choose>
					<xsl:when test="number($R1)=1">Approved</xsl:when>
					<xsl:when test="number($R1)=0">Declined</xsl:when>
					<xsl:when test="number($R1)=3">Cancelled</xsl:when>
					<xsl:otherwise>Error</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="DeclCom">
				<xsl:choose>
					<xsl:when test="number($R1)=1"/>
					<xsl:otherwise>
						<xsl:value-of select="col[@name='Message']"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="DueOver">0</xsl:variable>
			<xsl:variable name="TranStat">Send</xsl:variable>
			<xsl:variable name="PayType">Refund</xsl:variable>
			<xsl:variable name="TranType">ACH</xsl:variable>
			<xsl:variable name="R10" select="col[@name='Amount']"/>
			<xsl:variable name="RoutingBank">XP ACH</xsl:variable>
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
					<create xmlns="urn:partner.soap.sforce.com">
						<sObjects>
							<type xmlns="urn:sobject.partner.soap.sforce.com">Transaction__c</type>
							<Approval_Status__c>
								<xsl:value-of select="$AppStatus"/>
							</Approval_Status__c>
							<Auth_net_Transaction_ID__c>
								<xsl:value-of select="$R7"/>
							</Auth_net_Transaction_ID__c>
							<Client__c>
								<xsl:value-of select="$SFId"/>
							</Client__c>
							<Amount__c>
								<xsl:value-of select="$R10"/>
							</Amount__c>
							<Date_Ran__c>%current_date%</Date_Ran__c>
							<Declined_Comments__c>
								<xsl:value-of select="$DeclCom"/>
							</Declined_Comments__c>
							<Due_Date_Override__c>
								<xsl:value-of select="$DueOver"/>
							</Due_Date_Override__c>
							<Transaction_Status__c>
								<xsl:value-of select="$TranStat"/>
							</Transaction_Status__c>
							<Transaction_Type__c>
								<xsl:value-of select="$TranType"/>
							</Transaction_Type__c>
							<First_Recurring__c>
								<xsl:value-of select="$PayType"/>
							</First_Recurring__c>
							<Routing_Bank__c>
								<xsl:value-of select="$RoutingBank"/>
							</Routing_Bank__c>
						</sObjects>
					</create>
				</soap:Body>
			</soap:Envelope>`</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
