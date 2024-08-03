import React from "react";
import "./MapModal.css";
import { getStaticImage } from "@/utils/get-static-image";
import { APIProvider, Map } from "@vis.gl/react-google-maps";

const MapModal = () => {
  return (
    <div className="ModalBackground" onClick={(e) => e.preventDefault()}>
      <div className="mapModal">
        <section className="CloseModal">
          <img src={getStaticImage("close")} alt="" />
        </section>
        <section className="SearchLocation">
          <h4>검색</h4>
          <input type="text" />
        </section>
        <section className="MapSection">
          <APIProvider apiKey={import.meta.env.VITE_GOOGLE_MAP_API_KEY}>
            <Map
              style={{ width: "80vw", height: "20vh" }}
              defaultCenter={{ lat: 37.5642, lng: 127.00169 }}
              defaultZoom={15}
              gestureHandling={"greedy"}
              disableDefaultUI={true}
            />
          </APIProvider>
        </section>
        <section className="SearchResult"></section>
      </div>
    </div>
  );
};

export default MapModal;
