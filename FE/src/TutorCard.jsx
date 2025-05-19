// src/components/TutorCard.jsx
const TutorCard = ({ image, name, subjects }) => {
  return (
    <div className="bg-white rounded-lg shadow-md p-4 w-60">
      <img src={image} alt={name} className="rounded-lg w-full h-40 object-cover" />
      <h2 className="text-lg font-semibold mt-2">{name}</h2>
      <div className="flex flex-wrap gap-1 mt-2">
        {subjects.map((subject, idx) => (
          <span key={idx} className="text-xs bg-blue-100 text-blue-800 px-2 py-1 rounded">
            {subject}
          </span>
        ))}
      </div>
      <div className="mt-2 text-sm">⭐ 5.0/5.0</div>
      <button className="mt-3 w-full bg-blue-500 text-white py-1 rounded hover:bg-blue-600">
        Lihat Tentor
      </button>
    </div>
  );
};

export default TutorCard;