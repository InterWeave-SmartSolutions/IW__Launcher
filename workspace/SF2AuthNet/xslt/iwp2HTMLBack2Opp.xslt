<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="iso-8859-1" indent="yes" doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN" doctype-system="http://www.w3.org/TR/html4/loose.dtd"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="AccIntURL">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="UseIdorName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__UseIdorName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__UseIdorName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="TransactionSourceName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ReturnToURL">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ReturnToURL']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ReturnToURL']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ReturnToURLP">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ReturnToURLP']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ReturnToURLP']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ReturnToURLV">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ReturnToURLV']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ReturnToURLV']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ReturnToURLId">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ReturnToURLId']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ReturnToURLId']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ContactID">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ContactID']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ContactID']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="SFId0">
			<xsl:if test="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Id']">
				<xsl:value-of select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Id']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="SFId">
			<xsl:choose>
				<xsl:when test="$SFId0!=''">
					<xsl:value-of select="$SFId0"/>
				</xsl:when>
				<xsl:when test="$UseIdorName='Id'">
					<xsl:value-of select="$TransactionSourceName"/>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="SFURL">
			<xsl:choose>
				<xsl:when test="$ReturnToURL!=''">
					<xsl:value-of select="$ReturnToURL"/>
					<xsl:if test="$ReturnToURLP!='' or ReturnToURLId!='' or ContactID!=''">?</xsl:if>
					<xsl:if test="$ReturnToURLP!=''">
						<xsl:value-of select="$ReturnToURLP"/>=<xsl:value-of select="$ReturnToURLV"/>
					</xsl:if>
					<xsl:if test="$ReturnToURLId!='' and $SFId!=''">
						<xsl:if test="$ReturnToURLP!=''">&amp;</xsl:if>
						<xsl:value-of select="$ReturnToURLId"/>=<xsl:value-of select="$SFId"/>
					</xsl:if>
					<xsl:if test="$ContactID!=''">
						<xsl:if test="$ReturnToURLP!='' or $ReturnToURLId!=''">&amp;</xsl:if>ContactID=<xsl:value-of select="$ContactID"/>
					</xsl:if>
				</xsl:when>
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
			<xsl:if test="$ReturnToURL=''">
				<xsl:text>.salesforce.com/</xsl:text>
				<xsl:value-of select="$SFId"/>
			</xsl:if>
		</xsl:variable>
		<html>
			<head>
				<!--			<meta HTTP-EQUIV="REFRESH" content="0; url=https://na5.salesforce.com/00Q/o"/>-->
			</head>
			<body onload="top.location.href='{$SFURL}'"/>
		</html>
	</xsl:template>
</xsl:stylesheet>
