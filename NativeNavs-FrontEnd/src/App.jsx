import "./App.css";
import StarScore from "./components/Star/StarScore";

function App() {
  return (
    <>
      <h1 className="test">Hello REACT</h1>
      <hr />
      <StarScore score={80}/>
    </>
  );
}

export default App;
