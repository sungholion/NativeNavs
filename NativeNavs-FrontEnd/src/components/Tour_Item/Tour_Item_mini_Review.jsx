import styles from "./Tour_Item_mini_Review.module.css";

const info = {
  tour: {
    image:
      "https://cdn.pixabay.com/photo/2016/11/29/05/45/astronomy-1867616_960_720.jpg",
    title: "투어 제목",
    nav: {
      image:
        "https://cdn.pixabay.com/photo/2016/11/29/05/45/astronomy-1867616_960_720.jpg",
      nickname: "가이드이름",
    },
  },
  progress: {
    date: "2021-09-01",
    participant: 2,
  },
};

const Tour_Item_mini_Review = ({ thumbnailImage, title, progress, nav }) => {
  return (
    <div className={styles.Tour_Item_mini_Review}>
      <section className={styles.tourImageSection}>
        <img src={thumbnailImage} alt="" />
      </section>
      <section className={styles.tourInfoSection}>
        <div className={styles.tourTextInfo}>
          <h3>{title}</h3>
          <div>{progress.date}</div>
          <div>{progress.participantCount}명</div>
        </div>
        <div className={styles.tourNavInfo}>
          <img src={nav.image} alt="" />
          <p>{nav.nickname}</p>
        </div>
      </section>
    </div>
  );
};

export default Tour_Item_mini_Review;
