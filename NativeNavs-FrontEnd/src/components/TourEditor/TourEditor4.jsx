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
        <button onClick={goBeforePage}>
          {user && user.isKorean ? "이전" : "Previous"}
        </button>
        <button
          onClick={() => {
            onSubmit({
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
            });
          }}
        >
          {user && user.isKorean ? "추가" : "Add"}
        </button>
      </section>
    </div>
  );
};

export default Confirm;
