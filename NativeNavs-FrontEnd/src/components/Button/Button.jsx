import styles from "./Button.module.css";
// size : 버튼의 사이즈를 커스텀
const Button = ({ size, text, onClickEvent }) => {
  
  const onClickButton = () => {
    onClickEvent();
  };

  return (
    <button className={styles[`button_${size}`]} onClick={onClickEvent}>
      {text}
    </button>
  );
};

export default Button;
