import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  Building2,
  Loader2,
  ArrowRight,
  ArrowLeft,
  Eye,
  EyeOff,
  CheckCircle2,
} from "lucide-react";
import { apiFetch } from "@/lib/api";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Separator } from "@/components/ui/separator";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

/**
 * Solution types from CompanyRegistration.jsp — grouped by CRM source.
 * Each entry: [code, label]
 */
const SOLUTION_GROUPS: { crm: string; fs: string; options: [string, string][] }[] = [
  {
    crm: "Salesforce",
    fs: "QuickBooks",
    options: [
      ["SF2QB3", "Salesforce → QuickBooks (Tier 3 — Full)"],
      ["SF2QB", "Salesforce → QuickBooks (Tier 2)"],
      ["SF2QB1", "Salesforce → QuickBooks (Tier 1 — Basic)"],
    ],
  },
  {
    crm: "Creatio (CRM)",
    fs: "QuickBooks",
    options: [
      ["CRM2QB3", "Creatio → QuickBooks (Tier 3 — Full)"],
      ["CRM2QB", "Creatio → QuickBooks (Tier 2)"],
      ["CRM2QB1", "Creatio → QuickBooks (Tier 1 — Basic)"],
    ],
  },
  {
    crm: "Salesforce",
    fs: "NetSuite",
    options: [
      ["SF2NS3", "Salesforce → NetSuite (Tier 3 — Full)"],
      ["SF2NS", "Salesforce → NetSuite (Tier 2)"],
      ["SF2NS1", "Salesforce → NetSuite (Tier 1 — Basic)"],
    ],
  },
  {
    crm: "Creatio (CRM)",
    fs: "NetSuite",
    options: [["CRM2NS", "Creatio → NetSuite"]],
  },
  {
    crm: "Salesforce",
    fs: "Authorize.Net",
    options: [["SF2AUTH", "Salesforce → Authorize.Net"]],
  },
  {
    crm: "Creatio (CRM)",
    fs: "Payment Gateway",
    options: [
      ["CRM2PGG", "Creatio → Payment Gateway"],
      ["CRM2PT3", "Creatio → Payment (Tier 3)"],
      ["CRM2PT", "Creatio → Payment (Tier 2)"],
      ["CRM2PT1", "Creatio → Payment (Tier 1)"],
    ],
  },
  {
    crm: "Salesforce",
    fs: "Other",
    options: [
      ["SF2CMS", "Salesforce → CMS"],
      ["SF2DBG", "Salesforce → Database Gateway"],
      ["SF2PGG", "Salesforce → Payment Gateway"],
    ],
  },
  {
    crm: "Creatio (CRM)",
    fs: "Other",
    options: [
      ["CRM2DBG", "Creatio → Database Gateway"],
      ["CRM2EGG", "Creatio → E-Commerce Gateway"],
      ["CRM2OMC", "Creatio → OMS Connector"],
    ],
  },
  {
    crm: "MS Dynamics",
    fs: "QuickBooks",
    options: [["MSDCRM2QB", "MS Dynamics CRM → QuickBooks"]],
  },
  {
    crm: "SugarCRM",
    fs: "QuickBooks",
    options: [
      ["SUG2QB3", "Sugar → QuickBooks (Tier 3)"],
      ["SUG2QB", "Sugar → QuickBooks (Tier 2)"],
      ["SUG2QB1", "Sugar → QuickBooks (Tier 1)"],
    ],
  },
  {
    crm: "Other Sources",
    fs: "Various",
    options: [
      ["ORA2QB", "Oracle → QuickBooks"],
      ["OMS2QB", "OMS → QuickBooks"],
      ["DB2QBG", "Database → QuickBooks Gateway"],
      ["AR2QB", "Aria → QuickBooks"],
      ["AR2NS", "Aria → NetSuite"],
    ],
  },
];

interface CompanyForm {
  companyName: string;
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  confirmPassword: string;
  solutionType: string;
}

