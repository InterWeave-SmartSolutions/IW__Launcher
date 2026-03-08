import { useState } from "react";

interface OnboardingOverlayProps {
  show: boolean;
  onDismiss: () => void;
}

const STORAGE_KEY = "iw-onboarding-integrations-seen";

const steps = [
  {
    title: "Integration Health",
    description:
      "The KPI cards show active flows, running count, success rate, and failures at a glance.",
  },
  {
    title: "Flow History",
    description:
      "The Flows tab shows all integration flows derived from transaction history with success rates.",
  },
  {
    title: "Engine Controls",
    description:
      "Switch to the Engine Controls tab to start/stop flows and manage schedules. Requires classic portal login first.",
  },
  {
    title: "System Credentials",
    description:
      "The Credentials tab shows your connected systems. Test connectivity to verify endpoints are reachable.",
  },
  {
    title: "Architecture View",
    description:
      "The dependency map shows which external systems your flows connect to and their health status.",
  },
];

export function OnboardingOverlay({ show, onDismiss }: OnboardingOverlayProps) {
  const [step, setStep] = useState(0);

  if (!show) return null;

  // Already seen — don't render
  if (typeof window !== "undefined" && localStorage.getItem(STORAGE_KEY) === "true") {
    return null;
  }

  function complete() {
    localStorage.setItem(STORAGE_KEY, "true");
    onDismiss();
  }

  function next() {
    if (step < steps.length - 1) {
      setStep(step + 1);
    } else {
      complete();
    }
  }

  function back() {
    if (step > 0) {
      setStep(step - 1);
    }
  }

  const current = steps[step]!;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      {/* Backdrop */}
      <div
        className="absolute inset-0 bg-black/60 backdrop-blur-sm"
        onClick={complete}
      />

      {/* Card */}
      <div className="relative glass-panel rounded-[var(--radius)] p-6 max-w-sm w-full mx-4 shadow-xl border border-border/50">
        {/* Step indicator */}
        <div className="flex items-center gap-1 mb-4">
          <span className="text-[10px] font-medium text-muted-foreground uppercase tracking-wider">
            Step {step + 1} of {steps.length}
          </span>
        </div>

        {/* Title */}
        <h3 className="text-base font-semibold text-foreground mb-2">
          {current.title}
        </h3>

        {/* Description */}
        <p className="text-sm text-muted-foreground leading-relaxed mb-6">
          {current.description}
        </p>

        {/* Progress dots */}
        <div className="flex items-center justify-center gap-1.5 mb-4">
          {steps.map((_, i) => (
            <div
              key={i}
              className={`h-1.5 rounded-full transition-all duration-200 ${
                i === step
                  ? "w-4 bg-primary"
                  : i < step
                    ? "w-1.5 bg-primary/50"
                    : "w-1.5 bg-muted-foreground/30"
              }`}
            />
          ))}
        </div>

        {/* Buttons */}
        <div className="flex items-center justify-between">
          <button
            onClick={complete}
            className="text-xs text-muted-foreground hover:text-foreground transition-colors"
          >
            Skip tour
          </button>
          <div className="flex items-center gap-2">
            {step > 0 && (
              <button
                onClick={back}
                className="px-3 py-1.5 text-xs font-medium text-muted-foreground hover:text-foreground border border-border rounded-[var(--radius)] transition-colors"
              >
                Back
              </button>
            )}
            <button
              onClick={next}
              className="px-3 py-1.5 text-xs font-medium text-primary-foreground bg-primary hover:bg-primary/90 rounded-[var(--radius)] transition-colors"
            >
              {step < steps.length - 1 ? "Next" : "Got it"}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
