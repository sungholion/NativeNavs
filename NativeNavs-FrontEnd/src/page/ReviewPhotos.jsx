import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import Modal from "@/components/Modal/Modal";
import styles from "./ReviewPhotos.module.css";

const ReviewPhotos = ({ keyword }) => {
  const params = useParams();
  const [showModal, setShowModal] = useState(false);
  const [selectedPhotoIndex, setSelectedPhotoIndex] = useState(0);
  const [reviewData, setReviewData] = useState({
    imageUrls: [],
    reviewAverage: 0,
    reviewCount: 0,
    reviews: [],
    totalImageCount: 0,
  });

  const handlePhotoClick = (index) => {
    setSelectedPhotoIndex(index);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setSelectedPhotoIndex(0);
  };

  // FE -> BE : ReviewData API 요청
  useEffect(() => {
    const getUrlParam = () => {
      switch (keyword) {
        case "tour":
          return `https://i11d110.p.ssafy.io/api/reviews/tour/${params.tour_id}`;
        case "guide":
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
        setReviewData(response.data);
        console.log("Reviews response data : ", response.data);
      } catch (error) {
        console.error("Error fetching reviewData:", error);
      }
    };

    fetchReviewData();
  }, []);

  return (
    <div className={styles.ReviewPhotos}>
      {reviewData.imageUrls.map((photo, index) => (
        <img
          key={index}
          src={photo}
          alt={`리뷰 사진 ${index + 1}`}
          className={styles.photo}
          onClick={() => handlePhotoClick(index)}
        />
      ))}

      {showModal && (
        <Modal
          show={showModal}
          onClose={closeModal}
          photos={reviewData.imageUrls}
          selectedPhotoIndex={selectedPhotoIndex}
          setSelectedPhotoIndex={setSelectedPhotoIndex}
        />
      )}
    </div>
  );
};

export default ReviewPhotos;
