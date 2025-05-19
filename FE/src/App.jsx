import './App.css';
import Header from './header.jsx'; 
import SearchBar from './SearchBar.jsx';
import TutorCard from './TutorCard.jsx';
import TutorList from './TutorList.jsx';

function App() {
  return (
    <div className="w-full min-h-screen bg-gray-50">
      <Header />
      <SearchBar />
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <TutorList />
      </div>
    </div>
      
  );
}

export default App;