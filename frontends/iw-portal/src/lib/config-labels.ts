/** Shared label formatters for configuration display — used by both wizard and config pages */

export function envLabel(code: string): string {
  const map: Record<string, string> = {
    Prd: "Production A", Tst: "Production B", Prd1: "Production C",
    Tst1: "Production D", Dev: "Production E", Addtnl: "Additional Server",
    Ddctd: "Dedicated Server",
  };
  return map[code] || code;
}

export function stopLabel(code: string | undefined): string {
  if (!code || code === "None") return "Never";
  if (code === "Con") return "After Connection Failure";
  if (code === "EveryErr") return "After Every Error";
  return code;
}

export function emailLabel(code: string | undefined): string {
  if (!code || code === "None") return "None";
  const map: Record<string, string> = {
    Con: "Connection Failures", EveryErr: "After Every Error",
    DailyEveryErr: "Every Error + Daily Report", ConD: "Connection + Daily Report",
    ConDE: "Connection + Error Report", DailyE: "Error Report Only", Daily: "Full Daily Report",
  };
  return map[code] || code;
}
