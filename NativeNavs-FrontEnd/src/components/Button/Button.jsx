import styles from "./Button.module.css";
// size : 버튼의 사이즈를 커스텀
const Button = ({ text, onClickEvent }) => {
  const size = 1;

  const onClickButton = () => {
    console.log("클릭 이벤트 발생");
    onClickEvent();
  };

  return (
    <button className={styles[`button_${size}`]} onClick={onClickButton}>
      +{text}
    </button>
  );
};

export default Button;
