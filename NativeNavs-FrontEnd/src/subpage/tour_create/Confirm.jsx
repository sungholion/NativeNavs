import Button from "@/components/Button/Button";
import "./Confirm.css";
import Plan_Item from "@/components/Plan_Item/Plan_Item";
import { getStringedDate } from "@utils/get-stringed-date";
import { TourDataContext } from "./Tour_Create";
import { useContext } from "react";

const Confirm = ({ BeforePage }) => {
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
  } = useContext(TourDataContext);
  return (
    <div className="TourConfirm">
      <section className="TourThumbnailCheck">
        <p>썸네일 사진</p>
        <img src={thumbnail_image} alt="" />
      </section>
      <hr />
      <section className="TourTitle">
        <p>제목</p>
        <div>{title}</div>
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
        <Button text={"이전"} size={0} onClickEvent={BeforePage} />
        <Button text={"추가"} size={0} />
      </section>
    </div>
  );
};

export default Confirm;
