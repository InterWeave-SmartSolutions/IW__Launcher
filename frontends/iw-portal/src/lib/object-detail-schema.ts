/**
 * Declarative schema for per-object detail configuration properties.
 *
 * Each enabled object (SyncTypeAC, SyncTypeSO, etc.) has a set of
 * field-level properties that the user can configure. These properties
 * are stored in the same XML alongside SyncType* direction values.
 *
 * This schema mirrors the fields from the original JSP detail pages:
 *   CompanyConfigurationDetail.jsp   — Account/Customer details
 *   CompanyConfigurationDetailT.jsp  — Transaction details (SO, PO, Bill, etc.)
 *   CompanyConfigurationDetailP.jsp  — Product details
 */
import type { SolutionMeta } from "@/types/configuration";

// ─── Types ───────────────────────────────────────────────────────────

export interface DetailSelectOption {
  value: string;
  /** Template string — {crm}, {fs}, {crmcust}, {crmtran}, {fscust} replaced at render */
  label: string;
}

export interface DetailField {
  key: string;
  /** Template string for the field label */
  label: string;
  type: "text" | "select";
  defaultValue: string;
  options?: DetailSelectOption[];
  helpText?: string;
  placeholder?: string;
  maxLength?: number;
  /** Only show this field when the sync direction matches */
  showForDirections?: string[];
  /** Only show when another field has one of these values */
  showWhen?: { key: string; values: string[] };
}

export interface DetailFieldGroup {
  label: string;
  helpText?: string;
  fields: DetailField[];
  /** Only show this group when the sync direction matches */
  showForDirections?: string[];
}

export interface ObjectDetailSchema {
  /** Which SyncType key this schema applies to (e.g. "SyncTypeAC") */
  objectKey: string;
  /** Section heading template */
  sectionLabel: string;
  groups: DetailFieldGroup[];
}

// ─── Helper to resolve label templates ───────────────────────────────

export function resolveTemplate(template: string, meta: SolutionMeta): string {
  return template
    .replace(/\{crm\}/g, meta.crmName)
    .replace(/\{fs\}/g, meta.fsName)
    .replace(/\{crmcust\}/g, meta.crmCustomerLabel)
    .replace(/\{crmtran\}/g, meta.crmTransactionLabel)
    .replace(/\{fscust\}/g, meta.fsCustomerLabel);
}

// ─── Human-readable labels for config keys (for Review step) ────────

