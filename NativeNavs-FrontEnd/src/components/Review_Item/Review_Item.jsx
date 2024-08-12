import styles from "./Review_Item.module.css";
import StarScore from "../Star/StarScore";
import Review_Item_img from "./Review_Item_img";

const Review_Item = ({
  description,
  imageList,
  user,
  score,
  tourTitle,
  needToShowTourTitle = false,
}) => {
  return (
    <div className={styles.Review_Item}>
      <section className={styles.Review_Item_header}>
        <div className={styles.Review_Item_travel}>
          <img className={styles.userImage} src={user.image} alt="user_profile" />
          <div className={styles.Review_Item_travel_text}>
            <p className={styles.Review_Item_travel_nickname}>
              {user.nickname}
            </p>
          </div>
        </div>
        <div className={styles.Review_Item_score}>
          <StarScore score={score * 20} />
        </div>
      </section>
      <section className={styles.Review_Item_content}>
        {needToShowTourTitle ? <div><p className={styles.tourTitle}>{tourTitle}</p></div> : null}
        <div className={styles.Review_Item_tour_description}>{description}</div>
        <div className={styles.Review_Item_image}></div>
      </section>
      <section className={styles.Review_Item_image}>
        {imageList.length > 0 ? (
          <Review_Item_img imageList={imageList} />
        ) : null}
      </section>
    </div>
  );
};

export default Review_Item;
