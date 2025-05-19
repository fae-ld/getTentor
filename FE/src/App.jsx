import './App.css';
import Header from './header.jsx'; 
import SearchBar from './SearchBar.jsx';
import TutorCard from './TutorCard.jsx';
import TutorList from './TutorList.jsx';

function App() {
  return (
    <div>
      <Header />
      <SearchBar />
      <TutorList />
    </div>
      
  );
}

export default App;