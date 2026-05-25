import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import api from '../services/api';
import { normalizeBooking } from '../services/normalizers';

export default function Profile() {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!user) {
      navigate('/login');
      return;
    }

    api
      .get('/bookings/my-bookings')
      .then((res) => setBookings(res.data.map(normalizeBooking)))
      .catch((err) => {
        console.error('Failed to load bookings', err);
        setError('Failed to load your bookings.');
      })
      .finally(() => setLoading(false));
  }, [navigate, user]);

  const handleCancel = async (bookingId) => {
    try {
      await api.post(`/bookings/cancel/${bookingId}`);
      setBookings((current) =>
        current.map((booking) =>
          booking.id === bookingId ? { ...booking, bookingStatus: 'CANCELLED' } : booking
        )
      );
    } catch (err) {
      console.error('Failed to cancel booking', err);
      setError(err.response?.data?.message || 'Failed to cancel booking.');
    }
  };

  if (loading) {
    return <div className="py-xl text-center text-secondary">Loading your bookings...</div>;
  }

  return (
    <div className="mx-auto max-w-container-max px-lg py-xl">
      <div className="mb-lg rounded-3xl bg-white p-lg shadow-sm">
        <h1 className="text-3xl font-bold text-primary">{user?.name}</h1>
        <p className="text-secondary">{user?.email}</p>
      </div>

      {error && <div className="mb-md rounded-xl bg-error-container/30 px-md py-sm text-on-error-container">{error}</div>}

      {bookings.length === 0 ? (
        <div className="rounded-3xl border border-dashed border-outline-variant/40 p-lg text-center text-secondary">
          No bookings found yet.
        </div>
      ) : (
        <div className="space-y-md">
          {bookings.map((booking) => (
            <article className="rounded-3xl bg-white p-lg shadow-[0px_8px_30px_rgba(15,23,42,0.08)]" key={booking.id}>
              <div className="mb-sm flex flex-col gap-sm sm:flex-row sm:items-start sm:justify-between">
                <div>
                  <h2 className="text-2xl font-bold text-primary">{booking.room?.hotel?.hotelName || booking.hotelName}</h2>
                  <p className="text-secondary">{booking.room?.roomType || booking.roomType}</p>
                </div>
                <span className="rounded-full bg-primary/10 px-md py-xs text-sm font-bold text-primary">
                  {booking.bookingStatus}
                </span>
              </div>
              <div className="grid grid-cols-1 gap-sm text-sm text-on-surface-variant sm:grid-cols-3">
                <div>Check in: {booking.checkIn}</div>
                <div>Check out: {booking.checkOut}</div>
                <div>Total: ${Number(booking.totalPrice || 0).toFixed(2)}</div>
              </div>
              {booking.bookingStatus !== 'CANCELLED' && (
                <button
                  className="mt-md rounded-xl border border-error-container px-md py-sm font-bold text-on-error-container"
                  onClick={() => handleCancel(booking.id)}
                >
                  Cancel booking
                </button>
              )}
            </article>
          ))}
        </div>
      )}
    </div>
  );
}
