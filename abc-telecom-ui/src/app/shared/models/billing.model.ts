export interface Invoice {
  invoiceId?: number;
  customerId: number;
  billingPeriodStart: string;
  billingPeriodEnd: string;
  totalAmount: number;
  status: string;
}

export interface Payment {
  paymentId?: number;
  invoiceId: number;
  amount: number;
  paymentMethod: string;
  status: string;
}
