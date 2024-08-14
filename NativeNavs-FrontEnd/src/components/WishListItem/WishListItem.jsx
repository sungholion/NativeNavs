import React, { useState, useEffect } from "react";
import styles from "./WishListItem.module.css";
import NativeNavs from "../../assets/NativeNavs.png";
import Button from "../Button/Button.jsx";
import Tour_Item from "../Tour_Item/Tour_Item.jsx";
import {
  navigateToWishDetailFragment,
  navigateFromWishToTourListFragment,
} from "../../utils/get-android-function";

import NativeNavsRemoveNeedle from "../../assets/NativeNavsRemoveNeedle.png";
import compassNeedleRemoveBack from "../../assets/compassNeedleRemoveBack.png";

const WishListItem = ({ user, tours, wishList = [], loading = true }) => {
  const [isReadyToDisplay, setIsReadyToDisplay] = useState(false);

  const wishListedTours = tours.filter((tour) => wishList.includes(tour.id));

  const formatDate = (date) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const dateString = new Date(date).toLocaleDateString("ko-KR", options);
    return dateString.replace(/\.$/, "").replace(/\s/g, "");
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      if (!loading) {
        setIsReadyToDisplay(true);
      }
    }, 500);

    return () => clearTimeout(timer);
  }, [loading]);

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
    <div className={styles.TotalContainer}>
      {wishListedTours.length === 0 ? (
        <div className={styles.NoneWishListContainer}>
          <img
            className={styles.NativeNavsImg}
            src={NativeNavs}
            alt="NativeNavs"
          />
          <h2>
            {user && user.isKorean
              ? "아직 관심을 둔 Tour가 없어요!"
              : "No tours saved yet!"}
          </h2>
          <h5>
            {user && user.isKorean
              ? "NativeNavs를 통해 한국에서 특별한 추억을 만들어 보세요!"
              : "Start your unique experience in Korea now with NativeNavs!"}
          </h5>
          <Button
            size="4"
            text={user && user.isKorean ? "둘러보기" : "Browse"}
            onClickEvent={() => {
              navigateFromWishToTourListFragment();
            }}
          />
        </div>
      ) : (
        <div className={styles.WishListContainer}>
          {wishListedTours.map((tour) => (
            <Tour_Item
              key={tour.id}
              tourId={tour.id}
              userId={tour.user.userId}
              title={tour.title}
              thumbnailImage={tour.thumbnailImage}
              startDate={formatDate(tour.startDate)}
              endDate={formatDate(tour.endDate)}
              reviewAverage={tour.reviewAverage}
              user={user}
              nav_profile_img={tour.user.image}
              nickname={tour.user.nickname}
              userLanguages={tour.user.userLanguage}
              navigateFragment={navigateToWishDetailFragment}
              categoryIds={tour.categoryIds}
              isWishPage={true}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default WishListItem;
