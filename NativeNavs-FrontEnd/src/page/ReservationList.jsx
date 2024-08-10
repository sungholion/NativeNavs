import Carousel2 from "@/components/Carousel/Carousel2";
import styles from "./ReservationList.module.css";
import Tour_Item3 from "@/components/Tour_Item/Tour_Item3";
import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  navigateToReservationListFragmentReservationDetail,
  navigateToReservationListFragmentTourList,
} from "../utils/get-android-function";
import Button from "@/components/Button/Button";
import NativeNavs from "../assets/NativeNavs.png";

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

  // 유저 정보 가져오기
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  // FE -> BE : 예정된 투어 정보 요청
  const getReservationList = async (e) => {
    try {
      const response = await axios.get(
        "https://i11d110.p.ssafy.io/api/reservations",
        {
          headers: {
            Authorization: user.userToken,
            // Authorization:
            // "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MTIzNEBnbWFpbC5jb20iLCJpYXQiOjE3MjMwNzgzOTUsImV4cCI6MTcyMzA4MTk5NX0.xRtizR6U4bIh8VYnqNrpkRPobjS1bIhznIL1IYAYMRbcFPE0IROdhyi-GQWJhgXHXiX6wXX3VuctcQQOUxISCg",
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

  if (reservationsInProgress.length != 0) {
    return (
      <>
        <h2 className={styles.TourListTitle}>
          {user && user.isKorean == true  ? "예정된 Tour" : "Upcoming Tour"}
        </h2>
        <div className={styles.ReservationList}>
          {/* 예약된 투어 리스트 */}

          <h3 className={styles.reservationLength}>
            {/* 총 {tourData.length}개의 투어가 예약 중입니다 */}
          </h3>
          <div className={styles.upcomingTourList}>
            {/* <Carousel2 reservationsInProgress={reservationsInProgress} navigateToReservationListFragmentReservationDetail={() => navigateToReservationListFragmentReservationDetail(reservationsInProgress.tourId, reservationsInProgress.reservationId)} /> */}
            {reservationsInProgress.length > 0 && (
              <Carousel2
                reservationsInProgress={reservationsInProgress}
                navigateToReservationListFragmentReservationDetail={
                  navigateToReservationListFragmentReservationDetail
                }
              />
            )}
          </div>
          {/* 완료된 투어 리스트 */}
          <h2 className={styles.TourListTitle}>
            {user && user.isKorean == true  ? "완료된 Tour" : "Completed Tour"}
          </h2>
          <div className={styles.completedTourList}>
            {reservationsCompleted.map((tour) => (
              <Tour_Item3
                key={tour.tourId}
                tour={tour}
                navigateToReservationListFragmentReservationDetail={
                  navigateToReservationListFragmentReservationDetail
                }
              />
            ))}
          </div>
        </div>
      </>
    );
  }
  return (
    <>
      <h2 className={styles.TourListTitle}>
        {user && user.isKorean == true  ? "예정된 Tour" : "Upcoming Tour"}
      </h2>
      <div className={styles.TopContainer}>
        <div className={styles.MiddleContainer}>
          <img
            className={styles.NativeNavsImg}
            src={NativeNavs}
            alt="NativeNavs"
          />
          <h2>
            {user && user.isKorean == true 
              ? "아직 예약한 Tour가 없어요!"
              : "You have no booked tours yet!"}
          </h2>
          <h5>
            {user && user.isKorean == true 
              ? "NativeNavs를 통해 한국에서 특별한 추억을 만들어 보세요!"
              : "Create special memories in Korea with NativeNavs!"}
          </h5>
          <Button
            size="4"
            text={"둘러보기"}
            onClickEvent={() => {
              navigateToReservationListFragmentTourList(); // 네이티브 함수 호출
            }}
          />
        </div>
      </div>
      <h2 className={styles.TourListTitle}>
        {user && user.isKorean == true ? "완료된 Tour" : "Completed Tour"}
      </h2>
      <div className={styles.completedTourList}>
        {reservationsCompleted.map((tour) => (
          <Tour_Item3
            key={tour.tourId}
            tour={tour}
            navigateToReservationListFragmentReservationDetail={
              navigateToReservationListFragmentReservationDetail
            }
          />
        ))}
      </div>
    </>
  );
};
export default ReservationList;
