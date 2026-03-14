import { useState } from "react";
import { Link } from "react-router-dom";
import {
  Database,
  Plus,
  ArrowLeft,
  Loader2,
  AlertTriangle,
  RefreshCw,
  Zap,
} from "lucide-react";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { useToast } from "@/providers/ToastProvider";
import {
  useConnections,
  saveTestResult,
  type ConnectionTestResult,
} from "@/hooks/useConnections";
import { Button } from "@/components/ui/button";
import { ConnectionCard } from "@/components/connections/ConnectionCard";
import { ConnectionDialog, type ConnectionFormData } from "@/components/connections/ConnectionDialog";
import { useSaveCredential, useTestCredential } from "@/hooks/useConfiguration";

export function ConnectionManagerPage() {
  useDocumentTitle("Connections");
  const { connections, isLoading, error, refetch } = useConnections();
  const saveCredential = useSaveCredential();
  const testCredential = useTestCredential();
  const { showToast } = useToast();

  const [dialogOpen, setDialogOpen] = useState(false);
  const [editingCredential, setEditingCredential] = useState<typeof connections[0]["credential"] | null>(null);
  const [testingId, setTestingId] = useState<string | null>(null);
  const [testingAll, setTestingAll] = useState(false);

  const handleTest = async (connection: typeof connections[0]) => {
    const cred = connection.credential;
    if (!cred.endpointUrl) return;

    setTestingId(cred.credentialType);
    try {
      const resp = await testCredential.mutateAsync({
        credentialType: cred.credentialType,
        endpointUrl: cred.endpointUrl,
      });
      const result: ConnectionTestResult = {
        reachable: resp.reachable,
        statusCode: resp.statusCode ?? 0,
        responseTimeMs: resp.responseTimeMs ?? 0,
        testedAt: new Date().toISOString(),
      };
      saveTestResult(cred.credentialType, result);
      refetch();
      showToast(
        resp.reachable
          ? `${cred.credentialName}: reachable (${result.responseTimeMs}ms)`
          : `${cred.credentialName}: unreachable`,
        resp.reachable ? "success" : "error"
      );
    } catch (e) {
      const result: ConnectionTestResult = {
        reachable: false,
        statusCode: 0,
        responseTimeMs: 0,
        testedAt: new Date().toISOString(),
      };
      saveTestResult(cred.credentialType, result);
      refetch();
      showToast(e instanceof Error ? e.message : "Test failed", "error");
    } finally {
      setTestingId(null);
    }
  };

  const handleTestAll = async () => {
    const testable = connections.filter((c) => c.credential.endpointUrl);
    if (testable.length === 0) return;

    setTestingAll(true);
    let passed = 0;
    let failed = 0;

    for (const conn of testable) {
      setTestingId(conn.credential.credentialType);
      try {
        const resp = await testCredential.mutateAsync({
          credentialType: conn.credential.credentialType,
          endpointUrl: conn.credential.endpointUrl,
        });
        const result: ConnectionTestResult = {
          reachable: resp.reachable,
          statusCode: resp.statusCode ?? 0,
          responseTimeMs: resp.responseTimeMs ?? 0,
          testedAt: new Date().toISOString(),
        };
        saveTestResult(conn.credential.credentialType, result);
        if (resp.reachable) passed++;
        else failed++;
      } catch {
        const result: ConnectionTestResult = {
          reachable: false,
          statusCode: 0,
          responseTimeMs: 0,
          testedAt: new Date().toISOString(),
        };
        saveTestResult(conn.credential.credentialType, result);
        failed++;
      }
    }

    setTestingId(null);
    setTestingAll(false);
    refetch();
    showToast(
      `Test complete: ${passed} reachable, ${failed} unreachable`,
      passed > 0 && failed === 0 ? "success" : "error"
    );
  };

  const handleEdit = (cred: typeof connections[0]["credential"]) => {
    setEditingCredential(cred);
    setDialogOpen(true);
  };

  const handleAdd = () => {
    setEditingCredential(null);
    setDialogOpen(true);
  };

  const handleSave = async (formData: ConnectionFormData) => {
    try {
      await saveCredential.mutateAsync({
        credentialType: formData.credentialType,
        credentialName: formData.credentialName,
        endpointUrl: formData.endpointUrl,
        username: formData.username,
        password: formData.password || undefined,
        apiKey: formData.apiKey || undefined,
      });
      showToast(
        editingCredential ? "Connection updated" : "Connection added",
        "success"
      );
      setDialogOpen(false);
      refetch();
    } catch (e) {
      showToast(e instanceof Error ? e.message : "Save failed", "error");
    }
  };

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="w-8 h-8 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (error) {
    return (
      <div className="glass-panel rounded-2xl p-6 border border-[hsl(var(--destructive)/0.3)]">
        <div className="flex items-center gap-2 text-[hsl(var(--destructive))]">
          <AlertTriangle className="w-5 h-5" />
          <span className="font-medium">Failed to load connections</span>
        </div>
        <p className="text-sm text-muted-foreground mt-2">
          {error instanceof Error ? error.message : "Unknown error"}
        </p>
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-5xl">
      {/* Breadcrumb */}
      <div className="flex items-center gap-2 text-sm text-muted-foreground">
        <Link to="/company" className="hover:text-foreground transition-colors">
          <ArrowLeft className="w-4 h-4 inline mr-1" />
          Company
        </Link>
        <span>/</span>
        <span className="text-foreground">Connections</span>
      </div>

      {/* Header */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-2xl font-semibold">Connection Manager</h1>
          <p className="text-sm text-muted-foreground mt-1">
            Manage endpoint connections and credentials for each integrated system.
          </p>
        </div>
        <div className="flex items-center gap-2">
          <Button
            variant="outline"
            size="sm"
            onClick={() => void handleTestAll()}
            disabled={testingAll || connections.every((c) => !c.credential.endpointUrl)}
          >
            {testingAll ? (
              <Loader2 className="w-3.5 h-3.5 animate-spin" />
            ) : (
              <Zap className="w-3.5 h-3.5" />
            )}
            Test All
          </Button>
          <Button
            variant="outline"
            size="sm"
            onClick={() => refetch()}
          >
            <RefreshCw className="w-3.5 h-3.5" />
            Refresh
          </Button>
          <Button size="sm" onClick={handleAdd}>
            <Plus className="w-3.5 h-3.5" />
            Add Connection
          </Button>
        </div>
      </div>

      {/* Connection grid */}
      {connections.length === 0 ? (
        <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-12 text-center">
          <Database className="w-12 h-12 text-muted-foreground mx-auto mb-4" />
          <h3 className="text-lg font-medium mb-1">No Connections Configured</h3>
          <p className="text-sm text-muted-foreground mb-4 max-w-md mx-auto">
            Add your first system connection to start configuring integration endpoints.
          </p>
          <Button onClick={handleAdd}>
            <Plus className="w-4 h-4" />
            Add Connection
          </Button>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {connections.map((conn) => (
            <ConnectionCard
              key={conn.credential.id}
              connection={conn}
              onTest={() => void handleTest(conn)}
              onEdit={() => handleEdit(conn.credential)}
              isTesting={testingId === conn.credential.credentialType}
            />
          ))}
        </div>
      )}

      {/* Connection dialog */}
      <ConnectionDialog
        open={dialogOpen}
        onClose={() => setDialogOpen(false)}
        onSave={handleSave}
        credential={editingCredential}
        isSaving={saveCredential.isPending}
      />
    </div>
  );
}
