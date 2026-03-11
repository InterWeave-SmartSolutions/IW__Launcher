<?xml version="1.0" encoding="utf-8"?>
<!-- Transformer: Get Magento 2 Customer by ID
     Query transformer that formats a single Magento Customer response -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<customer>
			<xsl:for-each select="iwrecordset/transaction/datamap[@name='magento_customer']/data/row">
				<id><xsl:value-of select="col[@name='id']"/></id>
				<email><xsl:value-of select="col[@name='email']"/></email>
				<firstname><xsl:value-of select="col[@name='firstname']"/></firstname>
				<lastname><xsl:value-of select="col[@name='lastname']"/></lastname>
				<created_at><xsl:value-of select="col[@name='created_at']"/></created_at>
				<updated_at><xsl:value-of select="col[@name='updated_at']"/></updated_at>
				<group_id><xsl:value-of select="col[@name='group_id']"/></group_id>
			</xsl:for-each>
		</customer>
	</xsl:template>
</xsl:stylesheet>
