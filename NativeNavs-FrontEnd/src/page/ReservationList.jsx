import Carousel2 from "@/components/Carousel/Carousel2";
import styles from "./ReservationList.module.css";
import { tours, upcomingTours, nav } from "../dummy";
import Tour_Item3 from "@/components/Tour_Item/Tour_Item3";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { navigateToReservationListFragmentReservationDetail } from "../utils/get-android-function";

const ReservationList = () => {
  const [user, setUser] = useState(null);
  const [reservationsInProgress, setreservationsInProgress] = useState([]);
  const [reservationsCompleted, setreservationsCompleted] = useState([]);

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
      console.log(user);
    }
  }, []);


  // // MB -> FE : 유저 정보 파싱
  // useEffect(() => {
  //   window.getUserData = (userJson) => {
  //     console.log("Received user JSON:", userJson);
  //     try {
  //       const parsedUser = JSON.parse(userJson);
  //       console.log(`User ID: ${parsedUser.userId}`);
  //       console.log(`Token: ${parsedUser.userToken}`);
  //       console.log(`isNav: ${parsedUser.isNav}`);
  //       setUser(parsedUser);
  //     } catch (error) {
  //       console.error("Failed to parse user JSON", error);
  //     }
  //   };
  // }, []);

  // FE -> BE : 예정된 투어 정보 요청
  const getReservationList = async (e) => {
    // e.stopPropagation();
    try {
      const response = await axios.get(
        "https://i11d110.p.ssafy.io/api/reservations",
        {
          headers: {
            Authorization: user.userToken,
            // Authorization:
            // "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkbGd1c3dsczQ1NkBuYXZlci5jb20iLCJpYXQiOjE3MjMwMTE3ODIsImV4cCI6MTcyMzAxNTM4Mn0.5SuyzqKssTYosZvzpkYznYkmMiL5kmT5HbjLW5AuK-c1H5VVVTU-ZJDNtoUT0HO9sW5ln6NISDCjvBHyNqRULw",
          },
        }
      );
      setreservationsInProgress(response.data.reservationsInProgress);
      setreservationsCompleted(response.data.reservationsCompleted);
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
    <div className={styles.ReservationList}>
      {/* 예약된 투어 리스트 */}
      <h3 className={styles.reservationLength}>
        {/* 총 {tourData.length}개의 투어가 예약 중입니다 */}
      </h3>
      <div className={styles.upcomingTourList}>
        <Carousel2 reservationsInProgress={reservationsInProgress} navigateToReservationListFragmentReservationDetail={navigateToReservationListFragmentReservationDetail} />
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