export const CONFIG_KEY_LABELS: Record<string, string> = {
  // Sync directions
  SyncTypeAC: "Account/Customer Sync",
  SyncTypeSO: "Sales Order Sync",
  SyncTypeInv: "Invoice Sync",
  SyncTypeSR: "Sales Receipt Sync",
  SyncTypePrd: "Product/Item Sync",
  SyncTypeEst: "Estimate Sync",
  SyncTypeBill: "Bill Sync",
  SyncTypeCheck: "Check Sync",
  SyncTypeCM: "Credit Memo Sync",
  SyncTypeVAC: "Vendor Sync",
  SyncTypeOJ: "Job Sync",
  SyncTypePO: "Purchase Order Sync",
  SyncTypeVC: "Vendor Credit Sync",
  SyncTypeDep: "Deposit Sync",
  SyncTypePR: "Payment Received Sync",
  SyncTypeBP: "Bill Payment Sync",
  SyncTypeCCC: "Credit Card Charge Sync",
  SyncTypeCOA: "Chart of Accounts Sync",
  SyncTypeJE: "Journal Entry Sync",
  SyncTypeTT: "Time Tracking Sync",
  SyncTypeSC: "Statement Charges Sync",

  // Account detail fields
  BSFQBLID: "Custom Binding Field (ListID)",
  AcctCustBind: "Binding Criteria",
  BSFQBFN: "Full/Normalized Name Binding",
  NormBindName: "Normalize Names for Binding",
  SFQBCustObj: "Custom Object for Customer",
  SFQBCustNm: "Field for Customer Name",
  SFQBCompNm: "Field for Company Name",
  PrimCont: "Primary Contact Selection",
  DefPrimRole: "Default Primary Role",
  SyncCnMlAcBl: "Sync Contact Address with Billing",
  UseBillAsShipCust: "Use Billing as Shipping Address",
  UseShipAsBillCust: "Use Shipping as Billing Address",
  SyncACJHr: "Hierarchy Propagation",
  HJobLevel: "Job Hierarchy Level",
  CONJob: "Object for Job/Address",
  CustTerm: "Customer Terms Field",
  CustTypeSt: "Customer Type Field",
  CustTaxCodeSt: "Customer Tax Code Field",
  CustTaxItemSt: "Customer Tax Item Field",
  SustRemBal: "Total Balance Field",
  MIddleName: "Middle Name Field",
  AccQB2SFETL: "Initial Upload Required (FS→CRM)",
  AccSF2QBETL: "Initial Upload Required (CRM→FS)",
  ACSF2QBOps: "Permitted Sync Operations (CRM→FS)",
  ACQB2SFOps: "Permitted Sync Operations (FS→CRM)",
  CreateNC: "Customer Creation Trigger",
  SFOppStVal: "Stage for Customer Creation",
  SFCrCusF: "Custom Field for Customer Creation",
  SFCrCusFVal: "Custom Field Value for Customer Creation",
  MergeAC: "Merge CRM→FS Customers",
  MergeCA: "Merge FS→CRM Customers",
  AcctBSAddr: "Billing Street Address Fill",
  AcctSSAddr: "Shipping Street Address Fill",
  CreateUpdateAc: "Account Create/Update Trigger",
  QBCrAccF: "FS Field for Account Creation",
  QBCrAccFVal: "FS Field Value for Account Creation",
  CreateSFCont: "Create/Update Contact Records",
  CreateSFContAlt: "Create Alt. Contact Record",
  AccQB2SFNoJobs: "Ignore FS Jobs",
  QB2SFFinInfoOnly: "Financial Information Only",
  AccQB2SFAcctNmbr: "Sync Account Number",
  QB306090: "30/60/90 Terms Support",
  MrgQBFN: "Use FS Full Name to Merge",
  AccOwnRepMap: "Owner to Sales Rep Mapping",

  // Transaction (SO) detail fields
  SONumber: "Sales Order Number Field",
  SOTranObject: "Transactional Object Name",
  SOTranObjectLine: "Transactional Object Line Name",
  SONumGen: "SO Number Generation",
  SOQB2SFETL: "Initial Upload Required (SO)",
  SOSF2QBOps: "Permitted SO Sync Operations",
  DelInsAsUpdSO: "Delete/Insert to Update SO",
  CreateInFullSO: "Create SO in Full (Cached)",
  CreateNSO: "SO Creation Trigger",
  SFOppStValSO: "Stage for SO Creation",
  SFCrCusFSO: "Custom Field for SO Creation",
  SFCrCusFSOVal: "Custom Field Value for SO Creation",
  DummySOSHNm: "Default S&H Item Name",
  DummySOSHPrc: "Default S&H Price Field",
  SOLISorting: "Line Item Sorting Field",
  SOSkipLI: "Skip Line Item Field",
  SOSkipLIVal: "Skip Line Item Value",
  UseDmy4DmySO: "Default S&H for Dummy SO",
  SO2BACFOpp: "SO Billing Address Source",
  SO2SACFOpp: "SO Shipping Address Source",
  SOBSAddr: "SO Billing Street Fill",

  // Invoice detail fields
  InvNumber: "Invoice Number Field",
  InvTranObject: "Invoice Object Name",
  InvTranObjectLine: "Invoice Object Line Name",
  InvNumGen: "Invoice Number Generation",

  // Product detail fields
  PrdTranObject: "Product Object Name",
  PrdTranObjectLine: "Product Line Item Object",
  PrdBinding: "Product Binding Field",
  PrdBindCriteria: "Product Binding Criteria",
  PrdType: "Product Type Field",
  PrdIncmAcct: "Product Income Account Field",
  PrdExpAcct: "Product Expense Account Field",

  // Execution settings
  SandBoxUsed: "Sandbox Mode",
  Env2Con: "Base Environment",
  QBVersion: "QB Version/Locale",
  QBLocation: "QB Location",
  StopSchedTr: "Stop on Error",
  SleepStart: "Sleep Window Start",
  SleepEnd: "Sleep Window End",
  TimeZone: "Time Zone Shift",
  EmlNtf: "Email Notification Mode",
  UseAdmEml: "Use Admin Email",
  CCEmail: "CC Email Addresses",
  BCCEmail: "BCC Email Addresses",
  LongTimeOut: "Extended Timeout",
  ConFailState: "Restore State on Failure",
};

