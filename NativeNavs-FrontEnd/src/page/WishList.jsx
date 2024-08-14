import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "./WishList.module.css";
import WishListItem from "../components/WishListItem/WishListItem";


function WishList() {
  const [user, setUser] = useState(null);
  const [tours, setTours] = useState([]);
  const [wishList, setWishList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
    }
  }, []);

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

  useEffect(() => {
    const fetchWishLists = async () => {
      if (user && user.isNav == false) {
        try {
          setLoading(true);
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
        } finally {
          setLoading(false);
        }
      }
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
