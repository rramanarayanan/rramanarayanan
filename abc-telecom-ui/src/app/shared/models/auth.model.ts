export interface User {
  userId?: number;
  username: string;
  email: string;
  role: string;
}

export interface AuthRequest {
  usernameOrEmail: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  refreshToken?: string;
  username: string;
  email: string;
  role: string;
  message: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  role?: string;
}
