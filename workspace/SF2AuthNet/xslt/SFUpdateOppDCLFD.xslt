<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:variable name="SFId" select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Id']"/>
		<xsl:variable name="SFAmt" select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Gross_Monthly_Charge__c']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/row">
			<xsl:variable name="R1" select="col[@name='r_approved']"/>
			<xsl:variable name="R4" select="col[@name='r_message']"/>
			<xsl:variable name="R7" select="col[@name='r_ref']"/>
			<xsl:variable name="R10" select="$SFAmt"/>
			<xsl:variable name="R13" select="$SFId"/>
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
				<xsl:when test="$R1='APPROVED'">
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
									<type xmlns="urn:sobject.partner.soap.sforce.com">Opportunity</type>
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
									<type xmlns="urn:sobject.partner.soap.sforce.com">Opportunity</type>
									<Id>
										<xsl:value-of select="$R13"/>
									</Id>
									<Number_of_Declines__c>
										<xsl:value-of select="$Decl"/>
									</Number_of_Declines__c>
								</sObjects>
							</update>
						</soap:Body>
					</soap:Envelope>`<xsl:if test="$R1!='DECLINED'">
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
							<xsl:variable name="ErrorType">is held for review</xsl:variable>
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
												The credit card charge failed. The transaction</xsl:text>
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
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>