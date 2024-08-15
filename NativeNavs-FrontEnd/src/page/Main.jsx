import React, { useState, useEffect } from "react";
import axios from "axios";
import Tour_Item from "../components/Tour_Item/Tour_Item";
import styles from "./Main.module.css";
import { navigateToTourDetailFragment } from "../utils/get-android-function";
import NativeNavsRemoveNeedle from "../assets/NativeNavsRemoveNeedle.png";
import compassNeedleRemoveBack from "../assets/compassNeedleRemoveBack.png";

const Main = () => {
  const [tours, setTours] = useState();
  const [user, setUser] = useState(null);
  const [search, setSearch] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isReadyToDisplay, setIsReadyToDisplay] = useState(false);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
    }

    const storedSearch = localStorage.getItem("search");
    if (storedSearch) {
      const parsedSearch = JSON.parse(storedSearch);
      setSearch(parsedSearch);
      console.log(search);
    }
  }, []);

  const fetchTours = async () => {
    setLoading(true);
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
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (user && search) {
      console.log("API 요청 시작 - user와 search 상태:", { user, search });
      fetchTours();
      setUser(null);
      setSearch(null);
    }
  }, [user, search]);

  useEffect(() => {
    const timer = setTimeout(() => {
      if (!loading) {
        setIsReadyToDisplay(true);
      }
    }, 1000);

    return () => clearTimeout(timer);
  }, [loading]);

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
        {tours &&
          tours.map((tour) => (
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
