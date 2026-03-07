/** Matches GET /api/monitoring/dashboard JSON response */
export interface DashboardResponse {
  success: boolean;
  data: {
    summary: DashboardSummary;
    running_transactions: RunningTransaction[];
    recent_activity: {
      last_hour: { total: number; success: number; failed: number };
    };
  };
}

export interface DashboardSummary {
  running_count: number;
  completed_count_24h: number;
  failed_count_24h: number;
  total_count_24h: number;
  success_rate_24h: number;
  completed_count_7d: number;
  failed_count_7d: number;
  total_count_7d: number;
  success_rate_7d: number;
  avg_duration_ms_24h: number;
  last_updated: string;
}

export interface RunningTransaction {
  execution_id: string;
  flow_name: string;
  started_at: string;
  duration_ms: number;
  records_processed: number;
}

/** Matches GET /api/monitoring/transactions JSON response */
export interface TransactionsResponse {
  success: boolean;
  data: {
    transactions: Transaction[];
    pagination: {
      page: number;
      page_size: number;
      total_count: number;
      total_pages: number;
    };
  };
}

export interface Transaction {
  execution_id: string;
  flow_name: string;
  status: "running" | "success" | "failed" | "timeout";
  started_at: string;
  completed_at: string | null;
  duration_ms: number | null;
  records_processed: number;
  records_failed: number;
  company_id: number;
  error_message: string | null;
}

export interface AlertRule {
  id: number;
  name: string;
  condition_type: string;
  threshold_value: number;
  severity: "info" | "warning" | "critical";
  is_enabled: boolean;
  last_triggered_at: string | null;
}

/** Matches GET /api/monitoring/metrics JSON response (Chart.js shape) */
export interface MetricsResponse {
  success: boolean;
  data: {
    labels: string[];
    datasets: MetricsDataset[];
    summary: MetricsSummary;
  };
}

export interface MetricsDataset {
  label: string;
  data: number[];
}

export interface MetricsSummary {
  total_executions: number;
  success_rate: number;
  avg_duration_ms: number;
  total_records: number;
}

/** Matches GET /api/monitoring/alerts JSON response */
export interface AlertRulesResponse {
  success: boolean;
  data: {
    alert_rules: AlertRule[];
  };
}

/** Recharts-friendly data point (transformed from MetricsResponse) */
export interface MetricsDataPoint {
  date: string;
  success: number;
  failed: number;
  avgDuration: number;
  records: number;
}
