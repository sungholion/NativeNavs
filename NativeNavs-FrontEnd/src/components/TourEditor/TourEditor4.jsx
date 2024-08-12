import "./TourEditor4.css";
import Plan_Item from "@/components/Plan_Item/Plan_Item";
import { getStringedDate } from "@utils/get-stringed-date";
import { TourDataContext } from "./TourEditorHead";
import { useContext, useEffect, useState } from "react";
import { categoryItemKr } from "@utils/constant";
import { getImageUrl } from "@/utils/get-image-url";

const Confirm = ({ goBeforePage, onSubmit, user }) => {
  const {
    title,
    thumbnailImage,
    description,
    location,
    price,
    startDate,
    endDate,
    maxParticipants,
    plans,
    categoryIds,
  } = useContext(TourDataContext);
  const [thumbnailImgUrl, setThumbnailImagUrl] = useState(""); //실제 사용자게에 보여줄 썸내일 이미지 담긴 것
  useEffect(() => {
    getImageUrl(thumbnailImage, setThumbnailImagUrl);
  }, [thumbnailImage]); //썸네일 이미지 보려주기, STRING이냐 FILE이냐에 따라서

  // 업로드 가능 여부 확인 함수
  const checkUpload = () => {
    if (typeof thumbnailImage === "string" && thumbnailImage === "") {
      return 0;
    }
    if (typeof thumbnailImage === "object" && !thumbnailImage) {
      return 0;
    }
    if (description.trim() === "") {
      return 0;
    }
    if (startDate > endDate) {
      return 0;
    }
    if (maxParticipants === 0) {
      return 0;
    }
    if (plans.length === 0) {
      return 0;
    }
    if (categoryIds.length === 0) {
      return 0;
    }
    return 1;
  };

  // 업로드 상태여부 확인
  // 0 -> 업로드 불가능(빈 값 존재), 1 -> 업로드 가능, 2 -> 업로드 버튼 누름
  const [uploadState, setUploadState] = useState(0);
  useEffect(() => {
    setUploadState(checkUpload());
  }, []);

  return (
    <div className="TourConfirm">
      <section className="TourThumbnailCheck">
        <p>{user && user.isKorean ? "썸네일 사진" : "Thumbnail Photo"}</p>
        {thumbnailImgUrl ? (
          <img src={thumbnailImgUrl} alt="썸네일이미지" />
        ) : (
          <div style={{ color: "red", fontSize: "20px" }}>
            {user && user.isKorean
              ? "이미지를 업로드 해주세요"
              : "Please upload an image"}
          </div>
        )}
      </section>
      <hr />
      <section className="TourTitle">
        <p>{user && user.isKorean ? "제목" : "Title"}</p>
        {title.length > 0 ? (
          <div>{title}</div>
        ) : (
          <div style={{ color: "red" }}>
            {user && user.isKorean
              ? "제목 입력해 주세요"
              : "Please enter a title"}
          </div>
        )}
      </section>
      <section className="TourDuration">
        <p>{user && user.isKorean ? "기간" : "Duration"}</p>
        <div className="TourDateShow">
          <span className="DateBox">
            {getStringedDate(new Date(startDate))}
          </span>
          <span>~</span>
          <span className="DateBox">{getStringedDate(new Date(endDate))}</span>
        </div>
      </section>
      <hr />
      <section className="TourMaxPeople">
        <p>
          {user && user.isKorean
            ? "최대 허용 인원"
            : "Maximum Allowed Participants"}
        </p>
        <div>
          <div>
            {maxParticipants} {user && user.isKorean ? "명" : ""}
          </div>
        </div>
      </section>
      <section className="TourExpectPrice">
        <p>{user && user.isKorean ? "예상 비용" : "Estimated Cost"}</p>
        <div>
          {price} {user && user.isKorean ? "원" : "KRW"}
        </div>
      </section>
      <section className="TourTheme">
        <p>{user && user.isKorean ? "테마" : "Theme"}</p>
        <div className="TourThemeList">
          {categoryIds.map((cartegoryid) => {
            return (
              <div key={cartegoryid} className="TourThemeItem">
                {categoryItemKr[cartegoryid - 1]["name"]}
              </div>
            );
          })}
        </div>
      </section>
      <section className="TourPlanList">
        <p>{user && user.isKorean ? "일정" : "Plans"}</p>
        <div className="TourPlanItem">
          {plans.map((item) => {
            return (
              <Plan_Item key={item.id} {...item} enableDeleteOption={false} />
            );
          })}
        </div>
      </section>
      <hr />
      <section className="TourMapList"></section>
      <section className="Tourdescription">
        <p>{user && user.isKorean ? "당부사항" : "Reminders"}</p>
        <div>{description}</div>
      </section>
      <section className="ButtonSection">
        <button className="leftButton" onClick={goBeforePage}>
          {user && user.isKorean ? "이전" : "Previous"}
        </button>
        <button
          disabled={uploadState === 0 || uploadState === 2}
          className={`rightButton ${uploadState === 0 ? "disabled" : ""}   ${
            uploadState === 2 ? "uploading" : ""
          }`}
          onClick={() => {
            if (uploadState === 1) {
              setUploadState(2);
              onSubmit({
                title,
                thumbnailImage,
                description: description.trim(),
                location,
                price,
                startDate,
                endDate,
                maxParticipants,
                plans,
                categoryIds,
              });
              setUploadState(1);
            }
          }}
        >
          {uploadState === 0
            ? user && user.isKorean
              ? "빈 값을 채워주세요"
              : "Fill in the blanks"
            : uploadState === 1
            ? user && user.isKorean
              ? "업로드"
              : "Upload"
            : user && user.isKorean
            ? "업로드 중"
            : "Uploading"}
        </button>
      </section>
    </div>
  );
};

export default Confirm;
