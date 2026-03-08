/** Sync direction options for object mappings */
export type SyncDirection = "None" | "SF2QB" | "QB2SF" | "SFQB";

/** Category groupings for object mappings */
export type MappingCategory = "customer" | "transaction" | "financial";

/** A single sync mapping entry (e.g. "CRM Account to QB Customer") */
export interface SyncMapping {
  key: string;
  label: string;
  sourceLabel: string;
  destLabel: string;
  value: SyncDirection;
  supportsBidirectional: boolean;
  tier: "core" | "extended";
  category: MappingCategory;
}

/** Response from GET /api/config/wizard */
export interface WizardConfigResponse {
  success: boolean;
  data?: {
    solutionType: string;
    profileName: string | null;
    hasConfiguration: boolean;
    syncMappings: Record<string, string>;
  };
  error?: string;
}

/** Request body for PUT /api/config/wizard */
export interface WizardConfigSaveRequest {
  solutionType: string;
  syncMappings: Record<string, string>;
  profileName?: string;
}

/** A single company credential entry */
export interface CompanyCredential {
  id: number;
  credentialType: string;
  credentialName: string;
  username: string;
  endpointUrl: string;
  hasApiKey: boolean;
  isActive: boolean;
}

/** Profile-level credentials (SF/QB/CRM from profiles table) */
export interface ProfileCredentials {
  sfUsername: string;
  sfUrl: string;
  qbCompanyFile: string;
  qbUsername: string;
  crmUrl: string;
  crmUsername: string;
}

/** Response from GET /api/config/credentials */
export interface CredentialsResponse {
  success: boolean;
  data?: {
    credentials: CompanyCredential[];
    profileCredentials: ProfileCredentials;
  };
  error?: string;
}

/** Request body for PUT /api/config/credentials */
export interface CredentialSaveRequest {
  credentialType: string;
  credentialName?: string;
  username?: string;
  password?: string;
  apiKey?: string;
  apiSecret?: string;
  endpointUrl?: string;
  extraConfig?: string;
}

/** Request body for POST /api/config/credentials/test */
export interface CredentialTestRequest {
  credentialType: string;
  endpointUrl: string;
  username?: string;
  password?: string;
  apiKey?: string;
}

/** Response from POST /api/config/credentials/test */
export interface CredentialTestResponse {
  success: boolean;
  reachable: boolean;
  statusCode?: number;
  responseTimeMs?: number;
  message: string;
  error?: string;
}

/** A saved configuration profile */
export interface ConfigProfile {
  profileName: string;
  solutionType: string;
  updatedAt: string;
}

/** Response from GET /api/config/profiles */
export interface ProfilesResponse {
  success: boolean;
  data?: {
    profiles: ConfigProfile[];
  };
  error?: string;
}

/** CRM system names mapped from solution type codes */
export interface SolutionMeta {
  crmName: string;
  crmCustomerLabel: string;
  crmTransactionLabel: string;
  fsName: string;
  fsCustomerLabel: string;
}