/** Category for grouping in Review step */
export type ReviewCategory = "direction" | "account" | "transaction" | "product" | "execution" | "other";

export function categorizeKey(key: string): ReviewCategory {
  if (key.startsWith("SyncType")) return "direction";
  if (/^(BSFQB|AcctCust|NormBind|SFQBCust|SFQBComp|PrimCont|DefPrim|SyncCnMl|UseBill|UseShip|SyncACJ|HJobLevel|CONJob|CustT|SustRem|MIddl|AccQB2|AccSF2|ACSF2|ACQB2|CreateNC|SFOpp|SFCrCus|Merge|Acct[BS]S|CreateUpdate|QBCr|CreateSF|QB2SF|QB306|MrgQB|AccOwn)/.test(key)) return "account";
  if (/^(SO|Inv|DelIns|CreateIn|CreateN|Dummy|UseDmy)/.test(key)) return "transaction";
  if (/^(Prd|SyncTypePrd)/.test(key)) return "product";
  if (/^(SandBox|Env2|QBVer|QBLoc|Stop|Sleep|TimeZone|Eml|UseAdm|CCE|BCCE|Long|ConFail)/.test(key)) return "execution";
  return "other";
}

export const REVIEW_CATEGORY_LABELS: Record<ReviewCategory, string> = {
  direction: "Sync Directions",
  account: "Account/Customer Details",
  transaction: "Transaction Details",
  product: "Product Details",
  execution: "Execution Settings",
  other: "Other Settings",
};

// ─── Sync direction labels for Review ────────────────────────────────

export function syncDirectionLabel(value: string, meta: SolutionMeta): string {
  switch (value) {
    case "None": return "Disabled";
    case "SF2QB": return `${meta.crmName} → ${meta.fsName}`;
    case "QB2SF": return `${meta.fsName} → ${meta.crmName}`;
    case "SFQB": return "Bi-directional";
    default: return value;
  }
}

// ─── Object Detail Schemas ───────────────────────────────────────────

