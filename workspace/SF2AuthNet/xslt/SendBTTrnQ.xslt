<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TestMode" select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='gettestmode']/data/row/col[@name='Result']"/>
		<xsl:variable name="CurrentDate">%current_date_p%</xsl:variable>
		<xsl:variable name="CurrentTimestamp">%current_timestamp_ms%</xsl:variable>
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
			<xsl:variable name="AcctNumber">
				<xsl:choose>
					<xsl:when test="row/col[@name='ACCOUNT__c']">
						<xsl:value-of select="row/col[@name='ACCOUNT__c']"/>
					</xsl:when>
					<xsl:when test="row/col[@name='Client_Acct__c']">
						<xsl:value-of select="row/col[@name='Client_Acct__c']"/>
					</xsl:when>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="RoutingNumber" select="row/col[@name='Routing__c']"/>
			<xsl:variable name="Address" select="substring(row/col[@name='Street'], '1', '60')"/>
			<xsl:variable name="PostCode" select="row/col[@name='PostalCode']"/>
			<xsl:variable name="City" select="row/col[@name='City']"/>
			<xsl:variable name="State" select="row/col[@name='State']"/>
			<xsl:variable name="Country" select="row/col[@name='Country']"/>
			<xsl:variable name="Email" select="row/col[@name='Email']"/>
			<xsl:variable name="Phone" select="row/col[@name='Phone']"/>
			<xsl:variable name="SFBirthDate" select="row/col[@name='Birthdate__c']"/>
			<xsl:variable name="BirthDate">
				<xsl:value-of select="substring($SFBirthDate, 1, 4)"/>
				<xsl:value-of select="substring($SFBirthDate, 6, 2)"/>
				<xsl:value-of select="substring($SFBirthDate, 9, 2)"/>
			</xsl:variable>
			<xsl:variable name="BankName">
				<xsl:choose>
					<xsl:when test="$TestMode='FALSE' or $TestMode='false'"/>
					<xsl:otherwise>Test Bank</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="BankCity">
				<xsl:choose>
					<xsl:when test="$TestMode='FALSE' or $TestMode='false'"/>
					<xsl:otherwise>Test Bank City</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="AcctName">
				<xsl:choose>
					<xsl:when test="$TestMode='FALSE' or $TestMode='false'"/>
					<xsl:otherwise>Test Account Name</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="TransId">
				<xsl:choose>
					<xsl:when test="$TestMode='FALSE' or $TestMode='false'"/>
					<xsl:otherwise>TestId-0001</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="VerId">
				<xsl:choose>
					<xsl:when test="$TestMode='FALSE' or $TestMode='false'">
						<xsl:value-of select="$SFId"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$CurrentTimestamp"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="CheckNumber"/>
			<xsl:variable name="EmpId"/>
			<xsl:variable name="SFAcctType" select="row/col[@name='Account_Type__c']"/>
			<xsl:variable name="AcctType">
				<xsl:choose>
					<xsl:when test="$SFAcctType='C' or $SFAcctType='c'">Checking</xsl:when>
					<xsl:when test="$SFAcctType='S' or $SFAcctType='s'">Saving</xsl:when>
				</xsl:choose>
			</xsl:variable>
			<xsl:value-of select="$VerId"/>,<xsl:value-of select="$FirstName"/>,,<xsl:value-of select="$LastName"/>,<xsl:value-of select="$Address"/>,<xsl:value-of select="$City"/>,<xsl:value-of select="$State"/>,<xsl:value-of select="$PostCode"/>,<xsl:value-of select="$Country"/>,<xsl:value-of select="$Phone"/>,<xsl:value-of select="$BirthDate"/>,<xsl:value-of select="$BankName"/>,<xsl:value-of select="$BankCity"/>,<xsl:value-of select="$AcctName"/>,<xsl:value-of select="$CheckNumber"/>,<xsl:value-of select="$AcctType"/>,<xsl:value-of select="$RoutingNumber"/>,<xsl:value-of select="$AcctNumber"/>,<xsl:value-of select="$Charge"/>,<xsl:value-of select="$CurrentDate"/>,<xsl:value-of select="$EmpId"/>,<xsl:value-of select="$TransId"/>`</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
