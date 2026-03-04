<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TransKey">%iw_password%</xsl:variable>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:variable name="SFId" select="row/col[@name='Id']"/>
			<xsl:variable name="Name" select="row/col[@name='Name']"/>
			<xsl:variable name="FirstName" select="row/col[@name='FirstName']"/>
			<xsl:variable name="LastName" select="row/col[@name='LastName']"/>
			<xsl:variable name="Charge" select="row/col[@name='Monthly_Charge__c']"/>
			<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
			<xsl:variable name="CCExpDate" select="row/col[@name='CC_EXP_Date_00_00__c']"/>
			<xsl:variable name="Address" select="substring(row/col[@name='Street'], '1', '60')"/>
			<xsl:variable name="PostCode" select="row/col[@name='PostalCode']"/>customerPaymentPageText=<xsl:value-of select="$TransKey"/>;cardHolderName=<xsl:value-of select="$FirstName"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="$LastName"/>;firstName=<xsl:value-of select="$FirstName"/>;lastName=<xsl:value-of select="$LastName"/>;Address=<xsl:value-of select="$Address"/>;Zip=<xsl:value-of select="$PostCode"/>;purchaseAmount=<xsl:value-of select="$Charge"/>;cardNo=<xsl:value-of select="$CCNumber"/>;cardExpireMonth=<xsl:value-of select="substring-before($CCExpDate, '/')"/>;cardExpireYear=20<xsl:value-of select="substring-after($CCExpDate, '/')"/>;orderDescription=<xsl:value-of select="$SFId"/>`</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
