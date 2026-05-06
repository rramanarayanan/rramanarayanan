export interface Customer {
  customerId?: number;
  userId: number;
  fullName: string;
  address: string;
  phoneNumber: string;
}

export interface Service {
  serviceId?: number;
  customerId: number;
  serviceType: string;
  startDate: string;
  status: string;
}

export interface UsageRecord {
  usageId?: number;
  usageDate: string;
  usageAmount: number;
  unit: string;
}