export function CompanyRegisterPage() {
  useDocumentTitle("Register Company");
  const navigate = useNavigate();
  const [form, setForm] = useState<CompanyForm>({
    companyName: "",
    email: "",
    firstName: "",
    lastName: "",
    password: "",
    confirmPassword: "",
    solutionType: "CRM2QB3",
  });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const set = (field: keyof CompanyForm) => (
    e: React.ChangeEvent<HTMLInputElement>
  ) => setForm((prev) => ({ ...prev, [field]: e.target.value }));

  const validate = (): string | null => {
    if (!form.companyName.trim()) return "Company name is required.";
    if (!form.email.trim()) return "Email is required.";
    if (!form.firstName.trim()) return "First name is required.";
    if (!form.lastName.trim()) return "Last name is required.";
    if (!form.password) return "Password is required.";
    if (form.password.length < 4) return "Password must be at least 4 characters.";
    if (form.password !== form.confirmPassword) return "Passwords do not match.";
    if (!form.solutionType) return "Please select a solution type.";
    return null;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const err = validate();
    if (err) {
      setError(err);
      return;
    }
    setLoading(true);
    setError("");
    try {
      await apiFetch("/api/register/company", {
        method: "POST",
        body: JSON.stringify({
          companyName: form.companyName.trim(),
          email: form.email.trim(),
          firstName: form.firstName.trim(),
          lastName: form.lastName.trim(),
          password: form.password,
          confirmPassword: form.confirmPassword,
          solutionType: form.solutionType,
        }),
      });
      setSuccess(true);
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Registration failed. Please try again."
      );
    } finally {
      setLoading(false);
    }
  };

  if (success) {
    return (
      <div className="app-background min-h-screen flex items-center justify-center p-4">
        <div className="glass-panel rounded-[var(--radius)] p-6 max-w-md w-full text-center">
          <CheckCircle2 className="w-12 h-12 text-[hsl(var(--success))] mx-auto mb-4" />
          <h2 className="text-xl font-bold">Company Registered</h2>
          <p className="text-sm text-muted-foreground mt-2">
            Your company and administrator account have been created.
            Sign in to begin configuring your integrations.
          </p>
          <Button onClick={() => navigate("/login")} className="mt-6 mx-auto">
            Continue to Sign In <ArrowRight className="w-4 h-4" />
          </Button>
        </div>
      </div>
    );
  }

  return (
    <div className="app-background min-h-screen flex items-center justify-center p-4 sm:p-6">
      <div className="glass-panel rounded-[var(--radius)] p-6 max-w-lg w-full">
        {/* Header */}
        <div className="flex items-center gap-3 mb-5">
          <div className="w-10 h-10 rounded-[14px] bg-[hsl(var(--success)/0.15)] border border-[hsl(var(--success)/0.3)] grid place-items-center">
            <Building2 className="w-5 h-5 text-[hsl(var(--success))]" />
          </div>
          <div>
            <h1 className="text-lg font-bold">Register your company</h1>
            <p className="text-xs text-muted-foreground">
              Create a new organization and admin account
            </p>
          </div>
        </div>

        <form onSubmit={handleSubmit} className="space-y-3.5">
          {/* Company name */}
          <div>
            <Label htmlFor="creg-company" className="mb-1.5">
              Company / Organization Name *
            </Label>
            <Input
              id="creg-company"
              value={form.companyName}
              onChange={set("companyName")}
              maxLength={255}
              placeholder="Your organization name"
              autoFocus
            />
          </div>

          {/* Solution type */}
          <div>
            <Label className="mb-1.5">
              Integration Solution *
            </Label>
            <Select
              value={form.solutionType}
              onValueChange={(val) => setForm((prev) => ({ ...prev, solutionType: val }))}
            >
              <SelectTrigger>
                <SelectValue placeholder="Select a solution" />
              </SelectTrigger>
              <SelectContent>
                {SOLUTION_GROUPS.map((group) => (
                  <SelectGroup key={`${group.crm}-${group.fs}`}>
                    <SelectLabel>{group.crm} → {group.fs}</SelectLabel>
                    {group.options.map(([code, label]) => (
                      <SelectItem key={code} value={code}>
                        {label}
                      </SelectItem>
                    ))}
                  </SelectGroup>
                ))}
              </SelectContent>
            </Select>
            <p className="text-[11px] text-muted-foreground mt-1">
              Determines which CRM and financial system objects you can sync
            </p>
          </div>

          <Separator />

          <p className="text-xs font-medium text-muted-foreground">
            Administrator account
          </p>

          {/* Name row */}
          <div className="grid grid-cols-2 gap-3">
            <div>
              <Label htmlFor="creg-first" className="mb-1.5">
                First Name *
              </Label>
              <Input
                id="creg-first"
                value={form.firstName}
                onChange={set("firstName")}
                maxLength={45}
                placeholder="First"
              />
            </div>
            <div>
              <Label htmlFor="creg-last" className="mb-1.5">
                Last Name *
              </Label>
              <Input
                id="creg-last"
                value={form.lastName}
                onChange={set("lastName")}
                maxLength={45}
                placeholder="Last"
              />
            </div>
          </div>

          {/* Email */}
          <div>
            <Label htmlFor="creg-email" className="mb-1.5">
              Admin Email *
            </Label>
            <Input
              id="creg-email"
              type="email"
              value={form.email}
              onChange={set("email")}
              maxLength={255}
              placeholder="admin@company.com"
              autoComplete="email"
            />
          </div>

          {/* Password row */}
          <div className="grid grid-cols-2 gap-3">
            <div>
              <Label htmlFor="creg-pass" className="mb-1.5">
                Password *
              </Label>
              <div className="relative">
                <Input
                  id="creg-pass"
                  type={showPassword ? "text" : "password"}
                  value={form.password}
                  onChange={set("password")}
                  maxLength={255}
                  className="pr-10"
                  placeholder="Min. 4 characters"
                  autoComplete="new-password"
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground cursor-pointer"
                  tabIndex={-1}
                >
                  {showPassword ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
                </button>
              </div>
            </div>
            <div>
              <Label htmlFor="creg-confirm" className="mb-1.5">
                Confirm Password *
              </Label>
              <Input
                id="creg-confirm"
                type={showPassword ? "text" : "password"}
                value={form.confirmPassword}
                onChange={set("confirmPassword")}
                maxLength={255}
                placeholder="Repeat password"
                autoComplete="new-password"
              />
            </div>
          </div>

          {/* Error */}
          {error && (
            <div className="text-xs text-[hsl(var(--destructive))] bg-[hsl(var(--destructive)/0.08)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2">
              {error}
            </div>
          )}

          {/* Submit */}
          <Button type="submit" disabled={loading} className="w-full">
            {loading ? (
              <Loader2 className="w-4 h-4 animate-spin" />
            ) : (
              <Building2 className="w-4 h-4" />
            )}
            {loading ? "Registering company..." : "Register Company"}
          </Button>
        </form>

        {/* Footer links */}
        <div className="mt-5 pt-4 border-t border-[hsl(var(--border))] flex items-center justify-between">
          <Link
            to="/login"
            className="flex items-center gap-1.5 text-xs text-muted-foreground hover:text-foreground transition-colors"
          >
            <ArrowLeft className="w-3.5 h-3.5" /> Back to sign in
          </Link>
          <Link
            to="/register"
            className="text-xs text-[hsl(var(--primary))] hover:underline"
          >
            Join existing company →
          </Link>
        </div>
      </div>
    </div>
  );
}
