import ReservationEditor from "@/components/ReservationEditor/ReservationEditor";
import Tour_Item_mini_Reservation from "@/components/Tour_Item/Tour_Item_mini_Reservation";
import "./ReservationCreate.css";
import axios from "axios";
import { getStringedDate } from "@/utils/get-stringed-date";
import { getFromattedDatetime } from "@/utils/get-formatted-datetime";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";

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

const ReservationCreate = () => {
  const params = useParams();

  // 참가 관광객에 대한 정보 변수
  const [travInfo, setTravInfo] = useState(null);

  // 가이드 정보에 대한 정보 변수
  const [navInfo, setNavInfo] = useState(null);

  // 관련 투어 정보에 대한 정보 변수
  const [tourInfo, setTourInfo] = useState(null);

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

  // Nav 에 대한 정보 가져오기
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setNavInfo(parsedUser);
      console.log("NavInfo data : ", parsedUser);
    } else {
      console.log("해당 가이드 정보가 없어요");
    }
  }, []);

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
        />
      </section>
      <section className="TravInforSection">
        <h4>Trav 정보</h4>
        <div className="TravInfo">
          <img src={travInfo.image} alt="프로필사진" />
          <div>{travInfo.nickname}</div>
        </div>
      </section>
      <ReservationEditor />
    </div>
  );
};

export default ReservationCreate;
