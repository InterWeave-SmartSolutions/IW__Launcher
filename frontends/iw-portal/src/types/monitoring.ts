export interface DashboardData {
  totalTransactions: number;
  successCount: number;
  failureCount: number;
  avgDuration: number;
  successRate: number;
  activeAlerts: number;
  recentTransactions: Transaction[];
  hourlyMetrics: HourlyMetric[];
}

export interface Transaction {
  executionId: string;
  flowName: string;
  status: "running" | "completed" | "failed" | "timeout";
  startedAt: string;
  completedAt: string | null;
  duration: number | null;
  recordsProcessed: number;
  recordsFailed: number;
  companyId: number;
  errorMessage: string | null;
}

export interface HourlyMetric {
  hour: string;
  totalExecutions: number;
  successCount: number;
  failureCount: number;
  avgDuration: number;
}

export interface AlertRule {
  id: number;
  name: string;
  conditionType: string;
  thresholdValue: number;
  severity: "info" | "warning" | "critical";
  isEnabled: boolean;
  lastTriggeredAt: string | null;
}

export interface MetricsData {
  period: string;
  metrics: HourlyMetric[];
}
