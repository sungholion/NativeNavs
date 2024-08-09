import styles from "./Modal2.module.css" 

function Modal2({ cancelTourReservation, clickModal, navigateBack }) {
    const handleCancelClick = async () => { // 비동기 함수 선언
        await cancelTourReservation(); // 투어 예약 취소가 될 때까지 wait 후 뒤로가기 실행
        navigateBack();
      };

  return (
    <div className={styles.Modal2}>
      <p className={styles.p} >정말로 취소하시겠습니까?</p>
      <button className={styles.Button1} onClick={handleCancelClick}>예</button>
      <button className={styles.Button2} onClick={clickModal}>아니오</button>
    </div>
  );
}

export default Modal2;
