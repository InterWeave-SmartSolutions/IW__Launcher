<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="CVJName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CustomerFullName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CustomerFullName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="CurrentFlowId">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="AccIntURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']"/>
		<xsl:variable name="OFURLM">
			<xsl:variable name="URLShort">
				<xsl:choose>
					<xsl:when test="contains($AccIntURL, ' %STP:')">
						<xsl:value-of select="substring-before($AccIntURL, ' %STP:')"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$AccIntURL"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:choose>
				<xsl:when test="contains($AccIntURL, '0/odata')">
					<xsl:value-of select="concat(substring-before($URLShort, '0/odata'), 'ServiceModel/AuthService.svc/Login')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$URLShort"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<!--<xsl:when test="starts-with($CurrentFlowId, 'QBCust2OCAcct')">/customers/742</xsl:when>-->
				<xsl:when test="starts-with($CurrentFlowId, 'QBCust2OCAcct') and starts-with($CVJName, '/')">
					<xsl:value-of select="$CVJName"/>
				</xsl:when>
				<xsl:when test="starts-with($CurrentFlowId, 'QBCust2OCAcct')">/customers</xsl:when>
				<xsl:when test="(contains($CurrentFlowId, 'OF') or contains($CurrentFlowId, 'OSC')) and not(contains($AccIntURL, '0/odata'))">
					<xsl:text>/crmCommonSalesParties/AccountService</xsl:text>
				</xsl:when>
				<xsl:when test="$CurrentFlowId='WCCustOrder2QBCustInv'">
					<xsl:variable name="WCClient" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
					<xsl:variable name="WCPwd" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFPswd']"/>
					<xsl:text>wc/v2/orders?status=completed&amp;per_page=5&amp;consumer_key=</xsl:text>
					<xsl:value-of select="$WCClient"/>
					<xsl:text>&amp;consumer_secret=</xsl:text>
					<xsl:value-of select="$WCPwd"/>
				</xsl:when>
				<xsl:when test="contains($CurrentFlowId, 'WCCust')">
					<!--<xsl:variable name="WCClient" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
					<xsl:variable name="WCPwd" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFPswd']"/>
					<xsl:for-each select="iwrecordset/transaction/datamap[@name='findacct']/data/rowset[@name='o']">
						<xsl:variable name="SFId" select="row/col[@name='id']"/>
						<xsl:if test="$SFId!=''">
							<xsl:text>%URL=</xsl:text>
							<xsl:value-of select="$AccIntURL"/>
							<xsl:text>acf/v3/users/</xsl:text>
							<xsl:value-of select="$SFId"/>
							<xsl:variable name="CustomerName">
								<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CustomerName']">
									<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CustomerName']"/>
								</xsl:if>
							</xsl:variable>
							<xsl:choose>
								<xsl:when test="$CustomerName='*'"/>
								<xsl:when test="starts-with($CustomerName, '#')">
									<xsl:text>/</xsl:text>
									<xsl:value-of select="substring($CustomerName, 2)"/>
								</xsl:when>
							</xsl:choose>
							<xsl:text>?consumer_key=</xsl:text>
							<xsl:value-of select="$WCClient"/>
							<xsl:text>&amp;consumer_secret=</xsl:text>
							<xsl:value-of select="$WCPwd"/>
							<xsl:text>%`</xsl:text>
						</xsl:if>
					</xsl:for-each>-->
				</xsl:when>
				<xsl:when test="contains($CurrentFlowId, 'WC')">
					<!--<xsl:variable name="WCClient" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
					<xsl:variable name="WCPwd" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFPswd']"/>
					<xsl:for-each select="iwrecordset/transaction/datamap[@name='findacct']/data/rowset[@name='o']">
						<xsl:variable name="SFId" select="row/col[@name='id']"/>
						<xsl:if test="$SFId!=''">
							<xsl:text>%URL=</xsl:text>
							<xsl:value-of select="$AccIntURL"/>
							<xsl:text>acf/v3/users/</xsl:text>
							<xsl:value-of select="$SFId"/>
							<xsl:variable name="CustomerName">
								<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CustomerName']">
									<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CustomerName']"/>
								</xsl:if>
							</xsl:variable>
							<xsl:choose>
								<xsl:when test="$CustomerName='*'"/>
								<xsl:when test="starts-with($CustomerName, '#')">
									<xsl:text>/</xsl:text>
									<xsl:value-of select="substring($CustomerName, 2)"/>
								</xsl:when>
							</xsl:choose>
							<xsl:text>?consumer_key=</xsl:text>
							<xsl:value-of select="$WCClient"/>
							<xsl:text>&amp;consumer_secret=</xsl:text>
							<xsl:value-of select="$WCPwd"/>
							<xsl:text>%`</xsl:text>
						</xsl:if>
					</xsl:for-each>-->
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:value-of select="$OFURLM"/>
		<xsl:if test="contains($AccIntURL, ' %STP:')">
			<xsl:text> %STP:</xsl:text>
			<xsl:value-of select="substring-after($AccIntURL, ' %STP:')"/>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>