import { useState } from "react";
import { Link } from "react-router-dom";
import { Database, Settings, ArrowRight, Key, Loader2, Zap, CheckCircle, XCircle, Clock } from "lucide-react";
import { cn } from "@/lib/utils";
import { useTestCredential } from "@/hooks/useConfiguration";
import { useToast } from "@/providers/ToastProvider";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";
import type { CompanyCredential, ProfileCredentials } from "@/types/configuration";

interface CredentialInventoryProps {
  credentials: CompanyCredential[];
  profileCredentials?: ProfileCredentials;
  isLoading: boolean;
}

interface TestResult {
  reachable: boolean;
  statusCode: number;
  responseTimeMs: number;
  testedAt: string;
}

function loadTestResult(credentialType: string): TestResult | null {
  try {
    const raw = localStorage.getItem(`iw-cred-test-${credentialType}`);
    if (!raw) return null;
    return JSON.parse(raw) as TestResult;
  } catch {
    return null;
  }
}

function saveTestResult(credentialType: string, result: TestResult) {
  localStorage.setItem(`iw-cred-test-${credentialType}`, JSON.stringify(result));
}

function fmtTestTime(iso: string): string {
  const diff = Date.now() - new Date(iso).getTime();
  const seconds = Math.floor(diff / 1000);
  if (seconds < 60) return `${seconds}s ago`;
  const minutes = Math.floor(seconds / 60);
  if (minutes < 60) return `${minutes}m ago`;
  const hours = Math.floor(minutes / 60);
  if (hours < 24) return `${hours}h ago`;
  const days = Math.floor(hours / 24);
  return `${days}d ago`;
}

function StatusDot({ cred, testResult }: { cred: CompanyCredential; testResult: TestResult | null }) {
  if (!cred.endpointUrl) {
    return (
      <Tooltip>
        <TooltipTrigger asChild>
          <span className="inline-block w-2 h-2 rounded-full bg-muted-foreground opacity-40" />
        </TooltipTrigger>
        <TooltipContent>No endpoint URL configured</TooltipContent>
      </Tooltip>
    );
  }

  if (!cred.isActive) {
    return (
      <Tooltip>
        <TooltipTrigger asChild>
          <span className="inline-block w-2 h-2 rounded-full bg-[hsl(var(--destructive))]" />
        </TooltipTrigger>
        <TooltipContent>Credential is inactive</TooltipContent>
      </Tooltip>
    );
  }

  if (!testResult) {
    return (
      <Tooltip>
        <TooltipTrigger asChild>
          <span className="inline-block w-2 h-2 rounded-full bg-[hsl(var(--warning))]" />
        </TooltipTrigger>
        <TooltipContent>Active but not tested</TooltipContent>
      </Tooltip>
    );
  }

  if (!testResult.reachable) {
    return (
      <Tooltip>
        <TooltipTrigger asChild>
          <span className="inline-block w-2 h-2 rounded-full bg-[hsl(var(--destructive))]" />
        </TooltipTrigger>
        <TooltipContent>Unreachable ({fmtTestTime(testResult.testedAt)})</TooltipContent>
      </Tooltip>
    );
  }

  if (testResult.responseTimeMs > 2000) {
    return (
      <Tooltip>
        <TooltipTrigger asChild>
          <span className="inline-block w-2 h-2 rounded-full bg-[hsl(var(--warning))]" />
        </TooltipTrigger>
        <TooltipContent>Slow response ({testResult.responseTimeMs}ms, {fmtTestTime(testResult.testedAt)})</TooltipContent>
      </Tooltip>
    );
  }

  return (
    <Tooltip>
      <TooltipTrigger asChild>
        <span className="inline-block w-2 h-2 rounded-full bg-[hsl(var(--success))]" />
      </TooltipTrigger>
      <TooltipContent>Reachable ({testResult.responseTimeMs}ms, {fmtTestTime(testResult.testedAt)})</TooltipContent>
    </Tooltip>
  );
}

