import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { LoginRequest } from '../models/login-request.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginRequest: LoginRequest = { username: '', password: '' };
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    this.authService.login(this.loginRequest).subscribe({
      next: () => {
        console.log("Login successful");
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.error("Login error:", error);
        this.errorMessage = error.message;
      }
    });
  }
}

