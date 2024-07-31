import styles from "./Review.module.css";
import { reviews } from "../dummy";
import StarScore from "@/components/Star/StarScore";
import Review_Item from "@/components/Review_Item/Review_Item";
import { useParams, useNavigate } from "react-router-dom";

const Review = ({ tour_id }) => {
  const params = useParams();
  const photos = reviews.img_urls; // 전체 사진 배열
  const navigate = useNavigate();

  const onClickButton = () => {
    navigate(`/tour/detail/${params.tour_id}/reviewphotos`);
  };

  return (
    <div className={styles.Review}>
      <div className={styles.header}>
        {/* 별점 */}
        <StarScore score={reviews.averageScore * 20} /> {/* Assuming a score of 5 (100/20) */}

        {/* 상단 사진 장수 & 전체보기 버튼 */}
        <div className={styles.headerHeader}>
          <h2 className={styles.headerPhotoCounter}>
            사진 {reviews.totalPhotos}장
          </h2>
          <button onClick={onClickButton} className={styles.headerButton}>
            전체보기 {">"}
          </button>
        </div>

        {/* 사진 미리보기 4장 */}
        <div onClick={onClickButton} className={styles.headerPhotoPreview}>
          {photos.slice(0, 4).map((photo, index) => (
            <img key={index} src={photo} alt={`리뷰 사진 ${index + 1}`} />
          ))}
        </div>
      </div>

      {/* 리뷰 하단 상세보기 */}
      <div className={styles.body}>
        <h2 className={styles.bodyHeader}>후기 {reviews.totalReviews}개</h2>
        <div className={styles.bodyReviewList}>
          {reviews.reviews.map((review) => (
            <Review_Item
              key={review.id}
              user={review.user}
              score={review.score}
              description={review.description}
              created_at={review.created_at}
              tour={review.tour}
              needToShowTourTitle={true}
              imageList={review.imageList}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default Review;
