<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TestReq">?</xsl:variable>
		<xsl:variable name="APIURI" select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='QDSN0']"/>
		<xsl:variable name="APIID" select="substring-before($APIURI, ':')"/>
		<xsl:variable name="APIRK" select="substring-after($APIURI, ':')"/>
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
				<xsl:variable name="Charge00" select="row/col[@name='Gross_Monthly_Charge__c']"/>
				<xsl:variable name="Charge01">
					<xsl:choose>
						<xsl:when test="contains($Charge00, '.')">
							<xsl:variable name="CA" select="substring-after($Charge00, '.')"/>
							<xsl:value-of select="substring-before($Charge00, '.')"/>
							<xsl:value-of select="$CA"/>
							<xsl:choose>
								<xsl:when test="string-length($CA)=0">00</xsl:when>
								<xsl:when test="string-length($CA)=1">0</xsl:when>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$Charge00"/>00</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Charge0" select="translate($Charge01, ',','')"/>
				<xsl:variable name="Charge1">00000000<xsl:value-of select="$Charge0"/>
				</xsl:variable>
				<xsl:variable name="Charge" select="substring($Charge1, string-length($Charge0)+1)"/>
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
				<xsl:variable name="CCExpY">
					<xsl:choose>
						<xsl:when test="contains($CCExpDate, '/')">
							<xsl:value-of select="substring-after($CCExpDate, '/')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring($CCExpDate, 3, 2)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="CompName" select="rowset[@name='Account']/row/col[@name='Name']"/>
				<xsl:variable name="Address0" select="substring(row/col[@name='Billing_Company_Street__c'], '1', '50')"/>
				<xsl:variable name="Address">
					<xsl:choose>
						<xsl:when test="$Address0=''">
							<xsl:value-of select="substring(rowset[@name='Account']/row/col[@name='BillingStreet'], '1', '50')"/>
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
				<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
					<SOAP-ENV:Body>
						<m:SendTranRequest xmlns:m="http://postilion/realtime/merchantframework/xsd/v1/">
							<m:merc>
								<m:id>
									<xsl:value-of select="$APILogin"/>
								</m:id>
								<m:regKey>
									<xsl:value-of select="$TransKey"/>
								</m:regKey>
								<!--<m:usrId>
									<xsl:value-of select="$APIID"/>
								</m:usrId>
								<m:pwd>
									<xsl:value-of select="$APIRK"/>
								</m:pwd>-->
								<m:inType>1</m:inType>
								<m:prodType>5</m:prodType>
							</m:merc>
							<m:tranCode>1</m:tranCode>
							<m:card>
								<!--<m:type>
									<xsl:value-of select="$CCType"/>
								</m:type>-->
								<m:pan>
									<xsl:value-of select="$CCNumber"/>
								</m:pan>
								<xsl:if test="$CVM!=''">
									<m:sec>
										<xsl:value-of select="$CVM"/>
									</m:sec>
								</xsl:if>
								<m:xprDt>
									<xsl:value-of select="$CCExpY"/>
									<xsl:value-of select="$CCExpM"/>
								</m:xprDt>
								<!--<m:sqncNr/>
								<m:trk1/>
								<m:trk2/>-->
								<m:dbtOrCdt>1</m:dbtOrCdt>
							</m:card>
							<m:contact>
								<m:id>
									<xsl:value-of select="$SFId"/>
								</m:id>
								<xsl:if test="$FirstName!=''">
									<m:firstName>
										<xsl:value-of select="$FirstName"/>
									</m:firstName>
								</xsl:if>
								<xsl:if test="$LastName!=''">
									<m:lastName>
										<xsl:value-of select="$LastName"/>
									</m:lastName>
								</xsl:if>
								<xsl:if test="$FirstName!='' or $LastName!=''">
									<m:fullName>
										<xsl:value-of select="$FirstName"/>
										<xsl:text> </xsl:text>
										<xsl:value-of select="$LastName"/>
									</m:fullName>
								</xsl:if>
								<xsl:if test="$CompName!=''">
									<m:coName>
										<xsl:value-of select="$CompName"/>
									</m:coName>
								</xsl:if>
								<!--<m:title/>-->
								<xsl:if test="$Phone!=''">
									<m:phone>
										<m:type>
											<xsl:if test="$Phone!=''">3</xsl:if>
										</m:type>
										<m:nr>
											<xsl:value-of select="$Phone"/>
										</m:nr>
									</m:phone>
								</xsl:if>
								<xsl:if test="$Address!=''">
									<m:addrLn1>
										<xsl:value-of select="$Address"/>
									</m:addrLn1>
								</xsl:if>
								<!--<m:addrLn2/>-->
								<xsl:if test="$City!=''">
									<m:city>
										<xsl:value-of select="$City"/>
									</m:city>
								</xsl:if>
								<xsl:if test="$State!=''">
									<m:state>
										<xsl:value-of select="$State"/>
									</m:state>
								</xsl:if>
								<xsl:if test="$PostCode!=''">
									<m:zipCode>
										<xsl:value-of select="$PostCode"/>
									</m:zipCode>
								</xsl:if>
								<xsl:if test="$Country!=''">
									<m:ctry>
										<xsl:value-of select="$Country"/>
									</m:ctry>
								</xsl:if>
								<xsl:if test="$Email!=''">
									<xsl:if test="$Email!=''">
										<m:email>
											<xsl:value-of select="$Email"/>
										</m:email>
									</xsl:if>
								</xsl:if>
								<!--<m:type/>
								<m:stat/>
								<m:note/>
								<m:ship>
									<m:fullName/>
									<m:addrLn1/>
									<m:addrLn2/>
									<m:city/>
									<m:state/>
									<m:zipCode/>
									<m:phone/>
									<m:email/>
								</m:ship>-->
							</m:contact>
							<m:reqAmt>
								<xsl:value-of select="$Charge"/>
							</m:reqAmt>
							<!--<m:prevSettleAmt/>
							<m:lclDtTm/>
							<m:usrDef>
								<m:name/>
								<m:val/>
							</m:usrDef>-->
							<m:indCode>0</m:indCode>
							<!--<m:tranFlags>
								<m:dupChkTmPrd>0</m:dupChkTmPrd>
								<m:rsbmt>N</m:rsbmt>
								<m:mgdSettle>Y</m:mgdSettle>
								<m:convFeeAcptd>Y</m:convFeeAcptd>
							</m:tranFlags>
							<m:tax>
								<m:idcr>0</m:idcr>
								<m:amt>0</m:amt>
							</m:tax>-->
						</m:SendTranRequest>
					</SOAP-ENV:Body>
				</SOAP-ENV:Envelope>`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
