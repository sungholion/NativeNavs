import React from "react";
import Tour_Review_Item from "./Tour_Review_Item";

const info = {
  user: {
    id: 1, //user_id
    image:
      "https://i.namu.wiki/i/u253q5zv58zJ1twYkeea-czVz8SQsvX-a1jVZ8oYsTVDH_TRC8-bpcVa4aKYQs5lI55B9srLYF9JJFUPbkI8MA.webp", // 유저 프로필 사진 (관강객)
    nickname: "오리고기",
    created_at: "2022-12-23",
  },
  tour_id: 12, //투어 ID
  score: 4.23, //리뷰점수
  description: "내용내용내용", //리뷰 내용
  created_at: "2024-12-23", // 리뷰 작성날짜
  image:
    "https://png.pngtree.com/thumb_back/fh260/background/20230322/pngtree-panorama-of-seoul-downtown-cityscape-illuminated-with-lights-and-namsan-seoul-photo-image_2092890.jpg",
};

const infoList = [{ ...info }, { ...info }, { ...info }];

const Tour_Review_List = () => {
  return (
    <div>
      <Tour_Review_Item {...info} />
    </div>
  );
};

export default Tour_Review_List;
