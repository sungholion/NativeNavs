// src/components/Star/StarScore.jsx
import React, { useState, useEffect } from "react";
import "./StarScore.css"; // 스타일을 위한 CSS 파일

function StarScore({ score }) {
  const [ratesResArr, setRatesResArr] = useState([0, 0, 0, 0, 0]);

  // 별의 채워진 정도를 계산하는 함수
  const calcStarRates = () => {
    let tempStarRatesArr = [0, 0, 0, 0, 0];
    let starVerScore = (score * 70) / 100;
    let idx = 0;
    while (starVerScore > 14) {
      tempStarRatesArr[idx] = 14; // 각 별의 최대 채워진 길이
      idx += 1;
      starVerScore -= 14; // 남은 점수 계산
    }
    tempStarRatesArr[idx] = starVerScore;
    return tempStarRatesArr;
  };

  useEffect(() => {
    setRatesResArr(calcStarRates());
  }, [score]);

  return (
    <div className="star-score-wrap">
      <div className="star-score-icons">
        {/* 별 아이콘을 렌더링 */}
        {["first", "second", "third", "fourth", "last"].map((item, idx) => (
          <span className="star-score-icon" key={`${item}_${idx}`}>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="20"
              height="20"
              viewBox="0 0 14 13"
              fill="#cacaca"
            >
              <clipPath id={`${item}StarClip`}>
                <rect width={`${ratesResArr[idx]}`} height="39" />
              </clipPath>
              <path
                id={`${item}Star`}
                d="M9,2l2.163,4.279L16,6.969,12.5,10.3l.826,4.7L9,12.779,4.674,15,5.5,10.3,2,6.969l4.837-.69Z"
                transform="translate(-2 -2)"
              />
              <use
                clipPath={`url(#${item}StarClip)`}
                href={`#${item}Star`}
                fill="#FFFF00"
              />
            </svg>
          </span>
        ))}
      </div>
      <span className="star-score-text">{score / 20}</span>
    </div>
  );
}

export default StarScore;
