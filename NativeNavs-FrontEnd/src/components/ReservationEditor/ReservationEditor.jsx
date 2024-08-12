import { getStaticImage } from "@/utils/get-static-image";
import "./ReservationEditor.css";
import MapModal from "../MapModal/MapModal";
import { createPortal } from "react-dom";
import { act, useCallback, useEffect, useReducer, useState } from "react";
import { getStringedDate } from "@/utils/get-stringed-date";
import { getStringedTime } from "@/utils/get-stringed-time";
import { getDateObjWithString } from "@/utils/get-date-obj-with-string";
import axios from "axios";
import { useParams } from "react-router-dom";

const IMPOSSIBLE_CORD = -1000;
const MAX_DESCRIPTION_LENGTH = 300;

// 초기 예약 정보 데이터들, tourId 및 participantId가 필요
const initData = {
  tourId: 0, //투어 글에 대한 것
  date: new Date(), // 예약 날짜 -> yyyy-mm-dd로 바꾸기
  startAt: new Date(), // 시작시간 -> yyyy-mm-ddThh:mm:ss
  endAt: new Date(), //종료시간 -> yyyy-mm-ddThh:mm:ss
  participantCount: 1, //참가자 수
  description: "", // 당부사항
  meetingAddress: "", //주소
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
        meetingAddress: action.data.description,
        meetingLatitude: action.data.lat,
        meetingLongitude: action.data.lng,
      };
    case "description":
      return { ...state, description: action.data };
    default:
      return state;
  }
};

