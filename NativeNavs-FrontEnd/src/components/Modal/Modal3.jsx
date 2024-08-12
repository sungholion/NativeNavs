import React from "react";
import styles from "./Modal3.module.css";

const Modal3 = ({ show, onClose, plan }) => {
  if (!show) {
    return null;
  }

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <span className={styles.closeButton} onClick={onClose}>
          &times;
        </span>
        <img src={plan.image} alt="Plan Image" className={styles.modalImage} />
        <div className={styles.planDetails}>
          <h3>{plan.field}</h3>
          <p>{plan.description}</p>
          <p>{plan.addressFull}</p>
        </div>
      </div>
    </div>
  );
};

export default Modal3;
