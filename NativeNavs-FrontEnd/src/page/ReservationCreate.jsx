import ReservationEditor from "@/components/ReservationEditor/ReservationEditor";
import Tour_Item_mini_Reservation from "@/components/Tour_Item/Tour_Item_mini_Reservation";
import "./ReservationCreate.css";
import axios from "axios";
import { getStringedDate } from "@/utils/get-stringed-date";
import { getFromattedDatetime } from "@/utils/get-formatted-datetime";
const NotAllowImgData = true;

const initData = {
  tourId: 0, //투어 글에 대한 것
  date: new Date(), // 예약 날짜 -> yyyy-mm-dd로 바꾸기
  startAt: new Date(), // 시작시간 -> yyyy-mm-ddThh:mm:ss
  endAt: new Date(), //종료시간 -> yyyy-mm-ddThh:mm:ss
  participantCount: 1, //참가자 수
  description: "", // 주소(그 지역 이름 혹은 주소명)
  meetingLatitude: -1,
  meetingLongitude: -1,
};

const info = {
  tour: {
    // 투어 정보
    image:
      "https://cdn.pixabay.com/photo/2016/11/29/05/45/astronomy-1867616_960_720.jpg",
    title: "투어 제목",
    score: 5,
    nav: {
      // 가이드 정보
      image:
        "https://cdn.pixabay.com/photo/2016/11/29/05/45/astronomy-1867616_960_720.jpg",
      nickname: "가이드이름",
    },
  },
  trav: {
    // 예약자 정보 (닉네임 및 이미지)
    nickname: "Trav닉넴",
    iamge:
      "https://static.remove.bg/sample-gallery/graphics/bird-thumbnail.jpg",
  },
};

const onSubmittoServer = async (data) => {
  const requestData = {
    tourId: Number(data.tourId),
    date: getStringedDate(data.date),
    participantId: 10, //  관광객 id
    startAt: getFromattedDatetime(data.startAt),
    endAt: getFromattedDatetime(data.endAt),
    participantCount: data.participantCount,
    description: data.description,
    meetingLatitude: data.meetingLatitude,
    meetingLongitude: data.meetingLongitude,
  };
  console.log(requestData);
  await axios
    .post("https://i11d110.p.ssafy.io/api/reservations", requestData, {
      headers: {
        Authorization:
          "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlb2JsdWUyM0BnbWFpbC5jb20iLCJpYXQiOjE3MjI5MTI1NTgsImV4cCI6MTcyMjkxNjE1OH0.dVVuyK5poDw7PxqObckdDeciJ4yhYGGjap6ARlX07qf8wRBoeVpO5sYIrPbdQCuCl3GZGUKdDdnsf0bjnWHMVQ",
      },
    })
    .then((res) => {
      console.log(res);
      window.alert("성공했어요!");
    })
    .catch((err) => {
      console.error(err);
      window.alert("실패했어요 ㅠㅠ");
    });
};

const ReservationCreate = () => {
  return (
    <div className="ReservationCreate">
      <section className="TourItem">
        <Tour_Item_mini_Reservation tour={info.tour} />
      </section>
      <section className="TravInforSection">
        <h4>Trav 정보</h4>
        <div className="TravInfo">
          <img src={info.trav.iamge} alt="프로필사진" />
          <div>{info.trav.nickname}</div>
        </div>
      </section>
      <ReservationEditor onSubmit={onSubmittoServer} />
    </div>
  );
};

export default ReservationCreate;
