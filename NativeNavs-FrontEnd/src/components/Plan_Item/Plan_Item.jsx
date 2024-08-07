import React, { useEffect, useState } from "react";
import styles from "./Plan_Item.module.css";
import { getStaticImage } from "@/utils/get-static-image";
import { getImageUrl } from "@/utils/get-image-url";

// const plan_info = {
//   id: 2,
//   tour_id: 1,
//   field: "Plan제목",
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
  id,
  field,
  description,
  latitude,
  longitude,
  addressFull,
  image,
  onDeleteEvent,
  onClickEvent,
  enableDeleteOption = true,
}) => {
  const [planImg, setPlanImg] = useState("");
  useEffect(() => {
    getImageUrl(image, setPlanImg);
  }, [image]);

  return (
    <div className={styles.Plan_Item}>
      {enableDeleteOption && (
        <div
          className={styles.delete_button}
          onClick={() => {
            onDeleteEvent(id);
          }}
        >
          <img src={getStaticImage("close")} alt="close" />
        </div>
      )}
      <div className={styles.content} onClick={onClickEvent}>
        <img src={planImg} alt="image" className={styles.plan_img} />
        <div className={styles.text}>
          <div className={styles.field}>{field}</div>
          <div className={styles.description}>{description}</div>
          <div className={styles.addressFull}>{addressFull}</div>
        </div>
      </div>
    </div>
  );
};

export default Plan_Item;
