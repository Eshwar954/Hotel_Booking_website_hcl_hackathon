import React, { useEffect, useMemo, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import api from '../services/api';
import { normalizeHotel } from '../services/normalizers';

export default function HotelList() {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();
  const initialLocation = searchParams.get('location') || '';

  const [hotels, setHotels] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchLoc, setSearchLoc] = useState(initialLocation);

  useEffect(() => {
    setSearchLoc(initialLocation);
    setLoading(true);
    api
      .get('/hotels')
      .then((res) => setHotels(res.data.map(normalizeHotel)))
      .catch((err) => console.error('Failed to load hotels', err))
      .finally(() => setLoading(false));
  }, [initialLocation]);

  const filteredHotels = useMemo(() => {
    const query = initialLocation.trim().toLowerCase();
    if (!query) {
      return hotels;
    }

    return hotels.filter((hotel) => {
      const name = hotel.hotelName?.toLowerCase() || '';
      const location = hotel.location?.toLowerCase() || '';
      return name.includes(query) || location.includes(query);
    });
  }, [hotels, initialLocation]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const query = searchLoc.trim();
    setSearchParams(query ? { location: query } : {});
  };

  return (
    <div className="mx-auto max-w-container-max px-lg py-xl">
      <div className="mb-lg">
        <h2 className="text-3xl font-bold text-primary">Browse Hotels</h2>
        <p className="text-secondary">Search the hotels currently available from the backend API.</p>
      </div>

      <form className="mb-lg flex flex-col gap-sm rounded-2xl bg-white p-md shadow-sm md:flex-row" onSubmit={handleSubmit}>
        <input
          className="flex-1 rounded-xl border border-outline-variant/40 px-md py-sm text-primary outline-none"
          placeholder="Search by city or hotel name"
          type="text"
          value={searchLoc}
          onChange={(e) => setSearchLoc(e.target.value)}
        />
        <button className="rounded-xl bg-primary px-lg py-sm font-bold text-white" type="submit">
          Search
        </button>
      </form>

      {loading ? (
        <div className="py-lg text-center text-secondary">Loading hotels...</div>
      ) : filteredHotels.length === 0 ? (
        <div className="rounded-2xl border border-dashed border-outline-variant/40 p-lg text-center text-secondary">
          No hotels match this search.
        </div>
      ) : (
        <div className="grid grid-cols-1 gap-lg">
          {filteredHotels.map((hotel) => (
            <article
              key={hotel.id}
              className="flex cursor-pointer flex-col overflow-hidden rounded-2xl bg-white shadow-[0px_8px_30px_rgba(15,23,42,0.08)] md:flex-row"
              onClick={() => navigate(`/hotels/${hotel.id}`)}
            >
              <img alt={hotel.hotelName} className="h-64 w-full object-cover md:w-80" src={hotel.image} />
              <div className="flex flex-1 flex-col justify-between p-lg">
                <div>
                  <p className="mb-xs text-sm font-bold uppercase tracking-wide text-[#FF7A00]">{hotel.location}</p>
                  <h3 className="mb-sm text-2xl font-bold text-primary">{hotel.hotelName}</h3>
                  <p className="text-on-surface-variant">{hotel.description}</p>
                </div>
                <div className="mt-md font-bold text-primary">View rooms</div>
              </div>
            </article>
          ))}
        </div>
      )}
    </div>
  );
}
