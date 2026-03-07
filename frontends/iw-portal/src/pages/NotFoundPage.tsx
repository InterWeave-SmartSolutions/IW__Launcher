import { useNavigate } from "react-router-dom";
import { Home, ArrowLeft } from "lucide-react";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";

export function NotFoundPage() {
  useDocumentTitle("Page Not Found");
  const navigate = useNavigate();
  return (
    <div className="flex flex-col items-center justify-center min-h-[50vh] gap-6 text-center">
      <div className="space-y-2">
        <h1 className="text-6xl font-bold text-muted-foreground/30">404</h1>
        <p className="text-lg font-semibold">Page not found</p>
        <p className="text-sm text-muted-foreground max-w-sm">
          The page you're looking for doesn't exist or has been moved.
          Try the search bar or navigate from the sidebar.
        </p>
      </div>
      <div className="flex items-center gap-3">
        <button
          onClick={() => navigate(-1)}
          className="flex items-center gap-2 px-4 py-2 rounded-[14px] border border-[hsl(var(--border))] text-sm hover:bg-[hsl(var(--muted)/0.3)] transition-colors cursor-pointer"
        >
          <ArrowLeft className="w-4 h-4" />
          Go back
        </button>
        <button
          onClick={() => navigate("/dashboard")}
          className="flex items-center gap-2 px-4 py-2 rounded-[14px] bg-[hsl(var(--primary)/0.18)] border border-[hsl(var(--primary)/0.35)] text-sm font-medium hover:bg-[hsl(var(--primary)/0.25)] transition-colors cursor-pointer"
        >
          <Home className="w-4 h-4" />
          Dashboard
        </button>
      </div>
    </div>
  );
}
