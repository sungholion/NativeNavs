import { tour } from "../../dummy";
import styles from "./Tour_Item.module.css";
import Rating from "../Star/Rating(Basic)";

const Tour_Item = () => {
  return (
    <div className={styles.tour_item}>
      <img src={tour.img_url} alt="" className={styles.tour_thumnail} />
      <section className={styles.tour_info}>
        <div className={styles.tour_leftinfo}>
          <p className={styles.tour_title}>{tour.title}</p>
          <p className={styles.tour_duration}>
            {tour.duration.start.toLocaleDateString()} ~{" "}
            {tour.duration.end.toLocaleDateString()}
          </p>
          <Rating avg={tour.avg} />
        </div>
        <div className={styles.tour_rightinfo}>
          <div className={styles.tour_nav}>
            <img src={tour.nav.Image} alt="" className={styles.nav_img} />
            <p>{tour.nav.Nickname}</p>
          </div>
          <div className={styles.tour_nav_language}>
            <img src="src/assets/language.png" alt="dd" />
            {tour.nav.Languages.length > 1 ? (
              <p>
                {tour.nav.Languages[0]}외 {tour.nav.Languages.length - 1}
                개국어
              </p>
            ) : (
              <p>{tour.nav.Languages[0]}</p>
            )}
          </div>
        </div>
      </section>
    </div>
  );
};

export default Tour_Item;
