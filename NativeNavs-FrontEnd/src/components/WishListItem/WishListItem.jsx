import React from "react";
import styles from "./WishListItem.module.css";
import NativeNavs from "../../assets/NativeNavs.png";
import Button from "../Button/Button.jsx";
import Tour_Item from "../Tour_Item/Tour_Item.jsx";
import {
  navigateToWishDetailFragment,
  navigateFromWishToTourListFragment,
} from "../../utils/get-android-function"; // 함수 임포트

const WishListItem = ({ user, tours, wishList }) => {
  const wishListedTours = tours.filter((tour) => wishList.includes(tour.id));
  console.log(wishList);

  // tour date formatting
  const formatDate = (date) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const dateString = new Date(date).toLocaleDateString("ko-KR", options);
    return dateString.replace(/\.$/, "").replace(/\s/g, ""); // 마지막 점 제거 후 공백 제거
  };
  return (
    <div className={styles.TotalContainer}>
      {/* 위시리스트 */}
      <div className={styles.WishListContainer}>
        {/* 삼항 연산자 */}
        {wishListedTours.length > 0 ? (
          // 예정된 여행이 있는 경우
          <div>
            {wishListedTours.map((tour) => {
              return (
                <Tour_Item
                  key={tour.id}
                  tourId={tour.id}
                  userId={tour.user.userId}
                  title={tour.title}
                  thumbnailImage={tour.thumbnailImage}
                  startDate={formatDate(tour.startDate)} // 'yyyy-mm-dd' 형식으로 바꾸기 위해 toLocaleDateString() 사용
                  endDate={formatDate(tour.endDate)} // 'yyyy-mm-dd' 형식으로 바꾸기 위해 toLocaleDateString() 사용
                  reviewAverage={tour.reviewAverage}
                  user={user} // 파싱된 유저 정보를 Tour_Item에 전달
                  nav_profile_img={tour.user.image}
                  nickname={tour.user.nickname}
                  userLanguages={tour.user.userLanguage}
                  navigateFragment={navigateToWishDetailFragment}
                />
              );
            })}
          </div>
        ) : (
          // 예정된 여행이 없는 경우
          <div className={styles.TopContainer}>
            <img
              className={styles.NativeNavsImg}
              src={NativeNavs}
              alt="NativeNavs"
            />
            <h2>
              {user && user.isKorean ? (
                "아직 관심을 둔 Tour가 없어요!"
              ) : (
                <>
                  No tours saved yet!
                </>
              )}
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
                navigateFromWishToTourListFragment(); // 네이티브 함수 호출
              }}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default WishListItem;
