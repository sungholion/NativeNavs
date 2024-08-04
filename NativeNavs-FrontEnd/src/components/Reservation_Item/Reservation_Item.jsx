import React from "react";
import styles from "./Reservation_Item.module.css";

const Reservation_Item = ({ reservation }) => {
  console.log(reservation);
  const formattedDate = new Date(
    reservation.reservation_date
  ).toLocaleDateString();

  return (
    <div className={styles.Reservation_Item}>
      {/* 상단 */}
      <div className={styles.Reservation_Top}>
        <img
          className={styles.Profile_Image}
          src={reservation.trav_profile_image}
          alt="프로필 이미지"
        />
        <div className={styles.Profile_Info}>
          <div className={styles.Profile_Info_Name}>
            <p className={styles.Profile_Name}>{reservation.trav_name} </p>
            <p> 님</p>
          </div>
          <p className={styles.Profile_Count}>
            인원 수: {reservation.trav_people}명
          </p>
        </div>
      </div>
      {/* 하단 */}
      <div className={styles.Reservation_Bottom}>
        <div className={styles.Reservation_Number}>
          <p>예약 번호</p>
          <p>{reservation.reservation_number}</p>
        </div>
        <div className={styles.Reservation_Date}>
          <p>예약 날짜</p>
          <p>{formattedDate}</p>
        </div>
        <div className={styles.Reservation_Message}>
          <p>요청 사항</p>
          <p>{reservation.trav_message}</p>
        </div>
      </div>
    </div>
  );
};

export default Reservation_Item;
