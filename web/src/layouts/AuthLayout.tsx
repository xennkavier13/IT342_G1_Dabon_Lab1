import type { ReactNode } from "react";

type AuthLayoutProps = {
  title: string;
  subtitle: string;
  footer?: ReactNode;
  children: ReactNode;
};

const AuthLayout = ({ title, subtitle, footer, children }: AuthLayoutProps) => {
  return (
    <div className="min-h-screen bg-[radial-gradient(circle_at_20%_20%,rgba(249,115,22,0.18),transparent_40%),radial-gradient(circle_at_80%_0%,rgba(13,148,136,0.18),transparent_45%),linear-gradient(120deg,#f6f1ea_0%,#fbf7f1_60%,#f5ede3_100%)]">
      <div className="grid min-h-screen gap-10 px-6 py-14 lg:grid-cols-[1.1fr_0.9fr] lg:px-16">
        <div className="relative overflow-hidden rounded-[32px] bg-gradient-to-br from-teal-700 via-teal-800 to-slate-900 p-10 text-white shadow-[0_24px_60px_rgba(15,23,42,0.18)]">
          <div className="pointer-events-none absolute -left-16 -top-24 h-72 w-72 rounded-full bg-[radial-gradient(circle,rgba(249,115,22,0.4),transparent_70%)]" />
          <div className="relative z-10 max-w-[420px] animate-[fadeUp_0.6s_ease-out]">
            <p className="mb-4 text-xs font-semibold uppercase tracking-[0.24em] text-teal-100">
              IT342 Auth Portal
            </p>
            <h1 className="font-display text-4xl font-semibold tracking-tight">
              {title}
            </h1>
            <p className="mt-3 text-base text-teal-100/90">{subtitle}</p>
          </div>
        </div>
        <div className="flex flex-col gap-6 rounded-[28px] bg-white p-10 shadow-[0_24px_60px_rgba(15,23,42,0.14)] animate-[fadeUp_0.6s_ease-out_0.08s_both]">
          {children}
          {footer ? (
            <div className="text-center text-sm font-semibold text-teal-900">
              {footer}
            </div>
          ) : null}
        </div>
      </div>
    </div>
  );
};

export default AuthLayout;
