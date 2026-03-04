<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<!-- Modes - CP - Creatre/Update Profile; Starts from PT0 - Profile Id at the same level;  Starts from PT1 - Profile Id at the parent level; Ends wit C - Capture, A - Authorize, AC - Aut and Capture, PAC prior A and C, V - Void, R - Refund  -->
		<xsl:variable name="CIMMode">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CIMMode']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CIMMode']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="APILogin" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBIntUsr0']"/>
		<xsl:variable name="TransKey" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QBPswd0']"/>
		<xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'"/>
		<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
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
		</xsl:variable>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:if test="row/col[@name='Id']">
				<xsl:variable name="SFId" select="row/col[@name='Id']"/>
				<xsl:variable name="Name" select="row/col[@name='Name']"/>
				<xsl:variable name="FirstName" select="row/col[@name='First_Name__c']"/>
				<xsl:variable name="LastName" select="row/col[@name='Last_Name__c']"/>
				<xsl:variable name="Charge" select="row/col[@name='Gross_Monthly_Charge__c']"/>
				<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
				<xsl:variable name="CCType">
					<xsl:choose>
						<xsl:when test="starts-with($CCNumber, '4')">Visa</xsl:when>
						<xsl:when test="starts-with($CCNumber, '5')">MasterCard</xsl:when>
						<xsl:when test="starts-with($CCNumber, '3')">Amex</xsl:when>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="CCExpDate" select="row/col[@name='CC_Exp_Date_00_00__c']"/>
				<xsl:variable name="CCExpM">
					<xsl:choose>
						<xsl:when test="contains($CCExpDate, '/')">
							<xsl:value-of select="substring-before($CCExpDate, '/')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring($CCExpDate, 1, 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="CCExpY0">
					<xsl:choose>
						<xsl:when test="contains($CCExpDate, '/')">
							<xsl:value-of select="substring-after($CCExpDate, '/')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring($CCExpDate, 3, 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="CCExpY">
					<xsl:if test="number(string-length($CCExpY0))=number(2)">20</xsl:if>
					<xsl:value-of select="$CCExpY0"/>
				</xsl:variable>
				<xsl:variable name="CompName">
					<xsl:choose>
						<xsl:when test="$ON='Account'">
							<xsl:value-of select="row/col[@name='Name']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Name']"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Address00" select="substring(row/col[@name='Billing_Company_Street__c'], '1', '60')"/>
				<xsl:variable name="Address0">
					<xsl:choose>
						<xsl:when test="$Address00='' and $ON='Account'">
							<xsl:value-of select="substring(row/col[@name='BillingStreet'], '1', '60')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Address00"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Address">
					<xsl:choose>
						<xsl:when test="$Address0='' and rowset[@name=$AON]/row/col[@name='BillingStreet']">
							<xsl:value-of select="substring(rowset[@name=$AON]/row/col[@name='BillingStreet'], '1', '60')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Address0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="City00" select="row/col[@name='Billing_City__c']"/>
				<xsl:variable name="City0">
					<xsl:choose>
						<xsl:when test="$City00='' and $ON='Account'">
							<xsl:value-of select="row/col[@name='BillingCity']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$City00"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="City">
					<xsl:choose>
						<xsl:when test="$City0='' and rowset[@name=$AON]/row/col[@name='BillingCity']">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingCity']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$City0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="State00" select="row/col[@name='Billing_State__c']"/>
				<xsl:variable name="State0">
					<xsl:choose>
						<xsl:when test="$State00='' and $ON='Account'">
							<xsl:value-of select="row/col[@name='BillingState']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$State00"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="State">
					<xsl:choose>
						<xsl:when test="$State0='' and rowset[@name=$AON]/row/col[@name='BillingState']">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingState']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$State0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Country00" select="row/col[@name='Billing_Country__c']"/>
				<xsl:variable name="Country0">
					<xsl:choose>
						<xsl:when test="$Country00='' and $ON='Account'">
							<xsl:value-of select="row/col[@name='BillingCountry']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Country00"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Country">
					<xsl:choose>
						<xsl:when test="$Country0='' and rowset[@name=$AON]/row/col[@name='BillingCountry']">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingCountry']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Country0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="PostCode00" select="row/col[@name='Billing_Postal_Code__c']"/>
				<xsl:variable name="PostCode0">
					<xsl:choose>
						<xsl:when test="$PostCode00='' and $ON='Account'">
							<xsl:value-of select="row/col[@name='BillingPostalCode']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$PostCode00"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="PostCode">
					<xsl:choose>
						<xsl:when test="$PostCode0='' and rowset[@name=$AON]/row/col[@name='BillingPostalCode']">
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
						<xsl:when test="$ON='Account'">
							<xsl:value-of select="row/col[@name='CVM_Value__c']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='CVM_Value__c']"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Phone">
					<xsl:choose>
						<xsl:when test="$ON='Account'">
							<xsl:value-of select="row/col[@name='Phone']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Phone']"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="ACH" select="row/col[@name='ACH__c']"/>
				<xsl:variable name="TotBal" select="row/col[@name='Total_Balance__c']"/>
				<xsl:variable name="ABA" select="row/col[@name='Routing__c']"/>
				<xsl:variable name="AcctNum" select="row/col[@name='Client_Acct__c']"/>
				<xsl:variable name="AcctType" select="row/col[@name='Account_Type__c']"/>
				<xsl:variable name="BankName" select="row/col[@name='Bank_Name__c']"/>
				<xsl:variable name="ApprovalCode"/>
				<xsl:variable name="PriorAuthId"/>
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
				<xsl:variable name="ProfileId">
					<xsl:choose>
						<xsl:when test="starts-with($CIMMode, 'PT1') and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Customer_Profile_Id__c']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="row/col[@name='Customer_Profile_Id__c']"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="PaymentProfileId">
					<xsl:choose>
						<xsl:when test="starts-with($CIMMode, 'PT1') and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Customer_Payment_Profile_Id__c']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="row/col[@name='Customer_Payment_Profile_Id__c']"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="AddressProfileId">
					<xsl:choose>
						<xsl:when test="starts-with($CIMMode, 'PT1') and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Customer_Address_Profile_Id__c']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="row/col[@name='Customer_Address_Profile_Id__c']"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:if test="$CIMMode='CP' and $ProfileId!=''">
					<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
						<SOAP-ENV:Body>
							<m:UpdateCustomerProfile xmlns:m="https://api.authorize.net/soap/v1/">
								<m:merchantAuthentication>
									<m:name>
										<xsl:value-of select="$APILogin"/>
									</m:name>
									<m:transactionKey>
										<xsl:value-of select="$TransKey"/>
									</m:transactionKey>
								</m:merchantAuthentication>
								<m:profile>
									<m:merchantCustomerId>
										<xsl:value-of select="$SFId"/>
									</m:merchantCustomerId>
									<m:description>
										<xsl:value-of select="$CompName"/>
									</m:description>
									<m:email>
										<xsl:value-of select="$Email"/>
									</m:email>
									<m:customerProfileId>
										<xsl:value-of select="$ProfileId"/>
									</m:customerProfileId>
								</m:profile>
							</m:UpdateCustomerProfile>
						</SOAP-ENV:Body>
					</SOAP-ENV:Envelope>`</xsl:if>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
