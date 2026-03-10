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
  AccSFQBCMap: "Customer Custom Mapping 1",
  AccSFQBCMap1: "Customer Custom Mapping 2",
  AccSFQBCMap2: "Customer Custom Mapping 3",
  AccSFQBCMap3: "Customer Custom Mapping 4",
  AccSFQBCMap4: "Customer Custom Mapping 5",
  AccSFQBCMap5: "Customer Custom Mapping 6",
  AccSFQBCMap6: "Customer Custom Mapping 7",
  AccSFQBCMap7: "Customer Custom Mapping 8",
  AccSFQBCMap8: "Customer Custom Mapping 9",
  AccSFQBCMap9: "Customer Custom Mapping 10",

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
  SOSFQBCMap: "Sales Order Custom Mapping 1",
  SOSFQBCMap1: "Sales Order Custom Mapping 2",
  SOSFQBCMap2: "Sales Order Custom Mapping 3",
  SOSFQBCMap3: "Sales Order Custom Mapping 4",
  SOSFQBCMap4: "Sales Order Custom Mapping 5",
  SOSFQBCMap5: "Sales Order Custom Mapping 6",
  SOSFQBCMap6: "Sales Order Custom Mapping 7",
  SOSFQBCMap7: "Sales Order Custom Mapping 8",
  SOSFQBCMap8: "Sales Order Custom Mapping 9",
  SOSFQBCMap9: "Sales Order Custom Mapping 10",

  // Invoice detail fields
  InvNumber: "Invoice Number Field",
  InvTranObject: "Invoice Object Name",
  InvTranObjectLine: "Invoice Object Line Name",
  InvNumGen: "Invoice Number Generation",
  InvQB2SFETL: "Initial Upload Required (Inv, FS→CRM)",
  InvSF2QBOps: "Permitted Inv Sync Operations (CRM→FS)",
  DelInsAsUpdInv: "Delete/Insert to Update Invoice",
  CreateInFullInv: "Create Invoice in Full (Cached)",
  DashAsDelimInv: "Dash as Delimiter in Invoice #",
  UseBillAsShip: "Use Billing as Shipping (Invoice)",
  UseShipAsBill: "Use Shipping as Billing (Invoice)",
  CreateNInv: "Invoice Creation Trigger",
  SFOppStValInv: "Stage for Invoice Creation",
  SFCrCusFInv: "Custom Field for Invoice Creation",
  SFCrCusFInvVal: "Custom Field Value for Invoice Creation",
  CORef4Inv: "Object Name for Invoice",
  CORef4InvLI: "Object/Field for Invoice Line Items",
  CORef4InvLIAM: "Field for Invoice Line Item Rate",
  CORef4InvLIQT: "Field for Invoice Line Item Quantity",
  CORef4InvLID: "Field for Invoice Line Item Service Dates",
  CORef4InvLINm: "Field for Invoice Line Item Name",
  UpdSF2InvAmnt: "Skip Amounts on Invoice Create/Update",
  DummyInvSHNm: "Default Invoice S&H Item Name",
  DummyInvSHPrc: "Default Invoice S&H Price Field",
  DummyInvLIDesc: "Dummy Description Line Item Field",
  InvLISorting: "Invoice Line Item Sorting Field",
  AdrObjRefInv: "Address Object Reference Field (Invoice)",
  SFSkipLineInv: "Skip Invoice Line Item Field",
  SFSkipLineInvVal: "Skip Invoice Line Item Value",
  UseProdSched: "Product Schedule for Invoice Line Items",
  Inv2BACFOpp: "Invoice Billing Address Source",
  Inv2SACFOpp: "Invoice Shipping Address Source",
  InvBSAddr: "Invoice Billing Street Fill",
  InvSSAddr: "Invoice Shipping Street Fill",
  InvQBPend: "Create Pending Invoice",
  SFCrConc2FInv: "Field to Convert Invoice to Final",
  SFCrConc2FInvVal: "Value to Convert Invoice to Final",
  InvLineDesc: "Invoice Line Item Description Mode",
  InvDefCustFlNm: "Default Customer Full Name Field",
  InvPREmNm: "Filtering Field for Invoice Operations",
  InvToBePrt: "Filtering Value for Invoice Print",
  InvToBeEml: "Filtering Value for Invoice Email",
  VendInstPmt: "Vendor Name for Instant Payment",
  VendInstPmtVal: "Value for Instant Payment",
  InvDefTemp: "Default Invoice Template",
  Tax4Inv: "Tax Field for Invoice",
  Tax4InvValAs: "Pre-Populate Tax Value Mode",
  QSBTaxFullN: "Sales Tax Item Full Name",
  InvSF2QBETL: "Initial Upload Required (Inv, CRM→FS)",
  InvQB2SFOps: "Permitted Inv Sync Operations (FS→CRM)",
  CreateUpdateOppInv: "Create/Update Trigger (FS→CRM Invoice)",
  QBCrOppInvF: "FS Field for Invoice Creation",
  QBCrOppInvFVal: "FS Field Value for Invoice Creation",
  SFOppStageInv: "New CRM Transaction Stage (Invoice)",
  UpdOooInvAmnt: "Update CRM Amounts from Invoice",
  InvBACFOpp: "Invoice Billing Address (FS→CRM)",
  InvSACFOpp: "Invoice Shipping Address (FS→CRM)",
  InvExpGrpItm: "Expand Group Product After Group Item",
  InvNoItems: "Create/Update Without Line Items",
  InvMulItems: "Multiple Identical Line Items",
  CreditMemo: "Copy Credit Memo to CRM Object",
  PaymentQB: "Copy Payment to CRM Object",
  InvRemBal: "Remaining Balance Field (Invoice)",
  InvCJName: "Customer/Job Name Field (Invoice)",
  InclPCOppInv: "Include Primary Contact Lookup",
  CORef2Cont4Inv: "Primary Contact Field (Invoice)",
  InvPONumber: "Purchase Order Number Field (Invoice)",
  InvIsPaid: "Is Paid Checkbox (Invoice)",
  InvDate: "Invoice Date Field",
  InvQBOppNm: "FS Field for CRM Transaction Name",
  InvSFQBCMap: "Invoice Custom Mapping 1",
  InvSFQBCMap1: "Invoice Custom Mapping 2",
  InvSFQBCMap2: "Invoice Custom Mapping 3",
  InvSFQBCMap3: "Invoice Custom Mapping 4",
  InvSFQBCMap4: "Invoice Custom Mapping 5",
  InvSFQBCMap5: "Invoice Custom Mapping 6",
  InvSFQBCMap6: "Invoice Custom Mapping 7",
  InvSFQBCMap7: "Invoice Custom Mapping 8",
  InvSFQBCMap8: "Invoice Custom Mapping 9",
  InvSFQBCMap9: "Invoice Custom Mapping 10",
  InvOwnRepMap: "Invoice Owner to Sales Rep Mapping",
  InvTerm: "Invoice Terms Field",
  InvShipVia: "Shipping Method Field",
  InvDefDiscItm: "Default Discount Item Field",
  InvClass: "Invoice Class Field",
  InvRflct: "Created Invoice Number Field",
  InvDplct: "Duplicate Prevention Field (Invoice)",
  InvLastTranDate: "Last Transaction Date Field",
  InvFastCust: "Fast Customer Search (Invoice)",
  InvUse4Qty: "Product Quantity Source (Invoice)",
  InvUse4Desc: "Product Description Source (Invoice)",

  // Product detail fields
  PrdBind: "Product Binding Method",
  SFFldPNm: "Product Field Containing Item Name",
  ItmPrdNmTrMd: "Item/Product Name Truncating Mode",
  CONItem: "Custom Object Name for Item",
  SFPrdSPrcCstm: "Sales Price Field",
  PrdGrpNm: "Product Group/Assembly Name Field",
  PrdGrpQt: "Product Group/Assembly Quantity Field",
  PrdItmNm: "Parent Item Name for Sub-Items",
  SF2QBItmNmOnl: "Bind by Item Name Only",
  SFCostItm: "Preferred Vendor/Item Cost Support",
  SFWeightItm: "Item Weight Support",
  SF2QBPriceLevel: "Price Level Mapping",
  SF2QBInvItemSite: "Inventory Item Sites Support",
  SerNumLot: "Serial Numbers/Lots Support",
  PrdPDescNm: "Purchase Description Field",
  PrdQOnHand: "Quantity on Hand Field",
  PrdManPNum: "Manufacturer Part Number Field",
  PrdQB2SFETL: "Initial Upload Required (Prd, FS→CRM)",
  PrdSF2QBOps: "Permitted Prd Sync Operations (CRM→FS)",
  QBItmTp: "Product Field for Item Type",
  CreateCOAAuto: "Create COA Accounts Automatically",
  QBItmTpI: "Inventory Item Type Value",
  QBItmTpA: "Inventory Assembly Item Type Value",
  DIAItmTpI: "Default Income Account (Inventory)",
  DCAItmTpI: "Default COGS Account (Inventory)",
  DAAItmTpI: "Default Asset Account (Inventory)",
  InvItemSite: "Inventory/Assembly Item Site Field",
  QBItmTpN: "Non-Inventory Item Type Value",
  DAItmTpN: "Default Income Account (Non-Inventory)",
  QBItmTpS: "Service Item Type Value",
  DAItmTpS: "Default Income Account (Service)",
  QBItmTpO: "Other Charge Item Type Value",
  DAItmTpO: "Default Income Account (Other Charge)",
  QBItmTpD: "Discount Item Type Value",
  DAItmTpD: "Default Account (Discount)",
  SFNOIItmRI: "Reimbursable Non-Inventory Support",
  ReimNmNISel: "Non-Inventory Reimbursable Field Name",
  ReimValNISel: "Non-Inventory Reimbursable Field Value",
  DEAItmTpN: "Expense Account (Non-Inventory Reimbursable)",
  SFSrvItmRI: "Reimbursable Service Item Support",
  ReimNmServSel: "Service Reimbursable Field Name",
  ReimValServSel: "Service Reimbursable Field Value",
  DEAItmTpS: "Expense Account (Service Reimbursable)",
  SFOCItmRI: "Reimbursable Other Charge Support",
  ReimNmOCSel: "Other Charge Reimbursable Field Name",
  ReimValOCSel: "Other Charge Reimbursable Field Value",
  DEAItmTpO: "Expense Account (Other Charge Reimbursable)",
  SFMargFldNm: "CRM Margin Field Name",
  SFSPMargFldNm: "CRM Sales Price with Margin Field",
  SFNm4ItmDesc: "Use CRM Name for Item Description",
  PrdSF2QBETL: "Initial Upload Required (Prd, CRM→FS)",
  PrdQB2SFOps: "Permitted Prd Sync Operations (FS→CRM)",
  MargFldNm: "FS Margin Field Name",
  MargFldVal: "Margin Field Values",
  MargVal: "Standard Price Margin Values",
  DefCurList: "Default Currencies List",
  PrdFamily: "FS Field for Product Family",
  PrdNoLoad: "FS Field to Suppress Transaction",
  PrdCode: "Populate Product Code With",
  PrdSFQBCMap: "Product Custom Mapping 1",
  PrdSFQBCMap1: "Product Custom Mapping 2",
  PrdSFQBCMap2: "Product Custom Mapping 3",
  PrdSFQBCMap3: "Product Custom Mapping 4",
  PrdSFQBCMap4: "Product Custom Mapping 5",
  PrdSFQBCMap5: "Product Custom Mapping 6",
  PrdSFQBCMap6: "Product Custom Mapping 7",
  PrdSFQBCMap7: "Product Custom Mapping 8",
  PrdSFQBCMap8: "Product Custom Mapping 9",
  PrdSFQBCMap9: "Product Custom Mapping 10",

  // Vendor detail fields
  VendorObject: "Vendor Object Name",
  BSFQBLIDV: "Vendor ListID Binding Field",
  VenCustBind: "Vendor Binding Criteria",
  BSFQBFNV: "Vendor Name Binding Field",
  NormBindNameV: "Normalize Vendor Names for Binding",
  SFQBVendNm: "Vendor Name Field",
  SFQBCompNmV: "Vendor Company Name Field",
  PrimContV: "Primary Contact Selection (Vendor)",
  DefPrimRoleV: "Default Primary Role (Vendor)",
  SyncCnMlAcBlV: "Sync Contact Address/Billing (Vendor)",
  VendTerm: "Vendor Terms Field",
  VendRemBal: "Vendor Total Balance Field",
  MIddleNameV: "Middle Name Field (Vendor)",
  VendQB2SFETL: "Initial Upload Required (Vend, FS→CRM)",
  ACSF2QBOpsV: "Permitted Vendor Sync Operations (CRM→FS)",
  CreateNV: "Vendor Creation Trigger",
  SFOppStValV: "Stage for Vendor Creation",
  SFCrVenF: "Custom Field for Vendor Creation",
  SFCrVenFVal: "Custom Field Value for Vendor Creation",
  MergeAV: "Merge CRM→FS Vendors",
  OCOCFA2Vend: "Vendor Address Source",
  VenBSAddr: "Vendor Street Address Fill",
  AccSF2QBETLV: "Initial Upload Required (Vend, CRM→FS)",
  ACQB2SFOpsV: "Permitted Vendor Sync Operations (FS→CRM)",
  CreateUpdateAcV: "Create/Update Trigger (FS→CRM Vendor)",
  QBCrAccFV: "FS Field for Vendor Creation",
  QBCrAccFVVal: "FS Field Value for Vendor Creation",
  MergeVA: "Merge FS→CRM Vendors",
  CreateSFContV: "Create CRM Contact Records (Vendor)",
  VenSSAddr: "Vendor Shipping Street Fill (FS→CRM)",
  QB2SFFinInfoOnlyV: "Financial Info Only (Vendor)",
  AccVSFQBCMap: "Vendor Custom Mapping 1",
  AccVSFQBCMap1: "Vendor Custom Mapping 2",
  AccVSFQBCMap2: "Vendor Custom Mapping 3",
  AccVSFQBCMap3: "Vendor Custom Mapping 4",
  AccVSFQBCMap4: "Vendor Custom Mapping 5",
  AccVSFQBCMap5: "Vendor Custom Mapping 6",
  AccVSFQBCMap6: "Vendor Custom Mapping 7",
  AccVSFQBCMap7: "Vendor Custom Mapping 8",
  AccVSFQBCMap8: "Vendor Custom Mapping 9",
  AccVSFQBCMap9: "Vendor Custom Mapping 10",

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
export type ReviewCategory = "direction" | "account" | "transaction" | "product" | "vendor" | "execution" | "other";

