<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/row/col[@name='Result']">
			<xsl:variable name="Result" select="."/>
			<xsl:variable name="R10" select="substring-after($Result, 'response=')"/>
			<xsl:variable name="R1" select="substring-before($R10, '&amp;')"/>
			<xsl:variable name="R20" select="substring-after($Result, 'responsetext=')"/>
			<xsl:variable name="R2" select="substring-before($R20, '&amp;')"/>
			<xsl:variable name="R30" select="substring-after($Result, 'authcode=')"/>
			<xsl:variable name="R3" select="substring-before($R30, '&amp;')"/>
			<xsl:variable name="R40" select="substring-after($Result, 'transactionid=')"/>
			<xsl:variable name="R4" select="substring-before($R40, '&amp;')"/>
			<xsl:variable name="R50" select="substring-after($Result, 'avsresponse=')"/>
			<xsl:variable name="R5" select="substring-before($R50, '&amp;')"/>
			<xsl:variable name="R60" select="substring-after($Result, 'cvvresponse=')"/>
			<xsl:variable name="R6" select="substring-before($R60, '&amp;')"/>
			<xsl:variable name="R70" select="substring-after($Result, 'orderid=')"/>
			<xsl:variable name="R7" select="substring-before($R70, '&amp;')"/>
			<xsl:variable name="R80" select="substring-after($Result, 'type=')"/>
			<xsl:variable name="R8" select="substring-before($R80, '&amp;')"/>
			<xsl:variable name="R90" select="substring-after($Result, 'response_code=')"/>
			<xsl:variable name="TrAmt"><xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
					<xsl:if test="row/col[@name='Id']=$R7">
						<xsl:value-of select="row/col[@name='Gross_Monthly_Charge__c']"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:variable>
			<xsl:variable name="AppStatus">
				<xsl:choose>
					<xsl:when test="$R1=1">Approved</xsl:when>
					<xsl:when test="$R1=2">Declined</xsl:when>
					<xsl:when test="$R1=3">Error</xsl:when>
					<xsl:otherwise>Error</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="DeclCom">
				<xsl:if test="$R1!=1">
					<xsl:value-of select="$R2"/>
				</xsl:if>
			</xsl:variable>
			<xsl:variable name="DueOver">0</xsl:variable>
			<xsl:variable name="TranStat">Processed</xsl:variable>
			<xsl:variable name="PayType">Recurring Payment</xsl:variable>
			<xsl:variable name="TranType">Credit Card</xsl:variable>
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
								<xsl:value-of select="$R4"/>
							</Auth_net_Transaction_ID__c>
							<xsl:choose>
								<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='PayAuthorizeNetAQSO']">
									<Sales_Order__c>
										<xsl:value-of select="$R7"/>
									</Sales_Order__c>
								</xsl:when>
								<xsl:otherwise>
									<Client__c>
										<xsl:value-of select="$R7"/>
									</Client__c>
								</xsl:otherwise>
							</xsl:choose>
							<Amount__c>
								<xsl:value-of select="$TrAmt"/>
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
						</sObjects>
					</create>
				</soap:Body>
			</soap:Envelope>`</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>