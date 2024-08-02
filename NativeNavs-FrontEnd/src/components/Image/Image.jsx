import styles from "./Image.module.css";
// url : 이미지링크 : string
// alt : 설명 : string
// size : 사이즈크기 : number
// onClickEvent : 이미지 클릭시 발생하는 이벤트 : (?) => void
const Image = ({ url = "", alt = "Empty", size = 1, onClickEvent }) => {
  return (
    <img
      src={url}
      alt={alt}
      className={`${styles.img_default} ${styles[`img_${size}`]}`}
      onClick={onClickEvent}
    />
  );
};

export default Image;
