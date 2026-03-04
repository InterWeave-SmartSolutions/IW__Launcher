<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
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
				<xsl:variable name="CCExpDate" select="row/col[@name='CC_Exp_Date_00_00__c']"/>
				<xsl:variable name="Address" select="substring(row/col[@name='BillingStreet'], '1', '60')"/>
				<xsl:variable name="City" select="row/col[@name='BillingCity']"/>
				<xsl:variable name="State" select="row/col[@name='BillingState']"/>
				<xsl:variable name="Country" select="row/col[@name='BillingCountry']"/>
				<xsl:variable name="PostCode" select="row/col[@name='BillingPostalCode']"/>
				<xsl:variable name="Email" select="row/col[@name='Billing_Email__c']"/>
				<xsl:variable name="CVM" select="row/col[@name='CVM_Value__c']"/>
				<xsl:variable name="Phone" select="row/col[@name='Phone']"/>x_delim_data=TRUE&amp;x_delim_char=,&amp;x_version=3.1&amp;x_login=<xsl:value-of select="$APILogin"/>&amp;x_tran_key=<xsl:value-of select="$TransKey"/>&amp;x_first_name=<xsl:value-of select="$FirstName"/>&amp;x_last_name=<xsl:value-of select="$LastName"/>&amp;x_address=<xsl:value-of select="$Address"/>
				<xsl:if test="$City!=''">&amp;x_city=<xsl:value-of select="$City"/>
				</xsl:if>
				<xsl:if test="$State!=''">&amp;x_state=<xsl:value-of select="$State"/>
				</xsl:if>
				<xsl:if test="$Country!=''">&amp;x_country=<xsl:value-of select="$Country"/>
				</xsl:if>
				<xsl:if test="$Email!=''">&amp;x_email=<xsl:value-of select="$Email"/>
				</xsl:if>
				<xsl:if test="$CVM!=''">&amp;x_card_code=<xsl:value-of select="$CVM"/>
				</xsl:if>
				<xsl:if test="$Phone!=''">&amp;x_phone=<xsl:value-of select="$Phone"/>
				</xsl:if>&amp;x_zip=<xsl:value-of select="$PostCode"/>&amp;x_amount=<xsl:value-of select="$Charge"/>&amp;x_card_num=<xsl:value-of select="$CCNumber"/>&amp;x_exp_date=<xsl:value-of select="$CCExpDate"/>&amp;x_cust_id=<xsl:value-of select="$SFId"/>&amp;x_test_request=<xsl:value-of select="$TestReq"/>&amp;x_relay_response=FALSE&amp;`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
