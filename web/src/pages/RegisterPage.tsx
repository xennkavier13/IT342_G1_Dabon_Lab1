import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import type { FormEvent } from "react";
import AuthLayout from "../layouts/AuthLayout";
import InputField from "../components/InputField";
import PrimaryButton from "../components/PrimaryButton";
import StatusMessage from "../components/StatusMessage";
import { authApi } from "../api/auth";

const RegisterPage = () => {
	const navigate = useNavigate();
	const [formValues, setFormValues] = useState({
		firstName: "",
		lastName: "",
		username: "",
		email: "",
		password: "",
		confirmPassword: "",
	});
	const [error, setError] = useState<string | null>(null);
	const [success, setSuccess] = useState<string | null>(null);
	const [isLoading, setIsLoading] = useState(false);

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const { name, value } = event.target;
		setFormValues((prev) => ({ ...prev, [name]: value }));
	};

	const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		setError(null);
		setSuccess(null);

		if (formValues.password !== formValues.confirmPassword) {
			setError("Passwords do not match.");
			return;
		}

		setIsLoading(true);

		try {
			await authApi.register({
				username: formValues.username,
				email: formValues.email,
				password: formValues.password,
				firstName: formValues.firstName,
				lastName: formValues.lastName,
			});
			setSuccess("Account created. Redirecting to login...");
			setTimeout(() => {
				navigate("/login", { state: { fromRegister: true } });
			}, 1000);
		} catch (err) {
			if (err && typeof err === "object" && "message" in err) {
				setError(String((err as { message: string }).message));
			} else {
				setError("Unable to register. Please try again.");
			}
		} finally {
			setIsLoading(false);
		}
	};

	return (
		<AuthLayout
			title="Create your account"
			subtitle="Join the system with your details and secure password."
			footer={
				<p>
					Already registered?{" "}
					<Link className="text-teal-700 hover:text-teal-800" to="/login">
						Login here
					</Link>
				</p>
			}
		>
			{success ? <StatusMessage variant="success" message={success} /> : null}
			{error ? <StatusMessage variant="error" message={error} /> : null}
			<form className="mt-2 space-y-5" onSubmit={handleSubmit}>
				<div className="grid gap-4 sm:grid-cols-2">
					<InputField
						id="firstName"
						label="First name"
						name="firstName"
						value={formValues.firstName}
						onChange={handleChange}
						autoComplete="given-name"
					/>
					<InputField
						id="lastName"
						label="Last name"
						name="lastName"
						value={formValues.lastName}
						onChange={handleChange}
						autoComplete="family-name"
					/>
				</div>
				<InputField
					id="username"
					label="Username"
					name="username"
					value={formValues.username}
					onChange={handleChange}
					autoComplete="username"
				/>
				<InputField
					id="email"
					label="Email"
					name="email"
					type="email"
					value={formValues.email}
					onChange={handleChange}
					autoComplete="email"
				/>
				<InputField
					id="password"
					label="Password"
					name="password"
					type="password"
					value={formValues.password}
					onChange={handleChange}
					placeholder="At least 8 characters"
					autoComplete="new-password"
				/>
				<InputField
					id="confirmPassword"
					label="Confirm password"
					name="confirmPassword"
					type="password"
					value={formValues.confirmPassword}
					onChange={handleChange}
					placeholder="Repeat your password"
					autoComplete="new-password"
				/>
				<PrimaryButton
					label={isLoading ? "Creating account..." : "Register"}
					type="submit"
					disabled={isLoading}
				/>
			</form>
		</AuthLayout>
	);
};

export default RegisterPage;
