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

import { PlacePicker } from "@googlemaps/extended-component-library/react";

import { getStaticImage } from "@/utils/get-static-image";
import { allowScroll, preventScroll } from "@/utils/scroll-prvent";

const DEFAULT_CENTER = {
  lat: 37.555167,
  lng: 126.970833,
};
const DEFAULT_ZOOM = 12;
const DEFAULT_ZOOM_WITH_LOCATION = 16;

const MapModal = ({ onClose, onSubmit }) => {
  const [user, setUser] = useState(null);
  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  const pickerRef = useRef(null); 
  const [searchLocation, setSearchLocation] = useState(undefined); 
  const [selectedLocation, setSelectedLocation] = useState(undefined); 
  useEffect(() => {
    const prevScrollY = preventScroll();
    return () => {
      allowScroll(prevScrollY);
    };
  }, []);

  return (
    <div
      className="ModalBackground"
      onClick={(e) => {
        e.preventDefault();
        onClose();
      }}
    >
      <div className="mapModal" onClick={(e) => e.stopPropagation()}>
        <section className="CloseModal">
          <img src={getStaticImage("close")} alt="" onClick={onClose} />
        </section>
        <section className="MapSection">
          <APIProvider
            apiKey={import.meta.env.VITE_GOOGLE_MAP_API_KEY}
            solutionChannel="GMP_devsite_samples_v3_rgmautocomplete"
            version="beta"
          >
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
              language={"kr"}
              placeholder={user && user.isKorean ? "장소 검색" : "Search"}
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
                  setSearchLocation(pickerRef.current?.value);
                  setSelectedLocation({
                    lat: pickerRef.current?.value.location.lat(),
                    lng: pickerRef.current?.value.location.lng(),
                    address:
                      pickerRef.current?.value.displayName +
                      " : " +
                      pickerRef.current?.value.formattedAddress,
                  });
                }
              }}
            />
          </APIProvider>
        </section>
        <section className="SearchResult">
          {searchLocation ? (
            <div className="Searched">
              <h4>{selectedLocation.address}</h4>
              <p>{pickerRef.current?.value?.formattedAddress}</p>
            </div>
          ) : (
            <div className="notSearch">
              {user && user.isKorean ? "검색해 주세요" : "Please search"}
              <p>
                {user && user.isKorean
                  ? "장소 검색 결과가 여기에 뜹니다"
                  : "The search results for locations will appear here."}
              </p>
            </div>
          )}
        </section>
        <section className="mapsearchButton">
          <button className="left ">{user?.isKorean ? "닫기" : "Close"}</button>
          <button
            className={`right ${searchLocation ? "" : "disable"}`}
            onClick={() => {
              const data = {
                description: selectedLocation.address,
                lat: selectedLocation.lat,
                lng: selectedLocation.lng,
              };
              onSubmit(data);
              onClose();
            }}
          >
            {user?.isKorean ? "등록" : "Register"}
          </button>
        </section>
      </div>
    </div>
  );
};

export default MapModal;
