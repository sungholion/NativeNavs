import styles from "./Review_Item.module.css";
import StarScore from "../Star/StarScore";
import Review_Item_img from "./Review_Item_img";
// const info = {
//     user: { user_id: 1, image: "", nickname: "오리불고기", nation: "미국" },
//     score: 4.2,
//     description: "설명설명",
//     created_at: new Date(2024, 3, 2),
//     tour: {
//       tour_id: 2,
//       title: "투어 내용",
//     },
//     needToShowTourTitle : 투어 타이틀 보여주기 여부
//      imageList : [] 이미지 url 담긴 배열
//   };

const Review_Item = ({
  user,
  score,
  description,
  created_at,
  tour,
  needToShowTourTitle = false,
  imageList,
}) => {
  return (
    <div className={styles.Review_Item}>
      <section className={styles.Review_Item_header}>
        <div className={styles.Review_Item_travel}>
          <img src={user.image} alt="user_profile" />
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
        {needToShowTourTitle ? <div>[ {tour.title} ]</div> : null}
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
