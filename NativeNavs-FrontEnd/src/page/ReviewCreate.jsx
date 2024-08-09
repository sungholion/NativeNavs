import "./ReviewCreate.css";
import Tour_Item_mini_Review from "@/components/Tour_Item/Tour_Item_mini_Review";
import { useEffect, useReducer, useState } from "react";
import StarScoring from "@/components/Star/StarScoring";
import { useParams } from "react-router-dom";
import axios from "axios";

const MAX_IMAGE_COUNT = 5; // 최대 이미지 업로드 수

const reducer = (state, action) => {
  switch (action.type) {
    case "score":
      return { ...state, score: action.score };
    case "description":
      return { ...state, description: action.description };
    case "image":
      return { ...state, image: action.image };
  }
};

const ReviewCreate = () => {
  const param = useParams();
  const [reviewData, dispatch] = useReducer(reducer, {
    score: 0,
    description: "",
    image: [], //이미지 파일 - Max 5개
  });

  const [user, setUser] = useState(null);
  useEffect(() => {
    window.getUserData = (userJson) => {
      console.log("Received user JSON:", userJson);
      try {
        const parsedUser = JSON.parse(userJson);
        console.log(`User ID: ${parsedUser.userId}`);
        console.log(`Token: ${parsedUser.userToken}`);
        console.log(`isNav: ${parsedUser.isNav}`);
        setUser(parsedUser);
      } catch (error) {
        console.log("Failed to parse user JSON");
      }
    };
  }, []);
  const [tourInfo, setTourInfo] = useState(null); // 투어 정보
  useEffect(() => {
    axios
      .get(`http://i11d110.p.ssafy.io:8080/api/tours/${param.tour_id}`)
      .then((res) => {
        console.log(res.data);
        setTourInfo(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  }, [param.tour_id]);

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
        <Tour_Item_mini_Review />
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
      <section className="Reviewdescription">
        <div>
          솔직한 후기를 남겨 주세요{" "}
          <span>
            {reviewData.description.length}/{200}자
          </span>
        </div>

        <textarea
          placeholder="리뷰를 작성해주세요."
          value={reviewData.description}
          onChange={(e) => {
            if (e.target.value.length <= 200) {
              dispatch({ type: "description", description: e.target.value });
            }
          }}
        />
      </section>
      <section className={`ReviewButtonSection`}>
        <button
          className={`ReviewCreateSend ${
            reviewData.image.length === 0 || reviewData.description === ""
              ? "notAbleButton"
              : ""
          }`}
          disabled={
            !(reviewData.image.length === 0 || reviewData.description !== "")
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
