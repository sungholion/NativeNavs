import styles from "./Heart.module.css";

const Heart = ({ isWishListed, onClickEvent }) => {
  const handleClick = (e) => {
    e.stopPropagation();
    onClickEvent(e); // 클릭 이벤트를 상위로 전달
  };

  return (
    <div
      className={`${styles.heart} ${
        isWishListed ? styles["heart-liked"] : styles["heart-unliked"]
      }`}
      onClick={handleClick}
    ></div>
  );
};

export default Heart;
