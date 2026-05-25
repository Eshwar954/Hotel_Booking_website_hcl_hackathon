import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import api from '../services/api';
import { normalizeBooking, normalizeHotel, normalizeRoom } from '../services/normalizers';

const emptyHotelForm = {
  hotelName: '',
  location: '',
  description: '',
  ownerName: '',
  ownerEmail: '',
  ownerPhone: '',
  imageUrl: '',
};

const emptyRoomForm = {
  hotelId: '',
  roomType: '',
  price: '',
  onlineAvailableRooms: '',
  description: '',
};

export default function AdminDashboard() {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  const [stats, setStats] = useState(null);
  const [hotels, setHotels] = useState([]);
  const [rooms, setRooms] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [users, setUsers] = useState([]);
  const [hotelForm, setHotelForm] = useState(emptyHotelForm);
  const [roomForm, setRoomForm] = useState(emptyRoomForm);
  const [savingHotel, setSavingHotel] = useState(false);
  const [savingRoom, setSavingRoom] = useState(false);

  useEffect(() => {
    if (!user || user.role !== 'ADMIN') {
      navigate('/login');
      return;
    }

    loadDashboard();
  }, [navigate, user]);

  const loadDashboard = async () => {
    try {
      const [statsRes, hotelsRes, roomsRes, bookingsRes, usersRes] = await Promise.all([
        api.get('/admin/dashboard'),
        api.get('/hotels'),
        api.get('/rooms'),
        api.get('/admin/bookings'),
        api.get('/admin/users'),
      ]);

      setStats(statsRes.data);
      setHotels(hotelsRes.data.map(normalizeHotel));
      setRooms(roomsRes.data.map(normalizeRoom));
      setBookings(bookingsRes.data.map(normalizeBooking));
      setUsers(usersRes.data);
    } catch (err) {
      console.error('Failed to load admin dashboard', err);
    }
  };

  const handleHotelSubmit = async (e) => {
    e.preventDefault();
    setSavingHotel(true);
    try {
      await api.post('/hotels', hotelForm);
      setHotelForm(emptyHotelForm);
      await loadDashboard();
    } finally {
      setSavingHotel(false);
    }
  };

  const handleRoomSubmit = async (e) => {
    e.preventDefault();
    setSavingRoom(true);
    try {
      await api.post('/rooms', {
        ...roomForm,
        hotelId: Number(roomForm.hotelId),
        price: Number(roomForm.price),
        onlineAvailableRooms: Number(roomForm.onlineAvailableRooms),
      });
      setRoomForm(emptyRoomForm);
      await loadDashboard();
    } finally {
      setSavingRoom(false);
    }
  };

  const updateBookingStatus = async (bookingId, status) => {
    await api.put(`/bookings/status/${bookingId}`, { status });
    await loadDashboard();
  };

  if (!user || user.role !== 'ADMIN') {
    return null;
  }

  return (
    <div className="mx-auto max-w-container-max px-lg py-xl">
      <div className="mb-lg">
        <h1 className="text-4xl font-bold text-primary">Admin Dashboard</h1>
        <p className="text-secondary">Connected view of hotels, rooms, bookings, and users.</p>
      </div>

      <section className="mb-lg grid grid-cols-1 gap-md md:grid-cols-3">
        <div className="rounded-3xl bg-white p-lg shadow-sm">
          <p className="text-sm font-bold uppercase tracking-wide text-secondary">Hotels</p>
          <p className="mt-sm text-4xl font-bold text-primary">{stats?.rooms != null ? hotels.length : '-'}</p>
        </div>
        <div className="rounded-3xl bg-white p-lg shadow-sm">
          <p className="text-sm font-bold uppercase tracking-wide text-secondary">Bookings</p>
          <p className="mt-sm text-4xl font-bold text-primary">{stats?.bookings ?? bookings.length}</p>
        </div>
        <div className="rounded-3xl bg-white p-lg shadow-sm">
          <p className="text-sm font-bold uppercase tracking-wide text-secondary">Users</p>
          <p className="mt-sm text-4xl font-bold text-primary">{stats?.users ?? users.length}</p>
        </div>
      </section>

      <section className="mb-lg grid grid-cols-1 gap-lg lg:grid-cols-2">
        <form className="rounded-3xl bg-white p-lg shadow-sm" onSubmit={handleHotelSubmit}>
          <h2 className="mb-md text-2xl font-bold text-primary">Create hotel</h2>
          <div className="grid grid-cols-1 gap-sm">
            <input className="rounded-xl border border-outline-variant/40 px-md py-sm" placeholder="Hotel name" value={hotelForm.hotelName} onChange={(e) => setHotelForm((c) => ({ ...c, hotelName: e.target.value }))} />
            <input className="rounded-xl border border-outline-variant/40 px-md py-sm" placeholder="Location" value={hotelForm.location} onChange={(e) => setHotelForm((c) => ({ ...c, location: e.target.value }))} />
            <textarea className="rounded-xl border border-outline-variant/40 px-md py-sm" placeholder="Description" rows="4" value={hotelForm.description} onChange={(e) => setHotelForm((c) => ({ ...c, description: e.target.value }))} />
            <input className="rounded-xl border border-outline-variant/40 px-md py-sm" placeholder="Image URL" value={hotelForm.imageUrl} onChange={(e) => setHotelForm((c) => ({ ...c, imageUrl: e.target.value }))} />
            <button className="rounded-xl bg-primary px-lg py-sm font-bold text-white" disabled={savingHotel} type="submit">
              {savingHotel ? 'Saving...' : 'Add hotel'}
            </button>
          </div>
        </form>

        <form className="rounded-3xl bg-white p-lg shadow-sm" onSubmit={handleRoomSubmit}>
          <h2 className="mb-md text-2xl font-bold text-primary">Create room</h2>
          <div className="grid grid-cols-1 gap-sm">
            <select className="rounded-xl border border-outline-variant/40 px-md py-sm" value={roomForm.hotelId} onChange={(e) => setRoomForm((c) => ({ ...c, hotelId: e.target.value }))}>
              <option value="">Select hotel</option>
              {hotels.map((hotel) => (
                <option key={hotel.id} value={hotel.id}>
                  {hotel.hotelName}
                </option>
              ))}
            </select>
            <input className="rounded-xl border border-outline-variant/40 px-md py-sm" placeholder="Room type" value={roomForm.roomType} onChange={(e) => setRoomForm((c) => ({ ...c, roomType: e.target.value }))} />
            <input className="rounded-xl border border-outline-variant/40 px-md py-sm" placeholder="Price" type="number" value={roomForm.price} onChange={(e) => setRoomForm((c) => ({ ...c, price: e.target.value }))} />
            <input className="rounded-xl border border-outline-variant/40 px-md py-sm" placeholder="Online available rooms" type="number" value={roomForm.onlineAvailableRooms} onChange={(e) => setRoomForm((c) => ({ ...c, onlineAvailableRooms: e.target.value }))} />
            <textarea className="rounded-xl border border-outline-variant/40 px-md py-sm" placeholder="Description" rows="4" value={roomForm.description} onChange={(e) => setRoomForm((c) => ({ ...c, description: e.target.value }))} />
            <button className="rounded-xl bg-[#FF7A00] px-lg py-sm font-bold text-white" disabled={savingRoom} type="submit">
              {savingRoom ? 'Saving...' : 'Add room'}
            </button>
          </div>
        </form>
      </section>

      <section className="mb-lg rounded-3xl bg-white p-lg shadow-sm">
        <h2 className="mb-md text-2xl font-bold text-primary">Recent bookings</h2>
        <div className="space-y-sm">
          {bookings.map((booking) => (
            <div className="flex flex-col gap-sm rounded-2xl border border-outline-variant/30 p-md md:flex-row md:items-center md:justify-between" key={booking.id}>
              <div>
                <p className="font-bold text-primary">{booking.hotelName}</p>
                <p className="text-sm text-secondary">
                  {booking.userName} • {booking.roomType} • {booking.checkIn} to {booking.checkOut}
                </p>
              </div>
              <div className="flex items-center gap-sm">
                <span className="rounded-full bg-primary/10 px-md py-xs text-sm font-bold text-primary">
                  {booking.bookingStatus}
                </span>
                <button className="rounded-xl border border-outline-variant px-md py-xs font-bold text-primary" onClick={() => updateBookingStatus(booking.id, 'CONFIRMED')}>
                  Confirm
                </button>
                <button className="rounded-xl border border-error-container px-md py-xs font-bold text-on-error-container" onClick={() => updateBookingStatus(booking.id, 'CANCELLED')}>
                  Cancel
                </button>
              </div>
            </div>
          ))}
        </div>
      </section>

      <section className="grid grid-cols-1 gap-lg lg:grid-cols-2">
        <div className="rounded-3xl bg-white p-lg shadow-sm">
          <h2 className="mb-md text-2xl font-bold text-primary">Rooms</h2>
          <div className="space-y-sm">
            {rooms.map((room) => (
              <div className="rounded-2xl border border-outline-variant/30 p-md" key={room.id}>
                <p className="font-bold text-primary">{room.roomType}</p>
                <p className="text-sm text-secondary">Hotel ID: {room.hotelId}</p>
                <p className="text-sm text-secondary">Available online: {room.onlineAvailableRooms}</p>
              </div>
            ))}
          </div>
        </div>

        <div className="rounded-3xl bg-white p-lg shadow-sm">
          <h2 className="mb-md text-2xl font-bold text-primary">Users</h2>
          <div className="space-y-sm">
            {users.map((account) => (
              <div className="rounded-2xl border border-outline-variant/30 p-md" key={account.userId}>
                <p className="font-bold text-primary">{account.name}</p>
                <p className="text-sm text-secondary">{account.email}</p>
                <p className="text-sm text-secondary">Role: {account.role}</p>
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}
