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
                  userId={tour.userId}
                  title={tour.title}
                  thumbnailImage={tour.thumbnailImage}
                  startDate={new Date(tour.startDate).toLocaleDateString()} // 'yyyy-mm-dd' 형식으로 바꾸기 위해 toLocaleDateString() 사용
                  endDate={new Date(tour.endDate).toLocaleDateString()} // 'yyyy-mm-dd' 형식으로 바꾸기 위해 toLocaleDateString() 사용
                  reviewAverage={tour.reviewAverage}
                  nav_profile_img={tour.user.image}
                  nickname={tour.user.nickname}
                  plans={tour.plans}
                  navigateFragment={navigateToWishDetailFragment}
                  user={user} // 파싱된 유저 정보를 Tour_Item에 전달
                  wishList={wishList}
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
              {user.isKorean
                ? "아직 관심을 둔 Tour가 없어요!"
                : "You haven’t saved any tours yet!"}
            </h2>
            <h5>
              {user.isKorean
                ? "NativeNavs를 통해 한국에서 특별한 추억을 만들어 보세요!"
                : "Create special memories in Korea with NativeNavs!"}
            </h5>
            <Button
              size="4"
              text={"둘러보기"}
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
