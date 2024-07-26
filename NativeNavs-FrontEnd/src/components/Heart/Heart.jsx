import { useState } from "react";
import Image from "../Image/Image";
import styles from "./Heart.module.css";
import heart_on from "../../assets/heart_on.png";
import heart_off from "../../assets/heart_off.png";

// isWishListed : 선택되었는가? : boolean
// setIsWishListed : 토글 버튼 이벤트 -> useState나 useReducer 등의 설정 함수
// onClickEvent : 클릭 이벤트 전달 -> 서버에전달용? 혹은 클라이언트에게 영향 주는 무언가?
const Heart = ({ isWishListed, setIsWishListed, onClickEvent }) => {
  return (
    <div
      onClick={() => {
        setIsWishListed((current) => !current);
      }}
      className={styles.heart_default}
    >
      <Image
        url={isWishListed ? heart_on : heart_off}
        size={0}
        onClickEvent={onClickEvent}
      />
    </div>
  );
};

export default Heart;
