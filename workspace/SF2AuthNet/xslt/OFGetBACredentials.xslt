<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<!--<xsl:variable name="OFClient" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr0']"/>-->
		<xsl:variable name="OFPwd" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd0']"/>
		<xsl:variable name="OFCred">
			<xsl:text>application/x-www-form-urlencoded:NOENCODE HTTP_BASIC_AUTH:</xsl:text>
			<xsl:value-of select="$OFPwd"/>
			<xsl:text>:</xsl:text>
			<!--<xsl:value-of select="$OFPwd"/>-->
		</xsl:variable>
		<xsl:value-of select="$OFCred"/>
	</xsl:template>
</xsl:stylesheet>
