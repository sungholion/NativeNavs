import "./App.css";
import StarScoring from "./components/Star/StarScoring";
import StarScore from "./components/Star/StarScore";
import React, { useState } from "react";

function App() {
  const [rating, setRating] = useState(0);

  // StarScoring 컴포넌트에서 별점이 변경될 때 호출되는 함수
  const handleRatingChange = (newRating) => {
    setRating(newRating);
    // 여기에 서버로 전달하는 로직을 추가할 수 있습니다.
    console.log("평가된 별점:", newRating);
  };
  return (
    <>
      <h1 className="test">Hello REACT</h1>
      <hr />
      <StarScoring onRatingChange={handleRatingChange} />
      <StarScore score={85}/>
    </>
  );
}

export default App;
