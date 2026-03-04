<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="APIURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN0']"/>
		<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr1']"/>
		<xsl:variable name="TransKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd1']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:if test="row/col[@name='Id']">
				<xsl:variable name="SFId" select="row/col[@name='Id']"/>
				<xsl:variable name="Name" select="row/col[@name='Name']"/>
				<xsl:variable name="FirstName" select="row/col[@name='FirstName']"/>
				<xsl:variable name="LastName" select="row/col[@name='LastName']"/>
				<xsl:variable name="Charge" select="row/col[@name='Gross_Monthly_Charge__c']"/>
				<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
				<xsl:variable name="CCExpDate0" select="row/col[@name='CC_Exp_Date_00_00__c']"/>
				<xsl:variable name="CCExpDate">
					<xsl:choose>
						<xsl:when test="contains($CCExpDate0, '/')">
							<xsl:value-of select="substring-before($CCExpDate0, '/')"/>xsl:value-of select="substring-after($CCExpDate0, '/')"/></xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$CCExpDate0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Address" select="substring(row/col[@name='BillingStreet'], '1', '60')"/>
				<xsl:variable name="PostCode" select="row/col[@name='BillingPostalCode']"/>username=<xsl:value-of select="$APILogin"/>&amp;password=<xsl:value-of select="$TransKey"/>&amp;type=sale&amp;firstname=<xsl:value-of select="$FirstName"/>&amp;lastname=<xsl:value-of select="$LastName"/>&amp;address1=<xsl:value-of select="$Address"/>&amp;zip=<xsl:value-of select="$PostCode"/>&amp;amount=<xsl:value-of select="$Charge"/>&amp;ccnumber=<xsl:value-of select="$CCNumber"/>&amp;ccexp=<xsl:value-of select="$CCExpDate"/>&amp;orderid=<xsl:value-of select="$SFId"/><!--username=demo&amp;password=password&amp;type=sale&amp;ccnumber=4111111111111111&amp;ccexp=0711&amp;cvv=999&amp;amount=10.00-->`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
