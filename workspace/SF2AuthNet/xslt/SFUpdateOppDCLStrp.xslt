<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:variable name="ProcessingMode0">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ProcessingMode']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ProcessingMode']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ProcessingMode">
			<xsl:choose>
				<xsl:when test="$ProcessingMode0=''">NORMAL</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$ProcessingMode0"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="ObjectName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="DecCharCur">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='DecCharCur']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='DecCharCur']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ON">
			<xsl:choose>
				<xsl:when test="$ObjectName='' and $DecCharCur=''">Opportunity</xsl:when>
				<xsl:when test="contains($DecCharCur, ':')">
					<xsl:value-of select="substring-before($DecCharCur, ':')"/>
				</xsl:when>
				<xsl:when test="$DecCharCur!=''">
					<xsl:value-of select="$DecCharCur"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$ObjectName"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$ProcessingMode='SETSTORAGE'">
				<xsl:variable name="AccountObjectName">
					<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__AccountObjectName']">
						<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__AccountObjectName']"/>
					</xsl:if>
				</xsl:variable>
				<xsl:variable name="AON">
					<xsl:choose>
						<xsl:when test="$ON='Opportunity' and $AccountObjectName=''">Account</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$AccountObjectName"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:if test="$AON!=''">
					<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
						<xsl:variable name="AcctId">
							<xsl:if test="rowset[@name=$AON]/row/col[@name='Id']">
								<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Id']"/>
							</xsl:if>
						</xsl:variable>
						<xsl:if test="$AcctId!=''">
							<xsl:variable name="CCN" select="row/col[@name='Credit_Card_Client__c']"/>
							<xsl:variable name="CCED" select="row/col[@name='CC_Exp_Date_00_00__c']"/>
							<xsl:variable name="CCCVM" select="row/col[@name='CVM_Value__c']"/>
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
												<xsl:value-of select="$AON"/>
											</type>
											<Id>
												<xsl:value-of select="$AcctId"/>
											</Id>
											<First_Name__c>
												<xsl:value-of select="row/col[@name='First_Name__c']"/>
											</First_Name__c>
											<Last_Name__c>
												<xsl:value-of select="row/col[@name='Last_Name__c']"/>
											</Last_Name__c>
											<Credit_Card_Client__c>
												<xsl:value-of select="$CCN"/>
											</Credit_Card_Client__c>
											<CC_Exp_Date_00_00__c>
												<xsl:value-of select="$CCED"/>
											</CC_Exp_Date_00_00__c>
											<CVM_Value__c>
												<xsl:value-of select="$CCCVM"/>
											</CVM_Value__c>
											<xsl:if test="$CCN!='' and $CCED!='' and $CCCVM!=''">
												<Credit_Card_Stored_H__c>1</Credit_Card_Stored_H__c>
											</xsl:if>
										</sObjects>
									</update>
								</soap:Body>
							</soap:Envelope>`</xsl:if>
					</xsl:for-each>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="NewStageName">
					<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__NewStageName']">
						<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__NewStageName']"/>
					</xsl:if>
				</xsl:variable>
				<xsl:variable name="NewStageValue">
					<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__NewStageValue']">
						<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__NewStageValue']"/>
					</xsl:if>
				</xsl:variable>
				<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/rowset[@name='o']">
					<!--<xsl:variable name="Result" select="."/>
					<xsl:if test="not(contains($Result, 'HTML'))">-->
					<xsl:variable name="R1" select="row/col[@name='status']"/>
					<xsl:variable name="R4">
						<xsl:if test="rowset[@name='Errors']">
							<xsl:value-of select="rowset[@name='Errors']/row/col[@name='ShortMessage']"/>
							<xsl:text>. </xsl:text>
							<xsl:value-of select="rowset[@name='Errors']/row/col[@name='LongMessage']"/>
						</xsl:if>
					</xsl:variable>
					<xsl:variable name="R7">
						<xsl:choose>
							<xsl:when test="rowset[@name='error']">
								<xsl:value-of select="rowset[@name='error']/row/col[@name='charge']"/>
							</xsl:when>
							<xsl:when test="row/col[@name='id']">
								<xsl:value-of select="row/col[@name='id']"/>
							</xsl:when>
						</xsl:choose>
					</xsl:variable>
					<xsl:variable name="R10">
						<xsl:choose>
							<xsl:when test="rowset[@name='error']">
								<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Gross_Monthly_Charge__c']"/>
							</xsl:when>
							<xsl:when test="row/col[@name='amount']">
								<xsl:value-of select="row/col[@name='amount'] div 100"/>
							</xsl:when>
						</xsl:choose>
					</xsl:variable>
					<xsl:variable name="R13" select="rowset[@name='metadata']/row/col[@name='cust_id']"/>
					<xsl:if test="$R13!=''">
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
						<xsl:variable name="ClientId">
							<xsl:choose>
								<xsl:when test="string-length($R13)=18">
									<xsl:value-of select="$R13"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="substring($R13, 1, 15)"/>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
						<xsl:choose>
							<xsl:when test="not(rowset[@name='error']) and $R1='succeeded'">
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
													<xsl:value-of select="$ClientId"/>
												</Id>
												<Number_of_Declines__c>0</Number_of_Declines__c>
												<Last_Authorized_Payment_Date__c>%current_date%</Last_Authorized_Payment_Date__c>
												<xsl:if test="$NewStageName!=''">
													<xsl:element name="{$NewStageName}">
														<xsl:value-of select="$NewStageValue"/>
													</xsl:element>
												</xsl:if>
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
													<xsl:value-of select="$ClientId"/>
												</Id>
												<Number_of_Declines__c>
													<xsl:value-of select="$Decl"/>
												</Number_of_Declines__c>
											</sObjects>
										</update>
									</soap:Body>
								</soap:Envelope>`<xsl:if test="not(rowset[@name='error'])">
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
												<xsl:when test="rowset[@name='error'] and rowset[@name='error']/row/col[@name='code']='card_declined'">Declined</xsl:when>
												<xsl:when test="rowset[@name='error']">Error</xsl:when>
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
															<xsl:value-of select="$R10"/>
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
					<!--</xsl:if>-->
				</xsl:for-each>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
