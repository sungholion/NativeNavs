import React from "react";
import styles from "./Tour_Item4.module.css";

const Tour_Item4 = ({ tour, onClickEvent, wishCount, bookCount }) => {
  const formatDate = (date) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const dateString = new Date(date).toLocaleDateString("ko-KR", options);
    return dateString.replace(/\.$/, "").replace(/\s/g, "");
  };

  return (
    <div
      onClick={() => onClickEvent(tour.tourId)}
      className={styles.Tour_Item4}
    >
      <div className={styles.TourImageContainer}>
        <img
          className={styles.TourImage}
          src={tour.thumbnailImage}
          alt="Tour Thumbnail"
        />
        {tour.removed && <div className={styles.Overlay}>종료</div>}
      </div>
      <div className={styles.TourDetailContainer}>
        <div className={styles.TourTitle}>{tour.title}</div>
        <div className={styles.TourDate}>
          {formatDate(tour.startDate)} ~ {formatDate(tour.endDate)}
        </div>
        <div className={styles.TourMeta}>
          📘 {bookCount} 💗 {wishCount}
        </div>
      </div>
    </div>
  );
};

export default Tour_Item4;
