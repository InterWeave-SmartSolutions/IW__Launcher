/** Sync direction options for object mappings */
export type SyncDirection = "None" | "SF2QB" | "QB2SF" | "SFQB";

/** A single sync mapping entry (e.g. "CRM Account to QB Customer") */
export interface SyncMapping {
  key: string;
  label: string;
  sourceLabel: string;
  destLabel: string;
  value: SyncDirection;
  supportsBidirectional: boolean;
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

/** Build the list of available sync mappings based on solution type */
export function buildSyncMappings(
  solutionType: string,
  existing: Record<string, string> = {}
): SyncMapping[] {
  const meta = deriveSolutionMeta(solutionType);
  const { crmName, crmCustomerLabel, crmTransactionLabel, fsName, fsCustomerLabel } = meta;

  const hasBidi = /[12BNPTGPC]$/.test(solutionType);
  const mappings: SyncMapping[] = [];

  const add = (key: string, sourceLabel: string, destLabel: string, bidi = hasBidi) => {
    mappings.push({
      key,
      label: `${sourceLabel} to ${destLabel}`,
      sourceLabel,
      destLabel,
      value: (existing[key] as SyncDirection) || "None",
      supportsBidirectional: bidi,
    });
  };

  // Core mappings (always available for most solution types)
  add("SyncTypeAC", `${crmName} ${crmCustomerLabel}`, `${fsName} ${fsCustomerLabel}`);
  add("SyncTypeSO", `${crmName} ${crmTransactionLabel}`, `${fsName} Sales Order`);
  add("SyncTypeInv", `${crmName} ${crmTransactionLabel}`, `${fsName} Invoice`);
  add("SyncTypeSR", `${crmName} ${crmTransactionLabel}`, `${fsName} Sales Receipt`);
  add("SyncTypePrd", `${crmName} Product`, `${fsName} Item`);

  // Extended mappings based on solution type
  if (hasBidi) {
    add("SyncTypeEst", `${crmName} ${crmTransactionLabel}`, `${fsName} Estimate`);
    add("SyncTypeBill", `${crmName} Object`, `${fsName} Bill`);
    add("SyncTypeCheck", `${crmName} ${crmTransactionLabel}`, `${fsName} Check`);
    add("SyncTypeCM", `${crmName} ${crmTransactionLabel}`, `${fsName} Credit Memo`);
  }

  if (/[1BNPTGP]$/.test(solutionType)) {
    add("SyncTypeVAC", `${crmName} Account/Contact/Object`, `${fsName} Vendor`);
    add("SyncTypeOJ", `${crmName} ${crmTransactionLabel}`, `${fsName} Job`);
    add("SyncTypePO", `${crmName} ${crmTransactionLabel}`, `${fsName} Purchase Order`);
    add("SyncTypeVC", `${crmName} Object`, `${fsName} Vendor Credit`, false);
    add("SyncTypeDep", `${crmName} Object`, `${fsName} Deposit`, false);
    add("SyncTypePR", `${crmName} Object`, `${fsName} Payment Received`, false);
    add("SyncTypeBP", `${crmName} Object`, `${fsName} Bill Payment`, false);
    add("SyncTypeCCC", `${crmName} Object`, `${fsName} Credit Card Charge`);
  }

  if (/[BNPTGP]$/.test(solutionType)) {
    add("SyncTypeCOA", `${crmName} Object`, `${fsName} Account (COA)`);
    add("SyncTypeJE", `${crmName} Object`, `${fsName} Journal Entry`);
    add("SyncTypeTT", `${crmName} Object`, `${fsName} Time Tracking`);
    add("SyncTypeSC", `${crmName} Object`, `${fsName} Statement Charges`);
  }

  return mappings;
}
