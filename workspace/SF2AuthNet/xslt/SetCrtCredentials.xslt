<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="substring-after(iwrecordset/transaction/datamap[@name='ppol_login']/data/row/col[@name='return'], 'SESSIONID:')"/>
		<xsl:variable name="CurrentFlowId" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']"/>
		<xsl:variable name="AccIntURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']"/>
		<xsl:variable name="OFClient" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
		<xsl:variable name="OFPwd" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFPswd']"/>
		<xsl:choose>
			<xsl:when test="contains($AccIntURL, '0/odata')">{
    "UserName":"<xsl:value-of select="$OFClient"/>",
    "UserPassword":"<xsl:value-of select="$OFPwd"/>"
}</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
