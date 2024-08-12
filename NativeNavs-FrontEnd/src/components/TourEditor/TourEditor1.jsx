import { getStaticImage } from "../../utils/get-static-image";
import styles from "./TourEditor1.module.css";
import { useEffect, useState, useContext } from "react";
import { getStringedDate } from "@utils/get-stringed-date";
import Button from "../Button/Button";
import { categoryItemKr, categoryItemEng } from "@/utils/constant";
import { TourDataContext, TourDispatchContext } from "./TourEditorHead";
import { getImageUrl } from "@/utils/get-image-url";

const TourEditor1 = ({ BeforePage, goAfterPage, user }) => {
  const {
    title,
    thumbnailImage,
    location,
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

  const chunkArray = (array, chunkSize) => {
    const results = [];
    for (let i = 0; i < array.length; i += chunkSize) {
      results.push(array.slice(i, i + chunkSize));
    }
    return results;
  };

  const categoryItems =
    user && user.isKorean ? categoryItemKr : categoryItemEng;
  const chunkedCategoryItems = chunkArray(categoryItems, 3);

  return (
    <section className={styles.TourEditor1}>
      <div className={styles.Thumbnail}>
        <p>{user && user.isKorean ? "썸네일 사진" : "Thumbnail Photo"}</p>
        <label htmlFor="thumbnail">
          {prevThumbnailImage !== "" ? (
            <img src={prevThumbnailImage} alt="이미지 미리보기" />
          ) : (
            <div className={styles.emptythumbnail}>
              {user && user.isKorean ? "여기를 눌러" : "Click here"}

              <br />
              {user && user.isKorean
                ? "이미지를 추가해 주세요"
                : "to add an image"}
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
        <p>{user && user.isKorean ? "제목" : "Title"}</p>
        <input
          type="text"
          value={title}
          onChange={(e) => onTourDataChange("title", e.target.value)}
        />
      </div>
      <div className={styles.Date}>
        <p className={styles.title}>
          {user && user.isKorean ? "기간" : "Duration"}
        </p>
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
        <p>{user && user.isKorean ? "최대 인원" : "Max Participants"}</p>
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
        <p>{user && user.isKorean ? "예상 비용" : "Estimated Cost"}</p>
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
      <div className={styles.locationSection}>
        <p>장소</p>
        <input
          type="text"
          onChange={(e) => {
            if (e.target.value.length <= 100) {
              onTourDataChange("location", e.target.value);
            }
          }}
        />
      </div>
      <div className={styles.Theme}>
        <p>{user && user.isKorean ? "테마" : "Theme"}</p>
        <div className={styles.themeList}>
          {chunkedCategoryItems.map((chunk, index) => (
            <div key={index} className={styles.themeRow}>
              {chunk.map((item) => (
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
                      onTourDataChange("categoryIds", [
                        ...categoryIds,
                        item.idx,
                      ]);
                    }
                  }}
                >
                  {item.name}
                </button>
              ))}
            </div>
          ))}
        </div>
      </div>

      <div className={styles.ButtonSection}>
        <button
          className={styles.rightButton}
          onClick={() => {
            onTourDataChange("themes");
            goAfterPage();
          }}
        >
          {user && user.isKorean ? "다음" : "Next"}
        </button>
      </div>
    </section>
  );
};

export default TourEditor1;
