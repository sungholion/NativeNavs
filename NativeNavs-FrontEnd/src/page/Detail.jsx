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
  const [user, setUser] = useState(null);
  // tour state 정의
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

  // review state 정의
  const [reviewData, setReviewData] = useState({
    imageUrls: [],
    reviewAverage: 0,
    reviewCount: 0,
    reviews: [],
    totalImageCount: 0,
  });

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
    }
  }, []);
  // FE -> BE : Tour API 요청
  useEffect(() => {
    const fetchTour = async () => {
      try {
        const response = await axios.get(
          `https://i11d110.p.ssafy.io/api/tours/${params.tour_id}`
        );
        setTour(response.data);
        console.log("Tours response data : ", response.data);
        console.log("Tours response data : ", response.data.user.id);
        console.log(tour);
      } catch (error) {
        console.error("Error fetching tours:", error);
      }
    };

    fetchTour();
  }, [user]);

  // NavLanguages 관리 state
  const [navLanguages, setNavLanguages] = useState([]);
  useEffect(() => {
    if (tour && tour.user && tour.user.userLanguage) {
      const userLanguage = tour.user.userLanguage
        .split(",")
        .map((lang) => lang.trim());
      setNavLanguages(userLanguage);
      console.log(navLanguages);
    }
  }, [tour]);

  const images = [tour.thumbnailImage, ...tour.plans.map((plan) => plan.image)];

  // FE -> BE : ReviewData API 요청
  useEffect(() => {
    const fetchReviewData = async () => {
      try {
        const response = await axios.get(
          `https://i11d110.p.ssafy.io/api/reviews/tour/${params.tour_id}`
        );
        setReviewData(response.data);
        console.log("Reviews response data : ", response.data);
      } catch (error) {
        console.error("Error fetching reviewData:", error);
      }
    };

    fetchReviewData();
  }, []);

  // 첫 번째 리뷰를 변수에 저장
  const firstReview =
    reviewData.reviews.length > 0 ? reviewData.reviews[0] : null;
  console.log(firstReview);

  // MB : Nav 프로필 클릭 이벤트 정의
  const onClickNav = (e) => {
    if (
      window.Android &&
      typeof window.Android.navigateToNavProfileFragment === "function"
    ) {
      window.Android.navigateToNavProfileFragment(parseInt(tour.user.id));
    } else {
      console.log("Android.navigateToNavProfileFragment is not defined");
    }
  };

  // MB : 리뷰 클릭 이벤트 정의
  const onClickReview = (e) => {
    if (
      window.Android &&
      typeof window.Android.navigateToReviewListFragment === "function"
    ) {
      window.Android.navigateToReviewListFragment(parseInt(tour.id));
    } else {
      console.log("Android.navigateToReviewListFragment is not defined");
    }
  };

  // Date 객체 formatting
  const formattedStartDate = tour.startDate
    ? new Date(tour.startDate).toLocaleDateString()
    : "N/A";
  const formattedEndDate = tour.endDate
    ? new Date(tour.endDate).toLocaleDateString()
    : "N/A";

  // price 변수 fotmatting
  const formattedPrice = tour.price.toLocaleString();

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
              {navLanguages.length > 1 ? (
                <p>
                  {navLanguages[0]} 외 {navLanguages.length - 1}개 국어
                </p>
              ) : (
                <p>{navLanguages[0]}</p>
              )}
            </div>
          </div>
        </div>
      </section>

      {/* Nav 정보 */}
      <div className={styles.navInfo}>
        <div className={styles.navInfo_inner} onClick={onClickNav}>
          <div className={styles.navInfoImage}>
            {tour.user ? (
              <img
                src={tour.user.image}
                alt={tour.userId}
                className={styles.nav_img}
              />
            ) : (
              <div></div>
            )}
          </div>
          <div className={styles.navInfoText}>
            {tour && tour.user ? (
              <p className={styles.navNickname}>
                Navs : {tour.user.nickname}님
              </p>
            ) : (
              <p>loading..</p>
            )}
            <p className={styles.navLanguage}>
              언어 : {navLanguages.join(", ")}
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
        {firstReview ? (
          <Review_Item
            user={firstReview.reviewer}
            score={firstReview.score}
            description={firstReview.description}
            tour={firstReview.tourTitle}
            needToShowTourTitle={false}
            imageList={firstReview.imageUrls}
          />
        ) : (
          <p>첫 리뷰를 남겨주세요!</p>
        )}
      </div>
    </div>
  );
};

export default Detail;
