<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr2']"/>
		<xsl:variable name="TransKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd2']"/>
		<xsl:variable name="CurrentDate">%current_date_up%</xsl:variable>
		<xsl:variable name="Description">First Universal Landing</xsl:variable>
		<xsl:variable name="CheckType">1</xsl:variable>
		<xsl:variable name="CheckNumber"/>
		<!--ES10486-->
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='loginvanco']/data/row/col[@name='SessionID']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:variable name="SFId" select="row/col[@name='Id']"/>
			<xsl:variable name="Name" select="row/col[@name='Name']"/>
			<xsl:variable name="FirstName" select="row/col[@name='FirstName']"/>
			<xsl:variable name="LastName" select="row/col[@name='LastName']"/>
			<xsl:variable name="Charge">
				<xsl:choose>
					<xsl:when test="row/col[@name='Monthly_Charge__c']">
						<xsl:value-of select="row/col[@name='Monthly_Charge__c']"/>
					</xsl:when>
					<xsl:when test="row/col[@name='Gross_Monthly_Charge__c']">
						<xsl:value-of select="row/col[@name='Gross_Monthly_Charge__c']"/>
					</xsl:when>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="ACHAcctNumber">
				<xsl:choose>
					<xsl:when test="row/col[@name='ACCOUNT__c']">
						<xsl:value-of select="row/col[@name='ACCOUNT__c']"/>
					</xsl:when>
					<xsl:when test="row/col[@name='Client_Acct__c']">
						<xsl:value-of select="row/col[@name='Client_Acct__c']"/>
					</xsl:when>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="ACHRoutingNumber" select="row/col[@name='Routing__c']"/>
			<xsl:variable name="SFAcctType" select="row/col[@name='Account_Type__c']"/>
			<xsl:variable name="ACHAcctType">
				<xsl:choose>
					<xsl:when test="$SFAcctType='c' or $SFAcctType='C'">Checking</xsl:when>
					<xsl:when test="$SFAcctType='s' or $SFAcctType='S'">Saving</xsl:when>
				</xsl:choose>
			</xsl:variable>MerchantID=<xsl:value-of select="$APILogin"/>&amp;MerchantKey=<xsl:value-of select="$TransKey"/>&amp;ReferenceNumber=<xsl:value-of select="$SFId"/>&amp;Amount=<xsl:value-of select="$Charge"/>&amp;RoutingNumber=<xsl:value-of select="$ACHRoutingNumber"/>&amp;AccountNumber=<xsl:value-of select="$ACHAcctNumber"/>&amp;BankAccountType=<xsl:value-of select="$ACHAcctType"/>&amp;AccountName=<xsl:value-of select="$FirstName"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="$LastName"/>&amp;ProcessDate=<xsl:value-of select="$CurrentDate"/>&amp;ACHCheckType=<xsl:value-of select="$CheckType"/>&amp;Description=<xsl:value-of select="$Description"/>&amp;CheckNumber=<xsl:value-of select="$CheckNumber"/>`</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
