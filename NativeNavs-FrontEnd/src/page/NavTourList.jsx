import styles from "./NavTourList.module.css";
import { useState, useEffect } from "react";
import Tour_Item4 from "@components/Tour_Item/Tour_Item4";
import axios from "axios";

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

  return (
    <div className={styles.NavTourList}>
      <div className={styles.TourList}>
        {tours.map((tour) => (
          <Tour_Item4 key={tour.tourId} tour={tour} />
        ))}
      </div>
    </div>
  );
};

export default NavTourList;
