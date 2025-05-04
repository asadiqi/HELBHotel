package com.example.helbhotel.strategy;

import com.example.helbhotel.model.*;
import com.example.helbhotel.parser.Reservation;

import java.util.List;

public class StayPurposeAssignmentStrategy implements RoomAssignmentStrategy {
    @Override
    public Room assignRoom(Hotel hotel, Reservation reservation) {
      /*  for (Room room : availableRooms) {
            if ("Affaire".equalsIgnoreCase(reservation.motifSejour) && room instanceof BusinessRoom) {
                return room;
            } else if (("Tourisme".equalsIgnoreCase(reservation.motifSejour) || "Autre".equalsIgnoreCase(reservation.motifSejour))) {
                if (reservation.fumeur && reservation.nombreEnfants > 0 && room instanceof EconomicRoom) {
                    return room;
                }
            }
        }

       */
        return null;
    }
}