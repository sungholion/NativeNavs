import React from "react";
import { useParams, useNavigate } from "react-router-dom";
import { tours } from "../dummy";
import styles from "./Detail.module.css";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Carousel from "@/components/Carousel/Carousel.jsx";
import mountain1 from "../assets/mountain1.png";
import mountain2 from "../assets/mountain2.png";
import mountain3 from "../assets/mountain3.png";
import Rating from "@/components/Star/Rating(Basic).jsx";
import Review_Item from "@/components/Review_Item/Review_Item.jsx";

const Detail = () => {
  const navigate = useNavigate();
  const params = useParams();
  const tour = tours[params.tour_id];
  const images = [mountain1, mountain2, mountain3];

  const onClickNav = (e) => {
    e.stopPropagation(); // 이벤트 전파 방지
    if (
      window.TourListBridge &&
      typeof window.TourListBridge.showToast === "function"
    ) {
      window.TourListBridge.showToast(
        `${tour.nav_nickname} 프로필 페이지로 이동`
      );
    } else {
      console.log("TourListBridge.showToast is not defined");
    }
    navigate(`/nav/${tour.user_id}`);
  };

  // Date 객체를 문자열로 변환
  const formattedStartDate = tour.start_date
    ? tour.start_date.toLocaleDateString()
    : "N/A";

  return (
    <div className={styles.Detail}>
      {/* 투어 사진(캐러셀) */}
      <Carousel images={images} />

      {/* 투어 정보(간략하게) */}
      <section className={styles.tour_info}>
        <div className={styles.tour_leftinfo}>
          <p className={styles.tour_title}>{tour.title}</p>
          <p className={styles.tour_duration}>{formattedStartDate}</p>
        </div>
        <div className={styles.tour_rightinfo}>
          <div className={styles.tour_rating}>
            <div className={styles.tour_rating_inner}>
              <Rating avg={tour.review_average} />
            </div>
          </div>

          <div className={styles.tour_nav_language}>
            <div className={styles.tour_nav_language_inner}>
              <img src={"/src/assets/language.png"} alt="언어 이미지" />
              {tour.language.length > 1 ? (
                <p>
                  {tour.language[0]} 외 {tour.language.length - 1}개 국어
                </p>
              ) : (
                <p>{tour.language[0]}</p>
              )}
            </div>
          </div>
        </div>
      </section>

      {/* Nav 정보 */}
      <div className={styles.navInfo}>
        <div className={styles.navInfo_inner} onClick={onClickNav}>
          <div className={styles.navInfoImage}>
            <img
              src={tour.image}
              alt={tour.nav_nickname}
              className={styles.nav_img}
            />
          </div>
          <div className={styles.navInfoText}>
            <p className={styles.navNickname}>Navs : {tour.nickname}님</p>
            <p className={styles.navLanguage}>
              언어 : {tour.language.join(", ")}
            </p>
          </div>
        </div>
      </div>

      {/* 투어 일정 */}
      <div className={styles.tourPlan}>
        <h1 className={styles.tourPlanTitle}>Plan</h1>
        <p>plan1</p>
        <p>plan2</p>
        <p>plan3</p>
      </div>

      {/* 투어 당부사항 */}
      <div className={styles.tourReminder}>
        <h1 className={styles.tourReminderTitle}>당부사항</h1>
        <p>당부1</p>
        <p>당부2</p>
        <p>당부3</p>
      </div>

      {/* 투어 리뷰 */}
      <div className={styles.tourReview}>
        {/* <Review_Item /> */}
      </div>
    </div>
  );
};

export default Detail;
