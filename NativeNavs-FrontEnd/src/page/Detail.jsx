import React, { useEffect } from "react";
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

const Detail = ({ userJson }) => {
  const navigate = useNavigate();
  const params = useParams();
  const tour = tours[params.tour_id - 1];
  const images = [mountain1, mountain2, mountain3];

  // 유저 정보 파싱 및 로그 출력
  useEffect(() => {
    console.log("Received user JSON:", userJson);
    try {
      const user = JSON.parse(userJson);
      console.log(`User ID: ${user.userId}`);
      console.log(`Token: ${user.userToken}`);
      console.log(`isNav: ${user.isNav}`);
    } catch (error) {
      console.error("Failed to parse user JSON", error);
    }
  }, [userJson]); // userJson 변경 시마다 실행

  const onClickNav = (e) => {
    e.stopPropagation(); // 이벤트 전파 방지
    if (
      window.Android &&
      typeof window.Android.navigateToNavProfileFragment === "function"
    ) {
      window.Android.navigateToNavProfileFragment(tour.user_id);
    } else {
      console.log("Android.navigateToNavProfileFragment is not defined");
    }
    navigate(`/nav/${tour.user_id}`);
  };

  // 리뷰 상세 페이지로 이동
  const onClickReview = (e) => {
    e.stopPropagation(); // 이벤트 전파 방지
    if (
      window.Android &&
      typeof window.Android.navigateToReviewListFragment === "function"
    ) {
      window.Android.navigateToReviewListFragment(tour.tour_id);
    } else {
      console.log("Android.navigateToReviewListFragment is not defined");
    }
    navigate(`/tour/detail/${tour.tour_id}/review`);
  };

  // Date 객체를 문자열로 변환
  const formattedStartDate = tour.start_date
    ? tour.start_date.toLocaleDateString()
    : "N/A";

  // 예시 리뷰 데이터
  const reviewData = {
    user: {
      user_id: 1,
      image:
        "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMjEyMTZfMTMx%2FMDAxNjcxMTg2NTM1MDYx.0vuGB7rfq1YZPV1kA8Wbuz51yLAS5Tvs0Zeuhiz-kswg.0iqBKg3vLwCvwnln6AqxZpV67RYgvEQ8qV7Y2wnqoI4g.JPEG.loivme%2F%25B8%25F1%25B5%25B5%25B8%25AE.jpg&type=sc960_832",
      nickname: "찌그렁오리",
      nation: "미국",
    },
    score: 4.2,
    description: "설명 설명~~",
    created_at: new Date(2024, 3, 2),
    tour: {
      tour_id: 2,
      title: "무진장 투어",
    },
    needToShowTourTitle: true,
    imageList: [
      "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNDA2MTlfMTgg%2FMDAxNzE4NzkzODA1MTQ5.5WZpqKWvIOCPc_v8V9tqTKZbQxC-cegb4Ql6zjOVdGgg.kOY5ndrPZE1VI_qj_5Mdoq0vjqAkx8bxEuzv0etqb-Ag.JPEG%2FIMG_9138.JPG&type=sc960_832",
      "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F20140922_33%2Fholaha00_1411349776141T4rkI_JPEG%2F%25B9%25D9%25B4%25D9_%25B9%25E8%25B0%25E6%25BB%25E7%25C1%25F8_%25B8%25F0%25C0%25BD_%25281%2529.jpg&type=sc960_832",
      "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA4MThfNDkg%2FMDAxNjI5Mjk0ODk2MTE3.9xQesy494fYu0DXNAk50hFRL3feTyiQAjP3FB5agcgog.s-21YxuWQNkPWcFv46a_i9krhMFZohStNgomCpu1E_gg.GIF.cooolsydney%2F%25BF%25C0%25B8%25AE%25BA%25D2%25B0%25ED%25B1%25E2.gif&type=a340",
    ],
  };

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
      투어 일정
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
      <div className={styles.tourReview} onClick={onClickReview}>
        <Review_Item
          user={reviewData.user}
          tour_id={tour.tour_id}
          score={reviewData.score}
          description={reviewData.description}
          created_at={reviewData.created_at}
          tour={reviewData.tour}
          needToShowTourTitle={reviewData.needToShowTourTitle}
          imageList={reviewData.imageList}
        />
      </div>
    </div>
  );
};

export default Detail;
