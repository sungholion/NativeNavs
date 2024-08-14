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

const MAX_IMAGE_COUNT = 5; 

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
    image: [], 
  });

  const [user, setUser] = useState(null);
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
    }
  }, []);
  const [info, setInfo] = useState(null); 

  useEffect(() => {
    axios
      .get(
        `https://i11d110.p.ssafy.io/api/reservations/${param.reservation_id}`,

        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: user?.userToken,
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
        alert(
          `${
            user?.isKorean
              ? "5개 이하의 이미지만 업로드 가능합니다."
              : "Up to 5 images can be uploaded."
          }`
        );
        return;
      }
      const fileArr = [...files];
      for (let file of fileArr) {
        if (file.size > 1024 * 1024 * 10) {
          alert(
            `${
              user?.isKorean
                ? "10메가 이하의 이미지만 업로드 가능합니다."
                : "Only images up to 10MB can be uploaded."
            }`
          );
          return;
        }
      }
      for (let imgData of reviewData.image) {
        URL.revokeObjectURL(imgData);
      }
      dispatch({ type: "image", image: fileArr });
    }
  };

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
