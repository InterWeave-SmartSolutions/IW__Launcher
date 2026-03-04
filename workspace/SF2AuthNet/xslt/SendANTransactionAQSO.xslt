<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TestReq">?</xsl:variable>
		<xsl:variable name="APILogin">%iw_user%</xsl:variable>
		<xsl:variable name="TransKey">%iw_password%</xsl:variable>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveso']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:if test="row/col[@name='ACH__c']='false'">
				<xsl:variable name="SFId" select="row/col[@name='Id']"/>
				<xsl:variable name="Name" select="row/col[@name='Name']"/>
				<xsl:variable name="FirstName" select="rowset[@name='PBSI__Contact__r']/row/col[@name='FirstName']"/>
				<xsl:variable name="LastName" select="rowset[@name='PBSI__Contact__r']/row/col[@name='LastName']"/>
				<xsl:variable name="Charge" select="row/col[@name='Gross_Monthly_Charge__c']"/>
				<xsl:variable name="CCNumber0" select="row/col[@name='Credit_Card_Client__c']"/>
				<xsl:variable name="CCNumber">
					<xsl:choose>
						<xsl:when test="$CCNumber0!=''">
							<xsl:value-of select="$CCNumber0"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="rowset[@name='PBSI__Customer__r']/row/col[@name='Credit_Card_Client__c']"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="CCExpDate0" select="row/col[@name='CC_Exp_Date_00_00__c']"/>
				<xsl:variable name="CCExpDate">
					<xsl:choose>
						<xsl:when test="$CCExpDate0!=''">
							<xsl:value-of select="$CCExpDate0"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="rowset[@name='PBSI__Customer__r']/row/col[@name='CC_Exp_Date_00_00__c']"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Address" select="substring(rowset[@name='PBSI__Customer__r']/row/col[@name='BillingStreet'], '1', '60')"/>
				<xsl:variable name="PostCode" select="rowset[@name='PBSI__Customer__r']/row/col[@name='BillingPostalCode']"/>x_delim_data=TRUE&amp;x_delim_char=,&amp;x_version=3.1&amp;x_login=<xsl:value-of select="$APILogin"/>&amp;x_tran_key=<xsl:value-of select="$TransKey"/>&amp;x_first_name=<xsl:value-of select="$FirstName"/>&amp;x_last_name=<xsl:value-of select="$LastName"/>&amp;x_address=<xsl:value-of select="$Address"/>&amp;x_zip=<xsl:value-of select="$PostCode"/>&amp;x_amount=<xsl:value-of select="$Charge"/>&amp;x_card_num=<xsl:value-of select="$CCNumber"/>&amp;x_exp_date=<xsl:value-of select="$CCExpDate"/>&amp;x_cust_id=<xsl:value-of select="$SFId"/>&amp;x_test_request=<xsl:value-of select="$TestReq"/>&amp;x_relay_response=FALSE&amp;`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
