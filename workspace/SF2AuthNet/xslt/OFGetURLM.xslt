<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template name="tknz">
		<xsl:param name="instring"/>
		<xsl:param name="delim"/>
		<xsl:param name="indx"/>
		<xsl:param name="num"/>
		<xsl:choose>
			<xsl:when test="contains($instring, $delim)">
				<xsl:choose>
					<xsl:when test="number($indx)=number($num)">
						<xsl:value-of select="substring-before($instring, $delim)"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="tknz">
							<xsl:with-param name="instring" select="substring-after($instring, $delim)"/>
							<xsl:with-param name="delim" select="$delim"/>
							<xsl:with-param name="indx" select="number($indx) + 1"/>
							<xsl:with-param name="num" select="$num"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="number($indx)=number($num)">
						<xsl:value-of select="$instring"/>
					</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="AccIntURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='AccIntURL']"/>
		<xsl:variable name="TransactionFlowCounter" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionFlowCounter']"/>
		<xsl:variable name="AccountName">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__AccountName']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__AccountName']"/>
				</xsl:when>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CustomerName']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CustomerName']"/>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="ProductName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ProductName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ProductName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="RecordId0">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="RecordId">
			<xsl:choose>
				<xsl:when test="contains($RecordId0, ':')">
					<xsl:value-of select="substring-after($RecordId0, ':')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$RecordId0"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="ObjName">
			<xsl:choose>
				<xsl:when test="contains($RecordId0, ':')">
					<xsl:value-of select="substring-before($RecordId0, ':')"/>
				</xsl:when>
				<xsl:otherwise>Invoice</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="CurrentFlowId">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="OFURLM">
			<xsl:choose>
				<xsl:when test="contains($AccIntURL, ' %STP:')">
					<xsl:value-of select="substring-before($AccIntURL, ' %STP:')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$AccIntURL"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="contains($AccIntURL, '0/odata')">
					<xsl:choose>
						<xsl:when test="$RecordId!=''">
							<xsl:value-of select="$ObjName"/>|$Filter=Id eq <xsl:value-of select="$RecordId"/>
						</xsl:when>
						<xsl:when test="$CurrentFlowId='OSCAcct2SageN' or $CurrentFlowId='CreatioAcct2QBCustN'">Account<xsl:if test="$AccountName!=''">|$Filter=Name eq '<xsl:value-of select="$AccountName"/>'</xsl:if>
						</xsl:when>
						<xsl:when test="$CurrentFlowId='OCProd2NSItemN'">Product<xsl:if test="$ProductName!=''">|$Filter=Name eq '<xsl:value-of select="$ProductName"/>'</xsl:if>
						</xsl:when>
					</xsl:choose>
				</xsl:when>
				<xsl:when test="starts-with($CurrentFlowId, 'QBCust2OCAcct')">/accounts</xsl:when>
				<xsl:when test="contains($CurrentFlowId, 'OF') or contains($CurrentFlowId, 'OSC')">
					<xsl:text>/crmCommonSalesParties/SalesPartyService</xsl:text>
				</xsl:when>
				<xsl:when test="$CurrentFlowId='WCCustOrder2QBCustInv'">
					<xsl:variable name="WCClient" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
					<xsl:variable name="WCPwd" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFPswd']"/>
					<!--<xsl:text>wc/v2/orders?status=processing&amp;after=%start_date%T%start_time%&amp;per_page=100&amp;consumer_key=</xsl:text>-->
					<xsl:text>wc/v2/orders?status=completed&amp;after=%start_date%T%start_time%&amp;per_page=100&amp;consumer_key=</xsl:text>
					<xsl:value-of select="$WCClient"/>
					<xsl:text>&amp;consumer_secret=</xsl:text>
					<xsl:value-of select="$WCPwd"/>
				</xsl:when>
				<xsl:when test="contains($CurrentFlowId, 'WCCust') and contains($CurrentFlowId, 'N')">
					<xsl:variable name="WCClient" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
					<xsl:variable name="WCPwd" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFPswd']"/>
					<xsl:variable name="CustomerName0">
						<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CustomerName']">
							<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CustomerName']"/>
						</xsl:if>
					</xsl:variable>
					<xsl:variable name="CustomerName">
						<xsl:choose>
							<xsl:when test="contains($CustomerName0, ';')">
								<xsl:call-template name="tknz">
									<xsl:with-param name="instring" select="$CustomerName0"/>
									<xsl:with-param name="delim">;</xsl:with-param>
									<xsl:with-param name="indx">0</xsl:with-param>
									<xsl:with-param name="num" select="$TransactionFlowCounter"/>
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="$CustomerName0"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:variable name="CustomerNameNext">
						<xsl:if test="contains($CustomerName0, ';')">
							<xsl:call-template name="tknz">
								<xsl:with-param name="instring" select="$CustomerName0"/>
								<xsl:with-param name="delim">;</xsl:with-param>
								<xsl:with-param name="indx">0</xsl:with-param>
								<xsl:with-param name="num" select="$TransactionFlowCounter+1"/>
							</xsl:call-template>
						</xsl:if>
					</xsl:variable>
					<xsl:choose>
						<xsl:when test="contains($CustomerName0, ';') and $CustomerNameNext=''">%stop_scheduler%</xsl:when>
						<xsl:otherwise>
							<xsl:text>wc/v2/customers</xsl:text>
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
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:when test="contains($CurrentFlowId, 'WCProd') and contains($CurrentFlowId, 'N')">
					<xsl:variable name="WCClient" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
					<xsl:variable name="WCPwd" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFPswd']"/>
					<xsl:text>wc/v2/products</xsl:text>
					<xsl:choose>
						<xsl:when test="$ProductName='*'"/>
						<xsl:when test="starts-with($ProductName, '#')">
							<xsl:text>/</xsl:text>
							<xsl:value-of select="substring($ProductName, 2)"/>
						</xsl:when>
					</xsl:choose>
					<xsl:text>?consumer_key=</xsl:text>
					<xsl:value-of select="$WCClient"/>
					<xsl:text>&amp;consumer_secret=</xsl:text>
					<xsl:value-of select="$WCPwd"/>
				</xsl:when>
				<xsl:when test="contains($CurrentFlowId, 'WCOrder') and contains($CurrentFlowId, 'N')">
					<xsl:variable name="WCClient" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
					<xsl:variable name="WCPwd" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFPswd']"/>
					<xsl:variable name="OrderNumber0">
						<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__OrderNumber']">
							<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__OrderNumber']"/>
						</xsl:if>
					</xsl:variable>
					<xsl:variable name="OrderNumber">
						<xsl:choose>
							<xsl:when test="contains($OrderNumber0, ';')">
								<xsl:call-template name="tknz">
									<xsl:with-param name="instring" select="$OrderNumber0"/>
									<xsl:with-param name="delim">;</xsl:with-param>
									<xsl:with-param name="indx">0</xsl:with-param>
									<xsl:with-param name="num" select="$TransactionFlowCounter"/>
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="$OrderNumber0"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:variable name="OrderNumberNext">
						<xsl:if test="contains($OrderNumber0, ';')">
							<xsl:call-template name="tknz">
								<xsl:with-param name="instring" select="$OrderNumber0"/>
								<xsl:with-param name="delim">;</xsl:with-param>
								<xsl:with-param name="indx">0</xsl:with-param>
								<xsl:with-param name="num" select="$TransactionFlowCounter+1"/>
							</xsl:call-template>
						</xsl:if>
					</xsl:variable>
					<xsl:choose>
						<xsl:when test="contains($OrderNumber0, ';') and $OrderNumberNext=''">%stop_scheduler%</xsl:when>
						<xsl:otherwise>
							<xsl:text>wc/v2/orders</xsl:text>
							<xsl:choose>
								<xsl:when test="$OrderNumber='*'"/>
								<xsl:when test="starts-with($OrderNumber, '#')">
									<xsl:text>/</xsl:text>
									<xsl:value-of select="substring($OrderNumber, 2)"/>
								</xsl:when>
							</xsl:choose>
							<xsl:text>?consumer_key=</xsl:text>
							<xsl:value-of select="$WCClient"/>
							<xsl:text>&amp;consumer_secret=</xsl:text>
							<xsl:value-of select="$WCPwd"/>
						</xsl:otherwise>
					</xsl:choose>
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
