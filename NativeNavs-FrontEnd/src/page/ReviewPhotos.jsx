import React, { useState } from "react";
import { reviews } from "../dummy"; // 더미 데이터를 가져옵니다.
import { useParams } from "react-router-dom";
import Modal from "@/components/Modal/Modal";
import styles from "./ReviewPhotos.module.css";

const ReviewPhotos = () => {
  const params = useParams();
  const [showModal, setShowModal] = useState(false);
  const [selectedPhoto, setSelectedPhoto] = useState("");

  const handlePhotoClick = (photo) => {
    setSelectedPhoto(photo);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setSelectedPhoto("");
  };

  return (
    <div className={styles.ReviewPhotos}>
      {reviews.img_urls.map((photo, index) => (
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
