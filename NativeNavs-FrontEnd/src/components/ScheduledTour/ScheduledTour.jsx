import styles from "./ScheduledTour.module.css";
import NativeNavs from "../../assets/NativeNavs.png";
import { tours, nav } from "../../dummy";
import Button from "../Button/Button.jsx";
import Tour_Item2 from "../Tour_Item/Tour_Item2.jsx";
import thumbnail_image from "../../assets/thumbnail_image.png";

const ScheduledTour = () => {
  const onClickTour = (tour_Id) => {
    console.log(`${tour_Id}번 여행으로 이동`);
  };

  return (
    <div className={styles.TotalContainer}>
      {tours && tours.length > 0 ? (
        /* 예정된 여행이 있는 경우 */
        <div>
          {tours.map((tour) => {
            // 날짜를 문자열로 변환
            const formattedDate = tour.date.toLocaleDateString();

            return (
              <Tour_Item2
                key={tour.id}
                user_id={tour.user_id}
                title={tour.title}
                date={formattedDate}
                thumbnail_image={thumbnail_image}
                review_average={tour.review_average}
                image={tour.image}
                nickname={tour.nickname}
                language={tour.language}
                onClick={onClickTour(tour.id)}
              />
            );
          })}
        </div>
      ) : (
        /* 예정된 여행이 없는 경우 */
        <div className={styles.TopContainer}>
          <img className={styles.NativeNavsImg} src={NativeNavs} alt="" />
          <h3>예정된 여행이 없어요!</h3>
          <h6>NativeNavs를 통해 한국에서 특별한 추억을 만들어 보세요!</h6>{" "}
          <Button
            size="3"
            text={"둘러보기"}
            onClick={() => {
              "메인 페이지로 이동하는 라우팅 추가";
            }}
          />
        </div>
      )}
    </div>
  );
};

export default ScheduledTour;
