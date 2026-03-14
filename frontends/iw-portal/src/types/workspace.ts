/** Summary of a workspace integration project (from GET /api/workspace/projects) */
export interface WorkspaceProject {
  name: string;
  transactionCount: number;
  queryCount: number;
  xsltCount: number;
  compiledClassCount: number;
  profileCount: number;
  solutionType: string;
  lastModified: string;
}

/** Response from GET /api/workspace/projects */
export interface WorkspaceProjectsResponse {
  success: boolean;
  workspacePath?: string;
  projects?: WorkspaceProject[];
  error?: string;
}

/** A parameter attached to a transaction or query */
export interface WorkspaceParameter {
  Name: string;
  Value: string;
  [key: string]: string;
}

/** A transaction definition from config.xml */
export interface WorkspaceTransaction {
  Id: string;
  Description: string;
  Solution?: string;
  Interval?: string;
  Shift?: string;
  NextTransaction?: string;
  parameters?: WorkspaceParameter[];
  [key: string]: string | WorkspaceParameter[] | undefined;
}

/** A query definition from config.xml */
export interface WorkspaceQuery {
  Id: string;
  Description?: string;
  Solution?: string;
  HttpGetQuery?: string;
  parameters?: WorkspaceParameter[];
  [key: string]: string | WorkspaceParameter[] | undefined;
}

/** A runtime profile file */
export interface WorkspaceRuntimeProfile {
  name: string;
  lastModified: string;
  sizeBytes: number;
}

/** Engine configuration attributes from config.xml root element */
export interface WorkspaceEngineConfig {
  ServerName?: string;
  Hosted?: string;
  TransformationServerUrl?: string;
  HeartBeatInterval?: string;
  [key: string]: string | undefined;
}

/** Full project details (from GET /api/workspace/projects/{name}) */
export interface WorkspaceProjectDetail {
  name: string;
  path: string;
  lastModified: string;
  engineConfig: WorkspaceEngineConfig;
  transactions: WorkspaceTransaction[];
  queries: WorkspaceQuery[];
  xsltFiles: string[];
  compiledClasses: string[];
  runtimeProfiles: WorkspaceRuntimeProfile[];
  hasDataConnections: boolean;
  dataConnectionsLastModified?: string;
  configError?: string;
}

/** Response from GET /api/workspace/projects/{name} */
export interface WorkspaceProjectDetailResponse {
  success: boolean;
  project?: WorkspaceProjectDetail;
  error?: string;
}