export function CredentialInventory({ credentials, profileCredentials, isLoading }: CredentialInventoryProps) {
  const { showToast } = useToast();
  const testCredential = useTestCredential();

  // Per-credential test state
  const [testResults, setTestResults] = useState<Record<string, TestResult | null>>(() => {
    const initial: Record<string, TestResult | null> = {};
    for (const cred of credentials) {
      initial[cred.credentialType] = loadTestResult(cred.credentialType);
    }
    return initial;
  });
  const [testingId, setTestingId] = useState<string | null>(null);
  const [testingAll, setTestingAll] = useState(false);

  const handleTest = async (cred: CompanyCredential) => {
    if (!cred.endpointUrl) return;
    setTestingId(cred.credentialType);
    try {
      const resp = await testCredential.mutateAsync({
        credentialType: cred.credentialType,
        endpointUrl: cred.endpointUrl,
      });
      const result: TestResult = {
        reachable: resp.reachable,
        statusCode: resp.statusCode ?? 0,
        responseTimeMs: resp.responseTimeMs ?? 0,
        testedAt: new Date().toISOString(),
      };
      saveTestResult(cred.credentialType, result);
      setTestResults((prev) => ({ ...prev, [cred.credentialType]: result }));
      if (resp.reachable) {
        showToast(`${cred.credentialName}: reachable (${result.responseTimeMs}ms)`, "success");
      } else {
        showToast(`${cred.credentialName}: unreachable`, "error");
      }
    } catch (e) {
      const result: TestResult = {
        reachable: false,
        statusCode: 0,
        responseTimeMs: 0,
        testedAt: new Date().toISOString(),
      };
      saveTestResult(cred.credentialType, result);
      setTestResults((prev) => ({ ...prev, [cred.credentialType]: result }));
      showToast(e instanceof Error ? e.message : "Test failed", "error");
    } finally {
      setTestingId(null);
    }
  };

  const handleTestAll = async () => {
    const testable = credentials.filter((c) => c.endpointUrl);
    if (testable.length === 0) {
      showToast("No credentials with endpoint URLs to test", "error");
      return;
    }
    setTestingAll(true);
    let passed = 0;
    let failed = 0;
    for (const cred of testable) {
      setTestingId(cred.credentialType);
      try {
        const resp = await testCredential.mutateAsync({
          credentialType: cred.credentialType,
          endpointUrl: cred.endpointUrl,
        });
        const result: TestResult = {
          reachable: resp.reachable,
          statusCode: resp.statusCode ?? 0,
          responseTimeMs: resp.responseTimeMs ?? 0,
          testedAt: new Date().toISOString(),
        };
        saveTestResult(cred.credentialType, result);
        setTestResults((prev) => ({ ...prev, [cred.credentialType]: result }));
        if (resp.reachable) passed++;
        else failed++;
      } catch {
        const result: TestResult = {
          reachable: false,
          statusCode: 0,
          responseTimeMs: 0,
          testedAt: new Date().toISOString(),
        };
        saveTestResult(cred.credentialType, result);
        setTestResults((prev) => ({ ...prev, [cred.credentialType]: result }));
        failed++;
      }
    }
    setTestingId(null);
    setTestingAll(false);
    showToast(`Test complete: ${passed} reachable, ${failed} unreachable`, passed > 0 && failed === 0 ? "success" : "error");
  };

  // Loading skeleton
  if (isLoading) {
    return (
      <div className="space-y-3">
        {[1, 2, 3].map((i) => (
          <div key={i} className="h-12 rounded-md bg-muted animate-pulse" />
        ))}
      </div>
    );
  }

  // Empty state
  if (credentials.length === 0 && !profileCredentials) {
    return (
      <div className="flex flex-col items-center justify-center py-12 text-center">
        <Database className="w-12 h-12 text-muted-foreground mb-4" />
        <h3 className="text-lg font-medium mb-1">No Credentials Configured</h3>
        <p className="text-sm text-muted-foreground mb-4 max-w-md">
          Set up your connector credentials in the configuration wizard to enable integration flows.
        </p>
        <Button asChild>
          <Link to="/company/config">
            <Settings className="w-4 h-4 mr-2" />
            Open Configuration Wizard
            <ArrowRight className="w-4 h-4 ml-2" />
          </Link>
        </Button>
      </div>
    );
  }

  const hasProfileCreds = profileCredentials && (
    profileCredentials.sfUsername ||
    profileCredentials.qbUsername ||
    profileCredentials.crmUsername
  );

  return (
    <div className="space-y-6">
      {/* Header with Test All */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-2">
          <Key className="w-4 h-4 text-muted-foreground" />
          <h3 className="text-sm font-medium">
            Connector Credentials
            <Badge variant="secondary" className="ml-2">{credentials.length}</Badge>
          </h3>
        </div>
        <Button
          variant="outline"
          size="sm"
          onClick={() => void handleTestAll()}
          disabled={testingAll || credentials.every((c) => !c.endpointUrl)}
        >
          {testingAll ? (
            <Loader2 className="w-3.5 h-3.5 animate-spin mr-1" />
          ) : (
            <Zap className="w-3.5 h-3.5 mr-1" />
          )}
          Test All
        </Button>
      </div>

      {/* Inventory Table */}
      <div className="border border-[hsl(var(--border))] rounded-lg overflow-hidden bg-[hsl(var(--card))] shadow-sm">
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-[hsl(var(--border))] bg-muted/80">
              <th className="text-left px-4 py-2.5 font-semibold text-foreground/70">Connector</th>
              <th className="text-left px-4 py-2.5 font-semibold text-foreground/70">System</th>
              <th className="text-left px-4 py-2.5 font-semibold text-foreground/70">Auth</th>
              <th className="text-left px-4 py-2.5 font-semibold text-foreground/70">Status</th>
              <th className="text-right px-4 py-2.5 font-semibold text-foreground/70">Actions</th>
            </tr>
          </thead>
          <tbody>
            {credentials.map((cred) => {
              const result = testResults[cred.credentialType] ?? null;
              const isTesting = testingId === cred.credentialType;

              return (
                <tr
                  key={cred.id}
                  className={cn(
                    "border-b border-[hsl(var(--border))] last:border-b-0 transition-colors",
                    isTesting && "bg-muted/30"
                  )}
                >
                  {/* Connector */}
                  <td className="px-4 py-3">
                    <div className="flex items-center gap-2.5">
                      <StatusDot cred={cred} testResult={result} />
                      <div>
                        <div className="font-medium">{cred.credentialName}</div>
                        {cred.endpointUrl && (
                          <div className="text-xs text-muted-foreground truncate max-w-[200px]">
                            {cred.endpointUrl}
                          </div>
                        )}
                      </div>
                    </div>
                  </td>

                  {/* System */}
                  <td className="px-4 py-3">
                    <Badge variant="outline">{cred.credentialType}</Badge>
                  </td>

                  {/* Auth */}
                  <td className="px-4 py-3">
                    <div className="flex items-center gap-1.5 text-muted-foreground">
                      {cred.username && (
                        <span className="text-xs truncate max-w-[120px]">{cred.username}</span>
                      )}
                      {cred.hasApiKey && (
                        <Badge variant="secondary" className="text-[10px] px-1.5 py-0">
                          API Key
                        </Badge>
                      )}
                      {!cred.username && !cred.hasApiKey && (
                        <span className="text-xs italic">No auth</span>
                      )}
                    </div>
                  </td>

                  {/* Status */}
                  <td className="px-4 py-3">
                    {isTesting ? (
                      <div className="flex items-center gap-1.5 text-muted-foreground">
                        <Loader2 className="w-3.5 h-3.5 animate-spin" />
                        <span className="text-xs">Testing...</span>
                      </div>
                    ) : result ? (
                      <div className="flex items-center gap-1.5">
                        {result.reachable ? (
                          <CheckCircle className="w-3.5 h-3.5 text-[hsl(var(--success))]" />
                        ) : (
                          <XCircle className="w-3.5 h-3.5 text-[hsl(var(--destructive))]" />
                        )}
                        <span className={cn(
                          "text-xs",
                          result.reachable ? "text-[hsl(var(--success))]" : "text-[hsl(var(--destructive))]"
                        )}>
                          {result.reachable ? `${result.statusCode}` : "Unreachable"}
                        </span>
                        {result.reachable && result.responseTimeMs > 0 && (
                          <span className="text-xs text-muted-foreground">
                            {result.responseTimeMs}ms
                          </span>
                        )}
                        <Tooltip>
                          <TooltipTrigger asChild>
                            <Clock className="w-3 h-3 text-muted-foreground cursor-help" />
                          </TooltipTrigger>
                          <TooltipContent>{fmtTestTime(result.testedAt)}</TooltipContent>
                        </Tooltip>
                      </div>
                    ) : (
                      <span className="text-xs text-muted-foreground italic">Not tested</span>
                    )}
                  </td>

                  {/* Actions */}
                  <td className="px-4 py-3 text-right">
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => void handleTest(cred)}
                      disabled={!cred.endpointUrl || isTesting || testingAll}
                    >
                      {isTesting ? (
                        <Loader2 className="w-3.5 h-3.5 animate-spin" />
                      ) : (
                        <Zap className="w-3.5 h-3.5" />
                      )}
                      Test
                    </Button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

      {/* Profile Credentials Section */}
      {hasProfileCreds && (
        <div className="space-y-3">
          <div className="flex items-center gap-2">
            <Key className="w-4 h-4 text-muted-foreground" />
            <h3 className="text-sm font-medium">Profile Credentials</h3>
          </div>
          <div className="border border-[hsl(var(--border))] rounded-lg overflow-hidden bg-[hsl(var(--card))] shadow-sm">
            <table className="w-full text-sm">
              <thead>
                <tr className="border-b border-[hsl(var(--border))] bg-muted/80">
                  <th className="text-left px-4 py-2.5 font-semibold text-foreground/70">System</th>
                  <th className="text-left px-4 py-2.5 font-semibold text-foreground/70">Username</th>
                </tr>
              </thead>
              <tbody>
                {profileCredentials!.sfUsername && (
                  <tr className="border-b last:border-b-0">
                    <td className="px-4 py-3">
                      <Badge variant="outline">Salesforce</Badge>
                    </td>
                    <td className="px-4 py-3 text-muted-foreground">
                      {profileCredentials!.sfUsername}
                    </td>
                  </tr>
                )}
                {profileCredentials!.qbUsername && (
                  <tr className="border-b last:border-b-0">
                    <td className="px-4 py-3">
                      <Badge variant="outline">QuickBooks</Badge>
                    </td>
                    <td className="px-4 py-3 text-muted-foreground">
                      {profileCredentials!.qbUsername}
                    </td>
                  </tr>
                )}
                {profileCredentials!.crmUsername && (
                  <tr className="border-b last:border-b-0">
                    <td className="px-4 py-3">
                      <Badge variant="outline">CRM</Badge>
                    </td>
                    <td className="px-4 py-3 text-muted-foreground">
                      {profileCredentials!.crmUsername}
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
}
