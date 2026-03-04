<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="RecordId0">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="RecordId">
			<xsl:choose>
				<xsl:when test="contains($RecordId0, ':')">
					<xsl:value-of select="substring-after($RecordId0, ':')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$RecordId0"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="TestReq">?</xsl:variable>
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
		<xsl:variable name="TokenMode">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TokenMode']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TokenMode']"/>
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
				<xsl:choose>
					<xsl:when test="$RecordId!=''">
						<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='findacct']/data/rowset/rowset[@name='value']/rowset">
							<xsl:if test="row/col[@name='Id']">
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
								<xsl:if test="$MultURL!='__%UNKNOWN_URL%__'">
									<xsl:variable name="SFId" select="row/col[@name='Id']"/>
									<xsl:variable name="Name">
										<xsl:choose>
											<xsl:when test="row/col[@name='Name']">
												<xsl:value-of select="row/col[@name='Name']"/>
											</xsl:when>
											<xsl:when test="row/col[@name='Number']">
												<xsl:value-of select="row/col[@name='Number']"/>
											</xsl:when>
										</xsl:choose>
									</xsl:variable>
									<xsl:variable name="FirstName">
										<xsl:if test="row/col[@name='UsrFirstName']">
											<xsl:value-of select="row/col[@name='UsrFirstName']"/>
										</xsl:if>
									</xsl:variable>
									<xsl:variable name="LastName">
										<xsl:if test="row/col[@name='UsrLastName']">
											<xsl:value-of select="row/col[@name='UsrLastName']"/>
										</xsl:if>
									</xsl:variable>
									<xsl:variable name="Charge0" select="string(number(row/col[@name='UsrGrossMonthlyCharge'])*100)"/>
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
									<xsl:variable name="CCNumber">
										<xsl:if test="row/col[@name='UsrCreditCardClient']">
											<xsl:value-of select="row/col[@name='UsrCreditCardClient']"/>
										</xsl:if>
									</xsl:variable>
									<xsl:variable name="CCExpDate">
										<xsl:if test="row/col[@name='UsrCCExpDate0000']">
											<xsl:value-of select="row/col[@name='UsrCCExpDate0000']"/>
										</xsl:if>
									</xsl:variable>
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
												<xsl:value-of select="substring($CCExpDate, 3)"/>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:variable>
									<xsl:variable name="CCExpY">
										<xsl:if test="number(string-length($CCExpY0))=number(1)">0</xsl:if>
										<xsl:value-of select="$CCExpY0"/>
									</xsl:variable>
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
									<xsl:variable name="CVM" select="row/col[@name='UsrCVMValue']"/>
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
									<xsl:variable name="BankCheckNum" select="row/col[@name='Bank_Check_Number__c']"/>
									<xsl:variable name="TokenId" select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='tokenstripe']/data/rowset[rowset[@name='card' and row[col[@name='name']=concat($FirstName, ' ', $LastName) and col[@name='last4']=substring($CCNumber, string-length($CCNumber) - 3)]]]//row[col[@name='object']='token']/col[@name='id']"/>amount=<xsl:value-of select="$Charge"/>&amp;currency=usd&amp;description=<xsl:value-of select="translate($Name, ',', '')"/>&amp;<xsl:choose>
										<xsl:when test="$TokenMode='YES'">source[id]=<xsl:value-of select="$TokenId"/>
										</xsl:when>
										<xsl:otherwise>source[number]=<xsl:value-of select="$CCNumber"/>&amp;source[exp_year]=<xsl:value-of select="$CCExpY"/>&amp;source[exp_month]=<xsl:value-of select="$CCExpM"/>&amp;source[object]=card<xsl:if test="$CVM!=''">&amp;source[cvc]=<xsl:value-of select="$CVM"/>
											</xsl:if>
											<xsl:if test="$FirstName!='' or $LastName!=''">&amp;source[name]=<xsl:value-of select="concat($FirstName, ' ', $LastName)"/>
											</xsl:if>
											<xsl:if test="$CompName!=''">&amp;source[address_line1]=<xsl:value-of select="$CompName"/>
											</xsl:if>
											<xsl:if test="$Address!=''">&amp;source[address_line<xsl:choose>
													<xsl:when test="$CompName!=''">2</xsl:when>
													<xsl:otherwise>1</xsl:otherwise>
												</xsl:choose>]=<xsl:value-of select="$Address"/>
											</xsl:if>
											<xsl:if test="$City!=''">&amp;source[address_city]=<xsl:value-of select="$City"/>
											</xsl:if>
											<xsl:if test="$State!=''">&amp;source[address_state]=<xsl:value-of select="$State"/>
											</xsl:if>
											<xsl:if test="$Country!=''">&amp;source[address_country]=<xsl:value-of select="$Country"/>
											</xsl:if>
											<xsl:if test="$PostCode!=''">&amp;source[address_zip]=<xsl:value-of select="$PostCode"/>
											</xsl:if>
										</xsl:otherwise>
									</xsl:choose>
									<xsl:if test="$Email!=''">&amp;receipt_email=<xsl:value-of select="$Email"/>
									</xsl:if>&amp;metadata[cust_id]=<xsl:value-of select="$SFId"/>`</xsl:if>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
							<xsl:if test="row/col[@name='Id']">
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
								<xsl:if test="$MultURL!='__%UNKNOWN_URL%__'">
									<xsl:variable name="SFId" select="row/col[@name='Id']"/>
									<xsl:variable name="Name" select="row/col[@name='Name']"/>
									<xsl:variable name="FirstName">
										<xsl:choose>
											<xsl:when test="$POSMode=''">
												<xsl:value-of select="row/col[@name='First_Name__c']"/>
											</xsl:when>
											<xsl:when test="starts-with($POSMode, '0')">
												<xsl:value-of select="substring-before(substring-after(//iwtransformationserver/iwrecordset/transaction/datamap[@name='getccftps']/data/row/col[@name=$POSRN], '/'), ' ')"/>
											</xsl:when>
										</xsl:choose>
									</xsl:variable>
									<xsl:variable name="LastName">
										<xsl:choose>
											<xsl:when test="$POSMode=''">
												<xsl:value-of select="row/col[@name='Last_Name__c']"/>
											</xsl:when>
											<xsl:when test="starts-with($POSMode, '0')">
												<xsl:value-of select="substring-before(substring-after(//iwtransformationserver/iwrecordset/transaction/datamap[@name='getccftps']/data/row/col[@name=$POSRN], '^'), '/')"/>
											</xsl:when>
										</xsl:choose>
									</xsl:variable>
									<xsl:variable name="Charge0" select="string(number(row/col[@name=$AmFN])*100)"/>
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
									<xsl:variable name="CCNumber">
										<xsl:choose>
											<xsl:when test="$POSMode=''">
												<xsl:value-of select="row/col[@name='Credit_Card_Client__c']"/>
											</xsl:when>
											<xsl:when test="starts-with($POSMode, '0')">
												<xsl:value-of select="substring-before(substring-after(//iwtransformationserver/iwrecordset/transaction/datamap[@name='getccftps']/data/row/col[@name=$POSRN], '%B'), '^')"/>
											</xsl:when>
										</xsl:choose>
									</xsl:variable>
									<xsl:variable name="CCExpDate">
										<xsl:choose>
											<xsl:when test="$POSMode=''">
												<xsl:value-of select="row/col[@name='CC_Exp_Date_00_00__c']"/>
											</xsl:when>
											<xsl:when test="starts-with($POSMode, '0')">
												<xsl:value-of select="substring(substring-after(//iwtransformationserver/iwrecordset/transaction/datamap[@name='getccftps']/data/row/col[@name=$POSRN], '='), 3, 2)"/>/<xsl:value-of select="substring(substring-after(//iwtransformationserver/iwrecordset/transaction/datamap[@name='getccftps']/data/row/col[@name=$POSRN], '='), 1, 2)"/>
											</xsl:when>
										</xsl:choose>
									</xsl:variable>
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
												<xsl:value-of select="substring($CCExpDate, 3)"/>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:variable>
									<xsl:variable name="CCExpY">
										<xsl:if test="number(string-length($CCExpY0))=number(1)">0</xsl:if>
										<xsl:value-of select="$CCExpY0"/>
									</xsl:variable>
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
									<xsl:variable name="BankCheckNum" select="row/col[@name='Bank_Check_Number__c']"/>
									<xsl:variable name="TokenId" select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='tokenstripe']/data/rowset[rowset[@name='card' and row[col[@name='name']=concat($FirstName, ' ', $LastName) and col[@name='last4']=substring($CCNumber, string-length($CCNumber) - 3)]]]//row[col[@name='object']='token']/col[@name='id']"/>amount=<xsl:value-of select="$Charge"/>&amp;currency=usd&amp;description=<xsl:value-of select="translate($Name, ',', '')"/>&amp;<xsl:choose>
										<xsl:when test="$TokenMode='YES'">source[id]=<xsl:value-of select="$TokenId"/>
										</xsl:when>
										<xsl:otherwise>source[number]=<xsl:value-of select="$CCNumber"/>&amp;source[exp_year]=<xsl:value-of select="$CCExpY"/>&amp;source[exp_month]=<xsl:value-of select="$CCExpM"/>&amp;source[object]=card<xsl:if test="$CVM!=''">&amp;source[cvc]=<xsl:value-of select="$CVM"/>
											</xsl:if>
											<xsl:if test="$FirstName!='' or $LastName!=''">&amp;source[name]=<xsl:value-of select="concat($FirstName, ' ', $LastName)"/>
											</xsl:if>
											<xsl:if test="$CompName!=''">&amp;source[address_line1]=<xsl:value-of select="$CompName"/>
											</xsl:if>
											<xsl:if test="$Address!=''">&amp;source[address_line<xsl:choose>
													<xsl:when test="$CompName!=''">2</xsl:when>
													<xsl:otherwise>1</xsl:otherwise>
												</xsl:choose>]=<xsl:value-of select="$Address"/>
											</xsl:if>
											<xsl:if test="$City!=''">&amp;source[address_city]=<xsl:value-of select="$City"/>
											</xsl:if>
											<xsl:if test="$State!=''">&amp;source[address_state]=<xsl:value-of select="$State"/>
											</xsl:if>
											<xsl:if test="$Country!=''">&amp;source[address_country]=<xsl:value-of select="$Country"/>
											</xsl:if>
											<xsl:if test="$PostCode!=''">&amp;source[address_zip]=<xsl:value-of select="$PostCode"/>
											</xsl:if>
										</xsl:otherwise>
									</xsl:choose>
									<xsl:if test="$Email!=''">&amp;receipt_email=<xsl:value-of select="$Email"/>
									</xsl:if>&amp;metadata[cust_id]=<xsl:value-of select="$SFId"/>`</xsl:if>
							</xsl:if>
						</xsl:for-each>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
