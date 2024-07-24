import styles from "./Tour_Item2.module.css";
import Rating from "../Star/Rating";
import Profile from "../Profile/Profile";

const Tour_Item2 = ({
  user_id,
  title,
  date,
  thumbnail_image,
  review_average,
  image,
  nickname,
  language,
  created_at,
}) => {
  return (
    <div className={styles.TotalContainer}>
      <div className={styles.TopContainer}>
        <img src={thumbnail_image} alt="" />
      </div>
      <div className={styles.BottomContainer}>
        <div className={styles.TourDescription}>
          <h4>{title}</h4>
          <h6>{date}</h6>
          <h6>
            <Rating review_average={review_average} />
          </h6>
        </div>
        <div className={styles.NavDescription}>
          <Profile image={image} nickname={nickname} language={language} created_at={created_at} />
        </div>
      </div>
    </div>
  );
};

export default Tour_Item2;
