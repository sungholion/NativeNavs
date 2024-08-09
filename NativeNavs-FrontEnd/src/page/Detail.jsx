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
import { getStaticImage } from "@/utils/get-static-image";
import {
  navigateToTourModifyFragment,
  navigateToTourListFragment,
} from "@/utils/get-android-function";
import NativeNavs from "@/assets/NativeNavs.png";

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
      console.log("User data : ", parsedUser);
    } else {
      console.log("No login user data");
    }
  }, []);

  const onDeleteEvent = async () => {
    await axios
      .delete(`https://i11d110.p.ssafy.io/api/tours/${params.tour_id}`, {
        headers: {
          Authorization: user.userToken,
        },
      })
      .then((res) => {
        console.log(res);
        navigateToTourListFragment();
      })
      .catch((err) => {
        console.err(err);
      });
  };

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

  // NavLanguages 관리 state : 문자열을 배열로 변환
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

  // 작성자 전용 -> 수정, 삭제 버튼 클릭 시 옵션창 열기
  const [openOption, setOpenOption] = useState(false);

  // 첫 번째 리뷰를 변수에 저장
  const firstReview =
    reviewData.reviews.length > 0 ? reviewData.reviews[0] : null;
  // console.log(firstReview);

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

  // tour date formatting
  const formatDate = (date) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const dateString = new Date(date).toLocaleDateString("ko-KR", options);
    return dateString.replace(/\.$/, "").replace(/\s/g, ""); // 마지막 점 제거 후 공백 제거
  };

  // price 변수 fotmatting
  const formattedPrice = tour.price.toLocaleString();

  return (
    <div className={styles.Detail}>
      {/* 투어 사진(캐러셀) */}
      {
        // 해당 글 작성자와 로그인한 유저가 같고, 글 작성자가 Nav인 경우
        // 수정 & 삭제 버튼을 보여줌
        user && tour && Number(user?.userId) === Number(tour?.user?.id) && (
          <div className={styles.WriterOnlyOptionSection}>
            <img
              src={getStaticImage("menu_vertical_button")}
              style={{ width: "30px", height: "30px" }}
              onClick={() => setOpenOption((cur) => !cur)} // 토글
            />
            {openOption && (
              <div className={styles.WriterOptions}>
                {/* 해당 버튼 클릭시 수정 버튼 이동 */}
                <button
                  className={styles.buttonEdit}
                  onClick={() => {
                    navigateToTourModifyFragment(Number(params.tour_id));
                  }}
                >
                  수정
                </button>
                {/* 해당 버튼 클릭시 삭제 버튼 이동 */}
                <button
                  className={styles.buttonDelete}
                  onClick={() => {
                    onDeleteEvent();
                  }}
                >
                  삭제
                </button>
              </div>
            )}
          </div>
        )
      }
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
            {formatDate(tour.endDate)} ~ {formatDate(tour.endDate)}
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
                <img className={styles.NativeNavs} src={NativeNavs} alt="Nav" />
                (Nav) : {tour.user.nickname}님
              </p>
            ) : (
              <p>loading..</p>
            )}
            <p className={styles.navLanguage}>
              🌏(Language) : {navLanguages.join(", ")}
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
