import "./App.css";
import Button from "./components/Button/Button";
import Heart from "./components/Heart/Heart";
import Image from "./components/Image/Image";
import Average from "./components/Star/Average";
import Rating from "./components/Star/Rating";
import CompletedTravelList from "./components/CompletedTourList/CompletedTravelList";

function App() {
  return (
    <>
      <h1 className="test">Hello REACT</h1>
      <div>dfafdf</div>
      <hr />

      <Button />
      <Heart />
      <Image />
      <Average />
      <Rating />
      <CompletedTravelList />
    </>
  );
}

export default App;
