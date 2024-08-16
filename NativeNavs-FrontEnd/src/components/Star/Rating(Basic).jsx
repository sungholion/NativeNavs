import styles from "./Rating.module.css";
import React from "react";
const Rating = ({ reviewAverage }) => {
  return (
    <div className="ratingContainer">
      <span className={styles.star}>★</span>
      <span className={styles.ratingValue}>{reviewAverage.toFixed(1)}</span>
    </div>
  );
};

export default Rating;
