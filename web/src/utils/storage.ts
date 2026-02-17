import type { UserProfile } from "../types/user";

const TOKEN_KEY = "auth_token";
const USER_KEY = "auth_user";

type StoredUser = Pick<UserProfile, "username" | "email"> &
  Partial<Pick<UserProfile, "firstName" | "lastName" | "createdAt">>;

export const setAuth = (token: string, user: StoredUser) => {
  localStorage.setItem(TOKEN_KEY, token);
  localStorage.setItem(USER_KEY, JSON.stringify(user));
};

export const clearAuth = () => {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USER_KEY);
};

export const getToken = () => localStorage.getItem(TOKEN_KEY);

export const getStoredUser = (): StoredUser | null => {
  const raw = localStorage.getItem(USER_KEY);
  if (!raw) {
    return null;
  }

  try {
    return JSON.parse(raw) as StoredUser;
  } catch {
    return null;
  }
};
