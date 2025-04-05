package tn.esprit.livrable.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.HashMap;
import java.util.Map;

@Entity
public class BuildingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buildingType; // Maison, Immeuble, Bureau
    private double length;
    private double width;
    private int floorCount;
    private double floorHeight;
    private int roomCount;
    private String architecturalStyle; // Moderne, Classique, Industriel

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBuildingType() { return buildingType; }
    public void setBuildingType(String buildingType) { this.buildingType = buildingType; }
    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    public int getFloorCount() { return floorCount; }
    public void setFloorCount(int floorCount) { this.floorCount = floorCount; }
    public double getFloorHeight() { return floorHeight; }
    public void setFloorHeight(double floorHeight) { this.floorHeight = floorHeight; }
    public int getRoomCount() { return roomCount; }
    public void setRoomCount(int roomCount) { this.roomCount = roomCount; }
    public String getArchitecturalStyle() { return architecturalStyle; }
    public void setArchitecturalStyle(String architecturalStyle) { this.architecturalStyle = architecturalStyle; }

    public Building3DData generate3DData() {
        Building3DData data = new Building3DData();
        data.setLength(this.length);
        data.setWidth(this.width);
        data.setHeight(this.floorCount * this.floorHeight);
        data.setStyle(this.architecturalStyle);

        // Calculate room distribution
        Map<Integer, Integer> roomsPerFloor = new HashMap<>();
        int roomsLeft = this.roomCount;
        for (int i = 1; i <= this.floorCount; i++) {
            roomsPerFloor.put(i, Math.min(roomsLeft, 4)); // Max 4 rooms per floor
            roomsLeft -= roomsPerFloor.get(i);
        }
        data.setRoomsPerFloor(roomsPerFloor);

        // Determine roof type
        data.setRoofType(this.buildingType.equals("Maison") ? "Sloped" : "Flat");

        return data;
    }
}