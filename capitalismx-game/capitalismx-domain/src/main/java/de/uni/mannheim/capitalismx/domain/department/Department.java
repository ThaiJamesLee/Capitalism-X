package de.uni.mannheim.capitalismx.domain.department;

public interface Department {

    String getName();

    int getLevel();

    void setLevel(int level);

    LevelingMechanism getLevelingMechanism();
}
