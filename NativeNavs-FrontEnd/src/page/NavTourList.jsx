import styles from "./NavTourList.module.css";
import { useState, useEffect } from "react";
import Tour_Item4 from "@components/Tour_Item/Tour_Item4";
import axios from "axios";
import { navigateToMyTripDetailFragment } from "../utils/get-android-function";
import Button from "@/components/Button/Button";
import NativeNavs from "../../assets/NativeNavs.png";

const NavTourList = () => {
  const [user, setUser] = useState();
  const [tours, setTours] = useState([]);

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
      console.log(tours);
    } catch (error) {
      console.error("투어 API 요청 실패", error);
    }
  };

  // 유저 정보가 업데이트되면 tour api 요청을 실행
  useEffect(() => {
    console.log("투어 API 요청 시작");
    fetchTours();
  }, [user]);

  // 조건부 렌더링: tours 배열이 비어 있으면 예정된 여행이 없음을 표시
  if (tours.length === 0) {
    return (
      <div className={styles.TopContainer}>
        <img
          className={styles.NativeNavsImg}
          src={NativeNavs}
          alt="NativeNavs"
        />
        <h2>
          {user && user.isKorean ? (
            "아직 관심을 둔 Tour가 없어요!"
          ) : (
            <>
              You haven’t saved
              <br />
              any tours yet!
            </>
          )}
        </h2>
        <h5>
          {user && user.isKorean
            ? "Tour를 작성해 특별한 추억을 만들어 보세요!"
            : "Create special memories in Korea with NativeNavs!"}
        </h5>
        <Button
          size="4"
          text={user && user.isKorean ? "둘러보기" : "Browse"}
          onClickEvent={() => {
            alert('투어 작성 이동 함수 호출')
            // navigateFromWishToTourListFragment(); // 네이티브 함수 호출
          }}
        />
      </div>
    );
  }

  return (
    <div className={styles.NavTourList}>
      <div className={styles.TourList}>
        {tours.map((tour) => (
          <Tour_Item4
            key={tour.tourId}
            tour={tour}
            onClickEvent={navigateToMyTripDetailFragment}
          />
        ))}
      </div>
    </div>
  );
};

export default NavTourList;
