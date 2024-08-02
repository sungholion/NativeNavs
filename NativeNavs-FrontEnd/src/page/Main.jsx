import React, { useState, useEffect } from "react";
import axios from "axios";
import Tour_Item from "../components/Tour_Item/Tour_Item";
import styles from "./Main.module.css";
import { navigateToTourDetailFragment } from "../utils/get-android-function";

const Main = () => {
  const [tours, setTours] = useState([]);
  const [user, setUser] = useState(null);

  // axios get 요청을 통해 server로부터 JSON 정보
  useEffect(() => {
    // useEffect 안에서 비동기(async) 함수를 정의
    const fetchTours = async () => {
      try {
        const response = await axios.get(
          "https://i11d110.p.ssafy.io/api/tours"
        );
        setTours(response.data);
      } catch (error) {
        console.error("Error fetching tours:", error);
      }
    };

    // 정의한 비동기 함수를 즉시 호출
    fetchTours();
  }, []);

  // android로부터 유저 정보를 수신 및 파싱
  useEffect(() => {
    window.getUserData = (userJson) => {
      console.log("Received user JSON:", userJson);
      try {
        const parsedUser = JSON.parse(userJson);
        console.log(`User ID: ${parsedUser.userId}`);
        console.log(`User ID: ${parsedUser.userId}`);
        console.log(`Token: ${parsedUser.userToken}`); // 후에 추가될 예정
        console.log(`isNav: ${parsedUser.isNav}`);
        setUser(parsedUser);
      } catch (error) {
        console.error("Failed to parse user JSON", error);
      }
    };
  }, []);

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
            startDate={new Date(tour.startDate).toLocaleDateString()} // 'yyyy-mm-dd' 형식으로 바꾸기 위해 toLocaleDateString() 사용
            endDate={new Date(tour.endDate).toLocaleDateString()} // 'yyyy-mm-dd' 형식으로 바꾸기 위해 toLocaleDateString() 사용
            reviewAverage={tour.reviewAverage}
            nav_profile_img={tour.thumbnailImage}
            nav_nickname={tour.userId}
            navigateToTourDetailFragment={navigateToTourDetailFragment}
            user={user} // 파싱된 유저 정보를 Tour_Item에 전달
          />
        ))}
      </div>
    </div>
  );
};

export default Main;
