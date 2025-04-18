package tn.esprit.livrable.entity;

import java.util.Map;

public class Building3DData {
    private double length;
    private double width;
    private double height;
    private Map<Integer, Integer> roomsPerFloor;
    private String style;
    private String roofType;

    // Getters and Setters
    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public Map<Integer, Integer> getRoomsPerFloor() { return roomsPerFloor; }
    public void setRoomsPerFloor(Map<Integer, Integer> roomsPerFloor) { this.roomsPerFloor = roomsPerFloor; }
    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }
    public String getRoofType() { return roofType; }
    public void setRoofType(String roofType) { this.roofType = roofType; }

}
