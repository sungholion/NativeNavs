import React, { useState } from "react";
import "./MapModal.css";
import { getStaticImage } from "@/utils/get-static-image";
import {
  APIProvider,
  Map,
  AdvancedMarker,
  Pin,
} from "@vis.gl/react-google-maps";
import axios from "axios";

const defaultLoc = {
  lat: 37.5642,
  lng: 127.00169,
};

const MapModal = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [places, setPlaces] = useState([]);
  const handleSearch = async () => {
    if (!searchTerm) return;
    try {
      const response = await axios.get(
        "https://maps.googleapis.com/maps/api/place/textsearch/json",
        {
          params: {
            key: import.meta.env.VITE_GOOGLE_MAP_API,
            fields: "formatted_address%2Cname",
            input: "setSerachTerm",
            language: "ko",
            locationbias: `circle%3500%40${defaultLoc.lat}%2C${defaultLoc.lng}`,
            inputtype: "textquery",
          },
        }
      );
      setPlaces(response.data.results);
      console.log(response.data.results);
    } catch (error) {
      console.error("Error fetching places:", error);
    }
  };
  return (
    <div className="ModalBackground" onClick={(e) => e.preventDefault()}>
      <div className="mapModal">
        <section className="CloseModal">
          <img src={getStaticImage("close")} alt="" />
        </section>
        <section className="SearchLocation">
          <h4>검색</h4>
          <form action="">
            <input
              type="text"
              placeholder="장소를 검색하세요"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button onClick={handleSearch}>검색</button>
          </form>
        </section>
        <section className="MapSection">
          <APIProvider apiKey={import.meta.env.VITE_GOOGLE_MAP_API_KEY}>
            <Map
              style={{ width: "80vw", height: "20vh" }}
              defaultCenter={defaultLoc}
              defaultZoom={13}
              gestureHandling={"greedy"}
              disableDefaultUI={true}
              mapId={import.meta.env.VITE_MAP_ID}
            >
              <AdvancedMarker
                position={defaultLoc}
                title={"AdvancedMarker with customized pin."}
                onClick={() => {
                  console.log("abcd");
                }}
              ></AdvancedMarker>
            </Map>
          </APIProvider>
        </section>
        <section className="SearchResult"></section>
      </div>
    </div>
  );
};

export default MapModal;
