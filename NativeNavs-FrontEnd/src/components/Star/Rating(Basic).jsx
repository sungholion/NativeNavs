import styles from "./Rating.module.css";
import React from "react";
// ratingValue : 계산된 평점
// toFixed(2) : 소수점 둘 째자리까지 표현
const Rating = ({ reviewAverage }) => {
  return (
    <div className="ratingContainer">
      <span className={styles.star}>★</span>
      <span className={styles.ratingValue}>{reviewAverage.toFixed(1)}</span>
    </div>
  );
};

export default Rating;