/** Derive CRM/Financial system labels from solution type — mirrors JSP logic */
export function deriveSolutionMeta(solutionType: string): SolutionMeta {
  let crmName = "CRM";
  let crmCustomerLabel = "Customer";
  let crmTransactionLabel = "Order";
  let fsName = "Financials";
  let fsCustomerLabel = "Customer";

  // CRM system
  if (solutionType.startsWith("SF")) crmName = "SF";
  else if (solutionType.startsWith("AR")) crmName = "Aria";
  else if (solutionType.startsWith("SUG")) crmName = "Sugar";
  else if (solutionType.startsWith("OMS")) crmName = "OMS";
  else if (solutionType.startsWith("ORA")) crmName = "Fusion";
  else if (solutionType.startsWith("MSDCRM")) crmName = "MSDCRM";
  else if (solutionType.startsWith("PPOL")) crmName = "PPOL";

  // CRM customer label
  if (["SF", "Sugar", "Fusion", "MSDCRM"].includes(crmName)) crmCustomerLabel = "Account/Contact";
  else if (crmName === "Aria") crmCustomerLabel = "Account";
  else if (crmName === "PPOL") crmCustomerLabel = "Organization";

  // CRM transaction label
  if (["SF", "Fusion", "MSDCRM"].includes(crmName)) crmTransactionLabel = "Opportunity/Object";
  else if (crmName === "Sugar") crmTransactionLabel = "Quote";
  else if (crmName === "Aria") crmTransactionLabel = "Transaction";

  // Financial system
  if (solutionType.includes("QB")) { fsName = "QB"; fsCustomerLabel = "Customer/Job"; }
  else if (solutionType.includes("NS")) fsName = "NetSuite";
  else if (solutionType.includes("ACC")) fsName = "Accpac";
  else if (solutionType.includes("PT")) fsName = "Sage";
  else if (solutionType.includes("2GP")) fsName = "MS GP";
  else if (solutionType.includes("2OM")) fsName = "OMS";

  return { crmName, crmCustomerLabel, crmTransactionLabel, fsName, fsCustomerLabel };
}

/**
 * Mapping dependency rules — advisory warnings when a dependent mapping
 * is enabled but its prerequisite is not. Keys are the dependent mapping,
 * values are the prerequisite mappings that should also be enabled.
 */
export const MAPPING_DEPENDENCIES: Record<string, string[]> = {
  SyncTypeSO:    ["SyncTypeAC"],
  SyncTypeInv:   ["SyncTypeAC"],
  SyncTypeSR:    ["SyncTypeAC"],
  SyncTypeEst:   ["SyncTypeAC"],
  SyncTypePO:    ["SyncTypeVAC"],
  SyncTypeBill:  ["SyncTypeVAC"],
  SyncTypeBP:    ["SyncTypeBill"],
};

/**
 * Recommended default sync directions for each solution type.
 * Applied only on first-time setup (empty syncValues).
 */
export const RECOMMENDED_DEFAULTS: Record<string, Record<string, SyncDirection>> = {
  SF2QB:  { SyncTypeAC: "SF2QB", SyncTypeSO: "SF2QB", SyncTypeInv: "SF2QB", SyncTypePrd: "SF2QB" },
  SF2QB1: { SyncTypeAC: "SF2QB", SyncTypeSO: "SF2QB", SyncTypeInv: "SF2QB", SyncTypePrd: "SF2QB", SyncTypeVAC: "SF2QB" },
  SF2QBB: { SyncTypeAC: "SFQB",  SyncTypeSO: "SFQB",  SyncTypeInv: "SFQB",  SyncTypePrd: "SFQB" },
  SF2NS:  { SyncTypeAC: "SF2QB", SyncTypeSO: "SF2QB", SyncTypeInv: "SF2QB", SyncTypePrd: "SF2QB" },
  SF2PT:  { SyncTypeAC: "SF2QB", SyncTypeSO: "SF2QB", SyncTypeInv: "SF2QB" },
  SF2GP:  { SyncTypeAC: "SF2QB", SyncTypeSO: "SF2QB", SyncTypeInv: "SF2QB" },
  CRM2QB: { SyncTypeAC: "SF2QB", SyncTypeSO: "SF2QB", SyncTypeInv: "SF2QB" },
  QB:     { SyncTypeAC: "SF2QB", SyncTypePrd: "SF2QB" },
  SF:     { SyncTypeAC: "SF2QB" },
  GENERIC: {},
};

/** Category display labels */
export const CATEGORY_LABELS: Record<MappingCategory, string> = {
  customer: "Customer Records",
  transaction: "Transactions",
  financial: "Financial Objects",
};

