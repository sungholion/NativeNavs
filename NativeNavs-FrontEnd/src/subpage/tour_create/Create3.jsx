import Button from "@/components/Button/Button";
import styles from "./Create3.module.css";
import { useState } from "react";
const Create3 = ({ BeforePage, AfterPage, onTourDataChange }) => {
  const [requestTest, setrequestTest] = useState("");
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
      <Button
        size={3}
        text={"다음"}
        onClickEvent={() => {
          if (requestTest.length > 0) {
            onTourDataChange("description", requestTest);
            AfterPage();
          } else {
            window.alert("공란 금지!");
          }
        }}
      />
    </div>
  );
};

export default Create3;
