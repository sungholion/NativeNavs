// src/components/Star/StarScoring.jsx
import React, { useState } from "react";
import "./StarScore.css"; // 기존 스타일을 재사용

function StarScoring({ onRatingChange }) { // onRatingChange를 props로 받음
  const [rating, setRating] = useState(0); // 현재 별점 상태 관리, 초기값 0

  // 별을 클릭했을 때 호출되는 함수
  const handleStarClick = (index) => {
    setRating(index + 1); // 클릭된 별의 인덱스를 바탕으로 점수 설정
    onRatingChange(index + 1); // 부모 컴포넌트에 새로운 점수 전달
  };

  return (
    <div className="star-score-wrap">
      <div className="star-score-icons">
        {Array.from({ length: 5 }).map((_, idx) => (
          <span
            key={idx}
            className={`star-score-icon ${rating > idx ? 'filled' : ''}`}
            // 해당 별이 클릭되면 이벤트 핸들러 함수에 인덱스를 인수로 해서 호출
            onClick={() => handleStarClick(idx)}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="20"
              height="20"
              viewBox="0 0 14 13"
              fill={rating > idx ? "#FFFF00" : "#cacaca"}
            >
              <path
                d="M9,2l2.163,4.279L16,6.969,12.5,10.3l.826,4.7L9,12.779,4.674,15,5.5,10.3,2,6.969l4.837-.69Z"
                transform="translate(-2 -2)"
              />
            </svg>
          </span>
        ))}
      </div>
      <span className="star-score-text">{rating}</span>
    </div>
  );
}

export default StarScoring;
