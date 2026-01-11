package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import kotlin.NotImplementedError;

public class ColorSensorController {
    private final NormalizedColorSensor _colorSensor;

    public ColorSensorController(NormalizedColorSensor colorSensor){
        _colorSensor = colorSensor;
    }
    public boolean IsBallIdentified(){
        throw new NotImplementedError("TODO: need to implement method that checks if ball is there, via color sensor");
    }
}
