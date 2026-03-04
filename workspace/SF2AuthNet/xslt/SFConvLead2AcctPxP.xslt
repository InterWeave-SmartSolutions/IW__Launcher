<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:variable name="OwnerId">00570000001AHw5</xsl:variable>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
			<xsl:variable name="SFId" select="row/col[@name='Id']"/>
			<xsl:variable name="IsAccepted">
				<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='payauth']/data/row">
					<xsl:variable name="R1" select="col[@name='Success']"/>
					<xsl:variable name="R13" select="col[@name='ReferenceNumber']"/>
					<xsl:if test="$R13=$SFId and number($R1)=1">1</xsl:if>
				</xsl:for-each>
			</xsl:variable>
			<xsl:if test="$IsAccepted!=''">
				<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
					<soap:Header>
						<SessionHeader xmlns="urn:partner.soap.sforce.com">
							<sessionId>
								<xsl:value-of select="$SessionId"/>
							</sessionId>
						</SessionHeader>
					</soap:Header>
					<soap:Body>
						<convertLead xmlns="urn:enterprise.soap.sforce.com">
							<leadConverts>
								<convertedStatus>Contacted</convertedStatus>
								<doNotCreateOpportunity>true</doNotCreateOpportunity>
								<leadId>
									<xsl:value-of select="$SFId"/>
								</leadId>
								<overwriteLeadSource>false</overwriteLeadSource>
								<ownerId>
									<xsl:value-of select="$OwnerId"/>
								</ownerId>
								<sendNotificationEmail>false</sendNotificationEmail>
							</leadConverts>
						</convertLead>
					</soap:Body>
				</soap:Envelope>`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
