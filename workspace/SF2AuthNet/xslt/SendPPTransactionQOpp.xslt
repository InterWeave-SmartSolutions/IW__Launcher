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
		<xsl:variable name="APIVersion" select="'104.0'"/>
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
				<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:m0="urn:ebay:apis:eBLBaseComponents">
					<SOAP-ENV:Header>
						<m:RequesterCredentials xmlns:m="urn:ebay:api:PayPalAPI">
							<m0:Credentials>
								<m0:Username>
									<xsl:value-of select="$APILogin"/>
								</m0:Username>
								<m0:Password>
									<xsl:value-of select="$TransKey"/>
								</m0:Password>
								<m0:Signature>
									<xsl:value-of select="$APIURL"/>
								</m0:Signature>
							</m0:Credentials>
						</m:RequesterCredentials>
					</SOAP-ENV:Header>
					<SOAP-ENV:Body>
						<m:DoDirectPaymentReq xmlns:m="urn:ebay:api:PayPalAPI">
							<m:DoDirectPaymentRequest>
								<m0:DetailLevel>ReturnAll</m0:DetailLevel>
								<!--<m0:ErrorLanguage>String</m0:ErrorLanguage>-->
								<m0:Version>
									<xsl:value-of select="$APIVersion"/>
								</m0:Version>
								<m0:DoDirectPaymentRequestDetails>
									<m0:PaymentAction>Sale</m0:PaymentAction>
									<m0:PaymentDetails>
										<m0:OrderTotal>
											<xsl:value-of select="$Charge"/>
										</m0:OrderTotal>
										<!--<m0:ItemTotal>String</m0:ItemTotal>
						<m0:ShippingTotal>String</m0:ShippingTotal>
						<m0:HandlingTotal>String</m0:HandlingTotal>
						<m0:TaxTotal>String</m0:TaxTotal>
						<m0:OrderDescription>String</m0:OrderDescription>
						<m0:Custom>String</m0:Custom>
						<m0:InvoiceID>String</m0:InvoiceID>
						<m0:ButtonSource>String</m0:ButtonSource>
						<m0:NotifyURL>String</m0:NotifyURL>
						<m0:ShipToAddress>
							<m0:Name>String</m0:Name>
							<m0:Street1>String</m0:Street1>
							<m0:Street2>String</m0:Street2>
							<m0:CityName>String</m0:CityName>
							<m0:StateOrProvince>String</m0:StateOrProvince>
							<m0:Country>AF</m0:Country>
							<m0:CountryName>String</m0:CountryName>
							<m0:Phone>String</m0:Phone>
							<m0:PostalCode>String</m0:PostalCode>
							<m0:AddressID>String</m0:AddressID>
							<m0:AddressOwner>PayPal</m0:AddressOwner>
							<m0:ExternalAddressID>String</m0:ExternalAddressID>
							<m0:InternationalName>String</m0:InternationalName>
							<m0:InternationalStateAndCity>String</m0:InternationalStateAndCity>
							<m0:InternationalStreet>String</m0:InternationalStreet>
							<m0:AddressStatus>None</m0:AddressStatus>
							<m0:AddressNormalizationStatus>None</m0:AddressNormalizationStatus>
						</m0:ShipToAddress>
						<m0:MultiShipping>String</m0:MultiShipping>
						<m0:FulfillmentReferenceNumber>String</m0:FulfillmentReferenceNumber>
						<m0:FulfillmentAddress>
							<m0:Name>String</m0:Name>
							<m0:Street1>String</m0:Street1>
							<m0:Street2>String</m0:Street2>
							<m0:CityName>String</m0:CityName>
							<m0:StateOrProvince>String</m0:StateOrProvince>
							<m0:Country>AF</m0:Country>
							<m0:CountryName>String</m0:CountryName>
							<m0:Phone>String</m0:Phone>
							<m0:PostalCode>String</m0:PostalCode>
							<m0:AddressID>String</m0:AddressID>
							<m0:AddressOwner>PayPal</m0:AddressOwner>
							<m0:ExternalAddressID>String</m0:ExternalAddressID>
							<m0:InternationalName>String</m0:InternationalName>
							<m0:InternationalStateAndCity>String</m0:InternationalStateAndCity>
							<m0:InternationalStreet>String</m0:InternationalStreet>
							<m0:AddressStatus>None</m0:AddressStatus>
							<m0:AddressNormalizationStatus>None</m0:AddressNormalizationStatus>
						</m0:FulfillmentAddress>
						<m0:PaymentCategoryType>InternationalShipping</m0:PaymentCategoryType>
						<m0:ShippingMethod>UPSGround</m0:ShippingMethod>
						<m0:ProfileAddressChangeDate>2001-12-17T09:30:47-05:00</m0:ProfileAddressChangeDate>
						<m0:PaymentDetailsItem>
							<m0:Name>String</m0:Name>
							<m0:Number>String</m0:Number>
							<m0:Quantity>0</m0:Quantity>
							<m0:Tax>String</m0:Tax>
							<m0:Amount>String</m0:Amount>
							<m0:EbayItemPaymentDetailsItem>
								<m0:ItemNumber>String</m0:ItemNumber>
								<m0:AuctionTransactionId>String</m0:AuctionTransactionId>
								<m0:OrderId>String</m0:OrderId>
								<m0:CartID>String</m0:CartID>
							</m0:EbayItemPaymentDetailsItem>
							<m0:PromoCode>String</m0:PromoCode>
							<m0:ProductCategory>Other</m0:ProductCategory>
							<m0:Description>String</m0:Description>
							<m0:ItemWeight>3.14159265358979</m0:ItemWeight>
							<m0:ItemLength>3.14159265358979</m0:ItemLength>
							<m0:ItemWidth>3.14159265358979</m0:ItemWidth>
							<m0:ItemHeight>3.14159265358979</m0:ItemHeight>
							<m0:ItemURL>String</m0:ItemURL>
							<m0:EnhancedItemData/>
							<m0:ItemCategory>Physical</m0:ItemCategory>
						</m0:PaymentDetailsItem>
						<m0:InsuranceTotal>String</m0:InsuranceTotal>
						<m0:ShippingDiscount>String</m0:ShippingDiscount>
						<m0:InsuranceOptionOffered>String</m0:InsuranceOptionOffered>
						<m0:AllowedPaymentMethod>Default</m0:AllowedPaymentMethod>
						<m0:EnhancedPaymentData/>
						<m0:SellerDetails>
							<m0:SellerId>String</m0:SellerId>
							<m0:SellerUserName>String</m0:SellerUserName>
							<m0:SellerRegistrationDate>2001-12-17T09:30:47-05:00</m0:SellerRegistrationDate>
							<m0:PayPalAccountID>String</m0:PayPalAccountID>
							<m0:SecureMerchantAccountID>String</m0:SecureMerchantAccountID>
						</m0:SellerDetails>
						<m0:NoteText>String</m0:NoteText>
						<m0:TransactionId>String</m0:TransactionId>
						<m0:PaymentAction>None</m0:PaymentAction>
						<m0:PaymentRequestID>String</m0:PaymentRequestID>
						<m0:OrderURL>String</m0:OrderURL>
						<m0:SoftDescriptor>String</m0:SoftDescriptor>
						<m0:BranchLevel>0</m0:BranchLevel>
						<m0:OfferDetails>
							<m0:OfferCode>String</m0:OfferCode>
							<m0:BMLOfferInfo>
								<m0:OfferTrackingID>String</m0:OfferTrackingID>
							</m0:BMLOfferInfo>
						</m0:OfferDetails>-->
										<!--<m0:Recurring>N</m0:Recurring>
										<m0:PaymentReason>None</m0:PaymentReason>-->
									</m0:PaymentDetails>
									<m0:CreditCard>
										<m0:CreditCardType>
											<xsl:value-of select="$CCType"/>
										</m0:CreditCardType>
										<m0:CreditCardNumber>
											<xsl:value-of select="$CCNumber"/>
										</m0:CreditCardNumber>
										<m0:ExpMonth>
											<xsl:value-of select="$CCExpM"/>
										</m0:ExpMonth>
										<m0:ExpYear>
											<xsl:value-of select="$CCExpY"/>
										</m0:ExpYear>
										<m0:CardOwner>
											<xsl:if test="$Email!=''">
												<m0:Payer>
													<xsl:value-of select="$Email"/>
												</m0:Payer>
											</xsl:if>
											<!--<m0:PayerID>String</m0:PayerID>
							<m0:PayerStatus>verified</m0:PayerStatus>-->
											<m0:PayerName>
												<m0:FirstName>
													<xsl:value-of select="$FirstName"/>
												</m0:FirstName>
												<m0:LastName>
													<xsl:value-of select="$LastName"/>
												</m0:LastName>
												<!--<m0:Suffix></m0:Suffix>-->
											</m0:PayerName>
											<!--<m0:PayerCountry>AF</m0:PayerCountry>
							<m0:PayerBusiness>String</m0:PayerBusiness>-->
											<m0:Address>
												<m0:Name>
													<xsl:value-of select="$BankAcctName"/>
												</m0:Name>
												<m0:Street1>
													<xsl:value-of select="$Address"/>
												</m0:Street1>
												<m0:Street2/>
												<m0:CityName>
													<xsl:value-of select="$City"/>
												</m0:CityName>
												<m0:StateOrProvince>
													<xsl:value-of select="$State"/>
												</m0:StateOrProvince>
												<m0:CountryName>
													<xsl:value-of select="$Country"/>
												</m0:CountryName>
												<m0:Phone>
													<xsl:value-of select="$Phone"/>
												</m0:Phone>
												<m0:PostalCode>
													<xsl:value-of select="$PostCode"/>
												</m0:PostalCode>
												<!--<m0:AddressID>String</m0:AddressID>
								<m0:AddressOwner>PayPal</m0:AddressOwner>
								<m0:ExternalAddressID>String</m0:ExternalAddressID>
								<m0:InternationalName>String</m0:InternationalName>
								<m0:InternationalStateAndCity>String</m0:InternationalStateAndCity>
								<m0:InternationalStreet>String</m0:InternationalStreet>
								<m0:AddressStatus>None</m0:AddressStatus>
								<m0:AddressNormalizationStatus>None</m0:AddressNormalizationStatus>-->
											</m0:Address>
											<!--<m0:ContactPhone>String</m0:ContactPhone>
							<m0:TaxIdDetails>
								<m0:TaxIdType>String</m0:TaxIdType>
								<m0:TaxId>String</m0:TaxId>
							</m0:TaxIdDetails>
							<m0:EnhancedPayerInfo/>-->
										</m0:CardOwner>
										<m0:CVV2>
											<xsl:value-of select="$CVM"/>
										</m0:CVV2>
										<!--<m0:StartMonth>0</m0:StartMonth>
						<m0:StartYear>0</m0:StartYear>
						<m0:IssueNumber>String</m0:IssueNumber>
						<m0:ThreeDSecureRequest>
							<m0:Eci3ds>String</m0:Eci3ds>
							<m0:Cavv>String</m0:Cavv>
							<m0:Xid>String</m0:Xid>
							<m0:MpiVendor3ds>String</m0:MpiVendor3ds>
							<m0:AuthStatus3ds>String</m0:AuthStatus3ds>
						</m0:ThreeDSecureRequest>-->
									</m0:CreditCard>
									<!--<m0:IPAddress>String</m0:IPAddress>
					<m0:MerchantSessionId>String</m0:MerchantSessionId>
					<m0:ReturnFMFDetails>1</m0:ReturnFMFDetails>-->
								</m0:DoDirectPaymentRequestDetails>
								<m:ReturnFMFDetails>0</m:ReturnFMFDetails>
							</m:DoDirectPaymentRequest>
						</m:DoDirectPaymentReq>
					</SOAP-ENV:Body>
				</SOAP-ENV:Envelope>`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
