package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorSensorController {

    private DistanceSensor _distanceSensor;
    private final double _maxDistanceFromCam = 2.5; // In centimeters

    public ColorSensorController(DistanceSensor colorSensor){
        _distanceSensor = colorSensor;
    }


    public boolean IsBallIdentified() {

        return false;
    }
}
