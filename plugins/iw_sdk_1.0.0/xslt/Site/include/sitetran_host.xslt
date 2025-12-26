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
		<transaction name="__register_hosted_user__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="register">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>INSERT INTO ?.userprofiles set FirstName=?,LastName=?,Password=?,Company=?,Title=?,Email=?, LisOfFlowId=?, ListOfQueryId=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>first_name</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>last_name</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>password</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>company</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>title</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>idlist</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>qidlist</input>
						<mapping quoted="true" type=""/>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
		<transaction name="__register_hosted_company__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="register">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>INSERT INTO ?.companies set FirstName=?,LastName=?,Password=?,Name=?,Configuration=?,Administrator=?,Token=?,SolutionType=?,FlowList=?,QueryList=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>first_name</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>last_name</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>password</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>company</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>configuration</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>token</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>type</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>idlist</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>qidlist</input>
						<mapping quoted="true" type=""/>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
		<transaction name="__update_hosted_company__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="register">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>UPDATE ?.companies SET FirstName=?,LastName=?,Configuration=?,Administrator=?,FlowList=?,QueryList=? WHERE Name=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>first_name</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>last_name</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>configuration</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>idlist</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>qidlist</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>company</input>
						<mapping quoted="true" type=""/>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
	<transaction name="__update_hosted_user__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="update">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>UPDATE ?.userprofiles set FirstName=?,LastName=?,Company=?,Title=?,Email=? WHERE Email=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>first_name</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>last_name</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>company</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>title</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
	<transaction name="__get_hosted_user__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="getuser">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>SELECT Email,FirstName,LastName,Password,Company,Title FROM ?.userprofiles WHERE Email=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>Email</input>
						<mapping quoted="false" type="return">fe</mapping>
					</parameter>
					<parameter>
						<input>FirstName</input>
						<mapping quoted="false" type="return">fn</mapping>
					</parameter>
					<parameter>
						<input>LastName</input>
						<mapping quoted="false" type="return">ln</mapping>
					</parameter>
					<parameter>
						<input>Password</input>
						<mapping quoted="false" type="return">p</mapping>
					</parameter>
					<parameter>
						<input>Company</input>
						<mapping quoted="false" type="return">fc</mapping>
					</parameter>
					<parameter>
						<input>Title</input>
						<mapping quoted="false" type="return">ft</mapping>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
	<transaction name="__get_hosted_company__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="getuser">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>SELECT Name,Administrator,FirstName,LastName,Password,SolutionType,Configuration FROM ?.companies WHERE Administrator=? AND Name=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>company</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>Administrator</input>
						<mapping quoted="false" type="return">fe</mapping>
					</parameter>
					<parameter>
						<input>Name</input>
						<mapping quoted="false" type="return">fc</mapping>
					</parameter>
					<parameter>
						<input>FirstName</input>
						<mapping quoted="false" type="return">fn</mapping>
					</parameter>
					<parameter>
						<input>LastName</input>
						<mapping quoted="false" type="return">ln</mapping>
					</parameter>
					<parameter>
						<input>Password</input>
						<mapping quoted="false" type="return">p</mapping>
					</parameter>
					<parameter>
						<input>SolutionType</input>
						<mapping quoted="false" type="return">ft</mapping>
					</parameter>
					<parameter>
						<input>Configuration</input>
						<mapping quoted="false" type="return">cc</mapping>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
	<transaction name="__login_hosted_user__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="login">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>SELECT userprofiles.Email, userprofiles.Password, userprofiles.LisOfFlowId, userprofiles.ListOfQueryId, companies.Configuration FROM ?.userprofiles LEFT JOIN ?.companies ON userprofiles.Company=companies.Name WHERE userprofiles.Email=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>Email</input>
						<mapping quoted="false" type="return">fe</mapping>
					</parameter>
					<parameter>
						<input>Password</input>
						<mapping quoted="false" type="return">p</mapping>
					</parameter>
					<parameter>
						<input>LisOfFlowId</input>
						<mapping quoted="false" type="return">t</mapping>
					</parameter>
					<parameter>
						<input>ListOfQueryId</input>
						<mapping quoted="false" type="return">q</mapping>
					</parameter>
					<parameter>
						<input>Configuration</input>
						<mapping quoted="false" type="return">cc</mapping>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
	<transaction name="__request_register_hosted_user__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="login">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>SELECT companies.Administrator, companies.FlowLIst, companies.QueryList FROM ?.companies WHERE companies.Name=? AND companies.Token=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>company</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>token</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>Administrator</input>
						<mapping quoted="false" type="return">fe</mapping>
					</parameter>
					<parameter>
						<input>FlowLIst</input>
						<mapping quoted="false" type="return">t</mapping>
					</parameter>
					<parameter>
						<input>QueryList</input>
						<mapping quoted="false" type="return">q</mapping>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
	<transaction name="__change_password_hosted_user__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="changepassword">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>UPDATE ?.userprofiles SET Password=? WHERE Email=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>new_password</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
	<transaction name="__change_password_hosted_company__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="changepassword">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>UPDATE ?.companies SET Password=? WHERE Administrator=? AND Name=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>new_password</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>company</input>
						<mapping quoted="true" type=""/>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
	<transaction name="__assign_hosted_user__" type="sequential">
		<transform/>
		<classname>com.interweave.adapter.database.IWSqlBase</classname>
		<datamap name="assign">
			<driver>@DBDriver@</driver>
			<url>@DBDSN@</url>
			<user>@DBUser@</user>
			<password>@DBPassword@</password>
			<access type="procedure">
				<statementpre>UPDATE ?.userprofiles SET LisOfFlowId =?, ListOfQueryId=? WHERE Email=?</statementpre>
				<statementpost/>
				<where/>
				<values>
					<parameter>
						<input>DBName</input>
						<mapping quoted="false" type=""/>
					</parameter>
					<parameter>
						<input>idlist</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>qidlist</input>
						<mapping quoted="true" type=""/>
					</parameter>
					<parameter>
						<input>email</input>
						<mapping quoted="true" type=""/>
					</parameter>
				</values>
			</access>
		</datamap>
	</transaction>
	</xsl:template>
</xsl:stylesheet>
