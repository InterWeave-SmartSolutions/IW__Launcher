import { useLocation, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import type { UserRole } from "@/types/auth";

export type Portal = "operator" | "associate" | "master";

const PORTAL_HOMES: Record<Portal, string> = {
  operator: "/dashboard",
  associate: "/associate/home",
  master: "/master/dashboard",
};

const PORTAL_LABELS: Record<Portal, string> = {
  operator: "Integration",
  associate: "Associate",
  master: "Master Console",
};

const PORTAL_SUBTITLES: Record<Portal, string> = {
  operator: "Integration Platform",
  associate: "Associate Portal",
  master: "Master Console",
};

const ALL_PORTALS: Portal[] = ["operator", "associate", "master"];
const VISIBILITY_KEY = "iw-portal-visible-portals";

/** Derives the active portal from the current URL. */
export function usePortal(): Portal {
  const { pathname } = useLocation();
  if (pathname.startsWith("/associate")) return "associate";
  if (pathname.startsWith("/master")) return "master";
  return "operator";
}

/** Navigate to a different portal's home page. */
export function usePortalSwitch() {
  const navigate = useNavigate();
  return (portal: Portal) => navigate(PORTAL_HOMES[portal]);
}

/** localStorage-backed portal visibility preferences. */
export function usePortalVisibility() {
  const getStored = (): Portal[] => {
    try {
      const raw = localStorage.getItem(VISIBILITY_KEY);
      if (raw) {
        const parsed = JSON.parse(raw) as Portal[];
        if (Array.isArray(parsed) && parsed.length > 0) return parsed;
      }
    } catch {}
    return ALL_PORTALS;
  };

  const [visible, setVisible] = useState<Portal[]>(getStored);

  useEffect(() => {
    try {
      localStorage.setItem(VISIBILITY_KEY, JSON.stringify(visible));
    } catch {}
  }, [visible]);

  const toggle = (portal: Portal) => {
    setVisible((prev) =>
      prev.includes(portal)
        ? prev.length > 1 ? prev.filter((p) => p !== portal) : prev // always keep at least one
        : [...prev, portal]
    );
  };

  return { visible, toggle, all: ALL_PORTALS };
}

/** Returns portals a role is allowed to access. Admin gets all. */
function getAllowedPortals(role: UserRole): Portal[] {
  switch (role) {
    case "admin":
      return ALL_PORTALS;
    case "associate":
      return ["associate"];
    case "operator":
    default:
      return ["operator"];
  }
}

/** Returns the default home path for a given role. */
function getRoleHome(role: UserRole): string {
  const portals = getAllowedPortals(role);
  return PORTAL_HOMES[portals[0] ?? "operator"];
}

export { PORTAL_HOMES, PORTAL_LABELS, PORTAL_SUBTITLES, ALL_PORTALS, getAllowedPortals, getRoleHome };