export const ACCOUNT_DETAIL_SCHEMA: ObjectDetailSchema = {
  objectKey: "SyncTypeAC",
  sectionLabel: "{crm} {crmcust} ↔ {fs} {fscust} Details",
  groups: [
    {
      label: "Customer Binding",
      helpText: "Configure how records are matched between systems",
      fields: [
        {
          key: "BSFQBLID",
          label: "Binding {crm} Custom Field with {fs} ListID",
          type: "text",
          defaultValue: "",
          helpText: "API field name in the CRM that stores the financial system's unique record ID. Used to link records between systems.",
          placeholder: "e.g. QB_ListID__c",
        },
        {
          key: "AcctCustBind",
          label: "Binding Criteria",
          type: "select",
          defaultValue: "No",
          helpText: "Fallback matching rule when the binding field is empty. Determines how to find existing records.",
          options: [
            { value: "No", label: "None" },
            { value: "BACN", label: "Name" },
            { value: "BACNP", label: "Name + Phone" },
            { value: "BACA", label: "Name + Address (no street)" },
            { value: "BACNPA", label: "Name + Phone + Address (no street)" },
            { value: "BACNOP", label: "Name or Phone" },
            { value: "BACNC", label: "Name or Company Name" },
          ],
        },
        {
          key: "BSFQBFN",
          label: "Binding {crm} Custom Field with {fs} Full/Normalized Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. QB_FullName__c",
        },
        {
          key: "NormBindName",
          label: "Normalize Names for Binding",
          type: "select",
          defaultValue: "No",
          helpText: "Strip extra whitespace and normalize casing before comparing names.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
      ],
    },
    {
      label: "Custom Object Mapping",
      helpText: "Map CRM objects/fields to financial system customer records",
      fields: [
        {
          key: "SFQBCustObj",
          label: "{crm} Object to create/update {fs} Customer",
          type: "text",
          defaultValue: "",
          helpText: "API name of the CRM object used as the source for customer records (e.g. Account, custom object).",
          placeholder: "e.g. Account",
        },
        {
          key: "SFQBCustNm",
          label: "{crm} Field with {fs} Customer Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Name",
        },
        {
          key: "SFQBCompNm",
          label: "{crm} Field with {fs} Company Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Company__c",
        },
      ],
    },
    {
      label: "Contact Handling",
      fields: [
        {
          key: "PrimCont",
          label: "Primary Contact Selected Via",
          type: "select",
          defaultValue: "None",
          helpText: "How the primary contact is determined when syncing to the financial system.",
          options: [
            { value: "None", label: "None" },
            { value: "ACR", label: "Account/Contact Role" },
            { value: "CLCF", label: "Contact Level Custom Field" },
            { value: "ALCf", label: "Contact Lookup in Account" },
            { value: "OLCf", label: "Contact Lookup in {crmtran}" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "DefPrimRole",
          label: "Default Primary Role Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Decision Maker",
        },
        {
          key: "SyncCnMlAcBl",
          label: "Sync Contact Mail Address with Billing Address",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "For Primary Contact" },
            { value: "All", label: "For All Contacts" },
          ],
        },
      ],
    },
    {
      label: "Address Handling",
      fields: [
        {
          key: "UseBillAsShipCust",
          label: "Use Billing Address as Shipping",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "UseShipAsBillCust",
          label: "Use Shipping Address as Billing",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "AcctBSAddr",
          label: "Fill {fs} Customer Billing Street Address With",
          type: "select",
          defaultValue: "AsSF",
          showForDirections: ["SF2QB", "SFQB"],
          options: [
            { value: "None", label: "Do not fill" },
            { value: "AsSF", label: "As {crm} Account" },
            { value: "AsNA", label: "Name / Address" },
            { value: "AsFLNA", label: "First+Last Name / Name / Address" },
            { value: "AaFLA", label: "First+Last Name / Address" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "AcctSSAddr",
          label: "Fill {fs} Customer Shipping Street Address With",
          type: "select",
          defaultValue: "AsSF",
          showForDirections: ["SF2QB", "SFQB"],
          options: [
            { value: "None", label: "Do not fill" },
            { value: "AsSF", label: "As {crm} Account" },
            { value: "AsNA", label: "Name / Address" },
            { value: "AsFLNA", label: "First+Last Name / Name / Address" },
            { value: "AaFLA", label: "First+Last Name / Address" },
            { value: "Other", label: "Other" },
          ],
        },
      ],
    },
    {
      label: "Hierarchy & Jobs",
      fields: [
        {
          key: "SyncACJHr",
          label: "Propagate {crm} Hierarchy to {fs} Customer/Job",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "YesO", label: "Yes, using Custom Object" },
          ],
        },
        {
          key: "HJobLevel",
          label: "Hierarchy Level to Create {fs} Job",
          type: "text",
          defaultValue: "",
          helpText: "Numeric level in the hierarchy at which a new Job record is created.",
          placeholder: "e.g. 2",
        },
        {
          key: "CONJob",
          label: "{crm} Object Name to Create {fs} Job/Address",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Opportunity",
        },
      ],
    },
    {
      label: "Customer Defaults",
      helpText: "CRM field names that map to financial system customer default values",
      fields: [
        {
          key: "CustTerm",
          label: "{crm} Field for Customer Terms",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Payment_Terms__c",
        },
        {
          key: "CustTypeSt",
          label: "{crm} Field for Customer Type",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Customer_Type__c",
        },
        {
          key: "CustTaxCodeSt",
          label: "{crm} Field for Customer Tax Code",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Tax_Code__c",
        },
        {
          key: "CustTaxItemSt",
          label: "{crm} Field for Customer Tax Item/Taxable",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Taxable__c",
        },
        {
          key: "SustRemBal",
          label: "{crm} Account Custom Field for Total Balance",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. QB_Balance__c",
        },
        {
          key: "MIddleName",
          label: "{crm} Contact Custom Field for Middle Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. MiddleName",
        },
      ],
    },
    {
      label: "Source → Destination Sync Rules",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "AccQB2SFETL",
          label: "Initial {fs} → {crm} Upload Required",
          type: "select",
          defaultValue: "No",
          showForDirections: ["SF2QB"],
          helpText: "Set to Yes if existing financial system customers need to be loaded into CRM first.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "ACSF2QBOps",
          label: "Permitted Sync Operations ({crm} → {fs})",
          type: "select",
          defaultValue: "CUOPPS",
          helpText: "Controls whether new records can be created, existing ones updated, or both.",
          options: [
            { value: "None", label: "None" },
            { value: "CUOPPS", label: "Create and Update" },
            { value: "COPPS", label: "Create Only" },
            { value: "UOPPS", label: "Update Only" },
          ],
        },
        {
          key: "CreateNC",
          label: "Create New {fs} Customer When",
          type: "select",
          defaultValue: "AFAccCr",
          helpText: "The trigger condition for creating a new customer record in the financial system.",
          options: [
            { value: "AFAccCr", label: "{crm} Account Created" },
            { value: "AFAcc", label: "{crm} {crmtran} is in Certain Stage" },
            { value: "AFAccCFAcc", label: "{crm} Account Custom Field has Certain Value" },
            { value: "AFAccCFOpp", label: "{crm} {crmtran} Custom Field has Certain Value" },
            { value: "AFAccIsWOpp", label: "{crm} {crmtran} is Won" },
            { value: "AFAccOppCr", label: "{crm} Account and {crmtran} Created" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "SFOppStVal",
          label: "{crm} Stage to Create New {fs} Customer",
          type: "text",
          defaultValue: "Closed Won",
          showWhen: { key: "CreateNC", values: ["AFAcc"] },
          placeholder: "e.g. Closed Won",
        },
        {
          key: "SFCrCusF",
          label: "{crm} Custom Field Name to Create New {fs} Customer",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateNC", values: ["AFAccCFAcc", "AFAccCFOpp"] },
          placeholder: "e.g. Sync_To_QB__c",
        },
        {
          key: "SFCrCusFVal",
          label: "{crm} Custom Field Value to Create New {fs} Customer",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateNC", values: ["AFAccCFAcc", "AFAccCFOpp"] },
          placeholder: "e.g. true",
        },
        {
          key: "MergeAC",
          label: "Merge New {crm} Accounts with Existing {fs} Customers",
          type: "select",
          defaultValue: "None",
          helpText: "How to match and merge new CRM accounts with existing financial system customers.",
          options: [
            { value: "None", label: "Do not merge" },
            { value: "MACN", label: "Name" },
            { value: "MACNP", label: "Name + Phone" },
            { value: "MACA", label: "Name + Address (no street)" },
            { value: "MACNPA", label: "Name + Phone + Address (no street)" },
            { value: "Other", label: "Other" },
          ],
        },
      ],
    },
    {
      label: "Destination → Source Sync Rules",
      showForDirections: ["QB2SF", "SFQB"],
      fields: [
        {
          key: "AccSF2QBETL",
          label: "Initial {crm} → {fs} Upload Required",
          type: "select",
          defaultValue: "No",
          showForDirections: ["QB2SF"],
          helpText: "Set to Yes if existing CRM accounts need to be loaded into the financial system first.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "ACQB2SFOps",
          label: "Permitted Sync Operations ({fs} → {crm})",
          type: "select",
          defaultValue: "CUOPPS",
          options: [
            { value: "None", label: "None" },
            { value: "CUOPPS", label: "Create and Update" },
            { value: "COPPS", label: "Create Only" },
            { value: "UOPPS", label: "Update Only" },
          ],
        },
        {
          key: "MergeCA",
          label: "Merge New {fs} Customers with Existing {crm} Accounts",
          type: "select",
          defaultValue: "None",
          options: [
            { value: "None", label: "Do not merge" },
            { value: "MACN", label: "Name" },
            { value: "MACNP", label: "Name + Phone" },
            { value: "MACA", label: "Name + Address (no street)" },
            { value: "MACNPA", label: "Name + Phone + Address (no street)" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "CreateSFCont",
          label: "Create/Update {crm} Contact Records",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "CreateSFContAlt",
          label: "Create Alt. Contact as {crm} Contact Record",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "AccQB2SFNoJobs",
          label: "Ignore {fs} Jobs",
          type: "select",
          defaultValue: "No",
          showForDirections: ["SFQB", "QB2SF"],
          helpText: "Skip Job sub-records when syncing from financial system.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "QB2SFFinInfoOnly",
          label: "Provide to {crm} Financial Information Only",
          type: "select",
          defaultValue: "No",
          showForDirections: ["SFQB", "QB2SF"],
          helpText: "Only sync balance and financial data — skip name/address fields.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "AccQB2SFAcctNmbr",
          label: "Synchronize {fs} Account Number to {crm}",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "QB306090",
          label: "30/60/90 Terms Support Required",
          type: "select",
          defaultValue: "No",
          helpText: "Enable multi-bucket payment terms (Net 30, Net 60, Net 90).",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "YesX", label: "Yes (Extended)" },
            { value: "YesX15", label: "Yes (15 days)" },
            { value: "YesI", label: "Yes (Invoice Date)" },
            { value: "YesXI", label: "Yes (Extended, Invoice Date)" },
            { value: "YesX15I", label: "Yes (15 days, Invoice Date)" },
          ],
        },
        {
          key: "MrgQBFN",
          label: "Use {fs} Full Name to Merge",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "AccOwnRepMap",
          label: "Account Owner to {fs} Sales Rep Mapping",
          type: "select",
          defaultValue: "No",
          helpText: "Map the CRM account owner to a sales rep field in the financial system.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "YesT", label: "Yes (using Customer Type)" },
            { value: "YesC", label: "Yes (using Custom Field)" },
          ],
        },
      ],
    },
  ],
};

