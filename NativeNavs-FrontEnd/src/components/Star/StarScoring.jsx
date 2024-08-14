import React, { useState } from "react";
import "./StarScoring.css"; 

function StarScoring({ onRatingChange }) {
  const [rating, setRating] = useState(0); 

  const handleStarClick = (index) => {
    setRating(index + 1);
    onRatingChange(index + 1); 
  };

  return (
    <div className="star-score-wrap">
      <div className="star-score-icons">
        {Array.from({ length: 5 }).map((_, idx) => (
          <span
            key={idx}
            className={`star-score-icon ${rating > idx ? "filled" : ""}`}
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
