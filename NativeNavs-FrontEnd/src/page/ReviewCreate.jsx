import "./ReviewCreate.css";
import Tour_Item_mini_Review from "@/components/Tour_Item/Tour_Item_mini_Review";
import { useEffect, useReducer, useState } from "react";
import StarScoring from "@/components/Star/StarScoring";
import { useParams } from "react-router-dom";
import axios from "axios";
import {
  moveFromReviewRegisterToReviewListFragment,
  showReviewRegisterFailDialog,
} from "@/utils/get-android-function";

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
  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
    }
  }, []);
  const [info, setInfo] = useState(null); // 예약 상세 정보

  // 예약 상세 정보 가져오기
  useEffect(() => {
    axios
      .get(
        `https://i11d110.p.ssafy.io/api/reservations/${param.reservation_id}`,

        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization:
              user?.userToken ||
              "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlb2JsdWUyM0BnbWFpbC5jb20iLCJpYXQiOjE3MjMyODM4NDQsImV4cCI6MTcyMzI4NzQ0NH0.N7sHXAvqHbVbWXBfwYOtYeu1sTZdaHxo-I_8XINobqYMgf1fIghH-SmTevqj_eeU6grBMTpE56ZAyh4y5lg2-g",
          },
        }
      )
      .then((res) => {
        console.log(res.data);
        setInfo(res.data);
      })
      .catch((error) => console.error(error));
  }, [param.reservation_id, user?.userToken]);

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

  // 서버제출
  const onSubmit = async () => {
    const formData = new FormData();

    const subData = {
      tourId: Number(info.tourId),
      score: Number(reviewData.score),
      description: reviewData.description,
      imageUrls: reviewData.image.map((img) => ""),
    };
    console.log(subData);

    formData.append(
      "review",
      new Blob([JSON.stringify(subData)], { type: "application/json" })
    );

    for (const imgFile of reviewData.image) {
      // imageUrl s
      formData.append("reviewImages", imgFile);
    }

    await axios
      .post(
        `https://i11d110.p.ssafy.io/api/reviews?reservationNumber=${param.reservation_id}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: user.userToken,
          },
        }
      )
      .then((res) => {
        moveFromReviewRegisterToReviewListFragment(Number(param.tour_id));
      })
      .catch((err) => {
        showReviewRegisterFailDialog();
      });
  };

  if (!info || !user) {
    return <div>Loading...</div>;
  }

  return (
    <div className="ReviewCreate">
      <section>
        <Tour_Item_mini_Review
          thumbnailImage={info.thumbnailImage}
          title={info.tourTitle}
          progress={{
            date: info.reservationDate,
            participantCount: info.participantCount,
          }}
          nav={{ image: info.guide.image, nickname: info.guide.nickname }}
        />
      </section>

      <section className="ScoreRating">
        <h4>
          {user.isKorean
            ? "Nav와 함께한 여행은 어땠나요?"
            : "How was your trip with Nav?"}
        </h4>
        <StarScoring
          onRatingChange={(score) => dispatch({ type: "score", score })}
        />
      </section>
      <section className="ReviewImgUploadSection">
        <div className="ReviewImgUploadHeader">
          <div>
            {user.isKorean == false ? "사진 등록 :" : "Upload Photos :"}
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
            <div className="noImgUploaded ">
              {user.isKorean
                ? "아직 등록한 이미지가 없습니다."
                : "No images uploaded yet."}
            </div>
          )}
        </div>
      </section>
      <section className="Reviewdescription">
        <div>
          {user.isKorean
            ? "솔직한 후기를 남겨 주세요"
            : "Please leave an honest review"}

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
            onSubmit();
          }}
        >
          {user.isKorean == false ? "제출" : "Submit"}
        </button>
      </section>
    </div>
  );
};

export default ReviewCreate;
