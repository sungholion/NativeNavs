import styles from "./PlanModal.module.css";
import { useSearchParams } from "react-router-dom";
import { APIProvider, Map } from "@vis.gl/react-google-maps";
import { GoogleMap, LoadScript, Marker } from "@react-google-maps/api";
import { useEffect, useState } from "react";
import Button from "../Button/Button";
import { getImageUrl } from "@/utils/get-image-url";
import { getStaticImage } from "@/utils/get-static-image";
import { createPortal } from "react-dom";
import MapModal from "@/components/MapModal/MapModal";

const MAX_PLAN_DESCRIPTION_LENGTH = 200;

const center = {
  lat: 37.5642,
  lng: 127.00169,
};

const PlanModal = ({ onClose, onSubmit, initData }) => {
  // 유저정보 가져오기
  const [user, setUser] = useState(null);
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  // 계획 등록 데이터
  const [planData, setPlanData] = useState({
    field: "",
    description: "",
    latitude: 0,
    longitude: 0,
    addressFull: "",
    image: "",
  });

  // 이미지 미리보기 전용
  const [planImg, setPlanImg] = useState("");
  useEffect(() => {
    if (initData) {
      setPlanData({
        ...initData,
      });

      getImageUrl(initData.image, setPlanImg);
    }
  }, [initData]);

  // 지도 검색 결과 넣는 곳
  const inputMapData = (data) => {
    setPlanData({
      ...planData,
      latitude: data.lat,
      longitude: data.lng,
      addressFull: data.description,
    });
  };

  const [isOpenMapModal, setIsOpenMapModal] = useState(false); // 맵 지도 검색 모달 띄울것인가?

  const onChangeEvent = (e) => {
    if (e.target.name === "image") {
      const { files } = e.target;
      if (files) {
        const uploadFile = files[0];
        if (uploadFile.size > 1024 * 1024 * 10) {
          alert("10MB 이하의 이미지만 업로드 가능합니다.");
          return;
        }
        setPlanData({ ...planData, [e.target.name]: uploadFile });
        getImageUrl(uploadFile, setPlanImg);
      } else {
        setPlanData({ ...planData, [e.target.name]: "" });
        getImageUrl("", setPlanImg);
      }
    } else {
      setPlanData({ ...planData, [e.target.name]: e.target.value });
    }
  };

  return (
    <div
      className={styles.ModalBackground}
      onClick={(e) => {
        onClose();
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
                <img src={planImg} alt="+" />
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
            <h3>{user?.isKorean ? "제목" : "Title"}</h3>
            <input
              type="text"
              placeholder={
                user?.isKorean
                  ? "일정 제목 입력해 주세요"
                  : "Enter the title of the plan"
              }
              maxLength="20"
              name="field"
              value={planData.field}
              onChange={onChangeEvent}
            />
          </div>
        </section>
        <div className={styles.ModalHeaderComment}>
          {user?.isKorean
            ? "10MB 이하 이미지만 업로드 가능합니다"
            : "Image must be 10MB or less"}
        </div>
        <section className={styles.ModalMap}>
          <div className={styles.ModalMapSearch}>
            <h3>{user?.isKorean ? "위치 검색" : "Location Search"}</h3>
            <img
              src={getStaticImage("search")}
              alt=""
              style={{ width: "20px", height: "20px" }}
              onClick={() => {
                setIsOpenMapModal(true);
              }}
            />
          </div>
          {isOpenMapModal &&
            createPortal(
              <MapModal
                onClose={() => {
                  setIsOpenMapModal(false);
                }}
                onSubmit={inputMapData}
              />,
              document.body
            )}
          <div>
            {planData.addressFull !== "" ? (
              <div style={{ width: "70vw" }}>
                <div>{planData.addressFull}</div>
              </div>
            ) : (
              <div>
                {user?.isKorean ? (
                  <div>위치를 검색해주세요</div>
                ) : (
                  <div>Search for a location</div>
                )}
              </div>
            )}
          </div>
        </section>
        <section className={styles.ModalContent}>
          <div className={styles.ModalContentHeader}>
            <h3>{user?.isKorean ? "계획 내용" : "Plan Description"}</h3>
            <p style={{ padding: "0px" }}>
              {planData.description.length} / {MAX_PLAN_DESCRIPTION_LENGTH}
            </p>
          </div>
          <textarea
            maxLength={200}
            value={planData.description}
            onChange={(e) => {
              if (e.target.value.length < MAX_PLAN_DESCRIPTION_LENGTH) {
                setPlanData({ ...planData, description: e.target.value });
              }
            }}
            name="description"
          />
        </section>
        <section className={styles.ButtonSection}>
          <button className={styles.leftButton} onClick={onClose}>
            {user?.isKorean ? "뒤로" : "Back"}
          </button>
          <button
            className={styles.rightButton}
            disabled={
              planData.field === "" ||
              planData.image === "" ||
              planData.description === "" ||
              planData.addressFull === ""
            }
            onClick={() => {
              onSubmit({ ...planData });
              onClose();
            }}
          >
            {user?.isKorean ? "반영" : "Reflect"}
          </button>
        </section>
      </div>
    </div>
  );
};

export default PlanModal;
