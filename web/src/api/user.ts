import { apiRequest, type ApiError } from "./http";
import { getToken } from "../utils/storage";
import type { UserProfile } from "../types/user";

const buildAuthHeaders = () => {
  const token = getToken();
  if (!token || token === "undefined" || token === "null" || !token.trim()) {
    const error: ApiError = { message: "Missing auth token", status: 401 };
    throw error;
  }

  return { Authorization: `Bearer ${token}` };
};

export const userApi = {
  getCurrentUser: () =>
    apiRequest<UserProfile>({
      url: "/user/me",
      method: "GET",
      headers: buildAuthHeaders(),
    }),
};
