<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TestReq">?</xsl:variable>
		<xsl:variable name="APIURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN0']"/>
		<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr0']"/>
		<xsl:variable name="TransKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd0']"/>
		<xsl:variable name="Amount00" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__Amount']"/>
		<xsl:variable name="Amount01" select="translate(substring-before($Amount00, '.'), '$,', '')"/>
		<xsl:variable name="Amount02" select="substring-after($Amount00, '.')"/>
		<xsl:variable name="Amount03" select="string-length($Amount02)"/>
		<xsl:variable name="Amount04">
			<xsl:choose>
				<xsl:when test="$Amount03=2">
					<xsl:value-of select="$Amount02"/>
				</xsl:when>
				<xsl:when test="$Amount03=1">
					<xsl:value-of select="$Amount02"/>0</xsl:when>
				<xsl:when test="$Amount03=0">00</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="RefNumber" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__RefNumber']"/>
		<RefundPayment>
			<username>
				<xsl:value-of select="$APILogin"/>
			</username>
			<password>
				<xsl:value-of select="$TransKey"/>
			</password>
			<propertyCode>
				<xsl:value-of select="$APIURL"/>
			</propertyCode>
			<referenceNumber>
				<xsl:value-of select="$RefNumber"/>
			</referenceNumber>
			<amount>
				<xsl:value-of select="$Amount01"/>
				<xsl:value-of select="$Amount04"/>
			</amount>
			<!--<GUID>0897sfgWU266909s689h</GUID>-->
		</RefundPayment>
	</xsl:template>
</xsl:stylesheet>
