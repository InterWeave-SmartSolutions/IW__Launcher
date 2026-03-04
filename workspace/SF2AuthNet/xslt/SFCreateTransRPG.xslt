<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:variable name="SFId" select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Id']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/rowset">
			<xsl:variable name="R1">
				<xsl:choose>
					<xsl:when test="@name='CreditCardPaymentResponse' or @name='ACHPaymentResponse'">0</xsl:when>
					<xsl:otherwise>1</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="R4" select="row/col[@name='description']"/>
			<xsl:variable name="R7" select="row/col[@name='referenceNumber']"/>
			<xsl:variable name="R100" select="row/col[@name='amount']"/>
			<xsl:variable name="R101" select="string-length($R100)"/>
			<xsl:variable name="R102" select="number($R101)-number(2)"/>
			<xsl:variable name="R103" select="number($R101)-number(1)"/>
			<xsl:variable name="R10">
				<xsl:value-of select="substring($R100, 1, $R102)"/>.<xsl:value-of select="substring($R100, $R103)"/>
			</xsl:variable>
			<xsl:variable name="R11">
				<xsl:choose>
					<xsl:when test="@name='CreditCardPaymentResponse'">CC</xsl:when>
					<xsl:when test="@name='ACHPaymentResponse'">ECHECK</xsl:when>
					<xsl:otherwise>ERROR</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
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
			<xsl:variable name="Is1stPmnt">true</xsl:variable>
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
							<xsl:if test="$R10!='' and $R10!='.'">
								<Amount__c>
									<xsl:value-of select="$R10"/>
								</Amount__c>
							</xsl:if>
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
