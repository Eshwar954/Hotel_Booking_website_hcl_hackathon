import React, { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import api from '../services/api';
import { normalizeHotel, normalizeRoom } from '../services/normalizers';

export default function HotelDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);

  const [hotel, setHotel] = useState(null);
  const [rooms, setRooms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    setLoading(true);
    setError('');

    Promise.all([api.get(`/hotels/${id}`), api.get(`/rooms/hotel/${id}`)])
      .then(([hotelRes, roomsRes]) => {
        setHotel(normalizeHotel(hotelRes.data));
        setRooms(roomsRes.data.map(normalizeRoom));
      })
      .catch((err) => {
        console.error('Failed to load hotel detail', err);
        setError('Failed to load hotel details.');
      })
      .finally(() => setLoading(false));
  }, [id]);

  const handleBook = (roomId) => {
    if (!user) {
      navigate(`/login?redirect=${encodeURIComponent(`/checkout?roomId=${roomId}`)}`);
      return;
    }

    navigate(`/checkout?roomId=${roomId}`);
  };

  if (loading) {
    return <div className="py-xl text-center text-secondary">Loading hotel details...</div>;
  }

  if (error || !hotel) {
    return (
      <div className="mx-auto max-w-container-max px-lg py-xl text-center">
        <h2 className="mb-sm text-2xl font-bold text-primary">Hotel unavailable</h2>
        <p className="mb-md text-secondary">{error}</p>
        <button className="rounded-xl bg-primary px-lg py-sm font-bold text-white" onClick={() => navigate('/hotels')}>
          Back to hotels
        </button>
      </div>
    );
  }

  return (
    <div className="mx-auto max-w-container-max px-lg py-xl">
      <div className="grid grid-cols-1 gap-lg lg:grid-cols-[1.4fr_1fr]">
        <div>
          <img alt={hotel.hotelName} className="mb-lg h-[360px] w-full rounded-3xl object-cover" src={hotel.image} />
          <p className="mb-xs text-sm font-bold uppercase tracking-wide text-[#FF7A00]">{hotel.location}</p>
          <h1 className="mb-md text-4xl font-bold text-primary">{hotel.hotelName}</h1>
          <p className="rounded-2xl bg-white p-lg text-on-surface-variant shadow-sm">{hotel.description}</p>
        </div>

        <aside className="rounded-3xl bg-white p-lg shadow-[0px_8px_30px_rgba(15,23,42,0.08)]">
          <h2 className="mb-md text-2xl font-bold text-primary">Available rooms</h2>
          <div className="space-y-md">
            {rooms.length === 0 ? (
              <p className="text-secondary">No rooms are available for this hotel yet.</p>
            ) : (
              rooms.map((room) => (
                <div className="rounded-2xl border border-outline-variant/30 p-md" key={room.id}>
                  <div className="mb-xs flex items-center justify-between gap-sm">
                    <h3 className="text-lg font-bold text-primary">{room.roomType}</h3>
                    <span className="text-lg font-bold text-[#FF7A00]">${room.price}</span>
                  </div>
                  <p className="mb-sm text-sm text-on-surface-variant">{room.description || 'Comfortable stay with standard amenities.'}</p>
                  <p className="mb-sm text-sm text-secondary">Rooms left online: {room.onlineAvailableRooms}</p>
                  <button
                    className="w-full rounded-xl bg-primary px-md py-sm font-bold text-white disabled:cursor-not-allowed disabled:bg-outline-variant"
                    disabled={!room.availability}
                    onClick={() => handleBook(room.id)}
                  >
                    {room.availability ? 'Book this room' : 'Sold out'}
                  </button>
                </div>
              ))
            )}
          </div>
        </aside>
      </div>
    </div>
  );
}
