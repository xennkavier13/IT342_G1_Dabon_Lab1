import axios, { AxiosError, type AxiosRequestConfig } from "axios";

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api";

export type ApiError = {
  message: string;
  status: number;
};

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

const resolveErrorMessage = (error: AxiosError) => {
  const data = error.response?.data;
  if (typeof data === "string") {
    return data;
  }

  if (data && typeof data === "object" && "message" in data) {
    return String((data as { message?: string }).message ?? "Request failed");
  }

  return error.message || "Request failed";
};

export const apiRequest = async <T>(config: AxiosRequestConfig) => {
  try {
    const response = await apiClient.request<T>(config);
    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      const status = error.response?.status ?? 0;
      const message = resolveErrorMessage(error);
      const apiError: ApiError = { message, status };
      throw apiError;
    }

    throw error;
  }
};
