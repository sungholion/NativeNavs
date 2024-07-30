import styles from "./Create3.module.css";
const Create3 = () => {
  return (
    <div className={styles.Create3}>
      <p>당부 사항</p>
      <div className={styles.comments}>
        Trav에게 당부할 내용을 작성해 주세요
      </div>
      <div className={styles.Request}>
        <textarea name="" id="" />
      </div>
    </div>
  );
};

export default Create3;
