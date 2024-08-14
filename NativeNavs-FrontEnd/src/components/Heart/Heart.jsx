import styles from "./Heart.module.css";

const Heart = ({ isWishListed, onClickEvent }) => {
  const handleClick = (e) => {
    e.stopPropagation();
    onClickEvent(e);
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
