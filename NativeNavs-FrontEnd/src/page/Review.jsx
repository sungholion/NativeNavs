import React, { useEffect, useState } from "react";
import axios from "axios";
import styles from "./Review.module.css";
import StarScore from "@/components/Star/StarScore";
import Review_Item from "@/components/Review_Item/Review_Item";
import { useParams } from "react-router-dom";

const Review = ({ navigateToReviewPhotoFragment, keyword = "" }) => {
  const [user, setUser] = useState(null);
  const params = useParams();

  const [reviewData, setReviewData] = useState({
    imageUrls: [],
    reviewAverage: 0,
    reviewCount: 0,
    reviews: [],
    totalImageCount: 0,
  });

  const [travReviewData, setTravReviewData] = useState({
    reviewCount: 0,
    reviews: [],
  });

  // 유저 정보 가져오기
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  // FE -> BE : ReviewData API 요청
  useEffect(() => {
    const getUrlParam = () => {
      switch (keyword) {
        case "tour":
          return `https://i11d110.p.ssafy.io/api/reviews/tour/${params.tour_id}`;
        case "trav":
          return `https://i11d110.p.ssafy.io/api/reviews/user/${params.user_id}`;
        case "nav":
          return `https://i11d110.p.ssafy.io/api/reviews/guide/${params.user_id}`;
        default:
          return "";
      }
    };

    const fetchReviewData = async () => {
      const url = getUrlParam();
      if (!url) return;

      try {
        const response = await axios.get(url);
        if (keyword === "trav") {
          setTravReviewData(response.data);
        } else {
          setReviewData(response.data);
        }
        console.log("Reviews response data : ", response.data);
      } catch (error) {
        console.error("Error fetching reviewData:", error);
      }
    };

    fetchReviewData();
  }, [keyword]);

  // 페이지 이동 함수 매개변수 분기
  const onClickButton = () => {
    if (params.tour_id) {
      navigateToReviewPhotoFragment(parseInt(params.tour_id));
    } else {
      navigateToReviewPhotoFragment(parseInt(params.user_id));
    }
  };

  return (
    <div className={styles.Review}>
      <div className={styles.header}>
        {keyword !== "trav" && reviewData && (
          <>
            {/* 별점 */}
            <div className={styles.StarScore}>
              <StarScore score={reviewData.reviewAverage * 20} />
            </div>

            {/* 상단 사진 장수 & 전체보기 버튼 */}
            <div className={styles.headerHeader}>
              <h2 className={styles.headerPhotoCounter}>
                {user && user.isKorean
                  ? `사진 ${reviewData.imageUrls.length}장`
                  : `${reviewData.imageUrls.length} photos`}
              </h2>
              <button onClick={onClickButton} className={styles.headerButton}>
                {user && user.isKorean ? "전체보기 >" : "View All >"}
              </button>
            </div>

            {/* 사진 미리보기 4장 */}
            <div onClick={onClickButton} className={styles.headerPhotoPreview}>
              {reviewData.imageUrls.slice(0, 4).map((photo, index) => (
                <img key={index} src={photo} alt={`리뷰 사진 ${index + 1}`} />
              ))}
            </div>
          </>
        )}
      </div>

      {/* 리뷰 하단 상세보기 */}
      <div className={styles.body}>
        <h2 className={styles.bodyHeader}>
          {user && user.isKorean
            ? `후기 ${
                keyword === "trav"
                  ? travReviewData.reviewCount
                  : reviewData.reviewCount
              }개`
            : `${
                keyword === "trav"
                  ? travReviewData.reviewCount
                  : reviewData.reviewCount
              } Reviews`}
        </h2>
        <div className={styles.bodyReviewList}>
          {(keyword === "trav"
            ? travReviewData.reviews
            : reviewData.reviews
          )?.map((review) => (
            <Review_Item
              key={review.id}
              description={review.description}
              imageList={review.imageUrls}
              user={review.reviewer}
              score={review.score}
              tourTitle={review.tourTitle}
              needToShowTourTitle={true}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default Review;
