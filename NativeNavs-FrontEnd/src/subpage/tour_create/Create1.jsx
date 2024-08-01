import { getStaticImage } from "../../utils/get-static-image";
import styles from "./Create1.module.css";
import { useEffect, useState, useContext } from "react";
import { getStringedDate } from "@utils/get-stringed-date";
import Button from "./../../components/Button/Button";
import { TourDataContext, TourDispatchContext } from "./Tour_Create";

const Create1 = ({ BeforePage, goAfterPage }) => {
  const {
    title,
    thumbnail_image,
    start_date,
    end_date,
    price,
    max_participant,
    themes,
  } = useContext(TourDataContext);

  const { onTourDataChange } = useContext(TourDispatchContext);

  // onTourDataChange가 함수인지 확인
  if (typeof onTourDataChange !== "function") {
    throw new Error("onTourDataChange is not a function");
  }

  const onImgChange = (e) => {
    const { files } = e.target;
    if (files && files.length > 0) {
      const uploadFile = files[0];
      const reader = new FileReader();
      reader.readAsDataURL(uploadFile);
      reader.onloadend = () => {
        onTourDataChange("thumbnail_image", reader.result);
      };
    }
  };

  return (
    <section className={styles.Create1}>
      <div className={styles.Thumbnail}>
        <p>썸네일 사진</p>
        <label htmlFor="thumbnail">
          {thumbnail_image !== "" ? (
            <img src={thumbnail_image} alt="이미지 미리보기" />
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
      <div className={styles.Title}>
        <p>제목</p>
        <input
          type="text"
          value={title}
          onChange={(e) => onTourDataChange("title", e.target.value)}
        />
      </div>
      <div className={styles.Date}>
        <p className={styles.title}>기간</p>
        <div className={styles.DateSection}>
          <input
            type="date"
            placeholder="시작날짜"
            value={getStringedDate(new Date(start_date))}
            onChange={(e) =>
              onTourDataChange("start_date", new Date(e.target.value).getTime())
            }
            className={styles.DateInput}
          />
          <span> ~ </span>
          <input
            type="date"
            placeholder="끝 날짜"
            className={styles.DateInput}
            value={getStringedDate(new Date(end_date))}
            onChange={(e) =>
              onTourDataChange("end_date", new Date(e.target.value).getTime())
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
              if (max_participant > 1) {
                onTourDataChange("max_participant", max_participant - 1);
              }
            }}
          />
          <div>{max_participant}</div>
          <img
            src={getStaticImage("add")}
            alt=""
            onClick={() => {
              if (max_participant < 20) {
                onTourDataChange("max_participant", max_participant + 1);
              }
            }}
          />
        </div>
      </div>
      <div className={styles.Cost}>
        <p>예상 비용</p>
        <input
          type="number"
          value={price.toString()}
          onChange={(e) => {
            const newValue = e.target.value;
            if (!isNaN(newValue) && Number(newValue) >= 0) {
              onTourDataChange("price", Number(newValue));
            }
          }}
        />
      </div>
      <div className={styles.Theme}>
        <p>테마</p>
        <div className={styles.themeList}>
          {themes.map((theme) => {
            return (
              <button
                key={theme.idx}
                className={`${styles.themeButton} ${
                  theme.state ? styles.selected : styles.notselected
                }`}
                onClick={() => {
                  const newThemes = themes.map((t) => {
                    if (t.idx === theme.idx) {
                      return { ...t, state: !t.state };
                    } else {
                      return t;
                    }
                  });
                  onTourDataChange("themes", newThemes);
                  console.log(theme.state);
                }}
              >
                {theme.name}
              </button>
            );
          })}
        </div>
      </div>

      <div className={styles.ButtonSection}>
        <button
          onClick={() => {
            onTourDataChange("themes", themes);
            goAfterPage();
          }}
        >
          다음
        </button>
      </div>
    </section>
  );
};

export default Create1;
