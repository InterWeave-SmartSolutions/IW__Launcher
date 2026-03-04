<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TestReq">?</xsl:variable>
		<xsl:variable name="APIURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN0']"/>
		<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr0']"/>
		<xsl:variable name="TransKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd0']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:if test="row/col[@name='Id']">
				<xsl:variable name="SFId" select="row/col[@name='Id']"/>
				<xsl:variable name="Name" select="row/col[@name='Name']"/>
				<xsl:variable name="FirstName" select="row/col[@name='First_Name__c']"/>
				<xsl:variable name="LastName" select="row/col[@name='Last_Name__c']"/>
				<xsl:variable name="Charge" select="row/col[@name='Gross_Monthly_Charge__c']"/>
				<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
				<xsl:variable name="CCCVM" select="row/col[@name='CVM_Value__c']"/>
				<xsl:variable name="CCExpDate" select="row/col[@name='CC_Exp_Date_00_00__c']"/>
				<xsl:variable name="CCExpM" select="substring-before($CCExpDate, '/')"/>
				<xsl:variable name="CCExpY" select="substring-after($CCExpDate, '/')"/>
				<xsl:variable name="BStreetIni" select="row/col[@name='Billing_Company_Street__c']"/>
				<xsl:variable name="BStreet">
					<xsl:choose>
						<xsl:when test="$BStreetIni=''">
							<xsl:value-of select="rowset[@name='Account']/row/col[@name='BillingStreet']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$BStreetIni"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Address" select="substring-before($BStreet, ' ')"/>
				<xsl:variable name="PostCodeIni" select="row/col[@name='Billing_Postal_Code__c']"/>
				<xsl:variable name="PostCode">
					<xsl:choose>
						<xsl:when test="$PostCodeIni=''">
							<xsl:value-of select="rowset[@name='Account']/row/col[@name='BillingPostalCode']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$PostCodeIni"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>com.interweave.plugin.FDPlugin.processCCSaleMin(<xsl:value-of select="$Address"/>,<xsl:value-of select="$PostCode"/>,<xsl:value-of select="$CCNumber"/>,<xsl:value-of select="$CCExpM"/>,<xsl:value-of select="$CCExpY"/>,<xsl:value-of select="$Charge"/>,<xsl:value-of select="$CCCVM"/>)</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
