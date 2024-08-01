import React from "react";
import styles from "./WishListItem.module.css";
import NativeNavs from "../../assets/NativeNavs.png";
import { tours } from "../../dummy.jsx";
import Button from "../Button/Button.jsx";
import Tour_Item from "../Tour_Item/Tour_Item.jsx";
import thumbnail_image from "../../assets/thumbnail_image.png";
import { useNavigate } from "react-router-dom";
import { navigateToWishDetailFragment } from "../../utils/get-android-function"; // 함수 임포트

const WishListItem = () => {
  const navigate = useNavigate();

  // const onClickTour = (tour_id) => {
  //   console.log("hello");
  //   navigateToWishDetailFragment(tour_id); // 함수 호출
  // };

  return (
    <div className={styles.TotalContainer}>
      {/* 위시리스트 */}
      <div className={styles.WishListContainer}>
        {/* 삼항 연산자 */}
        {tours && tours.length > 0 ? (
          // 예정된 여행이 있는 경우
          <div>
            {tours.map((tour) => {
              const formattedStartDate = tour.start_date
                ? new Date(tour.start_date).toLocaleDateString("en-CA")
                : "N/A";
              const formattedEndDate = tour.end_date
                ? new Date(tour.end_date).toLocaleDateString("en-CA")
                : "N/A";

              return (
                <div key={tour.tour_id}>
                  <Tour_Item
                  navigateToTourDetailFragment={() => {navigateToWishDetailFragment(tour.id)}}
                    key={tour.tour_id}
                    // onClick={() => onClickTour(tour.tour_id)}
                    tour_id={tour.tour_id}
                    user_id={tour.user_id}
                    title={tour.title}
                    thumbnail_image={tour.image || thumbnail_image}
                    start_date={formattedStartDate}
                    end_date={formattedEndDate}
                    review_average={tour.review_average}
                    nav_profile_img={tour.img_url}
                    nav_nickname={tour.nickname}
                    nav_language={tour.language ? tour.language : []}
                  />
                </div>
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
            <h3>아직 관심을 둔 Tour가 없어요!</h3>
            <h6>NativeNavs를 통해 한국에서 특별한 추억을 만들어 보세요!</h6>
            <Button
              size="3"
              text={"둘러보기"}
              onClickEvent={() => {
                // main 주소로 이동하는 네비게이션 이벤트를 화살표 함수로 정의해서 props로 전달
                navigate("/main");
              }}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default WishListItem;
