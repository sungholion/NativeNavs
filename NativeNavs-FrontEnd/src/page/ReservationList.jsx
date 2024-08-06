import Carousel2 from "@/components/Carousel/Carousel2";
import styles from "./ReservationList.module.css";
import { tours, upcomingTours, nav } from "../dummy";
import Tour_Item3 from "@/components/Tour_Item/Tour_Item3";

const ReservationList = () => {
  // 필요한 정보를 포함한 객체 리스트 생성
  const tourData = upcomingTours.map(tour => ({
    thumbnail: tour.thumbnailImage[0],
    title: tour.title,
    date: tour.start_date,
    reviewAverage: tour.review_average,
    languages: tour.language,
    navImage: tour.image,
    navNickname: tour.nickname
  }));

  return (
    <div className={styles.ReservationList}>
      {/* 예약된 투어 리스트 */}
      <h3 className={styles.reservationLength}>총 {tourData.length}개의 투어가 예약 중입니다</h3>
      <div className={styles.upcomingTourList}>
        <Carousel2 tourData={tourData} />
      </div>
      {/* 완료된 투어 리스트 */}
      <h2 className={styles.TourListTitle}>완료된 Tour</h2>
      <div className={styles.completedTourList}>
        {tours.map((tour) => (
          <Tour_Item3 key={tour.tour_id} tour={tour} />
        ))}
      </div>
    </div>
  );
};

export default ReservationList;
