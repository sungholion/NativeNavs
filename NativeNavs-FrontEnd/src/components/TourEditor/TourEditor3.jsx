import styles from "./TourEditor3.module.css";
import { useContext, useEffect, useState } from "react";
import { TourDataContext, TourDispatchContext } from "./TourEditorHead";

const MAX_DESCRIPTION_LENGTH = 1000; // 최대 당부사항 길이

const TourEditor3 = ({ goBeforePage, goAfterPage, user }) => {
  const { description } = useContext(TourDataContext);
  const { onTourDataChange } = useContext(TourDispatchContext);
  const [requestTest, setrequestTest] = useState(""); //

  useEffect(() => {
    if (description !== "") {
      setrequestTest(description);
    }
  }, [description]);
  return (
    <div className={styles.TourEditor3}>
      <p>{user && user.isKorean ? "당부 사항" : "Reminders"}</p>
      <div>
        <div className={styles.comments}>
          {user && user.isKorean
            ? "Trav에게 당부할 내용을 작성해 주세요"
            : "Please write any reminders for Trav"}
        </div>
        <div className={styles.lengthCounter}>
          {requestTest.length} / {MAX_DESCRIPTION_LENGTH}
        </div>
      </div>
      <div className={styles.Request}>
        <textarea
          name=""
          id=""
          value={requestTest}
          onChange={(e) => {
            if (e.target.value.length <= MAX_DESCRIPTION_LENGTH) {
              setrequestTest(e.target.value);
            }
          }}
        />
      </div>
      <section className={styles.ButtonSection}>
        <button
          className={styles.leftButton}
          onClick={() => {
            onTourDataChange("description", requestTest);
            goBeforePage();
          }}
        >
          {user && user.isKorean ? "뒤로" : "Back"}
        </button>
        <button
          className={styles.rightButton}
          onClick={() => {
            onTourDataChange("description", requestTest);
            goAfterPage();
          }}
        >
          {user && user.isKorean ? "다음" : "Next"}
        </button>
      </section>
    </div>
  );
};

export default TourEditor3;
