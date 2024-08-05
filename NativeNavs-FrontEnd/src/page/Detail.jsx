import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import styles from "./Detail.module.css";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Carousel from "@/components/Carousel/Carousel.jsx";
import Rating from "@/components/Star/Rating(Basic).jsx";
import Review_Item from "@/components/Review_Item/Review_Item.jsx";
import Plan_Item2 from "@/components/Plan_Item/Plan_Item2";

const Detail = () => {
  const params = useParams();
  const [tour, setTour] = useState({
    price: 0,
    title: "",
    maxParticipants: 0,
    startDate: "",
    endDate: "",
    reviewAverage: 0,
    thumbnailImage: "",
    categoryIds: [],
    userId: "",
    description: "",
    plans: [],
    removed: false,
  });

  // BE로 API 요청
  useEffect(() => {
    const fetchTour = async () => {
      try {
        const response = await axios.get(
          `https://i11d110.p.ssafy.io/api/tours/${params.tour_id}`
        );
        setTour(response.data);
      } catch (error) {
        console.error("Error fetching tours:", error);
      }
    };

    fetchTour();
  }, [params.tour_id]);
  
  const images = [tour.thumbnailImage, ...tour.plans.map((plan) => plan.image)];
  console.log(images);

  // MB로부터 유저 데이터 파싱
  useEffect(() => {
    window.getUserData = (userJson) => {
      console.log("Received user JSON:", userJson);
      try {
        const parsedUser = JSON.parse(userJson);
        console.log(`User ID: ${parsedUser.userId}`);
        console.log(`Token: ${parsedUser.userToken}`); // 후에 추가될 예정
        console.log(`isNav: ${parsedUser.isNav}`);
      } catch (error) {
        console.error("Failed to parse user JSON", error);
      }
    };
  }, []);


  const onClickNav = (e) => {
    e.stopPropagation(); // 이벤트 전파 방지
    if (
      window.Android &&
      typeof window.Android.navigateToNavProfileFragment === "function"
    ) {
      window.Android.navigateToNavProfileFragment(parseInt(tour.userId));
    } else {
      console.log("Android.navigateToNavProfileFragment is not defined");
    }
  };

  const onClickReview = (e) => {
    e.stopPropagation(); // 이벤트 전파 방지
    if (
      window.Android &&
      typeof window.Android.navigateToReviewListFragment === "function"
    ) {
      window.Android.navigateToReviewListFragment(parseInt(tour.id));
    } else {
      console.log("Android.navigateToReviewListFragment is not defined");
    }
  };

  const formattedStartDate = tour.startDate
    ? new Date(tour.startDate).toLocaleDateString()
    : "N/A";
  const formattedEndDate = tour.endDate
    ? new Date(tour.endDate).toLocaleDateString()
    : "N/A";

  const formattedPrice = tour.price.toLocaleString();

  // 예시 리뷰 데이터
  const reviewData = {
    user: {
      user_id: 1,
      image:
        "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMjEyMTZfMTMx%2FMDAxNjcxMTg2NTM1MDYx.0vuGB7rfq1YZPV1kA8Wbuz51yLAS5Tvs0Zeuhiz-kswg.0iqBKg3vLwCvwnln6AqxZpV67RYgvEQ8qV7Y2wnqoI4g.JPEG.loivme%2F%25B8%25F1%25B5%25B5%25B8%25AE.jpg&type=sc960_832",
      nickname: "오리",
      nation: "미국",
    },
    score: 4.2,
    description: "너무 맛있어요",
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

  if (!tour) {
    return <div>Loading...</div>;
  }

  return (
    <div className={styles.Detail}>
      {/* 투어 사진(캐러셀) */}
      <Carousel images={images} />

      {/* 투어 정보(간략하게) */}
      <section className={styles.tour_info}>
        {/* left */}
        <div className={styles.tour_leftinfo}>
          <h3 className={styles.tour_title}>{tour.title}</h3>
          <p className={styles.tour_maxParticipants}>
            최대 인원 : {tour.maxParticipants}명
          </p>
          <p className={styles.tour_duration}>
            {formattedStartDate} ~ {formattedEndDate}
          </p>
        </div>

        {/* right */}
        <div className={styles.tour_rightinfo}>
          <div className={styles.tour_rating}>
            <div className={styles.tour_rating_inner}>
              <Rating avg={tour.reviewAverage} />
            </div>
          </div>

          <div className={styles.tour_nav_language}>
            <div className={styles.tour_nav_language_inner}>
            🌏
              {tour.categoryIds.length > 1 ? (
                <p>
                  {tour.categoryIds[0]} 외 {tour.categoryIds.length - 1}개 국어
                </p>
              ) : (
                <p>{tour.categoryIds[0]}</p>
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
              src={tour.thumbnailImage}
              alt={tour.userId}
              className={styles.nav_img}
            />
          </div>
          <div className={styles.navInfoText}>
            <p className={styles.navNickname}>Navs : {tour.userId}님</p>
            <p className={styles.navLanguage}>
              언어 : {tour.categoryIds.join(", ")}
            </p>
          </div>
        </div>
      </div>
      {/* 투어 일정 */}
      <div className={styles.tourPlan}>
        <h3 className={styles.tourPlanTitle}>Plan</h3>
        <div className={styles.tourPlanContainer}>
          {tour.plans.map((plan) => (
            <Plan_Item2
              key={plan.id}
              field={plan.field}
              description={plan.description}
              image={plan.image}
              latitude={plan.latitude}
              longitude={plan.longitude}
              addressFull={plan.addressFull}
              enableDeleteOption={false}
            />
          ))}
        </div>
      </div>
      {/* 투어 예상금액 및 당부사항 */}
      <div className={styles.tourReminder}>
        <h3 className={styles.tourReminderPrive}>예상 금액</h3>
        <h4>{formattedPrice}₩</h4>
        <h3 className={styles.tourReminderDecription}>투어 설명</h3>
        <h4>{tour.description}</h4>
      </div>
      {/* 투어 리뷰 */}
      <div className="" onClick={onClickReview}>
        <Review_Item
          user={reviewData.user}
          tour_id={tour.id}
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
