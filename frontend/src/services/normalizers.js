export const normalizeHotel = (hotel = {}) => ({
  ...hotel,
  id: hotel.id ?? hotel.hotelId,
  image: hotel.image ?? hotel.imageUrl ?? 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&q=80&w=1200',
});

export const normalizeRoom = (room = {}) => {
  const availableRooms = room.onlineAvailableRooms ?? 0;
  return {
    ...room,
    id: room.id ?? room.roomId,
    hotelId: room.hotelId,
    capacity: room.capacity ?? Math.max(1, availableRooms),
    availability: room.availability ?? availableRooms > 0,
    onlineAvailableRooms: availableRooms,
  };
};

export const normalizeBooking = (booking = {}) => ({
  ...booking,
  id: booking.id ?? booking.bookingId,
  checkIn: booking.checkIn ?? booking.checkInDate,
  checkOut: booking.checkOut ?? booking.checkOutDate,
  bookingStatus: booking.bookingStatus ?? booking.status,
  user: booking.user ?? {
    id: booking.userId,
    name: booking.userName,
    email: booking.userEmail,
  },
  room: booking.room ?? {
    id: booking.roomId,
    roomType: booking.roomType,
    hotel: {
      id: booking.hotelId,
      hotelName: booking.hotelName,
    },
  },
});
