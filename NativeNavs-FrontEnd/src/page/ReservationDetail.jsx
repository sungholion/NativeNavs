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
import Modal2 from "../components/Modal/Modal2";

const ReservationDetail = () => {
  const [tour, setTour] = useState();
  const [user, setUser] = useState();
  const [images, setImages] = useState([]);

  // 모달
  const [modal, setModal] = useState(false);

  const clickModal = () => {
    setModal((current) => !current);
  };

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

  // FE -> BE : 투어 정보 요청 api
  const getReservationDetail = async (e) => {
    try {
      const response = await axios.get(
        `https://i11d110.p.ssafy.io/api/reservations/${params.res_id}`,
        {
          headers: {
            Authorization: user.userToken,
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
    getReservationDetail();
  }, [user]);

  // tour 정보를 받아온 후 실행
  useEffect(() => {
    if (tour && tour.thumbnailImage && tour.planImages) {
      setImages([tour.thumbnailImage, ...tour.planImages]);
    }
  }, [tour]);

  // FE -> BE : 투어 예약 취소 api
  const cancelTourReservation = async (e) => {
    try {
      const response = await axios.post(
        `https://i11d110.p.ssafy.io/api/reservations/${params.res_id}/cancel`,
        {}, // 빈 객체를 데이터로 전달 -> 세 번째 인자 headers를 전달해야하므로!
        {
          headers: {
            Authorization: user.userToken,
          },
        }
      );
      console.log("투어 예약 취소 결과:", response.data);
    } catch (error) {
      console.error("투어 예약 취소 실패:", error);
    }
  };

  // date formatting
  const formatDate = (date) => {
    const dateObj = new Date(date);
    const year = dateObj.getFullYear();
    const month = String(dateObj.getMonth() + 1).padStart(2, "0");
    const day = String(dateObj.getDate()).padStart(2, "0");
    return `${year} / ${month} / ${day}`;
  };

  // time formatting
  const formatTime = (time) => {
    const dateObj = new Date(`1970-01-01T${time}Z`);
    const hours = dateObj.getUTCHours();
    const minutes = String(dateObj.getUTCMinutes()).padStart(2, "0");
    const period = hours >= 12 ? "pm" : "am";
    const formattedHours = hours % 12 === 0 ? 12 : hours % 12;
    return `${formattedHours}:${minutes} ${period}`;
  };

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
                <p className={styles.value2}>
                  {formatTime(tour.meetingStartAt)}
                </p>
              </div>
              <div className={styles.tourInfoTopTextRight}>
                <p className={styles.value1}>만남 시작</p>
                <p className={styles.value2}>
                  {formatDate(tour.reservationDate)}
                </p>
                <p className={styles.value2}>{formatTime(tour.meetingEndAt)}</p>
              </div>
            </div>
            {/* 채팅 아이디 수정 필요 ★★★★★★★★★★★★★★ */}
            <div
              onClick={() => navigateToReservationDetailChattingRoom()}
              className={styles.tourInfoTopTextBottom}
            >
              <img className={styles.messageBox} src={messageBox} alt="" />
              {user && user.isNav == true ? (
                <div>
                  <p className={styles.value3}>Trav에게 메세지 남기기</p>
                  <p className={styles.value4}>{tour.participant.nickname}님</p>
                </div>
              ) : (
                <div>
                  <p className={styles.value3}>Nav에게 메세지 남기기</p>
                  <p className={styles.value4}>{tour.guide.nickname}님</p>
                </div>
              )}
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
                {tour.reservationNumber}
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
              {!modal ? (
                <button onClick={clickModal} className={styles.reserveButton}>
                  예약 취소
                </button>
              ) : (
                <Modal2
                  cancelTourReservation={cancelTourReservation}
                  navigateBack={navigateBack}
                  clickModal={clickModal}
                  className={styles.reserveButton}
                />
              )}
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default ReservationDetail;
