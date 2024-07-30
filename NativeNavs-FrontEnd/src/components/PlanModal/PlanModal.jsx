import styles from "./PlanModal.module.css";
import { useSearchParams } from "react-router-dom";
import { APIProvider, Map } from "@vis.gl/react-google-maps";
import { useState } from "react";

const PlanData = {
  image:
    "https://i.namu.wiki/i/u253q5zv58zJ1twYkeea-czVz8SQsvX-a1jVZ8oYsTVDH_TRC8-bpcVa4aKYQs5lI55B9srLYF9JJFUPbkI8MA.webp",
};
const PlanModal = ({ onClose }) => {
  const [uploadImgUrl, setUploadImgUrl] = useState("");
  const onImgChange = (e) => {
    const { files } = e.target;
    const uploadFile = files[0];
    const reader = new FileReader();
    reader.readAsDataURL(uploadFile);
    reader.onloadend = () => {
      setUploadImgUrl(reader.result);
    };
  };
  const apiKey = import.meta.env.VITE_GOOGLE_MAP_API_KEY;
  const onClickEvent = (e) => {
    e.stopPropagation;
    onClose();
  };
  return (
    <div
      className={styles.ModalBackground}
      onClick={(e) => {
        onClickEvent(e);
      }}
    >
      <div
        className={styles.Modal}
        onClick={(e) => {
          e.stopPropagation();
        }}
      >
        <section className={styles.ModalHeader}>
          <div className={styles.ModalImg}>
            <label htmlFor="PlanImg">
              {uploadImgUrl !== "" ? (
                <img src={uploadImgUrl} alt="+" />
              ) : (
                <div className={styles.emptyModalImg}>+</div>
              )}
            </label>
            <input
              type="file"
              accept="image/*"
              name="PlanImg"
              id="PlanImg"
              onChange={onImgChange}
            />
          </div>
          <div className={styles.ModalHeaderTitle}>
            <h3>제목</h3>
            <input
              type="text"
              placeholder="일정 제목 입력해 주세요"
              maxLength="20"
            />
          </div>
        </section>
        <section className={styles.ModalMap}>
          <div className={styles.ModalMapSearch}>
            <h3>위치 검색</h3>

            <APIProvider apiKey={apiKey}>
              <Map
                style={{ width: "80vw", height: "20vh" }}
                defaultCenter={{ lat: 37.5642, lng: 127.00169 }}
                defaultZoom={15}
                gestureHandling={"greedy"}
                disableDefaultUI={true}
              />
            </APIProvider>
          </div>
        </section>
        <section className={styles.ModalContent}>
          <h3>내용</h3>
          <textarea />
        </section>
      </div>
    </div>
  );
};

export default PlanModal;
