<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/row/col[@name='Result']">
			<xsl:variable name="Result" select="."/>
			<xsl:variable name="R1" select="substring-before($Result, ',')"/>
			<xsl:if test="$R1=1">
				<xsl:variable name="R1c">
					<xsl:value-of select="$R1"/>,</xsl:variable>
				<xsl:variable name="R1a" select="substring-after($Result, $R1c)"/>
				<xsl:variable name="R2" select="substring-before($R1a, ',')"/>
				<xsl:variable name="R2c">
					<xsl:value-of select="$R2"/>,</xsl:variable>
				<xsl:variable name="R2a" select="substring-after($R1a, $R2c)"/>
				<xsl:variable name="R3" select="substring-before($R2a, ',')"/>
				<xsl:variable name="R3c">
					<xsl:value-of select="$R3"/>,</xsl:variable>
				<xsl:variable name="R3a" select="substring-after($R2a, $R3c)"/>
				<xsl:variable name="R4" select="substring-before($R3a, ',')"/>
				<xsl:variable name="R4c">
					<xsl:value-of select="$R4"/>,</xsl:variable>
				<xsl:variable name="R4a" select="substring-after($R3a, $R4c)"/>
				<xsl:variable name="R5" select="substring-before($R4a, ',')"/>
				<xsl:variable name="R5c">
					<xsl:value-of select="$R5"/>,</xsl:variable>
				<xsl:variable name="R5a" select="substring-after($R4a, $R5c)"/>
				<xsl:variable name="R6" select="substring-before($R5a, ',')"/>
				<xsl:variable name="R6c">
					<xsl:value-of select="$R6"/>,</xsl:variable>
				<xsl:variable name="R6a" select="substring-after($R5a, $R6c)"/>
				<xsl:variable name="R7" select="substring-before($R6a, ',')"/>
				<xsl:variable name="R7c">
					<xsl:value-of select="$R7"/>,</xsl:variable>
				<xsl:variable name="R7a" select="substring-after($R6a, $R7c)"/>
				<xsl:variable name="R8" select="substring-before($R7a, ',')"/>
				<xsl:variable name="R8c">
					<xsl:value-of select="$R8"/>,</xsl:variable>
				<xsl:variable name="R8a" select="substring-after($R7a, $R8c)"/>
				<xsl:variable name="R9" select="substring-before($R8a, ',')"/>
				<xsl:variable name="R9c">
					<xsl:value-of select="$R9"/>,</xsl:variable>
				<xsl:variable name="R9a" select="substring-after($R8a, $R9c)"/>
				<xsl:variable name="R10" select="substring-before($R9a, ',')"/>
				<xsl:variable name="R10c">
					<xsl:value-of select="$R10"/>,</xsl:variable>
				<xsl:variable name="R10a" select="substring-after($R9a, $R10c)"/>
				<xsl:variable name="R11" select="substring-before($R10a, ',')"/>
				<xsl:variable name="R11c">
					<xsl:value-of select="$R11"/>,</xsl:variable>
				<xsl:variable name="R11a" select="substring-after($R10a, $R11c)"/>
				<xsl:variable name="R12" select="substring-before($R11a, ',')"/>
				<xsl:variable name="R12c">
					<xsl:value-of select="$R12"/>,</xsl:variable>
				<xsl:variable name="R12a" select="substring-after($R11a, $R12c)"/>
				<xsl:variable name="R13" select="substring-before($R12a, ',')"/>
				<xsl:variable name="R13c">
					<xsl:value-of select="$R13"/>,</xsl:variable>
				<xsl:variable name="R13a" select="substring-after($R12a, $R13c)"/>
				<xsl:variable name="R14" select="substring-before($R13a, ',')"/>
				<xsl:variable name="AppStatus">Approved</xsl:variable>
				<xsl:variable name="DeclCom"/>
				<xsl:variable name="DueOver">0</xsl:variable>
				<xsl:variable name="TranStat">Processed</xsl:variable>
				<xsl:variable name="PayType">First Payment</xsl:variable>
				<xsl:variable name="TranType">Credit Card</xsl:variable>
				<xsl:variable name="RoutingBank">Durango</xsl:variable>
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
