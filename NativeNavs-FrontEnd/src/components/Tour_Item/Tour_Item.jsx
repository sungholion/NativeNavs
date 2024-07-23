import { tour } from "../../dummy";
import styles from "./Tour_Item.module.css";
import Rating from "../Star/Rating(Basic)";

// user_id : 유저 고유 키값
// title : 투어 제목 :String
// thumbnail_image : 투어 썸네일 이미지 : String
// start_date : 시작날짜 (yyyy-mm-dd) : string
// end_date : 끝 날짜 (yyyy-mm-dd) : string
// nav_profile_img : 해당 가이드의 프로필 이미지 링크 : string
// nav_nickname : 가이드 닉네임 : string
// nav_language : 가이드가 사용가능한 언어 목록 : string[]
const Tour_Item = ({
  user_id,
  title,
  thumbnail_image,
  start_date,
  end_date,
  review_average,
  nav_profile_img,
  nav_nickname,
  nav_language,
}) => {
  return (
    <div className={styles.tour_item}>
      <img src={thumbnail_image} alt="" className={styles.tour_thumnail} />
      <section className={styles.tour_info}>
        <div className={styles.tour_leftinfo}>
          <p className={styles.tour_title}>{title}</p>
          <p className={styles.tour_duration}>
            {start_date} ~ {end_date}
          </p>
          <Rating avg={review_average} />
        </div>
        <div className={styles.tour_rightinfo}>
          <div className={styles.tour_nav}>
            <img src={nav_profile_img} alt="" className={styles.nav_img} />
            <p>{nav_nickname}</p>
          </div>
          <div className={styles.tour_nav_language}>
            <img src="src/assets/language.png" alt="dd" />
            {nav_language.length > 1 ? (
              <p>
                {nav_language[0]}외 {nav_language.length - 1}
                개국어
              </p>
            ) : (
              <p>{nav_language[0]}</p>
            )}
          </div>
        </div>
      </section>
    </div>
  );
};

export default Tour_Item;
