import React from "react";
import styles from "./Modal.module.css";

const Modal = ({ show, onClose, photos, selectedPhotoIndex, setSelectedPhotoIndex }) => {
  if (!show) {
    return null;
  }

  const handlePrevClick = () => {
    setSelectedPhotoIndex((prevIndex) => (prevIndex === 0 ? photos.length - 1 : prevIndex - 1));
  };

  const handleNextClick = () => {
    setSelectedPhotoIndex((prevIndex) => (prevIndex === photos.length - 1 ? 0 : prevIndex + 1));
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <span className={styles.closeButton} onClick={onClose}>
          &times;
        </span>
        <img
          src={photos[selectedPhotoIndex]}
          alt="확대된 리뷰 사진"
          className={styles.modalImage}
        />
        <button className={styles.prevButton} onClick={handlePrevClick}>
          &#8249;
        </button>
        <button className={styles.nextButton} onClick={handleNextClick}>
          &#8250;
        </button>
      </div>
    </div>
  );
};

export default Modal;
