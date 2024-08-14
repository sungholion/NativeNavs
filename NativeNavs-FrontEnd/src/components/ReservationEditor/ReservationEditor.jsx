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

const initData = {
  tourId: 0,
  date: new Date(),
  startAt: new Date(),
  endAt: new Date(),
  participantCount: 1,
  description: "",
  meetingAddress: "",
  meetingLatitude: IMPOSSIBLE_CORD,
  meetingLongitude: IMPOSSIBLE_CORD,
};

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
  const [user, setUser] = useState(null);

  const [buttonStatus, setButtonStatus] = useState(0);
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  const [openMapModal, setToggleMapModal] = useState(false);
  const [maxParticipants, setMaxParticipants] = useState(
    maxParticipant_info || 1
  );
  const params = useParams();
  const openModal = () => {
    setToggleMapModal(true);
  };
  const closeModal = () => {
    setToggleMapModal(false);
  };
  const [resInfo, dispatch] = useReducer(reducer, { ...initData });

  useEffect(() => {
    dispatch({
      type: "init",
      data: {
        ...initData,
        tourId: Number(params.tour_id),
      },
    });
  }, [params.tour_id]);

  const onChangeDate = useCallback((e) => {
    dispatch({
      type: "date",
      data: new Date(e.target.value),
    });
  }, []);

  const onChangeHour = (e) => {
    console.log(resInfo.date);
    dispatch({
      type: e.target.name,
      data: getDateObjWithString(getStringedDate(resInfo.date), e.target.value),
    });
  };

  const onChangeParticipant = (value) => {
    if (
      resInfo.participantCount + value > 0 &&
      resInfo.participantCount + value <= maxParticipants
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
