import { useState } from "react";
import styles from "./Create1.module.css";
const Create1 = () => {
  const [thumbnailImg, setthumbnailImg] = useState("");
  const onUpdateThumbnail = (e) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      setthumbnailImg((current) => e.target.result);
    };
    reader.readAsDataURL(e.target.files[0]);
  };
  return (
    <div className={styles.Create1}>
      <div className={styles.thumbnailPart}>
        <p>썸네일 이미지</p>
        <div className={styles.thumbnail}>
          <input
            type="file"
            accept="image/*"
            /* style={{ display: "none" }} */
            name="tourThumbnailImageUpload"
            id="tourThumbnailImageUpload"
            onChange={onUpdateThumbnail}
            style={{
              display: thumbnailImg === "" ? "block" : "none",
            }}
          />
          {/* <label htmlFor="tourThumbnailImageUpload">이미지 추가</label> */}
          {thumbnailImg === "" ? (
            <div></div>
          ) : (
            <img
              src={thumbnailImg}
              alt=""
              style={{
                width: "15vw",
                height: "15vw",
              }}
            />
          )}
        </div>
      </div>
      <div className={styles.datePart}>
        <p>날짜</p>
        <div className={styles.dateSelect}>
          <input type="date" />
          <input type="date" />
        </div>
      </div>
      <div className={styles.themePart}>
        <p>테마</p>
      </div>
    </div>
  );
};

export default Create1;
