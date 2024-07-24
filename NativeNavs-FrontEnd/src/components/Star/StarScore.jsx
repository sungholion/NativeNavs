// src/components/Star/StarScore.jsx
import React, { useState, useEffect } from 'react';
import './StarScore.css'; // 스타일을 위한 CSS 파일

function StarScore({ score }) {
    // 별의 채워진 정도를 저장할 상태 변수
    const [ratesResArr, setRatesResArr] = useState([0, 0, 0, 0, 0]);

    // 별의 채워진 정도를 계산하는 함수
    const calcStarRates = () => {
        let tempStarRatesArr = [0, 0, 0, 0, 0];
        // 점수를 100점 만점으로 가정하고, 70%를 별 점수로 변환
        let starVerScore = (score * 70) / 100;
        let idx = 0;

        // 점수에 따라 별의 채워진 정도를 계산
        while (starVerScore > 14) {
            tempStarRatesArr[idx] = 14; // 각 별의 최대 채워진 길이
            idx += 1;
            starVerScore -= 14; // 남은 점수 계산
        }
        // 마지막 별에 남은 점수 할당
        tempStarRatesArr[idx] = starVerScore;
        return tempStarRatesArr;
    };

    useEffect(() => {
        // score가 변경될 때마다 별의 채워진 정도를 업데이트
        setRatesResArr(calcStarRates());
    }, [score]);

    return (
        <div className="star-score-wrap">
            <div className="star-score-icons">
                {/* 별 아이콘을 렌더링 */}
                {['first', 'second', 'third', 'fourth', 'last'].map((item, idx) => (
                    <span className="star-score-icon" key={`${item}_${idx}`}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 14 13" fill="#cacaca">
                            {/* 별 아이콘의 채워진 정도를 결정하는 클리핑 경로 */}
                            <clipPath id={`${item}StarClip`}>
                                <rect width={`${ratesResArr[idx]}`} height="39" />
                            </clipPath>
                            {/* 별 모양을 정의 */}
                            <path
                                id={`${item}Star`}
                                d="M9,2l2.163,4.279L16,6.969,12.5,10.3l.826,4.7L9,12.779,4.674,15,5.5,10.3,2,6.969l4.837-.69Z"
                                transform="translate(-2 -2)"
                            />
                            {/* 클리핑된 별 아이콘을 채우기 */}
                            <use clipPath={`url(#${item}StarClip)`} href={`#${item}Star`} fill="#FFFF00" />
                        </svg>
                    </span>
                ))}
            </div>
            {/* 점수를 텍스트로 표시 */}
            <span className="star-score-text">{score/20}</span>
        </div>
    );
}

export default StarScore;
