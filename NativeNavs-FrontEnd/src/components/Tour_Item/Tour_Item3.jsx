import styles from "./Tour_item3.module.css";

const Tour_Item3 = ({ tour }) => {
  return (
    <div className={styles.Tour_Item3}>
      <div className={styles.tour_left_info}>
        <img className={styles.img_url} src={tour.img_url} alt="투어 이미지" />
      </div>
      <div className={styles.tour_right_info}>
        <p className={styles.title}>{tour.title}</p>
        <p>{tour.nickname}</p>
        <p>
          {tour.date.toLocaleDateString("ko-KR", {
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
          })}{" "}
          , {tour.regional_information}
        </p>
      </div>
    </div>
  );
};

export default Tour_Item3;
