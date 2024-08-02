// Modal.jsx
import React from "react";
import styles from "./Modal.module.css";

const Modal = ({ show, onClose, photo }) => {
  if (!show) {
    return null;
  }

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <span className={styles.closeButton} onClick={onClose}>
          &times;
        </span>
        <img src={photo} alt="확대된 리뷰 사진" className={styles.modalImage} />
      </div>
    </div>
  );
};

export default Modal;
