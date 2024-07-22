import styles from "./CompletedTravelList.module.css";
import CompletedTravel from "./CompletedTravel.jsx";
import { tours as dummyTours } from "./dummyData.jsx";
import { useState } from "react";

const CompletedTravelList = () => {
  const [tours, setTours] = useState(dummyTours);
  const onClickTravelDetail = (id) => {
    console.log(`${id}번 여행 상세 페이지로 이동`);
  };
  return (
    <div>
      {tours.map((tour) => {
        return (
          <CompletedTravel
            onClick={onClickTravelDetail(tour.id)}
            key={tour.id}
            name={tour.name}
            img_url={tour.img_url}
            title={tour.title}
            start_date={tour.start_date}
            regional_information={tour.regional_information}
          />
        );
      })}
    </div>
  );
};

export default CompletedTravelList;
