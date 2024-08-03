import styles from "./ReservationDetail.module.css";
import Carousel3 from "@/components/Carousel/Carousel3";
import { upcomingTours } from "../dummy";
import { useParams } from "react-router-dom";
import messageBox from "../assets/messageBox.png";

const ReservationDetail = () => {
  const params = useParams();
  const tour = upcomingTours[params.tour_id - 1];
  console.log(tour);
  const formatDate = (date) => {
    const options = { year: "numeric", month: "long", day: "numeric" };
    return date.toLocaleDateString("ko-KR", options);
  };

  const formatTime = (date) => {
    const options = { hour: "2-digit", minute: "2-digit" };
    return date.toLocaleTimeString("ko-KR", options);
  };
  return (
    <div>
      {/* Top */}
      <div className={styles.tourInfoTop}>
        <div className={styles.tourInfoTopImage}>
          <Carousel3 images={tour.thumbnailImage} />
        </div>
        <div className={styles.tourInfoTopTextTop}>
          <div className={styles.tourInfoTopTextLeft}>
            <p className={styles.value1}>만남 시작</p>
            <p className={styles.value2}>{formatDate(tour.start_date)}</p>
            <p className={styles.value2}>{formatTime(tour.start_date)}</p>
          </div>
          <div className={styles.tourInfoTopTextRight}>
            <p className={styles.value1}>만남 시작</p>
            <p className={styles.value2}>{formatDate(tour.end_date)}</p>
            <p className={styles.value2}>{formatTime(tour.end_date)}</p>
          </div>
        </div>
        <div className={styles.tourInfoTopTextBottom}>
          <img className={styles.messageBox} src={messageBox} alt="" />
          <div>
            <p className={styles.value3}>Nav에게 메세지 남기기</p>
            <p className={styles.value4}>{tour.nickname}</p>
          </div>
        </div>
      </div>

      {/* Middle */}
      <div className={styles.tourInfoMiddle}></div>

      {/* Bottom */}
      <div className={styles.tourInfoBottom}>
        <h3 className={styles.tourInfoBottomtitle}>예약 상세 내역</h3>
        <div className={styles.tourInfoBottominfoItem}>
          <p className={styles.tourInfoBottominfoItemTitle}>Trav 인원</p>
          <p className={styles.tourInfoBottominfoItemContent}>
            {tour.trav_people}
          </p>
        </div>
        <div className={styles.tourInfoBottominfoItem}>
          <p className={styles.tourInfoBottominfoItemTitle}>예약 번호</p>
          <p className={styles.tourInfoBottominfoItemContent}>
            {tour.reservation_number}
          </p>
        </div>
        <div className={styles.tourInfoBottominfoItem}>
          <p className={styles.tourInfoBottominfoItemTitle}>만남 장소</p>
          <p className={styles.tourInfoBottominfoItemContent}>
            {tour.meeting_place}
          </p>
        </div>
        <div className={styles.tourInfoBottominfoItem}>
          <p className={styles.tourInfoBottominfoItemTitle}>추가 요청 사항</p>
          <p className={styles.tourInfoBottominfoItemContent}>
            {tour.additional_requests}
          </p>
        </div>
        <div className={styles.buttonContainer}>
          <button className={styles.reserveButton}>예약하기</button>
        </div>
      </div>
    </div>
  );
};

export default ReservationDetail;
