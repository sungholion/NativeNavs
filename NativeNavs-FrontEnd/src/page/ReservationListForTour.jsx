import React from "react";
import styles from "./ReservationListForTour.module.css";
import { upcomingTours, reservations } from "@/dummy";
import { useParams } from "react-router-dom";
import Tour_Item4 from "@/components/Tour_Item/Tour_Item4";
import Reservation_Item from "@/components/Reservation_Item/Reservation_Item";

const ReservationListForTour = () => {
  const params = useParams();
  const tour = upcomingTours[params.tour_id];

  return (
    <div className={styles.ReservationListForTour}>
      {/* 투어 정보 */}
      <div className={styles.TourInfo}>
        <Tour_Item4 tour={tour} />
      </div>

      {/* 예역 목록 */}
      <div className={styles.ReservationList}>
        {reservations.map((reservation) => {
          return (
            <Reservation_Item key={reservation.id} reservation={reservation} />
          );
        })}
      </div>
    </div>
  );
};

export default ReservationListForTour;
