import React, { useState, useEffect } from "react";
import axios from "axios";
import Tour_Item from "../components/Tour_Item/Tour_Item";
import styles from "./Main.module.css";
import { navigateToTourDetailFragment } from "../utils/get-android-function";
import confetti from "canvas-confetti";

const Main = () => {
  const [tours, setTours] = useState([]);
  const [user, setUser] = useState(null);
  const [search, setSearch] = useState(null);

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
    setSearch(JSON.parse(localStorage.getItem("search")));

    // 안드로이드로부터 search 데이터를 받는 함수 정의
    window.getSearchData = (searchJson) => {
      const parsedSearch = JSON.parse(searchJson);
      setSearch(parsedSearch);
      localStorage.setItem("search", searchJson); // localStorage에도 저장
    };

  }, []);

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

  // search 상태가 변경될 때마다 투어 데이터를 다시 가져옴
  useEffect(() => {
    if (user && search) {
      console.log("API 요청 시작");
      fetchTours();
    }
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
            startDate={formatDate(tour.startDate)}
            endDate={formatDate(tour.endDate)}
            reviewAverage={tour.reviewAverage}
            nav_profile_img={tour.user.image}
            nickname={tour.user.nickname}
            navigateFragment={navigateToTourDetailFragment}
            user={user}
            userLanguages={tour.user.userLanguage}
          />
        ))}
      </div>
    </div>
  );
};

export default Main;
