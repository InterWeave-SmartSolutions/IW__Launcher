<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/rowset[@name='VancoWS']">
			<xsl:variable name="R1">
				<xsl:if test="rowset[@name='Response']/rowset[@name='Errors']">1</xsl:if>
			</xsl:variable>
			<xsl:if test="$R1=''">
				<xsl:variable name="R11">CC</xsl:variable>
				<xsl:variable name="R7" select="rowset[@name='Response']/row/col[@name='TransactionRef']"/>
				<xsl:variable name="R13" select="rowset[@name='Auth']/row/col[@name='RequestID']"/>
				<xsl:variable name="AppStatus">Approved</xsl:variable>
				<xsl:variable name="DeclCom"/>
				<xsl:variable name="DueOver">0</xsl:variable>
				<xsl:variable name="TranStat">Processed</xsl:variable>
				<xsl:variable name="PayType">First Payment</xsl:variable>
				<xsl:variable name="TranType">
					<xsl:choose>
						<xsl:when test="$R11='CC'">Credit Card</xsl:when>
						<xsl:when test="$R11='ECHECK'">ACH-VANCO</xsl:when>
						<xsl:otherwise>Other</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="SFAccountIdIni">
					<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='convertlead']/data/row">
						<xsl:if test="col[@name='success']='true' and col[@name='leadId']=$R13">
							<xsl:value-of select="col[@name='accountId']"/>;</xsl:if>
					</xsl:for-each>
				</xsl:variable>
				<xsl:variable name="SFId">
					<xsl:choose>
						<xsl:when test="$SFAccountIdIni=''">
							<xsl:value-of select="$R13"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring-before($SFAccountIdIni,';')"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="R10">
					<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
						<xsl:if test="row/col[@name='Id']=$R13">
							<xsl:value-of select="row/col[@name='Monthly_Charge__c']"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:variable>
				<xsl:variable name="RoutingBank">Vanco CC</xsl:variable>
				<xsl:if test="$SFId!=''">
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
					</soap:Envelope>`</xsl:if>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
