import React, { useState, useEffect } from "react";
import NativeNavsRemoveNeedle from "../assets/NativeNavsRemoveNeedle.png";
import compassNeedleRemoveBack from "../assets/compassNeedleRemoveBack.png";
import styles from "./NoTour.module.css";

const NoTour = ({ user }) => {
  const [showMessage, setShowMessage] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setShowMessage(true);
    }, 1500); // 1.5초 후에 메시지를 표시

    return () => clearTimeout(timer); // 컴포넌트가 언마운트될 때 타이머 정리
  }, []);

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
      {showMessage && (
        <div className={styles.messageContainer}>
{user && user.isKorean
  ? (
    <>
      <div className={styles.message}>검색된 투어가</div>
      <div className={styles.message}>없습니다</div>
    </>
  )
  : (
    <>
      <div className={styles.message}>No tours found</div>
    </>
  )
}

        </div>
      )}
    </div>
  );
};

export default NoTour;
