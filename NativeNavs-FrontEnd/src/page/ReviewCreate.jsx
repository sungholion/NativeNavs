import "./ReviewCreate.css";
import Tour_Item_mini_Review from "@/components/Tour_Item/Tour_Item_mini_Review";
import { useEffect, useReducer, useState } from "react";
import StarScoring from "@/components/Star/StarScoring";

const MAX_IMAGE_COUNT = 5; // 최대 이미지 업로드 수
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
    case "image":
      return { ...state, image: action.image };
  }
};

const ReviewCreate = ({ info } = dummy_info) => {
  const [reviewData, dispatch] = useReducer(reducer, {
    score: 0,
    description: "",
    image: [], //이미지 파일 - Max 5개
  });
  const [tour_info, setTour_info] = useState({});

  const onImgChange = (e) => {
    const { files } = e.target;
    if (files && files.length > 0) {
      if (files.length > MAX_IMAGE_COUNT) {
        alert("최대 5개까지 업로드 가능합니다.");
        return;
      }
      const fileArr = [...files];
      for (let file of fileArr) {
        if (file.size > 1024 * 1024 * 10) {
          alert("10MB 이하의 파일만 업로드 가능합니다.");
          return;
        }
      }
      for (let imgData of reviewData.image) {
        URL.revokeObjectURL(imgData);
      }
      dispatch({ type: "image", image: fileArr });
    }
  };

  return (
    <div className="ReviewCreate">
      <section>
        <Tour_Item_mini_Review {...dummy_info} />
      </section>

      <section className="ScoreRating">
        <h4>Nav와 함께한 여행은 어땟나요?</h4>
        <StarScoring
          onRatingChange={(score) => dispatch({ type: "score", score })}
        />
      </section>
      <section className="ReviewImgUploadSection">
        <div className="ReviewImgUploadHeader">
          <div>
            사진 등록 :
            <span>
              {reviewData.image.length} / {MAX_IMAGE_COUNT}
            </span>
          </div>
          <div>
            <label htmlFor="imgUploader">
              <div>+</div>
            </label>
            <input
              className="ReviewImgUploader"
              type="file"
              accept="image/*"
              multiple
              onChange={onImgChange}
              id="imgUploader"
              name="imgUploader"
            />
          </div>
        </div>
        <div className="ReviewImgUpload">
          {reviewData.image.length ? (
            reviewData.image.map((img, idx) => (
              <img key={idx} src={URL.createObjectURL(img)} alt="reviewImg" />
            ))
          ) : (
            <div className="noImgUploaded ">아직 등록한 이미지가 없습니다.</div>
          )}
        </div>
      </section>
      <section className="ReviewContent">
        <div>
          솔직한 후기를 남겨 주세요{" "}
          <span>
            {reviewData.content.length}/{200}자
          </span>
        </div>

        <textarea
          placeholder="리뷰를 작성해주세요."
          value={reviewData.content}
          onChange={(e) => {
            if (e.target.value.length < 200) {
              dispatch({ type: "content", content: e.target.value });
            }
          }}
        />
      </section>
      <section className={`ReviewButtonSection`}>
        <button
          className={`ReviewCreateSend ${
            reviewData.image.length === 0 || reviewData.content === ""
              ? "notAbleButton"
              : ""
          }`}
          disabled={
            !(reviewData.image.length === 0 || reviewData.content !== "")
          }
          onClick={() => {
            console.log("HI");
          }}
        >
          제출
        </button>
      </section>
    </div>
  );
};

export default ReviewCreate;
