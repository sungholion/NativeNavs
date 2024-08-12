import React from "react";
import styles from "./Image.module.css";

const Image = ({ url, size = 1, onClickEvent }) => {
  return (
    <img
      src={url}
      alt=""
      className={`${styles.img_default} ${styles[`img_${size}`]}`}
      onClick={onClickEvent}
    />
  );
};

export default Image;
