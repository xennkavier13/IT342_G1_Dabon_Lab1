import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PrimaryButton from "../components/PrimaryButton";
import StatusMessage from "../components/StatusMessage";
import { userApi } from "../api/user";
import type { UserProfile } from "../types/user";
import { clearAuth, getStoredUser } from "../utils/storage";

const DashboardPage = () => {
	const navigate = useNavigate();
	const storedUser = getStoredUser();
	const [profile, setProfile] = useState<UserProfile | null>(null);
	const [error, setError] = useState<string | null>(null);
	const [isLoading, setIsLoading] = useState(true);

	const user = profile ?? storedUser;
	
	const handleLogout = () => {
		clearAuth();
		navigate("/login");
	};

	useEffect(() => {
		let isMounted = true;

		const loadProfile = async () => {
			try {
				const response = await userApi.getCurrentUser();
				if (isMounted) {
					setProfile(response);
					setError(null);
				}
			} catch (err) {
				if (!isMounted) {
					return;
				}

				const message =
					err && typeof err === "object" && "message" in err
						? String((err as { message: string }).message)
						: "Unable to load your profile.";
				setError(message);

				if (err && typeof err === "object" && "status" in err) {
					const status = Number((err as { status?: number }).status ?? 0);
					if (status === 401) {
						clearAuth();
						navigate("/login");
					}
				}
			} finally {
				if (isMounted) {
					setIsLoading(false);
				}
			}
		};

		loadProfile();

		return () => {
			isMounted = false;
		};
	}, [navigate]);

	const formatCreatedAt = (value?: string) => {
		if (!value) {
			return "Not available";
		}

		const parsed = new Date(value);
		if (Number.isNaN(parsed.getTime())) {
			return value;
		}

		return parsed.toLocaleString();
	};

	return (
		<div className="min-h-screen bg-[radial-gradient(circle_at_20%_20%,rgba(249,115,22,0.18),transparent_40%),radial-gradient(circle_at_80%_0%,rgba(13,148,136,0.18),transparent_45%),linear-gradient(120deg,#f6f1ea_0%,#fbf7f1_60%,#f5ede3_100%)] px-6 py-14 lg:px-20">
			<header className="flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between">
				<div>
					<p className="mb-2 text-xs font-semibold uppercase tracking-[0.24em] text-teal-700">
						Dashboard
					</p>
					<h1 className="font-display text-4xl font-semibold tracking-tight text-slate-900">
						Welcome{user?.username ? `, ${user.username}` : ""}
					</h1>
				</div>
				<PrimaryButton label="Logout" type="button" onClick={handleLogout} />
			</header>
			<section className="mt-10 rounded-3xl bg-white p-8 shadow-[0_24px_60px_rgba(15,23,42,0.14)]">
				<div>
					<h2 className="font-display text-2xl font-semibold text-slate-900">
						Account snapshot
					</h2>
				</div>
				{error ? (
					<div className="mt-4">
						<StatusMessage variant="error" message={error} />
					</div>
				) : null}
				<div className="mt-6 grid gap-4 md:grid-cols-2">
					<div className="rounded-2xl border border-teal-100 bg-slate-50/80 p-4">
						<p className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-500">
							Username
						</p>
						<p className="mt-2 text-lg font-semibold text-slate-900">
							{isLoading ? "Loading..." : user?.username ?? "Not available"}
						</p>
					</div>
					<div className="rounded-2xl border border-teal-100 bg-slate-50/80 p-4">
						<p className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-500">
							Email
						</p>
						<p className="mt-2 text-lg font-semibold text-slate-900">
							{isLoading ? "Loading..." : user?.email ?? "Not available"}
						</p>
					</div>
					<div className="rounded-2xl border border-teal-100 bg-slate-50/80 p-4">
						<p className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-500">
							First name
						</p>
						<p className="mt-2 text-lg font-semibold text-slate-900">
							{isLoading ? "Loading..." : user?.firstName ?? "Not available"}
						</p>
					</div>
					<div className="rounded-2xl border border-teal-100 bg-slate-50/80 p-4">
						<p className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-500">
							Last name
						</p>
						<p className="mt-2 text-lg font-semibold text-slate-900">
							{isLoading ? "Loading..." : user?.lastName ?? "Not available"}
						</p>
					</div>
					<div className="rounded-2xl border border-teal-100 bg-slate-50/80 p-4">
						<p className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-500">
							Created at
						</p>
						<p className="mt-2 text-lg font-semibold text-slate-900">
							{isLoading ? "Loading..." : formatCreatedAt(user?.createdAt)}
						</p>
					</div>
				</div>
			</section>
		</div>
	);
};

export default DashboardPage;
