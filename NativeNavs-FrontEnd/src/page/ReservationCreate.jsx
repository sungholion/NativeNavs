import Tour_Item_mini_Reservation from "@/components/Tour_Item/Tour_Item_mini_Reservation";
const info = {
  tour: {
    // 투어 정보
    image:
      "https://cdn.pixabay.com/photo/2016/11/29/05/45/astronomy-1867616_960_720.jpg",
    title: "투어 제목",
    nav: {
      // 가이드 정보
      image:
        "https://cdn.pixabay.com/photo/2016/11/29/05/45/astronomy-1867616_960_720.jpg",
      nickname: "가이드이름",
    },
  },
  progress: {
    // 예약 정보
    date: "2021-09-01",
    participant: 2,
  },
};

const ReservationCreate = () => {
  return (
    <div>
      <Tour_Item_mini_Reservation tour={info.tour} />
    </div>
  );
};

export default ReservationCreate;
