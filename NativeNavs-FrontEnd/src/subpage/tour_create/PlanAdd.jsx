import Button from "@/components/Button/Button";
import styles from "./PlanAdd.module.css";
const PlanAdd = ({ toggleEvent }) => {
  return (
    <div className={styles.PlanAdd}>
      <section className={styles.Header}>헤더</section>
      <section className={styles.Map}>맵</section>
      <section className={styles.PlanContent}>작성</section>
      <section className={styles.ButtonArea}>
        <Button text={"abcd"} size={3} onClickEvent={toggleEvent} />
      </section>
    </div>
  );
};

export default PlanAdd;
