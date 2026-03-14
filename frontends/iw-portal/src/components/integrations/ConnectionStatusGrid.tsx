import { Link } from "react-router-dom";
import { Database, Wifi, WifiOff, Loader2 } from "lucide-react";
import { cn } from "@/lib/utils";
import { Badge } from "@/components/ui/badge";
import type { CompanyCredential } from "@/types/configuration";

interface ConnectionStatusGridProps {
  credentials: CompanyCredential[];
  isLoading: boolean;
}

/** Derive a human-readable system name from credential type codes */
function systemName(credentialType: string): string {
  const map: Record<string, string> = {
    salesforce: "Salesforce",
    sf: "Salesforce",
    quickbooks: "QuickBooks",
    qb: "QuickBooks",
    creatio: "Creatio CRM",
    crm: "CRM",
    magento: "Magento 2",
    mg: "Magento 2",
    authorizenet: "Authorize.Net",
    auth: "Authorize.Net",
    netsuite: "NetSuite",
    ns: "NetSuite",
  };
  const lower = credentialType.toLowerCase();
  return map[lower] ?? credentialType;
}

/** Load cached test result from localStorage */
function loadTestResult(credentialType: string): { reachable: boolean; testedAt: string } | null {
  try {
    const raw = localStorage.getItem(`iw-cred-test-${credentialType}`);
    if (!raw) return null;
    return JSON.parse(raw);
  } catch {
    return null;
  }
}

export function ConnectionStatusGrid({ credentials, isLoading }: ConnectionStatusGridProps) {
  if (isLoading) {
    return (
      <div className="flex items-center justify-center py-6">
        <Loader2 className="w-5 h-5 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (credentials.length === 0) {
    return (
      <div className="text-center py-4">
        <Database className="w-6 h-6 text-muted-foreground mx-auto mb-1.5" />
        <p className="text-xs text-muted-foreground">No connections configured</p>
        <Link to="/company/config/wizard" className="text-xs text-[hsl(var(--primary))] hover:underline mt-1 inline-block">
          Set up connections
        </Link>
      </div>
    );
  }

  return (
    <div className="grid grid-cols-2 gap-2">
      {credentials.map((cred) => {
        const testResult = loadTestResult(cred.credentialType);
        const isReachable = testResult?.reachable === true;
        const isUntested = !testResult;

        return (
          <div
            key={cred.id}
            className={cn(
              "rounded-lg border p-2.5 flex items-center gap-2.5 text-sm",
              isReachable
                ? "border-[hsl(var(--success)/0.3)] bg-[hsl(var(--success)/0.04)]"
                : isUntested
                  ? "border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.2)]"
                  : "border-[hsl(var(--destructive)/0.3)] bg-[hsl(var(--destructive)/0.04)]"
            )}
          >
            {isReachable ? (
              <Wifi className="w-3.5 h-3.5 text-[hsl(var(--success))] shrink-0" />
            ) : isUntested ? (
              <Database className="w-3.5 h-3.5 text-muted-foreground shrink-0" />
            ) : (
              <WifiOff className="w-3.5 h-3.5 text-[hsl(var(--destructive))] shrink-0" />
            )}
            <div className="min-w-0 flex-1">
              <p className="text-xs font-medium truncate">{systemName(cred.credentialType)}</p>
              {cred.username && (
                <p className="text-[10px] text-muted-foreground truncate">{cred.username}</p>
              )}
            </div>
            <Badge
              variant={isReachable ? "success" : isUntested ? "secondary" : "destructive"}
              className="text-[9px] px-1 py-0 shrink-0"
            >
              {isReachable ? "OK" : isUntested ? "?" : "Err"}
            </Badge>
          </div>
        );
      })}
    </div>
  );
}
