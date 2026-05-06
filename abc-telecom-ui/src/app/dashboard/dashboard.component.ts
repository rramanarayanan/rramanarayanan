import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { CustomerService } from '../../shared/services/customer.service';
import { BillingService } from '../../shared/services/billing.service';
import { Customer } from '../../shared/models/customer.model';
import { Invoice } from '../../shared/models/billing.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  customer: Customer | null = null;
  invoices: Invoice[] = [];
  loading = true;
  currentUser: any;

  constructor(
    public authService: AuthService,
    private customerService: CustomerService,
    private billingService: BillingService
  ) { }

  ngOnInit(): void {
    this.currentUser = this.authService.currentUserValue;
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.loading = true;
    // Load customer and invoice data
    // This is a placeholder implementation
    setTimeout(() => {
      this.loading = false;
    }, 1000);
  }
}
