import React, { useEffect } from "react";
import styles from "./Reservation_Item.module.css";

const Reservation_Item = ({
  participantInfo,
  navigateToReservationDetailFragment,
  user,
}) => {
  // tour date formatting
  const formatDate = (date) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const dateString = new Date(date).toLocaleDateString("ko-KR", options);
    return dateString.replace(/\.$/, "").replace(/\s/g, ""); // 마지막 점 제거 후 공백 제거
  };

  return (
    <div
      className={styles.Reservation_Item}
      onClick={() =>
        navigateToReservationDetailFragment(participantInfo.reservationId)
      }
    >
      {/* 상단 */}
      <div className={styles.Reservation_Top}>
        <img
          className={styles.Profile_Image}
          src={participantInfo.userImage}
          alt="프로필 이미지"
        />
        <div className={styles.Profile_Info}>
          <div className={styles.Profile_Info_Name}>
            <p className={styles.Profile_Name}>
              {user && user.isKorean
                ? `${participantInfo.userNickName} 님`
                : participantInfo.userNickName}
            </p>
          </div>
          <p className={styles.Profile_Count}>
            {user && user.isKorean
              ? `인원 수: ${participantInfo.participantCount}명`
              : `Number of participants: ${participantInfo.participantCount}`}
          </p>
        </div>
      </div>
      {/* 하단 */}
      <div className={styles.Reservation_Bottom}>
        <div className={styles.Reservation_Number}>
          <p>{user && user.isKorean ? "예약 번호" : "Reservation Number"}</p>
          <p>{participantInfo.reservationNumber}</p>
        </div>
        <div className={styles.Reservation_Date}>
          <p>{user && user.isKorean ? "예약 날짜" : "Reservation Date"}</p>
          <p>{formatDate(participantInfo.reservationDate)}</p>
        </div>
        <div className={styles.Reservation_Message}>
          <p>{user && user.isKorean ? "요청 사항" : "Requests"}</p>
          {/* <p>{participantInfo.trav_message}</p> */}
        </div>
      </div>
    </div>
  );
};

export default Reservation_Item;
