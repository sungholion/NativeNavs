import React, { useState, useEffect, useRef } from "react";
import "./MapModal.css";
import { createRoot } from "react-dom/client";
import {
  APIProvider,
  ControlPosition,
  MapControl,
  AdvancedMarker,
  Map,
  Pin,
  useMapsLibrary,
  useAdvancedMarkerRef,
} from "@vis.gl/react-google-maps";

import {
  PlaceReviews,
  PlaceDataProvider,
  PlaceDirectionsButton,
  IconButton,
  PlaceOverview,
  SplitLayout,
  OverlayLayout,
  PlacePicker,
} from "@googlemaps/extended-component-library/react";

import { getStaticImage } from "@/utils/get-static-image";
const DEFAULT_CENTER = {
  // 서울역 좌표
  lat: 37.555167,
  lng: 126.970833,
};
const DEFAULT_ZOOM = 12; // 검색 전 지도 확대 수준
const DEFAULT_ZOOM_WITH_LOCATION = 16; // 검색 이루어 질 때 지도 확대 수준

const MapModal = () => {
  const overlayLayoutRef = useRef(null);
  const pickerRef = useRef(null); // 자동 검색 결과에 대한 저장소
  const [searchLocation, setSearchLocation] = useState(undefined); // 위치 검색을 저장하는 곳
  return (
    <div className="ModalBackground" onClick={(e) => e.preventDefault()}>
      <div className="mapModal">
        <section className="CloseModal">
          <img src={getStaticImage("close")} alt="" />
        </section>
        <section className="MapSection">
          <APIProvider
            apiKey={import.meta.env.VITE_GOOGLE_MAP_API_KEY}
            solutionChannel="GMP_devsite_samples_v3_rgmautocomplete"
            version="beta"
          >
            {/* 실제 지도 결과를 나타내줌 */}
            <Map
              id="gmap"
              mapId={import.meta.env.VITE_MAP_ID}
              center={searchLocation?.location ?? DEFAULT_CENTER}
              zoom={searchLocation ? DEFAULT_ZOOM_WITH_LOCATION : DEFAULT_ZOOM}
              zoomControl={false}
              gestureHandling="none"
              fullscreenControl={true}
              style={{ width: "80vw", height: "30vh", borderRadius: "10px" }}
            >
              {/* 검색 결과가 있을 경우 지도에 Marker표시 */}
              {searchLocation?.location && (
                <AdvancedMarker position={searchLocation?.location}>
                  <Pin
                    background={"#FBBC04"}
                    glyphColor={"#000"}
                    borderColor={"#000"}
                  />
                </AdvancedMarker>
              )}
            </Map>

            <PlacePicker
              ref={pickerRef}
              forMap="gmap"
              country={["kr"]}
              placeholder="장소를 입력해 주세요"
              style={{
                width: "80vw",
                height: "5vh",
                borderRadius: "10px",
                marginTop: "10px",
              }}
              onPlaceChange={(e) => {
                console.log(e.target.value);
                if (!pickerRef.current?.value) {
                  console.log("No place selected");
                  setSearchLocation(undefined);
                } else {
                  console.log(pickerRef.current?.value.location);
                  console.log(pickerRef.current.value);
                  setSearchLocation(pickerRef.current?.value);
                }
              }}
            />
          </APIProvider>
        </section>
        <section className="SearchResult"></section>
      </div>
    </div>
  );
};

export default MapModal;
