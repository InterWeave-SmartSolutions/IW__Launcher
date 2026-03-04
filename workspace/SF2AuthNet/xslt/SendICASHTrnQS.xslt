<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TransKey">%iw_password%</xsl:variable>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:variable name="Charge" select="row/col[@name='Monthly_Charge__c']"/>
			<xsl:variable name="CCNumber" select="row/col[@name='Credit_Card_Client__c']"/>
			<xsl:variable name="CCCVV" select="row/col[@name='CVV__c']"/>
			<xsl:if test="$CCNumber!='' and $CCCVV!='' and $Charge!=''">
				<xsl:variable name="CCT" select="number(substring($CCNumber, 1,1))"/>
				<xsl:variable name="CCType">
					<xsl:choose>
						<xsl:when test="$CCT=4">VISA</xsl:when>
						<xsl:when test="$CCT=5">MASTERCARD</xsl:when>
						<xsl:when test="$CCT=3">AMEX</xsl:when>
						<xsl:when test="$CCT=6">DISCOVER</xsl:when>
						<xsl:otherwise/>
					</xsl:choose>
				</xsl:variable>
				<xsl:if test="$CCType!=''">
					<xsl:variable name="SFId" select="row/col[@name='Id']"/>
					<xsl:variable name="Name" select="row/col[@name='Name']"/>
					<xsl:variable name="FirstName" select="row/col[@name='FirstName']"/>
					<xsl:variable name="LastName" select="row/col[@name='LastName']"/>
					<xsl:variable name="CCExpDate" select="row/col[@name='CC_EXP_Date_00_00__c']"/>
					<xsl:variable name="Address" select="substring(row/col[@name='Street'], '1', '250')"/>
					<xsl:variable name="PostCode" select="row/col[@name='PostalCode']"/>
					<xsl:variable name="City" select="row/col[@name='City']"/>
					<xsl:variable name="State" select="row/col[@name='State']"/>
					<xsl:variable name="Country0" select="row/col[@name='Country']"/>
					<xsl:variable name="Country">
						<xsl:choose>
							<xsl:when test="string-length($Country0)=2">
								<xsl:value-of select="$Country0"/>
							</xsl:when>
							<xsl:otherwise>US</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:variable name="Email" select="row/col[@name='Email']"/>
					<xsl:variable name="Phone0" select="row/col[@name='Phone']"/>
					<xsl:variable name="Phone1" select="translate($Phone0, '(', '')"/>
					<xsl:variable name="Phone2" select="translate($Phone1, ')', '')"/>
					<xsl:variable name="Phone3" select="translate($Phone2, '-', '')"/>
					<xsl:variable name="Phone4" select="translate($Phone3, '+', '')"/>
					<xsl:variable name="Phone" select="translate($Phone4, ' ', '')"/>
					<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
						<SOAP-ENV:Body>
							<m:payment xmlns:m="http://acquirer.process.training.aquarius" SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
								<customerpaymentpagetext xsi:type="xsd:string">
									<xsl:value-of select="$TransKey"/>
								</customerpaymentpagetext>
								<orderdescription xsi:type="xsd:string">
									<xsl:value-of select="$SFId"/>
								</orderdescription>
								<purchaseamount xsi:type="xsd:string">
									<xsl:value-of select="$Charge"/>
								</purchaseamount>
								<currencytext xsi:type="xsd:string">USD</currencytext>
								<taxamount xsi:type="xsd:string">0.00</taxamount>
								<shippingamount xsi:type="xsd:string">0.00</shippingamount>
								<dutyamount xsi:type="xsd:string">0.00</dutyamount>
								<cardholdername xsi:type="xsd:string">
									<xsl:value-of select="$FirstName"/>
									<xsl:text> </xsl:text>
									<xsl:value-of select="$LastName"/>
								</cardholdername>
								<cardno xsi:type="xsd:string">
									<xsl:value-of select="$CCNumber"/>
								</cardno>
								<cardtypetext xsi:type="xsd:string">
									<xsl:value-of select="$CCType"/>
								</cardtypetext>
								<securitycode xsi:type="xsd:string">
									<xsl:value-of select="$CCCVV"/>
								</securitycode>
								<cardexpiremonth xsi:type="xsd:string">
									<xsl:value-of select="substring-before($CCExpDate, '/')"/>
								</cardexpiremonth>
								<cardexpireyear xsi:type="xsd:string">
									<xsl:text>20</xsl:text>
									<xsl:value-of select="substring-after($CCExpDate, '/')"/>
								</cardexpireyear>
								<cardissuemonth xsi:type="xsd:string">0</cardissuemonth>
								<cardissueyear xsi:type="xsd:string">0</cardissueyear>
								<issuername xsi:type="xsd:string"/>
								<firstname xsi:type="xsd:string">
									<xsl:value-of select="$FirstName"/>
								</firstname>
								<lastname xsi:type="xsd:string">
									<xsl:value-of select="$LastName"/>
								</lastname>
								<company xsi:type="xsd:string"/>
								<address xsi:type="xsd:string">
									<xsl:value-of select="$Address"/>
								</address>
								<city xsi:type="xsd:string">
									<xsl:value-of select="$City"/>
								</city>
								<state xsi:type="xsd:string">
									<xsl:value-of select="$State"/>
								</state>
								<zip xsi:type="xsd:string">
									<xsl:value-of select="$PostCode"/>
								</zip>
								<country xsi:type="xsd:string">
									<xsl:choose>
										<xsl:when test="$Country=''">USA</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="$Country"/>
										</xsl:otherwise>
									</xsl:choose>
								</country>
								<email xsi:type="xsd:string">
									<xsl:value-of select="$Email"/>
								</email>
								<phone xsi:type="xsd:string">
									<xsl:value-of select="$Phone"/>
								</phone>
								<shipfirstname xsi:type="xsd:string"/>
								<shiplastname xsi:type="xsd:string"/>
								<shipaddress xsi:type="xsd:string"/>
								<shipcity xsi:type="xsd:string"/>
								<shipstate xsi:type="xsd:string"/>
								<shipzip xsi:type="xsd:string"/>
								<shipcountry xsi:type="xsd:string"/>
								<cardholderip xsi:type="xsd:string">67.192.84.146</cardholderip>
							</m:payment>
						</SOAP-ENV:Body>
					</SOAP-ENV:Envelope>`</xsl:if>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
