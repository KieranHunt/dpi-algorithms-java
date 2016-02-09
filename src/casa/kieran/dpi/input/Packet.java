package casa.kieran.dpi.input;

public class Packet implements Input {

    private org.pcap4j.packet.Packet packet;
    private String location;

    public Packet(final org.pcap4j.packet.Packet packet, String location) {
        this.packet = packet;
        this.location = location;
    }

    @Override
    public Byte getByte(Integer location) {
        return packet.getRawData()[location];
    }

    @Override
    public Integer getLength() {
        return packet.getRawData().length;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Packet packet1 = (Packet) o;

        return packet.equals(packet1.packet);

    }

    @Override
    public int hashCode() {
        return packet.hashCode();
    }
}
