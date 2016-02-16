package casa.kieran.dpi.input;

import java.util.Arrays;

public class Packet implements Input {

    private org.pcap4j.packet.Packet packet;
    private String location;

    private String id;

    public Packet(final org.pcap4j.packet.Packet packet, String location) {
        this.packet = packet;
        this.location = location;
        this.id = Integer.toHexString(Arrays.hashCode(packet.getRawData()));
    }

    @Override
    public String getID() {
        return id;
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
    public String getString() {
        return new String(packet.getRawData());
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
