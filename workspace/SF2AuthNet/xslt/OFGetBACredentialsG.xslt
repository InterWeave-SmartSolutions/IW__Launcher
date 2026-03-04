<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="CurrentFlowId">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="AccIntURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']"/>
		<xsl:variable name="OFClient" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
		<xsl:variable name="OFPwd" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFPswd']"/>
		<xsl:choose>
			<xsl:when test="contains($AccIntURL, '0/odata')">application/json;charset=utf-8 PAR[ForceUseSession=true] PAR[Host=<xsl:value-of select="substring-after(substring-before($AccIntURL, '/0/odata/'), 'https://')"/>]</xsl:when>
			<xsl:when test="starts-with($CurrentFlowId, 'QBCust2OCAcct')">
				<xsl:text>application/vnd.api+json HTTP_WSSE_AUTH:</xsl:text>
				<xsl:value-of select="$OFClient"/>
				<xsl:text>:</xsl:text>
				<xsl:value-of select="$OFPwd"/>
				<xsl:text> GET</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="OFCred">
					<xsl:text>HTTP_BASIC_AUTH:</xsl:text>
					<xsl:value-of select="$OFClient"/>
					<xsl:text>:</xsl:text>
					<xsl:value-of select="$OFPwd"/>
					<xsl:text> GET</xsl:text>
				</xsl:variable>
				<xsl:value-of select="$OFCred"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
