import Button from "@/components/Button/Button";
import styles from "./Create3.module.css";
import { useContext, useState } from "react";
import { TourDataContext, TourDispatchContext } from "./Tour_Create";
const Create3 = ({ goBeforePage, goAfterPage }) => {
  const { description } = useContext(TourDataContext);
  const { onTourDataChange } = useContext(TourDispatchContext);
  const [requestTest, setrequestTest] = useState(""); // Define requestTest state variable and setrequestTest function
  return (
    <div className={styles.Create3}>
      <p>당부 사항</p>
      <div className={styles.comments}>
        Trav에게 당부할 내용을 작성해 주세요
      </div>
      <div className={styles.Request}>
        <textarea
          name=""
          id=""
          value={requestTest}
          onChange={(e) => setrequestTest(e.target.value)}
        />
      </div>
      <section className={styles.ButtonSection}>
        <button onClick={goBeforePage}>뒤로</button>
        <button onClick={goAfterPage}>앞으로</button>
      </section>
    </div>
  );
};

export default Create3;