const ReservationEditor = ({ maxParticipant_info, onSubmit }) => {
  // 유저정보가져오기 -> language 전용
  const [user, setUser] = useState(null);

  // 버튼 상태 -> 0 : 입력불가 , 1 : 입력가능, 2 : 입력된 상태 - 로딩
  const [buttonStatus, setButtonStatus] = useState(0);
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  const [openMapModal, setToggleMapModal] = useState(false); //맵 지도와 관련된 모달 state
  const [maxParticipants, setMaxParticipants] = useState(
    maxParticipant_info || 1
  ); //최대참가자수
  const params = useParams();
  const openModal = () => {
    setToggleMapModal(true);
  };
  const closeModal = () => {
    setToggleMapModal(false);
  };
  const [resInfo, dispatch] = useReducer(reducer, { ...initData });

  // 투어 id에 대한 정보
  useEffect(() => {
    dispatch({
      type: "init",
      data: {
        ...initData,
        tourId: Number(params.tour_id),
      },
    });
  }, [params.tour_id]);

  // 예약 날짜
  const onChangeDate = useCallback((e) => {
    dispatch({
      type: "date",
      data: new Date(e.target.value),
    });
  }, []);

  // 시간
  const onChangeHour = (e) => {
    console.log(resInfo.date);
    dispatch({
      type: e.target.name,
      data: getDateObjWithString(getStringedDate(resInfo.date), e.target.value),
    });
  };

  // 참가자 수
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

  // 지역 위치
  const onEditLocation = (data) => {
    dispatch({
      type: "mapInput",
      data,
    });
  };

  // 당부사항
  const onDescriptionChange = (e) => {
    if (e.target.value.length < 300)
      dispatch({
        type: "description",
        data: e.target.value,
      });
  };

  useEffect(() => {
    console.log("a");
    setButtonStatus(() => {
      if (!resInfo) return 0;
      if (
        resInfo.meetingAddress === "" ||
        resInfo.meetingLatitude === IMPOSSIBLE_CORD ||
        resInfo.meetingLongitude === IMPOSSIBLE_CORD
      ) {
        console.log("위치 입력해주세요");
        return 0;
      }
      if (resInfo.description.length === 0) {
        console.log("당부사항 입력해주세요");
        return 0;
      }
      if (!resInfo.date) {
        console.log("날짜 입력해주세요");
        return 0;
      }
      if (!resInfo.startAt || !resInfo.endAt) {
        console.log("시간 입력해주세요");
        return 0;
      }
      if (resInfo.startAt >= resInfo.endAt) {
        console.log("시작시간이 종료시간보다 늦어요.");
        return 0;
      }
      return 1;
    });
  }, [resInfo]);

  if (!resInfo) {
    // 초기값 설정 전에 로딩
    return <div>Loading</div>;
  }

  return (
    <div className="ReservationEditor">
      <section className="ResDateSection">
        <h4 style={{ color: `${!resInfo.date ? "lightcoral" : ""}` }}>
          {user?.isKorean ? "투어 예약 날짜" : "Tour planned date"}
        </h4>
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
        <h4
          style={{
            color: `${
              !resInfo.startAt ||
              !resInfo.endAt ||
              resInfo.startAt >= resInfo.endAt
                ? "lightcoral"
                : ""
            }`,
          }}
        >
          {user?.isKorean ? "투어 예정 시간" : "The scheduled time of the tour"}
        </h4>
        <div className="Res_Time">
          <div className="TimeInput">
            <span>{user?.isKorean ? "시작시간" : "Start Time"}</span>
            <input
              type="time"
              onChange={onChangeHour}
              name="startAt"
              value={getStringedTime(resInfo.startAt)}
            />
          </div>
          <div className="TimeInput">
            <span>{user?.isKorean ? "종료시간" : "End Time"}</span>
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
        <h4>{user?.isKorean ? "참여 인원" : "Number of participants"}</h4>
        <div className="Res_People">
          <img
            src={getStaticImage("minus")}
            style={{ cursor: "pointer", width: "40px", height: "40px" }}
            alt=""
            onClick={() => {
              onChangeParticipant(-1);
            }}
          />
          <div>{resInfo.participantCount}</div>
          <img
            src={getStaticImage("add")}
            style={{ cursor: "pointer", width: "40px", height: "40px" }}
            alt=""
            onClick={() => {
              onChangeParticipant(1);
            }}
          />
        </div>
      </section>
      <section className="Res_Location_Section">
        <div className="Res_Location_Section_Header">
          <h4
            style={{
              color: `${resInfo.meetingAddress === "" ? "lightcoral" : ""}`,
            }}
          >
            {user?.isKorean ? "만나는 위치" : "Meeting place"}
          </h4>
          <img src={getStaticImage("search")} alt="" onClick={openModal} />
          <div className="Res_Location_Info">
            {resInfo.meetingAddress === ""
              ? user?.isKorean
                ? "위치를 검색해주세요"
                : "Please Search the location"
              : resInfo.meetingAddress}
          </div>
        </div>
        {openMapModal &&
          createPortal(
            <MapModal onClose={closeModal} onSubmit={onEditLocation} />,
            document.body
          )}
      </section>
      <section className="Res_Description_Section">
        <div className="Res_Description_header">
          <h4>{user?.isKorean ? "당부사항" : "Reminder"}</h4>
          <div>
            {resInfo.description.length} / {MAX_DESCRIPTION_LENGTH}
          </div>
        </div>
        <textarea
          name="description"
          onChange={onDescriptionChange}
          value={resInfo.description}
        />
      </section>
      <section className="Res_ButtonSection">
        <button
          className={`${buttonStatus === 0 ? "disable" : ""} ${
            buttonStatus === 1 ? "able" : ""
          } ${buttonStatus === 2 ? "loading" : ""}`}
          onClick={async () => {
            if (buttonStatus === 1) {
              setButtonStatus(2);
              await onSubmit(resInfo, user.userToken);
              setButtonStatus(1);
            }
          }}
        >
          {user?.isKorean
            ? buttonStatus === 0
              ? "빨간색 부분 확인해 주세요"
              : buttonStatus === 1
              ? "예약하기"
              : "예약중"
            : buttonStatus === 0
            ? "Check the RED part"
            : buttonStatus === 1
            ? "Make Reservation"
            : "Reserving"}
        </button>
      </section>
    </div>
  );
};

export default ReservationEditor;