export const SALES_ORDER_DETAIL_SCHEMA: ObjectDetailSchema = {
  objectKey: "SyncTypeSO",
  sectionLabel: "{crm} {crmtran} ↔ {fs} Sales Order Details",
  groups: [
    {
      label: "Object & Number Configuration",
      fields: [
        {
          key: "SONumber",
          label: "{crm} Custom Field with {fs} Sales Order #",
          type: "text",
          defaultValue: "",
          helpText: "API field name in CRM that stores the financial system Sales Order number.",
          placeholder: "e.g. QB_SO_Number__c",
        },
        {
          key: "SOTranObject",
          label: "{crm} Transactional Object Name",
          type: "text",
          defaultValue: "",
          helpText: "API name of the CRM object used as the source for sales orders (e.g. Opportunity, custom object).",
          placeholder: "e.g. Opportunity",
        },
        {
          key: "SOTranObjectLine",
          label: "{crm} Transactional Object Line Name",
          type: "text",
          defaultValue: "",
          helpText: "API name of the line item object (child of the transactional object).",
          placeholder: "e.g. OpportunityLineItem",
        },
        {
          key: "SONumGen",
          label: "{fs} Sales Order # Generated By",
          type: "select",
          defaultValue: "SFGen",
          helpText: "Which system generates the Sales Order number.",
          options: [
            { value: "SFGen", label: "{crm}" },
            { value: "QBGen", label: "{fs}" },
            { value: "SFQBGen", label: "Mixed" },
          ],
        },
      ],
    },
    {
      label: "Sync Operations",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "SOQB2SFETL",
          label: "Initial {fs} → {crm} Upload Required",
          type: "select",
          defaultValue: "No",
          showForDirections: ["SF2QB"],
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "SOSF2QBOps",
          label: "Permitted Sync Operations ({crm} → {fs})",
          type: "select",
          defaultValue: "CUOPPS",
          options: [
            { value: "None", label: "None" },
            { value: "CUOPPS", label: "Create and Update" },
            { value: "COPPS", label: "Create Only" },
            { value: "UOPPS", label: "Update Only" },
          ],
        },
        {
          key: "DelInsAsUpdSO",
          label: "Use Delete/Insert to Update Sales Order",
          type: "select",
          defaultValue: "No",
          helpText: "Instead of updating individual fields, delete the old SO and create a new one. Useful when the FS doesn't support partial updates.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "CreateInFullSO",
          label: "Always Create Sales Order in Full (Cached)",
          type: "select",
          defaultValue: "No",
          helpText: "Cache the full SO data and re-create it each sync. Ensures consistency but is slower.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
      ],
    },
    {
      label: "Creation Triggers",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "CreateNSO",
          label: "Create New {fs} Sales Order When",
          type: "select",
          defaultValue: "AFAccCr",
          options: [
            { value: "AFAccCr", label: "When {crm} {crmtran} Created" },
            { value: "AFAcc", label: "{crm} {crmtran} is in Certain Stage" },
            { value: "AFAccCFOpp", label: "{crm} {crmtran} Custom Field has Certain Value" },
            { value: "AFAccIsWOpp", label: "{crm} {crmtran} is Won" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "SFOppStValSO",
          label: "{crm} Stage to Create New {fs} Sales Order",
          type: "text",
          defaultValue: "Closed Won",
          showWhen: { key: "CreateNSO", values: ["AFAcc"] },
          placeholder: "e.g. Closed Won",
        },
        {
          key: "SFCrCusFSO",
          label: "{crm} Custom Field Name for SO Creation",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateNSO", values: ["AFAccCFOpp"] },
        },
        {
          key: "SFCrCusFSOVal",
          label: "{crm} Custom Field Value for SO Creation",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateNSO", values: ["AFAccCFOpp"] },
        },
      ],
    },
    {
      label: "Line Items & Shipping",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "DummySOSHNm",
          label: "Default Shipping & Handling Item Name",
          type: "text",
          defaultValue: "",
          maxLength: 31,
          placeholder: "e.g. Shipping & Handling",
        },
        {
          key: "DummySOSHPrc",
          label: "{crm} Field for Default S&H Price",
          type: "text",
          defaultValue: "",
          maxLength: 31,
          placeholder: "e.g. ShippingCost__c",
        },
        {
          key: "SOLISorting",
          label: "{crm} Field for Custom Sorting of Line Items",
          type: "text",
          defaultValue: "",
          maxLength: 31,
          placeholder: "e.g. SortOrder__c",
        },
        {
          key: "SOSkipLI",
          label: "{crm} Field Name for Skipping Line Item",
          type: "text",
          defaultValue: "",
          maxLength: 31,
          helpText: "If this field has the value below, the line item is excluded from the SO.",
          placeholder: "e.g. Skip_QB__c",
        },
        {
          key: "SOSkipLIVal",
          label: "{crm} Field Value for Skipping Line Item",
          type: "text",
          defaultValue: "",
          maxLength: 31,
          placeholder: "e.g. true",
        },
        {
          key: "UseDmy4DmySO",
          label: "Use Default S&H for Dummy Sales Order",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
      ],
    },
    {
      label: "Address Population",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "SO2BACFOpp",
          label: "Populate Billing Address from",
          type: "select",
          defaultValue: "OBAddr",
          options: [
            { value: "None", label: "Do not populate" },
            { value: "ABAddr", label: "Account Billing Address" },
            { value: "OBAddr", label: "{crmtran} Billing Address (custom field)" },
            { value: "OBFLAddr", label: "{crmtran} Billing Address (with first/last name)" },
            { value: "OBNAddr", label: "{crmtran} Billing Address (with name)" },
            { value: "OBCAddr", label: "{crmtran} Billing Address (all)" },
            { value: "OBPCAddr", label: "Primary Contact Mailing Address" },
          ],
        },
        {
          key: "SO2SACFOpp",
          label: "Populate Shipping Address from",
          type: "select",
          defaultValue: "OSAddr",
          options: [
            { value: "None", label: "Do not populate" },
            { value: "ASAddr", label: "Account Shipping Address" },
            { value: "OSAddr", label: "{crmtran} Shipping Address (custom field)" },
            { value: "OSFLAddr", label: "{crmtran} Shipping Address (with first/last name)" },
            { value: "OSNAddr", label: "{crmtran} Shipping Address (with name)" },
            { value: "OSCAddr", label: "{crmtran} Shipping Address (all)" },
            { value: "OSPCAddr", label: "Primary Contact Mailing Address" },
          ],
        },
      ],
    },
  ],
};

