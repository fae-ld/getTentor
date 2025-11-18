// src/components/TutorCard.jsx
const TutorCard = ({ image, name, subjects }) => {
  return (
    <div className="bg-white rounded-lg shadow-md p-4 w-60 flex flex-col h-full">
      <img
        src={image}
        alt={name}
        className="rounded-lg w-full h-40 object-cover"
      />

      <h2 className="text-lg font-semibold mt-2">{name}</h2>

      {/* Subject Tags */}
      <div className="flex flex-wrap gap-1 mt-2 min-h-[48px]">
        {subjects.slice(0, 3).map((subject, idx) => (
          <span
            key={idx}
            className="text-xs bg-blue-100 text-blue-800 px-2 py-1 rounded max-w-[80px] truncate"
          >
            {subject}
          </span>
        ))}

        {subjects.length > 3 && (
          <span className="text-xs bg-blue-100 text-blue-800 px-2 py-1 rounded">
            +{subjects.length - 3} lainnya
          </span>
        )}
      </div>

      {/* Rating */}
      <div className="mt-2 text-sm">⭐ 5.0/5.0</div>

      {/* Action Button */}
      <button className="mt-auto w-full bg-blue-500 text-white py-1 rounded hover:bg-blue-600">
        Lihat Tentor
      </button>
    </div>
  );
};

export default TutorCard;
