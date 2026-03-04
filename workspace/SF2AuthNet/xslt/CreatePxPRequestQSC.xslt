<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="APILogin">%iw_user%</xsl:variable>
		<xsl:variable name="TransKey">%iw_password%</xsl:variable>
		<xsl:variable name="TransId">?</xsl:variable>
		<xsl:variable name="Charge">?</xsl:variable>MerchantID=<xsl:value-of select="$APILogin"/>&amp;MerchantKey=<xsl:value-of select="$TransKey"/>&amp;TransID=<xsl:value-of select="$TransId"/>&amp;CreditAmount=<xsl:value-of select="$Charge"/>
	</xsl:template>
</xsl:stylesheet>
