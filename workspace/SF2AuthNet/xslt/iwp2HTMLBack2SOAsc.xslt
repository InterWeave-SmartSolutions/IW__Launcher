<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="iso-8859-1" indent="yes" doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN" doctype-system="http://www.w3.org/TR/html4/loose.dtd"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="AccIntURL">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="Result" select="iwrecordset/transaction/datamap[@name='payauth']/data/row/col[@name='Result']"/>
		<xsl:variable name="R1" select="substring-before($Result, ',')"/>
		<xsl:variable name="R1c">
			<xsl:value-of select="$R1"/>,</xsl:variable>
		<xsl:variable name="R1a" select="substring-after($Result, $R1c)"/>
		<xsl:variable name="R2" select="substring-before($R1a, ',')"/>
		<xsl:variable name="R2c">
			<xsl:value-of select="$R2"/>,</xsl:variable>
		<xsl:variable name="R2a" select="substring-after($R1a, $R2c)"/>
		<xsl:variable name="R3" select="substring-before($R2a, ',')"/>
		<xsl:variable name="R3c">
			<xsl:value-of select="$R3"/>,</xsl:variable>
		<xsl:variable name="R3a" select="substring-after($R2a, $R3c)"/>
		<xsl:variable name="R4" select="substring-before($R3a, ',')"/>
		<xsl:variable name="R4c">
			<xsl:value-of select="$R4"/>,</xsl:variable>
		<xsl:variable name="R4a" select="substring-after($R3a, $R4c)"/>
		<xsl:variable name="R5" select="substring-before($R4a, ',')"/>
		<xsl:variable name="R5c">
			<xsl:value-of select="$R5"/>,</xsl:variable>
		<xsl:variable name="R5a" select="substring-after($R4a, $R5c)"/>
		<xsl:variable name="R6" select="substring-before($R5a, ',')"/>
		<xsl:variable name="R6c">
			<xsl:value-of select="$R6"/>,</xsl:variable>
		<xsl:variable name="R6a" select="substring-after($R5a, $R6c)"/>
		<xsl:variable name="R7" select="substring-before($R6a, ',')"/>
		<xsl:variable name="R7c">
			<xsl:value-of select="$R7"/>,</xsl:variable>
		<xsl:variable name="R7a" select="substring-after($R6a, $R7c)"/>
		<xsl:variable name="R8" select="substring-before($R7a, ',')"/>
		<xsl:variable name="R8c">
			<xsl:value-of select="$R8"/>,</xsl:variable>
		<xsl:variable name="R8a" select="substring-after($R7a, $R8c)"/>
		<xsl:variable name="R9" select="substring-before($R8a, ',')"/>
		<xsl:variable name="R9c">
			<xsl:value-of select="$R9"/>,</xsl:variable>
		<xsl:variable name="R9a" select="substring-after($R8a, $R9c)"/>
		<xsl:variable name="R10" select="substring-before($R9a, ',')"/>
		<xsl:variable name="R10c">
			<xsl:value-of select="$R10"/>,</xsl:variable>
		<xsl:variable name="R10a" select="substring-after($R9a, $R10c)"/>
		<xsl:variable name="R11" select="substring-before($R10a, ',')"/>
		<xsl:variable name="R11c">
			<xsl:value-of select="$R11"/>,</xsl:variable>
		<xsl:variable name="R11a" select="substring-after($R10a, $R11c)"/>
		<xsl:variable name="R12" select="substring-before($R11a, ',')"/>
		<xsl:variable name="R12c">
			<xsl:value-of select="$R12"/>,</xsl:variable>
		<xsl:variable name="R12a" select="substring-after($R11a, $R12c)"/>
		<xsl:variable name="SFId" select="substring-before($R12a, ',')"/>
		<xsl:variable name="SFURL">
			<xsl:choose>
				<xsl:when test="$AccIntURL=''">
					<xsl:choose>
						<xsl:when test="contains(iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='serverUrl'],'-')">
							<xsl:value-of select="substring-before(iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='serverUrl'],'-')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring-before(iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='serverUrl'],'.')"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>https://</xsl:text>
					<xsl:value-of select="$AccIntURL"/>
					<xsl:text>.my</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:text>.salesforce.com/</xsl:text>
			<xsl:value-of select="$SFId"/>
		</xsl:variable>
		<html>
			<head>
				<!--		<meta HTTP-EQUIV="REFRESH" content="0; url=https://na5.salesforce.com/00Q/o"/>-->
			</head>
			<body onload="top.location.href='{$SFURL}'"/>
		</html>
	</xsl:template>
</xsl:stylesheet>