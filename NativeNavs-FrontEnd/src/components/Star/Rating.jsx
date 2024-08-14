import React, { useEffect, useState } from "react";
import styles from "./Rating.module.css";

const Rating = ({ review_average }) => {
  const [displayValue, setDisplayValue] = useState(0);

  useEffect(() => {
    const duration = 1000;
    const start = performance.now();

    const animate = (currentTime) => {
      const progress = currentTime - start;
      const progressPercentage = Math.min(progress / duration, 1);
      setDisplayValue((review_average * progressPercentage).toFixed(2));

      if (progress < duration) {
        requestAnimationFrame(animate);
      }
    };

    requestAnimationFrame(animate);
  }, [review_average]);

  return (
    <div className={styles.ratingContainer}>
      <span className={styles.star}>★</span>
      <span className={styles.review_average}>{displayValue}</span>
    </div>
  );
};

export default Rating;
