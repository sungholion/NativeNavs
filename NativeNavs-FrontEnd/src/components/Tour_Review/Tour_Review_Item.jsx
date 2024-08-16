import React, { useEffect } from "react";
import styles from "./Tour_Review_Item.module.css";
import Rating from "../Star/Rating(Basic)";

const Tour_Review_Item = ({
  user,
  tour_id,
  score,
  description,
  created_at,
  image,
}) => {
  const createdDate = new Date(created_at);
  return (
    <div className={styles.Tour_Review_Item}>
      <section className={styles.review_header}>
        <Rating ratingValue={score} />
        <div className={styles.review_date}>
          {createdDate.getFullYear()}년 {createdDate.getMonth() + 1}월
        </div>
      </section>
      <section className={styles.review_content}>
        <div className={styles.review_content_text}>{description}</div>
        <div className={styles.review_content_img}>
          <img src={image} alt="리뷰사진" />
        </div>
      </section>
      <section className={styles.review_writer}>
        <div className={styles.review_writer_img}>
          <img src={user.image} alt="프로필사진" />
        </div>
        <div className={styles.review_writer_info}>
          <h5>{user.nickname}</h5>
          <p>Travel 경험 경력 : {`231`}</p>
        </div>
      </section>
    </div>
  );
};

export default Tour_Review_Item;
