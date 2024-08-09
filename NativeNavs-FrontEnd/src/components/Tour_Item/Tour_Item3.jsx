import styles from "./Tour_item3.module.css";

const Tour_Item3 = ({
  tour,
  navigateToReservationListFragmentReservationDetail,
}) => {
  // tour date formatting
  const formatDate = (date) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const dateString = new Date(date).toLocaleDateString("ko-KR", options);
    return dateString.replace(/\.$/, "").replace(/\s/g, ""); // 마지막 점 제거 후 공백 제거
  };

  return (
    <div
      onClick={() =>
        navigateToReservationListFragmentReservationDetail(
          tour.tourId,
          tour.reservationId
        )
      }
      className={styles.Tour_Item3}
    >
      <div className={styles.tour_left_info}>
        <img
          className={styles.thumbnailImage}
          src={tour.thumbnailImage}
          alt="투어 이미지"
        />
      </div>
      <div className={styles.tour_right_info}>
        <p className={styles.title}>{tour.tourTitle}</p>
        <p>{tour.guide.nickname}</p>
        <p>{formatDate(tour.reservationDate)}</p>
      </div>
    </div>
  );
};

export default Tour_Item3;
