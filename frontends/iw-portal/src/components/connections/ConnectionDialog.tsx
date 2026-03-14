import { useState, useEffect } from "react";
import { Loader2, Save } from "lucide-react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import type { CompanyCredential } from "@/types/configuration";

/** System type options for the dropdown */
const SYSTEM_TYPES = [
  { value: "salesforce", label: "Salesforce" },
  { value: "quickbooks", label: "QuickBooks" },
  { value: "creatio", label: "Creatio CRM" },
  { value: "magento", label: "Magento 2" },
  { value: "authorizenet", label: "Authorize.Net" },
  { value: "netsuite", label: "NetSuite" },
  { value: "sage", label: "Sage" },
  { value: "custom", label: "Custom" },
];

interface ConnectionDialogProps {
  open: boolean;
  onClose: () => void;
  onSave: (data: ConnectionFormData) => Promise<void>;
  /** If editing, the existing credential to pre-populate */
  credential?: CompanyCredential | null;
  isSaving: boolean;
}

export interface ConnectionFormData {
  credentialType: string;
  credentialName: string;
  endpointUrl: string;
  username: string;
  password: string;
  apiKey: string;
}

export function ConnectionDialog({ open, onClose, onSave, credential, isSaving }: ConnectionDialogProps) {
  const [formData, setFormData] = useState<ConnectionFormData>({
    credentialType: "",
    credentialName: "",
    endpointUrl: "",
    username: "",
    password: "",
    apiKey: "",
  });

  const isEdit = !!credential;

  // Pre-populate form when editing
  useEffect(() => {
    if (credential) {
      setFormData({
        credentialType: credential.credentialType,
        credentialName: credential.credentialName,
        endpointUrl: credential.endpointUrl || "",
        username: credential.username || "",
        password: "",
        apiKey: "",
      });
    } else {
      setFormData({
        credentialType: "",
        credentialName: "",
        endpointUrl: "",
        username: "",
        password: "",
        apiKey: "",
      });
    }
  }, [credential, open]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSave(formData);
  };

  const update = (field: keyof ConnectionFormData, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
  };

  return (
    <Dialog open={open} onOpenChange={(o) => !o && onClose()}>
      <DialogContent className="sm:max-w-[480px]">
        <DialogHeader>
          <DialogTitle>{isEdit ? "Edit Connection" : "Add Connection"}</DialogTitle>
          <DialogDescription>
            {isEdit
              ? `Update credentials for ${credential?.credentialName || "this connection"}.`
              : "Configure a new system connection with endpoint and authentication details."}
          </DialogDescription>
        </DialogHeader>

        <form onSubmit={handleSubmit} className="space-y-4 mt-2">
          {/* System Type */}
          <div className="space-y-1.5">
            <Label htmlFor="conn-type">System Type</Label>
            <Select
              value={formData.credentialType}
              onValueChange={(v) => {
                update("credentialType", v);
                if (!formData.credentialName) {
                  const label = SYSTEM_TYPES.find((s) => s.value === v)?.label ?? v;
                  update("credentialName", label);
                }
              }}
              disabled={isEdit}
            >
              <SelectTrigger id="conn-type">
                <SelectValue placeholder="Select system type" />
              </SelectTrigger>
              <SelectContent>
                {SYSTEM_TYPES.map((s) => (
                  <SelectItem key={s.value} value={s.value}>{s.label}</SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          {/* Connection Name */}
          <div className="space-y-1.5">
            <Label htmlFor="conn-name">Connection Name</Label>
            <Input
              id="conn-name"
              value={formData.credentialName}
              onChange={(e) => update("credentialName", e.target.value)}
              placeholder="e.g. Production Salesforce"
              required
            />
          </div>

          {/* Endpoint URL */}
          <div className="space-y-1.5">
            <Label htmlFor="conn-url">Endpoint URL</Label>
            <Input
              id="conn-url"
              type="url"
              value={formData.endpointUrl}
              onChange={(e) => update("endpointUrl", e.target.value)}
              placeholder="https://api.example.com"
            />
          </div>

          {/* Username */}
          <div className="space-y-1.5">
            <Label htmlFor="conn-user">Username</Label>
            <Input
              id="conn-user"
              value={formData.username}
              onChange={(e) => update("username", e.target.value)}
              placeholder="API username or email"
            />
          </div>

          {/* Password */}
          <div className="space-y-1.5">
            <Label htmlFor="conn-pass">
              Password {isEdit && <span className="text-muted-foreground">(leave blank to keep current)</span>}
            </Label>
            <Input
              id="conn-pass"
              type="password"
              value={formData.password}
              onChange={(e) => update("password", e.target.value)}
              placeholder={isEdit ? "Leave blank to keep current" : "Password or security token"}
            />
          </div>

          {/* API Key */}
          <div className="space-y-1.5">
            <Label htmlFor="conn-apikey">
              API Key <span className="text-muted-foreground">(optional)</span>
            </Label>
            <Input
              id="conn-apikey"
              type="password"
              value={formData.apiKey}
              onChange={(e) => update("apiKey", e.target.value)}
              placeholder="API key or secret"
            />
          </div>

          {/* Actions */}
          <div className="flex justify-end gap-2 pt-2">
            <Button type="button" variant="outline" onClick={onClose} disabled={isSaving}>
              Cancel
            </Button>
            <Button type="submit" disabled={isSaving || !formData.credentialType}>
              {isSaving ? (
                <Loader2 className="w-4 h-4 animate-spin" />
              ) : (
                <Save className="w-4 h-4" />
              )}
              {isEdit ? "Update" : "Add Connection"}
            </Button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
}
