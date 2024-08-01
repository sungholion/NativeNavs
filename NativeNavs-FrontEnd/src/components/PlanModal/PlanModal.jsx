import styles from "./PlanModal.module.css";
import { useSearchParams } from "react-router-dom";
import { APIProvider, Map } from "@vis.gl/react-google-maps";
import { GoogleMap, LoadScript, Marker } from "@react-google-maps/api";
import { useEffect, useState } from "react";
import Button from "../Button/Button";

const center = {
  lat: 37.5642,
  lng: 127.00169,
};

const PlanModal = ({ onClose, onSubmit, initData }) => {
  const [markers, setMarkers] = useState([]);

  const handleMapClick = (event) => {
    const newMarker = {
      lat: event.latLng.lat(),
      lng: event.latLng.lng(),
    };
    setMarkers([...markers, newMarker]);
  };
  const [planData, setPlanData] = useState({
    field: "",
    description: "",
    latitude: 0,
    longitude: 0,
    address_full: "",
    image: "",
  });
  const [searchQuery, setSearchQuery] = useState("");
  const [mapCenter, setMapCenter] = useState({ lat: 37.5642, lng: 127.00169 });

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

  const handleSearchChange = (e) => {};

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
              name="field"
              value={planData.field}
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
            <button>검색</button>

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
              planData.field === "" ||
              planData.image === "" ||
              planData.description === ""
            }
            onClick={() => {
              if (planData.field === "") {
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
