import React, { useState, useEffect } from "react";
import Rating from "../Star/Rating(Basic)";
import Heart from "../Heart/Heart";
import styles from "./Tour_Item.module.css";
import axios from "axios";

const Tour_Item = ({
  tourId = -1,
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
  userLanguages,
}) => {
  const [isWishListed, setIsWishListed] = useState(false);

  // 위시리스트 API
  const fetchWishLists = async () => {
    if (user && user.isNav == false) {
      console.log("위시리스트 체크 API 요청 시작");
      try {
        const response = await axios.get(
          `https://i11d110.p.ssafy.io/api/wishlist/check/${tourId}`,
          {
            headers: {
              Authorization: `Bearer ${user.userToken}`,
              accept: "application/json",
            },
          }
        );
        console.log("위시리스트 체크 API 응답 성공", response.data);
        setIsWishListed(response.data);
      } catch (error) {
        console.error(error);
      }
    }
  };

  // tourId가 변경될 때마다 isWishListed를 업데이트
  useEffect(() => {
    fetchWishLists();
  }, [tourId]);

  // 투어 클릭 이벤트
  const onClickTour = (e) => {
    // 네이티브 안드로이드 브릿지를 사용해 투어 상세 페이지로 이동
    console.log(parseInt(tourId));
    console.log(userId);
    console.log(user);
    navigateFragment(parseInt(tourId), parseInt(userId));
  };

  const toggleWishlist = async (e) => {
    // 투어 상세 페이지 이동 이벤트 전파 방지
    e.stopPropagation();
    try {
      if (isWishListed) {
        // 위시리스트에서 삭제
        await axios.delete(
          `https://i11d110.p.ssafy.io/api/wishlist/${tourId}`,
          {
            headers: {
              Authorization: `Bearer ${user.userToken}`,
            },
          }
        );
      } else {
        // 위시리스트에 등록
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
    }
  }, [userLanguages]);

  return (
    <div className={styles.Tour_Item} onClick={onClickTour}>
      <div className={styles.thumbnail_container}>
        <img src={thumbnailImage} alt="" className={styles.tour_thumbnail} />
        {!user.isNav && (
          <div className={styles.heart_container}>
            <Heart isWishListed={isWishListed} onClickEvent={toggleWishlist} />
          </div>
        )}
      </div>

      <section className={styles.infoContainer}>
        <div className={styles.infoTopContainer}>
          <div className={styles.infoTopLeftContainer}>
            <p className={styles.tour_title}>{title}</p>
            <p className={styles.tour_duration}>
              {startDate} ~ {endDate}
            </p>
          </div>
          <div className={styles.infoTopRightContainer}>
            <div className={styles.tour_nav}>
              <img
                src={nav_profile_img}
                alt={nickname}
                className={styles.nav_img}
              />
              <p className={styles.tour_nav}>{nickname}</p>
            </div>
          </div>
        </div>
        <div className={styles.infoBottomContainer}>
          <Rating reviewAverage={reviewAverage} />

          {navLanguages.length > 1 ? (
            <p className={styles.navLanguages}>
              {user && user.isKorean
                ? `🌏 ${navLanguages[0]} 외 ${navLanguages.length - 1}개 국어`
                : `🌏 ${navLanguages[0]} and ${navLanguages.length - 1} other`}
            </p>
          ) : (
            <p className={styles.navLanguages}>🌏 {navLanguages[0]}</p>
          )}
        </div>
      </section>
    </div>
  );
};

export default Tour_Item;
