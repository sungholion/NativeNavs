import React, { useEffect, useState } from "react";
import styles from "./Rating.module.css";

const Rating = () => {
  const ratingValue = 3.54124;
  const [displayValue, setDisplayValue] = useState(0);

  useEffect(() => {
    const duration = 1000; // 애니메이션 지속 시간 (ms)
    const start = performance.now(); // 시작 시간 기록

    const animate = (currentTime) => {
      const progress = currentTime - start; // 현재시간과 시작시간을 비교하여 지속 시간을 구함
      const progressPercentage = Math.min(progress / duration, 1); // 진행율 계산
      setDisplayValue((ratingValue * progressPercentage).toFixed(2)); // 진행율에 맞게 값을 표시

      if (progress < duration) { // 설정한 지속시간보다 덜 진행됐으면
        requestAnimationFrame(animate); // 다음 프레임 요청
      }
    };

    requestAnimationFrame(animate); // 애니메이션 루프 구현 메서드(인수 : 콜백함수)
  }, [ratingValue]);

  return (
    <div className={styles.ratingContainer}>
      <span className={styles.star}>★</span>
      <span className={styles.ratingValue}>{displayValue}</span>
    </div>
  );
};

export default Rating;
