import React, { useState } from "react";
import Modal from "../Modal/Modal";
import styles from "./Review_Item.module.css";
import StarScore from "../Star/StarScore";
import Review_Item_img from "./Review_Item_img";

const Review_Item = ({
  description,
  imageList,
  user,
  score,
  tourTitle,
  createdAt,
  needToShowTourTitle = false,
}) => {
  const getDaysAgo = (createdAt) => {
    const currentDate = new Date();
    const reviewDate = new Date(createdAt);
    const differenceInTime = currentDate.getTime() - reviewDate.getTime();
    const differenceInDays = Math.floor(differenceInTime / (1000 * 3600 * 24)); // 밀리초를 일 단위로 변환
    return differenceInDays;
  };
  
  const daysAgo = getDaysAgo(createdAt); // 작성일과 현재일의 차이 계산

  return (
    <div className={styles.Review_Item}>
      <section className={styles.Review_Item_header}>
        <div className={styles.Review_Item_travel}>
          <img
            className={styles.userImage}
            src={user.image}
            alt="user_profile"
          />
          <div className={styles.Review_Item_travel_text}>
            <p className={styles.Review_Item_travel_nickname}>
              {user.nickname}
            </p>
            <p className={styles.Review_Item_date}>
              {user.isKorean
                ? daysAgo === 0
                  ? "오늘 작성됨"
                  : `${daysAgo}일 전`
                : daysAgo === 0
                ? "Written today"
                : `${daysAgo} days ago`}
            </p>
          </div>
        </div>
        <div className={styles.Review_Item_score}>
          <StarScore score={score * 20} />
        </div>
      </section>
      <section className={styles.Review_Item_content}>
        {needToShowTourTitle ? (
          <div>
            <p className={styles.tourTitle}>{tourTitle}</p>
          </div>
        ) : null}
        <div className={styles.Review_Item_tour_description}>{description}</div>
        <div className={styles.Review_Item_image}></div>
      </section>
      <section className={styles.Review_Item_image}>
        {imageList.length > 0 ? (
          <Review_Item_img imageList={imageList} />
        ) : null}
      </section>
    </div>
  );
};

export default Review_Item;