/** Build the list of available sync mappings based on solution type */
export function buildSyncMappings(
  solutionType: string,
  existing: Record<string, string> = {}
): SyncMapping[] {
  const meta = deriveSolutionMeta(solutionType);
  const { crmName, crmCustomerLabel, crmTransactionLabel, fsName, fsCustomerLabel } = meta;

  // Fixed regex: suffix C was missing from extended conditions in the original
  const hasBidi = /[12BNPTGPC]$/.test(solutionType);
  const hasExtended1 = /[1BNPTGPC]$/.test(solutionType);
  const hasExtended2 = /[BNPTGP]$/.test(solutionType);
  const mappings: SyncMapping[] = [];

  const add = (
    key: string,
    sourceLabel: string,
    destLabel: string,
    category: MappingCategory,
    tier: "core" | "extended",
    bidi = hasBidi
  ) => {
    mappings.push({
      key,
      label: `${sourceLabel} to ${destLabel}`,
      sourceLabel,
      destLabel,
      value: (existing[key] as SyncDirection) || "None",
      supportsBidirectional: bidi,
      tier,
      category,
    });
  };

  // ─── Core mappings (always available) ───
  add("SyncTypeAC", `${crmName} ${crmCustomerLabel}`, `${fsName} ${fsCustomerLabel}`, "customer", "core");
  add("SyncTypeSO", `${crmName} ${crmTransactionLabel}`, `${fsName} Sales Order`, "transaction", "core");
  add("SyncTypeInv", `${crmName} ${crmTransactionLabel}`, `${fsName} Invoice`, "transaction", "core");
  add("SyncTypeSR", `${crmName} ${crmTransactionLabel}`, `${fsName} Sales Receipt`, "transaction", "core");
  add("SyncTypePrd", `${crmName} Product`, `${fsName} Item`, "customer", "core");

  // ─── Extended: bi-directional tier ───
  if (hasBidi) {
    add("SyncTypeEst", `${crmName} ${crmTransactionLabel}`, `${fsName} Estimate`, "transaction", "extended");
    add("SyncTypeBill", `${crmName} Object`, `${fsName} Bill`, "transaction", "extended");
    add("SyncTypeCheck", `${crmName} ${crmTransactionLabel}`, `${fsName} Check`, "transaction", "extended");
    add("SyncTypeCM", `${crmName} ${crmTransactionLabel}`, `${fsName} Credit Memo`, "transaction", "extended");
  }

  // ─── Extended: level 1 ───
  if (hasExtended1) {
    add("SyncTypeVAC", `${crmName} Account/Contact/Object`, `${fsName} Vendor`, "customer", "extended");
    add("SyncTypeOJ", `${crmName} ${crmTransactionLabel}`, `${fsName} Job`, "transaction", "extended");
    add("SyncTypePO", `${crmName} ${crmTransactionLabel}`, `${fsName} Purchase Order`, "transaction", "extended");
    add("SyncTypeVC", `${crmName} Object`, `${fsName} Vendor Credit`, "financial", "extended", false);
    add("SyncTypeDep", `${crmName} Object`, `${fsName} Deposit`, "financial", "extended", false);
    add("SyncTypePR", `${crmName} Object`, `${fsName} Payment Received`, "financial", "extended", false);
    add("SyncTypeBP", `${crmName} Object`, `${fsName} Bill Payment`, "financial", "extended", false);
    add("SyncTypeCCC", `${crmName} Object`, `${fsName} Credit Card Charge`, "financial", "extended");
  }

  // ─── Extended: level 2 (financial objects) ───
  if (hasExtended2) {
    add("SyncTypeCOA", `${crmName} Object`, `${fsName} Account (COA)`, "financial", "extended");
    add("SyncTypeJE", `${crmName} Object`, `${fsName} Journal Entry`, "financial", "extended");
    add("SyncTypeTT", `${crmName} Object`, `${fsName} Time Tracking`, "financial", "extended");
    add("SyncTypeSC", `${crmName} Object`, `${fsName} Statement Charges`, "financial", "extended");
  }

  return mappings;
}
