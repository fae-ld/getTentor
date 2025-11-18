// src/components/SearchBar.jsx
const SearchBar = () => {
  return (
    <div className="flex justify-center mt-6">
      <input
        type="text"
        placeholder="Masukkan mata kuliah yang ingin dipelajari"
        className="w-[400px] px-4 py-2 border rounded-l-md shadow"
      />
      <button className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-r-md">
        Cari 🔍
      </button>
    </div>
  );
};

export default SearchBar;
