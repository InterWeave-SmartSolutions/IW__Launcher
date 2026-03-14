<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='getleadid']/data/row">
			<xsl:if test="col[@name='Id']">
				<xsl:variable name="SFLeadCMSId" select="col[@name='Id']"/>
				<xsl:variable name="SFExternalLeadId" select="col[@name='External_Lead_ID__c']"/>
				<xsl:variable name="CMSLastTimeStamp">
					<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='getleads']/data/row">
						<xsl:if test="$SFExternalLeadId=col[@name='lid']">
							<xsl:value-of select="col[@name='lastmodifiedtimestamp']"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:variable>
					update lead set sfleadid='<xsl:value-of select="$SFLeadCMSId"/>', lastmodifiedtimestamp='<xsl:value-of select="$CMSLastTimeStamp"/>', sflastmodified='<xsl:value-of select="$CMSLastTimeStamp"/>' where lid=<xsl:value-of select="$SFExternalLeadId"/>`
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
