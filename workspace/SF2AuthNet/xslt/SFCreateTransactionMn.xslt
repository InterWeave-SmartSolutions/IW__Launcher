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
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/row/col[@name='Result']">
			<xsl:variable name="Result0" select="."/>
			<xsl:variable name="Result" select="normalize-space($Result0)"/>
			<xsl:variable name="R1a" select="substring-after($Result, 'result = ')"/>
			<xsl:variable name="R1">
				<xsl:choose>
					<xsl:when test="contains($R1a, '&lt;')">
						<xsl:value-of select="normalize-space(substring-before($R1a, '&lt;'))"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$R1a"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="R2a" select="substring-after($Result, 'response_code = ')"/>
			<xsl:variable name="R2">
				<xsl:choose>
					<xsl:when test="contains($R2a, '&lt;')">
						<xsl:value-of select="normalize-space(substring-before($R2a, '&lt;'))"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$R2a"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="R4a" select="substring-after($Result, 'message = ')"/>
			<xsl:variable name="R4">
				<xsl:value-of select="$R2"/>
				<xsl:text> </xsl:text>
				<xsl:choose>
					<xsl:when test="contains($R4a, '&lt;')">
						<xsl:value-of select="normalize-space(substring-before($R4a, '&lt;'))"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$R4a"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="R7a" select="substring-after($Result, 'txn_num = ')"/>
			<xsl:variable name="R7">
				<xsl:choose>
					<xsl:when test="contains($R7a, '&lt;')">
						<xsl:value-of select="normalize-space(substring-before($R7a, '&lt;'))"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$R7a"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="R10a" select="substring-after($Result, 'charge_total = ')"/>
			<xsl:variable name="R10">
				<xsl:choose>
					<xsl:when test="contains($R10a, '&lt;')">
						<xsl:value-of select="normalize-space(substring-before($R10a, '&lt;'))"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$R10a"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="R13a" select="substring-after($Result, 'response_order_id = ')"/>
			<xsl:variable name="R130">
				<xsl:choose>
					<xsl:when test="contains($R13a, '&lt;')">
						<xsl:value-of select="normalize-space(substring-before($R13a, '&lt;'))"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$R13a"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="R13" select="substring-before($R130, ':')"/>
			<xsl:variable name="AppStatus">
				<xsl:choose>
					<xsl:when test="$R1=1">Approved</xsl:when>
					<xsl:when test="$R1=0">
						<xsl:choose>
							<xsl:when test="$R2='null'">Error</xsl:when>
							<xsl:otherwise>Declined</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>Error</xsl:otherwise>
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
			<xsl:variable name="TranType">Credit Card</xsl:variable>
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
			</soap:Envelope>`</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
