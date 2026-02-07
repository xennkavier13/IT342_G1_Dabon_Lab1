import { Link, useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import type { FormEvent } from "react";
import AuthLayout from "../layouts/AuthLayout";
import InputField from "../components/InputField";
import PrimaryButton from "../components/PrimaryButton";
import StatusMessage from "../components/StatusMessage";
import { authApi } from "../api/auth";
import { setAuth } from "../utils/storage";

type LocationState = {
	fromRegister?: boolean;
} | null;

const LoginPage = () => {
	const navigate = useNavigate();
	const location = useLocation();
	const locationState = location.state as LocationState;

	const [identifier, setIdentifier] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState<string | null>(null);
	const [isLoading, setIsLoading] = useState(false);

	const registrationNotice = locationState?.fromRegister
		? "Registration complete. Log in to continue."
		: null;

	const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		setError(null);
		setIsLoading(true);

		try {
			const response = await authApi.login({ identifier, password });
			setAuth(response.token, {
				username: response.username,
				email: response.email,
			});
			navigate("/dashboard");
		} catch (err) {
			if (err && typeof err === "object" && "message" in err) {
				setError(String((err as { message: string }).message));
			} else {
				setError("Unable to login. Please try again.");
			}
		} finally {
			setIsLoading(false);
		}
	};

	return (
		<AuthLayout
			title="Welcome back"
			subtitle="Use your username or email to access the platform."
			footer={
				<p>
					No account yet?{" "}
					<Link className="text-teal-700 hover:text-teal-800" to="/register">
						Create one
					</Link>
				</p>
			}
		>
			{registrationNotice ? (
				<StatusMessage variant="success" message={registrationNotice} />
			) : null}
			{error ? <StatusMessage variant="error" message={error} /> : null}
			<form className="mt-2 space-y-5" onSubmit={handleSubmit}>
				<InputField
					id="identifier"
					label="Username or Email"
					name="identifier"
					value={identifier}
					onChange={(event) => setIdentifier(event.target.value)}
					placeholder="john.smith"
					autoComplete="username"
				/>
				<InputField
					id="password"
					label="Password"
					name="password"
					type="password"
					value={password}
					onChange={(event) => setPassword(event.target.value)}
					placeholder="Your secure password"
					autoComplete="current-password"
				/>
				<PrimaryButton
					label={isLoading ? "Signing in..." : "Login"}
					type="submit"
					disabled={isLoading}
				/>
			</form>
		</AuthLayout>
	);
};

export default LoginPage;
