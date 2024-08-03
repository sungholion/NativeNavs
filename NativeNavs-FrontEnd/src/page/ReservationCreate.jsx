import ReservationEditor from "@/components/ReservationEditor/ReservationEditor";
import Tour_Item_mini_Reservation from "@/components/Tour_Item/Tour_Item_mini_Reservation";
import "./ReservationCreate.css";
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
      <ReservationEditor />
    </div>
  );
};

export default ReservationCreate;
