import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Invoice, Payment } from '../models/billing.model';
import { environment } from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BillingService {
  private invoicesUrl = `${environment.apiUrl}/invoices`;
  private paymentsUrl = `${environment.apiUrl}/payments`;

  constructor(private http: HttpClient) { }

  // Invoice Methods
  createInvoice(invoice: Invoice): Observable<Invoice> {
    return this.http.post<Invoice>(this.invoicesUrl, invoice);
  }

  getInvoice(id: number): Observable<Invoice> {
    return this.http.get<Invoice>(`${this.invoicesUrl}/${id}`);
  }

  getCustomerInvoices(customerId: number): Observable<Invoice[]> {
    return this.http.get<Invoice[]>(`${this.invoicesUrl}/customer/${customerId}`);
  }

  getInvoicesByStatus(status: string): Observable<Invoice[]> {
    return this.http.get<Invoice[]>(`${this.invoicesUrl}/status/${status}`);
  }

  updateInvoiceStatus(id: number, status: string): Observable<Invoice> {
    const params = new HttpParams().set('status', status);
    return this.http.patch<Invoice>(`${this.invoicesUrl}/${id}/status`, {}, { params });
  }

  // Payment Methods
  processPayment(payment: Payment): Observable<Payment> {
    return this.http.post<Payment>(this.paymentsUrl, payment);
  }

  getPayment(id: number): Observable<Payment> {
    return this.http.get<Payment>(`${this.paymentsUrl}/${id}`);
  }

  getInvoicePayments(invoiceId: number): Observable<Payment[]> {
    return this.http.get<Payment[]>(`${this.paymentsUrl}/invoice/${invoiceId}`);
  }

  getPaymentsByStatus(status: string): Observable<Payment[]> {
    return this.http.get<Payment[]>(`${this.paymentsUrl}/status/${status}`);
  }
}
