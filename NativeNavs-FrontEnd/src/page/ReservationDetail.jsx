 import styles from "./ReservationDetail.module.css";
import Carousel3 from "@/components/Carousel/Carousel3";
import { upcomingTours } from "../dummy";
import { useParams } from "react-router-dom";
import messageBox from "../assets/messageBox.png";
import { useEffect, useState } from "react";
import axios from "axios";
import {navigateToReservationDetailChattingRoom, navigateBack} from "../utils/get-android-function"

const ReservationDetail = () => {
  const [tour, setTour] = useState();
  const [user, setUser] = useState();

  const params = useParams();
  const tour1 = upcomingTours[params.tour_id - 1];
  // console.log(tour1);
  const formatDate = (date) => {
    const options = { year: "numeric", month: "long", day: "numeric" };
    return date.toLocaleDateString("ko-KR", options);
  };

  const formatTime = (date) => {
    const options = { hour: "2-digit", minute: "2-digit" };
    return date.toLocaleTimeString("ko-KR", options);
  };

  // FE -> BE : 예정된 투어 정보 요청
  const getReservationList = async (e) => {
    try {
      const response = await axios.get(
        "https://i11d110.p.ssafy.io/api/reservations",
        {
          headers: {
            // Authorization: user.userToken,
            Authorization:
              "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkbGd1c3dsczQ1NkBuYXZlci5jb20iLCJpYXQiOjE3MjMwMTE3ODIsImV4cCI6MTcyMzAxNTM4Mn0.5SuyzqKssTYosZvzpkYznYkmMiL5kmT5HbjLW5AuK-c1H5VVVTU-ZJDNtoUT0HO9sW5ln6NISDCjvBHyNqRULw",
          },
        }
      );
      console.log("받아온 정보:", response.data);
    } catch (error) {
      console.error("투어 예약 리스트 받아오기 실패:", error);
    }
  };

  // user 상태가 설정된 후 예정된 투어 요청 함수 호출
  useEffect(() => {
    //   if (user && user.userToken) {
    getReservationList();
    // }
  }, [user]);

  return (
    <div>
      {/* Top */}
      <div className={styles.tourInfoTop}>
        <div className={styles.tourInfoTopImage}>
          <Carousel3 images={tour1.thumbnailImage} />
        </div>
        <div className={styles.tourInfoTopTextTop}>
          <div className={styles.tourInfoTopTextLeft}>
            <p className={styles.value1}>만남 시작</p>
            <p className={styles.value2}>{formatDate(tour1.start_date)}</p>
            <p className={styles.value2}>{formatTime(tour1.start_date)}</p>
          </div>
          <div className={styles.tourInfoTopTextRight}>
            <p className={styles.value1}>만남 시작</p>
            <p className={styles.value2}>{formatDate(tour1.end_date)}</p>
            <p className={styles.value2}>{formatTime(tour1.end_date)}</p>
          </div>
        </div>
        <div className={styles.tourInfoTopTextBottom}>
          <img className={styles.messageBox} src={messageBox} alt="" />
          <div>
            <p className={styles.value3}>Nav에게 메세지 남기기</p>
            <p className={styles.value4}>{tour1.nickname}</p>
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
            {tour1.trav_people}
          </p>
        </div>
        <div className={styles.tourInfoBottominfoItem}>
          <p className={styles.tourInfoBottominfoItemTitle}>예약 번호</p>
          <p className={styles.tourInfoBottominfoItemContent}>
            {tour1.reservation_number}
          </p>
        </div>
        <div className={styles.tourInfoBottominfoItem}>
          <p className={styles.tourInfoBottominfoItemTitle}>만남 장소</p>
          <p className={styles.tourInfoBottominfoItemContent}>
            {tour1.meeting_place}
          </p>
        </div>
        <div className={styles.tourInfoBottominfoItem}>
          <p className={styles.tourInfoBottominfoItemTitle}>추가 요청 사항</p>
          <p className={styles.tourInfoBottominfoItemContent}>
            {tour1.additional_requests}
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
