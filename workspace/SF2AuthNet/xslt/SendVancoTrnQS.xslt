<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TransKey">%iw_password%</xsl:variable>
		<xsl:variable name="APILogin">%iw_user%</xsl:variable>
		<xsl:variable name="CurrentTime">%current_timestamp_s%</xsl:variable>
		<xsl:variable name="ClientID">?</xsl:variable>
		<!--ES10486-->
		<xsl:variable name="AcctType">CC</xsl:variable>
		<xsl:variable name="FrequencyCode">O</xsl:variable>
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='loginvanco']/data/row/col[@name='SessionID']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:variable name="SFId" select="row/col[@name='Id']"/>
			<xsl:variable name="Name" select="row/col[@name='Name']"/>
			<xsl:variable name="FirstName" select="row/col[@name='FirstName']"/>
			<xsl:variable name="LastName" select="row/col[@name='LastName']"/>
			<xsl:variable name="Charge" select="row/col[@name='Monthly_Charge__c']"/>
			<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
			<xsl:variable name="CCExpDate" select="row/col[@name='CC_EXP_Date_00_00__c']"/>
			<xsl:variable name="Address" select="row/col[@name='Street']"/>
			<xsl:variable name="PostCode" select="row/col[@name='PostalCode']"/>
			<xsl:variable name="City" select="row/col[@name='City']"/>
			<xsl:variable name="State" select="row/col[@name='State']"/>
			<xsl:variable name="Country" select="row/col[@name='Country']"/>
			<xsl:variable name="Email" select="row/col[@name='Email']"/>
			<xsl:variable name="Phone" select="row/col[@name='Phone']"/>
			<VancoWS>
				<Auth>
					<RequestType>EFTAddCompleteTransaction</RequestType>
					<RequestID>
						<xsl:value-of select="$SFId"/>
					</RequestID>
					<RequestTime>
						<xsl:value-of select="$CurrentTime"/>
					</RequestTime>
					<SessionID>
						<xsl:value-of select="$SessionId"/>
					</SessionID>
					<Version>2</Version>
				</Auth>
				<Request>
					<RequestVars>
						<ClientID>
							<xsl:value-of select="$ClientID"/>
						</ClientID>
						<CustomerName>
							<xsl:value-of select="$LastName"/>
							<xsl:text>, </xsl:text>
							<xsl:value-of select="$FirstName"/>
						</CustomerName>
						<CustomerAddress1>
							<xsl:value-of select="$Address"/>
						</CustomerAddress1>
						<CustomerCity>
							<xsl:value-of select="$City"/>
						</CustomerCity>
						<CustomerState>
							<xsl:value-of select="$State"/>
						</CustomerState>
						<CustomerZip>
							<xsl:value-of select="$PostCode"/>
						</CustomerZip>
						<CustomerPhone>
							<xsl:value-of select="$Phone"/>
						</CustomerPhone>
						<AccountType>
							<xsl:value-of select="$AcctType"/>
						</AccountType>
						<AccountNumber>
							<xsl:value-of select="$CCNumber"/>
						</AccountNumber>
						<CardBillingName>
							<xsl:value-of select="$FirstName"/>
							<xsl:text> </xsl:text>
							<xsl:value-of select="$LastName"/>
						</CardBillingName>
						<CardExpMonth>
							<xsl:value-of select="substring-before($CCExpDate, '/')"/>
						</CardExpMonth>
						<CardExpYear>
							<xsl:value-of select="substring-after($CCExpDate, '/')"/>
						</CardExpYear>
						<SameCCBillingAddrAsCust>Yes</SameCCBillingAddrAsCust>
						<Amount>
							<xsl:value-of select="$Charge"/>
						</Amount>
						<StartDate>%current_date%</StartDate>
						<FrequencyCode>
							<xsl:value-of select="$FrequencyCode"/>
						</FrequencyCode>
					</RequestVars>
				</Request>
			</VancoWS>`</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
