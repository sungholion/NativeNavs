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
  tourId: 0, //투어 글에 대한 것
  participantId: 0,
  date: new Date(), // 예약 날짜 -> yyyy-mm-dd로 바꾸기
  startAt: new Date(), // 시작시간 -> yyyy-mm-ddThh:mm:ss
  endAt: new Date(), //종료시간 -> yyyy-mm-ddThh:mm:ss
  participantCount: 1, //참가자 수
  description: "", // 당부사항
  meetingAddress: "", // 만남 장소
  meetingLatitude: -1, // 만남 장소 위도
  meetingLongitude: -1, // 만남 장소 경도
};

const ReservationCreate = () => {
  const params = useParams();

  // 참가 관광객에 대한 정보 변수
  const [travInfo, setTravInfo] = useState(null);

  // 관련 투어 정보에 대한 정보 변수
  const [tourInfo, setTourInfo] = useState(null);

  const [user, setUser] = useState(null);

  // 컴포넌트가 마운트될 때 localStorage에서 유저 정보를 가져옴
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  // 해당 관강객에 대한 정보 가져오기
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

  // 투어 정보 가져오기
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
        // console.log(res);
        // console.log(Number(params.tour_id));
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
        />
      </section>
      <section className="TravInforSection">
        <h4>{user.isKorean ? "Trav 정보" : "Trav Information"}</h4>
        <div className="TravInfo">
          <img src={travInfo.image} alt="프로필사진" />
          <div>{travInfo.nickname}</div>
        </div>
      </section>
      <ReservationEditor
        maxParticipant_info={tourInfo.maxParticipants}
        onSubmit={requestResCreate}
      />
    </div>
  );
};

export default ReservationCreate;
