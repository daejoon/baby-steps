
function createReservation(passenger, flight, saver) {
    const reservation = {
        passengerInformation: passenger,
        flightInformation: flight,
    };

    saver.saveReservation(reservation);
    return reservation;
}

const ReservationSaver = function () {
};

ReservationSaver.prototype.saveReservation = function (reservation) {
};