import React, { useState, useEffect } from "react";
import axios from "axios";
import Carousel2 from "@/components/Carousel/Carousel2";
import Tour_Item3 from "@/components/Tour_Item/Tour_Item3";
import Button from "@/components/Button/Button";
import styles from "./ReservationList.module.css";
import {
  navigateToReservationListFragmentReservationDetail,
  navigateToReservationListFragmentTourList,
} from "../utils/get-android-function";
import NativeNavs from "../assets/NativeNavs.png";
import NativeNavsRemoveNeedle from "../assets/NativeNavsRemoveNeedle.png";
import compassNeedleRemoveBack from "../assets/compassNeedleRemoveBack.png";

const ReservationList = () => {
  const [user, setUser] = useState(null);
  const [reservationsInProgress, setreservationsInProgress] = useState([]);
  const [reservationsCompleted, setreservationsCompleted] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isReadyToDisplay, setIsReadyToDisplay] = useState(false);

  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  const getReservationList = async (e) => {
    try {
      const response = await axios.get(
        "https://i11d110.p.ssafy.io/api/reservations",
        {
          headers: {
            Authorization: user.userToken,
          },
        }
      );
      setreservationsInProgress(response.data.reservationsInProgress);
      setreservationsCompleted(response.data.reservationsCompleted);
      console.log("받아온 정보:", response.data);
    } catch (error) {
      console.error("투어 예약 리스트 받아오기 실패:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getReservationList();
  }, [user]);

  useEffect(() => {
    const timer = setTimeout(() => {
      if (!loading) {
        setIsReadyToDisplay(true);
      }
    }, 500);

    return () => clearTimeout(timer);
  }, [loading]);

  if (!isReadyToDisplay) {
    return (
      <div className={styles.compassContainer}>
        <img
          src={NativeNavsRemoveNeedle}
          alt="Compass Background"
          className={styles.backgroundImage}
        />
        <img
          src={compassNeedleRemoveBack}
          alt="Compass Needle"
          className={styles.needle}
        />
      </div>
    );
  }

  if (reservationsInProgress.length != 0) {
    return (
      <>
        <h2 className={styles.TourListTitle}>
          {user && user.isKorean ? "예정된 Tour" : "Upcoming Tour"}
        </h2>

        <div className={styles.upcomingTourList}>
          {reservationsInProgress.length > 0 && (
            <Carousel2
              reservationsInProgress={reservationsInProgress}
              navigateToReservationListFragmentReservationDetail={
                navigateToReservationListFragmentReservationDetail
              }
              user={user}
            />
          )}
        </div>
        <h2 className={styles.TourListTitle}>
          {user && user.isKorean ? "완료된 Tour" : "Completed Tour"}
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
  }
  return (
    <>
      <h2 className={styles.TourListTitle}>
        {user && user.isKorean == true ? "예정된 Tour" : "Upcoming Tour"}
      </h2>
      <div className={styles.TopContainer}>
        <div className={styles.MiddleContainer}>
          <img
            className={styles.NativeNavsImg}
            src={NativeNavs}
            alt="NativeNavs"
          />
          <h2>
            {user && user.isKorean == true ? (
              "아직 예약한 Tour가 없어요!"
            ) : (
              <>No upcoming tours booked!</>
            )}
          </h2>
          <h5>
            {user && user.isKorean == true
              ? "NativeNavs를 통해 한국에서 특별한 추억을 만들어 보세요!"
              : "Create special memories in Korea with NativeNavs!"}
          </h5>
          <Button
            size="4"
            text={user && user.isKorean ? "둘러보기" : "Browse"}
            onClickEvent={() => {
              navigateToReservationListFragmentTourList();
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
