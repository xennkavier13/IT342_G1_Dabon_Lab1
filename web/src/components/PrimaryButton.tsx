type PrimaryButtonProps = {
  label: string;
  type?: "button" | "submit" | "reset";
  disabled?: boolean;
  onClick?: () => void;
};

const PrimaryButton = ({
  label,
  type = "button",
  disabled,
  onClick,
}: PrimaryButtonProps) => {
  return (
    <button
      className="rounded-full bg-gradient-to-r from-teal-600 via-teal-600 to-teal-700 px-6 py-3 text-base font-semibold text-white shadow-[0_16px_32px_rgba(13,148,136,0.28)] transition hover:-translate-y-0.5 hover:shadow-[0_22px_36px_rgba(13,148,136,0.32)] disabled:cursor-not-allowed disabled:opacity-70 disabled:shadow-none"
      type={type}
      disabled={disabled}
      onClick={onClick}
    >
      {label}
    </button>
  );
};

export default PrimaryButton;
