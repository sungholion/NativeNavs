import "./ReviewCreate.css";
import Tour_Item_mini_Review from "@/components/Tour_Item/Tour_Item_mini_Review";
import { useEffect, useReducer, useState } from "react";
import StarScoring from "@/components/Star/StarScoring";
const dummy_info = {
  tour: {
    // 투어 정보
    image:
      "https://cdn.pixabay.com/photo/2016/11/29/05/45/astronomy-1867616_960_720.jpg",
    title: "투어 제목",
    nav: {
      // 가이드 정보
      image:
        "https://cdn.pixabay.com/photo/2016/11/29/05/45/astronomy-1867616_960_720.jpg",
      nickname: "가이드이름",
    },
  },
  progress: {
    // 예약 정보
    date: "2021-09-01",
    participant: 2,
  },
};

const reducer = (state, action) => {
  switch (action.type) {
    case "score":
      return { ...state, score: action.score };
    case "content":
      return { ...state, content: action.content };
  }
};

const ReviewCreate = ({ info } = dummy_info) => {
  const [reviewData, dispatch] = useReducer(reducer, {
    score: 0,
    description: "",
    image: [], //이미지 파일 - Max 5개
  });

  const [tour_info, setTour_info] = useState({});

  return (
    <div className="ReviewCreate">
      <section>
        <Tour_Item_mini_Review {...dummy_info} />
      </section>

      <section className="ScoreRating">
        <h4>별점</h4>
        <StarScoring
          onRatingChange={(score) => dispatch({ type: "score", score })}
        />
      </section>
    </div>
  );
};

export default ReviewCreate;
