import React from "react";
import styles from "./WishListItem.module.css";
import NativeNavs from "../../assets/NativeNavs.png";
import { tours } from "../../dummy.jsx";
import Button from "../Button/Button.jsx";
import Tour_Item from "../Tour_Item/Tour_Item.jsx";
import thumbnail_image from "../../assets/thumbnail_image.png";
import { useNavigate } from "react-router-dom";

// tour_id : 투어 고유 키값
// user_id : 유저 고유 키값
// title : 투어 제목 :String
// thumbnail_image : 투어 썸네일 이미지 : String
// start_date : 시작날짜 (yyyy-mm-dd) : string
// end_date : 끝 날짜 (yyyy-mm-dd) : string
// review_average : 리뷰 평균 점수 : number
// nav_profile_img : 해당 가이드의 프로필 이미지 링크 : string
// nav_nickname : 가이드 닉네임 : string
// nav_language : 가이드가 사용가능한 언어 목록 : string[]

const WishListItem = () => {
  const navigate = useNavigate();

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
                ? tour.start_date.toLocaleDateString()
                : "N/A";
              const formattedEndDate = tour.end_date
                ? tour.end_date.toLocaleDateString()
                : "N/A";

              return (
                <Tour_Item
                  key={tour.tour_id}
                  tour_id={tour.tour_id}
                  user_id={tour.user_id}
                  title={tour.title}
                  thumbnail_image={tour.image || thumbnail_image}
                  start_date={formattedStartDate}
                  end_date={formattedEndDate}
                  review_average={tour.review_average}
                  nav_profile_img={tour.img_url}
                  nav_nickname={tour.nickname}
                  nav_language={tour.language ? tour.language.split(", ") : []}
                />
              );
            })}
          </div>
        ) 
        
        : (
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
