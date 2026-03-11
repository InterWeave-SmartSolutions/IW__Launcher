<?xml version="1.0" encoding="utf-8"?>
<!--
  Creatio <-> QuickBooks Integration
  Solution-specific transaction definitions for IW transformation engine.

  Connections:
    iw* params = Creatio OData/REST API (source CRM)
    ms* params = QuickBooks Online/Desktop API (destination accounting)

  Transaction Flows:
    CRMLogin           - Authenticate to Creatio OData API
    CRMAcctSync2QB     - Creatio Accounts -> QuickBooks Customers
    CRMOrderSync2QB    - Creatio Sales Orders -> QuickBooks Sales Receipts
    CRMInvSync2QB      - Creatio Invoices -> QuickBooks Invoices
    CRMProdSync2QB     - Creatio Products -> QuickBooks Items
    CRMSRSync2QB       - Creatio Service Requests -> QuickBooks Credit Memos
    CRMDRSSync         - Deferred response sync (payment gateway callbacks)

  Queries:
    Creatio2AuthNetQ   - Creatio to Authorize.Net payment query
    Creatio2StrpQ      - Creatio to Stripe payment query
    CRMAcct2QBQ        - Creatio Account to QuickBooks Customer query
    CRMOrder2QBQ       - Creatio Sales Order to QuickBooks query
    CRMInv2QBQ         - Creatio Invoice to QuickBooks query
    CRMProd2QBQ        - Creatio Product to QuickBooks Item query
    CRMSRSync2QBQ      - Creatio Service Request to QuickBooks query
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output omit-xml-declaration="yes" />
	<xsl:strip-space elements="*" />

	<!-- ============================================================ -->
	<!-- soltran: Creatio -> QuickBooks transaction flows              -->
	<!-- ============================================================ -->
	<xsl:template name="soltran">

		<!-- Creatio OData Login / Session Initialization -->
		<transaction name="CRMLogin" type="sequential">
			<transform>CRMLogin</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="login">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/ServiceModel/AuthService.svc/Login</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="post">
					<statementpre>{"UserName":"?iwuser?","UserPassword":"?password?"}</statementpre>
					<statementpost/>
				</access>
			</datamap>
			<nexttransaction name="BPMTransactions2Auth:CRMAcctSync2QB;CRMOrderSync2QB:CRMOrderSync2QB;CRMInvSync2QB:CRMInvSync2QB;CRMProdSync2QB:CRMProdSync2QB;CRMSRSync2QB:CRMSRSync2QB;" type="Unconditional"/>
		</transaction>

		<!-- Sync Accounts from Creatio to QuickBooks Customers -->
		<transaction name="CRMAcctSync2QB" type="sequential">
			<transform>CRMAcctSync2QB</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<!-- Step 1: Read Accounts from Creatio OData -->
			<datamap name="creatio_accounts">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Account?$select=Id,Name,Phone,Email,Address,City,Region,Zip,Country&amp;$filter=ModifiedOn gt @lastSyncDate</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<!-- Step 2: Write Customers to QuickBooks -->
			<datamap name="qb_customers">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/v3/company/@companyId/customer</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Sales Orders from Creatio to QuickBooks Sales Receipts -->
		<transaction name="CRMOrderSync2QB" type="sequential">
			<transform>CRMOrderSync2QB</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="creatio_orders">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Order?$select=Id,Number,Account,Amount,PaymentStatus,DeliveryStatus&amp;$filter=ModifiedOn gt @lastSyncDate</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="qb_salesreceipts">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/v3/company/@companyId/salesreceipt</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Invoices from Creatio to QuickBooks Invoices -->
		<transaction name="CRMInvSync2QB" type="sequential">
			<transform>CRMInvSync2QB</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="creatio_invoices">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Invoice?$select=Id,Number,Account,Amount,PaymentStatus&amp;$filter=ModifiedOn gt @lastSyncDate</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="qb_invoices">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/v3/company/@companyId/invoice</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Products from Creatio to QuickBooks Items -->
		<transaction name="CRMProdSync2QB" type="sequential">
			<transform>CRMProdSync2QB</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="creatio_products">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Product?$select=Id,Name,Code,Price,Unit,IsArchive&amp;$filter=ModifiedOn gt @lastSyncDate</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="qb_items">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/v3/company/@companyId/item</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Service Requests from Creatio to QuickBooks Credit Memos -->
		<transaction name="CRMSRSync2QB" type="sequential">
			<transform>CRMSRSync2QB</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="creatio_servicerequests">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Case?$select=Id,Number,Account,Subject,Status,Priority&amp;$filter=ModifiedOn gt @lastSyncDate</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="qb_creditmemos">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/v3/company/@companyId/creditmemo</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Deferred Response Sync (payment gateway callbacks) -->
		<transaction name="CRMDRSSync" type="sequential">
			<transform>CRMDRSSync</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="deferred_response">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/IWDeferredResponse?$filter=Status eq 'Pending'</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

	</xsl:template>

	<!-- ============================================================ -->
	<!-- soltran1: Query flows (on-demand, payment + lookup)          -->
	<!-- ============================================================ -->
	<xsl:template name="soltran1">

		<!-- Query: Creatio to Authorize.Net payment processing -->
		<transaction name="Creatio2AuthNetQ" type="sequential">
			<transform>Creatio2AuthNetQ</transform>
			<classname>com.interweave.adapter.http.IWHttpBaseAdaptor</classname>
			<datamap name="authnet_payment">
				<driver/>
				<url>https://api.authorize.net/xml/v1/request.api</url>
				<user/>
				<password/>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Query: Creatio to Stripe payment processing -->
		<transaction name="Creatio2StrpQ" type="sequential">
			<transform>Creatio2StrpQ</transform>
			<classname>com.interweave.adapter.http.IWHttpBaseAdaptor</classname>
			<datamap name="stripe_payment">
				<driver/>
				<url>https://api.stripe.com/v1/charges</url>
				<user/>
				<password/>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Query: Creatio Account to QuickBooks Customer lookup -->
		<transaction name="CRMAcct2QBQ" type="sequential">
			<transform>CRMAcct2QBQ</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="creatio_account">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Account(@accountId)?$select=Id,Name,Phone,Email,Address,City,Region,Zip,Country</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="qb_customer">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/v3/company/@companyId/query?query=select * from Customer where DisplayName='@customerName'</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Query: Creatio Sales Order to QuickBooks lookup -->
		<transaction name="CRMOrder2QBQ" type="sequential">
			<transform>CRMOrder2QBQ</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="creatio_order">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Order(@orderId)?$select=Id,Number,Account,Amount,PaymentStatus</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="qb_salesreceipt">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/v3/company/@companyId/query?query=select * from SalesReceipt where DocNumber='@orderNumber'</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Query: Creatio Invoice to QuickBooks lookup -->
		<transaction name="CRMInv2QBQ" type="sequential">
			<transform>CRMInv2QBQ</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="creatio_invoice">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Invoice(@invoiceId)?$select=Id,Number,Account,Amount,PaymentStatus</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="qb_invoice">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/v3/company/@companyId/query?query=select * from Invoice where DocNumber='@invoiceNumber'</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Query: Creatio Product to QuickBooks Item lookup -->
		<transaction name="CRMProd2QBQ" type="sequential">
			<transform>CRMProd2QBQ</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="creatio_product">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Product(@productId)?$select=Id,Name,Code,Price,Unit</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="qb_item">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/v3/company/@companyId/query?query=select * from Item where Name='@productName'</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Query: Creatio Service Request to QuickBooks lookup -->
		<transaction name="CRMSRSync2QBQ" type="sequential">
			<transform>CRMSRSync2QBQ</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="creatio_servicerequest">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Case(@caseId)?$select=Id,Number,Account,Subject,Status,Priority</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="qb_creditmemo">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/v3/company/@companyId/query?query=select * from CreditMemo where DocNumber='@caseNumber'</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

	</xsl:template>

</xsl:stylesheet>
