<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='refundResponse']/rowset[@name='refundReturn']/rowset[@name='wddxPacket']/rowset[@name='data']/rowset[@name='struct']">
			<xsl:variable name="R1">
				<xsl:for-each select="rowset[@name='var']">
					<xsl:if test="row/col[@name='name_at_var']='TRANSACTIONSTATUSTEXT'">
						<xsl:value-of select="row/col[@name='string']"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:variable>
			<xsl:if test="$R1!='SUCCESSFUL'">
				<xsl:variable name="R4">
					<xsl:for-each select="rowset[@name='var']">
						<xsl:if test="row/col[@name='name_at_var']='ERRORMESSAGE'">
							<xsl:value-of select="row/col[@name='string']"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:variable>
				<xsl:variable name="R13">?</xsl:variable>
				<xsl:variable name="To">
					<xsl:choose>
						<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='UseAdmEml']='Yes'">
							<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CCEmail']"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="CC">
					<xsl:if test="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='UseAdmEml']='Yes'">
						<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CCEmail']"/>
					</xsl:if>
				</xsl:variable>
				<xsl:variable name="BCC" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='BCCEmail']"/>
				<xsl:if test="$To!='' or $CC!='' or $BCC!=''">
					<xsl:variable name="ErrorType">error occured</xsl:variable>
					<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
						<soap:Header>
							<SessionHeader xmlns="urn:partner.soap.sforce.com">
								<sessionId>
									<xsl:value-of select="$SessionId"/>
								</sessionId>
							</SessionHeader>
						</soap:Header>
						<soap:Body>
							<sendEmail xmlns="urn:partner.soap.sforce.com">
								<messages xsi:type="SingleEmailMessage">
									<bccSender>false</bccSender>
									<emailPriority>High</emailPriority>
									<subject>Credit card transaction error</subject>
									<useSignature>false</useSignature>
									<toAddresses>
										<xsl:value-of select="$To"/>
									</toAddresses>
									<ccAddresses>
										<xsl:value-of select="$CC"/>
									</ccAddresses>
									<bccAddresses>
										<xsl:value-of select="$BCC"/>
									</bccAddresses>
									<plainTextBody>
										<xsl:text>Hello, 
												The credit card charge for </xsl:text>
										<xsl:value-of select="$R13"/>
										<xsl:text> failed. The transaction</xsl:text>
										<xsl:value-of select="$ErrorType"/>
										<xsl:text>
											</xsl:text>
										<xsl:value-of select="$R4"/>
										<xsl:text>
											
												Thanks.</xsl:text>
									</plainTextBody>
								</messages>
							</sendEmail>
						</soap:Body>
					</soap:Envelope>`</xsl:if>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
