import { getStaticImage } from "../../utils/get-static-image";
import styles from "./Create1.module.css";
import { useState } from "react";
import { getStringedDate } from "@utils/get-stringed-date";

const Create1 = () => {
  const [uploadImgUrl, setUploadImgUrl] = useState("");
  const [duration, setDuration] = useState({
    start: new Date().toString(),
    end: new Date().toString(),
  });
  const [MaxPeople, setMaxPeople] = useState(1);
  const onImgChange = (e) => {
    const { files } = e.target;
    const uploadFile = files[0];
    const reader = new FileReader();
    reader.readAsDataURL(uploadFile);
    reader.onloadend = () => {
      setUploadImgUrl(reader.result);
    };
  };
  return (
    <section className={styles.Create1}>
      <div className={styles.Thumbnail}>
        <p>썸네일 사진</p>
        <label htmlFor="thumbnail">
          {uploadImgUrl !== "" ? (
            <img src={uploadImgUrl} alt="이미지 미리보기" />
          ) : (
            <div className={styles.emptythumbnail}>
              여기를 눌러
              <br />
              이미지를 추가해 주세요
            </div>
          )}
        </label>
        <input
          type="file"
          accept="image/*"
          name="thumbnail"
          id="thumbnail"
          onChange={onImgChange}
        />
      </div>
      <div className={styles.Date}>
        <p className={styles.title}>기간</p>
        <div className={styles.DateSection}>
          <input
            type="date"
            placeholder="시작날짜"
            className={styles.DateInput}
          />
          <span> ~ </span>
          <input
            type="date"
            placeholder="끝 날짜"
            className={styles.DateInput}
          />
        </div>
      </div>
      <div className={styles.MaxPeople}>
        <p>최대 인원</p>
        <div className={styles.MaxPeopleInput}>
          <img
            src={getStaticImage("minus")}
            alt=""
            onClick={() => {
              setMaxPeople((cur) => (cur > 1 ? cur - 1 : cur));
            }}
          />
          <div>{MaxPeople}</div>
          <img
            src={getStaticImage("add")}
            alt=""
            onClick={() => {
              setMaxPeople((cur) => (cur < 8 ? cur + 1 : cur));
            }}
          />
        </div>
      </div>
      <div className={styles.Cost}>
        <p>예상 비용</p>
        <input type="number" />
      </div>
      <div className={styles.Theme}>
        <div
          style={{
            display: "flex",
            justifyContent: "left",
            marginLeft: "10vw",
          }}
        >
          <div
            style={{ fontWeight: "bold", marginRight: "2vw", fontSize: "20px" }}
          >
            테마
          </div>
          <div>추가</div>
        </div>
      </div>
    </section>
  );
};

export default Create1;
