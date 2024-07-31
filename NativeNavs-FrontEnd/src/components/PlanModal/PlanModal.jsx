import styles from "./PlanModal.module.css";
import { useSearchParams } from "react-router-dom";
import { APIProvider, Map } from "@vis.gl/react-google-maps";
import { useEffect, useState } from "react";
import Button from "../Button/Button";

const PlanData = {
  image:
    "https://i.namu.wiki/i/u253q5zv58zJ1twYkeea-czVz8SQsvX-a1jVZ8oYsTVDH_TRC8-bpcVa4aKYQs5lI55B9srLYF9JJFUPbkI8MA.webp",
};
const PlanModal = ({ onClose, onSubmit, initData }) => {
  const [planData, setPlanData] = useState({
    title: "",
    description: "",
    latitude: 0,
    longitude: 0,
    address_full: "",
    image: "",
  });

  useEffect(() => {
    if (initData) {
      setPlanData({
        ...initData,
      });
    }
  }, [initData]);

  // const onImgChange = (e) => {
  //   const { files } = e.target;
  //   const uploadFile = files[0];
  //   const reader = new FileReader();
  //   reader.readAsDataURL(uploadFile);
  //   reader.onloadend = () => {
  //     setUploadImgUrl(reader.result);
  //   };
  // };
  const onChangeEvent = (e) => {
    if (e.target.name === "image") {
      const { files } = e.target;
      const uploadFile = files[0];
      const reader = new FileReader();
      reader.readAsDataURL(uploadFile);
      reader.onloadend = () => {
        setPlanData({ ...planData, [e.target.name]: reader.result });
      };
    } else {
      setPlanData({ ...planData, [e.target.name]: e.target.value });
    }
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
            <label htmlFor="image">
              {planData.image !== "" ? (
                <img src={planData.image} alt="+" name="image" id="image" />
              ) : (
                <div className={styles.emptyModalImg}>+</div>
              )}
            </label>
            <input
              type="file"
              accept="image/*"
              name="image"
              id="image"
              onChange={onChangeEvent}
            />
          </div>
          <div className={styles.ModalHeaderTitle}>
            <h3>제목</h3>
            <input
              type="text"
              placeholder="일정 제목 입력해 주세요"
              maxLength="20"
              name="title"
              value={planData.title}
              onChange={onChangeEvent}
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
          <textarea
            maxLength={200}
            value={planData.description}
            onChange={onChangeEvent}
            name="description"
          />
        </section>
        <section className={styles.ButtonSection}>
          <Button text={"뒤로"} size={5} onClickEvent={onClose} />
          <Button
            text={"등록"}
            size={5}
            onClickEvent={() => {
              onSubmit({ ...planData });
              onClose();
            }}
          />
        </section>
      </div>
    </div>
  );
};

export default PlanModal;
