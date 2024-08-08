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
  user, // 추가: user 정보를 props로 받음
  wishList,
}) => {
  const [isWishListed, setIsWishListed] = useState(
    wishList ? wishList.includes(tourId) : false
  );

  // const images = [thumbnailImage, ...plans.map((plan) => plan.image)];
  // 투어 클릭 이벤트
  const onClickTour = (e) => {
    e.stopPropagation(); // 이벤트 전파 방지
    // 네이티브 안드로이드 브릿지를 사용해 투어 상세 페이지로 이동
    navigateFragment(parseInt(tourId), parseInt(userId));
  };

  useEffect(() => {
    console.log(isWishListed);
  }, [isWishListed]);

  const toggleWishlist = async (e) => {
    try {
      if (isWishListed) {
        // 위시리스트에서 제거
        await axios.delete(
          `https://i11d110.p.ssafy.io/api/wishlist/${tourId}`,
          {
            headers: {
              Authorization: user.userToken,
            },
          }
        );
      } else {
        console.log(tourId);
        // 위시리스트에 추가
        await axios.post(
          `https://i11d110.p.ssafy.io/api/wishlist?tourId=${tourId}`,
          null,
          {
            headers: {
              // Authorization: `Bearer ${user.userToken}`,
              Authorization: user.userToken,
            },
          }
        );
      }
      setIsWishListed((isWishListed) => !isWishListed);
      console.log(isWishListed);
      console.log("하트클릭");
    } catch (error) {
      console.error("위시리스트 업데이트 중 오류 발생:", error);
    }
  };

  return (
    <div className={styles.Tour_Item} onClick={onClickTour}>
      {/* 투어 이미지 */}
      <div className={styles.thumbnail_container}>
        <img src={thumbnailImage} alt="" className={styles.tour_thumbnail} />
        {/* <Carousel4 images={images} /> */}
        {/* <Carousel images={images} /> */}
        {/* {images.length > 1 ? (
          <Carousel images={images} />
        ) : (
          <img src={thumbnailImage} alt="" className={styles.tour_thumbnail} />
        )} */}
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

      {/* 투어 정보 */}
      <section className={styles.tour_info}>
        {/* 왼쪽 정보 */}
        <div className={styles.tour_leftinfo}>
          <p className={styles.tour_title}>{title}</p>
          <p className={styles.tour_duration}>
            {startDate} ~ {endDate}
          </p>
          <Rating avg={reviewAverage} />
        </div>
        {/* 오른쪽 정보 */}
        <div className={styles.tour_rightinfo}>
          <div className={styles.tour_nav}>
            {/* Nav 프로필 이미지 */}
            <img
              src={nav_profile_img}
              alt={nickname}
              className={styles.nav_img}
            />
            {/* Nav 닉네임 */}
            <p style={{ cursor: "pointer" }}>{nickname}</p>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Tour_Item;
