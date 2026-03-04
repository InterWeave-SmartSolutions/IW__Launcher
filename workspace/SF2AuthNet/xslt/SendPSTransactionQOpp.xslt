<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TestReq">?</xsl:variable>
		<xsl:variable name="ObjectName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']"/>
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
		<xsl:variable name="OldSDKUsed">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='OldSDKUsed']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='OldSDKUsed']"/>
				</xsl:when>
				<xsl:otherwise>No</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="AmFN">
			<xsl:choose>
				<xsl:when test="$OldSDKUsed='Yes' and $CurrentFlowId='SFTransactionsO2Auth'">Recurring_Monthly_Payment_Amount__c</xsl:when>
				<xsl:otherwise>Gross_Monthly_Charge__c</xsl:otherwise>
			</xsl:choose>
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
		</xsl:variable>
		<xsl:variable name="APIURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN0']"/>
		<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr0']"/>
		<xsl:variable name="TransKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd0']"/>
		<xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'"/>
		<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:if test="row/col[@name='Id']">
				<xsl:variable name="SFId" select="row/col[@name='Id']"/>
				<xsl:variable name="Name" select="row/col[@name='Name']"/>
				<xsl:variable name="FirstName" select="row/col[@name='First_Name__c']"/>
				<xsl:variable name="LastName" select="row/col[@name='Last_Name__c']"/>
				<xsl:variable name="Charge" select="row/col[@name=$AmFN]"/>
				<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
				<xsl:variable name="CCExpDate" select="row/col[@name='CC_Exp_Date_00_00__c']"/>
				<xsl:variable name="CompName" select="rowset[@name=$AON]/row/col[@name='Name']"/>
				<xsl:variable name="Address0" select="substring(row/col[@name='Billing_Company_Street__c'], '1', '60')"/>
				<xsl:variable name="Address">
					<xsl:choose>
						<xsl:when test="$Address0='' and $AON!=''">
							<xsl:value-of select="substring(rowset[@name=$AON]/row/col[@name='BillingStreet'], '1', '60')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Address0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="City0" select="row/col[@name='Billing_City__c']"/>
				<xsl:variable name="City">
					<xsl:choose>
						<xsl:when test="$City0='' and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingCity']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$City0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="State0" select="row/col[@name='Billing_State__c']"/>
				<xsl:variable name="State">
					<xsl:choose>
						<xsl:when test="$State0='' and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingState']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$State0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Country0" select="row/col[@name='Billing_Country__c']"/>
				<xsl:variable name="Country">
					<xsl:choose>
						<xsl:when test="$Country0='' and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingCountry']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Country0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="PostCode0" select="row/col[@name='Billing_Postal_Code__c']"/>
				<xsl:variable name="PostCode">
					<xsl:choose>
						<xsl:when test="$PostCode0='' and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingPostalCode']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$PostCode0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Email" select="row/col[@name='Billing_Email__c']"/>
				<xsl:variable name="CVM" select="row/col[@name='CVM_Value__c']"/>
				<xsl:variable name="Phone">
					<xsl:if test="$AON!=''">
						<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Phone']"/>
					</xsl:if>
				</xsl:variable>
				<xsl:variable name="ACH" select="row/col[@name='ACH__c']"/>
				<xsl:variable name="TotBal" select="row/col[@name='Total_Balance__c']"/>
				<xsl:variable name="ABA" select="row/col[@name='Routing__c']"/>
				<xsl:variable name="AcctNum" select="row/col[@name='Client_Acct__c']"/>
				<xsl:variable name="AcctType" select="row/col[@name='Account_Type__c']"/>
				<xsl:variable name="BankName" select="row/col[@name='Bank_Name__c']"/>
				<xsl:variable name="BankAcctName">
					<xsl:value-of select="$FirstName"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="$LastName"/>
				</xsl:variable>
				<xsl:variable name="ECheckType0" select="row/col[@name='Echeck_Type__c']"/>
				<xsl:variable name="ECheckType">
					<xsl:choose>
						<xsl:when test="$ECheckType0=''">WEB</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$ECheckType0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="BankCheckNum" select="row/col[@name='Bank_Check_Number__c']"/>type=sale&amp;username=<xsl:value-of select="$APILogin"/>&amp;password=<xsl:value-of select="$TransKey"/>&amp;<xsl:choose>
					<xsl:when test="$ACH='true'">payment=check&amp;amount=<xsl:value-of select="$TotBal"/>&amp;checkaba=<xsl:value-of select="$ABA"/>&amp;checkaccount=<xsl:value-of select="$AcctNum"/>&amp;account_type=<xsl:value-of select="translate($AcctType, $lowercase, $uppercase)"/>&amp;x_bank_name=<xsl:value-of select="$BankName"/>&amp;x_bank_acct_name=<xsl:value-of select="$BankAcctName"/>&amp;x_echeck_type=<xsl:value-of select="translate($ECheckType, $lowercase, $uppercase)"/>
						<xsl:if test="$BankCheckNum!=''">&amp;x_bank_check_number=<xsl:value-of select="$BankCheckNum"/>
						</xsl:if>&amp;x_recurring_billing=FALSE</xsl:when>
					<xsl:otherwise>payment=creditcard&amp;<xsl:if test="$CVM!=''">cvv=<xsl:value-of select="$CVM"/>
						</xsl:if>&amp;amount=<xsl:value-of select="$Charge"/>&amp;ccnumber=<xsl:value-of select="$CCNumber"/>&amp;ccexp=<xsl:value-of select="$CCExpDate"/>
					</xsl:otherwise>
				</xsl:choose>&amp;orderdescription=<xsl:value-of select="translate($Name, ',', '')"/>&amp;firstname=<xsl:value-of select="$FirstName"/>&amp;lastname=<xsl:value-of select="$LastName"/>&amp;<xsl:if test="$CompName!=''">company=<xsl:value-of select="$CompName"/>
				</xsl:if>
				<xsl:if test="$Address!=''">&amp;address1=<xsl:value-of select="$Address"/>
				</xsl:if>
				<xsl:if test="$City!=''">&amp;city=<xsl:value-of select="$City"/>
				</xsl:if>
				<xsl:if test="$State!=''">&amp;state=<xsl:value-of select="$State"/>
				</xsl:if>
				<xsl:if test="$Country!=''">&amp;country=<xsl:value-of select="$Country"/>
				</xsl:if>
				<xsl:if test="$Email!=''">&amp;email=<xsl:value-of select="$Email"/>
				</xsl:if>
				<xsl:if test="$Phone!=''">&amp;phone=<xsl:value-of select="$Phone"/>
				</xsl:if>&amp;zip=<xsl:value-of select="$PostCode"/>&amp;orderid=<xsl:value-of select="$SFId"/>`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
