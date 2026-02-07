import type { ReactNode } from "react";
import { Navigate } from "react-router-dom";
import { getToken } from "../utils/storage";

type ProtectedRouteProps = {
  children: ReactNode;
};

const ProtectedRoute = ({ children }: ProtectedRouteProps) => {
  if (!getToken()) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
};

export default ProtectedRoute;
