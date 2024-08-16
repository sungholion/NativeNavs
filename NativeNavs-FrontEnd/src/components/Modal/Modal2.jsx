import styles from "./Modal2.module.css";

function Modal2({ cancelTourReservation, clickModal, navigateBack, user }) {
  const handleCancelClick = async () => {
    await cancelTourReservation();
    navigateBack();
  };

  return (
    <div className={styles.Modal2}>
      <p className={styles.p}>
        {user && user.isKorean
          ? "정말로 취소하시겠습니까?"
          : "Are you sure you want to cancel?"}
      </p>
      <button className={styles.Button1} onClick={handleCancelClick}>
        {user && user.isKorean ? "예" : "Yes"}
      </button>
      <button className={styles.Button2} onClick={clickModal}>
        {user && user.isKorean ? "아니오" : "No"}
      </button>
    </div>
  );
}

export default Modal2;
