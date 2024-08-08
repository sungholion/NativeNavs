import React, { useState, useEffect } from "react";
import axios from "axios";
import Tour_Item from "../components/Tour_Item/Tour_Item";
import styles from "./Main.module.css";
import { navigateToTourDetailFragment } from "../utils/get-android-function";

const Main = () => {
  const [tours, setTours] = useState([]);
  const [user, setUser] = useState(null);
  const [wishList, setWishList] = useState(null);

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
    }
  }, []);

  // 위시리스트 API 
  const fetchWishLists = async () => {
    if (user && user.isNav == false) {
      try {
        const response = await axios.get(
          "https://i11d110.p.ssafy.io/api/wishlist",
          {
            headers: {
              Authorization: `Bearer ${user.userToken}`,
              accept: "application/json",
            },
          }
        );
        console.log("Fetched wishlist data:", response.data);
        setWishList(response.data.map((item) => item.id));
      } catch (error) {
        console.error(error);
      }
    }
  };
  const fetchTours = async () => {
    try {
      const response = await axios.get(
        "https://i11d110.p.ssafy.io/api/tours/search"
      );
      console.log("투어 API 요청 성공", response.data);
      console.log("위시리스트 API 요청 시작");
      setTours(response.data);
    } catch (error) {
      console.error("투어 API 요청 실패", error);
    }
  };
  // 투어 API
  useEffect(() => {
    console.log("투어 API 요청 시작");
    fetchTours();
    fetchWishLists();
  }, [user]);


  const formatDate = (date) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const dateString = new Date(date).toLocaleDateString("ko-KR", options);
    return dateString.replace(/\.$/, ""); // 마지막 점 제거
  };

  return (
    <div className={styles.main}>
      <div className={styles.tourList}>
        {tours.map((tour) => (
          <Tour_Item
            key={tour.id}
            tourId={tour.id}
            userId={tour.userId}
            title={tour.title}
            thumbnailImage={tour.thumbnailImage}
            startDate={formatDate(tour.startDate)} // 'yyyy-mm-dd' 형식으로 바꾸기 위해 toLocaleDateString() 사용
            endDate={formatDate(tour.endDate)} // 'yyyy-mm-dd' 형식으로 바꾸기 위해 toLocaleDateString() 사용
            reviewAverage={tour.reviewAverage}
            nav_profile_img={tour.user.image}
            nickname={tour.user.nickname}
            navigateFragment={navigateToTourDetailFragment}
            user={user} // 파싱된 유저 정보를 Tour_Item에 전달
            wishList={wishList}
          />
        ))}
      </div>
    </div>
  );
};

export default Main;
