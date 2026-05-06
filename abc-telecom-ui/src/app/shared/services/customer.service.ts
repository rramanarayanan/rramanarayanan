import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customer, Service, UsageRecord } from '../models/customer.model';
import { environment } from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private apiUrl = `${environment.apiUrl}/customers`;

  constructor(private http: HttpClient) { }

  createCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.apiUrl, customer);
  }

  getCustomer(id: number): Observable<Customer> {
    return this.http.get<Customer>(`${this.apiUrl}/${id}`);
  }

  updateCustomer(id: number, customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(`${this.apiUrl}/${id}`, customer);
  }

  getCustomerServices(customerId: number): Observable<Service[]> {
    return this.http.get<Service[]>(`${this.apiUrl}/${customerId}/services`);
  }

  addService(customerId: number, service: Service): Observable<Service> {
    return this.http.post<Service>(`${this.apiUrl}/${customerId}/services`, service);
  }

  getServiceUsage(serviceId: number): Observable<UsageRecord[]> {
    return this.http.get<UsageRecord[]>(`${environment.apiUrl}/services/${serviceId}/usage`);
  }
}
