
import TutorCard from './TutorCard';

const tutors = [
  {
    name: 'Seixin',
    image: 'seixin.jpg',
    subjects: ['PBO', 'Jarkom', 'APPL', 'AI'],
  },
  {
    name: 'Saiki Kusuo',
    image: 'saiki.jpg',
    subjects: ['Alpro', 'BasDat'],
  },
  {
    name: 'Uesugi Fuutarou',
    image: 'uesugi.jpg',
    subjects: ['Kalkulus', 'Statistika', 'Bindo'],
  },
  {
    name: 'Yuiga Nariyuki',
    image: 'yuiga.jpg',
    subjects: ['Fisika', 'Bindo', 'Bing', 'Kalkulus', 'Statistika'],
  },
];

const TutorList = () => {
  return (
    <section className="mt-8 px-8">
      <h2 className="text-xl font-semibold mb-4 text-blue-900">Tentor Tersedia</h2>
      <div className="flex flex-wrap gap-6">
        {tutors.map((tutor, idx) => (
          <TutorCard key={idx} {...tutor} />
        ))}
      </div>
    </section>
  );
};

export default TutorList;
