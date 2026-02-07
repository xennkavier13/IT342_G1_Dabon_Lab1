import type { ChangeEvent } from "react";

type InputFieldProps = {
  id: string;
  label: string;
  type?: string;
  name: string;
  value: string;
  placeholder?: string;
  autoComplete?: string;
  onChange: (event: ChangeEvent<HTMLInputElement>) => void;
};

const InputField = ({
  id,
  label,
  type = "text",
  name,
  value,
  placeholder,
  autoComplete,
  onChange,
}: InputFieldProps) => {
  return (
    <label className="flex flex-col gap-2 text-sm font-semibold text-slate-900" htmlFor={id}>
      <span className="tracking-tight">{label}</span>
      <input
        id={id}
        name={name}
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        autoComplete={autoComplete}
        className="rounded-2xl border border-teal-100 bg-amber-50/40 px-4 py-3 text-base text-slate-900 shadow-sm transition focus:border-teal-500 focus:outline-none focus:ring-4 focus:ring-teal-200"
        required
      />
    </label>
  );
};

export default InputField;
