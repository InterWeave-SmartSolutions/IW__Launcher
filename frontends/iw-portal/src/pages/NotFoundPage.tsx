import { useNavigate } from "react-router-dom";

export function NotFoundPage() {
  const navigate = useNavigate();
  return (
    <div className="flex flex-col items-center justify-center min-h-[50vh] gap-4">
      <h1 className="text-4xl font-bold">404</h1>
      <p className="text-muted-foreground">Page not found.</p>
      <button
        onClick={() => navigate("/dashboard")}
        className="px-4 py-2 rounded-[14px] bg-[hsl(var(--primary)/0.18)] border border-[hsl(var(--primary)/0.35)] text-sm font-medium hover:bg-[hsl(var(--primary)/0.25)] transition-colors cursor-pointer"
      >
        Go to Dashboard
      </button>
    </div>
  );
}
