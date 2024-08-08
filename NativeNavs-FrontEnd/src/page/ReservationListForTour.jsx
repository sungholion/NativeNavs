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
  const [bookCount, setBookCount] = useState(0);
  const params = useParams();

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
    }
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
      setBookCount(response.data.bookCount);
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
          <Tour_Item4 tour={tour} wishCount={wishCount} bookCount={bookCount} />
        ) : null}
      </div>

      {/* 예약 목록 */}
      <div className={styles.ReservationList}>
        <div className={styles.ReservationCount}>
          <h4>📘총 {bookCount}개의 예약이 있습니다</h4>
        </div>
        {participantsInfo.length > 0 ? (
          participantsInfo.map((participantInfo) => (
            <Reservation_Item
              key={participantInfo.id}
              // onClick={() => navigateToReservationDetailFragment()} ★★★★★★★★★★★
              participantInfo={participantInfo}
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
