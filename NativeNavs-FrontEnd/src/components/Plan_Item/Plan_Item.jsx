import React, { useEffect, useState } from "react";
import styles from "./Plan_Item.module.css";
import { getStaticImage } from "@/utils/get-static-image";
import { getImageUrl } from "@/utils/get-image-url";

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