export const INVOICE_DETAIL_SCHEMA: ObjectDetailSchema = {
  objectKey: "SyncTypeInv",
  sectionLabel: "{crm} {crmtran} ↔ {fs} Invoice Details",
  groups: [
    {
      label: "Object & Number Configuration",
      fields: [
        {
          key: "InvNumber",
          label: "{crm} Custom Field with {fs} Invoice #",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. QB_Invoice_Number__c",
        },
        {
          key: "InvTranObject",
          label: "{crm} Invoice Object Name",
          type: "text",
          defaultValue: "",
          helpText: "API name of the CRM object used as the source for invoices.",
          placeholder: "e.g. Opportunity",
        },
        {
          key: "InvTranObjectLine",
          label: "{crm} Invoice Object Line Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. OpportunityLineItem",
        },
        {
          key: "InvNumGen",
          label: "{fs} Invoice # Generated By",
          type: "select",
          defaultValue: "SFGen",
          options: [
            { value: "SFGen", label: "{crm}" },
            { value: "QBGen", label: "{fs}" },
            { value: "SFQBGen", label: "Mixed" },
          ],
        },
      ],
    },
  ],
};

export const PRODUCT_DETAIL_SCHEMA: ObjectDetailSchema = {
  objectKey: "SyncTypePrd",
  sectionLabel: "{crm} Product ↔ {fs} Item Details",
  groups: [
    {
      label: "Product Mapping",
      fields: [
        {
          key: "PrdTranObject",
          label: "{crm} Product Object Name",
          type: "text",
          defaultValue: "",
          helpText: "API name of the CRM product object.",
          placeholder: "e.g. Product2",
        },
        {
          key: "PrdBinding",
          label: "Product Binding Field",
          type: "text",
          defaultValue: "",
          helpText: "CRM field that stores the financial system item ID for binding.",
          placeholder: "e.g. QB_ItemRef__c",
        },
        {
          key: "PrdBindCriteria",
          label: "Product Binding Criteria",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "None" },
            { value: "BACN", label: "Name" },
            { value: "BACNC", label: "Name or Code" },
          ],
        },
        {
          key: "PrdType",
          label: "{crm} Field for Product Type",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Family",
        },
        {
          key: "PrdIncmAcct",
          label: "{crm} Field for Income Account",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Income_Account__c",
        },
        {
          key: "PrdExpAcct",
          label: "{crm} Field for Expense Account",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Expense_Account__c",
        },
      ],
    },
  ],
};

