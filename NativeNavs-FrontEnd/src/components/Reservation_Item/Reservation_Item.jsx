import React, {useEffect} from "react";
import styles from "./Reservation_Item.module.css";


const Reservation_Item = ({ participantInfo }) => {
  useEffect(() => 
    console.log(participantInfo)
  , [participantInfo])
  const formattedDate = new Date(
    participantInfo.reservationDate
  ).toLocaleDateString();

  return (
    <div className={styles.Reservation_Item}>
      {/* 상단 */}
      <div className={styles.Reservation_Top}>
        <img
          className={styles.Profile_Image}
          src={participantInfo.userImage}
          alt="프로필 이미지"
        />
        <div className={styles.Profile_Info}>
          <div className={styles.Profile_Info_Name}>
            <p className={styles.Profile_Name}>{participantInfo.userNickName} 님</p>
            <p> 님</p>
          </div>
          <p className={styles.Profile_Count}>
            인원 수: {participantInfo.participantCount}명
          </p>
        </div>
      </div>
      {/* 하단 */}
      <div className={styles.Reservation_Bottom}>
        <div className={styles.Reservation_Number}>
          <p>예약 번호</p>
          <p>{participantInfo.reservationNumber}</p>
        </div>
        <div className={styles.Reservation_Date}>
          <p>예약 날짜</p>
          <p>{formattedDate}</p>
        </div>
        <div className={styles.Reservation_Message}>
          <p>요청 사항</p>
          {/* <p>{participantInfo.trav_message}</p> */}
        </div>
      </div>
    </div>
  );
};

export default Reservation_Item;
