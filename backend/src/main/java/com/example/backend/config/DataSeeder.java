package com.example.backend.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.backend.model.Amenity;
import com.example.backend.model.Booking;
import com.example.backend.model.Hotel;
import com.example.backend.model.Role;
import com.example.backend.model.Room;
import com.example.backend.model.RoomAmenity;
import com.example.backend.model.User;
import com.example.backend.repository.AmenityRepository;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.HotelRepository;
import com.example.backend.repository.RoomAmenityRepository;
import com.example.backend.repository.RoomRepository;
import com.example.backend.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

        private final UserRepository userRepository;
        private final HotelRepository hotelRepository;
        private final RoomRepository roomRepository;
        private final BookingRepository bookingRepository;
        private final AmenityRepository amenityRepository;
        private final RoomAmenityRepository roomAmenityRepository;
        private final PasswordEncoder passwordEncoder;

        public DataSeeder(
                        UserRepository userRepository,
                        HotelRepository hotelRepository,
                        RoomRepository roomRepository,
                        BookingRepository bookingRepository,
                        AmenityRepository amenityRepository,
                        RoomAmenityRepository roomAmenityRepository,
                        PasswordEncoder passwordEncoder) {

                this.userRepository = userRepository;
                this.hotelRepository = hotelRepository;
                this.roomRepository = roomRepository;
                this.bookingRepository = bookingRepository;
                this.amenityRepository = amenityRepository;
                this.roomAmenityRepository = roomAmenityRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) {

                if (userRepository.count() > 0) {
                        return;
                }

                User admin = createUser(
                                "Admin",
                                "admin@gmail.com",
                                "admin123",
                                Role.ADMIN);

                User user = createUser(
                                "Eshwar",
                                "user@gmail.com",
                                "user123",
                                Role.USER);

                Amenity wifi = amenityRepository.save(
                                createAmenity("WiFi"));

                Amenity pool = amenityRepository.save(
                                createAmenity("Swimming Pool"));

                Amenity breakfast = amenityRepository.save(
                                createAmenity("Breakfast"));

                Hotel hotel = hotelRepository.save(
                                createHotel(
                                                "Grand Palace Hotel",
                                                "Hyderabad",
                                                "Luxury hotel with premium rooms",
                                                "Ramesh Kumar",
                                                "owner@gmail.com",
                                                "9876543210",
                                                "hotel-image.jpg"));

                Room deluxeRoom = roomRepository.save(
                                createRoom(
                                                hotel,
                                                "Deluxe Room",
                                                2500.0,
                                                10,
                                                "Spacious deluxe room"));

                Room suiteRoom = roomRepository.save(
                                createRoom(
                                                hotel,
                                                "Suite Room",
                                                5000.0,
                                                5,
                                                "Premium suite room"));

                roomAmenityRepository.save(
                                createRoomAmenity(
                                                deluxeRoom,
                                                wifi));

                roomAmenityRepository.save(
                                createRoomAmenity(
                                                deluxeRoom,
                                                breakfast));

                roomAmenityRepository.save(
                                createRoomAmenity(
                                                suiteRoom,
                                                wifi));

                roomAmenityRepository.save(
                                createRoomAmenity(
                                                suiteRoom,
                                                pool));

                bookingRepository.save(
                                createBooking(
                                                user,
                                                deluxeRoom,
                                                LocalDate.now().plusDays(1),
                                                LocalDate.now().plusDays(3),
                                                5000.0,
                                                "CONFIRMED"));
        }

        private User createUser(
                        String name,
                        String email,
                        String password,
                        Role role) {

                User user = new User();

                user.setName(name);
                user.setEmail(email);

                user.setPassword(
                                passwordEncoder.encode(password));

                user.setRole(role);

                return userRepository.save(user);
        }

        private Amenity createAmenity(
                        String name) {

                Amenity amenity = new Amenity();

                amenity.setName(name);

                return amenity;
        }

        private Hotel createHotel(
                        String hotelName,
                        String location,
                        String description,
                        String ownerName,
                        String ownerEmail,
                        String ownerPhone,
                        String imageUrl) {

                Hotel hotel = new Hotel();

                hotel.setHotelName(hotelName);
                hotel.setLocation(location);
                hotel.setDescription(description);
                hotel.setOwnerName(ownerName);
                hotel.setOwnerEmail(ownerEmail);
                hotel.setOwnerPhone(ownerPhone);
                hotel.setImageUrl(imageUrl);

                return hotel;
        }

        private Room createRoom(
                        Hotel hotel,
                        String roomType,
                        Double price,
                        Integer availableRooms,
                        String description) {

                Room room = new Room();

                room.setHotel(hotel);
                room.setRoomType(roomType);
                room.setPrice(price);
                room.setOnlineAvailableRooms(
                                availableRooms);
                room.setDescription(description);

                return room;
        }

        private RoomAmenity createRoomAmenity(
                        Room room,
                        Amenity amenity) {

                RoomAmenity roomAmenity = new RoomAmenity();

                roomAmenity.setRoom(room);
                roomAmenity.setAmenity(amenity);

                return roomAmenity;
        }

        private Booking createBooking(
                        User user,
                        Room room,
                        LocalDate checkInDate,
                        LocalDate checkOutDate,
                        Double totalPrice,
                        String status) {

                Booking booking = new Booking();

                booking.setUser(user);
                booking.setRoom(room);
                booking.setCheckInDate(checkInDate);
                booking.setCheckOutDate(checkOutDate);
                booking.setTotalPrice(totalPrice);
                booking.setStatus(status);

                return booking;
        }
}
