import React, { useState, useEffect } from "react";
import axios from "axios";
import Tour_Item from "../components/Tour_Item/Tour_Item";
import styles from "./Main.module.css";
import { navigateToTourDetailFragment } from "../utils/get-android-function";
import NativeNavsRemoveNeedle from "../assets/NativeNavsRemoveNeedle.png";
import compassNeedleRemoveBack from "../assets/compassNeedleRemoveBack.png";

const Main = () => {
  const [tours, setTours] = useState([]);
  const [user, setUser] = useState(null);
  const [search, setSearch] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isReadyToDisplay, setIsReadyToDisplay] = useState(false);

  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
    setSearch(JSON.parse(localStorage.getItem("search")));

    window.getSearchData = (searchJson) => {
      const parsedSearch = JSON.parse(searchJson);
      setSearch(parsedSearch);
      localStorage.setItem("search", searchJson);
    };
  }, []);

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
      console.log("투어 검색 API 요청 성공", tourResponse.data);
      setTours(tourResponse.data);
    } catch (error) {
      console.error("투어 API 요청 실패", error);
    } finally {
      setLoading(false);
    }
  };

  // 데이터 미리 로드
  useEffect(() => {
    if (user && search) {
      fetchTours();
    }
  }, [user, search]);

  // 페이지 전환 효과를 처리하면서 데이터 준비
  useEffect(() => {
    if (!loading) {
      // 1초 동안 전환 효과를 유지한 후 데이터 표시
      const timer = setTimeout(() => {
        setIsReadyToDisplay(true);
      }, 1000); // 1초 동안 로딩 상태를 유지

      return () => clearTimeout(timer);
    }
  }, [loading]);

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY === 0) {
        fetchTours();
      }
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, [search]);

  const formatDate = (date) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const dateString = new Date(date).toLocaleDateString("ko-KR", options);
    return dateString.replace(/\.$/, "").replace(/\s/g, "");
  };

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
            categoryIds={tour.categoryIds}
          />
        ))}
      </div>
    </div>
  );
};

export default Main;
