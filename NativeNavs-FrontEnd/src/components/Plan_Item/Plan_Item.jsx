import React from "react";
import styles from "./Plan_Item.module.css";

// const plan_info = {
//   plan_id: 2,
//   tour_id: 1,
//   title: "Plan제목",
//   description:
//     "Plan에 대한 내용임 Plan에 대한 내용임 Plan에 대한 내용임Plan에 대한 내용임  Plan에 대한 내용임Plan에 대한 내용임Plan에 대한 내용임 Plan에 대한 내용임 Plan에 대한 내용임 Plan에 대한 내용임 Plan에 대한 내용임 ",
//   latitude: 37.5642135,
//   longitude: 127.0016958,
//   address_full: "서울특별시 중심지",
//   image:
//     "https://d3kxs6kpbh59hp.cloudfront.net/community/COMMUNITY/ba663a6c2e8446b6ae79f076a33a8b88/d4107e3bf8cd46c5b0ca9b7a11ce9087_1652305717.png",
// };

// const onClickEvent = () => {
//   console.log("와 클릭이벤트!");
// };
// const onDeleteEvent = () => {
//   console.log("와 삭제이벤트!");
// };

const Plan_Item = ({
  plan_id,
  tour_id,
  title,
  description,
  latitude,
  longitude,
  address_full,
  image,
  onDeleteEvent,
  onClickEvent,
}) => {
  return (
    <div className={styles.Plan_Item}>
      <div className={styles.delete_button} onClick={onDeleteEvent}>
        <img src="src/assets/close.png" alt="close" />
      </div>

      <div className={styles.content} onClick={onClickEvent}>
        <img src={image} alt="image" className={styles.plan_img} />
        <div className={styles.text}>
          <div className={styles.title}>{title}</div>
          <div className={styles.description}>{description}</div>
        </div>
      </div>
    </div>
  );
};

export default Plan_Item;
