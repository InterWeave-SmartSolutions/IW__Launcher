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
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='getquotes']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='get_entryResponse' or @name='get_entry_listResponse']/rowset[@name='return']/rowset[@name='entry_list']/rowset[@name='item']/rowset[@name='name_value_list']">
			<xsl:if test="rowset[@name='item']/row[col[@name='name']='id']/col[@name='value']">
				<xsl:variable name="SFId" select="rowset[@name='item']/row[col[@name='name']='id']/col[@name='value']"/>
				<xsl:variable name="Name" select="rowset[@name='item']/row[col[@name='name']='name']/col[@name='value']"/>
				<xsl:variable name="FirstName" select="rowset[@name='item']/row[col[@name='name']='first_name_c']/col[@name='value']"/>
				<xsl:variable name="LastName" select="rowset[@name='item']/row[col[@name='name']='last_name_c']/col[@name='value']"/>
				<xsl:variable name="Charge" select="rowset[@name='item']/row[col[@name='name']='gross_monthly_charge_c']/col[@name='value']"/>
				<xsl:variable name="CCNumber" select="rowset[@name='item']/row[col[@name='name']='credit_card_client_c']/col[@name='value']"/>
				<xsl:variable name="CCExpDate" select="rowset[@name='item']/row[col[@name='name']='cc_exp_date_00_00_c']/col[@name='value']"/>
				<xsl:variable name="CompName" select="rowset[@name='item']/row[col[@name='name']='customer_account_name_c']/col[@name='value']"/>
				<xsl:variable name="Address" select="substring(rowset[@name='item']/row[col[@name='name']='billing_company_street_c']/col[@name='value'], '1', '60')"/>
				<!--<xsl:variable name="Address">
					<xsl:choose>
						<xsl:when test="$Address0='' and $AON!=''">
							<xsl:value-of select="substring(rowset[@name=$AON]/row/col[@name='BillingStreet'], '1', '60')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Address0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>-->
				<xsl:variable name="City" select="rowset[@name='item']/row[col[@name='name']='billing_city_c']/col[@name='value']"/>
				<!--<xsl:variable name="City">
					<xsl:choose>
						<xsl:when test="$City0='' and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingCity']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$City0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>-->
				<xsl:variable name="State" select="rowset[@name='item']/row[col[@name='name']='billing_state_c']/col[@name='value']"/>
				<!--<xsl:variable name="State">
					<xsl:choose>
						<xsl:when test="$State0='' and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingState']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$State0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>-->
				<xsl:variable name="Country" select="rowset[@name='item']/row[col[@name='name']='billing_country_c']/col[@name='value']"/>
				<!--<xsl:variable name="Country">
					<xsl:choose>
						<xsl:when test="$Country0='' and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingCountry']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Country0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>-->
				<xsl:variable name="PostCode" select="rowset[@name='item']/row[col[@name='name']='billing_address_postalcode_c']/col[@name='value']"/>
				<!--<xsl:variable name="PostCode">
					<xsl:choose>
						<xsl:when test="$PostCode0='' and $AON!=''">
							<xsl:value-of select="rowset[@name=$AON]/row/col[@name='BillingPostalCode']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$PostCode0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>-->
				<xsl:variable name="Email" select="rowset[@name='item']/row[col[@name='name']='billing_email_c']/col[@name='value']"/>
				<xsl:variable name="CVM" select="rowset[@name='item']/row[col[@name='name']='cvm_value_c']/col[@name='value']"/>
				<xsl:variable name="Phone"/>
				<!--<xsl:variable name="Phone">
					<xsl:if test="$AON!=''">
						<xsl:value-of select="rowset[@name=$AON]/row/col[@name='Phone']"/>
					</xsl:if>
				</xsl:variable>-->
				<xsl:variable name="ACH" select="rowset[@name='item']/row[col[@name='name']='ach_c']/col[@name='value']"/>
				<xsl:variable name="TotBal" select="rowset[@name='item']/row[col[@name='name']='total_balance_c']/col[@name='value']"/>
				<xsl:variable name="ABA" select="rowset[@name='item']/row[col[@name='name']='routing_c']/col[@name='value']"/>
				<xsl:variable name="AcctNum" select="rowset[@name='item']/row[col[@name='name']='client_acct_c']/col[@name='value']"/>
				<xsl:variable name="AcctType" select="rowset[@name='item']/row[col[@name='name']='account_type_c']/col[@name='value']"/>
				<xsl:variable name="BankName" select="rowset[@name='item']/row[col[@name='name']='bank_name_c']/col[@name='value']"/>
				<xsl:variable name="BankAcctName">
					<xsl:value-of select="$FirstName"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="$LastName"/>
				</xsl:variable>
				<xsl:variable name="ECheckType0" select="rowset[@name='item']/row[col[@name='name']='echeck_type_c']/col[@name='value']"/>
				<xsl:variable name="ECheckType">
					<xsl:choose>
						<xsl:when test="$ECheckType0=''">WEB</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$ECheckType0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="BankCheckNum" select="rowset[@name='item']/row[col[@name='name']='bank_check_number_c']/col[@name='value']"/>x_delim_data=TRUE&amp;x_delim_char=,&amp;x_version=3.1&amp;x_login=<xsl:value-of select="$APILogin"/>&amp;x_tran_key=<xsl:value-of select="$TransKey"/><xsl:choose>
					<xsl:when test="$ACH='true'">&amp;x_method=ECHECK&amp;x_amount=<xsl:value-of select="$TotBal"/>&amp;x_bank_aba_code=<xsl:value-of select="$ABA"/>&amp;x_bank_acct_num=<xsl:value-of select="$AcctNum"/>&amp;x_bank_acct_type=<xsl:value-of select="translate($AcctType, $lowercase, $uppercase)"/>&amp;x_bank_name=<xsl:value-of select="$BankName"/>&amp;x_bank_acct_name=<xsl:value-of select="$BankAcctName"/>&amp;x_echeck_type=<xsl:value-of select="translate($ECheckType, $lowercase, $uppercase)"/>
						<xsl:if test="$BankCheckNum!=''">&amp;x_bank_check_number=<xsl:value-of select="$BankCheckNum"/>
						</xsl:if>&amp;x_recurring_billing=FALSE</xsl:when>
					<xsl:otherwise>
						<xsl:if test="$CVM!=''">&amp;x_card_code=<xsl:value-of select="$CVM"/>
						</xsl:if>&amp;x_amount=<xsl:value-of select="$Charge"/>&amp;x_card_num=<xsl:value-of select="$CCNumber"/>&amp;x_exp_date=<xsl:value-of select="$CCExpDate"/>
					</xsl:otherwise>
				</xsl:choose>&amp;x_description=<xsl:value-of select="translate($Name, ',', '')"/>&amp;x_first_name=<xsl:value-of select="$FirstName"/>&amp;x_last_name=<xsl:value-of select="$LastName"/>&amp;x_company=<xsl:value-of select="$CompName"/>
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
				</xsl:if>&amp;x_zip=<xsl:value-of select="$PostCode"/>&amp;x_cust_id=<xsl:value-of select="$SFId"/>&amp;x_test_request=<xsl:value-of select="$TestReq"/>&amp;x_relay_response=FALSE&amp;`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>