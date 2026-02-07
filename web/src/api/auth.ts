import { apiRequest } from "./http";
import type { AuthResponse, LoginRequest, RegisterRequest } from "../types/auth";

export const authApi = {
  register: (payload: RegisterRequest) =>
    apiRequest<void>({
      url: "/auth/register",
      method: "POST",
      data: payload,
    }),
  login: (payload: LoginRequest) =>
    apiRequest<AuthResponse>({
      url: "/auth/login",
      method: "POST",
      data: payload,
    }),
};
