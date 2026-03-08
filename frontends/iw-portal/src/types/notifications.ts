export type NotificationType = "system" | "alert" | "flow-status" | "security";

export interface Notification {
  id: number;
  type: NotificationType;
  title: string;
  message: string;
  link: string | null;
  isRead: boolean;
  createdAt: string;
}

export interface NotificationsResponse {
  success: boolean;
  notifications: Notification[];
  unreadCount: number;
  totalCount: number;
  page: number;
  pageSize: number;
}

export interface UnreadCountResponse {
  success: boolean;
  unreadCount: number;
}
