<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<!--APIType: WS - WebServices, POST - post, HOST - hosted-->
		<xsl:variable name="TransactionSourceName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="APIType00">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__APIType']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__APIType']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="APIType0">
			<xsl:choose>
				<xsl:when test="contains($APIType00, ':')">
					<xsl:value-of select="substring-before($APIType00, ':')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$APIType00"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="SchedCCType">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SchedCCType']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SchedCCType']"/>
				</xsl:when>
				<xsl:otherwise>None</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="APIType">
			<xsl:choose>
				<xsl:when test="$APIType0!=''">
					<xsl:value-of select="$APIType0"/>
				</xsl:when>
				<xsl:when test="$SchedCCType='Cust'">POST</xsl:when>
				<xsl:otherwise>WS</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="QDSN0" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN0']"/>
		<xsl:variable name="OFURLM">
			<xsl:choose>
				<xsl:when test="starts-with($APIType, 'CARDPOINT')">
					<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr0']"/>
					<xsl:variable name="APIKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd0']"/>
					<xsl:variable name="OFClient">
						<xsl:choose>
							<xsl:when test="contains($APILogin, '|')">
								<xsl:value-of select="substring-after($APILogin, '|')"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="$APILogin"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:variable name="OFCred">
						<xsl:text>application/json HTTP_BASIC_AUTH:</xsl:text>
						<xsl:value-of select="$OFClient"/>
						<xsl:text>:</xsl:text>
						<xsl:value-of select="$APIKey"/>
					</xsl:variable>
					<xsl:value-of select="$OFCred"/>
				</xsl:when>
				<xsl:when test="contains($QDSN0, ' %STP:')">
					<xsl:value-of select="substring-before($QDSN0, ' %STP:')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$QDSN0"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="$APIType='WS'"/>
				<xsl:when test="$APIType='POST'">
					<xsl:text>/TransactionStatus</xsl:text>
				</xsl:when>
			</xsl:choose>
			<xsl:if test="contains($QDSN0, ' %STP:') and not(starts-with($APIType, 'CARDPOINT'))">
				<xsl:text> %STP:</xsl:text>
				<xsl:value-of select="substring-after($QDSN0, ' %STP:')"/>
			</xsl:if>
		</xsl:variable>
		<xsl:value-of select="$OFURLM"/>
	</xsl:template>
</xsl:stylesheet>
