import styles from "./NavTourList.module.css";
import { useState, useEffect } from "react";
import Tour_Item4 from "@components/Tour_Item/Tour_Item4";
import axios from "axios";
import {
  navigateToMyTripDetailFragment,
  navigateToMyTripListToTourRegisterFragment,
} from "../utils/get-android-function";
import NativeNavs from "@/assets/NativeNavs.png";
import Button from "@/components/Button/Button";
import NativeNavsRemoveNeedle from "@/assets/NativeNavsRemoveNeedle.png";
import compassNeedleRemoveBack from "@/assets/compassNeedleRemoveBack.png";

const NavTourList = () => {
  const [user, setUser] = useState();
  const [tours, setTours] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isReadyToDisplay, setIsReadyToDisplay] = useState(false);

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
    }
  }, []);

  // 투어 api 요청 정의
  const fetchTours = async () => {
    try {
      const response = await axios.get(
        "https://i11d110.p.ssafy.io/api/tours/guide",
        {
          headers: {
            Authorization: `Bearer ${user.userToken}`,
            accept: "application/json",
          },
        }
      );
      console.log("투어 API 요청 성공", response.data);
      setTours(response.data);
    } catch (error) {
      console.error("투어 API 요청 실패", error);
    } finally {
      setLoading(false); // API 요청 완료 후 로딩 상태 false
    }
  };

  // 유저 정보가 업데이트되면 tour api 요청을 실행
  useEffect(() => {
    if (user) {
      console.log("투어 API 요청 시작");
      fetchTours();
    }
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

  return (
    <div className={styles.NavTourList}>
      {tours.length > 0 ? (
        <div className={styles.TourList}>
          {tours.map((tour) => (
            <Tour_Item4
              key={tour.tourId}
              tour={tour}
              wishCount={tour.wishedCount}
              bookCount={tour.reservationCount}
              onClickEvent={navigateToMyTripDetailFragment}
            />
          ))}
        </div>
      ) : (
        <div className={styles.TopContainer}>
          <div className={styles.MiddleContainer}>
            <img
              className={styles.NativeNavsImg}
              src={NativeNavs}
              alt="NativeNavs"
            />
            <h2>
              {user && user.isKorean ? (
                "아직 예약한 Tour가 없어요!"
              ) : (
                <>
                  You haven’t created
                  <br />
                  any tours yet!
                </>
              )}
            </h2>
            <h5>
              {user && user.isKorean
                ? "NativeNavs를 통해 한국에서 특별한 투어를 만들어 보세요!"
                : "Create special tours in Korea with NativeNavs!"}
            </h5>
            <Button
              size="4"
              text={user && user.isKorean ? "투어 등록하기" : "Create a Tour"}
              onClickEvent={() => {
                navigateToMyTripListToTourRegisterFragment(); // 네이티브 함수 호출
              }}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default NavTourList;
