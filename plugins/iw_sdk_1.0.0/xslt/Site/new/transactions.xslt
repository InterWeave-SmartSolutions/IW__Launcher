<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:import href="../include/appconstants.xslt"/>
 	<xsl:import href="../../include/dataconnections.xslt"/> 	 
	<xsl:import href="../include/sitetran.xslt"/>	 
	<xsl:import href="include/soltran.xslt"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<iwmappings>
			<xsl:call-template name="sitetran"/>
			<xsl:call-template name="soltran"/>
			<xsl:call-template name="soltran1"/>
		</iwmappings>
	</xsl:template>
</xsl:stylesheet>