export const VENDOR_DETAIL_SCHEMA: ObjectDetailSchema = {
  objectKey: "SyncTypeVAC",
  sectionLabel: "{crm} Account/Contact ↔ {fs} Vendor Details",
  groups: [
    {
      label: "Vendor Binding",
      fields: [
        {
          key: "BSFQBLID_V",
          label: "Binding {crm} Custom Field with {fs} Vendor ListID",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. QB_Vendor_ListID__c",
        },
        {
          key: "VendorBindCriteria",
          label: "Vendor Binding Criteria",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "None" },
            { value: "BACN", label: "Name" },
            { value: "BACNP", label: "Name + Phone" },
            { value: "BACA", label: "Name + Address (no street)" },
          ],
        },
      ],
    },
    {
      label: "Vendor Sync Rules",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "VACSF2QBOps",
          label: "Permitted Sync Operations ({crm} → {fs})",
          type: "select",
          defaultValue: "CUOPPS",
          options: [
            { value: "None", label: "None" },
            { value: "CUOPPS", label: "Create and Update" },
            { value: "COPPS", label: "Create Only" },
            { value: "UOPPS", label: "Update Only" },
          ],
        },
      ],
    },
  ],
};

// ─── Schema Registry ─────────────────────────────────────────────────

/** All object detail schemas, keyed by SyncType key */
export const OBJECT_DETAIL_SCHEMAS: Record<string, ObjectDetailSchema> = {
  SyncTypeAC: ACCOUNT_DETAIL_SCHEMA,
  SyncTypeSO: SALES_ORDER_DETAIL_SCHEMA,
  SyncTypeInv: INVOICE_DETAIL_SCHEMA,
  SyncTypePrd: PRODUCT_DETAIL_SCHEMA,
  SyncTypeVAC: VENDOR_DETAIL_SCHEMA,
};

