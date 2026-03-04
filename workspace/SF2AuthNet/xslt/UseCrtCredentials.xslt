<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="CurrentFlowId" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']"/>
		<xsl:variable name="AccIntURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']"/>
		<xsl:choose>
			<xsl:when test="contains($AccIntURL, '0/odata')">PAR[ForceUseSession=true]  PAR[Host=<xsl:value-of select="substring-after(substring-before($AccIntURL, '/0/odata/'), 'https://')"/>] PAR[BPMCSRF=<xsl:value-of select="iwrecordset/transaction[@name='AuthCrt']/datamap[@name='getauthcookies']/data/rowset[@name='o']/row/col[@name='BPMCSRF']"/>] PAR[Cookie=.ASPXAUTH=<xsl:value-of select="iwrecordset/transaction[@name='AuthCrt']/datamap[@name='getauthcookies']/data/rowset[@name='o']/row/col[@name='_.ASPXAUTH']"/>;BPMCSRF=<xsl:value-of select="iwrecordset/transaction[@name='AuthCrt']/datamap[@name='getauthcookies']/data/rowset[@name='o']/row/col[@name='BPMCSRF']"/>;UserName=<xsl:value-of select="iwrecordset/transaction[@name='AuthCrt']/datamap[@name='getauthcookies']/data/rowset[@name='o']/row/col[@name='UserName']"/>;BPMLOADER=<xsl:value-of select="iwrecordset/transaction[@name='AuthCrt']/datamap[@name='getauthcookies']/data/rowset[@name='o']/row/col[@name='BPMLOADER']"/>;] GET</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>