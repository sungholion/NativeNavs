import Button from "@/components/Button/Button";
import "./Confirm.css";
import Plan_Item from "@/components/Plan_Item/Plan_Item";
import { getStringedDate } from "@utils/get-stringed-date";
import { TourDataContext } from "./Tour_Create";
import { useContext } from "react";
import axios from "axios";
import { API_Tour_Create } from "@/utils/apis";

const Tour_Create_Request = async (data) => {
  try {
    const response = await axios.post(API_Tour_Create, data);
    console.log(response);
    window.alert("성공했어요!");
    return response;
  } catch (error) {
    console.error(error);
    window.alert("실패했어요 ㅠㅠ");
    return error;
  }
};

const Confirm = ({ goBeforePage }) => {
  const {
    title,
    thumbnail_image,
    description,
    location,
    price,
    start_date,
    end_date,
    max_participant,
    plans,
    themes,
  } = useContext(TourDataContext);
  return (
    <div className="TourConfirm">
      <section className="TourThumbnailCheck">
        <p>썸네일 사진</p>
        {thumbnail_image.length > 0 ? (
          <img src={thumbnail_image} alt="썸네일이미지" />
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
            {getStringedDate(new Date(start_date))}
          </span>
          <span>~</span>
          <span className="DateBox">{getStringedDate(new Date(end_date))}</span>
        </div>
      </section>
      <hr />
      <section className="TourMaxPeople">
        <p>최대 허용 인원</p>
        <div>{max_participant} 명</div>
      </section>
      <section className="TourExpectPrice">
        <p>예상 비용</p>
        <div>{price} 원</div>
      </section>
      <section className="TourTheme">
        <p>테마</p>
        <div className="TourThemeList">
          {themes
            .filter((theme) => theme.state)
            .map((theme) => {
              return (
                <div key={theme.idx} className="TourThemeItem">
                  {theme.name}
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
              <Plan_Item
                key={item.plan_id}
                {...item}
                enableDeleteOption={false}
              />
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
        <Button text={"이전"} size={0} onClickEvent={goBeforePage} />
        <Button
          text={"추가"}
          size={0}
          onClickEvent={async () => {
            await Tour_Create_Request({
              title,
              thumbnail_image,
              description,
              location,
              price,
              startDate: getStringedDate(new Date(start_date)),
              endDate: getStringedDate(new Date(end_date)),
              max_participant,
              plans,
              categoryIds: themes
                .filter((theme) => theme.state)
                .map((theme) => theme.idx),
            });
          }}
        />
      </section>
    </div>
  );
};

export default Confirm;
