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
  const [thumbnailImgUrl, setThumbnailImagUrl] = useState("");
  useEffect(() => {
    getImageUrl(thumbnailImage, setThumbnailImagUrl);
  }, [thumbnailImage]);

  const checkUpload = () => {
    if (typeof thumbnailImage === "object" && !thumbnailImage) {
      return 0;
    }
    if (startDate > endDate) {
      return 0;
    }
    if (
      startDate < getStringedDate(new Date(new Date() + 1000 * 60 * 60 * 9))
    ) {
      return 0;
    }
    if (maxParticipants <= 0) {
      return 0;
    }
    if (plans.length <= 0) {
      return 0;
    }
    if (categoryIds.length <= 0) {
      return 0;
    }
    return 1;
  };

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
          <div style={{ color: "red", fontSize: "20px", marginLeft: "20px" }}>
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
          <div style={{ color: "red", fontSize: "20px", marginLeft: "20px" }}>
            {user && user.isKorean
              ? "제목 입력해 주세요"
              : "Please enter a title"}
          </div>
        )}
      </section>
      <section className="TourDuration">
        <p>{user && user.isKorean ? "기간" : "Duration"}</p>
        {(!startDate || !endDate) && <div></div>}
        {startDate <= endDate &&
          startDate >=
            getStringedDate(new Date(new Date() + 1000 * 60 * 60 * 9)) && (
            <div className="TourDateShow">
              <div className="TourDateArea">
                <span className="DateBox">
                  {getStringedDate(new Date(startDate))}
                </span>
                <span>~</span>
                <span className="DateBox">
                  {getStringedDate(new Date(endDate))}
                </span>
              </div>
            </div>
          )}
        {startDate > endDate && (
          <div style={{ color: "red", fontSize: "20px" }}>
            {user && user.isKorean
              ? "시작일이 끝 날짜보다 클 수 없습니다"
              : "The start date cannot be later than the end date."}
          </div>
        )}
        {startDate <
          getStringedDate(new Date(new Date() + 1000 * 60 * 60 * 9)) && (
          <div style={{ color: "red", fontSize: "20px" }}>
            {user && user.isKorean
              ? "시작일은 항상 오늘포함 그 이후여야 합니다"
              : "The start date must always be today or later."}
          </div>
        )}
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
        {categoryIds.length > 0 ? (
          <div className="TourThemeList">
            {categoryIds.map((cartegoryid) => {
              return (
                <div key={cartegoryid} className="TourThemeItem">
                  {categoryItemKr[cartegoryid - 1]["name"]}
                </div>
              );
            })}
          </div>
        ) : (
          <div style={{ color: "red", fontSize: "20px", marginLeft: "20px" }}>
            {user && user.isKorean
              ? "카테고리는 최소 1개 이상 선택해 주세요"
              : "Please select at least one category"}
          </div>
        )}
      </section>
      <section className="TourPlanList">
        <p>{user && user.isKorean ? "일정" : "Plans"}</p>
        {plans.length > 0 ? (
          <div className="TourPlanItem">
            {plans.map((item) => {
              return (
                <Plan_Item key={item.id} {...item} enableDeleteOption={false} />
              );
            })}
          </div>
        ) : (
          <div style={{ color: "red", fontSize: "20px", marginLeft: "20px" }}>
            {user && user.isKorean
              ? "일정은 최소 1개 이상 등록해 주세요"
              : "Please register at least one plan"}
          </div>
        )}
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
            uploadState === 2 ? "loading" : ""
          }`}
          onClick={async () => {
            if (uploadState === 1) {
              setUploadState(2);
              await onSubmit({
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
              })
                .then((res) => {
                  setUploadState(1);
                })
                .catch((err) => {
                  console.log(err);
                  setUploadState(1);
                });
            }
          }}
        >
          {uploadState === 0
            ? user && user.isKorean
              ? "제출 불가능"
              : "Not available"
            : uploadState === 1
            ? user && user.isKorean
              ? "등록하기"
              : "Apply"
            : user && user.isKorean
            ? "..."
            : "..."}
        </button>
      </section>
    </div>
  );
};

export default Confirm;
