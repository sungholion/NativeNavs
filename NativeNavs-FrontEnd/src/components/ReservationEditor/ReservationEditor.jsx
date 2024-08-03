import { getStaticImage } from "@/utils/get-static-image";
import "./ReservationEditor.css";
import MapModal from "../MapModal/MapModal";
import { createPortal } from "react-dom";

const ReservationEditor = () => {
  return (
    <div className="ReservationEditor">
      <section className="ResDateSection">
        <h4>날짜</h4>
        <div className="DateInput">
          <input type="date" />
        </div>
      </section>
      <section className="Res_Time_Section">
        <h4>시간</h4>
        <div className="Res_Time">
          <div className="TimeInput">
            <span>시작시간 </span>
            <input type="time" />
          </div>
          <div className="TimeInput">
            <span>끝시간 </span>
            <input type="time" />
          </div>
        </div>
      </section>
      <section className="Res_People_Section">
        <h4>인원</h4>
        <div className="Res_People">
          <img src={getStaticImage("minus")} alt="" />
          <div>3명</div>
          <img src={getStaticImage("add")} alt="" />
        </div>
      </section>
      <section className="Res_Location_Section">
        <h4>위치</h4>
      </section>
      <section className="Res_ButtonSection">
        <button>예약하기</button>
        {createPortal(<MapModal />, document.body)}
      </section>
    </div>
  );
};

export default ReservationEditor;
