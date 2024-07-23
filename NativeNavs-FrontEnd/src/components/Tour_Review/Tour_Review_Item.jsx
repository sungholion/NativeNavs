import React, { useEffect } from "react";
import styles from "./Tour_Review_Item.module.css";
import Rating from "../Star/Rating";

// const info = {
//   user: {
//     id: 1, //user_id
//     image:
//       "https://i.namu.wiki/i/u253q5zv58zJ1twYkeea-czVz8SQsvX-a1jVZ8oYsTVDH_TRC8-bpcVa4aKYQs5lI55B9srLYF9JJFUPbkI8MA.webp", // 유저 프로필 사진 (관강객)
//     nickname: "오리고기",
//     created_at: "2022-12-23",
//   },
//   tour_id: 12, //투어 ID
//   score: 4.23, //리뷰점수
//   description: "내용내용내용", //리뷰 내용
//   created_at: "2024-12-23", // 리뷰 작성날짜
//   image:
//     "https://png.pngtree.com/thumb_back/fh260/background/20230322/pngtree-panorama-of-seoul-downtown-cityscape-illuminated-with-lights-and-namsan-seoul-photo-image_2092890.jpg",
// };

const Tour_Review_Item = ({
  user,
  tour_id,
  score,
  description,
  created_at,
  image,
}) => {
  useEffect(() => {}, []);
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
