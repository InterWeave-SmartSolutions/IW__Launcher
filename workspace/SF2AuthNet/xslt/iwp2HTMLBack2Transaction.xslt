<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="iso-8859-1" indent="yes" doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN" doctype-system="http://www.w3.org/TR/html4/loose.dtd"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="AccIntURL">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="SFId">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction/datamap[@name='createtransaction'] and iwrecordset/transaction/datamap[@name='createtransaction']/data/row/col[@name='success']='true'">
					<xsl:value-of select="iwrecordset/transaction/datamap[@name='createtransaction']/data/row/col[@name='id']"/>
				</xsl:when>
				<xsl:when test="iwrecordset/transaction/datamap[@name='createtransactionla'] and iwrecordset/transaction/datamap[@name='createtransactionla']/data/row/col[@name='success']='true'">
					<xsl:value-of select="iwrecordset/transaction/datamap[@name='createtransactionla']/data/row/col[@name='id']"/>
				</xsl:when>
				<xsl:otherwise>%P(SFTransactionId)%</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
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
