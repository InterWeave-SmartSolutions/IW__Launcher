<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TestReq">?</xsl:variable>
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
		<xsl:variable name="ProcessingMode0">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ProcessingMode']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ProcessingMode']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ProcessingMode">
			<xsl:choose>
				<xsl:when test="starts-with($CurrentFlowId,'SFTransactions2TSYSSt')">FROMSTORAGE</xsl:when>
				<xsl:when test="$ProcessingMode0=''">NORMAL</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$ProcessingMode0"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:if test="$ProcessingMode!='SETSTORAGE'">
			<xsl:variable name="ObjectName">
				<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']"/>
				</xsl:if>
			</xsl:variable>
			<xsl:variable name="CIMMode">
				<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CIMMode']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CIMMode']"/>
				</xsl:if>
			</xsl:variable>
			<xsl:variable name="POSMode">
				<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__POSMode']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__POSMode']"/>
				</xsl:if>
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
			<xsl:variable name="FileMode0">
				<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__FileMode']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__FileMode']"/>
				</xsl:if>
			</xsl:variable>
			<xsl:variable name="FileMode">
				<xsl:choose>
					<xsl:when test="$FileMode0!=''">
						<xsl:value-of select="$FileMode0"/>
					</xsl:when>
					<xsl:otherwise>SFTP</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<!--<xsl:variable name="APIURL" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN0']"/>
		<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr0']"/>
		<xsl:variable name="TransKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd0']"/>-->
			<xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'"/>
			<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
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
			<xsl:for-each select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[starts-with(@name,'QDSN')]">
				<xsl:variable name="QDSNG" select="@name"/>
				<xsl:variable name="QDSNGVal" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name=$QDSNG]"/>
				<xsl:if test="$QDSNGVal!='' and ($QDSNG='QDSN0' or $CIMMode!='0')">
					<xsl:variable name="SF">SFQBCompFlSelVl<xsl:value-of select="substring-after($QDSNG, 'QDSN')"/>
					</xsl:variable>
					<xsl:variable name="AL">QBIntUsr<xsl:value-of select="substring-after($QDSNG, 'QDSN')"/>
					</xsl:variable>
					<xsl:variable name="TK">QBPswd<xsl:value-of select="substring-after($QDSNG, 'QDSN')"/>
					</xsl:variable>
					<xsl:variable name="SFQBCompFlSelVlG" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name=$SF]"/>
					<xsl:variable name="CompFlSelVlGW">
						<xsl:if test="contains($SFQBCompFlSelVlG, '*')">
							<xsl:value-of select="translate($SFQBCompFlSelVlG, '*', '')"/>
						</xsl:if>
					</xsl:variable>
					<xsl:variable name="APILogin" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name=$AL]"/>
					<xsl:variable name="TransKey" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name=$TK]"/>
					<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
						<xsl:if test="row/col[@name='Id'] and (not(starts-with($CurrentFlowId, 'SFTransactions2TSYS')) or $SchedCCType!='Cust' or rowset[@name='Scheduled_Payments__r']/rowset[@name='records']/row/col[@name='Payment_Amount__c'])">
							<xsl:variable name="POSRN">
								<xsl:choose>
									<xsl:when test="$FileMode='FILE'">CCDATA.txt</xsl:when>
									<xsl:otherwise>/<xsl:value-of select="rowset[@name='Owner']/row/col[@name='Username']"/>/CCDATA.txt</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							<xsl:variable name="MultURL">
								<xsl:if test="$QBCompFilNum &gt; 1 and $OppSFQBCompFlSelNm!=''">
									<xsl:variable name="CFSel">
										<xsl:choose>
											<xsl:when test="contains($OppSFQBCompFlSelNm, '.')">
												<xsl:value-of select="rowset[@name=substring-before($OppSFQBCompFlSelNm,'.')]/row/col[@name=substring-after($OppSFQBCompFlSelNm,'.')]"/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="row/col[@name=$OppSFQBCompFlSelNm]"/>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:variable>
									<xsl:choose>
										<xsl:when test="($CFSel=$SFQBCompFlSelVlG or (contains($CFSel, ';') and contains($CFSel, $SFQBCompFlSelVlG)) or ($CompFlSelVlGW!='' and contains($CFSel, $CompFlSelVlGW)))">
											<xsl:value-of select="$QDSNGVal"/>
										</xsl:when>
										<xsl:otherwise>__%UNKNOWN_URL%__</xsl:otherwise>
									</xsl:choose>
								</xsl:if>
							</xsl:variable>
							<!--<xsl:variable name="SPNum">
								<xsl:if test="rowset[@name='Scheduled_Payments__r']/rowset[@name='records']/row/col[@name='Payment_Amount__c']">
									<xsl:value-of select="count(rowset[@name='Scheduled_Payments__r']/rowset[@name='records']/row/col[@name='Payment_Amount__c'])"/>
								</xsl:if>
							</xsl:variable>-->
							<xsl:if test="$MultURL!='__%UNKNOWN_URL%__'">
								<xsl:variable name="SFId" select="row/col[@name='Id']"/>
								<xsl:variable name="Name" select="row/col[@name='Name']"/>
								<!--<xsl:variable name="FirstName" select="row/col[@name='First_Name__c']"/>-->
								<xsl:variable name="FirstName">
									<xsl:choose>
										<xsl:when test="$ProcessingMode='FROMSTORAGE' and $AON!=''">
											<xsl:value-of select="rowset[@name=$AON]/row/col[@name='First_Name__c']"/>
										</xsl:when>
										<xsl:when test="$POSMode=''">
											<xsl:value-of select="row/col[@name='First_Name__c']"/>
										</xsl:when>
										<xsl:when test="starts-with($POSMode, '0')">
											<xsl:value-of select="substring-before(substring-after(//iwtransformationserver/iwrecordset/transaction/datamap[@name='getccftps']/data/row/col[@name=$POSRN], '/'), ' ')"/>
										</xsl:when>
									</xsl:choose>
								</xsl:variable>
								<!--<xsl:variable name="LastName" select="row/col[@name='Last_Name__c']"/>-->
								<xsl:variable name="LastName">
									<xsl:choose>
										<xsl:when test="$ProcessingMode='FROMSTORAGE' and $AON!=''">
											<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Last_Name__c']"/>
										</xsl:when>
										<xsl:when test="$POSMode=''">
											<xsl:value-of select="row/col[@name='Last_Name__c']"/>
										</xsl:when>
										<xsl:when test="starts-with($POSMode, '0')">
											<xsl:value-of select="substring-before(substring-after(//iwtransformationserver/iwrecordset/transaction/datamap[@name='getccftps']/data/row/col[@name=$POSRN], '^'), '/')"/>
										</xsl:when>
									</xsl:choose>
								</xsl:variable>
								<xsl:variable name="Charge0">
									<xsl:choose>
										<xsl:when test="starts-with($CurrentFlowId, 'SFTransactions2TSYS') and $SchedCCType='Cust'">
											<xsl:value-of select="string(rowset[@name='Scheduled_Payments__r']/rowset[@name='records']/row/col[@name='Payment_Amount__c']*100)"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="string(number(row/col[@name=$AmFN])*100)"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
								<xsl:variable name="Charge">
									<xsl:choose>
										<xsl:when test="contains($Charge0, '.')">
											<xsl:value-of select="substring-before($Charge0, '.')"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="$Charge0"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
								<!--<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>-->
								<xsl:variable name="CCNumber">
									<xsl:choose>
										<xsl:when test="$ProcessingMode='FROMSTORAGE' and $AON!=''">
											<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Credit_Card_Client__c']"/>
										</xsl:when>
										<xsl:when test="$POSMode=''">
											<xsl:value-of select="row/col[@name='Credit_Card_Client__c']"/>
										</xsl:when>
										<xsl:when test="starts-with($POSMode, '0')">
											<xsl:value-of select="substring-before(substring-after(//iwtransformationserver/iwrecordset/transaction/datamap[@name='getccftps']/data/row/col[@name=$POSRN], '%B'), '^')"/>
										</xsl:when>
									</xsl:choose>
								</xsl:variable>
								<!--<xsl:variable name="CCExpDate" select="row/col[@name='CC_Exp_Date_00_00__c']"/>-->
								<xsl:variable name="CCExpDate0">
									<xsl:choose>
										<xsl:when test="$ProcessingMode='FROMSTORAGE' and $AON!=''">
											<xsl:value-of select="rowset[@name=$AON]/row/col[@name='CC_Exp_Date_00_00__c']"/>
										</xsl:when>
										<xsl:when test="$POSMode=''">
											<xsl:value-of select="row/col[@name='CC_Exp_Date_00_00__c']"/>
										</xsl:when>
										<xsl:when test="starts-with($POSMode, '0')">
											<xsl:value-of select="substring(substring-after(//iwtransformationserver/iwrecordset/transaction/datamap[@name='getccftps']/data/row/col[@name=$POSRN], '='), 3, 2)"/>/<xsl:value-of select="substring(substring-after(//iwtransformationserver/iwrecordset/transaction/datamap[@name='getccftps']/data/row/col[@name=$POSRN], '='), 1, 2)"/>
										</xsl:when>
									</xsl:choose>
								</xsl:variable>
								<xsl:variable name="CCExpDate" select="translate($CCExpDate0, '/', '')"/>
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
								<xsl:variable name="CVM">
									<xsl:choose>
										<xsl:when test="$ProcessingMode='FROMSTORAGE' and $AON!=''">
											<xsl:value-of select="rowset[@name=$AON]/row/col[@name='CVM_Value__c']"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="row/col[@name='CVM_Value__c']"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
								<xsl:variable name="Phone0">
									<xsl:if test="$AON!=''">
										<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Phone']"/>
									</xsl:if>
								</xsl:variable>
								<xsl:variable name="Phone" select="translate(normalize-space(translate($Phone0,'+-()', '')), ' ', '')"/>
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
								<xsl:variable name="BankCheckNum" select="row/col[@name='Bank_Check_Number__c']"/>
								<!--<xsl:choose>
									<xsl:when test="$APIType='WS'">-->
								<xsl:if test="$APIType='WS'">
									<xsl:text>{   "merc": {     "id": "</xsl:text>
									<xsl:value-of select="$APILogin"/>
									<xsl:text>",     "regKey": "</xsl:text>
									<xsl:value-of select="$TransKey"/>
									<xsl:text>",     "inType": 1,     },   "tranCode": </xsl:text>
									<xsl:choose>
										<xsl:when test="$ACH='true'">
											<xsl:text>11,    },   "contact": {     "fullName": "John Doe",     "phone": [       {         "type": 3,         "nr": 5551114444,       },     ],     "addrLn1": "123 Test ST",     "city": "Denver",     "state": "CO",     "zipCode": "80021",     "email": "info@email.com",     "ship": {       "addrLn1": "Jane Doe",       "addrLn2": "123 Main St",       "city": "Denver",       "state": "CO",       "zipCode": "80021",       "phone": "3037779899",       "email": "support@emial.com",     }   },   "reqAmt": "123",   "indCode": 0, 
Integration Guide ACH Payments 
Version 2.10 Copyright 2016 TransFirst, LLC. All rights reserved. 155  
  "achEcheck": {     "bankRtNr": "121212120",     "acctNr": "987654321",     "acctType": 0,     "seccCode": 0,   }, }`</xsl:text>
										</xsl:when>
										<xsl:otherwise>
											<xsl:text>1,   "card": {     "pan": "</xsl:text>
											<xsl:value-of select="$CCNumber"/>
											<xsl:text>"</xsl:text>
											<xsl:if test="$CVM!=''">
												<xsl:text>,     "sec": "</xsl:text>
												<xsl:value-of select="$CVM"/>
												<xsl:text>"</xsl:text>
											</xsl:if>
											<xsl:text>,     "xprDt": "</xsl:text>
											<xsl:value-of select="$CCExpDate"/>
											<xsl:text>",     "cardInputType ": "0",     },   "contact": {     "fullName": "</xsl:text>
											<xsl:value-of select="concat($FirstName, ' ', $LastName)"/>
											<xsl:text>"</xsl:text>
											<xsl:if test="$Phone!=''">
												<xsl:text>,     "phone": [       {         "type": 3,         "nr": </xsl:text>
												<xsl:value-of select="$Phone"/>
												<xsl:text>,       },     ]</xsl:text>
											</xsl:if>
											<xsl:if test="$Address!=''">
												<xsl:text>,     "addrLn1": "</xsl:text>
												<xsl:value-of select="$Address"/>
												<xsl:text>"</xsl:text>
											</xsl:if>
											<xsl:if test="$City!=''">
												<xsl:text>,     "city": "</xsl:text>
												<xsl:value-of select="$City"/>
												<xsl:text>"</xsl:text>
											</xsl:if>
											<xsl:if test="$State!=''">
												<xsl:text>,     "state": "</xsl:text>
												<xsl:value-of select="$State"/>
												<xsl:text>"</xsl:text>
											</xsl:if>
											<xsl:if test="$PostCode!=''">
												<xsl:text>,     "zipCode": "</xsl:text>
												<xsl:value-of select="$PostCode"/>
												<xsl:text>"</xsl:text>
											</xsl:if>
											<xsl:if test="$Email!=''">
												<xsl:text>,     "email": "</xsl:text>
												<xsl:value-of select="$Email"/>
												<xsl:text>"</xsl:text>
											</xsl:if>
											<xsl:text>  },   "reqAmt": "</xsl:text>
											<xsl:value-of select="$Charge"/>
											<xsl:text>"</xsl:text>
											<xsl:text>,   }`</xsl:text>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:if>
								<!--</xsl:when>
									<xsl:when test="$APIType='POST'">GatewayID=<xsl:value-of select="$APILogin"/>&amp;RegKey=<xsl:value-of select="$TransKey"/>&amp;IndustryCode=2&amp;AccountNumber=<xsl:value-of select="$CCNumber"/>&amp;CVV2=<xsl:value-of select="$CVM"/>&amp;ExpirationDate=<xsl:value-of select="$CCExpDate"/>&amp;Track1Data=&amp;Track2Data=&amp;Amount=<xsl:value-of select="$Charge"/>&amp;TaxIndicator=&amp;TaxAmount=&amp;PONumber=&amp;ShipToZip=<xsl:value-of select="$PostCode"/>&amp;CustRefID=<xsl:value-of select="$SFId"/>&amp;FullName=<xsl:value-of select="concat($FirstName, ' ', $LastName)"/>&amp;Address1=<xsl:value-of select="$Address"/>&amp;Address2=&amp;City=<xsl:value-of select="$City"/>&amp;State=<xsl:value-of select="$State"/>&amp;Zip=<xsl:value-of select="$PostCode"/>&amp;PhoneNumber=<xsl:value-of select="$Phone"/>&amp;Email=<xsl:value-of select="$Email"/></xsl:when>
								</xsl:choose>-->
								<!--&amp;x_description=<xsl:value-of select="translate($Name, ',', '')"/>&amp;x_first_name=<xsl:value-of select="$FirstName"/>&amp;x_last_name=<xsl:value-of select="$LastName"/>&amp;x_company=<xsl:value-of select="$CompName"/>
								<xsl:if test="$Address!=''">&amp;x_address=<xsl:value-of select="$Address"/>
								</xsl:if>
								<xsl:if test="$City!=''">&amp;x_city=<xsl:value-of select="$City"/>
								</xsl:if>
								<xsl:if test="$State!=''">&amp;x_state=<xsl:value-of select="$State"/>
								</xsl:if>
								<xsl:if test="$Country!=''">&amp;x_country=<xsl:value-of select="$Country"/>
								</xsl:if>
								<xsl:if test="$Email!=''">&amp;x_email=<xsl:value-of select="$Email"/>
								</xsl:if>
								<xsl:if test="$Phone!=''">&amp;x_phone=<xsl:value-of select="$Phone"/>
								</xsl:if>&amp;x_zip=<xsl:value-of select="$PostCode"/>&amp;x_cust_id=<xsl:value-of select="$SFId"/>&amp;x_test_request=<xsl:value-of select="$TestReq"/>&amp;x_relay_response=FALSE&amp;`-->
							</xsl:if>
						</xsl:if>
					</xsl:for-each>
				</xsl:if>
			</xsl:for-each>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
