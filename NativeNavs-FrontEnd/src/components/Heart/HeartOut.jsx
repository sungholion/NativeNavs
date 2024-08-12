import styles from "./HeartOut.module.css";

const HeartOut = ({ isWishListed }) => {


  return (
    <div
      className={`${styles.heart} ${
        isWishListed ? styles["heart-liked"] : styles["heart-unliked"]
      }`}
    ></div>
  );
};

export default HeartOut;
