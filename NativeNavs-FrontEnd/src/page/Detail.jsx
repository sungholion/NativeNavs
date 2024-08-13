import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import styles from "./Detail.module.css";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Carousel from "@/components/Carousel/Carousel.jsx";
import Review_Item from "@/components/Review_Item/Review_Item.jsx";
import Plan_Item2 from "@/components/Plan_Item/Plan_Item2";
import { getStaticImage } from "@/utils/get-static-image";
import {
  navigateToTourModifyFragment,
  navigateToTourListFragment,
} from "@/utils/get-android-function";
import NativeNavs from "@/assets/NativeNavs.png";
import StarScore2 from "../components/Star/StarScore2";
import Modal3 from "../components/Modal/Modal3";
import NativeNavsRemoveNeedle from "@/assets/NativeNavsRemoveNeedle.png";
import compassNeedleRemoveBack from "@/assets/compassNeedleRemoveBack.png";

const Detail = () => {
  const params = useParams();
  const [user, setUser] = useState(null);
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
  const [loading, setLoading] = useState(true);
  const [isReadyToDisplay, setIsReadyToDisplay] = useState(false);

  // review state 정의
  const [reviewData, setReviewData] = useState({
    imageUrls: [],
    reviewAverage: 0,
    reviewCount: 0,
    reviews: [],
    totalImageCount: 0,
  });

  const [showModal, setShowModal] = useState(false);
  const [selectedPlan, setSelectedPlan] = useState(null);

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
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
        setLoading(false); // 데이터 로드가 완료되면 로딩 상태를 false로 설정
        console.log("Tours response data : ", response.data);
      } catch (error) {
        console.error("Error fetching tours:", error);
        setLoading(false);
      }
    };

    if (user) {
      fetchTour();
    }
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
  const formattedPrice = `₩ ${tour.price.toLocaleString()}`;

  const handlePlanClick = (plan) => {
    setSelectedPlan(plan);
    setShowModal(true);

    // 모달이 열릴 때 body의 스크롤을 잠급니다.
    document.body.style.overflow = "hidden";
  };

  const closeModal = () => {
    setShowModal(false);
    setSelectedPlan(null);

    // 모달이 닫힐 때 body의 스크롤을 해제합니다.
    document.body.style.overflow = "auto";
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      if (!loading) {
        setIsReadyToDisplay(true);
      }
    }, 500);

    return () => clearTimeout(timer);
  }, [loading]);

  // 테마 맵핑 데이터
  const categoryMapping = {
    1: { ko: "시장", en: "Market" },
    2: { ko: "액티비티", en: "Activity" },
    3: { ko: "자연", en: "Nature" },
    4: { ko: "역사", en: "History" },
    5: { ko: "문화", en: "Culture" },
    6: { ko: "축제", en: "Festival" },
    7: { ko: "음식", en: "Food" },
    8: { ko: "트렌디", en: "Trendy" },
    9: { ko: "랜드마크", en: "Landmark" },
    10: { ko: "쇼핑", en: "Shopping" },
    11: { ko: "미용", en: "Beauty" },
    12: { ko: "사진", en: "Photography" },
  };

  // 테마 이름 가져오기
  const getCategoryNames = () => {
    return tour.categoryIds
      .map((id) => categoryMapping[id])
      .filter(Boolean)
      .map((category) => (user.isKorean ? category.ko : category.en))
      .join(", ");
  };

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
    <div className={styles.Detail}>
      {/* 투어 사진(캐러셀) */}
      {user && tour && Number(user?.userId) === Number(tour?.user?.id) && (
        <div className={styles.WriterOnlyOptionSection}>
          <img
            src={getStaticImage("menu_vertical_button")}
            style={{ width: "30px", height: "30px" }}
            onClick={() => setOpenOption((cur) => !cur)} // 토글
          />
          {openOption && (
            <div className={styles.WriterOptions}>
              <button
                className={styles.buttonEdit}
                onClick={() => {
                  navigateToTourModifyFragment(Number(params.tour_id));
                }}
              >
                {user && user.isKorean ? "수정" : "Edit"}
              </button>
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
      )}
      <Carousel tourId={tour.tourId} images={images} user={user} />

      {/* 투어 정보(간략하게) */}
      <div className={styles.tour_info}>
        {/* first */}
        <div className={styles.tour_info_first}>
          <h3 className={styles.tour_title}>{tour.title}</h3>
          <div>
            <StarScore2 score={tour.reviewAverage * 20} />
          </div>
        </div>
        {/* second */}
        <div className={styles.tour_info_first}>
          <div className={styles.tour_maxParticipants}>
            {user && user.isKorean
              ? `최대 인원 ${tour.maxParticipants}명`
              : `Maximum ${tour.maxParticipants} people`}
          </div>
          <div className={styles.categoryContainer}>
            {getCategoryNames() &&
              getCategoryNames()
                .split(", ")
                .slice(2) // 3번째 이후의 카테고리들을 가져옵니다.
                .map((category, index) => (
                  <div key={index + 2} className={styles.categoryBox}>
                    {category}
                  </div>
                ))}
          </div>
        </div>
        {/* third */}
        <div className={styles.tour_info_first}>
          <div>
            {formatDate(tour.endDate)} ~ {formatDate(tour.endDate)}
          </div>
          <div className={styles.categoryContainer}>
            {getCategoryNames()
              .split(", ")
              .slice(0, 2)
              .map((category, index) => (
                <div key={index} className={styles.categoryBox}>
                  {category}
                </div>
              ))}
          </div>
        </div>
      </div>

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
                {user && user.isKorean
                  ? `Nav: ${tour.user.nickname}님`
                  : `Nav: ${tour.user.nickname}`}
              </p>
            ) : (
              <p>loading..</p>
            )}
            <p className={styles.navLanguage}>
              {user && user.isKorean
                ? `🌏 언어: ${navLanguages.join(", ")}`
                : `🌏 Language: ${navLanguages.join(", ")}`}
            </p>
          </div>
        </div>
      </div>
      {/* 투어 일정 */}
      <div className={styles.tourPlan}>
        <h3 className={styles.tourPlanTitle}>
          {user && user.isKorean ? `일정` : `Plan`}
        </h3>
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
              onClick={() => handlePlanClick(plan)}
            />
          ))}
        </div>
      </div>
      {/* 투어 예상금액 및 당부사항 */}
      <div className={styles.tourReminder}>
        <h3 className={styles.tourReminderPrive}>
          {" "}
          {user && user.isKorean ? "예상 금액" : "Estimated Price"}
        </h3>
        <h4>{formattedPrice}</h4>
        <h3 className={styles.tourReminderDecription}>
          {user && user.isKorean ? "당부 사항" : "Reminder"}
        </h3>
        <h4>{tour.description}</h4>
      </div>
      {/* 투어 리뷰 */}
      <div className="" onClick={onClickReview}>
        <div className={styles.buttonContainer}>
          <button className={styles.Button}>
            {user && user.isKorean ? "전체 리뷰 보기 >" : "View All Reviews >"}
          </button>
        </div>
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
          <p>
            {user && user.isKorean
              ? "첫 리뷰를 남겨주세요!"
              : "Be the first to leave a review!"}
          </p>
        )}
      </div>

      {/* Plan 모달 */}
      {selectedPlan && (
        <Modal3 show={showModal} onClose={closeModal} plan={selectedPlan} />
      )}
    </div>
  );
};

export default Detail;
