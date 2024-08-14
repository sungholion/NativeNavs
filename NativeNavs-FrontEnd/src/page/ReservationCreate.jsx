import ReservationEditor from "@/components/ReservationEditor/ReservationEditor";
import Tour_Item_mini_Reservation from "@/components/Tour_Item/Tour_Item_mini_Reservation";
import "./ReservationCreate.css";
import axios from "axios";
import { getStringedDate } from "@/utils/get-stringed-date";
import { getFromattedDatetime } from "@/utils/get-formatted-datetime";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import {
  navigateToReservationRegisterDetailFragment,
  showReservationRegisterFailDialog,
} from "@/utils/get-android-function";

const initData = {
  tourId: 0, 
  participantId: 0,
  date: new Date(), 
  startAt: new Date(), 
  endAt: new Date(), 
  participantCount: 1, 
  description: "", 
  meetingAddress: "", 
  meetingLatitude: -1, 
  meetingLongitude: -1, 
};

const ReservationCreate = () => {
  const params = useParams();

  const [travInfo, setTravInfo] = useState(null);

  const [tourInfo, setTourInfo] = useState(null);

  const [user, setUser] = useState(null);

  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  useEffect(() => {
    axios
      .get(`https://i11d110.p.ssafy.io/api/users/search/id/${params.trav_id}`)
      .then((res) => {
        console.log(res.data);
        setTravInfo(res.data);
      })
      .catch((err) => {
        console.log("해당 관광객 정보가 없어요.");
      });
  }, [params.trav_id]);

  useEffect(() => {
    axios
      .get(`https://i11d110.p.ssafy.io/api/tours/${params.tour_id}`)
      .then((res) => {
        console.log(res.data);
        setTourInfo(res.data);
      })
      .catch((err) => {
        console.log("해당 투어 정보가 없어요.");
      });
  }, [params.tour_id]);

  const requestResCreate = async (data, navToken) => {
    console.log(data);
    console.log(navToken);
    const resData = {
      tourId: data.tourId,
      participantId: Number(params.trav_id),
      date: getStringedDate(data.date),
      startAt: getFromattedDatetime(data.startAt),
      endAt: getFromattedDatetime(data.endAt),
      participantCount: data.participantCount,
      description: data.description,
      meetingAddress: data.meetingAddress,
      meetingLatitude: data.meetingLatitude,
      meetingLongitude: data.meetingLongitude,
    };
    console.log(resData);
    axios
      .post("https://i11d110.p.ssafy.io/api/reservations", resData, {
        headers: {
          "Content-Type": "application/json",
          Authorization: navToken,
        },
      })
      .then((res) => {
        const resId = Number(res.data.substr(res.data.indexOf(":") + 1));
        navigateToReservationRegisterDetailFragment(
          Number(params.tour_id),
          resId
        );
      })
      .catch((err) => {
        console.log(err);
        showReservationRegisterFailDialog();
      });
  };

  if (!travInfo || !tourInfo) {
    return <div>Loading...</div>;
  }

  return (
    <div className="ReservationCreate">
      <section className="TourItem">
        <Tour_Item_mini_Reservation
          image={tourInfo.thumbnailImage}
          title={tourInfo.title}
          score={tourInfo.reviewAverage}
          language={user?.isKorean ? "ko" : "en"}
          location={tourInfo.location}
        />
      </section>
      <section className="TravInforSection">
        <h4>
          {user.isKorean ? "예약 Trav 정보" : "Reservation Trav Information"}
        </h4>
        <div className="TravInfo">
          <img src={travInfo.image} alt="프로필사진" />
          <div>{travInfo.nickname}</div>
        </div>
        <section style={{ fontSize: "22px", width: "100%" }}>
          <hr
            style={{
              border: "1px solid #d9d9d9",
              alignSelf: "center",
              width: "100%",
              marginBottom: "10px",
            }}
          />
          <br />
          <div>
            {user?.isKorean
              ? "밑의 정보를 입력해 주세요"
              : "Please enter the information below"}
          </div>
        </section>
      </section>
      <ReservationEditor
        maxParticipant_info={tourInfo.maxParticipants}
        onSubmit={requestResCreate}
      />
    </div>
  );
};

export default ReservationCreate;
