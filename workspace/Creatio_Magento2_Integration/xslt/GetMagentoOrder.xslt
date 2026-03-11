<?xml version="1.0" encoding="utf-8"?>
<!-- Transformer: Get Magento 2 Order by ID
     Query transformer that formats a single Magento Order response -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<order>
			<xsl:for-each select="iwrecordset/transaction/datamap[@name='magento_order']/data/row">
				<entity_id><xsl:value-of select="col[@name='entity_id']"/></entity_id>
				<increment_id><xsl:value-of select="col[@name='increment_id']"/></increment_id>
				<status><xsl:value-of select="col[@name='status']"/></status>
				<grand_total><xsl:value-of select="col[@name='grand_total']"/></grand_total>
				<customer_email><xsl:value-of select="col[@name='customer_email']"/></customer_email>
				<created_at><xsl:value-of select="col[@name='created_at']"/></created_at>
				<shipping_amount><xsl:value-of select="col[@name='shipping_amount']"/></shipping_amount>
				<tax_amount><xsl:value-of select="col[@name='tax_amount']"/></tax_amount>
			</xsl:for-each>
		</order>
	</xsl:template>
</xsl:stylesheet>
