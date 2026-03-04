<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="FileMode0">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__FileMode']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__FileMode']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="FileMode">
			<xsl:choose>
				<xsl:when test="$FileMode0!=''">
					<xsl:value-of select="$FileMode0"/>
				</xsl:when>
				<xsl:otherwise>SFTP</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="APIURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN1']"/>
		<!--<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr0']"/>
		<xsl:variable name="APIKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd0']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='getacctcontall']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse' or @name='queryMoreResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:variable name="SFCustomerNo" select="row/col[@name='Sage_Customer_Number__c']"/>
			<xsl:variable name="SFCustomerDiv" select="row/col[@name='Sage_Customer_Division__c']"/>-->
		<xsl:variable name="SFO" select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/rowset[@name='Owner']/row/col[@name='Username']"/>
		<xsl:variable name="SFOwner">
			<xsl:choose>
				<xsl:when test="$FileMode='FILE' and contains($SFO, '@')">
					<xsl:value-of select="substring-before($SFO, '@')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$SFO"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:value-of select="concat($APIURL, $SFOwner, '/CCDATA.txt')"/>
		<!--</xsl:for-each>-->
	</xsl:template>
</xsl:stylesheet>
