<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:variable name="ObjectName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ON">
			<xsl:choose>
				<xsl:when test="$ObjectName=''">Opportunity</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$ObjectName"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/row/col[@name='Result']">
			<xsl:variable name="Result" select="."/>
			<xsl:if test="not(contains($Result, 'HTML'))">
				<xsl:variable name="R1" select="substring-before(substring-after($Result, 'response='), '&amp;')"/>
				<xsl:variable name="R4" select="substring-before(substring-after($Result, 'responsetext='), '&amp;')"/>
				<xsl:variable name="R7" select="substring-before(substring-after($Result, 'transactionid='), '&amp;')"/>
				<xsl:variable name="R13" select="substring-before(substring-after($Result, 'orderid='), '&amp;')"/>
				<xsl:if test="$R13!=''">
					<xsl:variable name="R14"/>
					<xsl:variable name="R15"/>
					<xsl:variable name="Decl0">
						<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
							<xsl:if test="row/col[@name='Id']=$R13">
								<xsl:value-of select="row/col[@name='Number_of_Declines__c']"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:variable>
					<xsl:variable name="Decl">
						<xsl:choose>
							<xsl:when test="$Decl0!=''">
								<xsl:value-of select="$Decl0+1"/>
							</xsl:when>
							<xsl:otherwise>1</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:choose>
						<xsl:when test="$R1=1">
							<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
								<soap:Header>
									<QueryOptions xmlns="urn:partner.soap.sforce.com">
										<batchSize>2000</batchSize>
									</QueryOptions>
									<SessionHeader xmlns="urn:partner.soap.sforce.com">
										<sessionId>
											<xsl:value-of select="$SessionId"/>
										</sessionId>
									</SessionHeader>
									<CallOptions xmlns="urn:partner.soap.sforce.com">
										<client>IntegrationTechnologies/Interweave/</client>
									</CallOptions>
								</soap:Header>
								<soap:Body>
									<update xmlns="urn:partner.soap.sforce.com">
										<sObjects>
											<type xmlns="urn:sobject.partner.soap.sforce.com">
												<xsl:value-of select="$ON"/>
											</type>
											<Id>
												<xsl:value-of select="$R13"/>
											</Id>
											<Number_of_Declines__c>0</Number_of_Declines__c>
											<Last_Authorized_Payment_Date__c>%current_date%</Last_Authorized_Payment_Date__c>
										</sObjects>
									</update>
								</soap:Body>
							</soap:Envelope>`</xsl:when>
						<xsl:otherwise>
							<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
								<soap:Header>
									<QueryOptions xmlns="urn:partner.soap.sforce.com">
										<batchSize>2000</batchSize>
									</QueryOptions>
									<SessionHeader xmlns="urn:partner.soap.sforce.com">
										<sessionId>
											<xsl:value-of select="$SessionId"/>
										</sessionId>
									</SessionHeader>
									<CallOptions xmlns="urn:partner.soap.sforce.com">
										<client>IntegrationTechnologies/Interweave/</client>
									</CallOptions>
								</soap:Header>
								<soap:Body>
									<update xmlns="urn:partner.soap.sforce.com">
										<sObjects>
											<type xmlns="urn:sobject.partner.soap.sforce.com">
												<xsl:value-of select="$ON"/>
											</type>
											<Id>
												<xsl:value-of select="$R13"/>
											</Id>
											<Number_of_Declines__c>
												<xsl:value-of select="$Decl"/>
											</Number_of_Declines__c>
										</sObjects>
									</update>
								</soap:Body>
							</soap:Envelope>`<xsl:if test="$R1!=2">
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
									<xsl:variable name="ErrorType">
										<xsl:choose>
											<xsl:when test="$R1=3">error occured</xsl:when>
											<xsl:when test="$R1=4">is held for review</xsl:when>
										</xsl:choose>
									</xsl:variable>
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
														<xsl:value-of select="$R14"/>
														<xsl:text> </xsl:text>
														<xsl:value-of select="$R15"/>
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
						</xsl:otherwise>
					</xsl:choose>
				</xsl:if>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
