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

/** Response from GET /api/flows */
export interface FlowsResponse {
  success: boolean;
  data?: {
    serverName: string;
    heartbeatInterval: number;
    isHosted: boolean;
    profileName: string;
    scheduledFlows: EngineFlow[];
    utilityFlows: EngineFlow[];
    queryFlows: Array<{
      index: number;
      flowId: string;
      type: "query";
      interval: number;
      counter: number;
    }>;
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
