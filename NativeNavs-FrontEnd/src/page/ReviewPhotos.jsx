import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import Modal from "@/components/Modal/Modal";
import styles from "./ReviewPhotos.module.css";

const ReviewPhotos = () => {
  const params = useParams();
  const [showModal, setShowModal] = useState(false);
  const [selectedPhoto, setSelectedPhoto] = useState("");
  const [reviewData, setReviewData] = useState({
    imageUrls: [],
    reviewAverage: 0,
    reviewCount: 0,
    reviews: [],
    totalImageCount: 0,
  });

  const handlePhotoClick = (photo) => {
    setSelectedPhoto(photo);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setSelectedPhoto("");
  };

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

  return (
    <div className={styles.ReviewPhotos}>
      {reviewData.imageUrls.map((photo, index) => (
        <img
          key={index}
          src={photo}
          alt={`리뷰 사진 ${index + 1}`}
          className={styles.photo}
          onClick={() => handlePhotoClick(photo)}
        />
      ))}

      <Modal show={showModal} onClose={closeModal} photo={selectedPhoto} />
    </div>
  );
};

export default ReviewPhotos;
