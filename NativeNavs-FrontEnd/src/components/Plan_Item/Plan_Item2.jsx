import React from "react";
import styles from "./Plan_Item2.module.css";
import { getStaticImage } from "@/utils/get-static-image";

const Plan_Item2 = ({
  plan_id,
  tour_id,
  field,
  description,
  latitude,
  longitude,
  address_full,
  image,
}) => {
  return (
    <div className={styles.Plan_Item2}>
      <div className={styles.container}>
        <img src={image} alt="image" className={styles.plan_img} />
        <div className={styles.text}>
          <div className={styles.field}>{field}</div>
          <div className={styles.description}>{description}</div>
        </div>
      </div>
    </div>
  );
};

export default Plan_Item2;
