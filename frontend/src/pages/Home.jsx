import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import { normalizeHotel } from '../services/normalizers';

export default function Home() {
  const navigate = useNavigate();
  const [searchLocation, setSearchLocation] = useState('');
  const [featuredHotels, setFeaturedHotels] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api
      .get('/hotels')
      .then((res) => setFeaturedHotels(res.data.map(normalizeHotel).slice(0, 3)))
      .catch((err) => console.error('Failed to load hotels', err))
      .finally(() => setLoading(false));
  }, []);

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    const query = searchLocation.trim();
    navigate(query ? `/hotels?location=${encodeURIComponent(query)}` : '/hotels');
  };

  return (
    <div className="w-full pb-xl">
      <section className="relative h-[620px] flex items-center justify-center overflow-hidden">
        <img
          alt="Luxury hotel"
          className="absolute inset-0 h-full w-full object-cover"
          src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?auto=format&fit=crop&q=80&w=1600"
        />
        <div className="absolute inset-0 bg-slate-900/60" />
        <div className="relative z-10 mx-auto max-w-5xl px-lg text-center text-white">
          <h1 className="font-display-lg text-display-lg mb-md">Stay Somewhere Worth Remembering</h1>
          <p className="mx-auto mb-lg max-w-2xl text-body-lg text-white/90">
            Browse hotels, compare rooms, and book directly through the live backend.
          </p>
          <form
            onSubmit={handleSearchSubmit}
            className="mx-auto flex max-w-3xl flex-col gap-sm rounded-2xl bg-white p-sm shadow-2xl md:flex-row"
          >
            <input
              className="flex-1 rounded-xl border border-outline-variant/40 px-md py-md text-primary outline-none"
              placeholder="Search by city or hotel name"
              type="text"
              value={searchLocation}
              onChange={(e) => setSearchLocation(e.target.value)}
            />
            <button className="rounded-xl bg-[#FF7A00] px-lg py-md font-bold text-white" type="submit">
              Explore
            </button>
          </form>
        </div>
      </section>

      <section className="mx-auto max-w-container-max px-lg py-xl">
        <div className="mb-lg flex items-end justify-between gap-md">
          <div>
            <h2 className="font-headline-md text-headline-md text-primary">Featured Hotels</h2>
            <p className="text-secondary">Connected to your current backend inventory.</p>
          </div>
          <button className="font-bold text-primary" onClick={() => navigate('/hotels')}>
            View all hotels
          </button>
        </div>

        {loading ? (
          <div className="py-lg text-center text-secondary">Loading hotels...</div>
        ) : featuredHotels.length === 0 ? (
          <div className="rounded-2xl border border-dashed border-outline-variant/40 p-lg text-center text-secondary">
            No hotels are available yet.
          </div>
        ) : (
          <div className="grid grid-cols-1 gap-lg md:grid-cols-3">
            {featuredHotels.map((hotel) => (
              <article
                key={hotel.id}
                className="cursor-pointer overflow-hidden rounded-2xl bg-white shadow-[0px_8px_30px_rgba(15,23,42,0.08)]"
                onClick={() => navigate(`/hotels/${hotel.id}`)}
              >
                <img alt={hotel.hotelName} className="h-56 w-full object-cover" src={hotel.image} />
                <div className="p-md">
                  <p className="mb-xs text-sm font-bold uppercase tracking-wide text-[#FF7A00]">{hotel.location}</p>
                  <h3 className="mb-sm text-xl font-bold text-primary">{hotel.hotelName}</h3>
                  <p className="line-clamp-3 text-sm text-on-surface-variant">{hotel.description}</p>
                </div>
              </article>
            ))}
          </div>
        )}
      </section>
    </div>
  );
}
