<?xml version="1.0" encoding="utf-8"?>
<!--
  Creatio <-> Magento 2 Bidirectional Integration
  Solution-specific transaction definitions for IW transformation engine.

  Connections:
    iw* params = Creatio OData/REST API (source CRM)
    ms* params = Magento 2 REST API (destination eCommerce)

  Transaction Flows:
    SyncAccounts_CRM2MG  - Creatio Accounts -> Magento Customers
    SyncAccounts_MG2CRM  - Magento Customers -> Creatio Accounts
    SyncProducts_CRM2MG  - Creatio Products  -> Magento Catalog
    SyncProducts_MG2CRM  - Magento Catalog   -> Creatio Products
    SyncOrders_MG2CRM    - Magento Orders    -> Creatio Orders
    SyncOrders_CRM2MG    - Creatio Orders    -> Magento Orders
    SyncInvoices_MG2CRM  - Magento Invoices  -> Creatio Invoices
    SyncInvoices_CRM2MG  - Creatio Invoices  -> Magento Invoices

  Queries:
    GetCreatioAccount   - Retrieve Creatio Account by ID
    GetMagentoCustomer  - Retrieve Magento 2 Customer by ID
    GetMagentoOrder     - Retrieve Magento 2 Order by ID
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output omit-xml-declaration="yes" />
	<xsl:strip-space elements="*" />

	<!-- ============================================================ -->
	<!-- soltran: Creatio -> Magento direction flows                   -->
	<!-- ============================================================ -->
	<xsl:template name="soltran">

		<!-- Sync Accounts from Creatio to Magento 2 Customers -->
		<transaction name="SyncAccounts_CRM2MG" type="sequential">
			<transform>SyncAccounts_CRM2MG</transform>
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
			<!-- Step 2: Write Customers to Magento 2 REST -->
			<datamap name="magento_customers">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/rest/V1/customers</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Accounts from Magento 2 Customers to Creatio -->
		<transaction name="SyncAccounts_MG2CRM" type="sequential">
			<transform>SyncAccounts_MG2CRM</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="magento_customers">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/rest/V1/customers/search?searchCriteria[filterGroups][0][filters][0][field]=updated_at&amp;searchCriteria[filterGroups][0][filters][0][conditionType]=gt&amp;searchCriteria[filterGroups][0][filters][0][value]=@lastSyncDate</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="creatio_accounts">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Account</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Products from Creatio to Magento 2 Catalog -->
		<transaction name="SyncProducts_CRM2MG" type="sequential">
			<transform>SyncProducts_CRM2MG</transform>
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
			<datamap name="magento_products">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/rest/V1/products</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Products from Magento 2 Catalog to Creatio -->
		<transaction name="SyncProducts_MG2CRM" type="sequential">
			<transform>SyncProducts_MG2CRM</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="magento_products">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/rest/V1/products?searchCriteria[filterGroups][0][filters][0][field]=updated_at&amp;searchCriteria[filterGroups][0][filters][0][conditionType]=gt&amp;searchCriteria[filterGroups][0][filters][0][value]=@lastSyncDate</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="creatio_products">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Product</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Orders from Magento 2 to Creatio -->
		<transaction name="SyncOrders_MG2CRM" type="sequential">
			<transform>SyncOrders_MG2CRM</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="magento_orders">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/rest/V1/orders?searchCriteria[filterGroups][0][filters][0][field]=updated_at&amp;searchCriteria[filterGroups][0][filters][0][conditionType]=gt&amp;searchCriteria[filterGroups][0][filters][0][value]=@lastSyncDate</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="creatio_orders">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Order</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Orders from Creatio to Magento 2 -->
		<transaction name="SyncOrders_CRM2MG" type="sequential">
			<transform>SyncOrders_CRM2MG</transform>
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
			<datamap name="magento_orders">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/rest/V1/orders/create</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Invoices from Magento 2 to Creatio -->
		<transaction name="SyncInvoices_MG2CRM" type="sequential">
			<transform>SyncInvoices_MG2CRM</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="magento_invoices">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/rest/V1/invoices?searchCriteria[filterGroups][0][filters][0][field]=updated_at&amp;searchCriteria[filterGroups][0][filters][0][conditionType]=gt&amp;searchCriteria[filterGroups][0][filters][0][value]=@lastSyncDate</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
			<datamap name="creatio_invoices">
				<driver><xsl:value-of select="$iwdriver"/></driver>
				<url><xsl:value-of select="$iwurl"/>/0/odata/Invoice</url>
				<user><xsl:value-of select="$iwuser"/></user>
				<password><xsl:value-of select="$password"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Sync Invoices from Creatio to Magento 2 -->
		<transaction name="SyncInvoices_CRM2MG" type="sequential">
			<transform>SyncInvoices_CRM2MG</transform>
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
			<datamap name="magento_invoices">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/rest/V1/invoices</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="post">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

	</xsl:template>

	<!-- ============================================================ -->
	<!-- soltran1: Query flows (on-demand)                            -->
	<!-- ============================================================ -->
	<xsl:template name="soltran1">

		<!-- Query: Get Creatio Account by ID -->
		<transaction name="GetCreatioAccount" type="sequential">
			<transform>GetCreatioAccount</transform>
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
		</transaction>

		<!-- Query: Get Magento 2 Customer by ID -->
		<transaction name="GetMagentoCustomer" type="sequential">
			<transform>GetMagentoCustomer</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="magento_customer">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/rest/V1/customers/@customerId</url>
				<user><xsl:value-of select="$msuser"/></user>
				<password><xsl:value-of select="$mspassword"/></password>
				<access type="get">
					<statementpre/>
					<statementpost/>
				</access>
			</datamap>
		</transaction>

		<!-- Query: Get Magento 2 Order by ID -->
		<transaction name="GetMagentoOrder" type="sequential">
			<transform>GetMagentoOrder</transform>
			<classname>com.interweave.adapter.rest.IWRestJson</classname>
			<datamap name="magento_order">
				<driver><xsl:value-of select="$msdriver"/></driver>
				<url><xsl:value-of select="$msurl"/>/rest/V1/orders/@orderId</url>
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
