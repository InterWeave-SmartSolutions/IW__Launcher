<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:variable name="TransactionSourceName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="CurrentFlowId">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']"/>
				</xsl:when>
				<xsl:otherwise>SugarCO2QBBillNQQ</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="OldSDKUsed">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='OldSDKUsed']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='OldSDKUsed']"/>
				</xsl:when>
				<xsl:otherwise>No</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="AmFN">
			<xsl:choose>
				<xsl:when test="$OldSDKUsed='Yes' and $CurrentFlowId='SFTransactionsO2Auth'">Recurring_Monthly_Payment_Amount__c</xsl:when>
				<xsl:otherwise>Gross_Monthly_Charge__c</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/row/col[@name='Result']">
			<xsl:variable name="Result" select="."/>
			<xsl:if test="not(contains($Result, 'HTML'))">
				<xsl:variable name="R1" select="substring-before(substring-after($Result, 'response='), '&amp;')"/>
				<xsl:variable name="R4" select="substring-before(substring-after($Result, 'responsetext='), '&amp;')"/>
				<xsl:variable name="R7" select="substring-before(substring-after($Result, 'transactionid='), '&amp;')"/>
				<xsl:variable name="R13" select="substring-before(substring-after($Result, 'orderid='), '&amp;')"/>
				<xsl:variable name="R10" select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name=$AmFN]"/>
				<xsl:variable name="AppStatus">
					<xsl:choose>
						<xsl:when test="$R1=1">Approved</xsl:when>
						<xsl:when test="$R1=2">Declined</xsl:when>
						<xsl:when test="$R1=3">Error</xsl:when>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="DeclCom">
					<xsl:if test="$R1!=1">
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
					<!--<xsl:choose>
						<xsl:when test="$R11='CC'">-->Credit Card<!--</xsl:when>
						<xsl:when test="$R11='ECHECK'">ACH</xsl:when>
						<xsl:otherwise>Other</xsl:otherwise>
					</xsl:choose>-->
				</xsl:variable>
				<xsl:variable name="RoutingBank">Payscape</xsl:variable>
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
											<xsl:choose>
												<xsl:when test="$R13!=''">
													<xsl:value-of select="$R13"/>
												</xsl:when>
												<xsl:otherwise>
													<xsl:value-of select="$TransactionSourceName"/>
												</xsl:otherwise>
											</xsl:choose>
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
				</soap:Envelope>`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
