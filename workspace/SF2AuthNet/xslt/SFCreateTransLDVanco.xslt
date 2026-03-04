<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/rowset[@name='VancoWS']">
			<xsl:variable name="R1">
				<xsl:if test="rowset[@name='Response']/rowset[@name='Errors']">1</xsl:if>
			</xsl:variable>
			<xsl:if test="$R1!=''">
				<xsl:variable name="R13" select="rowset[@name='Auth']/row/col[@name='RequestID']"/>
				<xsl:variable name="R4" select="rowset[@name='Response']/rowset[@name='Errors']/rowset[@name='Error']/row/col[@name='ErrorDescription']"/>
				<xsl:variable name="DeclCom0">
					<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
						<xsl:if test="row/col[@name='Id']=$R13">
							<xsl:value-of select="row/col[@name='Reason_s_For_Denial__c']"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:variable>
				<xsl:variable name="DeclCom">
					<xsl:if test="$DeclCom0!=''">
						<xsl:value-of select="$DeclCom0"/>
						<xsl:text>
				</xsl:text>
					</xsl:if>
					<xsl:value-of select="$R4"/>
				</xsl:variable>
				<xsl:variable name="Auth0">
					<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
						<xsl:if test="row/col[@name='Id']=$R13">
							<xsl:value-of select="row/col[@name='Authorization_Attempts__c']"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:variable>
				<xsl:variable name="Auth">
					<xsl:choose>
						<xsl:when test="$Auth0!=''">
							<xsl:value-of select="$Auth0+1"/>
						</xsl:when>
						<xsl:otherwise>1</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
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
								<type xmlns="urn:sobject.partner.soap.sforce.com">Lead</type>
								<Id>
									<xsl:value-of select="$R13"/>
								</Id>
								<Reason_s_For_Denial__c>
									<xsl:value-of select="$DeclCom"/>
								</Reason_s_For_Denial__c>
								<Authorization_Attempts__c>
									<xsl:value-of select="$Auth"/>
								</Authorization_Attempts__c>
							</sObjects>
						</update>
					</soap:Body>
				</soap:Envelope>`</xsl:if>
		</xsl:for-each>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:variable name="R13" select="row/col[@name='Id']"/>
			<xsl:variable name="R3">
				<xsl:variable name="Charge" select="row/col[@name='Monthly_Charge__c']"/>
				<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
				<xsl:variable name="CCCVV" select="row/col[@name='CVV__c']"/>
				<xsl:variable name="CCT" select="number(substring($CCNumber, 1,1))"/>
				<xsl:variable name="CCType">
					<xsl:choose>
						<xsl:when test="$CCT=4">VISA</xsl:when>
						<xsl:when test="$CCT=5">MASTERCARD</xsl:when>
						<xsl:when test="$CCT=3">AMEX</xsl:when>
						<xsl:when test="$CCT=6">DISCOVER</xsl:when>
						<xsl:otherwise/>
					</xsl:choose>
				</xsl:variable>
				<xsl:choose>
					<xsl:when test="$Charge=''">Amount is missing</xsl:when>
					<xsl:when test="$CCNumber=''">CC Number is missing</xsl:when>
					<xsl:when test="$CCCVV=''">CC CVV is missing</xsl:when>
					<xsl:when test="$CCType=''">CC Type is not recognized</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:variable>
			<xsl:if test="$R3!=''">
				<xsl:variable name="DeclCom0" select="row/col[@name='Reason_s_For_Denial__c']"/>
				<xsl:variable name="DeclCom">
					<xsl:if test="$DeclCom0!=''">
						<xsl:value-of select="$DeclCom0"/>
						<xsl:text>
				</xsl:text>
					</xsl:if>
					<xsl:value-of select="$R3"/>
				</xsl:variable>
				<xsl:variable name="Auth01" select="row/col[@name='Authorization_Attempts__c']"/>
				<xsl:variable name="Auth1">
					<xsl:choose>
						<xsl:when test="$Auth01!=''">
							<xsl:value-of select="$Auth01+1"/>
						</xsl:when>
						<xsl:otherwise>1</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
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
								<type xmlns="urn:sobject.partner.soap.sforce.com">Lead</type>
								<Id>
									<xsl:value-of select="$R13"/>
								</Id>
								<Reason_s_For_Denial__c>
									<xsl:value-of select="$DeclCom"/>
								</Reason_s_For_Denial__c>
								<Authorization_Attempts__c>
									<xsl:value-of select="$Auth1"/>
								</Authorization_Attempts__c>
							</sObjects>
						</update>
					</soap:Body>
				</soap:Envelope>`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
