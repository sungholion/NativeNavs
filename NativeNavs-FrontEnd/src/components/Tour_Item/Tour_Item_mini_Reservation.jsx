import styles from "./Tour_Item_mini_Review.module.css";
import Rating from "../Star/Rating(Basic)";

const info = {
  tour: {
    // 투어 정보
    image:
      "https://cdn.pixabay.com/photo/2016/11/29/05/45/astronomy-1867616_960_720.jpg",
    title: "투어 제목",
    nav: {
      // 가이드 정보
      image:
        "https://cdn.pixabay.com/photo/2016/11/29/05/45/astronomy-1867616_960_720.jpg",
      nickname: "가이드이름",
    },
  },
  progress: {
    // 예약 정보
    date: "2021-09-01",
    participant: 2,
  },
};

// 예약 작성 페이지에 작성할 관련 투어 정보를 간략하게 보여주는
const Tour_Item_mini_Reservation = ({ tour }) => {
  return (
    <div className={styles.Tour_Item_mini_Review}>
      <section className={styles.tourImageSection}>
        <img src={tour.image} alt="" />
      </section>
      <section className={styles.tourInfoSection}>
        <div className={styles.tourTextInfo}>
          <h3>{tour.title}</h3>
          <Rating score={tour.score} />
        </div>
      </section>
    </div>
  );
};

export default Tour_Item_mini_Reservation;
