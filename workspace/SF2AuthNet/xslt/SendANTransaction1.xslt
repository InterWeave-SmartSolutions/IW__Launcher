<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<!--		<xsl:variable name="CurrentDay" select="iwrecordset/transaction/datamap[@name='getsftime']/data/row/col[@name='currentday']"/>
		<xsl:variable name="CurrentDay3" select="iwrecordset/transaction/datamap[@name='getsftime3']/data/row/col[@name='currentday3']"/>
		<xsl:variable name="CurrentDay6" select="iwrecordset/transaction/datamap[@name='getsftime6']/data/row/col[@name='currentday6']"/>
		<xsl:variable name="CurrentDay9" select="iwrecordset/transaction/datamap[@name='getsftime9']/data/row/col[@name='currentday9']"/>-->
		<xsl:variable name="APIURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN1']"/>
		<xsl:variable name="TestReq">
			<xsl:choose>
				<xsl:when test="contains($APIURL, 'test')">TRUE</xsl:when>
				<xsl:otherwise>FALSE</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr1']"/>
		<xsl:variable name="TransKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd1']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<!--			<xsl:variable name="DeclNum" select="row/col[@name='Number_of_Declines__c']"/>
			<xsl:if test="//iwtransformationserver/iwrecordset/transaction[@name='RetrieveSFAcctDataLN'] or //iwtransformationserver/iwrecordset/transaction[@name='RetrieveSFLeadDataLN'] or ((starts-with(rowset[@name='Contacts']/rowset[@name='records']/row/col[@name='Card_Bills_On__c'], $CurrentDay) or ( $DeclNum=1 and starts-with(rowset[@name='Contacts']/rowset[@name='records']/row/col[@name='Card_Bills_On__c'], $CurrentDay3)) or ( $DeclNum=2 and starts-with(rowset[@name='Contacts']/rowset[@name='records']/row/col[@name='Card_Bills_On__c'], $CurrentDay6)) or ( $DeclNum=3 and starts-with(rowset[@name='Contacts']/rowset[@name='records']/row/col[@name='Card_Bills_On__c'], $CurrentDay9))) and rowset[@name='Contacts']/rowset[@name='records']/row/col[@name='Special_Billing__c']='false' and rowset[@name='Contacts']/rowset[@name='records']/row/col[@name='File_COMPLETED__c']='false')">-->
			<xsl:if test="row/col[@name='Id']">
				<xsl:variable name="SFId" select="row/col[@name='Id']"/>
				<xsl:variable name="Name" select="row/col[@name='Name']"/>
				<xsl:variable name="FirstName" select="row/col[@name='FirstName']"/>
				<xsl:variable name="LastName" select="row/col[@name='LastName']"/>
				<xsl:variable name="Charge" select="row/col[@name='Gross_Monthly_Charge__c']"/>
				<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
				<xsl:variable name="CCExpDate" select="row/col[@name='CC_Exp_Date_00_00__c']"/>
				<xsl:variable name="Address" select="substring(row/col[@name='BillingStreet'], '1', '60')"/>
				<xsl:variable name="PostCode" select="row/col[@name='BillingPostalCode']"/>x_delim_data=TRUE&amp;x_delim_char=,&amp;x_version=3.1&amp;x_login=<xsl:value-of select="$APILogin"/>&amp;x_tran_key=<xsl:value-of select="$TransKey"/>&amp;x_first_name=<xsl:value-of select="$FirstName"/>&amp;x_last_name=<xsl:value-of select="$LastName"/>&amp;x_address=<xsl:value-of select="$Address"/>&amp;x_zip=<xsl:value-of select="$PostCode"/>&amp;x_amount=<xsl:value-of select="$Charge"/>&amp;x_card_num=<xsl:value-of select="$CCNumber"/>&amp;x_exp_date=<xsl:value-of select="$CCExpDate"/>&amp;x_cust_id=<xsl:value-of select="$SFId"/>&amp;x_test_request=<xsl:value-of select="$TestReq"/>&amp;x_relay_response=FALSE&amp;`</xsl:if>
			<!--</xsl:if>-->
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>