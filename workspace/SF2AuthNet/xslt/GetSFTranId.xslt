<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
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
		<!--<xsl:variable name="CIMMode">
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
		</xsl:variable>-->
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
					<queryString>Select Id, Auth_net_Transaction_ID__c<xsl:if test="$APIType='CARDPOINT'">, Retrieval_Reference_Number__c</xsl:if>, Client__c, Amount__c<xsl:value-of select="$OppMCF"/>,  Client__r.Credit_Card_Client__c, Client__r.First_Name__c, Client__r.Last_Name__c, Client__r.Billing_Postal_Code__c, Client__r.Billing_Company_Street__c, Client__r.Billing_Email__c from Transaction__c where Approval_Status__c='Approved' and <xsl:value-of select="$IdorName"/>=?
						</queryString>
				</query>
			</soap:Body>
		</soap:Envelope>
	</xsl:template>
</xsl:stylesheet>