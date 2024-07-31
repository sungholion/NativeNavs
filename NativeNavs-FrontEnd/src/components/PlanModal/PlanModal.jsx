import styles from "./PlanModal.module.css";
import { useSearchParams } from "react-router-dom";
import { APIProvider, Map } from "@vis.gl/react-google-maps";
import { useEffect, useState } from "react";
import Button from "../Button/Button";

const PlanModal = ({ onClose, onSubmit, initData }) => {
  const [planData, setPlanData] = useState({
    title: "",
    description: "",
    latitude: 0,
    longitude: 0,
    address_full: "",
    image: "",
  });
  const [searchQuery, setSearchQuery] = useState("");
  const [mapCenter, setMapCenter] = useState({ lat: 37.5642, lng: 127.00169 });
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
    const handleSearchClick = () => {
      // 여기에 실제 위치 검색 로직을 추가하세요
      // 예를 들어, geocoding API를 사용하여 검색어를 좌표로 변환합니다.
      // 검색 결과를 기반으로 mapCenter를 업데이트합니다.
      const newCenter = { lat: 37.5665, lng: 126.978 }; // 예시 좌표
      setMapCenter(newCenter);
    };
  };

  useEffect(() => {
    if (initData) {
      setPlanData({
        ...initData,
      });
    }
  }, [initData]);

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
                <img src={planData.image} alt="+" />
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
            <input
              type="text"
              placeholder="위치를 입력해 주세요"
              value={searchQuery}
              onChange={handleSearchChange}
            />
            <button onClick={handleSearchClick}>검색</button>

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
          <button onClick={onClose}>뒤로</button>
          <button
            disabled={
              planData.title === "" ||
              planData.image === "" ||
              planData.description === ""
            }
            onClick={() => {
              if (planData.title === "") {
                window.alert("제목을 입력하세요");
                return;
              }
              if (planData.image === "") {
                window.alert("해당 일정에 대한 이미지를 업로드 하세요");
                return;
              }
              if (planData.description === "") {
                window.alert("해당 일정에 대한 글을 써주세요");
                return;
              }
              onSubmit({ ...planData });
              onClose();
            }}
          >
            계획 등록
          </button>
        </section>
      </div>
    </div>
  );
};

export default PlanModal;
