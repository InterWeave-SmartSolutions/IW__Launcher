# InterWeave Production Engine JARs Reference

Copied from production server on 2026-03-13.

Source: `C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\*\WEB-INF\lib\`

## Modular Engine JARs (16 files)

These are the core InterWeave engine components, shared across all deployed solutions.
Copied from `SF2QBBase/WEB-INF/lib/` (identical across all webapps).

| JAR | Description |
|-----|-------------|
| iwcore.jar | Core engine runtime |
| iwdatabase.jar | Database connectivity and ORM |
| iwhttp.jar | HTTP client/server support |
| iwhttps.jar | HTTPS/SSL extension for iwhttp |
| iwconnector.jar | External system connector framework |
| iwadapter.jar | Data adapter/transformation layer |
| iwclib.jar | Common library utilities |
| iwcreditcard.jar | Credit card processing module |
| iwdeveloper.jar | Developer/debugging tools |
| iwemail.jar | Email sending/receiving |
| iwencrypt.jar | Encryption utilities |
| iwfilesystem.jar | File system operations |
| iwlicense.jar | License management |
| iwservices.jar | Service layer framework |
| iwservlets.jar | Servlet integration |
| iwactionscript.jar | ActionScript engine (solution logic execution) |

## Deployed Solution JARs (5 files)

Located in `solutions/` subdirectory. Each is a compiled InterWeave solution
containing the business logic for its respective webapp.

| JAR | Webapp | Description |
|-----|--------|-------------|
| iwsolution_SF2QBBase.jar | SF2QBBase | Salesforce-to-QuickBooks base integration |
| iwsolution_SF2QBCustom.jar | SF2QBCustom | Salesforce-to-QuickBooks custom extensions |
| iwsolution_SF2AuthNet.jar | SF2AuthNet | Salesforce-to-Authorize.Net integration |
| iwsolution_QB_POAgent.jar | QB_POAgent | QuickBooks Purchase Order agent |
| iwsolution_SN2QB_SP.jar | SN2QB_SP | ServiceNow-to-QuickBooks integration |
