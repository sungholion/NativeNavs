// src/components/Tour_Item4.jsx
import React from "react";
import styles from "./Tour_Item4.module.css";

const Tour_Item4 = ({ tour }) => {
  const Image = tour.thumbnailImage[0];
  return (
    <div className={styles.Tour_Item4}>
      <div className={styles.TourImageContainer}>
        <img className={styles.TourImage} src={Image} alt="Tour Thumbnail" />
        {tour.isEnded && <div className={styles.Overlay}>종료</div>}
      </div>
      <div className={styles.TourDetailContainer}>
        <div className={styles.TourTitle}>{tour.title}</div>
        <div className={styles.TourDate}>
          {tour.start_date.toLocaleDateString()} - {tour.end_date.toLocaleDateString()}
        </div>
        <div className={styles.TourMeta}>
          🌏{tour.language.length} 💗: {tour.review_average}
        </div>
      </div>
    </div>
  );
};

export default Tour_Item4;
