import { Component, type ReactNode } from "react";
import { AlertTriangle, RefreshCw, Home } from "lucide-react";

interface Props {
  children: ReactNode;
}

interface State {
  hasError: boolean;
  error: Error | null;
}

export class ErrorBoundary extends Component<Props, State> {
  state: State = { hasError: false, error: null };

  static getDerivedStateFromError(error: Error): State {
    return { hasError: true, error };
  }

  componentDidCatch(error: Error, info: React.ErrorInfo) {
    console.error("ErrorBoundary caught:", error, info.componentStack);
    // Stale deployment: chunk filenames changed since the user loaded index.html.
    // Auto-reload once to fetch the new index.html and correct chunk names.
    if (error.message.includes("Failed to fetch dynamically imported module") ||
        error.message.includes("Importing a module script failed")) {
      const reloaded = sessionStorage.getItem("chunk_reload");
      if (!reloaded) {
        sessionStorage.setItem("chunk_reload", "1");
        window.location.reload();
      }
    }
  }

  render() {
    if (!this.state.hasError) return this.props.children;

    return (
      <div className="min-h-screen app-background flex items-center justify-center p-6">
        <div className="glass-panel rounded-[var(--radius)] p-8 max-w-lg w-full text-center space-y-4">
          <div className="w-16 h-16 rounded-full bg-[hsl(var(--destructive)/0.1)] flex items-center justify-center mx-auto">
            <AlertTriangle className="w-8 h-8 text-[hsl(var(--destructive))]" />
          </div>
          <div>
            <h1 className="text-xl font-bold">Something went wrong</h1>
            <p className="text-sm text-muted-foreground mt-2">
              The application encountered an unexpected error. Try refreshing or navigate home.
            </p>
          </div>
          {this.state.error && (
            <pre className="text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)] rounded-[10px] p-3 overflow-auto text-left max-h-32">
              {this.state.error.message}
            </pre>
          )}
          <div className="flex items-center justify-center gap-3 pt-2">
            <button
              onClick={() => window.location.reload()}
              className="flex items-center gap-2 px-4 py-2 rounded-[14px] border border-[hsl(var(--border))] text-sm hover:bg-[hsl(var(--muted)/0.3)] transition-colors cursor-pointer"
            >
              <RefreshCw className="w-4 h-4" />
              Refresh
            </button>
            <a
              href="/iw-portal/dashboard"
              className="flex items-center gap-2 px-4 py-2 rounded-[14px] bg-[hsl(var(--primary)/0.18)] border border-[hsl(var(--primary)/0.35)] text-sm font-medium hover:bg-[hsl(var(--primary)/0.25)] transition-colors"
            >
              <Home className="w-4 h-4" />
              Dashboard
            </a>
          </div>
        </div>
      </div>
    );
  }
}
