<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<!--<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr0']"/>
		<xsl:variable name="TransKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd0']"/>-->
		<xsl:variable name="TestReq">?</xsl:variable>
		<xsl:variable name="CreditMode0">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CreditMode']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CreditMode']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="CreditMode">
			<xsl:choose>
				<xsl:when test="$CreditMode0!=''">
					<xsl:value-of select="$CreditMode0"/>
				</xsl:when>
				<xsl:otherwise>CREDIT</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="QBCompFilNum0">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBCompFilNum']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBCompFilNum']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="QBCompFilNum">
			<xsl:choose>
				<xsl:when test="$QBCompFilNum0=''">1</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$QBCompFilNum0"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="OppSFQBCompFlSelNm">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='OppSFQBCompFlSelNm']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='OppSFQBCompFlSelNm']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:for-each select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[starts-with(@name,'QDSN')]">
			<xsl:variable name="QDSNG" select="@name"/>
			<xsl:variable name="QDSNGVal" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name=$QDSNG]"/>
			<xsl:if test="$QDSNGVal!=''">
				<xsl:variable name="SF">SFQBCompFlSelVl<xsl:value-of select="substring-after($QDSNG, 'QDSN')"/>
				</xsl:variable>
				<xsl:variable name="AL">QBIntUsr<xsl:value-of select="substring-after($QDSNG, 'QDSN')"/>
				</xsl:variable>
				<xsl:variable name="TK">QBPswd<xsl:value-of select="substring-after($QDSNG, 'QDSN')"/>
				</xsl:variable>
				<xsl:variable name="SFQBCompFlSelVlG" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name=$SF]"/>
				<xsl:variable name="CompFlSelVlGW">
					<xsl:if test="contains($SFQBCompFlSelVlG, '*')">
						<xsl:value-of select="translate($SFQBCompFlSelVlG, '*', '')"/>
					</xsl:if>
				</xsl:variable>
				<xsl:variable name="APILogin" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name=$AL]"/>
				<xsl:variable name="TransKey" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name=$TK]"/>
				<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
					<xsl:if test="row/col[@name='Id']">
						<xsl:variable name="MultURL">
							<xsl:if test="$QBCompFilNum &gt; 1 and $OppSFQBCompFlSelNm!=''">
								<xsl:variable name="CFSel">
									<xsl:choose>
										<xsl:when test="contains($OppSFQBCompFlSelNm, '.')">
											<xsl:value-of select="rowset[@name=substring-before($OppSFQBCompFlSelNm,'.')]/row/col[@name=substring-after($OppSFQBCompFlSelNm,'.')]"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="row/col[@name=$OppSFQBCompFlSelNm]"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
								<xsl:choose>
									<xsl:when test="($CFSel=$SFQBCompFlSelVlG or (contains($CFSel, ';') and contains($CFSel, $SFQBCompFlSelVlG)) or ($CompFlSelVlGW!='' and contains($CFSel, $CompFlSelVlGW)))">
										<xsl:value-of select="$QDSNGVal"/>
									</xsl:when>
									<xsl:otherwise>__%UNKNOWN_URL%__</xsl:otherwise>
								</xsl:choose>
							</xsl:if>
						</xsl:variable>
						<xsl:if test="$MultURL!='__%UNKNOWN_URL%__'">
							<xsl:variable name="TransId" select="row/col[@name='Auth_net_Transaction_ID__c']"/>
							<xsl:variable name="SFId" select="row/col[@name='Id']"/>
							<xsl:variable name="ParSFId" select="row/col[@name='Client__c']"/>
							<xsl:variable name="FirstName" select="rowset[@name='Client__r']/row/col[@name='First_Name__c']"/>
							<xsl:variable name="LastName" select="rowset[@name='Client__r']/row/col[@name='Last_Name__c']"/>
							<xsl:variable name="Charge" select="row/col[@name='Amount__c']"/>
							<xsl:variable name="CCNumber" select="rowset[@name='Client__r']/row/col[@name='Credit_Card_Client__c']"/>
							<xsl:variable name="Address" select="substring(rowset[@name='Client__r']/row/col[@name='Billing_Company_Street__c'], '1', '60')"/>
							<xsl:variable name="PostCode" select="rowset[@name='Client__r']/row/col[@name='Billing_Postal_Code__c']"/>x_delim_data=TRUE&amp;x_delim_char=,&amp;x_version=3.1&amp;x_login=<xsl:value-of select="$APILogin"/>&amp;x_tran_key=<xsl:value-of select="$TransKey"/>&amp;x_type=<xsl:value-of select="$CreditMode"/>&amp;x_trans_id=<xsl:value-of select="$TransId"/>&amp;x_first_name=<xsl:value-of select="$FirstName"/>&amp;x_last_name=<xsl:value-of select="$LastName"/>&amp;x_address=<xsl:value-of select="$Address"/>&amp;x_zip=<xsl:value-of select="$PostCode"/>&amp;x_amount=<xsl:value-of select="$Charge"/>&amp;x_card_num=<xsl:value-of select="$CCNumber"/>&amp;x_cust_id=<xsl:value-of select="$ParSFId"/>&amp;x_test_request=<xsl:value-of select="$TestReq"/>&amp;x_relay_response=FALSE&amp;</xsl:if>
					</xsl:if>
				</xsl:for-each>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
