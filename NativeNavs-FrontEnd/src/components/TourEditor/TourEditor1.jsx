import { getStaticImage } from "../../utils/get-static-image";
import styles from "./TourEditor1.module.css";
import { useEffect, useState, useContext } from "react";
import { getStringedDate } from "@utils/get-stringed-date";
import Button from "../Button/Button";
import { categoryItemKr, categoryItemEng } from "@/utils/constant";
import { TourDataContext, TourDispatchContext } from "./TourEditorHead";
import { getImageUrl } from "@/utils/get-image-url";

const checkData = (data) => {
  let errorDate = 0;
  if (data.thumbnailImage === "" || !data.thumbnailImage) {
    errorDate |= 1 << 0;
  }
  if (data.title.trim() === "") {
    errorDate |= 1 << 1;
  }
  if (!data.startDate || !data.endDate || data.startDate > data.endDate) {
    errorDate |= 1 << 2;
  }
  if (data.maxParticipants < 1) {
    errorDate |= 1 << 3;
  }
  if (data.price < 0) {
    errorDate |= 1 << 4;
  }
  if (data.location.trim() === "") {
    errorDate |= 1 << 5;
  }
  if (data.categoryIds.length === 0 || data.categoryIds.length > 4) {
    errorDate |= 1 << 6;
  }

  return errorDate;
};

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

  const [checkInput, setCheckInput] = useState(0); // 2진수로 각 자리 위치마다 문제점 표시
  // 1: thumbnail, 2: title, 4: date, 8: maxPeople, 16: cost, 32: location, 64: theme

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
      if (uploadFile.size > 1024 * 1024 * 10) {
        alert("10MB 이하의 이미지만 업로드 가능합니다.");
        return;
      }
      setCheckInput((cur) => cur & ~(1 << 0));
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
        <p style={{ color: `${checkInput & (1 << 0) ? "lightcoral" : ""}` }}>
          {user && user.isKorean ? "썸네일 사진" : "Thumbnail Photo"}
        </p>
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
        <div
          style={{ textAlign: "center", marginTop: "10px", color: "#595959" }}
        >
          {user?.isKorean
            ? "10MB 이하 이미지만 업로드 가능합니다"
            : "Image must be 10MB or less"}
        </div>
      </div>
      <div className={styles.Title}>
        <p style={{ color: `${checkInput & (1 << 1) ? "lightcoral" : ""}` }}>
          {user && user.isKorean ? "제목" : "Title"}
        </p>
        <input
          type="text"
          value={title}
          onChange={(e) => {
            onTourDataChange("title", e.target.value);
            if (e.target.value.length > 0) {
              setCheckInput((cur) => cur & ~(1 << 1));
            } else {
              setCheckInput((cur) => cur | (1 << 1));
            }
          }}
        />
      </div>
      <div className={styles.Date}>
        <p
          className={styles.title}
          style={{ color: `${checkInput & (1 << 2) ? "lightcoral" : ""}` }}
        >
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
        <p style={{ color: `${checkInput & (1 << 3) ? "lightcoral" : ""}` }}>
          {user && user.isKorean ? "최대 인원" : "Max Participants"}
        </p>
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
              if (maxParticipants < 20) {
                onTourDataChange("maxParticipants", maxParticipants + 1);
              }
            }}
          />
        </div>
      </div>
      <div className={styles.Cost}>
        <p style={{ color: `${checkInput & (1 << 4) ? "lightcoral" : ""}` }}>
          {user && user.isKorean ? "예상 비용(₩)" : "Estimated Cost"}
        </p>
        <input
          type="number"
          value={price.toString()}
          onChange={(e) => {
            const newValue = e.target.value;
            if (
              !isNaN(newValue) &&
              Number(newValue) >= 0 &&
              newValue.length <= 1000000
            ) {
              onTourDataChange("price", Number(newValue));
            }
          }}
        />
        <div style={{ marginLeft: "26px", color: "#595959", fontSize: "15px" }}>
          {user?.isKorean
            ? "최대 1,000,000₩ 까지 설정 가능합니다"
            : "You can set up to a maximum of 1,000,000 KRW"}
        </div>
      </div>
      <div
        className={styles.locationSection}
        style={{ color: `${checkInput & (1 << 5) ? "lightcoral" : ""}` }}
      >
        <p>
          {user?.isKorean ? "장소" : "Location"}
          <span
            style={{ color: "#595959", fontSize: "14px", marginLeft: "10px" }}
          >
            {location.length}/{100}
          </span>
        </p>
        <input
          type="text"
          onChange={(e) => {
            if (e.target.value.length === 0) {
              onTourDataChange("location", e.target.value);
              setCheckInput((cur) => cur | (1 << 5));
            } else if (e.target.value.length <= 100) {
              onTourDataChange("location", e.target.value);
              setCheckInput((cur) => cur & ~(1 << 5));
            }
          }}
        />
      </div>
      <div className={styles.Theme}>
        <p style={{ color: `${checkInput & (1 << 6) ? "lightcoral" : ""}` }}>
          {user && user.isKorean ? "테마" : "Theme"}
          <span>
            {" "}
            {categoryIds.length} / {4}
          </span>
        </p>
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
                      if (categoryIds.length === 0) {
                        setCheckInput((cur) => cur | (1 << 6));
                      } else if (categoryIds.length === 5) {
                        setCheckInput((cur) => cur & ~(1 << 6));
                      }
                      onTourDataChange(
                        "categoryIds",
                        categoryIds.filter((id) => id !== item.idx)
                      );
                    } else {
                      if (categoryIds.length <= 3) {
                        setCheckInput((cur) => cur & ~(1 << 6));
                      } else {
                        setCheckInput((cur) => cur | (1 << 6));
                      }
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
            let res = checkData({
              thumbnailImage,
              title,
              startDate,
              endDate,
              maxParticipants,
              price,
              location,
              categoryIds,
            });
            if (res === 0) {
              goAfterPage();
            } else {
              alert(
                `${
                  user?.isKorean
                    ? "항목들을 확인해주세요"
                    : "Please check field(s)"
                }`
              );
            }
            setCheckInput(res);
          }}
        >
          {user && user.isKorean ? "다음" : "Next"}
        </button>
      </div>
    </section>
  );
};

export default TourEditor1;
