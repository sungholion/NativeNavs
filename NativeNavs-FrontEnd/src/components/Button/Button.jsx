import styles from "./Button.module.css";
const Button = ({ size, text, onClickEvent }) => {
  return (
    <button className={styles[`button_${size}`]} onClick={onClickEvent}>
      {text}
    </button>
  );
};

export default Button;
