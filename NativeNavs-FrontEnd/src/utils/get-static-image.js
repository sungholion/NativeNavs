import add from "@/assets/add.png";
import close from "@/assets/close.png";
import heart_off from "@/assets/heart_off.png";
import heart_on from "@/assets/heart_on.png";
import language from "@/assets/language.png";
import minus from "@/assets/minus.png";
import NativeNavs from "@/assets/NativeNavs.png";
import RouterImg from "@/assets/router-img.svg";
import search from "@/assets/search.svg";

export function getStaticImage(type = "") {
  switch (type) {
    case "add":
      return add;
    case "minus":
      return minus;
    case "close":
      return close;
    case "heart_off":
      return heart_off;
    case "heart_on":
      return heart_on;
    case "language":
      return language;
    case "router-img":
      return RouterImg;
    case "search":
      return search;
    default:
      return type;
  }
}
