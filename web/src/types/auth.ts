export type LoginRequest = {
  identifier: string;
  password: string;
};

export type RegisterRequest = {
  username: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
};

export type AuthResponse = {
  jwt?: string;
  token?: string;
  username: string;
  email: string;
};
