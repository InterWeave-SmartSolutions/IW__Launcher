<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="CurrentFlowId">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']"/>
				</xsl:when>
				<xsl:otherwise>SugarCO2QBBillNQQ</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="ProcessingMode">
			<xsl:choose>
				<xsl:when test="starts-with($CurrentFlowId,'SFTransactions2TSYSSt') or starts-with($CurrentFlowId,'SFTransactions2CardPSt')">FROMSTORAGE</xsl:when>
				<xsl:otherwise>NORMAL</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="OldSDKUsed">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='OldSDKUsed']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='OldSDKUsed']"/>
				</xsl:when>
				<xsl:otherwise>No</xsl:otherwise>
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
		<xsl:variable name="StrdCCSpprt">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='StrdCCSpprt']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='StrdCCSpprt']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="DecCharCur">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='DecCharCur']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='DecCharCur']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="CIMMode">PT1AC</xsl:variable>
		<xsl:variable name="ObjectName">
			<xsl:choose>
				<xsl:when test="contains($DecCharCur, ':')">
					<xsl:value-of select="substring-before($DecCharCur, ':')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$DecCharCur"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="AccountObjectName">
			<xsl:if test="contains($DecCharCur, ':')">
				<xsl:value-of select="substring-after($DecCharCur, ':')"/>
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
		<xsl:variable name="AmFN">
			<xsl:choose>
				<xsl:when test="$OldSDKUsed='Yes' and starts-with($CurrentFlowId,'SFTransactionsO2Auth')">Recurring_Monthly_Payment_Amount__c</xsl:when>
				<xsl:otherwise>Gross_Monthly_Charge__c</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="CurrentDay" select="iwrecordset/transaction/datamap[@name='getsftime']/data/row/col[@name='currentday']"/>
		<xsl:variable name="CurrentDay3" select="iwrecordset/transaction/datamap[@name='getsftime3']/data/row/col[@name='currentday3']"/>
		<xsl:variable name="CurrentDay6" select="iwrecordset/transaction/datamap[@name='getsftime6']/data/row/col[@name='currentday6']"/>
		<xsl:variable name="DaysPrevMonth" select="iwrecordset/transaction/datamap[@name='getmonthdays']/data/row/col[@name='monthday']"/>
		<xsl:variable name="Q0">
			<xsl:if test="$CurrentDay='01'">
				<xsl:choose>
					<xsl:when test="$DaysPrevMonth=28">or Card_Bills_On_2__c like '29%' or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=29"> or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=30"> or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="Q1">
			<xsl:if test="$CurrentDay='04'">
				<xsl:choose>
					<xsl:when test="$DaysPrevMonth=28">or Card_Bills_On_2__c like '29%' or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=29"> or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=30"> or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="Q2">
			<xsl:if test="$CurrentDay='07'">
				<xsl:choose>
					<xsl:when test="$DaysPrevMonth=28">or Card_Bills_On_2__c like '29%' or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=29"> or Card_Bills_On_2__c like '30%' or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:when test="$DaysPrevMonth=30"> or Card_Bills_On_2__c like '31%'</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="SessionId" select="iwrecordset/transaction[@name='SFLogin_CM']/datamap[@name='login']/data/row/col[@name='sessionId']"/>
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
					<queryString>
						<xsl:choose>
							<xsl:when test="contains($CurrentFlowId, 'CIM')">Select Id, Name, <xsl:value-of select="$AmFN"/>, Credit_Card_Client__c, CC_Exp_Date_00_00__c, First_Name__c, Last_Name__c, Number_of_Declines__c, ACH__c, Billing_Postal_Code__c, Billing_Company_Street__c, Billing_City__c, Billing_State__c, Billing_Country__c, Billing_Email__c<xsl:if test="$CIMMode=''">, CVM_Value__c</xsl:if>
								<xsl:value-of select="$OppMCF"/>
								<xsl:if test="$AON!=''">, <xsl:value-of select="$AON"/>.Name, <xsl:value-of select="$AON"/>.BillingCity, <xsl:value-of select="$AON"/>.BillingCountry, <xsl:value-of select="$AON"/>.BillingPostalCode, <xsl:value-of select="$AON"/>.BillingState, <xsl:value-of select="$AON"/>.BillingStreet, <xsl:value-of select="$AON"/>.Phone</xsl:if>
								<xsl:if test="$ON='Account'">, BillingCity, BillingCountry, BillingPostalCode, BillingState, BillingStreet, Phone</xsl:if>, Client_Acct__c, Routing__c, Account_Type__c, Total_Balance__c, Bank_Name__c, Echeck_Type__c, Bank_Check_Number__c<xsl:if test="$CIMMode!=''">, <xsl:if test="starts-with($CIMMode, 'PT1') and $AON!=''">
										<xsl:value-of select="$AON"/>.</xsl:if>Customer_Profile_Id__c, <xsl:if test="starts-with($CIMMode, 'PT1') and $AON!=''">
										<xsl:value-of select="$AON"/>.</xsl:if>Customer_Payment_Profile_Id__c, <xsl:if test="starts-with($CIMMode, 'PT1') and $AON!=''">
										<xsl:value-of select="$AON"/>.</xsl:if>CVM_Value__c</xsl:if>
								<xsl:value-of select="$Mapping01"/> from <xsl:value-of select="$ON"/>
							</xsl:when>
							<xsl:otherwise>Select Id<xsl:if test="$SchedCCType='Cust'">, (Select Payment_Amount__c from Scheduled_Payments__r where Payment_Date__c=TODAY and Use_Stored_CC__c=<xsl:choose>
										<xsl:when test="$CurrentFlowId='SFTransactions2TSYSSt' or $CurrentFlowId='SFTransactions2CardPSt'">TRUE</xsl:when>
										<xsl:otherwise>FALSE</xsl:otherwise>
									</xsl:choose>)</xsl:if>, Name, <xsl:value-of select="$AmFN"/>, <xsl:if test="$ProcessingMode='FROMSTORAGE' and $AON!=''">
									<xsl:value-of select="$AON"/>.</xsl:if>Credit_Card_Client__c, <xsl:if test="$ProcessingMode='FROMSTORAGE' and $AON!=''">
									<xsl:value-of select="$AON"/>.</xsl:if>CC_Exp_Date_00_00__c, <xsl:if test="$ProcessingMode='FROMSTORAGE' and $AON!=''">
									<xsl:value-of select="$AON"/>.</xsl:if>First_Name__c, <xsl:if test="$ProcessingMode='FROMSTORAGE' and $AON!=''">
									<xsl:value-of select="$AON"/>.</xsl:if>Last_Name__c, Number_of_Declines__c, ACH__c, Billing_Postal_Code__c, Billing_Company_Street__c, Billing_City__c, Billing_State__c, Billing_Country__c, Billing_Email__c<xsl:value-of select="$OppMCF"/>, <xsl:if test="$ProcessingMode='FROMSTORAGE' and $AON!=''">
									<xsl:value-of select="$AON"/>.</xsl:if>CVM_Value__c, <xsl:value-of select="$AON"/>.Name, <xsl:value-of select="$AON"/>.BillingCity, <xsl:value-of select="$AON"/>.BillingCountry, <xsl:value-of select="$AON"/>.BillingPostalCode, <xsl:value-of select="$AON"/>.BillingState, <xsl:value-of select="$AON"/>.BillingStreet, <xsl:value-of select="$AON"/>.Phone, Client_Acct__c, Routing__c, Account_Type__c, Total_Balance__c, Bank_Name__c, Echeck_Type__c, Bank_Check_Number__c<xsl:value-of select="$Mapping01"/> from <xsl:value-of select="$ON"/>
							</xsl:otherwise>
						</xsl:choose> where Last_Authorized_Payment_Date__c!=TODAY and (Client_Status__c='Active' or Client_Status__c='Cancel No Pay') and Special_Billing_2__c=false and <xsl:if test="contains($CurrentFlowId, 'SFTransactions2TSYS') or contains($CurrentFlowId,'SFTransactions2CardP')">ACH__c=<xsl:choose>
								<xsl:when test="contains($CurrentFlowId, 'ACH')">true</xsl:when>
								<xsl:otherwise>false</xsl:otherwise>
							</xsl:choose> and </xsl:if>
						<xsl:choose>
							<xsl:when test="$SchedCCType='Cust'">Payment_Start__c &lt;= TODAY and Payment_End__c &gt;= TODAY</xsl:when>
							<xsl:otherwise>(Card_Bills_On_2__c like '<xsl:value-of select="$CurrentDay"/>%'<xsl:value-of select="$Q0"/> or (Number_of_Declines__c=1 and (Card_Bills_On_2__c like '<xsl:value-of select="$CurrentDay3"/>%'<xsl:value-of select="$Q1"/>)) or (Number_of_Declines__c=2 and (Card_Bills_On_2__c like '<xsl:value-of select="$CurrentDay6"/>%'<xsl:value-of select="$Q2"/>)))</xsl:otherwise>
						</xsl:choose>
						<xsl:if test="$SchedCCType!='Cust'"> and <xsl:choose>
								<xsl:when test="$CurrentFlowId='SFTransactionsO2AuthES'">(Check_if_Recurring_Monthly_Payment__c=true or Check_if_Recurring_Quarterly_Payment__c=true or Check_if_Recurring_Semi-Annual_Payment__c=true or Check_if_Recurring_Annual_Payment__c=true)</xsl:when>
								<xsl:otherwise>Check_if_Recurring_Monthly_Payment__c=true</xsl:otherwise>
							</xsl:choose> and Recurring_Payments_Start_Month__c&lt;=TODAY and Recurring_Payments_End_Month__c&gt;TODAY</xsl:if>
					</queryString>
				</query>
			</soap:Body>
		</soap:Envelope>
	</xsl:template>
</xsl:stylesheet>
