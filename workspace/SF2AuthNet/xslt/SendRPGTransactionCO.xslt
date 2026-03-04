<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
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
				<xsl:variable name="Charge00" select="row/col[@name='Gross_Monthly_Chargedt__c']"/>
				<xsl:variable name="Charge01" select="substring-before($Charge00, '.')"/>
				<xsl:variable name="Charge02" select="substring-after($Charge00, '.')"/>
				<xsl:variable name="Charge03" select="string-length($Charge02)"/>
				<xsl:variable name="Charge04">
					<xsl:choose>
						<xsl:when test="$Charge03=2">
							<xsl:value-of select="$Charge02"/>
						</xsl:when>
						<xsl:when test="$Charge03=1">
							<xsl:value-of select="$Charge02"/>0</xsl:when>
						<xsl:when test="$Charge03=0">00</xsl:when>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
				<xsl:variable name="CCType">
					<xsl:choose>
						<xsl:when test="starts-with($CCNumber, '4')">Visa</xsl:when>
						<xsl:when test="starts-with($CCNumber, '5')">MasterCard</xsl:when>
						<xsl:when test="starts-with($CCNumber, '3')">Amex</xsl:when>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="ArrDate" select="row/col[@name='Arrival_Date__c']"/>
				<xsl:variable name="DepDate" select="row/col[@name='Departure_Date__c']"/>
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
				<xsl:variable name="CompName" select="rowset[@name='Guest_Name__r']/row/col[@name='Name']"/>
				<xsl:variable name="Address0" select="substring(row/col[@name='Billing_Company_Street__c'], '1', '60')"/>
				<xsl:variable name="Address">
					<xsl:choose>
						<xsl:when test="$Address0=''">
							<xsl:value-of select="substring(rowset[@name='Guest_Name__r']/row/col[@name='BillingStreet'], '1', '60')"/>
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
							<xsl:value-of select="rowset[@name='Guest_Name__r']/row/col[@name='BillingCity']"/>
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
							<xsl:value-of select="rowset[@name='Guest_Name__r']/row/col[@name='BillingState']"/>
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
							<xsl:value-of select="rowset[@name='Guest_Name__r']/row/col[@name='BillingCountry']"/>
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
							<xsl:value-of select="rowset[@name='Guest_Name__r']/row/col[@name='BillingPostalCode']"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$PostCode0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="Email" select="row/col[@name='Billing_Email__c']"/>
				<xsl:variable name="CVM" select="row/col[@name='CVM_Value__c']"/>
				<xsl:variable name="Phone" select="rowset[@name='Guest_Name__r']/row/col[@name='Phone']"/>
				<xsl:variable name="ACH" select="row/col[@name='ACH__c']"/>
				<xsl:variable name="TotBal00" select="row/col[@name='Total_Balancedt__c']"/>
				<xsl:variable name="TotBal01" select="substring-before($TotBal00, '.')"/>
				<xsl:variable name="TotBal02" select="substring-after($TotBal00, '.')"/>
				<xsl:variable name="TotBal03" select="string-length($TotBal02)"/>
				<xsl:variable name="TotBal04">
					<xsl:choose>
						<xsl:when test="$TotBal03=2">
							<xsl:value-of select="$TotBal02"/>
						</xsl:when>
						<xsl:when test="$TotBal03=1">
							<xsl:value-of select="$TotBal02"/>0</xsl:when>
						<xsl:when test="$TotBal03=0">00</xsl:when>
					</xsl:choose>
				</xsl:variable>
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
				<xsl:choose>
					<xsl:when test="$ACH='true'">
						<ACHPayment>
							<username>
								<xsl:value-of select="$APILogin"/>
							</username>
							<password>
								<xsl:value-of select="$TransKey"/>
							</password>
							<propertyCode>
								<xsl:value-of select="$APIURL"/>
							</propertyCode>
							<account>
								<xsl:value-of select="$AcctNum"/>
							</account>
							<routing>
								<xsl:value-of select="$ABA"/>
							</routing>
							<accountholder>
								<xsl:value-of select="$FirstName"/>
								<xsl:text> </xsl:text>
								<xsl:value-of select="$LastName"/>
							</accountholder>
							<street>
								<xsl:value-of select="$Address"/>
							</street>
							<city>
								<xsl:value-of select="$City"/>
							</city>
							<state>
								<xsl:value-of select="$State"/>
							</state>
							<zip>
								<xsl:value-of select="$PostCode"/>
							</zip>
							<phone>
								<xsl:value-of select="translate($Phone, '()- ', '')"/>
							</phone>
							<email>
								<xsl:value-of select="$Email"/>
							</email>
							<amount>
								<xsl:value-of select="$TotBal01"/>
								<xsl:value-of select="$TotBal04"/>
							</amount>
							<id>
								<xsl:value-of select="$SFId"/>
							</id>
							<personFirstname>
								<xsl:value-of select="$FirstName"/>
							</personFirstname>
							<personLastname>
								<xsl:value-of select="$LastName"/>
							</personLastname>
							<!--<reservationNumber>myReservationNumber</reservationNumber>
							<GUID>0897sfgWU266909s689h</GUID>-->
						</ACHPayment>
					</xsl:when>
					<xsl:otherwise>
						<CreditCardPayment>
							<username>
								<xsl:value-of select="$APILogin"/>
							</username>
							<password>
								<xsl:value-of select="$TransKey"/>
							</password>
							<propertyCode>
								<xsl:value-of select="$APIURL"/>
							</propertyCode>
							<number>
								<xsl:value-of select="$CCNumber"/>
							</number>
							<expiration>
								<xsl:value-of select="$CCExpM"/>
								<xsl:text>-</xsl:text>
								<xsl:value-of select="$CCExpY"/>
							</expiration>
							<cardholder>
								<xsl:value-of select="$FirstName"/>
								<xsl:text> </xsl:text>
								<xsl:value-of select="$LastName"/>
							</cardholder>
							<type>
								<xsl:value-of select="$CCType"/>
							</type>
							<street>
								<xsl:value-of select="$Address"/>
							</street>
							<city>
								<xsl:value-of select="$City"/>
							</city>
							<state>
								<xsl:value-of select="$State"/>
							</state>
							<zip>
								<xsl:value-of select="$PostCode"/>
							</zip>
							<country>
								<xsl:value-of select="$Country"/>
							</country>
							<phone>
								<xsl:value-of select="translate($Phone, '()- ', '')"/>
							</phone>
							<email>
								<xsl:value-of select="$Email"/>
							</email>
							<amount>
								<xsl:value-of select="$Charge01"/>
								<xsl:value-of select="$Charge04"/>
							</amount>
							<id>
								<xsl:value-of select="$SFId"/>
							</id>
							<personFirstname>
								<xsl:value-of select="$FirstName"/>
							</personFirstname>
							<personLastname>
								<xsl:value-of select="$LastName"/>
							</personLastname>
							<arrivalDate>
								<xsl:value-of select="$ArrDate"/>
							</arrivalDate>
							<departureDate>
								<xsl:value-of select="$DepDate"/>
							</departureDate>
							<roomRate>0000</roomRate>
							<roomTax>0000</roomTax>
							<xsl:if test="$CVM!=''">
								<cvv>
									<xsl:value-of select="$CVM"/>
								</cvv>
							</xsl:if>
							<!--<preAuthorization>false</preAuthorization>
<reservationNumber>myReservationNumber</reservationNumber>
<magneticStrip>myMagneticStripInfo</magneticStrip>
<GUID>0897sfgWU266909s689h</GUID>
<ReturnToken>true</ReturnToken>-->
						</CreditCardPayment>`</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>