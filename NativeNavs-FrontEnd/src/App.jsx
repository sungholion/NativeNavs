import "./App.css";
import ScheduledTour from "./components/ScheduledTour/ScheduledTour.jsx";
import Profile from "./components/Profile/Profile.jsx";
import { tours } from "./dummy.jsx";

function App() {
  return (
    <>
      <h1 className="test">Hello REACT</h1>
      <hr />
      <ScheduledTour />
      {tours.map((tour) => (
        <Profile
          key={tour.id} // key prop 추가
          image={tour.image}
          nickname={tour.nickname}
          language={tour.language}
        />
      ))}
    </>
  );
}

export default App;
