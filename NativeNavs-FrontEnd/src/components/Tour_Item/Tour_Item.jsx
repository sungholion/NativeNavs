import React, { useState, useEffect } from "react";
import Rating from "../Star/Rating(Basic)";
import Heart from "../Heart/Heart";
import styles from "./Tour_Item.module.css";
import axios from "axios";

const Tour_Item = ({
  tourId,
  userId,
  title,
  thumbnailImage,
  startDate,
  endDate,
  reviewAverage,
  nav_profile_img,
  nickname,
  navigateFragment,
  user,
  wishList,
  userLanguages,
}) => {
  const [isWishListed, setIsWishListed] = useState(false);

  // wishList가 변경될 때마다 isWishListed를 업데이트
  useEffect(() => {
    setIsWishListed(wishList ? wishList.includes(tourId) : false);
  }, [wishList, tourId]);

  // 투어 클릭 이벤트
  const onClickTour = (e) => {
    // 네이티브 안드로이드 브릿지를 사용해 투어 상세 페이지로 이동
    console.log(parseInt(tourId));
    console.log(userId);
    console.log(user);
    navigateFragment(parseInt(tourId), parseInt(userId));
  };

  const toggleWishlist = async (e) => {
    e.stopPropagation(); // 이벤트 전파 방지
    try {
      if (isWishListed) {
        // 위시리스트에서 제거
        await axios.delete(
          `https://i11d110.p.ssafy.io/api/wishlist/${tourId}`,
          {
            headers: {
              Authorization: `Bearer ${user.userToken}`,
            },
          }
        );
      } else {
        // 위시리스트에 추가
        await axios.post(
          `https://i11d110.p.ssafy.io/api/wishlist?tourId=${tourId}`,
          null,
          {
            headers: {
              Authorization: `Bearer ${user.userToken}`,
            },
          }
        );
      }
      // 위시리스트 상태를 토글
      setIsWishListed((prev) => !prev);
    } catch (error) {
      console.error("위시리스트 업데이트 중 오류 발생:", error);
    }
  };

  // NavLanguages 관리 state : 문자열을 배열로 변환
  const [navLanguages, setNavLanguages] = useState([]);
  useEffect(() => {
    if (userLanguages) {
      const userLanguageList = userLanguages
        .split(",")
        .map((lang) => lang.trim());
      setNavLanguages(userLanguageList);
      console.log(navLanguages);
    }
  }, [userLanguages]);

  return (
    <div className={styles.Tour_Item} onClick={onClickTour}>
      <div className={styles.thumbnail_container}>
        <img src={thumbnailImage} alt="" className={styles.tour_thumbnail} />
        {!user.isNav && (
          <div className={styles.heart_container}>
            <Heart
              isWishListed={isWishListed}
              onClickEvent={toggleWishlist}
              wishList={wishList}
            />
          </div>
        )}
      </div>

      <section className={styles.tour_info}>
        <div className={styles.tour_leftinfo}>
          <p className={styles.tour_title}>{title}</p>
          <p className={styles.tour_duration}>
            {startDate} ~ {endDate}
          </p>
          <Rating avg={reviewAverage} />
        </div>
        <div className={styles.tour_rightinfo}>
          <div className={styles.tour_nav}>
            <img
              src={nav_profile_img}
              alt={nickname}
              className={styles.nav_img}
            />
            <p style={{ cursor: "pointer" }}>{nickname}</p>
          </div>
          <div className={styles.navLanguagesContainer}>
            {navLanguages.length > 1 ? (
              <p className={styles.navLanguages}>
                🌏 {navLanguages[0]} 외 {navLanguages.length - 1}개
              </p>
            ) : (
              <p className={styles.navLanguages}>🌏 {navLanguages[0]}</p>
            )}
          </div>
        </div>
      </section>
    </div>
  );
};

export default Tour_Item;
