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
		<xsl:variable name="UseIdorName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__UseIdorName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__UseIdorName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="IdorName">
			<xsl:choose>
				<xsl:when test="$UseIdorName='Name'">Name</xsl:when>
				<xsl:otherwise>Id</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="ShowCCTypes">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='ShowCCTypes']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='ShowCCTypes']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/rowset[@name='o']">
			<xsl:variable name="R1" select="row/col[@name='status']"/>
			<xsl:variable name="R4">
				<xsl:if test="rowset[@name='Errors']">
					<xsl:value-of select="rowset[@name='Errors']/row/col[@name='ShortMessage']"/>
					<xsl:text>. </xsl:text>
					<xsl:value-of select="rowset[@name='Errors']/row/col[@name='LongMessage']"/>
				</xsl:if>
			</xsl:variable>
			<xsl:variable name="R7">
				<xsl:choose>
					<xsl:when test="rowset[@name='error']">
						<xsl:value-of select="rowset[@name='error']/row/col[@name='charge']"/>
					</xsl:when>
					<xsl:when test="row/col[@name='id']">
						<xsl:value-of select="row/col[@name='id']"/>
					</xsl:when>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="R10">
				<xsl:choose>
					<xsl:when test="rowset[@name='error']">
						<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Gross_Monthly_Charge__c']"/>
					</xsl:when>
					<xsl:when test="row/col[@name='amount']">
						<xsl:value-of select="row/col[@name='amount'] div 100"/>
					</xsl:when>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="R13" select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Id']"/>
			<xsl:variable name="AppStatus">
				<xsl:choose>
					<xsl:when test="$R1='succeeded'">Approved</xsl:when>
					<xsl:when test="rowset[@name='error'] and rowset[@name='error']/row/col[@name='code']='card_declined'">Declined</xsl:when>
					<xsl:when test="rowset[@name='error']">Error</xsl:when>
					<!--<xsl:when test="$R1=4">Held for Review</xsl:when>-->
					<xsl:otherwise>Error</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="DeclCom">
				<xsl:choose>
					<xsl:when test="$R1!='succeeded'">Declined</xsl:when>
					<xsl:when test="rowset[@name='error']">
						<xsl:value-of select="rowset[@name='error']/row/col[@name='message']"/>
					</xsl:when>
					<xsl:when test="$R1=4">Held for Review</xsl:when>
				</xsl:choose>
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
					<xsl:when test="row/col[@name='object']='charge' and $Is1stPmnt='true'">First Payment</xsl:when>
					<xsl:when test="row/col[@name='object']='charge'">Recurring Payment</xsl:when>
					<xsl:when test="row/col[@name='object']='refund'">Refund</xsl:when>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="TranType">Credit Card</xsl:variable>
			<!--<xsl:choose>
						<xsl:when test="$R11='CC'">Credit Card</xsl:when>
						<xsl:when test="$R11='ECHECK'">ACH</xsl:when>
						<xsl:otherwise>Other</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>-->
			<xsl:variable name="RoutingBank">Stripe</xsl:variable>
			<xsl:variable name="ClientId0">
				<xsl:choose>
					<xsl:when test="row/col[@name='object']='refund'">
						<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Client__c']"/>
					</xsl:when>
					<xsl:when test="$R13!=''">
						<xsl:value-of select="$R13"/>
					</xsl:when>
					<xsl:when test="$IdorName!='Id'">
						<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Id']"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$TransactionSourceName"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="ClientId">
				<xsl:choose>
					<xsl:when test="string-length($ClientId0)=18">
						<xsl:value-of select="$ClientId0"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="substring($ClientId0, 1, 15)"/>
					</xsl:otherwise>
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
										<xsl:value-of select="$ClientId"/>
									</Sales_Order__c>
								</xsl:when>
								<xsl:otherwise>
									<Client__c>
										<xsl:value-of select="$ClientId"/>
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
							<xsl:if test="$ShowCCTypes='Yes'">
								<Credit_Card_Type__c>
									<xsl:value-of select="rowset[@name='source']/row/col[@name='brand']"/>
								</Credit_Card_Type__c>
							</xsl:if>
						</sObjects>
					</create>
				</soap:Body>
			</soap:Envelope>`</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
