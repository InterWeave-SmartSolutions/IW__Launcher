<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TestReq">?</xsl:variable>
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
				<xsl:variable name="Charge" select="row/col[@name='Gross_Monthly_Charge__c']"/>
				<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
				<xsl:variable name="CCExpDate" select="row/col[@name='CC_Exp_Date_00_00__c']"/>
				<xsl:variable name="CompName" select="rowset[@name='Account']/row/col[@name='Name']"/>
				<xsl:variable name="Address0" select="substring(row/col[@name='Billing_Company_Street__c'], '1', '60')"/>
				<xsl:variable name="Address">
					<xsl:choose>
						<xsl:when test="$Address0=''">
							<xsl:value-of select="substring(rowset[@name='Account']/row/col[@name='BillingStreet'], '1', '60')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Address0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="City0" select="row/col[@name='Billing_City__c']"/>
				<xsl:variable name="City">
					<xsl:choose>
						<xsl:when test="$City0=''">
							<xsl:value-of select="rowset[@name='Account']/row/col[@name='BillingCity']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$City0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="State0" select="row/col[@name='Billing_State__c']"/>
				<xsl:variable name="State">
					<xsl:choose>
						<xsl:when test="$State0=''">
							<xsl:value-of select="rowset[@name='Account']/row/col[@name='BillingState']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$State0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Country0" select="row/col[@name='Billing_Country__c']"/>
				<xsl:variable name="Country">
					<xsl:choose>
						<xsl:when test="$Country0=''">
							<xsl:value-of select="rowset[@name='Account']/row/col[@name='BillingCountry']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Country0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="PostCode0" select="row/col[@name='Billing_Postal_Code__c']"/>
				<xsl:variable name="PostCode">
					<xsl:choose>
						<xsl:when test="$PostCode0=''">
							<xsl:value-of select="rowset[@name='Account']/row/col[@name='BillingPostalCode']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$PostCode0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Email" select="row/col[@name='Billing_Email__c']"/>
				<xsl:variable name="CVM" select="row/col[@name='CVM_Value__c']"/>
				<xsl:variable name="Phone" select="rowset[@name='Account']/row/col[@name='Phone']"/>
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
				<xsl:text>%P(ElavonURL)%?xmldata=&lt;txn&gt;&lt;ssl_merchant_ID&gt;</xsl:text>
				<xsl:value-of select="$APIURL"/>
				<xsl:text>&lt;/ssl_merchant_ID&gt;&lt;ssl_user_id&gt;</xsl:text>
				<xsl:value-of select="$APILogin"/>
				<xsl:text>&lt;/ssl_user_id&gt;&lt;ssl_pin&gt;</xsl:text>
				<xsl:value-of select="$TransKey"/>
				<xsl:text>&lt;/ssl_pin&gt;&lt;ssl_transaction_type&gt;ccsale&lt;/ssl_transaction_type&gt;&lt;ssl_card_number&gt;</xsl:text>
				<xsl:value-of select="$CCNumber"/>
				<xsl:text>&lt;/ssl_card_number&gt;&lt;ssl_exp_date&gt;</xsl:text>
				<xsl:value-of select="$CCExpDate"/>
				<xsl:text>&lt;/ssl_exp_date&gt;&lt;ssl_amount&gt;</xsl:text>
				<xsl:value-of select="format-number($Charge, '0.00')"/>
				<xsl:text>&lt;/ssl_amount&gt;&lt;ssl_salestax&gt;0.00&lt;/ssl_salestax&gt;&lt;ssl_cvv2cvc2_indicator&gt;</xsl:text>
				<xsl:choose>
					<xsl:when test="$CVM!=''">1</xsl:when>
					<xsl:otherwise>9</xsl:otherwise>
				</xsl:choose>
				<xsl:text>&lt;/ssl_cvv2cvc2_indicator&gt;&lt;ssl_cvv2cvc2&gt;</xsl:text>
				<xsl:value-of select="$CVM"/>
				<xsl:text>&lt;/ssl_cvv2cvc2&gt;&lt;ssl_invoice_number/&gt;&lt;ssl_customer_code&gt;</xsl:text>
				<xsl:value-of select="$SFId"/>
				<xsl:text>&lt;/ssl_customer_code&gt;&lt;ssl_first_name&gt;</xsl:text>
				<xsl:value-of select="$FirstName"/>
				<xsl:text>&lt;/ssl_first_name&gt;&lt;ssl_last_name&gt;</xsl:text>
				<xsl:value-of select="$LastName"/>
				<xsl:text>&lt;/ssl_last_name&gt;&lt;ssl_avs_address&gt;</xsl:text>
				<xsl:value-of select="$Address"/>
				<xsl:text>&lt;/ssl_avs_address&gt;&lt;ssl_address2/&gt;&lt;ssl_city&gt;</xsl:text>
				<xsl:value-of select="$City"/>
				<xsl:text>&lt;/ssl_city&gt;&lt;ssl_state&gt;</xsl:text>
				<xsl:value-of select="$State"/>
				<xsl:text>&lt;/ssl_state&gt;&lt;ssl_avs_zip&gt;</xsl:text>
				<xsl:value-of select="$PostCode"/>
				<xsl:text>&lt;/ssl_avs_zip&gt;&lt;ssl_phone&gt;</xsl:text>
				<xsl:value-of select="$Phone"/>
				<xsl:text>&lt;/ssl_phone&gt;&lt;ssl_email&gt;</xsl:text>
				<xsl:value-of select="$Email"/>
				<xsl:text>&lt;/ssl_email&gt;&lt;/txn&gt;</xsl:text>
				<xsl:text>&lt;ssl_test_mode&gt;%P(TestMode)%&lt;/ssl_test_mode&gt;</xsl:text>
				<!--x_delim_data=TRUE&amp;x_delim_char=,&amp;x_version=3.1&amp;x_login=<xsl:value-of select="$APILogin"/>&amp;x_tran_key=<xsl:value-of select="$TransKey"/>&amp;<xsl:choose>
					<xsl:when test="$ACH='true'">x_method=ECHECK&amp;x_amount=<xsl:value-of select="$TotBal"/>&amp;x_bank_aba_code=<xsl:value-of select="$ABA"/>&amp;x_bank_acct_num=<xsl:value-of select="$AcctNum"/>&amp;x_bank_acct_type=<xsl:value-of select="translate($AcctType, $lowercase, $uppercase)"/>&amp;x_bank_name=<xsl:value-of select="$BankName"/>&amp;x_bank_acct_name=<xsl:value-of select="$BankAcctName"/>&amp;x_echeck_type=<xsl:value-of select="translate($ECheckType, $lowercase, $uppercase)"/>
						<xsl:if test="$BankCheckNum!=''">&amp;x_bank_check_number=<xsl:value-of select="$BankCheckNum"/>
						</xsl:if>&amp;x_recurring_billing=FALSE</xsl:when>
					<xsl:otherwise>
						<xsl:if test="$CVM!=''">&amp;x_card_code=<xsl:value-of select="$CVM"/>
						</xsl:if>&amp;x_amount=<xsl:value-of select="$Charge"/>&amp;x_card_num=<xsl:value-of select="$CCNumber"/>&amp;x_exp_date=<xsl:value-of select="$CCExpDate"/>
					</xsl:otherwise>
				</xsl:choose>&amp;x_description=<xsl:value-of select="$Name"/>&amp;x_first_name=<xsl:value-of select="$FirstName"/>&amp;x_last_name=<xsl:value-of select="$LastName"/>&amp;x_company=<xsl:value-of select="$CompName"/>&amp;x_address=<xsl:value-of select="$Address"/>
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
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
