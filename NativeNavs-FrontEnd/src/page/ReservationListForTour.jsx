import React, { useState, useEffect } from "react";
import styles from "./ReservationListForTour.module.css";
import { useParams } from "react-router-dom";
import Tour_Item4 from "@/components/Tour_Item/Tour_Item4";
import Reservation_Item from "@/components/Reservation_Item/Reservation_Item";
import axios from "axios";
import { navigateToReservationDetailFragment } from "../utils/get-android-function";

const ReservationListForTour = () => {
  const [user, setUser] = useState(null);
  const [participantsInfo, setParticipantsInfo] = useState([]);
  const [tour, setTour] = useState();
  const [wishCount, setWishCount] = useState(0);
  const [reservationCount, setReservationCount] = useState(0);
  const params = useParams();

    // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
    useEffect(() => {
      setUser(JSON.parse(localStorage.getItem("user")));
    }, []);

  // 투어 참여자 조회 API 정의
  const fetchParticipantsInfo = async () => {
    try {
      const response = await axios.get(
        `https://i11d110.p.ssafy.io/api/reservations/tour/${params.tour_id}/participants`,
        {
          headers: {
            Authorization: `Bearer ${user.userToken}`,
            accept: "application/json",
          },
        }
      );
      console.log("투어 참여자 조회 API 요청 성공", response.data);
      setParticipantsInfo(response.data.reservationResponseDTOList);
      setTour(response.data.tourDTO);
      console.log();
      setWishCount(response.data.wishCount);
      setReservationCount(response.data.reservationResponseDTOList.length);
    } catch (error) {
      console.error("투어 참여자 조회 API 요청 실패", error);
    }
  };

  // user 정보를 받아오면 axios 실행
  useEffect(() => {
    fetchParticipantsInfo();
  }, [user]);

  return (
    <div className={styles.ReservationListForTour}>
      {/* 투어 정보 */}
      <div className={styles.TourInfo}>
        {tour ? (
          <Tour_Item4
            tour={tour}
            wishCount={wishCount}
            bookCount={reservationCount}
          />
        ) : null}
      </div>

      {/* 예약 목록 */}
      <div className={styles.ReservationList}>
        <div className={styles.ReservationCount}>
          <h4>
            {user && user.isKorean
              ? `📘총 ${reservationCount}개의 예약이 있습니다`
              : `📘You have ${reservationCount} reservations`}
          </h4>
        </div>
        {participantsInfo.length > 0 ? (
          participantsInfo.map((participantInfo, index) => (
            <Reservation_Item
              key={index}
              navigateToReservationDetailFragment={
                navigateToReservationDetailFragment
              }
              participantInfo={participantInfo}
              user={user}
            />
          ))
        ) : (
          <div></div>
        )}
      </div>
    </div>
  );
};

export default ReservationListForTour;
