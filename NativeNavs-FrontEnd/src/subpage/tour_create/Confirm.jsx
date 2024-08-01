import Button from "@/components/Button/Button";
import "./Confirm.css";
import Plan_Item from "@/components/Plan_Item/Plan_Item";
import { getStringedDate } from "@utils/get-stringed-date";
import { TourDataContext } from "./Tour_Create";
import { useContext } from "react";
import axios from "axios";
import { API_Tour_Create } from "@/utils/apis";
const NO_NEED_IMAGE = true;

const testJSON = {
  userId: 10,
  title: "Summer Vacation",
  thumbnailImage: "http://example.com/image.jpg",
  description: "A relaxing summer vacation tour",
  location: "서울특별시 종로구",
  price: 500000,
  startDate: "2024-08-01",
  endDate: "2024-08-15",
  reviewAverage: 0,
  reviewCount: 0,
  maxParticipants: 1,
  removed: false,
  categoryIds: [1, 2],
  plans: [
    {
      id: 1,
      field: "Field 1",
      description: "Description of plan 1",
      image: "http://example.com/plan1.jpg",
      latitude: 37.5665,
      longitude: 126.978,
      addressFull: "123 Example Street",
    },
    {
      id: 2,
      field: "Field 2",
      description: "Description of plan 2",
      image: "http://example.com/plan2.jpg",
      latitude: 37.567,
      longitude: 126.979,
      addressFull: "456 Example Avenue",
    },
  ],
};

const Tour_Create_Request = async (data) => {
  try {
    const response = await axios.post(
      "http://i11d110.p.ssafy.io:8081/api/tours",
      data,
      {
        headers: {
          AccessToken: "strx ucbb pelf hynv",
        },
      }
    );
    console.log(response);
    window.alert("성공했어요!");
    return response;
  } catch (error) {
    console.error(error);
    window.alert("실패했어요 ㅠㅠ");
    return error;
  }
};

const Confirm = ({ goBeforePage }) => {
  const {
    title,
    thumbnailImage,
    description,
    location,
    price,
    startDate,
    endDate,
    maxParticipants,
    plans,
    categoryIds,
  } = useContext(TourDataContext);
  return (
    <div className="TourConfirm">
      <section className="TourThumbnailCheck">
        <p>썸네일 사진</p>
        {thumbnailImage.length > 0 ? (
          <img src={thumbnailImage} alt="썸네일이미지" />
        ) : (
          <div style={{ color: "red", fontSize: "20px" }}>
            이미지 업로드 해주세요
          </div>
        )}
      </section>
      <hr />
      <section className="TourTitle">
        <p>제목</p>
        {title.length > 0 ? (
          <div>{title}</div>
        ) : (
          <div style={{ color: "red" }}>제목 입력해 주세요</div>
        )}
      </section>
      <section className="TourDuration">
        <p>기간</p>
        <div className="TourDateShow">
          <span className="DateBox">
            {getStringedDate(new Date(startDate))}
          </span>
          <span>~</span>
          <span className="DateBox">{getStringedDate(new Date(endDate))}</span>
        </div>
      </section>
      <hr />
      <section className="TourMaxPeople">
        <p>최대 허용 인원</p>
        <div>{maxParticipants} 명</div>
      </section>
      <section className="TourExpectPrice">
        <p>예상 비용</p>
        <div>{price} 원</div>
      </section>
      <section className="TourTheme">
        <p>테마</p>
        <div className="TourThemeList">
          {categoryIds
            .filter((theme) => theme.state)
            .map((theme) => {
              return (
                <div key={theme.idx} className="TourThemeItem">
                  {theme.name}
                </div>
              );
            })}
        </div>
      </section>
      <section className="TourPlanList">
        <p>Plans</p>
        <div className="TourPlanItem">
          {plans.map((item) => {
            return (
              <Plan_Item key={item.id} {...item} enableDeleteOption={false} />
            );
          })}
        </div>
      </section>
      <hr />
      <section className="TourMapList"></section>
      <section className="Tourdescription">
        <p>당부사항</p>
        <div>{description}</div>
      </section>
      <section className="ButtonSection">
        <Button text={"이전"} size={0} onClickEvent={goBeforePage} />
        <Button
          text={"추가"}
          size={0}
          onClickEvent={() => {
            const data = {
              title,
              thumbnailImage,
              description,
              location: "",
              price,
              startDate,
              endDate,
              maxParticipants,
              categoryIds: categoryIds
                .filter((theme) => theme.state)
                .map((theme) => theme.idx),
              plans,
            };
            if (NO_NEED_IMAGE) {
              data.thumbnailImage =
                "https://previews.123rf.com/images/skynetphoto/skynetphoto1310/skynetphoto131000007/22778130-%EC%8B%9C%EA%B3%A8%EA%B3%BC-%EC%82%B0-%EB%B0%B0%EA%B2%BD%EC%9D%98-%ED%92%8D%EA%B2%BD.jpg";
              data.plans = data.plans.map((plan) => {
                return {
                  ...plan,
                  image:
                    "https://previews.123rf.com/images/skynetphoto/skynetphoto1310/skynetphoto131000007/22778130-%EC%8B%9C%EA%B3%A8%EA%B3%BC-%EC%82%B0-%EB%B0%B0%EA%B2%BD%EC%9D%98-%ED%92%8D%EA%B2%BD.jpg",
                };
              });
            }
            console.log(data);
            Tour_Create_Request(data);
          }}
        />
      </section>
    </div>
  );
};

export default Confirm;
