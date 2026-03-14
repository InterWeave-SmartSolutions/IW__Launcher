<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:w="urn:schemas-microsoft-com:office:word" xmlns="http://www.w3.org/TR/REC-html40">
			<head>
				<title>Column Name</title>
				<style>
</style>
			</head>
			<body>
				<div>
					<div>
						<xsl:for-each select="iwrecordset">
							<xsl:for-each select="transaction">
							<xsl:choose>
 								<xsl:when test="@name='sessionvars'">
 								</xsl:when>
 								<xsl:otherwise>
									<xsl:for-each select="datamap">
										<p class="MsoNormal" style="margin-left:288.0pt;text-indent:36.0pt"><xsl:value-of select="@name"></xsl:value-of></p>
										<p class="MsoNormal">
											<o:p><xsl:text> </xsl:text></o:p>
										</p>
										<xsl:for-each select="data">
											<table class="MsoNormalTable" border="1" cellspacing="0" cellpadding="0" align="left" style="border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;
 mso-yfti-tbllook:480;mso-table-lspace:9.0pt;margin-left:6.75pt;mso-table-rspace:
 9.0pt;margin-right:6.75pt;mso-table-anchor-vertical:paragraph;mso-table-anchor-horizontal:
 margin;mso-table-left:left;mso-table-top:84.3pt;mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
 mso-border-insideh:.5pt solid windowtext;mso-border-insidev:.5pt solid windowtext">
												<tr style="mso-yfti-irow:0;mso-yfti-firstrow:yes">
													<td width="295" valign="top" style="width:221.4pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt">
														<p class="MsoNormal" align="center" style="mso-margin-top-alt:auto;mso-margin-bottom-alt:
  auto;text-align:center;mso-element:frame;mso-element-frame-hspace:9.0pt;
  mso-element-wrap:around;mso-element-anchor-vertical:paragraph;mso-element-anchor-horizontal:
  margin;mso-element-top:84.3pt;mso-height-rule:exactly">Column Name</p>
													</td>
													<td width="616" valign="top" style="width:462.2pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt">
														<p class="MsoNormal" align="center" style="mso-margin-top-alt:auto;mso-margin-bottom-alt:
  auto;text-align:center;mso-element:frame;mso-element-frame-hspace:9.0pt;
  mso-element-wrap:around;mso-element-anchor-vertical:paragraph;mso-element-anchor-horizontal:
  margin;mso-element-top:84.3pt;mso-height-rule:exactly">Column Value</p>
													</td>
												</tr>
												<xsl:for-each select="row">
													<xsl:for-each select="col">
														<tr style="mso-yfti-irow:1">
															<td width="295" valign="top" style="width:221.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt">
																<p class="MsoNormal" style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;
  mso-element:frame;mso-element-frame-hspace:9.0pt;mso-element-wrap:around;
  mso-element-anchor-vertical:paragraph;mso-element-anchor-horizontal:margin;
  mso-element-top:84.3pt;mso-height-rule:exactly"><xsl:value-of select="@name"></xsl:value-of></p>
															</td>
															<td width="616" valign="top" style="width:462.2pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt">
																<p class="MsoNormal" style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;
  mso-element:frame;mso-element-frame-hspace:9.0pt;mso-element-wrap:around;
  mso-element-anchor-vertical:paragraph;mso-element-anchor-horizontal:margin;
  mso-element-top:84.3pt;mso-height-rule:exactly"><xsl:value-of select="."></xsl:value-of></p>
															</td>
														</tr>
													</xsl:for-each>
												</xsl:for-each>
											</table>
										</xsl:for-each>
									</xsl:for-each>
								</xsl:otherwise>
								</xsl:choose>
							</xsl:for-each>
						</xsl:for-each>
						<p class="MsoNormal" style="mso-margin-top-alt:auto;mso-margin-bottom-alt:auto">
							<o:p><xsl:text> </xsl:text></o:p>
						</p>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