/** Get the detail schema for a given object key, or null if none exists */
export function getObjectDetailSchema(objectKey: string): ObjectDetailSchema | null {
  return OBJECT_DETAIL_SCHEMAS[objectKey] ?? null;
}

/** Count how many detail properties are configured (non-empty, non-default) for an object */
export function countConfiguredDetails(
  objectKey: string,
  allValues: Record<string, string>,
): number {
  const schema = OBJECT_DETAIL_SCHEMAS[objectKey];
  if (!schema) return 0;
  let count = 0;
  for (const group of schema.groups) {
    for (const field of group.fields) {
      const val = allValues[field.key];
      if (val && val !== field.defaultValue && val !== "") count++;
    }
  }
  return count;
}

/** Get all detail field keys for a given object (for Review step filtering) */
export function getDetailFieldKeys(objectKey: string): string[] {
  const schema = OBJECT_DETAIL_SCHEMAS[objectKey];
  if (!schema) return [];
  return schema.groups.flatMap((g) => g.fields.map((f) => f.key));
}

/** All detail field keys across all schemas (for separating from exec settings) */
export function getAllDetailFieldKeys(): Set<string> {
  const keys = new Set<string>();
  for (const schema of Object.values(OBJECT_DETAIL_SCHEMAS)) {
    for (const group of schema.groups) {
      for (const field of group.fields) {
        keys.add(field.key);
      }
    }
  }
  return keys;
}
