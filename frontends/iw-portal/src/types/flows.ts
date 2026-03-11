/** A single integration flow from the ConfigContext engine */
export interface EngineFlow {
  index: number;
  flowId: string;
  type: "scheduled" | "utility" | "query";
  state: string;
  command: string;
  running: boolean;
  executing: boolean;
  isScheduled: boolean;
  interval: number;
  intervalDisplay: string;
  shift: number;
  shiftDisplay: string;
  counter: number;
  successes: number;
  failures: number;
  startTime: string;
  logLevel: number;
}

/** Query flow (subset of EngineFlow with optional HTTP GET URL) */
export interface QueryFlow {
  index: number;
  flowId: string;
  type: "query";
  interval: number;
  counter: number;
  httpGetQuery?: string;
}

/** Response from GET /api/flows */
export interface FlowsResponse {
  success: boolean;
  data?: {
    serverName: string;
    heartbeatInterval: number;
    isHosted: boolean;
    profileName: string;
    solutionType?: string;
    scheduledFlows: EngineFlow[];
    utilityFlows: EngineFlow[];
    queryFlows: QueryFlow[];
    /** Flow IDs from the company's workspace im/config.xml (even if not loaded in engine) */
    configuredFlowIds?: string[];
  };
  error?: string;
}

/** Response from POST /api/flows/start or /stop */
export interface FlowActionResponse {
  success: boolean;
  data?: EngineFlow;
  message?: string;
  error?: string;
}

/** Response from PUT /api/flows/schedule */
export interface FlowScheduleResponse {
  success: boolean;
  data?: EngineFlow;
  error?: string;
}

/** A single variable property of a flow */
export interface FlowProperty {
  name: string;
  value: string;
  type: "text" | "password" | "upload";
}

/** Response from GET /api/flows/properties */
export interface FlowPropertiesResponse {
  success: boolean;
  data?: {
    flowId: string;
    isFlow: boolean;
    description: string;
    running: boolean;
    profileName: string;
    properties: FlowProperty[];
    tsUrls?: Record<string, string>;
  };
  error?: string;
}
