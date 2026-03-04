<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="QDSN0" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN0']"/>
		<xsl:variable name="OFURLM">
			<xsl:value-of select="$QDSN0"/>
			<xsl:text>/tokens</xsl:text>
		</xsl:variable>
		<xsl:value-of select="$OFURLM"/>
	</xsl:template>
</xsl:stylesheet>