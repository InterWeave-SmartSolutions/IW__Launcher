<?xml version="1.0" encoding="utf-8"?>
<!--This XSLT class loads the available IW transactions -->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output omit-xml-declaration="yes" />
	<xsl:strip-space elements="*" />
	<xsl:template name="blankconnect">
		<driver></driver>
		<url></url>
		<user></user>
		<password></password>
	</xsl:template>
	<xsl:template name="sitetran">
		<transaction name="index" type="sequential">
			<transform>sessionid</transform>
		</transaction>
<!-- not used
		<transaction name="daemonstart" type="sequential">
			<transform>daemonstart</transform>
		</transaction>
		<transaction name="sessiondata" type="sequential">
			<transform />
		</transaction>
 -->		
		<transaction name="session" type="chain">
			<transform />
			<classname>com.interweave.adapter.IWGetSession</classname>
			<datamap name="sessionvars">
				<xsl:call-template name="blankconnect" />
				<access type="procedure">
					<statementpre />
					<statementpost />
				</access>
			</datamap>
		</transaction>
	</xsl:template>
</xsl:stylesheet>
