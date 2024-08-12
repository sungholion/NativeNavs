import styles from "./Tour_Item_mini_Reservation.module.css";
import Rating from "../Star/Rating(Basic)";

// 예약 작성 페이지에 작성할 관련 투어 정보를 간략하게 보여주는
const Tour_Item_mini_Reservation = ({
  image,
  location,
  title,
  score,
  language,
}) => {
  return (
    <div className={styles.Tour_Item_mini_Review}>
      <section className={styles.tourImageSection}>
        <img src={image} alt="" />
      </section>
      <section className={styles.tourTextInfo}>
        <div className={styles.tourTextInfo_header}>
          <h3>{title}</h3>
          <p>
            {language === "ko" ? "장소" : "location"} : {location || "abcd"}
          </p>
          <div className={styles.tourTextInfo_score}>
            <Rating reviewAverage={score} />
          </div>
        </div>
      </section>
    </div>
  );
};

export default Tour_Item_mini_Reservation;
