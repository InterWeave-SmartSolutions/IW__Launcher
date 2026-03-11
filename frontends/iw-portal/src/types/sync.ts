export type SyncStatus = "in_sync" | "db_ahead" | "workspace_ahead" | "not_synced";

export interface ProfileSyncState {
  profileName: string;
  solutionType: string;
  dbUpdatedAt: string | null;
  workspaceXmlExists: boolean;
  /** Timestamp of the last successful portal → IDE push (from .push_epoch sidecar). */
  workspaceXmlModified: string | null;
  generatedProfileExists: boolean;
  generatedProfileModified: string | null;
  syncStatus: SyncStatus;
}

export interface SyncStatusResponse {
  success: boolean;
  data: {
    bridgeRunning: boolean;
    profiles: ProfileSyncState[];
  };
}

export interface SyncLogResponse {
  success: boolean;
  data: {
    logExists: boolean;
    totalLines: number;
    lines: string[];
  };
}

export interface SyncActionResponse {
  success: boolean;
  data?: {
    pushed?: number;
    failed?: number;
    warnings?: string;
    profileName?: string;
    solutionType?: string;
    sourcePath?: string;
  };
  error?: string;
}
