<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:variable name="SFId" select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Id']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/row">
			<xsl:variable name="R1" select="col[@name='ssl_result']"/>
			<xsl:variable name="R4" select="col[@name='ssl_result_message']"/>
			<xsl:variable name="R7" select="col[@name='ssl_txn_id']"/>
			<xsl:variable name="R10" select="col[@name='ssl_amount']"/>
			<xsl:variable name="R11">CC</xsl:variable>
			<xsl:variable name="R13" select="$SFId"/>
			<xsl:variable name="AppStatus">
				<xsl:choose>
					<xsl:when test="$R1=0">Approved</xsl:when>
					<xsl:otherwise>Declined</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="DeclCom">
				<xsl:if test="$R1!=0">
					<xsl:value-of select="$R4"/>
				</xsl:if>
			</xsl:variable>
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
			<xsl:variable name="TranType">
				<xsl:choose>
					<xsl:when test="$R11='CC'">Credit Card</xsl:when>
					<xsl:when test="$R11='ECHECK'">ACH</xsl:when>
					<xsl:otherwise>Other</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="RoutingBank">
				<xsl:choose>
					<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='RetrieveSFAcctDataLId']">Monterey</xsl:when>
					<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='RetrieveSFSODataLId']">Authorize.net</xsl:when>
				</xsl:choose>
			</xsl:variable>
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
