import React, { useState, useEffect } from "react";
import axios from "axios";
import Tour_Item from "../components/Tour_Item/Tour_Item";
import styles from "./Main.module.css";
import { navigateToTourDetailFragment } from "../utils/get-android-function";

const Main = () => {
  const [tours, setTours] = useState([]); // 이렇게 하면 map 이 실행되어도 오류가 발생하지 않음
  const [user, setUser] = useState(null);
  const [search, setSearch] = useState(null);
  const [wishList, setWishList] = useState(null);

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  // 위시리스트 API
  const fetchWishLists = async () => {
    if (user && user.isNav == false) {
      console.log("위시리스트 API 요청 시작");
      try {
        const wishResponse = await axios.get(
          "https://i11d110.p.ssafy.io/api/wishlist",
          {
            headers: {
              Authorization: `Bearer ${user.userToken}`,
              accept: "application/json",
            },
          }
        );
        console.log("위시리스트 API 요청 성공", wishResponse.data);
        setWishList(wishResponse.data.map((item) => item.id));
      } catch (error) {
        console.error(error);
      }
    }
  };

  // 투어 검색 API 정의
  const fetchTours = async () => {
    const category = search ? search.category.map(String).join(".") : "";
    try {
      console.log("투어 검색 API 요청 시작");
      console.log(
        `?location=${search.travel}&date=${search.date}&categoryId=${category}`
      );
      const tourResponse = await axios.get(
        `https://i11d110.p.ssafy.io/api/tours/search${
          search.travel || search.date || category
            ? `?location=${search.travel}&date=${search.date}&categoryId=${category}`
            : ""
        }`
      );
      console.log(
        `https://i11d110.p.ssafy.io/api/tours/search?location=${search.travel}&date=${search.date}&categoryId=${category} 로 요청을 보냄`
      );
      console.log("투어 검색 API 요청 성공", tourResponse.data);
      setTours(tourResponse.data);
    } catch (error) {
      console.error("투어 API 요청 실패", error);
    }
  };

  // user 정보로 useEffect(투어 API & 위시리스트 API)
  useEffect(() => {
    console.log("API 요청 시작");
    fetchTours();
    fetchWishLists();
  }, [user, search]);

  // tour date formatting
  const formatDate = (date) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const dateString = new Date(date).toLocaleDateString("ko-KR", options);
    return dateString.replace(/\.$/, "").replace(/\s/g, ""); // 마지막 점 제거 후 공백 제거
  };

  return (
    <div className={styles.main}>
      <div className={styles.tourList}>
        {tours.map((tour) => (
          <Tour_Item
            key={tour.id}
            tourId={tour.id}
            userId={tour.user.id}
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
            userLanguages={tour.user.userLanguage}
          />
        ))}
      </div>
    </div>
  );
};

export default Main;
