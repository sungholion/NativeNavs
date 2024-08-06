import React from "react";
import { APIProvider, Map, AdvancedMarker } from "@vis.gl/react-google-maps";
const defaultLoc = {
  lat: 37.5642,
  lng: 127.00169,
};

export const MapSection = () => {
  return (
    <div>
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
            clickable={true}
            onClick={() => {
              console.log("abcd");
            }}
          />
        </Map>
      </APIProvider>
    </div>
  );
};