export function categorizeKey(key: string): ReviewCategory {
  if (key.startsWith("SyncType")) return "direction";
  // Vendor-specific keys (must be checked before account to avoid false matches)
  if (/^(Vendor|VenCustBind|VenBSAddr|VenSSAddr|VendQB2|VendTerm|VendRemBal|VendInstPmt|VendInstPmtVal|BSFQBLIDV|BSFQBFNV|NormBindNameV|SFQBVend|SFQBCompNmV|PrimContV|DefPrimRoleV|SyncCnMlAcBlV|MIddleNameV|ACSF2QBOpsV|CreateNV|SFOppStValV|SFCrVenF|MergeAV|OCOCFA2Vend|AccSF2QBETLV|ACQB2SFOpsV|CreateUpdateAcV|QBCrAccFV|MergeVA|CreateSFContV|QB2SFFinInfoOnlyV|AccVSFQBCMap|CustVendSel)/.test(key)) return "vendor";
  // Account/Customer keys
  if (/^(BSFQB|AcctCust|NormBind|SFQBCust|SFQBComp|PrimCont|DefPrim|SyncCnMl|UseBillAsShipCust|UseShipAsBillCust|SyncACJ|HJobLevel|CONJob|CustT|SustRem|MIddleName$|AccQB2|AccSF2|AccSFQBCMap|ACSF2Q|ACQB2S|CreateNC|SFOpp[^S]|SFCrCus|Merge[AC]C|MergeAC|MergeCA|Acct[BS]S|CreateUpdate[^O]|QBCr[^O]|CreateSF|QB2SF[^F]|QB306|MrgQB|AccOwn)/.test(key)) return "account";
  // Transaction keys (SO + Invoice)
  if (/^(SO|Inv|DelIns|CreateIn|CreateN[^V]|Dummy|UseDmy|UseBillAsShip$|UseShipAsBill$|DashAsDelim|CORef|UpdSF2Inv|Adr|SFSkipLine|UseProdSched|SFCrConc|InclPC|CreditMemo|PaymentQB|Tax4Inv|QSBTax|UpdOoo|SFOppStage|CreateUpdateOpp|QBCrOpp)/.test(key)) return "transaction";
  // Product keys
  if (/^(Prd|SFFldPNm|ItmPrdNm|CONItem|SFPrdS|SF2QB|SFCost|SFWeight|SerNum|QBItm|DIAItm|DCAItm|DAAItm|InvItemSite|DAItm|DEAItm|SFNOIItm|SFSrvItm|SFOCItm|ReimNm|ReimVal|SFMarg|SFSP|SFNm4|Marg|DefCur|DefTax|DefNoTax|NSTax|NSInc)/.test(key)) return "product";
  if (/^(SandBox|Env2|QBVer|QBLoc|Stop|Sleep|TimeZone|Eml|UseAdm|CCE|BCCE|Long|ConFail)/.test(key)) return "execution";
  return "other";
}

