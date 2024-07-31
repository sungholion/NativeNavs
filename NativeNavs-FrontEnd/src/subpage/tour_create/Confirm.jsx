import Plan_Item from "@/components/Plan_Item/Plan_Item";
import { getStringedDate } from "@utils/get-stringed-date";

const Confirm = ({
  title,
  thumbnail_image,
  description,
  location,
  price,
  start_date,
  end_date,
  max_participant,
  plans,
}) => {
  return (
    <div className="TourConfirm">
      <section className="TourThumbnailCheck">
        <p>썸네일 사진</p>
        <img src="" alt="" />
      </section>
      <section className="TourDuration">
        <p>기간</p>
        <div>
          <span>{getStringedDate(new Date(start_date))}</span>
          <span>{getStringedDate(new Date(end_date))}</span>
        </div>
      </section>
      <section className="TourMaxPeople">
        <p>최대 허용 인원</p>
        <div>{max_participant}</div>
      </section>
      <section className="TourPlanList">
        <p>Plans</p>
        <div>
          {plans.map((item) => {
            return <Plan_Item key={item.plan_id} {...item} />;
          })}
        </div>
      </section>
      <section className="TourMapList"></section>
      <section className="Tourdescription">
        <p>당부사항</p>
        <div>{description}</div>
      </section>
      <section className="ButtonSection">
        <button> 이전</button>
        <button>추가</button>
      </section>
    </div>
  );
};

export default Confirm;
