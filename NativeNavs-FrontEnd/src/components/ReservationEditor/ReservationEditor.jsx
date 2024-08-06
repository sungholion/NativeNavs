import { getStaticImage } from "@/utils/get-static-image";
import "./ReservationEditor.css";
import MapModal from "../MapModal/MapModal";
import { createPortal } from "react-dom";
import { act, useEffect, useReducer, useState } from "react";
import { getStringedDate } from "@/utils/get-stringed-date";
import { getStringedTime } from "@/utils/get-stringed-time";
import { getDateObjWithString } from "@/utils/get-date-obj-with-string";
import axios from "axios";
import { useParams } from "react-router-dom";

const IMPOSSIBLE_CORD = -1000;

// 초기 예약 정보 데이터들, tourId 및 participantId가 필요
const initData = {
  tourId: 0, //투어 글에 대한 것
  date: new Date(), // 예약 날짜 -> yyyy-mm-dd로 바꾸기
  startAt: new Date(), // 시작시간 -> yyyy-mm-ddThh:mm:ss
  endAt: new Date(), //종료시간 -> yyyy-mm-ddThh:mm:ss
  participantCount: 1, //참가자 수
  description: "", // 주소(그 지역 이름 혹은 주소명)
  meetingLatitude: IMPOSSIBLE_CORD, // 위도
  meetingLongitude: IMPOSSIBLE_CORD, // 경도
};

// 예약 정보 데이터에 대한 reducer
const reducer = (state, action) => {
  switch (action.type) {
    case "init":
      return action.data;
    case "date":
      return { ...state, date: action.data };
    case "startAt":
    case "endAt":
      return { ...state, [action.type]: action.data };
    case "participantCount":
      return { ...state, participantCount: action.data };
    case "mapInput":
      return {
        ...state,
        description: action.data.description,
        meetingLatitude: action.data.lat,
        meetingLongitude: action.data.lng,
      };
    default:
      return state;
  }
};

const ReservationEditor = ({ onSubmit }) => {
  const [openMapModal, setToggleMapModal] = useState(false); //맵 지도와 관련된 모달 state
  const [maxParticipants, setMaxParticipants] = useState(10); //최대참가자수
  const params = useParams();
  const openModal = () => {
    setToggleMapModal(true);
  };
  const closeModal = () => {
    setToggleMapModal(false);
  };
  const [resInfo, dispatch] = useReducer(reducer);
  useEffect(() => {
    dispatch({
      type: "init",
      data: {
        ...initData,
        tourId: params.tour_id,
      },
    });
  }, [params.tour_id]);

  const onChangeDate = (e) => {
    dispatch({
      type: "date",
      data: new Date(e.target.value),
    });
  };

  const onChangeHour = (e) => {
    if (resInfo.startAt.getTime())
      dispatch({
        type: e.target.name,
        data: getDateObjWithString(
          getStringedDate(resInfo.date),
          e.target.value
        ),
      });
  };

  const onChangeParticipant = (value) => {
    if (
      resInfo.participantCount + value > 0 &&
      resInfo.participantCount + value < maxParticipants
    ) {
      dispatch({
        type: "participantCount",
        data: resInfo.participantCount + value,
      });
    }
  };

  const onEditLocation = (data) => {
    dispatch({
      type: "mapInput",
      data,
    });
  };

  if (!resInfo) {
    // 초기값 설정 전에 로딩
    return <div>Loading</div>;
  }

  return (
    <div className="ReservationEditor">
      <section className="ResDateSection">
        <h4>날짜</h4>
        <div className="DateInput">
          <input
            type="date"
            value={getStringedDate(resInfo.date)}
            name="date"
            onChange={onChangeDate}
          />
        </div>
      </section>
      <section className="Res_Time_Section">
        <h4>시간</h4>
        <div className="Res_Time">
          <div className="TimeInput">
            <span>시작시간 </span>
            <input
              type="time"
              onChange={onChangeHour}
              name="startAt"
              value={getStringedTime(resInfo.startAt)}
            />
          </div>
          <div className="TimeInput">
            <span>끝시간 </span>
            <input
              type="time"
              onChange={onChangeHour}
              name="endAt"
              value={getStringedTime(resInfo.endAt)}
            />
          </div>
        </div>
      </section>
      <section className="Res_People_Section">
        <h4>인원</h4>
        <div className="Res_People">
          <img
            src={getStaticImage("minus")}
            alt=""
            onClick={() => {
              onChangeParticipant(-1);
            }}
          />
          <div>{resInfo.participantCount}명</div>
          <img
            src={getStaticImage("add")}
            alt=""
            onClick={() => {
              onChangeParticipant(1);
            }}
          />
        </div>
      </section>
      <section className="Res_Location_Section">
        <div className="Res_Location_Section_Header">
          <h4>위치</h4>
          <img src={getStaticImage("search")} alt="" onClick={openModal} />
          {resInfo.description !== "" && <div>{resInfo.description}</div>}
        </div>
        {openMapModal &&
          createPortal(
            <MapModal onClose={closeModal} onSubmit={onEditLocation} />,
            document.body
          )}
      </section>
      <section className="Res_ButtonSection">
        <button
          onClick={() => {
            onSubmit(resInfo);
          }}
        >
          예약하기
        </button>
      </section>
    </div>
  );
};

export default ReservationEditor;
