export interface AuditEntry {
  id: number;
  userId: number | null;
  userEmail: string;
  actionType: string;
  actionDetail: string;
  resourceType: string | null;
  resourceId: string | null;
  ipAddress: string | null;
  createdAt: string;
}

export interface AuditLogResponse {
  success: boolean;
  entries: AuditEntry[];
  totalCount: number;
  page: number;
  pageSize: number;
}

export interface AuditStatsResponse {
  success: boolean;
  stats: {
    totalEvents: number;
    byActionType: Record<string, number>;
    byDay: { date: string; count: number }[];
  };
}

export interface AuditUsersResponse {
  success: boolean;
  users: { id: number; email: string; name: string }[];
}
