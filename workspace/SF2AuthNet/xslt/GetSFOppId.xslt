<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<!-- NORMAL - process CC from Object; FROMSTORAGE - process CC from parent obgect (__Param__AccountObjectName); SETSTORAGE - populate CC data to
            parent object (__Param__AccountObjectName)-->
		<xsl:variable name="CRM_PREFIX">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CRM_PREFIX']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CRM_PREFIX']"/>
			</xsl:if>
		</xsl:variable>
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
		<xsl:variable name="CIMMode">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CIMMode']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CIMMode']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ObjectName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="AccountObjectName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__AccountObjectName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__AccountObjectName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="InvoiceNumber">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__InvoiceNumber']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__InvoiceNumber']"/>
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
		<xsl:variable name="AON">
			<xsl:choose>
				<xsl:when test="$ON='Opportunity' and $AccountObjectName=''">Account</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$AccountObjectName"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="AddrPrfx">
			<xsl:choose>
				<xsl:when test="contains($AON, 'Contact')">Mailing</xsl:when>
				<xsl:otherwise>Billing</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="StreetAN" select="concat($AddrPrfx, 'Street')"/>
		<xsl:variable name="CityAN" select="concat($AddrPrfx, 'City')"/>
		<xsl:variable name="StateAN" select="concat($AddrPrfx, 'State')"/>
		<xsl:variable name="CountryAN" select="concat($AddrPrfx, 'Country')"/>
		<xsl:variable name="PostalCodeAN" select="concat($AddrPrfx, 'PostalCode')"/>
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
		<xsl:variable name="OppMCF">
			<xsl:if test="$QBCompFilNum &gt; 1 and $OppSFQBCompFlSelNm!=''">, <xsl:value-of select="$OppSFQBCompFlSelNm"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="Mapping010">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__Mapping01']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__Mapping01']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="Mapping01">
			<xsl:if test="$Mapping010!='' and contains($Mapping010, ':')">, <xsl:value-of select="substring-before($Mapping010, ':')"/>
			</xsl:if>
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
				<query xmlns="urn:partner.soap.sforce.com">
					<queryString>Select Id, Name, <xsl:value-of select="$CRM_PREFIX"/>Gross_Monthly_Charge__c, <xsl:if test="$ProcessingMode='FROMSTORAGE' and $AON!='' and $CIMMode=''">
							<xsl:value-of select="$AON"/>.</xsl:if>
						<xsl:value-of select="$CRM_PREFIX"/>Credit_Card_Client__c, <xsl:if test="$ProcessingMode='FROMSTORAGE' and $AON!='' and $CIMMode=''">
							<xsl:value-of select="$AON"/>.</xsl:if>
						<xsl:value-of select="$CRM_PREFIX"/>CC_Exp_Date_00_00__c, <xsl:if test="$ProcessingMode='FROMSTORAGE' and $AON!='' and $CIMMode=''">
							<xsl:value-of select="$AON"/>.</xsl:if>
						<xsl:value-of select="$CRM_PREFIX"/>First_Name__c, <xsl:if test="$ProcessingMode='FROMSTORAGE' and $AON!='' and $CIMMode=''">
							<xsl:value-of select="$AON"/>.</xsl:if>
						<xsl:value-of select="$CRM_PREFIX"/>Last_Name__c, <xsl:value-of select="$CRM_PREFIX"/>Number_of_Declines__c, <xsl:value-of select="$CRM_PREFIX"/>ACH__c, <xsl:value-of select="$CRM_PREFIX"/>Billing_Postal_Code__c, <xsl:value-of select="$CRM_PREFIX"/>Billing_Company_Street__c, <xsl:value-of select="$CRM_PREFIX"/>Billing_City__c, <xsl:value-of select="$CRM_PREFIX"/>Billing_State__c, <xsl:value-of select="$CRM_PREFIX"/>Billing_Country__c, <xsl:value-of select="$CRM_PREFIX"/>Billing_Email__c<xsl:if test="$CIMMode='' or $CIMMode='0'">, <xsl:if test="$ProcessingMode='FROMSTORAGE' and $AON!='' and $CIMMode=''">
								<xsl:value-of select="$AON"/>.</xsl:if>
							<xsl:value-of select="$CRM_PREFIX"/>CVM_Value__c</xsl:if>
						<xsl:value-of select="$OppMCF"/>
						<xsl:if test="$AON!=''">, <xsl:value-of select="$AON"/>.Id, <xsl:value-of select="$AON"/>.Name, <xsl:value-of select="$AON"/>.<xsl:value-of select="$CityAN"/>, <xsl:value-of select="$AON"/>.<xsl:value-of select="$CountryAN"/>, <xsl:value-of select="$AON"/>.<xsl:value-of select="$PostalCodeAN"/>, <xsl:value-of select="$AON"/>.<xsl:value-of select="$StateAN"/>, <xsl:value-of select="$AON"/>.<xsl:value-of select="$StreetAN"/>, <xsl:value-of select="$AON"/>.Phone</xsl:if>
						<xsl:if test="$ON='Account'">, BillingCity, BillingCountry, BillingPostalCode, BillingState, BillingStreet, Phone</xsl:if>, <xsl:value-of select="$CRM_PREFIX"/>Client_Acct__c, Routing__c, <xsl:value-of select="$CRM_PREFIX"/>Account_Type__c, <xsl:value-of select="$CRM_PREFIX"/>Total_Balance__c, <xsl:value-of select="$CRM_PREFIX"/>Bank_Name__c, <xsl:value-of select="$CRM_PREFIX"/>Echeck_Type__c, <xsl:value-of select="$CRM_PREFIX"/>Bank_Check_Number__c<xsl:if test="$CIMMode!=''">, Owner.Username<xsl:if test="$CIMMode!='0'">, <xsl:if test="starts-with($CIMMode, 'PT1') and $AON!=''">
									<xsl:value-of select="$AON"/>.</xsl:if>
								<xsl:value-of select="$CRM_PREFIX"/>Customer_Profile_Id__c, <xsl:if test="starts-with($CIMMode, 'PT1') and $AON!=''">
									<xsl:value-of select="$AON"/>.</xsl:if>
								<xsl:value-of select="$CRM_PREFIX"/>Customer_Payment_Profile_Id__c, <xsl:if test="starts-with($CIMMode, 'PT1') and $AON!=''">
									<xsl:value-of select="$AON"/>.</xsl:if>
								<xsl:value-of select="$CRM_PREFIX"/>CVM_Value__c</xsl:if>
						</xsl:if>
						<xsl:if test="$InvoiceNumber!=''">, <xsl:value-of select="$InvoiceNumber"/>
						</xsl:if>
						<xsl:value-of select="$Mapping01"/> from <xsl:value-of select="$ON"/> where <xsl:if test="$ProcessingMode!='SETSTORAGE'">
							<xsl:value-of select="$CRM_PREFIX"/>Last_Authorized_Payment_Date__c!=TODAY and </xsl:if>
						<xsl:value-of select="$IdorName"/>=?
						</queryString>
				</query>
			</soap:Body>
		</soap:Envelope>
	</xsl:template>
</xsl:stylesheet>
