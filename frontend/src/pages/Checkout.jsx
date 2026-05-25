import React, { useContext, useEffect, useMemo, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import api from '../services/api';
import { normalizeRoom } from '../services/normalizers';

export default function Checkout() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const roomId = searchParams.get('roomId');

  const [room, setRoom] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [booking, setBooking] = useState(null);
  const [form, setForm] = useState({
    checkInDate: new Date(Date.now() + 86400000).toISOString().split('T')[0],
    checkOutDate: new Date(Date.now() + 86400000 * 2).toISOString().split('T')[0],
  });

  useEffect(() => {
    if (!user) {
      navigate(`/login?redirect=${encodeURIComponent(`/checkout?roomId=${roomId}`)}`);
      return;
    }

    if (!roomId) {
      navigate('/hotels');
      return;
    }

    setLoading(true);
    api
      .get(`/rooms/${roomId}`)
      .then((res) => setRoom(normalizeRoom(res.data)))
      .catch((err) => {
        console.error('Failed to load room', err);
        setError('Failed to load room details.');
      })
      .finally(() => setLoading(false));
  }, [navigate, roomId, user]);

  const nights = useMemo(() => {
    const start = new Date(form.checkInDate);
    const end = new Date(form.checkOutDate);
    const diff = Math.ceil((end - start) / (1000 * 60 * 60 * 24));
    return Math.max(1, diff);
  }, [form.checkInDate, form.checkOutDate]);

  const total = room ? room.price * nights : 0;

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (new Date(form.checkOutDate) <= new Date(form.checkInDate)) {
      setError('Check-out must be after check-in.');
      return;
    }

    setSubmitting(true);
    setError('');

    try {
      const res = await api.post('/bookings', {
        roomId: room.id,
        checkInDate: form.checkInDate,
        checkOutDate: form.checkOutDate,
        totalPrice: total,
      });
      setBooking(res.data);
    } catch (err) {
      console.error('Failed to create booking', err);
      setError(err.response?.data?.message || 'Booking failed.');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return <div className="py-xl text-center text-secondary">Preparing checkout...</div>;
  }

  if (booking) {
    return (
      <div className="mx-auto max-w-2xl px-lg py-xl text-center">
        <div className="rounded-3xl bg-white p-xl shadow-[0px_8px_30px_rgba(15,23,42,0.08)]">
          <h2 className="mb-sm text-3xl font-bold text-primary">Booking confirmed</h2>
          <p className="mb-md text-secondary">Reservation ID: {booking.bookingId}</p>
          <p className="mb-lg text-on-surface-variant">
            Your stay has been saved. You can manage it from your profile.
          </p>
          <div className="flex flex-col gap-sm sm:flex-row sm:justify-center">
            <button className="rounded-xl bg-primary px-lg py-sm font-bold text-white" onClick={() => navigate('/profile')}>
              View my bookings
            </button>
            <button className="rounded-xl border border-outline-variant px-lg py-sm font-bold text-primary" onClick={() => navigate('/hotels')}>
              Book another stay
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="mx-auto max-w-4xl px-lg py-xl">
      <div className="grid grid-cols-1 gap-lg lg:grid-cols-[1.2fr_0.8fr]">
        <form className="rounded-3xl bg-white p-lg shadow-[0px_8px_30px_rgba(15,23,42,0.08)]" onSubmit={handleSubmit}>
          <h1 className="mb-md text-3xl font-bold text-primary">Complete your booking</h1>
          {error && <div className="mb-md rounded-xl bg-error-container/30 px-md py-sm text-on-error-container">{error}</div>}
          <div className="grid grid-cols-1 gap-md sm:grid-cols-2">
            <label className="flex flex-col gap-xs">
              <span className="text-sm font-bold uppercase tracking-wide text-secondary">Check in</span>
              <input
                className="rounded-xl border border-outline-variant/40 px-md py-sm"
                min={new Date().toISOString().split('T')[0]}
                type="date"
                value={form.checkInDate}
                onChange={(e) => setForm((current) => ({ ...current, checkInDate: e.target.value }))}
              />
            </label>
            <label className="flex flex-col gap-xs">
              <span className="text-sm font-bold uppercase tracking-wide text-secondary">Check out</span>
              <input
                className="rounded-xl border border-outline-variant/40 px-md py-sm"
                min={form.checkInDate}
                type="date"
                value={form.checkOutDate}
                onChange={(e) => setForm((current) => ({ ...current, checkOutDate: e.target.value }))}
              />
            </label>
          </div>
          <button className="mt-lg w-full rounded-xl bg-[#FF7A00] px-lg py-md font-bold text-white" disabled={submitting} type="submit">
            {submitting ? 'Booking...' : `Confirm booking for $${total.toFixed(2)}`}
          </button>
        </form>

        <aside className="rounded-3xl bg-white p-lg shadow-[0px_8px_30px_rgba(15,23,42,0.08)]">
          <h2 className="mb-md text-2xl font-bold text-primary">Stay summary</h2>
          {room && (
            <>
              <p className="text-lg font-bold text-primary">{room.roomType}</p>
              <p className="mb-sm text-secondary">Room ID: {room.id}</p>
              <p className="mb-md text-on-surface-variant">{room.description || 'Comfortable room with hotel amenities included.'}</p>
              <div className="space-y-sm text-sm">
                <div className="flex justify-between">
                  <span className="text-secondary">Nightly rate</span>
                  <span className="font-bold text-primary">${room.price}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-secondary">Nights</span>
                  <span className="font-bold text-primary">{nights}</span>
                </div>
                <div className="flex justify-between border-t border-outline-variant/30 pt-sm">
                  <span className="text-secondary">Total</span>
                  <span className="text-xl font-bold text-[#FF7A00]">${total.toFixed(2)}</span>
                </div>
              </div>
            </>
          )}
        </aside>
      </div>
    </div>
  );
}
