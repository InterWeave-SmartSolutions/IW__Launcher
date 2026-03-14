/**
 * Production field schemas extracted from workspace XSLT transformers.
 *
 * These replace the hardcoded sample schemas in FieldMappingPanel with
 * real field names from actual InterWeave integration projects.
 *
 * Source: workspace/ XSLT transformers + docs/production-reference/workspace-projects/
 */

import type { FieldDef } from "@/components/mappings/FieldMappingPanel";

// ─── Creatio CRM Fields (from workspace/Creatio_Magento2_Integration) ─────

export const CREATIO_ACCOUNT_FIELDS: FieldDef[] = [
  { name: "Id", type: "string", label: "Account ID" },
  { name: "Name", type: "string", label: "Account Name" },
  { name: "Phone", type: "string", label: "Phone" },
  { name: "Email", type: "string", label: "Email" },
  { name: "Address", type: "string", label: "Address" },
  { name: "City", type: "string", label: "City" },
  { name: "Region", type: "string", label: "State/Region" },
  { name: "Zip", type: "string", label: "Postal Code" },
  { name: "Country", type: "string", label: "Country" },
];

// ─── Magento 2 Fields (from workspace/Creatio_Magento2_Integration) ────

export const MAGENTO_CUSTOMER_FIELDS: FieldDef[] = [
  { name: "id", type: "integer", label: "Customer ID" },
  { name: "email", type: "string", label: "Email" },
  { name: "firstname", type: "string", label: "First Name" },
  { name: "lastname", type: "string", label: "Last Name" },
  { name: "created_at", type: "datetime", label: "Created At" },
  { name: "updated_at", type: "datetime", label: "Updated At" },
  { name: "group_id", type: "integer", label: "Customer Group" },
  { name: "addresses.street", type: "string", label: "Street Address" },
  { name: "addresses.city", type: "string", label: "City" },
  { name: "addresses.region", type: "string", label: "Region" },
  { name: "addresses.postcode", type: "string", label: "Postal Code" },
  { name: "addresses.country_id", type: "string", label: "Country" },
  { name: "addresses.telephone", type: "string", label: "Telephone" },
  { name: "custom_attributes.creatio_id", type: "string", label: "Creatio ID (Custom)" },
];

// ─── Salesforce Fields (from production SF2QBBase XSLTs) ─────────────

export const SALESFORCE_ACCOUNT_FIELDS: FieldDef[] = [
  { name: "Id", type: "reference", label: "Account ID" },
  { name: "Name", type: "string", label: "Account Name" },
  { name: "AccountNumber", type: "string", label: "Account Number" },
  { name: "BillingStreet", type: "string", label: "Billing Street" },
  { name: "BillingCity", type: "string", label: "Billing City" },
  { name: "BillingState", type: "string", label: "Billing State" },
  { name: "BillingPostalCode", type: "string", label: "Billing Zip" },
  { name: "BillingCountry", type: "string", label: "Billing Country" },
  { name: "ShippingStreet", type: "string", label: "Shipping Street" },
  { name: "Phone", type: "string", label: "Phone" },
  { name: "Fax", type: "string", label: "Fax" },
  { name: "Website", type: "string", label: "Website" },
  { name: "Type", type: "string", label: "Account Type" },
  { name: "Rating", type: "string", label: "Rating" },
  { name: "NumberOfEmployees", type: "integer", label: "Employees" },
  { name: "TickerSymbol", type: "string", label: "Ticker Symbol" },
  { name: "OwnerId", type: "reference", label: "Owner" },
  { name: "CreatedDate", type: "datetime", label: "Created Date" },
  { name: "LastModifiedDate", type: "datetime", label: "Last Modified" },
  { name: "QB_Full_Name__c", type: "string", label: "QB Full Name (Custom)" },
];

export const SALESFORCE_CONTACT_FIELDS: FieldDef[] = [
  { name: "Contact.Id", type: "reference", label: "Contact ID" },
  { name: "Contact.FirstName", type: "string", label: "First Name" },
  { name: "Contact.LastName", type: "string", label: "Last Name" },
  { name: "Contact.Name", type: "string", label: "Full Name" },
  { name: "Contact.Email", type: "string", label: "Email" },
  { name: "Contact.Phone", type: "string", label: "Phone" },
  { name: "Contact.HomePhone", type: "string", label: "Home Phone" },
  { name: "Contact.MobilePhone", type: "string", label: "Mobile" },
  { name: "Contact.OtherPhone", type: "string", label: "Other Phone" },
  { name: "Contact.Fax", type: "string", label: "Fax" },
  { name: "Contact.Salutation", type: "string", label: "Salutation" },
  { name: "Contact.MailingStreet", type: "string", label: "Mailing Street" },
  { name: "Contact.MailingCity", type: "string", label: "Mailing City" },
  { name: "Contact.MailingState", type: "string", label: "Mailing State" },
  { name: "Contact.MailingPostalCode", type: "string", label: "Mailing Zip" },
  { name: "Contact.MailingCountry", type: "string", label: "Mailing Country" },
];

export const SALESFORCE_OPPORTUNITY_FIELDS: FieldDef[] = [
  { name: "Id", type: "reference", label: "Opportunity ID" },
  { name: "Name", type: "string", label: "Opportunity Name" },
  { name: "AccountId", type: "reference", label: "Account ID" },
  { name: "Amount", type: "currency", label: "Amount" },
  { name: "CloseDate", type: "datetime", label: "Close Date" },
  { name: "CurrencyIsoCode", type: "string", label: "Currency" },
  { name: "Invoice_Number__c", type: "string", label: "Invoice Number (Custom)" },
  { name: "Invoice_Date__c", type: "datetime", label: "Invoice Date (Custom)" },
];

