import styles from "./PlanModal.module.css";
import { useSearchParams } from "react-router-dom";

const PlanData = {
  image:
    "https://i.namu.wiki/i/u253q5zv58zJ1twYkeea-czVz8SQsvX-a1jVZ8oYsTVDH_TRC8-bpcVa4aKYQs5lI55B9srLYF9JJFUPbkI8MA.webp",
};
const PlanModal = ({ onClose }) => {
  const onClickEvent = (e) => {
    e.stopPropagation;
    onClose();
  };
  return (
    <div
      className={styles.ModalBackground}
      onClick={(e) => {
        onClickEvent(e);
      }}
    >
      <div
        className={styles.Modal}
        onClick={(e) => {
          e.stopPropagation();
        }}
      >
        <section className={styles.ModalHeader}>
          <div></div>
          <img src={PlanData.image} alt="" />
          <div className={styles.ModalHeaderTitle}>
            <h3>제목</h3>
            <input
              type="text"
              placeholder="일정 제목 입력해 주세요"
              maxLength="20"
            />
          </div>
        </section>
        <section className={styles.ModalMap}>
          <div className={styles.ModalMapSearch}>
            <h3>위치 검색</h3>
            <div>useSearchParams</div>
          </div>
          <div>구글 맵</div>
        </section>
        <section className={styles.ModalContent}>
          <h3>내용</h3>
          <textarea name="" id=""></textarea>
        </section>
      </div>
    </div>
  );
};

export default PlanModal;
