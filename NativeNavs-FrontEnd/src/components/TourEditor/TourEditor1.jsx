import { getStaticImage } from "../../utils/get-static-image";
import styles from "./TourEditor1.module.css";
import { useEffect, useState, useContext } from "react";
import { getStringedDate } from "@utils/get-stringed-date";
import Button from "../Button/Button";
import { categoryItem } from "@/utils/constant";
import { TourDataContext, TourDispatchContext } from "./TourEditorHead";
import { getImageUrl } from "@/utils/get-image-url";

const TourEditor1 = ({ BeforePage, goAfterPage }) => {
  const {
    title,
    thumbnailImage,
    startDate,
    endDate,
    price,
    maxParticipants,
    categoryIds,
  } = useContext(TourDataContext);

  const { onTourDataChange } = useContext(TourDispatchContext);

  const [prevThumbnailImage, setPrevThumbnailImage] = useState(""); //썸네일 이미지 미리보기
  // onTourDataChange가 함수인지 확인
  if (typeof onTourDataChange !== "function") {
    throw new Error("onTourDataChange is not a function");
  }

  // 썸네일 이미지
  // 투어 생성인 경우 썸네일 이미지가 아직 객체 상태
  // 아닌 경우 - 서버로드 이미지인경우 URL이니 그대로 사용
  useEffect(() => {
    getImageUrl(thumbnailImage, setPrevThumbnailImage);
  }, [thumbnailImage]);

  const onImgChange = (e) => {
    const { files } = e.target;
    if (files && files.length > 0) {
      const uploadFile = files[0];
      onTourDataChange("thumbnailImage", uploadFile);
      const reader = new FileReader();
      reader.readAsDataURL(uploadFile);
      reader.onloadend = () => {
        setPrevThumbnailImage(reader.result);
      };
    }
  };

  return (
    <section className={styles.TourEditor1}>
      <div className={styles.Thumbnail}>
        <p>썸네일 사진</p>
        <label htmlFor="thumbnail">
          {prevThumbnailImage !== "" ? (
            <img src={prevThumbnailImage} alt="이미지 미리보기" />
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
            value={getStringedDate(new Date(startDate))}
            onChange={(e) =>
              onTourDataChange(
                "startDate",
                getStringedDate(new Date(e.target.value))
              )
            }
            className={styles.DateInput}
          />
          <span> ~ </span>
          <input
            type="date"
            placeholder="끝 날짜"
            className={styles.DateInput}
            value={getStringedDate(new Date(endDate))}
            onChange={(e) =>
              onTourDataChange(
                "endDate",
                getStringedDate(new Date(e.target.value))
              )
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
              if (maxParticipants > 1) {
                onTourDataChange("maxParticipants", maxParticipants - 1);
              }
            }}
          />
          <div>{maxParticipants}</div>
          <img
            src={getStaticImage("add")}
            alt=""
            onClick={() => {
              console.log(maxParticipants);
              if (maxParticipants < 20) {
                onTourDataChange("maxParticipants", maxParticipants + 1);
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
          {categoryItem.map((item) => {
            return (
              <button
                key={item.idx}
                className={`${styles.themeButton} ${
                  categoryIds.includes(item.idx)
                    ? styles.selected
                    : styles.notselected
                }`}
                onClick={() => {
                  if (categoryIds.includes(item.idx)) {
                    onTourDataChange(
                      "categoryIds",
                      categoryIds.filter((id) => id !== item.idx)
                    );
                  } else {
                    onTourDataChange("categoryIds", [...categoryIds, item.idx]);
                  }
                }}
              >
                {item.name}
              </button>
            );
          })}
        </div>
      </div>

      <div className={styles.ButtonSection}>
        <button
          onClick={() => {
            onTourDataChange("themes");
            goAfterPage();
          }}
        >
          다음
        </button>
      </div>
    </section>
  );
};

export default TourEditor1;