// ─── QuickBooks Fields (from production SF2QBBase XSLTs) ─────────────

export const QUICKBOOKS_CUSTOMER_FIELDS: FieldDef[] = [
  { name: "Name", type: "string", label: "Customer Name" },
  { name: "CompanyName", type: "string", label: "Company Name" },
  { name: "FirstName", type: "string", label: "First Name" },
  { name: "LastName", type: "string", label: "Last Name" },
  { name: "Salutation", type: "string", label: "Salutation" },
  { name: "Phone", type: "string", label: "Phone" },
  { name: "Fax", type: "string", label: "Fax" },
  { name: "Email", type: "string", label: "Email" },
  { name: "BillAddress.Addr1", type: "string", label: "Bill Address Line 1" },
  { name: "BillAddress.City", type: "string", label: "Bill City" },
  { name: "BillAddress.State", type: "string", label: "Bill State" },
  { name: "BillAddress.PostalCode", type: "string", label: "Bill Postal Code" },
  { name: "BillAddress.Country", type: "string", label: "Bill Country" },
  { name: "ShipAddress.Addr1", type: "string", label: "Ship Address Line 1" },
  { name: "ShipAddress.City", type: "string", label: "Ship City" },
  { name: "ShipAddress.State", type: "string", label: "Ship State" },
  { name: "ShipAddress.PostalCode", type: "string", label: "Ship Postal Code" },
  { name: "ShipAddress.Country", type: "string", label: "Ship Country" },
  { name: "AccountNumber", type: "string", label: "Account Number" },
  { name: "Balance", type: "currency", label: "Balance" },
  { name: "CreditLimit", type: "currency", label: "Credit Limit" },
  { name: "TermsRef.FullName", type: "reference", label: "Payment Terms" },
  { name: "SalesRepRef.FullName", type: "reference", label: "Sales Rep" },
];

// ─── Schema Catalog (keyed by solution type + object type) ───────────

export interface SchemaEntry {
  sourceFields: FieldDef[];
  targetFields: FieldDef[];
  sourceLabel: string;
  targetLabel: string;
}

/**
 * Schema catalog keyed by "{solutionPrefix}:{mappingKey}".
 * Used by FieldMappingPanel to provide real production schemas
 * instead of sample data.
 */
export const SCHEMA_CATALOG: Record<string, SchemaEntry> = {
  // Creatio ↔ Magento
  "CRM:SyncTypeAC": {
    sourceFields: CREATIO_ACCOUNT_FIELDS,
    targetFields: MAGENTO_CUSTOMER_FIELDS,
    sourceLabel: "Creatio Account",
    targetLabel: "Magento Customer",
  },

  // Salesforce ↔ QuickBooks (Customer)
  "SF:SyncTypeAC": {
    sourceFields: SALESFORCE_ACCOUNT_FIELDS,
    targetFields: QUICKBOOKS_CUSTOMER_FIELDS,
    sourceLabel: "Salesforce Account",
    targetLabel: "QuickBooks Customer",
  },

  // Salesforce ↔ QuickBooks (Opportunity → Invoice)
  "SF:SyncTypeInv": {
    sourceFields: SALESFORCE_OPPORTUNITY_FIELDS,
    targetFields: [
      { name: "RefNumber", type: "string", label: "Invoice Number" },
      { name: "TxnDate", type: "datetime", label: "Transaction Date" },
      { name: "DueDate", type: "datetime", label: "Due Date" },
      { name: "CustomerRef.FullName", type: "reference", label: "Customer" },
      { name: "Amount", type: "currency", label: "Amount" },
      { name: "BalanceRemaining", type: "currency", label: "Balance Remaining" },
      { name: "Memo", type: "string", label: "Memo" },
    ],
    sourceLabel: "Salesforce Opportunity",
    targetLabel: "QuickBooks Invoice",
  },

  // Salesforce ↔ QuickBooks (Product → Item)
  "SF:SyncTypePrd": {
    sourceFields: [
      { name: "Id", type: "reference", label: "Product ID" },
      { name: "Name", type: "string", label: "Product Name" },
      { name: "ProductCode", type: "string", label: "Product Code" },
      { name: "Description", type: "string", label: "Description" },
      { name: "IsActive", type: "boolean", label: "Active" },
    ],
    targetFields: [
      { name: "Name", type: "string", label: "Item Name" },
      { name: "FullName", type: "string", label: "Full Name" },
      { name: "Description", type: "string", label: "Description" },
      { name: "Price", type: "currency", label: "Price" },
      { name: "IsActive", type: "boolean", label: "Active" },
    ],
    sourceLabel: "Salesforce Product",
    targetLabel: "QuickBooks Item",
  },
};

/**
 * Look up production schemas for a given solution type and mapping key.
 * Returns null if no production schema is available (falls back to defaults).
 */
export function lookupSchema(solutionType: string, mappingKey: string): SchemaEntry | null {
  // Try solution-specific first, then generic prefix
  const prefix = solutionType.startsWith("SF") ? "SF"
    : solutionType.startsWith("CRM") ? "CRM"
    : solutionType.startsWith("SUG") ? "SUG"
    : solutionType.startsWith("OMS") ? "OMS"
    : null;

  if (prefix) {
    const key = `${prefix}:${mappingKey}`;
    if (SCHEMA_CATALOG[key]) return SCHEMA_CATALOG[key];
  }

  return null;
}