export const REVIEW_CATEGORY_LABELS: Record<ReviewCategory, string> = {
  direction: "Sync Directions",
  account: "Account/Customer Details",
  transaction: "Transaction Details",
  product: "Product Details",
  vendor: "Vendor Details",
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
    {
      label: "Custom Mappings",
      fields: [
        {
          key: "AccSFQBCMap",
          label: "{crm} to {fs} Customer Custom Mapping 1",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccSFQBCMap1",
          label: "{crm} to {fs} Customer Custom Mapping 2",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccSFQBCMap2",
          label: "Custom Mapping 3",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccSFQBCMap3",
          label: "Custom Mapping 4",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccSFQBCMap4",
          label: "Custom Mapping 5",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccSFQBCMap5",
          label: "Custom Mapping 6",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccSFQBCMap6",
          label: "Custom Mapping 7",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccSFQBCMap7",
          label: "Custom Mapping 8",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccSFQBCMap8",
          label: "Custom Mapping 9",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccSFQBCMap9",
          label: "Custom Mapping 10",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
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
    {
      label: "Custom Mappings",
      fields: [
        {
          key: "SOSFQBCMap",
          label: "{crm} {crmtran} to {fs} Sales Order Custom Mapping 1",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "SOSFQBCMap1",
          label: "{crm} {crmtran} to {fs} Sales Order Custom Mapping 2",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "SOSFQBCMap2",
          label: "Custom Mapping 3",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "SOSFQBCMap3",
          label: "Custom Mapping 4",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "SOSFQBCMap4",
          label: "Custom Mapping 5",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "SOSFQBCMap5",
          label: "Custom Mapping 6",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "SOSFQBCMap6",
          label: "Custom Mapping 7",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "SOSFQBCMap7",
          label: "Custom Mapping 8",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "SOSFQBCMap8",
          label: "Custom Mapping 9",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "SOSFQBCMap9",
          label: "Custom Mapping 10",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
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
          label: "{crm} {crmtran} Custom Field with {fs} Invoice #",
          type: "text",
          defaultValue: "",
          helpText: "API field name in CRM that stores the financial system Invoice number.",
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
          helpText: "Which system generates the Invoice number.",
          options: [
            { value: "SFGen", label: "{crm}" },
            { value: "QBGen", label: "{fs}" },
            { value: "SFQBGen", label: "Mixed" },
            { value: "MSFQBGen", label: "{crm} (Merged)" },
          ],
        },
      ],
    },
    {
      label: "Sync Operations ({crm} → {fs})",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "InvQB2SFETL",
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
          key: "InvSF2QBOps",
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
          key: "DelInsAsUpdInv",
          label: "Use Delete/Insert to Update an Invoice",
          type: "select",
          defaultValue: "No",
          helpText: "Instead of updating individual fields, delete the old Invoice and create a new one.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "CreateInFullInv",
          label: "Always Create Invoice in Full (Cached)",
          type: "select",
          defaultValue: "No",
          helpText: "Cache the full Invoice data and re-create it each sync.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "DashAsDelimInv",
          label: "Consider Dash (-) in Invoice Number as Delimiter",
          type: "select",
          defaultValue: "Yes",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "UseBillAsShip",
          label: "Use Billing Address as Shipping",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "UseShipAsBill",
          label: "Use Shipping Address as Billing",
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
      label: "Creation Triggers",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "CreateNInv",
          label: "Create New {fs} Invoice When",
          type: "select",
          defaultValue: "AFAccCr",
          options: [
            { value: "AFAccCr", label: "When {crm} {crmtran} Created" },
            { value: "AFAcc", label: "{crm} {crmtran} is in Certain Stage" },
            { value: "AFAccCFOpp", label: "{crm} {crmtran} Custom Field has Certain Value" },
            { value: "AFAccCFOppEx", label: "{crm} {crmtran} Custom Field has Certain Value (Exclusively)" },
            { value: "AFAccIsWOpp", label: "{crm} {crmtran} is Won" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "SFOppStValInv",
          label: "{crm} {crmtran} Stage to Create New {fs} Invoice",
          type: "text",
          defaultValue: "Closed Won",
          showWhen: { key: "CreateNInv", values: ["AFAcc"] },
          placeholder: "e.g. Closed Won",
        },
        {
          key: "SFCrCusFInv",
          label: "{crm} Custom Field Name to Create New {fs} Invoice",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateNInv", values: ["AFAccCFOpp", "AFAccCFOppEx"] },
        },
        {
          key: "SFCrCusFInvVal",
          label: "{crm} Custom Field Value to Create New {fs} Invoice",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateNInv", values: ["AFAccCFOpp", "AFAccCFOppEx"] },
        },
      ],
    },
    {
      label: "Object & Line Item Mapping",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "CORef4Inv",
          label: "{crm} Object Name to Create {fs} Invoice",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Opportunity",
        },
        {
          key: "CORef4InvLI",
          label: "{crm} Object/Field Name(s) to Create {fs} Invoice Line Items",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. OpportunityLineItem",
        },
        {
          key: "CORef4InvLIAM",
          label: "{crm} Field Name(s) for {fs} Invoice Line Item Rate",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. UnitPrice",
        },
        {
          key: "CORef4InvLIQT",
          label: "{crm} Field Name(s) for {fs} Invoice Line Item Quantity",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Quantity",
        },
        {
          key: "CORef4InvLID",
          label: "{crm} Field Name(s) for {fs} Invoice Line Item Service Dates",
          type: "text",
          defaultValue: "",
        },
        {
          key: "CORef4InvLINm",
          label: "{crm} Field Name for {fs} Invoice Line Item Name",
          type: "text",
          defaultValue: "",
        },
        {
          key: "UpdSF2InvAmnt",
          label: "Skip {crm} {crmtran} Amounts When Creating/Updating {fs} Invoice",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "YesLI", label: "Yes" },
          ],
        },
      ],
    },
    {
      label: "Shipping, Line Items & Filtering",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "DummyInvSHNm",
          label: "Default Shipping & Handling {fs} Item Name(s)",
          type: "text",
          defaultValue: "",
          maxLength: 31,
          placeholder: "e.g. Shipping & Handling",
        },
        {
          key: "DummyInvSHPrc",
          label: "{crm} Field for Default S&H Price",
          type: "text",
          defaultValue: "",
          maxLength: 31,
          placeholder: "e.g. ShippingCost__c",
        },
        {
          key: "DummyInvLIDesc",
          label: "{crm} Field Name for Dummy Description as Line Item Description",
          type: "text",
          defaultValue: "",
          maxLength: 31,
        },
        {
          key: "InvLISorting",
          label: "{crm} Field Name for Custom Sorting of Line Items",
          type: "text",
          defaultValue: "",
          maxLength: 31,
          placeholder: "e.g. SortOrder__c",
        },
        {
          key: "AdrObjRefInv",
          label: "{crm} Field Name with Reference to Address Object",
          type: "text",
          defaultValue: "",
        },
        {
          key: "SFSkipLineInv",
          label: "{crm} Line Field Name to Skip {fs} Invoice Line Item",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Skip_QB__c",
        },
        {
          key: "SFSkipLineInvVal",
          label: "{crm} Line Field Value to Skip {fs} Invoice Line Item",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. true",
        },
        {
          key: "UseProdSched",
          label: "Use Product Schedule to Filter {fs} Invoice Line Items",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes (Both)" },
            { value: "YesQ", label: "Yes (Quantity)" },
            { value: "YesR", label: "Yes (Revenue)" },
            { value: "YesM", label: "Yes (Multiple Invoices)" },
            { value: "YesC", label: "Yes (Custom Scheduler)" },
          ],
        },
      ],
    },
    {
      label: "Address Population ({crm} → {fs})",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "Inv2BACFOpp",
          label: "Populate Billing Address to {fs} Invoice from",
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
          key: "Inv2SACFOpp",
          label: "Populate Shipping Address to {fs} Invoice from",
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
        {
          key: "InvBSAddr",
          label: "Fill {fs} Invoice Billing Street Address with",
          type: "select",
          defaultValue: "None",
          options: [
            { value: "None", label: "Do not fill" },
            { value: "AsSF", label: "As {crm} Source" },
            { value: "AsNA", label: "Name / Address" },
            { value: "AsFLNA", label: "First+Last Name / Name / Address" },
            { value: "AaFLA", label: "First+Last Name / Address" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "InvSSAddr",
          label: "Fill {fs} Invoice Shipping Street Address with",
          type: "select",
          defaultValue: "None",
          options: [
            { value: "None", label: "Do not fill" },
            { value: "AsSF", label: "As {crm} Source" },
            { value: "AsNA", label: "Name / Address" },
            { value: "AsFLNA", label: "First+Last Name / Name / Address" },
            { value: "AaFLA", label: "First+Last Name / Address" },
            { value: "Other", label: "Other" },
          ],
        },
      ],
    },
    {
      label: "Pending & Description Options",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "InvQBPend",
          label: "Create Pending Invoice",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "SFCrConc2FInv",
          label: "{crm} Field Name to Convert {fs} Invoice to Final",
          type: "text",
          defaultValue: "",
        },
        {
          key: "SFCrConc2FInvVal",
          label: "{crm} Field Value to Convert {fs} Invoice to Final",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvLineDesc",
          label: "Create {fs} Line Item Description from {crm} Product Line",
          type: "select",
          defaultValue: "Ovr",
          options: [
            { value: "Ovr", label: "Overwrite" },
            { value: "Conc", label: "Concatenate" },
          ],
        },
        {
          key: "InvDefCustFlNm",
          label: "{crm} Field with Default Customer Full Name",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvPREmNm",
          label: "Filtering {crm} Field Name for Invoice Operations",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvToBePrt",
          label: "Filtering Value(s) for Invoice to be Printed",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvToBeEml",
          label: "Filtering Value(s) for Invoice to be Emailed",
          type: "text",
          defaultValue: "",
        },
        {
          key: "VendInstPmt",
          label: "Vendor Name/Reference for Instant Payment",
          type: "text",
          defaultValue: "",
        },
        {
          key: "VendInstPmtVal",
          label: "Field with a Value for Instant Payment",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvDefTemp",
          label: "Default Template for Invoice",
          type: "text",
          defaultValue: "",
        },
      ],
    },
    {
      label: "Tax Configuration",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "Tax4Inv",
          label: "{crm} Field to Setup Tax Value(s)/Taxable {fs} Invoice",
          type: "text",
          defaultValue: "",
          maxLength: 31,
        },
        {
          key: "Tax4InvValAs",
          label: "Pre-Populate Tax Value to {fs} Invoice as",
          type: "select",
          defaultValue: "None",
          options: [
            { value: "None", label: "Do not populate" },
            { value: "SalesTaxItem", label: "Sales Tax Item / Tax Rate" },
            { value: "TaxItem", label: "Additional Line Item" },
            { value: "TaxValue", label: "Additional Amount for Each Line Item" },
          ],
        },
        {
          key: "QSBTaxFullN",
          label: "{fs} Sales Tax Item/Tax Rate Full Name",
          type: "text",
          defaultValue: "",
          maxLength: 31,
        },
      ],
    },
    {
      label: "Sync Operations ({fs} → {crm})",
      showForDirections: ["QB2SF", "SFQB"],
      fields: [
        {
          key: "InvSF2QBETL",
          label: "Initial {crm} → {fs} Upload Required",
          type: "select",
          defaultValue: "No",
          showForDirections: ["QB2SF"],
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "InvQB2SFOps",
          label: "Permitted Sync Operations ({fs} → {crm})",
          type: "select",
          defaultValue: "CUOPPS",
          options: [
            { value: "None", label: "None" },
            { value: "CUOPPS", label: "Create and Update" },
            { value: "COPPS", label: "Create Only" },
            { value: "UOPPS", label: "Update Only" },
            { value: "UOPPSBPO", label: "Update Balance/Payments Only" },
          ],
        },
        {
          key: "CreateUpdateOppInv",
          label: "Create/Update {crm} {crmtran} When",
          type: "select",
          defaultValue: "QBInvCr",
          options: [
            { value: "QBInvCr", label: "{fs} Invoice Created/Modified" },
            { value: "QBInvCFOpp", label: "{fs} Invoice Field has Certain Value" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "QBCrOppInvF",
          label: "{fs} Field Name to Create/Update {crm} {crmtran}",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateUpdateOppInv", values: ["QBInvCFOpp"] },
        },
        {
          key: "QBCrOppInvFVal",
          label: "{fs} Field Value to Create/Update {crm} {crmtran}",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateUpdateOppInv", values: ["QBInvCFOpp"] },
        },
        {
          key: "SFOppStageInv",
          label: "New {crm} {crmtran} Stage",
          type: "text",
          defaultValue: "",
        },
        {
          key: "UpdOooInvAmnt",
          label: "Update {crm} {crmtran} Amounts with Calculated {fs} Invoice Amounts",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "Never" },
            { value: "YesLI", label: "For Line Items Only" },
            { value: "YesLI1", label: "For Line Items Only, Quantity 1" },
            { value: "Yes", label: "For Line Items and Total" },
            { value: "Yes1", label: "For Line Items and Total, Quantity 1" },
          ],
        },
      ],
    },
    {
      label: "Address Population ({fs} → {crm})",
      showForDirections: ["QB2SF", "SFQB"],
      fields: [
        {
          key: "InvBACFOpp",
          label: "Populate Billing Address Change in {fs} Invoice to",
          type: "select",
          defaultValue: "None",
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
          key: "InvSACFOpp",
          label: "Populate Shipping Address Change in {fs} Invoice to",
          type: "select",
          defaultValue: "None",
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
    {
      label: "Line Item Handling ({fs} → {crm})",
      showForDirections: ["QB2SF", "SFQB"],
      fields: [
        {
          key: "InvExpGrpItm",
          label: "Expand {crm} Group Product After {fs} Group Item Expanded",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "InvNoItems",
          label: "Create/Update {crmtran} Without Line Items",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "InvMulItems",
          label: "Support for Multiple Identical Line Items Required",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "YesC", label: "Yes (with Clones)" },
          ],
        },
        {
          key: "CreditMemo",
          label: "Copy Credit Memo to {crm} Custom Object",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "YesF", label: "Yes (Fast Query)" },
          ],
        },
        {
          key: "PaymentQB",
          label: "Copy Payment to {crm} Custom Object",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "YesF", label: "Yes (Fast Query)" },
            { value: "YesFC", label: "Yes (Fast Query with Check #)" },
          ],
        },
        {
          key: "InvRemBal",
          label: "{crm} {crmtran} Custom Field with Remaining Balance",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. QB_Balance__c",
        },
        {
          key: "InvCJName",
          label: "{crm} {crmtran} Custom Field with Customer/Job Name",
          type: "text",
          defaultValue: "",
        },
      ],
    },
    {
      label: "Contact & Reference Fields",
      fields: [
        {
          key: "InclPCOppInv",
          label: "Include {crmtran} Primary Contact Lookup",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "CORef2Cont4Inv",
          label: "{crm} Field Name with {crmtran} Primary Contact",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvPONumber",
          label: "{crm} {crmtran} Custom Field with {fs} Purchase Order #",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvIsPaid",
          label: "{crm} {crmtran} Custom Checkbox Is Paid",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvDate",
          label: "{crm} {crmtran} Custom Field with {fs} Invoice Date",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvQBOppNm",
          label: "{fs} Custom Field for {crm} {crmtran} Name",
          type: "text",
          defaultValue: "",
        },
      ],
    },
    {
      label: "Custom Mappings & Sales Rep",
      fields: [
        {
          key: "InvSFQBCMap",
          label: "{crm} {crmtran} to {fs} Invoice Custom Mapping 1",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvSFQBCMap1",
          label: "{crm} {crmtran} to {fs} Invoice Custom Mapping 2",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvSFQBCMap2",
          label: "Custom Mapping 3",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "InvSFQBCMap3",
          label: "Custom Mapping 4",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "InvSFQBCMap4",
          label: "Custom Mapping 5",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "InvSFQBCMap5",
          label: "Custom Mapping 6",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "InvSFQBCMap6",
          label: "Custom Mapping 7",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "InvSFQBCMap7",
          label: "Custom Mapping 8",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "InvSFQBCMap8",
          label: "Custom Mapping 9",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "InvSFQBCMap9",
          label: "Custom Mapping 10",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "InvOwnRepMap",
          label: "{crm} {crmtran} Owner to {fs} Sales Rep Mapping",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "YesC", label: "Yes (using Custom Field)" },
          ],
        },
        {
          key: "InvTerm",
          label: "{crm} Custom Field for Invoice Terms",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Payment_Terms__c",
        },
        {
          key: "InvShipVia",
          label: "{crm} Custom Field for Shipping Method",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvDefDiscItm",
          label: "{crm} Custom Field for Default Discount Item",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvClass",
          label: "{crm} Custom Field for Class",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvRflct",
          label: "{crm} Custom Field for Created Invoice Number",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvDplct",
          label: "{crm} Duplicate Prevention Field",
          type: "text",
          defaultValue: "",
        },
        {
          key: "InvLastTranDate",
          label: "{crm} Field for Last Transaction Date",
          type: "text",
          defaultValue: "",
        },
      ],
    },
    {
      label: "Advanced Options",
      fields: [
        {
          key: "InvFastCust",
          label: "Fast Search for a Customer",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "YesF", label: "Yes (by Full Name Only)" },
          ],
        },
        {
          key: "InvUse4Qty",
          label: "Use for {crm} Product Quantity",
          type: "select",
          defaultValue: "Std",
          options: [
            { value: "Std", label: "Standard Field" },
            { value: "Cust", label: "Custom Field" },
          ],
        },
        {
          key: "InvUse4Desc",
          label: "Use for {crm} Product Description",
          type: "select",
          defaultValue: "Std",
          options: [
            { value: "Std", label: "Standard Field" },
            { value: "Cust", label: "Custom Field" },
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
      label: "Product Binding & Naming",
      fields: [
        {
          key: "PrdBind",
          label: "Binding Between {crm} Product and {fs} Item",
          type: "select",
          defaultValue: "PrdNm",
          helpText: "How CRM products are matched to financial system items.",
          options: [
            { value: "PrdNm", label: "Product Name - Item Name" },
            { value: "PrdNSKU", label: "Product Name - Item SKU (Custom)" },
            { value: "PrdSKU", label: "Product Code - Item SKU (Custom)" },
            { value: "PrdCNSKU", label: "Product Code - Item Name" },
            { value: "PrdCFNm", label: "Product Custom Field - Item Name" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "SFFldPNm",
          label: "{crm} Product Field Containing Item Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Name",
        },
        {
          key: "ItmPrdNmTrMd",
          label: "Item/Product Name Truncating Mode",
          type: "text",
          defaultValue: "",
          helpText: "Controls how product names are truncated if they exceed the financial system limit.",
        },
        {
          key: "CONItem",
          label: "Custom Object Name to Create {fs} Item",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Product2",
        },
        {
          key: "SFPrdSPrcCstm",
          label: "{crm} Field Name for Sales Price",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. UnitPrice",
        },
        {
          key: "PrdGrpNm",
          label: "{crm} Product Field Containing Product Group/Assembly Name",
          type: "text",
          defaultValue: "",
        },
        {
          key: "PrdGrpQt",
          label: "{crm} Product Field Containing Product Group/Assembly Quantity",
          type: "text",
          defaultValue: "",
        },
        {
          key: "PrdItmNm",
          label: "{crm} Product Field Containing Parent Item Name for Sub-Items",
          type: "text",
          defaultValue: "",
        },
        {
          key: "SF2QBItmNmOnl",
          label: "Bind {crm} Product to {fs} Item Using Item Name Only",
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
      label: "Cost, Weight & Inventory",
      fields: [
        {
          key: "SFCostItm",
          label: "{crm} Support for Preferred Vendor/Item Cost",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "YesC", label: "Yes, Using Cost Custom Object" },
            { value: "YesU", label: "Yes, Always Update Cost" },
          ],
        },
        {
          key: "SFWeightItm",
          label: "{crm} Support for Item Weight",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "SF2QBPriceLevel",
          label: "{crm} Mapping for {fs} Price Level",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "SF2QBInvItemSite",
          label: "{crm} Support for {fs} Inventory Item Sites",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "SerNumLot",
          label: "{crm} Support for {fs} Serial Numbers/Lots",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "SN", label: "Serial Numbers" },
            { value: "Lot", label: "Lots" },
          ],
        },
        {
          key: "PrdPDescNm",
          label: "{crm} Product Field Containing Purchase Description",
          type: "text",
          defaultValue: "",
        },
        {
          key: "PrdQOnHand",
          label: "{crm} Product Field Containing Quantity on Hand",
          type: "text",
          defaultValue: "",
        },
        {
          key: "PrdManPNum",
          label: "{crm} Product Field Containing Manufacturer Part Number",
          type: "text",
          defaultValue: "",
        },
      ],
    },
    {
      label: "Sync Operations ({crm} → {fs})",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "PrdQB2SFETL",
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
          key: "PrdSF2QBOps",
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
          key: "QBItmTp",
          label: "{crm} Product Field to Select {fs} Item Type",
          type: "text",
          defaultValue: "",
          helpText: "CRM field that indicates which financial system item type to create.",
        },
        {
          key: "CreateCOAAuto",
          label: "Create COA Accounts Automatically",
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
      label: "Item Type Mappings ({crm} → {fs})",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "QBItmTpI",
          label: "{crm} Product Field Value for Inventory Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "QBItmTpA",
          label: "{crm} Product Field Value for Inventory Assembly Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "DIAItmTpI",
          label: "Default Income Account for Inventory/Assembly Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "DCAItmTpI",
          label: "Default COGS Account for Inventory/Assembly Item Type",
          type: "text",
          defaultValue: "Cost of Goods Sold",
        },
        {
          key: "DAAItmTpI",
          label: "Default Asset Account for Inventory/Assembly Item Type",
          type: "text",
          defaultValue: "Inventory Asset",
        },
        {
          key: "InvItemSite",
          label: "Transactional {crm} Field for Inventory/Assembly Item Site",
          type: "text",
          defaultValue: "",
        },
        {
          key: "QBItmTpN",
          label: "{crm} Product Field Value for Non-Inventory Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "DAItmTpN",
          label: "Default Account (Income) for Non-Inventory Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "QBItmTpS",
          label: "{crm} Product Field Value for Service Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "DAItmTpS",
          label: "Default Account (Income) for Service Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "QBItmTpO",
          label: "{crm} Product Field Value for Other Charge Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "DAItmTpO",
          label: "Default Account (Income) for Other Charge Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "QBItmTpD",
          label: "{crm} Product Field Value for Discount Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "DAItmTpD",
          label: "Default Account for Discount Item Type",
          type: "text",
          defaultValue: "",
        },
      ],
    },
    {
      label: "Reimbursable Items",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "SFNOIItmRI",
          label: "{crm} Support for Reimbursable Non-Inventory Items",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "Cust", label: "Custom" },
          ],
        },
        {
          key: "ReimNmNISel",
          label: "{crm} Field Name for Reimbursable Non-Inventory Item Selection",
          type: "text",
          defaultValue: "",
        },
        {
          key: "ReimValNISel",
          label: "{crm} Field Value for Reimbursable Non-Inventory Item Selection",
          type: "text",
          defaultValue: "",
        },
        {
          key: "DEAItmTpN",
          label: "Default Expense Account for Reimbursable Non-Inventory Item Type",
          type: "text",
          defaultValue: "",
        },
        {
          key: "SFSrvItmRI",
          label: "{crm} Support for Reimbursable Service Items",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "Cust", label: "Custom" },
          ],
        },
        {
          key: "ReimNmServSel",
          label: "{crm} Field Name for Reimbursable Service Item Selection",
          type: "text",
          defaultValue: "",
        },
        {
          key: "ReimValServSel",
          label: "{crm} Field Value for Reimbursable Service Item Selection",
          type: "text",
          defaultValue: "",
        },
        {
          key: "DEAItmTpS",
          label: "Default Expense Account for Service Item Type with Cost Support",
          type: "text",
          defaultValue: "",
        },
        {
          key: "SFOCItmRI",
          label: "{crm} Support for Reimbursable Other Charge Items",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
            { value: "Cust", label: "Custom" },
          ],
        },
        {
          key: "ReimNmOCSel",
          label: "{crm} Field Name for Reimbursable Other Charge Item Selection",
          type: "text",
          defaultValue: "",
        },
        {
          key: "ReimValOCSel",
          label: "{crm} Field Value for Reimbursable Other Charge Item Selection",
          type: "text",
          defaultValue: "",
        },
        {
          key: "DEAItmTpO",
          label: "Default Expense Account for Other Charge Item Type with Cost Support",
          type: "text",
          defaultValue: "",
        },
      ],
    },
    {
      label: "Margin & Pricing",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "SFMargFldNm",
          label: "{crm} Field Name to Define Margin",
          type: "text",
          defaultValue: "",
        },
        {
          key: "SFSPMargFldNm",
          label: "{crm} Field Name for Sales Price with Margin",
          type: "text",
          defaultValue: "",
        },
        {
          key: "SFNm4ItmDesc",
          label: "Use {crm} Name for Item Description",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes (Overwrite)" },
            { value: "YesC", label: "Yes (Combine)" },
          ],
        },
      ],
    },
    {
      label: "Sync Operations ({fs} → {crm})",
      showForDirections: ["QB2SF", "SFQB"],
      fields: [
        {
          key: "PrdSF2QBETL",
          label: "Initial {crm} → {fs} Upload Required",
          type: "select",
          defaultValue: "No",
          showForDirections: ["QB2SF"],
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "PrdQB2SFOps",
          label: "Permitted Sync Operations ({fs} → {crm})",
          type: "select",
          defaultValue: "CUOPPS",
          options: [
            { value: "None", label: "None" },
            { value: "CUOPPS", label: "Create and Update" },
            { value: "COPPS", label: "Create Only" },
            { value: "UOPPS", label: "Update Only" },
            { value: "UOPPSE", label: "Update Only (Exclusive)" },
          ],
        },
        {
          key: "MargFldNm",
          label: "{fs} Field Name to Define Margin",
          type: "text",
          defaultValue: "",
        },
        {
          key: "MargFldVal",
          label: "Field Values to Define Margins",
          type: "text",
          defaultValue: "",
        },
        {
          key: "MargVal",
          label: "Standard Price Margin Values",
          type: "text",
          defaultValue: "",
        },
        {
          key: "DefCurList",
          label: "List of Default Currencies",
          type: "text",
          defaultValue: "",
        },
        {
          key: "PrdFamily",
          label: "{fs} Field for {crm} Product Family",
          type: "text",
          defaultValue: "",
        },
        {
          key: "PrdNoLoad",
          label: "{fs} Custom Field to Suppress Transaction",
          type: "text",
          defaultValue: "",
        },
        {
          key: "PrdCode",
          label: "Populate {crm} Product Code with",
          type: "select",
          defaultValue: "No",
          options: [
            { value: "No", label: "Do Not Populate" },
            { value: "ItmNm", label: "Item Name" },
            { value: "ItmDesc", label: "Item Description" },
            { value: "ItmMPN", label: "Item Manufacturer Part Number" },
            { value: "ItmSKU", label: "Item SKU (Custom)" },
          ],
        },
      ],
    },
    {
      label: "Custom Mappings",
      fields: [
        {
          key: "PrdSFQBCMap",
          label: "{crm} Product to {fs} Item Custom Mapping 1",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "PrdSFQBCMap1",
          label: "{crm} Product to {fs} Item Custom Mapping 2",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "PrdSFQBCMap2",
          label: "Custom Mapping 3",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "PrdSFQBCMap3",
          label: "Custom Mapping 4",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "PrdSFQBCMap4",
          label: "Custom Mapping 5",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "PrdSFQBCMap5",
          label: "Custom Mapping 6",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "PrdSFQBCMap6",
          label: "Custom Mapping 7",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "PrdSFQBCMap7",
          label: "Custom Mapping 8",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "PrdSFQBCMap8",
          label: "Custom Mapping 9",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "PrdSFQBCMap9",
          label: "Custom Mapping 10",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
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
      label: "Vendor Object & Binding",
      helpText: "Configure how CRM records are matched to financial system vendors",
      fields: [
        {
          key: "VendorObject",
          label: "{crm} Object Name for {fs} Vendor",
          type: "text",
          defaultValue: "",
          helpText: "API name of the CRM object used as the source for vendor records.",
          placeholder: "e.g. Account",
        },
        {
          key: "BSFQBLIDV",
          label: "Binding {crm} Custom Field with {fs} Vendor ListID",
          type: "text",
          defaultValue: "",
          helpText: "API field name in the CRM that stores the financial system's unique vendor record ID.",
          placeholder: "e.g. QB_Vendor_ListID__c",
        },
        {
          key: "VenCustBind",
          label: "Binding Criteria",
          type: "select",
          defaultValue: "No",
          helpText: "Fallback matching rule when the binding field is empty.",
          options: [
            { value: "No", label: "None" },
            { value: "BACN", label: "Name" },
            { value: "BACNP", label: "Name + Phone" },
            { value: "BACA", label: "Name + Address (no street)" },
            { value: "BACNPA", label: "Name + Phone + Address (no street)" },
          ],
        },
        {
          key: "BSFQBFNV",
          label: "Binding {crm} Custom Field with {fs} Vendor Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. QB_VendorFullName__c",
        },
        {
          key: "NormBindNameV",
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
      label: "Vendor Name & Contact Mapping",
      fields: [
        {
          key: "SFQBVendNm",
          label: "{crm} Field with {fs} Vendor Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Name",
        },
        {
          key: "SFQBCompNmV",
          label: "{crm} Field with {fs} Company Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Company__c",
        },
        {
          key: "PrimContV",
          label: "Primary Contact Selected Via",
          type: "select",
          defaultValue: "None",
          options: [
            { value: "None", label: "None" },
            { value: "ACR", label: "Account/Contact Role" },
            { value: "CLCF", label: "Contact Level Custom Field" },
            { value: "ALCf", label: "Contact Lookup in Account" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "DefPrimRoleV",
          label: "Default Primary Role Name",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Decision Maker",
        },
        {
          key: "SyncCnMlAcBlV",
          label: "Sync Contact Mail Address and Account Billing Address",
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
      label: "Vendor Defaults",
      helpText: "CRM field names that map to financial system vendor default values",
      fields: [
        {
          key: "VendTerm",
          label: "{crm} Custom Field for Vendor Terms",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. Payment_Terms__c",
        },
        {
          key: "VendRemBal",
          label: "{crm} Account/Contact/Object Field for Total Balance",
          type: "text",
          defaultValue: "",
          placeholder: "e.g. QB_Vendor_Balance__c",
        },
        {
          key: "MIddleNameV",
          label: "{crm} Contact/Object Field for Middle Name",
          type: "text",
          defaultValue: "",
        },
      ],
    },
    {
      label: "Source → Destination Sync Rules ({crm} → {fs})",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "VendQB2SFETL",
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
          key: "ACSF2QBOpsV",
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
          key: "CreateNV",
          label: "Create New {fs} Vendor When",
          type: "select",
          defaultValue: "AFAccCr",
          options: [
            { value: "AFAccCr", label: "{crm} Account Created" },
            { value: "AFAcc", label: "{crm} Opportunity is in Certain Stage" },
            { value: "AFAccCFAcc", label: "{crm} Account Custom Field has Certain Value" },
            { value: "AFAccCFOpp", label: "{crm} Opportunity Custom Field has Certain Value" },
            { value: "AFAccIsWOpp", label: "{crm} Opportunity is Won" },
            { value: "AFAccOppCr", label: "{crm} Account or Opportunity Created" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "SFOppStValV",
          label: "{crm} Opportunity Stage to Create New {fs} Vendor",
          type: "text",
          defaultValue: "Closed Won",
          showWhen: { key: "CreateNV", values: ["AFAcc"] },
          placeholder: "e.g. Closed Won",
        },
        {
          key: "SFCrVenF",
          label: "{crm} Custom Field Name to Create New {fs} Vendor",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateNV", values: ["AFAccCFAcc", "AFAccCFOpp"] },
        },
        {
          key: "SFCrVenFVal",
          label: "{crm} Custom Field Value to Create New {fs} Vendor",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateNV", values: ["AFAccCFAcc", "AFAccCFOpp"] },
        },
        {
          key: "MergeAV",
          label: "Merge New {crm} Accounts with Existing {fs} Vendors",
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
      ],
    },
    {
      label: "Address Population ({crm} → {fs})",
      showForDirections: ["SF2QB", "SFQB"],
      fields: [
        {
          key: "OCOCFA2Vend",
          label: "Populate {fs} Vendor Address from",
          type: "select",
          defaultValue: "ABAddr",
          options: [
            { value: "None", label: "Do not populate" },
            { value: "ABAddr", label: "{crm} Account/Contact/Object Address" },
            { value: "OBAddr", label: "{crm} Parent Account Billing Address" },
            { value: "OBCAddr", label: "{crm} Parent Contact Address" },
            { value: "OBPCAddr", label: "{crm} Primary Child Contact Mailing Address" },
          ],
        },
        {
          key: "VenBSAddr",
          label: "Fill {fs} Vendor Street Address with",
          type: "select",
          defaultValue: "AsSF",
          options: [
            { value: "None", label: "Do not fill" },
            { value: "AsSF", label: "As {crm} Account/Contact" },
            { value: "AsNA", label: "Name / Address" },
            { value: "AsFLNA", label: "First+Last Name / Name / Address" },
            { value: "AaFLA", label: "First+Last Name / Address" },
            { value: "Other", label: "Other" },
          ],
        },
      ],
    },
    {
      label: "Destination → Source Sync Rules ({fs} → {crm})",
      showForDirections: ["QB2SF", "SFQB"],
      fields: [
        {
          key: "AccSF2QBETLV",
          label: "Initial {crm} → {fs} Upload Required",
          type: "select",
          defaultValue: "No",
          showForDirections: ["QB2SF"],
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
        {
          key: "ACQB2SFOpsV",
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
          key: "CreateUpdateAcV",
          label: "Create/Update {crm} Account/Contact/Object When",
          type: "select",
          defaultValue: "QBCustCr",
          options: [
            { value: "QBCustCr", label: "{fs} Vendor Created/Modified" },
            { value: "QBCustCFAcc", label: "{fs} Vendor Field has Certain Value" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "QBCrAccFV",
          label: "{fs} Field Name to Create/Update {crm} Account/Contact/Object",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateUpdateAcV", values: ["QBCustCFAcc"] },
        },
        {
          key: "QBCrAccFVVal",
          label: "{fs} Field Value to Create/Update {crm} Account/Contact/Object",
          type: "text",
          defaultValue: "",
          showWhen: { key: "CreateUpdateAcV", values: ["QBCustCFAcc"] },
        },
        {
          key: "MergeVA",
          label: "Merge New {fs} Vendors with Existing {crm} Accounts",
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
          key: "CreateSFContV",
          label: "Create {crm} Contact Records",
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
      label: "Address Population ({fs} → {crm})",
      showForDirections: ["QB2SF", "SFQB"],
      fields: [
        {
          key: "VenSSAddr",
          label: "Fill {crm} Shipping/Other Street Address with",
          type: "select",
          defaultValue: "AsSF",
          options: [
            { value: "None", label: "Do not fill" },
            { value: "AsSF", label: "As {fs} Vendor" },
            { value: "AsNA", label: "Name / Address" },
            { value: "AsFLNA", label: "First+Last Name / Name / Address" },
            { value: "AaFLA", label: "First+Last Name / Address" },
            { value: "Other", label: "Other" },
          ],
        },
        {
          key: "QB2SFFinInfoOnlyV",
          label: "Provide to {crm} Financial Information Only",
          type: "select",
          defaultValue: "No",
          helpText: "Only sync balance and financial data -- skip name/address fields.",
          options: [
            { value: "No", label: "No" },
            { value: "Yes", label: "Yes" },
          ],
        },
      ],
    },
    {
      label: "Custom Mappings",
      fields: [
        {
          key: "AccVSFQBCMap",
          label: "{crm} to {fs} Vendor Custom Mapping 1",
          type: "text",
          defaultValue: "",
        },
        {
          key: "AccVSFQBCMap1",
          label: "{crm} to {fs} Vendor Custom Mapping 2",
          type: "text",
          defaultValue: "",
        },
        {
          key: "AccVSFQBCMap2",
          label: "Custom Mapping 3",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccVSFQBCMap3",
          label: "Custom Mapping 4",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccVSFQBCMap4",
          label: "Custom Mapping 5",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccVSFQBCMap5",
          label: "Custom Mapping 6",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccVSFQBCMap6",
          label: "Custom Mapping 7",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccVSFQBCMap7",
          label: "Custom Mapping 8",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccVSFQBCMap8",
          label: "Custom Mapping 9",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
        },
        {
          key: "AccVSFQBCMap9",
          label: "Custom Mapping 10",
          type: "text",
          defaultValue: "",
          helpText: "Additional custom field mapping between {crm} and {fs}",
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
