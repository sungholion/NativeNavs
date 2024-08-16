import React, { useState, useEffect } from "react";
import styles from "./Modal.module.css";

const Modal = ({ show, onClose, photos, selectedPhotoIndex, setSelectedPhotoIndex }) => {
  const [isDragging, setIsDragging] = useState(false);
  const [startX, setStartX] = useState(0);
  const [translateX, setTranslateX] = useState(0);
  const [showIndicators, setShowIndicators] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setShowIndicators(false);
    }, 1000);

    return () => clearTimeout(timer);
  }, []);

  if (!show) {
    return null;
  }

  const handleMouseDown = (e) => {
    setIsDragging(true);
    setStartX(e.clientX);
  };

  const handleMouseMove = (e) => {
    if (!isDragging) return;
    const moveX = e.clientX - startX;
    setTranslateX(moveX);
  };

  const handleMouseUp = () => {
    setIsDragging(false);
    if (translateX > 50) {
      setSelectedPhotoIndex((prevIndex) => (prevIndex === 0 ? photos.length - 1 : prevIndex - 1));
    } else if (translateX < -50) {
      setSelectedPhotoIndex((prevIndex) => (prevIndex === photos.length - 1 ? 0 : prevIndex + 1));
    }
    setTranslateX(0);
  };

  const handleTouchStart = (e) => {
    setIsDragging(true);
    setStartX(e.touches[0].clientX);
  };

  const handleTouchMove = (e) => {
    if (!isDragging) return;
    const moveX = e.touches[0].clientX - startX;
    setTranslateX(moveX);
  };

  const handleTouchEnd = () => {
    setIsDragging(false);
    if (translateX > 50) {
      setSelectedPhotoIndex((prevIndex) => (prevIndex === 0 ? photos.length - 1 : prevIndex - 1));
    } else if (translateX < -50) {
      setSelectedPhotoIndex((prevIndex) => (prevIndex === photos.length - 1 ? 0 : prevIndex + 1));
    }
    setTranslateX(0);
  };

  const handleOverlayClick = () => {
    onClose(); 
  };

  const handleContentClick = (e) => {
    e.stopPropagation();
  };

  return (
    <div className={styles.modalOverlay} onClick={handleOverlayClick}>
      <div
        className={`${styles.modalContent} ${showIndicators ? styles.dimmedBackground : ''}`}
        onClick={handleContentClick}
        onMouseDown={handleMouseDown}
        onMouseMove={handleMouseMove}
        onMouseUp={handleMouseUp}
        onMouseLeave={handleMouseUp}
        onTouchStart={handleTouchStart}
        onTouchMove={handleTouchMove}
        onTouchEnd={handleTouchEnd}
        style={{ transform: `translateX(${translateX}px)` }}
      >
        {showIndicators && (
          <div className={styles.indicatorOverlay}>
            <span className={`${styles.indicator} ${styles.leftIndicator}`}>&lt;&lt;</span>
            <span className={`${styles.indicator} ${styles.rightIndicator}`}>&gt;&gt;</span>
          </div>
        )}
        <img
          src={photos[selectedPhotoIndex]}
          alt="확대된 리뷰 사진"
          className={styles.modalImage}
        />
      </div>
    </div>
  );
};

export default Modal;
