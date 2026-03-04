<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="DBLQ">"</xsl:variable>
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:variable name="TransactionSourceName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="CurrentFlowId">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']"/>
				</xsl:when>
				<xsl:otherwise>SugarCO2QBBillNQQ</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="UseIdorName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__UseIdorName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__UseIdorName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="IdorName">
			<xsl:choose>
				<xsl:when test="$UseIdorName='Name'">Name</xsl:when>
				<xsl:otherwise>Id</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="APIType00">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__APIType']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__APIType']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="APIType0">
			<xsl:choose>
				<xsl:when test="contains($APIType00, ':')">
					<xsl:value-of select="substring-before($APIType00, ':')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$APIType00"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="DSName">
			<xsl:choose>
				<xsl:when test="contains($APIType00, ':')">payauthinfo</xsl:when>
				<xsl:otherwise>payauth</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="SchedCCType">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SchedCCType']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SchedCCType']"/>
				</xsl:when>
				<xsl:otherwise>None</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="APIType">
			<xsl:choose>
				<xsl:when test="$APIType0!=''">
					<xsl:value-of select="$APIType0"/>
				</xsl:when>
				<xsl:when test="$SchedCCType='Cust'">POST</xsl:when>
				<xsl:otherwise>WS</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
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
		<xsl:variable name="TrnName">
			<xsl:choose>
				<xsl:when test="$APIType='WS'">PayTSYSQOpp</xsl:when>
				<xsl:when test="$APIType='POST' and $CreditMode0!=''">PayTSYSTQCQ</xsl:when>
				<xsl:when test="$APIType='POST'">PayTSYSQOppP</xsl:when>
				<xsl:when test="starts-with($APIType, 'CARDPOINT')">PayCardPQOpp</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="ProcessingMode0">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ProcessingMode']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ProcessingMode']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ProcessingMode">
			<xsl:choose>
				<xsl:when test="starts-with($CurrentFlowId,'SFTransactions2TSYSSt') or starts-with($CurrentFlowId,'SFTransactions2CardPSt')">FROMSTORAGE</xsl:when>
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
				<xsl:choose>
					<xsl:when test="$APIType='WS'"/>
					<xsl:when test="$APIType='POST'">
						<xsl:for-each select="iwrecordset/transaction[@name=$TrnName]/datamap[@name=$DSName]/data/row/col[@name='Result']">
							<xsl:variable name="Pos" select="position()"/>
							<xsl:variable name="Result" select="concat(translate(., $DBLQ, ''), '&amp;')"/>
							<xsl:if test="not(contains($Result, 'HTML'))">
								<xsl:variable name="R1" select="substring-before($Result, '&amp;')"/>
								<xsl:variable name="R1c">
									<xsl:value-of select="$R1"/>&amp;</xsl:variable>
								<xsl:variable name="R1a" select="substring-after($Result, $R1c)"/>
								<xsl:variable name="R2" select="substring-before($R1a, '&amp;')"/>
								<xsl:variable name="R2c">
									<xsl:value-of select="$R2"/>&amp;</xsl:variable>
								<xsl:variable name="R2a" select="substring-after($R1a, $R2c)"/>
								<xsl:variable name="R3" select="substring-before($R2a, '&amp;')"/>
								<xsl:variable name="R3c">
									<xsl:value-of select="$R3"/>&amp;</xsl:variable>
								<xsl:variable name="R3a" select="substring-after($R2a, $R3c)"/>
								<xsl:variable name="R4" select="substring-before($R3a, '&amp;')"/>
								<xsl:variable name="R4c">
									<xsl:value-of select="$R4"/>&amp;</xsl:variable>
								<xsl:variable name="R4a" select="substring-after($R3a, $R4c)"/>
								<xsl:variable name="R5" select="substring-before($R4a, '&amp;')"/>
								<xsl:variable name="R5c">
									<xsl:value-of select="$R5"/>&amp;</xsl:variable>
								<xsl:variable name="R5a" select="substring-after($R4a, $R5c)"/>
								<xsl:variable name="R6" select="substring-before($R5a, '&amp;')"/>
								<xsl:variable name="R6c">
									<xsl:value-of select="$R6"/>&amp;</xsl:variable>
								<xsl:variable name="R6a" select="substring-after($R5a, $R6c)"/>
								<xsl:variable name="R7" select="substring-before($R6a, '&amp;')"/>
								<xsl:variable name="R7c">
									<xsl:value-of select="$R7"/>&amp;</xsl:variable>
								<xsl:variable name="R7a" select="substring-after($R6a, $R7c)"/>
								<xsl:variable name="R8" select="substring-before($R7a, '&amp;')"/>
								<xsl:variable name="R8c">
									<xsl:value-of select="$R8"/>&amp;</xsl:variable>
								<xsl:variable name="R8a" select="substring-after($R7a, $R8c)"/>
								<xsl:variable name="R9" select="substring-before($R8a, '&amp;')"/>
								<xsl:variable name="R9c">
									<xsl:value-of select="$R9"/>&amp;</xsl:variable>
								<xsl:variable name="R9a" select="substring-after($R8a, $R9c)"/>
								<xsl:variable name="R10" select="substring-before($R9a, '&amp;')"/>
								<!--<xsl:variable name="R10c">
									<xsl:value-of select="$R10"/>,</xsl:variable>
								<xsl:variable name="R10a" select="substring-after($R9a, $R10c)"/>
								<xsl:variable name="R11" select="substring-before($R10a, ',')"/>
								<xsl:variable name="R11c">
									<xsl:value-of select="$R11"/>,</xsl:variable>
								<xsl:variable name="R11a" select="substring-after($R10a, $R11c)"/>
								<xsl:variable name="R12" select="substring-before($R11a, ',')"/>
								<xsl:variable name="R12c">
									<xsl:value-of select="$R12"/>,</xsl:variable>
								<xsl:variable name="R12a" select="substring-after($R11a, $R12c)"/>
								<xsl:variable name="R13" select="substring-before($R12a, ',')"/>
								<xsl:if test="$R13!=''">
									<xsl:variable name="R13c">
										<xsl:value-of select="$R13"/>,</xsl:variable>
									<xsl:variable name="R13a" select="substring-after($R12a, $R13c)"/>
									<xsl:variable name="R14" select="substring-before($R13a, ',')"/>
									<xsl:variable name="R14c">
										<xsl:value-of select="$R14"/>,</xsl:variable>
									<xsl:variable name="R14a" select="substring-after($R13a, $R14c)"/>
									<xsl:variable name="R15" select="substring-before($R14a, ',')"/>-->
								<xsl:variable name="Decl0">
									<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
										<!--<xsl:if test="row/col[@name='Id']=$R13">-->
										<xsl:value-of select="row/col[@name='Number_of_Declines__c']"/>
										<!--</xsl:if>-->
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
								<xsl:variable name="TrnId">
									<xsl:choose>
										<xsl:when test="contains($APIType00, ':') and substring-before($R1, '=')='tranNr' and starts-with(substring-after($R4, '='), 'Approved')">
											<xsl:value-of select="substring-after($R1, '=')"/>
										</xsl:when>
										<xsl:when test="not(contains($APIType00, ':')) and substring-before($R1, '=')='ResponseCode'">
											<xsl:value-of select="substring-after($R2, '=')"/>
										</xsl:when>
										<xsl:otherwise>0</xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
								<xsl:variable name="ClientId0">
									<xsl:choose>
										<xsl:when test="contains($APIType00, ':') and substring-before($R2, '=')='CustRefID'">
											<xsl:value-of select="substring-after($R2, 'CustRefID=')"/>
										</xsl:when>
										<xsl:when test="not(contains($APIType00, ':')) and //iwtransformationserver/iwrecordset/transaction[@name=$TrnName]/datamap[@name='payauthinfo']/data/row/col[@name='Result']">
											<xsl:variable name="ResT">
												<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction[@name=$TrnName]/datamap[@name='payauthinfo']/data/row/col[@name='Result']">
													<xsl:variable name="ResultI" select="concat(translate(., $DBLQ, ''), '&amp;')"/>
													<xsl:if test="not(contains($ResultI, 'HTML'))">
														<xsl:variable name="R1I" select="substring-before($ResultI, '&amp;')"/>
														<xsl:variable name="R1Ic">
															<xsl:value-of select="$R1I"/>&amp;</xsl:variable>
														<xsl:variable name="R1Ia" select="substring-after($ResultI, $R1Ic)"/>
														<xsl:variable name="R2I" select="substring-before($R1Ia, '&amp;')"/>
														<xsl:if test="number(substring-after($R1I, 'tranNr='))=number($TrnId)">
															<xsl:value-of select="substring-after($R2I, 'CustRefID=')"/>;</xsl:if>
													</xsl:if>
												</xsl:for-each>
											</xsl:variable>
											<xsl:value-of select="substring-before($ResT, ';')"/>
										</xsl:when>
										<xsl:when test="not(contains($APIType00, ':')) and $CreditMode0!=''">
											<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Client__c']"/>
										</xsl:when>
										<xsl:when test="not(contains($APIType00, ':')) and $IdorName!='Id'">
											<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Id']"/>
										</xsl:when>
										<xsl:when test="not(contains($APIType00, ':')) and $TransactionSourceName!=''">
											<xsl:value-of select="$TransactionSourceName"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records' and position()=$Pos]/row/col[@name='Id']"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
								<xsl:variable name="ClientId">
									<xsl:choose>
										<xsl:when test="string-length($ClientId0)=18">
											<xsl:value-of select="$ClientId0"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="substring($ClientId0, 1, 15)"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
								<xsl:choose>
									<xsl:when test="(contains($APIType00, ':') and substring-before($R4, '=')='Status' and starts-with(substring-after($R4, '='), 'Approved')) or (not(contains($APIType00, ':')) and substring-before($R1, '=')='ResponseCode' and substring-after($R1, '=')='00')">
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
										</soap:Envelope>`<!--<xsl:if test="(substring-before($R1, '=')='ResponseCode' and substring-after($R1, '=')='06') or substring-before($R1, '=')='ErrorCode'">
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
											</xsl:if>-->
									</xsl:otherwise>
								</xsl:choose>
							</xsl:if>
							<!--</xsl:if>-->
						</xsl:for-each>
					</xsl:when>
					<xsl:when test="starts-with($APIType, 'CARDPOINT')">
						<xsl:for-each select="iwrecordset/transaction[@name=$TrnName]/datamap[@name=$DSName]/data/rowset[@name='o']/row">
							<xsl:variable name="ClientId0" select="col[@name='orderId']"/>
							<xsl:variable name="ClientId">
								<xsl:choose>
									<xsl:when test="string-length($ClientId0)=18">
										<xsl:value-of select="$ClientId0"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="substring($ClientId0, 1, 15)"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							<xsl:variable name="Decl0" select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row[col[@name='Id']=$ClientId]/col[@name='Number_of_Declines__c']"/>
							<xsl:variable name="Decl">
								<xsl:choose>
									<xsl:when test="$Decl0!=''">
										<xsl:value-of select="$Decl0+1"/>
									</xsl:when>
									<xsl:otherwise>1</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							<xsl:choose>
								<xsl:when test="number(col[@name='respcode'])=0">
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
									</soap:Envelope>`</xsl:otherwise>
							</xsl:choose>
							<!--</xsl:if>-->
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
