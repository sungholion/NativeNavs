import styles from "./ReservationDetail.module.css";
import Carousel3 from "@/components/Carousel/Carousel3";
import { useParams } from "react-router-dom";
import messageBox from "../assets/messageBox.png";
import { useEffect, useState } from "react";
import axios from "axios";
import {
  navigateToReservationDetailChattingRoom,
  navigateBack,
} from "../utils/get-android-function";

const ReservationDetail = () => {
  const [tour, setTour] = useState();
  const [user, setUser] = useState();
  const [images, setImages] = useState([]);

  const params = useParams();
  console.log(params.res_id);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
      console.log(user);
    }
  }, []);

  // FE -> BE : 예정된 투어 정보 요청
  const getReservationDetail = async (e) => {
    try {
      const response = await axios.get(
        `https://i11d110.p.ssafy.io/api/reservations/${params.res_id}`,
        {
          headers: {
            Authorization: user.userToken,
            // Authorization:
              // "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlb2JsdWUyM0BnbWFpbC5jb20iLCJpYXQiOjE3MjMwNjQwNTMsImV4cCI6MTcyMzA2NzY1M30.Y_v9RMO9EOTFqRqB77IvYUmg_AZ99O2U_MF8Ptg8QsRMY5Hqj2DdmAQJgP3J5a6if4KtRMb9a_C_Nx8U6cx-Cw",
          },
        }
      );
      setTour(response.data);
      console.log("받아온 정보:", response.data);
    } catch (error) {
      console.error("투어 예약 리스트 받아오기 실패:", error);
    }
  };

  // user 상태가 설정된 후 예정된 투어 요청 함수 호출
  useEffect(() => {
    // if (user) {
    getReservationDetail();
    // }
  }, []);

  // tour 정보를 받아온 후 실행
  useEffect(() => {
    if (tour && tour.thumbnailImage && tour.planImages) {
      setImages([tour.thumbnailImage, ...tour.planImages]);
    }
  }, [tour]);

  const formatDate = (date) => {
    const dateObj = new Date(date);
    const year = dateObj.getFullYear();
    const month = String(dateObj.getMonth() + 1).padStart(2, "0");
    const day = String(dateObj.getDate()).padStart(2, "0");
    return `${year} / ${month} / ${day}`;
  };

  const formatTime = (time) => {
    const dateObj = new Date(`1970-01-01T${time}Z`);
    const hours = dateObj.getUTCHours();
    const minutes = String(dateObj.getUTCMinutes()).padStart(2, "0");
    const period = hours >= 12 ? "pm" : "am";
    const formattedHours = hours % 12 === 0 ? 12 : hours % 12;
    return `${formattedHours}:${minutes} ${period}`;
  };

  // 예약 번호 생성(임시)
  const generateReservationNumber = (length) => {
    const characters =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    let result = "";
    const charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
  };
  const reservationNumber = generateReservationNumber(16);


  return (
    <div>
      {tour && (
        <>
          {/* Top */}
          <div className={styles.tourInfoTop}>
            <div className={styles.tourInfoTopImage}>
              <Carousel3 images={images} />
            </div>
            <div className={styles.tourInfoTopTextTop}>
              <div className={styles.tourInfoTopTextLeft}>
                <p className={styles.value1}>만남 시작</p>
                <p className={styles.value2}>
                  {formatDate(tour.reservationDate)}
                </p>
                <p className={styles.value2}>{tour.meetingStartAt}</p>
              </div>
              <div className={styles.tourInfoTopTextRight}>
                <p className={styles.value1}>만남 시작</p>
                <p className={styles.value2}>
                  {formatDate(tour.reservationDate)}
                </p>
                <p className={styles.value2}>{formatTime(tour.meetingEndAt)}</p>
              </div>
            </div>
            {/* 채팅 아이디 수정 필요 */}
            <div
              onClick={() => navigateToReservationDetailChattingRoom()}
              className={styles.tourInfoTopTextBottom}
            >
              <img className={styles.messageBox} src={messageBox} alt="" />
              <div>
                <p className={styles.value3}>Nav에게 메세지 남기기</p>
                <p className={styles.value4}>{tour.guide.nickname}님</p>
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
                {tour.participantCount}명
              </p>
            </div>
            <div className={styles.tourInfoBottominfoItem}>
              <p className={styles.tourInfoBottominfoItemTitle}>예약 번호</p>
              <p className={styles.tourInfoBottominfoItemContent}>
                {reservationNumber}
              </p>
            </div>
            <div className={styles.tourInfoBottominfoItem}>
              <p className={styles.tourInfoBottominfoItemTitle}>만남 장소</p>
              {/* 구글 맵 API */}
              <p className={styles.tourInfoBottominfoItemContent}>
                {tour.meetingAddress}
              </p>
            </div>
            <div className={styles.tourInfoBottominfoItem}>
              <p className={styles.tourInfoBottominfoItemTitle}>
                추가 요청 사항
              </p>
              <p className={styles.tourInfoBottominfoItemContent}>
                {tour.reservationDescription}
              </p>
            </div>
            <div className={styles.buttonContainer}>
              <button className={styles.reserveButton}>예약 취소</button>
              
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default ReservationDetail;
