import "./TourEditor4.css";
import Plan_Item from "@/components/Plan_Item/Plan_Item";
import { getStringedDate } from "@utils/get-stringed-date";
import { TourDataContext } from "./TourEditorHead";
import { useContext } from "react";
import { categoryItem } from "@utils/constant";

const Confirm = ({ goBeforePage, onSubmit }) => {
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
  return (
    <div className="TourConfirm">
      <section className="TourThumbnailCheck">
        <p>썸네일 사진</p>
        {thumbnailImage.length > 0 ? (
          <img src={thumbnailImage} alt="썸네일이미지" />
        ) : (
          <div style={{ color: "red", fontSize: "20px" }}>
            이미지 업로드 해주세요
          </div>
        )}
      </section>
      <hr />
      <section className="TourTitle">
        <p>제목</p>
        {title.length > 0 ? (
          <div>{title}</div>
        ) : (
          <div style={{ color: "red" }}>제목 입력해 주세요</div>
        )}
      </section>
      <section className="TourDuration">
        <p>기간</p>
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
        <p>최대 허용 인원</p>
        <div>{maxParticipants} 명</div>
      </section>
      <section className="TourExpectPrice">
        <p>예상 비용</p>
        <div>{price} 원</div>
      </section>
      <section className="TourTheme">
        <p>테마</p>
        <div className="TourThemeList">
          {categoryIds.map((cartegoryid) => {
            return (
              <div key={cartegoryid} className="TourThemeItem">
                {categoryItem[cartegoryid]["name"]}
              </div>
            );
          })}
        </div>
      </section>
      <section className="TourPlanList">
        <p>Plans</p>
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
        <p>당부사항</p>
        <div>{description}</div>
      </section>
      <section className="ButtonSection">
        <button onClick={goBeforePage}>이전</button>
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
          추가
        </button>
      </section>
    </div>
  );
};

export default Confirm;
