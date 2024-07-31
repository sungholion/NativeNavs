import { getStaticImage } from "../../utils/get-static-image";
import styles from "./Create1.module.css";
import { useState } from "react";
import { getStringedDate } from "@utils/get-stringed-date";
import Button from "./../../components/Button/Button";

const Create1 = ({ BeforePage, AfterPage, onTourDataChange }) => {
  const [uploadImgUrl, setUploadImgUrl] = useState("");
  const [duration, setDuration] = useState({
    start: new Date().getTime(),
    end: new Date().getTime(),
  });
  const [MaxPeople, setMaxPeople] = useState(1);
  const [expectedMoney, setExpectedMoney] = useState(0);
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
            value={getStringedDate(new Date(duration.start))}
            onChange={(e) =>
              setDuration((cur) => {
                return { ...cur, start: new Date(e.target.value).getTime() };
              })
            }
            className={styles.DateInput}
          />
          <span> ~ </span>
          <input
            type="date"
            placeholder="끝 날짜"
            className={styles.DateInput}
            value={getStringedDate(new Date(duration.end))}
            onChange={(e) =>
              setDuration((cur) => {
                return { ...cur, end: new Date(e.target.value).getTime() };
              })
            }
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
        <input
          type="number"
          value={expectedMoney}
          onChange={(e) => {
            try {
              setExpectedMoney(Number(e.target.value));
            } catch (err) {
              setExpectedMoney(0);
            }
          }}
        />
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
      <Button
        size={3}
        text={"다음"}
        onClickEvent={() => {
          console.log(MaxPeople, duration.start, duration.end, expectedMoney);
          if (
            uploadImgUrl !== "" &&
            MaxPeople &&
            expectedMoney > 0 &&
            duration.start &&
            duration.end &&
            duration.start <= duration.end
          ) {
            onTourDataChange("thumbnail_image", uploadImgUrl);
            onTourDataChange("start_date", duration.start);
            onTourDataChange("end_date", duration.end);
            onTourDataChange("max_participant", MaxPeople);
            onTourDataChange("price", expectedMoney);
            AfterPage();
          } else {
            window.alert("잘못되거나 누락된 정보가 있어요!\n확인해 주세요");
          }
        }}
      />
    </section>
  );
};

export default Create1;
