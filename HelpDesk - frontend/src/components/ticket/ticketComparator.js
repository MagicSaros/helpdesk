class Comparator {

    static compareUrgency(x, y) {
        let order = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
        return order.indexOf(x.urgency) - order.indexOf(y.urgency);
    }

    static compareName(x, y) {
        return x.name >= y.name;
    }
    
    static compareDesiredDate(x, y) {
        return x.desiredResolutionDate - y.desiredResolutionDate;
    }

    static compareId(x, y) {
        return x.id - y.id;
    }

    static compareStatus(x, y) {
        return x.state >= y.state;
    }
}

export default Comparator;