<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='SendTranResponse']">
			<xsl:variable name="AppStatus">Approved</xsl:variable>
			<xsl:variable name="R7" select="rowset[@name='tranData']/row/col[@name='tranNr']"/>
			<xsl:variable name="R10">
				<xsl:value-of select="substring(rowset[@name='tranData']/row/col[@name='amt'], 1, 10)"/>
				<xsl:text>.</xsl:text>
				<xsl:value-of select="substring(rowset[@name='tranData']/row/col[@name='amt'], 11, 2)"/>
			</xsl:variable>
			<xsl:variable name="R13" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']"/>
			<xsl:variable name="DeclCom"/>
			<xsl:variable name="Is1stPmnt">
				<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
					<xsl:if test="row/col[@name='Id']=$R13">
						<xsl:variable name="TB" select="row/col[@name='Total_Balance__c']"/>
						<xsl:choose>
							<xsl:when test="$TB='' or number($TB)=number(0.0)">true</xsl:when>
							<xsl:otherwise>false</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
				</xsl:for-each>
			</xsl:variable>
			<xsl:variable name="DueOver">0</xsl:variable>
			<xsl:variable name="TranStat">Processed</xsl:variable>
			<xsl:variable name="PayType">
				<xsl:choose>
					<xsl:when test="$Is1stPmnt='true'">First Payment</xsl:when>
					<xsl:otherwise>Recurring Payment</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="TranType">Refund</xsl:variable>
			<xsl:variable name="RoutingBank">TransFirst</xsl:variable>
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
							<xsl:choose>
								<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='PayAuthorizeNetAQSO']">
									<Sales_Order__c>
										<xsl:value-of select="$R13"/>
									</Sales_Order__c>
								</xsl:when>
								<xsl:otherwise>
									<Client__c>
										<xsl:value-of select="$R13"/>
									</Client__c>
								</xsl:otherwise>
							</xsl:choose>
							<Amount__c>
								<xsl:value-of select="$R10"/>
							</Amount__c>
							<Date_Ran__c>
								<xsl:value-of select="substring(rowset[@name='tranData']/row/col[@name='dtTm'], 1, 10)"/>
							</Date_Ran__c>
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
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='Fault']">
			<xsl:variable name="R7">XXXXXXXXXXXX</xsl:variable>
			<xsl:variable name="R10" select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Gross_Monthly_Charge__c']"/>
			<xsl:variable name="R13" select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Id']"/>
			<xsl:variable name="AppStatus">
				<xsl:choose>
					<xsl:when test="rowset[@name='detail']/rowset[@name='ServiceFault']">Declined</xsl:when>
					<xsl:when test="rowset[@name='detail']/rowset[@name='SystemFault']">Error</xsl:when>
					<xsl:otherwise>Error</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="DeclCom">
				<xsl:value-of select="rowset[@name='detail']/rowset[contains(@name,'Fault')]/row/col[@name='name']"/>
				<xsl:text>: </xsl:text>
				<xsl:value-of select="rowset[@name='detail']/rowset[contains(@name,'Fault')]/row/col[@name='message']"/>
				<xsl:text> Error Code: </xsl:text>
				<xsl:value-of select="rowset[@name='detail']/rowset[contains(@name,'Fault')]/row/col[@name='errorCode']"/>
			</xsl:variable>
			<xsl:variable name="Is1stPmnt">true</xsl:variable>
			<xsl:variable name="DueOver">0</xsl:variable>
			<xsl:variable name="TranStat">Processed</xsl:variable>
			<xsl:variable name="PayType">
				<xsl:choose>
					<xsl:when test="$Is1stPmnt='true'">First Payment</xsl:when>
					<xsl:otherwise>Recurring Payment</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="TranType">Credit Card</xsl:variable>
			<xsl:variable name="RoutingBank">TransFirst</xsl:variable>
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
							<xsl:choose>
								<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='PayAuthorizeNetAQSO']">
									<Sales_Order__c>
										<xsl:value-of select="$R13"/>
									</Sales_Order__c>
								</xsl:when>
								<xsl:otherwise>
									<Client__c>
										<xsl:value-of select="$R13"/>
									</Client__c>
								</xsl:otherwise>
							</xsl:choose>
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