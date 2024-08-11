import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "./WishList.module.css";
import WishListItem from "../components/WishListItem/WishListItem";

function WishList() {
  const [user, setUser] = useState(null);
  const [tours, setTours] = useState([]);
  const [wishList, setWishList] = useState([]);
  const [loading, setLoading] = useState(true); // 로딩 상태 추가

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
    }
  }, []);

  // 투어 API
  useEffect(() => {
    console.log("Tour API Run");
    const fetchTours = async () => {
      try {
        const response = await axios.get(
          "https://i11d110.p.ssafy.io/api/tours"
        );
        setTours(response.data);
      } catch (error) {
        console.error("Failed to get tours", error);
      }
    };
    fetchTours();
  }, []);

  // 위시리스트 API
  useEffect(() => {
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
      setLoading(false); // 데이터 로딩 완료 후 로딩 상태를 false로 변경
    };
    fetchWishLists();
  }, [user]);

  
  return (
    <div className={styles.container}>
      <WishListItem
        user={user}
        tours={tours}
        wishList={wishList}
        loading={loading}
      />
    </div>
  );
}

export default WishList;
