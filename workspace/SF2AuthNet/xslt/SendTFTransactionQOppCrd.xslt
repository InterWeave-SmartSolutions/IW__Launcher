<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TestReq">?</xsl:variable>
		<xsl:variable name="APIURI" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN0']"/>
		<xsl:variable name="APIID" select="substring-before($APIURI, ':')"/>
		<xsl:variable name="APIRK" select="substring-after($APIURI, ':')"/>
		<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr0']"/>
		<xsl:variable name="TransKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd0']"/>
		<xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'"/>
		<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
		<xsl:variable name="Charge00" select="translate(iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TranAmt'], '$', '')"/>
		<xsl:variable name="Charge01">
			<xsl:choose>
				<xsl:when test="contains($Charge00, '.')">
					<xsl:variable name="CA" select="substring-after($Charge00, '.')"/>
					<xsl:value-of select="substring-before($Charge00, '.')"/>
					<xsl:value-of select="$CA"/>
					<xsl:choose>
						<xsl:when test="string-length($CA)=0">00</xsl:when>
						<xsl:when test="string-length($CA)=1">0</xsl:when>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$Charge00"/>00</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="Charge0" select="translate($Charge01, ',','')"/>
		<xsl:variable name="Charge1">00000000<xsl:value-of select="$Charge0"/>
		</xsl:variable>
		<xsl:variable name="Charge" select="substring($Charge1, string-length($Charge0)+1)"/>
		<xsl:variable name="TranNumber" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TranNum']"/>
		<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
			<SOAP-ENV:Body>
				<m:SendTranRequest xmlns:m="http://postilion/realtime/merchantframework/xsd/v1/">
					<m:merc>
						<m:id>
							<xsl:value-of select="$APILogin"/>
						</m:id>
						<m:regKey>
							<xsl:value-of select="$TransKey"/>
						</m:regKey>
						<m:inType>1</m:inType>
						<m:prodType>5</m:prodType>
					</m:merc>
					<m:tranCode>4</m:tranCode>
					<m:reqAmt>
						<xsl:value-of select="$Charge"/>
					</m:reqAmt>
					<m:origTranData>
						<m:tranNr>
							<xsl:value-of select="$TranNumber"/>
						</m:tranNr>
					</m:origTranData>
				</m:SendTranRequest>
			</SOAP-ENV:Body>
		</SOAP-ENV:Envelope>`</xsl:template>
</xsl:stylesheet>
