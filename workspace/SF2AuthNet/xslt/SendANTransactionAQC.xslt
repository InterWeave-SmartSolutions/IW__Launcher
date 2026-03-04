<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TestReq">?</xsl:variable>
		<xsl:variable name="CCNumber">?</xsl:variable>
		<xsl:variable name="TransId">?</xsl:variable>
		<xsl:variable name="APILogin">%iw_user%</xsl:variable>
		<xsl:variable name="TransKey">%iw_password%</xsl:variable>
		<xsl:variable name="SFId">?</xsl:variable>
		<xsl:variable name="FirstName">?</xsl:variable>
		<xsl:variable name="LastName">?</xsl:variable>
		<xsl:variable name="Charge">?</xsl:variable>
		<xsl:variable name="Address">?</xsl:variable>
		<xsl:variable name="PostCode">?</xsl:variable>x_delim_data=TRUE&amp;x_delim_char=,&amp;x_version=3.1&amp;x_login=<xsl:value-of select="$APILogin"/>&amp;x_tran_key=<xsl:value-of select="$TransKey"/>&amp;x_type=CREDIT&amp;x_trans_id=<xsl:value-of select="$TransId"/>&amp;x_first_name=<xsl:value-of select="$FirstName"/>&amp;x_last_name=<xsl:value-of select="$LastName"/>&amp;x_address=<xsl:value-of select="$Address"/>&amp;x_zip=<xsl:value-of select="$PostCode"/>&amp;x_amount=<xsl:value-of select="$Charge"/>&amp;x_card_num=<xsl:value-of select="$CCNumber"/>&amp;x_cust_id=<xsl:value-of select="$SFId"/>&amp;x_test_request=<xsl:value-of select="$TestReq"/>&amp;x_relay_response=FALSE&amp;</xsl:template>
</xsl:stylesheet>
