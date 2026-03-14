<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='ordquery']/data/rowset[@name='OrderQueryReply']/rowset[@name='Order']">
			<xsl:variable name="OMSCustomerNo" select="rowset[@name='Customer']/row/col[@name='CustomerNo']"/>
			<xsl:variable name="OMSCustomerEmail" select="rowset[@name='Customer']/row/col[@name='Email']"/>
			<xsl:variable name="OMSCAType" select="rowset[@name='Customer']/rowset[@name='Address']/row/col[@name='Type_at_Address']"/>
			<xsl:variable name="OMSCALabel" select="rowset[@name='Customer']/rowset[@name='Address']/row/col[@name='Label_at_Address']"/>
			<xsl:variable name="OMSCAAddr1" select="rowset[@name='Customer']/rowset[@name='Address']/row/col[@name='StreetAddress1']"/>
			<xsl:variable name="OMSCAAddr2" select="rowset[@name='Customer']/rowset[@name='Address']/row/col[@name='StreetAddress2']"/>
			<xsl:variable name="OMSCACity" select="rowset[@name='Customer']/rowset[@name='Address']/row/col[@name='City']"/>
			<xsl:variable name="OMSCAStateProv" select="rowset[@name='Customer']/rowset[@name='Address']/row/col[@name='StateProvCode']"/>
			<xsl:variable name="OMSCAZipCode" select="rowset[@name='Customer']/rowset[@name='Address']/row/col[@name='ZipPostalCode']"/>
			<xsl:variable name="OMSCACountry" select="rowset[@name='Customer']/rowset[@name='Address']/row/col[@name='CountryCode']"/>
			<xsl:variable name="OMSCAPhone" select="rowset[@name='Customer']/rowset[@name='Address']/row/col[@name='PhoneNumber']"/>
			<xsl:variable name="OMSCAPhoneExt" select="rowset[@name='Customer']/rowset[@name='Address']/row/col[@name='PhoneExt']"/>
			<xsl:variable name="SFOppAccountNmIni">
				<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='SFAcctID']/data/row">
					<xsl:if test="col[@name='Customer_No__c']=$OMSCustomerNo">
						<xsl:value-of select="col[@name='Name']"/>|</xsl:if>
				</xsl:for-each>
			</xsl:variable>
			<xsl:variable name="SFOppAccountNm" select="substring-before($SFOppAccountNmIni, '|')"/>
			<xsl:variable name="OMSLName" select="rowset[@name='Customer']/rowset[@name='Address']/rowset[@name='Name']/row/col[@name='LastName']"/>
			<xsl:variable name="OMSFName" select="rowset[@name='Customer']/rowset[@name='Address']/rowset[@name='Name']/row/col[@name='FirstName']"/>
			<xsl:variable name="OMSFullName">
				<xsl:value-of select="$OMSLName"/>
				<xsl:text>, </xsl:text>
				<xsl:value-of select="$OMSFName"/>
			</xsl:variable>
			<xsl:variable name="OMSPONumber" select="rowset[@name='Payment']/row/col[@name='PONumber']"/>
			<xsl:variable name="OMSSalesTaxTotal">
				<xsl:choose>
					<xsl:when test="rowset[@name='SalesTax']/row/col[@name='SalesTaxTotal']">
						<xsl:value-of select="rowset[@name='SalesTax']/row/col[@name='SalesTaxTotal']"/>
					</xsl:when>
					<xsl:otherwise>0.00</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="OMSOrderStatus" select="row/col[@name='OrderStatus']"/>
			<xsl:variable name="OMSPlacedBy" select="row/col[@name='PlacedBy']"/>
			<xsl:variable name="OMSComments" select="row/col[@name='Comments']"/>
			<xsl:variable name="OMSOrderReason" select="row/col[@name='ReasonCanceled']"/>
			<xsl:variable name="OMSBillingStatus" select="row/col[@name='BillingStatus']"/>
			<xsl:variable name="OMSBillingAddressType" select="rowset[@name='BillTo']/rowset[@name='Address']/row/col[@name='Type_at_Address']"/>
			<xsl:variable name="OMSBillLName" select="rowset[@name='BillTo']/rowset[@name='Address']/rowset[@name='Name']/row/col[@name='LastName']"/>
			<xsl:variable name="OMSBillFName" select="rowset[@name='BillTo']/rowset[@name='Address']/rowset[@name='Name']/row/col[@name='FirstName']"/>
			<xsl:variable name="OMSBillingAddress1" select="rowset[@name='BillTo']/rowset[@name='Address']/row/col[@name='StreetAddress1']"/>
			<xsl:variable name="OMSBillingAddress2" select="rowset[@name='BillTo']/rowset[@name='Address']/row/col[@name='StreetAddress2']"/>
			<xsl:variable name="OMSBillingCity" select="rowset[@name='BillTo']/rowset[@name='Address']/row/col[@name='City']"/>
			<xsl:variable name="OMSBillingStateProv" select="rowset[@name='BillTo']/rowset[@name='Address']/row/col[@name='StateProvCode']"/>
			<xsl:variable name="OMSBillingZipCode" select="rowset[@name='BillTo']/rowset[@name='Address']/row/col[@name='ZipPostalCode']"/>
			<xsl:variable name="OMSBillingCountry" select="rowset[@name='BillTo']/rowset[@name='Address']/row/col[@name='CountryCode']"/>
			<xsl:variable name="OMSBillingPhone" select="rowset[@name='BillTo']/rowset[@name='Address']/row/col[@name='PhoneNumber']"/>
			<xsl:variable name="OMSBillingPhoneExt" select="rowset[@name='BillTo']/rowset[@name='Address']/row/col[@name='PhoneExt']"/>
			<xsl:variable name="OMSCreditCard" select="rowset[@name='Payment']/rowset[@name='CreditCard']/row/col[@name='CreditCardType']"/>
			<xsl:variable name="OMSCustComment" select="rowset[@name='Comments']/row/col[@name='CustomerComments']"/>
			<xsl:variable name="OMSCoComment" select="rowset[@name='Comments']/row/col[@name='CompanyComments']"/>
			<xsl:variable name="SFOppNameSF" select="row/col[@name='OrderNo']"/>
			<xsl:variable name="SFOrderType" select="row/col[@name='OrderType']"/>
			<xsl:variable name="SFOppName">
				<xsl:value-of select="$SFOppAccountNm"/>-<xsl:value-of select="$SFOppNameSF"/>
			</xsl:variable>
			<xsl:variable name="SFOppCloseDateNx" select="rowset[@name='OrderDate']/rowset[@name='DateTime']/row/col[@name='Date']"/>
			<xsl:variable name="SDYP" select="string-length($SFOppCloseDateNx)-5"/>
			<xsl:variable name="DM" select="substring($SFOppCloseDateNx,1,$SDYP)"/>
			<xsl:variable name="OrderDay" select="substring-after($DM,'/')"/>
			<xsl:variable name="OrderMonth" select="substring-before($SFOppCloseDateNx,'/')"/>
			<xsl:variable name="OrderDateIni">
				<xsl:value-of select="substring($SFOppCloseDateNx,$SDYP+2,4)"/>-<xsl:if test="string-length($OrderMonth)=1">0</xsl:if>
				<xsl:value-of select="$OrderMonth"/>-<xsl:if test="string-length($OrderDay)=1">0</xsl:if>
				<xsl:value-of select="$OrderDay"/>
			</xsl:variable>
			<xsl:variable name="OrderDate">
				<xsl:choose>
					<xsl:when test="$OrderDateIni='--'">NULL</xsl:when>
					<xsl:otherwise>'<xsl:value-of select="$OrderDateIni"/>'</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="OrderTimeNx" select="rowset[@name='OrderDate']/rowset[@name='DateTime']/row/col[@name='Time']"/>
			<xsl:variable name="OrderTime">
				<xsl:choose>
					<xsl:when test="$OrderTimeNx=''">00:00:00.0</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$OrderTimeNx"/>:00.0</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="SFOppShipDateNx" select="rowset[@name='ShipDate']/rowset[@name='DateTime']/row/col[@name='Date']"/>
			<xsl:variable name="SDYP1" select="string-length($SFOppShipDateNx)-5"/>
			<xsl:variable name="DM1" select="substring($SFOppShipDateNx,1,$SDYP1)"/>
			<xsl:variable name="ShipDay" select="substring-after($DM1,'/')"/>
			<xsl:variable name="ShipMonth" select="substring-before($SFOppShipDateNx,'/')"/>
			<xsl:variable name="ShipDateIni">
				<xsl:value-of select="substring($SFOppShipDateNx,$SDYP1+2,4)"/>-<xsl:if test="string-length($ShipMonth)=1">0</xsl:if>
				<xsl:value-of select="$ShipMonth"/>-<xsl:if test="string-length($ShipDay)=1">0</xsl:if>
				<xsl:value-of select="$ShipDay"/>
			</xsl:variable>
			<xsl:variable name="ShipDate">
				<xsl:choose>
					<xsl:when test="$ShipDateIni='--'">NULL</xsl:when>
					<xsl:otherwise>'<xsl:value-of select="$ShipDateIni"/>'</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="SFOppUpdDateNx" select="rowset[@name='LastUpd']/rowset[@name='DateTime']/row/col[@name='Date']"/>
			<xsl:variable name="SDYP2" select="string-length($SFOppUpdDateNx)-5"/>
			<xsl:variable name="DM2" select="substring($SFOppUpdDateNx,1,$SDYP2)"/>
			<xsl:variable name="UpdDay" select="substring-after($DM2,'/')"/>
			<xsl:variable name="UpdMonth" select="substring-before($SFOppUpdDateNx,'/')"/>
			<xsl:variable name="UpdDateIni">
				<xsl:value-of select="substring($SFOppUpdDateNx,$SDYP2+2,4)"/>-<xsl:if test="string-length($UpdMonth)=1">0</xsl:if>
				<xsl:value-of select="$UpdMonth"/>-<xsl:if test="string-length($UpdDay)=1">0</xsl:if>
				<xsl:value-of select="$UpdDay"/>
			</xsl:variable>
			<xsl:variable name="UpdDate">
				<xsl:choose>
					<xsl:when test="$UpdDateIni='--'">NULL</xsl:when>
					<xsl:otherwise>'<xsl:value-of select="$UpdDateIni"/>'</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="UpdTimeNx" select="rowset[@name='LastUpd']/rowset[@name='DateTime']/row/col[@name='Time']"/>
			<xsl:variable name="UpdTime">
				<xsl:value-of select="$UpdTimeNx"/>:00.0</xsl:variable>
			<xsl:variable name="OMSLULName" select="rowset[@name='LastUpdBy']/rowset[@name='Name']/row/col[@name='LastName']"/>
			<xsl:variable name="OMSLUFName" select="rowset[@name='LastUpdBy']/rowset[@name='Name']/row/col[@name='FirstName']"/>
			<xsl:variable name="SFOppAmount" select="row/col[@name='OrderAmount']"/>
			<xsl:variable name="SFOppNet" select="row/col[@name='OrderNet']"/>
			<xsl:variable name="SFOppStageName">
				<xsl:choose>
					<xsl:when test="$OMSBillingStatus='Authorized'">Closed Won - Authorized</xsl:when>
					<xsl:when test="$OMSBillingStatus='Paid'">Closed Won - Paid</xsl:when>
					<xsl:when test="$OMSBillingStatus='Refunded'">Closed Lost - Refund</xsl:when>
					<xsl:when test="$OMSBillingStatus='Canceled'">Closed Lost - Cancelled</xsl:when>
					<xsl:when test="$OMSBillingStatus='Declined CC'">Closed Lost</xsl:when>
					<xsl:otherwise>Closed Won - Paid</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="OMSOrderDiscount">
				<xsl:choose>
					<xsl:when test="rowset[@name='Discounts']/row/col[@name='OrderDiscount']">
						<xsl:choose>
							<xsl:when test="rowset[@name='Discounts']/row/col[@name='OrderDiscount']=''">0.0</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="rowset[@name='Discounts']/row/col[@name='OrderDiscount']"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>0.0</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="OMSCouponDiscount">
				<xsl:choose>
					<xsl:when test="rowset[@name='Discounts']/row/col[@name='CouponDiscount']">
						<xsl:choose>
							<xsl:when test="rowset[@name='Discounts']/row/col[@name='CouponDiscount']=''">0.0</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="rowset[@name='Discounts']/row/col[@name='CouponDiscount']"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>0.0</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="OMSCouponDiscountCode">
				<xsl:choose>
					<xsl:when test="rowset[@name='Discounts']/row/col[@name='Code_at_CouponDiscount']">
						<xsl:value-of select="rowset[@name='Discounts']/row/col[@name='Code_at_CouponDiscount']"/>
					</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="OMSPaymentMethod">
				<xsl:choose>
					<xsl:when test="rowset[@name='Payment']/row/col[@name='PaymentMethod']">
						<xsl:value-of select="rowset[@name='Payment']/row/col[@name='PaymentMethod']"/>
					</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>
			</xsl:variable>insert into SF_OrderQuery (OrderNo, OrderDate, OrderTime, OrderType, CustomerNo, CustomerEmail, CA_Type, CA_Label, CA_FirstName, CA_LastName, CA_Address1, CA_Address2, CA_City, CA_State, CA_Zip, CA_Country, CA_Phone, CA_PhoneExt, OrderStatus, ShipDate, ReasonCancelled, BillingStatus, OrderAmount, OrderNet, BT_Type, BT_FirstName, BT_LastName, BT_Address1, BT_Address2, BT_City, BT_State, BT_Zip, BT_Country, BT_Phone, BT_PhoneExt, PaymentMethod, PlacedBy, PONumber, Comments, CustomerComments, OrderDiscount, CouponDiscount, CouponCode,  SalesTaxTotal,  LastUpd_Date, LastUpd_Time, LastUpd_ByFirst, LastUpd_ByLast) values (<xsl:value-of select="$SFOppNameSF"/>,<xsl:value-of select="$OrderDate"/>,'<xsl:value-of select="$OrderTime"/>','<xsl:value-of select="$SFOrderType"/>',<xsl:value-of select="$OMSCustomerNo"/>,'<xsl:value-of select="$OMSCustomerEmail"/>','<xsl:value-of select="$OMSCAType"/>','<xsl:value-of select="$OMSCALabel"/>','<xsl:value-of select="$OMSFName"/>','<xsl:value-of select="$OMSLName"/>','<xsl:value-of select="$OMSCAAddr1"/>','<xsl:value-of select="$OMSCAAddr2"/>','<xsl:value-of select="$OMSCACity"/>','<xsl:value-of select="$OMSCAStateProv"/>','<xsl:value-of select="$OMSCAZipCode"/>','<xsl:value-of select="$OMSCACountry"/>','<xsl:value-of select="$OMSCAPhone"/>','<xsl:value-of select="$OMSCAPhoneExt"/>','<xsl:value-of select="$OMSOrderStatus"/>',<xsl:value-of select="$ShipDate"/>,'<xsl:value-of select="$OMSOrderReason"/>','<xsl:value-of select="$OMSBillingStatus"/>',<xsl:value-of select="$SFOppAmount"/>,<xsl:value-of select="$SFOppNet"/>,'<xsl:value-of select="$OMSBillingAddressType"/>','<xsl:value-of select="$OMSBillFName"/>','<xsl:value-of select="$OMSBillLName"/>','<xsl:value-of select="$OMSBillingAddress1"/>','<xsl:value-of select="$OMSBillingAddress2"/>','<xsl:value-of select="$OMSBillingCity"/>','<xsl:value-of select="$OMSBillingStateProv"/>','<xsl:value-of select="$OMSBillingZipCode"/>','<xsl:value-of select="$OMSBillingCountry"/>','<xsl:value-of select="$OMSBillingPhone"/>','<xsl:value-of select="$OMSBillingPhoneExt"/>','<xsl:value-of select="$OMSPaymentMethod"/>','<xsl:value-of select="$OMSPlacedBy"/>','<xsl:value-of select="$OMSPONumber"/>','<xsl:value-of select="$OMSCoComment"/>','<xsl:value-of select="$OMSCustComment"/>',<xsl:value-of select="$OMSOrderDiscount"/>,<xsl:value-of select="$OMSCouponDiscount"/>,'<xsl:value-of select="$OMSCouponDiscountCode"/>',<xsl:value-of select="$OMSSalesTaxTotal"/>,<xsl:value-of select="$UpdDate"/>,'<xsl:value-of select="$UpdTime"/>','<xsl:value-of select="$OMSLULName"/>','<xsl:value-of select="$OMSLUFName"/>')`select SF_OrderID, OrderNo from SF_OrderQuery where OrderNo=<xsl:value-of select="$SFOppNameSF"/>`</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
